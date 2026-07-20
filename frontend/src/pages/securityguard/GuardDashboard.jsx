import React, { useState, useEffect } from 'react';
import {
  ShieldCheck, UserPlus, QrCode, ClipboardList,
  AlertCircle, LogOut, Loader2, CheckCircle,
  Package, X
} from 'lucide-react';
import { useAuth } from '../../hooks/useAuth';
import useToast from '../../hooks/useToast';
import {
  addDeliveryVisitor, getAllVisitors, markExit,
  verifyGuestBarcode, getGuardComplaints, createGuardComplaint
} from '../../services/guardService';

export default function GuardDashboard() {
  const { user, logout } = useAuth();
  const { showToast, ToastComponent } = useToast();
  const [activeTab, setActiveTab] = useState('dashboard');
  const [isLoading, setIsLoading] = useState(false);

  // Data states
  const [visitors, setVisitors] = useState([]);
  const [complaints, setComplaints] = useState([]);
  const [guestVerification, setGuestVerification] = useState(null);

  // Modals state
  const [isComplaintModalOpen, setComplaintModalOpen] = useState(false);
  const [barcodeInput, setBarcodeInput] = useState('');

  // Forms state
  const [deliveryForm, setDeliveryForm] = useState({
    visitorName: '', companyName: '', vehicleType: 'NONE', vehicleNumber: '', flatId: '', mobileNumber: ''
  });
  const [complaintForm, setComplaintForm] = useState({ title: '', description: '', category: 'SECURITY' });
  const [deliverySuccess, setDeliverySuccess] = useState(false);

  useEffect(() => {
    fetchInitialData();
  }, []);

  useEffect(() => {
    if (activeTab === 'dashboard') fetchInitialData();
    if (activeTab === 'visitor_log') fetchVisitors();
    if (activeTab === 'my_complaints') fetchComplaints();
  }, [activeTab]);

  const fetchInitialData = async () => {
    setIsLoading(true);
    try {
      const [visRes, compRes] = await Promise.all([
        getAllVisitors(), getGuardComplaints()
      ]);
      setVisitors(visRes.data || []);
      setComplaints(compRes.data || []);
    } catch (e) {
      console.error(e);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchVisitors = async () => {
    setIsLoading(true);
    try {
      const res = await getAllVisitors();
      setVisitors(res.data || []);
    } catch (e) { showToast('Error fetching visitors', 'error'); } finally { setIsLoading(false); }
  };

  const fetchComplaints = async () => {
    setIsLoading(true);
    try {
      const res = await getGuardComplaints();
      setComplaints(res.data || []);
    } catch (e) { showToast('Error fetching complaints', 'error'); } finally { setIsLoading(false); }
  };

  // Handlers
  const handleDeliverySubmit = async (e) => {
    e.preventDefault();
    try {
      await addDeliveryVisitor(deliveryForm);
      setDeliverySuccess(true);
      showToast('Delivery visitor added successfully', 'success');
      setDeliveryForm({ visitorName: '', companyName: '', vehicleType: 'NONE', vehicleNumber: '', flatId: '', mobileNumber: '' });
      setTimeout(() => setDeliverySuccess(false), 3000);
      fetchVisitors();
    } catch (error) { showToast('Error adding delivery visitor', 'error'); }
  };

  const handleVerifyGuest = async (e) => {
    e.preventDefault();
    setGuestVerification(null);
    try {
      const res = await verifyGuestBarcode(barcodeInput);
      setGuestVerification({ success: true, data: res.data });
      showToast('Guest verified successfully', 'success');
    } catch (error) {
      setGuestVerification({ success: false, error: 'Invalid or expired barcode.' });
      showToast('Invalid barcode', 'error');
    }
  };

  const handleMarkExit = async (visitorId) => {
    try {
      await markExit(visitorId);
      showToast('Exit recorded successfully', 'success');
      fetchVisitors();
    } catch (error) { showToast('Error recording exit', 'error'); }
  };

  const handleComplaintSubmit = async (e) => {
    e.preventDefault();
    try {
      await createGuardComplaint(complaintForm);
      showToast('Complaint raised successfully', 'success');
      setComplaintModalOpen(false);
      setComplaintForm({ title: '', description: '', category: 'SECURITY' });
      fetchComplaints();
    } catch (error) { showToast('Error raising complaint', 'error'); }
  };

  const pendingDeliveries = visitors.filter(v => v.visitorType === 'DELIVERY' && !v.exitTime).length;
  const todaysVisitors = visitors.filter(v => new Date(v.entryTime).toDateString() === new Date().toDateString()).length;

  return (
    <div className="flex h-screen bg-gray-950 text-white font-sans overflow-hidden">
      {ToastComponent}

      {/* Sidebar */}
      <aside className="w-64 bg-gray-900 border-r border-gray-800 flex flex-col justify-between hidden md:flex">
        <div>
          <div className="p-6 border-b border-gray-800">
            <h1 className="text-2xl font-bold bg-gradient-to-r from-orange-400 to-amber-400 bg-clip-text text-transparent">
              Society Sphere
            </h1>
            <p className="text-xs text-gray-400 mt-1">Guard Portal</p>
          </div>
          <nav className="p-4 space-y-1">
            {[
              { id: 'dashboard', icon: ShieldCheck, label: 'Dashboard' },
              { id: 'add_delivery', icon: Package, label: 'Add Delivery' },
              { id: 'verify_guest', icon: QrCode, label: 'Verify Guest' },
              { id: 'visitor_log', icon: ClipboardList, label: 'Visitor Log' },
              { id: 'my_complaints', icon: AlertCircle, label: 'My Complaints' },
            ].map((item) => {
              const Icon = item.icon;
              return (
                <button
                  key={item.id}
                  onClick={() => setActiveTab(item.id)}
                  className={`w-full flex items-center px-4 py-3 rounded-xl transition-all duration-200 ${
                    activeTab === item.id
                      ? 'bg-orange-600 text-white shadow-lg shadow-orange-900/20'
                      : 'text-gray-400 hover:bg-gray-800 hover:text-white'
                  }`}
                >
                  <Icon className="w-5 h-5 mr-3" />
                  <span className="font-medium">{item.label}</span>
                </button>
              );
            })}
          </nav>
        </div>
        <div className="p-4 border-t border-gray-800">
          <button onClick={logout} className="w-full flex items-center px-4 py-3 text-red-400 hover:bg-red-950/30 rounded-xl transition-all">
            <LogOut className="w-5 h-5 mr-3" />
            <span className="font-medium">Logout</span>
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-1 flex flex-col h-full overflow-hidden">
        <header className="h-16 flex items-center justify-between px-8 bg-gray-900/50 backdrop-blur-md border-b border-gray-800 sticky top-0 z-10">
          <h2 className="text-xl font-semibold capitalize">{activeTab.replace(/_/g, ' ')}</h2>
          <div className="flex items-center space-x-4">
            <div className="text-right">
              <p className="text-sm font-medium">{user?.name || 'Security Guard'}</p>
              <p className="text-xs text-orange-400 font-bold">On Duty</p>
            </div>
            <div className="w-10 h-10 rounded-full bg-gradient-to-tr from-orange-500 to-amber-500 flex items-center justify-center font-bold">
              {user?.name?.charAt(0) || 'G'}
            </div>
          </div>
        </header>

        <div className="flex-1 overflow-auto p-8">
          {isLoading && activeTab === 'dashboard' ? (
            <div className="flex items-center justify-center h-64">
              <Loader2 className="w-8 h-8 animate-spin text-orange-500" />
            </div>
          ) : (
            <div className="space-y-6">
              
              {/* DASHBOARD TAB */}
              {activeTab === 'dashboard' && (
                <>
                  <div className="bg-gradient-to-r from-orange-900/40 to-amber-900/40 border border-orange-500/20 rounded-2xl p-8 mb-6 shadow-2xl">
                    <h2 className="text-2xl font-bold mb-2">Shift Active, {user?.name}!</h2>
                    <p className="text-orange-100/80">Stay vigilant and keep the society secure.</p>
                  </div>
                  
                  <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">Today's Visitors</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{todaysVisitors}</h3>
                        <Users className="w-8 h-8 text-blue-400 opacity-80" />
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">Inside Deliveries</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{pendingDeliveries}</h3>
                        <Package className="w-8 h-8 text-orange-400 opacity-80" />
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">My Open Complaints</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{complaints.filter(c => c.status !== 'RESOLVED').length}</h3>
                        <AlertCircle className="w-8 h-8 text-red-400 opacity-80" />
                      </div>
                    </div>
                  </div>
                </>
              )}

              {/* ADD DELIVERY TAB */}
              {activeTab === 'add_delivery' && (
                <div className="max-w-2xl mx-auto bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50 bg-gray-900/50">
                    <h3 className="font-semibold text-lg flex items-center"><Package className="w-5 h-5 mr-2 text-orange-400" /> Log New Delivery</h3>
                  </div>
                  {deliverySuccess && (
                    <div className="bg-emerald-500/20 border-l-4 border-emerald-500 p-4 m-6 rounded-r-xl flex items-center">
                      <CheckCircle className="text-emerald-400 w-5 h-5 mr-3" />
                      <p className="text-emerald-100 font-medium">Delivery logged successfully!</p>
                    </div>
                  )}
                  <form onSubmit={handleDeliverySubmit} className="p-6 space-y-5">
                    <div className="grid grid-cols-2 gap-5">
                      <div><label className="block text-sm text-gray-400 mb-1">Visitor Name</label><input required className="w-full bg-gray-900 border border-gray-700 rounded-xl px-4 py-3 text-white focus:border-orange-500 outline-none" value={deliveryForm.visitorName} onChange={e => setDeliveryForm({...deliveryForm, visitorName: e.target.value})} /></div>
                      <div><label className="block text-sm text-gray-400 mb-1">Company Name</label><input required className="w-full bg-gray-900 border border-gray-700 rounded-xl px-4 py-3 text-white focus:border-orange-500 outline-none" placeholder="e.g. Amazon, Swiggy" value={deliveryForm.companyName} onChange={e => setDeliveryForm({...deliveryForm, companyName: e.target.value})} /></div>
                    </div>
                    <div className="grid grid-cols-2 gap-5">
                      <div><label className="block text-sm text-gray-400 mb-1">Vehicle Type</label><select className="w-full bg-gray-900 border border-gray-700 rounded-xl px-4 py-3 text-white focus:border-orange-500 outline-none" value={deliveryForm.vehicleType} onChange={e => setDeliveryForm({...deliveryForm, vehicleType: e.target.value})}><option>NONE</option><option>TWO_WHEELER</option><option>FOUR_WHEELER</option></select></div>
                      <div><label className="block text-sm text-gray-400 mb-1">Vehicle Number</label><input className="w-full bg-gray-900 border border-gray-700 rounded-xl px-4 py-3 text-white focus:border-orange-500 outline-none" value={deliveryForm.vehicleNumber} onChange={e => setDeliveryForm({...deliveryForm, vehicleNumber: e.target.value})} /></div>
                    </div>
                    <div className="grid grid-cols-2 gap-5">
                      <div><label className="block text-sm text-gray-400 mb-1">Flat ID / Number</label><input required type="number" className="w-full bg-gray-900 border border-gray-700 rounded-xl px-4 py-3 text-white focus:border-orange-500 outline-none" value={deliveryForm.flatId} onChange={e => setDeliveryForm({...deliveryForm, flatId: e.target.value})} /></div>
                      <div><label className="block text-sm text-gray-400 mb-1">Mobile Number (Optional)</label><input className="w-full bg-gray-900 border border-gray-700 rounded-xl px-4 py-3 text-white focus:border-orange-500 outline-none" value={deliveryForm.mobileNumber} onChange={e => setDeliveryForm({...deliveryForm, mobileNumber: e.target.value})} /></div>
                    </div>
                    <button type="submit" className="w-full mt-6 bg-orange-600 hover:bg-orange-700 text-white py-4 rounded-xl font-bold text-lg transition-all shadow-lg shadow-orange-900/20">
                      Grant Entry
                    </button>
                  </form>
                </div>
              )}

              {/* VERIFY GUEST TAB */}
              {activeTab === 'verify_guest' && (
                <div className="max-w-xl mx-auto space-y-6">
                  <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden p-8 text-center">
                    <QrCode className="w-16 h-16 mx-auto text-orange-500 mb-4" />
                    <h2 className="text-2xl font-bold mb-6">Scan Guest Barcode</h2>
                    <form onSubmit={handleVerifyGuest} className="space-y-4">
                      <input required type="text" placeholder="Enter barcode number..." className="w-full bg-gray-900 border border-gray-700 rounded-xl px-6 py-4 text-center text-2xl font-mono text-white focus:border-orange-500 outline-none tracking-widest uppercase" value={barcodeInput} onChange={e => setBarcodeInput(e.target.value.toUpperCase())} />
                      <button type="submit" className="w-full bg-orange-600 hover:bg-orange-700 text-white py-4 rounded-xl font-bold text-lg transition-all shadow-lg shadow-orange-900/20">
                        Verify Barcode
                      </button>
                    </form>
                  </div>

                  {guestVerification && (
                    <div className={`p-6 rounded-2xl border ${guestVerification.success ? 'bg-emerald-900/20 border-emerald-500/50' : 'bg-red-900/20 border-red-500/50'}`}>
                      {guestVerification.success ? (
                        <div className="text-center">
                          <CheckCircle className="w-12 h-12 text-emerald-400 mx-auto mb-3" />
                          <h3 className="text-xl font-bold text-emerald-100">Valid Guest Pass</h3>
                          <div className="mt-4 text-left bg-gray-900 p-4 rounded-xl border border-gray-700">
                            <p><span className="text-gray-400">Guest Name:</span> {guestVerification.data.guestName}</p>
                            <p><span className="text-gray-400">Flat:</span> {guestVerification.data.flatNumber}</p>
                            <p><span className="text-gray-400">Host:</span> {guestVerification.data.residentName}</p>
                          </div>
                          <button className="w-full mt-4 bg-emerald-600 hover:bg-emerald-700 text-white py-3 rounded-xl font-bold transition-all shadow-lg">
                            Allow Entry
                          </button>
                        </div>
                      ) : (
                        <div className="text-center">
                          <X className="w-12 h-12 text-red-400 mx-auto mb-3" />
                          <h3 className="text-xl font-bold text-red-100">Verification Failed</h3>
                          <p className="text-red-200 mt-2">{guestVerification.error}</p>
                        </div>
                      )}
                    </div>
                  )}
                </div>
              )}

              {/* VISITOR LOG TAB */}
              {activeTab === 'visitor_log' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50">
                    <h3 className="font-semibold text-lg">Society Visitor Log</h3>
                  </div>
                  <div className="overflow-x-auto">
                    <table className="w-full text-left">
                      <thead className="bg-gray-900/50 text-gray-400 text-sm">
                        <tr>
                          <th className="p-4 font-medium">Name</th>
                          <th className="p-4 font-medium">Type</th>
                          <th className="p-4 font-medium">Flat</th>
                          <th className="p-4 font-medium">Entry</th>
                          <th className="p-4 font-medium">Exit</th>
                          <th className="p-4 font-medium text-right">Actions</th>
                        </tr>
                      </thead>
                      <tbody className="divide-y divide-gray-700/50">
                        {visitors.map((v, idx) => (
                          <tr key={idx} className="hover:bg-gray-700/20 transition-colors">
                            <td className="p-4 font-medium">{v.visitorName}</td>
                            <td className="p-4">
                              <span className={`px-2 py-1 text-xs rounded-lg border ${v.visitorType === 'GUEST' ? 'bg-blue-500/20 text-blue-400 border-blue-500/50' : 'bg-orange-500/20 text-orange-400 border-orange-500/50'}`}>
                                {v.visitorType}
                              </span>
                            </td>
                            <td className="p-4 text-gray-400">{v.flatNumber || v.flatId || '-'}</td>
                            <td className="p-4 text-sm">{new Date(v.entryTime).toLocaleTimeString()}</td>
                            <td className="p-4 text-sm">{v.exitTime ? new Date(v.exitTime).toLocaleTimeString() : '-'}</td>
                            <td className="p-4 text-right">
                              {v.visitorType === 'DELIVERY' && !v.exitTime ? (
                                <button onClick={() => handleMarkExit(v.id)} className="bg-gray-700 hover:bg-gray-600 text-white px-3 py-1 rounded-lg text-sm transition-colors">
                                  Record Exit
                                </button>
                              ) : (
                                <span className="text-gray-500 text-sm">-</span>
                              )}
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              )}

              {/* MY COMPLAINTS TAB */}
              {activeTab === 'my_complaints' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50 flex justify-between items-center">
                    <h3 className="font-semibold text-lg">My Complaints</h3>
                    <button onClick={() => setComplaintModalOpen(true)} className="bg-orange-600 hover:bg-orange-700 text-white px-4 py-2 rounded-xl flex items-center transition-colors">
                      <AlertCircle className="w-4 h-4 mr-2" /> Raise Complaint
                    </button>
                  </div>
                  <div className="overflow-x-auto">
                    <table className="w-full text-left">
                      <thead className="bg-gray-900/50 text-gray-400 text-sm">
                        <tr>
                          <th className="p-4 font-medium">Title</th>
                          <th className="p-4 font-medium">Status</th>
                          <th className="p-4 font-medium">Remarks</th>
                          <th className="p-4 font-medium text-right">Date</th>
                        </tr>
                      </thead>
                      <tbody className="divide-y divide-gray-700/50">
                        {complaints.map((c, idx) => (
                          <tr key={idx} className="hover:bg-gray-700/20 transition-colors">
                            <td className="p-4 font-medium">{c.title}</td>
                            <td className="p-4">
                              <span className={`px-2 py-1 text-xs rounded-lg border ${
                                c.status === 'PENDING' ? 'bg-yellow-500/20 text-yellow-400 border-yellow-500/50' : 
                                c.status === 'RESOLVED' ? 'bg-emerald-500/20 text-emerald-400 border-emerald-500/50' : 
                                'bg-blue-500/20 text-blue-400 border-blue-500/50'}`}>
                                {c.status}
                              </span>
                            </td>
                            <td className="p-4 text-sm text-gray-400">{c.remarks || '-'}</td>
                            <td className="p-4 text-right text-sm text-gray-400">{new Date(c.createdAt).toLocaleDateString()}</td>
                          </tr>
                        ))}
                        {complaints.length === 0 && (
                          <tr><td colSpan="4" className="p-8 text-center text-gray-500">No complaints found.</td></tr>
                        )}
                      </tbody>
                    </table>
                  </div>
                </div>
              )}

            </div>
          )}
        </div>
      </main>

      {/* Complaint Modal */}
      {isComplaintModalOpen && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-gray-900 border border-gray-700 rounded-2xl w-full max-w-md overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-200">
            <div className="p-6 border-b border-gray-800 flex justify-between items-center">
              <h3 className="text-xl font-bold">Raise Complaint</h3>
              <button onClick={() => setComplaintModalOpen(false)}><X className="text-gray-400 hover:text-white" /></button>
            </div>
            <form onSubmit={handleComplaintSubmit} className="p-6 space-y-4">
              <div><label className="block text-sm text-gray-400 mb-1">Title</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white focus:border-orange-500 outline-none" value={complaintForm.title} onChange={e => setComplaintForm({...complaintForm, title: e.target.value})} /></div>
              <div><label className="block text-sm text-gray-400 mb-1">Description</label><textarea required rows={4} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white focus:border-orange-500 outline-none" value={complaintForm.description} onChange={e => setComplaintForm({...complaintForm, description: e.target.value})} /></div>
              <div>
                <label className="block text-sm text-gray-400 mb-1">Category</label>
                <select className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white focus:border-orange-500 outline-none" value={complaintForm.category} onChange={e => setComplaintForm({...complaintForm, category: e.target.value})}>
                  <option>SECURITY</option><option>MAINTENANCE</option><option>OTHER</option>
                </select>
              </div>
              <button type="submit" className="w-full mt-4 bg-orange-600 hover:bg-orange-700 text-white py-3 rounded-xl font-medium transition-all shadow-lg shadow-orange-900/20">Submit Complaint</button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

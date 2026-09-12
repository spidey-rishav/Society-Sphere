import React, { useState, useEffect } from 'react';
import {
  Home, FileText, Users, CreditCard,
  Bell, AlertCircle, Clock, CheckCircle,
  Plus, X, LogOut, Loader2, QrCode
} from 'lucide-react';
import { useAuth } from '../../context/AuthContext';
import useToast from '../../hooks/useToast';
import {
  getMyComplaints, createComplaint,
  getMyNotices, getMyVisitors,
  getMyGuestPreApprovals, createGuestPreApproval,
  getMyPayments, getMyProfile
} from '../../services/residentService';
import { makePayment } from '../../services/paymentService';

export default function ResidentDashboard() {
  const { user, logout } = useAuth();
  const { showToast, ToastComponent } = useToast();
  const [activeTab, setActiveTab] = useState('dashboard');
  const [isLoading, setIsLoading] = useState(false);
  const [profile, setProfile] = useState(null);

  // Data states
  const [complaints, setComplaints] = useState([]);
  const [notices, setNotices] = useState([]);
  const [visitors, setVisitors] = useState([]);
  const [preApprovals, setPreApprovals] = useState([]);
  const [payments, setPayments] = useState([]);

  // Modals state
  const [isComplaintModalOpen, setComplaintModalOpen] = useState(false);
  const [isGuestModalOpen, setGuestModalOpen] = useState(false);
  const [paymentModalData, setPaymentModalData] = useState(null);

  // Forms state
  const [complaintForm, setComplaintForm] = useState({ title: '', description: '', category: 'MAINTENANCE' });
  const [guestForm, setGuestForm] = useState({ guestName: '', guestMobile: '', purpose: '', expectedArrival: '' });
  const [paymentMethod, setPaymentMethod] = useState('ONLINE');

  useEffect(() => {
    fetchProfile();
    fetchInitialData();
  }, []);

  useEffect(() => {
    if (activeTab === 'complaints') fetchComplaints();
    if (activeTab === 'visitors') fetchVisitors();
    if (activeTab === 'guest_pre_approval') fetchPreApprovals();
    if (activeTab === 'notices') fetchNotices();
    if (activeTab === 'payments') fetchPayments();
  }, [activeTab]);

  const fetchProfile = async () => {
    try {
      const res = await getMyProfile();
      setProfile(res.data);
    } catch (e) {
      console.error(e);
    }
  };

  const fetchInitialData = async () => {
    setIsLoading(true);
    try {
      const [compRes, notRes, payRes] = await Promise.all([
        getMyComplaints(), getMyNotices(), getMyPayments()
      ]);
      setComplaints(compRes.data?.data || []);
      setNotices(notRes.data?.data || []);
      setPayments(payRes.data?.data || []);
    } catch (e) {
      console.error(e);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchComplaints = async () => {
    setIsLoading(true);
    try {
      const res = await getMyComplaints();
      setComplaints(res.data?.data || []);
    } catch (e) { showToast('Error fetching complaints', 'error'); } finally { setIsLoading(false); }
  };

  const fetchVisitors = async () => {
    setIsLoading(true);
    try {
      const res = await getMyVisitors();
      setVisitors(res.data?.data || []);
    } catch (e) { showToast('Error fetching visitors', 'error'); } finally { setIsLoading(false); }
  };

  const fetchPreApprovals = async () => {
    setIsLoading(true);
    try {
      const res = await getMyGuestPreApprovals();
      setPreApprovals(res.data?.data || []);
    } catch (e) { showToast('Error fetching pre-approvals', 'error'); } finally { setIsLoading(false); }
  };

  const fetchNotices = async () => {
    setIsLoading(true);
    try {
      const res = await getMyNotices();
      setNotices(res.data?.data || []);
    } catch (e) { showToast('Error fetching notices', 'error'); } finally { setIsLoading(false); }
  };

  const fetchPayments = async () => {
    setIsLoading(true);
    try {
      const res = await getMyPayments();
      setPayments(res.data?.data || []);
    } catch (e) { showToast('Error fetching payments', 'error'); } finally { setIsLoading(false); }
  };

  // Handlers
  const handleComplaintSubmit = async (e) => {
    e.preventDefault();
    try {
      await createComplaint(complaintForm);
      showToast('Complaint raised successfully', 'success');
      setComplaintModalOpen(false);
      setComplaintForm({ title: '', description: '', category: 'MAINTENANCE' });
      fetchComplaints();
    } catch (error) { showToast('Error raising complaint', 'error'); }
  };

  const handleGuestSubmit = async (e) => {
    e.preventDefault();
    try {
      await createGuestPreApproval(guestForm);
      showToast('Guest pre-approved successfully', 'success');
      setGuestModalOpen(false);
      setGuestForm({ guestName: '', guestMobile: '', purpose: '', expectedArrival: '' });
      fetchPreApprovals();
    } catch (error) { showToast('Error pre-approving guest', 'error'); }
  };

  const handlePaymentSubmit = async (e) => {
    e.preventDefault();
    try {
      await makePayment({ complaintId: paymentModalData.id, amount: paymentModalData.amount, method: paymentMethod });
      showToast('Payment successful', 'success');
      setPaymentModalData(null);
      fetchPayments();
      fetchComplaints(); // Update complaint status if needed
    } catch (error) { showToast('Error processing payment', 'error'); }
  };

  const statusColor = (status) => {
    switch (status) {
      case 'PENDING': return 'bg-yellow-500/20 text-yellow-400 border-yellow-500/50';
      case 'IN_PROGRESS': return 'bg-blue-500/20 text-blue-400 border-blue-500/50';
      case 'RESOLVED': return 'bg-emerald-500/20 text-emerald-400 border-emerald-500/50';
      case 'REJECTED': return 'bg-red-500/20 text-red-400 border-red-500/50';
      default: return 'bg-gray-500/20 text-gray-400 border-gray-500/50';
    }
  };

  return (
    <div className="flex h-screen bg-gray-950 text-white font-sans overflow-hidden">
      {ToastComponent}

      {/* Sidebar */}
      <aside className="w-64 bg-gray-900 border-r border-gray-800 flex flex-col justify-between hidden md:flex">
        <div>
          <div className="p-6 border-b border-gray-800">
            <h1 className="text-2xl font-bold bg-gradient-to-r from-emerald-400 to-teal-400 bg-clip-text text-transparent">
              Society Sphere
            </h1>
            <p className="text-xs text-gray-400 mt-1">Resident Portal</p>
          </div>
          <nav className="p-4 space-y-1">
            {[
              { id: 'dashboard', icon: Home, label: 'Dashboard' },
              { id: 'complaints', icon: AlertCircle, label: 'My Complaints' },
              { id: 'visitors', icon: Users, label: 'My Visitors' },
              { id: 'guest_pre_approval', icon: QrCode, label: 'Guest Pre-Approval' },
              { id: 'notices', icon: Bell, label: 'Notices' },
              { id: 'payments', icon: CreditCard, label: 'Payments' },
            ].map((item) => {
              const Icon = item.icon;
              return (
                <button
                  key={item.id}
                  onClick={() => setActiveTab(item.id)}
                  className={`w-full flex items-center px-4 py-3 rounded-xl transition-all duration-200 ${
                    activeTab === item.id
                      ? 'bg-emerald-600 text-white shadow-lg shadow-emerald-900/20'
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
              <p className="text-sm font-medium">{user?.name || 'Resident'}</p>
              <p className="text-xs text-emerald-400 font-bold">{profile?.flatNumber || 'Flat N/A'}</p>
            </div>
            <div className="w-10 h-10 rounded-full bg-gradient-to-tr from-emerald-500 to-teal-500 flex items-center justify-center font-bold">
              {user?.name?.charAt(0) || 'R'}
            </div>
          </div>
        </header>

        <div className="flex-1 overflow-auto p-8">
          {isLoading && activeTab === 'dashboard' ? (
            <div className="flex items-center justify-center h-64">
              <Loader2 className="w-8 h-8 animate-spin text-emerald-500" />
            </div>
          ) : (
            <div className="space-y-6">
              
              {/* DASHBOARD TAB */}
              {activeTab === 'dashboard' && (
                <>
                  <div className="bg-gradient-to-r from-emerald-900/40 to-teal-900/40 border border-emerald-500/20 rounded-2xl p-8 mb-6 shadow-2xl">
                    <h2 className="text-2xl font-bold mb-2">Welcome back, {user?.name}!</h2>
                    <p className="text-emerald-100/80">Flat {profile?.flatNumber || 'N/A'} • {profile?.residentType || 'OWNER'}</p>
                  </div>
                  
                  <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">Open Complaints</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{complaints.filter(c => c.status !== 'RESOLVED').length}</h3>
                        <AlertCircle className="w-8 h-8 text-orange-400 opacity-80" />
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">Pending Payments</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{complaints.filter(c => c.paymentRequired && c.paymentStatus === 'PENDING').length}</h3>
                        <CreditCard className="w-8 h-8 text-red-400 opacity-80" />
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">Recent Notices</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{notices.length}</h3>
                        <Bell className="w-8 h-8 text-blue-400 opacity-80" />
                      </div>
                    </div>
                  </div>

                  <div className="mt-8">
                    <h3 className="text-lg font-semibold mb-4">Recent Notices</h3>
                    <div className="space-y-4">
                      {notices.slice(0, 3).map((n, idx) => (
                        <div key={idx} className="bg-gray-800 border border-gray-700/50 p-4 rounded-xl shadow">
                          <div className="flex justify-between items-start">
                            <h4 className="font-bold text-white">{n.title}</h4>
                            <span className="text-xs text-gray-500">{new Date(n.createdAt).toLocaleDateString()}</span>
                          </div>
                          <p className="text-sm text-gray-400 mt-2">{n.description}</p>
                        </div>
                      ))}
                    </div>
                  </div>
                </>
              )}

              {/* COMPLAINTS TAB */}
              {activeTab === 'complaints' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50 flex justify-between items-center">
                    <h3 className="font-semibold text-lg">My Complaints</h3>
                    <button onClick={() => setComplaintModalOpen(true)} className="bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-xl flex items-center transition-colors">
                      <Plus className="w-4 h-4 mr-2" /> Raise Complaint
                    </button>
                  </div>
                  <div className="overflow-x-auto">
                    <table className="w-full text-left">
                      <thead className="bg-gray-900/50 text-gray-400 text-sm">
                        <tr>
                          <th className="p-4 font-medium">Title</th>
                          <th className="p-4 font-medium">Category</th>
                          <th className="p-4 font-medium">Status</th>
                          <th className="p-4 font-medium text-right">Date</th>
                        </tr>
                      </thead>
                      <tbody className="divide-y divide-gray-700/50">
                        {complaints.map((c, idx) => (
                          <tr key={idx} className="hover:bg-gray-700/20 transition-colors">
                            <td className="p-4 font-medium">{c.title}</td>
                            <td className="p-4 text-gray-400">{c.category}</td>
                            <td className="p-4">
                              <span className={`px-2 py-1 text-xs rounded-lg border ${statusColor(c.status)}`}>
                                {c.status}
                              </span>
                            </td>
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

              {/* GUEST PRE-APPROVAL TAB */}
              {activeTab === 'guest_pre_approval' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50 flex justify-between items-center">
                    <h3 className="font-semibold text-lg">Pre-Approved Guests</h3>
                    <button onClick={() => setGuestModalOpen(true)} className="bg-emerald-600 hover:bg-emerald-700 text-white px-4 py-2 rounded-xl flex items-center transition-colors">
                      <Plus className="w-4 h-4 mr-2" /> Pre-Approve Guest
                    </button>
                  </div>
                  <div className="p-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {preApprovals.map((g, idx) => (
                      <div key={idx} className="bg-gray-900 border border-gray-700 rounded-xl p-5 relative overflow-hidden group">
                        <div className="absolute top-0 right-0 w-16 h-16 bg-emerald-500/10 rounded-bl-full -z-10 transition-transform group-hover:scale-110"></div>
                        <h4 className="font-bold text-lg">{g.guestName}</h4>
                        <p className="text-sm text-gray-400 mb-4">{g.purpose}</p>
                        <div className="bg-white p-3 rounded-lg flex items-center justify-center mb-3">
                          <span className="font-mono text-black font-bold tracking-widest text-xl">{g.barcode}</span>
                        </div>
                        <div className="flex justify-between items-center text-xs">
                          <span className="text-gray-400">Arrival: {new Date(g.expectedArrival).toLocaleString()}</span>
                          <span className={`px-2 py-1 rounded border ${g.status === 'PENDING' ? 'bg-yellow-500/20 text-yellow-400 border-yellow-500/50' : 'bg-emerald-500/20 text-emerald-400 border-emerald-500/50'}`}>{g.status}</span>
                        </div>
                      </div>
                    ))}
                    {preApprovals.length === 0 && (
                      <div className="col-span-full py-12 text-center text-gray-500">
                        <QrCode className="w-12 h-12 mx-auto mb-3 opacity-20" />
                        <p>No pre-approved guests yet.</p>
                      </div>
                    )}
                  </div>
                </div>
              )}

              {/* VISITORS TAB */}
              {activeTab === 'visitors' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50">
                    <h3 className="font-semibold text-lg">My Visitor Log</h3>
                  </div>
                  <div className="overflow-x-auto">
                    <table className="w-full text-left">
                      <thead className="bg-gray-900/50 text-gray-400 text-sm">
                        <tr>
                          <th className="p-4 font-medium">Name</th>
                          <th className="p-4 font-medium">Type</th>
                          <th className="p-4 font-medium">Entry</th>
                          <th className="p-4 font-medium">Exit</th>
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
                            <td className="p-4 text-sm">{new Date(v.entryTime).toLocaleString()}</td>
                            <td className="p-4 text-sm">{v.exitTime ? new Date(v.exitTime).toLocaleString() : '-'}</td>
                          </tr>
                        ))}
                        {visitors.length === 0 && (
                          <tr><td colSpan="4" className="p-8 text-center text-gray-500">No visitors logged yet.</td></tr>
                        )}
                      </tbody>
                    </table>
                  </div>
                </div>
              )}

              {/* NOTICES TAB */}
              {activeTab === 'notices' && (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                  {notices.map((n, idx) => (
                    <div key={idx} className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <div className="flex justify-between items-start mb-4">
                        <h4 className="font-bold text-lg">{n.title}</h4>
                        <span className={`px-2 py-1 text-xs rounded-lg border ${n.priority === 'HIGH' || n.priority === 'URGENT' ? 'border-red-500/50 text-red-400' : 'border-blue-500/50 text-blue-400'}`}>{n.priority}</span>
                      </div>
                      <p className="text-gray-400 text-sm mb-4">{n.description}</p>
                      <p className="text-xs text-gray-500 text-right">{new Date(n.createdAt).toLocaleDateString()}</p>
                    </div>
                  ))}
                  {notices.length === 0 && (
                    <div className="col-span-full py-12 text-center text-gray-500">No notices found.</div>
                  )}
                </div>
              )}

              {/* PAYMENTS TAB */}
              {activeTab === 'payments' && (
                <div className="space-y-6">
                  <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                    <div className="p-6 border-b border-gray-700/50">
                      <h3 className="font-semibold text-lg">Pending Payments</h3>
                    </div>
                    <div className="p-6 space-y-4">
                      {complaints.filter(c => c.paymentRequired && c.paymentStatus === 'PENDING').map((c, idx) => (
                        <div key={idx} className="flex items-center justify-between p-4 bg-gray-900 border border-gray-700 rounded-xl">
                          <div>
                            <h4 className="font-bold">{c.title}</h4>
                            <p className="text-sm text-gray-400 mt-1">Amount Due: ₹{c.amount || 'N/A'}</p>
                          </div>
                          <button onClick={() => setPaymentModalData(c)} className="bg-emerald-600 hover:bg-emerald-700 text-white px-6 py-2 rounded-xl transition-all shadow-lg shadow-emerald-900/20">
                            Pay Now
                          </button>
                        </div>
                      ))}
                      {complaints.filter(c => c.paymentRequired && c.paymentStatus === 'PENDING').length === 0 && (
                        <p className="text-gray-500 text-center py-4">No pending payments.</p>
                      )}
                    </div>
                  </div>

                  <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                    <div className="p-6 border-b border-gray-700/50">
                      <h3 className="font-semibold text-lg">Payment History</h3>
                    </div>
                    <div className="overflow-x-auto">
                      <table className="w-full text-left">
                        <thead className="bg-gray-900/50 text-gray-400 text-sm">
                          <tr>
                            <th className="p-4 font-medium">Transaction ID</th>
                            <th className="p-4 font-medium">Amount</th>
                            <th className="p-4 font-medium">Method</th>
                            <th className="p-4 font-medium">Date</th>
                            <th className="p-4 font-medium text-right">Status</th>
                          </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-700/50">
                          {payments.map((p, idx) => (
                            <tr key={idx} className="hover:bg-gray-700/20 transition-colors">
                              <td className="p-4 text-sm font-mono">{p.id}</td>
                              <td className="p-4 font-medium">₹{p.amount}</td>
                              <td className="p-4">{p.method}</td>
                              <td className="p-4 text-sm text-gray-400">{new Date(p.date).toLocaleString()}</td>
                              <td className="p-4 text-right">
                                <span className="px-2 py-1 text-xs rounded-lg border bg-emerald-500/20 text-emerald-400 border-emerald-500/50">SUCCESS</span>
                              </td>
                            </tr>
                          ))}
                          {payments.length === 0 && (
                            <tr><td colSpan="5" className="p-8 text-center text-gray-500">No past payments.</td></tr>
                          )}
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              )}

            </div>
          )}
        </div>
      </main>

      {/* MODALS */}
      {/* Complaint Modal */}
      {isComplaintModalOpen && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-gray-900 border border-gray-700 rounded-2xl w-full max-w-md overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-200">
            <div className="p-6 border-b border-gray-800 flex justify-between items-center">
              <h3 className="text-xl font-bold">Raise Complaint</h3>
              <button onClick={() => setComplaintModalOpen(false)}><X className="text-gray-400 hover:text-white" /></button>
            </div>
            <form onSubmit={handleComplaintSubmit} className="p-6 space-y-4">
              <div><label className="block text-sm text-gray-400 mb-1">Title</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={complaintForm.title} onChange={e => setComplaintForm({...complaintForm, title: e.target.value})} /></div>
              <div><label className="block text-sm text-gray-400 mb-1">Description</label><textarea required rows={4} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={complaintForm.description} onChange={e => setComplaintForm({...complaintForm, description: e.target.value})} /></div>
              <div>
                <label className="block text-sm text-gray-400 mb-1">Category</label>
                <select className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={complaintForm.category} onChange={e => setComplaintForm({...complaintForm, category: e.target.value})}>
                  <option>PLUMBING</option><option>ELECTRICAL</option><option>CLEANING</option><option>SECURITY</option><option>MAINTENANCE</option><option>OTHER</option>
                </select>
              </div>
              <button type="submit" className="w-full mt-4 bg-emerald-600 hover:bg-emerald-700 text-white py-3 rounded-xl font-medium transition-all shadow-lg shadow-emerald-900/20">Submit Complaint</button>
            </form>
          </div>
        </div>
      )}

      {/* Guest Pre-Approval Modal */}
      {isGuestModalOpen && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-gray-900 border border-gray-700 rounded-2xl w-full max-w-md overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-200">
            <div className="p-6 border-b border-gray-800 flex justify-between items-center">
              <h3 className="text-xl font-bold">Pre-Approve Guest</h3>
              <button onClick={() => setGuestModalOpen(false)}><X className="text-gray-400 hover:text-white" /></button>
            </div>
            <form onSubmit={handleGuestSubmit} className="p-6 space-y-4">
              <div><label className="block text-sm text-gray-400 mb-1">Guest Name</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guestForm.guestName} onChange={e => setGuestForm({...guestForm, guestName: e.target.value})} /></div>
              <div><label className="block text-sm text-gray-400 mb-1">Guest Mobile</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guestForm.guestMobile} onChange={e => setGuestForm({...guestForm, guestMobile: e.target.value})} /></div>
              <div><label className="block text-sm text-gray-400 mb-1">Purpose</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guestForm.purpose} onChange={e => setGuestForm({...guestForm, purpose: e.target.value})} /></div>
              <div><label className="block text-sm text-gray-400 mb-1">Expected Arrival</label><input required type="datetime-local" className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guestForm.expectedArrival} onChange={e => setGuestForm({...guestForm, expectedArrival: e.target.value})} /></div>
              <button type="submit" className="w-full mt-4 bg-emerald-600 hover:bg-emerald-700 text-white py-3 rounded-xl font-medium transition-all shadow-lg shadow-emerald-900/20">Generate Pass</button>
            </form>
          </div>
        </div>
      )}

      {/* Payment Modal */}
      {paymentModalData && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-gray-900 border border-gray-700 rounded-2xl w-full max-w-md overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-200">
            <div className="p-6 border-b border-gray-800 flex justify-between items-center">
              <h3 className="text-xl font-bold">Make Payment</h3>
              <button onClick={() => setPaymentModalData(null)}><X className="text-gray-400 hover:text-white" /></button>
            </div>
            <form onSubmit={handlePaymentSubmit} className="p-6 space-y-6">
              <div className="text-center mb-6">
                <p className="text-gray-400 text-sm mb-1">Amount to Pay</p>
                <h2 className="text-4xl font-bold text-emerald-400">₹{paymentModalData.amount || '0'}</h2>
                <p className="text-sm mt-2 font-medium">{paymentModalData.title}</p>
              </div>
              <div>
                <label className="block text-sm text-gray-400 mb-2">Select Payment Method</label>
                <div className="grid grid-cols-3 gap-3">
                  {['ONLINE', 'UPI', 'CASH'].map(method => (
                    <button
                      type="button"
                      key={method}
                      onClick={() => setPaymentMethod(method)}
                      className={`py-3 rounded-xl border text-sm font-medium transition-all ${paymentMethod === method ? 'bg-emerald-500/20 border-emerald-500 text-emerald-400' : 'bg-gray-800 border-gray-700 text-gray-400 hover:border-gray-500'}`}
                    >
                      {method}
                    </button>
                  ))}
                </div>
              </div>
              <button type="submit" className="w-full bg-emerald-600 hover:bg-emerald-700 text-white py-4 rounded-xl font-bold text-lg transition-all shadow-lg shadow-emerald-900/20">
                Confirm Payment
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

import React, { useState, useEffect } from 'react';
import {
  Users, UserPlus, Shield, ShieldAlert,
  ClipboardList, CheckCircle, Clock, XCircle,
  Bell, FileText, Search, Plus, Filter,
  MoreVertical, X, Calendar, Activity,
  ChevronRight, CheckSquare, LogOut, Loader2, AlertCircle
} from 'lucide-react';
import { useAuth } from '../../context/AuthContext';
import useToast from '../../hooks/useToast';
import {
  getResidents, createResident, deleteResident,
  getGuards, createGuard, deleteGuard,
  getNotices, createNotice, deleteNotice,
  getResidentComplaints, getGuardComplaints, updateComplaint,
  getAllVisitors
} from '../../services/adminService';

export default function AdminDashboard() {
  const { user, logout } = useAuth();
  const { showToast, ToastComponent } = useToast();
  const [activeTab, setActiveTab] = useState('dashboard');
  const [isLoading, setIsLoading] = useState(false);
  const [stats, setStats] = useState({ residents: 0, guards: 0, complaints: 0, visitors: 0 });

  // Data states
  const [residents, setResidents] = useState([]);
  const [guards, setGuards] = useState([]);
  const [complaints, setComplaints] = useState([]);
  const [visitors, setVisitors] = useState([]);
  const [notices, setNotices] = useState([]);

  // Modals state
  const [isResidentModalOpen, setResidentModalOpen] = useState(false);
  const [isGuardModalOpen, setGuardModalOpen] = useState(false);
  const [isNoticeModalOpen, setNoticeModalOpen] = useState(false);
  const [complaintModalData, setComplaintModalData] = useState(null);

  // Forms state
  const [residentForm, setResidentForm] = useState({
    name: '', email: '', flatNumber: '', mobileNumber: '', gender: 'MALE', residentType: 'OWNER'
  });
  const [guardForm, setGuardForm] = useState({
    name: '', email: '', mobileNumber: '', gender: 'MALE', shiftType: 'MORNING', employeeId: '', joiningDate: ''
  });
  const [noticeForm, setNoticeForm] = useState({
    title: '', description: '', audience: 'ALL', priority: 'LOW'
  });
  const [complaintUpdateForm, setComplaintUpdateForm] = useState({ status: '', remarks: '' });

  // Fetch initial dashboard stats
  useEffect(() => {
    fetchStats();
  }, []);

  // Fetch data based on active tab
  useEffect(() => {
    if (activeTab === 'dashboard') fetchStats();
    if (activeTab === 'residents') fetchResidents();
    if (activeTab === 'guards') fetchGuards();
    if (activeTab === 'complaints') fetchComplaints();
    if (activeTab === 'visitors') fetchVisitors();
    if (activeTab === 'notices') fetchNotices();
  }, [activeTab]);

  const fetchStats = async () => {
    setIsLoading(true);
    try {
      const [resData, guardData, compData, visData] = await Promise.all([
        getResidents(), getGuards(), getResidentComplaints(), getAllVisitors()
      ]);
      setStats({
        residents: resData.data?.data?.length || 0,
        guards: guardData.data?.data?.length || 0,
        complaints: compData.data?.data?.filter(c => c.status === 'PENDING').length || 0,
        visitors: visData.data?.data?.filter(v => new Date(v.entryTime).toDateString() === new Date().toDateString()).length || 0
      });
    } catch (error) {
      console.error(error);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchResidents = async () => {
    setIsLoading(true);
    try {
      const res = await getResidents();
      setResidents(res.data?.data || []);
    } catch (error) {
      showToast('Failed to fetch residents', error);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchGuards = async () => {
    setIsLoading(true);
    try {
      const res = await getGuards();
      setGuards(res.data?.data || []);
    } catch (error) {
      showToast('Failed to fetch guards', error);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchComplaints = async () => {
    setIsLoading(true);
    try {
      // Merging resident and guard complaints for admin
      const [rComp, gComp] = await Promise.all([getResidentComplaints(), getGuardComplaints()]);
      setComplaints([...(rComp.data?.data || []), ...(gComp.data?.data || [])]);
    } catch (error) {
      showToast('Failed to fetch complaints', error);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchVisitors = async () => {
    setIsLoading(true);
    try {
      const res = await getAllVisitors();
      setVisitors(res.data?.data || []);
    } catch (error) {
      showToast('Failed to fetch visitors', error);
    } finally {
      setIsLoading(false);
    }
  };

  const fetchNotices = async () => {
    setIsLoading(true);
    try {
      const res = await getNotices();
      setNotices(res.data?.data || []);
    } catch (error) {
      showToast('Failed to fetch notices', error);
    } finally {
      setIsLoading(false);
    }
  };

  // Handlers
  const handleResidentSubmit = async (e) => {
    e.preventDefault();
    try {
      await createResident(residentForm);
      showToast('Resident added successfully', 'success');
      setResidentModalOpen(false);
      fetchResidents();
    } catch (error) {
      showToast('Error adding resident', error);
    }
  };

  const handleGuardSubmit = async (e) => {
    e.preventDefault();
    try {
      await createGuard(guardForm);
      showToast('Guard added successfully', 'success');
      setGuardModalOpen(false);
      fetchGuards();
    } catch (error) {
      showToast('Error adding guard', error);
    }
  };

  const handleNoticeSubmit = async (e) => {
    e.preventDefault();
    try {
      await createNotice(noticeForm);
      showToast('Notice created successfully', 'success');
      setNoticeModalOpen(false);
      fetchNotices();
    } catch (error) {
      showToast('Error creating notice', error);
    }
  };

  const handleComplaintUpdate = async (e) => {
    e.preventDefault();
    try {
      await updateComplaint(complaintModalData.id, complaintUpdateForm);
      showToast('Complaint updated successfully', 'success');
      setComplaintModalData(null);
      fetchComplaints();
    } catch (error) {
      showToast('Error updating complaint', error);
    }
  };

  // Helpers
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
            <h1 className="text-2xl font-bold bg-gradient-to-r from-indigo-400 to-purple-400 bg-clip-text text-transparent">
              Society Sphere
            </h1>
            <p className="text-xs text-gray-400 mt-1">Admin Portal</p>
          </div>
          <nav className="p-4 space-y-1">
            {[
              { id: 'dashboard', icon: Activity, label: 'Dashboard' },
              { id: 'residents', icon: Users, label: 'Residents' },
              { id: 'guards', icon: Shield, label: 'Guards' },
              { id: 'complaints', icon: AlertCircle, label: 'Complaints' },
              { id: 'visitors', icon: ClipboardList, label: 'Visitors' },
              { id: 'notices', icon: Bell, label: 'Notices' },
            ].map((item) => {
              const Icon = item.icon;
              return (
                <button
                  key={item.id}
                  onClick={() => setActiveTab(item.id)}
                  className={`w-full flex items-center px-4 py-3 rounded-xl transition-all duration-200 ${
                    activeTab === item.id
                      ? 'bg-indigo-600 text-white shadow-lg shadow-indigo-900/20'
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
        {/* Header */}
        <header className="h-16 flex items-center justify-between px-8 bg-gray-900/50 backdrop-blur-md border-b border-gray-800 sticky top-0 z-10">
          <h2 className="text-xl font-semibold capitalize">{activeTab}</h2>
          <div className="flex items-center space-x-4">
            <div className="text-right">
              <p className="text-sm font-medium">{user?.name || 'Admin User'}</p>
              <p className="text-xs text-gray-400">{user?.email || 'admin@society.com'}</p>
            </div>
            <div className="w-10 h-10 rounded-full bg-gradient-to-tr from-indigo-500 to-purple-500 flex items-center justify-center font-bold">
              {user?.name?.charAt(0) || 'A'}
            </div>
          </div>
        </header>

        {/* Scrollable Content */}
        <div className="flex-1 overflow-auto p-8">
          {isLoading && activeTab === 'dashboard' ? (
            <div className="flex items-center justify-center h-64">
              <Loader2 className="w-8 h-8 animate-spin text-indigo-500" />
            </div>
          ) : (
            <div className="space-y-6">
              
              {/* DASHBOARD TAB */}
              {activeTab === 'dashboard' && (
                <>
                  <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <div className="flex justify-between items-start">
                        <div>
                          <p className="text-gray-400 text-sm font-medium">Total Residents</p>
                          <h3 className="text-3xl font-bold mt-2">{stats.residents}</h3>
                        </div>
                        <div className="p-3 bg-indigo-500/20 rounded-xl text-indigo-400">
                          <Users className="w-6 h-6" />
                        </div>
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <div className="flex justify-between items-start">
                        <div>
                          <p className="text-gray-400 text-sm font-medium">Total Guards</p>
                          <h3 className="text-3xl font-bold mt-2">{stats.guards}</h3>
                        </div>
                        <div className="p-3 bg-purple-500/20 rounded-xl text-purple-400">
                          <Shield className="w-6 h-6" />
                        </div>
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <div className="flex justify-between items-start">
                        <div>
                          <p className="text-gray-400 text-sm font-medium">Active Complaints</p>
                          <h3 className="text-3xl font-bold mt-2">{stats.complaints}</h3>
                        </div>
                        <div className="p-3 bg-orange-500/20 rounded-xl text-orange-400">
                          <ShieldAlert className="w-6 h-6" />
                        </div>
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <div className="flex justify-between items-start">
                        <div>
                          <p className="text-gray-400 text-sm font-medium">Today's Visitors</p>
                          <h3 className="text-3xl font-bold mt-2">{stats.visitors}</h3>
                        </div>
                        <div className="p-3 bg-emerald-500/20 rounded-xl text-emerald-400">
                          <ClipboardList className="w-6 h-6" />
                        </div>
                      </div>
                    </div>
                  </div>
                </>
              )}

              {/* RESIDENTS TAB */}
              {activeTab === 'residents' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50 flex justify-between items-center">
                    <h3 className="font-semibold text-lg">Manage Residents</h3>
                    <button onClick={() => setResidentModalOpen(true)} className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-xl flex items-center transition-colors">
                      <UserPlus className="w-4 h-4 mr-2" /> Add Resident
                    </button>
                  </div>
                  <div className="overflow-x-auto">
                    <table className="w-full text-left">
                      <thead className="bg-gray-900/50 text-gray-400 text-sm">
                        <tr>
                          <th className="p-4 font-medium">Name</th>
                          <th className="p-4 font-medium">Email</th>
                          <th className="p-4 font-medium">Flat</th>
                          <th className="p-4 font-medium">Status</th>
                          <th className="p-4 font-medium text-right">Actions</th>
                        </tr>
                      </thead>
                      <tbody className="divide-y divide-gray-700/50">
                        {residents.map((r, idx) => (
                          <tr key={idx} className="hover:bg-gray-700/20 transition-colors">
                            <td className="p-4 font-medium">{r.name}</td>
                            <td className="p-4 text-gray-400">{r.email}</td>
                            <td className="p-4">{r.flatNumber}</td>
                            <td className="p-4">
                              <span className={`px-2 py-1 text-xs rounded-lg border ${r.active ? 'bg-emerald-500/20 text-emerald-400 border-emerald-500/50' : 'bg-red-500/20 text-red-400 border-red-500/50'}`}>
                                {r.active ? 'Active' : 'Inactive'}
                              </span>
                            </td>
                            <td className="p-4 text-right">
                              <button onClick={() => deleteResident(r.id).then(() => { fetchResidents(); showToast('Deleted', 'info'); })} className="text-red-400 hover:text-red-300">Delete</button>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              )}

              {/* GUARDS TAB */}
              {activeTab === 'guards' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50 flex justify-between items-center">
                    <h3 className="font-semibold text-lg">Manage Guards</h3>
                    <button onClick={() => setGuardModalOpen(true)} className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-xl flex items-center transition-colors">
                      <UserPlus className="w-4 h-4 mr-2" /> Add Guard
                    </button>
                  </div>
                  <div className="overflow-x-auto">
                    <table className="w-full text-left">
                      <thead className="bg-gray-900/50 text-gray-400 text-sm">
                        <tr>
                          <th className="p-4 font-medium">Name</th>
                          <th className="p-4 font-medium">Emp ID</th>
                          <th className="p-4 font-medium">Shift</th>
                          <th className="p-4 font-medium">Status</th>
                          <th className="p-4 font-medium text-right">Actions</th>
                        </tr>
                      </thead>
                      <tbody className="divide-y divide-gray-700/50">
                        {guards.map((g, idx) => (
                          <tr key={idx} className="hover:bg-gray-700/20 transition-colors">
                            <td className="p-4 font-medium">{g.name}</td>
                            <td className="p-4 text-gray-400">{g.employeeId}</td>
                            <td className="p-4">{g.shiftType}</td>
                            <td className="p-4">
                              <span className={`px-2 py-1 text-xs rounded-lg border bg-emerald-500/20 text-emerald-400 border-emerald-500/50`}>
                                Active
                              </span>
                            </td>
                            <td className="p-4 text-right">
                              <button onClick={() => deleteGuard(g.id).then(() => { fetchGuards(); showToast('Deleted', 'info'); })} className="text-red-400 hover:text-red-300">Delete</button>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              )}

              {/* COMPLAINTS TAB */}
              {activeTab === 'complaints' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50">
                    <h3 className="font-semibold text-lg">All Complaints</h3>
                  </div>
                  <div className="overflow-x-auto">
                    <table className="w-full text-left">
                      <thead className="bg-gray-900/50 text-gray-400 text-sm">
                        <tr>
                          <th className="p-4 font-medium">Title</th>
                          <th className="p-4 font-medium">Category</th>
                          <th className="p-4 font-medium">Status</th>
                          <th className="p-4 font-medium text-right">Actions</th>
                        </tr>
                      </thead>
                      <tbody className="divide-y divide-gray-700/50">
                        {complaints.map((c, idx) => (
                          <tr key={idx} className="hover:bg-gray-700/20 transition-colors cursor-pointer" onClick={() => { setComplaintModalData(c); setComplaintUpdateForm({ status: c.status, remarks: c.remarks || '' }); }}>
                            <td className="p-4 font-medium">{c.title}</td>
                            <td className="p-4 text-gray-400">{c.category}</td>
                            <td className="p-4">
                              <span className={`px-2 py-1 text-xs rounded-lg border ${statusColor(c.status)}`}>
                                {c.status}
                              </span>
                            </td>
                            <td className="p-4 text-right text-indigo-400">View</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              )}

              {/* NOTICES TAB */}
              {activeTab === 'notices' && (
                <div>
                  <div className="flex justify-between items-center mb-6">
                    <h3 className="font-semibold text-lg">Society Notices</h3>
                    <button onClick={() => setNoticeModalOpen(true)} className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-xl flex items-center transition-colors">
                      <Plus className="w-4 h-4 mr-2" /> Create Notice
                    </button>
                  </div>
                  <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {notices.map((n, idx) => (
                      <div key={idx} className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl relative">
                        <button onClick={() => deleteNotice(n.id).then(() => { fetchNotices(); showToast('Deleted', 'info'); })} className="absolute top-4 right-4 text-gray-500 hover:text-red-400">
                          <X className="w-5 h-5" />
                        </button>
                        <h4 className="font-bold text-lg mb-2 pr-6">{n.title}</h4>
                        <p className="text-gray-400 text-sm mb-4">{n.description}</p>
                        <div className="flex space-x-2">
                          <span className="px-2 py-1 bg-gray-700 text-xs rounded-lg">{n.audience}</span>
                          <span className={`px-2 py-1 text-xs rounded-lg border ${n.priority === 'HIGH' || n.priority === 'URGENT' ? 'border-red-500/50 text-red-400' : 'border-blue-500/50 text-blue-400'}`}>{n.priority}</span>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              )}

              {/* VISITORS TAB */}
              {activeTab === 'visitors' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50">
                    <h3 className="font-semibold text-lg">Visitor Log</h3>
                  </div>
                  <div className="overflow-x-auto">
                    <table className="w-full text-left">
                      <thead className="bg-gray-900/50 text-gray-400 text-sm">
                        <tr>
                          <th className="p-4 font-medium">Name</th>
                          <th className="p-4 font-medium">Flat</th>
                          <th className="p-4 font-medium">Type</th>
                          <th className="p-4 font-medium">Entry</th>
                          <th className="p-4 font-medium">Exit</th>
                        </tr>
                      </thead>
                      <tbody className="divide-y divide-gray-700/50">
                        {visitors.map((v, idx) => (
                          <tr key={idx} className="hover:bg-gray-700/20 transition-colors">
                            <td className="p-4 font-medium">{v.visitorName}</td>
                            <td className="p-4 text-gray-400">{v.flatNumber}</td>
                            <td className="p-4">
                              <span className={`px-2 py-1 text-xs rounded-lg border ${v.visitorType === 'GUEST' ? 'bg-blue-500/20 text-blue-400 border-blue-500/50' : 'bg-orange-500/20 text-orange-400 border-orange-500/50'}`}>
                                {v.visitorType}
                              </span>
                            </td>
                            <td className="p-4 text-sm">{new Date(v.entryTime).toLocaleTimeString()}</td>
                            <td className="p-4 text-sm">{v.exitTime ? new Date(v.exitTime).toLocaleTimeString() : '-'}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              )}

            </div>
          )}
        </div>
      </main>

      {/* MODALS */}
      {/* Resident Modal */}
      {isResidentModalOpen && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-gray-900 border border-gray-700 rounded-2xl w-full max-w-md overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-200">
            <div className="p-6 border-b border-gray-800 flex justify-between items-center">
              <h3 className="text-xl font-bold">Add Resident</h3>
              <button onClick={() => setResidentModalOpen(false)}><X className="text-gray-400 hover:text-white" /></button>
            </div>
            <form onSubmit={handleResidentSubmit} className="p-6 space-y-4">
              <div><label className="block text-sm text-gray-400 mb-1">Full Name</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={residentForm.name} onChange={e => setResidentForm({...residentForm, name: e.target.value})} /></div>
              <div><label className="block text-sm text-gray-400 mb-1">Email</label><input required type="email" className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={residentForm.email} onChange={e => setResidentForm({...residentForm, email: e.target.value})} /></div>
              <div className="grid grid-cols-2 gap-4">
                <div><label className="block text-sm text-gray-400 mb-1">Flat Number</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={residentForm.flatNumber} onChange={e => setResidentForm({...residentForm, flatNumber: e.target.value})} /></div>
                <div><label className="block text-sm text-gray-400 mb-1">Mobile</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={residentForm.mobileNumber} onChange={e => setResidentForm({...residentForm, mobileNumber: e.target.value})} /></div>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div><label className="block text-sm text-gray-400 mb-1">Gender</label><select className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={residentForm.gender} onChange={e => setResidentForm({...residentForm, gender: e.target.value})}><option>MALE</option><option>FEMALE</option><option>OTHER</option></select></div>
                <div><label className="block text-sm text-gray-400 mb-1">Type</label><select className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={residentForm.residentType} onChange={e => setResidentForm({...residentForm, residentType: e.target.value})}><option>OWNER</option><option>TENANT</option></select></div>
              </div>
              <button type="submit" className="w-full mt-4 bg-indigo-600 hover:bg-indigo-700 text-white py-3 rounded-xl font-medium transition-all shadow-lg shadow-indigo-900/20">Create Resident</button>
            </form>
          </div>
        </div>
      )}

      {/* Guard Modal */}
      {isGuardModalOpen && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-gray-900 border border-gray-700 rounded-2xl w-full max-w-md overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-200">
            <div className="p-6 border-b border-gray-800 flex justify-between items-center">
              <h3 className="text-xl font-bold">Add Guard</h3>
              <button onClick={() => setGuardModalOpen(false)}><X className="text-gray-400 hover:text-white" /></button>
            </div>
            <form onSubmit={handleGuardSubmit} className="p-6 space-y-4">
              <div><label className="block text-sm text-gray-400 mb-1">Full Name</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guardForm.name} onChange={e => setGuardForm({...guardForm, name: e.target.value})} /></div>
              <div><label className="block text-sm text-gray-400 mb-1">Email</label><input required type="email" className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guardForm.email} onChange={e => setGuardForm({...guardForm, email: e.target.value})} /></div>
              <div className="grid grid-cols-2 gap-4">
                <div><label className="block text-sm text-gray-400 mb-1">Employee ID</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guardForm.employeeId} onChange={e => setGuardForm({...guardForm, employeeId: e.target.value})} /></div>
                <div><label className="block text-sm text-gray-400 mb-1">Mobile</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guardForm.mobileNumber} onChange={e => setGuardForm({...guardForm, mobileNumber: e.target.value})} /></div>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div><label className="block text-sm text-gray-400 mb-1">Gender</label><select className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guardForm.gender} onChange={e => setGuardForm({...guardForm, gender: e.target.value})}><option>MALE</option><option>FEMALE</option><option>OTHER</option></select></div>
                <div><label className="block text-sm text-gray-400 mb-1">Shift</label><select className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={guardForm.shiftType} onChange={e => setGuardForm({...guardForm, shiftType: e.target.value})}><option>MORNING</option><option>EVENING</option><option>NIGHT</option></select></div>
              </div>
              <button type="submit" className="w-full mt-4 bg-indigo-600 hover:bg-indigo-700 text-white py-3 rounded-xl font-medium transition-all shadow-lg shadow-indigo-900/20">Create Guard</button>
            </form>
          </div>
        </div>
      )}

      {/* Notice Modal */}
      {isNoticeModalOpen && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-gray-900 border border-gray-700 rounded-2xl w-full max-w-md overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-200">
            <div className="p-6 border-b border-gray-800 flex justify-between items-center">
              <h3 className="text-xl font-bold">Create Notice</h3>
              <button onClick={() => setNoticeModalOpen(false)}><X className="text-gray-400 hover:text-white" /></button>
            </div>
            <form onSubmit={handleNoticeSubmit} className="p-6 space-y-4">
              <div><label className="block text-sm text-gray-400 mb-1">Title</label><input required className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={noticeForm.title} onChange={e => setNoticeForm({...noticeForm, title: e.target.value})} /></div>
              <div><label className="block text-sm text-gray-400 mb-1">Description</label><textarea required rows={4} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={noticeForm.description} onChange={e => setNoticeForm({...noticeForm, description: e.target.value})} /></div>
              <div className="grid grid-cols-2 gap-4">
                <div><label className="block text-sm text-gray-400 mb-1">Audience</label><select className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={noticeForm.audience} onChange={e => setNoticeForm({...noticeForm, audience: e.target.value})}><option>ALL</option><option>RESIDENTS</option><option>GUARDS</option></select></div>
                <div><label className="block text-sm text-gray-400 mb-1">Priority</label><select className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={noticeForm.priority} onChange={e => setNoticeForm({...noticeForm, priority: e.target.value})}><option>LOW</option><option>MEDIUM</option><option>HIGH</option><option>URGENT</option></select></div>
              </div>
              <button type="submit" className="w-full mt-4 bg-indigo-600 hover:bg-indigo-700 text-white py-3 rounded-xl font-medium transition-all shadow-lg shadow-indigo-900/20">Publish Notice</button>
            </form>
          </div>
        </div>
      )}

      {/* Complaint Update Modal */}
      {complaintModalData && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-gray-900 border border-gray-700 rounded-2xl w-full max-w-md overflow-hidden shadow-2xl animate-in fade-in zoom-in duration-200">
            <div className="p-6 border-b border-gray-800 flex justify-between items-center">
              <h3 className="text-xl font-bold">Update Complaint</h3>
              <button onClick={() => setComplaintModalData(null)}><X className="text-gray-400 hover:text-white" /></button>
            </div>
            <form onSubmit={handleComplaintUpdate} className="p-6 space-y-4">
              <div className="bg-gray-800 p-4 rounded-xl border border-gray-700">
                <h4 className="font-bold">{complaintModalData.title}</h4>
                <p className="text-sm text-gray-400 mt-1">{complaintModalData.description}</p>
              </div>
              <div>
                <label className="block text-sm text-gray-400 mb-1">Status</label>
                <select className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={complaintUpdateForm.status} onChange={e => setComplaintUpdateForm({...complaintUpdateForm, status: e.target.value})}>
                  <option value="PENDING">PENDING</option>
                  <option value="IN_PROGRESS">IN_PROGRESS</option>
                  <option value="RESOLVED">RESOLVED</option>
                  <option value="REJECTED">REJECTED</option>
                </select>
              </div>
              <div><label className="block text-sm text-gray-400 mb-1">Remarks</label><textarea rows={3} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2 text-white" value={complaintUpdateForm.remarks} onChange={e => setComplaintUpdateForm({...complaintUpdateForm, remarks: e.target.value})} /></div>
              <button type="submit" className="w-full mt-4 bg-indigo-600 hover:bg-indigo-700 text-white py-3 rounded-xl font-medium transition-all shadow-lg shadow-indigo-900/20">Update Status</button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

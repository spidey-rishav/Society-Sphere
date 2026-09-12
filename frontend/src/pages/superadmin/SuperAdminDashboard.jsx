import React, { useState, useEffect } from 'react';
import {
  Building2, Users, CheckCircle, XCircle,
  Activity, Settings, LogOut, Loader2, Database
} from 'lucide-react';
import { useAuth } from '../../context/AuthContext';
import useToast from '../../hooks/useToast';

import { getPlatformStats, getSocieties, updateSocietyStatus } from '../../services/superAdminService';

export default function SuperAdminDashboard() {
  const { user, logout } = useAuth();
  const { showToast, ToastComponent } = useToast();
  const [activeTab, setActiveTab] = useState('dashboard');
  const [isLoading, setIsLoading] = useState(false);

  // Data states
  const [stats, setStats] = useState({ totalSocieties: 0, totalUsers: 0, activeSocieties: 0, pendingRequests: 0 });
  const [societies, setSocieties] = useState([]);

  useEffect(() => {
    if (activeTab === 'dashboard') fetchStats();
    if (activeTab === 'societies') fetchSocieties();
  }, [activeTab]);

  const fetchStats = async () => {
    setIsLoading(true);
    try {
      const res = await getPlatformStats();
      setStats(res.data?.data || { totalSocieties: 0, totalUsers: 0, activeSocieties: 0, pendingRequests: 0 });
    } catch (error) {
      showToast('Error fetching stats', 'error');
    } finally {
      setIsLoading(false);
    }
  };

  const fetchSocieties = async () => {
    setIsLoading(true);
    try {
      const res = await getSocieties();
      setSocieties(res.data?.data || []);
    } catch (error) {
      showToast('Error fetching societies', 'error');
    } finally {
      setIsLoading(false);
    }
  };

  const handleApproveSociety = async (id) => {
    try {
      await updateSocietyStatus(id, true);
      showToast(`Society ${id} approved successfully`, 'success');
      setSocieties(societies.map(s => s.id === id ? { ...s, status: 'ACTIVE' } : s));
    } catch (e) { showToast('Error approving society', 'error'); }
  };

  const handleRejectSociety = async (id) => {
    try {
      await updateSocietyStatus(id, false);
      showToast(`Society ${id} rejected`, 'info');
      setSocieties(societies.map(s => s.id === id ? { ...s, status: 'REJECTED' } : s));
    } catch (e) { showToast('Error rejecting society', 'error'); }
  };

  return (
    <div className="flex h-screen bg-gray-950 text-white font-sans overflow-hidden">
      {ToastComponent}

      {/* Sidebar */}
      <aside className="w-64 bg-gray-900 border-r border-gray-800 flex flex-col justify-between hidden md:flex">
        <div>
          <div className="p-6 border-b border-gray-800">
            <h1 className="text-2xl font-bold bg-gradient-to-r from-red-500 to-rose-400 bg-clip-text text-transparent">
              Society Sphere
            </h1>
            <p className="text-xs text-gray-400 mt-1">Super Admin Portal</p>
          </div>
          <nav className="p-4 space-y-1">
            {[
              { id: 'dashboard', icon: Activity, label: 'Platform Overview' },
              { id: 'societies', icon: Building2, label: 'Manage Societies' },
              { id: 'settings', icon: Settings, label: 'Global Settings' },
            ].map((item) => {
              const Icon = item.icon;
              return (
                <button
                  key={item.id}
                  onClick={() => setActiveTab(item.id)}
                  className={`w-full flex items-center px-4 py-3 rounded-xl transition-all duration-200 ${
                    activeTab === item.id
                      ? 'bg-red-600 text-white shadow-lg shadow-red-900/20'
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
          <h2 className="text-xl font-semibold capitalize">{activeTab.replace('_', ' ')}</h2>
          <div className="flex items-center space-x-4">
            <div className="text-right">
              <p className="text-sm font-medium">{user?.name || 'Super Admin'}</p>
              <p className="text-xs text-red-400 font-bold">System Root</p>
            </div>
            <div className="w-10 h-10 rounded-full bg-gradient-to-tr from-red-500 to-rose-500 flex items-center justify-center font-bold">
              {user?.name?.charAt(0) || 'S'}
            </div>
          </div>
        </header>

        {/* Scrollable Content */}
        <div className="flex-1 overflow-auto p-8">
          {isLoading && activeTab === 'dashboard' ? (
            <div className="flex items-center justify-center h-64">
              <Loader2 className="w-8 h-8 animate-spin text-red-500" />
            </div>
          ) : (
            <div className="space-y-6">
              
              {/* DASHBOARD TAB */}
              {activeTab === 'dashboard' && (
                <>
                  <div className="bg-gradient-to-r from-red-900/40 to-rose-900/40 border border-red-500/20 rounded-2xl p-8 mb-6 shadow-2xl">
                    <h2 className="text-2xl font-bold mb-2">Platform Command Center</h2>
                    <p className="text-red-100/80">Manage all registered housing societies across the platform.</p>
                  </div>
                  
                  <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">Total Societies</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{stats.totalSocieties}</h3>
                        <Building2 className="w-8 h-8 text-indigo-400 opacity-80" />
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">Active Societies</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{stats.activeSocieties}</h3>
                        <CheckCircle className="w-8 h-8 text-emerald-400 opacity-80" />
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">Pending Requests</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{stats.pendingRequests}</h3>
                        <Activity className="w-8 h-8 text-orange-400 opacity-80" />
                      </div>
                    </div>
                    <div className="bg-gray-800 border border-gray-700/50 p-6 rounded-2xl shadow-xl">
                      <p className="text-gray-400 text-sm font-medium mb-2">Total Users</p>
                      <div className="flex items-center justify-between">
                        <h3 className="text-3xl font-bold">{stats.totalUsers}</h3>
                        <Users className="w-8 h-8 text-blue-400 opacity-80" />
                      </div>
                    </div>
                  </div>
                </>
              )}

              {/* SOCIETIES TAB */}
              {activeTab === 'societies' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl overflow-hidden">
                  <div className="p-6 border-b border-gray-700/50 flex justify-between items-center">
                    <h3 className="font-semibold text-lg">Manage Registered Societies</h3>
                  </div>
                  <div className="overflow-x-auto">
                    <table className="w-full text-left">
                      <thead className="bg-gray-900/50 text-gray-400 text-sm">
                        <tr>
                          <th className="p-4 font-medium">Society Name</th>
                          <th className="p-4 font-medium">Code</th>
                          <th className="p-4 font-medium">City</th>
                          <th className="p-4 font-medium">Users</th>
                          <th className="p-4 font-medium">Status</th>
                          <th className="p-4 font-medium text-right">Actions</th>
                        </tr>
                      </thead>
                      <tbody className="divide-y divide-gray-700/50">
                        {societies.map((s) => (
                          <tr key={s.id} className="hover:bg-gray-700/20 transition-colors">
                            <td className="p-4 font-medium">{s.name}</td>
                            <td className="p-4 text-gray-400">{s.code}</td>
                            <td className="p-4">{s.city}</td>
                            <td className="p-4">{s.users}</td>
                            <td className="p-4">
                              <span className={`px-2 py-1 text-xs rounded-lg border ${
                                s.status === 'ACTIVE' ? 'bg-emerald-500/20 text-emerald-400 border-emerald-500/50' : 
                                s.status === 'PENDING' ? 'bg-yellow-500/20 text-yellow-400 border-yellow-500/50' : 
                                'bg-red-500/20 text-red-400 border-red-500/50'}`}>
                                {s.status}
                              </span>
                            </td>
                            <td className="p-4 text-right">
                              {s.status === 'PENDING' && (
                                <div className="flex justify-end space-x-2">
                                  <button onClick={() => handleApproveSociety(s.id)} className="bg-emerald-600 hover:bg-emerald-700 text-white px-3 py-1 rounded-lg text-sm transition-colors">Approve</button>
                                  <button onClick={() => handleRejectSociety(s.id)} className="bg-gray-700 hover:bg-gray-600 text-white px-3 py-1 rounded-lg text-sm transition-colors">Reject</button>
                                </div>
                              )}
                              {s.status === 'ACTIVE' && (
                                <button className="text-gray-500 hover:text-white transition-colors text-sm">Manage</button>
                              )}
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                </div>
              )}

              {/* SETTINGS TAB */}
              {activeTab === 'settings' && (
                <div className="bg-gray-800 border border-gray-700/50 rounded-2xl shadow-xl p-8 max-w-2xl text-center">
                  <Database className="w-16 h-16 mx-auto text-gray-600 mb-4" />
                  <h3 className="text-2xl font-bold mb-2">Global Settings</h3>
                  <p className="text-gray-400 mb-6">Database maintenance and platform-wide configurations are restricted for security.</p>
                  <button disabled className="bg-gray-700 text-gray-400 px-6 py-3 rounded-xl cursor-not-allowed">
                    System Configuration Locked
                  </button>
                </div>
              )}

            </div>
          )}
        </div>
      </main>
    </div>
  );
}

import api from './api';

export const getPlatformStats = () => api.get('/api/superadmin/stats');
export const getSocieties = () => api.get('/api/superadmin/societies');
export const getSocietyDetails = (id) => api.get(`/api/superadmin/societies/${id}`);
export const updateSocietyStatus = (id, approve) => api.put(`/api/superadmin/societies/${id}/status`, null, { params: { approve } });
export const resendAdminCredentials = (adminId) => api.post(`/api/superadmin/societies/admins/${adminId}/send-credentials`);

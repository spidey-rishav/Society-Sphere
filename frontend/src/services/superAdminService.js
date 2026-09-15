import api from './api';

export const getPlatformStats = () => api.get('/api/superadmin/stats');
export const getSocieties = () => api.get('/api/superadmin/societies');
export const updateSocietyStatus = (id, approve) => api.put(`/api/superadmin/societies/${id}/status`, null, { params: { approve } });

export const resendSocietyCredentials = (id) => api.post(`/api/superadmin/societies/${id}/send-credentials`);

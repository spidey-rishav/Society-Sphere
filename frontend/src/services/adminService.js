import api from './api';

// Residents
export const getResidents = (societyId) => api.get('/api/admin/residents', { params: { societyId } });
export const createResident = (data) => api.post('/api/admin/residents', data);
export const updateResident = (id, data) => api.put(`/api/admin/residents/${id}`, data);
export const deleteResident = (id) => api.delete(`/api/admin/residents/${id}`);

// Guards
export const getGuards = (societyId) => api.get('/api/admin/guards', { params: { societyId } });
export const createGuard = (data) => api.post('/api/admin/guards', data);
export const updateGuard = (id, data) => api.put(`/api/admin/guards/${id}`, data);
export const deleteGuard = (id) => api.delete(`/api/admin/guards/${id}`);

// Notices
export const createNotice = (data) => api.post('/api/admin/notices', data);
export const getNotices = () => api.get('/api/admin/notices');
export const updateNotice = (id, data) => api.put(`/api/admin/notices/${id}`, data);
export const deleteNotice = (id) => api.delete(`/api/admin/notices/${id}`);

// Complaints (admin)
export const getResidentComplaints = () => api.get('/api/complaints/residents');
export const getGuardComplaints = () => api.get('/api/complaints/guards');
export const updateComplaint = (id, data) => api.put(`/api/complaints/${id}`, data);

// Visitors (admin)
export const getAllVisitors = () => api.get('/api/visitors/all');

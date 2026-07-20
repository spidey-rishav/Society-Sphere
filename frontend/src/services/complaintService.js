import api from './api';

export const getMyComplaints = () => api.get('/api/complaints/my');
export const createComplaint = (data) => api.post('/api/complaints', data);
export const getGuardComplaints = () => api.get('/api/complaints/guard');
export const createGuardComplaint = (data) => api.post('/api/complaints/guard', data);
export const getResidentComplaints = () => api.get('/api/complaints/residents');
export const updateComplaint = (id, data) => api.put(`/api/complaints/${id}`, data);

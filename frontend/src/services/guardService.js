import api from './api';

export const addDeliveryVisitor = (data) => api.post('/api/visitors/delivery', data);
export const getAllVisitors = () => api.get('/api/visitors/all');
export const markExit = (visitorId) => api.put(`/api/visitors/${visitorId}/exit`);
export const verifyGuestBarcode = (data) => api.post('/api/guest/verify', data);
export const getGuardComplaints = () => api.get('/api/complaints/guards');
export const createGuardComplaint = (data) => api.post('/api/complaints', data);

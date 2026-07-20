import api from './api';

export const addDeliveryVisitor = (data) => api.post('/api/visitors/delivery', data);
export const getAllVisitors = () => api.get('/api/visitors');
export const markExit = (visitorId) => api.put(`/api/visitors/${visitorId}/exit`);
export const verifyGuestBarcode = (barcode) => api.get(`/api/visitors/verify-guest/${barcode}`);
export const getGuardComplaints = () => api.get('/api/complaints/guard');
export const createGuardComplaint = (data) => api.post('/api/complaints/guard', data);

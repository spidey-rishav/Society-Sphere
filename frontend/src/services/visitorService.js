import api from './api';

export const addDeliveryVisitor = (data) => api.post('/api/visitors/delivery', data);
export const approveVisitor = (id, approved) => api.put(`/api/visitors/${id}/approve`, null, { params: { approved } });
export const recordExit = (id) => api.put(`/api/visitors/${id}/exit`);
export const getMyVisitors = (flatId) => api.get(`/api/visitors/flat/${flatId}`);
export const getPendingApprovals = () => api.get('/api/visitors/pending');

export const verifyGuestBarcode = (barcode) => api.post('/api/guest/verify', null, { params: { barcode } });
export const checkInGuest = (data) => api.post('/api/guest/checkin', data);
export const approveGuestPreBook = (id, approved) => api.put(`/api/guest/${id}/approve`, null, { params: { approved } });
export const getMyGuestPreApprovals = () => api.get('/api/guest/my');
export const preBookGuest = (data) => api.post('/api/guest/prebook', data);

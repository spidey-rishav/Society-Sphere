import api from './api';

export const getMyComplaints = () => api.get('/api/complaints/my');
export const createComplaint = (data) => api.post('/api/complaints', data);
export const getMyNotices = () => api.get('/api/notices');
export const getMyVisitors = (flatId) => api.get(`/api/visitors/flat/${flatId}`);
export const getMyGuestPreApprovals = () => api.get('/api/guest/my');
export const createGuestPreApproval = (data) => api.post('/api/guest/prebook', data);
export const getMyProfile = () => api.get('/api/residents/me');
export const getMyPayments = () => api.get('/api/payments/my');

import api from './api';

export const getMyComplaints = () => api.get('/api/complaints/my');
export const createComplaint = (data) => api.post('/api/complaints', data);
export const getMyNotices = () => api.get('/api/notices/my');
export const getMyVisitors = () => api.get('/api/visitors/my');
export const getMyGuestPreApprovals = () => api.get('/api/guest/my-approvals');
export const createGuestPreApproval = (data) => api.post('/api/guest/pre-approval', data);
export const getMyProfile = () => api.get('/api/residents/me');
export const getMyPayments = () => api.get('/api/payments/my');

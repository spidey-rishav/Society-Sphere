import api from './api';

export const makePayment = (data) => api.post('/api/payments', data);
export const getMyPayments = () => api.get('/api/payments/my');
export const getAllPayments = () => api.get('/api/payments/all');

import api from './api';

export const searchActiveSocieties = () => api.get('/api/societies/search');
export const getSocietyDetails = (code) => api.get(`/api/societies/${code}`);

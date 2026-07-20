import api from './api';

export const searchSociety = (query) => api.get('/api/societies/search', { params: { query } });
export const getSocietyDetails = (code) => api.get(`/api/societies/${code}`);

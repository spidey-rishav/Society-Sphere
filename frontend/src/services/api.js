import axios from 'axios';

const api = axios.create({
  // Vite forwards /api requests to the backend during development. Keeping
  // requests same-origin prevents browser extensions from blocking localhost:8080.
  baseURL: '',
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  // Login must not reuse a stale token. Other auth routes, such as profile
  // completion, require the token that was issued when the user signed in.
  const isPublicRequest = config.url === '/api/auth/login';
  if (token && !isPublicRequest) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;

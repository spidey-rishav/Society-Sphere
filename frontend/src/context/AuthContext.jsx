import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  const parseJwt = (token) => {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        window
          .atob(base64)
          .split('')
          .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join('')
      );
      return JSON.parse(jsonPayload);
    } catch (e) {
      return null;
    }
  };

  const login = (token, userData) => {
    localStorage.setItem('token', token);
    if (userData) {
      setUser(userData);
      localStorage.setItem('user', JSON.stringify(userData));
    } else {
      const decoded = parseJwt(token);
      if (decoded) {
        setUser(decoded);
        localStorage.setItem('user', JSON.stringify(decoded));
      }
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
  };

  useEffect(() => {
    const token = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    if (token) {
      if (storedUser) {
        try {
          setUser(JSON.parse(storedUser));
        } catch (e) {
          setUser(parseJwt(token));
        }
      } else {
        setUser(parseJwt(token));
      }
    }
    setLoading(false);
  }, []);

  if (loading) {
    return <div className="min-h-screen bg-gray-950 flex items-center justify-center text-white">Loading...</div>;
  }

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

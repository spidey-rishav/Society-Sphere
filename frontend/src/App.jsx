import './App.css';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import LandingPage from './pages/landing/Landing';
import LoginPage from './pages/auth/LoginPage';
import FirstLoginPage from './pages/auth/FirstLoginPage';
import SuperAdminLogin from './pages/auth/SuperAdminLogin';
import RegisterSociety from './pages/auth/RegisterSociety';
import SocietySelectionPage from './pages/SocietySelectionPage';
import SuperAdminDashboard from './pages/superadmin/SuperAdminDashboard';
import AdminDashboard from './pages/admin/AdminDashboard';
import ResidentDashboard from './pages/resident/ResidentDashboard';
import GuardDashboard from './pages/securityguard/GuardDashboard';
import GuestPreBookingPage from './pages/GuestPreBookingPage';
import ProtectedRoute from './components/ProtectedRoute';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<LandingPage />} />
        <Route path="/society" element={<SocietySelectionPage />} />
        <Route path="/register-society" element={<RegisterSociety />} />
        <Route path="/super-admin-login" element={<SuperAdminLogin />} />
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/first-login"
          element={
            <ProtectedRoute>
              <FirstLoginPage />
            </ProtectedRoute>
          }
        />
        
        {/* Dashboards */}
        <Route
          path="/super-admin"
          element={
            <ProtectedRoute requiredRole="SUPER_ADMIN">
              <SuperAdminDashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/admin/*"
          element={
            <ProtectedRoute roles={['ADMIN']}>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/resident/*"
          element={
            <ProtectedRoute roles={['RESIDENT']}>
              <ResidentDashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/guard/*"
          element={
            <ProtectedRoute roles={['SECURITY_GUARD']}>
              <GuardDashboard />
            </ProtectedRoute>
          }
        />
        <Route path="/guest/:societyCode" element={<GuestPreBookingPage />} />
        <Route path="/unauthorized" element={<div className="min-h-screen flex items-center justify-center bg-gray-950 text-red-500 font-bold text-2xl">Unauthorized Access</div>} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;

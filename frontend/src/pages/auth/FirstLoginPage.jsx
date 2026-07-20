import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { useToast } from '../../context/ToastContext';
import { completeProfile as completeProfileService } from '../../services/authService';

const FirstLoginPage = () => {
  const { user, login } = useAuth();
  const { addToast } = useToast();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [showOptional, setShowOptional] = useState(false);

  const [formData, setFormData] = useState({
    phoneNumber: '',
    dateOfBirth: '',
    gender: '',
    emergencyContactName: '',
    emergencyContactPhone: '',
    newPassword: '',
    confirmPassword: '',
    occupation: '',
    bloodGroup: '',
    alternatePhone: ''
  });

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.newPassword !== formData.confirmPassword) {
      addToast('Passwords do not match', 'error');
      return;
    }
    
    setLoading(true);
    try {
      const response = await completeProfileService({ ...formData, userId: user?.id });
      // Update local storage and context to set firstLogin = false
      const updatedUser = { ...user, firstLogin: false, ...response.data };
      const token = localStorage.getItem('token');
      login(token, updatedUser);
      addToast('Profile completed successfully!');
      
      const role = updatedUser.role;
      if (role === 'ADMIN') navigate('/admin');
      else if (role === 'RESIDENT') navigate('/resident');
      else if (role === 'SECURITY_GUARD') navigate('/guard');
      else navigate('/');
    } catch (err) {
      addToast(err.response?.data?.message || 'Failed to complete profile', 'error');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-950 flex items-center justify-center p-4 py-12">
      <div className="bg-gray-900 border border-gray-800 p-8 rounded-3xl shadow-2xl w-full max-w-3xl">
        <div className="mb-8 border-b border-gray-800 pb-6">
          <h1 className="text-3xl font-bold text-white mb-2">Complete Your Profile</h1>
          <p className="text-gray-400">Welcome to Society Sphere! Please provide the following details to proceed.</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Mandatory */}
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Phone Number *</label>
              <input type="tel" name="phoneNumber" required value={formData.phoneNumber} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Date of Birth *</label>
              <input type="date" name="dateOfBirth" required value={formData.dateOfBirth} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Gender *</label>
              <select name="gender" required value={formData.gender} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none">
                <option value="">Select</option>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
                <option value="OTHER">Other</option>
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">New Password *</label>
              <input type="password" name="newPassword" required value={formData.newPassword} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Confirm Password *</label>
              <input type="password" name="confirmPassword" required value={formData.confirmPassword} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Emergency Contact Name *</label>
              <input type="text" name="emergencyContactName" required value={formData.emergencyContactName} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Emergency Contact Phone *</label>
              <input type="tel" name="emergencyContactPhone" required value={formData.emergencyContactPhone} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none" />
            </div>
          </div>

          <div className="pt-4">
            <button type="button" onClick={() => setShowOptional(!showOptional)} className="text-indigo-400 text-sm font-medium flex items-center gap-2 hover:text-indigo-300">
              {showOptional ? 'Hide Optional Details' : 'Show Optional Details'}
              <svg className={`w-4 h-4 transform transition-transform ${showOptional ? 'rotate-180' : ''}`} fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
              </svg>
            </button>
          </div>

          {showOptional && (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 pt-4 border-t border-gray-800/50">
              <div>
                <label className="block text-sm font-medium text-gray-300 mb-2">Occupation</label>
                <input type="text" name="occupation" value={formData.occupation} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-300 mb-2">Blood Group</label>
                <input type="text" name="bloodGroup" value={formData.bloodGroup} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none" />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-300 mb-2">Alternate Phone</label>
                <input type="tel" name="alternatePhone" value={formData.alternatePhone} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-2.5 text-white focus:ring-2 focus:ring-indigo-500 outline-none" />
              </div>
            </div>
          )}

          <div className="pt-6">
            <button
              type="submit"
              disabled={loading}
              className="w-full bg-indigo-600 hover:bg-indigo-500 text-white font-semibold rounded-xl px-4 py-3 transition-all duration-200 disabled:opacity-70 flex justify-center items-center"
            >
              {loading ? 'Submitting...' : 'Complete Profile'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default FirstLoginPage;

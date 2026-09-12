import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

const RegisterSociety = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    societyName: '',
    registrationNumber: '',
    address: '',
    adminName: '',
    adminEmail: '',
    adminPhone: ''
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // TODO: Connect to backend for Society Registration
    console.log('Society Registration', formData);
  };

  return (
    <div className="min-h-screen bg-gray-950 flex flex-col items-center justify-center p-6 text-white relative overflow-hidden">
      {/* Background gradients */}
      <div className="absolute top-[-20%] left-[-10%] w-[500px] h-[500px] bg-emerald-600/20 rounded-full blur-[120px]" />
      <div className="absolute bottom-[-20%] right-[-10%] w-[500px] h-[500px] bg-indigo-600/20 rounded-full blur-[120px]" />

      <div className="w-full max-w-2xl relative z-10 py-12">
        <div className="text-center mb-10">
          <Link to="/society" className="inline-flex items-center text-gray-400 hover:text-white mb-6 transition-colors">
            <svg className="w-5 h-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 19l-7-7m0 0l7-7m-7 7h18" />
            </svg>
            Back to Options
          </Link>
          <div className="w-16 h-16 bg-emerald-600/20 rounded-2xl flex items-center justify-center mx-auto mb-6">
            <svg className="w-8 h-8 text-emerald-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
            </svg>
          </div>
          <h1 className="text-3xl font-bold mb-2">Register Your Society</h1>
          <p className="text-gray-400">Onboard your housing society to the Society Sphere platform.</p>
        </div>

        <div className="bg-gray-900/80 backdrop-blur-xl border border-gray-800 p-8 rounded-3xl shadow-2xl">
          <form onSubmit={handleSubmit} className="space-y-6">
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {/* Society Info */}
              <div className="space-y-6">
                <h3 className="text-xl font-semibold text-gray-200 border-b border-gray-800 pb-2">Society Details</h3>
                
                <div>
                  <label className="block text-sm font-medium text-gray-400 mb-2">Society Name *</label>
                  <input
                    type="text"
                    name="societyName"
                    required
                    value={formData.societyName}
                    onChange={handleChange}
                    className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all"
                    placeholder="e.g. Sunrise Apartments"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-400 mb-2">Registration No. *</label>
                  <input
                    type="text"
                    name="registrationNumber"
                    required
                    value={formData.registrationNumber}
                    onChange={handleChange}
                    className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all"
                    placeholder="Reg No. or License"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-400 mb-2">Full Address *</label>
                  <textarea
                    name="address"
                    required
                    rows="3"
                    value={formData.address}
                    onChange={handleChange}
                    className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all resize-none"
                    placeholder="Complete society address..."
                  ></textarea>
                </div>
              </div>

              {/* Admin Info */}
              <div className="space-y-6">
                <h3 className="text-xl font-semibold text-gray-200 border-b border-gray-800 pb-2">Admin Details</h3>
                
                <div>
                  <label className="block text-sm font-medium text-gray-400 mb-2">Admin Name *</label>
                  <input
                    type="text"
                    name="adminName"
                    required
                    value={formData.adminName}
                    onChange={handleChange}
                    className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all"
                    placeholder="Full Name"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-400 mb-2">Admin Email *</label>
                  <input
                    type="email"
                    name="adminEmail"
                    required
                    value={formData.adminEmail}
                    onChange={handleChange}
                    className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all"
                    placeholder="admin@example.com"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-400 mb-2">Admin Phone *</label>
                  <input
                    type="tel"
                    name="adminPhone"
                    required
                    value={formData.adminPhone}
                    onChange={handleChange}
                    className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-emerald-500 outline-none transition-all"
                    placeholder="+91 9876543210"
                  />
                </div>
              </div>
            </div>

            <div className="pt-6">
              <button
                type="submit"
                className="w-full bg-emerald-600 hover:bg-emerald-500 text-white font-bold rounded-xl px-4 py-4 transition-all shadow-lg shadow-emerald-600/30"
              >
                Submit Registration Request
              </button>
            </div>
            
          </form>
        </div>
      </div>
    </div>
  );
};

export default RegisterSociety;

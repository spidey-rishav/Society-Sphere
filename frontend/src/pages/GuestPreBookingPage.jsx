import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { useToast } from '../context/ToastContext';
import { preBookGuest } from '../services/visitorService';

const GuestPreBookingPage = () => {
  const { societyCode } = useParams();
  const { addToast } = useToast();
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [barcode, setBarcode] = useState(null);
  
  const [formData, setFormData] = useState({
    guestName: '',
    mobileNumber: '',
    purposeOfVisit: '',
    expectedArrival: '',
    hostFlatNumber: ''
  });

  const handleChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      // Pass societyCode appropriately depending on backend expectations
      const response = await preBookGuest({ ...formData, societyCode });
      setSuccess(true);
      if (response.data && response.data.barcode) {
        setBarcode(response.data.barcode);
      }
      addToast('Request sent to resident!', 'success');
    } catch (err) {
      addToast(err.response?.data?.message || 'Failed to send request', 'error');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-950 flex flex-col items-center justify-center p-4">
      <div className="bg-gray-900 border border-gray-800 rounded-3xl shadow-2xl p-8 w-full max-w-lg relative overflow-hidden">
        {/* Glow effect */}
        <div className="absolute -top-32 -right-32 w-64 h-64 bg-indigo-600 rounded-full mix-blend-multiply filter blur-[80px] opacity-20"></div>

        <div className="text-center mb-8 relative z-10">
          <h1 className="text-3xl font-bold text-white mb-2">Society Sphere</h1>
          <p className="text-gray-400">Guest Pass Request</p>
          <div className="mt-2 inline-block bg-indigo-600/20 text-indigo-400 px-3 py-1 rounded-full text-xs font-semibold">
            {societyCode}
          </div>
        </div>

        {success ? (
          <div className="text-center bg-gray-800/50 p-6 rounded-2xl border border-emerald-500/30 relative z-10">
            <div className="w-16 h-16 bg-emerald-600/20 rounded-full flex items-center justify-center mx-auto mb-4">
              <svg className="w-8 h-8 text-emerald-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
            </div>
            <h2 className="text-xl font-bold text-white mb-2">Request Sent!</h2>
            <p className="text-gray-400 mb-6">Your request has been sent to the resident. You will receive a barcode pass upon approval.</p>
            
            {barcode && (
              <div className="bg-white p-4 rounded-xl inline-block">
                {/* Mock barcode visual */}
                <div className="flex gap-1 h-16 w-full items-center justify-center mb-2 px-2">
                  {[...Array(20)].map((_, i) => (
                    <div key={i} className="bg-black h-full" style={{ width: `${Math.max(1, Math.random() * 4)}px` }}></div>
                  ))}
                </div>
                <p className="text-black font-mono font-bold tracking-widest">{barcode}</p>
              </div>
            )}
          </div>
        ) : (
          <form onSubmit={handleSubmit} className="space-y-5 relative z-10">
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Guest Name</label>
              <input type="text" name="guestName" required value={formData.guestName} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none transition-all" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Mobile Number</label>
              <input type="tel" name="mobileNumber" required value={formData.mobileNumber} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none transition-all" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Host Flat Number</label>
              <input type="text" name="hostFlatNumber" required value={formData.hostFlatNumber} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none transition-all" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Expected Arrival Date & Time</label>
              <input type="datetime-local" name="expectedArrival" required value={formData.expectedArrival} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none transition-all" />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-300 mb-2">Purpose of Visit</label>
              <input type="text" name="purposeOfVisit" required value={formData.purposeOfVisit} onChange={handleChange} className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none transition-all" />
            </div>
            
            <button
              type="submit"
              disabled={loading}
              className="w-full bg-indigo-600 hover:bg-indigo-500 text-white font-semibold rounded-xl px-4 py-3 transition-all mt-6 shadow-lg shadow-indigo-500/20"
            >
              {loading ? 'Sending...' : 'Request Entry Pass'}
            </button>
          </form>
        )}
      </div>
    </div>
  );
};

export default GuestPreBookingPage;

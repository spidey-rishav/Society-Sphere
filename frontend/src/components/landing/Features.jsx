import React from 'react';

const Features = () => {
  return (
    <section id="features" className="py-20 bg-gray-950 text-white relative">
      <div className="absolute top-0 right-0 w-[500px] h-[500px] bg-purple-600/10 rounded-full blur-[120px]" />
      
      <div className="max-w-6xl mx-auto px-4 relative z-10">
        <div className="text-center mb-16">
          <h2 className="text-4xl font-bold mb-4 bg-clip-text text-transparent bg-gradient-to-r from-purple-400 to-indigo-400">Core Features</h2>
          <p className="text-gray-400 text-lg">Everything you need to manage your society effortlessly.</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {/* Feature 1 */}
          <div className="bg-gray-900 border border-gray-800 p-8 rounded-3xl hover:border-indigo-500/50 transition-all group hover:-translate-y-2">
            <div className="w-14 h-14 bg-indigo-600/20 rounded-2xl flex items-center justify-center mb-6 text-indigo-400">
              <svg className="w-8 h-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold mb-3">Resident & Admin Management</h3>
            <p className="text-gray-400">Comprehensive management system for residents and multi-tiered admin roles.</p>
          </div>

          {/* Feature 2 */}
          <div className="bg-gray-900 border border-gray-800 p-8 rounded-3xl hover:border-emerald-500/50 transition-all group hover:-translate-y-2">
            <div className="w-14 h-14 bg-emerald-600/20 rounded-2xl flex items-center justify-center mb-6 text-emerald-400">
              <svg className="w-8 h-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v1m6 11h2m-6 0h-2v4m0-11v3m0 0h.01M12 12h4.01M16 20h4M4 12h4m12 0h.01M5 8h2a1 1 0 001-1V5a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1zm14 0h2a1 1 0 001-1V5a1 1 0 00-1-1h-2a1 1 0 00-1 1v2a1 1 0 001 1zM5 20h2a1 1 0 001-1v-2a1 1 0 00-1-1H5a1 1 0 00-1 1v2a1 1 0 001 1z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold mb-3">Smart QR Guest Entry</h3>
            <p className="text-gray-400">Pre-approve guests using unique QR codes for seamless and secure entry.</p>
          </div>

          {/* Feature 3 */}
          <div className="bg-gray-900 border border-gray-800 p-8 rounded-3xl hover:border-red-500/50 transition-all group hover:-translate-y-2">
            <div className="w-14 h-14 bg-red-600/20 rounded-2xl flex items-center justify-center mb-6 text-red-400">
              <svg className="w-8 h-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold mb-3">Complaint Tracking</h3>
            <p className="text-gray-400">Integrated complaint raising, tracking, and resolution system with payment options.</p>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Features;

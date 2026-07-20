import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';

const SocietySelectionPage = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gray-950 p-6 md:p-12 text-white">
      <div className="max-w-6xl mx-auto">
        <header className="mb-12 text-center md:text-left">
          <h1 className="text-4xl md:text-5xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-indigo-400 to-purple-400 mb-4">
            Welcome to Society Sphere
          </h1>
          <p className="text-gray-400 text-lg max-w-2xl">
            The premium platform for modern housing societies. Select an option below to get started.
          </p>
        </header>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {/* Option 1: Search & Choose */}
          <div className="bg-gray-900 border border-gray-800 p-8 rounded-3xl shadow-xl hover:shadow-2xl hover:border-indigo-500/50 transition-all duration-300 group">
            <div className="w-16 h-16 bg-indigo-600/20 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
              <svg className="w-8 h-8 text-indigo-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
            </div>
            <h2 className="text-2xl font-bold mb-4">Find Your Society</h2>
            <p className="text-gray-400 mb-6 line-clamp-2">Search for your registered society and log in to your dashboard.</p>
            <div className="relative">
              <input
                type="text"
                placeholder="Search society name..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full bg-gray-800 border border-gray-700 rounded-xl px-4 py-3 text-white focus:ring-2 focus:ring-indigo-500 outline-none"
              />
              <button 
                onClick={() => navigate('/login')}
                className="mt-4 w-full bg-indigo-600 hover:bg-indigo-500 text-white font-semibold rounded-xl px-4 py-3 transition-all"
              >
                Proceed to Login
              </button>
            </div>
          </div>

          {/* Option 2: Register */}
          <div className="bg-gray-900 border border-gray-800 p-8 rounded-3xl shadow-xl hover:shadow-2xl hover:border-emerald-500/50 transition-all duration-300 group">
            <div className="w-16 h-16 bg-emerald-600/20 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
              <svg className="w-8 h-8 text-emerald-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
              </svg>
            </div>
            <h2 className="text-2xl font-bold mb-4">Register Society</h2>
            <p className="text-gray-400 mb-6">Create a new workspace for your housing society and get started.</p>
            <button className="w-full bg-gray-800 hover:bg-gray-700 border border-gray-700 text-white font-semibold rounded-xl px-4 py-3 transition-all mt-auto flex justify-center items-center gap-2">
              Start Registration <span aria-hidden="true">&rarr;</span>
            </button>
          </div>

          {/* Option 3: Super Admin */}
          <div className="bg-gray-900 border border-gray-800 p-8 rounded-3xl shadow-xl hover:shadow-2xl hover:border-red-500/50 transition-all duration-300 group">
            <div className="w-16 h-16 bg-red-600/20 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
              <svg className="w-8 h-8 text-red-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
            </div>
            <h2 className="text-2xl font-bold mb-4">Super Admin</h2>
            <p className="text-gray-400 mb-6">For platform administrators to manage societies and global settings.</p>
            <button className="w-full bg-gray-800 hover:bg-gray-700 border border-gray-700 text-white font-semibold rounded-xl px-4 py-3 transition-all mt-auto flex justify-center items-center gap-2">
              Admin Login <span aria-hidden="true">&rarr;</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SocietySelectionPage;

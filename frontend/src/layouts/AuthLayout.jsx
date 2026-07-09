import React from "react";

const AuthLayout = ({ title, subtitle, children }) => {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-indigo-900 via-purple-900 to-indigo-800 px-4">
      
      {/* Card */}
      <div className="w-full max-w-md bg-white/10 backdrop-blur-xl border border-white/20 rounded-2xl shadow-2xl p-8">
        
        {/* Header */}
        <div className="text-center mb-6">
          <h1 className="text-3xl font-bold text-white">{title}</h1>
          <p className="text-indigo-200 mt-2 text-sm">{subtitle}</p>
        </div>

        {/* Form Content */}
        <div>{children}</div>

      </div>
    </div>
  );
};

export default AuthLayout;
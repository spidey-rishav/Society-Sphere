import React, { useEffect, useState } from 'react';
import { X, CheckCircle, AlertCircle, Info } from 'lucide-react';

const Toast = ({ message, type = 'info', onClose }) => {
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    setIsVisible(true);
    const timer = setTimeout(() => {
      setIsVisible(false);
      setTimeout(onClose, 300); // Wait for animation before unmounting
    }, 3000);
    return () => clearTimeout(timer);
  }, [onClose]);

  const styles = {
    success: 'bg-emerald-900/50 border-emerald-500/50 text-emerald-100',
    error: 'bg-red-900/50 border-red-500/50 text-red-100',
    info: 'bg-blue-900/50 border-blue-500/50 text-blue-100',
  };

  const icons = {
    success: <CheckCircle className="w-5 h-5 text-emerald-400" />,
    error: <AlertCircle className="w-5 h-5 text-red-400" />,
    info: <Info className="w-5 h-5 text-blue-400" />,
  };

  return (
    <div
      className={`fixed top-4 right-4 z-50 flex items-center p-4 rounded-xl border shadow-lg backdrop-blur-md transition-all duration-300 transform ${
        isVisible ? 'translate-x-0 opacity-100' : 'translate-x-full opacity-0'
      } ${styles[type]}`}
    >
      <div className="mr-3">{icons[type]}</div>
      <p className="font-medium mr-6">{message}</p>
      <button
        onClick={() => {
          setIsVisible(false);
          setTimeout(onClose, 300);
        }}
        className="opacity-70 hover:opacity-100 transition-opacity"
      >
        <X className="w-4 h-4" />
      </button>
    </div>
  );
};

export default Toast;

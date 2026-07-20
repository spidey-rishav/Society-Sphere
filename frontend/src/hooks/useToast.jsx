import { useState, useCallback } from 'react';
import Toast from '../components/common/Toast';

const useToast = () => {
  const [toast, setToast] = useState(null);

  const showToast = useCallback((message, type = 'info') => {
    setToast({ message, type });
    // Auto-clear after 3.5s (Toast component also auto-dismisses, but this clears React state)
    setTimeout(() => setToast(null), 3500);
  }, []);

  const hideToast = useCallback(() => {
    setToast(null);
  }, []);

  const ToastComponent = toast ? (
    <Toast message={toast.message} type={toast.type} onClose={hideToast} />
  ) : null;

  return { showToast, ToastComponent };
};

export default useToast;

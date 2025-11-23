import { Navigate } from 'react-router-dom';
import { useEffect } from 'react';

function ProtectedRoute({ children }) {
  const isAuthenticated = localStorage.getItem('isAdmin') === 'true';

  useEffect(() => {
    // 인증되지 않은 사용자가 관리자 페이지 접근 시도 시 로그 (디버깅용)
    if (!isAuthenticated) {
      console.log('인증되지 않은 접근 - 메인 페이지로 리다이렉트');
    }
  }, [isAuthenticated]);

  if (!isAuthenticated) {
    // 관리자 페이지 접근 차단 - 메인 페이지로 리다이렉트
    return <Navigate to="/" replace />;
  }

  return children;
}

export default ProtectedRoute;

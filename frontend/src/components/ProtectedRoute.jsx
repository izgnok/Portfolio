import { Navigate } from 'react-router-dom';

function ProtectedRoute({ children }) {
  const isAuthenticated = localStorage.getItem('isAdmin') === 'true';

  if (!isAuthenticated) {
    // 관리자 페이지 접근 차단 - 메인 페이지로 리다이렉트
    return <Navigate to="/" replace />;
  }

  return children;
}

export default ProtectedRoute;

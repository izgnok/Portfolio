import { Link, useNavigate } from 'react-router-dom';
import { useState, useCallback } from 'react';
import LoginModal from './LoginModal';
import { authAPI } from '../services/api';
import './Navigation.css';

function Navigation({ isAdmin = false }) {
  const navigate = useNavigate();
  // 초기값 false - 페이지 로드 시 모달이 절대 표시되지 않음
  const [showLoginModal, setShowLoginModal] = useState(false);
  const isAuthenticated = localStorage.getItem('isAdmin') === 'true';

  // 관리자 버튼 클릭 핸들러
  const handleAdminClick = useCallback((e) => {
    e.preventDefault();
    e.stopPropagation();
    
    if (isAuthenticated) {
      // 이미 로그인된 경우 바로 Control Panel로 이동
      navigate('/admin');
    } else {
      // 로그인되지 않은 경우에만 모달 표시
      console.log('Opening login modal');
      setShowLoginModal(true);
    }
  }, [isAuthenticated, navigate]);

  // 로그인 성공 핸들러
  const handleLogin = useCallback(() => {
    console.log('Login successful - navigating to control panel');
    setShowLoginModal(false);
    navigate('/admin');
  }, [navigate]);

  // 로그아웃 핸들러
  const handleLogout = useCallback(async () => {
    if (window.confirm('Are you sure you want to logout?')) {
      try {
        // 백엔드 POST /api/users/logout 호출 (세션 무효화)
        await authAPI.logout();
        console.log('Logout successful - session invalidated');
      } catch (error) {
        console.error('Logout error:', error);
      } finally {
        // 로컬 스토리지 정리
        localStorage.removeItem('isAdmin');
        localStorage.removeItem('userInfo');
        // 메인 페이지로 이동
        navigate('/');
      }
    }
  }, [navigate]);

  return (
    <>
      <nav className="nav">
        <div className="nav-container">
          <Link to="/" className="nav-logo">
            ✨ Portfolio
          </Link>
          {isAdmin ? (
            <ul className="nav-links">
              <li><Link to="/admin/profile">👤 Profile</Link></li>
              <li><Link to="/admin/education">🎓 Education</Link></li>
              <li><Link to="/admin/certificates">📜 Certificates</Link></li>
              <li><Link to="/admin/projects">🚀 Projects</Link></li>
              <li><Link to="/admin/skills">💻 Skills</Link></li>
              <li><Link to="/">🏠 Home</Link></li>
              <li><button onClick={handleLogout} className="btn-logout">🚪 Logout</button></li>
            </ul>
          ) : (
            <ul className="nav-links">
              <li><Link to="/">🏠 Home</Link></li>
              <li><Link to="/projects">🚀 Projects</Link></li>
              <li><a href="/admin" onClick={handleAdminClick}>🔐 Admin</a></li>
            </ul>
          )}
        </div>
      </nav>

      <LoginModal
        isOpen={showLoginModal}
        onClose={() => setShowLoginModal(false)}
        onLogin={handleLogin}
      />
    </>
  );
}

export default Navigation;

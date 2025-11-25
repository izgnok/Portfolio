import { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { authAPI } from '../services/api';
import './LoginModal.css';

function LoginModal({ isOpen, onClose, onLogin }) {
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { register, handleSubmit, formState: { errors }, reset } = useForm();

  // 모달이 닫힐 때 폼 초기화
  useEffect(() => {
    if (!isOpen) {
      reset();
      setError('');
    }
  }, [isOpen, reset]);

  const onSubmit = async (data) => {
    setLoading(true);
    setError('');

    try {
      // 백엔드 POST /api/users/login 호출 (세션 생성)
      const response = await authAPI.login(data);
      
      // 백엔드 ResponseDto 구조: { data: UserResponse, message, ... }
      // 세션이 백엔드에서 생성되고, JSESSIONID 쿠키가 자동으로 설정됨
      if (response.data) {
        // localStorage에 관리자 상태와 사용자 정보 저장
        localStorage.setItem('isAdmin', 'true');
        localStorage.setItem('userInfo', JSON.stringify(response.data));
        
        onLogin();
        onClose();
      } else {
        setError('로그인에 실패했습니다. 응답 데이터가 없습니다.');
      }
    } catch (err) {
      setError(err.message || '아이디 또는 비밀번호가 올바르지 않습니다.');
    } finally {
      setLoading(false);
    }
  };

  // isOpen이 false면 렌더링하지 않음
  if (!isOpen) return null;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-container" onClick={(e) => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>×</button>
        
        <div className="modal-header">
          <h2>🔐 관리자 로그인</h2>
          <p>관리자 계정으로 로그인하세요</p>
        </div>

        {error && (
          <div className="login-error">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit(onSubmit)} className="login-form">
          <div className="form-group">
            <label>아이디</label>
            <input
              type="text"
              {...register('username', { required: '아이디를 입력하세요' })}
              placeholder="admin"
              className={errors.username ? 'error' : ''}
            />
            {errors.username && (
              <span className="error-text">{errors.username.message}</span>
            )}
          </div>

          <div className="form-group">
            <label>비밀번호</label>
            <input
              type="password"
              {...register('password', { required: '비밀번호를 입력하세요' })}
              placeholder="••••••••"
              className={errors.password ? 'error' : ''}
            />
            {errors.password && (
              <span className="error-text">{errors.password.message}</span>
            )}
          </div>

          <button type="submit" className="btn-login" disabled={loading}>
            {loading ? '로그인 중...' : '로그인'}
          </button>
        </form>

        <div className="login-info">
          <p className="info-text">💡 관리자 계정으로 로그인하세요</p>
          <p className="info-detail">백엔드에 등록된 관리자 계정이 필요합니다</p>
        </div>
      </div>
    </div>
  );
}

export default LoginModal;

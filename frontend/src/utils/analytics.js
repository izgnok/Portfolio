import ReactGA from 'react-ga4';

// Google Analytics 이벤트 전송 헬퍼 함수
export const trackEvent = (category, action, label = null) => {
  const GA_MEASUREMENT_ID = import.meta.env.VITE_GA_MEASUREMENT_ID;
  
  if (GA_MEASUREMENT_ID && GA_MEASUREMENT_ID !== 'G-XXXXXXXXXX' && import.meta.env.PROD) {
    ReactGA.event({
      category,
      action,
      label
    });
    
    console.log('Event tracked:', { category, action, label });
  }
};

// 프로젝트 상세 조회 추적
export const trackProjectView = (projectId, projectName) => {
  trackEvent('Project', 'View Project Detail', `${projectId} - ${projectName}`);
};

// 외부 링크 클릭 추적
export const trackOutboundLink = (url, label) => {
  trackEvent('Outbound Link', 'Click', label || url);
};

// 버튼 클릭 추적
export const trackButtonClick = (buttonName, location) => {
  trackEvent('User Interaction', 'Click Button', `${buttonName} - ${location}`);
};

// 다운로드 추적
export const trackDownload = (fileName, fileType) => {
  trackEvent('Download', 'File Download', `${fileName} (${fileType})`);
};

// 관리자 액션 추적
export const trackAdminAction = (action, target) => {
  trackEvent('Admin', action, target);
};

// 로그인 추적
export const trackLogin = (success) => {
  trackEvent('Authentication', success ? 'Login Success' : 'Login Failure', null);
};

// 로그아웃 추적
export const trackLogout = () => {
  trackEvent('Authentication', 'Logout', null);
};

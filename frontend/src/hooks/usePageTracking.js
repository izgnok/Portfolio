import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import ReactGA from 'react-ga4';

export const usePageTracking = () => {
  const location = useLocation();

  useEffect(() => {
    // Google Analytics가 초기화된 경우에만 전송
    const GA_MEASUREMENT_ID = import.meta.env.VITE_GA_MEASUREMENT_ID;
    
    if (GA_MEASUREMENT_ID && GA_MEASUREMENT_ID !== 'G-XXXXXXXXXX' && import.meta.env.PROD) {
      ReactGA.send({ 
        hitType: 'pageview', 
        page: location.pathname + location.search,
        title: document.title
      });
      
      console.log('Page tracked:', location.pathname);
    }
  }, [location]);
};

import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import { ThemeProvider } from './contexts/ThemeContext'
import './index.css'
import './styles/darkmode.css'
import ReactGA from 'react-ga4'

// Google Analytics 초기화
const GA_MEASUREMENT_ID = import.meta.env.VITE_GA_MEASUREMENT_ID
if (GA_MEASUREMENT_ID && import.meta.env.PROD) {
  ReactGA.initialize(GA_MEASUREMENT_ID)
  console.log('Google Analytics initialized')
}

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <ThemeProvider>
      <App />
    </ThemeProvider>
  </React.StrictMode>,
)

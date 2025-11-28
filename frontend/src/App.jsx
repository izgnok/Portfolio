import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import MainPage from './pages/MainPage';
import ProjectsList from './pages/ProjectsList';
import ProjectDetail from './pages/ProjectDetail';
import AdminDashboard from './pages/admin/AdminDashboard';
import AdminProfile from './pages/admin/AdminProfile';
import AdminProjects from './pages/admin/AdminProjects';
import AdminSkills from './pages/admin/AdminSkills';
import AdminEducation from './pages/admin/AdminEducation';
import AdminCertificates from './pages/admin/AdminCertificates';
import { usePageTracking } from './hooks/usePageTracking';

// Router 내부에서 usePageTracking 사용
function AppRoutes() {
  usePageTracking();

  return (
    <Routes>
      {/* 방문자 페이지 */}
      <Route path="/" element={<MainPage />} />
      <Route path="/projects" element={<ProjectsList />} />
      <Route path="/projects/:id" element={<ProjectDetail />} />
      
      {/* 관리자 페이지 (보호됨) */}
      <Route path="/admin" element={
        <ProtectedRoute>
          <AdminDashboard />
        </ProtectedRoute>
      } />
      <Route path="/admin/profile" element={
        <ProtectedRoute>
          <AdminProfile />
        </ProtectedRoute>
      } />
      <Route path="/admin/projects" element={
        <ProtectedRoute>
          <AdminProjects />
        </ProtectedRoute>
      } />
      <Route path="/admin/projects/:id" element={
        <ProtectedRoute>
          <AdminProjects />
        </ProtectedRoute>
      } />
      <Route path="/admin/skills" element={
        <ProtectedRoute>
          <AdminSkills />
        </ProtectedRoute>
      } />
      <Route path="/admin/education" element={
        <ProtectedRoute>
          <AdminEducation />
        </ProtectedRoute>
      } />
      <Route path="/admin/certificates" element={
        <ProtectedRoute>
          <AdminCertificates />
        </ProtectedRoute>
      } />
    </Routes>
  );
}

function App() {
  return (
    <Router>
      <AppRoutes />
    </Router>
  );
}

export default App;

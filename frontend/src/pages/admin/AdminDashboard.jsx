import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Navigation from '../../components/Navigation';
import Loading from '../../components/Loading';
import { projectsAPI, skillsAPI } from '../../services/api';
import './AdminDashboard.css';

function AdminDashboard() {
  const [loading, setLoading] = useState(true);
  const [projects, setProjects] = useState([]);
  const [skills, setSkills] = useState([]);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [projectsData, skillsData] = await Promise.all([
        projectsAPI.getAll(),
        skillsAPI.getAll()
      ]);

      setProjects(projectsData.data);
      setSkills(skillsData.data);
    } catch (error) {
      console.error('Failed to load data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="admin-dashboard">
      <Navigation isAdmin />
      
      <div className="container">
        {/* Header */}
        <section className="admin-header fade-in">
          <h1 className="admin-title">⚡ Control Panel</h1>
          <p className="admin-subtitle">Welcome back! Manage your portfolio with ease</p>
        </section>

        {/* Statistics Cards */}
        <section className="stats-section fade-in">
          <div className="stats-grid">
            <div className="stat-card stat-projects">
              <div className="stat-icon">🚀</div>
              <div className="stat-content">
                <h3>Projects</h3>
                <p className="stat-number">{projects.length}</p>
              </div>
            </div>
            <div className="stat-card stat-skills">
              <div className="stat-icon">💻</div>
              <div className="stat-content">
                <h3>Skills</h3>
                <p className="stat-number">{skills.length}</p>
              </div>
            </div>
          </div>
        </section>

        {/* Quick Actions */}
        <section className="quick-actions fade-in">
          <h2 className="section-title">🎯 Quick Access</h2>
          <div className="actions-grid">
            <Link to="/admin/profile" className="action-card">
              <span className="action-icon">👤</span>
              <h3>Edit Profile</h3>
              <p>Manage basic information and image</p>
            </Link>
            <Link to="/admin/education" className="action-card">
              <span className="action-icon">🎓</span>
              <h3>Education</h3>
              <p>Add/edit education information</p>
            </Link>
            <Link to="/admin/certificates" className="action-card">
              <span className="action-icon">📜</span>
              <h3>Certificates</h3>
              <p>Add/edit certificate information</p>
            </Link>
            <Link to="/admin/projects" className="action-card">
              <span className="action-icon">🚀</span>
              <h3>Projects</h3>
              <p>Add/edit/delete projects</p>
            </Link>
            <Link to="/admin/skills" className="action-card">
              <span className="action-icon">💻</span>
              <h3>Skills</h3>
              <p>Add/edit/delete skills</p>
            </Link>
            <Link to="/" className="action-card">
              <span className="action-icon">👁️</span>
              <h3>Preview Site</h3>
              <p>View visitor screen</p>
            </Link>
          </div>
        </section>


      </div>
    </div>
  );
}

export default AdminDashboard;

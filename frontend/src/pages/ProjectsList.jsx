import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Navigation from '../components/Navigation';
import Loading from '../components/Loading';
import { projectsAPI } from '../services/api';
import { formatDate } from '../utils/dateFormat';
import './ProjectsList.css';

function ProjectsList() {
  const [loading, setLoading] = useState(true);
  const [projects, setProjects] = useState([]);

  useEffect(() => {
    loadProjects();
  }, []);

  const loadProjects = async () => {
    try {
      const response = await projectsAPI.getAll();
      setProjects(response.data);
    } catch (error) {
      console.error('프로젝트 로딩 실패:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="projects-list-page">
      <Navigation />
      
      <div className="container">
        {/* 헤더 */}
        <section className="projects-header fade-in">
          <h1 className="page-title">🚀 프로젝트</h1>
          <p className="page-subtitle">
            다양한 프로젝트를 확인해보세요
          </p>
        </section>

        {/* 프로젝트 카드 */}
        {projects.length === 0 ? (
          <div className="no-projects">
            <p>😢 프로젝트가 없습니다</p>
          </div>
        ) : (
          <section className="projects-grid fade-in">
            {projects.map((project) => (
              <Link
                key={project.id}
                to={`/projects/${project.id}`}
                className="project-card"
              >
                {project.firstImage && (
                  <div className="project-image">
                    <img src={project.firstImage} alt={project.name || project.title} />
                  </div>
                )}
                <div className="project-content">
                  <div className="project-header">
                    <h3 className="project-title">{project.name || project.title}</h3>
                    {project.hasAward && (
                      <span className="award-badge" title={project.awardName}>🏆</span>
                    )}
                  </div>
                  {project.subtitle && (
                    <p className="project-subtitle">{project.subtitle}</p>
                  )}
                  {project.description && (
                    <p className="project-description">
                      {project.description.length > 100
                        ? `${project.description.substring(0, 100)}...`
                        : project.description}
                    </p>
                  )}
                  <div className="project-meta">
                    <span className="project-date">
                      📅 {formatDate(project.startDate)}
                      {project.endDate && ` ~ ${formatDate(project.endDate)}`}
                    </span>
                    {project.hasAward && project.awardName && (
                      <span className="award-info">
                        🏆 {project.awardName}
                      </span>
                    )}
                  </div>
                  {project.techStacks && project.techStacks.length > 0 && (
                    <div className="tech-tags">
                      {project.techStacks.slice(0, 5).map((tech, index) => (
                        <span key={index} className="tech-tag">
                          {tech.name}
                        </span>
                      ))}
                      {project.techStacks.length > 5 && (
                        <span className="tech-tag">+{project.techStacks.length - 5}</span>
                      )}
                    </div>
                  )}
                </div>
                <div className="project-footer">
                  <span className="view-detail">자세히 보기 →</span>
                </div>
              </Link>
            ))}
          </section>
        )}
      </div>
    </div>
  );
}

export default ProjectsList;

import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import Navigation from '../components/Navigation';
import Loading from '../components/Loading';
import { projectsAPI } from '../services/api';
import { formatDate } from '../utils/dateFormat';
import { trackProjectView, trackOutboundLink } from '../utils/analytics';
import './ProjectDetail.css';

function ProjectDetail() {
  const { id } = useParams();
  const [loading, setLoading] = useState(true);
  const [project, setProject] = useState(null);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);

  useEffect(() => {
    loadProject();
  }, [id]);

  const loadProject = async () => {
    try {
      const response = await projectsAPI.getById(id);
      setProject(response.data);
      
      // Google Analytics: 프로젝트 상세 조회 추적
      if (response.data) {
        trackProjectView(id, response.data.name || response.data.title);
      }
    } catch (error) {
      // Error silently handled
    } finally {
      setLoading(false);
    }
  };

  // JSON 문자열 파싱 헬퍼 함수
  const parseJsonField = (field) => {
    if (!field) return [];
    if (typeof field === 'string') {
      try {
        return JSON.parse(field);
      } catch (e) {
        return [];
      }
    }
    return Array.isArray(field) ? field : [];
  };

  const nextImage = () => {
    const projectImages = project?.projectImages || [];
    if (projectImages && projectImages.length > 0) {
      setCurrentImageIndex((prev) =>
        prev === projectImages.length - 1 ? 0 : prev + 1
      );
    }
  };

  const prevImage = () => {
    const projectImages = project?.projectImages || [];
    if (projectImages && projectImages.length > 0) {
      setCurrentImageIndex((prev) =>
        prev === 0 ? projectImages.length - 1 : prev - 1
      );
    }
  };

  if (loading) {
    return <Loading />;
  }

  if (!project) {
    return (
      <div className="project-detail-page">
        <Navigation />
        <div className="container">
          <div className="error-message">
            <h2>😢 프로젝트를 찾을 수 없습니다</h2>
            <Link to="/projects" className="btn">← 목록으로</Link>
          </div>
        </div>
      </div>
    );
  }

  // JSON 필드 파싱
  const summaries = parseJsonField(project.summaries);
  const coreValues = parseJsonField(project.coreValues);
  const mainFeatures = parseJsonField(project.mainFeatures);
  const roles = parseJsonField(project.roles);
  const problemSolutions = parseJsonField(project.problemSolutions);
  const regrets = parseJsonField(project.regrets);
  const improvements = parseJsonField(project.improvements);
  
  // 프로젝트 이미지 (백엔드에서 projectImages로 옴)
  const projectImages = project.projectImages || [];

  // 기술스택 필드 파싱
  const techDatabase = parseJsonField(project.techDatabase);
  const techBackend = parseJsonField(project.techBackend);
  const techFrontend = parseJsonField(project.techFrontend);
  const techIot = parseJsonField(project.techIot);
  const techCicd = parseJsonField(project.techCicd);
  const techExternalApi = parseJsonField(project.techExternalApi);

  return (
    <div className="project-detail-page">
      <Navigation />
      
      <div className="container">
        {/* 뒤로가기 */}
        <div className="back-button-container fade-in">
          <Link to="/projects" className="back-button">
            ← 목록으로
          </Link>
        </div>

        {/* 프로젝트 헤더 */}
        <section className="project-header fade-in">
          <div className="header-content">
            <div>
              <h1 className="project-title-large">
                {project.name || project.title}
                {project.hasAward && (
                  <span className="award-badge-large" title={project.awardName}>🏆</span>
                )}
              </h1>
              {project.subtitle && <p className="project-subtitle-large">{project.subtitle}</p>}
              {project.hasAward && project.awardName && (
                <p className="award-name">🏆 {project.awardName}</p>
              )}
            </div>
          </div>
        </section>

        {/* 이미지 갤러리 */}
        {projectImages && projectImages.length > 0 && (
          <section className="image-gallery fade-in">
            <div className="gallery-main">
              {projectImages.length > 1 && (
                <>
                  <button className="gallery-button prev" onClick={prevImage}>
                    ‹
                  </button>
                  <button className="gallery-button next" onClick={nextImage}>
                    ›
                  </button>
                </>
              )}
              <img
                src={projectImages[currentImageIndex]?.imageData}
                alt={`Project ${currentImageIndex + 1}`}
                className="gallery-image"
              />
            </div>
            {projectImages.length > 1 && (
              <div className="gallery-thumbnails">
                {projectImages.map((image, index) => (
                  <img
                    key={image.id || index}
                    src={image.imageData}
                    alt={`Thumbnail ${index + 1}`}
                    className={`thumbnail ${index === currentImageIndex ? 'active' : ''}`}
                    onClick={() => setCurrentImageIndex(index)}
                  />
                ))}
              </div>
            )}
          </section>
        )}

        {/* 프로젝트 정보 */}
        <section className="project-info fade-in">
          <div className="info-grid">
            <div className="info-card">
              <h3>📅 프로젝트 기간</h3>
              <p>
                {formatDate(project.startDate)}
                {project.endDate && ` ~ ${formatDate(project.endDate)}`}
              </p>
            </div>
            {project.teamSize && (
              <div className="info-card">
                <h3>👥 팀 구성</h3>
                <p>{project.teamSize}명</p>
              </div>
            )}
            {project.githubUrl && (
              <div className="info-card">
                <h3>💻 GitHub</h3>
                <a 
                  href={project.githubUrl} 
                  target="_blank" 
                  rel="noopener noreferrer" 
                  className="project-link"
                  onClick={() => trackOutboundLink(project.githubUrl, `Project ${project.name} GitHub`)}
                >
                  {project.githubUrl}
                </a>
              </div>
            )}
          </div>
        </section>

        {/* 프로젝트 요약 */}
        {summaries.length > 0 && (
          <section className="section-block fade-in">
            <div className="section-card">
              <h2 className="section-title">📝 프로젝트 요약</h2>
              <ul className="list-items">
                {summaries.map((summary, index) => (
                  <li key={index}>{summary}</li>
                ))}
              </ul>
            </div>
          </section>
        )}

        {/* 핵심 가치 */}
        {coreValues.length > 0 && (
          <section className="section-block fade-in">
            <div className="section-card">
              <h2 className="section-title">💎 핵심 가치</h2>
              <ul className="list-items">
                {coreValues.map((value, index) => (
                  <li key={index}>{value}</li>
                ))}
              </ul>
            </div>
          </section>
        )}

        {/* 주요 기능 */}
        {mainFeatures.length > 0 && (
          <section className="section-block fade-in">
            <div className="section-card">
              <h2 className="section-title">⚙️ 주요 기능</h2>
              <ul className="list-items">
                {mainFeatures.map((feature, index) => (
                  <li key={index}>{feature}</li>
                ))}
              </ul>
            </div>
          </section>
        )}

        {/* 나의 역할 */}
        {roles.length > 0 && (
          <section className="section-block fade-in">
            <div className="section-card">
              <h2 className="section-title">🎯 나의 역할</h2>
              <ul className="list-items">
                {roles.map((role, index) => (
                  <li key={index}>{role}</li>
                ))}
              </ul>
            </div>
          </section>
        )}

        {/* 문제해결 */}
        {problemSolutions.length > 0 && (
          <section className="section-block fade-in">
            <div className="section-card">
              <h2 className="section-title">🔧 문제해결</h2>
              <div className="problem-solution-items">
                {problemSolutions.map((item, index) => (
                  <div key={index} className="problem-solution-item">
                    <div className="problem-part">
                      <div className="item-badge error">문제</div>
                      <p className="item-text">{item.problem}</p>
                    </div>
                    <div className="solution-part">
                      <div className="item-badge success">해결</div>
                      <p className="item-text">{item.solution}</p>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </section>
        )}

        {/* 아쉬운 점 및 개선 방안 */}
        {(regrets.length > 0 || improvements.length > 0) && (
          <section className="regrets-improvements-section fade-in">
            <div className="two-column-grid">
              {/* 좌측: 아쉬운 점 */}
              {regrets.length > 0 && (
                <div className="column-card regrets-card">
                  <div className="column-header">
                    <div className="column-icon warning">⚠️</div>
                    <h3 className="column-title">아쉬운 점</h3>
                  </div>
                  <div className="regret-items">
                    {regrets.map((regret, index) => (
                      <div key={index} className="regret-item">
                        <div className="item-badge warning">⚠️</div>
                        <p className="item-text">{regret}</p>
                      </div>
                    ))}
                  </div>
                </div>
              )}

              {/* 우측: 개선 방안 */}
              {improvements.length > 0 && (
                <div className="column-card improvements-card">
                  <div className="column-header">
                    <div className="column-icon">✅</div>
                    <h3 className="column-title">개선 방안</h3>
                  </div>
                  <div className="improvement-items">
                    {improvements.map((improvement, index) => (
                      <div key={index} className="improvement-item">
                        <div className="item-badge info">✅</div>
                        <p className="item-text">{improvement}</p>
                      </div>
                    ))}
                  </div>
                </div>
              )}
            </div>
          </section>
        )}

        {/* 시스템 아키텍처 */}
        {project.architectureImage && (
          <section className="architecture-section fade-in">
            <div className="section-card">
              <h2 className="section-title">🏗️ 시스템 아키텍처</h2>
              <div className="architecture-image-wrapper">
                <img
                  src={project.architectureImage}
                  alt="시스템 아키텍처"
                  className="architecture-image"
                />
              </div>
            </div>
          </section>
        )}

        {/* 기술스택 */}
        <section className="tech-stack-section fade-in">
          <div className="section-card">
            <h2 className="section-title">💻 기술스택</h2>
            
            {techFrontend.length > 0 && (
              <div className="tech-category">
                <h3 className="tech-category-title">🎨 Frontend</h3>
                <div className="tech-tags">
                  {techFrontend.map((tech, index) => (
                    <span key={index} className="tech-tag">{tech}</span>
                  ))}
                </div>
              </div>
            )}

            {techBackend.length > 0 && (
              <div className="tech-category">
                <h3 className="tech-category-title">⚙️ Backend</h3>
                <div className="tech-tags">
                  {techBackend.map((tech, index) => (
                    <span key={index} className="tech-tag">{tech}</span>
                  ))}
                </div>
              </div>
            )}

            {techDatabase.length > 0 && (
              <div className="tech-category">
                <h3 className="tech-category-title">🗄️ Database</h3>
                <div className="tech-tags">
                  {techDatabase.map((tech, index) => (
                    <span key={index} className="tech-tag">{tech}</span>
                  ))}
                </div>
              </div>
            )}

            {techIot.length > 0 && (
              <div className="tech-category">
                <h3 className="tech-category-title">🤖 IoT</h3>
                <div className="tech-tags">
                  {techIot.map((tech, index) => (
                    <span key={index} className="tech-tag">{tech}</span>
                  ))}
                </div>
              </div>
            )}

            {techCicd.length > 0 && (
              <div className="tech-category">
                <h3 className="tech-category-title">🔄 CI/CD</h3>
                <div className="tech-tags">
                  {techCicd.map((tech, index) => (
                    <span key={index} className="tech-tag">{tech}</span>
                  ))}
                </div>
              </div>
            )}

            {techExternalApi.length > 0 && (
              <div className="tech-category">
                <h3 className="tech-category-title">🔌 External API</h3>
                <div className="tech-tags">
                  {techExternalApi.map((tech, index) => (
                    <span key={index} className="tech-tag">{tech}</span>
                  ))}
                </div>
              </div>
            )}
          </div>
        </section>
      </div>
    </div>
  );
}

export default ProjectDetail;

import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Navigation from '../components/Navigation';
import Loading from '../components/Loading';
import { profileAPI, keywordsAPI, educationsAPI, certificatesAPI, skillsAPI, projectsAPI } from '../services/api';
import { formatDate } from '../utils/dateFormat';
import './MainPage.css';

function MainPage() {
  const [loading, setLoading] = useState(true);
  const [profile, setProfile] = useState(null);
  const [keywords, setKeywords] = useState([]);
  const [educations, setEducations] = useState([]);
  const [certificates, setCertificates] = useState([]);
  const [skills, setSkills] = useState([]);
  const [awardedProjects, setAwardedProjects] = useState([]);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [
        profileData,
        keywordsData,
        educationsData,
        certificatesData,
        skillsData,
        awardedProjectsData
      ] = await Promise.all([
        profileAPI.get(),
        keywordsAPI.getAll(),
        educationsAPI.getAll(),
        certificatesAPI.getAll(),
        skillsAPI.getAll(),
        projectsAPI.getAwarded()
      ]);

      setProfile(profileData.data);
      setKeywords(keywordsData.data || []);
      
      // 학력은 종료일 기준으로 최신순 정렬
      const sortedEducations = (educationsData.data || []).sort((a, b) => {
        const dateA = a.endDate ? new Date(a.endDate) : new Date();
        const dateB = b.endDate ? new Date(b.endDate) : new Date();
        return dateB - dateA; // 최신순
      });
      setEducations(sortedEducations);
      
      // Sort certificates by issue date (most recent first)
      const sortedCertificates = (certificatesData.data || []).sort((a, b) => {
        return new Date(b.issueDate) - new Date(a.issueDate);
      });
      setCertificates(sortedCertificates);
      
      setSkills(skillsData.data || []);
      
      // Sort awarded projects by end date (most recent first)
      const sortedProjects = (awardedProjectsData.data || []).sort((a, b) => {
        const dateA = a.endDate ? new Date(a.endDate) : new Date(a.startDate);
        const dateB = b.endDate ? new Date(b.endDate) : new Date(b.startDate);
        return dateB - dateA;
      });
      setAwardedProjects(sortedProjects);
    } catch (error) {
      console.error('데이터 로딩 실패:', error);
    } finally {
      setLoading(false);
    }
  };

  // 카테고리 순서 고정
  const categoryOrder = ['Frontend', 'Backend', 'IoT', 'Database', 'CI/CD', 'Others'];
  
  // 스킬을 카테고리별로 그룹화하고 정렬
  const groupedSkills = categoryOrder.reduce((acc, category) => {
    const categorySkills = skills
      .filter(skill => skill.category === category)
      .sort((a, b) => (a.displayOrder || 0) - (b.displayOrder || 0));
    
    if (categorySkills.length > 0) {
      acc[category] = categorySkills;
    }
    return acc;
  }, {});

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="main-page">
      <Navigation />
      
      <div className="container">
        {/* 프로필 섹션 */}
        <section className="profile-section fade-in">
          <div className="profile-card">
            {profile?.profileImage && (
              <img src={profile.profileImage} alt={profile.name} className="profile-image" />
            )}
            <h1 className="profile-name">{profile?.name || '이름'}</h1>
            {profile?.nameEn && <p className="profile-name-en">{profile.nameEn}</p>}
            
            {/* 연락처 정보 */}
            <div className="profile-contact">
              {profile?.birthDate && (
                <div className="contact-item">
                  <span className="contact-icon">🎂</span>
                  <span className="contact-text">
                    {formatDate(profile.birthDate)}
                  </span>
                </div>
              )}
              {profile?.phone && (
                <div className="contact-item">
                  <span className="contact-icon">📞</span>
                  <span className="contact-text">{profile.phone}</span>
                </div>
              )}
              {profile?.email && (
                <div className="contact-item">
                  <span className="contact-icon">📧</span>
                  <a href={`mailto:${profile.email}`} className="contact-link">
                    {profile.email}
                  </a>
                </div>
              )}
              {profile?.github && (
                <div className="contact-item">
                  <span className="contact-icon">💻</span>
                  <a href={profile.github} target="_blank" rel="noopener noreferrer" className="contact-link">
                    {profile.github.replace('https://', '').replace('http://', '')}
                  </a>
                </div>
              )}
            </div>
          </div>
        </section>

        {/* Keywords Section */}
        {keywords.length > 0 && (
          <section className="keywords-section fade-in">
            <h2 className="section-title">🏷️ Keywords</h2>
            <div className="keywords-container">
              {keywords.map((kw) => (
                <span key={kw.id} className="keyword-tag">
                  {kw.keyword}
                </span>
              ))}
            </div>
          </section>
        )}

        {/* Education Section */}
        {educations.length > 0 && (
          <section className="education-container fade-in">
            <h2 className="section-title">🎓 Education</h2>
            <div className="cards-grid">
              {educations.map((edu) => (
                <div key={edu.id} className="card">
                  <h3 className="card-title">{edu.name}</h3>
                  <p className="card-date">
                    {formatDate(edu.startDate)} ~ 
                    {edu.endDate ? formatDate(edu.endDate) : 'Present'}
                  </p>
                  <p className="card-status">{edu.status}</p>
                  {edu.gpa && <p className="card-gpa">GPA: {edu.gpa}</p>}
                </div>
              ))}
            </div>
          </section>
        )}

        {/* Certificates Section */}
        {certificates.length > 0 && (
          <section className="certificates-container fade-in">
            <h2 className="section-title">📜 Certificates</h2>
            <div className="cards-grid">
              {certificates.map((cert) => (
                <div key={cert.id} className="card">
                  <h3 className="card-title">{cert.name}</h3>
                  <p className="card-subtitle">{cert.issuer}</p>
                  <p className="card-date">
                    {formatDate(cert.issueDate)}
                  </p>
                  {cert.certificateNumber && (
                    <p className="card-cert-number">No. {cert.certificateNumber}</p>
                  )}
                </div>
              ))}
            </div>
          </section>
        )}

        {/* Awarded Projects Section */}
        {awardedProjects.length > 0 && (
          <section className="awards-container fade-in">
            <h2 className="section-title">🏆 Awarded Projects</h2>
            <div className="cards-grid">
              {awardedProjects.map((project) => (
                <Link 
                  key={project.id} 
                  to={`/projects/${project.id}`}
                  className="card award-card"
                  style={{ textDecoration: 'none', color: 'inherit' }}
                >
                  <div className="award-badge">🏆</div>
                  <h3 className="card-title">{project.name}</h3>
                  <p className="card-subtitle award-name">{project.awardName}</p>
                  {project.awardOrganization && (
                    <p className="card-organization">{project.awardOrganization}</p>
                  )}
                  <p className="card-date">
                    {formatDate(project.startDate)}
                    {project.endDate && ` ~ ${formatDate(project.endDate)}`}
                  </p>
                </Link>
              ))}
            </div>
          </section>
        )}

        {/* Skills Section */}
        {Object.keys(groupedSkills).length > 0 && (
          <section className="skills-section fade-in">
            <h2 className="section-title">💻 Skills</h2>
            {Object.entries(groupedSkills).map(([category, categorySkills]) => (
              <div key={category} className="skills-category">
                <h3 className="category-title">{category}</h3>
                <div className="skills-grid">
                  {categorySkills.map((skill) => (
                    <div key={skill.id} className="skill-card">
                      <h4 className="skill-name">{skill.name}</h4>
                      <div className="skill-level">
                        {Array.from({ length: 5 }).map((_, i) => (
                          <span
                            key={i}
                            className={`level-dot ${i < skill.level ? 'active' : ''}`}
                          />
                        ))}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ))}
          </section>
        )}

        {/* 프로젝트로 이동 */}
        <section className="cta-section fade-in">
          <Link to="/projects" className="btn btn-large">
            🚀 프로젝트 보러가기
          </Link>
        </section>
      </div>
    </div>
  );
}

export default MainPage;

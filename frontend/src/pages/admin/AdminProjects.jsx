import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Navigation from '../../components/Navigation';
import Loading from '../../components/Loading';
import { projectsAPI } from '../../services/api';
import './AdminProjects.css';

function AdminProjects() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [projects, setProjects] = useState([]);
  const [selectedProject, setSelectedProject] = useState(null);
  const [activeTab, setActiveTab] = useState('basic');
  const [message, setMessage] = useState({ type: '', text: '' });
  
  // 폼 데이터
  const [formData, setFormData] = useState({
    title: '',
    subtitle: '',
    status: '진행중',
    startDate: '',
    endDate: '',
    teamSize: '',
    projectUrl: '',
    githubUrl: '',
    hasAward: false,
    awardName: '',
    awardOrganization: '',
    // JSON 배열 필드들 (입력 시 한 줄에 하나씩)
    summaries: [],
    coreValues: [],
    mainFeatures: [],
    roles: [],
    problemSolutions: [],
    regrets: [],
    improvements: [],
    // 기술스택 카테고리 (입력 시 한 줄에 하나씩)
    techDatabase: [],
    techBackend: [],
    techFrontend: [],
    techIot: [],
    techCicd: [],
    techExternalApi: [],
  });

  // 이미지 파일들
  const [projectImageFiles, setProjectImageFiles] = useState([]);
  const [architectureImageFile, setArchitectureImageFile] = useState(null);
  
  // 이미지 미리보기
  const [projectImagePreviews, setProjectImagePreviews] = useState([]);
  const [architectureImagePreview, setArchitectureImagePreview] = useState(null);

  useEffect(() => {
    loadData();
  }, []);

  useEffect(() => {
    if (id && id !== 'new') {
      loadProject(id);
    } else if (id === 'new') {
      setSelectedProject({ projectSeq: null });
      resetForm();
    } else {
      setSelectedProject(null);
    }
  }, [id]);

  const loadData = async () => {
    try {
      const response = await projectsAPI.getAll();
      setProjects(response.data);
    } catch (error) {
    } finally {
      setLoading(false);
    }
  };

  const loadProject = async (projectId) => {
    try {
      const response = await projectsAPI.getById(projectId);
      const project = response.data;
      setSelectedProject(project);
      
      // JSON 필드 파싱
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

      setFormData({
        title: project.name || project.title || '',  // 백엔드에서 'name' 필드로 올 수 있음
        subtitle: project.subtitle || '',
        status: project.status || '진행중',
        startDate: project.startDate?.split('T')[0] || '',
        endDate: project.endDate?.split('T')[0] || '',
        teamSize: project.teamSize || '',
        projectUrl: project.projectUrl || '',
        githubUrl: project.githubUrl || '',
        hasAward: project.hasAward || false,
        awardName: project.awardName || '',
        awardOrganization: project.awardOrganization || '',
        summaries: parseJsonField(project.summaries),
        coreValues: parseJsonField(project.coreValues),
        mainFeatures: parseJsonField(project.mainFeatures),
        roles: parseJsonField(project.roles),
        problemSolutions: parseJsonField(project.problemSolutions),
        regrets: parseJsonField(project.regrets),
        improvements: parseJsonField(project.improvements),
        techDatabase: parseJsonField(project.techDatabase),
        techBackend: parseJsonField(project.techBackend),
        techFrontend: parseJsonField(project.techFrontend),
        techIot: parseJsonField(project.techIot),
        techCicd: parseJsonField(project.techCicd),
        techExternalApi: parseJsonField(project.techExternalApi),
      });

      // 기존 이미지들을 미리보기로 설정
      const images = project.projectImages || project.images || [];
      if (images.length > 0) {
        setProjectImagePreviews(images.map(img => img.imageData || img.image));
      } else {
        setProjectImagePreviews([]);
      }
      
      if (project.architectureImage) {
        setArchitectureImagePreview(project.architectureImage);
      } else {
        setArchitectureImagePreview(null);
      }
      
      // 이미지 파일 상태 초기화
      setProjectImageFiles([]);
      setArchitectureImageFile(null);

    } catch (error) {
      showMessage('error', 'Failed to load project.');
    }
  };

  const resetForm = () => {
    setFormData({
      title: '',
      subtitle: '',
      status: '진행중',
      startDate: '',
      endDate: '',
      teamSize: '',
      projectUrl: '',
      githubUrl: '',
      hasAward: false,
      awardName: '',
      awardOrganization: '',
      summaries: [],
      coreValues: [],
      mainFeatures: [],
      roles: [],
      problemSolutions: [],
      regrets: [],
      improvements: [],
      techDatabase: [],
      techBackend: [],
      techFrontend: [],
      techIot: [],
      techCicd: [],
      techExternalApi: [],
    });
    setProjectImageFiles([]);
    setArchitectureImageFile(null);
    setProjectImagePreviews([]);
    setArchitectureImagePreview(null);
  };

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  // 배열 필드 핸들러 - '+' 버튼 패턴
  const addArrayItem = (fieldName) => {
    setFormData(prev => ({
      ...prev,
      [fieldName]: [...prev[fieldName], '']
    }));
  };

  const updateArrayItem = (fieldName, index, value) => {
    setFormData(prev => ({
      ...prev,
      [fieldName]: prev[fieldName].map((item, i) => 
        i === index ? value : item
      )
    }));
  };

  const removeArrayItem = (fieldName, index) => {
    setFormData(prev => ({
      ...prev,
      [fieldName]: prev[fieldName].filter((_, i) => i !== index)
    }));
  };

  // Problem Solutions 추가/수정/삭제
  const addProblemSolution = () => {
    setFormData(prev => ({
      ...prev,
      problemSolutions: [...prev.problemSolutions, { problem: '', solution: '' }]
    }));
  };

  const updateProblemSolution = (index, field, value) => {
    setFormData(prev => ({
      ...prev,
      problemSolutions: prev.problemSolutions.map((item, i) => 
        i === index ? { ...item, [field]: value } : item
      )
    }));
  };

  const removeProblemSolution = (index) => {
    setFormData(prev => ({
      ...prev,
      problemSolutions: prev.problemSolutions.filter((_, i) => i !== index)
    }));
  };

  const handleProjectImagesChange = (e) => {
    const files = Array.from(e.target.files);
    setProjectImageFiles(files);
    
    // 미리보기 생성
    const previews = files.map(file => URL.createObjectURL(file));
    setProjectImagePreviews(previews);
  };

  const handleArchitectureImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setArchitectureImageFile(file);
      setArchitectureImagePreview(URL.createObjectURL(file));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.title.trim()) {
      showMessage('error', 'Project title is required.');
      return;
    }
    
    if (!formData.teamSize) {
      showMessage('error', 'Team size is required.');
      return;
    }
    
    if (!formData.startDate) {
      showMessage('error', 'Start date is required.');
      return;
    }
    
    if (!formData.endDate) {
      showMessage('error', 'End date is required.');
      return;
    }

    setSaving(true);
    try {
      // ProjectRequest DTO에 맞춰 데이터 준비
      const projectData = {
        name: formData.title,  // 백엔드 Entity 필드명은 'name'
        teamSize: parseInt(formData.teamSize),  // 필수 필드
        githubUrl: formData.githubUrl || null,
        startDate: formData.startDate,
        endDate: formData.endDate,  // 필수 필드
        status: formData.status,
        hasAward: formData.hasAward,
        awardName: formData.awardName || null,
        awardOrganization: formData.awardOrganization || null,
        // JSON 문자열로 변환
        summaries: JSON.stringify(formData.summaries),
        coreValues: JSON.stringify(formData.coreValues),
        mainFeatures: JSON.stringify(formData.mainFeatures),
        roles: JSON.stringify(formData.roles),
        problemSolutions: JSON.stringify(formData.problemSolutions),
        regrets: JSON.stringify(formData.regrets),
        improvements: JSON.stringify(formData.improvements),
        techDatabase: JSON.stringify(formData.techDatabase),
        techBackend: JSON.stringify(formData.techBackend),
        techFrontend: JSON.stringify(formData.techFrontend),
        techIot: JSON.stringify(formData.techIot),
        techCicd: JSON.stringify(formData.techCicd),
        techExternalApi: JSON.stringify(formData.techExternalApi),
      };

      const projectId = selectedProject?.id || selectedProject?.projectSeq;
      
      if (projectId) {
        // 수정
        await projectsAPI.update(
          projectId,
          projectData,
          projectImageFiles.length > 0 ? projectImageFiles : undefined,
          architectureImageFile || undefined
        );
        showMessage('success', 'Project updated successfully!');
        await loadProject(projectId);
      } else {
        // 생성
        const response = await projectsAPI.create(
          projectData,
          projectImageFiles,
          architectureImageFile
        );
        showMessage('success', 'Project created successfully!');
        const newProjectId = response.data.id || response.data.projectSeq;
        navigate(`/admin/projects/${newProjectId}`);
      }
      
      await loadData();
    } catch (error) {
      showMessage('error', 'Save failed: ' + (error.response?.data?.message || error.message));
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (projectId) => {
    if (!window.confirm('Are you sure you want to delete this project?')) return;

    try {
      await projectsAPI.delete(projectId);
      showMessage('success', 'Project deleted successfully!');
      await loadData();
      const currentProjectId = selectedProject?.id || selectedProject?.projectSeq;
      if (projectId === currentProjectId) {
        navigate('/admin/projects');
        setSelectedProject(null);
      }
    } catch (error) {
      showMessage('error', 'Delete failed: ' + error.message);
    }
  };

  const showMessage = (type, text) => {
    setMessage({ type, text });
    setTimeout(() => setMessage({ type: '', text: '' }), 4000);
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="admin-projects">
      <Navigation isAdmin />
      
      <div className="container">
        <section className="admin-header fade-in">
          <h1 className="admin-title">🚀 Projects Management</h1>
          <p className="admin-subtitle">Manage your projects</p>
        </section>

        {message.text && (
          <div className={`message ${message.type}`}>
            {message.text}
          </div>
        )}

        <section className="projects-section fade-in">
          <div className="section-header">
            <h2>Projects List</h2>
            <button className="btn" onClick={() => navigate('/admin/projects/new')}>
              + New Project
            </button>
          </div>
          
          <div className="projects-list">
            {projects.length === 0 ? (
              <p className="no-data">No projects available.</p>
            ) : (
              projects.map((project) => (
                <div
                  key={project.id || project.projectSeq}
                  className={`project-item ${
                    (selectedProject?.id && selectedProject.id === project.id) || 
                    (selectedProject?.projectSeq && selectedProject.projectSeq === project.projectSeq) 
                    ? 'active' : ''
                  }`}
                  onClick={() => navigate(`/admin/projects/${project.id || project.projectSeq}`)}
                >
                  <div className="project-item-content">
                    <h4>{project.name || project.title}</h4>
                    {project.hasAward && (
                      <span className="award-badge">🏆 {project.awardName}</span>
                    )}
                  </div>
                  <button
                    className="btn-delete"
                    onClick={(e) => {
                      e.stopPropagation();
                      handleDelete(project.id || project.projectSeq);
                    }}
                  >
                    Delete
                  </button>
                </div>
              ))
            )}
          </div>
        </section>

        {selectedProject && (
          <section className="project-form-section fade-in">
            <h2>{selectedProject.projectSeq ? '프로젝트 수정' : '새 프로젝트'}</h2>
            
            <div className="tabs">
              <button
                className={`tab ${activeTab === 'basic' ? 'active' : ''}`}
                onClick={() => setActiveTab('basic')}
              >
                기본 정보
              </button>
              <button
                className={`tab ${activeTab === 'content' ? 'active' : ''}`}
                onClick={() => setActiveTab('content')}
              >
                상세 내용
              </button>
              <button
                className={`tab ${activeTab === 'tech' ? 'active' : ''}`}
                onClick={() => setActiveTab('tech')}
              >
                기술스택
              </button>
              <button
                className={`tab ${activeTab === 'images' ? 'active' : ''}`}
                onClick={() => setActiveTab('images')}
              >
                이미지
              </button>
            </div>

            <form onSubmit={handleSubmit} className="project-form">
              {activeTab === 'basic' && (
                <div className="form-tab-content">
                  <div className="form-group">
                    <label>프로젝트 제목 *</label>
                    <input
                      type="text"
                      name="title"
                      value={formData.title}
                      onChange={handleInputChange}
                      placeholder="포트폴리오 웹사이트"
                      required
                    />
                  </div>

                  <div className="form-group">
                    <label>Project Status *</label>
                    <select
                      name="status"
                      value={formData.status}
                      onChange={handleInputChange}
                      required
                    >
                      <option value="진행중">In Progress</option>
                      <option value="완료">Completed</option>
                    </select>
                  </div>

                  <div className="form-row">
                    <div className="form-group">
                      <label>Start Date *</label>
                      <input
                        type="date"
                        name="startDate"
                        value={formData.startDate}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                    <div className="form-group">
                      <label>End Date *</label>
                      <input
                        type="date"
                        name="endDate"
                        value={formData.endDate}
                        onChange={handleInputChange}
                        required
                      />
                    </div>
                  </div>

                  <div className="form-group">
                    <label>Team Size *</label>
                    <input
                      type="number"
                      name="teamSize"
                      value={formData.teamSize}
                      onChange={handleInputChange}
                      placeholder="4"
                      min="1"
                      required
                    />
                  </div>

                  <div className="form-group">
                    <label>GitHub URL</label>
                    <input
                      type="url"
                      name="githubUrl"
                      value={formData.githubUrl}
                      onChange={handleInputChange}
                      placeholder="https://github.com/username/repo"
                    />
                  </div>

                  <div className="form-group checkbox-group">
                    <label>
                      <input
                        type="checkbox"
                        name="hasAward"
                        checked={formData.hasAward}
                        onChange={handleInputChange}
                      />
                      Has Award
                    </label>
                  </div>

                  {formData.hasAward && (
                    <>
                      <div className="form-group">
                        <label>Award Name</label>
                        <input
                          type="text"
                          name="awardName"
                          value={formData.awardName}
                          onChange={handleInputChange}
                          placeholder="Best Prize"
                        />
                      </div>
                      <div className="form-group">
                        <label>Award Organization</label>
                        <input
                          type="text"
                          name="awardOrganization"
                          value={formData.awardOrganization}
                          onChange={handleInputChange}
                          placeholder="University Name"
                        />
                      </div>
                    </>
                  )}
                </div>
              )}

              {activeTab === 'content' && (
                <div className="form-tab-content">
                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>프로젝트 요약</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('summaries')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">프로젝트의 핵심 내용을 간결하게 설명하세요</p>
                    
                    <div className="array-items-list">
                      {formData.summaries.length === 0 ? (
                        <div className="empty-state">
                          <p>요약이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.summaries.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('summaries', index, e.target.value)}
                              placeholder="React와 Spring Boot를 사용한 풀스택 프로젝트"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('summaries', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>핵심 가치</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('coreValues')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">프로젝트에서 중요하게 생각한 가치를 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.coreValues.length === 0 ? (
                        <div className="empty-state">
                          <p>핵심 가치가 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.coreValues.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('coreValues', index, e.target.value)}
                              placeholder="사용자 경험 최우선"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('coreValues', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>주요 기능</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('mainFeatures')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">프로젝트의 주요 기능을 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.mainFeatures.length === 0 ? (
                        <div className="empty-state">
                          <p>주요 기능이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.mainFeatures.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('mainFeatures', index, e.target.value)}
                              placeholder="실시간 데이터 업데이트"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('mainFeatures', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>나의 역할</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('roles')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">프로젝트에서 담당한 역할을 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.roles.length === 0 ? (
                        <div className="empty-state">
                          <p>역할이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.roles.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('roles', index, e.target.value)}
                              placeholder="프론트엔드 개발 담당"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('roles', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>문제 해결 (Problem & Solution)</label>
                      <button type="button" className="btn-add-small" onClick={addProblemSolution}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">문제와 해결 방법을 쌍으로 입력하세요</p>
                    
                    <div className="problem-solutions-list">
                      {formData.problemSolutions.length === 0 ? (
                        <div className="empty-state">
                          <p>문제-해결 쌍이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.problemSolutions.map((item, index) => (
                          <div key={index} className="problem-solution-pair">
                            <div className="pair-number">#{index + 1}</div>
                            <div className="pair-content">
                              <div className="pair-field">
                                <label className="pair-label error-label">❌ 문제</label>
                                <textarea
                                  rows="2"
                                  value={item.problem || ''}
                                  onChange={(e) => updateProblemSolution(index, 'problem', e.target.value)}
                                  placeholder="과도한 연관관계 매핑으로 인한 N+1 쿼리 발생"
                                />
                              </div>
                              <div className="pair-field">
                                <label className="pair-label success-label">✅ 해결</label>
                                <textarea
                                  rows="2"
                                  value={item.solution || ''}
                                  onChange={(e) => updateProblemSolution(index, 'solution', e.target.value)}
                                  placeholder="필요한 테이블만 Lazy Loading, Batch Size 최적화로 N+1 쿼리 해결"
                                />
                              </div>
                            </div>
                            <button 
                              type="button" 
                              className="btn-remove-pair" 
                              onClick={() => removeProblemSolution(index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>아쉬운 점</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('regrets')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">프로젝트에서 아쉬웠던 점을 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.regrets.length === 0 ? (
                        <div className="empty-state">
                          <p>아쉬운 점이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.regrets.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('regrets', index, e.target.value)}
                              placeholder="테스트 코드 작성 부족"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('regrets', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>개선 방향</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('improvements')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">향후 개선하고 싶은 방향을 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.improvements.length === 0 ? (
                        <div className="empty-state">
                          <p>개선 방향이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.improvements.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('improvements', index, e.target.value)}
                              placeholder="E2E 테스트 도입"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('improvements', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>
                </div>
              )}

              {activeTab === 'tech' && (
                <div className="form-tab-content">
                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>🗄️ Database</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('techDatabase')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">사용한 데이터베이스 기술을 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.techDatabase.length === 0 ? (
                        <div className="empty-state">
                          <p>데이터베이스 기술이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.techDatabase.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('techDatabase', index, e.target.value)}
                              placeholder="MySQL"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('techDatabase', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>⚙️ Backend</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('techBackend')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">사용한 백엔드 기술을 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.techBackend.length === 0 ? (
                        <div className="empty-state">
                          <p>백엔드 기술이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.techBackend.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('techBackend', index, e.target.value)}
                              placeholder="Spring Boot"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('techBackend', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>🎨 Frontend</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('techFrontend')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">사용한 프론트엔드 기술을 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.techFrontend.length === 0 ? (
                        <div className="empty-state">
                          <p>프론트엔드 기술이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.techFrontend.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('techFrontend', index, e.target.value)}
                              placeholder="React"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('techFrontend', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>🤖 IoT</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('techIot')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">사용한 IoT 기술을 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.techIot.length === 0 ? (
                        <div className="empty-state">
                          <p>IoT 기술이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.techIot.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('techIot', index, e.target.value)}
                              placeholder="Arduino"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('techIot', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>🔄 CI/CD</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('techCicd')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">사용한 CI/CD 기술을 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.techCicd.length === 0 ? (
                        <div className="empty-state">
                          <p>CI/CD 기술이 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.techCicd.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('techCicd', index, e.target.value)}
                              placeholder="GitHub Actions"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('techCicd', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>

                  <div className="form-group full-width">
                    <div className="section-header-inline">
                      <label>🔌 External API</label>
                      <button type="button" className="btn-add-small" onClick={() => addArrayItem('techExternalApi')}>
                        + 추가
                      </button>
                    </div>
                    <p className="helper-text">사용한 외부 API를 입력하세요</p>
                    
                    <div className="array-items-list">
                      {formData.techExternalApi.length === 0 ? (
                        <div className="empty-state">
                          <p>외부 API가 없습니다. "추가" 버튼을 클릭하세요.</p>
                        </div>
                      ) : (
                        formData.techExternalApi.map((item, index) => (
                          <div key={index} className="array-item">
                            <span className="item-number">#{index + 1}</span>
                            <input
                              type="text"
                              value={item}
                              onChange={(e) => updateArrayItem('techExternalApi', index, e.target.value)}
                              placeholder="Kakao Map API"
                            />
                            <button 
                              type="button" 
                              className="btn-delete" 
                              onClick={() => removeArrayItem('techExternalApi', index)}
                              title="삭제"
                            >
                              ✕
                            </button>
                          </div>
                        ))
                      )}
                    </div>
                  </div>
                </div>
              )}

              {activeTab === 'images' && (
                <div className="form-tab-content">
                  <div className="form-group">
                    <label>프로젝트 이미지 (여러 개 가능)</label>
                    <input
                      type="file"
                      accept="image/*"
                      multiple
                      onChange={handleProjectImagesChange}
                    />
                    {projectImagePreviews.length > 0 && (
                      <div className="image-previews">
                        {projectImagePreviews.map((preview, index) => (
                          <div key={index} className="image-preview">
                            <img src={preview} alt={`Preview ${index + 1}`} />
                            <p>이미지 {index + 1}</p>
                          </div>
                        ))}
                      </div>
                    )}
                  </div>

                  <div className="form-group">
                    <label>시스템 아키텍처 이미지</label>
                    <input
                      type="file"
                      accept="image/*"
                      onChange={handleArchitectureImageChange}
                    />
                    {architectureImagePreview && (
                      <div className="image-preview">
                        <img src={architectureImagePreview} alt="Architecture Preview" />
                      </div>
                    )}
                  </div>
                </div>
              )}

              <div className="form-actions">
                <button type="submit" className="btn btn-primary" disabled={saving}>
                  {saving ? '저장 중...' : '저장'}
                </button>
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => {
                    navigate('/admin/projects');
                    setSelectedProject(null);
                  }}
                >
                  취소
                </button>
              </div>
            </form>
          </section>
        )}
      </div>
    </div>
  );
}

export default AdminProjects;

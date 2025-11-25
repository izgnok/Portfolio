import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import Navigation from '../../components/Navigation';
import Loading from '../../components/Loading';
import { profileAPI, keywordsAPI } from '../../services/api';
import './AdminProfile.css';

function AdminProfile() {
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });
  const [profile, setProfile] = useState(null);
  const [keywords, setKeywords] = useState([]);
  const [newKeyword, setNewKeyword] = useState('');
  const [imagePreview, setImagePreview] = useState(null);
  const [imageFile, setImageFile] = useState(null);
  const [draggedKeyword, setDraggedKeyword] = useState(null);

  const { register, handleSubmit, reset, formState: { errors } } = useForm();

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [profileData, keywordsData] = await Promise.all([
        profileAPI.get(),
        keywordsAPI.getAll()
      ]);

      setProfile(profileData.data);
      setKeywords(keywordsData.data || []);
      setImagePreview(profileData.data?.profileImage); // Base64 이미지
      
      // 폼 초기값 설정
      const formData = {
        ...profileData.data,
        birthDate: profileData.data?.birthDate 
          ? new Date(profileData.data.birthDate).toISOString().split('T')[0] 
          : ''
      };
      reset(formData);
    } catch (error) {
      showMessage('error', 'Failed to load data: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  const onSubmit = async (data) => {
    setSaving(true);
    setMessage({ type: '', text: '' });

    try {
      // ProfileRequest 객체 생성
      const profileData = {
        name: data.name,
        nameEn: data.nameEn,
        birthDate: data.birthDate,
        phone: data.phone || null,
        email: data.email || null,
        github: data.github || null,
      };

      // API 호출 (profileAPI.save가 @RequestPart 처리)
      await profileAPI.save(profileData, imageFile);

      showMessage('success', 'Profile saved successfully! ✅');
      setImageFile(null);
      loadData();
    } catch (error) {
      showMessage('error', 'Failed to save: ' + error.message);
    } finally {
      setSaving(false);
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setImageFile(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagePreview(reader.result);
      };
      reader.readAsDataURL(file);
    }
  };

  const addKeyword = async () => {
    if (!newKeyword.trim()) {
      showMessage('error', 'Please enter a keyword');
      return;
    }

    try {
      // 자동으로 마지막 순서 + 1로 설정
      const maxOrder = keywords.length > 0 
        ? Math.max(...keywords.map(k => k.displayOrder)) 
        : 0;
      
      await keywordsAPI.create({ 
        keyword: newKeyword, 
        displayOrder: maxOrder + 1
      });
      setNewKeyword('');
      loadData();
      showMessage('success', 'Keyword added successfully!');
    } catch (error) {
      showMessage('error', 'Failed to add keyword: ' + error.message);
    }
  };

  const deleteKeyword = async (id) => {
    if (!window.confirm('Are you sure you want to delete this keyword?')) return;

    try {
      await keywordsAPI.delete(id);
      loadData();
      showMessage('success', 'Keyword deleted successfully!');
    } catch (error) {
      showMessage('error', 'Failed to delete keyword: ' + error.message);
    }
  };

  // Drag & Drop handlers for keywords
  const handleKeywordDragStart = (e, keyword) => {
    setDraggedKeyword(keyword);
    e.dataTransfer.effectAllowed = 'move';
  };

  const handleKeywordDragOver = (e) => {
    e.preventDefault();
    e.dataTransfer.dropEffect = 'move';
  };

  const handleKeywordDrop = async (e, targetKeyword) => {
    e.preventDefault();
    
    if (!draggedKeyword || draggedKeyword.id === targetKeyword.id) {
      setDraggedKeyword(null);
      return;
    }

    const sortedKeywords = [...keywords].sort((a, b) => a.displayOrder - b.displayOrder);
    const draggedIndex = sortedKeywords.findIndex(k => k.id === draggedKeyword.id);
    const targetIndex = sortedKeywords.findIndex(k => k.id === targetKeyword.id);

    if (draggedIndex === -1 || targetIndex === -1) {
      setDraggedKeyword(null);
      return;
    }

    // Reorder keywords
    const newKeywords = [...sortedKeywords];
    const [removed] = newKeywords.splice(draggedIndex, 1);
    newKeywords.splice(targetIndex, 0, removed);

    // Update displayOrder for all keywords
    const updateRequest = {
      keywords: newKeywords.map((kw, index) => ({
        id: kw.id,
        displayOrder: index + 1
      }))
    };

    try {
      await keywordsAPI.updateOrder(updateRequest);
      await loadData();
      showMessage('success', 'Keyword order updated successfully!');
    } catch (error) {
      if (error.response?.status === 401 || error.response?.status === 403) {
        showMessage('error', 'Authentication required. Please login again.');
      } else if (error.response?.status === 405) {
        showMessage('error', 'Method not allowed. Please check backend CORS settings.');
      } else {
        showMessage('error', 'Failed to update keyword order: ' + (error.response?.data?.message || error.message));
      }
    }

    setDraggedKeyword(null);
  };

  const showMessage = (type, text) => {
    setMessage({ type, text });
    setTimeout(() => setMessage({ type: '', text: '' }), 3000);
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="admin-profile">
      <Navigation isAdmin />
      
      <div className="container">
        {/* Header */}
        <section className="admin-header fade-in">
          <h1 className="admin-title">👤 Profile Management</h1>
          <p className="admin-subtitle">Manage your basic information and profile image</p>
        </section>

        {/* Message */}
        {message.text && (
          <div className={`message ${message.type}`}>
            {message.text}
          </div>
        )}

        {/* Profile Form */}
        <form onSubmit={handleSubmit(onSubmit)} className="profile-form fade-in">
          {/* Profile Image */}
          <div className="form-section">
            <h2 className="form-section-title">📷 Profile Image</h2>
            <div className="image-upload-container">
              <div className="image-preview">
                {imagePreview ? (
                  <img src={imagePreview} alt="Profile" />
                ) : (
                  <div className="no-image">No Image</div>
                )}
                <label htmlFor="image-upload" className="image-upload-button">
                  📸
                </label>
                <input
                  id="image-upload"
                  type="file"
                  accept="image/*"
                  onChange={handleImageChange}
                  style={{ display: 'none' }}
                />
              </div>
              <p className="image-hint">Click to upload image</p>
            </div>
          </div>

          {/* Basic Information */}
          <div className="form-section">
            <h2 className="form-section-title">📝 Basic Information</h2>
            <div className="form-grid">
              <div className="form-group">
                <label>Name *</label>
                <input
                  type="text"
                  {...register('name', { required: 'Name is required' })}
                  className={errors.name ? 'error' : ''}
                  placeholder="Hong Gildong"
                />
                {errors.name && <span className="error-text">{errors.name.message}</span>}
              </div>

              <div className="form-group">
                <label>Name (English) *</label>
                <input
                  type="text"
                  {...register('nameEn', { required: 'English name is required' })}
                  className={errors.nameEn ? 'error' : ''}
                  placeholder="Hong Gildong"
                />
                {errors.nameEn && <span className="error-text">{errors.nameEn.message}</span>}
              </div>

              <div className="form-group">
                <label>Date of Birth *</label>
                <input
                  type="date"
                  {...register('birthDate', { required: 'Date of birth is required' })}
                  className={errors.birthDate ? 'error' : ''}
                />
                {errors.birthDate && <span className="error-text">{errors.birthDate.message}</span>}
              </div>

              <div className="form-group">
                <label>Phone</label>
                <input
                  type="tel"
                  {...register('phone')}
                  placeholder="010-1234-5678"
                />
              </div>

              <div className="form-group">
                <label>Email</label>
                <input
                  type="email"
                  {...register('email', { 
                    pattern: {
                      value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                      message: 'Invalid email format'
                    }
                  })}
                  className={errors.email ? 'error' : ''}
                  placeholder="email@example.com"
                />
                {errors.email && <span className="error-text">{errors.email.message}</span>}
              </div>

              <div className="form-group">
                <label>GitHub</label>
                <input
                  type="url"
                  {...register('github')}
                  placeholder="https://github.com/username"
                />
              </div>
            </div>
          </div>

          {/* Save Button */}
          <div className="form-actions">
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? 'Saving...' : '💾 Save Profile'}
            </button>
          </div>
        </form>

        {/* Keywords Management */}
        <section className="keywords-section fade-in">
          <h2 className="section-title">🏷️ Keywords Management</h2>
          
          <div className="keyword-input-container">
            <input
              type="text"
              value={newKeyword}
              onChange={(e) => setNewKeyword(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && addKeyword()}
              placeholder="Enter new keyword..."
              className="keyword-input"
            />
            <button onClick={addKeyword} className="btn btn-add">
              ➕ Add
            </button>
          </div>

          <div className="keywords-list">
            {keywords.sort((a, b) => a.displayOrder - b.displayOrder).map((kw) => (
              <div 
                key={kw.id} 
                className={`keyword-item ${draggedKeyword?.id === kw.id ? 'dragging' : ''}`}
                draggable={true}
                onDragStart={(e) => handleKeywordDragStart(e, kw)}
                onDragOver={handleKeywordDragOver}
                onDrop={(e) => handleKeywordDrop(e, kw)}
                onDragEnd={() => setDraggedKeyword(null)}
              >
                <span className="keyword-drag-handle" draggable={false}>⋮⋮</span>
                <span className="keyword-order" draggable={false}>#{kw.displayOrder}</span>
                <span className="keyword-content" draggable={false}>{kw.keyword}</span>
                <button 
                  onClick={(e) => {
                    e.stopPropagation();
                    deleteKeyword(kw.id);
                  }} 
                  className="btn-delete-keyword"
                  title="Delete"
                  draggable={false}
                >
                  ✕
                </button>
              </div>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}

export default AdminProfile;

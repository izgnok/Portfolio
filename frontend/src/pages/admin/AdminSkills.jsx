import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import Navigation from '../../components/Navigation';
import Loading from '../../components/Loading';
import { skillsAPI } from '../../services/api';
import './AdminSkills.css';

function AdminSkills() {
  const [loading, setLoading] = useState(true);
  const [skills, setSkills] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('Frontend');
  const [message, setMessage] = useState({ type: '', text: '' });
  const [showModal, setShowModal] = useState(false);
  const [editingSkill, setEditingSkill] = useState(null);
  const { register, handleSubmit, reset, formState: { errors } } = useForm();

  // Fixed category order
  const categories = ['Frontend', 'Backend', 'IoT', 'Database', 'CI/CD', 'Others'];

  useEffect(() => {
    loadSkills();
  }, []);

  const loadSkills = async () => {
    try {
      const response = await skillsAPI.getAll();
      setSkills(response.data || []);
    } catch (error) {
      console.error('Failed to load skills:', error);
      showMessage('error', 'Failed to load skills');
    } finally {
      setLoading(false);
    }
  };

  const filteredSkills = skills.filter(skill => skill.category === selectedCategory);

  const onSubmit = async (data) => {
    try {
      const skillData = {
        name: data.name,
        level: parseInt(data.level),
        category: selectedCategory,
      };

      if (editingSkill) {
        await skillsAPI.update(editingSkill.id, skillData);
        showMessage('success', 'Skill updated successfully!');
      } else {
        await skillsAPI.create(skillData);
        showMessage('success', 'Skill added successfully!');
      }

      loadSkills();
      handleCloseModal();
    } catch (error) {
      showMessage('error', 'Failed to save: ' + error.message);
    }
  };

  const handleEdit = (skill) => {
    setEditingSkill(skill);
    reset({
      name: skill.name,
      level: skill.level,
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this skill?')) return;

    try {
      await skillsAPI.delete(id);
      showMessage('success', 'Skill deleted successfully!');
      loadSkills();
    } catch (error) {
      showMessage('error', 'Failed to delete: ' + error.message);
    }
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditingSkill(null);
    reset();
  };

  const showMessage = (type, text) => {
    setMessage({ type, text });
    setTimeout(() => setMessage({ type: '', text: '' }), 3000);
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="admin-skills">
      <Navigation isAdmin />
      
      <div className="container">
        {/* Header */}
        <section className="admin-header fade-in">
          <h1 className="admin-title">💻 Skills Management</h1>
          <p className="admin-subtitle">Manage your technical skills</p>
        </section>

        {/* Message */}
        {message.text && (
          <div className={`message ${message.type}`}>
            {message.text}
          </div>
        )}

        {/* Category Tabs */}
        <section className="skills-section fade-in">
          <div className="category-tabs">
            {categories.map(cat => (
              <button
                key={cat}
                className={`category-tab ${selectedCategory === cat ? 'active' : ''}`}
                onClick={() => setSelectedCategory(cat)}
              >
                {cat}
              </button>
            ))}
          </div>

          <div className="section-header">
            <h2>{selectedCategory} Skills ({filteredSkills.length})</h2>
            <button className="btn btn-primary" onClick={() => setShowModal(true)}>
              + Add Skill
            </button>
          </div>

          {/* Skills List */}
          <div className="skills-list">
            {filteredSkills.length === 0 ? (
              <div className="no-data">
                <p>😢 No skills in this category</p>
                <button className="btn btn-primary" onClick={() => setShowModal(true)}>
                  + Add First Skill
                </button>
              </div>
            ) : (
              filteredSkills.map((skill) => (
                <div key={skill.id} className="skill-item">
                  <div className="skill-info">
                    <h3 className="skill-name">{skill.name}</h3>
                    <div className="skill-level">
                      {Array.from({ length: 5 }).map((_, i) => (
                        <div
                          key={i}
                          className={`level-bar ${i < skill.level ? 'active' : ''}`}
                        />
                      ))}
                      <span className="level-text">Level {skill.level}</span>
                    </div>
                  </div>
                  <div className="skill-actions">
                    <button className="btn-edit" onClick={() => handleEdit(skill)}>
                      Edit
                    </button>
                    <button className="btn-delete" onClick={() => handleDelete(skill.id)}>
                      Delete
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        </section>

        {/* Modal */}
        {showModal && (
          <div className="modal-overlay" onClick={handleCloseModal}>
            <div className="modal-container" onClick={(e) => e.stopPropagation()}>
              <button className="modal-close" onClick={handleCloseModal}>×</button>
              
              <div className="modal-header">
                <h2>{editingSkill ? 'Edit Skill' : 'Add Skill'}</h2>
              </div>

              <form onSubmit={handleSubmit(onSubmit)} className="skill-form">
                <div className="form-group">
                  <label>Skill Name *</label>
                  <input
                    type="text"
                    {...register('name', { required: 'Skill name is required' })}
                    className={errors.name ? 'error' : ''}
                    placeholder="React"
                  />
                  {errors.name && <span className="error-text">{errors.name.message}</span>}
                </div>

                <div className="form-group">
                  <label>Level (1-5) *</label>
                  <input
                    type="number"
                    {...register('level', { 
                      required: 'Level is required',
                      min: { value: 1, message: 'Level must be at least 1' },
                      max: { value: 5, message: 'Level must be at most 5' }
                    })}
                    className={errors.level ? 'error' : ''}
                    placeholder="5"
                    min="1"
                    max="5"
                  />
                  {errors.level && <span className="error-text">{errors.level.message}</span>}
                </div>

                <div className="form-group">
                  <label>Category</label>
                  <input
                    type="text"
                    value={selectedCategory}
                    disabled
                    className="disabled-input"
                  />
                </div>

                <div className="modal-actions">
                  <button type="button" className="btn-cancel" onClick={handleCloseModal}>
                    Cancel
                  </button>
                  <button type="submit" className="btn-submit">
                    {editingSkill ? 'Update' : 'Add'}
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default AdminSkills;

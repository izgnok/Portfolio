import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import Navigation from '../../components/Navigation';
import Loading from '../../components/Loading';
import { educationsAPI } from '../../services/api';
import { formatDate } from '../../utils/dateFormat';
import './AdminEducation.css';

function AdminEducation() {
  const [loading, setLoading] = useState(true);
  const [educations, setEducations] = useState([]);
  const [message, setMessage] = useState({ type: '', text: '' });
  const [showModal, setShowModal] = useState(false);
  const [editingEducation, setEditingEducation] = useState(null);
  const { register, handleSubmit, reset, formState: { errors } } = useForm();

  const statusOptions = ['Expected Graduation', 'Graduated', 'In Progress', 'Completed'];

  useEffect(() => {
    loadEducations();
  }, []);

  const loadEducations = async () => {
    try {
      const response = await educationsAPI.getAll();
      setEducations(response.data || []);
    } catch (error) {
      console.error('Failed to load educations:', error);
      showMessage('error', 'Failed to load educations');
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = () => {
    setEditingEducation(null);
    reset({
      name: '',
      startDate: '',
      endDate: '',
      status: 'Graduated',
      gpa: ''
    });
    setShowModal(true);
  };

  const handleEdit = (education) => {
    setEditingEducation(education);
    reset({
      name: education.name,
      startDate: education.startDate,
      endDate: education.endDate,
      status: education.status,
      gpa: education.gpa || ''
    });
    setShowModal(true);
  };

  const onSubmit = async (data) => {
    try {
      // gpa가 빈 문자열이면 null로 변환
      const submitData = {
        ...data,
        gpa: data.gpa || null
      };

      if (editingEducation) {
        await educationsAPI.update(editingEducation.id, submitData);
        showMessage('success', 'Education updated successfully!');
      } else {
        await educationsAPI.create(submitData);
        showMessage('success', 'Education added successfully!');
      }
      loadEducations();
      setShowModal(false);
    } catch (error) {
      showMessage('error', 'Failed to save: ' + error.message);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this education?')) return;

    try {
      await educationsAPI.delete(id);
      showMessage('success', 'Education deleted successfully!');
      loadEducations();
    } catch (error) {
      showMessage('error', 'Failed to delete: ' + error.message);
    }
  };

  const showMessage = (type, text) => {
    setMessage({ type, text });
    setTimeout(() => setMessage({ type: '', text: '' }), 3000);
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="admin-education">
      <Navigation isAdmin />
      
      <div className="container">
        <section className="admin-header fade-in">
          <h1 className="admin-title">🎓 Education Management</h1>
          <p className="admin-subtitle">Manage your education information</p>
        </section>

        {message.text && (
          <div className={`message ${message.type}`}>
            {message.text}
          </div>
        )}

        <section className="education-section fade-in">
          <div className="section-header">
            <h2>Education List ({educations.length})</h2>
            <button className="btn-add" onClick={handleAdd}>
              <span className="btn-icon">+</span>
              Add Education
            </button>
          </div>

          <div className="education-list">
            {educations.length === 0 ? (
              <div className="no-data">
                <p>😢 No education information available</p>
                <button className="btn-add" onClick={handleAdd}>
                  <span className="btn-icon">+</span>
                  Add Your First Education
                </button>
              </div>
            ) : (
              educations.map((edu) => (
                <div key={edu.id} className="education-card">
                  <div className="education-info">
                    <h3 className="education-name">{edu.name}</h3>
                    <p className="education-date">
                      {formatDate(edu.startDate)} ~ {' '}
                      {edu.endDate ? formatDate(edu.endDate) : 'Present'}
                    </p>
                    <p className="education-status">{edu.status}</p>
                    {edu.gpa && <p className="education-gpa">GPA: {edu.gpa}</p>}
                  </div>
                  <div className="education-actions">
                    <button className="btn-edit" onClick={() => handleEdit(edu)}>
                      Edit
                    </button>
                    <button className="btn-delete" onClick={() => handleDelete(edu.id)}>
                      Delete
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        </section>

        {/* 모달 */}
        {showModal && (
          <div className="modal-overlay" onClick={() => setShowModal(false)}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <h2>{editingEducation ? 'Edit Education' : 'Add Education'}</h2>
              <form onSubmit={handleSubmit(onSubmit)}>
                <div className="form-group">
                  <label>School/Institution Name *</label>
                  <input
                    type="text"
                    {...register('name', { required: 'School/Institution name is required' })}
                    className={errors.name ? 'error' : ''}
                    placeholder="Seoul National University"
                  />
                  {errors.name && <span className="error-text">{errors.name.message}</span>}
                </div>

                <div className="form-group">
                  <label>Start Date *</label>
                  <input
                    type="date"
                    {...register('startDate', { required: 'Start date is required' })}
                    className={errors.startDate ? 'error' : ''}
                  />
                  {errors.startDate && <span className="error-text">{errors.startDate.message}</span>}
                </div>

                <div className="form-group">
                  <label>End Date *</label>
                  <input
                    type="date"
                    {...register('endDate', { required: 'End date is required' })}
                    className={errors.endDate ? 'error' : ''}
                  />
                  {errors.endDate && <span className="error-text">{errors.endDate.message}</span>}
                </div>

                <div className="form-group">
                  <label>Status *</label>
                  <select
                    {...register('status', { required: 'Status is required' })}
                    className={errors.status ? 'error' : ''}
                  >
                    {statusOptions.map(status => (
                      <option key={status} value={status}>{status}</option>
                    ))}
                  </select>
                  {errors.status && <span className="error-text">{errors.status.message}</span>}
                </div>

                <div className="form-group">
                  <label>GPA (Optional)</label>
                  <input
                    type="text"
                    {...register('gpa')}
                    placeholder="4.5/4.5"
                  />
                </div>

                <div className="modal-actions">
                  <button type="button" className="btn-cancel" onClick={() => setShowModal(false)}>
                    Cancel
                  </button>
                  <button type="submit" className="btn btn-primary">
                    {editingEducation ? 'Update' : 'Add'}
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

export default AdminEducation;

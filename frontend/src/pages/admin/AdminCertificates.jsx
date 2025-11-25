import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import Navigation from '../../components/Navigation';
import Loading from '../../components/Loading';
import { certificatesAPI } from '../../services/api';
import { formatDate } from '../../utils/dateFormat';
import './AdminCertificates.css';

function AdminCertificates() {
  const [loading, setLoading] = useState(true);
  const [certificates, setCertificates] = useState([]);
  const [message, setMessage] = useState({ type: '', text: '' });
  const [showModal, setShowModal] = useState(false);
  const [editingCertificate, setEditingCertificate] = useState(null);
  const { register, handleSubmit, reset, formState: { errors } } = useForm();

  useEffect(() => {
    loadCertificates();
  }, []);

  const loadCertificates = async () => {
    try {
      const response = await certificatesAPI.getAll();
      // Sort by issue date descending (most recent first)
      const sorted = (response.data || []).sort((a, b) => {
        return new Date(b.issueDate) - new Date(a.issueDate);
      });
      setCertificates(sorted);
    } catch (error) {
      showMessage('error', 'Failed to load certificates');
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = () => {
    setEditingCertificate(null);
    reset({
      name: '',
      issuer: '',
      issueDate: '',
      certificateNumber: ''
    });
    setShowModal(true);
  };

  const handleEdit = (certificate) => {
    setEditingCertificate(certificate);
    reset({
      name: certificate.name,
      issuer: certificate.issuer,
      issueDate: certificate.issueDate,
      certificateNumber: certificate.certificateNumber || ''
    });
    setShowModal(true);
  };

  const onSubmit = async (data) => {
    try {
      const certificateData = {
        name: data.name,
        issuer: data.issuer,
        issueDate: data.issueDate,
        certificateNumber: data.certificateNumber?.trim() || null
      };

      if (editingCertificate) {
        await certificatesAPI.update(editingCertificate.id, certificateData);
        showMessage('success', 'Certificate updated successfully!');
      } else {
        await certificatesAPI.create(certificateData);
        showMessage('success', 'Certificate added successfully!');
      }

      loadCertificates();
      setShowModal(false);
    } catch (error) {
      showMessage('error', 'Failed to save: ' + error.message);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this certificate?')) return;

    try {
      await certificatesAPI.delete(id);
      showMessage('success', 'Certificate deleted successfully!');
      loadCertificates();
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
    <div className="admin-certificates">
      <Navigation isAdmin />
      
      <div className="container">
        {/* Header */}
        <section className="admin-header fade-in">
          <h1 className="admin-title">📜 Certificates Management</h1>
          <p className="admin-subtitle">Manage your professional certificates (sorted by issue date)</p>
        </section>

        {/* Message */}
        {message.text && (
          <div className={`message ${message.type}`}>
            {message.text}
          </div>
        )}

        {/* Certificates Section */}
        <section className="certificates-section fade-in">
          <div className="section-header">
            <h2>Certificates List ({certificates.length})</h2>
            <button className="btn-add" onClick={handleAdd}>
              <span className="btn-icon">+</span>
              Add Certificate
            </button>
          </div>

          <div className="certificates-grid">
            {certificates.length === 0 ? (
              <div className="no-data">
                <p>😢 No certificates available</p>
                <button className="btn-add" onClick={handleAdd}>
                  <span className="btn-icon">+</span>
                  Add Your First Certificate
                </button>
              </div>
            ) : (
              certificates.map((cert) => (
                <div key={cert.id} className="certificate-card">
                  <h3>{cert.name}</h3>
                  <p className="issuer">{cert.issuer}</p>
                  <p className="issue-date">
                    Issued: {formatDate(cert.issueDate)}
                  </p>
                  {cert.certificateNumber && (
                    <p className="certificate-number">Number: {cert.certificateNumber}</p>
                  )}
                  <div className="card-actions">
                    <button className="btn-edit" onClick={() => handleEdit(cert)}>
                      Edit
                    </button>
                    <button className="btn-delete" onClick={() => handleDelete(cert.id)}>
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
          <div className="modal-overlay" onClick={() => setShowModal(false)}>
            <div className="modal-container" onClick={(e) => e.stopPropagation()}>
              <button className="modal-close" onClick={() => setShowModal(false)}>×</button>
              
              <div className="modal-header">
                <h2>{editingCertificate ? 'Edit Certificate' : 'Add Certificate'}</h2>
              </div>

              <form onSubmit={handleSubmit(onSubmit)} className="certificate-form">
                <div className="form-group full-width">
                  <label>Certificate Name *</label>
                  <input
                    type="text"
                    {...register('name', { required: 'Certificate name is required' })}
                    className={errors.name ? 'error' : ''}
                    placeholder="AWS Certified Solutions Architect"
                  />
                  {errors.name && <span className="error-text">{errors.name.message}</span>}
                </div>

                <div className="form-group full-width">
                  <label>Issuing Organization *</label>
                  <input
                    type="text"
                    {...register('issuer', { required: 'Issuing organization is required' })}
                    className={errors.issuer ? 'error' : ''}
                    placeholder="Amazon Web Services"
                  />
                  {errors.issuer && <span className="error-text">{errors.issuer.message}</span>}
                </div>

                <div className="form-group">
                  <label>Issue Date *</label>
                  <input
                    type="date"
                    {...register('issueDate', { required: 'Issue date is required' })}
                    className={errors.issueDate ? 'error' : ''}
                  />
                  {errors.issueDate && <span className="error-text">{errors.issueDate.message}</span>}
                </div>

                <div className="form-group full-width">
                  <label>Certificate Number (Optional)</label>
                  <input
                    type="text"
                    {...register('certificateNumber')}
                    placeholder="ABC-123-XYZ-456"
                  />
                </div>

                <div className="modal-actions">
                  <button type="button" className="btn-cancel" onClick={() => setShowModal(false)}>
                    Cancel
                  </button>
                  <button type="submit" className="btn-submit">
                    {editingCertificate ? 'Update' : 'Add'}
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

export default AdminCertificates;

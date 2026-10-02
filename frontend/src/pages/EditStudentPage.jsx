import { useState, useEffect } from 'react'
import { useParams, useNavigate, Link } from 'react-router-dom'
import { studentApi } from '../api/studentApi.js'
import Spinner from '../components/Spinner.jsx'
import ErrorMessage from '../components/ErrorMessage.jsx'

function EditStudentPage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [form, setForm]     = useState(null)
  const [errors, setErrors] = useState({})
  const [error, setError]   = useState(null)
  const [loading, setLoading] = useState(true)
  const [saving, setSaving]   = useState(false)

  useEffect(() => {
    studentApi.getById(id)
      .then(data => setForm({
        name: data.name,
        email: data.email,
        phone: data.phone || '',
        rollNumber: data.rollNumber,
        course: data.course,
        semester: String(data.semester)
      }))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false))
  }, [id])

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
    if (errors[name]) setErrors(prev => ({ ...prev, [name]: null }))
  }

  function handleSubmit(e) {
    e.preventDefault()
    setSaving(true)
    setError(null)
    const payload = { ...form, semester: parseInt(form.semester, 10) }
    studentApi.update(id, payload)
      .then(() => navigate(`/students/${id}`))
      .catch(err => setError(err.message))
      .finally(() => setSaving(false))
  }

  if (loading) return <Spinner />
  if (!form)   return <ErrorMessage message={error} />

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Edit Student</h1>
        <Link to={`/students/${id}`} className="btn btn-outline">Cancel</Link>
      </div>
      <div className="card">
        <ErrorMessage message={error} />
        <form onSubmit={handleSubmit}>
          <div className="form-grid">
            <div className="form-group">
              <label className="form-label">Full Name *</label>
              <input className="form-input" name="name" value={form.name} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label className="form-label">Email *</label>
              <input className="form-input" type="email" name="email" value={form.email} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label className="form-label">Phone</label>
              <input className="form-input" name="phone" value={form.phone} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label className="form-label">Roll Number *</label>
              <input className="form-input" name="rollNumber" value={form.rollNumber} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label className="form-label">Course *</label>
              <input className="form-input" name="course" value={form.course} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label className="form-label">Semester *</label>
              <select className="form-select" name="semester" value={form.semester} onChange={handleChange}>
                {[1,2,3,4,5,6,7,8].map(s => (
                  <option key={s} value={s}>Semester {s}</option>
                ))}
              </select>
            </div>
          </div>
          <div className="form-actions">
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? 'Saving...' : 'Save Changes'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default EditStudentPage

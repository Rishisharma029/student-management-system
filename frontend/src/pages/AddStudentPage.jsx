import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { studentApi } from '../api/studentApi.js'
import ErrorMessage from '../components/ErrorMessage.jsx'

const EMPTY_FORM = {
  name: '', email: '', phone: '', rollNumber: '', course: '', semester: ''
}

function AddStudentPage() {
  const navigate = useNavigate()
  const [form, setForm]       = useState(EMPTY_FORM)
  const [errors, setErrors]   = useState({})
  const [error, setError]     = useState(null)
  const [saving, setSaving]   = useState(false)

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
    // Clear field error on change
    if (errors[name]) setErrors(prev => ({ ...prev, [name]: null }))
  }

  function validate() {
    const e = {}
    if (!form.name.trim())       e.name       = 'Name is required'
    if (!form.email.trim())      e.email      = 'Email is required'
    if (!form.rollNumber.trim()) e.rollNumber = 'Roll number is required'
    if (!form.course.trim())     e.course     = 'Course is required'
    if (!form.semester)          e.semester   = 'Semester is required'
    return e
  }

  function handleSubmit(e) {
    e.preventDefault()
    const validationErrors = validate()
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors)
      return
    }

    setSaving(true)
    setError(null)

    const payload = { ...form, semester: parseInt(form.semester, 10) }
    studentApi.create(payload)
      .then(newStudent => navigate(`/students/${newStudent.id}`))
      .catch(err => setError(err.message))
      .finally(() => setSaving(false))
  }

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Add New Student</h1>
        <Link to="/students" className="btn btn-outline">Cancel</Link>
      </div>

      <div className="card">
        <ErrorMessage message={error} />
        <form onSubmit={handleSubmit}>
          <div className="form-grid">
            <div className="form-group">
              <label className="form-label">Full Name *</label>
              <input className="form-input" name="name" value={form.name} onChange={handleChange} />
              {errors.name && <p className="form-error">{errors.name}</p>}
            </div>
            <div className="form-group">
              <label className="form-label">Email *</label>
              <input className="form-input" type="email" name="email" value={form.email} onChange={handleChange} />
              {errors.email && <p className="form-error">{errors.email}</p>}
            </div>
            <div className="form-group">
              <label className="form-label">Phone</label>
              <input className="form-input" name="phone" value={form.phone} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label className="form-label">Roll Number *</label>
              <input className="form-input" name="rollNumber" value={form.rollNumber} onChange={handleChange} />
              {errors.rollNumber && <p className="form-error">{errors.rollNumber}</p>}
            </div>
            <div className="form-group">
              <label className="form-label">Course *</label>
              <input className="form-input" name="course" value={form.course} onChange={handleChange} />
              {errors.course && <p className="form-error">{errors.course}</p>}
            </div>
            <div className="form-group">
              <label className="form-label">Semester *</label>
              <select className="form-select" name="semester" value={form.semester} onChange={handleChange}>
                <option value="">Select semester</option>
                {[1,2,3,4,5,6,7,8].map(s => (
                  <option key={s} value={s}>Semester {s}</option>
                ))}
              </select>
              {errors.semester && <p className="form-error">{errors.semester}</p>}
            </div>
          </div>
          <div className="form-actions">
            <button type="submit" className="btn btn-primary" disabled={saving}>
              {saving ? 'Saving...' : 'Add Student'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default AddStudentPage

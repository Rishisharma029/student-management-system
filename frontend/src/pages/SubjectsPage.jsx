import { useState, useEffect } from 'react'
import { subjectApi } from '../api/subjectApi.js'
import Spinner from '../components/Spinner.jsx'
import ErrorMessage from '../components/ErrorMessage.jsx'

function SubjectsPage() {
  const [subjects, setSubjects]   = useState([])
  const [loading, setLoading]     = useState(true)
  const [error, setError]         = useState(null)
  const [showForm, setShowForm]   = useState(false)
  const [form, setForm]           = useState({ name: '', code: '' })
  const [saving, setSaving]       = useState(false)
  const [formError, setFormError] = useState(null)

  useEffect(() => { loadSubjects() }, [])

  function loadSubjects() {
    setLoading(true)
    subjectApi.getAll()
      .then(data => setSubjects(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false))
  }

  function handleSubmit(e) {
    e.preventDefault()
    setSaving(true)
    setFormError(null)
    subjectApi.create(form)
      .then(() => {
        setShowForm(false)
        setForm({ name: '', code: '' })
        loadSubjects()
      })
      .catch(err => setFormError(err.message))
      .finally(() => setSaving(false))
  }

  function handleDelete(id, name) {
    if (!window.confirm(`Delete subject "${name}"?`)) return
    subjectApi.delete(id)
      .then(() => loadSubjects())
      .catch(err => setError(err.message))
  }

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Subjects</h1>
        <button className="btn btn-primary" onClick={() => setShowForm(!showForm)}>
          {showForm ? 'Cancel' : '+ Add Subject'}
        </button>
      </div>

      {/* Inline add form */}
      {showForm && (
        <div className="card" style={{ marginBottom: '1.5rem' }}>
          <h2 style={{ fontSize: '1rem', fontWeight: 600, marginBottom: '1rem' }}>New Subject</h2>
          <ErrorMessage message={formError} />
          <form onSubmit={handleSubmit}>
            <div className="form-grid">
              <div className="form-group">
                <label className="form-label">Subject Name *</label>
                <input className="form-input" value={form.name}
                  onChange={e => setForm(p => ({ ...p, name: e.target.value }))} required />
              </div>
              <div className="form-group">
                <label className="form-label">Subject Code *</label>
                <input className="form-input" value={form.code}
                  onChange={e => setForm(p => ({ ...p, code: e.target.value }))} required />
              </div>
            </div>
            <div className="form-actions">
              <button type="submit" className="btn btn-primary" disabled={saving}>
                {saving ? 'Saving...' : 'Add Subject'}
              </button>
            </div>
          </form>
        </div>
      )}

      <ErrorMessage message={error} />

      {loading ? <Spinner /> : (
        <div className="card table-container">
          <table>
            <thead>
              <tr><th>Code</th><th>Name</th><th>Added</th><th>Actions</th></tr>
            </thead>
            <tbody>
              {subjects.map(subject => (
                <tr key={subject.id}>
                  <td><strong>{subject.code}</strong></td>
                  <td>{subject.name}</td>
                  <td>{new Date(subject.createdAt).toLocaleDateString()}</td>
                  <td>
                    <button className="btn btn-danger"
                      style={{ padding: '0.3rem 0.7rem', fontSize: '0.8rem' }}
                      onClick={() => handleDelete(subject.id, subject.name)}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  )
}

export default SubjectsPage

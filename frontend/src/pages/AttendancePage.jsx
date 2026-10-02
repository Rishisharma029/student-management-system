import { useState, useEffect } from 'react'
import { studentApi } from '../api/studentApi.js'
import { subjectApi } from '../api/subjectApi.js'
import { attendanceApi } from '../api/attendanceApi.js'
import Spinner from '../components/Spinner.jsx'
import ErrorMessage from '../components/ErrorMessage.jsx'

function AttendancePage() {
  const [students, setStudents]       = useState([])
  const [subjects, setSubjects]       = useState([])
  const [records, setRecords]         = useState([])
  const [summary, setSummary]         = useState(null)
  const [loading, setLoading]         = useState(true)
  const [error, setError]             = useState(null)
  const [success, setSuccess]         = useState(null)

  // Form state
  const [selectedStudent, setSelectedStudent] = useState('')
  const [selectedSubject, setSelectedSubject] = useState('')
  const [date, setDate]                       = useState(new Date().toISOString().split('T')[0])
  const [status, setStatus]                   = useState('PRESENT')
  const [saving, setSaving]                   = useState(false)

  useEffect(() => {
    Promise.all([studentApi.getAll(), subjectApi.getAll()])
      .then(([s, sub]) => { setStudents(s); setSubjects(sub) })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false))
  }, [])

  // Load records when student selection changes
  useEffect(() => {
    if (selectedStudent) {
      attendanceApi.getByStudent(selectedStudent)
        .then(data => setRecords(data))
        .catch(err => setError(err.message))
    } else {
      setRecords([])
    }
    setSummary(null)
  }, [selectedStudent])

  // Load summary when both student and subject are selected
  useEffect(() => {
    if (selectedStudent && selectedSubject) {
      attendanceApi.getSummary(selectedStudent, selectedSubject)
        .then(data => setSummary(data))
        .catch(() => setSummary(null))
    } else {
      setSummary(null)
    }
  }, [selectedStudent, selectedSubject])

  function handleMark(e) {
    e.preventDefault()
    setSaving(true)
    setError(null)
    setSuccess(null)
    attendanceApi.markAttendance({
      studentId: parseInt(selectedStudent),
      subjectId: parseInt(selectedSubject),
      attendanceDate: date,
      status
    })
    .then(() => {
      setSuccess('Attendance marked successfully!')
      // Refresh records
      attendanceApi.getByStudent(selectedStudent).then(setRecords)
    })
    .catch(err => setError(err.message))
    .finally(() => setSaving(false))
  }

  if (loading) return <Spinner />

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Attendance</h1>
      </div>

      {/* Mark attendance form */}
      <div className="card" style={{ marginBottom: '1.5rem' }}>
        <h2 style={{ fontSize: '1rem', fontWeight: 600, marginBottom: '1rem' }}>Mark Attendance</h2>
        <ErrorMessage message={error} />
        {success && <div className="alert alert-success">{success}</div>}
        <form onSubmit={handleMark}>
          <div className="form-grid">
            <div className="form-group">
              <label className="form-label">Student *</label>
              <select className="form-select" value={selectedStudent}
                onChange={e => setSelectedStudent(e.target.value)} required>
                <option value="">Select student...</option>
                {students.map(s => (
                  <option key={s.id} value={s.id}>{s.name} ({s.rollNumber})</option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label className="form-label">Subject *</label>
              <select className="form-select" value={selectedSubject}
                onChange={e => setSelectedSubject(e.target.value)} required>
                <option value="">Select subject...</option>
                {subjects.map(s => (
                  <option key={s.id} value={s.id}>{s.name} ({s.code})</option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label className="form-label">Date *</label>
              <input className="form-input" type="date" value={date}
                onChange={e => setDate(e.target.value)} required />
            </div>
            <div className="form-group">
              <label className="form-label">Status *</label>
              <select className="form-select" value={status} onChange={e => setStatus(e.target.value)}>
                <option value="PRESENT">Present</option>
                <option value="ABSENT">Absent</option>
              </select>
            </div>
          </div>
          <div className="form-actions">
            <button type="submit" className="btn btn-primary"
              disabled={saving || !selectedStudent || !selectedSubject}>
              {saving ? 'Marking...' : 'Mark Attendance'}
            </button>
          </div>
        </form>
      </div>

      {/* Attendance summary */}
      {summary && (
        <div className="stat-grid" style={{ marginBottom: '1.5rem' }}>
          <div className="stat-card">
            <div className="stat-label">Total Classes</div>
            <div className="stat-value" style={{ fontSize: '1.5rem' }}>{summary.totalClasses}</div>
          </div>
          <div className="stat-card">
            <div className="stat-label">Present</div>
            <div className="stat-value" style={{ fontSize: '1.5rem', color: 'var(--success)' }}>{summary.presentCount}</div>
          </div>
          <div className="stat-card">
            <div className="stat-label">Absent</div>
            <div className="stat-value" style={{ fontSize: '1.5rem', color: 'var(--danger)' }}>{summary.absentCount}</div>
          </div>
          <div className="stat-card">
            <div className="stat-label">Attendance %</div>
            <div className="stat-value" style={{ fontSize: '1.5rem' }}>{summary.attendancePercentage}%</div>
          </div>
        </div>
      )}

      {/* Records table */}
      {records.length > 0 && (
        <div className="card table-container">
          <h2 style={{ fontSize: '1rem', fontWeight: 600, marginBottom: '1rem' }}>Attendance Records</h2>
          <table>
            <thead>
              <tr><th>Date</th><th>Subject</th><th>Status</th></tr>
            </thead>
            <tbody>
              {records.map(record => (
                <tr key={record.id}>
                  <td>{record.attendanceDate}</td>
                  <td>{record.subjectName} ({record.subjectCode})</td>
                  <td>
                    <span className={record.status === 'PRESENT' ? 'badge badge-present' : 'badge badge-absent'}>
                      {record.status}
                    </span>
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

export default AttendancePage

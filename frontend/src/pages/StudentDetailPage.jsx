import { useState, useEffect } from 'react'
import { useParams, Link, useNavigate } from 'react-router-dom'
import { studentApi } from '../api/studentApi.js'
import { attendanceApi } from '../api/attendanceApi.js'
import Spinner from '../components/Spinner.jsx'
import ErrorMessage from '../components/ErrorMessage.jsx'

function StudentDetailPage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [student, setStudent]       = useState(null)
  const [attendance, setAttendance] = useState([])
  const [loading, setLoading]       = useState(true)
  const [error, setError]           = useState(null)

  useEffect(() => {
    Promise.all([
      studentApi.getById(id),
      attendanceApi.getByStudent(id)
    ])
    .then(([studentData, attendanceData]) => {
      setStudent(studentData)
      setAttendance(attendanceData)
    })
    .catch(err => setError(err.message))
    .finally(() => setLoading(false))
  }, [id])

  if (loading) return <Spinner />
  if (error)   return <ErrorMessage message={error} />

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">{student.name}</h1>
        <div style={{ display: 'flex', gap: '0.5rem' }}>
          <Link to={`/students/${id}/edit`} className="btn btn-outline">Edit</Link>
          <Link to="/students" className="btn btn-outline">Back</Link>
        </div>
      </div>

      {/* Student information */}
      <div className="card" style={{ marginBottom: '1.5rem' }}>
        <h2 style={{ fontSize: '1rem', fontWeight: 600, marginBottom: '1rem' }}>
          Student Information
        </h2>
        <div className="form-grid">
          <div><strong>Email:</strong> {student.email}</div>
          <div><strong>Phone:</strong> {student.phone || '—'}</div>
          <div><strong>Roll Number:</strong> {student.rollNumber}</div>
          <div><strong>Course:</strong> {student.course}</div>
          <div><strong>Semester:</strong> {student.semester}</div>
          <div><strong>Joined:</strong> {new Date(student.createdAt).toLocaleDateString()}</div>
        </div>
      </div>

      {/* Attendance history */}
      <div className="card">
        <h2 style={{ fontSize: '1rem', fontWeight: 600, marginBottom: '1rem' }}>
          Attendance History
        </h2>
        {attendance.length === 0 ? (
          <div className="empty-state"><p>No attendance records found.</p></div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Date</th>
                <th>Subject</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {attendance.map(record => (
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
        )}
      </div>
    </div>
  )
}

export default StudentDetailPage

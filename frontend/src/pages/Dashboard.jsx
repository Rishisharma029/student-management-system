import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { dashboardApi } from '../api/dashboardApi.js'
import Spinner from '../components/Spinner.jsx'
import ErrorMessage from '../components/ErrorMessage.jsx'

/**
 * ============================================================
 *  Dashboard Page
 * ============================================================
 * 
 * Fetches live statistics from the backend and displays:
 * - Total students
 * - Total subjects
 * - Overall attendance percentage
 * - Recently added students
 * 
 * All numbers come from the API — never hardcoded.
 * ============================================================
 */
function Dashboard() {
  const [stats, setStats]     = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError]     = useState(null)

  useEffect(() => {
    // Fetch dashboard stats when the component first mounts
    dashboardApi.getStats()
      .then(data => setStats(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false))
  }, [])  // Empty dependency array = run once on mount

  if (loading) return <Spinner />
  if (error)   return <ErrorMessage message={`Unable to load dashboard: ${error}`} />

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Dashboard</h1>
      </div>

      {/* ── Statistics cards ── */}
      <div className="stat-grid">
        <div className="stat-card">
          <div className="stat-label">Total Students</div>
          <div className="stat-value">{stats.totalStudents}</div>
        </div>

        <div className="stat-card">
          <div className="stat-label">Total Subjects</div>
          <div className="stat-value">{stats.totalSubjects}</div>
        </div>

        <div className="stat-card">
          <div className="stat-label">Attendance Records</div>
          <div className="stat-value">{stats.totalAttendanceRecords}</div>
        </div>

        <div className="stat-card">
          <div className="stat-label">Overall Attendance</div>
          <div className="stat-value">{stats.overallAttendancePercentage}%</div>
        </div>
      </div>

      {/* ── Recent students ── */}
      <div className="card">
        <div className="page-header" style={{ marginBottom: '1rem' }}>
          <h2 style={{ fontSize: '1rem', fontWeight: 600 }}>Recent Students</h2>
          <Link to="/students" className="btn btn-outline" style={{ fontSize: '0.8rem' }}>
            View All
          </Link>
        </div>

        {stats.recentStudents?.length === 0 ? (
          <div className="empty-state">
            <p>No students added yet.</p>
          </div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Roll Number</th>
                <th>Course</th>
                <th>Semester</th>
              </tr>
            </thead>
            <tbody>
              {stats.recentStudents?.map(student => (
                <tr key={student.id}>
                  <td>
                    <Link to={`/students/${student.id}`} style={{ color: 'var(--primary)', textDecoration: 'none' }}>
                      {student.name}
                    </Link>
                  </td>
                  <td>{student.rollNumber}</td>
                  <td>{student.course}</td>
                  <td>Semester {student.semester}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  )
}

export default Dashboard

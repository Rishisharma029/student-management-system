import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { studentApi } from '../api/studentApi.js'
import Spinner from '../components/Spinner.jsx'
import ErrorMessage from '../components/ErrorMessage.jsx'

/**
 * ============================================================
 *  StudentsPage — List of all students with search + delete
 * ============================================================
 */
function StudentsPage() {
  const [students, setStudents] = useState([])
  const [loading, setLoading]   = useState(true)
  const [error, setError]       = useState(null)
  const [search, setSearch]     = useState('')
  const navigate = useNavigate()

  // Fetch students on mount
  useEffect(() => {
    loadStudents()
  }, [])

  function loadStudents(searchTerm = '') {
    setLoading(true)
    setError(null)
    const params = searchTerm ? { search: searchTerm } : {}
    studentApi.getAll(params)
      .then(data => setStudents(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false))
  }

  function handleSearch(e) {
    e.preventDefault()
    loadStudents(search)
  }

  function handleDelete(id, name) {
    if (!window.confirm(`Are you sure you want to delete ${name}?`)) return
    studentApi.delete(id)
      .then(() => loadStudents(search))
      .catch(err => setError(err.message))
  }

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Students</h1>
        <Link to="/students/new" className="btn btn-primary">
          + Add Student
        </Link>
      </div>

      {/* Search bar */}
      <form className="search-bar" onSubmit={handleSearch}>
        <input
          className="form-input"
          type="text"
          placeholder="Search by name, email, or roll number..."
          value={search}
          onChange={e => setSearch(e.target.value)}
        />
        <button type="submit" className="btn btn-outline">Search</button>
        {search && (
          <button type="button" className="btn btn-outline"
            onClick={() => { setSearch(''); loadStudents('') }}
          >
            Clear
          </button>
        )}
      </form>

      <ErrorMessage message={error} />

      {loading ? (
        <Spinner />
      ) : students.length === 0 ? (
        <div className="empty-state">
          <p>No students found.</p>
        </div>
      ) : (
        <div className="card table-container">
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Roll Number</th>
                <th>Course</th>
                <th>Semester</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {students.map(student => (
                <tr key={student.id}>
                  <td>
                    <Link to={`/students/${student.id}`}
                      style={{ color: 'var(--primary)', textDecoration: 'none', fontWeight: 500 }}
                    >
                      {student.name}
                    </Link>
                  </td>
                  <td>{student.email}</td>
                  <td>{student.rollNumber}</td>
                  <td>{student.course}</td>
                  <td>Sem {student.semester}</td>
                  <td>
                    <div style={{ display: 'flex', gap: '0.5rem' }}>
                      <button className="btn btn-outline"
                        style={{ padding: '0.3rem 0.7rem', fontSize: '0.8rem' }}
                        onClick={() => navigate(`/students/${student.id}/edit`)}
                      >
                        Edit
                      </button>
                      <button className="btn btn-danger"
                        style={{ padding: '0.3rem 0.7rem', fontSize: '0.8rem' }}
                        onClick={() => handleDelete(student.id, student.name)}
                      >
                        Delete
                      </button>
                    </div>
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

export default StudentsPage

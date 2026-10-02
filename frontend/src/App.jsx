import { Routes, Route, Navigate } from 'react-router-dom'
import Layout from './components/Layout.jsx'
import Dashboard from './pages/Dashboard.jsx'
import StudentsPage from './pages/StudentsPage.jsx'
import StudentDetailPage from './pages/StudentDetailPage.jsx'
import AddStudentPage from './pages/AddStudentPage.jsx'
import EditStudentPage from './pages/EditStudentPage.jsx'
import SubjectsPage from './pages/SubjectsPage.jsx'
import AttendancePage from './pages/AttendancePage.jsx'

/**
 * ============================================================
 *  App.jsx — Root component with route definitions
 * ============================================================
 * 
 * React Router v6 uses <Routes> and <Route> to render
 * different components based on the current URL.
 * 
 * Layout wraps every page — it contains the sidebar + topbar.
 * ============================================================
 */
function App() {
  return (
    <Routes>
      {/* All pages share the same Layout (sidebar + topbar) */}
      <Route path="/" element={<Layout />}>
        
        {/* Default route — redirect / to /dashboard */}
        <Route index element={<Navigate to="/dashboard" replace />} />
        
        {/* Dashboard: overview stats */}
        <Route path="dashboard" element={<Dashboard />} />
        
        {/* Students: list, detail, add, edit */}
        <Route path="students" element={<StudentsPage />} />
        <Route path="students/:id" element={<StudentDetailPage />} />
        <Route path="students/new" element={<AddStudentPage />} />
        <Route path="students/:id/edit" element={<EditStudentPage />} />
        
        {/* Subjects */}
        <Route path="subjects" element={<SubjectsPage />} />
        
        {/* Attendance */}
        <Route path="attendance" element={<AttendancePage />} />
        
      </Route>
    </Routes>
  )
}

export default App

import { Outlet, NavLink } from 'react-router-dom'

/**
 * ============================================================
 *  Layout — Persistent sidebar + content area
 * ============================================================
 * 
 * NavLink automatically adds an 'active' class when the
 * current URL matches the link's 'to' path.
 * 
 * <Outlet /> is where child routes render.
 * ============================================================
 */
function Layout() {
  return (
    <div className="app-layout">
      
      {/* ── Sidebar ── */}
      <nav className="sidebar">
        <div className="sidebar-logo">
          🎓 Student MS
        </div>
        
        <NavLink to="/dashboard" className={({ isActive }) =>
          isActive ? 'sidebar-link active' : 'sidebar-link'
        }>
          📊 Dashboard
        </NavLink>
        
        <NavLink to="/students" className={({ isActive }) =>
          isActive ? 'sidebar-link active' : 'sidebar-link'
        }>
          👥 Students
        </NavLink>
        
        <NavLink to="/subjects" className={({ isActive }) =>
          isActive ? 'sidebar-link active' : 'sidebar-link'
        }>
          📖 Subjects
        </NavLink>
        
        <NavLink to="/attendance" className={({ isActive }) =>
          isActive ? 'sidebar-link active' : 'sidebar-link'
        }>
          ✅ Attendance
        </NavLink>
      </nav>
      
      {/* ── Main content area ── */}
      <main className="main-content">
        {/* Child routes render here */}
        <Outlet />
      </main>
      
    </div>
  )
}

export default Layout

import { NavLink, Outlet } from "react-router-dom";

export function AppShell() {
  return (
    <div className="app-shell">
      <div className="layout-grid">
        <aside className="sidebar panel panel--sidebar">
          <div className="sidebar-brand">
            <div className="sidebar-brand__mark" aria-hidden="true">
              A
            </div>
            <div className="sidebar-brand__copy">
              <p className="eyebrow">Athena</p>
              <h2>Release control</h2>
              <p className="muted">One place to decide whether a release can move forward.</p>
            </div>
          </div>

          <div className="sidebar-divider" />

          <div className="nav-group">
            <p className="sidebar-section-title">Workspace</p>
            <nav className="nav-list" aria-label="Dashboards">
              <NavLink to="/release-readiness" className={({ isActive }) => `nav-link${isActive ? " nav-link--active" : ""}`}>
                Release readiness
              </NavLink>
              <NavLink to="/dashboards" className={({ isActive }) => `nav-link${isActive ? " nav-link--active" : ""}`}>
                Dashboards
              </NavLink>
              <NavLink to="/test-cycles" className={({ isActive }) => `nav-link${isActive ? " nav-link--active" : ""}`}>
                Test cycles
              </NavLink>
              <NavLink to="/performance" className={({ isActive }) => `nav-link${isActive ? " nav-link--active" : ""}`}>
                Performance
              </NavLink>
              <NavLink to="/correlation" className={({ isActive }) => `nav-link${isActive ? " nav-link--active" : ""}`}>
                Change &amp; run
              </NavLink>
              <NavLink to="/agent" className={({ isActive }) => `nav-link${isActive ? " nav-link--active" : ""}`}>
                Ask Athena
              </NavLink>
            </nav>
          </div>

          <div className="sidebar-card sidebar-card--contrast">
            <p className="eyebrow">Decision model</p>
            <h3>Four gates, one answer</h3>
            <p className="muted">Contracts, source sync, delivery flow, and quality sign-off are checked independently.</p>
          </div>
        </aside>

        <div className="content-shell">
          <main className="content" aria-live="polite">
            <Outlet />
          </main>
        </div>
      </div>
    </div>
  );
}

import { NavLink, Outlet } from "react-router-dom";

const dashboardLinks = [
  { to: "/overview", label: "Overview" },
  { to: "/reports/executive-briefing", label: "Executive Briefing" },
  { to: "/reports/portfolio", label: "Portfolio Report" },
  { to: "/reports/platform-operations", label: "Platform Operations" },
  { to: "/reports/release-readiness", label: "Release Readiness" },
  { to: "/reports/exception-follow-up", label: "Exception Follow-up" },
  { to: "/reports/delivery-quality", label: "Delivery + Quality" },
  { to: "/quality/executions", label: "Quality Runs" },
  { to: "/pipelines/runs", label: "Pipeline Runs" },
  { to: "/runtime/pods", label: "Runtime Pods" },
  { to: "/git/repositories", label: "Git Repositories" },
  { to: "/metrics/executions", label: "Metric Runs" },
  { to: "/apis/specs", label: "API Specs" },
];

const catalogLinks = [
  { to: "/catalog/projects", label: "Projects" },
  { to: "/catalog/environments", label: "Environments" },
  { to: "/catalog/versions", label: "Versions" },
  { to: "/catalog/users", label: "Users" },
];

const liveCoverage = ["Spec", "Metric", "Pipeline", "Quality", "Git", "Kube"];
const reportRouteCount = dashboardLinks.filter((link) => link.to.startsWith("/reports/")).length;
const shellMetrics = [
  {
    label: "Live domains",
    value: liveCoverage.length,
    detail: "Gateway-backed reporting modules",
  },
  {
    label: "Report routes",
    value: reportRouteCount,
    detail: "Executive and operational views",
  },
  {
    label: "Browser access",
    value: "/ui",
    detail: "Single gateway ingress path",
  },
];

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
              <p className="eyebrow">Athena reporting</p>
              <h2>Control Room</h2>
              <p className="muted">Contracts, telemetry, delivery, runtime, repository freshness, and quality oversight in one surface.</p>
            </div>
          </div>

          <div className="sidebar-divider" />

          <div className="nav-group">
            <p className="sidebar-section-title">Dashboards</p>
            <nav className="nav-list" aria-label="Dashboards">
              {dashboardLinks.map((link) => (
                <NavLink key={link.to} to={link.to} className={({ isActive }) => `nav-link${isActive ? " nav-link--active" : ""}`}>
                  {link.label}
                </NavLink>
              ))}
            </nav>
          </div>

          <div className="nav-group">
            <p className="sidebar-section-title">Catalog</p>
            <nav className="nav-list" aria-label="Catalog">
              {catalogLinks.map((link) => (
                <NavLink key={link.to} to={link.to} className={({ isActive }) => `nav-link${isActive ? " nav-link--active" : ""}`}>
                  {link.label}
                </NavLink>
              ))}
            </nav>
          </div>

          <div className="sidebar-card sidebar-card--contrast">
            <p className="eyebrow">Executive deck</p>
            <h3>Portfolio, executive, and operational reporting</h3>
            <p className="muted">
              Pipeline, metric, TMS, git, and kube provide summary, trend, and inventory views while spec exposes freshness and drift and the report area now provides executive,
              portfolio, platform-operations, release-readiness, exception follow-up, and delivery-focused rollups.
            </p>
          </div>

          <div className="sidebar-card sidebar-card--contrast">
            <p className="eyebrow">Portfolio coverage</p>
            <div className="token-grid">
              {liveCoverage.map((item) => (
                <span key={item} className="token-chip token-chip--muted">
                  {item}
                </span>
              ))}
            </div>
          </div>
        </aside>

        <div className="content-shell">
          <header className="masthead">
            <div className="masthead-intro">
              <p className="eyebrow">Unified reporting console</p>
              <h1 className="masthead-title">Athena Control Room</h1>
              <p className="masthead-copy">Gateway-routed delivery operations, contract health, performance telemetry, and quality execution reporting across Athena services.</p>
              <div className="callout-strip callout-strip--hero">
                <span>Gateway-only browser ingress</span>
                <span>External-friendly reporting deck</span>
                <span>Live spec, git, kube, metric, pipeline, and tms signals</span>
              </div>
            </div>

            <div className="signal-grid" aria-label="Portfolio shell highlights">
              {shellMetrics.map((metric) => (
                <article key={metric.label} className="signal-card signal-card--metric">
                  <p className="eyebrow">{metric.label}</p>
                  <strong className="signal-card__value">{metric.value}</strong>
                  <p className="signal-card__detail">{metric.detail}</p>
                </article>
              ))}
            </div>
          </header>

          <main className="content" aria-live="polite">
            <Outlet />
          </main>
        </div>
      </div>
    </div>
  );
}

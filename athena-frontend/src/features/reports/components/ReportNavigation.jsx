import { NavLink } from "react-router-dom";
import { reportPages } from "../reportPages";

export function ReportNavigation() {
  return (
    <section className="panel print-hidden">
      <div className="section-header">
        <div>
          <p className="eyebrow">Report navigation</p>
          <h2>Move between report routes without leaving /reports/*</h2>
        </div>
        <p className="page-summary">These pages reuse the live workspace contracts and stay inside the reporting area.</p>
      </div>

      <div className="dashboard-link-grid" aria-label="Report pages">
        {reportPages.map((page) => (
          <NavLink key={page.to} to={page.to} className={({ isActive }) => `nav-summary-card${isActive ? " nav-summary-card--active" : ""}`}>
            <p className="eyebrow">Report route</p>
            <h3>{page.label}</h3>
            <p className="muted">Stay inside the reporting deck and move directly into the live route for this audience.</p>
            <span className="summary-link-hint">Open report</span>
          </NavLink>
        ))}
      </div>
    </section>
  );
}

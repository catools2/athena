import { Link } from "react-router-dom";

export function DashboardPageHero({ eyebrow, title, summary, callouts = [], metrics = [], backLink, backLabel }) {
  const visibleCallouts = callouts.filter(Boolean);
  const visibleMetrics = metrics.filter(Boolean);

  return (
    <section className="panel panel--spotlight dashboard-hero">
      <div className="dashboard-hero__intro">
        {backLink && backLabel ? (
          <Link className="detail-link dashboard-hero__back" to={backLink}>
            {backLabel}
          </Link>
        ) : null}
        <p className="eyebrow">{eyebrow}</p>
        <h1>{title}</h1>
        <p className="lead">{summary}</p>
        {visibleCallouts.length > 0 ? (
          <div className="callout-strip">
            {visibleCallouts.map((callout, index) => (
              <span key={`${title}-callout-${index}`}>{callout}</span>
            ))}
          </div>
        ) : null}
      </div>

      {visibleMetrics.length > 0 ? (
        <div className="dashboard-hero__metrics" aria-label={`${title} highlights`}>
          {visibleMetrics.map((metric, index) => (
            <article key={metric.key ?? `${title}-metric-${index}`} className={`metric-card dashboard-hero__metric ${metric.toneClass ?? "metric-card--slate"}`}>
              <p className="eyebrow">{metric.label}</p>
              <h2 className="metric-card__value">{metric.value ?? "—"}</h2>
              {metric.detail ? <p className="metric-card__detail">{metric.detail}</p> : null}
            </article>
          ))}
        </div>
      ) : null}
    </section>
  );
}

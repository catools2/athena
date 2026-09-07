export function CatalogPlaceholderPage({ eyebrow, title, summary, nextStep }) {
  return (
    <div className="page-grid">
      <section className="panel panel--spotlight">
        <p className="eyebrow">{eyebrow}</p>
        <h1>{title}</h1>
        <p className="lead">{summary}</p>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Placeholder page</p>
            <h2>Why this route exists now</h2>
          </div>
        </div>
        <p className="muted">The shell needs real routes, navigation, loading states, and layout primitives before it starts depending on live service data.</p>
        <div className="next-step-card">
          <span className="status-pill status-pill--ready-now">Next</span>
          <p>{nextStep}</p>
        </div>
      </section>
    </div>
  );
}

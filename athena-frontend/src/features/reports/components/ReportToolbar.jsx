import { reportTimeWindowOptions } from "../reportUtils";

export function ReportToolbar({ title, summary, timeWindow, onTimeWindowChange, filterGroups = [], onExportJson, onExportCsv, onPrint }) {
  return (
    <section className="panel report-toolbar print-hidden">
      <div className="section-header">
        <div>
          <p className="eyebrow">Report controls</p>
          <h2>{title}</h2>
        </div>
        <p className="page-summary">{summary}</p>
      </div>

      <div className="report-toolbar__grid">
        <div className="report-toolbar__group">
          <p className="sidebar-section-title">Time window</p>
          <div className="button-row" role="toolbar" aria-label="Report time window filters">
            {reportTimeWindowOptions.map((option) => (
              <button
                key={option.key}
                type="button"
                className={`button ${timeWindow === option.key ? "button--primary" : "button--ghost"}`}
                aria-pressed={timeWindow === option.key}
                onClick={() => onTimeWindowChange(option.key)}
              >
                {option.label}
              </button>
            ))}
          </div>
        </div>

        {filterGroups.map((filterGroup) => (
          <div key={filterGroup.ariaLabel} className="report-toolbar__group">
            <p className="sidebar-section-title">{filterGroup.label}</p>
            <div className="button-row" role="toolbar" aria-label={filterGroup.ariaLabel}>
              {filterGroup.options.map((option) => (
                <button
                  key={option.key}
                  type="button"
                  className={`button ${filterGroup.value === option.key ? "button--primary" : "button--ghost"}`}
                  aria-pressed={filterGroup.value === option.key}
                  onClick={() => filterGroup.onChange(option.key)}
                >
                  {option.label}
                </button>
              ))}
            </div>
          </div>
        ))}

        <div className="report-toolbar__group">
          <p className="sidebar-section-title">Output</p>
          <div className="button-row" role="toolbar" aria-label="Report output actions">
            <button type="button" className="button button--ghost" onClick={onExportJson}>
              Export JSON
            </button>
            {onExportCsv ? (
              <button type="button" className="button button--ghost" onClick={onExportCsv}>
                Export CSV
              </button>
            ) : null}
            <button type="button" className="button button--ghost" onClick={onPrint}>
              Print view
            </button>
          </div>
        </div>
      </div>
    </section>
  );
}

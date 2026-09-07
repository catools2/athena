import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";
import {
  buildWorkspaceSearchParams,
  buildPagedResourcePath,
  buildResourcePath,
  emptyPage,
  formatDateTime,
  formatDuration,
  formatNumber,
  getErrorMessage,
  getFiltersFromSearchParams,
  getInitialFilters,
  getPageIndexFromSearchParams,
  normalizeFilters,
  parsePagePayload,
} from "../../../shared/ui/workspacePageUtils";

const filterFields = [
  { key: "project", label: "Project code", placeholder: "ATH" },
  { key: "environment", label: "Environment code", placeholder: "DEV (requires project)" },
  { key: "actionName", label: "Action name", placeholder: "sync" },
  { key: "actionType", label: "Action type", placeholder: "HTTP" },
  { key: "actionTarget", label: "Action target", placeholder: "/api/projects" },
];

const initialFilters = getInitialFilters(filterFields);

function MetricTrendChart({ points }) {
  const width = 720;
  const height = 240;
  const padding = 28;
  const innerWidth = width - padding * 2;
  const innerHeight = height - padding * 2;
  const lastIndex = Math.max(points.length - 1, 1);
  const maxMetricCount = Math.max(...points.map((point) => point.metricCount ?? 0), 1);
  const lastPoint = points[points.length - 1];
  const coordinates = points.map((point, index) => {
    const x = padding + (innerWidth * index) / lastIndex;
    const y = height - padding - ((point.metricCount ?? 0) / maxMetricCount) * innerHeight;
    return { x, y };
  });

  return (
    <div className="chart-shell">
      <svg className="trend-svg" viewBox={`0 0 ${width} ${height}`} role="img" aria-label="Metric execution volume trend">
        <line className="trend-axis" x1={padding} y1={height - padding} x2={width - padding} y2={height - padding} />
        <polyline className="trend-line" fill="none" points={coordinates.map((coordinate) => `${coordinate.x},${coordinate.y}`).join(" ")} />
        {coordinates.map((coordinate, index) => (
          <circle key={`${points[index].bucketStart}-${index}`} className="trend-point" cx={coordinate.x} cy={coordinate.y} r="4" />
        ))}
      </svg>

      <div className="trend-legend">
        <span>{formatNumber(points.length, 0, "0")} daily buckets</span>
        <span>Peak volume {formatNumber(maxMetricCount, 0, "0")}</span>
        <span>Last bucket {formatDateTime(lastPoint?.bucketStart)}</span>
      </div>
    </div>
  );
}

export function MetricWorkspacePage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const [draftFilters, setDraftFilters] = useState(() => getFiltersFromSearchParams(searchParams, filterFields));
  const [activeFilters, setActiveFilters] = useState(() => getFiltersFromSearchParams(searchParams, filterFields));
  const [pageIndex, setPageIndex] = useState(() => getPageIndexFromSearchParams(searchParams));
  const [pageData, setPageData] = useState(emptyPage);
  const [status, setStatus] = useState("loading");
  const [errorMessage, setErrorMessage] = useState("");
  const [summaryData, setSummaryData] = useState(null);
  const [summaryStatus, setSummaryStatus] = useState("loading");
  const [summaryErrorMessage, setSummaryErrorMessage] = useState("");
  const [trendData, setTrendData] = useState([]);
  const [trendStatus, setTrendStatus] = useState("loading");
  const [trendErrorMessage, setTrendErrorMessage] = useState("");
  const [reloadKey, setReloadKey] = useState(0);
  const serializedFilters = JSON.stringify(activeFilters);

  useEffect(() => {
    const nextFilters = getFiltersFromSearchParams(searchParams, filterFields);
    const nextSerializedFilters = JSON.stringify(nextFilters);
    const nextPageIndex = getPageIndexFromSearchParams(searchParams);

    setDraftFilters((current) => (JSON.stringify(current) === nextSerializedFilters ? current : nextFilters));
    setActiveFilters((current) => (JSON.stringify(current) === nextSerializedFilters ? current : nextFilters));
    setPageIndex((current) => (current === nextPageIndex ? current : nextPageIndex));
  }, [searchParams]);

  useEffect(() => {
    let isCancelled = false;

    setSummaryStatus("loading");
    setSummaryErrorMessage("");

    requestJson(apiRoots.metric, buildResourcePath("/summary", activeFilters))
      .then((payload) => {
        if (isCancelled) {
          return;
        }

        setSummaryData(payload ?? null);
        setSummaryStatus("ready");
      })
      .catch((error) => {
        if (isCancelled) {
          return;
        }

        setSummaryData(null);
        setSummaryErrorMessage(getErrorMessage(error));
        setSummaryStatus("error");
      });

    return () => {
      isCancelled = true;
    };
  }, [activeFilters, serializedFilters, reloadKey]);

  useEffect(() => {
    let isCancelled = false;

    setTrendStatus("loading");
    setTrendErrorMessage("");

    requestJson(apiRoots.metric, buildResourcePath("/trend", activeFilters))
      .then((payload) => {
        if (isCancelled) {
          return;
        }

        setTrendData(Array.isArray(payload) ? payload : []);
        setTrendStatus("ready");
      })
      .catch((error) => {
        if (isCancelled) {
          return;
        }

        setTrendData([]);
        setTrendErrorMessage(getErrorMessage(error));
        setTrendStatus("error");
      });

    return () => {
      isCancelled = true;
    };
  }, [activeFilters, serializedFilters, reloadKey]);

  useEffect(() => {
    let isCancelled = false;

    setStatus("loading");
    setErrorMessage("");

    requestJson(
      apiRoots.metric,
      buildPagedResourcePath("/all", {
        pageIndex,
        pageSize: 10,
        sort: "actionTime",
        direction: "DESC",
        filters: activeFilters,
      }),
    )
      .then((payload) => {
        if (isCancelled) {
          return;
        }

        setPageData(parsePagePayload(payload));
        setStatus("ready");
      })
      .catch((error) => {
        if (isCancelled) {
          return;
        }

        setPageData(emptyPage);
        setErrorMessage(getErrorMessage(error));
        setStatus("error");
      });

    return () => {
      isCancelled = true;
    };
  }, [activeFilters, pageIndex, serializedFilters, reloadKey]);

  const pageCount = Math.max(1, pageData.totalPages || 0);
  const pageLabel = pageData.totalElements ? `Page ${pageData.number + 1} of ${pageCount} · ${pageData.totalElements} total records` : "No records returned yet";
  const recentTrendPoints = trendData.slice(-4).reverse();
  const activeFilterCount = Object.values(activeFilters).filter((value) => (typeof value === "string" ? value.trim() : Boolean(value))).length;

  function handleFilterChange(key, value) {
    setDraftFilters((current) => ({
      ...current,
      [key]: value,
    }));
  }

  function handleApplyFilters(event) {
    event.preventDefault();
    setSearchParams(buildWorkspaceSearchParams(normalizeFilters(draftFilters)));
  }

  function handleClearFilters() {
    setSearchParams(buildWorkspaceSearchParams(initialFilters));
  }

  function handleRetry() {
    setReloadKey((current) => current + 1);
  }

  return (
    <div className="page-grid">
      <DashboardPageHero
        eyebrow="Metric workspace"
        title="Metric Performance Workspace"
        summary="Dashboard cards, a daily execution trend, and the raw inventory now share one gateway-routed workspace so external users can move from summary to investigation without leaving the Athena shell."
        callouts={[`Gateway root: ${apiRoots.metric}`, "Summary: /summary", "Trend: /trend", "Inventory: /all"]}
        metrics={[
          {
            key: "recorded-metrics",
            label: "Recorded metrics",
            value: formatNumber(summaryData?.totalCount, 0, "0"),
            detail: `${formatNumber(summaryData?.uniqueActionCount, 0, "0")} actions visible in the current scope.`,
            toneClass: "metric-card--blue",
          },
          {
            key: "avg-duration",
            label: "Average duration",
            value: formatDuration(summaryData?.averageDuration, 0),
            detail: `Slowest duration ${formatDuration(summaryData?.slowestDuration, 0)}.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-action",
            label: "Latest action",
            value: formatDateTime(summaryData?.latestActionTime),
            detail: `${formatNumber(trendData.length, 0, "0")} daily buckets in the execution trend.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "active-filters",
            label: "Active filters",
            value: formatNumber(activeFilterCount, 0, "0"),
            detail: "Project, environment, action, and target filters stay in sync across views.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Current snapshot</p>
            <h2>Metric dashboard cards</h2>
          </div>
          <p className="page-summary">Summary cards follow the same active filters as the trend and the inventory below.</p>
        </div>

        {summaryStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current metric summary.</h3>
            <p className="muted">The dashboard cards are aggregated from the same gateway-backed metric feed used by the execution table.</p>
          </div>
        ) : null}

        {summaryStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Summary unavailable</p>
            <h3>Metric dashboard cards could not be loaded.</h3>
            <p className="muted">{summaryErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {summaryStatus === "ready" && summaryData ? (
          <div className="summary-grid">
            <article className="summary-stat">
              <p className="eyebrow">Recorded metrics</p>
              <h3 className="summary-value">{formatNumber(summaryData.totalCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Unique actions</p>
              <h3 className="summary-value">{formatNumber(summaryData.uniqueActionCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Average duration</p>
              <h3 className="summary-value">{formatDuration(summaryData.averageDuration, 0)}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Slowest duration</p>
              <h3 className="summary-value">{formatDuration(summaryData.slowestDuration, 0)}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Latest action</p>
              <h3 className="summary-value summary-value--compact">{formatDateTime(summaryData.latestActionTime)}</h3>
            </article>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Daily chart</p>
            <h2>Execution volume trend</h2>
          </div>
          <p className="page-summary">Buckets are grouped by UTC day so trend comparisons stay consistent across environments.</p>
        </div>

        {trendStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the daily execution trend.</h3>
            <p className="muted">Trend buckets are derived from the filtered metric stream, not a separate reporting store.</p>
          </div>
        ) : null}

        {trendStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Trend unavailable</p>
            <h3>The daily chart could not be loaded.</h3>
            <p className="muted">{trendErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No trend data</p>
            <h3>No metric buckets matched the current filters.</h3>
            <p className="muted">Try clearing the filters or broadening the project and action criteria.</p>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length > 0 ? (
          <>
            <MetricTrendChart points={trendData} />
            <div className="trend-grid">
              {recentTrendPoints.map((point) => (
                <article key={point.bucketStart} className="summary-stat">
                  <p className="eyebrow">{formatDateTime(point.bucketStart)}</p>
                  <h3 className="summary-value">{formatNumber(point.metricCount, 0, "0")}</h3>
                  <p className="muted">
                    Avg {formatDuration(point.averageDuration, 0)} · Max {formatDuration(point.maxDuration, 0)}
                  </p>
                </article>
              ))}
            </div>
          </>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Live filters</p>
            <h2>Refine the metric dashboard without leaving the shell</h2>
          </div>
        </div>

        <form className="filter-form" onSubmit={handleApplyFilters}>
          <div className="filter-grid">
            {filterFields.map((filter) => (
              <label key={filter.key} className="field-label">
                <span>{filter.label}</span>
                <input
                  className="input-field"
                  name={filter.key}
                  value={draftFilters[filter.key]}
                  onChange={(event) => handleFilterChange(filter.key, event.target.value)}
                  placeholder={filter.placeholder}
                />
              </label>
            ))}
          </div>

          <div className="button-row">
            <button type="submit" className="button button--primary">
              Apply filters
            </button>
            <button type="button" className="button button--ghost" onClick={handleClearFilters}>
              Reset
            </button>
          </div>
        </form>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Execution inventory</p>
            <h2>Metric executions available through athena-boot-metric</h2>
          </div>
          <p className="page-summary">{pageLabel}</p>
        </div>

        {status === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current execution inventory.</h3>
            <p className="muted">Each row is loaded through the same metric gateway prefix the browser uses in production.</p>
          </div>
        ) : null}

        {status === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Gateway request failed</p>
            <h3>Live metric inventory is not available right now.</h3>
            <p className="muted">{errorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No results</p>
            <h3>No metric executions matched the current filters.</h3>
            <p className="muted">Try clearing the filters or widening the action criteria.</p>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length > 0 ? (
          <>
            <div className="table-shell">
              <table className="data-table">
                <thead>
                  <tr>
                    <th scope="col">Project</th>
                    <th scope="col">Environment</th>
                    <th scope="col">Action</th>
                    <th scope="col">Type</th>
                    <th scope="col">Target</th>
                    <th scope="col">Duration</th>
                    <th scope="col">Action time</th>
                  </tr>
                </thead>
                <tbody>
                  {pageData.content.map((row) => (
                    <tr key={`metric-${row.id}`}>
                      <td>{row.project}</td>
                      <td>{row.environment}</td>
                      <td>{row.actionName}</td>
                      <td>{row.actionType}</td>
                      <td>{row.actionTarget}</td>
                      <td>{formatDuration(row.duration, 0)}</td>
                      <td>{formatDateTime(row.actionTime)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            <div className="pagination-bar">
              <button
                type="button"
                className="button button--ghost"
                disabled={pageData.number <= 0}
                onClick={() => setSearchParams(buildWorkspaceSearchParams(activeFilters, Math.max(0, pageIndex - 1)))}
              >
                Previous page
              </button>
              <span className="page-summary">{pageLabel}</span>
              <button
                type="button"
                className="button button--ghost"
                disabled={pageData.totalPages === 0 || pageData.number >= pageData.totalPages - 1}
                onClick={() => setSearchParams(buildWorkspaceSearchParams(activeFilters, pageIndex + 1))}
              >
                Next page
              </button>
            </div>
          </>
        ) : null}
      </section>
    </div>
  );
}

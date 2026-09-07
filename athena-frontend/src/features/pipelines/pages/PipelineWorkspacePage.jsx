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
  { key: "version", label: "Version code", placeholder: "2.0.0" },
  { key: "environment", label: "Environment code", placeholder: "DEV" },
  { key: "name", label: "Pipeline name", placeholder: "release-main" },
  { key: "number", label: "Pipeline number", placeholder: "build-184" },
  { key: "state", label: "Pipeline state", placeholder: "RUNNING" },
];

const initialFilters = getInitialFilters(filterFields);

function getPipelineStateToneClass(state) {
  const normalizedState = String(state ?? "").toLowerCase();

  if (normalizedState.includes("complete") || normalizedState.includes("success")) {
    return "status-chip--success";
  }

  if (normalizedState.includes("fail") || normalizedState.includes("error")) {
    return "status-chip--critical";
  }

  if (normalizedState.includes("run") || normalizedState.includes("progress") || normalizedState.includes("pending")) {
    return "status-chip--warning";
  }

  return "status-chip--neutral";
}

function PipelineTrendChart({ points }) {
  const width = 720;
  const height = 240;
  const padding = 28;
  const innerWidth = width - padding * 2;
  const innerHeight = height - padding * 2;
  const lastIndex = Math.max(points.length - 1, 1);
  const maxPipelineCount = Math.max(...points.map((point) => Math.max(point.pipelineCount ?? 0, point.completedCount ?? 0)), 1);
  const lastPoint = points[points.length - 1];

  const totalCoordinates = points.map((point, index) => {
    const x = padding + (innerWidth * index) / lastIndex;
    const y = height - padding - ((point.pipelineCount ?? 0) / maxPipelineCount) * innerHeight;
    return { x, y };
  });

  const completedCoordinates = points.map((point, index) => {
    const x = padding + (innerWidth * index) / lastIndex;
    const y = height - padding - ((point.completedCount ?? 0) / maxPipelineCount) * innerHeight;
    return { x, y };
  });

  return (
    <div className="chart-shell chart-shell--dual">
      <svg className="trend-svg" viewBox={`0 0 ${width} ${height}`} role="img" aria-label="Pipeline activity trend">
        <line className="trend-axis" x1={padding} y1={height - padding} x2={width - padding} y2={height - padding} />
        <polyline className="trend-line" fill="none" points={totalCoordinates.map((coordinate) => `${coordinate.x},${coordinate.y}`).join(" ")} />
        <polyline className="trend-line trend-line--secondary" fill="none" points={completedCoordinates.map((coordinate) => `${coordinate.x},${coordinate.y}`).join(" ")} />
        {totalCoordinates.map((coordinate, index) => (
          <circle key={`${points[index].bucketStart}-${index}`} className="trend-point" cx={coordinate.x} cy={coordinate.y} r="4" />
        ))}
        {completedCoordinates.map((coordinate, index) => (
          <circle key={`${points[index].bucketStart}-completed-${index}`} className="trend-point trend-point--secondary" cx={coordinate.x} cy={coordinate.y} r="3" />
        ))}
      </svg>

      <div className="trend-legend">
        <span>Daily buckets {formatNumber(points.length, 0, "0")}</span>
        <span>Peak volume {formatNumber(maxPipelineCount, 0, "0")}</span>
        <span>Last bucket {formatDateTime(lastPoint?.bucketStart)}</span>
      </div>
    </div>
  );
}

export function PipelineWorkspacePage() {
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

    requestJson(apiRoots.pipeline, buildResourcePath("/summary", activeFilters))
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

    requestJson(apiRoots.pipeline, buildResourcePath("/trend", activeFilters))
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
      apiRoots.pipeline,
      buildPagedResourcePath("/all", {
        pageIndex,
        pageSize: 10,
        sort: "startDate",
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
  const pageLabel = pageData.totalElements ? `Page ${pageData.number + 1} of ${pageCount} · ${pageData.totalElements} total pipelines` : "No pipelines returned yet";
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
        eyebrow="Pipeline workspace"
        title="Pipeline Delivery Workspace"
        summary="Delivery runs now have the same aggregation-first treatment as metrics and quality: summary cards, a daily activity trend, and a searchable inventory exposed through the gateway shell."
        callouts={[`Gateway root: ${apiRoots.pipeline}`, "Summary: /summary", "Trend: /trend", "Inventory: /all"]}
        metrics={[
          {
            key: "pipeline-runs",
            label: "Pipeline runs",
            value: formatNumber(summaryData?.totalCount, 0, "0"),
            detail: `${formatNumber(summaryData?.uniqueNameCount, 0, "0")} unique pipeline names in scope.`,
            toneClass: "metric-card--blue",
          },
          {
            key: "running-runs",
            label: "Running",
            value: formatNumber(summaryData?.inProgressCount, 0, "0"),
            detail: `${formatNumber(summaryData?.completedCount, 0, "0")} completed with the current filters.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-start",
            label: "Latest start",
            value: formatDateTime(summaryData?.latestStartTime),
            detail: `Average duration ${formatDuration(summaryData?.averageDuration, 0)}.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "active-filters",
            label: "Active filters",
            value: formatNumber(activeFilterCount, 0, "0"),
            detail: "URL-backed filters stay aligned with trend and inventory slices.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Current snapshot</p>
            <h2>Pipeline dashboard cards</h2>
          </div>
          <p className="page-summary">Summary cards follow the same active filters as the trend and inventory below.</p>
        </div>

        {summaryStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current pipeline summary.</h3>
            <p className="muted">The cards are aggregated from the same gateway-backed pipeline stream used by the inventory.</p>
          </div>
        ) : null}

        {summaryStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Summary unavailable</p>
            <h3>Pipeline dashboard cards could not be loaded.</h3>
            <p className="muted">{summaryErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {summaryStatus === "ready" && summaryData ? (
          <div className="summary-grid">
            <article className="summary-stat">
              <p className="eyebrow">Pipeline runs</p>
              <h3 className="summary-value">{formatNumber(summaryData.totalCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Unique pipelines</p>
              <h3 className="summary-value">{formatNumber(summaryData.uniqueNameCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Completed</p>
              <h3 className="summary-value">{formatNumber(summaryData.completedCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Running</p>
              <h3 className="summary-value">{formatNumber(summaryData.inProgressCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Avg completed duration</p>
              <h3 className="summary-value">{formatDuration(summaryData.averageDuration, 0)}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Latest start</p>
              <h3 className="summary-value summary-value--compact">{formatDateTime(summaryData.latestStartTime)}</h3>
            </article>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Daily chart</p>
            <h2>Pipeline activity trend</h2>
          </div>
          <p className="page-summary">The primary line tracks all starts and the secondary line tracks completed runs.</p>
        </div>

        {trendStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the daily pipeline trend.</h3>
            <p className="muted">Trend buckets are grouped by pipeline start day using the filtered delivery stream.</p>
          </div>
        ) : null}

        {trendStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Trend unavailable</p>
            <h3>The pipeline activity chart could not be loaded.</h3>
            <p className="muted">{trendErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No trend data</p>
            <h3>No pipeline buckets matched the current filters.</h3>
            <p className="muted">Try clearing the filters or broadening the project, version, or pipeline criteria.</p>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length > 0 ? (
          <>
            <PipelineTrendChart points={trendData} />
            <div className="trend-grid">
              {recentTrendPoints.map((point) => (
                <article key={point.bucketStart} className="summary-stat">
                  <p className="eyebrow">{formatDateTime(point.bucketStart)}</p>
                  <h3 className="summary-value">{formatNumber(point.pipelineCount, 0, "0")}</h3>
                  <p className="muted">
                    Completed {formatNumber(point.completedCount, 0, "0")} · Avg {formatDuration(point.averageDuration, 0)}
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
            <h2>Refine the delivery dashboard without leaving the shell</h2>
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
            <p className="eyebrow">Pipeline inventory</p>
            <h2>Delivery runs available through athena-boot-pipeline</h2>
          </div>
          <p className="page-summary">{pageLabel}</p>
        </div>

        {status === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current pipeline inventory.</h3>
            <p className="muted">Each row is loaded through the same pipeline gateway prefix the browser will use in production.</p>
          </div>
        ) : null}

        {status === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Gateway request failed</p>
            <h3>Live pipeline inventory is not available right now.</h3>
            <p className="muted">{errorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No results</p>
            <h3>No pipelines matched the current filters.</h3>
            <p className="muted">Try clearing the filters or widening the project, environment, or pipeline criteria.</p>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length > 0 ? (
          <>
            <div className="table-shell">
              <table className="data-table">
                <thead>
                  <tr>
                    <th scope="col">Project</th>
                    <th scope="col">Version</th>
                    <th scope="col">Environment</th>
                    <th scope="col">Name</th>
                    <th scope="col">Number</th>
                    <th scope="col">State</th>
                    <th scope="col">Duration</th>
                    <th scope="col">Started</th>
                    <th scope="col">Ended</th>
                  </tr>
                </thead>
                <tbody>
                  {pageData.content.map((row) => (
                    <tr key={`pipeline-${row.id}`}>
                      <td>{row.project || "—"}</td>
                      <td>{row.version || "—"}</td>
                      <td>{row.environment || "—"}</td>
                      <td>{row.name || "—"}</td>
                      <td>{row.number || "—"}</td>
                      <td>
                        <span className={`status-chip ${getPipelineStateToneClass(row.state)}`}>{row.state || "UNKNOWN"}</span>
                      </td>
                      <td>{formatDuration(row.duration, 0)}</td>
                      <td>{formatDateTime(row.startDate)}</td>
                      <td>{formatDateTime(row.endDate)}</td>
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

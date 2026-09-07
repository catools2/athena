import { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";
import {
  buildWorkspaceSearchParams,
  buildPagedResourcePath,
  buildResourcePath,
  emptyPage,
  formatDateTime,
  formatNumber,
  getErrorMessage,
  getFiltersFromSearchParams,
  getInitialFilters,
  getPageIndexFromSearchParams,
  normalizeFilters,
  parsePagePayload,
} from "../../../shared/ui/workspacePageUtils";
import { getStatusToneClass } from "../statusTone";

const filterFields = [
  { key: "project", label: "Project code", placeholder: "ATH" },
  { key: "version", label: "Version code", placeholder: "2.0.0" },
  { key: "cycleCode", label: "Cycle code", placeholder: "REG-24.05" },
  { key: "itemCode", label: "Item code", placeholder: "ATH-TC-102" },
  { key: "status", label: "Execution status", placeholder: "PASSED" },
  { key: "progress", label: "Execution progress", placeholder: "PENDING" },
];

const initialFilters = getInitialFilters(filterFields);

function QualityTrendChart({ points }) {
  const width = 720;
  const height = 240;
  const padding = 28;
  const innerWidth = width - padding * 2;
  const innerHeight = height - padding * 2;
  const lastIndex = Math.max(points.length - 1, 1);
  const maxExecutionCount = Math.max(...points.map((point) => Math.max(point.executionCount ?? 0, point.executedCount ?? 0)), 1);
  const lastPoint = points[points.length - 1];

  const totalCoordinates = points.map((point, index) => {
    const x = padding + (innerWidth * index) / lastIndex;
    const y = height - padding - ((point.executionCount ?? 0) / maxExecutionCount) * innerHeight;
    return { x, y };
  });

  const executedCoordinates = points.map((point, index) => {
    const x = padding + (innerWidth * index) / lastIndex;
    const y = height - padding - ((point.executedCount ?? 0) / maxExecutionCount) * innerHeight;
    return { x, y };
  });

  return (
    <div className="chart-shell chart-shell--dual">
      <svg className="trend-svg" viewBox={`0 0 ${width} ${height}`} role="img" aria-label="Quality execution activity trend">
        <line className="trend-axis" x1={padding} y1={height - padding} x2={width - padding} y2={height - padding} />
        <polyline className="trend-line" fill="none" points={totalCoordinates.map((coordinate) => `${coordinate.x},${coordinate.y}`).join(" ")} />
        <polyline className="trend-line trend-line--secondary" fill="none" points={executedCoordinates.map((coordinate) => `${coordinate.x},${coordinate.y}`).join(" ")} />
        {totalCoordinates.map((coordinate, index) => (
          <circle key={`${points[index].bucketStart}-${index}`} className="trend-point" cx={coordinate.x} cy={coordinate.y} r="4" />
        ))}
        {executedCoordinates.map((coordinate, index) => (
          <circle key={`${points[index].bucketStart}-executed-${index}`} className="trend-point trend-point--secondary" cx={coordinate.x} cy={coordinate.y} r="3" />
        ))}
      </svg>

      <div className="trend-legend">
        <span>Total buckets {formatNumber(points.length, 0, "0")}</span>
        <span>Peak activity {formatNumber(maxExecutionCount, 0, "0")}</span>
        <span>Last completed {formatNumber(lastPoint?.executedCount, 0, "0")}</span>
      </div>
    </div>
  );
}

export function QualityExecutionWorkspacePage() {
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

    requestJson(apiRoots.tms, buildResourcePath("/summary", activeFilters))
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

    requestJson(apiRoots.tms, buildResourcePath("/trend", activeFilters))
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
      apiRoots.tms,
      buildPagedResourcePath("/all", {
        pageIndex,
        pageSize: 10,
        sort: "executedOn",
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
  const pageLabel = pageData.totalElements ? `Page ${pageData.number + 1} of ${pageCount} · ${pageData.totalElements} total executions` : "No executions returned yet";
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
        eyebrow="Quality workspace"
        title="Quality Execution Workspace"
        summary="TMS execution data now lands in an aggregation-first workspace with live summary cards, a daily activity trend, and a searchable execution inventory for external reporting."
        callouts={[`Gateway root: ${apiRoots.tms}`, "Summary: /summary", "Trend: /trend", "Inventory: /all"]}
        metrics={[
          {
            key: "executions",
            label: "Executions",
            value: formatNumber(summaryData?.totalCount, 0, "0"),
            detail: `${formatNumber(summaryData?.executedCount, 0, "0")} completed in the current workspace scope.`,
            toneClass: "metric-card--blue",
          },
          {
            key: "pending",
            label: "Pending",
            value: formatNumber(summaryData?.pendingCount, 0, "0"),
            detail: `${formatNumber(summaryData?.cycleCount, 0, "0")} cycles contributing to the dashboard.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-activity",
            label: "Latest activity",
            value: formatDateTime(summaryData?.latestActivityTime),
            detail: `${formatNumber(trendData.length, 0, "0")} trend buckets currently visible.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "active-filters",
            label: "Active filters",
            value: formatNumber(activeFilterCount, 0, "0"),
            detail: "Search state is shared by summary, chart, and inventory views.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Current snapshot</p>
            <h2>Execution quality summary</h2>
          </div>
          <p className="page-summary">Summary cards follow the same filters as the chart and inventory below.</p>
        </div>

        {summaryStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current quality summary.</h3>
            <p className="muted">The cards are calculated from the same TMS execution stream used by the live inventory.</p>
          </div>
        ) : null}

        {summaryStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Summary unavailable</p>
            <h3>Quality dashboard cards could not be loaded.</h3>
            <p className="muted">{summaryErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {summaryStatus === "ready" && summaryData ? (
          <>
            <div className="summary-grid">
              <article className="summary-stat">
                <p className="eyebrow">Executions</p>
                <h3 className="summary-value">{formatNumber(summaryData.totalCount, 0, "0")}</h3>
              </article>
              <article className="summary-stat">
                <p className="eyebrow">Cycles</p>
                <h3 className="summary-value">{formatNumber(summaryData.cycleCount, 0, "0")}</h3>
              </article>
              <article className="summary-stat">
                <p className="eyebrow">Items</p>
                <h3 className="summary-value">{formatNumber(summaryData.itemCount, 0, "0")}</h3>
              </article>
              <article className="summary-stat">
                <p className="eyebrow">Completed</p>
                <h3 className="summary-value">{formatNumber(summaryData.executedCount, 0, "0")}</h3>
              </article>
              <article className="summary-stat">
                <p className="eyebrow">Pending</p>
                <h3 className="summary-value">{formatNumber(summaryData.pendingCount, 0, "0")}</h3>
              </article>
              <article className="summary-stat">
                <p className="eyebrow">Latest activity</p>
                <h3 className="summary-value summary-value--compact">{formatDateTime(summaryData.latestActivityTime)}</h3>
              </article>
            </div>

            {Array.isArray(summaryData.statusBreakdown) && summaryData.statusBreakdown.length > 0 ? (
              <div className="quality-breakdown-grid">
                {summaryData.statusBreakdown.map((entry) => (
                  <article key={entry.status} className="summary-stat">
                    <span className={`status-chip ${getStatusToneClass(entry.status)}`}>{entry.status ?? "UNKNOWN"}</span>
                    <h3 className="summary-value">{formatNumber(entry.count, 0, "0")}</h3>
                  </article>
                ))}
              </div>
            ) : null}
          </>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Daily chart</p>
            <h2>Execution activity trend</h2>
          </div>
          <p className="page-summary">The primary line tracks all activity and the secondary line tracks completed executions.</p>
        </div>

        {trendStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the execution activity trend.</h3>
            <p className="muted">Trend buckets are calculated from filtered TMS activity by UTC day.</p>
          </div>
        ) : null}

        {trendStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Trend unavailable</p>
            <h3>The execution activity chart could not be loaded.</h3>
            <p className="muted">{trendErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No trend data</p>
            <h3>No execution buckets matched the current filters.</h3>
            <p className="muted">Try clearing the filters or broadening the project, cycle, or status criteria.</p>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length > 0 ? (
          <>
            <QualityTrendChart points={trendData} />
            <div className="trend-grid">
              {recentTrendPoints.map((point) => (
                <article key={point.bucketStart} className="summary-stat">
                  <p className="eyebrow">{formatDateTime(point.bucketStart)}</p>
                  <h3 className="summary-value">{formatNumber(point.executionCount, 0, "0")}</h3>
                  <p className="muted">
                    Completed {formatNumber(point.executedCount, 0, "0")} · Items {formatNumber(point.uniqueItemCount, 0, "0")}
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
            <h2>Refine the quality dashboard without leaving the shell</h2>
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
            <h2>TMS executions available through athena-boot-tms</h2>
          </div>
          <p className="page-summary">{pageLabel}</p>
        </div>

        {status === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current execution inventory.</h3>
            <p className="muted">Each row is loaded through the same TMS gateway prefix the browser will use in production.</p>
          </div>
        ) : null}

        {status === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Gateway request failed</p>
            <h3>Live quality inventory is not available right now.</h3>
            <p className="muted">{errorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No results</p>
            <h3>No executions matched the current filters.</h3>
            <p className="muted">Try clearing the filters or widening the project, cycle, or status criteria.</p>
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
                    <th scope="col">Cycle</th>
                    <th scope="col">Item</th>
                    <th scope="col">Status</th>
                    <th scope="col">Executor</th>
                    <th scope="col">Executed</th>
                    <th scope="col">Created</th>
                  </tr>
                </thead>
                <tbody>
                  {pageData.content.map((row) => (
                    <tr key={`quality-${row.id}`}>
                      <td>{row.project || "—"}</td>
                      <td>{row.version || "—"}</td>
                      <td>{row.cycleCode || row.cycleName || "—"}</td>
                      <td>
                        {row.id ? (
                          <Link className="detail-link" to={`/quality/executions/${row.id}`}>
                            {row.item || `Execution ${row.id}`}
                          </Link>
                        ) : (
                          row.item || "—"
                        )}
                      </td>
                      <td>
                        <span className={`status-chip ${getStatusToneClass(row.status)}`}>{row.status || "UNKNOWN"}</span>
                      </td>
                      <td>{row.executor || "—"}</td>
                      <td>{formatDateTime(row.executedOn)}</td>
                      <td>{formatDateTime(row.createdOn)}</td>
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

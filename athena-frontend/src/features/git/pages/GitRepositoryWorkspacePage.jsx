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
  formatNumber,
  getErrorMessage,
  getFiltersFromSearchParams,
  getInitialFilters,
  getPageIndexFromSearchParams,
  normalizeFilters,
  parsePagePayload,
} from "../../../shared/ui/workspacePageUtils";

const filterFields = [
  { key: "keyword", label: "Keyword", placeholder: "athena-main" },
  { key: "host", label: "Host", placeholder: "github.com" },
  { key: "freshness", label: "Freshness", placeholder: "FRESH" },
];

const initialFilters = getInitialFilters(filterFields);

function getFreshnessToneClass(status) {
  const normalizedStatus = String(status ?? "").toLowerCase();

  if (normalizedStatus.includes("stale")) {
    return "status-chip--critical";
  }

  if (normalizedStatus.includes("aging")) {
    return "status-chip--warning";
  }

  if (normalizedStatus.includes("fresh")) {
    return "status-chip--success";
  }

  return "status-chip--neutral";
}

function GitTrendChart({ points }) {
  const width = 720;
  const height = 240;
  const padding = 28;
  const innerWidth = width - padding * 2;
  const innerHeight = height - padding * 2;
  const lastIndex = Math.max(points.length - 1, 1);
  const maxRepositoryCount = Math.max(...points.map((point) => point.repositoryCount ?? 0), 1);
  const lastPoint = points[points.length - 1];

  const coordinates = points.map((point, index) => {
    const x = padding + (innerWidth * index) / lastIndex;
    const y = height - padding - ((point.repositoryCount ?? 0) / maxRepositoryCount) * innerHeight;
    return { x, y };
  });

  return (
    <div className="chart-shell">
      <svg className="trend-svg" viewBox={`0 0 ${width} ${height}`} role="img" aria-label="Repository freshness trend">
        <line className="trend-axis" x1={padding} y1={height - padding} x2={width - padding} y2={height - padding} />
        <polyline className="trend-line" fill="none" points={coordinates.map((coordinate) => `${coordinate.x},${coordinate.y}`).join(" ")} />
        {coordinates.map((coordinate, index) => (
          <circle key={`${points[index].bucketStart}-${index}`} className="trend-point" cx={coordinate.x} cy={coordinate.y} r="4" />
        ))}
      </svg>

      <div className="trend-legend">
        <span>{formatNumber(points.length, 0, "0")} daily buckets</span>
        <span>Peak repository count {formatNumber(maxRepositoryCount, 0, "0")}</span>
        <span>Last bucket {formatDateTime(lastPoint?.bucketStart)}</span>
      </div>
    </div>
  );
}

export function GitRepositoryWorkspacePage() {
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

    requestJson(apiRoots.git, buildResourcePath("/summary", activeFilters))
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

    requestJson(apiRoots.git, buildResourcePath("/trend", activeFilters))
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
      apiRoots.git,
      buildPagedResourcePath("/all", {
        pageIndex,
        pageSize: 10,
        sort: "lastSync",
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
  const pageLabel = pageData.totalElements ? `Page ${pageData.number + 1} of ${pageCount} · ${pageData.totalElements} total repositories` : "No repositories returned yet";
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
        eyebrow="Repository workspace"
        title="Git Repository Workspace"
        summary="Repository freshness now sits in the same reporting console as runtime, delivery, and quality data so external users can scan sync posture and drill into repository inventory through the gateway."
        callouts={[`Gateway root: ${apiRoots.git}`, "Summary: /summary", "Trend: /trend", "Inventory: /all"]}
        metrics={[
          {
            key: "repositories",
            label: "Repositories",
            value: formatNumber(summaryData?.totalCount, 0, "0"),
            detail: `${formatNumber(summaryData?.hostCount, 0, "0")} hosts represented in the current view.`,
            toneClass: "metric-card--blue",
          },
          {
            key: "stale-repos",
            label: "Stale",
            value: formatNumber(summaryData?.staleCount, 0, "0"),
            detail: `${formatNumber(summaryData?.agingCount, 0, "0")} aging with ${formatNumber(summaryData?.freshCount, 0, "0")} fresh.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-sync",
            label: "Latest sync",
            value: formatDateTime(summaryData?.latestSyncTime),
            detail: `${formatNumber(trendData.length, 0, "0")} repository trend buckets visible.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "active-filters",
            label: "Active filters",
            value: formatNumber(activeFilterCount, 0, "0"),
            detail: "Keyword, host, and freshness filters stay URL-backed.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Current snapshot</p>
            <h2>Repository freshness cards</h2>
          </div>
          <p className="page-summary">Freshness buckets use the same active filters as the trend and inventory below.</p>
        </div>

        {summaryStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current repository summary.</h3>
            <p className="muted">The cards are aggregated from the same repository inventory the browser uses for live reporting.</p>
          </div>
        ) : null}

        {summaryStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Summary unavailable</p>
            <h3>Repository dashboard cards could not be loaded.</h3>
            <p className="muted">{summaryErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {summaryStatus === "ready" && summaryData ? (
          <div className="summary-grid">
            <article className="summary-stat">
              <p className="eyebrow">Repositories</p>
              <h3 className="summary-value">{formatNumber(summaryData.totalCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Hosts</p>
              <h3 className="summary-value">{formatNumber(summaryData.hostCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Fresh</p>
              <h3 className="summary-value">{formatNumber(summaryData.freshCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Aging</p>
              <h3 className="summary-value">{formatNumber(summaryData.agingCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Stale</p>
              <h3 className="summary-value">{formatNumber(summaryData.staleCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Unknown sync</p>
              <h3 className="summary-value">{formatNumber(summaryData.unknownSyncCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Warning window</p>
              <h3 className="summary-value">{formatNumber(summaryData.warningWindowDays, 0, "0")}d</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Latest sync</p>
              <h3 className="summary-value summary-value--compact">{formatDateTime(summaryData.latestSyncTime)}</h3>
            </article>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Daily chart</p>
            <h2>Repository sync trend</h2>
          </div>
          <p className="page-summary">Trend buckets show how many repositories synced each UTC day and how many hosts were involved.</p>
        </div>

        {trendStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the repository sync trend.</h3>
            <p className="muted">Trend buckets are derived from filtered repository sync timestamps, not a separate report store.</p>
          </div>
        ) : null}

        {trendStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Trend unavailable</p>
            <h3>The repository sync chart could not be loaded.</h3>
            <p className="muted">{trendErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No trend data</p>
            <h3>No repository buckets matched the current filters.</h3>
            <p className="muted">Try clearing the filters or broadening the host and keyword criteria.</p>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length > 0 ? (
          <>
            <GitTrendChart points={trendData} />
            <div className="trend-grid">
              {recentTrendPoints.map((point) => (
                <article key={point.bucketStart} className="summary-stat">
                  <p className="eyebrow">{formatDateTime(point.bucketStart)}</p>
                  <h3 className="summary-value">{formatNumber(point.repositoryCount, 0, "0")}</h3>
                  <p className="muted">Hosts involved {formatNumber(point.uniqueHostCount, 0, "0")}</p>
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
            <h2>Refine repository freshness without leaving the shell</h2>
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
            <p className="eyebrow">Repository inventory</p>
            <h2>Repositories available through athena-boot-git</h2>
          </div>
          <p className="page-summary">{pageLabel}</p>
        </div>

        {status === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current repository inventory.</h3>
            <p className="muted">Each row is loaded through the same git gateway prefix the browser uses in production.</p>
          </div>
        ) : null}

        {status === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Gateway request failed</p>
            <h3>Live repository inventory is not available right now.</h3>
            <p className="muted">{errorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No results</p>
            <h3>No repositories matched the current filters.</h3>
            <p className="muted">Try clearing the filters or widening the host and freshness criteria.</p>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length > 0 ? (
          <>
            <div className="table-shell">
              <table className="data-table">
                <thead>
                  <tr>
                    <th scope="col">Repository</th>
                    <th scope="col">Host</th>
                    <th scope="col">Freshness</th>
                    <th scope="col">Sync age</th>
                    <th scope="col">Last sync</th>
                    <th scope="col">URL</th>
                  </tr>
                </thead>
                <tbody>
                  {pageData.content.map((row) => (
                    <tr key={`repo-${row.id}`}>
                      <td>{row.name ?? "—"}</td>
                      <td>{row.host ?? "—"}</td>
                      <td>
                        <span className={`status-chip ${getFreshnessToneClass(row.freshnessStatus)}`}>{row.freshnessStatus ?? "UNKNOWN"}</span>
                      </td>
                      <td>{row.syncAgeDays == null ? "—" : `${formatNumber(row.syncAgeDays, 0, "0")}d`}</td>
                      <td>{formatDateTime(row.lastSync)}</td>
                      <td>{row.url}</td>
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

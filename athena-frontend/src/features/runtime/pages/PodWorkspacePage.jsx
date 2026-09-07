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
  { key: "project", label: "Project code", placeholder: "ATH" },
  { key: "namespace", label: "Namespace", placeholder: "runtime-prod" },
  { key: "name", label: "Pod name", placeholder: "athena-api-7b6c48" },
  { key: "nodeName", label: "Node name", placeholder: "worker-a" },
  { key: "status", label: "Status", placeholder: "Running" },
];

const initialFilters = getInitialFilters(filterFields);

function getPodStatusToneClass(status) {
  const normalizedStatus = String(status ?? "").toLowerCase();

  if (normalizedStatus.includes("fail") || normalizedStatus.includes("error") || normalizedStatus.includes("crash")) {
    return "status-chip--critical";
  }

  if (normalizedStatus.includes("run") || normalizedStatus.includes("ready")) {
    return "status-chip--success";
  }

  if (normalizedStatus.includes("pend") || normalizedStatus.includes("init")) {
    return "status-chip--warning";
  }

  return "status-chip--neutral";
}

function PodTrendChart({ points }) {
  const width = 720;
  const height = 240;
  const padding = 28;
  const innerWidth = width - padding * 2;
  const innerHeight = height - padding * 2;
  const lastIndex = Math.max(points.length - 1, 1);
  const maxPodCount = Math.max(...points.map((point) => Math.max(point.podCount ?? 0, point.activeCount ?? 0)), 1);
  const lastPoint = points[points.length - 1];

  const totalCoordinates = points.map((point, index) => {
    const x = padding + (innerWidth * index) / lastIndex;
    const y = height - padding - ((point.podCount ?? 0) / maxPodCount) * innerHeight;
    return { x, y };
  });

  const activeCoordinates = points.map((point, index) => {
    const x = padding + (innerWidth * index) / lastIndex;
    const y = height - padding - ((point.activeCount ?? 0) / maxPodCount) * innerHeight;
    return { x, y };
  });

  return (
    <div className="chart-shell chart-shell--dual">
      <svg className="trend-svg" viewBox={`0 0 ${width} ${height}`} role="img" aria-label="Runtime pod inventory trend">
        <line className="trend-axis" x1={padding} y1={height - padding} x2={width - padding} y2={height - padding} />
        <polyline className="trend-line" fill="none" points={totalCoordinates.map((coordinate) => `${coordinate.x},${coordinate.y}`).join(" ")} />
        <polyline className="trend-line trend-line--secondary" fill="none" points={activeCoordinates.map((coordinate) => `${coordinate.x},${coordinate.y}`).join(" ")} />
        {totalCoordinates.map((coordinate, index) => (
          <circle key={`${points[index].bucketStart}-${index}`} className="trend-point" cx={coordinate.x} cy={coordinate.y} r="4" />
        ))}
        {activeCoordinates.map((coordinate, index) => (
          <circle key={`${points[index].bucketStart}-active-${index}`} className="trend-point trend-point--secondary" cx={coordinate.x} cy={coordinate.y} r="3" />
        ))}
      </svg>

      <div className="trend-legend">
        <span>Total buckets {formatNumber(points.length, 0, "0")}</span>
        <span>Peak pod count {formatNumber(maxPodCount, 0, "0")}</span>
        <span>Last bucket {formatDateTime(lastPoint?.bucketStart)}</span>
      </div>
    </div>
  );
}

export function PodWorkspacePage() {
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

    requestJson(apiRoots.kube, buildResourcePath("/summary", activeFilters))
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

    requestJson(apiRoots.kube, buildResourcePath("/trend", activeFilters))
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
      apiRoots.kube,
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
  const pageLabel = pageData.totalElements ? `Page ${pageData.number + 1} of ${pageCount} · ${pageData.totalElements} total pods` : "No runtime pods returned yet";
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
        eyebrow="Runtime workspace"
        title="Runtime Pod Workspace"
        summary="Kubernetes pods now land in the same aggregation-first shell as delivery and quality data, with summary cards, a daily runtime trend, and a searchable inventory exposed through the gateway."
        callouts={[`Gateway root: ${apiRoots.kube}`, "Summary: /summary", "Trend: /trend", "Inventory: /all"]}
        metrics={[
          {
            key: "pods-matched",
            label: "Pods matched",
            value: formatNumber(summaryData?.totalCount, 0, "0"),
            detail: `${formatNumber(summaryData?.namespaceCount, 0, "0")} namespaces in the current workspace scope.`,
            toneClass: "metric-card--blue",
          },
          {
            key: "deleted-pods",
            label: "Deleted pods",
            value: formatNumber(summaryData?.deletedCount, 0, "0"),
            detail: `${formatNumber(summaryData?.activeCount, 0, "0")} active pods remain.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-sync",
            label: "Latest sync",
            value: formatDateTime(summaryData?.latestSyncTime),
            detail: `${formatNumber(trendData.length, 0, "0")} runtime trend buckets visible.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "active-filters",
            label: "Active filters",
            value: formatNumber(activeFilterCount, 0, "0"),
            detail: "Runtime filters stay synchronized across chart and inventory views.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Current snapshot</p>
            <h2>Runtime pod dashboard cards</h2>
          </div>
          <p className="page-summary">Summary cards use the same active filters as the chart and inventory below.</p>
        </div>

        {summaryStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current runtime summary.</h3>
            <p className="muted">The cards are aggregated from the same pod inventory the browser uses for live drill-in.</p>
          </div>
        ) : null}

        {summaryStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Summary unavailable</p>
            <h3>Runtime dashboard cards could not be loaded.</h3>
            <p className="muted">{summaryErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {summaryStatus === "ready" && summaryData ? (
          <div className="summary-grid">
            <article className="summary-stat">
              <p className="eyebrow">Pods matched</p>
              <h3 className="summary-value">{formatNumber(summaryData.totalCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Namespaces</p>
              <h3 className="summary-value">{formatNumber(summaryData.namespaceCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Nodes</p>
              <h3 className="summary-value">{formatNumber(summaryData.nodeCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Active pods</p>
              <h3 className="summary-value">{formatNumber(summaryData.activeCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Deleted pods</p>
              <h3 className="summary-value">{formatNumber(summaryData.deletedCount, 0, "0")}</h3>
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
            <h2>Runtime pod activity trend</h2>
          </div>
          <p className="page-summary">The primary line tracks total pods and the secondary line tracks pods that remain active.</p>
        </div>

        {trendStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the runtime activity trend.</h3>
            <p className="muted">Trend buckets are derived from filtered pod sync activity grouped by UTC day.</p>
          </div>
        ) : null}

        {trendStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Trend unavailable</p>
            <h3>The runtime activity chart could not be loaded.</h3>
            <p className="muted">{trendErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No trend data</p>
            <h3>No pod buckets matched the current filters.</h3>
            <p className="muted">Try clearing the filters or widening the namespace and status criteria.</p>
          </div>
        ) : null}

        {trendStatus === "ready" && trendData.length > 0 ? (
          <>
            <PodTrendChart points={trendData} />
            <div className="trend-grid">
              {recentTrendPoints.map((point) => (
                <article key={point.bucketStart} className="summary-stat">
                  <p className="eyebrow">{formatDateTime(point.bucketStart)}</p>
                  <h3 className="summary-value">{formatNumber(point.podCount, 0, "0")}</h3>
                  <p className="muted">
                    Active {formatNumber(point.activeCount, 0, "0")} · Deleted {formatNumber(point.deletedCount, 0, "0")} · Namespaces{" "}
                    {formatNumber(point.uniqueNamespaceCount, 0, "0")}
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
            <h2>Refine runtime inventory without leaving the shell</h2>
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
            <p className="eyebrow">Runtime inventory</p>
            <h2>Pods available through athena-boot-kube</h2>
          </div>
          <p className="page-summary">{pageLabel}</p>
        </div>

        {status === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current pod inventory.</h3>
            <p className="muted">Each row is loaded through the same kube gateway prefix the browser will use in production.</p>
          </div>
        ) : null}

        {status === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Gateway request failed</p>
            <h3>Live runtime inventory is not available right now.</h3>
            <p className="muted">{errorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No results</p>
            <h3>No pods matched the current filters.</h3>
            <p className="muted">Try clearing the filters or widening the runtime criteria.</p>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length > 0 ? (
          <>
            <div className="table-shell">
              <table className="data-table">
                <thead>
                  <tr>
                    <th scope="col">Project</th>
                    <th scope="col">Namespace</th>
                    <th scope="col">Pod</th>
                    <th scope="col">Node</th>
                    <th scope="col">Status</th>
                    <th scope="col">Containers</th>
                    <th scope="col">Last sync</th>
                  </tr>
                </thead>
                <tbody>
                  {pageData.content.map((row) => (
                    <tr key={`pod-${row.id}`}>
                      <td>{row.project}</td>
                      <td>{row.namespace}</td>
                      <td>{row.name}</td>
                      <td>{row.nodeName}</td>
                      <td>
                        <span className={`status-chip ${getPodStatusToneClass(row.status)}`}>{row.status ?? "Unknown"}</span>
                      </td>
                      <td>{formatNumber(row.containerCount, 0, "0")}</td>
                      <td>{formatDateTime(row.lastSync)}</td>
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

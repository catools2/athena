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

const filterFields = [
  { key: "project", label: "Project code", placeholder: "ATH" },
  { key: "name", label: "Spec name", placeholder: "OpenApi" },
  { key: "title", label: "Spec title", placeholder: "Customer API" },
  { key: "version", label: "Version", placeholder: "1.0.0" },
  { key: "freshness", label: "Freshness", placeholder: "AGING" },
];

const initialFilters = getInitialFilters(filterFields);

function getDriftTone(status) {
  switch ((status ?? "").toLowerCase()) {
    case "fresh":
      return "status-chip status-chip--fresh";
    case "aging":
      return "status-chip status-chip--aging";
    case "stale":
      return "status-chip status-chip--stale";
    default:
      return "status-chip status-chip--unknown";
  }
}

export function ApiSpecWorkspacePage() {
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
  const [freshnessData, setFreshnessData] = useState(null);
  const [freshnessStatus, setFreshnessStatus] = useState("loading");
  const [freshnessErrorMessage, setFreshnessErrorMessage] = useState("");
  const [driftData, setDriftData] = useState(emptyPage);
  const [driftStatus, setDriftStatus] = useState("loading");
  const [driftErrorMessage, setDriftErrorMessage] = useState("");
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

    requestJson(apiRoots.spec, buildResourcePath("/summary", activeFilters))
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

    setFreshnessStatus("loading");
    setFreshnessErrorMessage("");

    requestJson(apiRoots.spec, buildResourcePath("/freshness", activeFilters))
      .then((payload) => {
        if (isCancelled) {
          return;
        }

        setFreshnessData(payload ?? null);
        setFreshnessStatus("ready");
      })
      .catch((error) => {
        if (isCancelled) {
          return;
        }

        setFreshnessData(null);
        setFreshnessErrorMessage(getErrorMessage(error));
        setFreshnessStatus("error");
      });

    return () => {
      isCancelled = true;
    };
  }, [activeFilters, serializedFilters, reloadKey]);

  useEffect(() => {
    let isCancelled = false;

    setDriftStatus("loading");
    setDriftErrorMessage("");

    requestJson(
      apiRoots.spec,
      buildPagedResourcePath("/drift", {
        pageIndex: 0,
        pageSize: 6,
        sort: "syncAgeDays",
        direction: "DESC",
        filters: activeFilters,
      }),
    )
      .then((payload) => {
        if (isCancelled) {
          return;
        }

        setDriftData(parsePagePayload(payload));
        setDriftStatus("ready");
      })
      .catch((error) => {
        if (isCancelled) {
          return;
        }

        setDriftData(emptyPage);
        setDriftErrorMessage(getErrorMessage(error));
        setDriftStatus("error");
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
      apiRoots.spec,
      buildPagedResourcePath("/all", {
        pageIndex,
        pageSize: 10,
        sort: "name",
        direction: "ASC",
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
        eyebrow="API workspace"
        title="API Specification Workspace"
        summary="The spec workspace now combines filtered inventory, freshness coverage, a drift queue, and direct access to full specification detail without leaving the gateway-routed Athena shell."
        callouts={[`Gateway root: ${apiRoots.spec}`, "Summary: /summary", "Freshness: /freshness", "Drift: /drift"]}
        metrics={[
          {
            key: "specs-matched",
            label: "Catalog coverage",
            value: formatNumber(summaryData?.specCount, 0, "0"),
            detail: `${formatNumber(summaryData?.pathCount, 0, "0")} tracked paths across the current filter set.`,
            toneClass: "metric-card--blue",
          },
          {
            key: "stale-specs",
            label: "Stale specs",
            value: formatNumber(freshnessData?.staleCount, 0, "0"),
            detail: `${formatNumber(freshnessData?.agingCount, 0, "0")} aging with latest sync ${formatDateTime(freshnessData?.latestSyncTime)}.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "drift-queue",
            label: "Drift queue",
            value: formatNumber(driftData.totalElements, 0, "0"),
            detail: `${formatNumber(Math.min(driftData.content.length, 6), 0, "0")} highest-drift rows surfaced above the inventory.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "active-filters",
            label: "Active filters",
            value: formatNumber(activeFilterCount, 0, "0"),
            detail: "URL-backed filter state stays synchronized across summary and inventory views.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Catalog summary</p>
            <h2>API catalog snapshot</h2>
          </div>
          <p className="page-summary">Summary cards and freshness signals stay aligned with the current filter set.</p>
        </div>

        {summaryStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current API catalog summary.</h3>
            <p className="muted">The summary aggregates the same filtered inventory shown in the workspace table.</p>
          </div>
        ) : null}

        {summaryStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Summary unavailable</p>
            <h3>Workspace totals could not be loaded right now.</h3>
            <p className="muted">{summaryErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {summaryStatus === "ready" && summaryData ? (
          <div className="summary-grid">
            <article className="summary-stat">
              <p className="eyebrow">Specs matched</p>
              <h3 className="summary-value">{formatNumber(summaryData.specCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Projects matched</p>
              <h3 className="summary-value">{formatNumber(summaryData.projectCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Tracked paths</p>
              <h3 className="summary-value">{formatNumber(summaryData.pathCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Stale syncs</p>
              <h3 className="summary-value">{formatNumber(summaryData.staleSpecCount, 0, "0")}</h3>
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
            <p className="eyebrow">Freshness coverage</p>
            <h2>Spec sync distribution</h2>
          </div>
          <p className="page-summary">
            Fresh means within {freshnessData?.warningWindowDays ?? 7} days. Stale means older than {freshnessData?.freshnessWindowDays ?? 30} days.
          </p>
        </div>

        {freshnessStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Computing freshness coverage for the active API scope.</h3>
            <p className="muted">These buckets show whether spec synchronization is keeping pace with delivery change.</p>
          </div>
        ) : null}

        {freshnessStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Freshness unavailable</p>
            <h3>The sync distribution could not be loaded.</h3>
            <p className="muted">{freshnessErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {freshnessStatus === "ready" && freshnessData ? (
          <div className="summary-grid">
            <article className="summary-stat">
              <p className="eyebrow">Fresh</p>
              <h3 className="summary-value">{formatNumber(freshnessData.freshCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Aging</p>
              <h3 className="summary-value">{formatNumber(freshnessData.agingCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Stale</p>
              <h3 className="summary-value">{formatNumber(freshnessData.staleCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Unknown sync</p>
              <h3 className="summary-value">{formatNumber(freshnessData.unknownSyncCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Latest sync</p>
              <h3 className="summary-value summary-value--compact">{formatDateTime(freshnessData.latestSyncTime)}</h3>
            </article>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Drift queue</p>
            <h2>Specs that need attention</h2>
          </div>
          <p className="page-summary">The drift queue prioritizes aging, stale, and unsynced specifications from the current filtered scope.</p>
        </div>

        {driftStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the highest-risk spec drift candidates.</h3>
            <p className="muted">Drift candidates are sorted by sync age so stale APIs surface first.</p>
          </div>
        ) : null}

        {driftStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Drift unavailable</p>
            <h3>The drift queue could not be loaded.</h3>
            <p className="muted">{driftErrorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {driftStatus === "ready" && driftData.content.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No drift candidates</p>
            <h3>No aging or stale specifications matched the current filters.</h3>
            <p className="muted">That usually means the active slice is synchronized within the freshness window.</p>
          </div>
        ) : null}

        {driftStatus === "ready" && driftData.content.length > 0 ? (
          <div className="table-shell">
            <table className="data-table">
              <thead>
                <tr>
                  <th scope="col">Name</th>
                  <th scope="col">Project</th>
                  <th scope="col">Paths</th>
                  <th scope="col">Sync age</th>
                  <th scope="col">Last sync</th>
                </tr>
              </thead>
              <tbody>
                {driftData.content.map((row) => (
                  <tr key={`drift-${row.id}`}>
                    <td>
                      <Link className="detail-link" to={`/apis/specs/${row.id}`}>
                        {row.name}
                      </Link>
                    </td>
                    <td>{row.project}</td>
                    <td>{formatNumber(row.pathCount, 0, "0")}</td>
                    <td>
                      <span className={getDriftTone(row.driftStatus)}>{row.driftStatus}</span>
                    </td>
                    <td>{formatNumber(row.syncAgeDays, 0, "0")}d</td>
                    <td>{formatDateTime(row.lastSyncTime)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Live filters</p>
            <h2>Refine the API workspace without leaving the shell</h2>
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
            <p className="eyebrow">Specification inventory</p>
            <h2>Specifications available through athena-boot-spec</h2>
          </div>
          <p className="page-summary">{pageLabel}</p>
        </div>

        {status === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current specification inventory.</h3>
            <p className="muted">The inventory view stays lightweight and links into the full detail payload only when you open a spec.</p>
          </div>
        ) : null}

        {status === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Gateway request failed</p>
            <h3>Live API catalog data is not available right now.</h3>
            <p className="muted">{errorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No results</p>
            <h3>No specifications matched the current filters.</h3>
            <p className="muted">Try clearing the filters or widening the search terms.</p>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length > 0 ? (
          <>
            <div className="table-shell">
              <table className="data-table">
                <thead>
                  <tr>
                    <th scope="col">Project</th>
                    <th scope="col">Name</th>
                    <th scope="col">Title</th>
                    <th scope="col">Version</th>
                    <th scope="col">Paths</th>
                    <th scope="col">Last sync</th>
                  </tr>
                </thead>
                <tbody>
                  {pageData.content.map((row) => (
                    <tr key={`spec-${row.id}`}>
                      <td>{row.project}</td>
                      <td>
                        <Link className="detail-link" to={`/apis/specs/${row.id}`}>
                          {row.name}
                        </Link>
                      </td>
                      <td>{row.title}</td>
                      <td>{row.version}</td>
                      <td>{formatNumber(row.pathCount, 0, "0")}</td>
                      <td>{formatDateTime(row.lastSyncTime)}</td>
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

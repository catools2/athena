import { useEffect, useState } from "react";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";

const emptyPage = {
  content: [],
  number: 0,
  size: 10,
  totalElements: 0,
  totalPages: 0,
};

function buildFilterParams(filters) {
  const params = new URLSearchParams();

  for (const [key, value] of Object.entries(filters)) {
    if (typeof value === "string" && value.trim()) {
      params.set(key, value.trim());
    }
  }

  return params;
}

function buildQueryString(config, pageIndex, filters) {
  const params = buildFilterParams(filters);
  params.set("page", String(pageIndex));
  params.set("size", String(config.pageSize ?? 10));
  params.set("sort", config.sort);
  params.set("direction", config.direction ?? "ASC");

  return params.toString();
}

function buildSummaryPath(config, filters) {
  const params = buildFilterParams(filters).toString();
  return params ? `${config.summaryEndpoint}?${params}` : config.summaryEndpoint;
}

function parsePagePayload(payload) {
  const pageInfo = payload?.page ?? payload ?? {};
  const content = Array.isArray(payload?.content) ? payload.content : [];

  return {
    content,
    number: pageInfo.number ?? payload?.number ?? 0,
    size: pageInfo.size ?? payload?.size ?? content.length,
    totalElements: pageInfo.totalElements ?? payload?.totalElements ?? content.length,
    totalPages: pageInfo.totalPages ?? payload?.totalPages ?? (content.length > 0 ? 1 : 0),
  };
}

function getInitialFilters(filters) {
  return Object.fromEntries(filters.map((filter) => [filter.key, ""]));
}

function normalizeFilters(filters) {
  return Object.fromEntries(Object.entries(filters).map(([key, value]) => [key, typeof value === "string" ? value.trim() : value]));
}

function getErrorMessage(error) {
  if (error?.message) {
    return error.message;
  }

  return "Unable to load data through the gateway.";
}

export function CoreCatalogPage(config) {
  const apiRoot = config.apiRoot ?? apiRoots.core;
  const serviceName = config.serviceName ?? "the selected service";
  const initialFilters = getInitialFilters(config.filters);
  const [draftFilters, setDraftFilters] = useState(initialFilters);
  const [activeFilters, setActiveFilters] = useState(initialFilters);
  const [pageIndex, setPageIndex] = useState(0);
  const [pageData, setPageData] = useState(emptyPage);
  const [status, setStatus] = useState("loading");
  const [errorMessage, setErrorMessage] = useState("");
  const [summaryStatus, setSummaryStatus] = useState(config.summaryEndpoint ? "loading" : "idle");
  const [summaryData, setSummaryData] = useState(null);
  const [summaryErrorMessage, setSummaryErrorMessage] = useState("");
  const serializedFilters = JSON.stringify(activeFilters);

  useEffect(() => {
    if (!config.summaryEndpoint) {
      return undefined;
    }

    let isCancelled = false;
    const summaryPath = buildSummaryPath(config, activeFilters);

    setSummaryStatus("loading");
    setSummaryErrorMessage("");

    requestJson(apiRoot, summaryPath)
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
  }, [config, apiRoot, serializedFilters, activeFilters]);

  useEffect(() => {
    let isCancelled = false;
    const queryString = buildQueryString(config, pageIndex, activeFilters);

    setStatus("loading");
    setErrorMessage("");

    requestJson(apiRoot, `${config.endpoint}?${queryString}`)
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
  }, [config, pageIndex, serializedFilters, activeFilters]);

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
    setPageIndex(0);
    setActiveFilters(normalizeFilters(draftFilters));
  }

  function handleClearFilters() {
    const cleared = getInitialFilters(config.filters);
    setDraftFilters(cleared);
    setActiveFilters(cleared);
    setPageIndex(0);
  }

  function handleRetry() {
    setActiveFilters((current) => ({ ...current }));
  }

  return (
    <div className="page-grid">
      <DashboardPageHero
        eyebrow={config.eyebrow}
        title={config.title}
        summary={config.summary}
        callouts={[`Gateway root: ${apiRoot}`, `Endpoint: ${config.endpoint}`, `Sort: ${config.sort}`]}
        metrics={[
          {
            key: "rows-matched",
            label: "Rows matched",
            value: String(pageData.totalElements ?? 0),
            detail: pageData.totalElements ? `Page ${pageData.number + 1} of ${pageCount}` : "Waiting for the first gateway response.",
            toneClass: "metric-card--blue",
          },
          {
            key: "active-filters",
            label: "Active filters",
            value: String(activeFilterCount),
            detail: `${config.filters.length} searchable fields available in this catalog.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "sort-key",
            label: "Sort key",
            value: config.sort ?? "—",
            detail: `${config.direction ?? "ASC"} order through ${serviceName}.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "browser-ingress",
            label: "Browser ingress",
            value: apiRoot,
            detail: "Single gateway-backed catalog entry point.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      {config.summaryEndpoint ? (
        <section className="panel">
          <div className="section-header">
            <div>
              <p className="eyebrow">{config.summaryEyebrow ?? "Workspace summary"}</p>
              <h2>{config.summaryTitle ?? `Current ${config.title} snapshot`}</h2>
            </div>
            {config.summaryDescription ? <p className="page-summary">{config.summaryDescription}</p> : null}
          </div>

          {summaryStatus === "loading" ? (
            <div className="empty-state">
              <p className="eyebrow">Loading</p>
              <h3>Pulling the current workspace summary from {serviceName}.</h3>
              <p className="muted">Summary cards follow the same active filters as the inventory table below.</p>
            </div>
          ) : null}

          {summaryStatus === "error" ? (
            <div className="error-card">
              <p className="eyebrow">Summary unavailable</p>
              <h3>Workspace totals could not be loaded right now.</h3>
              <p className="muted">{summaryErrorMessage}</p>
            </div>
          ) : null}

          {summaryStatus === "ready" && summaryData ? (
            <div className="summary-grid">
              {config.summaryCards.map((card) => (
                <article key={card.key} className="summary-stat">
                  <p className="eyebrow">{card.label}</p>
                  <h3 className="summary-value">{card.render(summaryData)}</h3>
                </article>
              ))}
            </div>
          ) : null}
        </section>
      ) : null}

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Live filters</p>
            <h2>{config.filterTitle ?? "Refine the live inventory without leaving the shell"}</h2>
          </div>
        </div>

        <form className="filter-form" onSubmit={handleApplyFilters}>
          <div className="filter-grid">
            {config.filters.map((filter) => (
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
            <p className="eyebrow">Live inventory</p>
            <h2>{config.tableTitle}</h2>
          </div>
          <p className="page-summary">{pageLabel}</p>
        </div>

        {status === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling the current inventory page from {serviceName}.</h3>
            <p className="muted">The request is routed through the same gateway prefixes the browser will keep using in production.</p>
          </div>
        ) : null}

        {status === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Gateway request failed</p>
            <h3>Live catalog data is not available right now.</h3>
            <p className="muted">{errorMessage}</p>
            <button type="button" className="button button--primary" onClick={handleRetry}>
              Retry
            </button>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No results</p>
            <h3>No records matched the current filters.</h3>
            <p className="muted">Try clearing the filters or widening the search terms.</p>
          </div>
        ) : null}

        {status === "ready" && pageData.content.length > 0 ? (
          <>
            <div className="table-shell">
              <table className="data-table">
                <thead>
                  <tr>
                    {config.columns.map((column) => (
                      <th key={column.key} scope="col">
                        {column.label}
                      </th>
                    ))}
                  </tr>
                </thead>
                <tbody>
                  {pageData.content.map((row) => (
                    <tr key={`${config.title}-${row.id}`}>
                      {config.columns.map((column) => (
                        <td key={column.key}>{column.render(row)}</td>
                      ))}
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            <div className="pagination-bar">
              <button type="button" className="button button--ghost" disabled={pageData.number <= 0} onClick={() => setPageIndex((current) => Math.max(0, current - 1))}>
                Previous page
              </button>
              <span className="page-summary">{pageLabel}</span>
              <button
                type="button"
                className="button button--ghost"
                disabled={pageData.totalPages === 0 || pageData.number >= pageData.totalPages - 1}
                onClick={() => setPageIndex((current) => current + 1)}
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

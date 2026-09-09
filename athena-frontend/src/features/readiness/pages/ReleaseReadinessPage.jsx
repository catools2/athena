import { useEffect, useMemo, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { buildPagedResourcePath, buildResourcePath, formatDateTime, formatNumber, getErrorMessage, parsePagePayload } from "../../../shared/ui/workspacePageUtils";

const gateDefinitions = [
  {
    key: "contracts",
    label: "API contracts",
    root: apiRoots.spec,
    paths: ["/summary", "/freshness"],
    detail: "Specifications are synchronized and not drifting behind the release.",
    href: "/apis/specs",
  },
  {
    key: "source",
    label: "Source sync",
    root: apiRoots.git,
    paths: ["/summary"],
    detail: "Repositories have recent source data available for delivery decisions.",
    href: "/git/repositories",
  },
  {
    key: "delivery",
    label: "Delivery flow",
    root: apiRoots.pipeline,
    paths: ["/summary"],
    detail: "Pipeline activity shows a release path that is complete or actively progressing.",
    href: "/pipelines/runs",
  },
  {
    key: "quality",
    label: "Quality sign-off",
    root: apiRoots.tms,
    paths: ["/summary"],
    detail: "Test executions provide enough evidence for release communication.",
    href: "/quality/executions",
  },
];

const initialFilters = { project: "", version: "", environment: "" };

function getFilters(searchParams) {
  return {
    project: searchParams.get("project") ?? "",
    version: searchParams.get("version") ?? "",
    environment: searchParams.get("environment") ?? "",
  };
}

function emptyCoreOptions() {
  return { projects: [], versions: [], environments: [] };
}

function getOptionValue(option) {
  return option?.code ?? option?.name ?? "";
}

function getGateState(key, data) {
  if (data.status === "error") {
    return "unknown";
  }

  if (key === "contracts") {
    if (Number(data.freshness?.staleCount) > 0) return "blocked";
    if (Number(data.freshness?.agingCount) > 0) return "watch";
    return "ready";
  }

  if (key === "source") {
    if (Number(data.summary?.staleCount) > 0) return "blocked";
    if (Number(data.summary?.agingCount) > 0) return "watch";
    return "ready";
  }

  if (key === "delivery") {
    if (Number(data.summary?.totalCount) > 0 && Number(data.summary?.completedCount) === 0) return "blocked";
    if (Number(data.summary?.inProgressCount) > 0) return "watch";
    return "ready";
  }

  if (Number(data.summary?.totalCount) > 0 && Number(data.summary?.executedCount) === 0) return "blocked";
  if (Number(data.summary?.pendingCount) > 0) return "watch";
  return "ready";
}

function stateLabel(state) {
  return { ready: "Ready", watch: "Watch", blocked: "Blocked", unknown: "Unavailable" }[state];
}

function stateTone(state) {
  return `readiness-state readiness-state--${state}`;
}

function getGateHeadline(gate, data) {
  if (data.status === "error") return "Data unavailable";
  if (gate.key === "contracts") return `${formatNumber(data.summary?.specCount, 0, "0")} specs · ${formatNumber(data.freshness?.staleCount, 0, "0")} stale`;
  if (gate.key === "source") return `${formatNumber(data.summary?.totalCount, 0, "0")} repositories · ${formatNumber(data.summary?.freshCount, 0, "0")} fresh`;
  if (gate.key === "delivery") return `${formatNumber(data.summary?.completedCount, 0, "0")} completed · ${formatNumber(data.summary?.inProgressCount, 0, "0")} active`;
  return `${formatNumber(data.summary?.executedCount, 0, "0")} executed · ${formatNumber(data.summary?.pendingCount, 0, "0")} pending`;
}

function getGateTimestamp(gate, data) {
  if (gate.key === "contracts") return data.freshness?.latestSyncTime;
  if (gate.key === "source") return data.summary?.latestSyncTime;
  if (gate.key === "delivery") return data.summary?.latestStartTime;
  return data.summary?.latestActivityTime;
}

export function ReleaseReadinessPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const activeFilters = useMemo(() => getFilters(searchParams), [searchParams]);
  const [draftFilters, setDraftFilters] = useState(activeFilters);
  const [coreOptions, setCoreOptions] = useState(emptyCoreOptions);
  const [coreStatus, setCoreStatus] = useState("loading");
  const [coreErrorMessage, setCoreErrorMessage] = useState("");
  const [gates, setGates] = useState(() => Object.fromEntries(gateDefinitions.map((gate) => [gate.key, { status: "loading", summary: null, freshness: null, error: "" }])));

  useEffect(() => {
    setDraftFilters(activeFilters);
  }, [activeFilters]);

  useEffect(() => {
    let cancelled = false;

    setCoreStatus("loading");
    setCoreErrorMessage("");
    requestJson(apiRoots.core, buildPagedResourcePath("/project/all", { pageIndex: 0, pageSize: 100, sort: "code", direction: "ASC" }))
      .then((payload) => {
        if (!cancelled) {
          setCoreOptions((current) => ({ ...current, projects: parsePagePayload(payload).content }));
          setCoreStatus("ready");
        }
      })
      .catch((error) => {
        if (!cancelled) {
          setCoreErrorMessage(getErrorMessage(error));
          setCoreStatus("error");
        }
      });

    return () => {
      cancelled = true;
    };
  }, []);

  useEffect(() => {
    let cancelled = false;

    if (!activeFilters.project) {
      setCoreOptions((current) => ({ ...current, versions: [], environments: [] }));
      return undefined;
    }

    Promise.all([
      requestJson(
        apiRoots.core,
        buildPagedResourcePath("/version/all", { pageIndex: 0, pageSize: 100, sort: "code", direction: "ASC", filters: { project: activeFilters.project } }),
      ),
      requestJson(
        apiRoots.core,
        buildPagedResourcePath("/environment/all", { pageIndex: 0, pageSize: 100, sort: "code", direction: "ASC", filters: { project: activeFilters.project } }),
      ),
    ])
      .then(([versions, environments]) => {
        if (!cancelled) {
          setCoreOptions((current) => ({ ...current, versions: parsePagePayload(versions).content, environments: parsePagePayload(environments).content }));
        }
      })
      .catch((error) => {
        if (!cancelled) setCoreErrorMessage(getErrorMessage(error));
      });

    return () => {
      cancelled = true;
    };
  }, [activeFilters.project]);

  useEffect(() => {
    let cancelled = false;
    const nextGates = Object.fromEntries(gateDefinitions.map((gate) => [gate.key, { status: "loading", summary: null, freshness: null, error: "" }]));
    setGates(nextGates);

    Promise.all(
      gateDefinitions.map(async (gate) => {
        try {
          const payloads = await Promise.all(gate.paths.map((path) => requestJson(gate.root, buildResourcePath(path, activeFilters))));
          return [gate.key, { status: "ready", summary: payloads[0] ?? null, freshness: payloads[1] ?? null, error: "" }];
        } catch (error) {
          return [gate.key, { status: "error", summary: null, freshness: null, error: getErrorMessage(error) }];
        }
      }),
    ).then((results) => {
      if (!cancelled) setGates(Object.fromEntries(results));
    });

    return () => {
      cancelled = true;
    };
  }, [activeFilters]);

  const gateRows = gateDefinitions.map((gate) => ({ ...gate, data: gates[gate.key], state: getGateState(gate.key, gates[gate.key]) }));
  const blockedCount = gateRows.filter(({ state }) => state === "blocked").length;
  const watchCount = gateRows.filter(({ state }) => state === "watch" || state === "unknown").length;
  const overallState = blockedCount > 0 ? "blocked" : watchCount > 0 ? "watch" : gateRows.some(({ state }) => state === "ready") ? "ready" : "unknown";

  function applyFilters(event) {
    event.preventDefault();
    const nextParams = new URLSearchParams();
    Object.entries(draftFilters).forEach(([key, value]) => {
      if (value.trim()) nextParams.set(key, value.trim());
    });
    setSearchParams(nextParams);
  }

  function clearFilters() {
    setDraftFilters(initialFilters);
    setSearchParams({});
  }

  function handleProjectChange(value) {
    setDraftFilters((current) => ({ ...current, project: value, version: "", environment: "" }));
  }

  return (
    <div className="readiness-page">
      <header className="readiness-header">
        <div>
          <p className="readiness-kicker">Release control</p>
          <h1>Release readiness</h1>
          <p className="readiness-intro">One decision surface for contracts, source, delivery, and quality evidence.</p>
        </div>
        <div className={stateTone(overallState)} aria-label={`Overall release status: ${stateLabel(overallState)}`}>
          <span className="readiness-state__dot" />
          {stateLabel(overallState)}
        </div>
      </header>

      <form className="readiness-filters" onSubmit={applyFilters}>
        <label>
          <span>Project</span>
          <select aria-label="Project" value={draftFilters.project} onChange={(event) => handleProjectChange(event.target.value)}>
            <option value="">All projects</option>
            {coreOptions.projects.map((project) => (
              <option key={project.id ?? getOptionValue(project)} value={getOptionValue(project)}>
                {project.code ?? project.name}
              </option>
            ))}
          </select>
        </label>
        <label>
          <span>Version</span>
          <select
            aria-label="Version"
            value={draftFilters.version}
            disabled={!draftFilters.project || coreStatus === "loading"}
            onChange={(event) => setDraftFilters((current) => ({ ...current, version: event.target.value }))}
          >
            <option value="">All versions</option>
            {coreOptions.versions.map((version) => (
              <option key={version.id ?? getOptionValue(version)} value={getOptionValue(version)}>
                {version.code ?? version.name}
              </option>
            ))}
          </select>
        </label>
        <label>
          <span>Environment</span>
          <select
            aria-label="Environment"
            value={draftFilters.environment}
            disabled={!draftFilters.project || coreStatus === "loading"}
            onChange={(event) => setDraftFilters((current) => ({ ...current, environment: event.target.value }))}
          >
            <option value="">All environments</option>
            {coreOptions.environments.map((environment) => (
              <option key={environment.id ?? getOptionValue(environment)} value={getOptionValue(environment)}>
                {environment.code ?? environment.name}
              </option>
            ))}
          </select>
        </label>
        <div className="readiness-filter-actions">
          <button type="submit">Check release</button>
          <button type="button" className="button-secondary" onClick={clearFilters}>
            Clear
          </button>
        </div>
      </form>
      {(coreStatus === "error" || coreErrorMessage) && <p className="readiness-context-error">Core context is unavailable: {coreErrorMessage}</p>}

      <section className="readiness-summary" aria-label="Release summary">
        <div>
          <span className="readiness-summary__label">Decision</span>
          <strong>{stateLabel(overallState)}</strong>
        </div>
        <div>
          <span className="readiness-summary__label">Blocked gates</span>
          <strong>{blockedCount}</strong>
        </div>
        <div>
          <span className="readiness-summary__label">Needs attention</span>
          <strong>{watchCount}</strong>
        </div>
        <div>
          <span className="readiness-summary__label">Scope</span>
          <strong>{activeFilters.project || "All projects"}</strong>
        </div>
      </section>

      <section className="readiness-gates" aria-label="Release gates">
        {gateRows.map((gate) => (
          <article className="readiness-gate" key={gate.key}>
            <div className="readiness-gate__status">
              <span className={stateTone(gate.state)}>{stateLabel(gate.state)}</span>
            </div>
            <div className="readiness-gate__body">
              <p className="readiness-gate__eyebrow">Gate {String(gateRows.indexOf(gate) + 1).padStart(2, "0")}</p>
              <h2>{gate.label}</h2>
              <p>{gate.detail}</p>
              <strong>{getGateHeadline(gate, gate.data)}</strong>
              <small>Latest activity: {formatDateTime(getGateTimestamp(gate, gate.data))}</small>
              {gate.data.error && <p className="readiness-gate__error">{gate.data.error}</p>}
            </div>
            <Link className="readiness-gate__link" to={gate.href}>
              Investigate <span aria-hidden="true">→</span>
            </Link>
          </article>
        ))}
      </section>
    </div>
  );
}

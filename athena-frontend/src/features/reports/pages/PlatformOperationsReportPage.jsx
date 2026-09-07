import { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";
import { formatDateTime, formatDuration, formatNumber, getErrorMessage } from "../../../shared/ui/workspacePageUtils";
import { ReportNavigation } from "../components/ReportNavigation";
import { ReportToolbar } from "../components/ReportToolbar";
import {
  buildReportPath,
  buildUpdatedSearchParams,
  buildWorkspaceHandoffPath,
  downloadReportCsv,
  downloadReportJson,
  getHealthToneClass,
  getQueryOptionValue,
  getTimeWindowDays,
  getTimeWindowLabel,
  matchesHealthFilter,
  reportHealthOptions,
  reportTimeWindowOptions,
  triggerReportPrint,
} from "../reportUtils";

const scopeOptions = [
  { key: "all", label: "All operations" },
  { key: "contracts", label: "Contracts" },
  { key: "repositories", label: "Repositories" },
  { key: "runtime", label: "Runtime" },
  { key: "performance", label: "Performance" },
];

const handoffRoutes = [
  {
    to: "/apis/specs",
    label: "API Specs",
    detail: "Review freshness, drift, and inventory when contract posture needs follow-up.",
    scope: "contracts",
  },
  {
    to: "/git/repositories",
    label: "Git Repositories",
    detail: "Move into repository inventory and sync posture when freshness slips.",
    scope: "repositories",
  },
  {
    to: "/runtime/pods",
    label: "Runtime Pods",
    detail: "Inspect pod inventory and churn when runtime posture turns unstable.",
    scope: "runtime",
  },
  {
    to: "/metrics/executions",
    label: "Metric Runs",
    detail: "Drill into telemetry inventory when latency and action counts need explanation.",
    scope: "performance",
  },
  {
    to: buildReportPath("/reports/exception-follow-up", { health: "attention" }),
    label: "Exception Follow-up",
    detail: "Escalate directly into the exception report when multiple operations need active follow-up.",
    scope: "all",
  },
];

function toNumber(value) {
  const numericValue = Number(value);
  return Number.isFinite(numericValue) ? numericValue : 0;
}

function getLatestTimestamp(values) {
  const validValues = values.filter(Boolean);
  if (validValues.length === 0) {
    return null;
  }

  return validValues.reduce((latestValue, currentValue) => (new Date(currentValue).getTime() > new Date(latestValue).getTime() ? currentValue : latestValue));
}

export function PlatformOperationsReportPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const timeWindow = getQueryOptionValue(searchParams, "window", reportTimeWindowOptions, "all");
  const healthFilter = getQueryOptionValue(searchParams, "health", reportHealthOptions, "all");
  const scopeFilter = getQueryOptionValue(searchParams, "scope", scopeOptions, "all");
  const [reportStatus, setReportStatus] = useState("loading");
  const [reportErrorMessage, setReportErrorMessage] = useState("");
  const [reportData, setReportData] = useState({
    specSummary: null,
    specFreshness: null,
    gitSummary: null,
    gitTrend: [],
    kubeSummary: null,
    kubeTrend: [],
    metricSummary: null,
    metricTrend: [],
  });

  useEffect(() => {
    let isCancelled = false;

    setReportErrorMessage("");
    const windowDays = getTimeWindowDays(timeWindow);

    Promise.all([
      requestJson(apiRoots.spec, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.spec, buildReportPath("/freshness", { windowDays })),
      requestJson(apiRoots.git, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.git, buildReportPath("/trend", { windowDays })),
      requestJson(apiRoots.kube, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.kube, buildReportPath("/trend", { windowDays })),
      requestJson(apiRoots.metric, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.metric, buildReportPath("/trend", { windowDays })),
    ])
      .then(([specSummary, specFreshness, gitSummary, gitTrend, kubeSummary, kubeTrend, metricSummary, metricTrend]) => {
        if (isCancelled) {
          return;
        }

        setReportData({
          specSummary: specSummary ?? null,
          specFreshness: specFreshness ?? null,
          gitSummary: gitSummary ?? null,
          gitTrend: Array.isArray(gitTrend) ? gitTrend : [],
          kubeSummary: kubeSummary ?? null,
          kubeTrend: Array.isArray(kubeTrend) ? kubeTrend : [],
          metricSummary: metricSummary ?? null,
          metricTrend: Array.isArray(metricTrend) ? metricTrend : [],
        });
        setReportStatus("ready");
      })
      .catch((error) => {
        if (isCancelled) {
          return;
        }

        setReportData({
          specSummary: null,
          specFreshness: null,
          gitSummary: null,
          gitTrend: [],
          kubeSummary: null,
          kubeTrend: [],
          metricSummary: null,
          metricTrend: [],
        });
        setReportErrorMessage(getErrorMessage(error));
        setReportStatus("error");
      });

    return () => {
      isCancelled = true;
    };
  }, [timeWindow]);

  const latestGitBucket = reportData.gitTrend.at(-1) ?? null;
  const latestKubeBucket = reportData.kubeTrend.at(-1) ?? null;
  const latestMetricBucket = reportData.metricTrend.at(-1) ?? null;
  const latestPlatformSignal = getLatestTimestamp([
    reportData.specFreshness?.latestSyncTime,
    reportData.gitSummary?.latestSyncTime,
    reportData.kubeSummary?.latestSyncTime,
    reportData.metricSummary?.latestActionTime,
  ]);
  const totalObservedRecords =
    toNumber(reportData.specSummary?.specCount) +
    toNumber(reportData.gitSummary?.totalCount) +
    toNumber(reportData.kubeSummary?.totalCount) +
    toNumber(reportData.metricSummary?.totalCount);

  function updateQuery(updates) {
    setSearchParams(
      buildUpdatedSearchParams(searchParams, updates, {
        window: "all",
        health: "all",
        scope: "all",
      }),
    );
  }

  function getSpecHealthState() {
    if (toNumber(reportData.specFreshness?.staleCount) > 0) {
      return "attention";
    }
    if (toNumber(reportData.specFreshness?.agingCount) > 0) {
      return "watch";
    }
    return "healthy";
  }

  function getGitHealthState() {
    if (toNumber(reportData.gitSummary?.staleCount) > 0) {
      return "attention";
    }
    if (toNumber(reportData.gitSummary?.agingCount) > 0) {
      return "watch";
    }
    return "healthy";
  }

  function getRuntimeHealthState() {
    if (toNumber(reportData.kubeSummary?.deletedCount) > 0) {
      return "attention";
    }
    if (toNumber(reportData.kubeSummary?.activeCount) === 0 && toNumber(reportData.kubeSummary?.totalCount) > 0) {
      return "watch";
    }
    return "healthy";
  }

  function getMetricHealthState() {
    if (toNumber(reportData.metricSummary?.slowestDuration) >= 600) {
      return "attention";
    }
    if (toNumber(reportData.metricSummary?.slowestDuration) >= 450) {
      return "watch";
    }
    return "healthy";
  }

  const domainRollups = [
    {
      label: "API Specs",
      scope: "contracts",
      health: getSpecHealthState(),
      headline: `${formatNumber(reportData.specSummary?.specCount, 0, "0")} specs`,
      detail: `${formatNumber(reportData.specFreshness?.staleCount, 0, "0")} stale · ${formatNumber(reportData.specSummary?.pathCount, 0, "0")} paths`,
      to: buildWorkspaceHandoffPath("/apis/specs", getSpecHealthState()),
    },
    {
      label: "Git Repositories",
      scope: "repositories",
      health: getGitHealthState(),
      headline: `${formatNumber(reportData.gitSummary?.totalCount, 0, "0")} repositories`,
      detail: `${formatNumber(reportData.gitSummary?.staleCount, 0, "0")} stale · ${formatNumber(reportData.gitSummary?.hostCount, 0, "0")} hosts`,
      to: buildWorkspaceHandoffPath("/git/repositories", getGitHealthState()),
    },
    {
      label: "Runtime Pods",
      scope: "runtime",
      health: getRuntimeHealthState(),
      headline: `${formatNumber(reportData.kubeSummary?.totalCount, 0, "0")} pods`,
      detail: `${formatNumber(reportData.kubeSummary?.activeCount, 0, "0")} active · ${formatNumber(reportData.kubeSummary?.deletedCount, 0, "0")} deleted`,
      to: buildWorkspaceHandoffPath("/runtime/pods", getRuntimeHealthState()),
    },
    {
      label: "Metric Runs",
      scope: "performance",
      health: getMetricHealthState(),
      headline: `${formatNumber(reportData.metricSummary?.totalCount, 0, "0")} executions`,
      detail: `${formatNumber(reportData.metricSummary?.uniqueActionCount, 0, "0")} actions · max ${formatDuration(reportData.metricSummary?.slowestDuration, 0)}`,
      to: buildWorkspaceHandoffPath("/metrics/executions", getMetricHealthState()),
    },
  ];

  const recentSignals = [
    {
      label: "Spec freshness",
      scope: "contracts",
      health: getSpecHealthState(),
      value: `${formatNumber(reportData.specFreshness?.staleCount, 0, "0")} stale`,
      detail: `Latest sync ${formatDateTime(reportData.specFreshness?.latestSyncTime)}`,
    },
    {
      label: "Repository sync",
      scope: "repositories",
      health: getGitHealthState(),
      value: `${formatNumber(latestGitBucket?.repositoryCount, 0, "0")} synced`,
      detail: latestGitBucket
        ? `${formatDateTime(latestGitBucket.bucketStart)} · ${formatNumber(latestGitBucket.uniqueHostCount, 0, "0")} hosts`
        : "No repository trend points returned yet.",
    },
    {
      label: "Runtime churn",
      scope: "runtime",
      health: getRuntimeHealthState(),
      value: `${formatNumber(latestKubeBucket?.podCount, 0, "0")} pods`,
      detail: latestKubeBucket
        ? `${formatDateTime(latestKubeBucket.bucketStart)} · ${formatNumber(latestKubeBucket.deletedCount, 0, "0")} deleted`
        : "No runtime trend points returned yet.",
    },
    {
      label: "Telemetry pressure",
      scope: "performance",
      health: getMetricHealthState(),
      value: `${formatNumber(latestMetricBucket?.metricCount, 0, "0")} executions`,
      detail: latestMetricBucket
        ? `${formatDateTime(latestMetricBucket.bucketStart)} · max ${formatDuration(latestMetricBucket.maxDuration, 0)}`
        : "No metric trend points returned yet.",
    },
  ];

  const activeScopeLabel = scopeOptions.find((option) => option.key === scopeFilter)?.label ?? "All operations";
  const filteredDomainRollups = domainRollups.filter((item) => (scopeFilter === "all" || item.scope === scopeFilter) && matchesHealthFilter(item.health, healthFilter));
  const filteredRecentSignals = recentSignals.filter((item) => (scopeFilter === "all" || item.scope === scopeFilter) && matchesHealthFilter(item.health, healthFilter));
  const filteredHandoffRoutes = handoffRoutes
    .filter((item) => {
      if (scopeFilter !== "all" && item.scope !== "all" && item.scope !== scopeFilter) {
        return false;
      }

      if (item.scope === "all") {
        return true;
      }

      const matchingRollup = domainRollups.find((rollup) => rollup.scope === item.scope);
      return matchesHealthFilter(matchingRollup?.health ?? "healthy", healthFilter);
    })
    .map((item) => {
      const matchingRollup = item.scope === "all" ? null : domainRollups.find((rollup) => rollup.scope === item.scope);
      return {
        ...item,
        to: buildWorkspaceHandoffPath(item.to, matchingRollup?.health ?? healthFilter),
      };
    });
  const attentionSignals = filteredDomainRollups.filter((item) => item.health === "attention").length;
  const watchSignals = filteredDomainRollups.filter((item) => item.health === "watch").length;
  const activeHealthLabel = reportHealthOptions.find((option) => option.key === healthFilter)?.label ?? "All health states";
  const generatedAt = new Date().toISOString();

  function handleExportJson() {
    downloadReportJson("athena-platform-operations-report.json", {
      report: "platform-operations",
      exportedAt: generatedAt,
      filters: {
        health: healthFilter,
        scope: scopeFilter,
        timeWindow,
      },
      summary: {
        totalObservedRecords,
        attentionSignals,
        watchSignals,
        latestPlatformSignal,
      },
      domainRollups: filteredDomainRollups,
      recentSignals: filteredRecentSignals,
      routes: filteredHandoffRoutes,
    });
  }

  function handleExportCsv() {
    downloadReportCsv(
      "athena-platform-operations-report.csv",
      [
        { key: "section", label: "Section" },
        { key: "label", label: "Label" },
        { key: "value", label: "Value" },
        { key: "detail", label: "Detail" },
      ],
      [
        {
          section: "Filters",
          label: "Active filters",
          value: `${activeScopeLabel} / ${activeHealthLabel} / ${getTimeWindowLabel(timeWindow)}`,
          detail: `Generated ${formatDateTime(generatedAt)}`,
        },
        {
          section: "Executive snapshot",
          label: "Observed records",
          value: formatNumber(totalObservedRecords, 0, "0"),
          detail: `${formatNumber(attentionSignals, 0, "0")} attention · ${formatNumber(watchSignals, 0, "0")} watch`,
        },
        {
          section: "Executive snapshot",
          label: "Latest platform signal",
          value: formatDateTime(latestPlatformSignal),
          detail: "Latest gateway-backed sync or telemetry activity across the platform slice",
        },
        ...filteredDomainRollups.map((item) => ({
          section: "Domain rollup",
          label: item.label,
          value: item.headline,
          detail: `${item.detail} · Health ${item.health}`,
        })),
        ...filteredRecentSignals.map((item) => ({
          section: "Recent signal",
          label: item.label,
          value: item.value,
          detail: `${item.detail} · Health ${item.health}`,
        })),
        ...filteredHandoffRoutes.map((item) => ({
          section: "Route handoff",
          label: item.label,
          value: item.to,
          detail: item.detail,
        })),
      ],
    );
  }

  return (
    <div className="page-grid">
      <DashboardPageHero
        eyebrow="Cross-domain report"
        title="Platform Operations Report"
        summary="This report narrows the platform view to contract freshness, repository sync posture, runtime churn, and telemetry pressure so external readers can assess platform readiness without mixing in delivery execution detail."
        callouts={[
          "Report route: /reports/platform-operations",
          `Scope: ${activeScopeLabel}`,
          `Health: ${activeHealthLabel}`,
          `Window: ${getTimeWindowLabel(timeWindow)}`,
          `Observed records: ${formatNumber(totalObservedRecords, 0, "0")}`,
          `Attention signals: ${formatNumber(attentionSignals, 0, "0")}`,
          `Latest platform signal: ${formatDateTime(latestPlatformSignal)}`,
        ]}
        metrics={[
          {
            key: "observed-records",
            label: "Observed records",
            value: formatNumber(totalObservedRecords, 0, "0"),
            detail: "Cross-domain platform scope across contracts, repos, runtime, and telemetry.",
            toneClass: "metric-card--blue",
          },
          {
            key: "attention-signals",
            label: "Attention signals",
            value: formatNumber(attentionSignals, 0, "0"),
            detail: `${formatNumber(watchSignals, 0, "0")} watch signals remain within the same report slice.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-signal",
            label: "Latest signal",
            value: formatDateTime(latestPlatformSignal),
            detail: `${formatNumber(filteredRecentSignals.length, 0, "0")} recent platform signals currently shown.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "workspace-handoffs",
            label: "Workspace handoffs",
            value: formatNumber(filteredHandoffRoutes.length, 0, "0"),
            detail: "Recommended operational drill-downs that match the active scope.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <ReportNavigation />

      <ReportToolbar
        title="Filter, export, or print the platform operations report"
        summary="Time windows narrow the freshness and runtime trend-derived sections while scope and health filters keep the report centered on the domains that need operational follow-up."
        timeWindow={timeWindow}
        onTimeWindowChange={(nextTimeWindow) => updateQuery({ window: nextTimeWindow })}
        filterGroups={[
          {
            label: "Operations scope",
            ariaLabel: "Platform operations scope filters",
            options: scopeOptions,
            value: scopeFilter,
            onChange: (nextScope) => updateQuery({ scope: nextScope }),
          },
          {
            label: "Health state",
            ariaLabel: "Platform operations health filters",
            options: reportHealthOptions,
            value: healthFilter,
            onChange: (nextHealth) => updateQuery({ health: nextHealth }),
          },
        ]}
        onExportJson={handleExportJson}
        onExportCsv={handleExportCsv}
        onPrint={triggerReportPrint}
      />

      <section className="panel report-print-summary print-only">
        <div className="section-header">
          <div>
            <p className="eyebrow">Stakeholder brief</p>
            <h2>Platform operations print summary</h2>
          </div>
          <p className="page-summary">This print layout keeps platform posture, current filters, and the hottest signals visible without the interactive toolbar.</p>
        </div>
        <div className="token-grid">
          <span className="token-chip">
            <strong>Generated</strong>
            {formatDateTime(generatedAt)}
          </span>
          <span className="token-chip">
            <strong>Scope</strong>
            {activeScopeLabel}
          </span>
          <span className="token-chip">
            <strong>Health</strong>
            {activeHealthLabel}
          </span>
          <span className="token-chip">
            <strong>Window</strong>
            {getTimeWindowLabel(timeWindow)}
          </span>
        </div>
        <div className="summary-grid">
          <article className="summary-stat">
            <p className="eyebrow">Observed records</p>
            <h3 className="summary-value">{formatNumber(totalObservedRecords, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Attention signals</p>
            <h3 className="summary-value">{formatNumber(attentionSignals, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Watch signals</p>
            <h3 className="summary-value">{formatNumber(watchSignals, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Latest platform signal</p>
            <h3 className="summary-value summary-value--compact">{formatDateTime(latestPlatformSignal)}</h3>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Executive snapshot</p>
            <h2>Platform posture at a glance</h2>
          </div>
          <p className="page-summary">These cards keep the most important operating counters visible before readers scan individual domain rollups.</p>
        </div>
        <div className="summary-grid">
          <article className="summary-stat">
            <p className="eyebrow">Specs tracked</p>
            <h3 className="summary-value">{formatNumber(reportData.specSummary?.specCount, 0, "0")}</h3>
            <p className="muted">
              {formatNumber(reportData.specSummary?.pathCount, 0, "0")} paths across {formatNumber(reportData.specSummary?.projectCount, 0, "0")} projects.
            </p>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Repositories tracked</p>
            <h3 className="summary-value">{formatNumber(reportData.gitSummary?.totalCount, 0, "0")}</h3>
            <p className="muted">
              {formatNumber(reportData.gitSummary?.hostCount, 0, "0")} hosts with {formatNumber(reportData.gitSummary?.staleCount, 0, "0")} stale.
            </p>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Runtime pods</p>
            <h3 className="summary-value">{formatNumber(reportData.kubeSummary?.totalCount, 0, "0")}</h3>
            <p className="muted">
              {formatNumber(reportData.kubeSummary?.activeCount, 0, "0")} active with {formatNumber(reportData.kubeSummary?.deletedCount, 0, "0")} deleted.
            </p>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Slowest telemetry</p>
            <h3 className="summary-value">{formatDuration(reportData.metricSummary?.slowestDuration, 0)}</h3>
            <p className="muted">
              {formatNumber(reportData.metricSummary?.uniqueActionCount, 0, "0")} actions with average {formatDuration(reportData.metricSummary?.averageDuration, 0)}.
            </p>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Operational rollups</p>
            <h2>Platform operations picture</h2>
          </div>
          <p className="page-summary">Each card maps to a live workspace route so the report can narrow attention and still hand off to the owning inventory page.</p>
        </div>

        {reportStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling platform signals through the gateway report contracts.</h3>
            <p className="muted">The report waits on spec, git, runtime, and metric summary plus trend endpoints.</p>
          </div>
        ) : null}

        {reportStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Report unavailable</p>
            <h3>The platform operations report could not load its live signals.</h3>
            <p className="muted">{reportErrorMessage}</p>
          </div>
        ) : null}

        {reportStatus === "ready" ? (
          filteredDomainRollups.length > 0 ? (
            <div className="readiness-grid" aria-label="Platform operations rollups">
              {filteredDomainRollups.map((item) => (
                <article key={item.label} className="status-card">
                  <p className="eyebrow">Domain</p>
                  <h3>{item.label}</h3>
                  <p className="summary-value summary-value--compact">{item.headline}</p>
                  <p className="muted">{item.detail}</p>
                  <span className={`status-chip ${getHealthToneClass(item.health)}`}>{item.health}</span>
                  <Link className="button button--ghost" to={item.to}>
                    Open workspace
                  </Link>
                </article>
              ))}
            </div>
          ) : (
            <div className="empty-state">
              <p className="eyebrow">No matches</p>
              <h3>No platform rollups match the current health filter.</h3>
              <p className="muted">Broaden the health state filter to restore the full platform operations picture.</p>
            </div>
          )
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Signal board</p>
            <h2>Recent platform signals</h2>
          </div>
          <p className="page-summary">These signal cards stay close to the freshest bucket or sync timestamp returned by each workspace contract.</p>
        </div>
        <div className="summary-grid">
          {filteredRecentSignals.map((item) => (
            <article key={item.label} className="summary-stat">
              <p className="eyebrow">{item.label}</p>
              <h3 className="summary-value summary-value--compact">{item.value}</h3>
              <p className="muted">{item.detail}</p>
              <span className={`status-chip ${getHealthToneClass(item.health)}`}>{item.health}</span>
            </article>
          ))}
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Drill-downs</p>
            <h2>Recommended drill-downs</h2>
          </div>
          <p className="page-summary">When a platform signal needs explanation, these routes take readers directly to the owning workspace inventory.</p>
        </div>
        {filteredHandoffRoutes.length > 0 ? (
          <div className="readiness-grid">
            {filteredHandoffRoutes.map((item) => (
              <article key={item.to} className="status-card">
                <p className="eyebrow">Workspace handoff</p>
                <h3>{item.label}</h3>
                <p className="muted">{item.detail}</p>
                <Link className="button button--ghost" to={item.to}>
                  Open workspace
                </Link>
              </article>
            ))}
          </div>
        ) : (
          <div className="empty-state">
            <p className="eyebrow">No matching handoffs</p>
            <h3>The current health filter excludes the related workspace routes.</h3>
            <p className="muted">Choose a broader health filter to restore the full set of recommended drill-downs.</p>
          </div>
        )}
      </section>
    </div>
  );
}

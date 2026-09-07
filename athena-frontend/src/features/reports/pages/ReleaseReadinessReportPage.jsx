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
  { key: "all", label: "All release signals" },
  { key: "contracts", label: "Contracts" },
  { key: "source", label: "Source sync" },
  { key: "delivery", label: "Delivery" },
  { key: "quality", label: "Quality" },
];

const handoffRoutes = [
  {
    to: "/apis/specs",
    label: "API Specs",
    detail: "Review stale or aging contracts before a release review goes forward.",
    scope: "contracts",
  },
  {
    to: "/git/repositories",
    label: "Git Repositories",
    detail: "Confirm repositories are syncing cleanly before delivery posture is treated as trustworthy.",
    scope: "source",
  },
  {
    to: "/pipelines/runs",
    label: "Pipeline Runs",
    detail: "Inspect active and completed runs when delivery flow becomes the gating signal.",
    scope: "delivery",
  },
  {
    to: "/quality/executions",
    label: "Quality Runs",
    detail: "Check pending and completed executions before sign-off or release communication.",
    scope: "quality",
  },
  {
    to: buildReportPath("/reports/exception-follow-up", { health: "attention" }),
    label: "Exception Follow-up",
    detail: "Escalate into the exception report when multiple release gates are already outside the healthy range.",
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

export function ReleaseReadinessReportPage() {
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
    pipelineSummary: null,
    pipelineTrend: [],
    qualitySummary: null,
    qualityTrend: [],
  });

  useEffect(() => {
    let isCancelled = false;

    setReportStatus("loading");
    setReportErrorMessage("");
    const windowDays = getTimeWindowDays(timeWindow);

    Promise.all([
      requestJson(apiRoots.spec, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.spec, buildReportPath("/freshness", { windowDays })),
      requestJson(apiRoots.git, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.git, buildReportPath("/trend", { windowDays })),
      requestJson(apiRoots.pipeline, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.pipeline, buildReportPath("/trend", { windowDays })),
      requestJson(apiRoots.tms, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.tms, buildReportPath("/trend", { windowDays })),
    ])
      .then(([specSummary, specFreshness, gitSummary, gitTrend, pipelineSummary, pipelineTrend, qualitySummary, qualityTrend]) => {
        if (isCancelled) {
          return;
        }

        setReportData({
          specSummary: specSummary ?? null,
          specFreshness: specFreshness ?? null,
          gitSummary: gitSummary ?? null,
          gitTrend: Array.isArray(gitTrend) ? gitTrend : [],
          pipelineSummary: pipelineSummary ?? null,
          pipelineTrend: Array.isArray(pipelineTrend) ? pipelineTrend : [],
          qualitySummary: qualitySummary ?? null,
          qualityTrend: Array.isArray(qualityTrend) ? qualityTrend : [],
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
          pipelineSummary: null,
          pipelineTrend: [],
          qualitySummary: null,
          qualityTrend: [],
        });
        setReportErrorMessage(getErrorMessage(error));
        setReportStatus("error");
      });

    return () => {
      isCancelled = true;
    };
  }, [timeWindow]);

  const latestGitBucket = reportData.gitTrend.at(-1) ?? null;
  const latestPipelineBucket = reportData.pipelineTrend.at(-1) ?? null;
  const latestQualityBucket = reportData.qualityTrend.at(-1) ?? null;
  const latestReleaseSignal = getLatestTimestamp([
    reportData.specFreshness?.latestSyncTime,
    reportData.gitSummary?.latestSyncTime,
    reportData.pipelineSummary?.latestStartTime,
    reportData.qualitySummary?.latestActivityTime,
  ]);

  function updateQuery(updates) {
    setSearchParams(
      buildUpdatedSearchParams(searchParams, updates, {
        window: "all",
        health: "all",
        scope: "all",
      }),
    );
  }

  function getContractHealthState() {
    if (toNumber(reportData.specFreshness?.staleCount) > 0) {
      return "attention";
    }
    if (toNumber(reportData.specFreshness?.agingCount) > 0) {
      return "watch";
    }
    return "healthy";
  }

  function getSourceHealthState() {
    if (toNumber(reportData.gitSummary?.staleCount) > 0) {
      return "attention";
    }
    if (toNumber(reportData.gitSummary?.agingCount) > 0) {
      return "watch";
    }
    return "healthy";
  }

  function getDeliveryHealthState() {
    if (toNumber(reportData.pipelineSummary?.completedCount) === 0 && toNumber(reportData.pipelineSummary?.totalCount) > 0) {
      return "attention";
    }
    if (toNumber(reportData.pipelineSummary?.inProgressCount) > 0) {
      return "watch";
    }
    return "healthy";
  }

  function getValidationHealthState() {
    if (toNumber(reportData.qualitySummary?.executedCount) === 0 && toNumber(reportData.qualitySummary?.totalCount) > 0) {
      return "attention";
    }
    if (toNumber(reportData.qualitySummary?.pendingCount) > 0) {
      return "watch";
    }
    return "healthy";
  }

  const domainRollups = [
    {
      label: "Contracts",
      scope: "contracts",
      health: getContractHealthState(),
      headline: `${formatNumber(reportData.specSummary?.specCount, 0, "0")} specs`,
      detail: `${formatNumber(reportData.specFreshness?.staleCount, 0, "0")} stale · ${formatNumber(reportData.specFreshness?.agingCount, 0, "0")} aging`,
      to: buildWorkspaceHandoffPath("/apis/specs", getContractHealthState()),
    },
    {
      label: "Source sync",
      scope: "source",
      health: getSourceHealthState(),
      headline: `${formatNumber(reportData.gitSummary?.totalCount, 0, "0")} repositories`,
      detail: `${formatNumber(reportData.gitSummary?.staleCount, 0, "0")} stale · ${formatNumber(reportData.gitSummary?.hostCount, 0, "0")} hosts`,
      to: buildWorkspaceHandoffPath("/git/repositories", getSourceHealthState()),
    },
    {
      label: "Delivery flow",
      scope: "delivery",
      health: getDeliveryHealthState(),
      headline: `${formatNumber(reportData.pipelineSummary?.totalCount, 0, "0")} runs`,
      detail: `${formatNumber(reportData.pipelineSummary?.completedCount, 0, "0")} completed · ${formatNumber(reportData.pipelineSummary?.inProgressCount, 0, "0")} in progress`,
      to: buildWorkspaceHandoffPath("/pipelines/runs", getDeliveryHealthState()),
    },
    {
      label: "Validation flow",
      scope: "quality",
      health: getValidationHealthState(),
      headline: `${formatNumber(reportData.qualitySummary?.totalCount, 0, "0")} executions`,
      detail: `${formatNumber(reportData.qualitySummary?.executedCount, 0, "0")} completed · ${formatNumber(reportData.qualitySummary?.pendingCount, 0, "0")} pending`,
      to: buildWorkspaceHandoffPath("/quality/executions", getValidationHealthState()),
    },
  ];

  const recentSignals = [
    {
      label: "Contract freshness",
      scope: "contracts",
      health: getContractHealthState(),
      value: `${formatNumber(reportData.specFreshness?.staleCount, 0, "0")} stale`,
      detail: `Latest sync ${formatDateTime(reportData.specFreshness?.latestSyncTime)}`,
    },
    {
      label: "Source sync",
      scope: "source",
      health: getSourceHealthState(),
      value: `${formatNumber(latestGitBucket?.repositoryCount, 0, "0")} synced`,
      detail: latestGitBucket
        ? `${formatDateTime(latestGitBucket.bucketStart)} · ${formatNumber(latestGitBucket.uniqueHostCount, 0, "0")} hosts`
        : "No repository trend points returned yet.",
    },
    {
      label: "Delivery signal",
      scope: "delivery",
      health: getDeliveryHealthState(),
      value: `${formatNumber(latestPipelineBucket?.pipelineCount, 0, "0")} runs`,
      detail: latestPipelineBucket
        ? `${formatDateTime(latestPipelineBucket.bucketStart)} · avg ${formatDuration(latestPipelineBucket.averageDuration, 0)}`
        : "No pipeline trend points returned yet.",
    },
    {
      label: "Validation signal",
      scope: "quality",
      health: getValidationHealthState(),
      value: `${formatNumber(latestQualityBucket?.executionCount, 0, "0")} executions`,
      detail: latestQualityBucket
        ? `${formatDateTime(latestQualityBucket.bucketStart)} · ${formatNumber(latestQualityBucket.executedCount, 0, "0")} completed`
        : "No quality trend points returned yet.",
    },
  ];

  const activeScopeLabel = scopeOptions.find((option) => option.key === scopeFilter)?.label ?? "All release signals";
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
  const releasePressure =
    toNumber(reportData.specFreshness?.staleCount) +
    toNumber(reportData.gitSummary?.staleCount) +
    toNumber(reportData.pipelineSummary?.inProgressCount) +
    toNumber(reportData.qualitySummary?.pendingCount);
  const readyDomains = domainRollups.filter((item) => item.health === "healthy").length;
  const attentionDomains = domainRollups.filter((item) => item.health === "attention").length;
  const activeHealthLabel = reportHealthOptions.find((option) => option.key === healthFilter)?.label ?? "All health states";
  const generatedAt = new Date().toISOString();

  function handleExportJson() {
    downloadReportJson("athena-release-readiness-report.json", {
      report: "release-readiness",
      exportedAt: generatedAt,
      filters: {
        health: healthFilter,
        scope: scopeFilter,
        timeWindow,
      },
      summary: {
        releasePressure,
        readyDomains,
        attentionDomains,
        latestReleaseSignal,
      },
      domainRollups: filteredDomainRollups,
      recentSignals: filteredRecentSignals,
      routes: filteredHandoffRoutes,
    });
  }

  function handleExportCsv() {
    downloadReportCsv(
      "athena-release-readiness-report.csv",
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
          label: "Release pressure",
          value: formatNumber(releasePressure, 0, "0"),
          detail: `${formatNumber(attentionDomains, 0, "0")} attention domains · ${formatNumber(readyDomains, 0, "0")} healthy`,
        },
        {
          section: "Executive snapshot",
          label: "Latest release signal",
          value: formatDateTime(latestReleaseSignal),
          detail: "Latest contract, source, delivery, or validation activity returned by the live report contracts",
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
        title="Release Readiness Report"
        summary="This page narrows the reporting surface to the signals most likely to gate a release review: contract freshness, source sync posture, delivery flow, and execution sign-off across the live Athena workspaces."
        callouts={[
          "Report route: /reports/release-readiness",
          `Scope: ${activeScopeLabel}`,
          `Health: ${activeHealthLabel}`,
          `Window: ${getTimeWindowLabel(timeWindow)}`,
          `Release pressure: ${formatNumber(releasePressure, 0, "0")}`,
          `Healthy domains: ${formatNumber(readyDomains, 0, "0")}`,
          `Latest release signal: ${formatDateTime(latestReleaseSignal)}`,
        ]}
        metrics={[
          {
            key: "release-pressure",
            label: "Release pressure",
            value: formatNumber(releasePressure, 0, "0"),
            detail: "Combined contract, source, delivery, and validation pressure.",
            toneClass: "metric-card--blue",
          },
          {
            key: "healthy-domains",
            label: "Healthy domains",
            value: formatNumber(readyDomains, 0, "0"),
            detail: `${formatNumber(attentionDomains, 0, "0")} domains currently demand direct follow-up.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-release-signal",
            label: "Latest signal",
            value: formatDateTime(latestReleaseSignal),
            detail: `${formatNumber(filteredRecentSignals.length, 0, "0")} recent readiness signals remain visible.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "recommended-handoffs",
            label: "Recommended handoffs",
            value: formatNumber(filteredHandoffRoutes.length, 0, "0"),
            detail: "Release-focused workspace drill-downs that match the current filter state.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <ReportNavigation />

      <ReportToolbar
        title="Filter, export, or print the release readiness report"
        summary="Time windows narrow the trend-driven sections while scope and health filters keep the report centered on domains that block or slow release review."
        timeWindow={timeWindow}
        onTimeWindowChange={(nextTimeWindow) => updateQuery({ window: nextTimeWindow })}
        filterGroups={[
          {
            label: "Release scope",
            ariaLabel: "Release readiness scope filters",
            options: scopeOptions,
            value: scopeFilter,
            onChange: (nextScope) => updateQuery({ scope: nextScope }),
          },
          {
            label: "Health state",
            ariaLabel: "Release readiness health filters",
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
            <h2>Release readiness print summary</h2>
          </div>
          <p className="page-summary">This print layout keeps release pressure, current filters, and the latest cross-domain readiness signal visible on the first page.</p>
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
            <p className="eyebrow">Release pressure</p>
            <h3 className="summary-value">{formatNumber(releasePressure, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Healthy domains</p>
            <h3 className="summary-value">{formatNumber(readyDomains, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Attention domains</p>
            <h3 className="summary-value">{formatNumber(attentionDomains, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Latest release signal</p>
            <h3 className="summary-value summary-value--compact">{formatDateTime(latestReleaseSignal)}</h3>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Executive snapshot</p>
            <h2>Release readiness picture</h2>
          </div>
          <p className="page-summary">These cards condense the release-facing domains before readers move into the rollups and workspace handoffs.</p>
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
              {formatNumber(reportData.gitSummary?.hostCount, 0, "0")} hosts with {formatNumber(reportData.gitSummary?.freshCount, 0, "0")} fresh.
            </p>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Pipeline runs</p>
            <h3 className="summary-value">{formatNumber(reportData.pipelineSummary?.totalCount, 0, "0")}</h3>
            <p className="muted">
              {formatNumber(reportData.pipelineSummary?.completedCount, 0, "0")} completed with avg {formatDuration(reportData.pipelineSummary?.averageDuration, 0)}.
            </p>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Quality executions</p>
            <h3 className="summary-value">{formatNumber(reportData.qualitySummary?.totalCount, 0, "0")}</h3>
            <p className="muted">
              {formatNumber(reportData.qualitySummary?.executedCount, 0, "0")} completed with {formatNumber(reportData.qualitySummary?.pendingCount, 0, "0")} pending.
            </p>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Readiness rollups</p>
            <h2>Where release review is healthy, watchful, or blocked</h2>
          </div>
          <p className="page-summary">Each rollup maps back to its owning workspace so the report can highlight blockers without becoming the investigative end-state.</p>
        </div>

        {reportStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the release readiness report.</h3>
            <p className="muted">The report is waiting on the live contracts from spec, git, pipeline, and quality.</p>
          </div>
        ) : null}

        {reportStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Report unavailable</p>
            <h3>The release readiness report could not be assembled.</h3>
            <p className="muted">{reportErrorMessage}</p>
          </div>
        ) : null}

        {reportStatus === "ready" ? (
          filteredDomainRollups.length > 0 ? (
            <div className="readiness-grid">
              {filteredDomainRollups.map((item) => (
                <article key={item.label} className="status-card">
                  <span className={`status-chip ${getHealthToneClass(item.health)}`}>{item.health}</span>
                  <h3>{item.label}</h3>
                  <p className="summary-value summary-value--compact">{item.headline}</p>
                  <p className="muted">{item.detail}</p>
                  <Link to={item.to} className="button button--ghost">
                    Open workspace
                  </Link>
                </article>
              ))}
            </div>
          ) : (
            <div className="empty-state">
              <p className="eyebrow">No matches</p>
              <h3>No release rollups match the current health filter.</h3>
              <p className="muted">Broaden the health filter to restore the full release readiness picture.</p>
            </div>
          )
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Signal board</p>
            <h2>Recent release-facing signals</h2>
          </div>
          <p className="page-summary">The latest sync and bucket-level signals stay visible so the report preserves trend context while remaining release-focused.</p>
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
          <p className="page-summary">These workspace routes are the next step when a release gate needs explanation or remediation.</p>
        </div>
        {filteredHandoffRoutes.length > 0 ? (
          <div className="readiness-grid">
            {filteredHandoffRoutes.map((item) => (
              <article key={item.to} className="status-card">
                <p className="eyebrow">Workspace handoff</p>
                <h3>{item.label}</h3>
                <p className="muted">{item.detail}</p>
                <Link to={item.to} className="button button--ghost">
                  Open workspace
                </Link>
              </article>
            ))}
          </div>
        ) : (
          <div className="empty-state">
            <p className="eyebrow">No matching handoffs</p>
            <h3>The current health filter excludes the related workspace routes.</h3>
            <p className="muted">Choose a broader health filter to restore the full set of release drill-downs.</p>
          </div>
        )}
      </section>
    </div>
  );
}

import { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";
import { formatDateTime, formatDuration, formatNumber, getErrorMessage } from "../../../shared/ui/workspacePageUtils";
import { ReportNavigation } from "../components/ReportNavigation";
import { ReportToolbar } from "../components/ReportToolbar";
import {
  buildUpdatedSearchParams,
  buildReportPath,
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
  { key: "all", label: "All exceptions" },
  { key: "contracts", label: "Contracts" },
  { key: "repositories", label: "Repositories" },
  { key: "runtime", label: "Runtime" },
  { key: "performance", label: "Performance" },
  { key: "delivery", label: "Delivery" },
  { key: "quality", label: "Quality" },
];

const handoffRoutes = [
  { to: "/apis/specs", label: "API Specs", detail: "Investigate stale or aging contracts in the specs workspace.", scope: "contracts" },
  { to: "/git/repositories", label: "Git Repositories", detail: "Follow repository freshness issues in the git workspace.", scope: "repositories" },
  { to: "/runtime/pods", label: "Runtime Pods", detail: "Inspect runtime churn or unstable pod states in the runtime workspace.", scope: "runtime" },
  { to: "/metrics/executions", label: "Metric Runs", detail: "Review slow or volatile telemetry signals in the metric workspace.", scope: "performance" },
  { to: "/pipelines/runs", label: "Pipeline Runs", detail: "Check running or blocked delivery flow in the pipeline workspace.", scope: "delivery" },
  { to: "/quality/executions", label: "Quality Runs", detail: "Check pending or incomplete execution coverage in the quality workspace.", scope: "quality" },
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

export function ExceptionFollowUpReportPage() {
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
      requestJson(apiRoots.kube, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.kube, buildReportPath("/trend", { windowDays })),
      requestJson(apiRoots.metric, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.metric, buildReportPath("/trend", { windowDays })),
      requestJson(apiRoots.pipeline, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.pipeline, buildReportPath("/trend", { windowDays })),
      requestJson(apiRoots.tms, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.tms, buildReportPath("/trend", { windowDays })),
    ])
      .then(
        ([specSummary, specFreshness, gitSummary, gitTrend, kubeSummary, kubeTrend, metricSummary, metricTrend, pipelineSummary, pipelineTrend, qualitySummary, qualityTrend]) => {
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
            pipelineSummary: pipelineSummary ?? null,
            pipelineTrend: Array.isArray(pipelineTrend) ? pipelineTrend : [],
            qualitySummary: qualitySummary ?? null,
            qualityTrend: Array.isArray(qualityTrend) ? qualityTrend : [],
          });
          setReportStatus("ready");
        },
      )
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
  const latestKubeBucket = reportData.kubeTrend.at(-1) ?? null;
  const latestMetricBucket = reportData.metricTrend.at(-1) ?? null;
  const latestPipelineBucket = reportData.pipelineTrend.at(-1) ?? null;
  const latestQualityBucket = reportData.qualityTrend.at(-1) ?? null;

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

  function getPipelineHealthState() {
    if (toNumber(reportData.pipelineSummary?.completedCount) === 0 && toNumber(reportData.pipelineSummary?.totalCount) > 0) {
      return "attention";
    }
    if (toNumber(reportData.pipelineSummary?.inProgressCount) > 0) {
      return "watch";
    }
    return "healthy";
  }

  function getQualityHealthState() {
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
      label: "API Specs",
      scope: "contracts",
      health: getSpecHealthState(),
      headline: `${formatNumber(reportData.specFreshness?.staleCount, 0, "0")} stale contracts`,
      detail: `${formatNumber(reportData.specFreshness?.agingCount, 0, "0")} aging · latest sync ${formatDateTime(reportData.specFreshness?.latestSyncTime)}`,
      to: buildWorkspaceHandoffPath("/apis/specs", getSpecHealthState()),
      nextStep: "Review freshness and drift inventory before the contract issue spreads into release review.",
    },
    {
      label: "Git Repositories",
      scope: "repositories",
      health: getGitHealthState(),
      headline: `${formatNumber(reportData.gitSummary?.staleCount, 0, "0")} stale repositories`,
      detail: `${formatNumber(reportData.gitSummary?.agingCount, 0, "0")} aging · ${formatNumber(reportData.gitSummary?.hostCount, 0, "0")} hosts`,
      to: buildWorkspaceHandoffPath("/git/repositories", getGitHealthState()),
      nextStep: "Check stale hosts and confirm the sync signal is still trustworthy.",
    },
    {
      label: "Runtime Pods",
      scope: "runtime",
      health: getRuntimeHealthState(),
      headline: `${formatNumber(reportData.kubeSummary?.deletedCount, 0, "0")} deleted pods`,
      detail: `${formatNumber(reportData.kubeSummary?.activeCount, 0, "0")} active · ${formatNumber(reportData.kubeSummary?.namespaceCount, 0, "0")} namespaces`,
      to: buildWorkspaceHandoffPath("/runtime/pods", getRuntimeHealthState()),
      nextStep: "Inspect runtime churn and confirm whether deletions are expected or drifting.",
    },
    {
      label: "Metric Runs",
      scope: "performance",
      health: getMetricHealthState(),
      headline: `Max ${formatDuration(reportData.metricSummary?.slowestDuration, 0)}`,
      detail: `${formatNumber(reportData.metricSummary?.uniqueActionCount, 0, "0")} actions · avg ${formatDuration(reportData.metricSummary?.averageDuration, 0)}`,
      to: buildWorkspaceHandoffPath("/metrics/executions", getMetricHealthState()),
      nextStep: "Drill into slow actions before telemetry pressure obscures the root cause.",
    },
    {
      label: "Pipeline Runs",
      scope: "delivery",
      health: getPipelineHealthState(),
      headline: `${formatNumber(reportData.pipelineSummary?.inProgressCount, 0, "0")} in progress`,
      detail: `${formatNumber(reportData.pipelineSummary?.completedCount, 0, "0")} completed · latest start ${formatDateTime(reportData.pipelineSummary?.latestStartTime)}`,
      to: buildWorkspaceHandoffPath("/pipelines/runs", getPipelineHealthState()),
      nextStep: "Inspect the active delivery flow before the issue becomes a release blocker.",
    },
    {
      label: "Quality Runs",
      scope: "quality",
      health: getQualityHealthState(),
      headline: `${formatNumber(reportData.qualitySummary?.pendingCount, 0, "0")} pending`,
      detail: `${formatNumber(reportData.qualitySummary?.executedCount, 0, "0")} completed · latest activity ${formatDateTime(reportData.qualitySummary?.latestActivityTime)}`,
      to: buildWorkspaceHandoffPath("/quality/executions", getQualityHealthState()),
      nextStep: "Follow pending execution coverage before sign-off assumptions drift.",
    },
  ];

  const exceptionSignals = [
    {
      label: "Contract freshness",
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
    {
      label: "Delivery pressure",
      scope: "delivery",
      health: getPipelineHealthState(),
      value: `${formatNumber(latestPipelineBucket?.pipelineCount, 0, "0")} runs`,
      detail: latestPipelineBucket
        ? `${formatDateTime(latestPipelineBucket.bucketStart)} · ${formatNumber(reportData.pipelineSummary?.inProgressCount, 0, "0")} in progress`
        : "No pipeline trend points returned yet.",
    },
    {
      label: "Validation pressure",
      scope: "quality",
      health: getQualityHealthState(),
      value: `${formatNumber(latestQualityBucket?.executionCount, 0, "0")} executions`,
      detail: latestQualityBucket
        ? `${formatDateTime(latestQualityBucket.bucketStart)} · ${formatNumber(reportData.qualitySummary?.pendingCount, 0, "0")} pending`
        : "No quality trend points returned yet.",
    },
  ];

  const exceptionRollups = domainRollups.filter((item) => item.health !== "healthy");
  const filteredExceptionRollups = exceptionRollups.filter((item) => (scopeFilter === "all" || item.scope === scopeFilter) && matchesHealthFilter(item.health, healthFilter));
  const filteredExceptionSignals = exceptionSignals.filter(
    (item) => item.health !== "healthy" && (scopeFilter === "all" || item.scope === scopeFilter) && matchesHealthFilter(item.health, healthFilter),
  );
  const filteredHandoffRoutes = handoffRoutes
    .filter((item) => {
      if (scopeFilter !== "all" && item.scope !== scopeFilter) {
        return false;
      }

      const matchingRollup = exceptionRollups.find((rollup) => rollup.scope === item.scope);
      return matchesHealthFilter(matchingRollup?.health ?? "healthy", healthFilter);
    })
    .map((item) => {
      const matchingRollup = exceptionRollups.find((rollup) => rollup.scope === item.scope);
      return {
        ...item,
        to: buildWorkspaceHandoffPath(item.to, matchingRollup?.health ?? healthFilter),
      };
    });
  const attentionCount = filteredExceptionRollups.filter((item) => item.health === "attention").length;
  const watchCount = filteredExceptionRollups.filter((item) => item.health === "watch").length;
  const latestExceptionSignal = getLatestTimestamp([
    reportData.specFreshness?.latestSyncTime,
    reportData.gitSummary?.latestSyncTime,
    reportData.kubeSummary?.latestSyncTime,
    reportData.metricSummary?.latestActionTime,
    reportData.pipelineSummary?.latestStartTime,
    reportData.qualitySummary?.latestActivityTime,
  ]);
  const activeScopeLabel = scopeOptions.find((option) => option.key === scopeFilter)?.label ?? "All exceptions";
  const activeHealthLabel = reportHealthOptions.find((option) => option.key === healthFilter)?.label ?? "All health states";
  const generatedAt = new Date().toISOString();

  function handleExportJson() {
    downloadReportJson("athena-exception-follow-up-report.json", {
      report: "exception-follow-up",
      exportedAt: generatedAt,
      filters: {
        health: healthFilter,
        scope: scopeFilter,
        timeWindow,
      },
      summary: {
        attentionCount,
        watchCount,
        latestExceptionSignal,
      },
      exceptions: filteredExceptionRollups,
      signals: filteredExceptionSignals,
      routes: filteredHandoffRoutes,
    });
  }

  function handleExportCsv() {
    downloadReportCsv(
      "athena-exception-follow-up-report.csv",
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
          label: "Attention exceptions",
          value: formatNumber(attentionCount, 0, "0"),
          detail: `${formatNumber(watchCount, 0, "0")} watch signals still need follow-up`,
        },
        {
          section: "Executive snapshot",
          label: "Latest exception signal",
          value: formatDateTime(latestExceptionSignal),
          detail: "Latest watch or attention signal returned by the live report contracts",
        },
        ...filteredExceptionRollups.map((item) => ({
          section: "Exception queue",
          label: item.label,
          value: item.headline,
          detail: `${item.detail} · Health ${item.health}`,
        })),
        ...filteredExceptionSignals.map((item) => ({
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
        title="Exception Follow-up Report"
        summary="This report starts from watch and attention signals across Athena and turns them into the next operational queue, so readers can move from a risk signal to the owning workspace without rebuilding context."
        callouts={[
          "Report route: /reports/exception-follow-up",
          `Scope: ${activeScopeLabel}`,
          `Health: ${activeHealthLabel}`,
          `Window: ${getTimeWindowLabel(timeWindow)}`,
          `Attention: ${formatNumber(attentionCount, 0, "0")}`,
          `Latest exception signal: ${formatDateTime(latestExceptionSignal)}`,
        ]}
        metrics={[
          {
            key: "attention-queue",
            label: "Attention queue",
            value: formatNumber(attentionCount, 0, "0"),
            detail: "Highest-priority exception items currently driving follow-up.",
            toneClass: "metric-card--blue",
          },
          {
            key: "watch-queue",
            label: "Watch signals",
            value: formatNumber(watchCount, 0, "0"),
            detail: `${formatNumber(filteredExceptionRollups.length, 0, "0")} exception rollups remain in scope.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-exception",
            label: "Latest signal",
            value: formatDateTime(latestExceptionSignal),
            detail: `${formatNumber(filteredExceptionSignals.length, 0, "0")} recent escalation cards still visible.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "handoff-routes",
            label: "Handoff routes",
            value: formatNumber(filteredHandoffRoutes.length, 0, "0"),
            detail: "Direct workspace routes for the current exception queue.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <ReportNavigation />

      <ReportToolbar
        title="Filter, export, or print the exception follow-up report"
        summary="Use scope and health filters to narrow the follow-up queue to the domains that currently need the next investigation step."
        timeWindow={timeWindow}
        onTimeWindowChange={(nextTimeWindow) => updateQuery({ window: nextTimeWindow })}
        filterGroups={[
          {
            label: "Exception scope",
            ariaLabel: "Exception follow-up scope filters",
            options: scopeOptions,
            value: scopeFilter,
            onChange: (nextScope) => updateQuery({ scope: nextScope }),
          },
          {
            label: "Health state",
            ariaLabel: "Exception follow-up health filters",
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
            <h2>Exception follow-up print summary</h2>
          </div>
          <p className="page-summary">This print layout keeps the exception queue, active filters, and the latest escalation signal visible without the interactive toolbar.</p>
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
            <p className="eyebrow">Attention exceptions</p>
            <h3 className="summary-value">{formatNumber(attentionCount, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Watch signals</p>
            <h3 className="summary-value">{formatNumber(watchCount, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Latest exception signal</p>
            <h3 className="summary-value summary-value--compact">{formatDateTime(latestExceptionSignal)}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Matching handoffs</p>
            <h3 className="summary-value">{formatNumber(filteredHandoffRoutes.length, 0, "0")}</h3>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Follow-up queue</p>
            <h2>Exception follow-up picture</h2>
          </div>
          <p className="page-summary">Only watch and attention domains appear here, so the report stays focused on action rather than full portfolio coverage.</p>
        </div>

        {reportStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the exception follow-up report.</h3>
            <p className="muted">The report is waiting on the live summary, freshness, and trend contracts across the active domains.</p>
          </div>
        ) : null}

        {reportStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Report unavailable</p>
            <h3>The exception follow-up report could not be assembled.</h3>
            <p className="muted">{reportErrorMessage}</p>
          </div>
        ) : null}

        {reportStatus === "ready" ? (
          filteredExceptionRollups.length > 0 ? (
            <div className="readiness-grid">
              {filteredExceptionRollups.map((item) => (
                <article key={item.label} className="status-card">
                  <span className={`status-chip ${getHealthToneClass(item.health)}`}>{item.health}</span>
                  <h3>{item.label}</h3>
                  <p className="summary-value summary-value--compact">{item.headline}</p>
                  <p className="muted">{item.detail}</p>
                  <p className="muted">{item.nextStep}</p>
                  <Link className="button button--ghost" to={item.to}>
                    Open workspace
                  </Link>
                </article>
              ))}
            </div>
          ) : (
            <div className="empty-state">
              <p className="eyebrow">No exceptions</p>
              <h3>No exception items match the current filters.</h3>
              <p className="muted">Broaden the scope or health filter to restore the follow-up queue.</p>
            </div>
          )
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Signal board</p>
            <h2>Recent exception signals</h2>
          </div>
          <p className="page-summary">These cards keep the most recent watch and attention signals visible before readers jump into the owning workspace.</p>
        </div>
        <div className="summary-grid">
          {filteredExceptionSignals.map((item) => (
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
          <p className="page-summary">These routes are the next investigative step when the exception queue highlights a domain that needs action.</p>
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
            <h3>The current filters exclude the related drill-down routes.</h3>
            <p className="muted">Choose a broader scope or health filter to restore the full set of exception handoffs.</p>
          </div>
        )}
      </section>
    </div>
  );
}

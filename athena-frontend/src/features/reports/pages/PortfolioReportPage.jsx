import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { BarTrendPanel, LineTrendPanel } from "../../../shared/ui/DashboardGraphs";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";
import { formatDateTime, formatDuration, formatNumber, getErrorMessage } from "../../../shared/ui/workspacePageUtils";
import { ReportNavigation } from "../components/ReportNavigation";
import { ReportToolbar } from "../components/ReportToolbar";
import {
  buildReportPath,
  buildWorkspaceHandoffPath,
  downloadReportCsv,
  downloadReportJson,
  getHealthToneClass,
  getTimeWindowLabel,
  getTimeWindowDays,
  matchesHealthFilter,
  reportHealthOptions,
  triggerReportPrint,
} from "../reportUtils";

const reportRoutes = [
  { to: "/apis/specs", label: "API Specs", detail: "Contract freshness and drift", focus: "contracts" },
  { to: "/git/repositories", label: "Git Repositories", detail: "Repository sync posture", focus: "repositories" },
  { to: "/runtime/pods", label: "Runtime Pods", detail: "Pod inventory and churn", focus: "runtime" },
  { to: "/metrics/executions", label: "Metric Runs", detail: "Performance telemetry inventory", focus: "performance" },
  { to: "/pipelines/runs", label: "Pipeline Runs", detail: "Delivery activity and status", focus: "delivery" },
  { to: "/quality/executions", label: "Quality Runs", detail: "Execution quality and drill-down", focus: "quality" },
];

const focusOptions = [
  { key: "all", label: "All domains" },
  { key: "contracts", label: "Contracts" },
  { key: "repositories", label: "Repositories" },
  { key: "runtime", label: "Runtime" },
  { key: "performance", label: "Performance" },
  { key: "delivery", label: "Delivery" },
  { key: "quality", label: "Quality" },
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

export function PortfolioReportPage() {
  const [focusFilter, setFocusFilter] = useState("all");
  const [healthFilter, setHealthFilter] = useState("all");
  const [timeWindow, setTimeWindow] = useState("all");
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
  const latestPlatformSignal = getLatestTimestamp([
    reportData.specFreshness?.latestSyncTime,
    reportData.gitSummary?.latestSyncTime,
    reportData.kubeSummary?.latestSyncTime,
    reportData.metricSummary?.latestActionTime,
    reportData.pipelineSummary?.latestStartTime,
    reportData.qualitySummary?.latestActivityTime,
  ]);
  const trackedRecordCount =
    toNumber(reportData.specSummary?.specCount) +
    toNumber(reportData.gitSummary?.totalCount) +
    toNumber(reportData.kubeSummary?.totalCount) +
    toNumber(reportData.metricSummary?.totalCount) +
    toNumber(reportData.pipelineSummary?.totalCount) +
    toNumber(reportData.qualitySummary?.totalCount);
  const attentionItemCount =
    toNumber(reportData.specFreshness?.staleCount) +
    toNumber(reportData.gitSummary?.staleCount) +
    toNumber(reportData.kubeSummary?.deletedCount) +
    toNumber(reportData.pipelineSummary?.inProgressCount) +
    toNumber(reportData.qualitySummary?.pendingCount);

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
      focus: "contracts",
      health: getSpecHealthState(),
      headline: `${formatNumber(reportData.specSummary?.specCount, 0, "0")} specs`,
      detail: `${formatNumber(reportData.specFreshness?.staleCount, 0, "0")} stale · ${formatNumber(reportData.specSummary?.pathCount, 0, "0")} paths`,
      to: buildWorkspaceHandoffPath("/apis/specs", getSpecHealthState()),
    },
    {
      label: "Git Repositories",
      focus: "repositories",
      health: getGitHealthState(),
      headline: `${formatNumber(reportData.gitSummary?.totalCount, 0, "0")} repositories`,
      detail: `${formatNumber(reportData.gitSummary?.staleCount, 0, "0")} stale · ${formatNumber(reportData.gitSummary?.hostCount, 0, "0")} hosts`,
      to: buildWorkspaceHandoffPath("/git/repositories", getGitHealthState()),
    },
    {
      label: "Runtime Pods",
      focus: "runtime",
      health: getRuntimeHealthState(),
      headline: `${formatNumber(reportData.kubeSummary?.totalCount, 0, "0")} pods`,
      detail: `${formatNumber(reportData.kubeSummary?.activeCount, 0, "0")} active · ${formatNumber(reportData.kubeSummary?.deletedCount, 0, "0")} deleted`,
      to: buildWorkspaceHandoffPath("/runtime/pods", getRuntimeHealthState()),
    },
    {
      label: "Metric Runs",
      focus: "performance",
      health: getMetricHealthState(),
      headline: `${formatNumber(reportData.metricSummary?.totalCount, 0, "0")} executions`,
      detail: `${formatNumber(reportData.metricSummary?.uniqueActionCount, 0, "0")} actions · avg ${formatDuration(reportData.metricSummary?.averageDuration, 0)}`,
      to: buildWorkspaceHandoffPath("/metrics/executions", getMetricHealthState()),
    },
    {
      label: "Pipeline Runs",
      focus: "delivery",
      health: getPipelineHealthState(),
      headline: `${formatNumber(reportData.pipelineSummary?.totalCount, 0, "0")} runs`,
      detail: `${formatNumber(reportData.pipelineSummary?.completedCount, 0, "0")} completed · ${formatNumber(reportData.pipelineSummary?.inProgressCount, 0, "0")} running`,
      to: buildWorkspaceHandoffPath("/pipelines/runs", getPipelineHealthState()),
    },
    {
      label: "Quality Runs",
      focus: "quality",
      health: getQualityHealthState(),
      headline: `${formatNumber(reportData.qualitySummary?.totalCount, 0, "0")} executions`,
      detail: `${formatNumber(reportData.qualitySummary?.executedCount, 0, "0")} completed · ${formatNumber(reportData.qualitySummary?.pendingCount, 0, "0")} pending`,
      to: buildWorkspaceHandoffPath("/quality/executions", getQualityHealthState()),
    },
  ];
  const dailyPulse = [
    {
      label: "Specs",
      focus: "contracts",
      health: getSpecHealthState(),
      value: `${formatNumber(reportData.specFreshness?.staleCount, 0, "0")} stale`,
      detail: `Latest sync ${formatDateTime(reportData.specFreshness?.latestSyncTime)}`,
    },
    {
      label: "Git",
      focus: "repositories",
      health: getGitHealthState(),
      value: `${formatNumber(latestGitBucket?.repositoryCount, 0, "0")} synced`,
      detail: latestGitBucket
        ? `${formatDateTime(latestGitBucket.bucketStart)} · ${formatNumber(latestGitBucket.uniqueHostCount, 0, "0")} hosts`
        : "No repository trend points returned yet.",
    },
    {
      label: "Runtime",
      focus: "runtime",
      health: getRuntimeHealthState(),
      value: `${formatNumber(latestKubeBucket?.podCount, 0, "0")} pods`,
      detail: latestKubeBucket
        ? `${formatDateTime(latestKubeBucket.bucketStart)} · ${formatNumber(latestKubeBucket.activeCount, 0, "0")} active`
        : "No pod trend points returned yet.",
    },
    {
      label: "Metrics",
      focus: "performance",
      health: getMetricHealthState(),
      value: `${formatNumber(latestMetricBucket?.metricCount, 0, "0")} executions`,
      detail: latestMetricBucket
        ? `${formatDateTime(latestMetricBucket.bucketStart)} · max ${formatDuration(latestMetricBucket.maxDuration, 0)}`
        : "No metric trend points returned yet.",
    },
    {
      label: "Pipelines",
      focus: "delivery",
      health: getPipelineHealthState(),
      value: `${formatNumber(latestPipelineBucket?.pipelineCount, 0, "0")} runs`,
      detail: latestPipelineBucket
        ? `${formatDateTime(latestPipelineBucket.bucketStart)} · ${formatNumber(latestPipelineBucket.completedCount, 0, "0")} completed`
        : "No pipeline trend points returned yet.",
    },
    {
      label: "Quality",
      focus: "quality",
      health: getQualityHealthState(),
      value: `${formatNumber(latestQualityBucket?.executionCount, 0, "0")} executions`,
      detail: latestQualityBucket
        ? `${formatDateTime(latestQualityBucket.bucketStart)} · ${formatNumber(latestQualityBucket.executedCount, 0, "0")} completed`
        : "No quality trend points returned yet.",
    },
  ];
  const activeFocusLabel = focusOptions.find((option) => option.key === focusFilter)?.label ?? "All domains";
  const activeHealthLabel = reportHealthOptions.find((option) => option.key === healthFilter)?.label ?? "All health states";
  const filteredDomainRollups = domainRollups.filter((item) => (focusFilter === "all" || item.focus === focusFilter) && matchesHealthFilter(item.health, healthFilter));
  const filteredDailyPulse = dailyPulse.filter((item) => (focusFilter === "all" || item.focus === focusFilter) && matchesHealthFilter(item.health, healthFilter));
  const filteredReportRoutes = reportRoutes
    .filter((item) => {
      const matchingRollup = domainRollups.find((rollup) => rollup.focus === item.focus);
      return (focusFilter === "all" || item.focus === focusFilter) && matchesHealthFilter(matchingRollup?.health ?? "healthy", healthFilter);
    })
    .map((item) => {
      const matchingRollup = domainRollups.find((rollup) => rollup.focus === item.focus);
      return {
        ...item,
        to: buildWorkspaceHandoffPath(item.to, matchingRollup?.health ?? healthFilter),
      };
    });
  const generatedAt = new Date().toISOString();

  function handleExportJson() {
    downloadReportJson("athena-portfolio-report.json", {
      report: "portfolio",
      exportedAt: generatedAt,
      filters: {
        focus: focusFilter,
        health: healthFilter,
        timeWindow,
      },
      summary: {
        trackedRecordCount,
        attentionItemCount,
        latestPlatformSignal,
      },
      domainRollups: filteredDomainRollups,
      dailyPulse: filteredDailyPulse,
      routes: filteredReportRoutes,
    });
  }

  function handleExportCsv() {
    downloadReportCsv(
      "athena-portfolio-report.csv",
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
          value: `${activeFocusLabel} / ${activeHealthLabel} / ${getTimeWindowLabel(timeWindow)}`,
          detail: `Generated ${formatDateTime(generatedAt)}`,
        },
        {
          section: "Executive snapshot",
          label: "Tracked records",
          value: formatNumber(trackedRecordCount, 0, "0"),
          detail: `Attention items ${formatNumber(attentionItemCount, 0, "0")}`,
        },
        {
          section: "Executive snapshot",
          label: "Latest platform activity",
          value: formatDateTime(latestPlatformSignal),
          detail: "Latest gateway-backed signal across reporting domains",
        },
        ...filteredDomainRollups.map((item) => ({
          section: "Domain rollup",
          label: item.label,
          value: item.headline,
          detail: `${item.detail} · Health ${item.health}`,
        })),
        ...filteredDailyPulse.map((item) => ({
          section: "Daily pulse",
          label: item.label,
          value: item.value,
          detail: `${item.detail} · Health ${item.health}`,
        })),
        ...filteredReportRoutes.map((item) => ({
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
        title="Portfolio Report"
        summary="This page rolls the live workspaces into one external-facing operating picture so stakeholders can scan delivery, contract freshness, runtime posture, telemetry, repository health, and execution quality without bouncing between routes."
        callouts={[
          "Report route: /reports/portfolio",
          "Reporting domains live: 6",
          `Focus: ${activeFocusLabel}`,
          `Health: ${activeHealthLabel}`,
          `Window: ${getTimeWindowLabel(timeWindow)}`,
          `Attention items: ${formatNumber(attentionItemCount, 0, "0")}`,
          `Latest platform signal: ${formatDateTime(latestPlatformSignal)}`,
        ]}
        metrics={[
          {
            key: "tracked-records",
            label: "Tracked records",
            value: formatNumber(trackedRecordCount, 0, "0"),
            detail: "Combined scope across the live portfolio feed.",
            toneClass: "metric-card--blue",
          },
          {
            key: "attention-items",
            label: "Attention items",
            value: formatNumber(attentionItemCount, 0, "0"),
            detail: `${formatNumber(filteredDomainRollups.length, 0, "0")} domain rollups currently match the active filters.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-signal",
            label: "Latest signal",
            value: formatDateTime(latestPlatformSignal),
            detail: `${formatNumber(filteredDailyPulse.length, 0, "0")} latest-domain pulse cards remain visible.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "active-handoffs",
            label: "Active handoffs",
            value: formatNumber(filteredReportRoutes.length, 0, "0"),
            detail: "Workspace and report drill-downs filtered to the current portfolio slice.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <ReportNavigation />

      <ReportToolbar
        title="Filter, export, or print the portfolio report"
        summary="Time windows apply to trend-derived sections while focus and health filtering narrow the report cards and handoff links."
        timeWindow={timeWindow}
        onTimeWindowChange={setTimeWindow}
        filterGroups={[
          {
            label: "Domain focus",
            ariaLabel: "Portfolio report domain focus filters",
            options: focusOptions,
            value: focusFilter,
            onChange: setFocusFilter,
          },
          {
            label: "Health state",
            ariaLabel: "Portfolio report health filters",
            options: reportHealthOptions,
            value: healthFilter,
            onChange: setHealthFilter,
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
            <h2>Portfolio print summary</h2>
          </div>
          <p className="page-summary">This print layout keeps the operating picture, active filters, and the most important exceptions visible on the first page.</p>
        </div>
        <div className="token-grid">
          <span className="token-chip">
            <strong>Generated</strong>
            {formatDateTime(generatedAt)}
          </span>
          <span className="token-chip">
            <strong>Focus</strong>
            {activeFocusLabel}
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
            <p className="eyebrow">Tracked records</p>
            <h3 className="summary-value">{formatNumber(trackedRecordCount, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Attention items</p>
            <h3 className="summary-value">{formatNumber(attentionItemCount, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Latest platform activity</p>
            <h3 className="summary-value summary-value--compact">{formatDateTime(latestPlatformSignal)}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Matching routes</p>
            <h3 className="summary-value">{formatNumber(filteredReportRoutes.length, 0, "0")}</h3>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Executive snapshot</p>
            <h2>Cross-domain operating picture</h2>
          </div>
          <p className="page-summary">These cards aggregate the same gateway-backed workspaces that the domain pages use directly.</p>
        </div>

        {reportStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the portfolio report from live workspace signals.</h3>
            <p className="muted">The report waits on the same summary and trend contracts already proven in the domain pages.</p>
          </div>
        ) : null}

        {reportStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Report unavailable</p>
            <h3>The portfolio report could not be assembled.</h3>
            <p className="muted">{reportErrorMessage}</p>
          </div>
        ) : null}

        {reportStatus === "ready" ? (
          <div className="summary-grid">
            <article className="summary-stat">
              <p className="eyebrow">Reporting domains live</p>
              <h3 className="summary-value">6</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Tracked records</p>
              <h3 className="summary-value">{formatNumber(trackedRecordCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Attention items</p>
              <h3 className="summary-value">{formatNumber(attentionItemCount, 0, "0")}</h3>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Latest platform activity</p>
              <h3 className="summary-value summary-value--compact">{formatDateTime(latestPlatformSignal)}</h3>
            </article>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Trend board</p>
            <h2>Grafana-style domain trend board</h2>
          </div>
          <p className="page-summary">Graph panels mirror a DevLake or Grafana board, combining trend movement with report-level filtering and handoff context.</p>
        </div>

        {reportStatus === "ready" ? (
          <div className="dashboard-graph-grid">
            <LineTrendPanel
              title="Repository sync trend"
              subtitle="Repository and host movement across report buckets"
              points={reportData.gitTrend}
              primaryKey="repositoryCount"
              secondaryKey="uniqueHostCount"
              primaryLabel="Repositories"
              secondaryLabel="Hosts"
            />
            <LineTrendPanel
              title="Runtime lifecycle trend"
              subtitle="Total versus active pods across runtime buckets"
              points={reportData.kubeTrend}
              primaryKey="podCount"
              secondaryKey="activeCount"
              primaryLabel="Pods"
              secondaryLabel="Active"
            />
            <LineTrendPanel
              title="Delivery and quality cadence"
              subtitle="Pipeline starts versus completed quality executions"
              points={reportData.pipelineTrend.map((point, index) => ({
                bucketStart: point.bucketStart,
                pipelineCount: point.pipelineCount,
                qualityCount: reportData.qualityTrend[index]?.executedCount ?? 0,
              }))}
              primaryKey="pipelineCount"
              secondaryKey="qualityCount"
              primaryLabel="Pipeline runs"
              secondaryLabel="Quality completed"
            />
            <BarTrendPanel
              title="Metric latency spikes"
              subtitle="Maximum metric duration by bucket"
              points={reportData.metricTrend}
              valueKey="maxDuration"
              valueLabel="Max duration"
              valueFormatter={(value) => formatDuration(value, 0)}
            />
          </div>
        ) : (
          <div className="empty-state">
            <p className="eyebrow">Loading graph board</p>
            <p className="muted">The graph board appears when the portfolio trend feeds finish loading.</p>
          </div>
        )}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Domain rollups</p>
            <h2>Move from report to workspace without changing shells</h2>
          </div>
          <p className="page-summary">
            Each card hands off to the underlying workspace that produced the live signal. {formatNumber(filteredDomainRollups.length, 0, "0")} cards match the active focus.
          </p>
        </div>

        {reportStatus === "ready" ? (
          <div className="readiness-grid">
            {filteredDomainRollups.map((item) => (
              <article key={item.label} className="status-card">
                <span className="status-pill status-pill--partial">Live route</span>
                <h3>{item.label}</h3>
                <span className={`status-chip ${getHealthToneClass(item.health)}`}>{item.health}</span>
                <p className="muted">{item.headline}</p>
                <p className="muted">{item.detail}</p>
                <Link to={item.to} className="button button--ghost">
                  Open workspace
                </Link>
              </article>
            ))}
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Daily pulse</p>
            <h2>Latest buckets across the active domains</h2>
          </div>
          <p className="page-summary">This section compresses the most recent trend or freshness signal from each live domain that matches the current focus.</p>
        </div>

        {reportStatus === "ready" ? (
          <div className="trend-grid">
            {filteredDailyPulse.map((item) => (
              <article key={item.label} className="summary-stat">
                <p className="eyebrow">{item.label}</p>
                <span className={`status-chip ${getHealthToneClass(item.health)}`}>{item.health}</span>
                <h3 className="summary-value">{item.value}</h3>
                <p className="muted">{item.detail}</p>
              </article>
            ))}
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Route map</p>
            <h2>Current report handoffs</h2>
          </div>
          <p className="page-summary">These drill-down links are filtered alongside the report cards so the report area works like its own workspace.</p>
        </div>

        <ul className="bullet-list">
          {filteredReportRoutes.map((item) => (
            <li key={item.to}>
              <Link to={item.to}>{item.label}</Link> {item.detail}
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
}

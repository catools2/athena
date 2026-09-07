import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { formatDateTime, formatDuration, formatNumber, getErrorMessage } from "../../../shared/ui/workspacePageUtils";
import { ReportNavigation } from "../components/ReportNavigation";
import { ReportToolbar } from "../components/ReportToolbar";
import {
  buildReportPath,
  buildWorkspaceHandoffPath,
  downloadReportCsv,
  downloadReportJson,
  getHealthToneClass,
  getTimeWindowDays,
  getTimeWindowLabel,
  triggerReportPrint,
} from "../reportUtils";

const followUpReports = [
  {
    to: "/reports/platform-operations",
    label: "Platform Operations",
    detail: "Move from leadership summary into contract freshness, repository posture, runtime churn, and telemetry health.",
  },
  {
    to: "/reports/release-readiness",
    label: "Release Readiness",
    detail: "Focus the conversation on contracts, delivery pressure, and execution sign-off before release review.",
  },
  {
    to: "/reports/exception-follow-up",
    label: "Exception Follow-up",
    detail: "Open the watch and attention queues that need a concrete next action.",
  },
  {
    to: "/reports/delivery-quality",
    label: "Delivery + Quality",
    detail: "Inspect pipeline, quality, and telemetry trend movement behind the executive posture.",
  },
];

function toNumber(value) {
  const numericValue = Number(value);
  return Number.isFinite(numericValue) ? numericValue : 0;
}

function toTimestamp(value) {
  const timestamp = new Date(value ?? 0).getTime();
  return Number.isFinite(timestamp) ? timestamp : 0;
}

function getLatestTimestamp(values) {
  const validValues = values.filter(Boolean);
  if (validValues.length === 0) {
    return null;
  }

  return validValues.reduce((latestValue, currentValue) => (toTimestamp(currentValue) > toTimestamp(latestValue) ? currentValue : latestValue));
}

function getHealthState(attentionCount, watchCount) {
  if (attentionCount > 0) {
    return "attention";
  }

  if (watchCount > 0) {
    return "watch";
  }

  return "healthy";
}

function getHealthLabel(healthState) {
  if (healthState === "attention") {
    return "Attention";
  }

  if (healthState === "watch") {
    return "Watch";
  }

  return "Healthy";
}

function ExecutiveHealthRing({ healthyCount, watchCount, attentionCount }) {
  const total = healthyCount + watchCount + attentionCount;
  const normalizedTotal = total || 1;
  const radius = 56;
  const circumference = 2 * Math.PI * radius;
  let accumulatedLength = 0;
  const segments = [
    { key: "healthy", value: healthyCount, toneClass: "status-chip--success", strokeClass: "ring-chart__stroke ring-chart__stroke--healthy", label: "Healthy" },
    { key: "watch", value: watchCount, toneClass: "status-chip--warning", strokeClass: "ring-chart__stroke ring-chart__stroke--watch", label: "Watch" },
    { key: "attention", value: attentionCount, toneClass: "status-chip--critical", strokeClass: "ring-chart__stroke ring-chart__stroke--attention", label: "Attention" },
  ]
    .filter((segment) => segment.value > 0)
    .map((segment) => {
      const dashLength = (segment.value / normalizedTotal) * circumference;
      const nextSegment = {
        ...segment,
        dashArray: `${dashLength} ${circumference - dashLength}`,
        dashOffset: -accumulatedLength,
      };
      accumulatedLength += dashLength;
      return nextSegment;
    });

  return (
    <div className="ring-chart" role="img" aria-label={`Domain health mix: ${healthyCount} healthy, ${watchCount} watch, ${attentionCount} attention`}>
      <div className="ring-chart__visual">
        <svg viewBox="0 0 140 140" aria-hidden="true">
          <circle className="ring-chart__track" cx="70" cy="70" r={radius} />
          {segments.map((segment) => (
            <circle key={segment.key} className={segment.strokeClass} cx="70" cy="70" r={radius} strokeDasharray={segment.dashArray} strokeDashoffset={segment.dashOffset} />
          ))}
        </svg>
        <div className="ring-chart__center">
          <strong>{total}</strong>
          <span>domains</span>
        </div>
      </div>

      <div className="ring-chart__legend">
        {[healthyCount, watchCount, attentionCount].every((value) => value === 0) ? (
          <p className="muted">No domain health data returned yet.</p>
        ) : (
          [
            { key: "healthy", label: "Healthy", value: healthyCount, toneClass: "status-chip--success" },
            { key: "watch", label: "Watch", value: watchCount, toneClass: "status-chip--warning" },
            { key: "attention", label: "Attention", value: attentionCount, toneClass: "status-chip--critical" },
          ].map((item) => (
            <div key={item.key} className="ring-chart__metric">
              <span className={`ring-chart__swatch ${item.toneClass}`} aria-hidden="true" />
              <div>
                <strong>{item.label}</strong>
                <p className="muted">{formatNumber(item.value, 0, "0")} domains</p>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

function ExecutiveActivityChart({ items }) {
  const width = 720;
  const height = 320;
  const topPadding = 32;
  const leftPadding = 48;
  const rightPadding = 24;
  const bottomPadding = 56;
  const chartWidth = width - leftPadding - rightPadding;
  const chartHeight = height - topPadding - bottomPadding;
  const maxValue = items.reduce((highestValue, item) => Math.max(highestValue, item.total), 1);
  const slotWidth = chartWidth / items.length;
  const barWidth = Math.min(56, slotWidth * 0.5);
  const tickValues = [0.25, 0.5, 0.75, 1].map((ratio) => Math.round(maxValue * ratio)).filter((value, index, values) => value > 0 && values.indexOf(value) === index);

  return (
    <svg className="bar-chart" viewBox={`0 0 ${width} ${height}`} role="img" aria-label="Portfolio coverage chart by domain">
      {tickValues.map((tickValue) => {
        const y = topPadding + chartHeight - (tickValue / maxValue) * chartHeight;

        return (
          <g key={tickValue}>
            <line className="bar-chart__grid" x1={leftPadding} y1={y} x2={width - rightPadding} y2={y} />
            <text className="bar-chart__axis" x={12} y={y + 4}>
              {tickValue}
            </text>
          </g>
        );
      })}

      {items.map((item, index) => {
        const totalHeight = item.total > 0 ? Math.max((item.total / maxValue) * chartHeight, 18) : 0;
        const flaggedHeight = item.flagged > 0 ? Math.max((item.flagged / maxValue) * chartHeight, 8) : 0;
        const x = leftPadding + slotWidth * index + (slotWidth - barWidth) / 2;
        const y = topPadding + chartHeight - totalHeight;
        const flaggedY = topPadding + chartHeight - flaggedHeight;
        const labelX = x + barWidth / 2;

        return (
          <g key={item.key}>
            <text className="bar-chart__value" x={labelX} y={Math.max(18, y - 10)}>
              {item.total}
            </text>
            <rect className={`bar-chart__bar bar-chart__bar--${item.health}`} x={x} y={y} width={barWidth} height={totalHeight} rx="18" />
            {flaggedHeight > 0 ? (
              <rect className={`bar-chart__bar-cap bar-chart__bar-cap--${item.health}`} x={x} y={flaggedY} width={barWidth} height={flaggedHeight} rx="18" />
            ) : null}
            <text className="bar-chart__label" x={labelX} y={height - 18}>
              {item.chartLabel}
            </text>
          </g>
        );
      })}
    </svg>
  );
}

export function ExecutiveBriefingReportPage() {
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
  const metricAttentionCount = toNumber(reportData.metricSummary?.slowestDuration) >= 450 ? 1 : 0;
  const metricWatchCount = metricAttentionCount === 0 && toNumber(reportData.metricSummary?.averageDuration) >= 250 ? 1 : 0;
  const specHealth = getHealthState(toNumber(reportData.specFreshness?.staleCount), toNumber(reportData.specFreshness?.agingCount));
  const gitHealth = getHealthState(toNumber(reportData.gitSummary?.staleCount), toNumber(reportData.gitSummary?.agingCount));
  const runtimeHealth = getHealthState(toNumber(reportData.kubeSummary?.deletedCount), 0);
  const metricHealth = getHealthState(metricAttentionCount, metricWatchCount);
  const pipelineHealth = getHealthState(0, toNumber(reportData.pipelineSummary?.inProgressCount));
  const qualityHealth = getHealthState(0, toNumber(reportData.qualitySummary?.pendingCount));
  const domainCoverage = [
    {
      key: "contracts",
      label: "Contracts",
      chartLabel: "Spec",
      total: toNumber(reportData.specSummary?.specCount),
      flagged: toNumber(reportData.specFreshness?.staleCount) + toNumber(reportData.specFreshness?.agingCount),
      health: specHealth,
      detail: `${formatNumber(reportData.specSummary?.pathCount, 0, "0")} paths · ${formatNumber(reportData.specFreshness?.staleCount, 0, "0")} stale`,
      latestActivity: reportData.specFreshness?.latestSyncTime,
      workspace: buildWorkspaceHandoffPath("/apis/specs", specHealth),
    },
    {
      key: "repositories",
      label: "Repositories",
      chartLabel: "Git",
      total: toNumber(reportData.gitSummary?.totalCount),
      flagged: toNumber(reportData.gitSummary?.staleCount) + toNumber(reportData.gitSummary?.agingCount),
      health: gitHealth,
      detail: `${formatNumber(reportData.gitSummary?.hostCount, 0, "0")} hosts · ${formatNumber(reportData.gitSummary?.staleCount, 0, "0")} stale`,
      latestActivity: reportData.gitSummary?.latestSyncTime,
      workspace: buildWorkspaceHandoffPath("/git/repositories", gitHealth),
    },
    {
      key: "runtime",
      label: "Runtime",
      chartLabel: "Kube",
      total: toNumber(reportData.kubeSummary?.totalCount),
      flagged: toNumber(reportData.kubeSummary?.deletedCount),
      health: runtimeHealth,
      detail: `${formatNumber(reportData.kubeSummary?.activeCount, 0, "0")} active · ${formatNumber(reportData.kubeSummary?.namespaceCount, 0, "0")} namespaces`,
      latestActivity: reportData.kubeSummary?.latestSyncTime,
      workspace: buildWorkspaceHandoffPath("/runtime/pods", runtimeHealth),
    },
    {
      key: "telemetry",
      label: "Telemetry",
      chartLabel: "Metric",
      total: toNumber(reportData.metricSummary?.totalCount),
      flagged: metricAttentionCount + metricWatchCount,
      health: metricHealth,
      detail: `${formatNumber(reportData.metricSummary?.uniqueActionCount, 0, "0")} actions · max ${formatDuration(reportData.metricSummary?.slowestDuration, 0)}`,
      latestActivity: reportData.metricSummary?.latestActionTime,
      workspace: buildWorkspaceHandoffPath("/metrics/executions", metricHealth),
    },
    {
      key: "delivery",
      label: "Delivery",
      chartLabel: "Pipeline",
      total: toNumber(reportData.pipelineSummary?.totalCount),
      flagged: toNumber(reportData.pipelineSummary?.inProgressCount),
      health: pipelineHealth,
      detail: `${formatNumber(reportData.pipelineSummary?.completedCount, 0, "0")} completed · avg ${formatDuration(reportData.pipelineSummary?.averageDuration, 0)}`,
      latestActivity: reportData.pipelineSummary?.latestStartTime,
      workspace: buildWorkspaceHandoffPath("/pipelines/runs", pipelineHealth),
    },
    {
      key: "quality",
      label: "Quality",
      chartLabel: "Quality",
      total: toNumber(reportData.qualitySummary?.totalCount),
      flagged: toNumber(reportData.qualitySummary?.pendingCount),
      health: qualityHealth,
      detail: `${formatNumber(reportData.qualitySummary?.executedCount, 0, "0")} completed · ${formatNumber(reportData.qualitySummary?.pendingCount, 0, "0")} pending`,
      latestActivity: reportData.qualitySummary?.latestActivityTime,
      workspace: buildWorkspaceHandoffPath("/quality/executions", qualityHealth),
    },
  ];
  const totalTrackedRecords = domainCoverage.reduce((sum, domain) => sum + domain.total, 0);
  const activeConcernCount = domainCoverage.reduce((sum, domain) => sum + domain.flagged, 0);
  const healthyDomainCount = domainCoverage.filter((domain) => domain.health === "healthy").length;
  const watchDomainCount = domainCoverage.filter((domain) => domain.health === "watch").length;
  const attentionDomainCount = domainCoverage.filter((domain) => domain.health === "attention").length;
  const latestPlatformSignal = getLatestTimestamp(domainCoverage.map((domain) => domain.latestActivity));
  const prioritizedDomains = [...domainCoverage].sort((leftDomain, rightDomain) => {
    if (rightDomain.flagged !== leftDomain.flagged) {
      return rightDomain.flagged - leftDomain.flagged;
    }
    return toTimestamp(rightDomain.latestActivity) - toTimestamp(leftDomain.latestActivity);
  });
  const executiveHighlights = [
    {
      key: "tracked",
      label: "Tracked records",
      value: formatNumber(totalTrackedRecords, 0, "0"),
      detail: "Combined scope across all live reporting domains.",
      toneClass: "metric-card--blue",
    },
    {
      key: "concerns",
      label: "Active concerns",
      value: formatNumber(activeConcernCount, 0, "0"),
      detail: "Current watch and attention signals that need follow-up.",
      toneClass: "metric-card--violet",
    },
    {
      key: "health",
      label: "Healthy domains",
      value: `${healthyDomainCount}/${domainCoverage.length}`,
      detail: `${watchDomainCount} watch · ${attentionDomainCount} attention`,
      toneClass: "metric-card--amber",
    },
    {
      key: "latest-signal",
      label: "Latest platform signal",
      value: formatDateTime(latestPlatformSignal),
      detail: "Most recent observed activity across the live portfolio.",
      toneClass: "metric-card--slate",
    },
  ];
  const topPriorityDomains = prioritizedDomains.slice(0, 3);
  const generatedAt = new Date().toISOString();

  function handleExportJson() {
    downloadReportJson("athena-executive-briefing.json", {
      report: "executive-briefing",
      exportedAt: generatedAt,
      filters: {
        timeWindow,
      },
      summary: {
        totalTrackedRecords,
        activeConcernCount,
        healthyDomainCount,
        watchDomainCount,
        attentionDomainCount,
        latestPlatformSignal,
      },
      domainCoverage,
      followUpReports,
    });
  }

  function handleExportCsv() {
    downloadReportCsv(
      "athena-executive-briefing.csv",
      [
        { key: "section", label: "Section" },
        { key: "label", label: "Label" },
        { key: "value", label: "Value" },
        { key: "detail", label: "Detail" },
      ],
      [
        {
          section: "Filters",
          label: "Window",
          value: getTimeWindowLabel(timeWindow),
          detail: `Generated ${formatDateTime(generatedAt)}`,
        },
        {
          section: "Executive posture",
          label: "Tracked records",
          value: formatNumber(totalTrackedRecords, 0, "0"),
          detail: `Active concerns ${formatNumber(activeConcernCount, 0, "0")}`,
        },
        {
          section: "Executive posture",
          label: "Domain health mix",
          value: `${healthyDomainCount} healthy / ${watchDomainCount} watch / ${attentionDomainCount} attention`,
          detail: `Latest platform signal ${formatDateTime(latestPlatformSignal)}`,
        },
        ...domainCoverage.map((domain) => ({
          section: "Coverage",
          label: domain.label,
          value: formatNumber(domain.total, 0, "0"),
          detail: `${domain.detail} · ${getHealthLabel(domain.health)}`,
        })),
        ...followUpReports.map((report) => ({
          section: "Follow-up report",
          label: report.label,
          value: report.to,
          detail: report.detail,
        })),
      ],
    );
  }

  return (
    <div className="page-grid">
      <section className="panel panel--spotlight">
        <p className="eyebrow">Executive report</p>
        <h1>Executive Briefing</h1>
        <p className="lead">Leadership-ready portfolio coverage, risk concentration, and next actions across contracts, repositories, runtime, telemetry, delivery, and quality.</p>
        <div className="callout-strip">
          <span>Report route: /reports/executive-briefing</span>
          <span>Live domains: {formatNumber(domainCoverage.length, 0, "0")}</span>
          <span>Tracked records: {formatNumber(totalTrackedRecords, 0, "0")}</span>
          <span>Active concerns: {formatNumber(activeConcernCount, 0, "0")}</span>
          <span>Latest signal: {formatDateTime(latestPlatformSignal)}</span>
          <span>Window: {getTimeWindowLabel(timeWindow)}</span>
        </div>
      </section>

      {reportStatus === "ready" ? (
        <section className="executive-kpi-grid">
          {executiveHighlights.map((card) => (
            <article key={card.key} className={`metric-card ${card.toneClass}`}>
              <p className="eyebrow">{card.label}</p>
              <h2 className="metric-card__value">{card.value}</h2>
              <p className="metric-card__detail">{card.detail}</p>
            </article>
          ))}
        </section>
      ) : null}

      <ReportNavigation />

      <ReportToolbar
        title="Filter, export, or print the executive briefing"
        summary="The window narrows the live briefing pull while keeping one presentation surface for external stakeholders and leadership reviews."
        timeWindow={timeWindow}
        onTimeWindowChange={setTimeWindow}
        filterGroups={[]}
        onExportJson={handleExportJson}
        onExportCsv={handleExportCsv}
        onPrint={triggerReportPrint}
      />

      <section className="panel report-print-summary print-only">
        <div className="section-header">
          <div>
            <p className="eyebrow">Stakeholder brief</p>
            <h2>Executive print summary</h2>
          </div>
          <p className="page-summary">This print layout keeps the portfolio posture readable when the interactive charts are not available.</p>
        </div>
        <div className="token-grid">
          <span className="token-chip">
            <strong>Generated</strong>
            {formatDateTime(generatedAt)}
          </span>
          <span className="token-chip">
            <strong>Window</strong>
            {getTimeWindowLabel(timeWindow)}
          </span>
          <span className="token-chip">
            <strong>Concerns</strong>
            {formatNumber(activeConcernCount, 0, "0")}
          </span>
        </div>
        <div className="summary-grid">
          <article className="summary-stat">
            <p className="eyebrow">Tracked records</p>
            <h3 className="summary-value">{formatNumber(totalTrackedRecords, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Healthy domains</p>
            <h3 className="summary-value">{formatNumber(healthyDomainCount, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Watch domains</p>
            <h3 className="summary-value">{formatNumber(watchDomainCount, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Attention domains</p>
            <h3 className="summary-value">{formatNumber(attentionDomainCount, 0, "0")}</h3>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Operating mix</p>
            <h2>Portfolio coverage by domain</h2>
          </div>
          <p className="page-summary">The layout mirrors a dashboard briefing: high-level KPIs first, then a single larger portfolio chart and a focused risk panel.</p>
        </div>

        {reportStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the executive briefing.</h3>
            <p className="muted">The briefing is waiting on the same live workspace contracts used by the other report routes.</p>
          </div>
        ) : null}

        {reportStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Report unavailable</p>
            <h3>The executive briefing could not be assembled.</h3>
            <p className="muted">{reportErrorMessage}</p>
          </div>
        ) : null}

        {reportStatus === "ready" ? (
          <div className="executive-grid">
            <article className="chart-shell chart-shell--hero">
              <p className="eyebrow">Coverage by domain</p>
              <h3>Portfolio coverage by domain</h3>
              <p className="page-summary">Each bar shows tracked scope, while the highlighted cap shows the subset currently driving watch or attention pressure.</p>
              <ExecutiveActivityChart items={domainCoverage} />
              <div className="trend-legend">
                {domainCoverage.map((domain) => (
                  <span key={domain.key}>
                    <strong>{domain.chartLabel}</strong>
                    {formatNumber(domain.total, 0, "0")} tracked · {formatNumber(domain.flagged, 0, "0")} priority
                  </span>
                ))}
              </div>
            </article>

            <div className="executive-stack">
              <article className="chart-shell chart-shell--dual">
                <p className="eyebrow">Risk mix</p>
                <h3>Domain health distribution</h3>
                <div className="ring-layout">
                  <ExecutiveHealthRing healthyCount={healthyDomainCount} watchCount={watchDomainCount} attentionCount={attentionDomainCount} />
                </div>
              </article>

              <article className="chart-shell">
                <p className="eyebrow">Highest pressure</p>
                <h3>Priority domains</h3>
                <div className="priority-stack">
                  {topPriorityDomains.map((domain) => (
                    <Link key={domain.key} className="priority-card" to={domain.workspace}>
                      <div>
                        <p className="eyebrow">{domain.label}</p>
                        <strong>{formatNumber(domain.flagged, 0, "0")} priority items</strong>
                      </div>
                      <span className={`status-chip ${getHealthToneClass(domain.health)}`}>{getHealthLabel(domain.health)}</span>
                    </Link>
                  ))}
                </div>
              </article>
            </div>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Activity cadence</p>
            <h2>Prioritized domains and next workspace handoffs</h2>
          </div>
          <p className="page-summary">This ordering keeps the highest-pressure domains at the top while preserving a direct handoff into the owning workspace.</p>
        </div>

        {reportStatus === "ready" ? (
          <div className="executive-grid executive-grid--secondary">
            <div className="timeline-list">
              {prioritizedDomains.map((domain) => (
                <Link key={domain.key} className="timeline-item" to={domain.workspace}>
                  <div className="timeline-item__content">
                    <div className="timeline-item__meta">
                      <div>
                        <p className="eyebrow">{domain.label}</p>
                        <h3>{formatNumber(domain.total, 0, "0")} tracked</h3>
                      </div>
                      <span className={`status-chip ${getHealthToneClass(domain.health)}`}>{getHealthLabel(domain.health)}</span>
                    </div>
                    <p className="muted">{domain.detail}</p>
                  </div>
                  <div className="timeline-item__side">
                    <strong className="timeline-item__value">{formatNumber(domain.flagged, 0, "0")}</strong>
                    <span className="timeline-item__caption">priority items</span>
                    <span className="timeline-item__timestamp">{formatDateTime(domain.latestActivity)}</span>
                  </div>
                </Link>
              ))}
            </div>

            <div className="trend-grid">
              <article className="summary-stat">
                <p className="eyebrow">Latest repository bucket</p>
                <h3 className="summary-value">{formatNumber(latestGitBucket?.repositoryCount, 0, "0")}</h3>
                <p className="muted">
                  {latestGitBucket
                    ? `${formatDateTime(latestGitBucket.bucketStart)} · ${formatNumber(latestGitBucket.uniqueHostCount, 0, "0")} hosts`
                    : "No repository trend points returned yet."}
                </p>
              </article>
              <article className="summary-stat">
                <p className="eyebrow">Latest runtime bucket</p>
                <h3 className="summary-value">{formatNumber(latestKubeBucket?.podCount, 0, "0")}</h3>
                <p className="muted">
                  {latestKubeBucket
                    ? `${formatDateTime(latestKubeBucket.bucketStart)} · ${formatNumber(latestKubeBucket.activeCount, 0, "0")} active`
                    : "No runtime trend points returned yet."}
                </p>
              </article>
              <article className="summary-stat">
                <p className="eyebrow">Latest pipeline bucket</p>
                <h3 className="summary-value">{formatNumber(latestPipelineBucket?.pipelineCount, 0, "0")}</h3>
                <p className="muted">
                  {latestPipelineBucket
                    ? `${formatDateTime(latestPipelineBucket.bucketStart)} · ${formatNumber(latestPipelineBucket.completedCount, 0, "0")} completed`
                    : "No pipeline trend points returned yet."}
                </p>
              </article>
              <article className="summary-stat">
                <p className="eyebrow">Latest quality bucket</p>
                <h3 className="summary-value">{formatNumber(latestQualityBucket?.executionCount, 0, "0")}</h3>
                <p className="muted">
                  {latestQualityBucket
                    ? `${formatDateTime(latestQualityBucket.bucketStart)} · ${formatNumber(latestQualityBucket.executedCount, 0, "0")} completed`
                    : "No quality trend points returned yet."}
                </p>
              </article>
              <article className="summary-stat">
                <p className="eyebrow">Latest metric bucket</p>
                <h3 className="summary-value">{formatNumber(latestMetricBucket?.metricCount, 0, "0")}</h3>
                <p className="muted">
                  {latestMetricBucket
                    ? `${formatDateTime(latestMetricBucket.bucketStart)} · max ${formatDuration(latestMetricBucket.maxDuration, 0)}`
                    : "No metric trend points returned yet."}
                </p>
              </article>
            </div>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Recommended drill-downs</p>
            <h2>Move from the briefing into the deeper operational reports</h2>
          </div>
          <p className="page-summary">These report routes carry the conversation from leadership posture into the next level of operational detail.</p>
        </div>

        <div className="readiness-grid">
          {followUpReports.map((report) => (
            <article key={report.to} className="status-card">
              <span className="status-pill status-pill--partial">Follow-up</span>
              <h3>{report.label}</h3>
              <p className="muted">{report.detail}</p>
              <Link to={report.to} className="button button--ghost">
                Open report
              </Link>
            </article>
          ))}
        </div>
      </section>
    </div>
  );
}

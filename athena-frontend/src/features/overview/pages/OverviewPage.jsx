import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { requestJson } from "../../../shared/api/gatewayClient";
import { BarTrendPanel, LineTrendPanel } from "../../../shared/ui/DashboardGraphs";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";
import { formatDateTime, formatDuration, formatNumber, getErrorMessage } from "../../../shared/ui/workspacePageUtils";
import { buildReportPath, getHealthToneClass } from "../../reports/reportUtils";

const moduleReadiness = [
  {
    name: "Core",
    status: "Ready now",
    detail: "Shared context and catalog routes are the first integration target.",
  },
  {
    name: "TMS",
    status: "Partial",
    detail: "Quality summary, trend, inventory, and execution drill-down are now live through the unified shell.",
  },
  {
    name: "Spec",
    status: "Partial",
    detail: "Inventory, detail, freshness, and drift views are now live through the unified browser shell.",
  },
  {
    name: "Metric",
    status: "Partial",
    detail: "Dashboard cards, a trend view, and execution inventory are now live through the metric workspace.",
  },
  {
    name: "Pipeline",
    status: "Partial",
    detail: "Delivery summary, trend, and inventory views are now live through the pipeline workspace.",
  },
  {
    name: "Git",
    status: "Partial",
    detail: "Repository freshness summary, trend, and inventory are now live through the git workspace.",
  },
  {
    name: "Kube",
    status: "Partial",
    detail: "Runtime pod summary, trend, and inventory are now live through the runtime workspace.",
  },
];

const phaseTwoDeliverables = [
  "Run the shell only through athena-gateway under /ui.",
  "Keep all browser API traffic on the gateway prefixes only.",
  "Promote spec and metric slices from inventory-only pages into summary-driven workspaces.",
  "Promote pipeline into a delivery-focused aggregation workspace with summary, trend, and inventory.",
  "Promote TMS into a quality-focused aggregation workspace with summary, trend, and inventory.",
  "Promote kube into a runtime-focused aggregation workspace with summary, trend, and inventory.",
  "Promote git into a repository-freshness workspace with summary, trend, and inventory.",
  "Validate frontend build and gateway coexistence before deeper domain integration.",
];

const reportDrilldowns = [
  {
    to: "/reports/executive-briefing",
    label: "Executive Briefing",
    detail: "Share a leadership-ready summary of coverage, risk concentration, and the next operational drill-downs.",
    cta: "Open Executive Briefing",
  },
  {
    to: "/reports/portfolio",
    label: "Portfolio Report",
    detail: "Open the external-facing operating picture that spans every live reporting domain.",
    cta: "Open Portfolio Report",
  },
  {
    to: "/reports/platform-operations",
    label: "Platform Operations",
    detail: "Drill into contract freshness, repository sync posture, runtime churn, and telemetry health.",
    cta: "Open Platform Operations",
  },
  {
    to: "/reports/release-readiness",
    label: "Release Readiness",
    detail: "Focus on contract freshness, source sync, delivery flow, and execution sign-off before release review.",
    cta: "Open Release Readiness",
  },
  {
    to: "/reports/exception-follow-up",
    label: "Exception Follow-up",
    detail: "Start from watch and attention signals that need the next operational action.",
    cta: "Open Exception Follow-up",
  },
  {
    to: "/reports/delivery-quality",
    label: "Delivery + Quality",
    detail: "Shift from platform posture into release flow, execution pressure, and supporting metrics.",
    cta: "Open Delivery + Quality",
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

export function OverviewPage({ apiRoots }) {
  const [signalStatus, setSignalStatus] = useState("loading");
  const [signalErrorMessage, setSignalErrorMessage] = useState("");
  const [liveSignals, setLiveSignals] = useState({
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
    specFreshness: null,
    specSummary: null,
  });

  useEffect(() => {
    let isCancelled = false;

    setSignalStatus("loading");
    setSignalErrorMessage("");

    Promise.all([
      requestJson(apiRoots.spec, "/summary"),
      requestJson(apiRoots.spec, "/freshness"),
      requestJson(apiRoots.git, "/summary"),
      requestJson(apiRoots.git, "/trend"),
      requestJson(apiRoots.kube, "/summary"),
      requestJson(apiRoots.kube, "/trend"),
      requestJson(apiRoots.metric, "/summary"),
      requestJson(apiRoots.metric, "/trend"),
      requestJson(apiRoots.pipeline, "/summary"),
      requestJson(apiRoots.pipeline, "/trend"),
      requestJson(apiRoots.tms, "/summary"),
      requestJson(apiRoots.tms, "/trend"),
    ])
      .then(
        ([specSummary, specFreshness, gitSummary, gitTrend, kubeSummary, kubeTrend, metricSummary, metricTrend, pipelineSummary, pipelineTrend, qualitySummary, qualityTrend]) => {
          if (isCancelled) {
            return;
          }

          setLiveSignals({
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
            specFreshness: specFreshness ?? null,
            specSummary: specSummary ?? null,
          });
          setSignalStatus("ready");
        },
      )
      .catch((error) => {
        if (isCancelled) {
          return;
        }

        setLiveSignals({
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
          specFreshness: null,
          specSummary: null,
        });
        setSignalErrorMessage(getErrorMessage(error));
        setSignalStatus("error");
      });

    return () => {
      isCancelled = true;
    };
  }, [apiRoots.git, apiRoots.kube, apiRoots.metric, apiRoots.pipeline, apiRoots.spec, apiRoots.tms]);

  const latestGitBucket = liveSignals.gitTrend.at(-1) ?? null;
  const latestKubeBucket = liveSignals.kubeTrend.at(-1) ?? null;
  const latestMetricBucket = liveSignals.metricTrend.at(-1) ?? null;
  const latestPipelineBucket = liveSignals.pipelineTrend.at(-1) ?? null;
  const latestQualityBucket = liveSignals.qualityTrend.at(-1) ?? null;
  const metricAttentionCount = toNumber(liveSignals.metricSummary?.slowestDuration) >= 450 ? 1 : 0;
  const metricWatchCount = metricAttentionCount === 0 && toNumber(liveSignals.metricSummary?.averageDuration) >= 250 ? 1 : 0;
  const overviewSignalCards = [
    {
      key: "specs-matched",
      label: "Specs matched",
      value: formatNumber(liveSignals.specSummary?.specCount, 0, "0"),
      detail: `${formatNumber(liveSignals.specSummary?.pathCount, 0, "0")} tracked paths across ${formatNumber(liveSignals.specSummary?.projectCount, 0, "0")} projects.`,
      to: buildReportPath("/reports/release-readiness", { scope: "contracts" }),
      hint: "Open release readiness filtered to contracts",
      ariaLabel: "Open Release Readiness report for contracts from specs matched overview signal",
    },
    {
      key: "spec-risk",
      label: "Spec sync risk",
      value: `${formatNumber(liveSignals.specFreshness?.staleCount, 0, "0")} stale`,
      detail: `${formatNumber(liveSignals.specFreshness?.agingCount, 0, "0")} aging · latest sync ${formatDateTime(liveSignals.specFreshness?.latestSyncTime)}`,
      to: buildReportPath("/reports/exception-follow-up", { scope: "contracts", health: "attention" }),
      hint: "Open exception follow-up filtered to contract risk",
      ariaLabel: "Open Exception Follow-up report for spec sync risk",
    },
    {
      key: "repositories-tracked",
      label: "Repositories tracked",
      value: formatNumber(liveSignals.gitSummary?.totalCount, 0, "0"),
      detail: `${formatNumber(liveSignals.gitSummary?.hostCount, 0, "0")} hosts · ${formatNumber(liveSignals.gitSummary?.freshCount, 0, "0")} fresh`,
      to: buildReportPath("/reports/platform-operations", { scope: "repositories" }),
      hint: "Open platform operations filtered to repositories",
      ariaLabel: "Open Platform Operations report for repositories tracked overview signal",
    },
    {
      key: "repository-risk",
      label: "Repository sync risk",
      value: `${formatNumber(liveSignals.gitSummary?.staleCount, 0, "0")} stale`,
      detail: latestGitBucket
        ? `${formatDateTime(latestGitBucket.bucketStart)} · ${formatNumber(latestGitBucket.repositoryCount, 0, "0")} synced`
        : `Latest sync ${formatDateTime(liveSignals.gitSummary?.latestSyncTime)}`,
      to: buildReportPath("/reports/exception-follow-up", { scope: "repositories", health: "attention" }),
      hint: "Open exception follow-up filtered to repository risk",
      ariaLabel: "Open Exception Follow-up report for repository sync risk",
    },
    {
      key: "runtime-pods",
      label: "Runtime pods",
      value: formatNumber(liveSignals.kubeSummary?.totalCount, 0, "0"),
      detail: `${formatNumber(liveSignals.kubeSummary?.activeCount, 0, "0")} active · ${formatNumber(liveSignals.kubeSummary?.namespaceCount, 0, "0")} namespaces`,
      to: buildReportPath("/reports/platform-operations", { scope: "runtime" }),
      hint: "Open platform operations filtered to runtime",
      ariaLabel: "Open Platform Operations report for runtime pods overview signal",
    },
    {
      key: "runtime-churn",
      label: "Runtime churn",
      value: `${formatNumber(liveSignals.kubeSummary?.deletedCount, 0, "0")} deleted`,
      detail: latestKubeBucket
        ? `${formatDateTime(latestKubeBucket.bucketStart)} · ${formatNumber(latestKubeBucket.activeCount, 0, "0")} active in bucket`
        : `Latest sync ${formatDateTime(liveSignals.kubeSummary?.latestSyncTime)}`,
      to: buildReportPath("/reports/exception-follow-up", { scope: "runtime", health: "attention" }),
      hint: "Open exception follow-up filtered to runtime churn",
      ariaLabel: "Open Exception Follow-up report for runtime churn",
    },
    {
      key: "recorded-metrics",
      label: "Recorded metrics",
      value: formatNumber(liveSignals.metricSummary?.totalCount, 0, "0"),
      detail: `${formatNumber(liveSignals.metricSummary?.uniqueActionCount, 0, "0")} unique actions · avg ${formatDuration(liveSignals.metricSummary?.averageDuration, 0)}`,
      to: buildReportPath("/reports/platform-operations", { scope: "performance" }),
      hint: "Open platform operations filtered to performance",
      ariaLabel: "Open Platform Operations report for recorded metrics overview signal",
    },
    {
      key: "latest-metric-bucket",
      label: "Latest metric bucket",
      value: formatNumber(latestMetricBucket?.metricCount, 0, "0"),
      detail: latestMetricBucket ? `${formatDateTime(latestMetricBucket.bucketStart)} · max ${formatDuration(latestMetricBucket.maxDuration, 0)}` : "No trend points returned yet.",
      to: buildReportPath("/reports/exception-follow-up", { scope: "performance", health: "watch" }),
      hint: "Open exception follow-up filtered to performance pressure",
      ariaLabel: "Open Exception Follow-up report for latest metric bucket",
    },
    {
      key: "pipeline-runs",
      label: "Pipeline runs",
      value: formatNumber(liveSignals.pipelineSummary?.totalCount, 0, "0"),
      detail: `${formatNumber(liveSignals.pipelineSummary?.completedCount, 0, "0")} completed · ${formatNumber(liveSignals.pipelineSummary?.inProgressCount, 0, "0")} running`,
      to: buildReportPath("/reports/release-readiness", { scope: "delivery" }),
      hint: "Open release readiness filtered to delivery",
      ariaLabel: "Open Release Readiness report for pipeline runs overview signal",
    },
    {
      key: "latest-pipeline-bucket",
      label: "Latest pipeline bucket",
      value: formatNumber(latestPipelineBucket?.pipelineCount, 0, "0"),
      detail: latestPipelineBucket
        ? `${formatDateTime(latestPipelineBucket.bucketStart)} · avg ${formatDuration(latestPipelineBucket.averageDuration, 0)}`
        : "No pipeline trend points returned yet.",
      to: buildReportPath("/reports/release-readiness", { scope: "delivery", health: "watch" }),
      hint: "Open release readiness filtered to delivery pressure",
      ariaLabel: "Open Release Readiness report for latest pipeline bucket",
    },
    {
      key: "quality-executions",
      label: "Quality executions",
      value: formatNumber(liveSignals.qualitySummary?.totalCount, 0, "0"),
      detail: `${formatNumber(liveSignals.qualitySummary?.executedCount, 0, "0")} completed · ${formatNumber(liveSignals.qualitySummary?.pendingCount, 0, "0")} pending`,
      to: buildReportPath("/reports/release-readiness", { scope: "quality" }),
      hint: "Open release readiness filtered to validation flow",
      ariaLabel: "Open Release Readiness report for quality executions overview signal",
    },
    {
      key: "latest-quality-bucket",
      label: "Latest quality bucket",
      value: formatNumber(latestQualityBucket?.executionCount, 0, "0"),
      detail: latestQualityBucket
        ? `${formatDateTime(latestQualityBucket.bucketStart)} · ${formatNumber(latestQualityBucket.executedCount, 0, "0")} completed`
        : "No quality trend points returned yet.",
      to: buildReportPath("/reports/release-readiness", { scope: "quality", health: "watch" }),
      hint: "Open release readiness filtered to validation pressure",
      ariaLabel: "Open Release Readiness report for latest quality bucket",
    },
  ];
  const portfolioBalance = [
    {
      key: "contracts",
      label: "Contracts",
      trackedCount: toNumber(liveSignals.specSummary?.specCount),
      flaggedCount: toNumber(liveSignals.specFreshness?.staleCount) + toNumber(liveSignals.specFreshness?.agingCount),
      health: getHealthState(toNumber(liveSignals.specFreshness?.staleCount), toNumber(liveSignals.specFreshness?.agingCount)),
      detail: `${formatNumber(liveSignals.specSummary?.pathCount, 0, "0")} paths tracked · latest ${formatDateTime(liveSignals.specFreshness?.latestSyncTime)}`,
    },
    {
      key: "repositories",
      label: "Repositories",
      trackedCount: toNumber(liveSignals.gitSummary?.totalCount),
      flaggedCount: toNumber(liveSignals.gitSummary?.staleCount) + toNumber(liveSignals.gitSummary?.agingCount),
      health: getHealthState(toNumber(liveSignals.gitSummary?.staleCount), toNumber(liveSignals.gitSummary?.agingCount)),
      detail: `${formatNumber(liveSignals.gitSummary?.hostCount, 0, "0")} hosts · latest ${formatDateTime(liveSignals.gitSummary?.latestSyncTime)}`,
    },
    {
      key: "runtime",
      label: "Runtime",
      trackedCount: toNumber(liveSignals.kubeSummary?.totalCount),
      flaggedCount: toNumber(liveSignals.kubeSummary?.deletedCount),
      health: getHealthState(toNumber(liveSignals.kubeSummary?.deletedCount), 0),
      detail: `${formatNumber(liveSignals.kubeSummary?.activeCount, 0, "0")} active · ${formatNumber(liveSignals.kubeSummary?.namespaceCount, 0, "0")} namespaces`,
    },
    {
      key: "telemetry",
      label: "Telemetry",
      trackedCount: toNumber(liveSignals.metricSummary?.totalCount),
      flaggedCount: metricAttentionCount + metricWatchCount,
      health: getHealthState(metricAttentionCount, metricWatchCount),
      detail: `${formatNumber(liveSignals.metricSummary?.uniqueActionCount, 0, "0")} actions · max ${formatDuration(liveSignals.metricSummary?.slowestDuration, 0)}`,
    },
    {
      key: "delivery",
      label: "Delivery",
      trackedCount: toNumber(liveSignals.pipelineSummary?.totalCount),
      flaggedCount: toNumber(liveSignals.pipelineSummary?.inProgressCount),
      health: getHealthState(0, toNumber(liveSignals.pipelineSummary?.inProgressCount)),
      detail: `${formatNumber(liveSignals.pipelineSummary?.completedCount, 0, "0")} completed · avg ${formatDuration(liveSignals.pipelineSummary?.averageDuration, 0)}`,
    },
    {
      key: "quality",
      label: "Quality",
      trackedCount: toNumber(liveSignals.qualitySummary?.totalCount),
      flaggedCount: toNumber(liveSignals.qualitySummary?.pendingCount),
      health: getHealthState(0, toNumber(liveSignals.qualitySummary?.pendingCount)),
      detail: `${formatNumber(liveSignals.qualitySummary?.executedCount, 0, "0")} completed · ${formatNumber(liveSignals.qualitySummary?.pendingCount, 0, "0")} pending`,
    },
  ];
  const maxPortfolioBalance = portfolioBalance.reduce((maxValue, item) => Math.max(maxValue, item.trackedCount), 1);
  const trackedRecordCount = portfolioBalance.reduce((totalCount, item) => totalCount + item.trackedCount, 0);
  const flaggedDomainCount = portfolioBalance.filter((item) => item.flaggedCount > 0).length;
  const latestOverviewSignal = getLatestTimestamp([
    liveSignals.specFreshness?.latestSyncTime,
    liveSignals.gitSummary?.latestSyncTime,
    liveSignals.kubeSummary?.latestSyncTime,
    liveSignals.metricSummary?.latestActionTime,
    liveSignals.pipelineSummary?.latestStartTime,
    liveSignals.qualitySummary?.latestActivityTime,
  ]);

  return (
    <div className="page-grid">
      <DashboardPageHero
        eyebrow="Phase 2 control tower"
        title="Overview"
        summary="The shell is no longer just a routing baseline. It now exposes live API specification and metric reporting signals, delivery aggregation, and quality execution aggregation through the same gateway-only browser path the production UI will keep using, while dedicated git and runtime workspaces are now available for repository freshness and pod inventory reporting."
        callouts={["Browser route prefix: /ui", "API policy: gateway-only", "Current focus: live spec + git + runtime + metric + pipeline + quality oversight"]}
        metrics={[
          {
            key: "live-domains",
            label: "Live domains",
            value: "6",
            detail: "Spec, Git, Runtime, Metric, Delivery, and Quality slices.",
            toneClass: "metric-card--blue",
          },
          {
            key: "tracked-records",
            label: "Tracked records",
            value: formatNumber(trackedRecordCount, 0, "0"),
            detail: "Combined scope across the live overview feeds.",
            toneClass: "metric-card--violet",
          },
          {
            key: "flagged-domains",
            label: "Flagged domains",
            value: formatNumber(flaggedDomainCount, 0, "0"),
            detail: `${formatNumber(reportDrilldowns.length, 0, "0")} stakeholder-ready report routes available.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "latest-overview-signal",
            label: "Latest signal",
            value: formatDateTime(latestOverviewSignal),
            detail: "Freshest sync or activity visible through the control room.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Live domain signals</p>
            <h2>Spec, git, runtime, metric, pipeline, and quality workspaces now feed the overview directly.</h2>
          </div>
          <p className="page-summary">These cards are loaded from the same gateway endpoints used by the dedicated workspaces.</p>
        </div>

        {signalStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Pulling live overview signals from spec, git, runtime, metric, pipeline, and quality.</h3>
            <p className="muted">The overview waits on the same summary, freshness, and trend contracts the downstream pages use.</p>
          </div>
        ) : null}

        {signalStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Signals unavailable</p>
            <h3>The overview could not load live workspace signals.</h3>
            <p className="muted">{signalErrorMessage}</p>
          </div>
        ) : null}

        {signalStatus === "ready" ? (
          <div className="summary-grid">
            {overviewSignalCards.map((item) => (
              <Link key={item.key} className="summary-stat summary-stat--link" to={item.to} aria-label={item.ariaLabel}>
                <p className="eyebrow">{item.label}</p>
                <h3 className="summary-value">{item.value}</h3>
                <p className="muted">{item.detail}</p>
                <span className="summary-link-hint">{item.hint}</span>
              </Link>
            ))}
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Portfolio balance</p>
            <h2>Coverage and operational pressure across every live domain</h2>
          </div>
          <p className="page-summary">The coverage bars pair tracked scope with the subset currently driving watch or attention pressure.</p>
        </div>

        {signalStatus === "ready" ? (
          <>
            <div className="comparison-grid">
              {portfolioBalance.map((item) => {
                const trackedWidth = item.trackedCount > 0 ? Math.max((item.trackedCount / maxPortfolioBalance) * 100, 14) : 0;
                const flaggedWidth = item.flaggedCount > 0 ? Math.max((item.flaggedCount / maxPortfolioBalance) * 100, 8) : 0;

                return (
                  <article key={item.key} className="comparison-row">
                    <div className="comparison-meta">
                      <div>
                        <p className="eyebrow">{item.label}</p>
                        <h3>{formatNumber(item.trackedCount, 0, "0")} tracked</h3>
                      </div>
                      <span className={`status-chip ${getHealthToneClass(item.health)}`}>{getHealthLabel(item.health)}</span>
                    </div>
                    <div className="comparison-track" aria-hidden="true">
                      <span className="comparison-fill" style={{ width: `${trackedWidth}%` }} />
                      {flaggedWidth > 0 ? <span className={`comparison-fill comparison-fill--${item.health}`} style={{ width: `${flaggedWidth}%` }} /> : null}
                    </div>
                    <p className="muted">{item.detail}</p>
                  </article>
                );
              })}
            </div>

            <div className="button-row">
              <Link className="button button--ghost" to="/reports/executive-briefing">
                Open Executive Briefing
              </Link>
            </div>
          </>
        ) : (
          <p className="muted">This chart fills in once the shared live overview signals are available.</p>
        )}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Operational graph board</p>
            <h2>Grafana-style live trend dashboard across Athena domains</h2>
          </div>
          <p className="page-summary">These panels expose live trend movement so operators can spot shifts quickly before drilling into workspaces.</p>
        </div>

        {signalStatus === "ready" ? (
          <div className="dashboard-graph-grid">
            <LineTrendPanel
              title="Repository sync throughput"
              subtitle="Repository and host counts over the latest trend buckets"
              points={liveSignals.gitTrend}
              primaryKey="repositoryCount"
              secondaryKey="uniqueHostCount"
              primaryLabel="Repositories"
              secondaryLabel="Hosts"
            />
            <LineTrendPanel
              title="Runtime pod stability"
              subtitle="Total pods versus active pods for runtime stability"
              points={liveSignals.kubeTrend}
              primaryKey="podCount"
              secondaryKey="activeCount"
              primaryLabel="Pods"
              secondaryLabel="Active"
            />
            <LineTrendPanel
              title="Delivery execution flow"
              subtitle="Pipeline starts versus completed runs"
              points={liveSignals.pipelineTrend}
              primaryKey="pipelineCount"
              secondaryKey="completedCount"
              primaryLabel="Started"
              secondaryLabel="Completed"
            />
            <BarTrendPanel
              title="Telemetry latency pressure"
              subtitle="Maximum duration per metric bucket"
              points={liveSignals.metricTrend}
              valueKey="maxDuration"
              valueLabel="Max duration"
              valueFormatter={(value) => formatDuration(value, 0)}
            />
          </div>
        ) : (
          <div className="empty-state">
            <p className="eyebrow">Loading graph board</p>
            <p className="muted">Graph panels appear once the live trend feeds are available.</p>
          </div>
        )}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Execution focus</p>
            <h2>What ships in this slice</h2>
          </div>
        </div>
        <ul className="bullet-list">
          {phaseTwoDeliverables.map((item) => (
            <li key={item}>{item}</li>
          ))}
        </ul>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Report drill-downs</p>
            <h2>Move from the overview into stakeholder-ready report routes</h2>
          </div>
          <p className="page-summary">These routes stay inside /reports/* and package the live workspace signals into audience-specific reporting views.</p>
        </div>
        <div className="readiness-grid">
          {reportDrilldowns.map((item) => (
            <article key={item.to} className="status-card">
              <p className="eyebrow">/reports/*</p>
              <h3>{item.label}</h3>
              <p className="muted">{item.detail}</p>
              <Link className="button button--ghost" to={item.to}>
                {item.cta}
              </Link>
            </article>
          ))}
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Gateway API prefixes</p>
            <h2>Only these prefixes are valid browser targets</h2>
          </div>
        </div>
        <div className="token-grid">
          {Object.entries(apiRoots).map(([label, path]) => (
            <span key={label} className="token-chip">
              <strong>{label}</strong>
              <span>{path}</span>
            </span>
          ))}
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Readiness snapshot</p>
            <h2>Browser-ready reporting now covers the current cross-domain portfolio.</h2>
          </div>
        </div>
        <div className="readiness-grid">
          {moduleReadiness.map((item) => (
            <article key={item.name} className="status-card">
              <span className={`status-pill status-pill--${item.status.toLowerCase().replace(/[^a-z]+/g, "-")}`}>{item.status}</span>
              <h3>{item.name}</h3>
              <p className="muted">{item.detail}</p>
            </article>
          ))}
        </div>
      </section>
    </div>
  );
}

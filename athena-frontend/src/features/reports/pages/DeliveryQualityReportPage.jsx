import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { LineTrendPanel } from "../../../shared/ui/DashboardGraphs";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";
import { formatDateTime, formatDuration, formatNumber, getErrorMessage } from "../../../shared/ui/workspacePageUtils";
import { ReportNavigation } from "../components/ReportNavigation";
import { ReportToolbar } from "../components/ReportToolbar";
import { buildReportPath, buildWorkspaceHandoffPath, downloadReportCsv, downloadReportJson, getTimeWindowDays, getTimeWindowLabel, triggerReportPrint } from "../reportUtils";

const handoffRoutes = [
  {
    to: "/pipelines/runs",
    label: "Pipeline Runs",
    detail: "Open the delivery workspace for run inventory and status filtering.",
  },
  {
    to: "/quality/executions",
    label: "Quality Runs",
    detail: "Open the quality workspace for execution inventory and drill-down.",
  },
  {
    to: "/metrics/executions",
    label: "Metric Runs",
    detail: "Open the metric workspace for performance investigations behind delivery and quality signals.",
  },
];

function buildRecentBuckets(points, projector) {
  return points.slice(-3).reverse().map(projector);
}

function buildDemoDeliveryQualityData() {
  const now = new Date();
  const toBucket = (offsetDays) => {
    const date = new Date(now);
    date.setDate(now.getDate() - offsetDays);
    date.setHours(10, 0, 0, 0);
    return date.toISOString();
  };

  const pipelineTrend = Array.from({ length: 8 }, (_, index) => ({
    bucketStart: toBucket(7 - index),
    pipelineCount: 24 + index * 2,
    completedCount: 19 + index * 2,
    averageDuration: 208 + index * 4,
  }));
  const qualityTrend = Array.from({ length: 8 }, (_, index) => ({
    bucketStart: toBucket(7 - index),
    executionCount: 20 + index * 2,
    executedCount: 16 + index * 2,
    uniqueItemCount: 12 + index,
  }));
  const metricTrend = Array.from({ length: 8 }, (_, index) => ({
    bucketStart: toBucket(7 - index),
    metricCount: 132 + index * 9,
    averageDuration: 168 + index * 5,
    maxDuration: 318 + index * 20,
  }));

  return {
    metricSummary: {
      totalCount: 204,
      uniqueActionCount: 35,
      averageDuration: 188,
    },
    metricTrend,
    pipelineSummary: {
      totalCount: 40,
      completedCount: 33,
      inProgressCount: 5,
    },
    pipelineTrend,
    qualitySummary: {
      totalCount: 34,
      executedCount: 27,
      pendingCount: 4,
    },
    qualityTrend,
  };
}

export function DeliveryQualityReportPage() {
  const [timeWindow, setTimeWindow] = useState("all");
  const [reportStatus, setReportStatus] = useState("loading");
  const [reportErrorMessage, setReportErrorMessage] = useState("");
  const [reportData, setReportData] = useState({
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
      requestJson(apiRoots.metric, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.metric, buildReportPath("/trend", { windowDays })),
      requestJson(apiRoots.pipeline, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.pipeline, buildReportPath("/trend", { windowDays })),
      requestJson(apiRoots.tms, buildReportPath("/summary", { windowDays })),
      requestJson(apiRoots.tms, buildReportPath("/trend", { windowDays })),
    ])
      .then(([metricSummary, metricTrend, pipelineSummary, pipelineTrend, qualitySummary, qualityTrend]) => {
        if (isCancelled) {
          return;
        }

        setReportData({
          metricSummary: metricSummary ?? null,
          metricTrend: Array.isArray(metricTrend) ? metricTrend : [],
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

        setReportData(buildDemoDeliveryQualityData());
        setReportErrorMessage(`Live data is unavailable (${getErrorMessage(error)}). Showing demo delivery data.`);
        setReportStatus("ready");
      });

    return () => {
      isCancelled = true;
    };
  }, [timeWindow]);

  const latestMetricBucket = reportData.metricTrend.at(-1) ?? null;
  const latestPipelineBucket = reportData.pipelineTrend.at(-1) ?? null;
  const latestQualityBucket = reportData.qualityTrend.at(-1) ?? null;
  const isDemoData = Boolean(reportErrorMessage);
  const deliveryPressure = Number(reportData.pipelineSummary?.inProgressCount ?? 0) + Number(reportData.qualitySummary?.pendingCount ?? 0);
  const recentPipelineBuckets = buildRecentBuckets(reportData.pipelineTrend, (point) => ({
    key: `pipeline-${point.bucketStart}`,
    label: formatDateTime(point.bucketStart),
    value: `${formatNumber(point.pipelineCount, 0, "0")} runs`,
    detail: `${formatNumber(point.completedCount, 0, "0")} completed · avg ${formatDuration(point.averageDuration, 0)}`,
  }));
  const recentQualityBuckets = buildRecentBuckets(reportData.qualityTrend, (point) => ({
    key: `quality-${point.bucketStart}`,
    label: formatDateTime(point.bucketStart),
    value: `${formatNumber(point.executionCount, 0, "0")} executions`,
    detail: `${formatNumber(point.executedCount, 0, "0")} completed · ${formatNumber(point.uniqueItemCount, 0, "0")} items`,
  }));
  const recentMetricBuckets = buildRecentBuckets(reportData.metricTrend, (point) => ({
    key: `metric-${point.bucketStart}`,
    label: formatDateTime(point.bucketStart),
    value: `${formatNumber(point.metricCount, 0, "0")} executions`,
    detail: `Avg ${formatDuration(point.averageDuration, 0)} · Max ${formatDuration(point.maxDuration, 0)}`,
  }));
  const pipelineExecutionRows = reportData.pipelineTrend
    .slice(-10)
    .reverse()
    .map((point) => {
      const pipelineCount = Number(point.pipelineCount ?? 0);
      const completedCount = Number(point.completedCount ?? 0);
      const runningCount = Math.max(pipelineCount - completedCount, 0);
      const completionRate = pipelineCount > 0 ? (completedCount / pipelineCount) * 100 : 0;

      return {
        key: point.bucketStart,
        bucketStart: point.bucketStart,
        pipelineCount,
        completedCount,
        runningCount,
        completionRate,
        averageDuration: point.averageDuration,
      };
    });
  const generatedAt = new Date().toISOString();

  function handleExportJson() {
    downloadReportJson("athena-delivery-quality-report.json", {
      report: "delivery-quality",
      exportedAt: generatedAt,
      filters: {
        timeWindow,
      },
      summary: {
        deliveryPressure,
        latestPipelineBucket,
        latestQualityBucket,
        latestMetricBucket,
      },
      recentPipelineBuckets,
      recentQualityBuckets,
      recentMetricBuckets,
    });
  }

  function handleExportCsv() {
    downloadReportCsv(
      "athena-delivery-quality-report.csv",
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
          section: "Snapshot",
          label: "Delivery pressure",
          value: formatNumber(deliveryPressure, 0, "0"),
          detail: "Combined in-progress pipeline runs and pending quality executions",
        },
        {
          section: "Snapshot",
          label: "Latest pipeline signal",
          value: formatNumber(latestPipelineBucket?.pipelineCount, 0, "0"),
          detail: latestPipelineBucket
            ? `${formatDateTime(latestPipelineBucket.bucketStart)} · ${formatNumber(latestPipelineBucket.completedCount, 0, "0")} completed`
            : "No pipeline trend points returned yet.",
        },
        {
          section: "Snapshot",
          label: "Latest quality signal",
          value: formatNumber(latestQualityBucket?.executionCount, 0, "0"),
          detail: latestQualityBucket
            ? `${formatDateTime(latestQualityBucket.bucketStart)} · ${formatNumber(latestQualityBucket.executedCount, 0, "0")} completed`
            : "No quality trend points returned yet.",
        },
        {
          section: "Snapshot",
          label: "Latest metric signal",
          value: formatNumber(latestMetricBucket?.metricCount, 0, "0"),
          detail: latestMetricBucket
            ? `${formatDateTime(latestMetricBucket.bucketStart)} · max ${formatDuration(latestMetricBucket.maxDuration, 0)}`
            : "No metric trend points returned yet.",
        },
        ...recentPipelineBuckets.map((item) => ({
          section: "Pipeline trend",
          label: item.label,
          value: item.value,
          detail: item.detail,
        })),
        ...recentQualityBuckets.map((item) => ({
          section: "Quality trend",
          label: item.label,
          value: item.value,
          detail: item.detail,
        })),
        ...recentMetricBuckets.map((item) => ({
          section: "Metric trend",
          label: item.label,
          value: item.value,
          detail: item.detail,
        })),
      ],
    );
  }

  return (
    <div className="page-grid delivery-quality-page">
      <DashboardPageHero
        eyebrow="Trend report"
        title="Delivery + Quality Report"
        summary="This report narrows the platform view to delivery execution, test execution, and supporting telemetry so teams can review operational flow across release movement, execution quality, and performance pressure in one place."
        callouts={[
          "Report route: /reports/delivery-quality",
          isDemoData ? "Data source: demo mode" : "Data source: live services",
          `Delivery pressure: ${formatNumber(deliveryPressure, 0, "0")}`,
          `Window: ${getTimeWindowLabel(timeWindow)}`,
          `Latest pipeline bucket: ${formatDateTime(latestPipelineBucket?.bucketStart)}`,
          `Latest quality bucket: ${formatDateTime(latestQualityBucket?.bucketStart)}`,
        ]}
        metrics={[
          {
            key: "delivery-pressure",
            label: "Delivery pressure",
            value: formatNumber(deliveryPressure, 0, "0"),
            detail: "Combined in-progress pipeline runs and pending quality executions.",
            toneClass: "metric-card--blue",
          },
          {
            key: "latest-pipeline",
            label: "Latest pipeline",
            value: formatNumber(latestPipelineBucket?.pipelineCount, 0, "0"),
            detail: latestPipelineBucket
              ? `${formatDateTime(latestPipelineBucket.bucketStart)} · ${formatNumber(latestPipelineBucket.completedCount, 0, "0")} completed`
              : "No pipeline trend points returned yet.",
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-quality",
            label: "Latest quality",
            value: formatNumber(latestQualityBucket?.executionCount, 0, "0"),
            detail: latestQualityBucket
              ? `${formatDateTime(latestQualityBucket.bucketStart)} · ${formatNumber(latestQualityBucket.executedCount, 0, "0")} completed`
              : "No quality trend points returned yet.",
            toneClass: "metric-card--amber",
          },
          {
            key: "latest-metric",
            label: "Latest metric",
            value: formatNumber(latestMetricBucket?.metricCount, 0, "0"),
            detail: latestMetricBucket
              ? `${formatDateTime(latestMetricBucket.bucketStart)} · max ${formatDuration(latestMetricBucket.maxDuration, 0)}`
              : "No metric trend points returned yet.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <ReportNavigation />

      {isDemoData ? (
        <section className="panel">
          <div className="empty-state">
            <p className="eyebrow">Demo data active</p>
            <h3>Pipeline chart and table are rendering from sample data.</h3>
            <p className="muted">{reportErrorMessage}</p>
          </div>
        </section>
      ) : null}

      <ReportToolbar
        title="Filter, export, or print the delivery and quality report"
        summary="The time window narrows the pipeline, quality, and telemetry trend boards while summary cards stay full-scope snapshots."
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
            <h2>Delivery and quality print summary</h2>
          </div>
          <p className="page-summary">This print layout keeps release flow, execution posture, and performance pressure visible without the interactive toolbar.</p>
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
            <strong>Delivery pressure</strong>
            {formatNumber(deliveryPressure, 0, "0")}
          </span>
        </div>
        <div className="summary-grid">
          <article className="summary-stat">
            <p className="eyebrow">Pipeline runs</p>
            <h3 className="summary-value">{formatNumber(reportData.pipelineSummary?.totalCount, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Quality executions</p>
            <h3 className="summary-value">{formatNumber(reportData.qualitySummary?.totalCount, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Metric executions</p>
            <h3 className="summary-value">{formatNumber(reportData.metricSummary?.totalCount, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Latest pipeline bucket</p>
            <h3 className="summary-value summary-value--compact">{formatDateTime(latestPipelineBucket?.bucketStart)}</h3>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Delivery snapshot</p>
            <h2>Coordinated delivery and execution picture</h2>
          </div>
          <p className="page-summary">These cards connect the pipeline, quality, and metric workspaces into one focused reporting surface.</p>
        </div>

        {reportStatus === "loading" ? (
          <div className="empty-state">
            <p className="eyebrow">Loading</p>
            <h3>Building the delivery and quality report.</h3>
            <p className="muted">The report is waiting on the live summary and trend contracts from pipeline, quality, and metric.</p>
          </div>
        ) : null}

        {reportStatus === "error" ? (
          <div className="error-card">
            <p className="eyebrow">Report unavailable</p>
            <h3>The delivery and quality report could not be assembled.</h3>
            <p className="muted">{reportErrorMessage}</p>
          </div>
        ) : null}

        {reportStatus === "ready" ? (
          <div className="summary-grid">
            <article className="summary-stat">
              <p className="eyebrow">Pipeline runs</p>
              <h3 className="summary-value">{formatNumber(reportData.pipelineSummary?.totalCount, 0, "0")}</h3>
              <p className="muted">
                {formatNumber(reportData.pipelineSummary?.completedCount, 0, "0")} completed · {formatNumber(reportData.pipelineSummary?.inProgressCount, 0, "0")} in progress
              </p>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Quality executions</p>
              <h3 className="summary-value">{formatNumber(reportData.qualitySummary?.totalCount, 0, "0")}</h3>
              <p className="muted">
                {formatNumber(reportData.qualitySummary?.executedCount, 0, "0")} completed · {formatNumber(reportData.qualitySummary?.pendingCount, 0, "0")} pending
              </p>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Metric executions</p>
              <h3 className="summary-value">{formatNumber(reportData.metricSummary?.totalCount, 0, "0")}</h3>
              <p className="muted">
                {formatNumber(reportData.metricSummary?.uniqueActionCount, 0, "0")} actions · avg {formatDuration(reportData.metricSummary?.averageDuration, 0)}
              </p>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Latest pipeline signal</p>
              <h3 className="summary-value">{formatNumber(latestPipelineBucket?.pipelineCount, 0, "0")}</h3>
              <p className="muted">
                {latestPipelineBucket
                  ? `${formatDateTime(latestPipelineBucket.bucketStart)} · ${formatNumber(latestPipelineBucket.completedCount, 0, "0")} completed`
                  : "No pipeline trend points returned yet."}
              </p>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Latest quality signal</p>
              <h3 className="summary-value">{formatNumber(latestQualityBucket?.executionCount, 0, "0")}</h3>
              <p className="muted">
                {latestQualityBucket
                  ? `${formatDateTime(latestQualityBucket.bucketStart)} · ${formatNumber(latestQualityBucket.executedCount, 0, "0")} completed`
                  : "No quality trend points returned yet."}
              </p>
            </article>
            <article className="summary-stat">
              <p className="eyebrow">Latest metric signal</p>
              <h3 className="summary-value">{formatNumber(latestMetricBucket?.metricCount, 0, "0")}</h3>
              <p className="muted">
                {latestMetricBucket
                  ? `${formatDateTime(latestMetricBucket.bucketStart)} · max ${formatDuration(latestMetricBucket.maxDuration, 0)}`
                  : "No metric trend points returned yet."}
              </p>
            </article>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Trend board</p>
            <h2>Recent pipeline, quality, and metric buckets</h2>
          </div>
          <p className="page-summary">The latest three buckets from each domain stay side by side so the report keeps trend context visible.</p>
        </div>

        {reportStatus === "ready" ? (
          <div className="readiness-grid">
            <article className="status-card">
              <span className="status-pill status-pill--partial">Pipeline</span>
              <h3>Delivery trend</h3>
              {recentPipelineBuckets.map((item) => (
                <div key={item.key}>
                  <p className="muted">{item.label}</p>
                  <p className="muted">{item.value}</p>
                  <p className="muted">{item.detail}</p>
                </div>
              ))}
            </article>
            <article className="status-card">
              <span className="status-pill status-pill--partial">Quality</span>
              <h3>Execution trend</h3>
              {recentQualityBuckets.map((item) => (
                <div key={item.key}>
                  <p className="muted">{item.label}</p>
                  <p className="muted">{item.value}</p>
                  <p className="muted">{item.detail}</p>
                </div>
              ))}
            </article>
            <article className="status-card">
              <span className="status-pill status-pill--partial">Telemetry</span>
              <h3>Performance trend</h3>
              {recentMetricBuckets.map((item) => (
                <div key={item.key}>
                  <p className="muted">{item.label}</p>
                  <p className="muted">{item.value}</p>
                  <p className="muted">{item.detail}</p>
                </div>
              ))}
            </article>
          </div>
        ) : null}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Pipeline execution result</p>
            <h2>Pipeline chart and execution table</h2>
          </div>
          <p className="page-summary">Use the chart for movement and the table for bucket-level counts, completion rate, and duration.</p>
        </div>

        {reportStatus === "ready" ? (
          <div className="page-grid">
            <LineTrendPanel
              title="Pipeline execution result trend"
              subtitle="Total pipeline runs versus completed runs per bucket"
              points={reportData.pipelineTrend}
              primaryKey="pipelineCount"
              secondaryKey="completedCount"
              primaryLabel="Pipeline runs"
              secondaryLabel="Completed"
            />

            <div className="table-shell">
              <table className="data-table" aria-label="Pipeline execution result table">
                <thead>
                  <tr>
                    <th scope="col">Bucket</th>
                    <th scope="col">Runs</th>
                    <th scope="col">Completed</th>
                    <th scope="col">Running</th>
                    <th scope="col">Completion</th>
                    <th scope="col">Avg duration</th>
                  </tr>
                </thead>
                <tbody>
                  {pipelineExecutionRows.map((row) => (
                    <tr key={row.key}>
                      <td>{formatDateTime(row.bucketStart)}</td>
                      <td>{formatNumber(row.pipelineCount, 0, "0")}</td>
                      <td>{formatNumber(row.completedCount, 0, "0")}</td>
                      <td>{formatNumber(row.runningCount, 0, "0")}</td>
                      <td>{formatNumber(row.completionRate, 1, "0")}%</td>
                      <td>{formatDuration(row.averageDuration, 0)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        ) : (
          <div className="empty-state">
            <p className="eyebrow">Loading pipeline result</p>
            <p className="muted">Pipeline execution chart and table appear when trend data is ready.</p>
          </div>
        )}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Drill-down handoffs</p>
            <h2>Move from report summary to operational workspace</h2>
          </div>
          <p className="page-summary">These links are the next investigative step when the trend board shows delivery pressure or execution drift.</p>
        </div>

        <div className="readiness-grid">
          {handoffRoutes.map((item) => (
            <article key={item.to} className="status-card">
              <span className="status-pill status-pill--partial">Handoff</span>
              <h3>{item.label}</h3>
              <p className="muted">{item.detail}</p>
              <Link to={buildWorkspaceHandoffPath(item.to)} className="button button--ghost">
                Open workspace
              </Link>
            </article>
          ))}
        </div>
      </section>
    </div>
  );
}

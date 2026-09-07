import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";
import { formatDateTime, formatNumber, getErrorMessage } from "../../../shared/ui/workspacePageUtils";
import { getStatusToneClass } from "../statusTone";

function getExecutionState(executedOn) {
  if (!executedOn) {
    return {
      badgeClassName: "status-chip status-chip--warning",
      label: "Pending execution",
    };
  }

  return {
    badgeClassName: "status-chip status-chip--success",
    label: "Executed",
  };
}

function getElapsedLabel(createdOn, executedOn) {
  if (!createdOn || !executedOn) {
    return "—";
  }

  const durationMs = new Date(executedOn).getTime() - new Date(createdOn).getTime();
  if (Number.isNaN(durationMs) || durationMs < 0) {
    return "—";
  }

  const durationMinutes = durationMs / (1000 * 60);
  if (durationMinutes < 1) {
    return `${formatNumber(durationMs / 1000, 1, "0")} sec`;
  }

  if (durationMinutes < 60) {
    return `${formatNumber(durationMinutes, 1, "0")} min`;
  }

  const durationHours = durationMinutes / 60;
  if (durationHours < 24) {
    return `${formatNumber(durationHours, 1, "0")} hr`;
  }

  return `${formatNumber(durationHours / 24, 1, "0")} d`;
}

export function QualityExecutionDetailPage() {
  const { id } = useParams();
  const [detailData, setDetailData] = useState(null);
  const [status, setStatus] = useState("loading");
  const [errorMessage, setErrorMessage] = useState("");
  const [reloadToken, setReloadToken] = useState(0);

  useEffect(() => {
    let isCancelled = false;

    setStatus("loading");
    setErrorMessage("");

    requestJson(apiRoots.tms, `/execution/${id}`)
      .then((payload) => {
        if (isCancelled) {
          return;
        }

        setDetailData(payload ?? null);
        setStatus("ready");
      })
      .catch((error) => {
        if (isCancelled) {
          return;
        }

        setDetailData(null);
        setErrorMessage(getErrorMessage(error));
        setStatus("error");
      });

    return () => {
      isCancelled = true;
    };
  }, [id, reloadToken]);

  if (status === "loading") {
    return (
      <div className="page-grid">
        <DashboardPageHero
          backLink="/quality/executions"
          backLabel="Back to quality workspace"
          eyebrow="Execution detail"
          title="Loading execution detail"
          summary="Pulling the full TMS execution payload through the gateway."
        />
      </div>
    );
  }

  if (status === "error") {
    return (
      <div className="page-grid">
        <DashboardPageHero
          backLink="/quality/executions"
          backLabel="Back to quality workspace"
          eyebrow="Execution detail"
          title="Execution detail is unavailable"
          summary="The gateway could not return the full quality execution payload."
          callouts={[`Gateway root: ${apiRoots.tms}`, "Endpoint: /tms/execution/{id}"]}
        />

        <section className="panel">
          <div className="error-card">
            <p className="eyebrow">Gateway request failed</p>
            <h3>Execution detail could not be loaded.</h3>
            <p className="muted">{errorMessage}</p>
            <div className="button-row">
              <button type="button" className="button button--primary" onClick={() => setReloadToken((current) => current + 1)}>
                Retry
              </button>
              <Link className="button button--ghost" to="/quality/executions">
                Back to workspace
              </Link>
            </div>
          </div>
        </section>
      </div>
    );
  }

  if (!detailData) {
    return (
      <div className="page-grid">
        <DashboardPageHero
          backLink="/quality/executions"
          backLabel="Back to quality workspace"
          eyebrow="Execution detail"
          title="No execution payload was returned"
          summary="The selected execution id does not currently resolve to a detailed TMS record."
        />
      </div>
    );
  }

  const executionState = getExecutionState(detailData.executedOn);

  return (
    <div className="page-grid">
      <DashboardPageHero
        backLink="/quality/executions"
        backLabel="Back to quality workspace"
        eyebrow="Execution detail"
        title={detailData.item ?? `Execution ${id}`}
        summary={`Execution ${formatNumber(detailData.id, 0, String(id))} is currently assigned status ${detailData.status ?? "UNKNOWN"}${detailData.executor ? ` and last touched by ${detailData.executor}` : ""}.`}
        callouts={[`Gateway root: ${apiRoots.tms}`, "Endpoint: /tms/execution/{id}", detailData.status ?? "UNKNOWN", executionState.label]}
        metrics={[
          {
            key: "execution-status",
            label: "Execution status",
            value: detailData.status ?? "UNKNOWN",
            detail: executionState.label,
            toneClass: "metric-card--blue",
          },
          {
            key: "executor",
            label: "Executor",
            value: detailData.executor ?? "—",
            detail: `Created ${formatDateTime(detailData.createdOn)}.`,
            toneClass: "metric-card--violet",
          },
          {
            key: "completion-window",
            label: "Completion window",
            value: getElapsedLabel(detailData.createdOn, detailData.executedOn),
            detail: `Executed ${formatDateTime(detailData.executedOn)}.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "gateway-route",
            label: "Gateway route",
            value: `/execution/${id}`,
            detail: `Execution id ${formatNumber(detailData.id, 0, String(id))}.`,
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Execution summary</p>
            <h2>Lifecycle and ownership</h2>
          </div>
        </div>

        <div className="summary-grid">
          <article className="summary-stat">
            <p className="eyebrow">Execution id</p>
            <h3 className="summary-value">{formatNumber(detailData.id, 0, String(id))}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Item</p>
            <h3 className="summary-value summary-value--compact">{detailData.item ?? "—"}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Status</p>
            <h3 className="summary-value summary-value--compact">{detailData.status ?? "UNKNOWN"}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Executor</p>
            <h3 className="summary-value summary-value--compact">{detailData.executor ?? "—"}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Created</p>
            <h3 className="summary-value summary-value--compact">{formatDateTime(detailData.createdOn)}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Executed</p>
            <h3 className="summary-value summary-value--compact">{formatDateTime(detailData.executedOn)}</h3>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Execution timeline</p>
            <h2>Operational view</h2>
          </div>
          <p className="page-summary">This drill-down stays intentionally light until richer TMS execution metadata is exposed by the service.</p>
        </div>

        <div className="summary-grid">
          <article className="summary-stat">
            <p className="eyebrow">Completion window</p>
            <h3 className="summary-value summary-value--compact">{getElapsedLabel(detailData.createdOn, detailData.executedOn)}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Execution state</p>
            <h3 className="summary-value summary-value--compact">{executionState.label}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Gateway route</p>
            <h3 className="summary-value summary-value--compact">/execution/{id}</h3>
          </article>
        </div>
      </section>
    </div>
  );
}

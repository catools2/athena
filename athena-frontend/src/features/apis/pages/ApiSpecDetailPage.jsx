import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { apiRoots, requestJson } from "../../../shared/api/gatewayClient";
import { DashboardPageHero } from "../../../shared/ui/DashboardPageHero";
import { formatDateTime, formatNumber, getErrorMessage } from "../../../shared/ui/workspacePageUtils";

const WARNING_SYNC_WINDOW_DAYS = 7;
const STALE_SYNC_WINDOW_DAYS = 30;

function getFreshnessState(lastSyncTime) {
  if (!lastSyncTime) {
    return {
      badgeClassName: "status-chip status-chip--unknown",
      label: "Unknown sync",
      syncAgeDays: null,
    };
  }

  const syncAgeDays = Math.max(0, Math.floor((Date.now() - new Date(lastSyncTime).getTime()) / (1000 * 60 * 60 * 24)));

  if (syncAgeDays > STALE_SYNC_WINDOW_DAYS) {
    return {
      badgeClassName: "status-chip status-chip--stale",
      label: "Stale",
      syncAgeDays,
    };
  }

  if (syncAgeDays > WARNING_SYNC_WINDOW_DAYS) {
    return {
      badgeClassName: "status-chip status-chip--aging",
      label: "Aging",
      syncAgeDays,
    };
  }

  return {
    badgeClassName: "status-chip status-chip--fresh",
    label: "Fresh",
    syncAgeDays,
  };
}

function toSortedArray(values) {
  if (!Array.isArray(values)) {
    return [];
  }

  return [...values].sort((left, right) => {
    const leftKey = `${left.method ?? ""}:${left.url ?? ""}`;
    const rightKey = `${right.method ?? ""}:${right.url ?? ""}`;
    return leftKey.localeCompare(rightKey);
  });
}

export function ApiSpecDetailPage() {
  const { id } = useParams();
  const [detailData, setDetailData] = useState(null);
  const [status, setStatus] = useState("loading");
  const [errorMessage, setErrorMessage] = useState("");
  const [reloadToken, setReloadToken] = useState(0);

  useEffect(() => {
    let isCancelled = false;

    setStatus("loading");
    setErrorMessage("");

    requestJson(apiRoots.spec, `/${id}`)
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
          backLink="/apis/specs"
          backLabel="Back to API specs"
          eyebrow="Spec detail"
          title="Loading specification detail"
          summary="Pulling the full API specification payload through the gateway."
        />
      </div>
    );
  }

  if (status === "error") {
    return (
      <div className="page-grid">
        <DashboardPageHero
          backLink="/apis/specs"
          backLabel="Back to API specs"
          eyebrow="Spec detail"
          title="Specification detail is unavailable"
          summary="The gateway could not return the full API specification payload."
          callouts={[`Gateway root: ${apiRoots.spec}`, "Route: /spec/{id}"]}
        />

        <section className="panel">
          <div className="error-card">
            <p className="eyebrow">Gateway request failed</p>
            <h3>Spec detail could not be loaded.</h3>
            <p className="muted">{errorMessage}</p>
            <div className="button-row">
              <button type="button" className="button button--primary" onClick={() => setReloadToken((current) => current + 1)}>
                Retry
              </button>
              <Link className="button button--ghost" to="/apis/specs">
                Back to workspace
              </Link>
            </div>
          </div>
        </section>
      </div>
    );
  }

  const paths = toSortedArray(detailData?.paths);
  const metadata = Array.isArray(detailData?.metadata) ? detailData.metadata : [];
  const freshnessState = getFreshnessState(detailData?.lastSyncTime);

  return (
    <div className="page-grid">
      <DashboardPageHero
        backLink="/apis/specs"
        backLabel="Back to API specs"
        eyebrow="Spec detail"
        title={detailData?.title ?? detailData?.name ?? `Spec ${id}`}
        summary={`${detailData?.name} in ${detailData?.project} currently tracks ${formatNumber(paths.length, 0, "0")} paths for version ${detailData?.version}.`}
        callouts={[`Gateway root: ${apiRoots.spec}`, "Endpoint: /spec/{id}", freshnessState.label]}
        metrics={[
          {
            key: "tracked-paths",
            label: "Tracked paths",
            value: formatNumber(paths.length, 0, "0"),
            detail: `${formatNumber(metadata.length, 0, "0")} metadata tags attached to the spec payload.`,
            toneClass: "metric-card--blue",
          },
          {
            key: "sync-age",
            label: "Sync age",
            value: freshnessState.syncAgeDays == null ? "—" : `${formatNumber(freshnessState.syncAgeDays, 0, "0")}d`,
            detail: freshnessState.label,
            toneClass: "metric-card--violet",
          },
          {
            key: "latest-sync",
            label: "Latest sync",
            value: formatDateTime(detailData?.lastSyncTime),
            detail: `First seen ${formatDateTime(detailData?.firstTimeSeen)}.`,
            toneClass: "metric-card--amber",
          },
          {
            key: "project-version",
            label: "Project version",
            value: detailData?.version ?? "—",
            detail: detailData?.project ?? "Project context unavailable.",
            toneClass: "metric-card--slate",
          },
        ]}
      />

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Specification summary</p>
            <h2>Identity and sync posture</h2>
          </div>
        </div>

        <div className="summary-grid">
          <article className="summary-stat">
            <p className="eyebrow">Project</p>
            <h3 className="summary-value">{detailData?.project}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Version</p>
            <h3 className="summary-value">{detailData?.version}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Tracked paths</p>
            <h3 className="summary-value">{formatNumber(paths.length, 0, "0")}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">First seen</p>
            <h3 className="summary-value summary-value--compact">{formatDateTime(detailData?.firstTimeSeen)}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Last sync</p>
            <h3 className="summary-value summary-value--compact">{formatDateTime(detailData?.lastSyncTime)}</h3>
          </article>
          <article className="summary-stat">
            <p className="eyebrow">Sync age</p>
            <h3 className="summary-value">{freshnessState.syncAgeDays == null ? "—" : `${formatNumber(freshnessState.syncAgeDays, 0, "0")}d`}</h3>
          </article>
        </div>
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Metadata</p>
            <h2>Specification tags</h2>
          </div>
          <p className="page-summary">Metadata stays attached to the full specification payload instead of the lightweight inventory row.</p>
        </div>

        {metadata.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No metadata</p>
            <h3>This specification does not expose additional metadata tags.</h3>
            <p className="muted">When sync pipelines begin emitting richer context, those values will surface here.</p>
          </div>
        ) : (
          <div className="token-grid">
            {metadata.map((entry) => (
              <span key={`${entry.name}-${entry.value}-${entry.id ?? "meta"}`} className="token-chip">
                <strong>{entry.name}</strong>
                {entry.value}
              </span>
            ))}
          </div>
        )}
      </section>

      <section className="panel">
        <div className="section-header">
          <div>
            <p className="eyebrow">Tracked paths</p>
            <h2>Spec paths</h2>
          </div>
          <p className="page-summary">Detailed path payloads stay local to this page so the inventory workspace can stay fast.</p>
        </div>

        {paths.length === 0 ? (
          <div className="empty-state">
            <p className="eyebrow">No paths</p>
            <h3>The specification payload did not include tracked paths.</h3>
            <p className="muted">That usually indicates an incomplete sync or a malformed contract source.</p>
          </div>
        ) : (
          <div className="table-shell">
            <table className="data-table">
              <thead>
                <tr>
                  <th scope="col">Method</th>
                  <th scope="col">URL</th>
                  <th scope="col">Title</th>
                  <th scope="col">Last sync</th>
                </tr>
              </thead>
              <tbody>
                {paths.map((path) => (
                  <tr key={`${path.id ?? path.url}-${path.method}`}>
                    <td>
                      <span className="path-method">{path.method}</span>
                    </td>
                    <td>{path.url}</td>
                    <td>{path.title || "—"}</td>
                    <td>{formatDateTime(path.lastSyncTime)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>
    </div>
  );
}

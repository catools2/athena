import { AlertTriangle, Clock, Info } from "lucide-react";
import type { PanelSpec, QueryResult } from "../../../shared/analytics/types";

interface PanelFrameProps {
  panel: PanelSpec;
  result?: QueryResult | null;
  error?: string | null;
  loading?: boolean;
  children?: React.ReactNode;
}

/**
 * The chrome every panel shares: title, staleness, and the honest empty/error states.
 *
 * Freshness is shown rather than assumed. Most of these views are materialized snapshots, so a
 * panel that renders without saying when its data was last refreshed is quietly presenting old
 * numbers as current - the exact failure this surfaces.
 */
export function PanelFrame({ panel, result, error, loading, children }: PanelFrameProps) {
  const stale = staleness(result);

  return (
    <section className="card flex h-full flex-col p-4">
      <header className="mb-3 flex items-start gap-2">
        <div className="min-w-0 flex-1">
          <h3 className="card-title truncate" title={panel.title}>
            {panel.title || "Untitled panel"}
          </h3>
          {panel.description ? (
            <p className="mt-0.5 line-clamp-2 text-[11px] text-ink-muted">{panel.description}</p>
          ) : null}
        </div>
        {stale ? (
          <span
            className="flex shrink-0 items-center gap-1 rounded-full border border-line px-2 py-0.5 text-[10px] text-ink-muted"
            title={stale.detail}
          >
            <Clock className="h-3 w-3" aria-hidden="true" />
            {stale.label}
          </span>
        ) : null}
      </header>

      <div className="min-h-0 flex-1">
        {loading ? (
          <Placeholder icon={<Info className="h-4 w-4" />} title="Loading" />
        ) : error ? (
          <Placeholder
            icon={<AlertTriangle className="h-4 w-4 text-state-danger" />}
            title="Could not load"
            detail={error}
          />
        ) : result && result.rows.length === 0 ? (
          <Placeholder
            icon={<Info className="h-4 w-4" />}
            title="No data for these filters"
            detail="The query ran successfully and matched no rows."
          />
        ) : (
          children
        )}
      </div>

      {result?.truncated ? (
        <p className="mt-2 flex items-center gap-1 text-[10px] text-state-warning">
          <AlertTriangle className="h-3 w-3" aria-hidden="true" />
          Row cap reached — this panel shows a partial result.
        </p>
      ) : null}
    </section>
  );
}

function Placeholder({ icon, title, detail }: { icon: React.ReactNode; title: string; detail?: string }) {
  return (
    <div className="flex h-full min-h-[120px] flex-col items-center justify-center gap-1 text-center">
      <span className="text-ink-muted" aria-hidden="true">{icon}</span>
      <p className="text-xs font-medium text-ink-muted">{title}</p>
      {detail ? <p className="max-w-[36ch] text-[11px] text-ink-muted/70">{detail}</p> : null}
    </div>
  );
}

function staleness(result?: QueryResult | null): { label: string; detail: string } | null {
  const materialized = result?.freshness?.filter((f) => f.materialized) ?? [];
  if (materialized.length === 0) return null;

  const unknown = materialized.filter((f) => !f.refreshedAt);
  if (unknown.length > 0) {
    return {
      label: "freshness unknown",
      detail:
        `No recorded refresh for ${unknown.map((f) => f.view).join(", ")}. ` +
        "These are snapshots, so the data may be old.",
    };
  }

  const oldest = materialized
    .map((f) => new Date(f.refreshedAt as string).getTime())
    .reduce((a, b) => Math.min(a, b));
  const ageMinutes = Math.round((Date.now() - oldest) / 60000);
  return {
    label: ageMinutes < 60 ? `${ageMinutes}m old` : `${Math.round(ageMinutes / 60)}h old`,
    detail: `Snapshot last refreshed ${new Date(oldest).toLocaleString()}.`,
  };
}

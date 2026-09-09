import { useEffect, useMemo, useState } from "react";
import { Ban } from "lucide-react";
import { runQuery } from "../../../shared/analytics/analyticsClient";
import type { DashboardSpec, PanelSpec, QueryResult } from "../../../shared/analytics/types";
import { PanelChart, PanelTable } from "./PanelChart";
import { PanelFrame } from "./PanelFrame";

/** Grafana lays panels out on a 24-column grid; keep the same arithmetic so specs port as-is. */
const GRID_COLUMNS = 24;
const ROW_HEIGHT_PX = 34;

interface DashboardRendererProps {
  spec: DashboardSpec;
  params: Record<string, unknown>;
}

export function DashboardRenderer({ spec, params }: DashboardRendererProps) {
  const ordered = useMemo(
    () => [...spec.panels].sort((a, b) => a.grid.y - b.grid.y || a.grid.x - b.grid.x),
    [spec.panels],
  );

  return (
    <div
      className="grid gap-3"
      style={{
        gridTemplateColumns: `repeat(${GRID_COLUMNS}, minmax(0, 1fr))`,
        gridAutoRows: `${ROW_HEIGHT_PX}px`,
      }}
    >
      {ordered.map((panel) => (
        <div
          key={`${panel.id}-${panel.grid.x}-${panel.grid.y}`}
          style={{
            gridColumn: `span ${Math.min(panel.grid.w, GRID_COLUMNS)}`,
            gridRow: `span ${Math.max(panel.grid.h, 2)}`,
          }}
          className="min-w-0"
        >
          <Panel panel={panel} params={params} />
        </div>
      ))}
    </div>
  );
}

function Panel({ panel, params }: { panel: PanelSpec; params: Record<string, unknown> }) {
  if (panel.viz === "row") {
    return (
      <div className="flex h-full items-end">
        <h2 className="font-display text-xs font-semibold uppercase tracking-[0.14em] text-ink-muted">
          {panel.title}
        </h2>
      </div>
    );
  }

  if (panel.viz === "text") {
    return <TextPanel panel={panel} />;
  }

  if (panel.unsupported) {
    // Shown, not hidden. A panel that silently vanishes is far harder to notice than one
    // that says why it is empty.
    return (
      <section className="card flex h-full flex-col items-center justify-center gap-1 p-4 text-center">
        <Ban className="h-4 w-4 text-ink-muted" aria-hidden="true" />
        <h3 className="card-title">{panel.title}</h3>
        <p className="max-w-[40ch] text-[11px] text-ink-muted">{panel.unsupported}</p>
      </section>
    );
  }

  return <QueryPanel panel={panel} params={params} />;
}

function QueryPanel({ panel, params }: { panel: PanelSpec; params: Record<string, unknown> }) {
  const [result, setResult] = useState<QueryResult | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  const query = panel.queries?.[0];
  // Send only what this query declares. Passing the whole filter bar would make unrelated
  // panels refetch whenever any filter moved.
  const scoped = useMemo(() => {
    if (!query) return {};
    return Object.fromEntries(
      query.params.filter((name) => params[name] !== undefined).map((name) => [name, params[name]]),
    );
  }, [query, params]);

  useEffect(() => {
    if (!query) return;
    let cancelled = false;
    setLoading(true);
    runQuery(query.queryId, scoped)
      .then((data) => {
        if (!cancelled) { setResult(data); setError(null); }
      })
      .catch((e: Error) => {
        if (!cancelled) { setError(e.message); setResult(null); }
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });
    return () => { cancelled = true; };
  }, [query, scoped]);

  return (
    <PanelFrame panel={panel} result={result} error={error} loading={loading}>
      {result ? (
        panel.viz === "table" ? <PanelTable result={result} /> : <PanelChart panel={panel} result={result} />
      ) : null}
    </PanelFrame>
  );
}

/**
 * Text panels carry the dashboards' own explanations of what a chart means. They are authored
 * HTML from the Grafana specs, so they are rendered as plain text rather than injected as
 * markup - the content is trusted-ish but there is no reason to hand it an HTML parser.
 */
function TextPanel({ panel }: { panel: PanelSpec }) {
  const text = useMemo(() => stripMarkup(panel.content ?? ""), [panel.content]);
  return (
    <section className="card h-full overflow-auto p-4">
      {panel.title ? <h3 className="card-title mb-2">{panel.title}</h3> : null}
      <p className="whitespace-pre-line text-[11px] leading-relaxed text-ink-muted">{text}</p>
    </section>
  );
}

function stripMarkup(html: string): string {
  return html
    .replace(/<\/(p|div|li|ul|h\d)>/gi, "\n")
    .replace(/<li[^>]*>/gi, "• ")
    .replace(/<[^>]+>/g, "")
    .replace(/&nbsp;/g, " ")
    .replace(/&amp;/g, "&")
    .replace(/\n{3,}/g, "\n\n")
    .trim();
}

import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { BarChart3, LayoutDashboard } from "lucide-react";
import { listDashboards } from "../../../shared/analytics/analyticsClient";
import type { DashboardSummary } from "../../../shared/analytics/types";

export function DashboardListPage() {
  const [dashboards, setDashboards] = useState<DashboardSummary[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    listDashboards().then(setDashboards).catch((e: Error) => setError(e.message));
  }, []);

  const byVariant = dashboards.reduce<Record<string, DashboardSummary[]>>((acc, d) => {
    (acc[d.variant] ??= []).push(d);
    return acc;
  }, {});

  return (
    <div className="animate-fade-in p-6">
      <header className="mb-6">
        <p className="eyebrow-label">Analytics</p>
        <h1 className="font-display text-2xl font-semibold text-ink">Dashboards</h1>
        <p className="mt-1 max-w-[70ch] text-sm text-ink-muted">
          Generated from the reporting views. Each panel runs a named, parameterised query on the
          server — the browser never sends SQL.
        </p>
      </header>

      {error ? (
        <p className="card p-4 text-sm text-state-danger">{error}</p>
      ) : (
        Object.entries(byVariant).map(([variant, items]) => (
          <section key={variant} className="mb-8">
            <h2 className="eyebrow-label mb-3">{variant}</h2>
            <div className="grid gap-3 sm:grid-cols-2 lg:grid-cols-3">
              {items.map((d) => (
                <Link
                  key={d.id}
                  to={`/dashboards/${d.id}`}
                  className="card group p-4 transition hover:border-line-strong"
                >
                  <div className="mb-2 flex items-center gap-2">
                    <LayoutDashboard className="h-4 w-4 text-accent" aria-hidden="true" />
                    <h3 className="card-title truncate">{d.title}</h3>
                  </div>
                  <p className="flex items-center gap-1.5 text-[11px] text-ink-muted">
                    <BarChart3 className="h-3 w-3" aria-hidden="true" />
                    {d.renderable} of {d.panelCount} panels backed by a query
                  </p>
                </Link>
              ))}
            </div>
          </section>
        ))
      )}
    </div>
  );
}

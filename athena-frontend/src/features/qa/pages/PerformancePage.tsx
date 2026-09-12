import { useMemo } from "react";
import { Activity, ChevronRight, Gauge, Layers, ListOrdered, TrendingUp } from "lucide-react";
import { toRecords } from "../../../shared/analytics/analyticsClient";
import {
  FilterBar, RangeFilter, SelectFilter, TextFilter,
  useDimensions, useFilters, useTimeWindow,
} from "../../../shared/analytics/filters";
import { QueryBoundary } from "../../../shared/analytics/QueryBoundary";
import { useQuery } from "../../../shared/analytics/useQuery";
import { PERF_DEFAULTS, notFilters } from "../../../shared/ui/navigation";

const NOT_FILTERS = notFilters("/performance");
import { DataTable } from "../components/DataTable";
import { DurationHistogram, PercentileTrend, TargetBreakdown } from "../components/PerfCharts";
import { StatTile } from "../components/StatTile";

/**
 * Timing, drilled from the whole window down to the individual measurement.
 *
 *   all actions  ->  one action  ->  one target  ->  the samples behind the number
 *
 * Each level answers the question the level above raises, and the level is held in the URL so a
 * specific finding is a link someone else can open rather than a screenshot with instructions.
 * Percentiles throughout: an average hides the tail, and the tail is the thing users feel.
 */
export function PerformancePage() {
  const { values, set, go, reset, activeCount } = useFilters(PERF_DEFAULTS, NOT_FILTERS);
  const window = useTimeWindow(values);
  const dimensions = useDimensions("filter_perf_dimensions");

  const action = values.action || null;
  const target = values.target || null;
  const level = target ? "target" : action ? "action" : "overview";

  /**
   * The scope every query on the page shares. Threading it through all of them is the point: an
   * environment control that moved only the tiles - which is what this page used to do - reads as
   * a page filter and is not one, so the charts below would answer a different question than the
   * numbers above them.
   */
  const scope = useMemo(() => ({
    ...window,
    environment: values.environment || null,
    project: values.project || null,
  }), [window, values.environment, values.project]);

  const listScope = useMemo(() => ({
    ...scope,
    actionType: values.actionType || null,
  }), [scope, values.actionType]);

  function filter(patch: Record<string, string | string[] | null>) {
    // A different scope can mean the drilled-into action no longer exists in it.
    set({ ...patch, action: null, target: null });
  }

  const overview = useQuery("perf_overview", useMemo(() => ({
    ...scope, actionType: values.actionType || null,
  }), [scope, values.actionType]), true);

  // Only the queries the current level needs actually run.
  const actions = useQuery("perf_action_summary", useMemo(() => ({
    ...listScope, action: values.search || null,
  }), [listScope, values.search]), level === "overview");
  const regression = useQuery("perf_regression", useMemo(() => ({
    ...listScope, action: values.search || null,
  }), [listScope, values.search]), level === "overview");
  const dailyTrend = useQuery("perf_trend", useMemo(() => ({
    ...listScope, action: values.search || null,
  }), [listScope, values.search]), level === "overview");

  const targets = useQuery("perf_action_targets", useMemo(() => ({
    ...scope, action,
  }), [scope, action]), level !== "overview");
  const actionTrend = useQuery("perf_action_trend", useMemo(() => ({
    ...scope, action, target,
  }), [scope, action, target]), level !== "overview");
  const histogram = useQuery("perf_action_histogram", useMemo(() => ({
    ...scope, action, target,
  }), [scope, action, target]), level !== "overview");
  const samples = useQuery("perf_samples", useMemo(() => ({
    ...scope, action, target,
  }), [scope, action, target]), level === "target");

  const head = overview.result && overview.result.rows.length > 0
    ? Object.fromEntries(overview.result.columns.map((c, i) => [c.name, overview.result!.rows[0][i]]))
    : null;

  return (
    <div className="animate-fade-in p-6">
      <header className="mb-4">
        <p className="eyebrow-label">Performance</p>
        <h1 className="font-display text-2xl font-semibold text-ink">Action timing</h1>
        <p className="mt-1 max-w-[75ch] text-sm text-ink-muted">
          From the whole window down to the individual measurement. Select an action to see its
          targets, a target to see the samples behind its percentiles.
        </p>
      </header>

      <FilterBar
        activeCount={activeCount}
        onReset={reset}
        note={
          <>
            Every filter applies to every panel, including both halves of the regression
            comparison.
          </>
        }
      >
        <RangeFilter value={values.range} from={values.from} to={values.to}
                     onChange={(patch) => filter(patch)} />
        <SelectFilter label="Environment" value={values.environment}
                      options={dimensions.environment ?? []}
                      onChange={(environment) => filter({ environment })} />
        <SelectFilter label="Project" value={values.project} options={dimensions.project ?? []}
                      onChange={(project) => filter({ project })} />
        <SelectFilter label="Action type" value={values.actionType}
                      options={dimensions.action_type ?? []} allLabel="All types"
                      onChange={(actionType) => filter({ actionType })} />
        <TextFilter label="Action name" value={values.search} placeholder="substring match"
                    onChange={(search) => filter({ search })} />
      </FilterBar>

      <div className="mb-4 grid gap-3 sm:grid-cols-3 xl:grid-cols-6">
        <StatTile label="Samples" value={head?.samples} />
        <StatTile label="Actions" value={head?.actions} />
        <StatTile label="Targets" value={head?.targets} />
        <StatTile label="p50" value={head?.p50_ms} unit="ms" />
        <StatTile label="p95" value={head?.p95_ms} unit="ms" tone="warning" hint="the tail users feel" />
        <StatTile label="Max" value={head?.max_ms} unit="ms" tone="danger" />
      </div>

      {level === "overview" ? (
        <OverviewLevel
          dailyTrend={dailyTrend}
          regression={regression}
          actions={actions}
          onPick={(name) => go({ action: name, target: null })}
        />
      ) : (
        <DetailLevel
          action={action!}
          target={target}
          actionTrend={actionTrend}
          histogram={histogram}
          targets={targets}
          samples={samples}
          onPickTarget={(t) => go({ target: t })}
        />
      )}
    </div>
  );
}

type Q = ReturnType<typeof useQuery>;

function Panel({
  icon, title, subtitle, children, className = "", height = "h-64",
}: {
  icon?: React.ReactNode; title: string; subtitle?: string;
  children: React.ReactNode; className?: string; height?: string;
}) {
  return (
    <section className={`card p-4 ${height} ${className}`}>
      <h2 className="card-title flex items-center gap-2">
        {icon ? <span aria-hidden="true">{icon}</span> : null}
        {title}
      </h2>
      {subtitle ? <p className="mt-0.5 mb-1 text-[11px] text-ink-muted">{subtitle}</p> : null}
      <div className={subtitle ? "h-[calc(100%-3.25rem)]" : "h-[calc(100%-1.75rem)]"}>{children}</div>
    </section>
  );
}

function OverviewLevel({
  dailyTrend, regression, actions, onPick,
}: { dailyTrend: Q; regression: Q; actions: Q; onPick: (action: string) => void }) {
  const regressionData = regression.result ? toRecords(regression.result).slice(0, 10) : [];

  return (
    <>
      <div className="mb-3 grid gap-3 lg:grid-cols-2">
        <Panel icon={<TrendingUp className="h-4 w-4 text-accent" />} title="Percentiles over time"
               subtitle="The shaded band is the gap between p50 and p95 — the tail.">
          <QueryBoundary query={dailyTrend} empty="No measurements in this window.">
            {(rows) => <PercentileTrend result={rows} granularity="day" />}
          </QueryBoundary>
        </Panel>
        <Panel icon={<Gauge className="h-4 w-4 text-accent" />} title="Change against the preceding window"
               subtitle="Positive is slower. Select an action to open it. Fewer than 5 samples either side is excluded.">
          <QueryBoundary query={regression} empty="Not enough history to compare windows.">
            {() => <RegressionBars data={regressionData} onPick={onPick} />}
          </QueryBoundary>
        </Panel>
      </div>

      <section className="card max-h-[28rem] overflow-hidden p-0">
        <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
          <ListOrdered className="h-4 w-4 text-accent" aria-hidden="true" />
          <span className="card-title">Actions</span>
          <span className="ml-auto flex items-center gap-1 text-[10px] text-ink-muted">
            select a row to drill in <ChevronRight className="h-3 w-3" aria-hidden="true" />
          </span>
        </h2>
        <QueryBoundary query={actions} empty="No action matches these filters.">
          {(rows) => <DataTable result={rows} onRowClick={(row) => onPick(String(row.action))} />}
        </QueryBoundary>
      </section>
    </>
  );
}

function RegressionBars({
  data, onPick,
}: { data: Record<string, unknown>[]; onPick: (action: string) => void }) {
  const worst = Math.max(...data.map((d) => Math.abs(Number(d.delta_ms) || 0)), 1);
  return (
    <ul className="h-full list-none space-y-1.5 overflow-auto p-0 pr-1">
      {data.map((row) => {
        const delta = Number(row.delta_ms) || 0;
        const slower = delta > 0;
        // Signed values need a shared zero. Drawing "faster" and "slower" as same-direction
        // bars of different colour makes the reader decode the colour to know the sign;
        // a centre baseline shows it in the geometry.
        const width = (Math.abs(delta) / worst) * 50;
        return (
          <li key={String(row.action)}>
            <button type="button" onClick={() => onPick(String(row.action))}
                    className="flex w-full items-center gap-2 rounded px-1 py-0.5 text-left text-[11px] hover:bg-white/[0.04] focus:bg-white/[0.06] focus:outline-none">
            <span className="w-28 shrink-0 truncate text-ink-muted" title={String(row.action)}>
              {String(row.action)}
            </span>
            <span className="relative h-3 flex-1 rounded-sm bg-white/[0.04]">
              <span className="absolute inset-y-0 left-1/2 w-px bg-white/20" aria-hidden="true" />
              <span
                className={`absolute inset-y-0 rounded-sm ${slower ? "bg-state-danger/70" : "bg-state-success/70"}`}
                style={slower
                  ? { left: "50%", width: `${width}%` }
                  : { right: "50%", width: `${width}%` }}
              />
            </span>
            <span className={`w-20 shrink-0 text-right tabular-nums ${slower ? "text-state-danger" : "text-state-success"}`}>
              {slower ? "+" : ""}{delta.toLocaleString()}ms
            </span>
            </button>
          </li>
        );
      })}
    </ul>
  );
}

function DetailLevel({
  action, target, actionTrend, histogram, targets, samples, onPickTarget,
}: {
  action: string; target: string | null;
  actionTrend: Q; histogram: Q; targets: Q; samples: Q;
  onPickTarget: (target: string) => void;
}) {
  return (
    <>
      <div className="mb-3 grid gap-3 lg:grid-cols-2">
        <Panel icon={<Activity className="h-4 w-4 text-accent" />}
               title={target ? `${action} · ${target}` : action}
               subtitle="Hourly, so a regression shows when it landed rather than which day.">
          <QueryBoundary query={actionTrend} empty="No measurements in this window.">
            {(rows) => <PercentileTrend result={rows} granularity="hour" />}
          </QueryBoundary>
        </Panel>
        <Panel icon={<Layers className="h-4 w-4 text-accent" />} title="Duration distribution"
               subtitle="Shape, not just percentiles: a wide spread and a tight cluster with outliers read the same in p95.">
          <QueryBoundary query={histogram} empty="Not enough samples to bucket.">
            {(rows) => <DurationHistogram result={rows} />}
          </QueryBoundary>
        </Panel>
      </div>

      {!target ? (
        <>
          <Panel icon={<Layers className="h-4 w-4 text-accent" />} title="p95 by target"
                 subtitle="Select a bar or a row to see the measurements behind it." className="mb-3">
            <QueryBoundary query={targets} empty="No target recorded for this action.">
              {(rows) => <TargetBreakdown result={rows} onSelect={onPickTarget} />}
            </QueryBoundary>
          </Panel>
          <section className="card max-h-[24rem] overflow-hidden p-0">
            <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
              <span className="card-title">Targets</span>
              <span className="ml-auto flex items-center gap-1 text-[10px] text-ink-muted">
                select a row for samples <ChevronRight className="h-3 w-3" aria-hidden="true" />
              </span>
            </h2>
            <QueryBoundary query={targets} empty="No target recorded for this action.">
              {(rows) => (
                <DataTable result={rows} onRowClick={(row) => onPickTarget(String(row.target))} />
              )}
            </QueryBoundary>
          </section>
        </>
      ) : (
        <section className="card max-h-[28rem] overflow-hidden p-0">
          <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
            <span className="card-title">Measurements</span>
            <span className="ml-auto text-[10px] text-ink-muted">slowest first, capped at 500</span>
          </h2>
          <QueryBoundary query={samples} empty="No measurement recorded for this target.">
            {(rows) => <DataTable result={rows} />}
          </QueryBoundary>
        </section>
      )}
    </>
  );
}

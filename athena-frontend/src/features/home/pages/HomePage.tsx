import { useMemo } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Activity, ArrowRight, GitCommit, Gauge, ListChecks, MousePointerClick, TestTube2, TrendingUp,
} from "lucide-react";
import {
  Area, Bar, BarChart, CartesianGrid, ComposedChart, Legend, Line,
  ResponsiveContainer, Tooltip, XAxis, YAxis,
} from "recharts";
import { toRecords } from "../../../shared/analytics/analyticsClient";
import { ChartTooltip } from "../../../shared/analytics/ChartTooltip";
import {
  FilterBar, MultiSelectFilter, RangeFilter, SelectFilter, dayWindow,
  useDimensions, useFilters, useTimeWindow,
} from "../../../shared/analytics/filters";
import { CHART_INK, SERIES_COLORS, STATUS_COLORS } from "../../../shared/analytics/palette";
import { QueryBoundary } from "../../../shared/analytics/QueryBoundary";
import { useQuery } from "../../../shared/analytics/useQuery";
import { DataTable } from "../../qa/components/DataTable";
import { StatTile } from "../../qa/components/StatTile";

/** Module-level, so the reference is stable - see the note on useFilters. */
const DEFAULTS = { range: "30d", from: "", to: "", version: "", project: "", team: [] as string[] };

const RANGE_PRESETS = [
  { label: "24h", hours: 24 },
  { label: "7d", hours: 24 * 7 },
  { label: "30d", hours: 24 * 30 },
  { label: "90d", hours: 24 * 90 },
];

const axisProps = {
  stroke: CHART_INK.axis,
  tick: { fill: CHART_INK.label, fontSize: 11 },
  tickLine: false,
  axisLine: { stroke: CHART_INK.grid },
};

/**
 * The landing view: a small number of charts that say whether anything needs attention, each
 * one a door into the workspace that can answer why.
 *
 * Every mark here is a link. Clicking a bar does not filter this page - it opens the page that
 * can explain that bar, already narrowed to the day, the status and the filters in force. That
 * is the difference between a dashboard you read and one you work from.
 *
 * Deliberately not a wall of panels. Everything is chosen to be readable in a few seconds.
 */
export function HomePage() {
  const { values, set, reset, activeCount } = useFilters(DEFAULTS);
  const window = useTimeWindow(values);
  const navigate = useNavigate();
  const dimensions = useDimensions("filter_test_dimensions");

  const scope = useMemo(() => ({
    version: values.version || null,
    project: values.project || null,
    team: values.team,
  }), [values.version, values.project, values.team]);

  const params = useMemo(() => ({ ...window, ...scope }), [window, scope]);

  const kpis = useQuery("overview_kpis", params);
  const outcomes = useQuery("overview_execution_trend", params);
  const activity = useQuery("overview_activity", params);
  const failures = useQuery("overview_top_failures", params);
  const timing = useQuery("perf_trend", useMemo(() => ({
    ...window, action: null, environment: null, project: scope.project, actionType: null,
  }), [window, scope.project]));

  const head = kpis.result && kpis.result.rows.length > 0
    ? Object.fromEntries(kpis.result.columns.map((c, i) => [c.name, kpis.result!.rows[0][i]]))
    : null;

  const passRate = Number(head?.pass_rate ?? 0);

  /**
   * Carry the page's filters into the target so a drill-through never silently widens the
   * question. The window comes from what was clicked; everything else comes from the bar.
   */
  function open(path: string, extra: Record<string, string | string[] | null | undefined> = {}) {
    const search = new URLSearchParams();
    const carried = { version: values.version, project: values.project, team: values.team, ...extra };
    for (const [key, value] of Object.entries(carried)) {
      if (value === null || value === undefined || value === "") continue;
      if (Array.isArray(value)) {
        if (value.length > 0) search.set(key, value.join(","));
      } else {
        search.set(key, value);
      }
    }
    navigate(`${path}?${search.toString()}`);
  }

  /** The window currently on screen, for links that are not driven by a clicked bucket. */
  const currentWindow = { range: values.range, from: values.from, to: values.to };

  /**
   * The exact status string the database stores, looked up rather than hardcoded: the view keeps
   * whatever casing athena_tms.status uses, so a literal "Blocked" would filter to nothing on an
   * instance that spells it "BLOCKED".
   */
  function statusValue(pattern: RegExp): string | null {
    return (dimensions.status ?? []).find((s) => pattern.test(s)) ?? null;
  }

  function openDay(bucket: unknown, path: string, extra: Record<string, string | null> = {}) {
    const day = dayWindow(bucket);
    if (!day) return;
    open(path, { ...day, ...extra });
  }

  return (
    <div className="animate-fade-in p-6">
      <header className="mb-4">
        <p className="eyebrow-label">Overview</p>
        <h1 className="font-display text-2xl font-semibold text-ink">Quality at a glance</h1>
        <p className="mt-1 max-w-[70ch] text-sm text-ink-muted">
          What moved in this window, and where to look next. Select any bar, point or row to open
          the workspace that explains it.
        </p>
      </header>

      <FilterBar
        activeCount={activeCount}
        onReset={reset}
        note={
          <>
            Version and team reach tests only; project also reaches timing and pipeline runs;
            commits carry none of the three.
          </>
        }
      >
        <RangeFilter
          value={values.range} from={values.from} to={values.to}
          presets={RANGE_PRESETS}
          onChange={(patch) => set(patch)}
        />
        <SelectFilter label="Version" value={values.version} options={dimensions.version ?? []}
                      onChange={(version) => set({ version })} />
        <SelectFilter label="Project" value={values.project} options={dimensions.project ?? []}
                      onChange={(project) => set({ project })} />
        <MultiSelectFilter label="Team" values={values.team} options={dimensions.team ?? []}
                           onChange={(team) => set({ team })} />
      </FilterBar>

      <div className="mb-3 grid gap-3 sm:grid-cols-3 xl:grid-cols-6">
        <StatTile label="Pass rate" value={head?.pass_rate} unit="%"
                  tone={passRate > 0 && passRate < 80 ? "danger" : "neutral"}
                  onClick={() => open("/test-cycles", currentWindow)} />
        <StatTile label="Executed" value={head?.executed}
                  onClick={() => open("/test-cycles", currentWindow)} />
        <StatTile label="Failures" value={head?.failed}
                  tone={Number(head?.failed ?? 0) > 0 ? "danger" : "neutral"}
                  onClick={() => open("/test-cycles", { ...currentWindow, status: statusValue(/^fail/i) })} />
        <StatTile label="Cycles" value={head?.cycles}
                  onClick={() => open("/test-cycles", currentWindow)} />
        <StatTile label="p95" value={head?.p95_ms} unit="ms" tone="warning"
                  onClick={() => open("/performance", currentWindow)} />
        <StatTile label="Commits" value={head?.commits}
                  onClick={() => open("/correlation", currentWindow)} />
      </div>

      <div className="mb-3 grid gap-3 lg:grid-cols-2">
        <ChartCard
          icon={<ListChecks className="h-4 w-4 text-accent" />}
          title="Test outcomes"
          subtitle="Stacked daily. A growing red band is the thing to chase."
          hint="a segment opens that day's cycles, filtered to that status"
          to="/test-cycles"
          linkLabel="Open test cycles"
        >
          <QueryBoundary query={outcomes}>{(outcomeRows) => (
            <ResponsiveContainer width="100%" height="100%">
              <BarChart
                data={toRecords(outcomeRows)}
                margin={{ top: 8, right: 12, bottom: 4, left: 4 }}
                style={{ cursor: "pointer" }}
                // Clicking the plot rather than a segment still means "that day", just without a
                // status. A zero-height segment cannot be hit, so this is the only way some days
                // are reachable at all.
                onClick={(state) => openDay(state?.activeLabel, "/test-cycles")}
              >
                <CartesianGrid stroke={CHART_INK.grid} vertical={false} />
                <XAxis dataKey="bucket" {...axisProps} tickFormatter={shortDate} minTickGap={24} />
                <YAxis {...axisProps} width={40} allowDecimals={false} />
                <Tooltip content={<ChartTooltip />} cursor={{ fill: "rgba(255,255,255,0.04)" }}
                         labelFormatter={(v) => new Date(String(v)).toLocaleDateString()} />
                <Legend wrapperStyle={{ fontSize: 11, color: CHART_INK.label }} iconType="square" iconSize={8} />
                {/* Status colours, not series colours: pass/fail/blocked are states, and the
                    legend labels carry the meaning so colour is never the only signal. */}
                <Bar stackId="o" dataKey="passed" name="Passed" fill={STATUS_COLORS.good}
                     stroke={CHART_INK.surface} strokeWidth={2} isAnimationActive={false}
                     cursor="pointer"
                     onClick={(bar, _i, event) => segment(event, bar, /^pass/i)} />
                <Bar stackId="o" dataKey="blocked" name="Blocked" fill={STATUS_COLORS.warning}
                     stroke={CHART_INK.surface} strokeWidth={2} isAnimationActive={false}
                     cursor="pointer"
                     onClick={(bar, _i, event) => segment(event, bar, /block/i)} />
                <Bar stackId="o" dataKey="failed" name="Failed" fill={STATUS_COLORS.critical}
                     radius={[4, 4, 0, 0]} stroke={CHART_INK.surface} strokeWidth={2}
                     isAnimationActive={false} cursor="pointer"
                     onClick={(bar, _i, event) => segment(event, bar, /^fail/i)} />
              </BarChart>
            </ResponsiveContainer>
          )}</QueryBoundary>
        </ChartCard>

        <ChartCard
          icon={<Gauge className="h-4 w-4 text-accent" />}
          title="Response time"
          subtitle="The band between p50 and p95 is the tail users feel."
          hint="a point opens that day's action timings"
          to="/performance"
          linkLabel="Open performance"
        >
          <QueryBoundary query={timing} empty="No measurements in this window.">{(timingRows) => (
            <ResponsiveContainer width="100%" height="100%">
              <ComposedChart data={toRecords(timingRows)} margin={{ top: 8, right: 12, bottom: 4, left: 4 }}
                             style={{ cursor: "pointer" }}
                             onClick={(state) => openDay(state?.activeLabel, "/performance")}>
                <defs>
                  <linearGradient id="homeP95" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="0%" stopColor={SERIES_COLORS[0]} stopOpacity={0.28} />
                    <stop offset="100%" stopColor={SERIES_COLORS[0]} stopOpacity={0.02} />
                  </linearGradient>
                </defs>
                <CartesianGrid stroke={CHART_INK.grid} vertical={false} />
                <XAxis dataKey="bucket" {...axisProps} tickFormatter={shortDate} minTickGap={24} />
                <YAxis {...axisProps} width={50} unit="ms" />
                <Tooltip content={<ChartTooltip />} cursor={{ stroke: CHART_INK.axis, strokeWidth: 1 }} />
                <Legend wrapperStyle={{ fontSize: 11, color: CHART_INK.label }} iconType="square" iconSize={8} />
                <Area type="monotone" dataKey="p95_ms" name="p95" stroke={SERIES_COLORS[0]}
                      strokeWidth={2} fill="url(#homeP95)" isAnimationActive={false} />
                <Line type="monotone" dataKey="p50_ms" name="p50" stroke={SERIES_COLORS[1]}
                      strokeWidth={2} dot={false} isAnimationActive={false} />
              </ComposedChart>
            </ResponsiveContainer>
          )}</QueryBoundary>
        </ChartCard>
      </div>

      <div className="grid gap-3 lg:grid-cols-2">
        <ChartCard
          icon={<Activity className="h-4 w-4 text-accent" />}
          title="Activity"
          subtitle="Commits, pipeline runs and test executions on one axis."
          hint="a point opens that day across every domain"
          to="/correlation"
          linkLabel="Open change & run"
        >
          <QueryBoundary query={activity}>{(activityRows) => (
            <ResponsiveContainer width="100%" height="100%">
              <ComposedChart data={toRecords(activityRows)} margin={{ top: 8, right: 12, bottom: 4, left: 4 }}
                             style={{ cursor: "pointer" }}
                             onClick={(state) => openDay(state?.activeLabel, "/correlation")}>
                <CartesianGrid stroke={CHART_INK.grid} vertical={false} />
                <XAxis dataKey="bucket" {...axisProps} tickFormatter={shortDate} minTickGap={24} />
                <YAxis {...axisProps} width={40} allowDecimals={false} />
                <Tooltip content={<ChartTooltip />} cursor={{ stroke: CHART_INK.axis, strokeWidth: 1 }} />
                <Legend wrapperStyle={{ fontSize: 11, color: CHART_INK.label }} iconType="square" iconSize={8} />
                <Line type="monotone" dataKey="test_executions" name="Test executions"
                      stroke={SERIES_COLORS[0]} strokeWidth={2} dot={false} isAnimationActive={false} />
                <Line type="monotone" dataKey="commits" name="Commits"
                      stroke={SERIES_COLORS[1]} strokeWidth={2} dot={false} isAnimationActive={false} />
                <Line type="monotone" dataKey="pipeline_runs" name="Pipeline runs"
                      stroke={SERIES_COLORS[2]} strokeWidth={2} dot={false} isAnimationActive={false} />
              </ComposedChart>
            </ResponsiveContainer>
          )}</QueryBoundary>
        </ChartCard>

        <section className="card flex h-72 flex-col overflow-hidden p-0">
          <h2 className="flex items-center gap-2 border-b border-line px-4 py-3">
            <TestTube2 className="h-4 w-4 text-accent" aria-hidden="true" />
            <span className="card-title">Most-failing tests</span>
            <Link to={`/test-cycles?${search(currentWindow, values)}`}
                  className="ml-auto flex items-center gap-1 text-[11px] text-ink-muted hover:text-ink">
              Open test cycles <ArrowRight className="h-3 w-3" aria-hidden="true" />
            </Link>
          </h2>
          <div className="min-h-0 flex-1 overflow-auto">
            <QueryBoundary query={failures} empty="No test failed in this window.">{(rows) => (
              <DataTable
                result={rows}
                onRowClick={(row) => open("/test-cycles", {
                  ...currentWindow, item: String(row.item_key ?? ""),
                })}
              />
            )}</QueryBoundary>
          </div>
        </section>
      </div>

      <nav className="mt-4 grid gap-3 sm:grid-cols-3" aria-label="Workspaces">
        <Shortcut to={`/test-cycles?${search(currentWindow, values)}`} icon={<ListChecks className="h-4 w-4" />}
                  title="Test cycles"
                  body="Cycle rollups, what ran in one, and whether a test has failed before." />
        <Shortcut to={`/performance?${search(currentWindow, values)}`} icon={<TrendingUp className="h-4 w-4" />}
                  title="Performance"
                  body="Percentiles from the whole window down to the individual measurement." />
        <Shortcut to={`/correlation?${search(currentWindow, values)}`} icon={<GitCommit className="h-4 w-4" />}
                  title="Change & run"
                  body="One window across commits, pipelines, tests, pods and timings." />
      </nav>
    </div>
  );

  /** A clicked stack segment: that day, narrowed to that status. */
  function segment(event: unknown, bar: unknown, pattern: RegExp) {
    // Without this the chart-level handler fires too and overwrites the status with nothing.
    (event as { stopPropagation?: () => void } | undefined)?.stopPropagation?.();
    const row = (bar as { payload?: Record<string, unknown> })?.payload
      ?? (bar as Record<string, unknown>);
    openDay(row?.bucket, "/test-cycles", { status: statusValue(pattern) });
  }
}

/** Build the query string for a link that keeps the current window and scope. */
function search(
  window: { range: string; from: string; to: string },
  values: { version: string; project: string; team: string[] },
): string {
  const params = new URLSearchParams();
  for (const [key, value] of Object.entries({ ...window, ...values })) {
    if (!value || (Array.isArray(value) && value.length === 0)) continue;
    params.set(key, Array.isArray(value) ? value.join(",") : value);
  }
  return params.toString();
}

function ChartCard({
  icon, title, subtitle, hint, to, linkLabel, children,
}: {
  icon: React.ReactNode; title: string; subtitle: string; hint?: string;
  to: string; linkLabel: string; children: React.ReactNode;
}) {
  return (
    <section className="card flex h-72 flex-col p-4">
      <h2 className="flex items-center gap-2">
        <span aria-hidden="true">{icon}</span>
        <span className="card-title">{title}</span>
        {/* The header link is not decoration: a chart click is mouse-only, so this is the
            keyboard and screen-reader route to the same place. */}
        <Link to={to} className="ml-auto flex items-center gap-1 text-[11px] text-ink-muted hover:text-ink">
          {linkLabel} <ArrowRight className="h-3 w-3" aria-hidden="true" />
        </Link>
      </h2>
      <p className="mt-0.5 text-[11px] text-ink-muted">{subtitle}</p>
      {hint ? (
        <p className="mb-1 mt-0.5 flex items-center gap-1 text-[10px] text-ink-muted/70">
          <MousePointerClick className="h-3 w-3 shrink-0" aria-hidden="true" />
          {hint}
        </p>
      ) : null}
      <div className="min-h-0 flex-1">{children}</div>
    </section>
  );
}

function Shortcut({ to, icon, title, body }: { to: string; icon: React.ReactNode; title: string; body: string }) {
  return (
    <Link to={to} className="card group p-4 transition hover:border-line-strong">
      <p className="mb-1 flex items-center gap-2">
        <span className="text-accent" aria-hidden="true">{icon}</span>
        <span className="card-title">{title}</span>
        <ArrowRight className="ml-auto h-3 w-3 text-ink-muted transition group-hover:translate-x-0.5" aria-hidden="true" />
      </p>
      <p className="text-[11px] leading-relaxed text-ink-muted">{body}</p>
    </Link>
  );
}

function shortDate(value: unknown): string {
  const date = new Date(String(value));
  return Number.isNaN(date.getTime()) ? String(value) : `${date.getMonth() + 1}/${date.getDate()}`;
}

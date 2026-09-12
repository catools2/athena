import { useMemo } from "react";
import { BarChart3, CalendarDays, ChevronRight, ListChecks, Target, TrendingUp, XCircle } from "lucide-react";
import { QueryBoundary } from "../../../shared/analytics/QueryBoundary";
import {
  FilterBar, MultiSelectFilter, RangeFilter, SelectFilter, TextFilter,
  useDimensions, useFilters, useTimeWindow,
} from "../../../shared/analytics/filters";
import { useQuery } from "../../../shared/analytics/useQuery";
import { DetailDialog } from "../../../shared/ui/DetailDialog";
import { CYCLES_DEFAULTS, notFilters } from "../../../shared/ui/navigation";
import { toRecords } from "../../../shared/analytics/analyticsClient";
import { ActivityByDay, CycleBurnUp, FailureProfile, OutcomeByCycle } from "../components/CycleCharts";
import { DataTable } from "../components/DataTable";
import { StatTile } from "../components/StatTile";

const NOT_FILTERS = notFilters("/test-cycles");

const PROFILE_DIMENSIONS = [
  { key: "priority", label: "Priority" },
  { key: "team", label: "Team" },
  { key: "executor", label: "Executor" },
  { key: "type", label: "Type" },
];

/**
 * Rebuild a drill from its URL form.
 *
 * The inverse of the openDrill calls below. Keeping both halves adjacent matters: an encoding
 * whose decoder lives somewhere else is how a shareable link quietly stops meaning what it said.
 */
function decodeDrill(
  kind: DrillKind | "",
  value: string,
  cycle: string | null,
  cycleLabel: string | null,
  statusFor: (segment: string) => string | null,
): Drill | null {
  if (!kind) return null;
  const parts = value ? value.split("|") : [];
  const inCycle = cycle ? `In ${cycleLabel ?? cycle}` : undefined;

  if (kind === "failures") {
    return {
      title: cycle ? `Failures in ${cycleLabel ?? cycle}` : "Every failure in this window",
      params: { cycle, status: statusFor("failed") },
    };
  }

  if (kind === "day") {
    const [bucket, segment] = parts;
    const day = dayWindow(bucket);
    if (!day) return null;
    const status = segment ? statusFor(segment) : null;
    return {
      title: status ? `${status} on ${day.label}` : `Executions on ${day.label}`,
      subtitle: inCycle,
      params: { cycle, status, timeFrom: day.timeFrom, timeTo: day.timeTo },
    };
  }

  const [dimension, bucket, segment] = parts;
  if (!dimension || !bucket) return null;
  const label = PROFILE_DIMENSIONS.find((d) => d.key === dimension)?.label ?? dimension;
  return {
    title: `${label}: ${bucket}`,
    subtitle: inCycle,
    params: {
      cycle,
      status: segment ? statusFor(segment) : null,
      ...(dimension === "priority" ? { priority: bucket } : {}),
      ...(dimension === "type" ? { itemType: bucket } : {}),
      ...(dimension === "executor" ? { executor: bucket } : {}),
      ...(dimension === "team" ? { team: [bucket] } : {}),
    },
  };
}

/** A day bucket as an absolute window, matching the database's own date_trunc boundary. */
function dayWindow(bucket: unknown) {
  const start = new Date(String(bucket));
  if (Number.isNaN(start.getTime())) return null;
  return {
    timeFrom: start.toISOString(),
    timeTo: new Date(start.getTime() + 24 * 3_600_000 - 1).toISOString(),
    label: start.toLocaleDateString(),
  };
}

/** Maps a clicked stack segment to the execution_status the drill query should filter on. */
const SEGMENT_STATUS: Record<string, RegExp> = {
  passed: /^pass/i,
  blocked: /block/i,
  failed: /^fail/i,
};

/**
 * What a chart click is asking to see, encoded into two search params.
 *
 * In the URL rather than in component state so Back closes the dialog instead of leaving the
 * page, a drill is a link somebody can send, and a refresh does not lose it. The value is a
 * pipe-joined tuple whose meaning depends on the kind - small enough to stay readable in the
 * address bar, which a JSON blob would not be.
 */
type DrillKind = "day" | "profile" | "failures";

interface Drill {
  title: string;
  subtitle?: string;
  params: Record<string, unknown>;
}

/**
 * The Zephyr-Scale-shaped view: cycles, then the tests inside one, then one test's history -
 * with the analysis that says which of them to open first.
 *
 * Two levels, and they answer different questions. Across cycles: which cycle is red, when did
 * it happen, and what do the red ones have in common. Inside one: is it progressing, and where
 * is its own failure mass. Every mark opens the executions behind it in a dialog, because the
 * next question after "that bar is red" is always "which tests" and the reader should not have
 * to rebuild the filter by hand to ask it.
 */
export function TestCyclesPage() {
  const { values, set, go, reset, activeCount } = useFilters(CYCLES_DEFAULTS, NOT_FILTERS);
  const window = useTimeWindow(values);
  const dimensions = useDimensions("filter_test_dimensions");

  const cycle = values.cycle || null;
  const item = values.item || null;

  /** Open a drill dialog. A push, so Back closes it. */
  const openDrill = (kind: DrillKind, value = "") => go({ drill: kind, drillValue: value });
  const closeDrill = () => set({ drill: null, drillValue: null });

  /** The row filters every query on the page shares. */
  const scope = useMemo(() => ({
    version: values.version || null,
    project: values.project || null,
    team: values.team,
  }), [values.version, values.project, values.team]);

  const cycles = useQuery("cycles_list", useMemo(() => ({
    ...window, ...scope, status: values.status || null, search: values.search || null,
  }), [window, scope, values.status, values.search]));

  const activity = useQuery("cycle_activity", useMemo(() => ({
    ...window, ...scope, cycle,
  }), [window, scope, cycle]));

  const profile = useQuery("cycle_failure_profile", useMemo(() => ({
    ...window, ...scope, cycle,
  }), [window, scope, cycle]));

  const progress = useQuery("cycle_progress", useMemo(() => ({ cycle }), [cycle]), Boolean(cycle));

  const executions = useQuery("cycle_executions", useMemo(() => ({
    cycle, status: values.status || null, search: values.search || null,
  }), [cycle, values.status, values.search]), Boolean(cycle));

  const history = useQuery("test_history", useMemo(() => ({ item }), [item]), Boolean(item));

  const cycleRows = useMemo(
    () => (cycles.result ? toRecords(cycles.result) : []), [cycles.result]);

  const selected = useMemo(
    () => cycleRows.find((row) => String(row.cycle_code) === cycle) ?? null,
    [cycleRows, cycle]);

  /** The exact status string stored, looked up rather than hardcoded - casing varies by instance. */
  const statusFor = (segment: string) =>
    (dimensions.status ?? []).find((s) => SEGMENT_STATUS[segment]?.test(s)) ?? null;

  const drill = useMemo(
    () => decodeDrill(values.drill as DrillKind, values.drillValue, cycle,
                      selected ? String(selected.cycle_short_name ?? cycle) : cycle,
                      statusFor),
    // statusFor closes over dimensions.status, which is what makes the label resolvable.
    [values.drill, values.drillValue, cycle, selected, dimensions.status]);

  // The dialog's query only runs while a dialog is open.
  const drillQuery = useQuery("executions_drill", useMemo(() => ({
    ...window, ...scope,
    cycle: null, status: null, priority: null, itemType: null, executor: null, search: null,
    ...(drill?.params ?? {}),
  }), [window, scope, drill]), Boolean(drill));

  /** Headline numbers, summed from the cycle rollup already on screen rather than re-queried. */
  const totals = useMemo(() => {
    const sum = (key: string) => cycleRows.reduce((n, row) => n + Number(row[key] ?? 0), 0);
    const executed = sum("executed");
    return {
      cycles: cycleRows.length,
      executed,
      failed: sum("failed"),
      notRun: sum("not_run"),
      passRate: executed > 0 ? Math.round((sum("passed") / executed) * 1000) / 10 : null,
    };
  }, [cycleRows]);

  const profileRows = useMemo(() => {
    if (!profile.result) return [];
    return toRecords(profile.result)
      .filter((row) => String(row.dimension) === values.by)
      .slice(0, 12);
  }, [profile.result, values.by]);

  /** Changing a filter invalidates the drill position: the selected cycle may no longer be listed. */
  function filter(patch: Record<string, string | string[] | null>) {
    set({ ...patch, cycle: null, item: null });
  }

  const dimensionLabel = PROFILE_DIMENSIONS.find((d) => d.key === values.by)?.label ?? values.by;

  return (
    <div className="animate-fade-in p-6">
      <header className="mb-4">
        <p className="eyebrow-label">Quality</p>
        <h1 className="font-display text-2xl font-semibold text-ink">Test cycles</h1>
        <p className="mt-1 max-w-[75ch] text-sm text-ink-muted">
          Which cycle is red, when it happened, and what the failures have in common. Select any
          bar or row to see the executions behind it.
        </p>
      </header>

      <FilterBar
        activeCount={activeCount}
        onReset={reset}
        note={
          <>
            Window, version, project and team change the counts. Status and search only change
            which cycles are listed, so a cycle filtered to Fail still reports its real pass rate.
          </>
        }
      >
        <RangeFilter value={values.range} from={values.from} to={values.to}
                     onChange={(patch) => filter(patch)} />
        <SelectFilter label="Version" value={values.version} options={dimensions.version ?? []}
                      onChange={(version) => filter({ version })} />
        <SelectFilter label="Project" value={values.project} options={dimensions.project ?? []}
                      onChange={(project) => filter({ project })} />
        <MultiSelectFilter label="Team" values={values.team} options={dimensions.team ?? []}
                           onChange={(team) => filter({ team })} />
        <SelectFilter label="Status" value={values.status} options={dimensions.status ?? []}
                      allLabel="Any outcome" onChange={(status) => set({ status })} />
        <TextFilter label="Search" value={values.search} placeholder="cycle or test"
                    onChange={(search) => set({ search })} />
      </FilterBar>

      {cycle ? (
        <div className="mb-3 grid gap-3 sm:grid-cols-3 xl:grid-cols-6">
          <StatTile label="In cycle" value={Number(selected?.executed ?? 0) + Number(selected?.not_run ?? 0)} />
          <StatTile label="Executed" value={selected?.executed} />
          <StatTile label="Pass rate" value={selected?.pass_rate} unit="%"
                    tone={Number(selected?.pass_rate ?? 100) < 80 ? "danger" : "neutral"} />
          <StatTile label="Failed" value={selected?.failed}
                    tone={Number(selected?.failed ?? 0) > 0 ? "danger" : "neutral"}
                    onClick={() => openDrill("failures")} />
          <StatTile label="Blocked" value={selected?.blocked}
                    tone={Number(selected?.blocked ?? 0) > 0 ? "warning" : "neutral"} />
          <StatTile label="Not run" value={selected?.not_run} hint="no execution recorded" />
        </div>
      ) : (
        <div className="mb-3 grid gap-3 sm:grid-cols-3 xl:grid-cols-5">
          <StatTile label="Cycles" value={totals.cycles} />
          <StatTile label="Executed" value={totals.executed} />
          <StatTile label="Pass rate" value={totals.passRate} unit="%"
                    tone={totals.passRate !== null && totals.passRate < 80 ? "danger" : "neutral"} />
          <StatTile label="Failed" value={totals.failed}
                    tone={totals.failed > 0 ? "danger" : "neutral"}
                    onClick={() => openDrill("failures")} />
          <StatTile label="Not run" value={totals.notRun} hint="no execution recorded" />
        </div>
      )}

      <div className="mb-3 grid gap-3 lg:grid-cols-2">
        {cycle ? (
          <Panel icon={<TrendingUp className="h-4 w-4 text-accent" />} title="Progress"
                 subtitle="Cumulative executions against the size of the cycle."
                 hint="a point opens that day's executions">
            <QueryBoundary query={progress} empty="Nothing in this cycle has been executed yet.">
              {(rows) => (
                <CycleBurnUp result={rows}
                             onSelect={(bucket) => openDrill("day", String(bucket))} />
              )}
            </QueryBoundary>
          </Panel>
        ) : (
          <Panel icon={<BarChart3 className="h-4 w-4 text-accent" />} title="Outcome by cycle"
                 subtitle="Composition, worst-executed first. The red band is what to open."
                 hint="a segment opens that cycle at that outcome">
            <QueryBoundary query={cycles} empty="No cycle matches these filters.">
              {(rows) => (
                <OutcomeByCycle result={rows} onSelect={(picked, segment) =>
                  go({ cycle: picked, item: null, status: statusFor(segment) ?? "" })} />
              )}
            </QueryBoundary>
          </Panel>
        )}

        <Panel icon={<CalendarDays className="h-4 w-4 text-accent" />} title="Execution activity"
               subtitle={cycle ? "Daily outcomes inside this cycle." : "Daily outcomes across every cycle in scope."}
               hint="a segment opens that day's executions">
          <QueryBoundary query={activity} empty="Nothing was executed in this window.">
            {(rows) => (
              <ActivityByDay
                result={rows}
                onSelect={(bucket, segment) =>
                  openDrill("day", segment ? `${bucket}|${segment}` : String(bucket))} />
            )}
          </QueryBoundary>
        </Panel>
      </div>

      <Panel
        icon={<Target className="h-4 w-4 text-accent" />}
        title="Where failures concentrate"
        subtitle={values.by === "team"
          ? "A test on two teams counts toward both, so these bars sum to more than the execution count."
          : `Outcome split by ${dimensionLabel.toLowerCase()}, worst first.`}
        hint="a segment opens those executions"
        className="mb-3"
        action={
          <div className="flex rounded-md border border-line bg-surface-muted/80 p-0.5">
            {PROFILE_DIMENSIONS.map((d) => (
              <button
                key={d.key} type="button" onClick={() => set({ by: d.key })}
                aria-pressed={values.by === d.key}
                className={`rounded px-2 py-0.5 text-[11px] transition ${
                  values.by === d.key ? "bg-accent/20 text-ink" : "text-ink-muted hover:text-ink"}`}
              >
                {d.label}
              </button>
            ))}
          </div>
        }
      >
        <QueryBoundary query={profile} empty="Nothing was executed in this window.">
          {() => profileRows.length === 0 ? (
            <div className="flex h-full items-center justify-center">
              <p className="text-xs text-ink-muted">No execution carries a {dimensionLabel.toLowerCase()}.</p>
            </div>
          ) : (
            <FailureProfile
              rows={profileRows}
              onSelect={(bucket, segment) =>
                openDrill("profile", `${values.by}|${bucket}|${segment}`)} />
          )}
        </QueryBoundary>
      </Panel>

      <section className="card mb-4 flex max-h-[22rem] flex-col overflow-hidden p-0">
        <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
          <ListChecks className="h-4 w-4 text-accent" aria-hidden="true" />
          <span className="card-title">Cycles</span>
          <span className="ml-auto flex items-center gap-1 text-[10px] text-ink-muted">
            select a row to see what ran <ChevronRight className="h-3 w-3" aria-hidden="true" />
          </span>
        </h2>
        <div className="min-h-0 flex-1 overflow-auto">
          <QueryBoundary query={cycles}
                         empty="No cycle matches these filters. Widen the window or clear the status.">
            {(rows) => (
              <DataTable result={rows}
                         onRowClick={(row) => go({ cycle: String(row.cycle_code ?? ""), item: null })} />
            )}
          </QueryBoundary>
        </div>
      </section>

      {cycle ? (
        <section className="card mb-4 flex max-h-[22rem] flex-col overflow-hidden p-0">
          <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
            <span className="card-title">Executions in {String(selected?.cycle_short_name ?? cycle)}</span>
            <button type="button" onClick={() => set({ cycle: null, item: null })}
                    className="ml-auto text-ink-muted hover:text-ink" aria-label="Close cycle">
              <XCircle className="h-4 w-4" />
            </button>
          </h2>
          <div className="min-h-0 flex-1 overflow-auto">
            <QueryBoundary query={executions}
                           empty="Nothing in this cycle matches the status or search filter.">
              {(rows) => (
                <DataTable result={rows} statusColumn="execution_status"
                           onRowClick={(row) => go({ item: String(row.item_key ?? "") })} />
              )}
            </QueryBoundary>
          </div>
        </section>
      ) : null}

      {item ? (
        <section className="card flex max-h-[20rem] flex-col overflow-hidden p-0">
          <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
            <span className="card-title">History for {item}</span>
            {/* Deliberately outside the window filter: the reason to open a test's history is to
                see whether it has always been flaky, and cropping it to the selected window is
                exactly the answer that question is not asking for. */}
            <span className="text-[10px] text-ink-muted">every recorded run, ignoring the window</span>
            <button type="button" onClick={() => set({ item: null })}
                    className="ml-auto text-ink-muted hover:text-ink" aria-label="Close history">
              <XCircle className="h-4 w-4" />
            </button>
          </h2>
          <div className="min-h-0 flex-1 overflow-auto">
            <QueryBoundary query={history} empty="This test has no recorded executions.">
              {(rows) => <DataTable result={rows} statusColumn="execution_status" />}
            </QueryBoundary>
          </div>
        </section>
      ) : null}

      <DetailDialog
        open={Boolean(drill)}
        title={drill?.title ?? ""}
        subtitle={drill?.subtitle}
        onClose={closeDrill}
      >
        <QueryBoundary query={drillQuery} empty="No execution matches this selection.">
          {(rows) => (
            <DataTable result={rows} statusColumn="execution_status"
                       onRowClick={(row) =>
                         go({ item: String(row.item_key ?? ""), drill: null, drillValue: null })} />
          )}
        </QueryBoundary>
      </DetailDialog>
    </div>
  );
}

function Panel({
  icon, title, subtitle, hint, action, children, className = "",
}: {
  icon: React.ReactNode;
  title: string;
  subtitle?: string;
  hint?: string;
  action?: React.ReactNode;
  children: React.ReactNode;
  className?: string;
}) {
  return (
    <section className={`card flex h-72 flex-col p-4 ${className}`}>
      <h2 className="flex items-center gap-2">
        <span aria-hidden="true">{icon}</span>
        <span className="card-title">{title}</span>
        {action ? <span className="ml-auto">{action}</span> : null}
      </h2>
      {subtitle ? <p className="mt-0.5 text-[11px] text-ink-muted">{subtitle}</p> : null}
      {hint ? <p className="mt-0.5 text-[10px] text-ink-muted/70">{hint}</p> : null}
      <div className="mt-1 min-h-0 flex-1">{children}</div>
    </section>
  );
}

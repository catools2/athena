import { useMemo } from "react";
import { Boxes, GitCommit, PlayCircle, Timer, TestTube2 } from "lucide-react";
import {
  FilterBar, MultiSelectFilter, RangeFilter, SelectFilter, TextFilter,
  useDimensions, useFilters, useTimeWindow,
} from "../../../shared/analytics/filters";
import { useQuery } from "../../../shared/analytics/useQuery";
import { DataTable } from "../components/DataTable";

/** Module-level, so the reference is stable - see the note on useFilters. */
const DEFAULTS = {
  range: "24h", from: "", to: "",
  repository: [] as string[], author: "", pipeline: "", version: "",
  environment: "", namespace: "", app: "", status: "", search: "",
};

const RANGE_PRESETS = [
  { label: "6h", hours: 6 },
  { label: "24h", hours: 24 },
  { label: "7d", hours: 24 * 7 },
  { label: "30d", hours: 24 * 30 },
];

/**
 * One window, five sources: what changed, what ran, what it ran on, and how it performed.
 *
 * This is the view neither Zephyr Scale nor Grafana gives you. Each half of the answer already
 * exists in Athena - commits in athena_git, runs in athena_pipeline, outcomes in the cycle
 * statistics, pods in athena_kube, timings in athena_metric - but only ever separately. Pinning
 * them all to the same interval is the whole point, so the time window is chosen once and every
 * panel below is bound to it.
 *
 * The other filters are per-domain by necessity - a namespace means nothing to a commit - so each
 * one sits above the panel it governs rather than pretending to be global.
 */
export function CorrelationPage() {
  const { values, set, reset, activeCount } = useFilters(DEFAULTS);
  const window = useTimeWindow(values);
  const dimensions = useDimensions("filter_change_dimensions");
  const quality = useDimensions("filter_test_dimensions");

  const commits = useQuery("correlate_commits", useMemo(() => ({
    ...window,
    repository: values.repository,
    author: values.author || null,
    search: values.search || null,
  }), [window, values.repository, values.author, values.search]));

  const runs = useQuery("correlate_pipeline_runs", useMemo(() => ({
    ...window,
    pipeline: values.pipeline || null,
    version: values.version || null,
    environment: values.environment || null,
  }), [window, values.pipeline, values.version, values.environment]));

  const tests = useQuery("correlate_test_executions", useMemo(() => ({
    ...window,
    status: values.status || null,
    version: values.version || null,
    search: values.search || null,
  }), [window, values.status, values.version, values.search]));

  const pods = useQuery("correlate_pods", useMemo(() => ({
    ...window,
    namespace: values.namespace || null,
    app: values.app || null,
    search: values.search || null,
  }), [window, values.namespace, values.app, values.search]));

  const metrics = useQuery("correlate_metrics", useMemo(() => ({
    ...window,
    environment: values.environment || null,
    search: values.search || null,
  }), [window, values.environment, values.search]));

  return (
    <div className="animate-fade-in p-6">
      <header className="mb-4">
        <p className="eyebrow-label">Correlation</p>
        <h1 className="font-display text-2xl font-semibold text-ink">Change and run</h1>
        <p className="mt-1 max-w-[75ch] text-sm text-ink-muted">
          One time window across every domain: the commits that landed, the pipelines that ran, the
          tests that executed, the pods that served it, and what the timings did.
        </p>
      </header>

      <FilterBar
        activeCount={activeCount}
        onReset={reset}
        note={
          <>
            <span className="text-ink-muted/80">
              {new Date(window.timeFrom).toLocaleString()} → {new Date(window.timeTo).toLocaleString()}
            </span>
            {" · "}the window is shared by all five panels; every other filter reaches only the
            panels whose domain has that dimension, named in each header.
          </>
        }
      >
        <RangeFilter value={values.range} from={values.from} to={values.to}
                     label="Window" presets={RANGE_PRESETS} onChange={(patch) => set(patch)} />
        <MultiSelectFilter label="Repository" values={values.repository}
                           options={dimensions.repository ?? []}
                           onChange={(repository) => set({ repository })} />
        <SelectFilter label="Author" value={values.author} options={dimensions.author ?? []}
                      onChange={(author) => set({ author })} />
        <SelectFilter label="Pipeline" value={values.pipeline} options={dimensions.pipeline ?? []}
                      onChange={(pipeline) => set({ pipeline })} />
        <SelectFilter label="Version" value={values.version} options={quality.version ?? []}
                      onChange={(version) => set({ version })} />
        <SelectFilter label="Environment" value={values.environment}
                      options={dimensions.environment ?? []}
                      onChange={(environment) => set({ environment })} />
        <SelectFilter label="Namespace" value={values.namespace} options={dimensions.namespace ?? []}
                      onChange={(namespace) => set({ namespace })} />
        <SelectFilter label="App" value={values.app} options={dimensions.app ?? []}
                      onChange={(app) => set({ app })} />
        <SelectFilter label="Outcome" value={values.status} options={quality.status ?? []}
                      allLabel="Any outcome" onChange={(status) => set({ status })} />
        <TextFilter label="Search" value={values.search} placeholder="message, test, pod, action"
                    onChange={(search) => set({ search })} />
      </FilterBar>

      <div className="grid gap-3 xl:grid-cols-2">
        <Section icon={<GitCommit className="h-4 w-4 text-accent" />} title="Commits" q={commits}
                 scope="repository · author · search" />
        <Section icon={<PlayCircle className="h-4 w-4 text-accent" />} title="Pipeline runs" q={runs}
                 scope="pipeline · version · environment" />
        <Section icon={<TestTube2 className="h-4 w-4 text-accent" />} title="Test executions" q={tests}
                 status="execution_status" scope="outcome · version · search" />
        <Section icon={<Boxes className="h-4 w-4 text-accent" />} title="Pods" q={pods}
                 scope="namespace · app · search" />
        <Section icon={<Timer className="h-4 w-4 text-accent" />} title="Timing" q={metrics}
                 scope="environment · search" className="xl:col-span-2" />
      </div>
    </div>
  );
}

function Section({
  icon, title, q, status, scope, className = "",
}: {
  icon: React.ReactNode;
  title: string;
  q: ReturnType<typeof useQuery>;
  status?: string;
  /** Which filters this panel actually obeys, named so nobody has to guess. */
  scope: string;
  className?: string;
}) {
  const count = q.result?.rows.length ?? 0;
  return (
    <section className={`card max-h-[24rem] overflow-hidden p-0 ${className}`}>
      <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
        <span aria-hidden="true">{icon}</span>
        <span className="card-title">{title}</span>
        <span className="text-[10px] text-ink-muted/70">{scope}</span>
        {q.result ? (
          <span className="ml-auto rounded-full border border-line px-2 py-0.5 text-[10px] tabular-nums text-ink-muted">
            {count}
          </span>
        ) : null}
      </h2>
      {q.error ? (
        <p className="p-3 text-xs text-state-danger">{q.error}</p>
      ) : q.result ? (
        <DataTable result={q.result} statusColumn={status}
                   emptyMessage="Nothing in this window matches the filters this panel obeys." />
      ) : (
        <p className="p-3 text-xs text-ink-muted">Loading…</p>
      )}
    </section>
  );
}

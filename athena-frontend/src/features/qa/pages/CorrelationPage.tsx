import { useMemo, useState } from "react";
import { Boxes, GitCommit, PlayCircle, Timer, TestTube2 } from "lucide-react";
import { DataTable } from "../components/DataTable";
import { useQuery } from "../components/useQuery";

const RANGES = [
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
 */
export function CorrelationPage() {
  const [range, setRange] = useState("24h");
  const [anchor, setAnchor] = useState<string>(() => new Date().toISOString().slice(0, 16));

  const window = useMemo(() => {
    const hours = (RANGES.find((r) => r.label === range) ?? RANGES[1]).hours;
    const end = new Date(anchor).getTime();
    const safeEnd = Number.isNaN(end) ? Date.now() : end;
    return {
      timeFrom: new Date(safeEnd - hours * 3600_000).toISOString(),
      timeTo: new Date(safeEnd).toISOString(),
    };
  }, [range, anchor]);

  const commits = useQuery("correlate_commits", { ...window, repository: [] });
  const runs = useQuery("correlate_pipeline_runs", window);
  const tests = useQuery("correlate_test_executions", window);
  const pods = useQuery("correlate_pods", { ...window, namespace: null });
  const metrics = useQuery("correlate_metrics", window);

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

      <div className="mb-4 flex flex-wrap items-end gap-3">
        <label className="flex flex-col gap-1">
          <span className="eyebrow-label">Ending at</span>
          <input
            type="datetime-local"
            value={anchor}
            onChange={(e) => setAnchor(e.target.value)}
            className="rounded-md border border-line bg-surface-muted/80 px-2 py-1 text-xs text-ink"
          />
        </label>
        <label className="flex flex-col gap-1">
          <span className="eyebrow-label">Looking back</span>
          <div className="flex rounded-md border border-line bg-surface-muted/80 p-0.5">
            {RANGES.map((r) => (
              <button
                key={r.label}
                type="button"
                onClick={() => setRange(r.label)}
                className={`rounded px-2.5 py-1 text-[11px] transition ${
                  range === r.label ? "bg-accent/20 text-ink" : "text-ink-muted hover:text-ink"
                }`}
              >
                {r.label}
              </button>
            ))}
          </div>
        </label>
        <p className="text-[11px] text-ink-muted">
          {new Date(window.timeFrom).toLocaleString()} → {new Date(window.timeTo).toLocaleString()}
        </p>
      </div>

      <div className="grid gap-3 xl:grid-cols-2">
        <Section icon={<GitCommit className="h-4 w-4 text-accent" />} title="Commits" q={commits} />
        <Section icon={<PlayCircle className="h-4 w-4 text-accent" />} title="Pipeline runs" q={runs} />
        <Section icon={<TestTube2 className="h-4 w-4 text-accent" />} title="Test executions" q={tests} status="execution_status" />
        <Section icon={<Boxes className="h-4 w-4 text-accent" />} title="Pods" q={pods} />
        <Section icon={<Timer className="h-4 w-4 text-accent" />} title="Timing" q={metrics} className="xl:col-span-2" />
      </div>
    </div>
  );
}

function Section({
  icon, title, q, status, className = "",
}: {
  icon: React.ReactNode;
  title: string;
  q: ReturnType<typeof useQuery>;
  status?: string;
  className?: string;
}) {
  const count = q.result?.rows.length ?? 0;
  return (
    <section className={`card max-h-[24rem] overflow-hidden p-0 ${className}`}>
      <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
        <span aria-hidden="true">{icon}</span>
        <span className="card-title">{title}</span>
        {q.result ? (
          <span className="ml-auto rounded-full border border-line px-2 py-0.5 text-[10px] tabular-nums text-ink-muted">
            {count}
          </span>
        ) : null}
      </h2>
      {q.error ? (
        <p className="p-3 text-xs text-state-danger">{q.error}</p>
      ) : q.result ? (
        <DataTable result={q.result} statusColumn={status} />
      ) : (
        <p className="p-3 text-xs text-ink-muted">Loading…</p>
      )}
    </section>
  );
}

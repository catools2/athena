import { useState } from "react";
import { ListChecks, XCircle } from "lucide-react";
import { DataTable } from "../components/DataTable";
import { useQuery } from "../components/useQuery";

/**
 * The Zephyr-Scale-shaped view: cycles, then the tests inside one, then one test's history.
 * Three levels in one page because that is how a failure is actually investigated - "which
 * cycle is red", "what failed in it", "has this test always been flaky".
 */
export function TestCyclesPage() {
  const [version, setVersion] = useState("");
  const [cycle, setCycle] = useState<string | null>(null);
  const [item, setItem] = useState<string | null>(null);

  const cycles = useQuery("cycles_list", { version: version || null, team: [] });
  const executions = useQuery("cycle_executions", { cycle }, Boolean(cycle));
  const history = useQuery("test_history", { item }, Boolean(item));

  return (
    <div className="animate-fade-in p-6">
      <header className="mb-4">
        <p className="eyebrow-label">Quality</p>
        <h1 className="font-display text-2xl font-semibold text-ink">Test cycles</h1>
        <p className="mt-1 max-w-[70ch] text-sm text-ink-muted">
          Every cycle with its execution rollup. Select a cycle to see what ran, and a test to see
          whether it has failed before.
        </p>
      </header>

      <label className="mb-4 flex w-48 flex-col gap-1">
        <span className="eyebrow-label">Version</span>
        <input
          value={version}
          onChange={(e) => { setVersion(e.target.value); setCycle(null); setItem(null); }}
          placeholder="all versions"
          className="rounded-md border border-line bg-surface-muted/80 px-2 py-1 text-xs text-ink placeholder:text-ink-muted/60"
        />
      </label>

      <section className="card mb-4 max-h-[22rem] overflow-hidden p-0">
        <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
          <ListChecks className="h-4 w-4 text-accent" aria-hidden="true" />
          <span className="card-title">Cycles</span>
        </h2>
        {cycles.error ? (
          <p className="p-3 text-xs text-state-danger">{cycles.error}</p>
        ) : cycles.result ? (
          <DataTable result={cycles.result} onRowClick={(row) => {
            setCycle(String(row.cycle_code)); setItem(null);
          }} />
        ) : (
          <p className="p-3 text-xs text-ink-muted">Loading…</p>
        )}
      </section>

      {cycle ? (
        <section className="card mb-4 max-h-[22rem] overflow-hidden p-0">
          <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
            <span className="card-title">Executions in {cycle}</span>
            <button
              type="button"
              onClick={() => { setCycle(null); setItem(null); }}
              className="ml-auto text-ink-muted hover:text-ink"
              aria-label="Close cycle"
            >
              <XCircle className="h-4 w-4" />
            </button>
          </h2>
          {executions.result ? (
            <DataTable
              result={executions.result}
              statusColumn="execution_status"
              onRowClick={(row) => setItem(String(row.item_key))}
            />
          ) : (
            <p className="p-3 text-xs text-ink-muted">{executions.error ?? "Loading…"}</p>
          )}
        </section>
      ) : null}

      {item ? (
        <section className="card max-h-[20rem] overflow-hidden p-0">
          <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
            <span className="card-title">History for {item}</span>
            <button
              type="button"
              onClick={() => setItem(null)}
              className="ml-auto text-ink-muted hover:text-ink"
              aria-label="Close history"
            >
              <XCircle className="h-4 w-4" />
            </button>
          </h2>
          {history.result ? (
            <DataTable result={history.result} statusColumn="execution_status" />
          ) : (
            <p className="p-3 text-xs text-ink-muted">{history.error ?? "Loading…"}</p>
          )}
        </section>
      ) : null}
    </div>
  );
}

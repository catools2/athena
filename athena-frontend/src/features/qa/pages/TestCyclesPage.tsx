import { useMemo } from "react";
import { ChevronRight, ListChecks, XCircle } from "lucide-react";
import {
  FilterBar, MultiSelectFilter, RangeFilter, SelectFilter, TextFilter,
  useDimensions, useFilters, useTimeWindow,
} from "../../../shared/analytics/filters";
import { useQuery } from "../../../shared/analytics/useQuery";
import { Breadcrumbs } from "../components/Breadcrumbs";
import { DataTable } from "../components/DataTable";

/** Module-level, so the reference is stable - see the note on useFilters. */
const DEFAULTS = {
  range: "30d", from: "", to: "",
  version: "", project: "", team: [] as string[], status: "", search: "",
  cycle: "", item: "",
};

/**
 * The Zephyr-Scale-shaped view: cycles, then the tests inside one, then one test's history.
 * Three levels in one page because that is how a failure is actually investigated - "which
 * cycle is red", "what failed in it", "has this test always been flaky".
 *
 * Filters and drill position both live in the URL, which is what makes a link from the overview
 * land on a specific day and status rather than on the page's default view.
 */
export function TestCyclesPage() {
  const { values, set, reset, activeCount } = useFilters(DEFAULTS);
  const window = useTimeWindow(values);
  const dimensions = useDimensions("filter_test_dimensions");

  const cycle = values.cycle || null;
  const item = values.item || null;

  const cycles = useQuery("cycles_list", useMemo(() => ({
    ...window,
    version: values.version || null,
    project: values.project || null,
    team: values.team,
    status: values.status || null,
    search: values.search || null,
  }), [window, values.version, values.project, values.team, values.status, values.search]));

  const executions = useQuery("cycle_executions", useMemo(() => ({
    cycle,
    status: values.status || null,
    search: values.search || null,
  }), [cycle, values.status, values.search]), Boolean(cycle));

  const history = useQuery("test_history", useMemo(() => ({ item }), [item]), Boolean(item));

  /** Changing a filter invalidates the drill position: the selected cycle may no longer be listed. */
  function filter(patch: Record<string, string | string[] | null>) {
    set({ ...patch, cycle: null, item: null });
  }

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

      {cycle || item ? (
        <Breadcrumbs
          crumbs={[
            { label: "All cycles", onClick: () => set({ cycle: null, item: null }) },
            ...(cycle ? [{ label: cycle, onClick: item ? () => set({ item: null }) : undefined }] : []),
            ...(item ? [{ label: item }] : []),
          ]}
        />
      ) : null}

      <section className="card mb-4 max-h-[22rem] overflow-hidden p-0">
        <h2 className="flex items-center gap-2 border-b border-line px-3 py-2">
          <ListChecks className="h-4 w-4 text-accent" aria-hidden="true" />
          <span className="card-title">Cycles</span>
          <span className="ml-auto flex items-center gap-1 text-[10px] text-ink-muted">
            select a row to see what ran <ChevronRight className="h-3 w-3" aria-hidden="true" />
          </span>
        </h2>
        {cycles.error ? (
          <p className="p-3 text-xs text-state-danger">{cycles.error}</p>
        ) : cycles.result ? (
          <DataTable
            result={cycles.result}
            emptyMessage="No cycle matches these filters. Widen the window or clear the status."
            onRowClick={(row) => set({ cycle: String(row.cycle_code ?? ""), item: null })}
          />
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
              onClick={() => set({ cycle: null, item: null })}
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
              emptyMessage="Nothing in this cycle matches the status or search filter."
              onRowClick={(row) => set({ item: String(row.item_key ?? "") })}
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
            {/* Deliberately outside the window filter: the reason to open a test's history is to
                see whether it has always been flaky, and cropping it to the selected window is
                exactly the answer that question is not asking for. */}
            <span className="text-[10px] text-ink-muted">every recorded run, ignoring the window</span>
            <button
              type="button"
              onClick={() => set({ item: null })}
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

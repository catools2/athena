import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { ChevronDown, RotateCcw, Search } from "lucide-react";
import { useQuery } from "./useQuery";

/**
 * Filter state for every workspace page, held in the URL.
 *
 * The URL is the single source of truth rather than component state, and that is a deliberate
 * choice with two consequences worth the plumbing:
 *
 *   A filtered view is a link. "The failures on the 9th in release 2.4" is something you send
 *   to somebody, not something you describe to them in a message.
 *
 *   A chart can navigate. Clicking a bar on the overview means "open the page that explains
 *   this, already narrowed to it" - which is only expressible if the target page reads its
 *   filters from where the link can put them.
 *
 * Values equal to their default are omitted from the query string, so a link carries only the
 * decisions its sender actually made.
 */

/** A resolved absolute window. Every windowed query binds exactly this. */
export interface TimeWindow {
  timeFrom: string;
  timeTo: string;
}

export interface RangePreset {
  label: string;
  hours: number;
}

export const RANGES: RangePreset[] = [
  { label: "6h", hours: 6 },
  { label: "24h", hours: 24 },
  { label: "7d", hours: 24 * 7 },
  { label: "30d", hours: 24 * 30 },
  { label: "90d", hours: 24 * 90 },
];

export const CUSTOM_RANGE = "custom";

export type FilterSpec = Record<string, string | string[]>;
export type FilterPatch<T> = Partial<Record<keyof T, string | string[] | null>>;

/**
 * Read and write the page's filters.
 *
 * `defaults` must be a stable reference - declare it as a module-level constant, not inline.
 * An object literal in the render body is a new identity every render, which would make every
 * memo below churn and every query refetch on a loop.
 *
 * `displayOnly` names keys that belong in the URL but are not filters - which chart dimension is
 * showing, say. They are shareable and restorable like everything else here, but they narrow
 * nothing, so counting them would make "Clear 3 filters" claim the view is narrower than it is.
 */
export function useFilters<T extends FilterSpec>(defaults: T, displayOnly: readonly string[] = []) {
  const [params, setParams] = useSearchParams();

  const values = useMemo(() => {
    const out = {} as { [K in keyof T]: T[K] };
    for (const key of Object.keys(defaults) as (keyof T)[]) {
      const fallback = defaults[key];
      const raw = params.get(String(key));
      out[key] = (Array.isArray(fallback)
        ? (raw ? raw.split(",").filter(Boolean) : fallback)
        : (raw ?? fallback)) as T[keyof T];
    }
    return out;
  }, [params, defaults]);

  const write = useCallback((patch: FilterPatch<T>, replace: boolean) => {
    const next = new URLSearchParams(params);
    for (const [key, value] of Object.entries(patch)) {
      const fallback = defaults[key];
      const serialised = Array.isArray(value) ? value.join(",") : value;
      const fallbackText = Array.isArray(fallback) ? fallback.join(",") : fallback;
      if (serialised === null || serialised === "" || serialised === fallbackText) {
        next.delete(key);
      } else {
        next.set(key, String(serialised));
      }
    }
    setParams(next, { replace });
  }, [params, setParams, defaults]);

  /**
   * Change a filter. Replaces, so dragging a window does not bury the previous page under twenty
   * history entries the back button has to walk through.
   */
  const set = useCallback((patch: FilterPatch<T>) => write(patch, true), [write]);

  /**
   * Move to a different thing. Pushes, because selecting a cycle, a test, an action or a target
   * is navigation and Back should undo exactly one step of it.
   *
   * The distinction is the whole reason both exist: the two used to be the same call, so drilling
   * three levels into a page left no history at all and Back jumped out of the page entirely.
   */
  const go = useCallback((patch: FilterPatch<T>) => write(patch, false), [write]);

  const reset = useCallback(() => setParams(new URLSearchParams(), { replace: true }), [setParams]);

  /**
   * How many filters differ from the default, so the bar can say whether it is doing anything.
   *
   * range/from/to are one decision, not three: a custom window would otherwise announce itself
   * as "3 filters" and make the count useless as a measure of how narrowed the view is.
   */
  const activeCount = useMemo(() => {
    let count = 0;
    let windowCounted = false;
    for (const key of Object.keys(defaults)) {
      const fallback = defaults[key];
      const raw = params.get(key);
      if (raw === null || raw === "") continue;
      const fallbackText = Array.isArray(fallback) ? fallback.join(",") : fallback;
      if (raw === fallbackText || displayOnly.includes(key)) continue;
      if (key === "range" || key === "from" || key === "to") {
        if (windowCounted) continue;
        windowCounted = true;
      }
      count += 1;
    }
    return count;
  }, [params, defaults, displayOnly]);

  return { values, set, go, reset, activeCount };
}

/**
 * Resolve range/from/to into absolute instants.
 *
 * A preset window is anchored once, when the range changes - not on every render. Re-anchoring
 * to `Date.now()` each pass would hand every query a new parameter set and refetch forever.
 */
export function useTimeWindow(values: { range: string; from: string; to: string }): TimeWindow {
  const { range, from, to } = values;
  return useMemo(() => {
    if (range === CUSTOM_RANGE && from && to) {
      return { timeFrom: from, timeTo: to };
    }
    const preset = RANGES.find((r) => r.label === range) ?? RANGES[3];
    const now = Date.now();
    return {
      timeFrom: new Date(now - preset.hours * 3_600_000).toISOString(),
      timeTo: new Date(now).toISOString(),
    };
  }, [range, from, to]);
}

/**
 * The values a filter can offer, from a `(dimension, value)` query.
 *
 * One fetch for the whole bar. Five separate option queries would render the bar five times as
 * each arrived, and a control that grows options while you are reaching for it is worse than one
 * that appears a moment later.
 */
const NO_PARAMS = {};

export function useDimensions(queryId: string): Record<string, string[]> {
  const { result } = useQuery(queryId, NO_PARAMS);
  return useMemo(() => {
    const out: Record<string, string[]> = {};
    if (!result) return out;
    const dimension = result.columns.findIndex((c) => c.name === "dimension");
    const value = result.columns.findIndex((c) => c.name === "value");
    if (dimension < 0 || value < 0) return out;
    for (const row of result.rows) {
      const key = String(row[dimension] ?? "");
      const item = String(row[value] ?? "");
      if (!key || !item) continue;
      (out[key] ??= []).push(item);
    }
    return out;
  }, [result]);
}

/* -------------------------------------------------------------------------- controls -------- */

/** One row above the charts, which is where a filter belongs - never beside or below the data. */
export function FilterBar({
  children, activeCount = 0, onReset, note,
}: {
  children: React.ReactNode;
  activeCount?: number;
  onReset?: () => void;
  note?: React.ReactNode;
}) {
  return (
    <div className="mb-4 rounded-lg border border-line bg-surface-muted/40 px-3 py-2">
      <div className="flex flex-wrap items-end gap-x-4 gap-y-2">
        {children}
        {activeCount > 0 && onReset ? (
          <button
            type="button"
            onClick={onReset}
            className="ml-auto flex items-center gap-1 self-end rounded-md border border-line px-2 py-1 text-[11px] text-ink-muted transition hover:border-line-strong hover:text-ink"
          >
            <RotateCcw className="h-3 w-3" aria-hidden="true" />
            Clear {activeCount} filter{activeCount === 1 ? "" : "s"}
          </button>
        ) : null}
      </div>
      {note ? <p className="mt-1.5 max-w-[95ch] text-[10px] leading-relaxed text-ink-muted">{note}</p> : null}
    </div>
  );
}

function Field({ label, children }: { label: string; children: React.ReactNode }) {
  return (
    <label className="flex flex-col gap-1">
      <span className="eyebrow-label">{label}</span>
      {children}
    </label>
  );
}

const CONTROL =
  "rounded-md border border-line bg-surface-muted/80 px-2 py-1 text-[11px] text-ink placeholder:text-ink-muted/60 focus:border-line-strong focus:outline-none";

/**
 * Presets plus an explicit custom window.
 *
 * The presets cover the question "what is happening now"; the custom window exists because the
 * other question - "what happened around that deploy on Tuesday" - cannot be expressed as a
 * duration ending at this instant, and it is the question a link from a chart always asks.
 */
export function RangeFilter({
  value, from, to, onChange, presets = RANGES, label = "Window",
}: {
  value: string;
  from: string;
  to: string;
  onChange: (next: { range?: string; from?: string | null; to?: string | null }) => void;
  presets?: RangePreset[];
  label?: string;
}) {
  const custom = value === CUSTOM_RANGE;
  const resolved = useTimeWindow({ range: value, from, to });

  return (
    <div className="flex flex-wrap items-end gap-2">
      <Field label={label}>
        <div className="flex rounded-md border border-line bg-surface-muted/80 p-0.5">
          {presets.map((preset) => (
            <button
              key={preset.label}
              type="button"
              onClick={() => onChange({ range: preset.label, from: null, to: null })}
              className={`rounded px-2 py-1 text-[11px] transition ${
                value === preset.label ? "bg-accent/20 text-ink" : "text-ink-muted hover:text-ink"
              }`}
              aria-pressed={value === preset.label}
            >
              {preset.label}
            </button>
          ))}
          <button
            type="button"
            // Seed the inputs from whatever window is on screen, so switching to custom starts
            // from the view the reader is looking at rather than from two empty fields.
            onClick={() => onChange({
              range: CUSTOM_RANGE,
              from: from || resolved.timeFrom,
              to: to || resolved.timeTo,
            })}
            className={`rounded px-2 py-1 text-[11px] transition ${
              custom ? "bg-accent/20 text-ink" : "text-ink-muted hover:text-ink"
            }`}
            aria-pressed={custom}
          >
            Custom
          </button>
        </div>
      </Field>

      {custom ? (
        <>
          <Field label="From">
            <input type="datetime-local" className={CONTROL} value={toLocalInput(from)}
                   onChange={(e) => onChange({ from: fromLocalInput(e.target.value) })} />
          </Field>
          <Field label="To">
            <input type="datetime-local" className={CONTROL} value={toLocalInput(to)}
                   onChange={(e) => onChange({ to: fromLocalInput(e.target.value) })} />
          </Field>
        </>
      ) : null}
    </div>
  );
}

export function SelectFilter({
  label, value, options, onChange, allLabel = "All",
}: {
  label: string;
  value: string;
  options: string[];
  onChange: (next: string) => void;
  allLabel?: string;
}) {
  // A value can arrive from a link even when the option list has not loaded, or names something
  // no longer present. Dropping it silently would show unfiltered data under an active filter.
  const choices = value && !options.includes(value) ? [value, ...options] : options;

  return (
    <Field label={label}>
      <select value={value} onChange={(e) => onChange(e.target.value)}
              className={`${CONTROL} min-w-[7.5rem]`}>
        <option value="">{allLabel}</option>
        {choices.map((option) => <option key={option} value={option}>{option}</option>)}
      </select>
    </Field>
  );
}

/**
 * Multi-select as a disclosure rather than a popover: no outside-click handling, no focus trap,
 * and it closes with Escape because that is what `<details>` already does.
 */
export function MultiSelectFilter({
  label, values, options, onChange,
}: {
  label: string;
  values: string[];
  options: string[];
  onChange: (next: string[]) => void;
}) {
  const choices = useMemo(
    () => [...values.filter((v) => !options.includes(v)), ...options],
    [values, options]);

  function toggle(option: string) {
    onChange(values.includes(option) ? values.filter((v) => v !== option) : [...values, option]);
  }

  return (
    <div className="flex flex-col gap-1">
      <span className="eyebrow-label">{label}</span>
      <details className="relative">
        <summary className={`${CONTROL} flex cursor-pointer list-none items-center gap-1`}>
          {values.length === 0 ? <span className="text-ink-muted">Any</span> : `${values.length} selected`}
          <ChevronDown className="h-3 w-3 text-ink-muted" aria-hidden="true" />
        </summary>
        <div className="absolute left-0 top-full z-30 mt-1 max-h-56 min-w-[11rem] overflow-auto rounded-md border border-line bg-surface-strong p-1 shadow-lg">
          {choices.length === 0 ? (
            <p className="px-2 py-1 text-[11px] text-ink-muted">Nothing recorded.</p>
          ) : choices.map((option) => (
            <label key={option}
                   className="flex cursor-pointer items-center gap-2 rounded px-2 py-1 text-[11px] text-ink hover:bg-white/[0.04]">
              <input type="checkbox" checked={values.includes(option)}
                     onChange={() => toggle(option)} className="accent-accent" />
              <span className="truncate">{option}</span>
            </label>
          ))}
          {values.length > 0 ? (
            <button type="button" onClick={() => onChange([])}
                    className="mt-1 w-full rounded px-2 py-1 text-left text-[11px] text-ink-muted hover:bg-white/[0.04] hover:text-ink">
              Clear selection
            </button>
          ) : null}
        </div>
      </details>
    </div>
  );
}

/**
 * Free text, committed on a debounce.
 *
 * Every keystroke reaching the server would issue a query per character and let a slow early
 * response land after a fast later one. The debounce is the fix; local state is what keeps the
 * field responsive while it waits.
 */
export function TextFilter({
  label, value, onChange, placeholder = "Search", delayMs = 300,
}: {
  label: string;
  value: string;
  onChange: (next: string) => void;
  placeholder?: string;
  delayMs?: number;
}) {
  const [draft, setDraft] = useState(value);

  /**
   * The callback lives in a ref so the debounce below does not depend on its identity.
   *
   * Callers write `onChange={(search) => set({ search })}`, which is a new function on every
   * render - and a page like this one re-renders each time one of its five panels resolves. With
   * onChange in the dependency list, every one of those renders would clear the pending timer and
   * start it again, so a search typed while the panels were still loading could never commit.
   */
  const latest = useRef(onChange);
  useEffect(() => { latest.current = onChange; });

  // Re-sync when the value changes from outside - a Clear filters press, or a link.
  useEffect(() => { setDraft(value); }, [value]);

  useEffect(() => {
    if (draft === value) return;
    const timer = setTimeout(() => latest.current(draft), delayMs);
    return () => clearTimeout(timer);
  }, [draft, value, delayMs]);

  return (
    <Field label={label}>
      <span className="relative flex items-center">
        <Search className="pointer-events-none absolute left-2 h-3 w-3 text-ink-muted" aria-hidden="true" />
        <input
          value={draft}
          onChange={(e) => setDraft(e.target.value)}
          onKeyDown={(e) => { if (e.key === "Enter") onChange(draft); }}
          placeholder={placeholder}
          className={`${CONTROL} w-40 pl-6`}
        />
      </span>
    </Field>
  );
}

/* ------------------------------------------------------------------- window helpers --------- */

/** The custom-range patch for a single day-bucket, as a chart click produces. */
export function dayWindow(bucket: unknown): { range: string; from: string; to: string } | null {
  const start = new Date(String(bucket));
  if (Number.isNaN(start.getTime())) return null;
  // The bucket is already the database's own date_trunc('day', ...) boundary, so adding exactly
  // 24 hours reproduces the same day the chart counted - whatever time zone the server is in.
  const end = new Date(start.getTime() + 24 * 3_600_000 - 1);
  return { range: CUSTOM_RANGE, from: start.toISOString(), to: end.toISOString() };
}

function toLocalInput(iso: string): string {
  const date = new Date(iso);
  if (!iso || Number.isNaN(date.getTime())) return "";
  const pad = (n: number) => String(n).padStart(2, "0");
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`
    + `T${pad(date.getHours())}:${pad(date.getMinutes())}`;
}

function fromLocalInput(local: string): string {
  const date = new Date(local);
  return Number.isNaN(date.getTime()) ? "" : date.toISOString();
}

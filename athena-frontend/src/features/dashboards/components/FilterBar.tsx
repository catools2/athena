import { useEffect, useState } from "react";
import { AlertCircle } from "lucide-react";
import { runQuery } from "../../../shared/analytics/analyticsClient";
import type { DashboardSpec, VariableSpec } from "../../../shared/analytics/types";

const RANGES = [
  { label: "24h", hours: 24 },
  { label: "7d", hours: 24 * 7 },
  { label: "30d", hours: 24 * 30 },
  { label: "90d", hours: 24 * 90 },
  { label: "1y", hours: 24 * 365 },
];
export const DEFAULT_RANGE = "30d";

/**
 * Resolve the parameter values every panel on this dashboard will be run with.
 *
 * Time is always present because the registry's `timeFrom`/`timeTo` come from Grafana's
 * `$__timeFrom()`/`$__timeTo()` macros, which every dashboard has whether or not it declares a
 * variable for them.
 */
export function defaultParams(spec: DashboardSpec, search: URLSearchParams): Record<string, unknown> {
  const rangeLabel = search.get("range") ?? DEFAULT_RANGE;
  const hours = (RANGES.find((r) => r.label === rangeLabel) ?? RANGES[2]).hours;
  const now = Date.now();

  const params: Record<string, unknown> = {
    timeFrom: new Date(now - hours * 3600_000).toISOString(),
    timeTo: new Date(now).toISOString(),
  };

  for (const variable of spec.variables) {
    if (variable.unsupported) continue;
    const raw = search.get(variable.name);
    if (variable.type === "constant") {
      params[variable.name] = variable.value ?? "";
    } else if (variable.multi) {
      // An empty list is the "All" case: the registry translates Grafana's __all__ sentinel
      // to cardinality(:x) = 0, so absent means "do not filter".
      params[variable.name] = raw ? raw.split(",").filter(Boolean) : [];
    } else if (raw) {
      params[variable.name] = raw;
    } else if (variable.options?.length) {
      params[variable.name] = variable.options[0];
    }
  }
  return params;
}

interface FilterBarProps {
  spec: DashboardSpec;
  params: Record<string, unknown>;
  /** The selected range label, read from the URL by the page that owns it. */
  range: string;
  onChange: (name: string, value: string | string[]) => void;
}

/** One row of filters above the charts, as the interaction spec calls for. */
export function FilterBar({ spec, params, onChange, range }: FilterBarProps) {
  const usable = spec.variables.filter((v) => !v.unsupported && v.type !== "constant");
  const blocked = spec.variables.filter((v) => v.unsupported);

  return (
    <div className="mb-4 flex flex-wrap items-end gap-3">
      <Field label="Time range">
        <div className="flex rounded-md border border-line bg-surface-muted/80 p-0.5">
          {RANGES.map((r) => {
            const active = range === r.label;
            return (
              <button
                key={r.label}
                type="button"
                onClick={() => onChange("range", r.label)}
                className={`rounded px-2.5 py-1 text-[11px] transition ${
                  active ? "bg-accent/20 text-ink" : "text-ink-muted hover:text-ink"
                }`}
              >
                {r.label}
              </button>
            );
          })}
        </div>
      </Field>

      {usable.map((variable) => (
        <VariableField
          key={variable.name}
          variable={variable}
          value={params[variable.name]}
          onChange={onChange}
        />
      ))}

      {blocked.length > 0 ? (
        <span
          className="flex items-center gap-1 text-[10px] text-ink-muted"
          title={blocked.map((v) => `$${v.name}: ${v.unsupported}`).join("\n")}
        >
          <AlertCircle className="h-3 w-3" aria-hidden="true" />
          {blocked.length} filter{blocked.length > 1 ? "s" : ""} unavailable
        </span>
      ) : null}
    </div>
  );
}

function VariableField({
  variable, value, onChange,
}: { variable: VariableSpec; value: unknown; onChange: FilterBarProps["onChange"] }) {
  const [options, setOptions] = useState<string[]>(variable.options ?? []);

  useEffect(() => {
    if (variable.type !== "query" || !variable.queryId) return;
    let cancelled = false;
    // Variable option lists are registry queries too, so the dropdown is populated by the
    // same path as the panels rather than a second mechanism.
    runQuery(variable.queryId, {})
      .then((result) => {
        if (cancelled) return;
        setOptions(result.rows.map((row) => String(row[0])).filter(Boolean));
      })
      .catch(() => {
        if (!cancelled) setOptions([]);
      });
    return () => { cancelled = true; };
  }, [variable.type, variable.queryId]);

  const selected = Array.isArray(value) ? value : value === undefined ? [] : [String(value)];

  return (
    <Field label={variable.label}>
      <select
        className="min-w-[9rem] rounded-md border border-line bg-surface-muted/80 px-2 py-1 text-[11px] text-ink"
        multiple={variable.multi}
        size={1}
        value={variable.multi ? selected : (selected[0] ?? "")}
        onChange={(e) => {
          if (variable.multi) {
            onChange(variable.name, Array.from(e.target.selectedOptions, (o) => o.value));
          } else {
            onChange(variable.name, e.target.value);
          }
        }}
      >
        {!variable.multi ? <option value="">All</option> : null}
        {options.map((option) => (
          <option key={option} value={option}>{option}</option>
        ))}
      </select>
    </Field>
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

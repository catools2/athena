import type { TooltipProps } from "recharts";

/**
 * Shared tooltip. Values wear ink tokens, never the series colour - the swatch beside a row
 * carries identity, so the text stays legible for anyone who cannot separate the hues.
 */
export function ChartTooltip({ active, payload, label }: TooltipProps<number, string>) {
  if (!active || !payload?.length) return null;

  return (
    <div className="rounded-md border border-line-strong bg-surface-strong/95 px-3 py-2 shadow-raised backdrop-blur-sm">
      {label !== undefined && label !== "" ? (
        <p className="mb-1 text-[11px] font-medium text-ink-muted">{String(label)}</p>
      ) : null}
      <ul className="space-y-0.5">
        {payload.map((entry) => (
          <li key={String(entry.dataKey)} className="flex items-center gap-2 text-xs">
            <span
              aria-hidden="true"
              className="h-2 w-2 shrink-0 rounded-[2px]"
              style={{ background: entry.color }}
            />
            <span className="text-ink-muted">{entry.name}</span>
            <span className="ml-auto font-medium tabular-nums text-ink">
              {formatValue(entry.value)}
            </span>
          </li>
        ))}
      </ul>
    </div>
  );
}

export function formatValue(value: unknown): string {
  if (value === null || value === undefined) return "—";
  if (typeof value === "number") {
    if (!Number.isFinite(value)) return "—";
    return Number.isInteger(value)
      ? value.toLocaleString()
      : value.toLocaleString(undefined, { maximumFractionDigits: 2 });
  }
  return String(value);
}

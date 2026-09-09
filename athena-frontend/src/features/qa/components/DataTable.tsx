import type { QueryResult } from "../../../shared/analytics/types";
import { formatValue } from "../../dashboards/components/ChartTooltip";

interface DataTableProps {
  result: QueryResult;
  onRowClick?: (row: Record<string, unknown>) => void;
  /** Column name whose value decides the row accent, for pass/fail style tables. */
  statusColumn?: string;
}

/** Status colour is never the only signal - the value itself is always in the cell. */
const STATUS_TONE: Record<string, string> = {
  pass: "text-state-success",
  fail: "text-state-danger",
  blocked: "text-state-warning",
  skipped: "text-ink-muted",
};

export function DataTable({ result, onRowClick, statusColumn }: DataTableProps) {
  const statusIndex = statusColumn
    ? result.columns.findIndex((c) => c.name === statusColumn)
    : -1;

  return (
    <div className="overflow-auto">
      <table className="w-full border-collapse text-left text-xs">
        <thead className="sticky top-0 z-10 bg-surface-strong/95 backdrop-blur-sm">
          <tr>
            {result.columns.map((c) => (
              <th key={c.name} className="whitespace-nowrap border-b border-line px-3 py-2 font-medium text-ink-muted">
                {c.name}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {result.rows.map((row, i) => {
            const status = statusIndex >= 0 ? String(row[statusIndex] ?? "").toLowerCase() : "";
            const tone = Object.entries(STATUS_TONE).find(([k]) => status.includes(k))?.[1];
            return (
              <tr
                key={i}
                onClick={onRowClick ? () => onRowClick(asRecord(result, row)) : undefined}
                className={`${onRowClick ? "cursor-pointer" : ""} hover:bg-white/[0.03]`}
              >
                {row.map((cell, j) => (
                  <td
                    key={j}
                    className={`whitespace-nowrap border-b border-line/40 px-3 py-1.5 tabular-nums ${
                      j === statusIndex && tone ? tone : "text-ink"
                    }`}
                  >
                    {formatValue(cell)}
                  </td>
                ))}
              </tr>
            );
          })}
        </tbody>
      </table>
      {result.rows.length === 0 ? (
        <p className="px-3 py-6 text-center text-xs text-ink-muted">
          The query ran successfully and matched no rows.
        </p>
      ) : null}
    </div>
  );
}

function asRecord(result: QueryResult, row: unknown[]): Record<string, unknown> {
  const record: Record<string, unknown> = {};
  result.columns.forEach((c, i) => { record[c.name] = row[i]; });
  return record;
}

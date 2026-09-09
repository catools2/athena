import {
  Bar, BarChart, CartesianGrid, Cell, Legend, Line, LineChart, Pie, PieChart,
  ResponsiveContainer, Tooltip, XAxis, YAxis,
} from "recharts";
import type { PanelSpec, QueryResult } from "../../../shared/analytics/types";
import { toRecords } from "../../../shared/analytics/analyticsClient";
import { CHART_INK, MAX_SERIES, SERIES_COLORS } from "../../../shared/analytics/palette";
import { ChartTooltip, formatValue } from "./ChartTooltip";

/**
 * Renders one panel's rows with the viz its spec asks for.
 *
 * The first column is the category or time axis and the rest are series - which is how the
 * Grafana panels these specs came from were already written, so no per-panel field mapping is
 * needed. Series beyond the palette's seven are folded into "Other" rather than given a
 * generated hue, because a cycled palette makes two different series the same colour.
 */
export function PanelChart({ panel, result }: { panel: PanelSpec; result: QueryResult }) {
  const records = toRecords(result);
  const [axis, ...valueColumns] = result.columns.map((c) => c.name);
  // Reserve a slot for "Other" when folding, so the key count never exceeds the palette.
  // Overshooting it hands recharts an undefined colour and silently drops out of the
  // validated set.
  const willFold = valueColumns.length > MAX_SERIES;
  const series = valueColumns.slice(0, willFold ? MAX_SERIES - 1 : MAX_SERIES);
  const overflow = valueColumns.length - series.length;

  const common = {
    data: overflow > 0 ? foldOther(records, axis, series, valueColumns) : records,
    margin: { top: 8, right: 12, bottom: 4, left: 4 },
  };
  const keys = overflow > 0 ? [...series, "Other"] : series;
  // A single series is named by the panel title, so a legend box would just repeat it.
  const showLegend = keys.length >= 2;

  const axisProps = {
    stroke: CHART_INK.axis,
    tick: { fill: CHART_INK.label, fontSize: 11 },
    tickLine: false,
    axisLine: { stroke: CHART_INK.grid },
  };

  switch (panel.viz) {
    case "timeseries":
      return (
        <Frame>
          <LineChart {...common}>
            <CartesianGrid stroke={CHART_INK.grid} vertical={false} />
            <XAxis dataKey={axis} {...axisProps} tickFormatter={shortLabel} minTickGap={24} />
            <YAxis {...axisProps} width={44} />
            <Tooltip content={<ChartTooltip />} cursor={{ stroke: CHART_INK.axis, strokeWidth: 1 }} />
            {showLegend ? <Legend {...legendProps} /> : null}
            {keys.map((key, i) => (
              <Line
                key={key} type="monotone" dataKey={key}
                stroke={SERIES_COLORS[i]} strokeWidth={2}
                dot={false} activeDot={{ r: 4, strokeWidth: 2, stroke: CHART_INK.surface }}
                isAnimationActive={false}
              />
            ))}
          </LineChart>
        </Frame>
      );

    case "bar":
    case "barGauge":
      return (
        <Frame>
          <BarChart {...common} layout={panel.viz === "barGauge" ? "vertical" : "horizontal"}>
            <CartesianGrid stroke={CHART_INK.grid} vertical={panel.viz === "barGauge"} horizontal={panel.viz !== "barGauge"} />
            {panel.viz === "barGauge" ? (
              <>
                <XAxis type="number" {...axisProps} />
                <YAxis type="category" dataKey={axis} {...axisProps} width={120} tickFormatter={shortLabel} />
              </>
            ) : (
              <>
                <XAxis dataKey={axis} {...axisProps} tickFormatter={shortLabel} />
                <YAxis {...axisProps} width={44} />
              </>
            )}
            <Tooltip content={<ChartTooltip />} cursor={{ fill: "rgba(255,255,255,0.04)" }} />
            {showLegend ? <Legend {...legendProps} /> : null}
            {keys.map((key, i) => (
              // 2px surface gap between adjacent bars, and rounded data-ends anchored to baseline.
              <Bar
                key={key} dataKey={key} fill={SERIES_COLORS[i]}
                radius={panel.viz === "barGauge" ? [0, 4, 4, 0] : [4, 4, 0, 0]}
                stroke={CHART_INK.surface} strokeWidth={2}
                isAnimationActive={false}
              />
            ))}
          </BarChart>
        </Frame>
      );

    case "pie": {
      const valueKey = series[0];
      const slices = common.data.slice(0, MAX_SERIES);
      return (
        <Frame>
          <PieChart margin={common.margin}>
            <Tooltip content={<ChartTooltip />} />
            <Legend {...legendProps} />
            <Pie
              data={slices} dataKey={valueKey} nameKey={axis}
              innerRadius="45%" outerRadius="72%" paddingAngle={2}
              stroke={CHART_INK.surface} strokeWidth={2}
              isAnimationActive={false}
            >
              {slices.map((_, i) => (
                <Cell key={i} fill={SERIES_COLORS[i % MAX_SERIES]} />
              ))}
            </Pie>
          </PieChart>
        </Frame>
      );
    }

    case "stat":
    case "gauge": {
      // A single headline number is not a chart; a tile reads faster and cannot mislead.
      const value = records[0]?.[series[0] ?? axis];
      return (
        <div className="flex h-full flex-col items-start justify-center">
          <p className="font-display text-3xl font-semibold tabular-nums text-ink">
            {formatValue(value)}
          </p>
          <p className="eyebrow-label mt-1">{series[0] ?? axis}</p>
        </div>
      );
    }

    default:
      return <PanelTable result={result} />;
  }
}

/** Always available, and the fallback for any viz the renderer does not implement yet. */
export function PanelTable({ result }: { result: QueryResult }) {
  return (
    <div className="h-full overflow-auto">
      <table className="w-full border-collapse text-left text-xs">
        <thead className="sticky top-0 bg-surface-strong/95 backdrop-blur-sm">
          <tr>
            {result.columns.map((c) => (
              <th key={c.name} className="border-b border-line px-2 py-1.5 font-medium text-ink-muted">
                {c.name}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {result.rows.map((row, i) => (
            <tr key={i} className="hover:bg-white/[0.03]">
              {row.map((cell, j) => (
                <td key={j} className="border-b border-line/50 px-2 py-1.5 tabular-nums text-ink">
                  {formatValue(cell)}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

const legendProps = {
  wrapperStyle: { fontSize: 11, color: CHART_INK.label, paddingTop: 4 },
  iconType: "square" as const,
  iconSize: 8,
};

function Frame({ children }: { children: React.ReactElement }) {
  return (
    <ResponsiveContainer width="100%" height="100%" minHeight={120}>
      {children}
    </ResponsiveContainer>
  );
}

function shortLabel(value: unknown): string {
  const text = String(value ?? "");
  if (/^\d{4}-\d{2}-\d{2}/.test(text)) {
    const date = new Date(text);
    if (!Number.isNaN(date.getTime())) {
      return `${date.getMonth() + 1}/${date.getDate()}`;
    }
  }
  return text.length > 18 ? `${text.slice(0, 17)}…` : text;
}

/** Sum the tail into a single "Other" series rather than cycling the palette. */
function foldOther(
  records: Record<string, unknown>[],
  axis: string,
  kept: string[],
  all: string[],
): Record<string, unknown>[] {
  const tail = all.slice(kept.length);
  return records.map((record) => {
    const folded: Record<string, unknown> = { [axis]: record[axis] };
    kept.forEach((key) => {
      folded[key] = record[key];
    });
    folded.Other = tail.reduce((sum, key) => sum + (Number(record[key]) || 0), 0);
    return folded;
  });
}

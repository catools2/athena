import {
  Area, Bar, BarChart, CartesianGrid, ComposedChart, Legend, Line,
  ResponsiveContainer, Tooltip, XAxis, YAxis,
} from "recharts";
import type { QueryResult } from "../../../shared/analytics/types";
import { toRecords } from "../../../shared/analytics/analyticsClient";
import { CHART_INK, SERIES_COLORS } from "../../../shared/analytics/palette";
import { ChartTooltip } from "../../../shared/analytics/ChartTooltip";

const axisProps = {
  stroke: CHART_INK.axis,
  tick: { fill: CHART_INK.label, fontSize: 11 },
  tickLine: false,
  axisLine: { stroke: CHART_INK.grid },
};

function Empty({ label }: { label: string }) {
  return (
    <div className="flex h-full min-h-[120px] items-center justify-center">
      <p className="text-xs text-ink-muted">{label}</p>
    </div>
  );
}

/**
 * Percentile bands over time.
 *
 * p95 is drawn as a filled area beneath the p50 line rather than as a second line: the gap
 * between them *is* the tail, and showing it as an area makes a widening tail visible at a
 * glance instead of requiring the reader to subtract two lines.
 */
export function PercentileTrend({ result, granularity }: { result: QueryResult | null; granularity: "hour" | "day" }) {
  if (!result || result.rows.length === 0) return <Empty label="No measurements in this window." />;
  const data = toRecords(result);

  return (
    <ResponsiveContainer width="100%" height="100%" minHeight={140}>
      <ComposedChart data={data} margin={{ top: 8, right: 12, bottom: 4, left: 4 }}>
        <defs>
          <linearGradient id="p95fill" x1="0" y1="0" x2="0" y2="1">
            <stop offset="0%" stopColor={SERIES_COLORS[0]} stopOpacity={0.28} />
            <stop offset="100%" stopColor={SERIES_COLORS[0]} stopOpacity={0.02} />
          </linearGradient>
        </defs>
        <CartesianGrid stroke={CHART_INK.grid} vertical={false} />
        <XAxis dataKey="bucket" {...axisProps} minTickGap={28}
               tickFormatter={(v) => formatBucket(v, granularity)} />
        <YAxis {...axisProps} width={52} unit="ms" />
        <Tooltip content={<ChartTooltip />} cursor={{ stroke: CHART_INK.axis, strokeWidth: 1 }}
                 labelFormatter={(v) => formatBucket(v, granularity, true)} />
        <Legend wrapperStyle={{ fontSize: 11, color: CHART_INK.label }} iconType="square" iconSize={8} />
        <Area type="monotone" dataKey="p95_ms" name="p95" stroke={SERIES_COLORS[0]} strokeWidth={2}
              fill="url(#p95fill)" isAnimationActive={false} />
        <Line type="monotone" dataKey="p50_ms" name="p50" stroke={SERIES_COLORS[1]} strokeWidth={2}
              dot={false} isAnimationActive={false} />
      </ComposedChart>
    </ResponsiveContainer>
  );
}

/** Duration distribution. The shape a percentile cannot show. */
export function DurationHistogram({ result }: { result: QueryResult | null }) {
  if (!result || result.rows.length === 0) return <Empty label="Not enough samples to bucket." />;
  const data = toRecords(result);

  return (
    <ResponsiveContainer width="100%" height="100%" minHeight={140}>
      <BarChart data={data} margin={{ top: 8, right: 12, bottom: 4, left: 4 }}>
        <CartesianGrid stroke={CHART_INK.grid} vertical={false} />
        <XAxis dataKey="range_ms" {...axisProps} interval="preserveStartEnd" minTickGap={16} />
        <YAxis {...axisProps} width={44} />
        <Tooltip content={<ChartTooltip />} cursor={{ fill: "rgba(255,255,255,0.04)" }} />
        <Bar dataKey="samples" name="samples" fill={SERIES_COLORS[0]} radius={[4, 4, 0, 0]}
             stroke={CHART_INK.surface} strokeWidth={2} isAnimationActive={false} />
      </BarChart>
    </ResponsiveContainer>
  );
}

/** Per-target p95, horizontal so long target names stay readable. */
export function TargetBreakdown({
  result, onSelect,
}: { result: QueryResult | null; onSelect?: (target: string) => void }) {
  if (!result || result.rows.length === 0) return <Empty label="No targets recorded." />;
  const data = toRecords(result).slice(0, 15);

  return (
    <ResponsiveContainer width="100%" height="100%" minHeight={140}>
      <BarChart data={data} layout="vertical" margin={{ top: 4, right: 12, bottom: 4, left: 4 }}>
        <CartesianGrid stroke={CHART_INK.grid} horizontal={false} />
        <XAxis type="number" {...axisProps} unit="ms" />
        <YAxis type="category" dataKey="target" {...axisProps} width={130} />
        <Tooltip content={<ChartTooltip />} cursor={{ fill: "rgba(255,255,255,0.04)" }} />
        <Bar dataKey="p95_ms" name="p95" fill={SERIES_COLORS[0]} radius={[0, 4, 4, 0]}
             stroke={CHART_INK.surface} strokeWidth={2} isAnimationActive={false}
             cursor={onSelect ? "pointer" : undefined}
             onClick={(bar: { target?: string }) => bar?.target && onSelect?.(bar.target)} />
      </BarChart>
    </ResponsiveContainer>
  );
}

function formatBucket(value: unknown, granularity: "hour" | "day", full = false): string {
  const date = new Date(String(value));
  if (Number.isNaN(date.getTime())) return String(value);
  if (full) return date.toLocaleString();
  return granularity === "hour"
    ? `${date.getMonth() + 1}/${date.getDate()} ${String(date.getHours()).padStart(2, "0")}h`
    : `${date.getMonth() + 1}/${date.getDate()}`;
}

import {
  Area, Bar, BarChart, CartesianGrid, ComposedChart, Legend, Line,
  ReferenceLine, ResponsiveContainer, Tooltip, XAxis, YAxis,
} from "recharts";
import { toRecords } from "../../../shared/analytics/analyticsClient";
import { ChartTooltip } from "../../../shared/analytics/ChartTooltip";
import { CHART_INK, SERIES_COLORS, STATUS_COLORS } from "../../../shared/analytics/palette";
import type { QueryResult } from "../../../shared/analytics/types";

/**
 * Charts for the quality workspace.
 *
 * Pass, blocked and failed are drawn in the reserved status palette everywhere here, never in
 * the categorical one - they are states, not series, and the reader should be able to carry the
 * meaning of red between every chart on the page. The legend always names them, so colour is
 * never the only thing carrying it.
 *
 * Every mark calls back with enough to rebuild the rows behind it, because a chart that shows a
 * problem without a way into it just moves the question somewhere else.
 */

const axisProps = {
  stroke: CHART_INK.axis,
  tick: { fill: CHART_INK.label, fontSize: 11 },
  tickLine: false,
  axisLine: { stroke: CHART_INK.grid },
};

const STATUS_SERIES = [
  { key: "passed", name: "Passed", color: STATUS_COLORS.good },
  { key: "blocked", name: "Blocked", color: STATUS_COLORS.warning },
  { key: "failed", name: "Failed", color: STATUS_COLORS.critical },
] as const;

/** recharts hands the bar's datum back in different shapes by version; normalise once. */
function payloadOf(bar: unknown): Record<string, unknown> {
  return ((bar as { payload?: Record<string, unknown> })?.payload
    ?? (bar as Record<string, unknown>)) ?? {};
}

function stop(event: unknown) {
  (event as { stopPropagation?: () => void } | undefined)?.stopPropagation?.();
}

/**
 * Outcome composition per cycle, horizontal so cycle names stay readable.
 *
 * Stacked rather than grouped: the question is "how much of this cycle is red", which is a
 * part-to-whole reading, and grouped bars make the reader add the segments themselves.
 */
export function OutcomeByCycle({
  result, onSelect, limit = 12,
}: {
  result: QueryResult;
  onSelect: (cycle: string, status: "passed" | "blocked" | "failed") => void;
  limit?: number;
}) {
  const data = toRecords(result).slice(0, limit);

  return (
    <ResponsiveContainer width="100%" height="100%" minHeight={140}>
      <BarChart data={data} layout="vertical" margin={{ top: 4, right: 12, bottom: 4, left: 4 }}>
        <CartesianGrid stroke={CHART_INK.grid} horizontal={false} />
        <XAxis type="number" {...axisProps} allowDecimals={false} />
        <YAxis type="category" dataKey="cycle_short_name" {...axisProps} width={140} />
        <Tooltip content={<ChartTooltip />} cursor={{ fill: "rgba(255,255,255,0.04)" }} />
        <Legend wrapperStyle={{ fontSize: 11, color: CHART_INK.label }} iconType="square" iconSize={8} />
        {STATUS_SERIES.map(({ key, name, color }) => (
          <Bar
            key={key} stackId="o" dataKey={key} name={name} fill={color} maxBarSize={26}
            stroke={CHART_INK.surface} strokeWidth={2} isAnimationActive={false} cursor="pointer"
            onClick={(bar, _i, event) => {
              stop(event);
              const row = payloadOf(bar);
              if (row.cycle_code) onSelect(String(row.cycle_code), key);
            }}
          />
        ))}
      </BarChart>
    </ResponsiveContainer>
  );
}

/** Daily outcomes. Answers "when did this happen" where the cycle chart answers "where". */
export function ActivityByDay({
  result, onSelect,
}: {
  result: QueryResult;
  onSelect: (bucket: unknown, status: "passed" | "blocked" | "failed" | null) => void;
}) {
  const data = toRecords(result);

  return (
    <ResponsiveContainer width="100%" height="100%" minHeight={140}>
      <BarChart data={data} margin={{ top: 8, right: 12, bottom: 4, left: 4 }}
                style={{ cursor: "pointer" }}
                onClick={(state) => state?.activeLabel && onSelect(state.activeLabel, null)}>
        <CartesianGrid stroke={CHART_INK.grid} vertical={false} />
        <XAxis dataKey="bucket" {...axisProps} tickFormatter={shortDate} minTickGap={22} />
        <YAxis {...axisProps} width={38} allowDecimals={false} />
        <Tooltip content={<ChartTooltip />} cursor={{ fill: "rgba(255,255,255,0.04)" }}
                 labelFormatter={(v) => new Date(String(v)).toLocaleDateString()} />
        <Legend wrapperStyle={{ fontSize: 11, color: CHART_INK.label }} iconType="square" iconSize={8} />
        {STATUS_SERIES.map(({ key, name, color }) => (
          <Bar
            key={key} stackId="o" dataKey={key} name={name} fill={color} maxBarSize={34}
            stroke={CHART_INK.surface} strokeWidth={2} isAnimationActive={false} cursor="pointer"
            radius={key === "failed" ? [4, 4, 0, 0] : undefined}
            onClick={(bar, _i, event) => { stop(event); onSelect(payloadOf(bar).bucket, key); }}
          />
        ))}
      </BarChart>
    </ResponsiveContainer>
  );
}

/**
 * Outcome split per bucket of one dimension, ordered worst-first.
 *
 * Failures are stacked against the rest of the executions on purpose: 40 failures out of 400 and
 * 40 out of 45 draw the same bar when only failures are plotted, and they are not the same
 * problem at all.
 */
export function FailureProfile({
  rows, onSelect,
}: {
  rows: Record<string, unknown>[];
  onSelect: (bucket: string, status: "passed" | "blocked" | "failed") => void;
}) {
  return (
    <ResponsiveContainer width="100%" height="100%" minHeight={140}>
      <BarChart data={rows} layout="vertical" margin={{ top: 4, right: 12, bottom: 4, left: 4 }}>
        <CartesianGrid stroke={CHART_INK.grid} horizontal={false} />
        <XAxis type="number" {...axisProps} allowDecimals={false} />
        <YAxis type="category" dataKey="bucket" {...axisProps} width={130} />
        <Tooltip content={<ChartTooltip />} cursor={{ fill: "rgba(255,255,255,0.04)" }} />
        <Legend wrapperStyle={{ fontSize: 11, color: CHART_INK.label }} iconType="square" iconSize={8} />
        {STATUS_SERIES.map(({ key, name, color }) => (
          <Bar
            key={key} stackId="o" dataKey={key} name={name} fill={color} maxBarSize={26}
            stroke={CHART_INK.surface} strokeWidth={2} isAnimationActive={false} cursor="pointer"
            onClick={(bar, _i, event) => {
              stop(event);
              const row = payloadOf(bar);
              if (row.bucket) onSelect(String(row.bucket), key);
            }}
          />
        ))}
      </BarChart>
    </ResponsiveContainer>
  );
}

/**
 * Burn-up: what the cycle has got through, against what it contains.
 *
 * The reference line is the cycle's own size, so "nearly done" and "stalled at 60%" are the same
 * glance. A burn-down of the remainder cannot tell a finished cycle from one whose scope was cut.
 */
export function CycleBurnUp({
  result, onSelect,
}: {
  result: QueryResult;
  onSelect: (bucket: unknown) => void;
}) {
  const data = toRecords(result);
  const total = Number(data[0]?.cycle_total ?? 0);

  return (
    <ResponsiveContainer width="100%" height="100%" minHeight={140}>
      <ComposedChart data={data} margin={{ top: 8, right: 12, bottom: 4, left: 4 }}
                     style={{ cursor: "pointer" }}
                     onClick={(state) => state?.activeLabel && onSelect(state.activeLabel)}>
        <defs>
          <linearGradient id="burnup" x1="0" y1="0" x2="0" y2="1">
            <stop offset="0%" stopColor={SERIES_COLORS[0]} stopOpacity={0.3} />
            <stop offset="100%" stopColor={SERIES_COLORS[0]} stopOpacity={0.02} />
          </linearGradient>
        </defs>
        <CartesianGrid stroke={CHART_INK.grid} vertical={false} />
        <XAxis dataKey="bucket" {...axisProps} tickFormatter={shortDate} minTickGap={22} />
        <YAxis {...axisProps} width={38} allowDecimals={false}
               domain={[0, (max: number) => Math.max(max, total)]} />
        <Tooltip content={<ChartTooltip />} cursor={{ stroke: CHART_INK.axis, strokeWidth: 1 }}
                 labelFormatter={(v) => new Date(String(v)).toLocaleDateString()} />
        <Legend wrapperStyle={{ fontSize: 11, color: CHART_INK.label }} iconType="square" iconSize={8} />
        {total > 0 ? (
          <ReferenceLine y={total} stroke={CHART_INK.axis} strokeDasharray="4 4"
                         label={{ value: `${total} in cycle`, position: "insideTopRight",
                                  fill: CHART_INK.label, fontSize: 10 }} />
        ) : null}
        <Area type="monotone" dataKey="cumulative_executed" name="Executed to date"
              stroke={SERIES_COLORS[0]} strokeWidth={2} fill="url(#burnup)" isAnimationActive={false} />
        <Line type="monotone" dataKey="failed" name="Failed that day"
              stroke={STATUS_COLORS.critical} strokeWidth={2} dot={false} isAnimationActive={false} />
      </ComposedChart>
    </ResponsiveContainer>
  );
}

function shortDate(value: unknown): string {
  const date = new Date(String(value));
  return Number.isNaN(date.getTime()) ? String(value) : `${date.getMonth() + 1}/${date.getDate()}`;
}

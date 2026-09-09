import { useMemo, useState } from "react";
import {
  Bar, BarChart, CartesianGrid, Legend, Line, LineChart, ResponsiveContainer, Tooltip, XAxis, YAxis,
} from "recharts";
import { Gauge, TrendingUp } from "lucide-react";
import { toRecords } from "../../../shared/analytics/analyticsClient";
import { CHART_INK, SERIES_COLORS } from "../../../shared/analytics/palette";
import { ChartTooltip } from "../../dashboards/components/ChartTooltip";
import { DataTable } from "../components/DataTable";
import { useQuery } from "../components/useQuery";

const RANGES = [
  { label: "24h", hours: 24 },
  { label: "7d", hours: 24 * 7 },
  { label: "30d", hours: 24 * 30 },
];

/**
 * Timing over athena_metric: distribution now, trend over time, and change against the
 * preceding window.
 *
 * Percentiles throughout, never a mean - an average hides exactly the tail that users feel,
 * and a regression usually shows in p95 long before it moves p50.
 */
export function PerformancePage() {
  const [range, setRange] = useState("7d");
  const [action, setAction] = useState("");

  const window = useMemo(() => {
    const hours = (RANGES.find((r) => r.label === range) ?? RANGES[1]).hours;
    const now = Date.now();
    return {
      timeFrom: new Date(now - hours * 3600_000).toISOString(),
      timeTo: new Date(now).toISOString(),
    };
  }, [range]);

  const filter = { ...window, action: action || null };
  const summary = useQuery("perf_action_summary", filter);
  const trend = useQuery("perf_trend", filter);
  const regression = useQuery("perf_regression", window);

  const trendData = trend.result ? toRecords(trend.result) : [];
  const regressionData = regression.result ? toRecords(regression.result).slice(0, 12) : [];

  const axisProps = {
    stroke: CHART_INK.axis,
    tick: { fill: CHART_INK.label, fontSize: 11 },
    tickLine: false,
    axisLine: { stroke: CHART_INK.grid },
  };

  return (
    <div className="animate-fade-in p-6">
      <header className="mb-4">
        <p className="eyebrow-label">Performance</p>
        <h1 className="font-display text-2xl font-semibold text-ink">Action timing</h1>
        <p className="mt-1 max-w-[70ch] text-sm text-ink-muted">
          Percentiles from <code className="text-ink">athena_metric</code>. p95 is the column that
          moves first when something regresses.
        </p>
      </header>

      <div className="mb-4 flex flex-wrap items-end gap-3">
        <label className="flex flex-col gap-1">
          <span className="eyebrow-label">Window</span>
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
        <label className="flex flex-col gap-1">
          <span className="eyebrow-label">Action contains</span>
          <input
            value={action}
            onChange={(e) => setAction(e.target.value)}
            placeholder="all actions"
            className="w-56 rounded-md border border-line bg-surface-muted/80 px-2 py-1 text-xs text-ink placeholder:text-ink-muted/60"
          />
        </label>
      </div>

      <div className="mb-4 grid gap-3 lg:grid-cols-2">
        <section className="card h-72 p-4">
          <h2 className="card-title mb-2 flex items-center gap-2">
            <TrendingUp className="h-4 w-4 text-accent" aria-hidden="true" />
            Daily percentiles
          </h2>
          <div className="h-[calc(100%-2rem)]">
            <ResponsiveContainer width="100%" height="100%">
              <LineChart data={trendData} margin={{ top: 8, right: 12, bottom: 4, left: 4 }}>
                <CartesianGrid stroke={CHART_INK.grid} vertical={false} />
                <XAxis dataKey="bucket" {...axisProps} tickFormatter={shortDate} minTickGap={24} />
                <YAxis {...axisProps} width={52} unit="ms" />
                <Tooltip content={<ChartTooltip />} cursor={{ stroke: CHART_INK.axis, strokeWidth: 1 }} />
                <Legend wrapperStyle={{ fontSize: 11, color: CHART_INK.label }} iconType="square" iconSize={8} />
                <Line type="monotone" dataKey="p95_ms" name="p95" stroke={SERIES_COLORS[0]} strokeWidth={2} dot={false} isAnimationActive={false} />
                <Line type="monotone" dataKey="p50_ms" name="p50" stroke={SERIES_COLORS[1]} strokeWidth={2} dot={false} isAnimationActive={false} />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </section>

        <section className="card h-72 p-4">
          <h2 className="card-title mb-2 flex items-center gap-2">
            <Gauge className="h-4 w-4 text-accent" aria-hidden="true" />
            Change against the preceding window
          </h2>
          <p className="mb-2 text-[11px] text-ink-muted">
            Positive is slower. Actions with fewer than 5 samples on either side are excluded.
          </p>
          <div className="h-[calc(100%-4rem)]">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={regressionData} layout="vertical" margin={{ top: 4, right: 12, bottom: 4, left: 4 }}>
                <CartesianGrid stroke={CHART_INK.grid} horizontal={false} />
                <XAxis type="number" {...axisProps} unit="ms" />
                <YAxis type="category" dataKey="action" {...axisProps} width={130} />
                <Tooltip content={<ChartTooltip />} cursor={{ fill: "rgba(255,255,255,0.04)" }} />
                <Bar dataKey="delta_ms" name="p95 delta" fill={SERIES_COLORS[0]} radius={[0, 4, 4, 0]} stroke={CHART_INK.surface} strokeWidth={2} isAnimationActive={false} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </section>
      </div>

      <section className="card max-h-[26rem] overflow-hidden p-0">
        <h2 className="card-title border-b border-line px-3 py-2">Distribution by action</h2>
        {summary.result ? (
          <DataTable result={summary.result} />
        ) : (
          <p className="p-3 text-xs text-ink-muted">{summary.error ?? "Loading…"}</p>
        )}
      </section>
    </div>
  );
}

function shortDate(value: unknown): string {
  const date = new Date(String(value));
  return Number.isNaN(date.getTime()) ? String(value) : `${date.getMonth() + 1}/${date.getDate()}`;
}

import { formatDateTime, formatNumber } from "./workspacePageUtils";

function toNumber(value) {
  const numericValue = Number(value);
  return Number.isFinite(numericValue) ? numericValue : 0;
}

function formatBucketLabel(value) {
  if (!value) {
    return "—";
  }

  const date = new Date(value);
  if (Number.isNaN(date.getTime())) {
    return "—";
  }

  return `${date.getMonth() + 1}/${date.getDate()}`;
}

function buildCoordinates(points, key, width, height, padding, maxValue) {
  const innerWidth = width - padding * 2;
  const innerHeight = height - padding * 2;
  const lastIndex = Math.max(points.length - 1, 1);

  return points.map((point, index) => {
    const x = padding + (innerWidth * index) / lastIndex;
    const y = height - padding - (toNumber(point?.[key]) / maxValue) * innerHeight;
    return { x, y };
  });
}

export function LineTrendPanel({
  title,
  subtitle,
  points,
  primaryKey,
  secondaryKey,
  primaryLabel,
  secondaryLabel,
  valueFormatter = (value) => formatNumber(value, 0, "0"),
  latestTimestampKey = "bucketStart",
}) {
  const chartPoints = Array.isArray(points) ? points.slice(-8) : [];

  if (chartPoints.length === 0) {
    return (
      <article className="chart-shell graph-panel">
        <p className="eyebrow">Trend panel</p>
        <h3>{title}</h3>
        {subtitle ? <p className="muted">{subtitle}</p> : null}
        <div className="empty-state">
          <p className="eyebrow">No trend points</p>
          <p className="muted">This panel will populate after the next reporting bucket is available.</p>
        </div>
      </article>
    );
  }

  const width = 560;
  const height = 220;
  const padding = 30;
  const latestPoint = chartPoints.at(-1);
  const maxValue = Math.max(...chartPoints.map((point) => Math.max(toNumber(point?.[primaryKey]), secondaryKey ? toNumber(point?.[secondaryKey]) : 0)), 1);
  const primaryCoordinates = buildCoordinates(chartPoints, primaryKey, width, height, padding, maxValue);
  const secondaryCoordinates = secondaryKey ? buildCoordinates(chartPoints, secondaryKey, width, height, padding, maxValue) : [];
  const gridValues = [0.25, 0.5, 0.75, 1].map((ratio) => Math.round(maxValue * ratio)).filter((value, index, values) => value > 0 && values.indexOf(value) === index);

  return (
    <article className="chart-shell graph-panel">
      <p className="eyebrow">Trend panel</p>
      <h3>{title}</h3>
      {subtitle ? <p className="muted">{subtitle}</p> : null}

      <svg className="graph-svg" viewBox={`0 0 ${width} ${height}`} role="img" aria-label={`${title} time trend`}>
        {gridValues.map((gridValue) => {
          const y = padding + (height - padding * 2) - (gridValue / maxValue) * (height - padding * 2);

          return (
            <g key={gridValue}>
              <line className="graph-grid" x1={padding} y1={y} x2={width - padding} y2={y} />
              <text className="graph-axis" x={6} y={y + 4}>
                {gridValue}
              </text>
            </g>
          );
        })}

        <line className="graph-baseline" x1={padding} y1={height - padding} x2={width - padding} y2={height - padding} />
        <polyline className="graph-line" fill="none" points={primaryCoordinates.map((point) => `${point.x},${point.y}`).join(" ")} />
        {secondaryCoordinates.length > 0 ? (
          <polyline className="graph-line graph-line--secondary" fill="none" points={secondaryCoordinates.map((point) => `${point.x},${point.y}`).join(" ")} />
        ) : null}

        {primaryCoordinates.map((point, index) => (
          <g key={`primary-${index}`}>
            <circle className="graph-point" cx={point.x} cy={point.y} r="3" />
            {index === primaryCoordinates.length - 1 ? (
              <text className="graph-value" x={point.x} y={Math.max(16, point.y - 10)}>
                {valueFormatter(latestPoint?.[primaryKey])}
              </text>
            ) : null}
          </g>
        ))}

        {secondaryCoordinates.map((point, index) => (
          <circle key={`secondary-${index}`} className="graph-point graph-point--secondary" cx={point.x} cy={point.y} r="2.8" />
        ))}

        {chartPoints.map((point, index) => {
          const coordinate = primaryCoordinates[index];

          return (
            <text key={`label-${index}`} className="graph-label" x={coordinate.x} y={height - 10}>
              {formatBucketLabel(point?.[latestTimestampKey])}
            </text>
          );
        })}
      </svg>

      <div className="graph-legend">
        <span>
          <strong>{primaryLabel}</strong>
          {valueFormatter(latestPoint?.[primaryKey])}
        </span>
        {secondaryKey ? (
          <span>
            <strong>{secondaryLabel}</strong>
            {valueFormatter(latestPoint?.[secondaryKey])}
          </span>
        ) : null}
        <span>
          <strong>Latest</strong>
          {formatDateTime(latestPoint?.[latestTimestampKey])}
        </span>
      </div>
    </article>
  );
}

export function BarTrendPanel({ title, subtitle, points, valueKey, valueLabel, valueFormatter = (value) => formatNumber(value, 0, "0"), latestTimestampKey = "bucketStart" }) {
  const chartPoints = Array.isArray(points) ? points.slice(-8) : [];

  if (chartPoints.length === 0) {
    return (
      <article className="chart-shell graph-panel">
        <p className="eyebrow">Trend panel</p>
        <h3>{title}</h3>
        {subtitle ? <p className="muted">{subtitle}</p> : null}
        <div className="empty-state">
          <p className="eyebrow">No trend points</p>
          <p className="muted">This panel will populate after the next reporting bucket is available.</p>
        </div>
      </article>
    );
  }

  const width = 560;
  const height = 220;
  const padding = 30;
  const innerWidth = width - padding * 2;
  const innerHeight = height - padding * 2;
  const slotWidth = innerWidth / chartPoints.length;
  const barWidth = Math.min(42, slotWidth * 0.55);
  const maxValue = Math.max(...chartPoints.map((point) => toNumber(point?.[valueKey])), 1);
  const latestPoint = chartPoints.at(-1);

  return (
    <article className="chart-shell graph-panel">
      <p className="eyebrow">Trend panel</p>
      <h3>{title}</h3>
      {subtitle ? <p className="muted">{subtitle}</p> : null}

      <svg className="graph-svg" viewBox={`0 0 ${width} ${height}`} role="img" aria-label={`${title} bar trend`}>
        <line className="graph-baseline" x1={padding} y1={height - padding} x2={width - padding} y2={height - padding} />
        {chartPoints.map((point, index) => {
          const value = toNumber(point?.[valueKey]);
          const barHeight = value > 0 ? Math.max((value / maxValue) * innerHeight, 10) : 0;
          const x = padding + slotWidth * index + (slotWidth - barWidth) / 2;
          const y = height - padding - barHeight;

          return (
            <g key={`${latestTimestampKey}-${index}`}>
              <rect className="graph-bar" x={x} y={y} width={barWidth} height={barHeight} rx="10" />
              {index === chartPoints.length - 1 ? (
                <text className="graph-value" x={x + barWidth / 2} y={Math.max(16, y - 10)}>
                  {valueFormatter(value)}
                </text>
              ) : null}
              <text className="graph-label" x={x + barWidth / 2} y={height - 10}>
                {formatBucketLabel(point?.[latestTimestampKey])}
              </text>
            </g>
          );
        })}
      </svg>

      <div className="graph-legend">
        <span>
          <strong>{valueLabel}</strong>
          {valueFormatter(latestPoint?.[valueKey])}
        </span>
        <span>
          <strong>Latest</strong>
          {formatDateTime(latestPoint?.[latestTimestampKey])}
        </span>
      </div>
    </article>
  );
}

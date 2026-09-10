import { ArrowUpRight } from "lucide-react";
import { formatValue } from "../../../shared/analytics/ChartTooltip";

interface StatTileProps {
  label: string;
  value: unknown;
  unit?: string;
  hint?: string;
  /** Reserved status colour, and never the only signal - the label always says what it is. */
  tone?: "neutral" | "warning" | "danger";
  /** When present the tile becomes a button into the workspace that explains the number. */
  onClick?: () => void;
}

/**
 * A single headline number. Not a chart on purpose: one value reads faster as text, and a
 * one-bar chart is the classic way to make a number harder to read than it needs to be.
 */
export function StatTile({ label, value, unit, hint, tone = "neutral", onClick }: StatTileProps) {
  const toneClass =
    tone === "danger" ? "text-state-danger" : tone === "warning" ? "text-state-warning" : "text-ink";

  const body = (
    <>
      <p className="eyebrow-label flex items-center gap-1">
        {label}
        {onClick ? (
          <ArrowUpRight className="h-3 w-3 opacity-0 transition group-hover:opacity-100" aria-hidden="true" />
        ) : null}
      </p>
      <p className={`font-display text-2xl font-semibold tabular-nums ${toneClass}`}>
        {formatValue(value)}
        {unit ? <span className="ml-0.5 text-sm font-normal text-ink-muted">{unit}</span> : null}
      </p>
      {hint ? <p className="mt-0.5 text-[10px] text-ink-muted">{hint}</p> : null}
    </>
  );

  // A real button, not a div with a handler: this way it is in the tab order and answers Enter
  // and Space without any of that being reimplemented here.
  return onClick ? (
    <button type="button" onClick={onClick}
            className="card group p-3 text-left transition hover:border-line-strong focus:border-line-strong focus:outline-none">
      {body}
    </button>
  ) : (
    <div className="card p-3">{body}</div>
  );
}

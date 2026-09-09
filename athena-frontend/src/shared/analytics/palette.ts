/**
 * Chart palette for Athena's dark surface (#111216).
 *
 * Derived from the product accent tokens in styles/main.css, then snapped into the OKLCH
 * lightness band the dark surface needs (L 0.48-0.67) and validated - not eyeballed. The
 * validator reports, for this order: lightness band PASS, chroma floor PASS, worst adjacent
 * CVD separation dE 11.0 (deutan), normal-vision floor dE 20.3, contrast >= 3:1 PASS.
 *
 * The ORDER is part of the result, not decoration. Violet and indigo are only dE 3.7 apart
 * under protanopia, so they must never land next to each other; this sequence keeps them at
 * positions 4 and 7. Reordering or inserting a hue invalidates the check - re-run
 * `validate_palette.js` if you change it.
 *
 * Assign in fixed order and never cycle. An eighth series is not a generated hue: fold the
 * tail into "Other", facet into small multiples, or add a second encoding.
 */
export const SERIES_COLORS = [
  "#5878fb", // blue
  "#bc7300", // amber
  "#00a077", // green
  "#ab56e6", // violet
  "#d5565e", // coral
  "#0097c4", // cyan
  "#8067ff", // indigo
] as const;

export const MAX_SERIES = SERIES_COLORS.length;

/** Colour follows the entity, never its rank, so a filter cannot repaint the survivors. */
export function seriesColor(key: string, allKeys: readonly string[]): string {
  const index = allKeys.indexOf(key);
  return SERIES_COLORS[(index < 0 ? 0 : index) % SERIES_COLORS.length];
}

/**
 * Status colours are reserved and never reused as "series 8". They always ship with a label,
 * never colour alone.
 */
export const STATUS_COLORS = {
  good: "#44d4a8",
  warning: "#ffbe5c",
  serious: "#ffb248",
  critical: "#ff7b81",
} as const;

/** Recessive chrome - grid and axes must sit behind the data, not compete with it. */
export const CHART_INK = {
  grid: "rgba(255,255,255,0.06)",
  axis: "rgba(255,255,255,0.28)",
  label: "#aeb6c6",
  surface: "#111216",
} as const;

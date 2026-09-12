/**
 * What each route's URL means, in one place.
 *
 * The top bar has to render a breadcrumb and a set of filter chips for whichever page is
 * mounted, and there are two ways to arrange that: pages publish their state upward through a
 * context, or the bar derives it from the URL. This is the second.
 *
 * The URL is already the single source of truth for both filters and drill position, so deriving
 * from it needs no extra state, cannot fall out of step with the page, and - the practical
 * reason - avoids pages writing into a shared store while rendering, which is the usual way this
 * shape turns into an update loop.
 *
 * The defaults live here rather than in the pages so that the param schema has one owner. Pages
 * import them; nothing here imports a page.
 */

export type FilterDefaults = Record<string, string | string[]>;

export const HOME_DEFAULTS = {
  range: "30d", from: "", to: "", version: "", project: "", team: [] as string[],
};

export const CYCLES_DEFAULTS = {
  range: "30d", from: "", to: "",
  version: "", project: "", team: [] as string[], status: "", search: "",
  cycle: "", item: "", by: "priority", drill: "", drillValue: "",
};

export const PERF_DEFAULTS = {
  range: "7d", from: "", to: "",
  environment: "", project: "", actionType: "", search: "",
  action: "", target: "",
};

export const CORRELATION_DEFAULTS = {
  range: "24h", from: "", to: "",
  repository: [] as string[], author: "", pipeline: "", version: "",
  environment: "", namespace: "", app: "", status: "", search: "",
};

/** Params that select a chart's view rather than narrowing data - never counted as filters. */
export const DISPLAY_ONLY = ["by"] as const;

interface RouteSpec {
  title: string;
  defaults: FilterDefaults;
  /** Params that are drill steps, outermost first. Each becomes a breadcrumb. */
  drill: string[];
  /** Filter params, with the label the chip should carry. */
  chips: Record<string, string>;
}

const TIME_CHIP = { range: "Window" };

export const ROUTES: Record<string, RouteSpec> = {
  "/": {
    title: "Overview",
    defaults: HOME_DEFAULTS,
    drill: [],
    chips: { ...TIME_CHIP, version: "Version", project: "Project", team: "Team" },
  },
  "/test-cycles": {
    title: "Test cycles",
    defaults: CYCLES_DEFAULTS,
    drill: ["cycle", "item"],
    chips: {
      ...TIME_CHIP, version: "Version", project: "Project", team: "Team",
      status: "Status", search: "Search",
    },
  },
  "/performance": {
    title: "Performance",
    defaults: PERF_DEFAULTS,
    drill: ["action", "target"],
    chips: {
      ...TIME_CHIP, environment: "Environment", project: "Project",
      actionType: "Type", search: "Action",
    },
  },
  "/correlation": {
    title: "Change & run",
    defaults: CORRELATION_DEFAULTS,
    drill: [],
    chips: {
      ...TIME_CHIP, repository: "Repository", author: "Author", pipeline: "Pipeline",
      version: "Version", environment: "Environment", namespace: "Namespace",
      app: "App", status: "Outcome", search: "Search",
    },
  },
  "/agent": { title: "Ask Athena", defaults: {}, drill: [], chips: {} },
};

export interface Crumb {
  label: string;
  /** Absent on the last crumb, which is where we already are. */
  to?: string;
}

export interface Chip {
  key: string;
  label: string;
  value: string;
}

/**
 * Params that are not filters: where the reader is (drill position, an open dialog) and which
 * view a chart is showing. They belong in the URL and none of them narrows the data, so counting
 * them would make "Clear 4 filters" claim a view is narrower than it is.
 */
export function notFilters(pathname: string): string[] {
  const spec = ROUTES[pathname];
  return [...DISPLAY_ONLY, ...(spec?.drill ?? []), "drill", "drillValue"];
}

export interface RouteView {
  title: string;
  crumbs: Crumb[];
  chips: Chip[];
}

/** Everything the top bar needs, read from the current location. */
export function describe(pathname: string, search: string): RouteView | null {
  const spec = ROUTES[pathname];
  if (!spec) return null;

  const params = new URLSearchParams(search);
  const active = spec.drill.filter((key) => params.get(key));

  const crumbs: Crumb[] = [{
    label: spec.title,
    // The root crumb keeps the filters and drops the drill, so it returns to the list rather
    // than resetting the view the reader had set up.
    to: active.length > 0 ? pathname + withoutDrill(params, spec.drill) : undefined,
  }];

  active.forEach((key, index) => {
    const deeper = active.slice(index + 1);
    crumbs.push({
      label: params.get(key)!,
      to: deeper.length > 0 ? pathname + withoutDrill(params, deeper) : undefined,
    });
  });

  const chips: Chip[] = [];
  for (const [key, label] of Object.entries(spec.chips)) {
    const raw = params.get(key);
    if (!raw) continue;
    const fallback = spec.defaults[key];
    const fallbackText = Array.isArray(fallback) ? fallback.join(",") : fallback;
    if (raw === fallbackText) continue;
    chips.push({
      key,
      label,
      // A custom window is two more params; the chip says so rather than showing an ISO instant.
      value: key === "range" && raw === "custom" ? describeCustom(params) : raw.replace(/,/g, ", "),
    });
  }

  return { title: spec.title, crumbs, chips };
}

/** Removing a chip also clears whatever else that filter is made of. */
export function chipParams(key: string): string[] {
  return key === "range" ? ["range", "from", "to"] : [key];
}

function withoutDrill(params: URLSearchParams, keys: string[]): string {
  const next = new URLSearchParams(params);
  for (const key of keys) next.delete(key);
  // A drill dialog belongs to the level it was opened from, so it closes with it.
  next.delete("drill");
  next.delete("drillValue");
  const text = next.toString();
  return text ? `?${text}` : "";
}

function describeCustom(params: URLSearchParams): string {
  const from = params.get("from");
  const to = params.get("to");
  if (!from || !to) return "custom";
  const short = (iso: string) => {
    const date = new Date(iso);
    return Number.isNaN(date.getTime()) ? iso : date.toLocaleDateString();
  };
  return from && to && short(from) === short(to) ? short(from) : `${short(from)} – ${short(to)}`;
}

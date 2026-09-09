/** Shapes returned by athena-boot-analytics. Mirrors the generated specs and QueryResult. */

export type ParamKind = "scalar" | "list" | "instant" | "operator";

export interface ParamSpec {
  name: string;
  kind: ParamKind;
  /** For `operator` params, the only values the server will accept. */
  allowed: string[];
}

export interface QuerySummary {
  id: string;
  title: string;
  views: string[];
  tables: string[];
  params: ParamSpec[];
  dashboards: string[];
}

export interface QueryColumn {
  name: string;
  type: string;
}

export interface ViewFreshness {
  view: string;
  /** Null when unknown - a plain view, or a refresh that has not run or did not succeed. */
  refreshedAt: string | null;
  materialized: boolean;
}

export interface QueryResult {
  queryId: string;
  columns: QueryColumn[];
  rows: unknown[][];
  /** True when the row cap cut the result short, so the panel can say so. */
  truncated: boolean;
  views: string[];
  freshness: ViewFreshness[];
}

export type Viz =
  | "timeseries" | "bar" | "barGauge" | "pie" | "table"
  | "stat" | "gauge" | "text" | "row" | "heatmap" | "stateTimeline";

export interface PanelQuery {
  queryId: string;
  params: string[];
}

export interface PanelSpec {
  id: number;
  title: string;
  viz: Viz;
  grid: { x: number; y: number; w: number; h: number };
  description?: string;
  queries?: PanelQuery[];
  /** Present when the panel cannot be rendered, with the reason. */
  unsupported?: string;
  content?: string;
  mode?: string;
  collapsed?: boolean;
  display?: {
    unit?: string;
    decimals?: number;
    min?: number;
    max?: number;
    thresholds?: unknown;
  };
  options?: Record<string, unknown>;
}

export interface VariableSpec {
  name: string;
  type: "query" | "custom" | "constant" | "interval" | string;
  label: string;
  multi: boolean;
  includeAll: boolean;
  options?: string[];
  value?: string;
  queryId?: string;
  unsupported?: string;
}

export interface DashboardSpec {
  id: string;
  title: string;
  variant: string;
  source: string;
  variables: VariableSpec[];
  panels: PanelSpec[];
}

export interface DashboardSummary {
  id: string;
  title: string;
  variant: string;
  panelCount: number;
  renderable: number;
}

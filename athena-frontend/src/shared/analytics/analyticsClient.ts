import type { DashboardSpec, DashboardSummary, QueryResult, QuerySummary } from "./types";

const ANALYTICS_ROOT = "/analytics";

export class AnalyticsError extends Error {
  readonly status: number;
  constructor(message: string, status: number) {
    super(message);
    this.name = "AnalyticsError";
    this.status = status;
  }
}

async function request<T>(path: string, init?: RequestInit): Promise<T> {
  const response = await fetch(`${ANALYTICS_ROOT}${path}`, {
    ...init,
    headers: { Accept: "application/json", ...(init?.headers ?? {}) },
  });

  if (!response.ok) {
    // The service answers 400 with {"error": "..."} for anything the caller got wrong;
    // surfacing that beats a generic failure message in a panel.
    let detail = `Request failed with ${response.status}`;
    try {
      const body = await response.json();
      if (body && typeof body.error === "string") detail = body.error;
    } catch {
      /* non-JSON error body */
    }
    throw new AnalyticsError(detail, response.status);
  }
  return response.json() as Promise<T>;
}

export const listDashboards = () => request<DashboardSummary[]>("/dashboards");

export const getDashboard = (id: string) => request<DashboardSpec>(`/dashboards/${encodeURIComponent(id)}`);

export const listQueries = () => request<QuerySummary[]>("/queries");

/**
 * `signal` is not optional in practice - a caller that drops the result still holds a browser
 * connection until the server answers, and the browser only opens six per origin. A page with
 * seven panels that navigates away twice can therefore starve the panels that come next.
 */
export const runQuery = (
  queryId: string,
  params: Record<string, unknown>,
  signal?: AbortSignal,
) =>
  request<QueryResult>(`/queries/${encodeURIComponent(queryId)}/run`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(params),
    signal,
  });

/** True for the DOMException fetch raises when a request is aborted, which is never an error. */
export function isAbort(error: unknown): boolean {
  return error instanceof DOMException && error.name === "AbortError";
}

/**
 * True for an abort the caller raised as a deadline rather than as cleanup. The two arrive
 * through the same channel and mean opposite things - one is "never mind", the other is "the
 * service is not answering" - so they are told apart by the reason's name.
 */
export function isTimeout(error: unknown): boolean {
  return error instanceof DOMException && error.name === "TimeoutError";
}

/** Column-oriented view of a result, which is what the chart components want. */
export function toRecords(result: QueryResult): Record<string, unknown>[] {
  return result.rows.map((row) => {
    const record: Record<string, unknown> = {};
    result.columns.forEach((column, index) => {
      record[column.name] = row[index];
    });
    return record;
  });
}

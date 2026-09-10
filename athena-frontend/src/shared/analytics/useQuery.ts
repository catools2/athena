import { useCallback, useEffect, useState } from "react";
import { isAbort, isTimeout, runQuery } from "./analyticsClient";
import type { QueryResult } from "./types";

/**
 * How long a panel waits before calling the service unreachable.
 *
 * Matched to the analytics service's own statement timeout: a query that the database is still
 * running gets answered - or refused - within that budget, so anything past it is the service not
 * answering at all rather than a slow query. Without a ceiling that case renders as a spinner
 * that never resolves, which looks like a broken widget and gives the reader nothing to act on.
 */
const TIMEOUT_MS = 30_000;

export interface QueryState {
  result: QueryResult | null;
  error: string | null;
  loading: boolean;
  /** Re-runs the query. Present so a failed panel can offer a way out that is not a page reload. */
  retry: () => void;
}

/**
 * Run a registry query and track its lifecycle. Skips entirely when `enabled` is false.
 *
 * The request is aborted on cleanup, not merely ignored. A flag that discards a late response
 * still leaves the request running, and a browser opens only six connections per origin: leaving
 * a page with seven panels twice is enough to queue everything the next page asks for behind
 * work whose answer nobody will read. React's StrictMode doubles that in development by mounting
 * every effect twice, so it shows up while developing well before it shows up in production.
 */
export function useQuery(
  queryId: string,
  params: Record<string, unknown>,
  enabled = true,
): QueryState {
  const [state, setState] = useState<{ result: QueryResult | null; error: string | null; loading: boolean }>({
    result: null, error: null, loading: enabled,
  });
  const [attempt, setAttempt] = useState(0);
  const key = JSON.stringify(params);

  const retry = useCallback(() => setAttempt((n) => n + 1), []);

  useEffect(() => {
    if (!enabled) {
      setState((current) => (current.loading ? { ...current, loading: false } : current));
      return;
    }

    const controller = new AbortController();
    const expiry = setTimeout(
      () => controller.abort(new DOMException(`No answer in ${TIMEOUT_MS / 1000}s`, "TimeoutError")),
      TIMEOUT_MS);

    // The previous result stays on screen while the next one loads. Blanking it would make every
    // filter keystroke flash the panel through an empty state on its way to the new data.
    setState((current) => ({ ...current, loading: true }));

    runQuery(queryId, JSON.parse(key), controller.signal)
      .then((result) => setState({ result, error: null, loading: false }))
      .catch((error: unknown) => {
        if (isTimeout(error) || isTimeout(controller.signal.reason)) {
          setState({
            result: null, loading: false,
            error: "The analytics service did not answer in time. It may be restarting.",
          });
          return;
        }
        // An abort is this hook's own cleanup, not a failure: the component is either gone or
        // already loading something newer, and either way it must not be told the query broke.
        if (isAbort(error) || controller.signal.aborted) return;
        setState({ result: null, error: (error as Error).message, loading: false });
      })
      .finally(() => clearTimeout(expiry));

    return () => {
      clearTimeout(expiry);
      controller.abort();
    };
  }, [queryId, key, enabled, attempt]);

  return { ...state, retry };
}

import { useEffect, useState } from "react";
import { runQuery } from "../../../shared/analytics/analyticsClient";
import type { QueryResult } from "../../../shared/analytics/types";

/** Run a registry query and track its lifecycle. Skips entirely when `enabled` is false. */
export function useQuery(queryId: string, params: Record<string, unknown>, enabled = true) {
  const [result, setResult] = useState<QueryResult | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(enabled);
  const key = JSON.stringify(params);

  useEffect(() => {
    if (!enabled) { setLoading(false); return; }
    let cancelled = false;
    setLoading(true);
    runQuery(queryId, JSON.parse(key))
      .then((data) => { if (!cancelled) { setResult(data); setError(null); } })
      .catch((e: Error) => { if (!cancelled) { setError(e.message); setResult(null); } })
      .finally(() => { if (!cancelled) setLoading(false); });
    return () => { cancelled = true; };
  }, [queryId, key, enabled]);

  return { result, error, loading };
}

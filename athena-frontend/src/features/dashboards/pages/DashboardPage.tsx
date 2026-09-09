import { useCallback, useEffect, useMemo, useState } from "react";
import { Link, useParams, useSearchParams } from "react-router-dom";
import { ArrowLeft } from "lucide-react";
import { getDashboard } from "../../../shared/analytics/analyticsClient";
import type { DashboardSpec } from "../../../shared/analytics/types";
import { DashboardRenderer } from "../components/DashboardRenderer";
import { DEFAULT_RANGE, FilterBar, defaultParams } from "../components/FilterBar";

export function DashboardPage() {
  const { id = "" } = useParams();
  const [spec, setSpec] = useState<DashboardSpec | null>(null);
  const [error, setError] = useState<string | null>(null);
  // Filters live in the URL so a filtered view is linkable and survives a reload.
  const [searchParams, setSearchParams] = useSearchParams();

  useEffect(() => {
    setSpec(null);
    setError(null);
    getDashboard(id).then(setSpec).catch((e: Error) => setError(e.message));
  }, [id]);

  const params = useMemo(
    () => (spec ? defaultParams(spec, searchParams) : {}),
    [spec, searchParams],
  );

  const onChange = useCallback(
    (name: string, value: string | string[]) => {
      const next = new URLSearchParams(searchParams);
      const encoded = Array.isArray(value) ? value.join(",") : value;
      if (encoded) next.set(name, encoded);
      else next.delete(name);
      setSearchParams(next, { replace: true });
    },
    [searchParams, setSearchParams],
  );

  if (error) {
    return <p className="card m-6 p-4 text-sm text-state-danger">{error}</p>;
  }
  if (!spec) {
    return <p className="m-6 text-sm text-ink-muted">Loading dashboard…</p>;
  }

  return (
    <div className="animate-fade-in p-6">
      <header className="mb-4">
        <Link to="/dashboards" className="mb-2 inline-flex items-center gap-1 text-[11px] text-ink-muted hover:text-ink">
          <ArrowLeft className="h-3 w-3" aria-hidden="true" />
          All dashboards
        </Link>
        <h1 className="font-display text-xl font-semibold text-ink">{spec.title}</h1>
      </header>

      <FilterBar
        spec={spec}
        params={params}
        range={searchParams.get("range") ?? DEFAULT_RANGE}
        onChange={onChange}
      />
      <DashboardRenderer spec={spec} params={params} />
    </div>
  );
}

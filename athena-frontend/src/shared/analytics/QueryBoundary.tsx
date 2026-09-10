import { Loader2, RotateCw } from "lucide-react";
import type { QueryState } from "./useQuery";
import type { QueryResult } from "./types";

/**
 * The four states a panel can be in, told apart.
 *
 * Every panel on these pages used to render `result && rows.length > 0 ? chart : empty`, which
 * collapses three different situations into one sentence: a query still in flight, a query that
 * failed, and a window that genuinely holds no data all said "Nothing recorded". The first is a
 * panel that needs another moment, the second is a panel that is broken, and only the third is
 * an answer - and reporting the first two as the third is how a dashboard quietly lies.
 *
 * A refetch keeps the previous content on screen and dims it, so changing a filter does not
 * flash every panel through a loading state on the way to the next answer.
 */
export function QueryBoundary({
  query, children, empty = "Nothing recorded in this window.", className = "",
}: {
  query: QueryState;
  children: (result: QueryResult) => React.ReactNode;
  empty?: string;
  className?: string;
}) {
  if (query.error) {
    return (
      <Centered className={className}>
        <div className="px-4 text-center">
          <p className="text-xs text-state-danger">{query.error}</p>
          {/* A failed panel that offers only a page reload throws away every other panel that
              did load, and the filters the reader had set. */}
          <button
            type="button"
            onClick={query.retry}
            className="mt-2 inline-flex items-center gap-1 rounded-md border border-line px-2 py-1 text-[11px] text-ink-muted transition hover:border-line-strong hover:text-ink"
          >
            <RotateCw className="h-3 w-3" aria-hidden="true" />
            Try again
          </button>
        </div>
      </Centered>
    );
  }

  if (!query.result) {
    return query.loading ? (
      <Centered className={className}>
        <p className="flex items-center gap-1.5 text-xs text-ink-muted">
          <Loader2 className="h-3 w-3 animate-spin" aria-hidden="true" />
          Loading…
        </p>
      </Centered>
    ) : (
      <Centered className={className}>
        <p className="text-xs text-ink-muted">{empty}</p>
      </Centered>
    );
  }

  if (query.result.rows.length === 0) {
    return (
      <Centered className={className}>
        <p className="text-xs text-ink-muted">{empty}</p>
      </Centered>
    );
  }

  return (
    <div className={`h-full ${query.loading ? "opacity-60 transition-opacity" : ""} ${className}`}
         aria-busy={query.loading || undefined}>
      {children(query.result)}
    </div>
  );
}

function Centered({ children, className = "" }: { children: React.ReactNode; className?: string }) {
  return (
    <div className={`flex h-full min-h-[120px] items-center justify-center ${className}`}
         role="status" aria-live="polite">
      {children}
    </div>
  );
}

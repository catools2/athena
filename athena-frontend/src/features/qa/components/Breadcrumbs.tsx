import { ChevronRight } from "lucide-react";

export interface Crumb {
  label: string;
  onClick?: () => void;
}

/**
 * The drill path, always visible. Every level stays clickable so a wrong turn costs one click
 * rather than a reset back to the top.
 */
export function Breadcrumbs({ crumbs }: { crumbs: Crumb[] }) {
  return (
    <nav className="mb-3 flex flex-wrap items-center gap-1 text-[11px]" aria-label="Drill path">
      {crumbs.map((crumb, i) => {
        const last = i === crumbs.length - 1;
        return (
          <span key={i} className="flex items-center gap-1">
            {i > 0 ? <ChevronRight className="h-3 w-3 text-ink-muted/60" aria-hidden="true" /> : null}
            {last || !crumb.onClick ? (
              <span className="font-medium text-ink" aria-current={last ? "page" : undefined}>
                {crumb.label}
              </span>
            ) : (
              <button type="button" onClick={crumb.onClick} className="text-ink-muted hover:text-ink hover:underline">
                {crumb.label}
              </button>
            )}
          </span>
        );
      })}
    </nav>
  );
}

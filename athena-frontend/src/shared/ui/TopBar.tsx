import { ChevronLeft, ChevronRight, RotateCcw, X } from "lucide-react";
import { Link, useLocation, useNavigate, useSearchParams } from "react-router-dom";
import { chipParams, describe } from "./navigation";
import { useHistoryPosition } from "./useHistoryPosition";

/**
 * One bar, above every page: where you are, what you are filtered to, and the way back.
 *
 * All three come from the URL rather than from the page below, so the bar cannot disagree with
 * what is rendered and a pasted link produces the same bar as arriving by clicking.
 */
export function TopBar() {
  const location = useLocation();
  const navigate = useNavigate();
  const [params, setParams] = useSearchParams();
  const { canGoBack, canGoForward } = useHistoryPosition();

  const view = describe(location.pathname, location.search);

  function removeChip(key: string) {
    const next = new URLSearchParams(params);
    for (const param of chipParams(key)) next.delete(param);
    setParams(next, { replace: true });
  }

  return (
    <nav className="mb-4 flex flex-wrap items-center gap-x-3 gap-y-2 rounded-lg border border-line bg-surface-muted/40 px-2 py-1.5"
         aria-label="History and location">
      <span className="flex items-center gap-0.5">
        <HistoryButton label="Back" disabled={!canGoBack} onClick={() => navigate(-1)}>
          <ChevronLeft className="h-4 w-4" />
        </HistoryButton>
        <HistoryButton label="Forward" disabled={!canGoForward} onClick={() => navigate(1)}>
          <ChevronRight className="h-4 w-4" />
        </HistoryButton>
      </span>

      {view ? (
        <ol className="m-0 flex list-none flex-wrap items-center gap-1 p-0 text-[11px]">
          {view.crumbs.map((crumb, index) => {
            const last = index === view.crumbs.length - 1;
            return (
              <li key={`${crumb.label}-${index}`} className="flex items-center gap-1">
                {index > 0 ? (
                  <ChevronRight className="h-3 w-3 shrink-0 text-ink-muted/50" aria-hidden="true" />
                ) : null}
                {crumb.to && !last ? (
                  <Link to={crumb.to} className="text-ink-muted hover:text-ink hover:underline">
                    {crumb.label}
                  </Link>
                ) : (
                  <span className="font-medium text-ink" aria-current={last ? "page" : undefined}>
                    {crumb.label}
                  </span>
                )}
              </li>
            );
          })}
        </ol>
      ) : null}

      {view && view.chips.length > 0 ? (
        <>
          <span className="h-4 w-px bg-line" aria-hidden="true" />
          <ul className="m-0 flex list-none flex-wrap items-center gap-1 p-0" aria-label="Active filters">
            {view.chips.map((chip) => (
              <li key={chip.key}>
                <span className="flex items-center gap-1 rounded-full border border-line bg-surface/80 py-0.5 pl-2 pr-1 text-[10px] text-ink-muted">
                  <span className="text-ink-muted/70">{chip.label}</span>
                  <span className="max-w-[14rem] truncate text-ink">{chip.value}</span>
                  <button type="button" onClick={() => removeChip(chip.key)}
                          aria-label={`Remove the ${chip.label} filter`}
                          className="rounded-full p-0.5 text-ink-muted transition hover:bg-white/10 hover:text-ink">
                    <X className="h-3 w-3" />
                  </button>
                </span>
              </li>
            ))}
          </ul>
          <button
            type="button"
            onClick={() => setParams(new URLSearchParams(), { replace: true })}
            className="ml-auto flex items-center gap-1 rounded-md px-2 py-1 text-[11px] text-ink-muted transition hover:text-ink"
          >
            <RotateCcw className="h-3 w-3" aria-hidden="true" />
            Clear all
          </button>
        </>
      ) : null}
    </nav>
  );
}

function HistoryButton({
  label, disabled, onClick, children,
}: {
  label: string; disabled: boolean; onClick: () => void; children: React.ReactNode;
}) {
  return (
    <button
      type="button"
      onClick={onClick}
      disabled={disabled}
      aria-label={label}
      title={label}
      // Disabled rather than hidden: a control that disappears is harder to aim at than one that
      // greys out, and its absence reads as a bug rather than as "there is nowhere to go".
      className="rounded-md p-1 text-ink-muted transition enabled:hover:bg-white/[0.06] enabled:hover:text-ink disabled:cursor-not-allowed disabled:opacity-30"
    >
      {children}
    </button>
  );
}

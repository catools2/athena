import { useEffect, useRef } from "react";
import { X } from "lucide-react";

/**
 * The rows behind a mark, over the page rather than instead of it.
 *
 * Built on the native <dialog> with showModal(), which is not laziness: it brings the focus
 * trap, the inert background, the Escape handling and the top-layer stacking with it. Every one
 * of those reimplemented over a positioned div is a thing to get wrong, and the usual result is
 * a modal you can tab out of behind.
 *
 * A dialog rather than a page because the drill is a detour. The reader is asking "who is in
 * that bar", not leaving the chart - and a page transition would cost them the filters, the
 * scroll position and the chart they were reading.
 */
export function DetailDialog({
  open, title, subtitle, onClose, children,
}: {
  open: boolean;
  title: string;
  subtitle?: string;
  onClose: () => void;
  children: React.ReactNode;
}) {
  const ref = useRef<HTMLDialogElement>(null);

  useEffect(() => {
    const dialog = ref.current;
    if (!dialog) return;
    if (open && !dialog.open) dialog.showModal();
    if (!open && dialog.open) dialog.close();
  }, [open]);

  useEffect(() => {
    const dialog = ref.current;
    if (!dialog) return;
    // Escape closes the element directly, without React knowing; this keeps the caller's state
    // in step so reopening the same drill works rather than appearing to do nothing.
    const sync = () => onClose();
    dialog.addEventListener("close", sync);
    return () => dialog.removeEventListener("close", sync);
  }, [onClose]);

  return (
    <dialog
      ref={ref}
      aria-labelledby="detail-dialog-title"
      // Clicking the backdrop lands on the dialog element itself; anything inside is a descendant.
      onClick={(event) => { if (event.target === ref.current) onClose(); }}
      className="max-h-[80vh] w-[min(60rem,92vw)] rounded-lg border border-line bg-surface-base p-0 text-ink shadow-raised backdrop:bg-black/60"
    >
      <div className="flex max-h-[80vh] flex-col">
        <header className="flex items-start gap-3 border-b border-line px-4 py-3">
          <div className="min-w-0">
            <h2 id="detail-dialog-title" className="card-title truncate">{title}</h2>
            {subtitle ? <p className="mt-0.5 text-[11px] text-ink-muted">{subtitle}</p> : null}
          </div>
          <button
            type="button"
            onClick={onClose}
            aria-label="Close"
            className="ml-auto rounded p-1 text-ink-muted transition hover:bg-white/[0.06] hover:text-ink"
          >
            <X className="h-4 w-4" />
          </button>
        </header>
        <div className="min-h-[8rem] flex-1 overflow-auto">{children}</div>
      </div>
    </dialog>
  );
}

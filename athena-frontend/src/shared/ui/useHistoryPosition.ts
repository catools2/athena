import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

const HIGH_WATER = "athena.history.maxIndex";

interface Position {
  canGoBack: boolean;
  canGoForward: boolean;
}

/**
 * Where we are in the history stack, so Back and Forward can be disabled rather than dead.
 *
 * The browser does not expose the stack, but react-router's history layer stamps an `idx` into
 * `history.state` on every navigation, and that is a real position rather than a guess. Back is
 * available when idx > 0. Forward has no such tell - "is there anything ahead" is not
 * answerable - so the furthest index reached this session is remembered in sessionStorage and
 * compared against; going back lowers idx below the mark, which is exactly when Forward means
 * something.
 *
 * sessionStorage rather than a ref because a full page load resets a ref but not the stack: land
 * on a deep link, refresh, and idx survives while a ref would claim we were at the start.
 */
export function useHistoryPosition(): Position {
  const location = useLocation();
  const [position, setPosition] = useState<Position>({ canGoBack: false, canGoForward: false });

  useEffect(() => {
    const index = Number((window.history.state as { idx?: number } | null)?.idx ?? 0);

    let highest = index;
    try {
      highest = Math.max(index, Number(window.sessionStorage.getItem(HIGH_WATER) ?? 0));
      window.sessionStorage.setItem(HIGH_WATER, String(highest));
    } catch {
      // Private windows and blocked site data throw on access; the mark is a convenience, so
      // fall back to "no forward" rather than losing the back button with it.
    }

    setPosition({ canGoBack: index > 0, canGoForward: index < highest });
  }, [location.key, location.pathname, location.search]);

  return position;
}

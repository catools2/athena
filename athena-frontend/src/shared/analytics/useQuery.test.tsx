import { afterEach, describe, expect, it, vi } from "vitest";
import { act, fireEvent, render, screen, waitFor } from "@testing-library/react";
import { QueryBoundary } from "./QueryBoundary";
import { useQuery } from "./useQuery";
import type { QueryResult } from "./types";

afterEach(() => {
  vi.restoreAllMocks();
  vi.unstubAllGlobals();
});

function result(rows: unknown[][] = [[1]]): QueryResult {
  return {
    queryId: "q", columns: [{ name: "n", type: "int8" }], rows,
    truncated: false, views: [], freshness: [],
  };
}

/** A fetch that never settles, so a request is still in flight when the test acts. */
function pendingFetch() {
  const signals: AbortSignal[] = [];
  const fetchMock = vi.fn((_url: string, init?: RequestInit) => {
    signals.push(init!.signal!);
    return new Promise<Response>((_resolve, reject) => {
      init!.signal!.addEventListener("abort", () =>
        reject(new DOMException("The operation was aborted.", "AbortError")));
    });
  });
  vi.stubGlobal("fetch", fetchMock);
  return { signals, fetchMock };
}

function Probe({ params, enabled = true }: { params: Record<string, unknown>; enabled?: boolean }) {
  const query = useQuery("q", params, enabled);
  return (
    <QueryBoundary query={query} empty="nothing recorded">
      {(rows) => <p>rows: {rows.rows.length}</p>}
    </QueryBoundary>
  );
}

describe("useQuery", () => {
  it("aborts the in-flight request when the component goes away", async () => {
    const { signals } = pendingFetch();

    const view = render(<Probe params={{ a: 1 }} />);
    await waitFor(() => expect(signals).toHaveLength(1));
    expect(signals[0].aborted).toBe(false);

    // Navigating away must free the connection. A flag that merely ignores the response leaves
    // the request occupying one of the browser's six per-origin slots until the server answers,
    // which is what starves the panels on the page the reader just opened.
    view.unmount();
    expect(signals[0].aborted).toBe(true);
  });

  it("aborts the previous request when the parameters change", async () => {
    const { signals } = pendingFetch();

    const view = render(<Probe params={{ range: "7d" }} />);
    await waitFor(() => expect(signals).toHaveLength(1));

    view.rerender(<Probe params={{ range: "30d" }} />);
    await waitFor(() => expect(signals).toHaveLength(2));

    expect(signals[0].aborted).toBe(true);
    expect(signals[1].aborted).toBe(false);
  });

  it("does not report its own abort as a failure", async () => {
    const { signals } = pendingFetch();

    const view = render(<Probe params={{ a: 1 }} />);
    await waitFor(() => expect(signals).toHaveLength(1));
    view.rerender(<Probe params={{ a: 2 }} />);

    // The rejection from the aborted request resolves on a later microtask; if it were treated
    // as an error it would overwrite the state of the request that replaced it.
    await new Promise((resolve) => setTimeout(resolve, 0));
    expect(screen.queryByText(/aborted/i)).toBeNull();
    expect(screen.getByText("Loading…")).toBeInTheDocument();
  });
});

describe("QueryBoundary", () => {
  it("says it is loading rather than claiming there is no data", async () => {
    pendingFetch();
    render(<Probe params={{ a: 1 }} />);

    // The distinction this asserts is the whole point: a panel still waiting for its answer
    // must not render the sentence that means "the window is genuinely empty".
    expect(await screen.findByText("Loading…")).toBeInTheDocument();
    expect(screen.queryByText("nothing recorded")).toBeNull();
  });

  it("surfaces a failure rather than claiming there is no data", async () => {
    vi.stubGlobal("fetch", vi.fn().mockResolvedValue({
      ok: false, status: 400, json: async () => ({ error: "column x does not exist" }),
    }));

    render(<Probe params={{ a: 1 }} />);

    expect(await screen.findByText("column x does not exist")).toBeInTheDocument();
    expect(screen.queryByText("nothing recorded")).toBeNull();
  });

  it("claims there is no data only when the query returned none", async () => {
    vi.stubGlobal("fetch", vi.fn().mockResolvedValue({
      ok: true, status: 200, json: async () => result([]),
    }));

    render(<Probe params={{ a: 1 }} />);

    expect(await screen.findByText("nothing recorded")).toBeInTheDocument();
  });

  it("renders the rows once they arrive", async () => {
    vi.stubGlobal("fetch", vi.fn().mockResolvedValue({
      ok: true, status: 200, json: async () => result([[1], [2]]),
    }));

    render(<Probe params={{ a: 1 }} />);

    expect(await screen.findByText(/rows: 2/)).toBeInTheDocument();
  });

  it("reports a service that never answers, instead of spinning forever", async () => {
    vi.useFakeTimers();
    const { signals } = pendingFetch();
    render(<Probe params={{ a: 1 }} />);
    await vi.waitFor(() => expect(signals).toHaveLength(1));

    // A wedged backend is indistinguishable from a slow one until a deadline says otherwise,
    // and an endless spinner is the symptom that reads to a user as "this widget is broken".
    // Wrapped in act: firing the deadline aborts the request, whose rejection then sets state.
    await act(async () => { await vi.advanceTimersByTimeAsync(31_000); });
    vi.useRealTimers();

    expect(await screen.findByText(/did not answer in time/)).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /try again/i })).toBeInTheDocument();
  });

  it("re-runs the query when the reader asks it to", async () => {
    const fetchMock = vi.fn()
      .mockResolvedValueOnce({ ok: false, status: 503, json: async () => ({ error: "service down" }) })
      .mockResolvedValue({ ok: true, status: 200, json: async () => result([[7]]) });
    vi.stubGlobal("fetch", fetchMock);

    render(<Probe params={{ a: 1 }} />);
    const again = await screen.findByRole("button", { name: /try again/i });

    // Wrapped: the retry starts a fetch that resolves on a later microtask, and the state
    // update it triggers belongs inside the same act scope as the click that caused it.
    await act(async () => { fireEvent.click(again); });

    expect(await screen.findByText(/rows: 1/)).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledTimes(2);
  });

  it("runs nothing at all while disabled", async () => {
    const { fetchMock } = pendingFetch();

    render(<Probe params={{ a: 1 }} enabled={false} />);

    await new Promise((resolve) => setTimeout(resolve, 0));
    expect(fetchMock).not.toHaveBeenCalled();
    expect(screen.getByText("nothing recorded")).toBeInTheDocument();
  });
});

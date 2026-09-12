import { describe, expect, it } from "vitest";
import { act, render, screen } from "@testing-library/react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useFilters } from "../analytics/filters";
import { chipParams, describe as describeRoute, CYCLES_DEFAULTS } from "./navigation";

describe("describe()", () => {
  it("builds a breadcrumb from the drill params, deepest last", () => {
    const view = describeRoute("/test-cycles", "?cycle=C1&item=DEMO-T15")!;
    expect(view.crumbs.map((c) => c.label)).toEqual(["Test cycles", "C1", "DEMO-T15"]);
    // Only the last crumb is where we already are, so only it has no link.
    expect(view.crumbs[2].to).toBeUndefined();
    expect(view.crumbs[0].to).toBe("/test-cycles");
  });

  it("keeps the filters when stepping back up the drill", () => {
    const view = describeRoute("/test-cycles", "?range=90d&cycle=C1&item=DEMO-T15")!;
    // Going back to the cycle must not silently widen the window the reader had chosen.
    expect(view.crumbs[1].to).toContain("range=90d");
    expect(view.crumbs[1].to).not.toContain("item=");
  });

  it("closes a drill dialog when the level it belongs to is left", () => {
    const view = describeRoute("/test-cycles", "?cycle=C1&drill=profile&drillValue=team|Beta|failed")!;
    expect(view.crumbs[0].to).not.toContain("drill");
  });

  it("shows active filters as chips and leaves defaults out", () => {
    const view = describeRoute("/test-cycles", "?range=30d&version=4.1&team=Beta,Gamma")!;
    const chips = Object.fromEntries(view.chips.map((c) => [c.label, c.value]));
    expect(chips).toEqual({ Version: "4.1", Team: "Beta, Gamma" });  // range=30d is the default
  });

  it("renders a custom window as dates rather than ISO instants", () => {
    const view = describeRoute(
      "/test-cycles",
      "?range=custom&from=2026-08-11T05:00:00.000Z&to=2026-08-12T04:59:59.999Z")!;
    const window = view.chips.find((c) => c.key === "range")!;
    expect(window.value).not.toContain("T05:00");
    expect(window.value).toMatch(/\d/);
  });

  it("clears every param a compound filter is made of", () => {
    expect(chipParams("range")).toEqual(["range", "from", "to"]);
    expect(chipParams("version")).toEqual(["version"]);
  });
});

function Probe() {
  const { values, set, go } = useFilters(CYCLES_DEFAULTS);
  return (
    <div>
      <span data-testid="cycle">{values.cycle || "-"}</span>
      <button onClick={() => set({ version: "4.1" })}>filter</button>
      <button onClick={() => go({ cycle: "C1" })}>drill</button>
    </div>
  );
}

describe("useFilters", () => {
  /**
   * The distinction this asserts is the entire point of having two writers: drilling used to
   * replace, so three levels of drill-down left no history and Back jumped out of the page.
   *
   * Measured against jsdom's real history rather than a MemoryRouter, because the length of the
   * stack is exactly the thing under test and MemoryRouter does not expose it.
   */
  function mount() {
    window.history.pushState({ idx: 0 }, "", "/test-cycles");
    return render(
      <BrowserRouter>
        <Routes>
          <Route path="/test-cycles" element={<Probe />} />
        </Routes>
      </BrowserRouter>);
  }

  it("a filter change replaces, leaving the stack the same depth", async () => {
    mount();
    const before = window.history.length;

    await act(async () => { screen.getByText("filter").click(); });

    expect(window.location.search).toContain("version=4.1");
    expect(window.history.length).toBe(before);
  });

  it("a drill pushes, so Back has somewhere to go", async () => {
    mount();
    const before = window.history.length;

    await act(async () => { screen.getByText("drill").click(); });

    expect(screen.getByTestId("cycle").textContent).toBe("C1");
    expect(window.history.length).toBe(before + 1);
  });
});

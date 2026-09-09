import { afterEach } from "vitest";
import { fireEvent, render, screen, waitFor, within } from "@testing-library/react";
import { App } from "./App";

afterEach(() => {
  vi.restoreAllMocks();
  vi.unstubAllGlobals();
  window.history.pushState({}, "", "/ui/overview");
});

describe("App shell", () => {
  it("renders the overview with focused workspace navigation", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/spec/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            specCount: 4,
            projectCount: 2,
            pathCount: 19,
            staleSpecCount: 1,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/spec/freshness")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            freshCount: 3,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/git/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 9,
            hostCount: 2,
            freshCount: 7,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:05:00.000Z",
          }),
        });
      }

      if (url.includes("/git/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              repositoryCount: 4,
              uniqueHostCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              repositoryCount: 5,
              uniqueHostCount: 2,
            },
          ],
        });
      }

      if (url.includes("/kube/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 6,
            namespaceCount: 2,
            nodeCount: 2,
            activeCount: 5,
            deletedCount: 1,
            latestSyncTime: "2026-05-12T23:10:00.000Z",
          }),
        });
      }

      if (url.includes("/kube/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              podCount: 2,
              activeCount: 1,
              deletedCount: 1,
              uniqueNamespaceCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              podCount: 4,
              activeCount: 4,
              deletedCount: 0,
              uniqueNamespaceCount: 2,
            },
          ],
        });
      }

      if (url.includes("/metric/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 18,
            uniqueActionCount: 4,
            averageDuration: 213.4,
            slowestDuration: 480,
            latestActionTime: "2026-05-12T23:15:00.000Z",
          }),
        });
      }

      if (url.includes("/metric/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              metricCount: 7,
              averageDuration: 176,
              maxDuration: 302,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              metricCount: 11,
              averageDuration: 213,
              maxDuration: 480,
            },
          ],
        });
      }

      if (url.includes("/pipeline/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 12,
            uniqueNameCount: 4,
            completedCount: 9,
            inProgressCount: 3,
            averageDuration: 421000,
            latestStartTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/pipeline/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              pipelineCount: 5,
              completedCount: 4,
              averageDuration: 380000,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              pipelineCount: 7,
              completedCount: 5,
              averageDuration: 421000,
            },
          ],
        });
      }

      if (url.includes("/tms/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 14,
            cycleCount: 2,
            itemCount: 6,
            executedCount: 11,
            pendingCount: 3,
            latestActivityTime: "2026-05-12T23:20:00.000Z",
            statusBreakdown: [
              { status: "PASSED", count: 9 },
              { status: "FAILED", count: 2 },
              { status: "BLOCKED", count: 3 },
            ],
          }),
        });
      }

      if (url.includes("/tms/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              executionCount: 6,
              executedCount: 4,
              uniqueItemCount: 3,
              uniqueCycleCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              executionCount: 8,
              executedCount: 7,
              uniqueItemCount: 4,
              uniqueCycleCount: 2,
            },
          ],
        });
      }

      if (url.includes("/git/all?")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            content: [
              {
                id: 41,
                name: "athena-gateway",
                url: "https://github.com/catools/athena-gateway",
                host: "github.com",
                freshnessStatus: "STALE",
                branchCount: 6,
                lastSync: "2026-05-12T23:05:00.000Z",
              },
            ],
            page: {
              number: 0,
              size: 10,
              totalElements: 1,
              totalPages: 1,
            },
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/overview");

    render(<App />);

    expect(screen.getByRole("heading", { name: /^Overview$/i })).toBeInTheDocument();
    const workspaceNav = screen.getByRole("navigation", { name: /Dashboards/i });
    expect(within(workspaceNav).getByRole("link", { name: /Release readiness/i })).toBeInTheDocument();
    expect(screen.getByText(/^API policy: gateway-only$/i)).toBeInTheDocument();
    expect(screen.getByText(/Only these prefixes are valid browser targets/i)).toBeInTheDocument();
    expect(screen.getByText(/Spec, git, runtime, metric, pipeline, and quality workspaces now feed the overview directly/i)).toBeInTheDocument();
    expect(screen.getByText(/Coverage and operational pressure across every live domain/i)).toBeInTheDocument();
    await screen.findByText(/Recorded metrics/i);
    expect(screen.getByRole("link", { name: /Open Exception Follow-up report for spec sync risk/i })).toHaveAttribute(
      "href",
      "/ui/reports/exception-follow-up?scope=contracts&health=attention",
    );
    expect(screen.getByRole("link", { name: /Open Release Readiness report for latest pipeline bucket/i })).toHaveAttribute(
      "href",
      "/ui/reports/release-readiness?scope=delivery&health=watch",
    );
    expect(screen.getAllByRole("link", { name: /Open Executive Briefing/i }).length).toBeGreaterThan(0);
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/freshness"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/git/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/git/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/kube/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/kube/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/metric/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/metric/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/pipeline/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/pipeline/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/tms/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/tms/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );

    fireEvent.click(screen.getByRole("link", { name: /Open Release Readiness report for latest pipeline bucket/i }));

    expect(await screen.findByRole("heading", { name: /Release Readiness Report/i, level: 1 })).toBeInTheDocument();
    expect(await screen.findByText(/Scope: Delivery/i)).toBeInTheDocument();
    expect(await screen.findByText(/Health: Watch/i)).toBeInTheDocument();
  });

  it("renders release readiness gates and keeps the selected scope in gateway requests", async () => {
    const response = (payload) =>
      Promise.resolve({
        ok: true,
        status: 200,
        headers: { get: () => "application/json" },
        json: async () => payload,
      });
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/core/project/all")) return response({ content: [{ id: 1, code: "ATH", name: "Athena" }], totalElements: 1, totalPages: 1 });
      if (url.includes("/core/version/all")) return response({ content: [{ id: 2, code: "2.0.0", name: "Release 2.0.0" }], totalElements: 1, totalPages: 1 });
      if (url.includes("/core/environment/all")) return response({ content: [{ id: 3, code: "PROD", name: "Production" }], totalElements: 1, totalPages: 1 });
      if (url.includes("/spec/summary")) return response({ specCount: 8 });
      if (url.includes("/spec/freshness")) return response({ staleCount: 2, agingCount: 0, latestSyncTime: "2026-09-08T10:00:00.000Z" });
      if (url.includes("/git/summary")) return response({ totalCount: 4, freshCount: 4, staleCount: 0, agingCount: 0, latestSyncTime: "2026-09-08T10:05:00.000Z" });
      if (url.includes("/pipeline/summary")) return response({ totalCount: 3, completedCount: 1, inProgressCount: 1, latestStartTime: "2026-09-08T10:10:00.000Z" });
      if (url.includes("/tms/summary")) return response({ totalCount: 6, executedCount: 6, pendingCount: 0, latestActivityTime: "2026-09-08T10:15:00.000Z" });
      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/release-readiness?project=ATH&version=2.0.0");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Release readiness/i })).toBeInTheDocument();
    expect(screen.getByLabelText("Project")).toHaveValue("ATH");
    expect(screen.getByText("API contracts")).toBeInTheDocument();
    expect(screen.getAllByText("Blocked").length).toBeGreaterThan(0);
    expect(screen.getByText("4 repositories · 4 fresh")).toBeInTheDocument();
    expect(await screen.findByRole("option", { name: "2.0.0" })).toBeInTheDocument();
    expect(await screen.findByRole("option", { name: "PROD" })).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith(expect.stringContaining("/core/version/all?project=ATH"), expect.anything());
    expect(fetchMock).toHaveBeenCalledWith(expect.stringContaining("/pipeline/summary?project=ATH&version=2.0.0"), expect.anything());
  });

  it("loads the executive briefing report through the gateway prefixes", async () => {
    const printMock = vi.fn();
    window.print = printMock;
    const clickSpy = vi.spyOn(HTMLAnchorElement.prototype, "click").mockImplementation(() => {});

    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/spec/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            specCount: 4,
            projectCount: 2,
            pathCount: 19,
            staleSpecCount: 1,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/spec/freshness")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            freshCount: 3,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/git/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 9,
            hostCount: 2,
            freshCount: 7,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:05:00.000Z",
          }),
        });
      }

      if (url.includes("/git/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              repositoryCount: 4,
              uniqueHostCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              repositoryCount: 5,
              uniqueHostCount: 2,
            },
          ],
        });
      }

      if (url.includes("/kube/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 6,
            namespaceCount: 2,
            nodeCount: 2,
            activeCount: 5,
            deletedCount: 1,
            latestSyncTime: "2026-05-12T23:10:00.000Z",
          }),
        });
      }

      if (url.includes("/kube/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              podCount: 2,
              activeCount: 1,
              deletedCount: 1,
              uniqueNamespaceCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              podCount: 4,
              activeCount: 4,
              deletedCount: 0,
              uniqueNamespaceCount: 2,
            },
          ],
        });
      }

      if (url.includes("/metric/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 18,
            uniqueActionCount: 4,
            averageDuration: 213.4,
            slowestDuration: 480,
            latestActionTime: "2026-05-12T23:15:00.000Z",
          }),
        });
      }

      if (url.includes("/metric/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              metricCount: 7,
              averageDuration: 176,
              maxDuration: 302,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              metricCount: 11,
              averageDuration: 213,
              maxDuration: 480,
            },
          ],
        });
      }

      if (url.includes("/pipeline/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 12,
            uniqueNameCount: 4,
            completedCount: 9,
            inProgressCount: 3,
            averageDuration: 421000,
            latestStartTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/pipeline/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              pipelineCount: 5,
              completedCount: 4,
              averageDuration: 380000,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              pipelineCount: 7,
              completedCount: 5,
              averageDuration: 421000,
            },
          ],
        });
      }

      if (url.includes("/tms/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 14,
            cycleCount: 2,
            itemCount: 6,
            executedCount: 11,
            pendingCount: 3,
            latestActivityTime: "2026-05-12T23:20:00.000Z",
            statusBreakdown: [
              { status: "PASSED", count: 9 },
              { status: "FAILED", count: 2 },
              { status: "BLOCKED", count: 3 },
            ],
          }),
        });
      }

      if (url.includes("/tms/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              executionCount: 6,
              executedCount: 4,
              uniqueItemCount: 3,
              uniqueCycleCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              executionCount: 8,
              executedCount: 7,
              uniqueItemCount: 4,
              uniqueCycleCount: 2,
            },
          ],
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/reports/executive-briefing");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Executive Briefing/i, level: 1 })).toBeInTheDocument();
    expect(await screen.findByRole("heading", { name: /Portfolio coverage by domain/i, level: 2 })).toBeInTheDocument();
    expect(await screen.findByText(/Domain health distribution/i)).toBeInTheDocument();
    expect(await screen.findByText(/Prioritized domains and next workspace handoffs/i)).toBeInTheDocument();
    fireEvent.click(screen.getByRole("button", { name: "3 days" }));
    await waitFor(() => {
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/git/summary?windowDays=3"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/pipeline/trend?windowDays=3"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
    });
    fireEvent.click(screen.getByRole("button", { name: "Export JSON" }));
    fireEvent.click(screen.getByRole("button", { name: "Export CSV" }));
    fireEvent.click(screen.getByRole("button", { name: "Print view" }));
    expect(clickSpy).toHaveBeenCalledTimes(2);
    expect(printMock).toHaveBeenCalled();
    clickSpy.mockRestore();
  });

  it("loads the project catalog through the core gateway prefix", async () => {
    const fetchMock = vi.fn().mockResolvedValue({
      ok: true,
      status: 200,
      headers: {
        get: () => "application/json",
      },
      json: async () => ({
        content: [
          {
            id: 7,
            code: "ATH",
            name: "Athena",
          },
        ],
        page: {
          number: 0,
          size: 10,
          totalElements: 1,
          totalPages: 1,
        },
      }),
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/catalog/projects");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Project Catalog/i })).toBeInTheDocument();
    expect(await screen.findByRole("cell", { name: "ATH" })).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/core/project/all?"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
  });

  it("loads the API specification workspace through the spec gateway prefix", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/spec/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            specCount: 1,
            projectCount: 1,
            pathCount: 12,
            staleSpecCount: 0,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/spec/freshness")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            freshCount: 1,
            agingCount: 0,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/spec/drift?")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            content: [
              {
                id: 29,
                project: "ATH",
                name: "LegacyContractStale",
                pathCount: 8,
                driftStatus: "STALE",
                syncAgeDays: 45,
                lastSyncTime: "2026-03-28T23:00:00.000Z",
              },
            ],
            page: {
              number: 0,
              size: 6,
              totalElements: 1,
              totalPages: 1,
            },
          }),
        });
      }

      if (url.includes("/spec/all?")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            content: [
              {
                id: 19,
                project: "ATH",
                name: "OpenApi",
                title: "Athena Public API",
                version: "1.0.0",
                pathCount: 12,
                lastSyncTime: "2026-05-12T23:00:00.000Z",
              },
            ],
            page: {
              number: 0,
              size: 10,
              totalElements: 1,
              totalPages: 1,
            },
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/apis/specs?project=ATH&name=OpenApi&page=1");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /API Specification Workspace/i })).toBeInTheDocument();
    expect(await screen.findByText(/Specs matched/i)).toBeInTheDocument();
    expect(await screen.findByRole("link", { name: "OpenApi" })).toBeInTheDocument();
    expect(screen.getByDisplayValue("ATH")).toBeInTheDocument();
    expect(screen.getByDisplayValue("OpenApi")).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/summary?project=ATH&name=OpenApi"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/freshness?project=ATH&name=OpenApi"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/drift?project=ATH&name=OpenApi&page=0"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/all?project=ATH&name=OpenApi&page=1"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
  });

  it("loads the metric performance workspace through the metric gateway prefix", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/metric/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 8,
            uniqueActionCount: 3,
            averageDuration: 182.4,
            slowestDuration: 390,
            latestActionTime: "2026-05-12T23:15:00.000Z",
          }),
        });
      }

      if (url.includes("/metric/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              metricCount: 3,
              averageDuration: 141,
              maxDuration: 220,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              metricCount: 5,
              averageDuration: 182,
              maxDuration: 390,
            },
          ],
        });
      }

      if (url.includes("/metric/all?")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            content: [
              {
                id: 31,
                project: "ATH",
                environment: "DEV",
                actionName: "sync-openapi",
                actionType: "HTTP",
                actionTarget: "/openapi/customers",
                duration: 182,
                actionTime: "2026-05-12T23:15:00.000Z",
              },
            ],
            page: {
              number: 0,
              size: 10,
              totalElements: 1,
              totalPages: 1,
            },
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/metrics/executions");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Metric Performance Workspace/i })).toBeInTheDocument();
    expect(await screen.findByText(/Execution volume trend/i)).toBeInTheDocument();
    expect(await screen.findByRole("cell", { name: "sync-openapi" })).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/metric/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/metric/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/metric/all?"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
  });

  it("loads the pipeline delivery workspace through the pipeline gateway prefix", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/pipeline/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 12,
            uniqueNameCount: 4,
            completedCount: 9,
            inProgressCount: 3,
            averageDuration: 421000,
            latestStartTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/pipeline/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              pipelineCount: 5,
              completedCount: 4,
              averageDuration: 380000,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              pipelineCount: 7,
              completedCount: 5,
              averageDuration: 421000,
            },
          ],
        });
      }

      if (url.includes("/pipeline/all?")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            content: [
              {
                id: 88,
                project: "ATH",
                version: "2.0.0",
                environment: "DEV",
                name: "release-main",
                number: "build-184",
                state: "RUNNING",
                duration: null,
                startDate: "2026-05-12T23:00:00.000Z",
                endDate: null,
              },
            ],
            number: 0,
            size: 10,
            totalElements: 1,
            totalPages: 1,
            first: true,
            last: true,
            empty: false,
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/pipelines/runs");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Pipeline Delivery Workspace/i })).toBeInTheDocument();
    expect(await screen.findByText(/Pipeline dashboard cards/i)).toBeInTheDocument();
    expect(await screen.findByRole("cell", { name: "release-main" })).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/pipeline/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/pipeline/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/pipeline/all?"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
  });

  it("loads the runtime pod workspace through the kube gateway prefix", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/kube/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 6,
            namespaceCount: 2,
            nodeCount: 2,
            activeCount: 5,
            deletedCount: 1,
            latestSyncTime: "2026-05-12T23:10:00.000Z",
          }),
        });
      }

      if (url.includes("/kube/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              podCount: 2,
              activeCount: 1,
              deletedCount: 1,
              uniqueNamespaceCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              podCount: 4,
              activeCount: 4,
              deletedCount: 0,
              uniqueNamespaceCount: 2,
            },
          ],
        });
      }

      if (url.includes("/kube/all?")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            content: [
              {
                id: 61,
                project: "ATH",
                namespace: "runtime-prod",
                name: "runtime-pod-a",
                nodeName: "worker-a",
                status: "Running",
                containerCount: 3,
                lastSync: "2026-05-12T23:10:00.000Z",
              },
            ],
            number: 0,
            size: 10,
            totalElements: 1,
            totalPages: 1,
            first: true,
            last: true,
            empty: false,
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/runtime/pods");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Runtime Pod Workspace/i })).toBeInTheDocument();
    expect(await screen.findByText(/Runtime pod dashboard cards/i)).toBeInTheDocument();
    expect(await screen.findByRole("cell", { name: "runtime-pod-a" })).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/kube/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/kube/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/kube/all?"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
  });

  it("loads the git repository workspace through the git gateway prefix", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/git/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 9,
            hostCount: 2,
            freshCount: 7,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:05:00.000Z",
          }),
        });
      }

      if (url.includes("/git/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              repositoryCount: 4,
              uniqueHostCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              repositoryCount: 5,
              uniqueHostCount: 2,
            },
          ],
        });
      }

      if (url.includes("/git/all?")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            content: [
              {
                id: 41,
                name: "athena-main",
                url: "https://github.com/athena/athena-main.git",
                host: "github.com",
                lastSync: "2026-05-12T23:05:00.000Z",
                syncAgeDays: 1,
                freshnessStatus: "FRESH",
              },
            ],
            number: 0,
            size: 10,
            totalElements: 1,
            totalPages: 1,
            first: true,
            last: true,
            empty: false,
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/git/repositories");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Git Repository Workspace/i })).toBeInTheDocument();
    expect(await screen.findByText(/Repository freshness cards/i)).toBeInTheDocument();
    expect(await screen.findByRole("cell", { name: "athena-main" })).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/git/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/git/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/git/all?"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
  });

  it("loads the cross-domain portfolio report through the gateway prefixes", async () => {
    const printMock = vi.fn();
    window.print = printMock;
    const clickSpy = vi.spyOn(HTMLAnchorElement.prototype, "click").mockImplementation(() => {});

    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/spec/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            specCount: 4,
            projectCount: 2,
            pathCount: 19,
            staleSpecCount: 1,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/spec/freshness")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            freshCount: 3,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/git/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 9,
            hostCount: 2,
            freshCount: 7,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:05:00.000Z",
          }),
        });
      }

      if (url.includes("/git/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              repositoryCount: 4,
              uniqueHostCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              repositoryCount: 5,
              uniqueHostCount: 2,
            },
          ],
        });
      }

      if (url.includes("/kube/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 6,
            namespaceCount: 2,
            nodeCount: 2,
            activeCount: 5,
            deletedCount: 1,
            latestSyncTime: "2026-05-12T23:10:00.000Z",
          }),
        });
      }

      if (url.includes("/kube/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              podCount: 2,
              activeCount: 1,
              deletedCount: 1,
              uniqueNamespaceCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              podCount: 4,
              activeCount: 4,
              deletedCount: 0,
              uniqueNamespaceCount: 2,
            },
          ],
        });
      }

      if (url.includes("/metric/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 18,
            uniqueActionCount: 4,
            averageDuration: 213.4,
            slowestDuration: 480,
            latestActionTime: "2026-05-12T23:15:00.000Z",
          }),
        });
      }

      if (url.includes("/metric/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              metricCount: 7,
              averageDuration: 176,
              maxDuration: 302,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              metricCount: 11,
              averageDuration: 213,
              maxDuration: 480,
            },
          ],
        });
      }

      if (url.includes("/pipeline/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 12,
            uniqueNameCount: 4,
            completedCount: 9,
            inProgressCount: 3,
            averageDuration: 421000,
            latestStartTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/pipeline/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              pipelineCount: 5,
              completedCount: 4,
              averageDuration: 380000,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              pipelineCount: 7,
              completedCount: 5,
              averageDuration: 421000,
            },
          ],
        });
      }

      if (url.includes("/tms/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 14,
            cycleCount: 2,
            itemCount: 6,
            executedCount: 11,
            pendingCount: 3,
            latestActivityTime: "2026-05-12T23:20:00.000Z",
            statusBreakdown: [
              { status: "PASSED", count: 9 },
              { status: "FAILED", count: 2 },
              { status: "BLOCKED", count: 3 },
            ],
          }),
        });
      }

      if (url.includes("/tms/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              executionCount: 6,
              executedCount: 4,
              uniqueItemCount: 3,
              uniqueCycleCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              executionCount: 8,
              executedCount: 7,
              uniqueItemCount: 4,
              uniqueCycleCount: 2,
            },
          ],
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/reports/portfolio");

    const { container } = render(<App />);

    expect(await screen.findByRole("heading", { name: /Portfolio Report/i, level: 1 })).toBeInTheDocument();
    expect(await screen.findByText(/Cross-domain operating picture/i)).toBeInTheDocument();
    expect((await screen.findAllByRole("link", { name: /API Specs/i })).length).toBeGreaterThan(0);
    expect(container.querySelectorAll(".status-card").length).toBe(6);
    fireEvent.click(screen.getByRole("button", { name: "3 days" }));
    await waitFor(() => {
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/git/summary?windowDays=3"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/git/trend?windowDays=3"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
    });
    fireEvent.click(
      within(screen.getByRole("toolbar", { name: "Portfolio report domain focus filters" })).getByRole("button", {
        name: "Delivery",
      }),
    );
    expect(container.querySelectorAll(".status-card").length).toBe(1);
    fireEvent.click(screen.getByRole("button", { name: "Export JSON" }));
    fireEvent.click(screen.getByRole("button", { name: "Export CSV" }));
    fireEvent.click(screen.getByRole("button", { name: "Print view" }));
    expect(clickSpy).toHaveBeenCalledTimes(2);
    expect(printMock).toHaveBeenCalled();
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/git/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/kube/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/metric/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/pipeline/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/tms/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    clickSpy.mockRestore();
  });

  it("loads the release readiness report through the gateway prefixes", async () => {
    const clickSpy = vi.spyOn(HTMLAnchorElement.prototype, "click").mockImplementation(() => {});
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/spec/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            specCount: 4,
            projectCount: 2,
            pathCount: 19,
            staleSpecCount: 1,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/spec/freshness")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            freshCount: 3,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/git/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 9,
            hostCount: 2,
            freshCount: 7,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:05:00.000Z",
          }),
        });
      }

      if (url.includes("/git/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              repositoryCount: 4,
              uniqueHostCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              repositoryCount: 5,
              uniqueHostCount: 2,
            },
          ],
        });
      }

      if (url.includes("/pipeline/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 12,
            uniqueNameCount: 4,
            completedCount: 9,
            inProgressCount: 3,
            averageDuration: 421000,
            latestStartTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/pipeline/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              pipelineCount: 5,
              completedCount: 4,
              averageDuration: 380000,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              pipelineCount: 7,
              completedCount: 5,
              averageDuration: 421000,
            },
          ],
        });
      }

      if (url.includes("/tms/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 14,
            cycleCount: 2,
            itemCount: 6,
            executedCount: 11,
            pendingCount: 3,
            latestActivityTime: "2026-05-12T23:20:00.000Z",
            statusBreakdown: [
              { status: "PASSED", count: 9 },
              { status: "FAILED", count: 2 },
              { status: "BLOCKED", count: 3 },
            ],
          }),
        });
      }

      if (url.includes("/tms/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              executionCount: 6,
              executedCount: 4,
              uniqueItemCount: 3,
              uniqueCycleCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              executionCount: 8,
              executedCount: 7,
              uniqueItemCount: 4,
              uniqueCycleCount: 2,
            },
          ],
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/reports/release-readiness?scope=delivery&health=watch");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Release Readiness Report/i, level: 1 })).toBeInTheDocument();
    expect(await screen.findByText(/Release readiness picture/i)).toBeInTheDocument();
    expect(await screen.findByText(/Recommended drill-downs/i)).toBeInTheDocument();
    expect(screen.getByText(/Scope: Delivery/i)).toBeInTheDocument();
    expect(screen.getByText(/Health: Watch/i)).toBeInTheDocument();
    fireEvent.click(screen.getByRole("button", { name: "3 days" }));
    await waitFor(() => {
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/pipeline/summary?windowDays=3"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/tms/trend?windowDays=3"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
    });
    fireEvent.click(screen.getByRole("button", { name: "Export CSV" }));
    expect(clickSpy).toHaveBeenCalledTimes(1);
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/git/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/pipeline/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/tms/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    clickSpy.mockRestore();
  });

  it("loads the platform operations report through the gateway prefixes", async () => {
    const clickSpy = vi.spyOn(HTMLAnchorElement.prototype, "click").mockImplementation(() => {});
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/spec/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            specCount: 4,
            projectCount: 2,
            pathCount: 19,
            staleSpecCount: 1,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/spec/freshness")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            freshCount: 3,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/git/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 9,
            hostCount: 2,
            freshCount: 7,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:05:00.000Z",
          }),
        });
      }

      if (url.includes("/git/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              repositoryCount: 4,
              uniqueHostCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              repositoryCount: 5,
              uniqueHostCount: 2,
            },
          ],
        });
      }

      if (url.includes("/kube/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 6,
            namespaceCount: 2,
            nodeCount: 2,
            activeCount: 5,
            deletedCount: 1,
            latestSyncTime: "2026-05-12T23:10:00.000Z",
          }),
        });
      }

      if (url.includes("/kube/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              podCount: 2,
              activeCount: 1,
              deletedCount: 1,
              uniqueNamespaceCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              podCount: 4,
              activeCount: 4,
              deletedCount: 0,
              uniqueNamespaceCount: 2,
            },
          ],
        });
      }

      if (url.includes("/metric/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 18,
            uniqueActionCount: 4,
            averageDuration: 213.4,
            slowestDuration: 480,
            latestActionTime: "2026-05-12T23:15:00.000Z",
          }),
        });
      }

      if (url.includes("/metric/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              metricCount: 7,
              averageDuration: 176,
              maxDuration: 302,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              metricCount: 11,
              averageDuration: 213,
              maxDuration: 480,
            },
          ],
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/reports/platform-operations?scope=repositories&health=attention");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Platform Operations Report/i, level: 1 })).toBeInTheDocument();
    expect(await screen.findByText(/Platform operations picture/i)).toBeInTheDocument();
    expect(await screen.findByText(/Recommended drill-downs/i)).toBeInTheDocument();
    expect(screen.getByText(/Scope: Repositories/i)).toBeInTheDocument();
    expect(screen.getByText(/Health: Attention/i)).toBeInTheDocument();
    const repositoryWorkspaceLink = screen.getAllByRole("link").find((link) => link.getAttribute("href") === "/ui/git/repositories?freshness=STALE");
    expect(repositoryWorkspaceLink).toBeDefined();
    fireEvent.click(screen.getByRole("button", { name: "7 days" }));
    await waitFor(() => {
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/git/summary?windowDays=7"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/kube/trend?windowDays=7"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
    });
    fireEvent.click(screen.getByRole("button", { name: "Export CSV" }));
    expect(clickSpy).toHaveBeenCalledTimes(1);
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/git/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/kube/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/metric/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    fireEvent.click(repositoryWorkspaceLink);
    expect(await screen.findByRole("heading", { name: /Git Repository Workspace/i, level: 1 })).toBeInTheDocument();
    expect(screen.getByRole("textbox", { name: /Freshness/i })).toHaveValue("STALE");
    await waitFor(() => {
      expect(fetchMock.mock.calls.some(([url]) => typeof url === "string" && url.includes("/git/all?") && url.includes("freshness=STALE"))).toBe(true);
    });
    clickSpy.mockRestore();
  });

  it("deep-links repository watch handoffs to the aging workspace filter", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/spec/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: { get: () => "application/json" },
          json: async () => ({
            specCount: 4,
            projectCount: 2,
            pathCount: 19,
            staleSpecCount: 0,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/spec/freshness")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: { get: () => "application/json" },
          json: async () => ({
            freshCount: 3,
            agingCount: 1,
            staleCount: 0,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/git/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: { get: () => "application/json" },
          json: async () => ({
            totalCount: 9,
            hostCount: 2,
            freshCount: 7,
            agingCount: 2,
            staleCount: 0,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:05:00.000Z",
          }),
        });
      }

      if (url.includes("/git/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: { get: () => "application/json" },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              repositoryCount: 4,
              uniqueHostCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              repositoryCount: 5,
              uniqueHostCount: 2,
            },
          ],
        });
      }

      if (url.includes("/kube/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: { get: () => "application/json" },
          json: async () => ({
            totalCount: 6,
            namespaceCount: 2,
            nodeCount: 2,
            activeCount: 6,
            deletedCount: 0,
            latestSyncTime: "2026-05-12T23:10:00.000Z",
          }),
        });
      }

      if (url.includes("/kube/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: { get: () => "application/json" },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              podCount: 2,
              activeCount: 2,
              deletedCount: 0,
              uniqueNamespaceCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              podCount: 4,
              activeCount: 4,
              deletedCount: 0,
              uniqueNamespaceCount: 2,
            },
          ],
        });
      }

      if (url.includes("/metric/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: { get: () => "application/json" },
          json: async () => ({
            totalCount: 18,
            uniqueActionCount: 4,
            averageDuration: 213.4,
            slowestDuration: 300,
            latestActionTime: "2026-05-12T23:15:00.000Z",
          }),
        });
      }

      if (url.includes("/metric/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: { get: () => "application/json" },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              metricCount: 7,
              averageDuration: 176,
              maxDuration: 302,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              metricCount: 11,
              averageDuration: 213,
              maxDuration: 300,
            },
          ],
        });
      }

      if (url.includes("/git/all?")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: { get: () => "application/json" },
          json: async () => ({
            content: [
              {
                id: 52,
                name: "athena-core",
                url: "https://github.com/catools/athena-core",
                host: "github.com",
                freshnessStatus: "AGING",
                branchCount: 5,
                lastSync: "2026-05-12T23:05:00.000Z",
              },
            ],
            page: {
              number: 0,
              size: 10,
              totalElements: 1,
              totalPages: 1,
            },
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/reports/platform-operations?scope=repositories&health=watch");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Platform Operations Report/i, level: 1 })).toBeInTheDocument();
    expect(screen.getByText(/Scope: Repositories/i)).toBeInTheDocument();
    expect(screen.getByText(/Health: Watch/i)).toBeInTheDocument();

    const repositoryWorkspaceLink = screen.getAllByRole("link").find((link) => link.getAttribute("href") === "/ui/git/repositories?freshness=AGING");

    expect(repositoryWorkspaceLink).toBeDefined();
    fireEvent.click(repositoryWorkspaceLink);

    expect(await screen.findByRole("heading", { name: /Git Repository Workspace/i, level: 1 })).toBeInTheDocument();
    expect(screen.getByRole("textbox", { name: /Freshness/i })).toHaveValue("AGING");
    await waitFor(() => {
      expect(fetchMock.mock.calls.some(([url]) => typeof url === "string" && url.includes("/git/all?") && url.includes("freshness=AGING"))).toBe(true);
    });
  });

  it("loads the exception follow-up report through the gateway prefixes", async () => {
    const clickSpy = vi.spyOn(HTMLAnchorElement.prototype, "click").mockImplementation(() => {});
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/spec/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            specCount: 4,
            projectCount: 2,
            pathCount: 19,
            staleSpecCount: 1,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/spec/freshness")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            freshCount: 3,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/git/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 9,
            hostCount: 2,
            freshCount: 7,
            agingCount: 1,
            staleCount: 1,
            unknownSyncCount: 0,
            warningWindowDays: 7,
            freshnessWindowDays: 30,
            latestSyncTime: "2026-05-12T23:05:00.000Z",
          }),
        });
      }

      if (url.includes("/git/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              repositoryCount: 4,
              uniqueHostCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              repositoryCount: 5,
              uniqueHostCount: 2,
            },
          ],
        });
      }

      if (url.includes("/kube/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 6,
            namespaceCount: 2,
            nodeCount: 2,
            activeCount: 5,
            deletedCount: 1,
            latestSyncTime: "2026-05-12T23:10:00.000Z",
          }),
        });
      }

      if (url.includes("/kube/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              podCount: 2,
              activeCount: 1,
              deletedCount: 1,
              uniqueNamespaceCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              podCount: 4,
              activeCount: 4,
              deletedCount: 0,
              uniqueNamespaceCount: 2,
            },
          ],
        });
      }

      if (url.includes("/metric/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 18,
            uniqueActionCount: 4,
            averageDuration: 213.4,
            slowestDuration: 480,
            latestActionTime: "2026-05-12T23:15:00.000Z",
          }),
        });
      }

      if (url.includes("/metric/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              metricCount: 7,
              averageDuration: 176,
              maxDuration: 302,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              metricCount: 11,
              averageDuration: 213,
              maxDuration: 480,
            },
          ],
        });
      }

      if (url.includes("/pipeline/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 12,
            uniqueNameCount: 4,
            completedCount: 9,
            inProgressCount: 3,
            averageDuration: 421000,
            latestStartTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/pipeline/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              pipelineCount: 5,
              completedCount: 4,
              averageDuration: 380000,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              pipelineCount: 7,
              completedCount: 5,
              averageDuration: 421000,
            },
          ],
        });
      }

      if (url.includes("/tms/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 14,
            cycleCount: 2,
            itemCount: 6,
            executedCount: 11,
            pendingCount: 3,
            latestActivityTime: "2026-05-12T23:20:00.000Z",
            statusBreakdown: [
              { status: "PASSED", count: 9 },
              { status: "FAILED", count: 2 },
              { status: "BLOCKED", count: 3 },
            ],
          }),
        });
      }

      if (url.includes("/tms/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              executionCount: 6,
              executedCount: 4,
              uniqueItemCount: 3,
              uniqueCycleCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              executionCount: 8,
              executedCount: 7,
              uniqueItemCount: 4,
              uniqueCycleCount: 2,
            },
          ],
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/reports/exception-follow-up?scope=runtime&health=attention");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Exception Follow-up Report/i, level: 1 })).toBeInTheDocument();
    expect(await screen.findByText(/Exception follow-up picture/i)).toBeInTheDocument();
    expect(await screen.findByText(/Recommended drill-downs/i)).toBeInTheDocument();
    expect(screen.getByText(/Scope: Runtime/i)).toBeInTheDocument();
    expect(screen.getByText(/Health: Attention/i)).toBeInTheDocument();
    fireEvent.click(screen.getByRole("button", { name: "7 days" }));
    await waitFor(() => {
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/kube/summary?windowDays=7"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/tms/trend?windowDays=7"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
    });
    fireEvent.click(screen.getByRole("button", { name: "Export CSV" }));
    expect(clickSpy).toHaveBeenCalledTimes(1);
    clickSpy.mockRestore();
  });

  it("loads the delivery and quality report through the gateway prefixes", async () => {
    const clickSpy = vi.spyOn(HTMLAnchorElement.prototype, "click").mockImplementation(() => {});
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/metric/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 18,
            uniqueActionCount: 4,
            averageDuration: 213.4,
            slowestDuration: 480,
            latestActionTime: "2026-05-12T23:15:00.000Z",
          }),
        });
      }

      if (url.includes("/metric/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              metricCount: 7,
              averageDuration: 176,
              maxDuration: 302,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              metricCount: 11,
              averageDuration: 213,
              maxDuration: 480,
            },
          ],
        });
      }

      if (url.includes("/pipeline/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 12,
            uniqueNameCount: 4,
            completedCount: 9,
            inProgressCount: 3,
            averageDuration: 421000,
            latestStartTime: "2026-05-12T23:00:00.000Z",
          }),
        });
      }

      if (url.includes("/pipeline/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              pipelineCount: 5,
              completedCount: 4,
              averageDuration: 380000,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              pipelineCount: 7,
              completedCount: 5,
              averageDuration: 421000,
            },
          ],
        });
      }

      if (url.includes("/tms/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 14,
            cycleCount: 2,
            itemCount: 6,
            executedCount: 11,
            pendingCount: 3,
            latestActivityTime: "2026-05-12T23:20:00.000Z",
            statusBreakdown: [
              { status: "PASSED", count: 9 },
              { status: "FAILED", count: 2 },
              { status: "BLOCKED", count: 3 },
            ],
          }),
        });
      }

      if (url.includes("/tms/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              executionCount: 6,
              executedCount: 4,
              uniqueItemCount: 3,
              uniqueCycleCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              executionCount: 8,
              executedCount: 7,
              uniqueItemCount: 4,
              uniqueCycleCount: 2,
            },
          ],
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/reports/delivery-quality");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Delivery \+ Quality Report/i })).toBeInTheDocument();
    expect(await screen.findByText(/Coordinated delivery and execution picture/i)).toBeInTheDocument();
    expect(await screen.findByText(/Recent pipeline, quality, and metric buckets/i)).toBeInTheDocument();
    fireEvent.click(screen.getByRole("button", { name: "1 day" }));
    await waitFor(() => {
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/pipeline/summary?windowDays=1"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
      expect(fetchMock).toHaveBeenCalledWith(
        expect.stringContaining("/pipeline/trend?windowDays=1"),
        expect.objectContaining({
          headers: expect.objectContaining({
            Accept: "application/json",
          }),
        }),
      );
    });
    fireEvent.click(screen.getByRole("button", { name: "Export CSV" }));
    expect(clickSpy).toHaveBeenCalledTimes(1);
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/metric/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/pipeline/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/tms/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    clickSpy.mockRestore();
  });

  it("loads the quality execution workspace through the tms gateway prefix", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.includes("/tms/summary")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            totalCount: 14,
            cycleCount: 2,
            itemCount: 6,
            executedCount: 11,
            pendingCount: 3,
            latestActivityTime: "2026-05-12T23:20:00.000Z",
            statusBreakdown: [
              { status: "PASSED", count: 9 },
              { status: "FAILED", count: 2 },
              { status: "BLOCKED", count: 3 },
            ],
          }),
        });
      }

      if (url.includes("/tms/trend")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => [
            {
              bucketStart: "2026-05-11T00:00:00.000Z",
              executionCount: 6,
              executedCount: 4,
              uniqueItemCount: 3,
              uniqueCycleCount: 1,
            },
            {
              bucketStart: "2026-05-12T00:00:00.000Z",
              executionCount: 8,
              executedCount: 7,
              uniqueItemCount: 4,
              uniqueCycleCount: 2,
            },
          ],
        });
      }

      if (url.includes("/tms/all?")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            content: [
              {
                id: 77,
                project: "ATH",
                version: "2.0.0",
                cycleCode: "REG-24.05",
                item: "ATH-TC-102",
                status: "PASSED",
                executor: "qa.bot",
                executedOn: "2026-05-12T23:20:00.000Z",
                createdOn: "2026-05-12T22:00:00.000Z",
              },
            ],
            page: {
              number: 0,
              size: 10,
              totalElements: 1,
              totalPages: 1,
            },
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/quality/executions");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Quality Execution Workspace/i })).toBeInTheDocument();
    expect(await screen.findByText(/Execution quality summary/i)).toBeInTheDocument();
    expect(await screen.findByRole("cell", { name: "ATH-TC-102" })).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/tms/summary"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/tms/trend"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/tms/all?"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
  });

  it("loads a quality execution detail page through the tms gateway prefix", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.endsWith("/tms/execution/77")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            id: 77,
            item: "ATH-TC-102",
            status: "PASSED",
            executor: "qa.bot",
            createdOn: "2026-05-12T22:00:00.000Z",
            executedOn: "2026-05-12T23:20:00.000Z",
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/quality/executions/77");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /ATH-TC-102/i, level: 1 })).toBeInTheDocument();
    expect(await screen.findByRole("heading", { name: /Lifecycle and ownership/i })).toBeInTheDocument();
    expect((await screen.findAllByText(/qa.bot/i)).length).toBeGreaterThan(0);
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/tms/execution/77"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
  });

  it("loads a specification detail page through the spec gateway prefix", async () => {
    const fetchMock = vi.fn().mockImplementation((url) => {
      if (url.endsWith("/spec/19")) {
        return Promise.resolve({
          ok: true,
          status: 200,
          headers: {
            get: () => "application/json",
          },
          json: async () => ({
            id: 19,
            project: "ATH",
            name: "OpenApi",
            title: "Athena Public API",
            version: "1.0.0",
            firstTimeSeen: "2026-05-01T23:00:00.000Z",
            lastSyncTime: "2026-05-12T23:00:00.000Z",
            metadata: [
              {
                id: 1,
                name: "owner",
                value: "platform-api",
              },
            ],
            paths: [
              {
                id: 7,
                specId: 19,
                method: "GET",
                url: "/customers",
                title: "List customers",
                lastSyncTime: "2026-05-12T23:00:00.000Z",
              },
            ],
          }),
        });
      }

      throw new Error(`Unexpected request: ${url}`);
    });

    vi.stubGlobal("fetch", fetchMock);
    window.history.pushState({}, "", "/ui/apis/specs/19");

    render(<App />);

    expect(await screen.findByRole("heading", { name: /Athena Public API/i })).toBeInTheDocument();
    expect(await screen.findByRole("heading", { name: /Spec paths/i })).toBeInTheDocument();
    expect(await screen.findByRole("cell", { name: "/customers" })).toBeInTheDocument();
    expect(fetchMock).toHaveBeenCalledWith(
      expect.stringContaining("/spec/19"),
      expect.objectContaining({
        headers: expect.objectContaining({
          Accept: "application/json",
        }),
      }),
    );
  });
});

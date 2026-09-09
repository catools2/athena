import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { OverviewPage } from "../features/overview/pages/OverviewPage";
import { ApiSpecDetailPage } from "../features/apis/pages/ApiSpecDetailPage";
import { ApiSpecWorkspacePage } from "../features/apis/pages/ApiSpecWorkspacePage";
import { CoreCatalogPage } from "../features/catalog/pages/CoreCatalogPage";
import { GitRepositoryWorkspacePage } from "../features/git/pages/GitRepositoryWorkspacePage";
import { QualityExecutionDetailPage } from "../features/governance/pages/QualityExecutionDetailPage";
import { QualityExecutionWorkspacePage } from "../features/governance/pages/QualityExecutionWorkspacePage";
import { MetricWorkspacePage } from "../features/metrics/pages/MetricWorkspacePage";
import { ReleaseReadinessPage } from "../features/readiness/pages/ReleaseReadinessPage";
import { PipelineWorkspacePage } from "../features/pipelines/pages/PipelineWorkspacePage";
import { DeliveryQualityReportPage } from "../features/reports/pages/DeliveryQualityReportPage";
import { ExecutiveBriefingReportPage } from "../features/reports/pages/ExecutiveBriefingReportPage";
import { ExceptionFollowUpReportPage } from "../features/reports/pages/ExceptionFollowUpReportPage";
import { PlatformOperationsReportPage } from "../features/reports/pages/PlatformOperationsReportPage";
import { ReleaseReadinessReportPage } from "../features/reports/pages/ReleaseReadinessReportPage";
import { PortfolioReportPage } from "../features/reports/pages/PortfolioReportPage";
import { PodWorkspacePage } from "../features/runtime/pages/PodWorkspacePage";
import { DashboardListPage } from "../features/dashboards/pages/DashboardListPage";
import { DashboardPage } from "../features/dashboards/pages/DashboardPage";
import { TestCyclesPage } from "../features/qa/pages/TestCyclesPage";
import { PerformancePage } from "../features/qa/pages/PerformancePage";
import { CorrelationPage } from "../features/qa/pages/CorrelationPage";
import { AgentPage } from "../features/agent/pages/AgentPage";
import { apiRoots } from "../shared/api/gatewayClient";
import { AppShell } from "../shared/ui/AppShell";
import { DashboardPageHero } from "../shared/ui/DashboardPageHero";

const catalogPages = [
  {
    apiRoot: apiRoots.core,
    serviceName: "athena-boot-core",
    path: "catalog/projects",
    endpoint: "/project/all",
    title: "Project Catalog",
    eyebrow: "Core workspace",
    summary: "Global project context starts here and anchors every other domain workspace.",
    tableTitle: "Projects available through athena-boot-core",
    sort: "code",
    filters: [
      { key: "code", label: "Project code", placeholder: "ATH" },
      { key: "name", label: "Project name", placeholder: "Athena" },
    ],
    columns: [
      { key: "code", label: "Code", render: (row) => row.code },
      { key: "name", label: "Name", render: (row) => row.name },
      { key: "id", label: "Id", render: (row) => row.id },
    ],
  },
  {
    apiRoot: apiRoots.core,
    serviceName: "athena-boot-core",
    path: "catalog/environments",
    endpoint: "/environment/all",
    title: "Environment Catalog",
    eyebrow: "Core workspace",
    summary: "Environment selection will feed runtime, delivery, and quality pages.",
    tableTitle: "Environments available through athena-boot-core",
    sort: "code",
    filters: [
      { key: "project", label: "Project code", placeholder: "ATH" },
      { key: "code", label: "Environment code", placeholder: "DEV" },
      { key: "name", label: "Environment name", placeholder: "Development" },
    ],
    columns: [
      { key: "code", label: "Code", render: (row) => row.code },
      { key: "name", label: "Name", render: (row) => row.name },
      { key: "project", label: "Project", render: (row) => row.project },
      { key: "id", label: "Id", render: (row) => row.id },
    ],
  },
  {
    apiRoot: apiRoots.core,
    serviceName: "athena-boot-core",
    path: "catalog/versions",
    endpoint: "/version/all",
    title: "Version Catalog",
    eyebrow: "Core workspace",
    summary: "Version context ties together release, test, and deployment reporting.",
    tableTitle: "Versions available through athena-boot-core",
    sort: "code",
    filters: [
      { key: "project", label: "Project code", placeholder: "ATH" },
      { key: "code", label: "Version code", placeholder: "2.0" },
      { key: "name", label: "Version name", placeholder: "Release 2.0" },
    ],
    columns: [
      { key: "code", label: "Code", render: (row) => row.code },
      { key: "name", label: "Name", render: (row) => row.name },
      { key: "project", label: "Project", render: (row) => row.project },
      { key: "id", label: "Id", render: (row) => row.id },
    ],
  },
  {
    apiRoot: apiRoots.core,
    serviceName: "athena-boot-core",
    path: "catalog/users",
    endpoint: "/user/all",
    title: "User Catalog",
    eyebrow: "Core workspace",
    summary: "User attribution will support ownership views across git, pipeline, and test workflows.",
    tableTitle: "Users available through athena-boot-core",
    sort: "username",
    filters: [
      { key: "username", label: "Username", placeholder: "a.keshmiri" },
      { key: "alias", label: "Alias", placeholder: "akeshmiri" },
    ],
    columns: [
      { key: "username", label: "Username", render: (row) => row.username },
      {
        key: "aliases",
        label: "Aliases",
        render: (row) => (Array.isArray(row.aliases) && row.aliases.length > 0 ? row.aliases.map((alias) => alias.alias).join(", ") : "—"),
      },
      { key: "id", label: "Id", render: (row) => row.id },
    ],
  },
];

function NotFoundPage() {
  return (
    <DashboardPageHero
      eyebrow="Route not found"
      title="That page is outside the current reporting portfolio."
      summary="The active shell currently covers overview, core catalog context, API specifications, pipeline runs, metric runs, runtime pods, git repositories, the quality execution workspace, and the stakeholder report deck."
      callouts={["Return to /overview", "Keep browser traffic on /ui and gateway-backed prefixes only"]}
      metrics={[
        {
          key: "fallback-route",
          label: "Recommended route",
          value: "/overview",
          detail: "Start from the control room and navigate from there.",
          toneClass: "metric-card--blue",
        },
        {
          key: "covered-areas",
          label: "Covered areas",
          value: "15+",
          detail: "Overview, catalogs, workspaces, drill-downs, and reports.",
          toneClass: "metric-card--slate",
        },
      ]}
    />
  );
}

export function App() {
  return (
    <BrowserRouter
      basename="/ui"
      future={{
        v7_relativeSplatPath: true,
        v7_startTransition: true,
      }}
    >
      <Routes>
        <Route element={<AppShell />}>
          <Route index element={<Navigate to="/release-readiness" replace />} />
          <Route path="dashboards" element={<DashboardListPage />} />
          <Route path="dashboards/:id" element={<DashboardPage />} />
          <Route path="test-cycles" element={<TestCyclesPage />} />
          <Route path="performance" element={<PerformancePage />} />
          <Route path="correlation" element={<CorrelationPage />} />
          <Route path="agent" element={<AgentPage />} />
          <Route path="release-readiness" element={<ReleaseReadinessPage />} />
          <Route path="overview" element={<OverviewPage apiRoots={apiRoots} />} />
          {catalogPages.map((page) => (
            <Route key={page.path} path={page.path} element={<CoreCatalogPage {...page} />} />
          ))}
          <Route path="apis/specs" element={<ApiSpecWorkspacePage />} />
          <Route path="apis/specs/:id" element={<ApiSpecDetailPage />} />
          <Route path="quality/executions" element={<QualityExecutionWorkspacePage />} />
          <Route path="quality/executions/:id" element={<QualityExecutionDetailPage />} />
          <Route path="pipelines/runs" element={<PipelineWorkspacePage />} />
          <Route path="reports" element={<Navigate to="/reports/portfolio" replace />} />
          <Route path="reports/executive-briefing" element={<ExecutiveBriefingReportPage />} />
          <Route path="reports/portfolio" element={<PortfolioReportPage />} />
          <Route path="reports/platform-operations" element={<PlatformOperationsReportPage />} />
          <Route path="reports/release-readiness" element={<ReleaseReadinessReportPage />} />
          <Route path="reports/exception-follow-up" element={<ExceptionFollowUpReportPage />} />
          <Route path="reports/delivery-quality" element={<DeliveryQualityReportPage />} />
          <Route path="runtime/pods" element={<PodWorkspacePage />} />
          <Route path="git/repositories" element={<GitRepositoryWorkspacePage />} />
          <Route path="metrics/executions" element={<MetricWorkspacePage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

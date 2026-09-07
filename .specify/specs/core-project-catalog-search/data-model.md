# Data Model: unified Athena web UI and reporting

## Modeling approach

This feature is primarily a browser and contract composition problem, not a single-schema change. The data model therefore has three layers:

1. Shared catalog context anchored in `core`.
2. Domain-specific read models exposed by each boot service.
3. Frontend-only view state for pages, filters, layout, and drill-down interactions.

The default rule is conservative:

- stable backend contracts that must be shared across services belong in `athena-model`
- UI-only state belongs in `athena-frontend`
- persistence changes belong in `athena-boot-init` only when the feature truly requires saved or precomputed data

## Cross-domain anchor model

The unified UI depends on `core` as the shared context source.

| Context entity | Source module      | Why it matters to the UI                          |
| -------------- | ------------------ | ------------------------------------------------- |
| `Project`      | `athena-boot-core` | primary cross-domain filter and navigation anchor |
| `Environment`  | `athena-boot-core` | required by kube, pipeline, and tms views         |
| `AppVersion`   | `athena-boot-core` | required by pipeline and tms reporting views      |
| `User`         | `athena-boot-core` | shared user lookup and attribution context        |

The UI should not invent alternate identifiers for these entities. Domain pages should reuse the same project, environment, version, and user semantics that already exist in core contracts.

## Browser-side state models

These models are frontend concerns unless they become persisted features later.

### `GlobalFilterContext`

Shared selection state used across modules.

| Field             | Type           | Purpose                                        |
| ----------------- | -------------- | ---------------------------------------------- |
| `projectCode`     | string         | cross-domain anchor for almost every workspace |
| `environmentCode` | string or null | optional context for kube, pipeline, tms       |
| `versionCode`     | string or null | optional context for release and quality views |
| `dateRange`       | object or null | reporting and dashboard time window            |
| `userQuery`       | string or null | user/person filter when supported              |

### `TableQueryState`

Shared state for list and report pages.

| Field       | Type   | Purpose                 |
| ----------- | ------ | ----------------------- |
| `page`      | number | page index              |
| `size`      | number | page size               |
| `sort`      | string | primary sort field      |
| `direction` | enum   | ascending or descending |
| `filters`   | map    | page-specific filters   |

### `WorkspaceDescriptor`

Frontend routing and navigation metadata.

| Field             | Type   | Purpose                                      |
| ----------------- | ------ | -------------------------------------------- |
| `id`              | string | stable workspace key                         |
| `routePrefix`     | string | UI route root                                |
| `gatewayPrefix`   | string | matching backend route prefix                |
| `requiredContext` | list   | which global filter keys are needed          |
| `capabilities`    | list   | tables, dashboards, drill-down, export, etc. |

### `DashboardViewModel`

Curated dashboard state used in the UI.

| Field         | Type              | Purpose                                |
| ------------- | ----------------- | -------------------------------------- |
| `title`       | string            | dashboard title                        |
| `cards`       | list              | KPI or summary cards                   |
| `sections`    | list              | charts, tables, and drill-down modules |
| `filters`     | object            | dashboard-local filter state           |
| `lastUpdated` | timestamp or null | freshness indicator                    |

## Backend contract expectations

The UI will only scale across all boot modules if service contracts are predictable.

### List and search endpoints

Every module exposed in the UI should support, directly or via additive endpoints:

- list and/or search access suitable for browser pages
- explicit detail retrieval for drill-down
- predictable pagination and sorting semantics
- stable filter names for common concepts where practical

### Summary and report endpoints

Report-heavy modules should expose additive contracts for:

- summary cards
- time-series or trend data
- grouped counts or status rollups
- drill-down links back to detailed records

These contracts belong in `athena-model` only if they are shared and stable. If a report shape is local to one page and one service, keep the adapter local to that service and the frontend.

### Error response handling

The UI needs a predictable way to render service failures. If error shapes differ materially between modules, normalize them with additive response handling before scaling out the browser experience.

## Domain model expectations by module

| Domain module          | Minimum UI-facing read model expectation                             |
| ---------------------- | -------------------------------------------------------------------- |
| `athena-boot-core`     | project, environment, version, and user list/search/detail contracts |
| `athena-boot-metric`   | scorecard, trend, and action summary models                          |
| `athena-boot-pipeline` | run history, duration, status, and release-flow summary models       |
| `athena-boot-tms`      | execution, quality, and coverage summary models                      |
| `athena-boot-spec`     | API inventory, drift, and documentation status models                |
| `athena-boot-git`      | repository, branch, commit, and activity list/detail models          |
| `athena-boot-kube`     | workload, environment, and runtime health models                     |

## Persistence and migration rules

No schema change should be assumed for the first UI slice.

Use `athena-boot-init` migrations only if one of these becomes necessary:

- saved dashboards or saved report presets
- cached or precomputed aggregates that cannot be rebuilt cheaply on demand
- metadata needed to configure UI-visible report catalogs

If persistence is added later:

1. define the stable DTOs in `athena-model`
2. choose the owning service for the persisted contract
3. add Flyway migrations through `athena-boot-init`
4. add service and feign support before frontend integration

## Contract synchronization rule

For each domain, the implementation order should be:

1. shared DTO changes in `athena-model`, only if required
2. repository, service, and controller work in the owning boot service
3. matching updates in the paired `*-feign` module
4. gateway adjustments only if routing, aliasing, or security behavior changes
5. frontend integration
6. integration and end-to-end tests

## Event and sync considerations

- Athena remains service-owned for source-of-truth data. The browser reads data; it does not replace ingestion flows.
- Cross-domain dashboards should prefer live or additive summary endpoints first.
- If caching is introduced later, it must be explicitly rebuildable and must not become a hidden source of truth.

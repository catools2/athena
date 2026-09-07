# Implementation Plan: unified Athena web UI and reporting

## Goal

Deliver one browser-based Athena application that:

- uses `athena-frontend` as the UI surface
- works across `core`, `git`, `kube`, `metric`, `pipeline`, `spec`, and `tms`
- routes all browser traffic through `athena-gateway`
- plans and closes prerequisite backend gaps before page implementation depends on them
- preserves existing service behavior for non-browser clients

## Exact impacted modules

- `athena-frontend`
- `athena-gateway`
- `athena-model`
- `athena-boot-core` and `athena-boot-core-feign`
- `athena-boot-git` and `athena-boot-git-feign`
- `athena-boot-kube` and `athena-boot-kube-feign`
- `athena-boot-metric` and `athena-boot-metric-feign`
- `athena-boot-pipeline` and `athena-boot-pipeline-feign`
- `athena-boot-spec` and `athena-boot-spec-feign`
- `athena-boot-tms` and `athena-boot-tms-feign`
- `athena-boot-init` only if persistence or aggregate storage becomes necessary

## Pre-implementation rule

Because the UI must work with all boot app modules, no domain page should start until its prerequisite checklist is complete or explicitly waived. The point is to avoid building frontend pages on top of service contracts that are not yet browser-ready.

For every domain, use this implementation order:

1. `athena-model` changes, only if a stable shared DTO is needed.
2. Repository, service, and controller work in the owning boot service.
3. Matching updates in the paired `*-feign` module.
4. `athena-gateway` changes only if routing, aliasing, or security behavior changes.
5. `athena-frontend` integration.
6. Service, gateway, and end-to-end tests.

## MVP route map

- `/overview`
- `/catalog/projects`
- `/catalog/environments`
- `/catalog/versions`
- `/catalog/users`
- `/metrics/*`
- `/pipelines/*`
- `/quality/*`
- `/apis/*`
- `/git/*`
- `/runtime/*`
- `/reports/*`

## Phase 0 — Scope and deployment decisions

Work:

- Confirm that the first release must span all seven user-facing boot services.
- Decide whether rollout is internal-only, read-only behind trusted network controls, or authenticated for external users.
- Decide how `athena-frontend` is deployed: separate static application behind the gateway or assets served by the gateway.
- Freeze the MVP route map and report list.

Exit criteria:

- Approved scope statement.
- Approved access posture.
- Approved deploy model.
- Frozen MVP route map.

## Phase 1 — Boot-service readiness matrix and prerequisite backlog

Impacted modules:

- all seven user-facing boot services
- all seven matching `*-feign` modules
- `athena-gateway`
- `athena-frontend` for dependency awareness only

Work:

- Create a readiness matrix for `core`, `git`, `kube`, `metric`, `pipeline`, `spec`, and `tms`.
- For each module, verify route availability, list/search/detail coverage, filter and pagination behavior, error response shape, demo/test data needs, and likely summary/report endpoint gaps.
- Convert every failing prerequisite into explicit backlog items before UI page work begins.
- Identify which gaps require only service changes, which require shared DTOs, and which affect gateway behavior.

Minimum checklist per module:

- stable route through the gateway
- browser-consumable read/list/detail contract
- documented filter and sort behavior
- predictable error handling
- test or demo data path for UI validation

Module-specific focus:

- `athena-boot-core`: verify it can drive project, environment, version, and user selectors used everywhere else.
- `athena-boot-metric`: identify summary and trend endpoints required for dashboard cards.
- `athena-boot-pipeline`: identify run history and delivery summary endpoints.
- `athena-boot-tms`: identify execution and quality summary endpoints.
- `athena-boot-spec`: identify API inventory and drift endpoints.
- `athena-boot-git`: verify repository and activity drill-down coverage.
- `athena-boot-kube`: verify runtime and environment health coverage.

Exit criteria:

- Each module is marked ready, partially ready with scheduled gap work, or explicitly deferred.
- No UI workspace begins without a recorded readiness state.

Initial readiness snapshot from current evidence:

- `athena-boot-core`: ready for the shared-context phase.
- `athena-boot-tms`: partially ready and currently the strongest non-core module.
- `athena-boot-git`, `athena-boot-kube`, `athena-boot-pipeline`, `athena-boot-spec`: partially ready.
- `athena-boot-metric`: not ready for dashboard-driven UI work yet.

Initial prerequisite backlog to seed Phase 1:

1. Expand `athena-boot-metric` with list, filter, summary, and trend/report endpoints; update `athena-boot-metric-feign`; add integration coverage.
2. Expand `athena-boot-spec` with inventory/list and report-oriented read endpoints; update `athena-boot-spec-feign`; add integration coverage.
3. Expand `athena-boot-pipeline` with browser-friendly history and summary endpoints and add missing controller-level IT coverage for status, execution, and scenario flows.
4. Review `athena-boot-tms` for consistent filter/list behavior and add summary contracts needed by dashboard pages.
5. Expand `athena-boot-git` from keyword lookup to list/filter/drill-down support suitable for browser pages.
6. Expand `athena-boot-kube` beyond pod-centric access to include runtime and environment-health read models.
7. Validate `athena-boot-core` selector payloads and shared filter semantics, then treat it as the anchor for the rest of the UI.

Detailed implementation tasks for backlog item 1: `athena-boot-metric`

Current evidence:

- `MetricController` exposes only create and detail retrieval.
- `MetricFeignClient` mirrors only `POST /metric` and `GET /metric/{id}`.
- Existing IT coverage verifies save flows only.

Concrete tasks:

1. `athena-model`
   - Decide whether `MetricDto` is sufficient for browser list pages.
   - If not, add additive read DTOs for:
     - metric list rows: `id`, `project`, `environment`, `action.name`, `action.category`, `action.type`, `action.target`, `duration`, `actionTime`
     - metric summary cards: total count, average duration, p95 duration, latest action time
     - metric trend points: bucket start, count, average duration, p95 duration
2. `athena-boot-metric`
   - Add an additive collection endpoint for browser list pages instead of relying only on `GET /metric/{id}`.
   - Support filters for `project`, `environment`, `action.name`, `action.category`, `action.type`, `action.target`, and time range (`from`, `to`).
   - Add explicit pagination and sorting to the collection endpoint.
   - Add an additive summary endpoint for KPI cards.
   - Add an additive trend endpoint for charting over time buckets.
   - Keep the existing create and detail endpoints unchanged.
3. `athena-boot-metric-feign`
   - Mirror the collection, summary, and trend endpoints in `MetricFeignClient`.
   - Keep the current `save()` and `getById()` contract intact.
4. Test work
   - Add controller ITs for filtered collection reads, pagination, sorting, empty results, summary responses, and trend responses.
   - Add at least one negative-path test for invalid filter combinations or malformed date ranges.
5. UI dependency outcome
   - The metrics workspace may start only after the module can power:
     - a metric inventory table
     - summary cards on `/overview` or `/metrics/*`
     - a time-series chart without browser-side reconstruction from raw detail calls

Sprint-sized checklist for backlog item 1: `athena-boot-metric`

Module: `athena-model`
File area: `athena-model/src/main/java/org/catools/athena/model/metrics/`

- [ ] Decide whether `MetricDto` remains detail-only for the UI.
- [ ] Add additive list-row DTOs if the browser needs lighter collection payloads.
- [ ] Add additive summary DTOs for KPI cards if they become stable shared contracts.
- [ ] Add additive trend-point DTOs for time-bucket chart responses if they become stable shared contracts.

Module: `athena-boot-metric`
File area: `athena-boot-metric/src/main/java/org/catools/athena/metric/controller/`

- [ ] Add a paged/filterable collection endpoint for metric inventory.
- [ ] Add a summary endpoint for KPI cards.
- [ ] Add a trend endpoint for time-series views.
      File area: `athena-boot-metric/src/main/java/org/catools/athena/metric/common/service/`
- [ ] Implement filter handling for `project`, `environment`, action fields, and time range.
- [ ] Implement pagination and sorting for metric collections.
- [ ] Implement summary aggregation for card-level responses.
- [ ] Implement trend aggregation for time-bucket responses.
      File area: `athena-boot-metric/src/main/java/org/catools/athena/metric/common/repository/`
- [ ] Add repository or query support for list, summary, and trend reads.

Module: `athena-boot-metric-feign`
File area: `athena-boot-metric-feign/src/main/java/org/catools/athena/metric/feign/`

- [ ] Extend `MetricFeignClient` with collection, summary, and trend methods.
- [ ] Keep `save()` and `getById()` unchanged for existing consumers.

Module: `athena-boot-metric`
File area: `athena-boot-metric/src/it/java/org/catools/athena/metric/controler/`

- [ ] Add IT coverage for filtered collection reads.
- [ ] Add IT coverage for pagination and sorting.
- [ ] Add IT coverage for summary responses.
- [ ] Add IT coverage for trend responses.
- [ ] Add at least one negative-path IT for invalid date or filter combinations.

Detailed implementation tasks for backlog item 2: `athena-boot-spec`

Current evidence:

- `ApiSpecController` exposes create, detail, and lookup by `project` plus `name`.
- `ApiSpecFeignClient` mirrors only lookup, detail, and save-or-update.
- Existing IT coverage verifies save/update, lookup by id, and lookup by project plus name.

Concrete tasks:

1. `athena-model`
   - Keep `ApiSpecDto` for detail pages.
   - Add a lightweight additive inventory DTO if browser list pages should avoid loading full `paths` collections.
   - Recommended inventory fields: `id`, `project`, `name`, `title`, `version`, `pathCount`, `firstTimeSeen`, `lastSyncTime`.
   - Add a summary DTO only if freshness or inventory cards are shared beyond the frontend.
2. `athena-boot-spec`
   - Add an additive inventory/list endpoint instead of forcing the UI to depend on the single-record lookup contract.
   - Support filters for `project`, `name`, `title`, `version`, and sync-time window.
   - Add explicit pagination and sorting to the inventory endpoint.
   - Preserve `GET /spec?project=&name=` as the targeted lookup contract for existing consumers.
   - Add an additive summary endpoint for API catalog cards such as spec count, path count, and freshness-oriented rollups derived from `lastSyncTime`.
   - Reuse `GET /spec/{id}` for the detail page unless a lighter detail contract is required later.
3. `athena-boot-spec-feign`
   - Mirror the inventory and summary endpoints in `ApiSpecFeignClient`.
   - Keep the current lookup and detail methods unchanged.
4. Test work
   - Add controller ITs for inventory filtering, pagination, sorting, empty results, and summary responses.
   - Add at least one test that proves the lightweight inventory contract does not require full path expansion.
5. UI dependency outcome
   - The spec workspace may start only after the module can power:
     - an API catalog inventory table
     - detail drill-down via `getById()`
     - freshness or inventory summary cards on `/apis/*` and `/overview`

Sprint-sized checklist for backlog item 2: `athena-boot-spec`

Module: `athena-model`
File area: `athena-model/src/main/java/org/catools/athena/model/apispec/`

- [ ] Keep `ApiSpecDto` as the detail contract unless a lighter detail variant is justified.
- [ ] Add an additive inventory DTO for list pages if full `paths` loading is too heavy.
- [ ] Add an additive summary DTO only if inventory and freshness cards become stable shared contracts.

Module: `athena-boot-spec`
File area: `athena-boot-spec/src/main/java/org/catools/athena/spec/rest/controller/`

- [ ] Add an inventory/list endpoint with pagination and sorting.
- [ ] Add a summary endpoint for API inventory cards.
- [ ] Preserve `GET /spec?project=&name=` and `GET /spec/{id}` unchanged.
      File area: `athena-boot-spec/src/main/java/org/catools/athena/spec/common/service/`
- [ ] Implement inventory filtering for `project`, `name`, `title`, `version`, and sync-time range.
- [ ] Implement lightweight inventory mapping that avoids full path expansion when not needed.
- [ ] Implement summary aggregation for inventory and freshness cards.
      File area: `athena-boot-spec/src/main/java/org/catools/athena/spec/common/repository/`
- [ ] Add repository or query support for inventory and summary reads.

Module: `athena-boot-spec-feign`
File area: `athena-boot-spec-feign/src/main/java/org/catools/athena/spec/feign/`

- [ ] Extend `ApiSpecFeignClient` with inventory and summary methods.
- [ ] Keep `search()`, `getById()`, and `saveOrUpdate()` intact for existing consumers.

Module: `athena-boot-spec`
File area: `athena-boot-spec/src/it/java/org/catools/athena/spec/controller/`

- [ ] Add IT coverage for inventory filtering.
- [ ] Add IT coverage for pagination and sorting.
- [ ] Add IT coverage for summary responses.
- [ ] Add IT coverage proving the inventory contract does not require full path expansion.

Detailed implementation tasks for backlog item 3: `athena-boot-pipeline`

Current evidence:

- `PipelineFeignClient` exposes targeted last-pipeline lookup, detail lookup, end-date update, and save-or-update.
- `PipelineExecutionStatusFeignClient` exposes collection and keyed lookups for execution statuses.
- `PipelineExecutionFeignClient` and `PipelineScenarioExecutionFeignClient` expose create and detail retrieval only.
- Existing IT coverage lives mainly in `PipelineControllerIT`, which verifies save, targeted lookup, status reads, execution create/detail, and scenario create/detail.

Concrete tasks:

1. `athena-model`
   - Decide whether `PipelineDto` remains detail and targeted-lookup oriented.
   - Add additive history-row DTOs for browser inventory pages if the UI should not bind directly to `PipelineDto` collections.
   - Recommended history fields: `id`, `name`, `number`, `project`, `environment`, `version`, `startDate`, `endDate`, derived duration, and latest execution status if available.
   - Add additive summary DTOs for run counts, duration KPIs, and status breakdown cards.
   - Add additive execution and scenario list-row DTOs only if drill-down pages need paged collection responses rather than by-id retrieval.
2. `athena-boot-pipeline`
   - Add an additive history/list endpoint for pipelines rather than relying only on targeted `getLastPipeline(...)` lookup.
   - Support filters for `project`, `environment`, `version`, `name`, `number`, date range, and optionally latest execution status.
   - Add explicit pagination and sorting to pipeline history reads.
   - Add additive summary endpoints for pipeline run counts, duration rollups, and status breakdowns.
   - Add additive collection endpoints for executions and scenario executions if the UI requires drill-down tables, filtered by pipeline id, status, executor, and time window.
   - Keep the existing targeted lookup, detail, update, and save endpoints unchanged.
3. `athena-boot-pipeline-feign`
   - Extend `PipelineFeignClient` with history and summary methods.
   - Extend `PipelineExecutionFeignClient` and `PipelineScenarioExecutionFeignClient` with collection methods if additive drill-down endpoints are introduced.
   - Keep existing lookup and mutation methods intact.
   - Reuse `PipelineExecutionStatusFeignClient.getAll()` unless a new summary or filter contract is added there.
4. Test work
   - Add dedicated IT coverage for history filtering, pagination, and sorting instead of relying only on the current multi-purpose IT class.
   - Add IT coverage for summary responses.
   - Add IT coverage for execution and scenario collection reads if those endpoints are added.
   - Add at least one negative-path test for invalid date ranges or unsupported filter combinations.
5. UI dependency outcome
   - The pipeline workspace may start only after the module can power:
     - a pipeline run history table
     - summary cards and status breakdowns on `/pipelines/*` and `/overview`
     - execution and scenario drill-down tables without chaining by-id calls in the browser

Sprint-sized checklist for backlog item 3: `athena-boot-pipeline`

Module: `athena-model`
File area: `athena-model/src/main/java/org/catools/athena/model/pipeline/`

- [ ] Decide whether `PipelineDto` remains lookup/detail only.
- [ ] Add additive history-row DTOs for pipeline inventory if needed.
- [ ] Add additive summary DTOs for run counts, durations, and status cards if they become stable shared contracts.
- [ ] Add additive execution/scenario list DTOs if browser drill-down tables need lighter payloads.

Module: `athena-boot-pipeline`
File area: `athena-boot-pipeline/src/main/java/org/catools/athena/pipeline/rest/controller/`

- [ ] Add pipeline history/list endpoint with filters, pagination, and sorting.
- [ ] Add pipeline summary endpoint(s) for dashboard cards.
- [ ] Add execution and scenario collection endpoints if drill-down tables need them.
- [ ] Preserve existing targeted lookup and mutation endpoints.
      File area: `athena-boot-pipeline/src/main/java/org/catools/athena/pipeline/common/service/`
- [ ] Implement history filtering for project, environment, version, name, number, and time range.
- [ ] Implement derived duration and summary aggregation logic.
- [ ] Implement execution/scenario collection reads for browser drill-down workflows.
      File area: `athena-boot-pipeline/src/main/java/org/catools/athena/pipeline/common/repository/`
- [ ] Add repository or query support for history, summary, and drill-down collection reads.

Module: `athena-boot-pipeline-feign`
File area: `athena-boot-pipeline-feign/src/main/java/org/catools/athena/pipeline/feign/`

- [ ] Extend `PipelineFeignClient` with history and summary methods.
- [ ] Extend `PipelineExecutionFeignClient` with collection methods if added.
- [ ] Extend `PipelineScenarioExecutionFeignClient` with collection methods if added.
- [ ] Keep existing targeted lookup and mutation methods unchanged.

Module: `athena-boot-pipeline`
File area: `athena-boot-pipeline/src/it/java/org/catools/athena/pipeline/controller/`

- [ ] Add IT coverage for pipeline history filtering.
- [ ] Add IT coverage for pagination and sorting.
- [ ] Add IT coverage for summary responses.
- [ ] Add IT coverage for execution/scenario collection reads if added.
- [ ] Add at least one negative-path IT for invalid date or filter combinations.

Detailed implementation tasks for backlog item 4: `athena-boot-tms`

Current evidence:

- `TestExecutionFeignClient` already exposes collection reads by `itemCode` and `cycleCode`, detail lookup, and save-or-update.
- `TestCycleFeignClient` exposes search by keyword, detail lookup, pattern lookup, and save.
- Current controllers expose multiple collection/detail reads across executions, cycles, items, item types, statuses, priorities, transitions, and sync info.
- Existing IT coverage is broad, with dedicated controller tests for cycles, executions, items, item types, and status transitions.

Concrete tasks:

1. `athena-model`
   - Decide whether `TestCycleDto` should remain a detail-oriented DTO because it embeds `testExecutions` and may be heavy for list pages.
   - Add a lightweight additive cycle inventory DTO if browser list pages should not always load nested execution sets.
   - Recommended cycle inventory fields: `id`, `code`, `name`, `project`, `version`, `startDate`, `endDate`, and derived execution count.
   - Add additive summary DTOs for execution totals by status, cycle progress, and executor rollups if those contracts are shared beyond the frontend.
   - Keep `TestExecutionDto` unless lighter list rows become necessary.
2. `athena-boot-tms`
   - Add additive list endpoints for cycles and executions with explicit pagination and sorting rather than relying only on the current search and filtered-set patterns.
   - Support filters for `project`, `version`, `cycleCode`, `itemCode`, `status`, `executor`, and execution time windows where the underlying data supports them.
   - Preserve existing `search(keyword)`, `findLastByPattern(...)`, `getAll(itemCode, cycleCode)`, and detail endpoints unchanged for existing consumers.
   - Add additive summary endpoints for dashboard cards such as execution totals by status, cycle progress/completion, and executor or item rollups.
   - Reuse existing reference-data controllers for statuses, priorities, item types, and transitions where possible instead of inventing duplicate lookup endpoints.
3. `athena-boot-tms-feign`
   - Extend `TestCycleFeignClient` with inventory and summary methods.
   - Extend `TestExecutionFeignClient` with paged/filterable list and summary methods.
   - Extend additional feign clients only if new summary contracts land on their paired controllers.
   - Keep current search, detail, and save methods intact.
4. Test work
   - Add IT coverage for cycle inventory filtering, pagination, and sorting.
   - Add IT coverage for execution list filtering, pagination, and sorting.
   - Add IT coverage for summary responses and empty-result cases.
   - Add at least one test proving list pages can use a lighter cycle inventory contract without requiring nested execution expansion.
5. UI dependency outcome
   - The TMS workspace may start only after the module can power:
     - a cycle inventory table
     - an execution table with browser-friendly filters
     - status and progress summary cards on `/quality/*` and `/overview`

Sprint-sized checklist for backlog item 4: `athena-boot-tms`

Module: `athena-model`
File area: `athena-model/src/main/java/org/catools/athena/model/tms/`

- [ ] Decide whether `TestCycleDto` remains detail-only because it embeds executions.
- [ ] Add a lightweight cycle inventory DTO if list pages should avoid nested execution expansion.
- [ ] Add additive summary DTOs for status totals, cycle progress, and executor rollups if they become stable shared contracts.
- [ ] Keep `TestExecutionDto` unless lighter list rows are required.

Module: `athena-boot-tms`
File area: `athena-boot-tms/src/main/java/org/catools/athena/tms/rest/controller/`

- [ ] Add paged/filterable cycle inventory endpoint(s).
- [ ] Add paged/filterable execution list endpoint(s).
- [ ] Add summary endpoint(s) for quality dashboard cards.
- [ ] Preserve current search, pattern lookup, detail, and save endpoints.
      File area: `athena-boot-tms/src/main/java/org/catools/athena/tms/common/service/`
- [ ] Implement cycle and execution filters for project, version, cycle, item, status, executor, and time windows where supported.
- [ ] Implement pagination and sorting for new list endpoints.
- [ ] Implement summary aggregation for status totals and cycle progress.
      File area: `athena-boot-tms/src/main/java/org/catools/athena/tms/common/repository/`
- [ ] Add repository or query support for cycle inventory, execution list, and summary reads.

Module: `athena-boot-tms-feign`
File area: `athena-boot-tms-feign/src/main/java/org/catools/athena/tms/feign/`

- [ ] Extend `TestCycleFeignClient` with inventory and summary methods.
- [ ] Extend `TestExecutionFeignClient` with paged/filterable list and summary methods.
- [ ] Extend other feign clients only if new summary contracts land on their paired controllers.
- [ ] Keep current search, detail, and save methods unchanged.

Module: `athena-boot-tms`
File area: `athena-boot-tms/src/it/java/org/catools/athena/tms/controller/`

- [ ] Add IT coverage for cycle inventory filtering.
- [ ] Add IT coverage for execution list filtering.
- [ ] Add IT coverage for pagination and sorting.
- [ ] Add IT coverage for summary responses and empty-result cases.
- [ ] Add at least one IT proving list pages can avoid nested execution expansion when a lightweight inventory contract is used.

Detailed implementation tasks for backlog item 5: `athena-boot-git`

Current evidence:

- `GitRepositoryFeignClient` exposes keyword search, detail lookup, and save-or-update only.
- `CommitFeignClient` exposes hash lookup, detail lookup, and save-or-update only.
- `GitRepositoryDto` is already lightweight enough for inventory-style use, but `CommitDto` is detail-heavy because it includes diff entries, tags, and metadata.
- Existing IT coverage verifies save/update, detail lookup, and keyword/hash lookup behavior, but not list, filter, or pagination behavior.

Concrete tasks:

1. `athena-model`
   - Keep `GitRepositoryDto` unless a dedicated repository inventory DTO becomes necessary for paging metadata.
   - Add a lightweight additive commit inventory DTO so browser list pages do not always load `diffEntries`, `tags`, and full metadata.
   - Recommended commit inventory fields: `id`, `hash`, `repository`, `parentHash`, `shortMessage`, `commitTime`, `author`, `committer`, and lightweight counters such as diff-entry count and tag count if useful.
   - Add additive summary DTOs only if repository freshness or commit activity cards become stable shared contracts.
2. `athena-boot-git`
   - Add an additive repository inventory/list endpoint with explicit pagination and sorting instead of relying only on `GET /repo?keyword=`.
   - Support repository filters for `name`, `url`, and `lastSync` time window.
   - Add an additive commit inventory/list endpoint with explicit pagination and sorting instead of relying only on `GET /commit?hash=`.
   - Support commit filters for `repository`, `hash`, `author`, `committer`, `commitTime` range, and optionally partial short message search.
   - Preserve `GET /repo?keyword=` and `GET /commit?hash=` as targeted lookup contracts for existing consumers.
   - Reuse `GET /repo/{id}` and `GET /commit/{id}` for detail drill-down pages unless lighter detail variants are required later.
   - Add additive summary endpoints only if the UI needs repository sync freshness cards or commit activity rollups on `/git/*` or `/overview`.
3. `athena-boot-git-feign`
   - Extend `GitRepositoryFeignClient` with repository inventory methods.
   - Extend `CommitFeignClient` with commit inventory methods.
   - Add summary methods only if new summary endpoints are introduced.
   - Keep current search, detail, and save methods unchanged.
4. Test work
   - Add IT coverage for repository inventory filtering, pagination, and sorting.
   - Add IT coverage for commit inventory filtering, pagination, and sorting.
   - Add IT coverage proving commit inventory pages can avoid full diff/tag expansion.
   - Add negative-path tests for invalid filters or unsupported paging/sort combinations.
5. UI dependency outcome
   - The git workspace may start only after the module can power:
     - a repository inventory table
     - a commit activity table with browser-friendly filters
     - repository and commit detail drill-down without keyword-only navigation

Sprint-sized checklist for backlog item 5: `athena-boot-git`

Module: `athena-model`
File area: `athena-model/src/main/java/org/catools/athena/model/git/`

- [ ] Keep `GitRepositoryDto` unless paging or inventory needs justify a dedicated repository inventory DTO.
- [ ] Add a lightweight commit inventory DTO that avoids full diff/tag expansion for list pages.
- [ ] Add additive summary DTOs only if repository freshness or commit activity cards become stable shared contracts.

Module: `athena-boot-git`
File area: `athena-boot-git/src/main/java/org/catools/athena/git/rest/controller/`

- [ ] Add paged/filterable repository inventory endpoint(s).
- [ ] Add paged/filterable commit inventory endpoint(s).
- [ ] Add summary endpoint(s) only if the UI requires git activity or freshness cards.
- [ ] Preserve current keyword/hash lookup, detail, and save endpoints.
      File area: `athena-boot-git/src/main/java/org/catools/athena/git/common/service/`
- [ ] Implement repository filters for name, url, and last-sync window.
- [ ] Implement commit filters for repository, hash, author, committer, message, and time range.
- [ ] Implement pagination and sorting for new inventory endpoints.
- [ ] Implement summary aggregation only if summary contracts are added.
      File area: `athena-boot-git/src/main/java/org/catools/athena/git/common/repository/`
- [ ] Add repository or query support for repository and commit inventory reads.
- [ ] Add query support for any git summary responses introduced.

Module: `athena-boot-git-feign`
File area: `athena-boot-git-feign/src/main/java/org/catools/athena/git/feign/`

- [ ] Extend `GitRepositoryFeignClient` with inventory methods.
- [ ] Extend `CommitFeignClient` with inventory methods.
- [ ] Add summary methods only if new summary endpoints are introduced.
- [ ] Keep current search, detail, and save methods unchanged.

Module: `athena-boot-git`
File area: `athena-boot-git/src/it/java/org/catools/athena/git/controller/`

- [ ] Add IT coverage for repository inventory filtering.
- [ ] Add IT coverage for repository pagination and sorting.
- [ ] Add IT coverage for commit inventory filtering.
- [ ] Add IT coverage for commit pagination and sorting.
- [ ] Add IT coverage proving inventory pages can avoid full commit diff/tag expansion.
- [ ] Add at least one negative-path IT for invalid filters or paging combinations.

Detailed implementation tasks for backlog item 6: `athena-boot-kube`

Current evidence:

- `PodFeignClient` exposes `getAll(project, namespace)`, `getByNameAndNamespace(name, namespace)`, `getById(id)`, and `saveOrUpdate(pod)`.
- `PodController` is the current browser-facing surface and is centered entirely on pods.
- `PodDto` is detail-heavy because it includes containers, metadata, annotations, selectors, and labels.
- Existing IT coverage verifies pod save/update and pod lookups, but not broader runtime-health or environment-oriented browser use cases.

Concrete tasks:

1. `athena-model`
   - Keep `PodDto` for detail pages.
   - Add a lightweight additive pod inventory DTO so list pages do not always load full container and metadata structures.
   - Recommended pod inventory fields: `id`, `uid`, `name`, `namespace`, `hostname`, `nodeName`, `createdAt`, `deletedAt`, `lastSync`, `project`, and a flattened pod status value.
   - Add additive summary DTOs for runtime-health cards such as pod totals by status, namespace rollups, and freshness indicators if those become stable shared contracts.
   - Add a namespace or environment-health DTO only if the service introduces a distinct summary/read model for that view.
2. `athena-boot-kube`
   - Keep the existing `GET /pods?project=&namespace=` flow, but add an additive paged/filterable pod inventory endpoint that can work without forcing a namespace-specific narrow query every time.
   - Support filters for `project`, `namespace`, `name`, `nodeName`, pod status, and time windows around `createdAt`, `deletedAt`, or `lastSync` as supported by the underlying data.
   - Add explicit pagination and sorting to pod inventory reads.
   - Add additive summary endpoints for runtime-health cards such as pod counts by status, namespace rollups, and last-sync freshness.
   - Add an additive environment-health or namespace summary endpoint if the UI needs a dedicated runtime overview on `/runtime/*`.
   - Preserve current detail and keyed lookup endpoints unchanged for existing consumers.
3. `athena-boot-kube-feign`
   - Extend `PodFeignClient` with pod inventory and summary methods.
   - Add environment-health or namespace-summary methods only if those endpoints are introduced.
   - Keep current lookup and save methods unchanged.
4. Test work
   - Add IT coverage for pod inventory filtering, pagination, and sorting.
   - Add IT coverage for runtime-health summary responses.
   - Add IT coverage proving list pages can avoid full container and metadata expansion when using inventory contracts.
   - Add negative-path tests for invalid namespace/project combinations or unsupported filter ranges.
5. UI dependency outcome
   - The kube workspace may start only after the module can power:
     - a pod inventory table with browser-friendly filters
     - runtime-health summary cards on `/runtime/*` and optionally `/overview`
     - pod detail drill-down without requiring the browser to load only namespace-scoped sets first

Sprint-sized checklist for backlog item 6: `athena-boot-kube`

Module: `athena-model`
File area: `athena-model/src/main/java/org/catools/athena/model/kube/`

- [ ] Keep `PodDto` as the detail contract unless a lighter detail variant is needed later.
- [ ] Add a lightweight pod inventory DTO that avoids full container and metadata expansion for list pages.
- [ ] Add additive runtime-health summary DTOs if status and freshness cards become stable shared contracts.
- [ ] Add a namespace or environment-health DTO only if dedicated runtime overview responses are introduced.

Module: `athena-boot-kube`
File area: `athena-boot-kube/src/main/java/org/catools/athena/kube/rest/controler/`

- [ ] Add a paged/filterable pod inventory endpoint.
- [ ] Add runtime-health summary endpoint(s) for pod status and freshness cards.
- [ ] Add namespace or environment summary endpoint(s) only if the UI needs dedicated overview pages.
- [ ] Preserve current keyed lookup and save endpoints.
      File area: `athena-boot-kube/src/main/java/org/catools/athena/kube/common/service/`
- [ ] Implement filters for project, namespace, name, node, status, and supported time windows.
- [ ] Implement pagination and sorting for pod inventory reads.
- [ ] Implement runtime-health summary aggregation.
- [ ] Implement namespace or environment-health aggregation only if those summary endpoints are added.
      File area: `athena-boot-kube/src/main/java/org/catools/athena/kube/common/repository/`
- [ ] Add repository or query support for pod inventory and summary reads.

Module: `athena-boot-kube-feign`
File area: `athena-boot-kube-feign/src/main/java/org/catools/athena/core/kube/`

- [ ] Extend `PodFeignClient` with inventory and summary methods.
- [ ] Add environment or namespace summary methods only if new summary endpoints are introduced.
- [ ] Keep current keyed lookup and save methods unchanged.

Module: `athena-boot-kube`
File area: `athena-boot-kube/src/it/java/org/catools/athena/kube/controler/`

- [ ] Add IT coverage for pod inventory filtering.
- [ ] Add IT coverage for pagination and sorting.
- [ ] Add IT coverage for runtime-health summary responses.
- [ ] Add IT coverage proving inventory pages can avoid full container and metadata expansion.
- [ ] Add at least one negative-path IT for invalid namespace/project or filter combinations.

## Phase 2 — Frontend and gateway foundation

Impacted modules:

- `athena-frontend`
- `athena-gateway`

Work:

- Turn `athena-frontend` from scaffold into a running application with build scripts, route bootstrapping, API clients, providers, and test tooling.
- Build the shared shell: navigation, global filter area, loading and error handling, empty states, and layout primitives.
- Implement a single browser API layer that targets gateway prefixes only.
- Add gateway support for UI hosting or reverse proxying, plus any required CORS or security header behavior.

Validation:

- frontend route smoke test
- gateway smoke test proving UI assets and proxied APIs coexist correctly

## Phase 3 — Core catalog and shared context first

Impacted modules:

- `athena-model`
- `athena-boot-core`
- `athena-boot-core-feign`
- `athena-frontend`

Work:

1. Add stable shared DTOs only if existing catalog contracts are insufficient for global selectors.
2. Make any additive service changes needed for project, environment, version, and user browsing.
3. Update `athena-boot-core-feign` to match.
4. Add or extend integration coverage.
5. Build catalog pages and global filter context in the frontend.

Reason for doing this first:

- Every other domain depends on the same core context.

## Phase 4 — Close remaining prerequisite gaps by domain

This phase exists to handle anything Phase 1 surfaced before full page implementation starts.

Impacted modules:

- whichever services were marked partially ready in the readiness matrix
- matching `*-feign` modules
- `athena-model` only when shared DTOs are justified

Work:

- implement the additive service and feign changes required to make each deferred module browser-ready
- keep gateway changes minimal unless route aliasing or security needs changed
- re-run service-level validation before starting the corresponding frontend workspace

Exit criteria:

- the target domain is marked ready in the matrix

## Phase 5 — Domain workspaces, ordered by reporting value

### Phase 5A — Report-heavy workspaces

Impacted modules:

- `athena-model`
- `athena-boot-metric`, `athena-boot-metric-feign`
- `athena-boot-pipeline`, `athena-boot-pipeline-feign`
- `athena-boot-tms`, `athena-boot-tms-feign`
- `athena-boot-spec`, `athena-boot-spec-feign`
- `athena-frontend`

Work:

1. Finalize any stable summary DTOs.
2. Implement additive service endpoints for summaries, trends, and drill-downs.
3. Update matching feign modules.
4. Add integration tests.
5. Build workspace pages and dashboards in the frontend.

### Phase 5B — Operational workspaces

Impacted modules:

- `athena-model`
- `athena-boot-git`, `athena-boot-git-feign`
- `athena-boot-kube`, `athena-boot-kube-feign`
- `athena-frontend`

Work:

1. Finalize any stable list/detail DTOs needed for UI browsing.
2. Implement additive service changes.
3. Update matching feign modules.
4. Add integration tests.
5. Build workspace pages.

## Phase 6 — Cross-domain dashboards and report pages

Impacted modules:

- `athena-frontend`
- owning boot services when additive summary endpoints are needed
- `athena-gateway` only if performance analysis justifies additional browser-facing behavior

Work:

- Build curated dashboards such as executive overview, delivery health, quality, API inventory, and runtime health.
- Prefer service-owned summary endpoints over large browser-side aggregation when dashboard complexity grows.
- Add export/report features only after page semantics are stable.

If persistence becomes mandatory:

1. define stable DTOs in `athena-model`
2. add service and persistence support in the owning service
3. add Flyway migrations through `athena-boot-init`
4. update matching feign modules
5. wire frontend integration

## Phase 7 — Rollout hardening

Impacted modules:

- `athena-gateway`
- `athena-frontend`
- any boot service affected by access control or audit requirements

Work:

- finalize access control based on the Phase 0 decision
- preserve existing gateway prefixes so existing clients do not break
- add route aliases only if a cleaner browser namespace is needed
- add audit or access logging only if operational requirements justify it
- run the UI in parallel with current service-by-service workflows before making it the primary interface

## Backward compatibility and migration concerns

- Keep service changes additive.
- Keep each `*-feign` module synchronized before frontend dependence.
- Do not expose the database directly to the browser.
- Do not move heavy domain logic into the gateway by default.
- Avoid schema changes unless persistence or precomputed aggregates are clearly required.

## Test strategy

- readiness verification per module before UI work starts
- service integration tests for new list, summary, and drill-down endpoints
- feign compatibility coverage where contracts expand
- gateway smoke tests for routing and UI delivery
- frontend route, component, and page tests
- end-to-end navigation across the gateway covering multiple domains

## Done criteria

- all seven user-facing boot modules are represented in the UI plan and pass through the readiness gate
- `athena-frontend` is a working application rather than a scaffold
- the UI can browse all seven domains through `athena-gateway`
- prerequisite service work is completed or explicitly deferred before each workspace starts
- cross-domain dashboards exist for the report-heavy domains
- no existing clients are broken by the browser rollout

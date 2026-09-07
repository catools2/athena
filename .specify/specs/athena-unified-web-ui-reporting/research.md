# Research Notes: unified Athena web UI and reporting

## Current platform evidence

- Root module inventory shows seven user-facing boot services plus gateway and matching feign modules: `athena-boot-core`, `athena-boot-git`, `athena-boot-kube`, `athena-boot-metric`, `athena-boot-pipeline`, `athena-boot-spec`, `athena-boot-tms`, `athena-gateway`, and `athena-boot-init` as migration support.
- Gateway routing already exposes all seven services behind stable prefixes: `/core`, `/git`, `/kube`, `/metric`, `/pipeline`, `/spec`, `/tms`.
- `athena-frontend` now has a runnable Phase 2 shell with a tracked Vite/React runtime, `/overview`, `/catalog/*`, `/apis/specs`, and `/metrics/executions`.
- The shell still remains intentionally thin: it routes browser traffic through `athena-gateway`, but `core` catalog pages, the additive `spec` workspace, and the first additive `metric` workspace are now wired to live service data.
- The smallest local runtime stack has now been proven end-to-end with `docker/core-compose.yml` PostgreSQL, `athena-gateway` on `8080`, frontend preview on `4173`, `athena-boot-core` on `8081`, `athena-boot-metric` on `8084`, and `athena-boot-spec` on `8086`.
- Live browser smoke now succeeds through the gateway for `/ui/overview`, `/ui/apis/specs`, and `/ui/metrics/executions`; the sampled detail route `/ui/apis/specs/19` is wired, but the current demo dataset returns `204` there, so the shell falls back to an empty detail state.

## Module inventory for UI planning

| Module                 | Current role in UI plan                 | Notes                                                                                   |
| ---------------------- | --------------------------------------- | --------------------------------------------------------------------------------------- |
| `athena-boot-core`     | shared catalog and global filter anchor | already has mature list and search behavior for project, environment, version, and user |
| `athena-boot-git`      | operational workspace                   | likely needs browser-oriented inventory and drill-down review                           |
| `athena-boot-kube`     | operational workspace                   | likely needs environment and runtime health views                                       |
| `athena-boot-metric`   | report-heavy workspace                  | strongest early dashboard candidate                                                     |
| `athena-boot-pipeline` | report-heavy workspace                  | delivery and execution reporting candidate                                              |
| `athena-boot-spec`     | report-heavy workspace                  | API inventory and drift reporting candidate                                             |
| `athena-boot-tms`      | report-heavy workspace                  | quality and execution reporting candidate                                               |
| `athena-gateway`       | browser ingress                         | already routes all services; still needs UI hosting and access-control decisions        |
| `athena-frontend`      | browser application                     | currently scaffold only                                                                 |
| `athena-model`         | shared stable DTOs                      | use only for backend contracts that are reused outside the frontend                     |
| `athena-boot-init`     | migration support only                  | only needed if UI introduces saved reports, cached aggregates, or new report tables     |

## What the UI must cover

The requested UI is not just a dashboard layer over one domain. It must span all user-facing boot app modules:

1. `core` for project, environment, version, and user selection.
2. `metric` for scorecards, action summaries, and trends.
3. `pipeline` for delivery and release flow views.
4. `tms` for test execution and quality reporting.
5. `spec` for API inventory and documentation-related views.
6. `git` for repository and code activity drill-down.
7. `kube` for runtime and environment visibility.

Because of that breadth, the feature needs a readiness phase before page implementation starts.

## Readiness prerequisites identified

### Cross-cutting prerequisites

1. Browser ingress decision.
   The UI must run only through `athena-gateway`; direct browser calls to individual boot services should be avoided.
2. Authentication and access posture.
   The project needs an explicit first-release stance: internal only, read-only behind trusted network controls, or authenticated external access.
3. Contract consistency.
   Services should expose predictable list, search, sort, pagination, and detail behavior so shared UI primitives are practical.
4. Error-shape consistency.
   Browser pages need stable error handling across services; if response shapes differ too much, adapter code and UX quality will suffer.
5. Performance strategy.
   Cross-domain dashboards will either need additive summary endpoints or careful browser composition. This decision belongs in planning, not late implementation.
6. Test and demo data.
   The UI will need deterministic test data across services for screenshots, end-to-end tests, and user acceptance.
7. Frontend runtime setup.
   `athena-frontend` needs an actual build system, test tooling, routing entry point, and deploy path.

### Per-module prerequisite checks

| Module                 | Minimum prerequisite before UI workspace starts                                                                  |
| ---------------------- | ---------------------------------------------------------------------------------------------------------------- |
| `athena-boot-core`     | confirm project, environment, version, and user endpoints can drive global selectors and cross-page filter state |
| `athena-boot-metric`   | identify summary and trend endpoints required for dashboard cards and time-series views                          |
| `athena-boot-pipeline` | identify run history, status, and duration summaries needed for delivery reporting                               |
| `athena-boot-tms`      | identify execution, coverage, and quality summaries needed for report pages                                      |
| `athena-boot-spec`     | identify inventory, drift, and documentation status endpoints needed for browser reports                         |
| `athena-boot-git`      | verify repository, branch, commit, and activity read flows are browser-ready                                     |
| `athena-boot-kube`     | verify workload, environment, and runtime-health views have usable read models                                   |
| `athena-gateway`       | settle static asset hosting or reverse proxy strategy and any CORS/security header needs                         |
| `athena-frontend`      | choose package/build/test stack and establish route bootstrapping                                                |

## Initial readiness matrix

This is the current evidence-based starting point for Phase 1. Status values are intentionally conservative.

| Module                 | Gateway route  | Current API surface evidence                                                                                                                                                                                                             | Feign and IT evidence                                                                          | Initial readiness                               | Pre-UI backlog                                                                                                                                                 |
| ---------------------- | -------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------- | ----------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `athena-boot-core`     | `/core/**`     | `ProjectController`, `EnvironmentController`, `VersionController`, and `UserController` expose collection, search, detail, save, and update patterns including `GET /all`, `GET`, `GET /{id}`, `POST`, `PUT`                             | matching feign clients for all four resources and broad IT coverage including filtering suites | Ready for shared-context phase                  | verify global selector payloads and preserve existing filter semantics for browser use                                                                         |
| `athena-boot-git`      | `/git/**`      | `GitRepositoryController` and `CommitController` expose keyword search, detail retrieval, and save-or-update                                                                                                                             | `GitRepositoryFeignClient`, `CommitFeignClient`, and two controller ITs                        | Partially ready                                 | add browser-friendly list, filter, and pagination endpoints; define repository and commit drill-down views                                                     |
| `athena-boot-kube`     | `/kube/**`     | `PodController` exposes `GET /pods`, `GET /pod`, `GET /pod/{id}`, and `POST /pod`                                                                                                                                                        | `PodFeignClient`, `PodControllerIT`, `KubeControllerIT`                                        | Partially ready                                 | add broader runtime, namespace, environment-health, and workload read models; define any missing summary endpoints                                             |
| `athena-boot-metric`   | `/metric/**`   | `MetricController` now exposes gateway-compatible summary, trend, inventory, create, and detail flows; live smoke validated `/metric/summary`, `/metric/trend`, and `/metric/all` through `athena-gateway`                               | `MetricFeignClient`, metric page wrapper, and `MetricControllerIT`                             | Partially ready, first metric UI slice active   | current contracts work for the first dashboard workspace; remaining work is richer drill-down/reporting depth and seeded demo data                             |
| `athena-boot-pipeline` | `/pipeline/**` | `PipelineController` exposes lookup, detail, save, and update; `PipelineExecutionStatusController` exposes collection reads; execution and scenario controllers expose create and detail flows                                           | four feign clients exist, but current IT evidence is centered on `PipelineControllerIT`        | Partially ready                                 | add browser-oriented history, filtering, and reporting endpoints; expand IT coverage for execution, status, and scenario flows                                 |
| `athena-boot-spec`     | `/spec/**`     | `ApiSpecController` now exposes gateway-compatible summary, freshness, drift, inventory, save, lookup, and detail flows; live smoke validated `/spec/summary`, `/spec/freshness`, `/spec/all`, and `/spec/{id}` through `athena-gateway` | `ApiSpecFeignClient`, `ApiSpecControllerIT`, `ApiSpecMapperIT`                                 | Partially ready, first non-core UI slice active | current contracts support the first full spec workspace; remaining work is richer seeded spec data and more polished detail behavior for `204`/empty responses |
| `athena-boot-tms`      | `/tms/**`      | multiple controllers already expose detail and collection reads for executions, cycles, items, statuses, priorities, transitions, and sync info                                                                                          | broad feign coverage and multiple controller ITs plus mapper IT                                | Partially ready, strongest after core           | normalize list and filter behavior where needed and add curated summary/report endpoints for dashboard pages                                                   |

## Initial prerequisite backlog implied by the matrix

1. `athena-boot-metric`
   The first metric workspace is now live through `/metrics/executions` with summary cards, daily trend, and inventory. Remaining work is deeper report flows and non-empty seeded demo data.
2. `athena-boot-spec`
   Summary, freshness, drift, inventory, and detail routing now drive the `/apis/specs` workspace. Remaining work is richer seeded data and stronger handling of `204` detail responses.
3. `athena-boot-pipeline`
   Has useful primitives, but still needs stronger history, summary, and controller-level IT coverage for UI use.
4. `athena-boot-tms`
   Has the broadest domain surface after core, but still needs report-focused summary contracts and consistency review.
5. `athena-boot-git`
   Needs list and filter semantics rather than only keyword lookup and detail retrieval.
6. `athena-boot-kube`
   Needs richer runtime and environment-health read models beyond pod-centric access.
7. `athena-boot-core`
   Lowest risk. Treat as the anchor for shared filters and selectors, not as a blocker.

## Contract and architecture observations

- `athena-boot-core` is already the identity root for the rest of the platform. This makes it the right first domain for shared UI filter context.
- The gateway is already thin and route-based. That is useful for the first iteration because it avoids adding a new aggregation layer before the browser workflows are understood.
- The gateway needs asymmetric proxy behavior: preserve `/ui/**` for the frontend preview base path, but keep stripping service prefixes such as `/metric/**` and `/spec/**` before forwarding to the boot apps.
- The frontend should not become the place where domain-specific business logic lives. If a report needs heavy aggregation, the owning boot service should expose it.
- `athena-model` should remain conservative. Only promote DTOs there when they are stable backend contracts, not just frontend conveniences.
- Visual/reporting refinements are most efficient when they reuse the existing gateway-backed summary and trend contracts. The current frontend has no chart library, so the safest professional UI path is richer shared styling plus lightweight custom SVG and bar-chart components built on top of existing report data.

## Live local smoke findings

- The demo PostgreSQL image in `docker/core-compose.yml` uses per-service users such as `athena_core_user`, `athena_metric_user`, and `athena_openapi_user`; the shared fallback `athena/athena` credentials from common defaults do not work against that image.
- `athena-boot-metric` and `athena-boot-spec` need local `ProjectFeignClient`, `EnvironmentFeignClient`, `UserFeignClient`, and `VersionFeignClient` beans because broad package scanning pulls in cached core-feign services during startup.
- `athena-gateway` must preserve `/ui/**` when proxying the frontend preview. Stripping the UI prefix breaks the Vite public base path `/ui/` and causes the browser to bounce on route loads.
- The new metric and spec workspace controllers must accept gateway-stripped paths such as `/summary`, `/freshness`, `/drift`, `/all`, and `/{id}` in addition to their legacy prefixed service-local paths so the existing `/metric/**` and `/spec/**` gateway routes continue to work.
- `athena-boot-metric` can retain a stale broken `MetricMapperImpl` after incremental rebuilds; a focused `./mvnw -pl athena-boot-metric -am -DskipTests clean package` cleared an unresolved `Action` bytecode stub and restored runtime startup.
- End-to-end smoke now confirms real gateway-backed rendering for the overview, spec workspace, and metric workspace. The current demo dataset is sparse, so those pages currently prove empty-state handling rather than populated-report fidelity.

## Recommended implementation order

1. Re-baseline the feature package to reflect the cross-domain UI scope.
2. Build a readiness matrix across all seven user-facing boot services.
3. Close the blocking prerequisite gaps per module.
4. Stand up the frontend and gateway foundation.
5. Implement `core` first for shared context.
6. Implement report-heavy domains next: `metric`, `pipeline`, `tms`, `spec`.
7. Implement operational drill-down domains: `git`, `kube`.
8. Add cross-domain dashboards only after the per-domain workspaces prove the underlying contracts.

## Open questions to resolve in planning

1. Is the first release internal-only or intended for authenticated external users?
2. Should the gateway serve frontend assets directly, or should it reverse-proxy a separate frontend deployment?
3. Which dashboards are mandatory for MVP, and which can wait until after the first unified release?
4. Do any boot services already expose summary endpoints that can be reused, or will those need to be designed now?

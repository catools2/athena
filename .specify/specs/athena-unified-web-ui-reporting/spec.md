# Feature Spec: unified Athena web UI and reporting

## Summary

- Problem: Athena exposes capabilities through multiple boot services, but external and internal users do not have one browser interface that spans all boot app modules. Each service may also require browser-readiness work before a single UI can use it consistently.
- Outcome: Define a unified web interface that works across all user-facing boot app modules, presents data with a professional reporting/dashboard theme, and explicitly plans the prerequisite backend, gateway, and contract work needed before rollout.
- User-facing boot app modules: `athena-boot-core`, `athena-boot-git`, `athena-boot-kube`, `athena-boot-metric`, `athena-boot-pipeline`, `athena-boot-spec`, `athena-boot-tms`.
- Supporting modules: `athena-frontend`, `athena-gateway`, `athena-model`, the matching `*-feign` modules, and `athena-boot-init` only when persistence or reporting tables are required.

## User Value

- Primary users: external read-heavy users, internal operators, engineering leads, and product stakeholders who need cross-domain visibility without moving between Swagger endpoints, CLIs, or service-specific tools.
- Trigger: viewing project health, delivery quality, API inventory, runtime state, and test/report outputs in one place.
- Business value: one UI reduces operational friction, makes Athena easier to adopt, and turns service data into user-facing reports instead of service-by-service inspection.

## Scope

- In scope: one browser UI in `athena-frontend`, gateway-backed access to all seven user-facing boot services, shared filters and navigation, curated dashboards and report pages, a professional BI-style presentation layer, and prerequisite contract/readiness work required to make every module consumable by the UI.
- In scope: additive backend read endpoints, report endpoints, DTOs, gateway adjustments, compatibility work in `*-feign` modules, and testing for browser-facing workflows.
- Out of scope for MVP: replacing CLI ingestion flows, direct SQL/BI access from the browser, free-form report builders, per-user dashboard authoring, and breaking existing service contracts.

## Acceptance Criteria

### AC-1: One UI shell spans all user-facing boot app modules

- Users can navigate from one shell to workspaces backed by `core`, `git`, `kube`, `metric`, `pipeline`, `spec`, and `tms`.
- The browser reaches all service capabilities through `athena-gateway`, not direct service URLs.
- Verified by: end-to-end navigation through the gateway across all seven route prefixes.

### AC-2: A readiness gate exists before domain pages ship

- Before a module is exposed in the UI, it must have a documented readiness result covering route availability, browser-consumable read endpoints, filter and pagination behavior, error response shape, and test data availability.
- Any unmet prerequisite must be scheduled explicitly as backlog work before that module's UI workspace starts.
- Verified by: a readiness matrix in the feature package and implementation tasks mapped to each module.

### AC-3: Shared context works across domains

- Core catalog data provides shared project, environment, version, and user context that can be reused by the other domain workspaces.
- Global filters preserve selection state while users move across pages.
- Verified by: UI tests that select a project context once and reuse it in at least three separate domain pages.

### AC-4: Report-heavy domains expose curated dashboards

- `metric`, `pipeline`, `tms`, and `spec` each provide at least one curated dashboard or report page designed for browser users rather than service operators.
- Dashboards support filtering, drill-down, empty-state handling, and at least one aggregation-first summary view per report-heavy domain.
- The reporting shell uses intentional dashboard styling suitable for external read-heavy users instead of placeholder or scaffold-only presentation.
- Verified by: page-level tests and one end-to-end workflow per report-heavy domain.

### AC-5: Operational domains expose browse and drill-down views

- `git` and `kube` provide read-oriented pages for inventory, status, and drill-down, even if they are not the first dashboard-heavy modules.
- `core` exposes catalog and lookup pages that anchor the rest of the application.
- Verified by: route and data-loading tests for each operational domain workspace.

### AC-6: Contract changes remain additive and synchronized

- Any backend changes required by the UI are additive, maintain backward compatibility, and are mirrored in the corresponding `*-feign` module before UI consumption depends on them.
- `athena-model` only gains shared DTOs that are part of stable backend contracts.
- Verified by: service integration tests plus compile/test coverage in the affected `*-feign` modules.

### AC-7: External-user exposure is gated by deployment and access decisions

- The feature must explicitly choose whether the first rollout is internal-only, read-only behind network controls, or authenticated for external users.
- `athena-gateway` is the only public ingress for the browser application.
- Verified by: deployment and security decisions recorded in the feature package and exercised in gateway smoke tests.

## API and Contract Impact

- Existing gateway prefixes remain the browser-facing API surface: `/core/**`, `/git/**`, `/kube/**`, `/metric/**`, `/pipeline/**`, `/spec/**`, `/tms/**`.
- Backend changes should prefer additive list, search, summary, and drill-down endpoints over breaking existing CRUD endpoints.
- Where response shapes are not browser-friendly, add stable DTOs or dedicated report endpoints in the owning service instead of pushing service-specific transformation into the frontend.
- Each `athena-boot-*-feign` module must stay aligned with its paired boot service before UI code relies on new contract shapes.

## Data Model Impact

- `athena-model` may gain shared response DTOs for summaries, filters, or report payloads when those contracts are stable and reused outside the frontend.
- UI-only state, layout metadata, and transient view models stay in `athena-frontend` unless they become persisted backend contracts.
- `athena-boot-init` is only impacted if the feature later requires saved dashboards, cached aggregates, or additional report tables.

## Service and Integration Impact

- `athena-frontend`: browser application shell, routing, shared UI primitives, dashboard/report pages.
- `athena-gateway`: browser ingress, route preservation, static asset or reverse-proxy integration, security and CORS handling.
- `athena-boot-core` and `athena-boot-core-feign`: shared catalog context and global selectors.
- `athena-boot-metric`, `athena-boot-pipeline`, `athena-boot-tms`, `athena-boot-spec` and matching feign modules: primary dashboard and report providers.
- `athena-boot-git`, `athena-boot-kube` and matching feign modules: operational inventory and drill-down providers.

## Risks

- Some services may lack browser-ready read and summary endpoints, creating hidden prerequisite work.
- Inconsistent pagination, filtering, or error envelopes across services can make a shared UI expensive to maintain.
- External-user access introduces gateway, identity, and audit concerns that are not solved by frontend work alone.
- Cross-domain dashboards may be slow if summary endpoints are missing and the browser must over-compose raw service responses.

## Validation Strategy

- Frontend tests: route smoke tests, shared filter behavior, and page-level loading/error/empty-state coverage.
- Boot service tests: integration tests for any new list, summary, report, or drill-down endpoints added for UI use.
- Feign module tests: compatibility checks when service contracts expand.
- Gateway tests: route and static-delivery smoke tests, plus any security-header or auth behavior introduced for browser access.
- System tests: one end-to-end suite that enters through the gateway, selects a core project context, visits multiple domain pages, and opens at least one curated dashboard.

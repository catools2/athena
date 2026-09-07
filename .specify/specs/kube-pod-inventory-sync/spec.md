# Feature Spec: kube-pod-inventory-sync

## Summary

- Problem: Kubernetes pod ingestion spans snapshots, containers, metadata, status; feature package defines stable contract
- Outcome: Complete API surface, service layer, persistence strategy for pod inventory synchronization from clusters
- Service domains: `athena-boot-kube`, `athena-boot-kube-feign`, `athena-model`, shared `athena-common`

## User Value

- Primary user: Automated cluster ingestion jobs (periodic sync from Kubernetes API, Helm changes)
- Trigger: Pod creation/update/deletion events in cluster; namespace-level sync jobs; project-specific pod queries
- Business value: Environment-aware quality metrics require accurate pod-to-project mapping and container inventory

## Scope

- In scope: Pod registration by name+namespace, container persistence, metadata/annotation/label/selector deduplication, saveOrUpdate idempotency, fetch by project/namespace
- Out of scope: Workload abstractions (Deployments, DaemonSets), pod eviction history, network policies

## Acceptance Criteria

### AC-1: Pod Identity & Project Binding

**Verified**: `PodController.saveOrUpdate()` (POST `/pod`), `PodServiceImpl.saveOrUpdate()`  
**Criterion**: Pod identity based on `(name, namespace)` pair; project_id required (FK to core.project); save-or-update finds existing pod on name+namespace match and updates fields without duplicate  
**Test**: `PodControllerIT.saveShallUpdatePodIdPodWithTheSameNameAndNamespaceExists()`

### AC-2: Container Lifecycle within Pod

**Verified**: `Pod.containers` (OneToMany, orphanRemoval=true, cascade=ALL), `PodServiceImpl.saveOrUpdate()` adds containers  
**Criterion**: Containers are persisted with pod; new containers added to existing pod; deleted containers cascade delete with orphan removal  
**Test**: `PodControllerIT` verifies containers in `PodDto` round-trip and are saved

### AC-3: PodStatus Normalization (Enum-Like)

**Verified**: `PodServiceImpl.normalizePodStatus()` (synchronized), `PodStatusRepository.findByNameAndPhaseAndMessageAndReason()`  
**Criterion**: Pod status stored as shared lookup on unique `(name, phase, message, reason)` tuple; same status reused across multiple pods; prevents status table explosion  
**Test**: Unit test for `normalizePodStatus()` verifies deduplication and synchronized access

### AC-4: Metadata/Annotation/Label/Selector Deduplication

**Verified**: `PodServiceImpl.normalizeMetadata()` (synchronized), 5 metadata repositories extend `MetadataRepository<T>`  
**Criterion**: Each metadata type (PodMetadata, PodAnnotation, PodLabel, PodSelector, ContainerMetadata) deduplicated on `(name, value)` pair during save; lookup/create synchronized to prevent race condition duplication  
**Test**: `PodControllerIT` saves pods with metadata; subsequent pods with same metadata should reuse without duplication

### AC-5: Project-Based Pod Query

**Verified**: `PodController.getAll(project, namespace)`, `PodServiceImpl.getPods(project, namespace)`, `PodRepository.findByProjectIdAndNamespace()`  
**Criterion**: GET `/pods?project=CODE&namespace=NS` returns all pods in project+namespace; uses project code → id resolution via `KubeMapperService`; returns empty set if no pods  
**Test**: `PodControllerIT.getPodsShallReturnCorrectValueWhenValidNameAndNamespaceProvided()`

### AC-6: Point Queries by Name+Namespace and UID

**Verified**: `PodController.getByNameAndNamespace()`, `getById()`, `PodRepository.findByNameAndNamespace()`, `findByUid()`  
**Criterion**: GET `/pod?name=X&namespace=Y` and GET `/pod/{id}` return pod or 204 No Content; UID is optional but unique if provided  
**Test**: `PodControllerIT.getPodByNameAndNamespaceShallReturnCorrectValueWhenValidNameAndNamespaceProvided()`, `shallReturnCorrectValueWhenValidPodIdProvided()`

### AC-7: Container Metadata Independence

**Verified**: `Container.metadata` (ManyToMany with ContainerMetadata), normalized via `normalizeMetadata()`  
**Criterion**: Each container can have independent metadata; stored in `container_metadata` table + `container_metadata_mid` junction; shared with pod-level metadata deduplication  
**Test**: Unit test for container metadata normalization via `PodServiceImpl.normalizeMetadata()`

## API Contract

### REST Endpoints

| Method | Endpoint    | Query/Body                   | Response               | Notes                                           |
| ------ | ----------- | ---------------------------- | ---------------------- | ----------------------------------------------- |
| POST   | `/pod`      | PodDto                       | 201 + Location header  | Save or update; idempotent on (name, namespace) |
| GET    | `/pod/{id}` | —                            | 200 + dto \| 204       | By id lookup                                    |
| GET    | `/pod`      | `?name=X&namespace=Y`        | 200 + dto \| 204       | By name+namespace lookup                        |
| GET    | `/pods`     | `?project=CODE&namespace=NS` | 200 + set \| 200 empty | By project+namespace; returns set (not 204)     |

### Feign Clients (athena-boot-kube-feign)

- `PodFeignClient`: Operations: `saveOrUpdate(dto)`, `getById(id)`, `getByNameAndNamespace(name, namespace)`, `getAll(project, namespace)`
- Note: `ContainerFeignClient` not found in search; may be internal to pod lifecycle

## Data Model Impact

**Tables**: `athena_kube.pod`, `athena_kube.container`, `athena_kube.pod_status`, `athena_kube.pod_metadata`, `athena_kube.pod_annotation`, `athena_kube.pod_label`, `athena_kube.pod_selector`, `athena_kube.container_metadata`, + 5 junction tables  
**DTOs**: `PodDto`, `ContainerDto`, `PodStatusDto`, `MetadataDto` (shared)  
**Entities**: `Pod`, `Container`, `PodStatus`, `PodMetadata`, `PodAnnotation`, `PodLabel`, `PodSelector`, `ContainerMetadata`

## Service & Integration Impact

- **Boot module**: `athena-boot-kube`
- **Feign module**: `athena-boot-kube-feign`
- **Model module**: `athena-model` (DTOs)
- **Dependencies**: `KubeMapperService` for project code → id lookup (core service)

## Risks & Constraints

- **Risk**: High pod churn (create/delete/restart) can create metadata table bloat; mitigated by deduplication pattern
- **Risk**: Concurrent ingestion from multiple cluster sync jobs can race on metadata insert; mitigated by synchronized `normalizeMetadata()`
- **Constraint**: Pod identity by (name, namespace) uniqueness; both fields required
- **Constraint**: PodStatus is shared enum-like; same status reused across pods
- **Open question**: Should soft-deleted pods (deletedAt != null) be included in `/pods` list query?

## Validation Strategy

- **Unit tests**: `normalizeMetadata()`, `normalizePodStatus()` with concurrent mock scenarios
- **Integration tests**: `PodControllerIT` with project+namespace queries, metadata deduplication verification
- **Manual**: Load pod snapshot from live cluster, verify all containers and metadata persisted correctly

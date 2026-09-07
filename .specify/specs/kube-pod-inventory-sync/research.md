# Research Notes: kube-pod-inventory-sync

## Module Inventory

### Controllers (REST API surface)

| File                 | Endpoint          | Operations                                           | Notes                                  |
| -------------------- | ----------------- | ---------------------------------------------------- | -------------------------------------- |
| `PodController.java` | `/pod` (singular) | POST (save/update), GET by id, GET by name+namespace | Idempotent on `(name, namespace)` pair |
| `PodController.java` | `/pods` (plural)  | GET (list by project+namespace)                      | Returns set of all matching pods       |

### Services & Repositories

| Tier              | Class                                                                                             | Responsibility                                                                                                                                            |
| ----------------- | ------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Service Interface | `PodService`                                                                                      | `saveOrUpdate(entity)`, `getById(id)`, `getPods(project, namespace)`, `getByNameAndNamespace(name, namespace)`                                            |
| Service Impl      | `PodServiceImpl`                                                                                  | Merge existing pod on name+namespace match; normalizes metadata/annotations/labels/selectors/containers via helper methods; retry logic on save collision |
| Repository        | `PodRepository`                                                                                   | `findByNamespace()`, `findByProjectIdAndNamespace()`, `findByNameAndNamespace()`, `findByUid()`                                                           |
| Repository        | `PodStatusRepository`                                                                             | `findByNameAndPhaseAndMessageAndReason()` (synced enum-like table)                                                                                        |
| Repository        | `PodMetadataRepository`, `PodAnnotationRepository`, `PodLabelRepository`, `PodSelectorRepository` | All extend `MetadataRepository<T>` with `findByNameAndValue()`                                                                                            |
| Repository        | `ContainerMetadataRepository`                                                                     | Same pattern as pod metadata repos                                                                                                                        |

### Entities & DTOs

| Entity              | DTO Location                    | Fields                                                                                                                                                | Notes                                                                                  |
| ------------------- | ------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- |
| `Pod`               | `athena-model` → `PodDto`       | id, uid, name, namespace, hostname, nodeName, createdAt, deletedAt, lastSync, projectId, status, metadata, annotations, labels, selectors, containers | Unique: `(name)`, `(uid)`; M:1 to Project; 1:M to Containers; M:M to metadata entities |
| `Container`         | `athena-model` → `ContainerDto` | id, type, name, image, imageId, ready, started, restartCount, startedAt, lastSync, metadata                                                           | 1:M to Pod; M:M to ContainerMetadata                                                   |
| `PodStatus`         | `athena-model` → `PodStatusDto` | id, name, phase, message, reason                                                                                                                      | Unique: `(name, phase, message, reason)` at DB; shared enum-like table                 |
| `PodMetadata`       | Generic                         | id, name, value                                                                                                                                       | Unique: `(name, value)` at DB; M:M via `pod_metadata_mid`                              |
| `PodAnnotation`     | Generic                         | id, name, value                                                                                                                                       | Unique: `(name, value)` at DB; M:M via `pod_annotation_mid`                            |
| `PodLabel`          | Generic                         | id, name, value                                                                                                                                       | Unique: `(name, value)` at DB; M:M via `pod_label_mid`                                 |
| `PodSelector`       | Generic                         | id, name, value                                                                                                                                       | Unique: `(name, value)` at DB; M:M via `pod_selector_mid`                              |
| `ContainerMetadata` | Generic                         | id, name, value                                                                                                                                       | Unique: `(name, value)` at DB; M:M via `container_metadata_mid`                        |

### Mappers

| Mapper                             | Strategy                          | Key Lookups                                                                  |
| ---------------------------------- | --------------------------------- | ---------------------------------------------------------------------------- |
| `KubeMapper` (MapStruct interface) | Pod/Container ↔ DTO bidirectional | Project code ↔ id via `KubeMapperService`                                    |
| `KubeMapperService` interface      | Qualifier for mapper              | `getProjectId(projectCode)`, `getProjectCode(projectId)` (to be implemented) |

### Integration Tests

| Test Class         | Coverage                                                                                                                   |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------- |
| `KubeControllerIT` | Base class; sets up project and builds/saves 2 test pods                                                                   |
| `PodControllerIT`  | 4 tests: save new pod, update existing (name+namespace match), getAll by project+namespace, getByNameAndNamespace, getById |

## Key Design Patterns & Findings

### 1. Metadata Normalization Pattern (Synchronized)

- `PodServiceImpl.normalizeRelationships()` synchronizes all metadata operations
- For each metadata entity (PodMetadata, PodAnnotation, PodLabel, PodSelector, ContainerMetadata):
  - Look up `(name, value)` pair in DB
  - If exists, reuse; if not, create and save
  - Race condition handling: On `DataIntegrityViolationException`, retry the lookup
  - Prevents duplicate metadata across multiple pod ingestions
- Used for 5 different metadata types (pod + container level)

### 2. Idempotent Save-or-Update Pattern

- `PodServiceImpl.saveOrUpdate()`: Merges on `findByNameAndNamespace(name, namespace)` → updates pod fields without creating duplicate
- Returns existing record if found (idempotent)
- Retry logic: `RetryUtils.retry(3, 1000ms)` on saveAndFlush collision

### 3. PodStatus as Shared Enum-Like Table

- `PodStatus` entity has unique constraint on `(name, phase, message, reason)` tuple
- `PodServiceImpl.normalizePodStatus()` synchronizes status lookup/creation
- Prevents status duplication when same (name, phase, message, reason) ingested from multiple pods

### 4. Entity Relationships

- **Pod → Project**: M:1 (FK on `project_id`, not null)
- **Pod → PodStatus**: M:1 (FK on `status_id`, not null, cascade merge)
- **Pod → Container**: 1:M (orphanRemoval=true, cascade=ALL)
- **Container → Pod**: M:1 (FK on `pod_id`, not null)
- **Container ↔ ContainerMetadata**: M:M via `container_metadata_mid`
- **Pod ↔ PodMetadata**: M:M via `pod_metadata_mid`
- **Pod ↔ PodAnnotation**: M:M via `pod_annotation_mid`
- **Pod ↔ PodLabel**: M:M via `pod_label_mid`
- **Pod ↔ PodSelector**: M:M via `pod_selector_mid`

### 5. Container Lifecycle within Pod

- `Pod.addContainer()` / `Pod.removeContainer()` manage bidirectional link
- Container inherits pod's last_sync timestamp for consistency
- Container metadata normalized separately via same pattern as pod metadata

## Identified Gaps & Hardening Opportunities

1. **No DELETE endpoints**: `/pod/{id}` DELETE not implemented. Soft delete (deletedAt column exists) vs hard delete unclear.

2. **No Filter/Pagination API**: Search is point-lookup only (by name+namespace, id, or namespace). No `getPods(project, namespaceFilter, labelFilter)` for discovery.

3. **KubeMapperService implementation missing**: Interface defined but `KubeMapperServiceImpl` not found; assumes it calls core service for project lookup.
   - If `KubeMapperServiceImpl` missing, saves will fail on project code → id resolution

4. **Feign clients not yet found**: Search didn't return `PodFeignClient` or `ContainerFeignClient` interfaces from `athena-boot-kube-feign`; may be stubbed.

5. **No container-level filtering**: All containers loaded eagerly (EAGER fetch) in pod; could optimize with lazy fetch if large container counts.

## Design Constraints & Assumptions

- **Assumption**: Project must exist in core service before pod can be saved (FK constraint)
- **Constraint**: Pod identity by `(name, namespace)` uniqueness; both name and namespace are required
- **Constraint**: PodStatus is a shared enum-like table; same status (name, phase, message, reason) used across multiple pods
- **Assumption**: Metadata/annotation/label/selector deduplication is critical to avoid data explosion on repeated ingestions
- **Constraint**: Container metadata is independently tracked; each container can have unique metadata

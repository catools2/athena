# Research Notes: spec-openapi-contract-ingestion

## Module Inventory

### Controllers

| File                     | Endpoint | Operations                                              | Notes                              |
| ------------------------ | -------- | ------------------------------------------------------- | ---------------------------------- |
| `ApiSpecController.java` | `/spec`  | POST (saveOrUpdate), GET `/{id}`, GET `?project=&name=` | Idempotent on `(project_id, name)` |

### Services

| Class                | Responsibility                                                                      |
| -------------------- | ----------------------------------------------------------------------------------- |
| `ApiSpecService`     | Interface: `saveOrUpdate`, `getById`, `getByProjectCodeAndName`                     |
| `ApiSpecServiceImpl` | Merge on `(projectId, name)`; normalizes metadata & paths; project lookup via feign |

### Repositories

| Repository                  | Key Methods                                                                                 |
| --------------------------- | ------------------------------------------------------------------------------------------- |
| `ApiSpecRepository`         | `findByProjectIdAndName(Long projectId, String name)`                                       |
| `ApiPathRepository`         | `findBySpecIdAndUrlAndMethodAndTitle(Long specId, String url, String method, String title)` |
| `ApiSpecMetadataRepository` | Metadata dedup via `MetadataPersistentHelper`                                               |
| `ApiPathMetadataRepository` | Metadata dedup via `MetadataPersistentHelper`                                               |

### Entities & DTOs

| Entity            | DTO           | Key Fields                                                                                                                            | Notes                      |
| ----------------- | ------------- | ------------------------------------------------------------------------------------------------------------------------------------- | -------------------------- |
| `ApiSpec`         | `ApiSpecDto`  | id, projectId, name (100), title (100), version (10), firstTimeSeen, lastSyncTime, metadata (M:M), paths (1:M)                        | FK → `athena_core.project` |
| `ApiPath`         | `ApiPathDto`  | id, specId, method (10), url (500), title (1000), description (5000), parameters (JSONB), firstTimeSeen, lastSyncTime, metadata (M:M) | JSONB for arbitrary params |
| `ApiSpecMetadata` | `MetadataDto` | id, name (100), value (2000)                                                                                                          | Unique `(name, value)`     |
| `ApiPathMetadata` | `MetadataDto` | id, name (100), value (2000)                                                                                                          | Unique `(name, value)`     |

### Mappers

| Mapper                      | Strategy              | Key Lookups                                                                         |
| --------------------------- | --------------------- | ----------------------------------------------------------------------------------- |
| `ApiSpecMapper` (MapStruct) | ApiSpec/ApiPath ↔ DTO | Delegates project code↔id resolution to `ApiSpecMapperService`                      |
| `ApiSpecMapperServiceImpl`  | Qualifier impl        | `getProjectId(code)` via `CachedProjectFeignService`; `getProjectCode(id)` via same |

### Feign Clients

| Module                   | Client               | Exposed Endpoints                                           |
| ------------------------ | -------------------- | ----------------------------------------------------------- |
| `athena-boot-spec-feign` | `ApiSpecFeignClient` | `search(project, name)`, `getById(id)`, `saveOrUpdate(dto)` |

### Integration Tests

| Test Class            | Tests   | Coverage                                                                                                   |
| --------------------- | ------- | ---------------------------------------------------------------------------------------------------------- |
| `ApiSpecControllerIT` | 4 tests | Save new spec; update same name same project; same name different project; getById; search by project+name |

---

## Key Design Patterns

### 1. Project-Scoped Spec Identity

- Spec identity: `(projectId, name)` pair enforced at application layer (no DB unique constraint)
- `saveOrUpdate()` resolves `projectCode → projectId` via `ProjectFeignClient.search(projectCode)` then `findByProjectIdAndName(projectId, name)`
- Different projects can have specs with same name; they are separate records

### 2. Non-Destructive Path Merge (Immutable Log)

```java
// ApiSpecServiceImpl lines ~60-70: only adds new paths, never removes existing
apiSpec.getPaths().stream()
    .filter(p1 -> apiSpecUtils.notContains(spec.getPaths(), p1))
    .forEach(spec::addPath);
```

- Paths are historical records; deletion would break spec-driven test statistics

### 3. Dual-Level Metadata Normalization

- Both `ApiSpec.metadata` and per-`ApiPath.metadata` normalized before save
- `MetadataPersistentHelper.normalizeMetadata()` looks up existing `(name, value)` pair; creates if missing
- Prevents metadata table bloat on repeated ingestion of same spec

### 4. JSON Parameters Column

- `ApiPath.parameters` stored as JSONB; `Map<String, String>` in Java
- No separate parameter entity; arbitrary structure accepted
- Validated only at serialize/deserialize boundary

### 5. Direct Feign Usage in Service Layer

- Unlike other services (kube, git) which use mapper service for lookups, spec service calls `ProjectFeignClient` directly in `saveOrUpdate()`
- Also uses `CachedProjectFeignService` in `ApiSpecMapperServiceImpl` for bidirectional code↔id mapping

---

## Identified Gaps

1. **No DB unique constraint on `(project_id, name)`**: Application-layer check only; concurrent saves could create duplicates
2. **`ApiPathRepository.findBySpecIdAndUrlAndMethodAndTitle()` exists but never used in service**: Path deduplication logic is unclear
3. **No DELETE endpoints**: Spec/path deletion strategy undocumented; immutable-log design not explicitly stated
4. **No pagination or listing API**: Only point lookups; no way to list all specs for a project
5. **No schema validation**: Raw JSON accepted for parameters; no validation against OpenAPI spec format

---

## Module Dependencies

- **Depends on**: `athena-boot-core-feign` (ProjectFeignClient, CachedProjectFeignService)
- **Referenced by**: `athena-client-openapi`, `athena-cli-openapi`

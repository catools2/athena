# Research Notes: metric-derived-quality-actions

## Module Inventory

### Controllers (1)

| File               | Endpoint  | Operations           |
| ------------------ | --------- | -------------------- |
| `MetricController` | `/metric` | POST save, GET by id |

### Services (1)

| Service             | Key Method        | Pattern                                             |
| ------------------- | ----------------- | --------------------------------------------------- |
| `MetricServiceImpl` | `save(MetricDto)` | Action dedup before metric save; append-only metric |

### Repositories (2)

| Repository         | Key Methods                                                |
| ------------------ | ---------------------------------------------------------- |
| `MetricRepository` | `findById()`, `saveAndFlush()`                             |
| `ActionRepository` | `findByNameAndTypeAndTargetAndCommand()`, `saveAndFlush()` |

### Entities (2)

| Entity   | Table                  | Key Fields                                                                                                                           | Notes                            |
| -------- | ---------------------- | ------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------- |
| `Metric` | `athena_metric.metric` | id, projectId (FK→core.project), environmentId (FK→core.environment), duration (Long), actionTime (TIMESTAMPTZ), action (M:1→Action) | Timestamped measurement event    |
| `Action` | `athena_metric.action` | id, category (100), name (100), type (100), target (1000), command (5000), parameter (5000)                                          | Describes the measured operation |

### Mappers

| Mapper                     | Strategy                                                                                                                                                                           |
| -------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `MetricMapper` (MapStruct) | `Metric` ↔ `MetricDto`                                                                                                                                                             |
| `MetricMapperServiceImpl`  | `getProjectId(code)` via `CachedProjectFeignService`; `getEnvironmentId(project, env)` via `CachedEnvironmentFeignService`; reverse `getProjectCode(id)`, `getEnvironmentCode(id)` |

### Feign Clients (1)

| Client              | Endpoints                  |
| ------------------- | -------------------------- |
| `MetricFeignClient` | `getById(id)`, `save(dto)` |

### Integration Tests

- `MetricControllerIT` — save, getById

---

## Key Design Patterns

### 1. Action as Deduplication Key

- `Action` entity is identified by `(name, type, target, command)` composite index
- `MetricServiceImpl.save()` looks up action before persisting metric; creates if not found
- No `UNIQUE` constraint in DDL, but lookup prevents duplicates if called serially
- No retry or race-condition handling (simpler than TMS metadata pattern)

### 2. Metric as Append-Only Measurement

- Every `POST /metric` always creates a new record — no dedup, no update
- Each metric captures: project, environment, action, duration, actionTime
- Represents a single measured event (e.g., "command X on target Y took 42ms")

### 3. Minimal Domain — Only 2 Entities

- Most focused module in Athena: 2 entities, 1 controller, 1 service
- No metadata tables, no junction tables, no lookup tables beyond Action
- No FK to `athena_core.user` (no user tracking)

### 4. Core Service Dependencies (2 feign lookups)

- Project: `CachedProjectFeignService.search(code)` → projectId
- Environment: `CachedEnvironmentFeignService.search(project, env)` → environmentId

### 5. No Retry, No Metadata Normalization

- Unlike other modules, `MetricServiceImpl` does not use `RetryUtils`
- Action dedup uses simple `orElseGet()` with no DataIntegrityViolation catch
- Risk: concurrent saves could create duplicate Action records

---

## Identified Gaps

1. **No `UNIQUE` constraint on Action**: `(name, type, target, command)` has only an index, not a DB-level unique constraint — concurrent saves can create duplicate actions
2. **No `RetryUtils` usage**: Metric save has no retry; transient DB failures propagate directly
3. **No DataIntegrityViolationException handling for Action**: Race condition between `findByNameAndTypeAndTargetAndCommand` and `saveAndFlush` not handled
4. **No listing endpoint**: No `GET /metrics?project=&environment=` for time-series retrieval
5. **Duration is a raw Long**: No unit tracking — could be ms, ns, or seconds depending on caller

---

## Module Dependencies

- **Depends on**: `athena-boot-core-feign` (Project, Environment feign clients only — no User, no Version)
- **Referenced by**: `athena-client-metric`, `athena-cli-kube`

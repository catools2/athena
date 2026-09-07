# Research Notes: pipeline-execution-timeline-capture

## Module Inventory

### Controllers (4)

| File                                  | Endpoint     | Operations                                                                                   |
| ------------------------------------- | ------------ | -------------------------------------------------------------------------------------------- |
| `PipelineController`                  | `/pipeline`  | POST saveOrUpdate, GET last by name+number+project+version+env, GET by id, PUT updateEndDate |
| `PipelineExecutionController`         | `/execution` | POST save, GET by id                                                                         |
| `PipelineScenarioExecutionController` | `/scenario`  | POST save, GET by id                                                                         |
| `PipelineExecutionStatusController`   | `/status`    | POST save, GET by id                                                                         |

### Services (4)

| Service                                | Key Method                                 | Pattern                                                                                      |
| -------------------------------------- | ------------------------------------------ | -------------------------------------------------------------------------------------------- |
| `PipelineServiceImpl`                  | `saveOrUpdate(PipelineDto)`                | Merge on `environmentId + name LIKE + number LIKE` (top1 by desc id); metadata normalization |
| `PipelineExecutionServiceImpl`         | `save(PipelineExecutionDto)`               | Append-only; no update; metadata normalization                                               |
| `PipelineScenarioExecutionServiceImpl` | `save(PipelineScenarioExecutionDto)`       | Append-only; no update; metadata normalization                                               |
| `PipelineExecutionStatusServiceImpl`   | `saveOrUpdate(PipelineExecutionStatusDto)` | Merge on name                                                                                |

### Repositories (6)

| Repository                            | Key Methods                                                                |
| ------------------------------------- | -------------------------------------------------------------------------- |
| `PipelineRepository`                  | `findTop1ByEnvironmentIdAndNameLikeAndNumberLikeOrderByIdDesc()`           |
| `PipelineRepositoryCustom`            | `findLastPipeline(name, number, versionId, environmentId)` (dynamic query) |
| `PipelineExecutionRepository`         | `findById()`, `saveAndFlush()`                                             |
| `PipelineScenarioExecutionRepository` | `findById()`, `saveAndFlush()`                                             |
| `PipelineExecutionStatusRepository`   | `findByName()`                                                             |
| `PipelineMetaDataRepository`          | `findByNameAndValue()`                                                     |
| `PipelineExecutionMetaDataRepository` | `findByNameAndValue()`                                                     |

### Entities (6)

| Entity                      | Table                                | Key Fields                                                                                                                                                                                                                                                                                 |
| --------------------------- | ------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `Pipeline`                  | `athena_pipeline.pipeline`           | id, name (100), description (300), number (100), startDate, endDate, environmentId (FK→core.environment), versionId (FK→core.app_version), metadata (M:M)                                                                                                                                  |
| `PipelineExecution`         | `athena_pipeline.execution`          | id, packageName (300), className (300), methodName (300), parameters (2000), startTime, endTime, testStartTime, testEndTime, beforeClassStartTime, beforeClassEndTime, beforeMethodStartTime, beforeMethodEndTime, status (M:1), executorId (FK→core.user), pipeline (M:1), metadata (M:M) |
| `PipelineScenarioExecution` | `athena_pipeline.scenario_execution` | id, feature (1000), scenario (500), parameters (2000), startTime, endTime, beforeScenarioStartTime, beforeScenarioEndTime, status (M:1), executorId (FK→core.user), pipeline (M:1), metadata (M:M)                                                                                         |
| `PipelineExecutionStatus`   | `athena_pipeline.status`             | id, name (100, unique)                                                                                                                                                                                                                                                                     |
| `PipelineMetadata`          | `athena_pipeline.pipeline_metadata`  | id, name (100), value (2000), UNIQUE(name,value)                                                                                                                                                                                                                                           |
| `PipelineExecutionMetadata` | `athena_pipeline.execution_metadata` | id, name (100), value (2000), UNIQUE(name,value)                                                                                                                                                                                                                                           |

### Mappers

| Mapper                       | Strategy                                                                                                                                                                                                                                                                    |
| ---------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `PipelineMapper` (MapStruct) | Pipeline/Execution/ScenarioExecution ↔ DTO                                                                                                                                                                                                                                  |
| `PipelineMapperServiceImpl`  | `getVersionId(projectCode, versionCode)` via CachedVersionFeignService; `getEnvironmentId(projectCode, environmentCode)` via CachedEnvironmentFeignService; `getExecutorId(username)` via CachedUserFeignService; `getStatusId(name)` via PipelineExecutionStatusRepository |

### Feign Clients (4)

| Client                                 | Endpoints                                                                                                                             |
| -------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| `PipelineFeignClient`                  | `getLastPipeline(name, number, project, version, environment)`, `getById(id)`, `updateEndDate(pipelineId, date)`, `saveOrUpdate(dto)` |
| `PipelineExecutionFeignClient`         | `getById(id)`, `save(dto)`                                                                                                            |
| `PipelineScenarioExecutionFeignClient` | `getById(id)`, `save(dto)`                                                                                                            |
| `PipelineExecutionStatusFeignClient`   | `getById(id)`, `save(dto)`                                                                                                            |

### Integration Tests

- `PipelineControllerIT` — covers pipeline save, update, getById, getLastPipeline, updateEndDate, execution save, scenario save

---

## Key Design Patterns

### 1. Pipeline as Run Container

- Pipeline identified by `environment + name LIKE + number LIKE` (LIKE pattern allows fuzzy matching)
- `findTop1ByEnvironmentIdAndNameLikeAndNumberLikeOrderByIdDesc()` returns most recent match
- `PipelineDynamicQueryBuilder` supports optional filters: version, environment
- `updateEndDate()` is a dedicated `PUT` endpoint (unlike other services)

### 2. Append-Only Executions

- `PipelineExecution` and `PipelineScenarioExecution` are never updated — only saved
- No merge/dedup logic; every POST creates a new execution record
- Captures full timing breakdown: beforeClass, beforeMethod, test, and overall start/end

### 3. Dual Execution Types

- `PipelineExecution`: unit/integration test method execution (package.class.method granularity)
- `PipelineScenarioExecution`: BDD scenario execution (feature/scenario granularity)
- Both share the same status lookup table (`athena_pipeline.status`) and executor pattern

### 4. Core Service Dependencies (3 feign lookups)

- Version: `CachedVersionFeignService.getVersionId(projectCode, versionCode)`
- Environment: `CachedEnvironmentFeignService.getEnvironmentId(projectCode, environmentCode)`
- User: `CachedUserFeignService.getUserId(username)` (for executorId)

### 5. Metadata Normalization (same as TMS/Spec)

- Both pipeline metadata tables have `UNIQUE(name,value)` constraint
- `normalizeMetadata()` calls `findByNameAndValue()` before save; catches `DataIntegrityViolationException` for concurrent inserts
- `PipelineServiceImpl.normalizeMetadata()` is `synchronized` (extra safeguard over TMS pattern)

---

## Identified Gaps

1. **No listing endpoint**: No `GET /executions?pipelineId=` — consumers cannot retrieve all executions for a pipeline
2. **Scenario metadata shares execution_metadata table**: `scenario_metadata_mid.execution_id` FK → `athena_pipeline.execution_metadata` (not a dedicated table)
3. **`PUT /pipeline` is the only non-POST mutation**: All other services use POST-only; pipeline alone has PUT for end date
4. **`PipelineExecutionStatus` uses only `name`** (no `code`): Unlike TMS status which has both code and name
5. **LIKE-based pipeline identity**: Fuzzy name/number matching in `findTop1` could match unexpected pipelines if name patterns overlap

---

## Module Dependencies

- **Depends on**: `athena-boot-core-feign` (Version, Environment, User feign clients)
- **Referenced by**: `athena-client-pipeline`, `athena-client-pipeline-testng`, `athena-cli-git`

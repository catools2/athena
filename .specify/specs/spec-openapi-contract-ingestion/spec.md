# Feature Spec: spec-openapi-contract-ingestion

## Summary

- **Problem**: OpenAPI/Swagger contract ingestion spans `api_spec`, `api_path`, and metadata; the complete feature contract is undocumented
- **Outcome**: Verified implementation of project-scoped spec storage, immutable path log, dual-level metadata normalization, and point queries
- **Service domains**: `athena-boot-spec`, `athena-boot-spec-feign`, `athena-model`

## User Value

- **Primary user**: Automated spec ingestion jobs (CI pipelines, API gateways, spec registries)
- **Trigger**: New OpenAPI version published; spec updated in registry; periodic full sync
- **Business value**: Reliable contract tracking enables spec-driven testing, API documentation, and breaking-change detection

## Scope

- **In scope**: Spec registration by `(projectId, name)`, path persistence with JSON parameters and metadata, idempotent saveOrUpdate, project-based and ID-based queries
- **Out of scope**: Schema validation, spec diffing, breaking change detection, path pruning, test generation

---

## Acceptance Criteria

### AC-1: Project-Scoped Spec Identity

**Verified**: `ApiSpecServiceImpl.saveOrUpdate()` — `projectFeignClient.search(entity.getProject())` then `findByProjectIdAndName(projectId, name)`  
**Criterion**: Spec identity is `(projectId, name)` pair; same name in different projects creates separate records; second save to same project+name updates rather than duplicates  
**Test**: `ApiSpecControllerIT.shallUpdateSpecificationIfSpecificationWithTheSameNameExistsForTheProject()`  
`ApiSpecControllerIT.shallSaveOpenApiSpecificationIfSpecificationWithTheSameNameDoesNotExistsForTheProject()`

### AC-2: Non-Destructive Path Merge (Immutable Log)

**Verified**: `ApiSpecServiceImpl.saveOrUpdate()` lines ~60-70 — only adds new paths via `apiSpecUtils.notContains(spec.getPaths(), p1)`  
**Criterion**: Existing paths are never deleted on spec update; only new paths are appended; historical path tracking preserved for statistics  
**Test**: `ApiSpecControllerIT.shallUpdateSpecificationIfSpecificationWithTheSameNameExistsForTheProject()` verifies paths from both saves are present

### AC-3: Spec-Level Metadata Normalization

**Verified**: `MetadataPersistentHelper.normalizeMetadata(apiSpec.getMetadata(), apiSpecMetadataRepository)`  
**Criterion**: Spec metadata entries deduplicated on `(name, value)` before save; metadata updated on spec update (stale entries removed, new entries added)  
**Test**: `ApiSpecControllerIT.verifySpec()` asserts `verifyNameValuePairs(apiSpec.getMetadata(), apiSpecDto.getMetadata())`

### AC-4: Path-Level Metadata Normalization

**Verified**: `apiSpec.getPaths().forEach(p -> p.setMetadata(MetadataPersistentHelper.normalizeMetadata(..., apiPathMetadataRepository)))`  
**Criterion**: Each path has independent metadata set; normalized against `api_path_metadata` unique table; prevents per-path metadata bloat  
**Test**: `ApiSpecControllerIT.verifySpec()` loop verifies each path's metadata via `verifyNameValuePairs`

### AC-5: Arbitrary JSON Parameters on Paths

**Verified**: `ApiPath.parameters` — `@JdbcTypeCode(SqlTypes.JSON)` on `Map<String, String>` field  
**Criterion**: Path parameters stored as JSONB in PostgreSQL; accepts arbitrary key-value pairs; round-trips correctly via feign client  
**Test**: `ApiSpecControllerIT.verifySpec()` — `assertThat(pathDto.getParameters(), IsEqual.equalTo(apiPath.getParameters()))`

### AC-6: First-Seen & Last-Sync Timestamps

**Verified**: `ApiSpec.firstTimeSeen`, `ApiSpec.lastSyncTime`, `ApiPath.firstTimeSeen`, `ApiPath.lastSyncTime`  
**Criterion**: Both spec and path track initial ingestion timestamp (immutable after create) and latest sync timestamp (updated on every saveOrUpdate)  
**Test**: `ApiSpecControllerIT.verifySpec()` — `assertThat(apiSpec.getFirstTimeSeen().truncatedTo(ChronoUnit.MILLIS), notNullValue())`

### AC-7: Point Queries with 204 Fallback

**Verified**: `ApiSpecController.getById()` and `search(project, name)` → `ResponseEntityUtils.okOrNoContent()`  
**Criterion**: `GET /spec/{id}` returns 200+body or 204 No Content; `GET /spec?project=CODE&name=NAME` resolves project code to id, returns 200+body or 204 No Content  
**Test**: `ApiSpecControllerIT.shallReturnCorrectValueWhenValidIdProvided()`, `shallReturnCorrectValueWhenValidCodeProvided()`

---

## API Contract

### REST Endpoints (`athena-boot-spec`)

| Method | Endpoint     | Body/Params               | Response       | Notes                             |
| ------ | ------------ | ------------------------- | -------------- | --------------------------------- |
| POST   | `/spec`      | `ApiSpecDto`              | 201 + Location | Idempotent on `(projectId, name)` |
| GET    | `/spec/{id}` | —                         | 200+dto \| 204 | Exact id lookup                   |
| GET    | `/spec`      | `?project=CODE&name=NAME` | 200+dto \| 204 | Resolves project code → id        |

### Feign Client (`athena-boot-spec-feign`)

```java
// ApiSpecFeignClient
TypedResponse<ApiSpecDto> search(@Param("project") String project, @Param("name") String name);
TypedResponse<ApiSpecDto> getById(@Param("id") Long id);
TypedResponse<Void>       saveOrUpdate(ApiSpecDto apiSpec);
```

---

## Data Model Impact

- **Tables**: `api_spec`, `api_path`, `api_spec_metadata`, `api_path_metadata`, `api_spec_metadata_mid`, `path_metadata_mid` (6 tables)
- **Migration**: `V9__spec_init.sql`
- **DTOs**: `ApiSpecDto`, `ApiPathDto`, `MetadataDto` (shared)
- **Entities**: `ApiSpec`, `ApiPath`, `ApiSpecMetadata`, `ApiPathMetadata`

---

## Risks & Constraints

| Risk                                                                      | Mitigation                                                                     |
| ------------------------------------------------------------------------- | ------------------------------------------------------------------------------ |
| Concurrent saves create duplicate `(project_id, name)` — no DB constraint | Add DB unique constraint on `(project_id, name)` for production hardening      |
| Large specs (1000+ paths) create ingestion load                           | Immutable log prevents delete-churn; acceptable at current scale               |
| Missing project throws NPE                                                | `Optional.ofNullable(...).orElseThrow()` fails fast                            |
| Duplicate paths can be created (path dedup not used)                      | Clarify dedup strategy; consider using `findBySpecIdAndUrlAndMethodAndTitle()` |

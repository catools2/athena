# Research Notes: git-repository-commit-ingestion

## Module Inventory

### Controllers (REST API surface)

| File                           | Endpoint  | Operations                                   | Notes                              |
| ------------------------------ | --------- | -------------------------------------------- | ---------------------------------- |
| `GitRepositoryController.java` | `/repo`   | POST (save/update), GET (by id, by name/url) | Idempotent on `(name \| url)` pair |
| `CommitController.java`        | `/commit` | POST (save/update), GET (by id, by hash)     | Idempotent on `hash`               |

### Services & Repositories

| Tier              | Class                      | Responsibility                                                                                                                         |
| ----------------- | -------------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| Service Interface | `GitRepositoryService`     | `findByNameOrUrl(keyword)`, `saveOrUpdate(entity)`                                                                                     |
| Service Impl      | `GitRepositoryServiceImpl` | Merge existing repo on name/url match; uses `RetryUtils.retry(3, 1000ms)` for saveAndFlush                                             |
| Service Interface | `CommitService`            | `findByHash(hash)`, `saveOrUpdate(entity)` with memory tracking                                                                        |
| Service Impl      | `CommitServiceImpl`        | Heavy lifting: normalizes large tag/metadata/diff collections via separate queries; retry logic for concurrent hash collisions         |
| Repository        | `GitRepositoryRepository`  | `findByName()`, `findByNameOrUrl()`                                                                                                    |
| Repository        | `CommitRepository`         | `findByHash()`, `findByIdWithRelations()`, `findByHashWithTags()`, `findByHashWithMetadata()` (separate queries prevent N+1 explosion) |
| Repository        | `TagRepository`            | `findByNameAndHash()`                                                                                                                  |
| Repository        | `CommitMetadataRepository` | `findByNameAndValue()`                                                                                                                 |

### Entities & DTOs

| Entity           | DTO                | Fields                                                                                                                                                                                | Notes                                                           |
| ---------------- | ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------- |
| `GitRepository`  | `GitRepositoryDto` | id, name, url, lastSync                                                                                                                                                               | Unique: `(name)`, `(url)` at DB                                 |
| `Commit`         | `CommitDto`        | id, hash, parentHash, shortMessage, commitTime, parentCount, authorId, committerId, repository, totalImpactedFiles, totalInsertedLine, totalDeletedLines, diffEntries, tags, metadata | Unique: `(hash)`; 1:M to DiffEntry; M:M to Tag & CommitMetadata |
| `DiffEntry`      | `DiffEntryDto`     | id, commit, oldPath, newPath, changeType, inserted, deleted                                                                                                                           | Cascade delete with commit; 1:M inverse                         |
| `Tag`            | `TagDto`           | id, hash, name                                                                                                                                                                        | Unique: `(name, hash)` at DB; M:M via `commit_tag_mid`          |
| `CommitMetadata` | `MetadataDto`      | id, name, value                                                                                                                                                                       | M:M via `commit_metadata_mid`; maps to `athena_common.metadata` |

### Feign Clients

| Client                     | Scope                   | Methods                                      |
| -------------------------- | ----------------------- | -------------------------------------------- |
| `GitRepositoryFeignClient` | `athena-boot-git-feign` | All repo operations via `/repo` endpoint     |
| `CommitFeignClient`        | `athena-boot-git-feign` | All commit operations via `/commit` endpoint |

### Mappers

| Mapper                            | Strategy                          | Key Lookups                                                                                                   |
| --------------------------------- | --------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| `GitMapper` (MapStruct interface) | Entity ↔ DTO                      | —                                                                                                             |
| `GitMapperServiceImpl`            | Qualifier service for `GitMapper` | `getUsername(id)` / `getUserId(username)` (cached via `CachedUserFeignService`); `findRepositoryByName(name)` |

### Integration Tests

| Test Class                  | Coverage                                                                                                                         |
| --------------------------- | -------------------------------------------------------------------------------------------------------------------------------- |
| `GitRepositoryControllerIT` | CRUD: save, update on name match, update on url match, get by id, search by name/url, search invalid → 204                       |
| `CommitControllerIT`        | Save commit with relations (tags, diffs, metadata), update existing, get by id, search by hash, cartesian product avoidance test |

## Key Design Patterns & Findings

### 1. Idempotent Save-or-Update Pattern

- `GitRepositoryServiceImpl.saveOrUpdate()`: Merges on `findByNameOrUrl(name, url)` → updates `name, url, lastSync` if found
- `CommitServiceImpl.saveOrUpdate()`: Tries to save; if collision on hash, retries up to 3× with 1000ms backoff (handles parallel ingestion)
- Both return existing record without creating duplicate if hash/name-url already exists

### 2. Memory-Safe Fetch for Large Collections

- `CommitServiceImpl.getById()` and `findByHash()` use three separate queries:
  - Main: `findByIdWithRelations()` loads Commit + Repository + DiffEntries
  - Separate: `findByIdWithTags()` loads tags in isolation
  - Separate: `findByIdWithMetadata()` loads metadata in isolation
- Prevents cartesian product explosion (e.g., 2000 tags × 15 diffs = 30K rows returned unnecessarily)
- Logs heap usage before/after to monitor ingestion memory impact

### 3. Entity Relationships

- **Commit → GitRepository**: M:1 (FK on `repository_id`, not null)
- **Commit → DiffEntry**: 1:M (orphanRemoval=true, cascade=ALL) — deleting commit cascades
- **Commit ↔ Tag**: M:M via `commit_tag_mid` (LAZY fetch, @BatchSize(50))
- **Commit ↔ CommitMetadata**: M:M via `commit_metadata_mid` (LAZY fetch, @BatchSize(50))
- DiffEntry.commit is FK; addDiffEntry() / removeDiffEntry() manage bidirectional link

### 4. Mappers Use Cross-Service Lookups

- `GitMapperServiceImpl.getUserId(username)` → calls `CachedUserFeignService.search()` (core service)
  - Throws `RecordNotFoundException` if user not found (author/committer must exist in core)
- `GitMapperServiceImpl.findRepositoryByName()` → looks up repository locally before commit insert

## Identified Gaps & Hardening Opportunities

1. **No Filter/Pagination API**: Unlike core module, git has no `/repo/all?name=X&page=0` endpoint. Search is point-lookup only.
   - Could add `RepositoryDynamicQueryBuilder` + `getAll(Pageable, FilterDto)` for discoverability
2. **No DELETE endpoints**: Neither `/repo/{id}` DELETE nor `/commit/{id}` DELETE implemented. May be intentional (immutable log) but undocumented.

3. **CommitServiceImpl.saveOrUpdate()** catches all exceptions and retries, masking real errors
   - Could narrow to `DataIntegrityViolationException` only; other exceptions should fail fast

4. **Tag normalization** in `CommitServiceImpl.saveAndFlush()` truncates to 1000 max but code is incomplete in the search result (looks like snippet cut off)

5. **No validation** on commit author/committer existence before save — relies on `GitMapperServiceImpl` throwing, which is deferred to mapping phase
   - Could add `@NotNull @ValidUser` constraint annotation if user existence is mandatory

## Design Constraints & Assumptions

- **Assumption**: Users (author/committer) must exist in core service before commit can be saved. Lookup via `CachedUserFeignService`.
- **Constraint**: All diffs must be linked to a commit; orphan diffs impossible.
- **Constraint**: Repository identity is by `(name, url)` uniqueness — URLs can change (update on match) but name and URL can't both be null.
- **Assumption**: Tags and metadata are unbounded collections; normalization at 1000+ items suggests production commits can be large; safe fetch pattern handles this.

# Feature Spec: git-repository-commit-ingestion

## Summary

- Problem: Git ingestion spans repositories, commits, tags, and diff entries; feature package captures stable ingestion contract.
- Outcome: Define complete API surface, service layer, data persistence, and test coverage for git history ingestion.
- Service domains: `athena-boot-git`, `athena-boot-git-feign`, `athena-model`, shared `athena-common`.

## User Value

- Primary user: Automated git ingestion jobs (external CI/CD systems or scheduled jobs) and downstream metric consumers
- Trigger: Cloning repositories, fetching commit history via JGit or git CLI, ingesting via REST API
- Business value: Reliable git data powers SDLC quality metrics (code churn, commit frequency, author contribution); ingestion must be idempotent and correct

## Scope

- In scope: Repository registration (name/URL), commit history persistence with author/committer/tags/diffs, saveOrUpdate idempotency, fetch with memory safety
- Out of scope: New SCM provider support, advanced analytics, branch/PR tracking, blame/hotspot detection

## Acceptance Criteria

### AC-1: Repository Identity & Idempotency

**Verified**: `GitRepositoryController.saveOrUpdate()` (POST `/repo`), `GitRepositoryServiceImpl.saveOrUpdate()`  
**Criterion**: Repository identity based on `(name, url)` pair; save-or-update finds existing repo on name or url match and updates fields without duplicate creation  
**Test**: `GitRepositoryControllerIT.shallUpdateTheRecordWhenRecordWithSameNameExists()`, `shallUpdateTheRecordWhenRecordWithSameUrlExists()`

### AC-2: Commit Persistence with Deduplication

**Verified**: `CommitController.saveOrUpdate()` (POST `/commit`), `CommitServiceImpl.saveOrUpdate()` with retry logic  
**Criterion**: Commit identity based on unique `hash`; repeated ingestion of same commit (by hash) returns existing record idempotently; retry 3× on concurrent collision  
**Test**: `CommitControllerIT.shallSaveTheRecordWhenValidInformationProvided()`, `shallUpdateTheRecordWhenValidInformationProvidedAndRecordExists()`

### AC-3: Author/Committer Lookup via Core Service

**Verified**: `GitMapperServiceImpl.getUserId()` via `CachedUserFeignService`, throws `RecordNotFoundException`  
**Criterion**: Commit author and committer must be valid usernames in core service; mapper resolves username → userId during commit save; fails fast if user not found  
**Test**: Unit test for `GitMapperServiceImpl.getUserId()` with mock feign response; IT would require pre-staged users

### AC-4: Memory-Safe Fetch for Large Collections

**Verified**: `CommitServiceImpl.getById()`, `findByHash()` use separate queries for tags/metadata  
**Criterion**: Commit retrieval loads repository + diffEntries in one query, then loads tags and metadata in separate queries to prevent N² cartesian product explosion  
**Test**: `CommitControllerIT` verifies tags and diffs are both loaded correctly; no assertion on query count (integration constraint)

### AC-5: Diff Entry Cascade Management

**Verified**: `Commit.diffEntries` (OneToMany with orphanRemoval=true, cascade=ALL), `setDiffEntries()` method  
**Criterion**: Deleting a commit cascades delete to all associated diff entries; adding/removing diffs via `addDiffEntry()` maintains bidirectional link  
**Test**: Unit test for `Commit.addDiffEntry()` and `removeDiffEntries()` with EqualsAndHashCode exclusion

### AC-6: Tag & Metadata Many-to-Many with Batch Loading

**Verified**: `Commit.tags` and `Commit.metadata` (ManyToMany with @BatchSize(50), LAZY fetch)  
**Criterion**: Tags and metadata are stored in M:M junction tables with unique constraints; batch size = 50 prevents N+1 on large collections  
**Test**: `CommitControllerIT` verifies tag and metadata round-trip via `GitTestUtils.verifyTagsDtoHasCorrectValue()`

### AC-7: Point Queries with No Content Response

**Verified**: `CommitController.search(hash)`, `GitRepositoryController.search(keyword)` return 200 with body or 204 No Content  
**Criterion**: GET `/repo?keyword=X` and GET `/commit?hash=Y` return 200 + body if found, else 204 No Content (not 404 or null body)  
**Test**: `CommitControllerIT.shallReturnTheRecordWhenSearchByInvalidHash()`, `GitRepositoryControllerIT.shallReturnTheRecordWhenSearchByInvalidParameter()`

## API Contract

### REST Endpoints

| Method | Endpoint       | Query/Body       | Response              | Notes                                   |
| ------ | -------------- | ---------------- | --------------------- | --------------------------------------- |
| POST   | `/repo`        | GitRepositoryDto | 201 + Location header | Save or update; idempotent on name\|url |
| GET    | `/repo/{id}`   | —                | 200 + dto \| 204      | By id lookup                            |
| GET    | `/repo`        | `?keyword=X`     | 200 + dto \| 204      | Keyword = name or url; case-sensitive   |
| POST   | `/commit`      | CommitDto        | 201 + Location header | Save or update; idempotent on hash      |
| GET    | `/commit/{id}` | —                | 200 + dto \| 204      | By id lookup                            |
| GET    | `/commit`      | `?hash=X`        | 200 + dto \| 204      | By hash lookup; case-sensitive          |

### Feign Clients (athena-boot-git-feign)

- `GitRepositoryFeignClient`: Operations: `saveOrUpdate(dto)`, `getById(id)`, `search(keyword)` → same HTTP semantics
- `CommitFeignClient`: Operations: `saveOrUpdate(dto)`, `getById(id)`, `search(hash)` → same HTTP semantics

## Data Model Impact

**Tables**: `athena_git.repository`, `athena_git.commit`, `athena_git.diff_entry`, `athena_git.tag`, `athena_git.commit_tag_mid`, `athena_git.commit_metadata`, `athena_git.commit_metadata_mid`  
**DTOs**: `GitRepositoryDto`, `CommitDto`, `DiffEntryDto`, `TagDto`, `MetadataDto` (shared with core)  
**Entities**: `GitRepository`, `Commit`, `DiffEntry`, `Tag`, `CommitMetadata`

## Service & Integration Impact

- **Boot module**: `athena-boot-git`
- **Feign module**: `athena-boot-git-feign`
- **Model module**: `athena-model` (DTOs)
- **Client module**: `athena-client-git` (optional wrapper over feign)
- **Dependencies**: `CachedUserFeignService` (core service lookup)

## Risks & Constraints

- **Risk**: Large commit histories (>2000 tags/diffs per commit) can cause memory spikes during fetch; mitigated by separate query strategy
- **Risk**: User not found in core service blocks commit ingestion; assumes users pre-provisioned
- **Constraint**: No pagination or filters for point lookups; search is hash/keyword only
- **Open question**: Should we add `getAll(Pageable, FilterDto)` for repository/commit discovery?

## Validation Strategy

- **Unit tests**: `GitMapperServiceImpl`, repository/service layer tests (mock feign)
- **Integration tests**: `GitRepositoryControllerIT`, `CommitControllerIT` (testcontainers DB, test feign builder for user lookup)
- **Manual**: Ingest known repo (e.g., GitHub), verify commits and tags persist with correct authors

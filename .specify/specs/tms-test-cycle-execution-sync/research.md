# Research Notes: tms-test-cycle-execution-sync

## Module Inventory

### Controllers (8 controllers)

| File                         | Endpoint      | Operations                                                                 | Notes                   |
| ---------------------------- | ------------- | -------------------------------------------------------------------------- | ----------------------- |
| `ItemController`             | `/item`       | POST saveOrUpdate, GET by id, GET search by keyword                        | Idempotent on `code`    |
| `ItemTypeController`         | `/itemType`   | POST saveOrUpdate, GET by id, GET search by keyword                        | Lookup table            |
| `PriorityController`         | `/priority`   | POST save, GET by id, GET search by keyword                                | Lookup table            |
| `StatusController`           | `/status`     | POST saveOrUpdate, GET by id, GET search by keyword                        | Lookup table            |
| `StatusTransitionController` | `/transition` | POST save (by itemCode), GET by id, GET by keyword, GET all by itemCode    | Append-only transitions |
| `TestCycleController`        | `/cycle`      | POST save, GET by id, GET search by keyword, GET findLastByPattern         | Cycle by version        |
| `TestExecutionController`    | `/execution`  | POST saveOrUpdate (by cycleCode), GET by id, GET all by itemCode+cycleCode | Per-cycle executions    |
| `SyncInfoController`         | `/syncInfo`   | POST saveOrUpdate, GET by id, GET by action+component+project              | Sync tracking           |

### Services (9 services)

| Service                       | Key Method                                  | Pattern                                                                                  |
| ----------------------------- | ------------------------------------------- | ---------------------------------------------------------------------------------------- |
| `ItemServiceImpl`             | `saveOrUpdate(ItemDto)`                     | Merge on `code`; metadata normalization before query; sync metadata/versions/transitions |
| `ItemTypeServiceImpl`         | `saveOrUpdate(ItemTypeDto)`                 | Merge on `code` or `name`                                                                |
| `PriorityServiceImpl`         | `saveOrUpdate(PriorityDto)`                 | Merge on `code` or `name`                                                                |
| `StatusServiceImpl`           | `saveOrUpdate(StatusDto)`                   | Merge on `code` or `name`                                                                |
| `StatusTransitionServiceImpl` | `save(itemCode, StatusTransitionDto)`       | Append only; no update                                                                   |
| `TestCycleServiceImpl`        | `saveOrUpdate(TestCycleDto)`                | Merge on `code`; sync executions                                                         |
| `TestExecutionServiceImpl`    | `saveOrUpdate(cycleCode, TestExecutionDto)` | Merge on item+cycle+status                                                               |
| `SyncInfoServiceImpl`         | `saveOrUpdate(SyncInfoDto)`                 | Merge on action+component+project                                                        |

### Repositories (9 repositories)

| Repository                   | Key Methods                                                            |
| ---------------------------- | ---------------------------------------------------------------------- |
| `ItemRepository`             | `findByCode()`, `findByCodeWithRelations()`, `findByIdWithRelations()` |
| `ItemMetadataRepository`     | `findByNameAndValue()`                                                 |
| `ItemTypeRepository`         | `findByCodeOrName()`                                                   |
| `PriorityRepository`         | `findByCodeOrName()`                                                   |
| `StatusRepository`           | `findByCodeOrName()`                                                   |
| `StatusTransitionRepository` | `findAllByItemId()`                                                    |
| `TestCycleRepository`        | `findByCodeWithRelations()`, `findLastByPattern()`                     |
| `TestExecutionRepository`    | `findByCycleIdAndItemId()`                                             |
| `SyncInfoRepository`         | `findByActionAndComponentAndProjectId()`                               |

### Entities (9 entities)

| Entity             | Table               | Key Fields                                                                                                                                                                                                 | Notes                        |
| ------------------ | ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------- |
| `Item`             | `item`              | id, code (15, unique), name, createdOn, createdBy, updatedOn, updatedBy, projectId (FK), type (M:1), status (M:1), priority (M:1), statusTransitions (1:M), versionIds (ElementCollection), metadata (M:M) | Central test case entity     |
| `TestCycle`        | `cycle`             | id, code (10, unique), name, startDate, endDate, versionId (FK), testExecutions (1:M)                                                                                                                      | Test run container           |
| `TestExecution`    | `execution`         | id, created, executed, cycleId (FK), itemId (FK), statusId (FK), executorId (FK)                                                                                                                           | Single test run result       |
| `StatusTransition` | `status_transition` | id, occurred, authorId (FK), itemId (FK), from (FK to status), to (FK to status)                                                                                                                           | Item lifecycle audit trail   |
| `Status`           | `status`            | id, code (10, unique), name (50)                                                                                                                                                                           | Lookup: test/item statuses   |
| `ItemType`         | `type`              | id, code (10, unique), name (50)                                                                                                                                                                           | Lookup: test case types      |
| `Priority`         | `priority`          | id, code (10, unique), name (50)                                                                                                                                                                           | Lookup: test case priorities |
| `ItemMetadata`     | `metadata`          | id, name (100), value (2000), unique `(name, value)`                                                                                                                                                       | Shared dedup table           |
| `SyncInfo`         | `sync_info`         | id, projectId, action (50), component (100), startTime, endTime                                                                                                                                            | Sync session tracking        |

### Mappers

| Mapper                  | Strategy                                                                                                                                                                                                    |
| ----------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `TmsMapper` (MapStruct) | Item/TestCycle/TestExecution/StatusTransition ↔ DTO                                                                                                                                                         |
| `TmsMapperServiceImpl`  | `getProjectId(code)`, `getProjectCode(id)` via CachedProjectFeignService; `getVersionId(code)`, `getUserId(username)`, `getStatusId(code)`, `getPriorityId(code)`, `getItemTypeId(code)`, `getItemId(code)` |

### Feign Clients (7 clients)

| Client                        | Endpoints                                                                                  |
| ----------------------------- | ------------------------------------------------------------------------------------------ |
| `ItemFeignClient`             | `search(keyword)`, `getById(id)`, `saveOrUpdate(dto)`                                      |
| `ItemTypeFeignClient`         | `search(keyword)`, `getById(id)`, `saveOrUpdate(dto)`                                      |
| `PriorityFeignClient`         | `search(keyword)`, `getById(id)`, `save(dto)`                                              |
| `StatusFeignClient`           | `search(keyword)`, `getById(id)`, `saveOrUpdate(dto)`                                      |
| `StatusTransitionFeignClient` | `getAllByItemCode(itemCode)`, `search(keyword)`, `getById(id)`, `save(itemCode, dto)`      |
| `TestCycleFeignClient`        | `search(keyword)`, `getById(id)`, `findLastByPattern(name, project, version)`, `save(dto)` |
| `TestExecutionFeignClient`    | `getAll(itemCode, cycleCode)`, `getById(id)`, `saveOrUpdate(cycleCode, dto)`               |
| `SyncInfoFeignClient`         | `search(action, component, project)`, `getById(id)`, `saveOrUpdate(dto)`                   |

### Integration Tests (6 test classes)

| Test Class                     | Tests                                                              |
| ------------------------------ | ------------------------------------------------------------------ |
| `ItemControllerIT`             | Save, update, getById, search                                      |
| `ItemTypeControllerIT`         | Save, update, getById, search                                      |
| `StatusTransitionControllerIT` | Save, getById, getAllByItemCode                                    |
| `TestCycleControllerIT`        | Save, update, getById, search                                      |
| `TestExecutionControllerIT`    | Save, update, getById, getAll                                      |
| `BaseTmsControllerIT`          | Shared setup: project, user, version, statuses, priority, itemType |

---

## Key Design Patterns

### 1. Lookup Table Pattern (Status, Priority, ItemType)

- Stored in shared tables with unique `code` constraint
- `saveOrUpdate()` merges on `code or name`; no duplicates
- Referenced by FK from Item and TestExecution

### 2. Item as Central Entity

- Test case item identified by unique `code`
- Carries: type, status, priority, project, versions, metadata, status transitions
- `ItemServiceImpl.saveOrUpdate()` validates type/status/priority exist before save
- Metadata normalized BEFORE `findByCode()` query to avoid Hibernate auto-flush issues

### 3. Test Cycle → Execution Containment

- `TestCycle` identified by unique `code`; linked to a version
- Executions scoped to cycle; each execution captures item+status+executor+timestamp
- `TestCycleServiceImpl` syncs executions on update (adds new, removes old)

### 4. Status Transition as Append-Only Audit Log

- Transitions never updated; only added
- Captures: from-status, to-status, author, timestamp
- Referenced by item; `syncStatusTransitions()` adds new, removes stale

### 5. Core Service Dependencies (4 feign lookups)

- Project: `ProjectFeignClient.search(code)` → projectId
- Version: `VersionFeignClient.search(code)` → versionId
- User: `UserFeignClient.search(username)` → userId (executor, author, createdBy, updatedBy)
- Plus TMS-internal lookups: status, priority, itemType, item

---

## Identified Gaps

1. **No GET for all items by project**: Only point lookups by code or id; no list by projectId
2. **`SyncInfo` concept may overlap**: Separate pattern from the rest; not tied to specific ingestion target
3. **Version lookup in TestCycleDto**: `TestCycleDto` has both `version` code and `project` code fields; unclear if both are needed
4. **`RetryUtils` usage**: Only `ItemServiceImpl` uses retry; `TestCycleServiceImpl` and `TestExecutionServiceImpl` do not

---

## Module Dependencies

- **Depends on**: `athena-boot-core-feign` (Project, Version, User, Environment feign clients)
- **Referenced by**: `athena-client-atlassian-jira`, `athena-client-atlassian-scale`, `athena-cli-atlassian-jira`, `athena-cli-atlassian-scale`

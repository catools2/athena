# Spec: tms-test-cycle-execution-sync

> Status: **IMPLEMENTED** — 100% feature-complete, 6 IT suites passing

## Feature Description

The TMS module ingests test case (item) data, test cycles, and test execution results from external
test management systems (Jira/Zephyr). It maintains audit trails through status transitions and
records sync sessions via SyncInfo entities.

## Acceptance Criteria

### AC-1: Test Item Idempotent Upsert (by code)

**Given** a `POST /item` request with a new item code  
**When** no item with that code exists  
**Then** a new item is persisted and `201 Created` returned with `Location` header

**Given** a `POST /item` request with an existing item code  
**When** an item with that code exists  
**Then** the existing item is updated (name, project, type, status, priority, metadata, versions, statusTransitions) and `201 Created` returned with the same entity id

**Code reference**: `ItemServiceImpl.saveOrUpdate()` → `itemRepository.findByCode(item.getCode()).map(...).orElse(item)`  
**Tests**: `ItemControllerIT.shallSaveRecordIfTheRecordDoesNotExists`, `shallUpdateRecordIfTheRecordAlreadyExists`

---

### AC-2: Item Validation Against Lookup Tables

**Given** an `ItemDto` where `type`, `status`, or `priority` code is unknown  
**When** `saveOrUpdate()` is called  
**Then** `EntityNotFoundException` is thrown with the unresolved code

**Given** a `StatusTransitionDto` within the item where `from` or `to` status code is unknown  
**When** `saveOrUpdate()` is called  
**Then** `EntityNotFoundException` is thrown for that transition status code

**Code reference**: `ItemServiceImpl.validateItemDtoFields()` → `itemTypeRepository.findByCodeOrName()`, `statusRepository.findByCodeOrName()`, `priorityRepository.findByCodeOrName()`; inner `validateStatusTransitions()`

---

### AC-3: Item Metadata Normalization (Dedup Before Flush)

**Given** an item with metadata (name+value pairs)  
**When** the item is saved or updated  
**Then** each metadata entry is deduplicated in `athena_tms.metadata` (constraint: `UNIQUE(name, value)`)  
**And** if two concurrent saves race on the same name+value, one catches `DataIntegrityViolationException` and retries lookup  
**And** metadata is normalized BEFORE the `findByCode()` query to avoid Hibernate auto-flush with transient entities

**Code reference**: `ItemServiceImpl.normalizeMetadata()` → `itemMetadataRepository.findByNameAndValue()` or `saveAndFlush()` with DataIntegrityViolation catch  
**Schema**: `athena_tms.metadata (name, value, UNIQUE(name, value))`

---

### AC-4: Item Version M:M Relationship

**Given** an `ItemDto` with a set of version codes  
**When** the item is saved or updated  
**Then** the item's `versionIds` (ElementCollection in `item_version_mid`) reflects exactly the provided versions  
**And** versions not present in the update are removed  
**And** versions not previously linked are added

**Code reference**: `ItemServiceImpl.syncVersionIds()` → `existing.getVersionIds().addAll(updates.getVersionIds())` + `removeIf`  
**Schema**: `athena_tms.item_version_mid (item_id, version_id) FK → athena_core.app_version`

---

### AC-5: Status Transition Audit Trail

**Given** an `ItemDto` with one or more `StatusTransitionDto` entries  
**When** the item is saved  
**Then** each transition (from, to, occurred, author) is persisted to `status_transition`  
**And** on update, transitions matching the incoming set remain; stale transitions are removed  
**And** all transitions require a non-null `occurred` timestamp  
**And** `from` and `to` status codes must refer to existing statuses

**Code reference**: `ItemServiceImpl.syncStatusTransitions()` + `validateStatusTransitions()`  
**Schema**: `athena_tms.status_transition (from_status FK, to_status FK, author FK, item_id FK, occurred)`

---

### AC-6: Test Cycle Idempotent Upsert (by code)

**Given** a `POST /cycle` request with a unique cycle code  
**When** no cycle with that code exists  
**Then** a new cycle is persisted with executions, `201 Created` returned

**Given** a `POST /cycle` request with an existing cycle code  
**When** the cycle exists  
**Then** name, versionId, startDate, endDate, and executions are updated  
**And** executions not present in update are removed; new ones added

**Code reference**: `TestCycleServiceImpl.saveOrUpdate()` → `testCycleRepository.findByCodeWithRelations().map(...)`  
**Tests**: `TestCycleControllerIT.shallSaveRecordIfTheRecordDoesNotExists`, `shallUpdateRecordIfTheRecordAlreadyExists`

---

### AC-7: Test Execution Upsert (per cycle, item, and status)

**Given** a `POST /execution?cycleCode={code}` with a `TestExecutionDto`  
**When** an execution with the same cycle+item does not exist  
**Then** a new execution is created and `201 Created` returned

**Given** a `POST /execution?cycleCode={code}` with a `TestExecutionDto`  
**When** an execution with the same cycle+item already exists  
**Then** the execution status, executor, and executedOn are updated

**Code reference**: `TestExecutionServiceImpl.saveOrUpdate(cycleCode, dto)` → `testExecutionRepository.findByCycleIdAndItemId()`  
**Tests**: `TestExecutionControllerIT.shallSaveRecordIfTheRecordDoesNotExists`, `shallUpdateRecordIfTheRecordAlreadyExists`

---

### AC-8: Lookup Table Idempotent Save (Status, Priority, ItemType)

**Given** a `POST /status` (or `/priority`, `/itemType`) with a code+name DTO  
**When** an entry with that code or name already exists  
**Then** the existing record is updated and `201 Created` returned

**Given** the code or name is new  
**Then** a new record is created

**Code reference**: `StatusServiceImpl`, `PriorityServiceImpl`, `ItemTypeServiceImpl` — all use `findByCodeOrName()`

---

### AC-9: SyncInfo Session Tracking

**Given** a `POST /syncInfo` with action, component, project code, start/end times  
**When** a sync_info record with matching action+component+project already exists  
**Then** the existing record is updated

**When** no matching record exists  
**Then** a new sync_info record is created

**Code reference**: `SyncInfoServiceImpl.saveOrUpdate()` → `syncInfoRepository.findByActionAndComponentAndProjectId()`  
**Schema**: `athena_tms.sync_info (action varchar(50), component varchar(100), project_id FK)`

---

### AC-10: Point Queries Return 204 on Miss

**Given** a `GET /item/{id}` or `GET /item?keyword={code}` with an unknown id or code  
**Then** `204 No Content` is returned

**Given** a `GET /cycle/{id}`, `GET /execution/{id}`, etc. with unknown id  
**Then** `204 No Content` is returned

**Code reference**: All controllers follow the pattern: `service.getById(id).map(...).orElse(ResponseEntity.noContent().build())`

---

## Out of Scope

- Batch import API (items ingested one at a time via `POST /item`)
- Full item listing by project (no `GET /items?project=` endpoint exists)
- Item deletion or status revert

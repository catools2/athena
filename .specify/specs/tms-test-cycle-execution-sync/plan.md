# Implementation Plan: tms-test-cycle-execution-sync

> Status: **Phase 1 COMPLETE** — All core functionality implemented and tested

## Overview

This plan tracks implementation of the TMS (Test Management System) ingestion module.
The module is fully implemented. This plan documents what was built and open hardening tasks.

---

## Phase 1: Core Domain — COMPLETE ✓

**Status**: All entities, services, controllers, and feign clients implemented.

### Deliverables Built

- [x] 9 entities: `Item`, `TestCycle`, `TestExecution`, `StatusTransition`, `Status`, `Priority`, `ItemType`, `ItemMetadata`, `SyncInfo`
- [x] 8 controllers with REST endpoints
- [x] 8 service implementations with idempotent `saveOrUpdate` pattern
- [x] 9 repositories with named queries
- [x] 3 mappers (MapStruct + `TmsMapperServiceImpl` for FK resolution)
- [x] 7 feign clients in `athena-boot-tms-feign`
- [x] `V10__tms_init.sql` migration with `athena_tms` schema

---

## Phase 2: Hardening — OPEN

### Task 2.1: Add `RetryUtils` to `TestCycleServiceImpl` and `TestExecutionServiceImpl`

- **Gap**: `ItemServiceImpl` uses `RetryUtils.retry(3, 1000)` but `TestCycleServiceImpl` and `TestExecutionServiceImpl` do not
- **Risk**: Concurrent saves to cycle/execution may fail without retry
- **Fix**: Wrap `cycleRepository.saveAndFlush()` and `executionRepository.saveAndFlush()` in `RetryUtils.retry(3, 1000, attempt -> ...)`
- **File**: `TestCycleServiceImpl.java`, `TestExecutionServiceImpl.java`
- **Effort**: 15 min

### Task 2.2: Add listing endpoint `GET /items?project={code}`

- **Gap**: No way to list items by project; only point lookup by code or id
- **Impact**: TMS clients cannot retrieve full item list for a project
- **Fix**: Add `ItemRepository.findByProjectId(Long projectId)`, expose as `GET /item/search?project={code}` in `ItemController`, add feign method to `ItemFeignClient`
- **Effort**: 30 min

### Task 2.3: Add negative test for unknown type/status/priority in ItemControllerIT

- **Gap**: `validateItemDtoFields()` throws `EntityNotFoundException` but no IT validates this path
- **Fix**: Add `shallReturn400WhenItemTypeIsUnknown()` and `shallReturn400WhenStatusIsUnknown()` tests to `ItemControllerIT`
- **Effort**: 20 min

---

## Phase 3: Integration Tests — COMPLETE ✓

| Test Class                     | Tests                           | Status |
| ------------------------------ | ------------------------------- | ------ |
| `ItemControllerIT`             | save, update, getById, search   | ✓      |
| `ItemTypeControllerIT`         | save, update, getById, search   | ✓      |
| `StatusTransitionControllerIT` | save, getById, getAllByItemCode | ✓      |
| `TestCycleControllerIT`        | save, update, getById, search   | ✓      |
| `TestExecutionControllerIT`    | save, update, getById, getAll   | ✓      |
| `TmsMapperIT`                  | 12 bidirectional mapper tests   | ✓      |

---

## Phase 4: Documentation — COMPLETE ✓

| Doc             | Status                                 |
| --------------- | -------------------------------------- |
| `research.md`   | ✓ Module inventory, 5 patterns, 4 gaps |
| `data-model.md` | ✓ DDL, entity-DTO mapping, ER diagram  |
| `spec.md`       | ✓ 10 ACs fully verified against code   |
| `plan.md`       | ✓ This document                        |

---

## Current Status

| Phase                      | Status   | Completion   |
| -------------------------- | -------- | ------------ |
| Phase 1: Core Domain       | COMPLETE | 100%         |
| Phase 2: Hardening         | OPEN     | 0% (3 tasks) |
| Phase 3: Integration Tests | COMPLETE | 100%         |
| Phase 4: Documentation     | COMPLETE | 100%         |

**Overall**: ~90% complete. Phase 2 hardening tasks are improvements, not blockers.

---

## Notes

- `TmsMapperServiceImpl` is fully implemented (unlike `KubeMapperServiceImpl` which is missing)
- Metadata normalization pattern in `ItemServiceImpl` is more sophisticated than other services — handles Hibernate auto-flush timing issue
- `SyncInfo` entity serves a cross-cutting concern (sync session tracking) separate from the core test data ingestion flow
- Module has the most complex dependency graph: uses all 4 core feign services (Project, Version, User, Environment)

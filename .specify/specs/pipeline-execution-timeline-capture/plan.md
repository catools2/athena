# Implementation Plan: pipeline-execution-timeline-capture

> Status: **Phase 1 COMPLETE** — All core functionality implemented and tested

## Overview

This plan tracks implementation of the Pipeline execution ingestion module.
The module is fully implemented. This plan documents what was built and open hardening tasks.

---

## Phase 1: Core Domain — COMPLETE ✓

### Deliverables Built

- [x] 6 entities: `Pipeline`, `PipelineExecution`, `PipelineScenarioExecution`, `PipelineExecutionStatus`, `PipelineMetadata`, `PipelineExecutionMetadata`
- [x] 4 controllers with REST endpoints (including unique `PUT /pipeline` for endDate patch)
- [x] 4 service implementations with metadata normalization and `RetryUtils`
- [x] 6 repositories including `PipelineDynamicQueryBuilder` for flexible pipeline search
- [x] 2 mappers (MapStruct + `PipelineMapperServiceImpl` for FK resolution)
- [x] 4 feign clients in `athena-boot-pipeline-feign`
- [x] `V8__pipeline_init.sql` migration with `athena_pipeline` schema

---

## Phase 2: Hardening — OPEN

### Task 2.1: Add listing endpoint `GET /execution?pipelineId={id}`

- **Gap**: No way to retrieve all executions for a pipeline; only point lookup by id
- **Impact**: Consumers building pipeline dashboards cannot list executions
- **Fix**: Add `PipelineExecutionRepository.findByPipelineId(Long pipelineId)`, expose as `GET /executions?pipelineId={id}`, add to feign client
- **Effort**: 25 min

### Task 2.2: Separate scenario metadata from execution metadata

- **Gap**: `scenario_metadata_mid.execution_id` FK → `execution_metadata` creates a subtle schema confusion — scenario and execution share the dedup table
- **Impact**: Minor confusion during querying; no functional issue today
- **Fix** (optional): Add `scenario_metadata` table with same `UNIQUE(name,value)` constraint and `scenario_metadata_mid` FK to it
- **Effort**: 45 min (requires new migration + entity refactor)

### Task 2.3: Replace synchronized `normalizeMetadata` with optimistic approach

- **Gap**: `PipelineServiceImpl.normalizeMetadata()` uses `synchronized` keyword — blocks concurrent requests at JVM level
- **Impact**: Low throughput under high concurrency for pipeline saves
- **Fix**: Remove `synchronized`; rely on `DataIntegrityViolationException` catch + retry (same as TMS pattern)
- **Effort**: 10 min

### Task 2.4: Add IT negative tests for pipeline not found

- **Gap**: No IT validates `PipelineNotExistsException` on `PUT /pipeline?pipelineId=999`
- **Fix**: Add `shallReturn400WhenPipelineIdNotFound()` to `PipelineControllerIT`
- **Effort**: 15 min

---

## Phase 3: Integration Tests — COMPLETE ✓

- `PipelineControllerIT` — covers all core CRUD scenarios
- `PipelineMapperIT` — bidirectional mapper coverage

---

## Phase 4: Documentation — COMPLETE ✓

| Doc             | Status                                 |
| --------------- | -------------------------------------- |
| `research.md`   | ✓ Module inventory, 5 patterns, 5 gaps |
| `data-model.md` | ✓ DDL, entity-DTO mapping, ER diagram  |
| `spec.md`       | ✓ 9 ACs fully verified against code    |
| `plan.md`       | ✓ This document                        |

---

## Current Status

| Phase                      | Status   | Completion   |
| -------------------------- | -------- | ------------ |
| Phase 1: Core Domain       | COMPLETE | 100%         |
| Phase 2: Hardening         | OPEN     | 0% (4 tasks) |
| Phase 3: Integration Tests | COMPLETE | 100%         |
| Phase 4: Documentation     | COMPLETE | 100%         |

**Overall**: ~90% complete. Phase 2 hardening tasks are improvements, not blockers.

---

## Notes

- Pipeline is the only module with a `PUT` endpoint (for end date)
- `PipelineDynamicQueryBuilder` supports LIKE-based fuzzy pipeline search — useful for CI pipelines with dynamic names
- Dual execution types (JUnit-style + BDD) make this the most timeline-rich module
- Depends only on 3 core feign services (Version, Environment, User) — fewer than TMS

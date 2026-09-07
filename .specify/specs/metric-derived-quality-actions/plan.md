# Implementation Plan: metric-derived-quality-actions

> Status: **Phase 1 COMPLETE** — All core functionality implemented and tested

## Overview

This plan tracks implementation of the Metric ingestion module.
The module is fully implemented. This plan documents what was built and open hardening tasks.

---

## Phase 1: Core Domain — COMPLETE ✓

### Deliverables Built

- [x] 2 entities: `Metric`, `Action`
- [x] 1 controller: `MetricController` (`POST /metric`, `GET /metric/{id}`)
- [x] 1 service: `MetricServiceImpl` (action dedup + metric append)
- [x] 2 repositories: `MetricRepository`, `ActionRepository`
- [x] 1 MapStruct mapper: `MetricMapper` + `MetricMapperServiceImpl` (FK resolution)
- [x] 1 feign client: `MetricFeignClient` in `athena-boot-metric-feign`
- [x] `V7__metric_init.sql` migration with `athena_metric` schema

---

## Phase 2: Hardening — OPEN

### Task 2.1: Add UNIQUE constraint to Action (name, type, target, command)

- **Gap**: Only an index, not a DB constraint — concurrent saves can create duplicate actions
- **Fix**: Add `V11__metric_action_unique.sql` with `ALTER TABLE athena_metric.action ADD CONSTRAINT uk_action_identity UNIQUE (name, type, target, command)`
- **And**: Catch `DataIntegrityViolationException` in `MetricServiceImpl.save()` with re-lookup (same as TMS metadata pattern)
- **Effort**: 20 min

### Task 2.2: Add RetryUtils to MetricServiceImpl

- **Gap**: No retry on transient DB failures — unlike Pipeline, Git, and TMS modules
- **Fix**: Wrap `metricRepository.saveAndFlush()` in `RetryUtils.retry(3, 1000)`
- **Effort**: 5 min

### Task 2.3: Add listing endpoint `GET /metrics?project={}&environment={}`

- **Gap**: No way to retrieve metrics by project or environment for analysis
- **Fix**: Add `MetricRepository.findByProjectIdAndEnvironmentId()`, expose as `GET /metrics?project=&environment=`
- **Effort**: 30 min

### Task 2.4: Track duration unit

- **Gap**: `duration` column is raw Long with no associated unit (ms vs. ns ambiguous)
- **Fix**: Add `unit` varchar(20) to `metric` table (new migration) or document convention externally
- **Effort**: 15 min (if simple convention doc) or 45 min (if schema change)

---

## Phase 3: Integration Tests — COMPLETE ✓

- `MetricControllerIT` — covers save and getById
- `MetricBuilder` — generates test data via Instancio

---

## Phase 4: Documentation — COMPLETE ✓

| Doc             | Status                                 |
| --------------- | -------------------------------------- |
| `research.md`   | ✓ Module inventory, 5 patterns, 5 gaps |
| `data-model.md` | ✓ DDL, entity-DTO mapping, ER diagram  |
| `spec.md`       | ✓ 4 ACs fully verified against code    |
| `plan.md`       | ✓ This document                        |

---

## Current Status

| Phase                      | Status   | Completion   |
| -------------------------- | -------- | ------------ |
| Phase 1: Core Domain       | COMPLETE | 100%         |
| Phase 2: Hardening         | OPEN     | 0% (4 tasks) |
| Phase 3: Integration Tests | COMPLETE | 100%         |
| Phase 4: Documentation     | COMPLETE | 100%         |

**Overall**: ~85% complete. Phase 2 hardening tasks address correctness gaps (Task 2.1 is highest priority).

---

## Notes

- Simplest Athena module: 2 entities, 2 tables, 1 controller
- No metadata, no junction tables, no status lookups
- Depends only on Project and Environment feign clients (no User, no Version)
- Action dedup is the single notable design pattern; all else is straightforward CRUD

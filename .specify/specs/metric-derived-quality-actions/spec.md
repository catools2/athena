# Spec: metric-derived-quality-actions

> Status: **IMPLEMENTED** — 100% feature-complete, IT suite passing

## Feature Description

The Metric module captures quality measurements from CI/CD and operational tooling.
Each metric records the outcome of a specific action (a named command on a target)
including timing and duration. The action dictionary is deduplicated automatically —
identical actions are reused across metrics.

## Acceptance Criteria

### AC-1: Metric Append-Only Save

**Given** a `POST /metric` request with a `MetricDto`  
**When** the request is processed  
**Then** a new metric record is always created (append-only, no dedup)  
**And** `201 Created` is returned with a `Location: /metric/{id}` header

**Code reference**: `MetricServiceImpl.save()` → `metricRepository.saveAndFlush(metric)`  
**Note**: No identity check or update path — every POST creates a new metric row

---

### AC-2: Action Deduplication on Save

**Given** a metric with action fields (name, type, target, command)  
**When** the metric is saved  
**Then** the action is looked up by `(name, type, target, command)`  
**And** if found, the existing action record is reused  
**And** if not found, a new action record is created

**Code reference**: `ActionRepository.findByNameAndTypeAndTargetAndCommand()` → `orElseGet(() -> actionRepository.saveAndFlush(action))`  
**Note**: Deduplication by 4-field composite; `parameter` and `category` are NOT part of the identity key — they are stored with the first creation and not updated on reuse

---

### AC-3: Core FK Resolution via Feign

**Given** a `MetricDto` with `project` (code) and `environment` (code) fields  
**When** the DTO is mapped to an entity  
**Then** `CachedProjectFeignService.search(project)` resolves the `projectId`  
**And** `CachedEnvironmentFeignService.search(project, environment)` resolves the `environmentId`  
**And** a `RecordNotFoundException` is thrown if either lookup returns no result

**Code reference**: `MetricMapperServiceImpl.getProjectId()`, `MetricMapperServiceImpl.getEnvironmentId()`

---

### AC-4: Metric Point Query

**Given** a `GET /metric/{id}` request  
**When** a metric with the given id exists  
**Then** the `MetricDto` is returned with `200 OK`

**When** no metric exists with the given id  
**Then** `204 No Content` is returned

**Code reference**: `MetricController.getActionById()` → `ResponseEntityUtils.okOrNoContent(metricService.getById(id))`

---

## Out of Scope

- Listing metrics by project, environment, or time range (no `GET /metrics?project=`)
- Updating or deleting metric records
- Aggregate queries (counts, averages, trends)
- Duration unit tracking

---

## Known Limitations

1. **Action race condition**: No `DataIntegrityViolationException` handling — concurrent requests with same action fields may create duplicate rows
2. **No RetryUtils**: Transient DB failures are not retried
3. **Duration unit is unspecified**: Callers must agree on milliseconds vs. other units externally

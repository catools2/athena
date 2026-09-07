# Spec: pipeline-execution-timeline-capture

> Status: **IMPLEMENTED** — 100% feature-complete, IT suite passing

## Feature Description

The Pipeline module ingests CI/CD pipeline execution data from automated test runs.
It captures full timing timelines for both unit/integration test method executions
and BDD scenario executions, grouped by pipeline run.

## Acceptance Criteria

### AC-1: Pipeline Idempotent Upsert (by environment + name LIKE + number LIKE)

**Given** a `POST /pipeline` request  
**When** no pipeline matching `environmentId + name LIKE + number LIKE` exists  
**Then** a new pipeline is created and `201 Created` returned with `Location` header

**Given** a `POST /pipeline` request  
**When** a pipeline with matching environment+name+number already exists  
**Then** the most recent pipeline (by desc id) is updated (name, description, number, startDate, endDate, versionId, metadata) and `201 Created` returned

**Code reference**: `PipelineServiceImpl.saveOrUpdate()` → `pipelineRepository.findTop1ByEnvironmentIdAndNameLikeAndNumberLikeOrderByIdDesc()`  
**Note**: Name and number support SQL `LIKE` wildcards for flexible pattern matching

---

### AC-2: Pipeline Metadata Normalization

**Given** a `PipelineDto` with metadata entries  
**When** the pipeline is saved or updated  
**Then** each metadata (name+value) is deduplicated in `athena_pipeline.pipeline_metadata` (constraint: `UNIQUE(name,value)`)  
**And** concurrent inserts caught via `DataIntegrityViolationException` with retry lookup

**Code reference**: `PipelineServiceImpl.normalizeMetadata()` (synchronized method)

---

### AC-3: Pipeline End Date Patch

**Given** a `PUT /pipeline?pipelineId={id}&date={instant}` request  
**When** the pipeline id exists  
**Then** the pipeline's `endDate` is updated and `200 OK` returned with the updated `PipelineDto`

**Given** the `date` parameter is omitted  
**Then** `Instant.now()` is used as the end date

**Given** the pipeline id does not exist  
**Then** `PipelineNotExistsException` is thrown

**Code reference**: `PipelineController.updateEndDate()` + `PipelineServiceImpl.updatePipelineEndDate()`

---

### AC-4: Pipeline Retrieval by Pattern Query

**Given** a `GET /pipeline?name={}&project={}&version={}&number={}&environment={}` request  
**When** a pipeline matching the criteria exists  
**Then** the most recent pipeline matching the pattern is returned with `200 OK`

**When** no pipeline matches  
**Then** `204 No Content` is returned

**Code reference**: `PipelineServiceImpl.getPipeline()` → `PipelineRepositoryCustom.findLastPipeline()` (dynamic query via `PipelineDynamicQueryBuilder`)  
**Note**: `project` is required; `version`, `number`, `environment` are optional filters

---

### AC-5: Test Method Execution Capture (Append-Only)

**Given** a `POST /execution` with a `PipelineExecutionDto`  
**When** the request is processed  
**Then** a new execution record is always created (no update/dedup)  
**And** full timing breakdown is captured: overall start/end, test phase, beforeClass phase, beforeMethod phase  
**And** `201 Created` is returned with the new execution id

**Code reference**: `PipelineExecutionServiceImpl.save()` — no lookup, always creates new  
**Schema**: 8 timing columns: startTime, endTime, testStartTime, testEndTime, beforeClassStartTime, beforeClassEndTime, beforeMethodStartTime, beforeMethodEndTime

---

### AC-6: BDD Scenario Execution Capture (Append-Only)

**Given** a `POST /scenario` with a `PipelineScenarioExecutionDto`  
**When** the request is processed  
**Then** a new scenario execution is always created  
**And** feature/scenario name, parameters, timing, and metadata are captured  
**And** `201 Created` is returned with the new scenario execution id

**Code reference**: `PipelineScenarioExecutionServiceImpl.save()`  
**Schema**: `athena_pipeline.scenario_execution` (feature varchar(1000), scenario varchar(500))

---

### AC-7: Execution Metadata Normalization

**Given** an execution or scenario execution with metadata  
**When** the record is saved  
**Then** each metadata entry is deduplicated in `athena_pipeline.execution_metadata`  
**And** scenario executions share the same `execution_metadata` dedup table as regular executions

**Code reference**: `PipelineExecutionServiceImpl.normalizeMetadata()`, `PipelineScenarioExecutionServiceImpl.normalizeMetadata()`  
**Note**: `scenario_metadata_mid.execution_id` FK points to `scenario_execution`, but shares `execution_metadata` lookup table

---

### AC-8: Execution Status Lookup

**Given** an execution or scenario execution with a status name  
**When** the DTO is mapped  
**Then** the status is looked up by name from `athena_pipeline.status`  
**And** if no status with that name exists, the mapper service creates or resolves it

**Code reference**: `PipelineMapperServiceImpl.getStatusId(name)` → `PipelineExecutionStatusRepository.findByName()`  
**Note**: `PipelineExecutionStatus` uses only `name` (no `code` field, unlike TMS status)

---

### AC-9: Point Queries Return 204 on Miss

**Given** any `GET /{resource}/{id}` request with an unknown id  
**Then** `204 No Content` is returned

**Code reference**: All controllers: `ResponseEntityUtils.okOrNoContent(service.getById(id))`

---

## Out of Scope

- Listing all executions for a pipeline (no `GET /executions?pipelineId=`)
- Updating or deleting individual execution records
- Pipeline deletion

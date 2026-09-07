# Data Model: pipeline-execution-timeline-capture

## Database Schema (PostgreSQL, `athena_pipeline` schema)

Source: `V8__pipeline_init.sql`

### Table: pipeline

```sql
create table athena_pipeline.pipeline (
  id             bigserial primary key,
  name           varchar(100) not null,
  description    varchar(300) not null,
  number         varchar(100) not null,
  environment_id bigint not null,
  version_id     bigint not null,
  start_date     TIMESTAMPTZ not null,
  end_date       TIMESTAMPTZ,
  foreign key (environment_id) references athena_core.environment,
  foreign key (version_id)     references athena_core.app_version
);
```

### Table: execution (PipelineExecution)

```sql
create table athena_pipeline.execution (
  id                       bigserial primary key,
  pipeline_id              bigint not null,
  status_id                bigint not null,
  executor_id              bigint not null,
  package_name             varchar(300) not null,
  class_name               varchar(300) not null,
  method_name              varchar(300) not null,
  parameters               varchar(2000),
  description              varchar(500),
  start_time               TIMESTAMPTZ not null,
  end_time                 TIMESTAMPTZ not null,
  test_start_time          TIMESTAMPTZ,
  test_end_time            TIMESTAMPTZ,
  before_class_start_time  TIMESTAMPTZ,
  before_class_end_time    TIMESTAMPTZ,
  before_method_start_time TIMESTAMPTZ,
  before_method_end_time   TIMESTAMPTZ,
  foreign key (pipeline_id)  references athena_pipeline.pipeline,
  foreign key (status_id)    references athena_pipeline.status,
  foreign key (executor_id)  references athena_core.user
);
create index IDXynam1aocxtr559vsbvmxpaaa on athena_pipeline.execution (created, pipeline_id);
```

### Table: scenario_execution (PipelineScenarioExecution)

```sql
create table athena_pipeline.scenario_execution (
  id                        bigserial primary key,
  pipeline_id               bigint not null,
  status_id                 bigint not null,
  executor_id               bigint not null,
  feature                   varchar(1000) not null,
  scenario                  varchar(500) not null,
  parameters                varchar(2000),
  start_time                TIMESTAMPTZ not null,
  end_time                  TIMESTAMPTZ not null,
  before_scenario_start_time TIMESTAMPTZ,
  before_scenario_end_time   TIMESTAMPTZ,
  foreign key (pipeline_id)  references athena_pipeline.pipeline,
  foreign key (status_id)    references athena_pipeline.status,
  foreign key (executor_id)  references athena_core.user
);
```

### Table: status (PipelineExecutionStatus)

```sql
create table athena_pipeline.status (
  id   bigserial primary key,
  name varchar(100) not null unique
);
```

### Metadata Tables

```sql
-- Pipeline metadata (dedup table)
create table athena_pipeline.pipeline_metadata (
  id    bigserial primary key,
  name  varchar(100) not null,
  value varchar(2000) not null,
  constraint uk_pipeline_metadata_name_value unique (name, value)
);

-- Execution metadata (dedup table; also used by scenario_execution)
create table athena_pipeline.execution_metadata (
  id    bigserial primary key,
  name  varchar(100) not null,
  value varchar(2000) not null,
  constraint uk_execution_metadata_name_value unique (name, value)
);
```

### Junction Tables

```sql
-- pipeline <-> pipeline_metadata
create table athena_pipeline.pipeline_metadata_mid (
  pipeline_id bigint not null,
  metadata_id bigint not null,
  primary key (metadata_id, pipeline_id)
);

-- execution <-> execution_metadata
create table athena_pipeline.execution_metadata_mid (
  execution_id bigint not null,
  metadata_id  bigint not null,
  primary key (execution_id, metadata_id)
);

-- scenario_execution <-> execution_metadata (shared table)
create table athena_pipeline.scenario_metadata_mid (
  execution_id bigint not null,  -- FK to scenario_execution.id
  metadata_id  bigint not null,  -- FK to execution_metadata.id
  primary key (execution_id, metadata_id)
);
```

---

## Entity-to-DTO Mapping

| Entity                      | DTO                            | Key Conversions                                                                                              |
| --------------------------- | ------------------------------ | ------------------------------------------------------------------------------------------------------------ |
| `Pipeline`                  | `PipelineDto`                  | `environmentId` ↔ environment code; `versionId` ↔ version code; `project` code resolved from version context |
| `PipelineExecution`         | `PipelineExecutionDto`         | `executorId` ↔ username; `status.name` ↔ status name; `pipeline.id` ↔ pipelineId                             |
| `PipelineScenarioExecution` | `PipelineScenarioExecutionDto` | Same as execution                                                                                            |
| `PipelineExecutionStatus`   | `PipelineExecutionStatusDto`   | Direct name                                                                                                  |
| `PipelineMetadata`          | `MetadataDto`                  | Direct name+value                                                                                            |
| `PipelineExecutionMetadata` | `MetadataDto`                  | Direct name+value                                                                                            |

---

## DTO Reference

### PipelineDto

```java
Long id; String name; String description; String number
String version       // version code
String project       // project code
String environment   // environment code
Instant startDate; Instant endDate
Set<MetadataDto> metadata
```

### PipelineExecutionDto

```java
Long id; Long pipelineId
String packageName; String className; String methodName; String parameters
String status        // status name
String executor      // username
Instant startTime; Instant endTime
Instant testStartTime; Instant testEndTime
Instant beforeClassStartTime; Instant beforeClassEndTime
Instant beforeMethodStartTime; Instant beforeMethodEndTime
Set<MetadataDto> metadata
```

### PipelineScenarioExecutionDto

```java
Long id; Long pipelineId
String feature; String scenario; String parameters
String status        // status name
String executor      // username
Instant startTime; Instant endTime
Instant beforeScenarioStartTime; Instant beforeScenarioEndTime
Set<MetadataDto> metadata
```

---

## Cross-Domain FK Dependencies

| Table                                | FK Column        | External Service | Target Table              |
| ------------------------------------ | ---------------- | ---------------- | ------------------------- |
| `athena_pipeline.pipeline`           | `environment_id` | `core`           | `athena_core.environment` |
| `athena_pipeline.pipeline`           | `version_id`     | `core`           | `athena_core.app_version` |
| `athena_pipeline.execution`          | `executor_id`    | `core`           | `athena_core.user`        |
| `athena_pipeline.scenario_execution` | `executor_id`    | `core`           | `athena_core.user`        |

---

## Schema: Entity Relationship Diagram

```
athena_core.environment (1) ─┐
                              └── athena_pipeline.pipeline (N)
athena_core.app_version (1) ──┘       │
                                       ├── athena_pipeline.execution (1:N)
                                       │       ├── athena_pipeline.status (M:1)
                                       │       ├── athena_core.user (M:1) [executor]
                                       │       └── athena_pipeline.execution_metadata (M:M)
                                       │
                                       └── athena_pipeline.scenario_execution (1:N)
                                               ├── athena_pipeline.status (M:1)
                                               ├── athena_core.user (M:1) [executor]
                                               └── athena_pipeline.execution_metadata (M:M) [shared]
```

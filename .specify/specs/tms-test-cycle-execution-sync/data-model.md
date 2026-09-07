# Data Model: tms-test-cycle-execution-sync

## Database Schema (PostgreSQL, `athena_tms` schema)

Source: `V10__tms_init.sql`

### Table: item

```sql
create table athena_tms.item (
  id          bigserial primary key,
  code        varchar(15) not null unique,
  name        varchar(300) not null,
  project_id  bigint not null,
  type_id     bigint not null,
  status_id   bigint not null,
  priority_id bigint not null,
  created_by  bigint not null,
  created_on  TIMESTAMPTZ not null,
  updated_by  bigint,
  updated_on  TIMESTAMPTZ,
  foreign key (project_id)  references athena_core.project,
  foreign key (type_id)     references athena_tms.type,
  foreign key (status_id)   references athena_tms.status,
  foreign key (priority_id) references athena_tms.priority,
  foreign key (created_by)  references athena_core.user,
  foreign key (updated_by)  references athena_core.user
);
create index IDX6cgogdarkq48dlg1lbnv4q1oq on athena_tms.item (code);
```

### Table: cycle (TestCycle)

```sql
create table athena_tms.cycle (
  id         bigserial primary key,
  code       varchar(10) not null unique,
  name       varchar(300) not null,
  version_id bigint,
  start_date TIMESTAMPTZ not null,
  end_date   TIMESTAMPTZ,
  foreign key (version_id) references athena_core.app_version
);
create index IDXk43tb8sntc046yxf4suq076os on athena_tms.cycle (code);
```

### Table: execution (TestExecution)

```sql
create table athena_tms.execution (
  id          bigserial primary key,
  cycle_id    bigint not null,
  item_id     bigint not null,
  status_id   bigint not null,
  executor_id bigint,
  created     TIMESTAMPTZ not null,
  executed    TIMESTAMPTZ,
  foreign key (cycle_id)    references athena_tms.cycle,
  foreign key (item_id)     references athena_tms.item,
  foreign key (status_id)   references athena_tms.status,
  foreign key (executor_id) references athena_core.user
);
create index IDXynam1aocxtr559vsbvmxpaaa on athena_tms.execution (created, cycle_id, item_id);
```

### Table: status_transition

```sql
create table athena_tms.status_transition (
  id          bigserial primary key,
  item_id     bigint not null,
  author      bigint not null,
  from_status bigint not null,
  to_status   bigint not null,
  occurred    TIMESTAMPTZ,
  foreign key (item_id)     references athena_tms.item,
  foreign key (author)      references athena_core.user,
  foreign key (from_status) references athena_tms.status,
  foreign key (to_status)   references athena_tms.status
);
```

### Lookup Tables

```sql
create table athena_tms.status   (id bigserial primary key, code varchar(10) not null unique, name varchar(50) not null);
create table athena_tms.priority (id bigserial primary key, code varchar(10) not null unique, name varchar(50) not null);
create table athena_tms.type     (id bigserial primary key, code varchar(10) not null unique, name varchar(50) not null);
```

### Table: metadata (ItemMetadata)

```sql
create table athena_tms.metadata (
  id    bigserial primary key,
  name  varchar(100) not null,
  value varchar(2000) not null,
  constraint uk_metadata_name_value unique (name, value)
);
create index idx_item_metadata_name_value on athena_tms.metadata(name, value);
```

### Junction Tables

```sql
-- item <-> metadata (M:M)
create table athena_tms.item_metadata_mid (
  item_id     bigint not null,
  metadata_id bigint not null,
  primary key (item_id, metadata_id)
);

-- item <-> app_version (M:M via ElementCollection)
create table athena_tms.item_version_mid (
  item_id    bigint not null,
  version_id bigint not null,
  primary key (item_id, version_id),
  foreign key (version_id) references athena_core.app_version,
  foreign key (item_id)    references athena_tms.item
);
```

### Table: sync_info

```sql
create table athena_tms.sync_info (
  id         bigserial primary key,
  project_id bigint not null,
  action     varchar(50) not null,
  component  varchar(100) not null,
  start_time TIMESTAMPTZ not null,
  end_time   TIMESTAMPTZ not null,
  foreign key (project_id) references athena_core.project
);
```

---

## Entity-to-DTO Mapping

| Entity             | DTO                   | Key Conversions                                                                                                                                                     |
| ------------------ | --------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Item`             | `ItemDto`             | `projectId` ↔ project code; `type.code` ↔ type; `status.code` ↔ status; `priority.code` ↔ priority; `createdBy/updatedBy` ↔ usernames; `versionIds` ↔ version codes |
| `TestCycle`        | `TestCycleDto`        | `versionId` ↔ version code; `project` resolved from version                                                                                                         |
| `TestExecution`    | `TestExecutionDto`    | `cycleId` via parent; `item.code` ↔ item; `status.code` ↔ status; `executorId` ↔ username                                                                           |
| `StatusTransition` | `StatusTransitionDto` | `from.code`, `to.code`, `authorId` ↔ username                                                                                                                       |
| `Status`           | `StatusDto`           | Direct code+name                                                                                                                                                    |
| `ItemType`         | `ItemTypeDto`         | Direct code+name                                                                                                                                                    |
| `Priority`         | `PriorityDto`         | Direct code+name                                                                                                                                                    |
| `ItemMetadata`     | `MetadataDto`         | Direct name+value                                                                                                                                                   |
| `SyncInfo`         | `SyncInfoDto`         | `projectId` ↔ project code; direct action/component/timestamps                                                                                                      |

---

## DTO Reference

### ItemDto

```java
Long id; String code; String name
String project           // project code
String type              // ItemType code
String status            // Status code
String priority          // Priority code
String createdBy         // username
String updatedBy         // username
Instant createdOn; Instant updatedOn
Set<String> versions     // version codes
Set<MetadataDto> metadata
Set<StatusTransitionDto> statusTransitions
```

### TestCycleDto

```java
Long id; String code; String name
String version           // version code
String project           // project code (via version)
Instant startDate; Instant endDate
```

### TestExecutionDto

```java
Long id; String item     // item code
String status            // status code
String executor          // username
Instant createdOn; Instant executedOn
```

---

## Cross-Domain FK Dependencies

| Table                          | FK Column                  | External Service | Target Table              |
| ------------------------------ | -------------------------- | ---------------- | ------------------------- |
| `athena_tms.item`              | `project_id`               | `core`           | `athena_core.project`     |
| `athena_tms.item`              | `created_by`, `updated_by` | `core`           | `athena_core.user`        |
| `athena_tms.item_version_mid`  | `version_id`               | `core`           | `athena_core.app_version` |
| `athena_tms.cycle`             | `version_id`               | `core`           | `athena_core.app_version` |
| `athena_tms.execution`         | `executor_id`              | `core`           | `athena_core.user`        |
| `athena_tms.status_transition` | `author`                   | `core`           | `athena_core.user`        |
| `athena_tms.sync_info`         | `project_id`               | `core`           | `athena_core.project`     |

---

## Schema: Entity Relationship Diagram

```
athena_core.project (1)
  └── athena_tms.item (N) [FK: project_id]
        ├── athena_tms.status         (M:1)
        ├── athena_tms.priority       (M:1)
        ├── athena_tms.type           (M:1)
        ├── athena_tms.metadata       (M:M via item_metadata_mid)
        ├── athena_core.app_version   (M:M via item_version_mid)
        └── athena_tms.status_transition (1:N)

athena_core.app_version (1)
  └── athena_tms.cycle (N) [FK: version_id]
        └── athena_tms.execution (1:N) [FK: cycle_id]
              ├── athena_tms.item   (M:1) [FK: item_id]
              ├── athena_tms.status (M:1) [FK: status_id]
              └── athena_core.user  (M:1) [FK: executor_id]
```

# Data Model: metric-derived-quality-actions

## Database Schema (PostgreSQL, `athena_metric` schema)

Source: `V7__metric_init.sql`

### Table: action

```sql
create table athena_metric.action (
  id        bigserial primary key,
  category  varchar(100) not null,
  name      varchar(100) not null,
  type      varchar(100) not null,
  target    varchar(1000) not null,
  command   varchar(5000) not null,
  parameter varchar(5000)
);
create index IDXadh6fulxe89os666w4l6aeepb on athena_metric.action (name, type, target, command);
```

> **Note**: No UNIQUE constraint — only an index. Concurrent saves can create duplicate Action rows.

### Table: metric

```sql
create table athena_metric.metric (
  id             bigserial primary key,
  project_id     bigint not null,
  environment_id bigint not null,
  action_id      bigint not null,
  action_time    TIMESTAMPTZ not null,
  duration       bigint,
  foreign key (project_id)     references athena_core.project,
  foreign key (environment_id) references athena_core.environment,
  foreign key (action_id)      references athena_metric.action
);
```

---

## Entity-to-DTO Mapping

| Entity   | DTO         | Key Conversions                                                                               |
| -------- | ----------- | --------------------------------------------------------------------------------------------- |
| `Metric` | `MetricDto` | `projectId` ↔ project code; `environmentId` ↔ environment code; `action` ↔ nested `ActionDto` |
| `Action` | `ActionDto` | Direct category/name/type/target/command/parameter fields                                     |

---

## DTO Reference

### MetricDto

```java
Long id
String project       // project code
String environment   // environment code
Long duration        // measurement value (unit unspecified)
Instant actionTime
ActionDto action
```

### ActionDto

```java
Long id
String category      // grouping category
String name          // action name
String type          // action type
String target        // target identifier (up to 1000 chars)
String command       // full command (up to 5000 chars)
String parameter     // optional parameter string (up to 5000 chars)
```

---

## Cross-Domain FK Dependencies

| Table                  | FK Column        | External Service | Target Table              |
| ---------------------- | ---------------- | ---------------- | ------------------------- |
| `athena_metric.metric` | `project_id`     | `core`           | `athena_core.project`     |
| `athena_metric.metric` | `environment_id` | `core`           | `athena_core.environment` |

---

## Schema: Entity Relationship Diagram

```
athena_core.project (1) ─────┐
                               └── athena_metric.metric (N)
athena_core.environment (1) ──┘         │
                                          └── athena_metric.action (M:1)
```

---

## Schema Notes

- **Smallest schema** in Athena: 2 tables, no junction tables, no metadata tables
- `duration` stores raw numeric value — callers must agree on unit (no column for unit type)
- `action.parameter` is nullable — allows recording actions without parameters
- `action_time` is the timestamp of the measurement event (not ingestion time)

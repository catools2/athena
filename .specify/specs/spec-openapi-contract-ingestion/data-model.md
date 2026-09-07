# Data Model: spec-openapi-contract-ingestion

## Database Schema (PostgreSQL, `athena_openapi` schema)

Source: `V9__spec_init.sql`

### Table: api_spec

```sql
create table athena_openapi.api_spec (
  id            bigserial primary key,
  project_id    bigint not null,
  name          varchar(100) not null,
  title         varchar(100) not null,
  version       varchar(10) not null,
  first_time_seen TIMESTAMPTZ,
  last_sync_time  TIMESTAMPTZ,
  foreign key (project_id) references athena_core.project
);
```

**Identity**: `(id)` PK  
**Application uniqueness**: `(project_id, name)` — enforced by service, no DB constraint  
**FK**: `project_id` → `athena_core.project`

### Table: api_path

```sql
create table athena_openapi.api_path (
  id              bigserial primary key,
  spec_id         bigint not null,
  method          varchar(10) not null,
  url             varchar(500) not null,
  title           varchar(1000),
  description     varchar(5000),
  parameters      jsonb,
  first_time_seen TIMESTAMPTZ,
  last_sync_time  TIMESTAMPTZ,
  foreign key (spec_id) references athena_openapi.api_spec
);
```

**FK**: `spec_id` → `athena_openapi.api_spec`  
**JSONB**: `parameters` stores `Map<String, String>` — arbitrary key-value

### Table: api_spec_metadata

```sql
create table athena_openapi.api_spec_metadata (
  id    bigserial primary key,
  name  varchar(100) not null,
  value varchar(2000) not null,
  constraint uk_api_spec_metadata_name_value unique (name, value)
);
create index idx_api_spec_metadata_name_value on athena_openapi.api_spec_metadata(name, value);
```

**Uniqueness**: `(name, value)` — deduplication table; shared across all specs

### Table: api_path_metadata

```sql
create table athena_openapi.api_path_metadata (
  id    bigserial primary key,
  name  varchar(100) not null,
  value varchar(2000) not null,
  constraint uk_api_path_metadata_name_value unique (name, value)
);
create index idx_api_path_metadata_name_value on athena_openapi.api_path_metadata(name, value);
```

### Table: api_spec_metadata_mid (M:M junction)

```sql
create table athena_openapi.api_spec_metadata_mid (
  spec_id     bigint not null,
  metadata_id bigint not null,
  primary key (spec_id, metadata_id),
  foreign key (spec_id)     references athena_openapi.api_spec,
  foreign key (metadata_id) references athena_openapi.api_spec_metadata
);
```

### Table: path_metadata_mid (M:M junction)

```sql
create table athena_openapi.path_metadata_mid (
  path_id     bigint not null,
  metadata_id bigint not null,
  primary key (path_id, metadata_id),
  foreign key (path_id)     references athena_openapi.api_path,
  foreign key (metadata_id) references athena_openapi.api_path_metadata
);
```

---

## Entity-to-DTO Mapping

| Entity Field            | DTO Field                  | Conversion                                                | Notes                                                       |
| ----------------------- | -------------------------- | --------------------------------------------------------- | ----------------------------------------------------------- |
| `ApiSpec.id`            | `ApiSpecDto.id`            | Direct                                                    | Set on read; ignored on create                              |
| `ApiSpec.projectId`     | `ApiSpecDto.project`       | `projectId` ↔ project code via `ApiSpecMapperServiceImpl` | FK resolution via CachedProjectFeignService                 |
| `ApiSpec.name`          | `ApiSpecDto.name`          | Direct                                                    | Identity field                                              |
| `ApiSpec.title`         | `ApiSpecDto.title`         | Direct                                                    |                                                             |
| `ApiSpec.version`       | `ApiSpecDto.version`       | Direct                                                    |                                                             |
| `ApiSpec.firstTimeSeen` | `ApiSpecDto.firstTimeSeen` | Direct                                                    |                                                             |
| `ApiSpec.lastSyncTime`  | `ApiSpecDto.lastSyncTime`  | Direct                                                    |                                                             |
| `ApiSpec.metadata`      | `ApiSpecDto.metadata`      | `ApiSpecMetadata` ↔ `MetadataDto`                         |                                                             |
| `ApiSpec.paths`         | `ApiSpecDto.paths`         | `ApiPath` ↔ `ApiPathDto`                                  | Non-destructive on update                                   |
| `ApiPath.spec.id`       | `ApiPathDto.specId`        | `spec.id` ↔ `specId`                                      | MapStruct `@Mapping(source = "spec.id", target = "specId")` |
| `ApiPath.parameters`    | `ApiPathDto.parameters`    | `Map<String, String>` ↔ JSONB                             | Jackson handles serialization                               |

---

## DTO Reference

### ApiSpecDto

```java
Long id
String project         // project code (maps to projectId FK)
String name            // spec name; unique within project
String title
String version         // e.g. "3.0.1", "2.0"
Instant firstTimeSeen
Instant lastSyncTime
Set<MetadataDto> metadata
Set<ApiPathDto>  paths
```

### ApiPathDto

```java
Long id
Long specId
String method          // HTTP verb: GET, POST, PUT, DELETE, PATCH
String url             // endpoint path: /users/{id}
String title
String description
Map<String, String> parameters   // JSON column
Instant firstTimeSeen
Instant lastSyncTime
Set<MetadataDto> metadata
```

### MetadataDto (shared)

```java
Long id
String name
String value
```

---

## Cross-Domain FK Dependencies

| Table                     | FK Column    | External Service | Target Table          |
| ------------------------- | ------------ | ---------------- | --------------------- |
| `athena_openapi.api_spec` | `project_id` | `core`           | `athena_core.project` |

---

## Schema: Entity Relationship Diagram

```
athena_core.project (1)
  └── athena_openapi.api_spec (N) [FK: project_id]
        ├── athena_openapi.api_spec_metadata (M:M via api_spec_metadata_mid)
        └── athena_openapi.api_path (1:N) [FK: spec_id]
              └── athena_openapi.api_path_metadata (M:M via path_metadata_mid)
```

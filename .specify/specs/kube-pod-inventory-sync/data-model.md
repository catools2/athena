# Data Model: kube-pod-inventory-sync

## Database Schema (PostgreSQL, athena_kube schema)

### Table: pod

```sql
create table athena_kube.pod (
  id bigserial primary key,
  uid varchar(36) unique,
  name varchar(500) unique,
  namespace varchar(100),
  hostname varchar(200),
  node_name varchar(200),
  created_at TIMESTAMPTZ,
  deleted_at TIMESTAMPTZ,
  last_sync TIMESTAMPTZ not null,
  project_id bigint not null,
  status_id bigint not null,
  foreign key (project_id) references athena_core.project,
  foreign key (status_id) references athena_kube.pod_status
);
```

**Identity**: `(id)` primary key  
**Uniqueness**: `(name)` unique; `(uid)` unique — both can be used to find a pod  
**FK Dependencies**: `project_id` → `athena_core.project`, `status_id` → `athena_kube.pod_status`  
**Soft Delete**: `deleted_at` column present; can mark pod as deleted without removal

### Table: pod_status

```sql
create table athena_kube.pod_status (
  id bigserial primary key,
  name varchar(200),
  phase varchar(200),
  message varchar(1000),
  reason varchar(1000),
  constraint UniquePodStatus unique (name, phase, message, reason)
);
```

**Identity**: `(id)` primary key  
**Uniqueness**: `(name, phase, message, reason)` unique — shared enum-like table across all pods  
**Purpose**: De-duplicates pod status; same status tuple reused across multiple pods

### Table: container

```sql
create table athena_kube.container (
  id bigserial not null,
  pod_id bigint not null,
  type varchar(100) not null,
  name varchar(300) not null,
  image varchar(1000) not null,
  image_id varchar(300) not null,
  ready boolean,
  started boolean,
  restart_count integer,
  started_at TIMESTAMPTZ,
  last_sync TIMESTAMPTZ not null,
  primary key (id),
  foreign key (pod_id) references athena_kube.pod
);
```

**Identity**: `(id)` primary key  
**FK Dependencies**: `pod_id` → `athena_kube.pod`  
**Lifecycle**: Cascade delete with pod

### Metadata Tables (Generic Pattern)

#### pod_metadata

```sql
create table athena_kube.pod_metadata (
  id bigserial primary key,
  name varchar(300) not null,
  value varchar(1000) not null,
  constraint UniquePodMetadataNameValue unique (name, value)
);
```

#### pod_annotation

```sql
create table athena_kube.pod_annotation (
  id bigserial primary key,
  name varchar(300) not null,
  value varchar(1000) not null,
  constraint UniquePodAnnotationNameValue unique (name, value)
);
```

#### pod_label

```sql
create table athena_kube.pod_label (
  id bigserial primary key,
  name varchar(300) not null,
  value varchar(1000) not null,
  constraint UniquePodLabelNameValue unique (name, value)
);
```

#### pod_selector

```sql
create table athena_kube.pod_selector (
  id bigserial primary key,
  name varchar(300) not null,
  value varchar(1000) not null,
  constraint UniquePodSelectorNameValue unique (name, value)
);
```

#### container_metadata

```sql
create table athena_kube.container_metadata (
  id bigserial primary key,
  name varchar(300) not null,
  value varchar(1000) not null,
  constraint UniquePodContainerMetadataNameValue unique (name, value)
);
```

### Junction Tables (M:M)

#### pod_metadata_mid

```sql
create table athena_kube.pod_metadata_mid (
  pod_id bigint not null,
  metadata_id bigint not null,
  primary key (pod_id, metadata_id),
  foreign key (pod_id) references athena_kube.pod,
  foreign key (metadata_id) references athena_kube.pod_metadata
);
```

#### pod_annotation_mid

```sql
create table athena_kube.pod_annotation_mid (
  pod_id bigint not null,
  annotation_id bigint not null,
  primary key (pod_id, annotation_id),
  foreign key (pod_id) references athena_kube.pod,
  foreign key (annotation_id) references athena_kube.pod_annotation
);
```

#### pod_label_mid

```sql
create table athena_kube.pod_label_mid (
  pod_id bigint not null,
  label_id bigint not null,
  primary key (pod_id, label_id),
  foreign key (pod_id) references athena_kube.pod,
  foreign key (label_id) references athena_kube.pod_label
);
```

#### pod_selector_mid

```sql
create table athena_kube.pod_selector_mid (
  pod_id bigint not null,
  selector_id bigint not null,
  primary key (pod_id, selector_id),
  foreign key (pod_id) references athena_kube.pod,
  foreign key (selector_id) references athena_kube.pod_selector
);
```

#### container_metadata_mid

```sql
create table athena_kube.container_metadata_mid (
  container_id bigint not null,
  metadata_id bigint not null,
  primary key (container_id, metadata_id),
  foreign key (container_id) references athena_kube.container,
  foreign key (metadata_id) references athena_kube.container_metadata
);
```

---

## Entity-to-DTO Mapping

| Entity                                                    | DTO            | Mapping Notes                                                                                                 |
| --------------------------------------------------------- | -------------- | ------------------------------------------------------------------------------------------------------------- |
| `Pod`                                                     | `PodDto`       | Direct 1:1; `projectId` ↔ `project` (project code via `KubeMapperService`); contains all metadata collections |
| `Container`                                               | `ContainerDto` | Direct 1:1; pod link excluded from DTO (implicit via parent)                                                  |
| `PodStatus`                                               | `PodStatusDto` | Direct 1:1                                                                                                    |
| `PodMetadata`, `PodAnnotation`, `PodLabel`, `PodSelector` | `MetadataDto`  | Mapped to generic `(name, value)` pair                                                                        |
| `ContainerMetadata`                                       | `MetadataDto`  | Mapped to generic `(name, value)` pair                                                                        |

---

## DTO Field Reference

### PodDto

```java
Long id
String uid                      // unique kubernetes pod UID
String name                     // unique within system
String namespace                // kubernetes namespace
String hostname                 // pod hostname
String nodeName                 // kubernetes node name
Instant createdAt
Instant deletedAt               // null if active, set for soft deletes
Instant lastSync                // timestamp of last ingestion
String project                  // project code (resolved from projectId FK)
PodStatusDto status
Set<MetadataDto> metadata       // custom metadata
Set<MetadataDto> annotations    // k8s annotations
Set<MetadataDto> labels         // k8s labels
Set<MetadataDto> selectors      // k8s selectors
Set<ContainerDto> containers
```

### ContainerDto

```java
Long id
String type                     // e.g., "init", "app"
String name
String image                    // container image URI
String imageId                  // container image ID/digest
Boolean ready
Boolean started
Integer restartCount
Instant startedAt
Instant lastSync
Set<MetadataDto> metadata
```

### PodStatusDto

```java
Long id
String name                     // status name (e.g., "Running", "Pending")
String phase                    // kubernetes phase
String message                  // detailed message
String reason                   // brief reason
```

### MetadataDto

```java
Long id
String name
String value
```

---

## Cross-Domain FK Dependencies

| Dependent Table   | FK Column    | Target Service | Target Table          | Notes                              |
| ----------------- | ------------ | -------------- | --------------------- | ---------------------------------- |
| `athena_kube.pod` | `project_id` | `core`         | `athena_core.project` | Project must exist before pod save |

---

## Uniqueness & Idempotency

| Table                                                         | Unique Constraint(s)             | Idempotent Save Behavior                                              |
| ------------------------------------------------------------- | -------------------------------- | --------------------------------------------------------------------- |
| `pod`                                                         | `(name)`, `(uid)`                | Save-or-update on `findByNameAndNamespace()`; updates fields if found |
| `pod_status`                                                  | `(name, phase, message, reason)` | Deduplicates status across pods; shared lookup/create                 |
| `pod_metadata`, `pod_annotation`, `pod_label`, `pod_selector` | `(name, value)`                  | Normalized during save; reused if found                               |
| `container_metadata`                                          | `(name, value)`                  | Normalized during save; reused if found                               |

---

## Optimization Notes

- **Metadata Deduplication**: All 5 metadata types use synchronized `normalizeMetadata()` method to prevent duplicates
- **Status Deduplication**: PodStatus normalized via synchronized method; shared across pods
- **Unique Indexes**: Pod name, pod uid, all metadata (name, value) pairs
- **Cascade**: Containers cascade delete with pod
- **Container Eager Load**: Containers fetched eagerly in pod (EAGER); consider lazy if large container counts
- **Race Condition Handling**: `DataIntegrityViolationException` on concurrent metadata/status save triggers retry lookup

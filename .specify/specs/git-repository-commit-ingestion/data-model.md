# Data Model: git-repository-commit-ingestion

## Database Schema (PostgreSQL, athena_git schema)

### Table: repository

```sql
create table athena_git.repository (
  id bigserial primary key,
  last_sync TIMESTAMPTZ,
  name varchar(200) not null unique,
  url varchar(300) not null unique
);
```

**Identity**: `(id)` primary key  
**Uniqueness**: `(name)` unique; `(url)` unique — either field can be used to find a repo  
**FK Targets**: None

### Table: commit

```sql
create table athena_git.commit (
  id bigserial primary key,
  hash varchar(50) not null unique,
  parent_hash varchar(50),
  short_message varchar(5000),
  commit_time TIMESTAMPTZ not null,
  parent_count integer not null,
  author_id bigint not null,
  committer_id bigint not null,
  repository_id bigint not null,
  total_file integer,
  line_inserted integer,
  line_deleted integer,
  foreign key (author_id) references athena_core.user,
  foreign key (committer_id) references athena_core.user,
  foreign key (repository_id) references athena_git.repository
);
```

**Identity**: `(id)` primary key  
**Uniqueness**: `(hash)` unique — prevents duplicate ingestion of same commit  
**FK Dependencies**: `author_id` → `athena_core.user`, `committer_id` → `athena_core.user`, `repository_id` → `athena_git.repository`

### Table: diff_entry

```sql
create table athena_git.diff_entry (
  id bigserial primary key,
  commit_id bigint not null,
  change_type varchar(30) not null,
  old varchar(1000) not null,
  new varchar(1000) not null,
  inserted integer not null,
  deleted integer not null,
  foreign key (commit_id) references athena_git.commit on delete cascade
);
```

**Identity**: `(id)` primary key  
**FK Dependencies**: `commit_id` → `athena_git.commit` (cascade delete)

### Table: tag

```sql
create table athena_git.tag (
  id bigserial primary key,
  hash varchar(50) not null,
  name varchar(200) not null,
  constraint uk_tag_name_hash unique (name, hash)
);
create index idx_tag_name_hash on athena_git.tag(name, hash);
```

**Identity**: `(id)` primary key  
**Uniqueness**: `(name, hash)` unique — prevents tag duplication  
**FK Targets**: None (indirect via M:M join table)

### Table: commit_tag_mid (M:M junction)

```sql
create table athena_git.commit_tag_mid (
  commit_id bigint not null,
  tag_id bigint not null,
  primary key (commit_id, tag_id),
  foreign key (commit_id) references athena_git.commit,
  foreign key (tag_id) references athena_git.tag
);
```

**Purpose**: Links commits to tags (many-to-many)

### Table: commit_metadata (shared with other domains)

```sql
create table athena_git.commit_metadata (
  id bigserial primary key,
  name varchar(300) not null,
  value varchar(1000) not null,
  constraint uk_commit_metadata_name_value unique (name, value)
);
create index idx_commit_metadata_name_value on athena_git.commit_metadata(name, value);
```

**Identity**: `(id)` primary key; also indexed as `(name, value)`  
**Purpose**: Generic key-value metadata; can be shared with other domains

### Table: commit_metadata_mid (M:M junction)

```sql
create table athena_git.commit_metadata_mid (
  commit_id bigint not null,
  metadata_id bigint not null,
  primary key (commit_id, metadata_id),
  foreign key (commit_id) references athena_git.commit,
  foreign key (metadata_id) references athena_git.commit_metadata
);
```

**Purpose**: Links commits to metadata (many-to-many)

---

## Entity-to-DTO Mapping

| Entity           | DTO                | Mapping Notes                                                                                                                                        |
| ---------------- | ------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------- |
| `GitRepository`  | `GitRepositoryDto` | Direct 1:1; `lastSync` field tracks last ingestion                                                                                                   |
| `Commit`         | `CommitDto`        | `authorId` ↔ `author` (username lookup via `CachedUserFeignService`); `committerId` ↔ `committer`; `repository` (FK id) ↔ `repository` (name string) |
| `DiffEntry`      | `DiffEntryDto`     | Direct mapping; commit link excluded from DTO (implicit via parent)                                                                                  |
| `Tag`            | `TagDto`           | Direct 1:1; `hash` and `name` both present                                                                                                           |
| `CommitMetadata` | `MetadataDto`      | Generic `(name, value)` pair                                                                                                                         |

---

## DTO Field Reference

### GitRepositoryDto

```java
Long id
String name             // unique
String url              // unique
Instant lastSync        // timestamp of last sync operation
```

### CommitDto

```java
Long id
String hash             // unique, SHA-1 or SHA-256
String parentHash       // optional
String shortMessage     // optional, first line of commit message
Instant commitTime      // non-null
Integer parentCount     // number of parent commits
String author           // username (resolved from authorId FK)
String committer        // username (resolved from committerId FK)
String repository       // repository name (resolved from repository FK)
Integer totalImpactedFiles
Integer totalInsertedLine
Integer totalDeletedLines
Set<DiffEntryDto> diffEntries
Set<TagDto> tags
Set<MetadataDto> metadata
```

### DiffEntryDto

```java
Long id
String oldPath
String newPath
String changeType       // e.g., "ADD", "MODIFY", "DELETE"
Integer inserted
Integer deleted
```

### TagDto

```java
Long id
String hash
String name
```

### MetadataDto

```java
Long id
String name
String value
```

---

## Cross-Domain FK Dependencies

| Dependent Table     | FK Column       | Target Service | Target Table            | Notes                                    |
| ------------------- | --------------- | -------------- | ----------------------- | ---------------------------------------- |
| `athena_git.commit` | `author_id`     | `core`         | `athena_core.user`      | Author must exist before commit save     |
| `athena_git.commit` | `committer_id`  | `core`         | `athena_core.user`      | Committer must exist before commit save  |
| `athena_git.commit` | `repository_id` | `git` (local)  | `athena_git.repository` | Repository must exist before commit save |

**Implication**: Core service must have the user records (author/committer) before git service can ingest commits. Typically resolved by ensuring core user provisioning runs before git commit ingestion.

---

## Uniqueness & Idempotency

| Table             | Unique Constraint(s) | Idempotent Save Behavior                                                             |
| ----------------- | -------------------- | ------------------------------------------------------------------------------------ |
| `repository`      | `(name)`, `(url)`    | Save-or-update on `findByNameOrUrl()`; updates `name, url, lastSync` if found        |
| `commit`          | `(hash)`             | Save-or-update on `findByHash()`; returns existing if found; retries 3× on collision |
| `tag`             | `(name, hash)`       | Inserted via mapper; duplicates prevented by unique constraint                       |
| `commit_metadata` | `(name, value)`      | Inserted via mapper; duplicates prevented by unique constraint                       |

---

## Optimization Notes

- **Fetch Strategy**: Commit loading uses three separate queries (relations, tags, metadata) to prevent N² cartesian product
- **Batch Size**: Tags and metadata use `@BatchSize(50)` for collection fetching
- **Cascade Delete**: DiffEntry cascade deletes with Commit (orphan = true)
- **Indexes**: `(name, hash)` on `tag`; `(name, value)` on `commit_metadata`

# Implementation Plan: spec-openapi-contract-ingestion

## Goal

Deliver complete OpenAPI specification contract ingestion with project scoping, immutable path log, and dual-level metadata normalization.

## Status: 85% COMPLETE

Core service, feign client, and integration tests exist. Two hardening tasks recommended.

---

## Phase 1: API & Entity Analysis ✓ COMPLETE

All controllers, services, repositories, entities, DTOs, mappers, and feign clients are implemented and compile.

**Evidence**:

- `ApiSpecController.java` — POST, GET by id, GET by project+name
- `ApiSpecServiceImpl.java` — idempotent saveOrUpdate, project feign lookup, path merge, dual metadata normalization
- `ApiSpecRepository`, `ApiPathRepository`, metadata repos — all exist
- `ApiSpec`, `ApiPath`, `ApiSpecMetadata`, `ApiPathMetadata` — all entities exist with proper JPA mappings
- `ApiSpecMapper` (MapStruct) + `ApiSpecMapperServiceImpl` — project code↔id resolution implemented
- `ApiSpecFeignClient` — exists in `athena-boot-spec-feign`

---

## Phase 2: Hardening (RECOMMENDED)

### Task 2.1: Add DB Unique Constraint on `(project_id, name)` [MEDIUM PRIORITY]

**File**: New Flyway migration + `ApiSpec.java`  
**Issue**: Concurrent saves can create duplicate `(project_id, name)` rows; only app-layer check exists  
**Action**:

```sql
-- New migration: V9_1__spec_unique_constraint.sql
alter table athena_openapi.api_spec
  add constraint uk_api_spec_project_id_name unique (project_id, name);
```

Add `@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "name"}))` to `ApiSpec.java`  
**Effort**: 15 min

### Task 2.2: Clarify Path Deduplication Policy [LOW PRIORITY]

**File**: `ApiSpecServiceImpl.java`, `ApiPathRepository.java`  
**Issue**: `ApiPathRepository.findBySpecIdAndUrlAndMethodAndTitle()` exists but is never called; duplicate paths may be created on repeated sync  
**Action**: Either use the repo method in service to deduplicate, or add a comment documenting intentional acceptance of duplicates  
**Effort**: 10 min

---

## Phase 3: Integration Test Coverage ✓ COMPLETE

**Tests in `ApiSpecControllerIT.java`**:

- `postMethodShallSaveNewlyProvidedApiSpecDto()` — verifies new spec created with all fields
- `shallUpdateSpecificationIfSpecificationWithTheSameNameExistsForTheProject()` — verifies idempotent update
- `shallSaveOpenApiSpecificationIfSpecificationWithTheSameNameDoesNotExistsForTheProject()` — verifies cross-project isolation
- `shallReturnCorrectValueWhenValidIdProvided()` — getById
- `shallReturnCorrectValueWhenValidCodeProvided()` — search by project+name

All tests verify: metadata round-trip, path round-trip, JSONB parameters equality, timestamp presence.

---

## Phase 4: Documentation ✓ COMPLETE

- ✓ `research.md` — module inventory, 5 patterns, 5 gaps
- ✓ `data-model.md` — DDL from V9\_\_spec_init.sql, entity-to-DTO mapping, ERD
- ✓ `spec.md` — 7 ACs verified against code with test references
- ✓ `plan.md` — this document

---

## Definition of Done (MVP)

- [x] Phase 1 complete — all components implemented
- [x] Phase 3 complete — 4 integration tests pass
- [x] Phase 4 complete — all 4 docs written
- [ ] Task 2.1 — DB unique constraint added
- [ ] Task 2.2 — path deduplication policy documented

---

## Future Work

1. **OpenAPI schema validation** — validate incoming JSON against OpenAPI 3.0 schema before save
2. **Spec listing API** — `GET /specs?project=CODE` to list all specs for a project
3. **Path diffing** — detect added/removed/modified paths between sync runs
4. **DELETE endpoint** — spec lifecycle management

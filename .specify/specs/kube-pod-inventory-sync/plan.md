# Implementation Plan: kube-pod-inventory-sync

## Goal

Deliver complete Kubernetes pod inventory synchronization contract with project binding, metadata deduplication, and comprehensive test coverage protecting against concurrent ingestion issues.

## Status: 70% COMPLETE

Most pod/container/metadata logic is implemented. Critical gaps: `KubeMapperServiceImpl`, feign clients, and some service hardening.

---

## Phase 1: API & Entity Analysis ✓ COMPLETE

**Deliverable**: Confirm all endpoints, entities, DTOs, services, and repositories exist.

**Tasks**:

- ✓ `PodController`: POST, GET by id, GET by name+namespace, GET `/pods` by project+namespace
- ✓ `PodService` + `PodServiceImpl`: saveOrUpdate with project binding, getPods, getByNameAndNamespace, getById
- ✓ `PodRepository`, `PodStatusRepository`, metadata repositories (5 types)
- ✓ `Pod`, `Container`, `PodStatus`, metadata entities (5 types)
- ✓ `PodDto`, `ContainerDto`, `PodStatusDto`, `MetadataDto` in `athena-model`
- ✓ `KubeMapper` (MapStruct) for bidirectional mapping
- ✓ `KubeMapperService` interface defined (but impl missing — see Phase 2)
- ✓ `KubeControllerIT`, `PodControllerIT` integration tests

**Evidence**: All entity/controller/service files exist in `athena-boot-kube/src/main/java/`; search_subagent provided 20+ code snippets.

**Acceptance**: Phase complete if all 20+ files exist, compile, and no TODO comments.

---

## Phase 2: Critical Gaps & Implementation (REQUIRED)

**Deliverable**: Identify and implement 2-3 blocking gaps preventing prod readiness.

**Tasks**:

### Task 2.1: Implement KubeMapperServiceImpl

**File**: `athena-boot-kube/src/main/java/org/catools/athena/kube/common/mapper/` (create new file)  
**Issue**: `KubeMapperService` interface exists but impl not found; pod saves will fail on project code → id lookup  
**Requirement**: `KubeMapperServiceImpl` must call core service feign client for project lookup  
**Recommendation**: Similar pattern to `GitMapperServiceImpl`; use `CachedProjectFeignService` or project-specific feign  
**Effort**: 30 min

```java
@Service
@Slf4j
public class KubeMapperServiceImpl implements KubeMapperService {
  @Autowired
  private CachedProjectFeignService projectFeignService; // or equivalent

  @Override
  @Transactional(readOnly = true)
  public Long getProjectId(String projectCode) {
    if (StringUtils.isBlank(projectCode)) return null;
    return Optional.ofNullable(projectFeignService.search(projectCode).body())
        .map(ProjectDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("project", "code", projectCode));
  }

  @Override
  @Transactional(readOnly = true)
  public String getProjectCode(Long projectId) {
    if (projectId == null) return null;
    return Optional.ofNullable(projectFeignService.getById(projectId).body())
        .map(ProjectDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("project", "id", projectId));
  }
}
```

**Test**: Unit test with mock feign response

### Task 2.2: Create PodFeignClient in athena-boot-kube-feign

**File**: `athena-boot-kube-feign/src/main/java/org/catools/athena/.../PodFeignClient.java` (create new file)  
**Issue**: Feign clients referenced in test but interfaces not found  
**Requirement**: Expose `/pod`, `/pods` endpoints via feign client for downstream services  
**Effort**: 20 min

```java
@FeignClient(name = "athena-kube", url = "${feign.clients.athena.kube.url}")
public interface PodFeignClient {
  @PostMapping("/pod")
  TypedResponse<Void> saveOrUpdate(@RequestBody PodDto pod);

  @GetMapping("/pod/{id}")
  TypedResponse<PodDto> getById(@PathVariable Long id);

  @GetMapping("/pod")
  TypedResponse<PodDto> getByNameAndNamespace(@RequestParam String name, @RequestParam String namespace);

  @GetMapping("/pods")
  TypedResponse<Set<PodDto>> getAll(@RequestParam String project, @RequestParam String namespace);
}
```

**Test**: Integration test verifies endpoints accessible via feign

### Task 2.3: Add Soft Delete Support (Optional)

**Files**: `PodController`, `PodServiceImpl`, `PodRepository`  
**Issue**: `deletedAt` column exists but DELETE endpoints and soft-delete queries not implemented  
**Recommendation**: Add DELETE `/pod/{id}` that sets `deletedAt`; add `findByProjectIdAndNamespaceAndDeletedAtNull()` to exclude soft-deleted pods from queries  
**Effort**: 30 min (optional for MVP)  
**Acceptance**: If required, document soft-delete policy and update queries

---

## Phase 3: Integration Test Coverage ✓ MOSTLY COMPLETE

**Deliverable**: Comprehensive integration test coverage.

**Tasks**:

- ✓ `KubeControllerIT`: Base class; sets up project; builds/saves 2 test pods
- ✓ `PodControllerIT`: 4 tests covering save, update on name+namespace match, getAll by project+namespace, getByNameAndNamespace, getById
- ⚠ Missing: Metadata deduplication test (verify no duplicate metadata across 2 pods with same labels)
- ⚠ Missing: Race condition test for synchronized metadata/status normalization

**Recommended Additions**:

- Add test `podsShouldShareMetadataWhenLabelsAreIdentical()` verifying metadata deduplication
- Add test for soft-delete if Task 2.3 implemented

---

## Phase 4: Documentation & Validation ✓ COMPLETE (FOR MVP)

**Deliverable**: Complete research.md, data-model.md, spec.md, plan.md.

**Tasks**:

- ✓ research.md: Module inventory, synchronized metadata pattern, project binding, identified gaps
- ✓ data-model.md: Exact SQL schema from V6\_\_kube_init.sql, entity-to-DTO mapping, FK dependencies
- ✓ spec.md: 7 ACs all verified against code + implementation references + test citations
- ✓ plan.md: This document with 4 phases and critical gap identification

**Acceptance**: All 4 markdown files present with correct content.

---

## Recommended Future Work (Post-MVP)

1. **Soft-delete enforcement** (Phase 2, Task 2.3) — document policy and filter deleted pods from queries
2. **Pod history/retention** — currently snapshot-only; if historical analysis needed, add timestamp-based queries
3. **Lazy-load containers** — currently EAGER; if large pod counts, switch to LAZY with selective batch loading
4. **Metadata filtering API** — add `/pods/all?project=X&label=Y:Z` for label-based pod discovery

---

## Definition of Done (MVP)

- [ ] Phases 1, 2.1, 2.2, 3, 4 complete
- [ ] All spec ACs verified against code
- [ ] Compile: `./mvnw clean compile -DskipTests` succeeds
- [ ] Unit tests: `./mvnw test` passes (or expected timeouts on TestContainers)
- [ ] `KubeMapperServiceImpl` implemented and tested
- [ ] `PodFeignClient` created in feign module
- [ ] research.md, data-model.md, spec.md, plan.md all present with correct content
- [ ] `.specify/specs/kube-pod-inventory-sync/` ready for publication

---

## Dependencies & Blockers

- **Core service availability** for project lookup during pod save
- **TestContainers + Docker** for running ITs locally
- **No external blockers** — feature is self-contained within kube service

---

## Risk Mitigation

| Risk                                                    | Mitigation                                                         |
| ------------------------------------------------------- | ------------------------------------------------------------------ |
| Metadata table bloat from duplicate (name, value) pairs | Synchronized deduplication in `normalizeMetadata()`                |
| Race condition on concurrent metadata/status creation   | Try/catch `DataIntegrityViolationException` + retry lookup pattern |
| Missing project → id lookup breaks pod saves            | Implement `KubeMapperServiceImpl` (Task 2.1)                       |
| Feign clients not available to consumers                | Create `PodFeignClient` (Task 2.2)                                 |
| Container eager-load performance                        | Document as known constraint; lazy-load optional future work       |

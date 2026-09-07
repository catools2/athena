---
topics: athena-features
---

# 5 · The feature packages

Nine packages live under `.specify/specs`. Four of them declare themselves
**IMPLEMENTED**; the rest describe work that is specified but not finished.

## Implemented

- **`metric-derived-quality-actions`** — the Metric module captures quality
  measurements from CI/CD and operational tooling. Each metric records the
  outcome of a specific action: a named command against a target.
  *Status: IMPLEMENTED, IT suite passing.*
- **`pipeline-execution-timeline-capture`** — the Pipeline module ingests CI/CD
  execution data from automated test runs, capturing full timing timelines for
  unit and integration test method executions.
  *Status: IMPLEMENTED, IT suite passing.*
- **`tms-test-cycle-execution-sync`** — the TMS module ingests test items, test
  cycles and execution results from Jira/Zephyr, maintaining audit trails
  through status transitions.
  *Status: IMPLEMENTED, 6 IT suites passing.*
- **`spec-openapi-contract-ingestion`** — project-scoped spec storage, an
  immutable path log, dual-level metadata normalisation and point queries across
  `api_spec` and `api_path`. Domains: `athena-boot-spec`,
  `athena-boot-spec-feign`, `athena-model`.

## Specified

- **`git-repository-commit-ingestion`** — repositories, commits, tags and diff
  entries. Domains: `athena-boot-git`, `athena-boot-git-feign`, `athena-model`,
  `athena-common`.
- **`kube-pod-inventory-sync`** — pod snapshots, containers, metadata and
  status, synchronised from clusters. Domains: `athena-boot-kube`,
  `athena-boot-kube-feign`, `athena-model`, `athena-common`.
- **`athena-unified-web-ui-reporting`** — one browser interface spanning every
  user-facing boot module (`core`, `git`, `kube`, `metric`, `pipeline`, `spec`,
  `tms`), with a reporting/dashboard theme, and the prerequisite backend,
  gateway and contract work planned explicitly.
- **`platform-specify-rollout`** — the `.specify` scaffold, slash prompts and
  seeded feature portfolio themselves. Athena documented a Specify workflow in
  the README before the repo had a runnable one; this package is how that gap
  was closed.

## One package to look at

**`core-project-catalog-search`** carries the *same* problem statement, outcome
and title as `athena-unified-web-ui-reporting` — "unified Athena web UI and
reporting". Its folder name promises project catalogue search and its `spec.md`
describes the web UI. One of the two is mislabelled, and until somebody decides
which, the package does not say what it is for.

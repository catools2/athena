---
topics: athena-architecture
---

# 2 · The module map

Athena is a Maven multi-module build: 41 modules, and the names are systematic
rather than historical. Learning the four suffixes is most of learning the tree.

## The four families

- **`athena-boot-<domain>`** — a runnable Spring service for one domain:
  `core`, `git`, `kube`, `metric`, `pipeline`, `spec`, `tms`. This is where the
  REST controllers live.
- **`athena-boot-<domain>-feign`** — the Feign client for that service. Every
  boot module has one, so a service is called through a declared contract rather
  than a hand-built request.
- **`athena-client-<domain>`** — the client-side library for a domain, including
  `athena-client-atlassian-jira`, `athena-client-atlassian-scale`,
  `athena-client-openapi` and `athena-client-pipeline-testng`.
- **`athena-cli-<source>`** — the command-line ingesters that feed the
  platform: `athena-cli-git`, `athena-cli-kube`, `athena-cli-openapi`,
  `athena-cli-atlassian-jira`, `athena-cli-atlassian-scale`.

## The shared modules

- `athena-model` — the entities and DTOs every domain shares.
- `athena-common` and `athena-common-test` — shared utilities, and the test
  support the integration suites are built on.
- `athena-gateway` — the entry point in front of the boot services.
- `athena-frontend` — the browser surface.
- `athena-bom`, `athena-parent`, `athena-boot-parent`, `athena-cli-parent`,
  `athena-client-parent` — dependency and build management, not behaviour.
- `athena-locust` — load testing.
- `athena-boot-init` — bootstrap.

## Why the pairing is load-bearing

A domain is a *pair*: `athena-boot-git` and `athena-boot-git-feign`. Changing the
service's contract without changing its Feign client leaves a caller compiling
against a shape the server no longer serves. The constitution names this
directly under **Contract Integrity**, and it is why a plan is required to list
the modules it touches.

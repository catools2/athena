---
topics: athena-overview
---

# 1 · What Athena is

Athena collects **quality metrics across the software development life cycle**.
The stated purpose is to identify quality problems early enough that fixing them
is cheap, rather than discovering them from a customer.

## What it collects, in the first phase

The README names five sources, and the module tree matches them one for one:

| Source | Module |
|---|---|
| CI/CD pipeline | `athena-boot-pipeline` |
| Git repository | `athena-boot-git` |
| Kubernetes infrastructure | `athena-boot-kube` |
| Task management (Jira / Zephyr) | `athena-boot-tms` |
| Swagger / OpenAPI documentation | `athena-boot-spec` |

Quality measurements themselves live in `athena-boot-metric`, and the shared
nouns — project, version, environment, user — in `athena-boot-core`.

## What it does not do yet

Analysis. The README is explicit that the first phase is *collection*: "in the
future, Athena will analyze data and related metrics to provide realtime insight
to code quality, performance, security, and functional correctness." Reading a
metric out of Athena today means reading what was ingested, not a judgement
about it.

## Why that distinction matters

An ingestion platform and an analysis platform fail differently. A gap in
ingestion is a missing row; a gap in analysis is a wrong conclusion drawn from
rows that are present. Athena is currently the first kind of system, and a
question of the form "is this service healthy" has no answer in it — only the
measurements somebody would use to decide.

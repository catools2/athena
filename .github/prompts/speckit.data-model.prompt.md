---
name: Speckit Data Model
description: "Define or refine DTO, entity, migration, and sync impacts for an Athena feature package."
argument-hint: "Feature folder and model change request"
agent: agent
model: GPT-5 (copilot)
---

The user will provide a feature package path under `.specify/specs/<feature-slug>`.

Your task:

1. Read the feature's `spec.md` and `research.md`.
2. Update `data-model.md` with specific Athena entities, DTOs, relationships, persistence implications, and migration notes.
3. Call out any effects on `athena-model`, Flyway migrations, metadata tables, or external sync flows.

Do not invent tables or fields without clearly marking them as proposed.

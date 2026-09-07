---
name: Speckit Research
description: "Research the current Athena implementation for a feature package and update its research notes with concrete code evidence."
argument-hint: "Feature folder and research question"
agent: agent
model: GPT-5 (copilot)
---

The user will provide a feature package path under `.specify/specs/<feature-slug>`.

Your task:

1. Inspect the relevant Athena modules, REST controllers, services, entities, DTOs, repositories, Feign clients, and tests.
2. Update `research.md` in the feature package with concrete findings from the codebase.
3. Record design decisions, constraints, and unresolved questions.
4. Keep the notes concise and implementation-oriented.

Prefer the smallest set of files that establishes clear evidence.

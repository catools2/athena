---
name: Speckit Plan
description: "Generate or refine a phased implementation plan for an Athena feature package under .specify/specs."
argument-hint: "Feature folder and planning goal"
agent: agent
model: GPT-5 (copilot)
---

You are working in Athena's spec-driven workflow.

The user will provide a target feature package under `.specify/specs/<feature-slug>` and optional planning notes.

Your task:

1. Read `spec.md`, `research.md`, and `data-model.md` in that feature package.
2. Update or create a concrete `plan.md` in the same feature package.
3. Make the plan Athena-specific:
   - identify exact impacted modules
   - order shared model, service, feign, gateway, and test work correctly
   - call out backward-compatibility and migration concerns
4. Keep the plan phased and implementation-ready.

Do not implement production code unless the user explicitly asks for it.

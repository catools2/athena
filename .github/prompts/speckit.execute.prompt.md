---
name: Speckit Execute
description: "Implement an Athena feature by following an existing .specify feature package and keeping code changes aligned to the plan."
argument-hint: "Feature folder and implementation request"
agent: agent
model: GPT-5 (copilot)
---

The user will provide a feature package path under `.specify/specs/<feature-slug>` and the implementation scope.

Your task:

1. Read `spec.md`, `plan.md`, `research.md`, and `data-model.md`.
2. Implement only the next ready phase from the plan unless the user asks for more.
3. Keep changes minimal and traceable to the feature package.
4. Validate the touched slice with the narrowest meaningful test or build command.
5. If implementation reveals missing requirements, update the feature package before expanding scope.

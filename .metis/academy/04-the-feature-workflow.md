---
topics: athena-practice
---

# 4 · The feature workflow

The mechanics of taking a change from an idea to merged code. Athena uses
[Specify](https://github.com/specify-project/specify) for this.

## The steps

1. **Create the package**

   ```bash
   .specify/scripts/bash/create-new-feature.sh "your-feature-name"
   ```

   This creates `.specify/specs/<feature-slug>/` with the required artifacts.

2. **Fill out the spec** in `.specify/specs/<slug>/spec.md`.

3. **Generate the supporting artifacts**, through the Copilot prompt surface:

   - `@workspace /speckit.research` — current-state evidence
   - `@workspace /speckit.data-model` — entity and DTO impact
   - `@workspace /speckit.plan` — the phased implementation plan

   Then review the generated `plan.md`, `research.md` and `data-model.md`.

4. **Implement following the plan phases.**

5. **Update agent context**

   ```bash
   .specify/scripts/bash/update-agent-context.sh copilot
   ```

## Where the authority lives

The prompts generate; the spec package decides. Principle 6 says agent output
must stay aligned to the package rather than scatter notes elsewhere, so a
research or plan file that disagrees with `spec.md` is a defect in the generated
artifact, not a revision of the spec.

## Where to read further

- `.specify/constitution.md` — the principles and the two definitions
- `.specify/WORKFLOW.md` — the step-by-step guide
- `.github/agents/copilot-instructions.md` — AI-assisted development context
- `.github/prompts/` — the slash commands themselves

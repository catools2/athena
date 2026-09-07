---
topics: athena-practice
---

# 3 · Spec before code

Athena develops specification-first, and the constitution states why: to reduce
**contract drift across microservices**, and to make human and agent execution
predictable. Every production change should trace to a feature package under
`.specify/specs`.

## The six principles

1. **Spec Before Code** — every user-facing or integration-facing change starts
   with a `spec.md`. Code may not introduce behaviour absent from the spec.
2. **Small Vertical Features** — thin, testable slices; no service-wide mega
   specs unless the work is purely operational.
3. **Contract Integrity** — API, DTO, Feign and persistence changes must be
   called out explicitly, and backward-compatibility impact documented *before*
   implementation.
4. **Cross-Module Traceability** — a plan must identify every impacted module,
   especially `athena-model`, `athena-boot-*`, `athena-boot-*-feign` and
   `athena-gateway`.
5. **Validation Is Part of the Feature** — unit, integration and manual
   verification expectations are defined by the feature, and the work is not
   done until they are satisfied or explicitly deferred.
6. **Agent Execution Must Be Reviewable** — agents may research, plan and
   implement, but outputs stay aligned to the spec package rather than
   scattering ad hoc notes.

## The four required artifacts

Every feature package carries all four:

- `spec.md` — business intent, scope, acceptance criteria, contract impact
- `plan.md` — phased implementation sequence and module touchpoints
- `research.md` — current-state evidence and design decisions
- `data-model.md` — entity, DTO, migration and sync considerations

## Ready, and done

**Definition of Ready**: the package exists under `.specify/specs/<slug>`,
acceptance criteria are concrete and testable, module ownership is identified,
and known risks and open questions are recorded.

**Definition of Done**: implementation matches the accepted spec, contract
changes are documented, required tests pass, and follow-up work is either
completed or captured as a new feature package.

Both are stated as checklists rather than as judgement, which is what makes them
answerable by someone who did not write the feature.

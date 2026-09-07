---
system: athena
---
# The Athena academy

**Authored, not generated.** These lessons are written about Athena; nothing
here is extracted from the code. They are landed into Métis as `Lesson` nodes so
that `ask` and `search_knowledge` answer questions about Athena the way they
answer questions about any other documented system.

Source material, all of it in this repository:

- `README.md` — purpose, the five ingestion sources, the build, the test sequence
- `.specify/constitution.md` — the six principles, required artifacts, DoR/DoD
- `.specify/WORKFLOW.md` — the feature workflow
- `.specify/specs/*` — the nine feature packages

**They land at Quarantine like every other source (S-4).** A lesson is authored
text, not agreed fact.

The root of this topic tree is `topic:athena`, declared above. Sub-topics are
prefixed `athena-` on purpose: a topic slug is global, so a bare `practice`
would merge into another corpus's topic of the same name and cross-link two
academies that document different systems.

| # | Lesson | Topics |
|---|---|---|
| 01 | What Athena is | `athena-overview` |
| 02 | The module map | `athena-architecture` |
| 03 | Spec before code | `athena-practice` |
| 04 | The feature workflow | `athena-practice` |
| 05 | The feature packages | `athena-features` |
| 06 | Building and testing | `athena-practice` |

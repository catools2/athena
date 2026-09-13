# Grafana import tools

**Developer tools, not a build step.** Nothing in the Maven build runs these; they are how the
contents of `src/main/resources/analytics/` got there, and how to refresh it after a dashboard
changes in Grafana.

They import **queries only**. Layout is not imported at all any more: the console has
hand-written report pages, so there is no dashboard specification to generate and nothing here
writes outside this module.

They live at the module root rather than under `src/` because they are not Java and are not
packaged. No peer module has an equivalent, because no peer serves SQL that was authored
somewhere else.

| Script | What it does |
|---|---|
| `grafana_format.py` | Normalises both Grafana export formats to one shape. Eight of the thirteen dashboards are the v2beta1 resource format; the other five are classic. |
| `extract_grafana.py` | Reads `grafana/**/*.json` and writes the parameterised query registry. |
| `build_curated.py` | The hand-authored queries, kept in a separate manifest so regeneration cannot delete them. |
| `selftest.py` | The invariants. Run it after any regeneration. |

## Refreshing after a Grafana change

```bash
python3 tools/extract_grafana.py              # dry run: reports what would change
python3 tools/extract_grafana.py --write
python3 tools/selftest.py                     # must print "all checks passed"
```

`extract_grafana.py` rewrites `queries/` wholesale, so review the diff.

## Why the normaliser matters

Panel SQL lives under `spec.elements` keyed by name in the v2beta1 format, and variables moved
from `templating.list[]` to `spec.variables[]`. A sweep written for the classic format finds a
fraction of what is there and **reports success** — a registry built without `grafana_format.py`
silently omits the dropdowns half the panels are filtered by, and there is no error to notice.

That is the failure this module guards against, and why `selftest.py` asserts both formats parse.

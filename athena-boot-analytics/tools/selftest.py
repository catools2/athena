#!/usr/bin/env python3
"""Invariants for the generated analytics registry.

Plain asserts and no test framework, because this lives in a Maven repo that has no Python
test wiring and one more toolchain is not worth it. Run it after regenerating:

    python3 athena-boot-analytics/tools/selftest.py

Every check here corresponds to a way the import can go wrong while still looking fine: a
format the reader does not understand yields a registry missing half its queries, and an
untranslated variable yields a query that fails only when someone selects a filter.

Panel and layout checks used to live here too. They are gone with the dashboard specs: the
console has hand-written report pages now rather than a renderer driven by imported JSON, so
"is every panel bound to a query the registry has?" is a test in that repository, against its
own source, where it belongs.
"""
import hashlib
import json
import pathlib
import re
import sys

sys.path.insert(0, str(pathlib.Path(__file__).resolve().parent))
import grafana_format as gf
import extract_grafana as ex

HERE = pathlib.Path(__file__).resolve().parent
OUT = ex.OUT

failures = []


def check(condition, message):
    if condition:
        print(f"  ok   {message}")
    else:
        print(f"  FAIL {message}")
        failures.append(message)


def main():
    print("grafana source")
    dashboards = sorted(ex.GRAFANA.rglob("*.json"))
    check(len(dashboards) > 0, f"found {len(dashboards)} dashboard files")

    formats = {"v2beta1": 0, "classic": 0}
    panels = variables = 0
    for path in dashboards:
        doc = gf.load(path)
        formats["v2beta1" if doc.get("__source_format") == "v2beta1" else "classic"] += 1
        found = doc.get("panels", [])
        variables += len(doc.get("templating", {}).get("list", []))
        panels += len(found)
        # The failure this guards: v2beta1 panels live under spec.elements, so a reader that
        # does not understand the format returns an empty list and looks successful.
        check(len(found) > 0, f"{path.stem}: {len(found)} panels")

    check(formats["v2beta1"] > 0, f"both formats parsed: {formats}")
    print(f"  ..   {panels} panels, {variables} variables total")

    print("\nquery registry")
    manifest = json.loads((OUT / "queries.json").read_text())
    sql_files = {p.stem for p in OUT.glob("*.sql")}
    ids = {q["id"] for q in manifest}

    check(len(manifest) > 0, f"{len(manifest)} queries registered")
    check(not (ids - sql_files), f"every manifest entry has SQL ({len(ids - sql_files)} missing)")
    check(not (sql_files - ids), f"no orphan SQL files ({len(sql_files - ids)} orphans)")

    bodies = {}
    for path in OUT.glob("*.sql"):
        bodies.setdefault(hashlib.sha256(path.read_bytes()).hexdigest(), []).append(path.stem)
    duplicate_bodies = [names for names in bodies.values() if len(names) > 1]
    check(not duplicate_bodies, f"no duplicate SQL bodies ({duplicate_bodies[:3]})")

    # No Grafana template may survive into stored SQL. One that does is either a query that
    # fails at runtime or, worse, a place where caller text reaches SQL unescaped.
    leftovers = []
    for path in OUT.glob("*.sql"):
        body = path.read_text()
        found = re.findall(r"\$\{?\w+", body)
        if found:
            leftovers.append((path.stem, sorted(set(found))))
    check(not leftovers, f"no untranslated Grafana variables in stored SQL ({leftovers[:3]})")

    # A bare `?` is read by the JDBC driver as a parameter marker, so a jsonb `?` operator
    # that survives translation fails at runtime with a parameter-count error - and does so
    # silently at import time, because there is no leftover $variable to notice.
    question = []
    for path in OUT.glob("*.sql"):
        body = re.sub(r"\{\{\w+\}\}", "", path.read_text())
        if "?" in body:
            question.append(path.stem)
    check(not question, f"no bare '?' survives into stored SQL ({question[:3]})")

    # {{slot}} is the one place text is substituted into SQL, so every slot must be backed by
    # an operator parameter with a closed whitelist.
    operator_params = {
        q["id"]: {p["name"] for p in q["params"] if p["kind"] == "operator"} for q in manifest
    }
    unbacked = []
    for path in OUT.glob("*.sql"):
        slots = set(re.findall(r"\{\{(\w+)\}\}", path.read_text()))
        missing = slots - operator_params.get(path.stem, set())
        if missing:
            unbacked.append((path.stem, sorted(missing)))
    check(not unbacked, f"every {{{{slot}}}} has an operator parameter ({unbacked[:3]})")

    for q in manifest:
        for p in q["params"]:
            if p["kind"] == "operator" and not p.get("allowed"):
                failures.append(f"{q['id']}: operator {p['name']} has no whitelist")
    check(
        all(p.get("allowed") for q in manifest for p in q["params"] if p["kind"] == "operator"),
        "every operator parameter enumerates its allowed values",
    )

    print()
    if failures:
        print(f"FAILED: {len(failures)} check(s)")
        return 1
    print("all checks passed")
    return 0


if __name__ == "__main__":
    sys.exit(main())

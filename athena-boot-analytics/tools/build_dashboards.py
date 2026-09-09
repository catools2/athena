#!/usr/bin/env python3
"""Turn Grafana dashboards into Athena dashboard specs.

Companion to extract_grafana.py, which produces the query registry. This produces the
layout: which panels a dashboard has, how they are arranged, which registered query each
one runs, and which variables feed it.

The point of doing it this way is that nobody hand-writes 28 dashboards in React. The
Grafana JSON already describes them; a spec plus one renderer is the whole implementation.

Panels backed by a datasource Athena cannot serve (InfluxDB, Prometheus) are emitted with
`"unsupported"` and the reason, rather than dropped. A panel that silently disappears is
much harder to notice than one that says why it is empty.

Usage:  python3 athena-boot-analytics/tools/build_dashboards.py [--write]
"""
import hashlib, json, pathlib, re, sys, collections

sys.path.insert(0, str(pathlib.Path(__file__).resolve().parent))
import extract_grafana as ex

OUT = pathlib.Path(__file__).resolve().parent.parent / "src/main/resources/analytics"

# Grafana panel type -> the viz the renderer implements.
VIZ = {
    "timeseries": "timeseries", "graph": "timeseries",
    "barchart": "bar", "bargauge": "barGauge",
    "piechart": "pie", "table": "table",
    "stat": "stat", "gauge": "gauge",
    "text": "text", "row": "row",
    "heatmap": "heatmap", "state-timeline": "stateTimeline",
}

ATHENA_DS = "${athena_datasource}"
SQL_START = re.compile(r"\s*(select|with)\b", re.I)


def qid(sql):
    return "q" + hashlib.sha1(sql.strip().encode()).hexdigest()[:10]


def datasource_uid(node, inherited):
    ds = node.get("datasource")
    if isinstance(ds, dict) and ds.get("uid"):
        return ds["uid"]
    if isinstance(ds, str):
        return ds
    return inherited


def variable_spec(v, registry_ids):
    """A dashboard variable, and where its options come from."""
    name = v.get("name")
    vtype = v.get("type")
    spec = {
        "name": name,
        "type": vtype,
        "label": v.get("label") or name,
        "multi": bool(v.get("multi")),
        "includeAll": bool(v.get("includeAll")),
    }
    if vtype == "custom":
        spec["options"] = [o.get("value") for o in v.get("options", []) if o.get("value") is not None]
        if not spec["options"] and isinstance(v.get("query"), str):
            spec["options"] = [o.strip() for o in v["query"].split(",") if o.strip()]
    elif vtype == "constant":
        spec["value"] = v.get("query")
    elif vtype == "query":
        raw = v.get("query")
        raw = raw if isinstance(raw, str) else (raw or {}).get("query", "")
        if SQL_START.match(raw or ""):
            spec["queryId"] = qid(raw)
            if spec["queryId"] not in registry_ids:
                spec["unsupported"] = "variable query is not in the registry"
        else:
            # Flux and other non-SQL variables have no Athena equivalent.
            spec["unsupported"] = "variable is backed by a datasource Athena does not serve"
    return spec


def main():
    registry = json.loads((OUT / "queries.json").read_text())
    registry_ids = {q["id"] for q in registry}
    params_of = {q["id"]: [p["name"] for p in q["params"]] for q in registry}

    specs, stats = [], collections.Counter()

    for path in sorted(ex.GRAFANA.rglob("*.json")):
        dash = json.loads(path.read_text())
        variant = path.parent.name
        dash_ds = None

        variables = [variable_spec(v, registry_ids)
                     for v in dash.get("templating", {}).get("list", [])
                     if v.get("type") != "datasource"]

        panels = []

        def walk(node, inherited_ds):
            if isinstance(node, dict):
                if "gridPos" in node and "type" in node:
                    panels.append(build_panel(node, inherited_ds))
                    # Rows carry nested panels when collapsed.
                    for child in node.get("panels", []) or []:
                        walk(child, inherited_ds)
                    return
                for v in node.values():
                    walk(v, inherited_ds)
            elif isinstance(node, list):
                for v in node:
                    walk(v, inherited_ds)

        def build_panel(node, inherited_ds):
            grid = node.get("gridPos", {})
            panel = {
                "id": node.get("id"),
                "title": node.get("title", ""),
                "viz": VIZ.get(node.get("type"), node.get("type")),
                "grid": {"x": grid.get("x", 0), "y": grid.get("y", 0),
                         "w": grid.get("w", 12), "h": grid.get("h", 8)},
            }
            if node.get("description"):
                panel["description"] = node["description"]

            if panel["viz"] == "text":
                opts = node.get("options", {})
                panel["content"] = opts.get("content", "")
                panel["mode"] = opts.get("mode", "markdown")
                stats["text"] += 1
                return panel
            if panel["viz"] == "row":
                panel["collapsed"] = bool(node.get("collapsed"))
                stats["row"] += 1
                return panel

            targets = [t for t in (node.get("targets") or []) if not t.get("hide")]
            sql_targets = [t for t in targets if isinstance(t.get("rawSql"), str) and t["rawSql"].strip()]
            ds = datasource_uid(targets[0] if targets else node, inherited_ds)

            if not sql_targets:
                panel["unsupported"] = (
                    f"panel reads {ds or 'an unknown datasource'}, which Athena does not serve")
                stats["unsupported"] += 1
                return panel

            ids = [qid(t["rawSql"]) for t in sql_targets]
            missing = [i for i in ids if i not in registry_ids]
            if missing:
                panel["unsupported"] = "query is not in the registry (unknown view)"
                stats["unregistered"] += 1
                return panel

            panel["queries"] = [{"queryId": i, "params": params_of[i]} for i in ids]
            fc = (node.get("fieldConfig") or {}).get("defaults") or {}
            display = {k: fc[k] for k in ("unit", "decimals", "min", "max") if k in fc}
            if fc.get("thresholds"):
                display["thresholds"] = fc["thresholds"]
            if display:
                panel["display"] = display
            if node.get("options"):
                panel["options"] = node["options"]
            stats["rendered"] += 1
            return panel

        walk(dash.get("panels", []), dash_ds)

        specs.append({
            "id": f"{variant}-{path.stem}".lower(),
            "title": dash.get("title") or path.stem,
            "variant": variant,
            "source": f"{variant}/{path.stem}",
            "variables": variables,
            "panels": panels,
        })

    total_panels = sum(len(s["panels"]) for s in specs)
    print(f"dashboards        : {len(specs)}")
    print(f"panels            : {total_panels}")
    for k, v in sorted(stats.items()):
        print(f"  {k:14s}: {v}")

    unsupported_vars = sum(1 for s in specs for v in s["variables"] if v.get("unsupported"))
    print(f"variables         : {sum(len(s['variables']) for s in specs)} "
          f"({unsupported_vars} unsupported)")

    if "--write" in sys.argv:
        ddir = OUT / "dashboards"
        ddir.mkdir(parents=True, exist_ok=True)
        for stale in ddir.glob("*.json"):
            stale.unlink()
        for s in specs:
            (ddir / f"{s['id']}.json").write_text(json.dumps(s, indent=2) + "\n")
        (OUT / "dashboards.json").write_text(json.dumps(
            [{"id": s["id"], "title": s["title"], "variant": s["variant"],
              "panelCount": len(s["panels"]),
              "renderable": sum(1 for p in s["panels"] if not p.get("unsupported"))}
             for s in specs], indent=2) + "\n")
        print(f"\nwrote {len(specs)} dashboard specs to {ddir}")


if __name__ == "__main__":
    main()

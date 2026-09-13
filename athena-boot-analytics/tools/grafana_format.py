#!/usr/bin/env python3
"""Read either Grafana dashboard format, hand back one shape.

Grafana 12 exports dashboards as a Kubernetes-style resource
(`apiVersion: dashboard.grafana.app/v2beta1`) whose panels live under `spec.elements`
keyed by name, with position held separately in `spec.layout`. The classic export
(`schemaVersion: 38`) puts panels in a flat `panels` list, each carrying its own
`gridPos`. Eight of the thirteen dashboards here are v2beta1 and five are classic.

Nothing downstream should have to care. The importers ask for panels and variables,
and this module answers in the classic shape regardless of what was on disk.

**Why this matters more than it looks.** `build_dashboards.py` keys on `gridPos` +
`type`, neither of which exists in v2beta1 - so without this it finds *zero* panels in
8 of 13 dashboards and reports success. `extract_grafana.py` stumbles onto panel SQL
because it recurses looking for `rawSql`, but misses every variable query, because
those moved from `templating.list[]` to `spec.variables[]`. The result would be a
registry that looks complete and silently omits the dropdowns half the panels filter by.
"""
import pathlib
import json

V2_API_PREFIX = "dashboard.grafana.app/"


def is_v2(doc):
    return isinstance(doc, dict) and str(doc.get("apiVersion", "")).startswith(V2_API_PREFIX)


def load(path):
    """Load a dashboard from disk in whatever format it is stored in."""
    doc = json.loads(pathlib.Path(path).read_text())
    return normalise(doc)


def normalise(doc):
    """v2beta1 -> classic. A classic document is returned untouched."""
    if not is_v2(doc):
        # Some exports wrap the dashboard in {"dashboard": {...}}.
        return doc.get("dashboard", doc) if isinstance(doc, dict) else doc

    spec = doc.get("spec", {}) or {}
    return {
        "title": spec.get("title"),
        "description": spec.get("description"),
        "tags": spec.get("tags", []),
        "panels": _panels(spec),
        "templating": {"list": [_variable(v) for v in spec.get("variables", []) or []]},
        # Kept so a reader can tell what produced this without going back to disk.
        "__source_format": "v2beta1",
    }


def _panels(spec):
    """Rejoin `elements` (what a panel is) with `layout` (where it sits)."""
    elements = spec.get("elements", {}) or {}
    panels = []

    for item in _layout_items(spec):
        item_spec = item.get("spec", {}) or {}
        ref = (item_spec.get("element") or {}).get("name")
        element = elements.get(ref)
        if not element:
            continue
        panel = _panel(element)
        if panel is None:
            continue
        panel["gridPos"] = {
            "x": item_spec.get("x", 0),
            "y": item_spec.get("y", 0),
            "w": item_spec.get("width", 12),
            "h": item_spec.get("height", 8),
        }
        panels.append(panel)

    # An element with no layout entry still exists and still runs a query. Emitting it
    # with a default position beats dropping it, which would lose it from the registry
    # as well as from the page.
    placed = {(item.get("spec", {}) or {}).get("element", {}).get("name")
              for item in _layout_items(spec)}
    for name, element in elements.items():
        if name in placed:
            continue
        panel = _panel(element)
        if panel is not None:
            panel["gridPos"] = {"x": 0, "y": 0, "w": 12, "h": 8}
            panels.append(panel)

    return panels


def _layout_items(spec):
    """Flatten the layout, including rows and tabs, which nest their own items."""
    layout = spec.get("layout", {}) or {}
    items = []

    def walk(node):
        if isinstance(node, dict):
            if node.get("kind", "").endswith("LayoutItem") and "spec" in node:
                items.append(node)
                return
            for value in node.values():
                walk(value)
        elif isinstance(node, list):
            for value in node:
                walk(value)

    walk(layout)
    return items


def _panel(element):
    """One `elements[*]` entry as a classic panel."""
    spec = element.get("spec", {}) or {}
    viz = element.get("spec", {}).get("vizConfig", {}) or {}
    viz_spec = viz.get("spec", {}) or {}

    panel = {
        "id": spec.get("id"),
        "title": spec.get("title", ""),
        # vizConfig.group is where the panel type moved to.
        "type": viz.get("group") or element.get("kind", "").lower(),
        "targets": _targets(spec),
    }
    if spec.get("description"):
        panel["description"] = spec["description"]
    if viz_spec.get("options"):
        panel["options"] = viz_spec["options"]
    if viz_spec.get("fieldConfig"):
        panel["fieldConfig"] = viz_spec["fieldConfig"]
    return panel


def _targets(panel_spec):
    """`data.spec.queries[]` as classic `targets[]`, preserving datasource and hidden."""
    queries = ((panel_spec.get("data") or {}).get("spec") or {}).get("queries") or []
    targets = []
    for entry in queries:
        q_spec = entry.get("spec", {}) or {}
        query = q_spec.get("query", {}) or {}
        inner = query.get("spec", {}) or {}
        target = {
            "hide": bool(q_spec.get("hidden")),
            # `group` names the datasource plugin - this is what tells a Postgres panel
            # from an InfluxDB one, and so what decides "renderable" vs "unsupported".
            "datasource": {
                "type": query.get("group"),
                "uid": (query.get("datasource") or {}).get("name"),
            },
        }
        raw_sql = inner.get("rawSql")
        if isinstance(raw_sql, str) and raw_sql.strip():
            target["rawSql"] = raw_sql
        # InfluxDB keeps Flux under `query`; carried through so the panel can be
        # reported as unsupported-with-a-reason rather than as empty.
        if isinstance(inner.get("query"), str):
            target["query"] = inner["query"]
        targets.append(target)
    return targets


def _variable(var):
    """A `spec.variables[]` entry as a classic `templating.list[]` entry."""
    kind = var.get("kind", "")
    spec = var.get("spec", {}) or {}
    out = {
        "name": spec.get("name"),
        "label": spec.get("label") or spec.get("name"),
        "multi": bool(spec.get("multi")),
        "includeAll": bool(spec.get("includeAll")),
    }

    if kind == "QueryVariable":
        out["type"] = "query"
        # `definition` is the readable copy; `__legacyStringValue` is what the query
        # editor round-trips. Either may be the populated one, so prefer whichever has SQL.
        query = spec.get("query", {}) or {}
        inner = query.get("spec", {}) or {}
        out["query"] = (
            spec.get("definition")
            or inner.get("rawSql")
            or inner.get("__legacyStringValue")
            or ""
        )
        out["datasourceType"] = query.get("group")
    elif kind == "CustomVariable":
        out["type"] = "custom"
        out["options"] = [
            {"value": o.get("value"), "text": o.get("text")}
            for o in spec.get("options", []) or []
            if o.get("value") is not None
        ]
        if isinstance(spec.get("query"), str):
            out["query"] = spec["query"]
    elif kind == "ConstantVariable":
        out["type"] = "constant"
        out["query"] = spec.get("value") or spec.get("query")
    elif kind == "IntervalVariable":
        out["type"] = "interval"
        out["query"] = spec.get("query")
    elif kind == "DatasourceVariable":
        out["type"] = "datasource"
    else:
        out["type"] = kind or "unknown"
    return out

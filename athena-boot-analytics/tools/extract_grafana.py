#!/usr/bin/env python3
"""Convert Grafana dashboards into an Athena analytics query registry.

Import tool, not a build step: it reads ../../grafana/**/*.json and writes
../src/main/resources/analytics/{queries/*.sql,queries.json,dashboards/*.json}.
The generated files are the source of truth once reviewed; re-running overwrites
them, so diff before keeping.

Grafana templating is not SQL. Three constructs need structural handling rather
than a bind parameter, and getting them wrong is either a broken query or an
injection:

  $cycle_type   expands to an OPERATOR (LIKE / NOT LIKE). It cannot be bound, so
                it is emitted as a {{cycle_type}} slot filled at render time from
                a closed whitelist.
  jsonb ? / ?|  the `?` operator collides with the JDBC parameter marker. Rewritten
                to jsonb_exists(col, :p) / jsonb_exists_any(col, :p), which are the
                same operators by another name.
  ARRAY[$x] = ARRAY['__all__']
                Grafana's "All" sentinel. Becomes cardinality(:x) = 0, so an empty
                list means "no filter" instead of "matches nothing".
"""
import json, pathlib, re, sys, hashlib, collections

HERE = pathlib.Path(__file__).resolve().parent
ROOT = HERE.parent.parent
GRAFANA = ROOT / "grafana"
OUT = HERE.parent / "src/main/resources/analytics"

# Variables that are values. Everything else must be declared here or the query is rejected.
SCALAR = {"version", "versions", "environment", "username", "name", "components", "team"}
LIST   = {"team", "teams", "versions", "reporters", "rcas", "affected_versions", "username",
          "name", "components", "cycle_type"}
# Variables that are SQL fragments, with the only values they may take.
OPERATOR_ENUMS = {"cycle_type": ["LIKE", "NOT LIKE"]}

class Unsupported(Exception):
    pass


def translate(sql):
    """Grafana SQL -> (named-parameter SQL, {param: kind})."""
    params = {}
    s = sql

    # -- time macros -------------------------------------------------------
    s = re.sub(r"\$__timeFilter\(\s*([\w.\"]+)\s*\)",
               lambda m: f"{m.group(1)} BETWEEN :timeFrom AND :timeTo", s)
    s = re.sub(r"\$__timeFrom\(\s*\)", ":timeFrom", s)
    s = re.sub(r"\$__timeTo\(\s*\)", ":timeTo", s)
    s = re.sub(r"\$__timeGroupAlias\(\s*([\w.\"]+)\s*,\s*([^)]+)\)",
               lambda m: f"date_trunc('hour', {m.group(1)}) AS time", s)
    if ":timeFrom" in s or ":timeTo" in s:
        params["timeFrom"] = "instant"; params["timeTo"] = "instant"

    # -- the "All" sentinel, before anything else touches ARRAY[...] --------
    def all_sentinel(m):
        var = m.group(1)
        params[var] = "list"
        return f"cardinality(:{var}) = 0"
    s = re.sub(r"ARRAY\[\s*\$\{?(\w+)\}?\s*\]\s*=\s*ARRAY\['__all__'\]", all_sentinel, s)

    # -- jsonb existence: rewrite away from the `?` operator ---------------
    def exists_any(m):
        col, var = m.group(1), m.group(2)
        params[var] = "list"
        return f"jsonb_exists_any({col}, :{var})"
    s = re.sub(r"([\w.\"]+(?:::jsonb)?)\s*\?\|\s*ARRAY\[\s*\$\{(\w+)(?::\w+)?\}\s*\]", exists_any, s)

    # `col ? '$var'` - quoted, so a single key.
    def exists_one(m):
        col, var = m.group(1), m.group(2)
        params[var] = "scalar"
        return f"jsonb_exists({col}, :{var})"
    s = re.sub(r"([\w.\"]+(?:::jsonb)?)\s*\?\s*'\$\{?(\w+)\}?'", exists_one, s)

    # `col ? ($var)` - unparenthesised value list. Every dashboard using this form
    # declares the variable multi=true, so Grafana renders `? ('a','b')`, which is a
    # record and fails with "operator does not exist: jsonb ? record". The panels only
    # work today by accident, with exactly one value selected. "any of these keys" is
    # what was meant, and it is the same thing the ?| form expresses elsewhere.
    def exists_any_paren(m):
        col, var = m.group(1), m.group(2)
        params[var] = "list"
        return f"jsonb_exists_any({col}, :{var})"
    s = re.sub(r"([\w.\"]+(?:::jsonb)?)\s*\?\s*\(\s*\$\{?(\w+)\}?\s*\)", exists_any_paren, s)

    # `col ? 'literal'` / `col ?| ARRAY['a','b']` - no variable involved, but the bare `?`
    # is still consumed by the JDBC driver as a parameter marker and the statement fails with
    # a parameter-count error. Same rewrite, no parameter created.
    s = re.sub(r"([\w.\"]+(?:::jsonb)?)\s*\?\|\s*(ARRAY\[[^\]]*\])",
               lambda m: f"jsonb_exists_any({m.group(1)}, {m.group(2)})", s)
    s = re.sub(r"([\w.\"]+(?:::jsonb)?)\s*\?\s*('(?:[^']|'')*')",
               lambda m: f"jsonb_exists({m.group(1)}, {m.group(2)})", s)

    # -- operator-valued variables ----------------------------------------
    for var in OPERATOR_ENUMS:
        if re.search(rf"\$\{{?{var}\}}?", s):
            s = re.sub(rf"\$\{{?{var}\}}?", "{{" + var + "}}", s)
            params[var] = "operator"

    # -- IN (...) lists ----------------------------------------------------
    def in_list(m):
        col, var = m.group(1), m.group(2)
        params[var] = "list"
        return f"{col} = ANY(:{var})"
    s = re.sub(r"([\w.\"]+)\s+(?:IN|in)\s*\(\s*\$\{?(\w+)(?::\w+)?\}?\s*\)", in_list, s)

    # -- quoted scalars ----------------------------------------------------
    def scalar(m):
        var = m.group(1)
        params.setdefault(var, "scalar")
        return f":{var}"
    s = re.sub(r"'\$\{?(\w+)\}?'", scalar, s)

    # -- anything left is untranslated ------------------------------------
    leftover = re.findall(r"\$\{?(\w+)", s)
    if leftover:
        raise Unsupported(f"untranslated variables: {sorted(set(leftover))}")
    return s, params


SQL_START = re.compile(r"\s*(select|with)\b", re.I)
VIEW_RE = re.compile(r"\bathena\.\s*([a-z0-9_]+)")
# Base tables are legitimate sources too; recording them keeps an empty view list
# explainable rather than looking like a resolution failure.
TABLE_RE = re.compile(r"\b(athena_\w+\.\s*[a-z0-9_]+)")


def main():
    if not GRAFANA.exists():
        sys.exit(f"no grafana directory at {GRAFANA}")

    seen, usage, titles = {}, collections.defaultdict(list), {}
    for path in sorted(GRAFANA.rglob("*.json")):
        dash = json.loads(path.read_text())
        dash_title = dash.get("title") or path.stem

        def record(body, title):
            body = body.strip()
            qid = "q" + hashlib.sha1(body.encode()).hexdigest()[:10]
            seen.setdefault(qid, body)
            titles.setdefault(qid, title)
            usage[qid].append(f"{path.parent.name}/{path.stem}")

        def walk(node, panel_title=None):
            if isinstance(node, dict):
                pt = node.get("title", panel_title)
                raw = node.get("rawSql")
                if isinstance(raw, str) and raw.strip():
                    record(raw, pt or dash_title)
                for v in node.values():
                    walk(v, pt)
            elif isinstance(node, list):
                for v in node:
                    walk(v, panel_title)
        walk(dash)

        # Dashboard variables hold their SQL in `query`/`definition`, not `rawSql`, so a
        # panel-only sweep misses them - and without them the UI has no way to populate the
        # very dropdowns those panels are filtered by. Flux variables are skipped: they belong
        # to a datasource Athena does not serve.
        for var in dash.get("templating", {}).get("list", []):
            if var.get("type") != "query":
                continue
            raw = var.get("query")
            raw = raw if isinstance(raw, str) else (raw or {}).get("query", "")
            if raw and SQL_START.match(raw):
                record(raw, f"${var.get('name')} options")

    known_views = {f.name.split("__", 1)[1][:-4]
                   for f in (ROOT / "orchestration/athena_db/views").glob("*.sql") if "__" in f.name}

    manifest, failures, dangling = [], [], []
    for qid, body in sorted(seen.items()):
        try:
            sql, params = translate(body)
        except Unsupported as e:
            failures.append((qid, str(e))); continue

        views = sorted(set(VIEW_RE.findall(sql)))
        tables = sorted({re.sub(r"\s+", "", t) for t in TABLE_RE.findall(sql)})
        missing = [v for v in views if v not in known_views]
        if missing:
            dangling.append((qid, missing, sorted(set(usage[qid]))))
            continue

        manifest.append({
            "id": qid,
            "title": titles[qid],
            "views": views,
            "tables": tables,
            "params": [
                {"name": n, "kind": k,
                 **({"allowed": OPERATOR_ENUMS[n]} if k == "operator" else {})}
                for n, k in sorted(params.items())
            ],
            "dashboards": sorted(set(usage[qid])),
            "sql": sql,
        })

    print(f"distinct queries : {len(seen)}")
    print(f"registered       : {len(manifest)}")
    print(f"untranslatable   : {len(failures)}")
    print(f"dangling view    : {len(dangling)}")
    for qid, missing, where in dangling:
        print(f"  {qid}  missing {missing}  used by {where}")
    for qid, why in failures:
        print(f"  {qid}  {why}")

    if "--write" in sys.argv:
        qdir = OUT / "queries"
        qdir.mkdir(parents=True, exist_ok=True)
        for stale in qdir.glob("*.sql"):
            stale.unlink()
        for m in manifest:
            (qdir / f"{m['id']}.sql").write_text(m["sql"].rstrip() + "\n")
        OUT.mkdir(parents=True, exist_ok=True)
        (OUT / "queries.json").write_text(json.dumps(
            [{k: v for k, v in m.items() if k != "sql"} for m in manifest],
            indent=2) + "\n")
        print(f"\nwrote {len(manifest)} queries + manifest to {OUT}")


if __name__ == "__main__":
    main()

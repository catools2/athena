#!/usr/bin/env python3
"""Split a pgAdmin schema dump into dependency-ordered view artifacts.

This is an IMPORT tool, not a build step. views/*.sql is the source of truth and
is edited by hand; nothing regenerates it on a normal change. Reach for this only
when re-importing a whole schema from a live database, in which case dump the
`athena` schema to views.sql next to this script and run it - it will overwrite
views/ entirely, so review the diff before keeping it.


views.sql is a pgAdmin-style dump of the live `athena` schema (each object
preceded by a `-- athena.<name> source` comment). This script:

  1. parses every CREATE [OR REPLACE] VIEW / CREATE MATERIALIZED VIEW,
  2. derives the dependency graph from athena.* references between them,
  3. topologically sorts it,
  4. reformats each body with sqlparse, asserting the token stream is unchanged
     so the reformat is provably lossless,
  5. emits views/NN__<name>.sql - one file per object, numbered in dependency
     order, each DROPping its own object with CASCADE before recreating it.

Applying views/*.sql in filename order rebuilds the whole graph, so a Flyway
repeatable migration is just:  cat views/*.sql > R__athena_views.sql

Usage:  python3 orchestration/athena_db/generate_views.py
Requires: pip install sqlparse
"""
import json
import os
import re
import sys

try:
    import sqlparse
    import sqlparse.engine.grouping as _grouping
except ImportError:
    sys.exit("sqlparse is required: pip install sqlparse")

# The largest view (mvw_inventory_trend, ~1000 lines) exceeds the default limit.
_grouping.MAX_GROUPING_TOKENS = 500_000

HERE = os.path.dirname(os.path.abspath(__file__))
SOURCE = os.path.join(HERE, "views.sql")
VIEWS_DIR = os.path.join(HERE, "views")

# Carried over from the retired add_views.sql, which was the only place the
# athena schema itself was created.
SCHEMA_BOOTSTRAP = "CREATE SCHEMA IF NOT EXISTS athena AUTHORIZATION postgres;"

CREATE_RE = re.compile(r"^CREATE (OR REPLACE VIEW|MATERIALIZED VIEW) athena\.")


def tokens(sql):
    """Token stream ignoring whitespace, with string literals kept verbatim.

    Used to prove a reformat changed only layout: any pure-whitespace edit
    produces an identical stream, while a real edit does not.
    """
    out, i, n = [], 0, len(sql)
    while i < n:
        c = sql[i]
        if c == "'":
            j, lit = i + 1, "'"
            while j < n:
                if sql[j] == "'":
                    if j + 1 < n and sql[j + 1] == "'":
                        lit += "''"
                        j += 2
                        continue
                    lit += "'"
                    j += 1
                    break
                lit += sql[j]
                j += 1
            out.append(lit)
            i = j
        elif c.isspace():
            i += 1
        elif c.isalnum() or c == "_":
            j = i
            while j < n and (sql[j].isalnum() or sql[j] == "_"):
                j += 1
            out.append(sql[i:j])
            i = j
        else:
            out.append(c)
            i += 1
    return out


def parse_objects(text):
    lines = text.split("\n")
    starts = [(i, l) for i, l in enumerate(lines) if CREATE_RE.match(l)]
    objs = []
    for idx, (i, line) in enumerate(starts):
        end = starts[idx + 1][0] if idx + 1 < len(starts) else len(lines)
        # Walk back past the next object's "-- athena.<x> source" banner so it is
        # not mistaken for a dependency of this one.
        while end > i and (lines[end - 1].strip() == "" or lines[end - 1].lstrip().startswith("--")):
            end -= 1
        objs.append({
            "name": re.search(r"athena\.([a-z_]+)", line).group(1),
            "kind": "MV" if "MATERIALIZED" in line else "VIEW",
            "body": "\n".join(lines[i:end]),
        })
    names = {o["name"] for o in objs}
    for o in objs:
        stripped = "\n".join(l.split("--")[0] for l in o["body"].split("\n"))
        o["deps"] = sorted((set(re.findall(r"athena\.([a-z_]+)", stripped)) & names) - {o["name"]})
    return objs


def toposort(objs):
    deps = {o["name"]: set(o["deps"]) for o in objs}
    order, done = [], set()
    while len(order) < len(objs):
        ready = sorted(n for n, d in deps.items() if n not in done and d <= done)
        if not ready:
            raise SystemExit(f"dependency cycle among: {sorted(set(deps) - done)}")
        order.extend(ready)
        done.update(ready)
    level = {}
    for n in order:
        level[n] = max([level[d] for d in deps[n]] or [-1]) + 1
    return order, level


def wrap_long_lines(sql, width=120):
    """Split over-long lines at commas sitting at the line's shallowest paren depth.

    Whitespace-only: string literals are never entered, tokens never reordered.
    """
    out = []
    for line in sql.split("\n"):
        if len(line) <= width:
            out.append(line)
            continue
        indent = len(line) - len(line.lstrip())
        body = line[indent:]
        depths, d, q, i = [], 0, False, 0
        while i < len(body):
            c = body[i]
            if q:
                if c == "'":
                    if i + 1 < len(body) and body[i + 1] == "'":
                        i += 2
                        continue
                    q = False
            else:
                if c == "'":
                    q = True
                elif c == "(":
                    d += 1
                elif c == ")":
                    d -= 1
                elif c == ",":
                    depths.append((i, d))
            i += 1
        if not depths:
            out.append(line)
            continue
        target = min(dd for _, dd in depths)
        cuts = [pos for pos, dd in depths if dd == target]
        parts, prev = [], 0
        for pos in cuts:
            parts.append(body[prev:pos + 1])
            prev = pos + 1
        parts.append(body[prev:])
        parts = [p.strip() for p in parts if p.strip()]
        if len(parts) < 2:
            out.append(line)
            continue
        out.append(" " * indent + parts[0])
        out.extend(" " * (indent + 2) + p for p in parts[1:])
    return "\n".join(out)


def _settle(sql):
    for _ in range(8):
        nxt = wrap_long_lines(sql)
        if nxt == sql:
            break
        sql = nxt
    return sql


def _max_width(sql):
    return max(len(l) for l in sql.split("\n"))


UNIQUE_INDEXES = {
    "mv_api_path_info_with_matching_pattern": (
        "mv_api_path_info_matching_id_uidx", "id"),
    "mv_pipeline_product_statistic": (
        "mv_pipeline_product_statistic_key_uidx",
        "project, environment, pipeline_name, pipeline_number, start_date"),
    "mv_pod_basic_info": (
        "mv_pod_basic_info_row_uidx",
        "pod_rank, app, version, namespace, created_at, last_sync, "
        "project_id, status_id, phase, name"),
    "mv_product_team_scale_link": (
        "mv_product_team_scale_link_key_uidx", "item_id, scale_key"),
    "mv_tested_api_path_info": (
        "mv_tested_api_path_info_key_uidx", "method, url, internal"),
}


def fmt(body):
    """Reformat losslessly, preferring whichever candidate is narrower.

    sqlparse's reindent is usually best, but on the very wide
    jsonb_build_object/FILTER views it produces runaway indentation (6000+
    column lines), so plain comma-wrapping of the original wins there.
    """
    candidates = [_settle("\n".join(l.rstrip() for l in body.split("\n")))]
    try:
        candidates.append(_settle(sqlparse.format(
            body, reindent=True, keyword_case=None, indent_width=2)))
    except Exception:
        pass
    for c in candidates:
        if tokens(c) != tokens(body):
            raise SystemExit("refusing to write: reformat altered the SQL")
    best = min(candidates, key=_max_width)
    return best.rstrip().rstrip(";") + ";"


def main():
    objs = parse_objects(open(SOURCE).read())
    order, level = toposort(objs)
    by = {o["name"]: o for o in objs}
    mvs = [n for n in order if by[n]["kind"] == "MV"]

    os.makedirs(VIEWS_DIR, exist_ok=True)
    for f in os.listdir(VIEWS_DIR):
        if f.endswith(".sql"):
            os.remove(os.path.join(VIEWS_DIR, f))

    with open(os.path.join(VIEWS_DIR, "00__schema.sql"), "w") as fh:
        fh.write("-- athena schema bootstrap\n"
                 "--\n"
                 "-- Generated from orchestration/athena_db/views.sql. Files are numbered in\n"
                 "-- dependency order; apply them in ascending numeric order.\n\n"
                 + SCHEMA_BOOTSTRAP + "\n")

    for i, name in enumerate(order, start=1):
        o = by[name]
        kind = "MATERIALIZED VIEW" if o["kind"] == "MV" else "VIEW"
        deps = ", ".join(o["deps"]) or "(base tables only)"
        header = (
            f"-- athena.{name}\n"
            f"-- type      : {kind}\n"
            f"-- dep level : {level[name]}\n"
            f"-- depends on: {deps}\n"
            f"--\n"
            f"-- Generated from orchestration/athena_db/views.sql. Files are numbered in\n"
            f"-- dependency order; apply them in ascending numeric order.\n\n"
        )
        drop = f"DROP {kind} IF EXISTS athena.{name} CASCADE;\n\n"
        index = ""
        if name in UNIQUE_INDEXES:
            index_name, columns = UNIQUE_INDEXES[name]
            index = (
                f"\nCREATE UNIQUE INDEX {index_name}\n"
                f"  ON athena.{name} ({columns});\n"
            )
        with open(os.path.join(VIEWS_DIR, f"{i:02d}__{name}.sql"), "w") as fh:
            fh.write(header + drop + fmt(o["body"]) + "\n" + index)


    print(f"{len(order)} objects ({len(order) - len(mvs)} views, {len(mvs)} materialized views)")
    print(f"  -> {VIEWS_DIR}/NN__<name>.sql")


if __name__ == "__main__":
    main()

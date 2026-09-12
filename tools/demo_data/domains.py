"""One loader per Athena domain, in dependency order.

The services resolve foreign keys by name or code through Feign calls to core and raise rather
than create, so ordering is not a preference: a commit whose author is not already a core user is
rejected, and so is a pipeline whose version does not exist. Reference entities are therefore
loaded serially and only the leaves are sent concurrently.
"""

from __future__ import annotations

import logging
import random
import threading
import time
from datetime import timedelta

import conventions as C
from client import AthenaClient, Stats, iso
from narrative import (
    ACTIONS, ENVIRONMENTS, PROJECTS, REPOSITORIES, TEAMS, TEST_NAMES, USERS,
    BLOCKED_CYCLE_EVERY, FLAKY_ITEM_INDEX, REGRESSED_ACTION, STALLED_CYCLE_EVERY, WEAK_TEAM_INDEX,
    World, duration_for, item_key, stable_hash,
)

log = logging.getLogger("demo_data.domains")

ITEMS_PER_PROJECT = 120


def _ensure(client: AthenaClient, path: str, payloads: list[dict], lookup: str,
            stats: Stats, label: str, attempts: int = 3) -> None:
    """POST reference values, then read them back and retry whatever did not land.

    Reference data is not like the leaves: a status or a user that fails to create does not cost
    one row, it costs every row that names it. The first run of this loader lost four item
    statuses to a cold connection pool during service start-up and the failure surfaced hundreds
    of requests later as "null value in column item_id", which is a long way from the cause.

    So the contract here is stronger than "we sent it": we send it, read it back, retry what is
    missing, and refuse to continue if it is still missing.
    """
    if client.dry_run:
        return
    outstanding = list(payloads)
    for attempt in range(attempts):
        for payload in outstanding:
            client.post_one(path, payload, stats)
        outstanding = [p for p in outstanding
                       if not client.get(path, keyword=p[lookup])]
        if not outstanding:
            return
        if attempt + 1 < attempts:
            log.warning("      %s: %d of %d not readable yet, retrying",
                        label, len(outstanding), len(payloads))
            time.sleep(2)

    raise SystemExit(
        f"{label}: {[p[lookup] for p in outstanding]} could not be created after {attempts} "
        f"attempts. Everything that references them by {lookup} would be rejected, so stopping "
        f"rather than loading a dataset with holes in it.")


def meta(name: str, value: str) -> dict:
    return {"name": name, "value": value}


# ---------------------------------------------------------------------------- core ------------

def load_core(client: AthenaClient, world: World) -> Stats:
    """Projects, users, versions and environments. Everything else refers to these by code."""
    stats = Stats()

    _ensure(client, "/core/project", [{"code": c, "name": n} for c, n in PROJECTS],
            "code", stats, "projects")

    _require_users(client, stats)

    for code, name in PROJECTS:
        for env_code, env_name in ENVIRONMENTS:
            client.post_one("/core/environment",
                            {"code": env_code, "name": env_name, "project": code}, stats)
        for version in world.versions:
            client.post_one("/core/version",
                            {"code": version.code, "name": version.name, "project": code}, stats)

    _require_users(client, stats)
    return stats


def _require_users(client: AthenaClient, stats: Stats) -> None:
    """Users are reference data too - git, pipeline and tms all resolve authors and executors by
    name and reject the whole parent record when one is absent."""
    payloads = []
    for username in USERS:
        initials = "".join(part[0] for part in username.split())
        payloads.append({
            "username": username,
            "aliases": [{"alias": username}, {"alias": f"{initials}{username.split()[-1]}"},
                        {"alias": username.replace(" ", ".")}],
        })
    _ensure(client, "/core/user", payloads, "username", stats, "users")


# ---------------------------------------------------------------------------- tms -------------

def load_tms_reference(client: AthenaClient, world: World) -> Stats:
    """Item types, statuses and priorities.

    All three DTOs carry both a code and a name, and an item resolves them by *code* - the error
    is "No status with code In Progress found", not "with name". The code is capped at 10
    characters while the name is what the analytics views compare against, so only the code is
    ever abbreviated.
    """
    stats = Stats()
    _ensure(client, "/tms/itemType",
            [{"code": C.code_for(n), "name": n} for n in C.ITEM_TYPES],
            "code", stats, "item types")
    # One table serves both item workflow states and execution outcomes; "In Progress" is in both
    # lists, so dedupe while preserving order.
    _ensure(client, "/tms/status",
            [{"code": C.code_for(n), "name": n}
             for n in dict.fromkeys(C.ITEM_STATUSES + C.EXECUTION_STATUSES)],
            "code", stats, "statuses")
    _ensure(client, "/tms/priority",
            [{"code": C.code_for(n), "name": n} for n in C.PRIORITIES],
            "code", stats, "priorities")
    return stats


def _item_payloads(world: World) -> list[dict]:
    """Test items, each carrying the metadata the analytics views pivot into *_set columns."""
    rng = random.Random(world.seed + 1)
    payloads = []
    for project, _ in PROJECTS:
        if project == C.CI_RUN_PROJECT_CODE:
            continue  # CI_RUN carries pipeline runs, not test items
        for index in range(1, ITEMS_PER_PROJECT + 1):
            key = item_key(project, index)
            team = TEAMS[index % len(TEAMS)]
            created = world.start + timedelta(days=rng.randint(0, 20))
            metadata = [
                # 03__mvw_item_metadata.sql pivots these exact names; teams_set is what the
                # frontend's Team filter reads.
                meta(C.META_TEAM, team),
                meta(C.META_SCRUM_TEAM, team),
                meta(C.META_FUNCTIONAL_AREA, C.FUNCTIONAL_AREAS[index % len(C.FUNCTIONAL_AREAS)]),
                # 09__mvw_product_team_scale_link.sql matches value LIKE 'DEMO-T%'.
                meta(C.META_LABEL, item_key("DEMO", index)),
                meta(C.META_AFFECTED_VERSION, world.versions[index % len(world.versions)].code),
            ]
            if index % 3 == 0:
                metadata.append(meta(C.META_RCA, C.RCA_VALUES[index % len(C.RCA_VALUES)]))
            payloads.append({
                "code": key,
                "name": TEST_NAMES[index % len(TEST_NAMES)],
                "createdOn": iso(created),
                "createdBy": USERS[index % len(USERS)],
                "updatedOn": iso(created + timedelta(days=1)),
                "updatedBy": USERS[(index + 1) % len(USERS)],
                "type": C.code_for("Test"),
                "status": C.code_for(C.ITEM_STATUSES[index % len(C.ITEM_STATUSES)]),
                "priority": C.code_for(C.PRIORITIES[index % len(C.PRIORITIES)]),
                "project": project,
                "versions": [world.versions[index % len(world.versions)].code],
                "metadata": metadata,
                "statusTransitions": [],
            })
    return payloads


def load_items(client: AthenaClient, world: World) -> Stats:
    return client.post_many("/tms/item", _item_payloads(world), "items")


def _outcome(world: World, rng: random.Random, cycle_index: int, item_index: int,
             team: str) -> str:
    """Which status one execution gets - where the planted quality signals live."""
    if cycle_index % BLOCKED_CYCLE_EVERY == 0 and rng.random() < 0.35:
        return C.EXEC_BLOCKED
    if item_index % len(TEST_NAMES) == FLAKY_ITEM_INDEX:
        # Alternates with the cycle, so its history reads as a pattern rather than a verdict.
        return C.EXEC_FAIL if cycle_index % 2 == 0 else C.EXEC_PASS
    fail_rate = 0.34 if team == TEAMS[WEAK_TEAM_INDEX] else 0.11
    return C.EXEC_FAIL if rng.random() < fail_rate else C.EXEC_PASS


def _cycle_payloads(world: World) -> list[dict]:
    rng = random.Random(world.seed + 2)
    per_sprint = max(1, world.profile.cycles // len(world.sprints))
    payloads = []
    cycle_index = 0

    for sprint in world.sprints:
        for slot in range(per_sprint):
            cycle_index += 1
            project = "DEMO" if slot % 3 else ("PAY" if slot % 2 else "SRCH")
            kind = ["Regression Automated", "Smoke", "Regression Manual", "Exploratory"][slot % 4]
            team = TEAMS[cycle_index % len(TEAMS)]
            size = rng.randint(12, 28)
            first = rng.randint(1, ITEMS_PER_PROJECT - size)

            # A stalled cycle stops executing partway through, leaving the rest Not Executed -
            # which is what flattens its burn-up against the reference line.
            stalls_at = size // 2 if cycle_index % STALLED_CYCLE_EVERY == 0 else size

            executions = []
            for offset in range(size):
                index = first + offset
                created = sprint.start + timedelta(hours=rng.randint(0, 12))
                if offset >= stalls_at:
                    executions.append({
                        "createdOn": iso(created),
                        "item": item_key(project, index),
                        "status": C.code_for(C.EXEC_NOT_EXECUTED),
                        "executor": USERS[index % len(USERS)],
                    })
                    continue
                executed = sprint.start + timedelta(
                    days=rng.randint(0, max(1, (sprint.end - sprint.start).days - 1)),
                    hours=rng.randint(8, 19), minutes=rng.randint(0, 59))
                executions.append({
                    "createdOn": iso(created),
                    "executedOn": iso(executed),
                    "item": item_key(project, index),
                    "status": C.code_for(_outcome(world, rng, cycle_index, index, team)),
                    "executor": USERS[index % len(USERS)],
                })

            payloads.append({
                "code": f"C{cycle_index}",
                # cycle_short_name is substring(name, '.*/(.*)$') - the trailing segment.
                "name": f"/{sprint.version.code}/{kind} {team}",
                "startDate": iso(sprint.start),
                "endDate": iso(sprint.end),
                "project": project,
                "version": sprint.version.code,
                "testExecutions": executions,
            })
    return payloads


def load_cycles(client: AthenaClient, world: World) -> Stats:
    """One POST per cycle: TestCycleDto carries its executions nested, so ~200 requests rather
    than ~20,000.

    Serial, and that is not a throughput choice. Concurrent cycle writes collide on
    athena_tms.execution's primary key - "duplicate key value violates unique constraint
    execution_pkey" - even though the column is a bigserial whose sequence is not behind the
    data. Two concurrent posts of 14 cycles land 2; the same 14 posted one at a time land all 14.
    That is a race inside the tms service's write path rather than anything this loader controls,
    so the loader takes the slow lane and the defect is reported rather than papered over.
    Because each request carries a whole cycle, serial still means ~200 requests, not 20,000.
    """
    return client.post_many("/tms/cycle", _cycle_payloads(world), "cycles", serial=True)


# ---------------------------------------------------------------------------- git -------------

CHANGE_TYPES = ["ADD", "MODIFY", "DELETE", "RENAME", "COPY"]
SOURCE_DIRS = ["src/main/java", "src/test/java", "src/main/resources", "web/src", "docs"]


def load_repositories(client: AthenaClient, world: World) -> Stats:
    stats = Stats()
    for name in REPOSITORIES:
        client.post_one("/git/repo",
                        {"name": name, "url": f"https://github.com/athena-demo/{name}.git"}, stats)
    return stats


def _commit_payloads(world: World) -> list[dict]:
    rng = random.Random(world.seed + 3)
    span = (world.end - world.start).total_seconds()
    payloads = []
    for n in range(world.profile.commits):
        # Weekday-weighted: pick a moment, then nudge weekends into the following Monday so the
        # activity chart has the shape of a working week rather than a flat band.
        moment = world.start + timedelta(seconds=rng.random() * span)
        if moment.weekday() >= 5:
            moment += timedelta(days=7 - moment.weekday())
        moment = moment.replace(hour=rng.randint(8, 19), minute=rng.randint(0, 59))
        if moment > world.end:
            moment = world.end - timedelta(hours=rng.randint(1, 48))

        author = USERS[n % len(USERS)]
        repo = REPOSITORIES[n % len(REPOSITORIES)]
        files = rng.randint(1, 6)
        diffs = []
        for f in range(files):
            path = f"{SOURCE_DIRS[(n + f) % len(SOURCE_DIRS)]}/File{(n + f) % 400}.java"
            diffs.append({
                "oldPath": path, "newPath": path,
                "inserted": rng.randint(1, 120), "deleted": rng.randint(0, 60),
                "changeType": CHANGE_TYPES[(n + f) % len(CHANGE_TYPES)],
            })
        payloads.append({
            # Deterministic, so a re-run with the same seed collides rather than duplicating.
            "hash": stable_hash(world.seed, "commit", n),
            "parentHash": stable_hash(world.seed, "commit", n - 1) if n else None,
            "parentCount": 1 if n else 0,
            "shortMessage": _commit_message(rng, n),
            "repository": repo,
            "author": author,
            "committer": USERS[(n + 3) % len(USERS)],
            "commitTime": iso(moment),
            # Without diffEntries the commit's total_file / line_inserted / line_deleted stay
            # NULL, and those are columns the Change & run Commits panel shows.
            "diffEntries": diffs,
            "tags": [],
            "metadata": [meta("branch", "main" if n % 4 else f"feature/DEMO-{n % 500}")],
        })
    return payloads


def _commit_message(rng: random.Random, n: int) -> str:
    verb = ["Fix", "Add", "Refactor", "Remove", "Update", "Harden", "Document"][n % 7]
    noun = ["checkout validation", "payment retry", "search ranking", "cart persistence",
            "order history paging", "session handling", "report export", "index rebuild"][n % 8]
    return f"{verb} {noun}"


def load_commits(client: AthenaClient, world: World) -> Stats:
    return client.post_many("/git/commit", _commit_payloads(world), "commits")


# ------------------------------------------------------------------------- pipeline -----------

def load_pipeline_statuses(client: AthenaClient, world: World) -> Stats:
    stats = Stats()
    for name in C.PIPELINE_STATUSES:
        client.post_one("/pipeline/execution_status", {"name": name}, stats)
    return stats


def load_pipelines(client: AthenaClient, world: World) -> tuple[Stats, Stats]:
    """Pipelines first, then their executions - an execution needs a numeric pipelineId, which
    only exists once the pipeline is created and its entity_id header read back."""
    rng = random.Random(world.seed + 4)
    span = (world.end - world.start).total_seconds()
    runs: list[tuple[dict, int]] = []
    lock = threading.Lock()

    payloads = []
    for n in range(world.profile.pipelines):
        started = world.start + timedelta(seconds=rng.random() * span)
        # 30__mvw_pipeline_execution.sql excludes names beginning 'local'.
        name = f"{C.PIPELINE_NAME_PREFIXES[n % len(C.PIPELINE_NAME_PREFIXES)]}-pipeline"
        version = world.versions[min(len(world.versions) - 1,
                                     int((started - world.start) / (world.end - world.start)
                                         * len(world.versions)))]
        payloads.append({
            "name": name,
            "number": str(1000 + n),
            "description": f"{name} run {1000 + n}",
            "startDate": iso(started),
            "endDate": iso(started + timedelta(minutes=rng.randint(4, 55))),
            "project": C.CI_RUN_PROJECT_CODE,
            "environment": ENVIRONMENTS[n % len(ENVIRONMENTS)][0],
            "version": version.code,
            "metadata": [meta("trigger", "schedule" if n % 3 else "merge")],
        })

    def remember(payload: dict, entity_id: int | None) -> None:
        if entity_id:
            with lock:
                runs.append((payload, entity_id))

    pipeline_stats = client.post_many("/pipeline/pipeline", payloads, "pipelines",
                                      on_created=remember)

    if client.dry_run:
        return pipeline_stats, Stats()

    execution_payloads = []
    for payload, pipeline_id in runs:
        started = payload["startDate"]
        for e in range(rng.randint(3, 9)):
            group = C.FUNCTIONAL_GROUPS[e % len(C.FUNCTIONAL_GROUPS)]
            status = (C.PIPELINE_PASS if rng.random() > 0.18
                      else (C.PIPELINE_FAIL if rng.random() > 0.3 else C.PIPELINE_SKIP))
            execution_payloads.append({
                # mvw_pipeline_execution derives functional_group from '%demo%.<group>%'.
                "packageName": C.execution_package(group),
                "className": f"{group.capitalize()}Test",
                "methodName": f"test{group.capitalize()}{e}",
                "parameters": "",
                "startTime": started,
                "endTime": payload["endDate"],
                "status": status,
                "executor": USERS[e % len(USERS)],
                "pipelineId": pipeline_id,
                "metadata": [],
            })

    return pipeline_stats, client.post_many("/pipeline/execution", execution_payloads,
                                            "pipeline executions")


# ---------------------------------------------------------------------------- kube ------------

APPS = ["storefront", "payments", "search", "reporting", "gateway"]
NAMESPACES = ["demo-prod", "demo-stg", "demo-qa"]


def _pod_payloads(world: World) -> list[dict]:
    rng = random.Random(world.seed + 5)
    span = (world.end - world.start).total_seconds()
    payloads = []
    for n in range(world.profile.pods):
        app = APPS[n % len(APPS)]
        version = world.versions[n % len(world.versions)]
        created = world.start + timedelta(seconds=rng.random() * span)
        # Most pods are still alive at the end of the window; a few were replaced.
        deleted = created + timedelta(days=rng.randint(1, 20)) if n % 5 == 0 else None
        last_sync = min(deleted or world.end, world.end)
        name = f"{app}-{version.code.replace('.', '-')}-{stable_hash(world.seed, 'pod', n)[:8]}"
        payloads.append({
            "uid": stable_hash(world.seed, "uid", n)[:36],
            "name": name,
            "namespace": NAMESPACES[n % len(NAMESPACES)],
            "hostname": name,
            "nodeName": f"node-{n % 6}",
            "createdAt": iso(created),
            "deletedAt": iso(deleted) if deleted else None,
            "lastSync": iso(last_sync),
            "status": {
                "name": name,
                "phase": "Running" if deleted is None else "Succeeded",
                "message": "", "reason": "",
            },
            "project": PROJECTS[n % 3][0],
            "containers": [{
                "type": "app", "name": app,
                "image": f"registry.demo/{app}:{version.code}",
                "imageId": stable_hash(world.seed, "image", app, version.code),
                "ready": True, "started": True, "restartCount": rng.randint(0, 3),
                "startedAt": iso(created), "lastSync": iso(last_sync), "metadata": [],
            }],
            "metadata": [],
            "annotations": [meta("kubectl.kubernetes.io/restartedAt", iso(created))],
            "selectors": [meta("app", app)],
            # 06__mvw_pod_basic_info.sql requires BOTH of these label names. Without them the pod
            # exists in athena_kube.pod and produces no row in the view the UI reads.
            "labels": [
                meta(C.POD_LABEL_NAME, app),
                meta(C.POD_LABEL_VERSION, version.code),
                meta("app.kubernetes.io/component", "backend"),
            ],
        })
    return payloads


def load_pods(client: AthenaClient, world: World) -> Stats:
    """Serial, for the same reason as the cycles and a different underlying bug.

    A pod is written together with its containers and labels, and concurrent writes fail with
    "Instance of Container references an unsaved transient instance of Pod" - Hibernate ordering
    the cascade wrong when several pods are in flight at once. It cost 32 of 300 pods on a
    concurrent run and none at all serially.
    """
    return client.post_many("/kube/pod", _pod_payloads(world), "pods", serial=True)


# --------------------------------------------------------------------------- metric -----------

def _metric_payloads(world: World) -> list[dict]:
    rng = random.Random(world.seed + 6)
    span = (world.end - world.start).total_seconds()
    series = [(name, target, spec[0], spec[1])
              for name, spec in ACTIONS.items() for target in spec[2]]

    payloads = []
    for n in range(world.profile.metrics):
        action, target, category, kind = series[n % len(series)]
        moment = world.start + timedelta(seconds=rng.random() * span)
        payloads.append({
            "duration": duration_for(action, moment, world, rng),
            "actionTime": iso(moment),
            "environment": "PROD" if n % 4 else "STG",
            "project": PROJECTS[n % 3][0],
            "action": {
                "name": action, "category": category, "target": target, "type": kind,
                "command": f"{action}:{target}",
                "parameter": "",
            },
        })
    return payloads


def load_metrics(client: AthenaClient, world: World) -> Stats:
    return client.post_many("/metric/metric", _metric_payloads(world), "metric samples")

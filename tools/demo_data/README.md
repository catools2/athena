# Demo data generator

Loads a coherent six-month dataset into Athena by POSTing to the gateway's REST API.

```bash
pip install -r requirements.txt

# start the ingestion services first - see "Running the services" below
python3 generate.py --profile demo --refresh
python3 verify.py
```

## Why the API and not SQL

Posting through the services exercises the same validation, DTO mapping and foreign-key
resolution that real ingestion uses, so the data cannot drift from what the services would
actually accept. It is the slower path and the right one: three of the defects this generator
had to work around were only visible because the services rejected the payloads.

## Profiles

| profile | window | cycles | commits | pipelines | pods | metrics | approx |
|---|---|---|---|---|---|---|---|
| `small` | 1 month | 15 | 500 | 120 | 40 | 10k | ~40s |
| `demo` | 6 months | 200 | 5,000 | 1,500 | 300 | 90k | ~3min |
| `large` | 6 months | 600 | 20,000 | 5,000 | 800 | 400k | ~15min |

## The planted signals

Uniform random data makes every chart look the same. These exist so each panel says something:

| Signal | Where it shows |
|---|---|
| `checkout` p95 steps up 1.9x two thirds of the way in | Performance -> "Change against the preceding window" |
| One test alternates pass/fail every cycle | Test cycles -> a test's history reads as a pattern |
| One cycle in 17 stops halfway | Test cycles -> the burn-up flattens against its reference line |
| One cycle in 11 is blocked-heavy | The Blocked segments, which had no data at all before |
| Team Beta fails ~3x more than the rest | Test cycles -> "Where failures concentrate", by team |

## Conventions

`conventions.py` holds every string a view or curated query hardcodes, each citing its source.
Get one wrong and the rows land in PostgreSQL and then never appear in the UI - which is a much
more expensive failure than a rejected POST. The two worth knowing:

- **Pod labels must include `app.kubernetes.io/name` and `app.kubernetes.io/version`.**
  `06__mvw_pod_basic_info.sql` inner-joins on both, and the UI reads that view, not the table.
- **Pipeline status must be `SUCCESS`/`FAILURE`**, the vocabulary `PipelineListener` reports.
  (`athena-locust`'s pipeline factory emits `PASSED`/`FAILED`, which every query counts as a
  failure. That factory does not match the real ETL.)

## `--refresh` is not optional

Every `mv_*` is a snapshot. Loading without refreshing leaves every panel empty, which reads as a
broken dashboard rather than as stale data. `--refresh` calls
`athena.refresh_analytics_views()`; `--db-user` needs execute on it (`athena_core_user` works).

## Running the services

The loader needs core, git, kube, metric, pipeline and tms up. Two things are easy to miss:

```bash
# ATHENA_DB_USERNAME must be explicit: each service's application.properties sets its own
# spring.datasource.username, but defaults/datasource.properties is pulled in with
# spring.config.import, and an imported document overrides the one importing it.
#
# SPRING_PROFILES_ACTIVE=dev registers the Feign clients (FeignConfiguration is
# @Profile({"prod","dev"})). Without it git, kube and pipeline cannot resolve users or projects
# and refuse to start.
ATHENA_DB_USERNAME=athena_git_user ATHENA_DB_PASSWORD=athena_git_user \
SPRING_PROFILES_ACTIVE=dev java -jar athena-boot-git/target/athena-boot-git-2.0.0.jar
```

Run services from a *copy* of the jar. Spring Boot reads nested jar entries lazily, so rebuilding
under a running JVM makes not-yet-loaded classes vanish and wedges the service with
`NoClassDefFoundError`.

## Known service defects this works around

Found while loading; none are in this generator, and all are reported rather than hidden:

| Defect | Effect | Workaround here |
|---|---|---|
| Concurrent cycle writes collide on `athena_tms.execution` primary key, though the column is a `bigserial` whose sequence is not behind the data | 12 of 14 cycles fail | cycles are posted serially |
| Concurrent pod writes fail with `Container references an unsaved transient instance of Pod` | ~10% of pods fail | pods are posted serially - which does **not** fix it (see below) |
| ~11% of pods fail with a 500 *deterministically*, in isolation, serial or not | those pods never load | none; 268 of 300 is enough for a demo |

Reference data (users, statuses, priorities, item types, projects) is written, read back, and
retried, because a status lost to a cold connection pool during service start-up does not cost one
row - it costs every row that names it, and it surfaces hundreds of requests later as
`null value in column item_id`.

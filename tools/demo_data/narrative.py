"""The story the demo data tells.

Random data makes every chart look the same: flat, uniform, and impossible to read a conclusion
from. This builds a deliberately shaped six months instead - release trains, sprints, and a small
number of planted signals that each make one panel of the UI say something true and specific.

Everything derives from a seed, so two runs with the same seed propose the same entities and the
same natural keys.
"""

from __future__ import annotations

import hashlib
import random
from dataclasses import dataclass, field
from datetime import datetime, timedelta, timezone

import conventions as C


@dataclass(frozen=True)
class Profile:
    name: str
    months: int
    cycles: int
    commits: int
    pipelines: int
    pods: int
    metrics: int


PROFILES = {
    # Enough to see the shape of every page without waiting.
    "small": Profile("small", months=1, cycles=15, commits=500, pipelines=120, pods=40,
                     metrics=10_000),
    # The default. ~500 timing samples a day across 24 action/target series is roughly 20 per
    # series per day, which is enough for a p95 that does not jump around on one slow sample.
    "demo": Profile("demo", months=6, cycles=200, commits=5_000, pipelines=1_500, pods=300,
                    metrics=90_000),
    # For exercising the row caps, the statement timeout and the cost of a view refresh.
    "large": Profile("large", months=6, cycles=600, commits=20_000, pipelines=5_000, pods=800,
                     metrics=400_000),
}

# --- the planted signals ----------------------------------------------------------------------
#
# Each exists so one specific panel has something to show. Without them the data is uniform and
# every chart is a flat line, which demonstrates nothing.

REGRESSED_ACTION = "checkout"       # p95 steps up partway through -> perf_regression
FLAKY_ITEM_INDEX = 7                # alternates pass/fail every cycle -> test_history as a pattern
STALLED_CYCLE_EVERY = 17            # one in 17 cycles stops halfway -> the burn-up flattens
BLOCKED_CYCLE_EVERY = 11            # one in 11 is blocked-heavy -> the Blocked segments exist
WEAK_TEAM_INDEX = 2                 # this team fails far more -> the failure profile is worth using

TEAMS = ["Team Alpha", "Team Beta", "Team Gamma", "Team Delta", "Team Epsilon", "Team Zeta"]

USERS = [
    "alex smith", "jane doe", "sam taylor", "priya nair",
    "marco rossi", "yuki tanaka", "omar haddad", "lena fischer",
]

PROJECTS = [
    ("DEMO", "Demo Storefront"),
    ("PAY", "Payments Platform"),
    ("SRCH", "Search Services"),
    # Required by 02__mvw_ci_run_pipeline_execution.sql, which selects only this code.
    (C.CI_RUN_PROJECT_CODE, "CI Runs"),
]

ENVIRONMENTS = [("DEV", "Development"), ("QA", "Quality Assurance"),
                ("STG", "Staging"), ("PROD", "Production")]

REPOSITORIES = ["storefront-web", "payments-api", "search-indexer", "platform-shared"]

# name -> (category, type, targets). Targets give the performance drill-down a second level.
ACTIONS = {
    "login":     ("auth", "action", ["web-app", "mobile-app"]),
    "checkout":  ("order", "action", ["web-app", "mobile-app", "partner-api"]),
    "search":    ("catalog", "action", ["web-app", "partner-api"]),
    "add_to_cart": ("order", "action", ["web-app", "mobile-app"]),
    "payment_authorise": ("payment", "service", ["psp-primary", "psp-fallback"]),
    "order_history": ("account", "action", ["web-app"]),
    "index_rebuild": ("catalog", "batch", ["search-cluster"]),
    "report_export": ("reporting", "batch", ["reporting-db"]),
}

TEST_NAMES = [
    "login with valid credentials", "login rejects a wrong password", "add a single item to the cart",
    "cart survives a session refresh", "checkout with a saved card", "checkout with a new card",
    "checkout rejects an expired card", "search returns ranked results", "search handles no matches",
    "order history paginates", "refund a completed order", "apply a discount code",
    "guest checkout completes", "address validation rejects a bad postcode", "export a sales report",
]


@dataclass
class Version:
    code: str
    name: str
    project: str
    released: datetime


@dataclass
class Sprint:
    index: int
    start: datetime
    end: datetime
    version: Version


@dataclass
class World:
    profile: Profile
    seed: int
    start: datetime
    end: datetime
    versions: list[Version] = field(default_factory=list)
    sprints: list[Sprint] = field(default_factory=list)
    rng: random.Random = field(default_factory=random.Random)

    @property
    def regression_starts(self) -> datetime:
        """Two thirds of the way in, so both the baseline and the regressed window have history."""
        return self.start + (self.end - self.start) * 2 // 3


def build_world(profile: Profile, seed: int, end: datetime) -> World:
    rng = random.Random(seed)
    start = end - timedelta(days=profile.months * 30)
    world = World(profile=profile, seed=seed, start=start, end=end, rng=rng)

    # A two-week release train. Version codes read like real ones so the version filter is legible.
    sprint_length = timedelta(days=14)
    count = max(1, int((end - start) / sprint_length))
    for i in range(count):
        sprint_start = start + sprint_length * i
        sprint_end = min(sprint_start + sprint_length, end)
        version = Version(
            code=f"{4 + i // 6}.{i % 6}",
            name=f"{4 + i // 6}.{i % 6}",
            project="DEMO",
            released=sprint_end,
        )
        world.versions.append(version)
        world.sprints.append(Sprint(i, sprint_start, sprint_end, version))
    return world


def stable_hash(*parts: object) -> str:
    """A commit-hash-shaped digest that is the same on every run with the same seed. Real sha1s
    from faker would differ per run and make a re-run duplicate rather than collide."""
    return hashlib.sha1("|".join(str(p) for p in parts).encode()).hexdigest()


def item_key(project: str, index: int) -> str:
    return f"{project}-T{index}"


def duration_for(action: str, moment: datetime, world: World, rng: random.Random) -> int:
    """Milliseconds for one sample.

    A log-normal-ish shape rather than a uniform one, because response times have a tail and the
    whole reason this page reports percentiles is that the tail is what users feel. The planted
    regression is a step change, not a drift - that is what a bad deploy actually looks like.
    """
    base = {
        "login": 240, "checkout": 520, "search": 180, "add_to_cart": 150,
        "payment_authorise": 700, "order_history": 300,
        "index_rebuild": 4200, "report_export": 2600,
    }[action]

    # Weekday business hours are busier and slower.
    hour_load = 1.35 if 9 <= moment.hour <= 18 and moment.weekday() < 5 else 1.0
    sample = rng.lognormvariate(0, 0.42) * base * hour_load

    if action == REGRESSED_ACTION and moment >= world.regression_starts:
        sample *= 1.9  # the step the regression panel exists to catch

    return max(1, int(sample))

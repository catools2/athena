"""HTTP client for the Athena gateway.

Thin on purpose. The one thing it knows that a bare `requests` call does not is Athena's
create convention: a successful POST answers 201 with an empty body and the new id in an
`entity_id` header, and a few endpoints answer 208 when the row already exists.
"""

from __future__ import annotations

import itertools
import logging
import threading
from concurrent.futures import ThreadPoolExecutor, as_completed
from dataclasses import dataclass, field
from datetime import datetime, timezone
from typing import Any, Callable, Iterable, Sequence

import requests
from requests.adapters import HTTPAdapter
from urllib3.util.retry import Retry

log = logging.getLogger("demo_data.client")

# The DTOs all declare @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC").
# Jackson is strict about it: three-digit milliseconds and a literal Z, not an offset.
TIMESTAMP = "%Y-%m-%dT%H:%M:%S.%f"


def iso(moment: datetime) -> str:
    """Format an instant the way every Athena DTO expects it."""
    return moment.astimezone(timezone.utc).strftime(TIMESTAMP)[:-3] + "Z"


@dataclass
class Stats:
    """What happened, counted. A loader reports rather than assumes."""

    created: int = 0
    existed: int = 0
    failed: int = 0
    errors: list[str] = field(default_factory=list)
    _lock: threading.Lock = field(default_factory=threading.Lock, repr=False)

    def record(self, status: int, detail: str = "") -> None:
        with self._lock:
            if status == 201:
                self.created += 1
            elif status in (200, 208):
                self.existed += 1
            else:
                self.failed += 1
                # Keep a bounded sample: a systematic failure repeats thousands of times and the
                # first few say everything the thousandth would.
                if len(self.errors) < 5:
                    self.errors.append(detail)

    def merge(self, other: "Stats") -> None:
        self.created += other.created
        self.existed += other.existed
        self.failed += other.failed
        self.errors.extend(other.errors[: max(0, 5 - len(self.errors))])

    def __str__(self) -> str:
        out = f"{self.created} created, {self.existed} already present, {self.failed} failed"
        return out + (f"\n      first failures: {self.errors}" if self.errors else "")


class AthenaClient:
    def __init__(self, host: str, workers: int = 16, timeout: int = 30, dry_run: bool = False):
        self.host = host.rstrip("/")
        self.workers = workers
        self.timeout = timeout
        self.dry_run = dry_run
        self._local = threading.local()

    def _session(self) -> requests.Session:
        """One session per thread: requests.Session is not documented as thread-safe, and the
        point of having one at all is the pooled keep-alive connection underneath it."""
        session = getattr(self._local, "session", None)
        if session is None:
            session = requests.Session()
            retry = Retry(
                total=3, backoff_factor=0.3,
                status_forcelist=(502, 503, 504),
                allowed_methods=frozenset(["GET", "POST", "PUT"]),
            )
            adapter = HTTPAdapter(max_retries=retry, pool_maxsize=self.workers * 2)
            session.mount("http://", adapter)
            session.mount("https://", adapter)
            self._local.session = session
        return session

    def post(self, path: str, payload: dict) -> tuple[int, int | None]:
        """POST one entity. Returns (status, new id or None)."""
        if self.dry_run:
            return 201, 0
        response = self._session().post(
            f"{self.host}{path}", json=payload, timeout=self.timeout,
            headers={"Content-Type": "application/json"},
        )
        entity_id = response.headers.get("entity_id")
        return response.status_code, int(entity_id) if entity_id else None

    def get(self, path: str, **params: Any) -> Any:
        if self.dry_run:
            return None
        response = self._session().get(f"{self.host}{path}", params=params, timeout=self.timeout)
        if response.status_code != 200 or not response.content:
            return None
        return response.json()

    def post_one(self, path: str, payload: dict, stats: Stats) -> int | None:
        status, entity_id = self.post(path, payload)
        stats.record(status, f"{status} {path} {str(payload)[:120]}")
        return entity_id

    def post_many(
        self,
        path: str,
        payloads: Sequence[dict] | Iterable[dict],
        label: str,
        on_created: Callable[[dict, int | None], None] | None = None,
        serial: bool = False,
    ) -> Stats:
        """POST a batch. Ordering is not preserved when concurrent, so this is only for entities
        whose creation does not depend on a sibling - which is why the reference entities are
        loaded through _ensure and only the leaves come through here.

        `serial` exists for one caller: see the note on load_cycles."""
        stats = Stats()
        payloads = list(payloads)
        if not payloads:
            return stats

        counter = itertools.count(1)
        total = len(payloads)

        def send(payload: dict) -> None:
            status, entity_id = self.post(path, payload)
            # One retry on a server error. Concurrent creates race on the unique constraints of
            # shared child rows - two metric samples inventing the same action, say - and the
            # loser sees a 500 that succeeds immediately on a second attempt.
            if status >= 500:
                status, entity_id = self.post(path, payload)
            stats.record(status, f"{status} {path} {str(payload)[:120]}")
            if on_created is not None:
                on_created(payload, entity_id)
            n = next(counter)
            if n % 2000 == 0 or n == total:
                log.info("      %s %d/%d", label, n, total)

        if serial:
            for payload in payloads:
                send(payload)
            return stats

        with ThreadPoolExecutor(max_workers=self.workers) as pool:
            for future in as_completed([pool.submit(send, p) for p in payloads]):
                future.result()
        return stats

    def healthy(self) -> bool:
        try:
            return self._session().get(f"{self.host}/analytics/queries", timeout=10).ok
        except requests.RequestException:
            return False

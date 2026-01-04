import random
from datetime import datetime, timezone
import factory
from faker import Faker

from tasks.athena_task_set import AthenaTaskSet

fake = Faker()


def _short(text: str, max_len: int) -> str:
    return (text or "")[:max_len]


def _now_iso() -> str:
    return datetime.now(timezone.utc).isoformat(timespec="milliseconds").replace("+00:00", "Z")


class Metadata:
    def __init__(self, name: str, value: str):
        self.name = name
        self.value = value

    def to_dict(self) -> dict:
        return {
            "name": self.name,
            "value": self.value,
        }


class ApiPath:
    def __init__(self, method: str, url: str, title: str, description: str, firstTimeSeen: str,
                 lastSyncTime: str | None, parameters: dict, metadata: list[dict]):
        self.method = method
        self.url = url
        self.title = title
        self.description = description
        self.firstTimeSeen = firstTimeSeen
        self.lastSyncTime = lastSyncTime
        self.parameters = parameters
        self.metadata = metadata

    def to_dict(self) -> dict:
        result = {
            "method": self.method,
            "url": self.url,
            "title": self.title,
            "description": self.description,
            "firstTimeSeen": self.firstTimeSeen,
            "parameters": self.parameters,
            "metadata": self.metadata,
        }
        if self.lastSyncTime:
            result["lastSyncTime"] = self.lastSyncTime
        return result


class ApiSpec:
    def __init__(self, project: str, name: str, version: str, title: str,
                 firstTimeSeen: str, lastSyncTime: str | None, paths: list[dict], metadata: list[dict]):
        self.project = project
        self.name = name
        self.version = version
        self.title = title
        self.firstTimeSeen = firstTimeSeen
        self.lastSyncTime = lastSyncTime
        self.paths = paths
        self.metadata = metadata

    def to_dict(self) -> dict:
        result = {
            "project": self.project,
            "name": self.name,
            "version": self.version,
            "title": self.title,
            "firstTimeSeen": self.firstTimeSeen,
            "paths": self.paths,
            "metadata": self.metadata,
        }
        if self.lastSyncTime:
            result["lastSyncTime"] = self.lastSyncTime
        return result


class MetadataFactory(factory.Factory):
    class Meta:
        model = Metadata

    name = factory.LazyFunction(lambda: _short(f"meta_{fake.word()}", 200))
    value = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=5), 1000))

    @classmethod
    def to_dict(cls) -> dict:
        obj: Metadata = MetadataFactory()
        return obj.to_dict()


class ApiPathFactory(factory.Factory):
    class Meta:
        model = ApiPath

    method = factory.LazyFunction(lambda: _short(random.choice(["GET", "POST", "PUT", "DELETE", "PATCH"]), 10))
    url = factory.LazyFunction(lambda: _short(f"/{fake.word()}/{fake.word()}", 500))
    title = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=4), 1000))
    description = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=10), 5000))
    firstTimeSeen = factory.LazyFunction(_now_iso)
    lastSyncTime = factory.LazyFunction(lambda: _now_iso() if random.choice([True, False]) else None)
    parameters = factory.LazyFunction(lambda: {f"param{i}": fake.word() for i in range(random.randint(0, 3))})
    metadata = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 2))])

    @classmethod
    def to_dict(cls) -> dict:
        obj: ApiPath = ApiPathFactory()
        return obj.to_dict()


class ApiSpecFactory(factory.Factory):
    class Meta:
        model = ApiSpec

    name = factory.LazyFunction(lambda: _short(f"{fake.word()}-api", 100))
    title = factory.LazyFunction(lambda: _short(fake.catch_phrase(), 100))
    firstTimeSeen = factory.LazyFunction(_now_iso)
    lastSyncTime = factory.LazyFunction(lambda: _now_iso() if random.choice([True, False]) else None)
    paths = factory.LazyFunction(lambda: [ApiPathFactory.to_dict() for _ in range(random.randint(1, 3))])
    metadata = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 2))])

    @factory.lazy_attribute
    def project(self):
        return AthenaTaskSet.get_project_with_versions()

    @factory.lazy_attribute
    def version(self):
        if not self.project:
            return None
        return AthenaTaskSet.get_version(self.project)["code"]

    @classmethod
    def to_dict(cls) -> dict:
        obj: ApiSpec = ApiSpecFactory()
        return obj.to_dict()


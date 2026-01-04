import random
from datetime import datetime, timezone

import factory
from faker import Faker

from tasks.athena_task_set import AthenaTaskSet

fake = Faker()


# Helpers

def _short(text: str, max_len: int) -> str:
    return (text or "")[:max_len]


def _now_iso() -> str:
    # Match 'yyyy-MM-dd'T'HH:mm:ss.SSS'Z''
    return datetime.now(timezone.utc).isoformat(timespec="milliseconds").replace("+00:00", "Z")


# Core compatible metadata dto (name/value)
class Metadata:
    def __init__(self, name: str, value: str):
        self.name = name
        self.value = value

    def to_dict(self) -> dict:
        return {"name": self.name, "value": self.value}


# PodStatusDto-compatible
class PodStatus:
    def __init__(self, name: str, phase: str, message: str, reason: str):
        self.name = name
        self.phase = phase
        self.message = message
        self.reason = reason

    def to_dict(self) -> dict:
        return {
            "name": self.name,
            "phase": self.phase,
            "message": self.message,
            "reason": self.reason,
        }


# ContainerDto-compatible
class Container:
    def __init__(self,
                 type: str,
                 name: str,
                 image: str,
                 imageId: str,
                 ready: bool,
                 started: bool,
                 restartCount: int,
                 startedAt: str,
                 lastSync: str,
                 metadata: list[dict]):
        self.type = type
        self.name = name
        self.image = image
        self.imageId = imageId
        self.ready = ready
        self.started = started
        self.restartCount = restartCount
        self.startedAt = startedAt
        self.lastSync = lastSync
        self.metadata = metadata

    def to_dict(self) -> dict:
        return {
            "type": self.type,
            "name": self.name,
            "image": self.image,
            "imageId": self.imageId,
            "ready": self.ready,
            "started": self.started,
            "restartCount": self.restartCount,
            "startedAt": self.startedAt,
            "lastSync": self.lastSync,
            "metadata": self.metadata,
        }


# PodDto-compatible
class Pod:
    def __init__(self,
                 uid: str,
                 name: str,
                 namespace: str,
                 hostname: str,
                 nodeName: str,
                 createdAt: str | None,
                 deletedAt: str | None,
                 lastSync: str,
                 status: dict,
                 project: str,
                 containers: list[dict],
                 metadata: list[dict],
                 annotations: list[dict],
                 selectors: list[dict],
                 labels: list[dict]):
        self.uid = uid
        self.name = name
        self.namespace = namespace
        self.hostname = hostname
        self.nodeName = nodeName
        self.createdAt = createdAt
        self.deletedAt = deletedAt
        self.lastSync = lastSync
        self.status = status
        self.project = project
        self.containers = containers
        self.metadata = metadata
        self.annotations = annotations
        self.selectors = selectors
        self.labels = labels

    def to_dict(self) -> dict:
        return {
            "uid": self.uid,
            "name": self.name,
            "namespace": self.namespace,
            "hostname": self.hostname,
            "nodeName": self.nodeName,
            "createdAt": self.createdAt,
            "deletedAt": self.deletedAt,
            "lastSync": self.lastSync,
            "status": self.status,
            "project": self.project,
            "containers": self.containers,
            "metadata": self.metadata,
            "annotations": self.annotations,
            "selectors": self.selectors,
            "labels": self.labels,
        }


# Factories
class MetadataFactory(factory.Factory):
    class Meta:
        model = Metadata

    name = factory.LazyFunction(lambda: _short(f"{fake.word()}_{random.randint(1, 9999)}", 200))
    value = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=6), 1000))

    @classmethod
    def to_dict(cls) -> dict:
        return MetadataFactory().to_dict()


class PodStatusFactory(factory.Factory):
    class Meta:
        model = PodStatus

    name = factory.LazyFunction(lambda: _short(fake.word().capitalize(), 200))
    phase = factory.LazyFunction(lambda: _short(random.choice(["Running", "Pending", "Failed", "Succeeded", "Unknown"]), 200))
    message = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=8), 1000))
    reason = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=4), 1000))

    @classmethod
    def to_dict(cls) -> dict:
        return PodStatusFactory().to_dict()


class ContainerFactory(factory.Factory):
    class Meta:
        model = Container

    type = factory.LazyFunction(lambda: _short(random.choice(["app", "sidecar", "init"]), 100))
    name = factory.LazyFunction(lambda: _short(f"{fake.word()}-{random.randint(1, 99999)}", 300))
    image = factory.LazyFunction(lambda: _short(f"{fake.word()}/{fake.word()}:{random.randint(1, 10)}.{random.randint(0, 9)}", 1000))
    imageId = factory.LazyFunction(lambda: _short(fake.sha1(), 300))
    ready = factory.LazyFunction(lambda: random.choice([True, False]))
    started = factory.LazyFunction(lambda: random.choice([True, False]))
    restartCount = factory.LazyFunction(lambda: random.randint(0, 20))
    startedAt = factory.LazyFunction(_now_iso)
    lastSync = factory.LazyFunction(_now_iso)
    metadata = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 3))])

    @classmethod
    def to_dict(cls) -> dict:
        return ContainerFactory().to_dict()


class PodFactory(factory.Factory):
    class Meta:
        model = Pod

    uid = factory.LazyFunction(lambda: _short(fake.uuid4(), 36))
    name = factory.LazyFunction(lambda: _short(f"{fake.word()}-{random.randint(1, 999999)}", 500))
    namespace = factory.LazyFunction(lambda: _short(random.choice(["default", "kube-system", "dev", "test", "prod"]), 100))
    hostname = factory.LazyFunction(lambda: _short(f"{fake.word()}-{random.randint(1, 999)}", 200))
    nodeName = factory.LazyFunction(lambda: _short(f"node-{random.randint(1, 999)}", 200))
    createdAt = factory.LazyFunction(_now_iso)
    deletedAt = factory.LazyFunction(lambda: _now_iso() if random.choice([True, False]) else None)  # default None
    lastSync = factory.LazyFunction(_now_iso)
    status = factory.LazyFunction(lambda: PodStatusFactory.to_dict())
    containers = factory.LazyFunction(lambda: [ContainerFactory.to_dict() for _ in range(random.randint(1, 3))])
    metadata = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 3))])
    annotations = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 2))])
    selectors = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 2))])
    labels = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 3))])

    @factory.lazy_attribute
    def project(self):
        return AthenaTaskSet.get_project()


    @classmethod
    def to_dict(cls, project_code: str | None = None) -> dict:
        pod: Pod = PodFactory()
        if project_code:
            pod.project = _short(project_code, 50)
        return pod.to_dict()

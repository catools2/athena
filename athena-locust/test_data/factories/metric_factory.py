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


class Action:
    def __init__(self, name: str, category: str, target: str, type: str, command: str, parameter: str | None = None):
        self.name = name
        self.category = category
        self.target = target
        self.type = type
        self.command = command
        self.parameter = parameter

    def to_dict(self) -> dict:
        return {
            "name": self.name,
            "category": self.category,
            "target": self.target,
            "type": self.type,
            "command": self.command,
            "parameter": self.parameter,
        }


class Metric:
    def __init__(self, duration: int, actionTime: str, environment: str, project: str, action: dict):
        self.duration = duration
        self.actionTime = actionTime
        self.environment = environment
        self.project = project
        self.action = action

    def to_dict(self) -> dict:
        return {
            "duration": self.duration,
            "actionTime": self.actionTime,
            "environment": self.environment,
            "project": self.project,
            "action": self.action,
        }


class ActionFactory(factory.Factory):
    class Meta:
        model = Action

    name = factory.LazyFunction(lambda: _short(fake.word().capitalize(), 200))
    category = factory.LazyFunction(lambda: _short(random.choice(["UI", "API", "DB", "Infra"]), 100))
    target = factory.LazyFunction(lambda: _short(fake.uri(), 500))
    type = factory.LazyFunction(lambda: _short(random.choice(["READ", "WRITE", "EXECUTE", "DELETE"]), 50))
    command = factory.LazyFunction(lambda: _short(random.choice(["GET", "POST", "PUT", "DELETE", "RUN"]), 50))
    parameter = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=3), 500))

    @classmethod
    def to_dict(cls) -> dict:
        obj: Action = ActionFactory()
        return obj.to_dict()


class MetricFactory(factory.Factory):
    class Meta:
        model = Metric

    duration = factory.LazyFunction(lambda: random.randint(1, 10_000))
    actionTime = factory.LazyFunction(_now_iso)
    action = factory.LazyFunction(ActionFactory.to_dict)

    @factory.lazy_attribute
    def project(self):
        return AthenaTaskSet.get_project_with_environments()

    @factory.lazy_attribute
    def environment(self):
        if not self.project:
            return None

        return AthenaTaskSet.get_environment(self.project)["code"]

    @classmethod
    def to_dict(cls) -> dict:
        obj: Metric = MetricFactory()
        return obj.to_dict()

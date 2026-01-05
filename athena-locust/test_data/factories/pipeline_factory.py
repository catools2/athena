import random
from datetime import datetime, timezone, timedelta

import factory
from faker import Faker

from tasks.athena_task_set import AthenaTaskSet

fake = Faker()


def _short(text: str, max_len: int) -> str:
    return (text or "")[:max_len]


def _now_iso() -> str:
    return datetime.now(timezone.utc).isoformat(timespec="milliseconds").replace("+00:00", "Z")


def _future_iso(minutes: int = 30) -> str:
    return (datetime.now(timezone.utc) + timedelta(minutes=minutes)).isoformat(timespec="milliseconds").replace("+00:00", "Z")


class Metadata:
    def __init__(self, name: str, value: str):
        self.name = name
        self.value = value

    def to_dict(self) -> dict:
        return {
            "name": self.name,
            "value": self.value,
        }


class PipelineExecution:
    def __init__(self, packageName: str, className: str, methodName: str, parameters: str | None,
                 startTime: str, endTime: str, testStartTime: str | None, testEndTime: str | None,
                 beforeClassStartTime: str | None, beforeClassEndTime: str | None,
                 beforeMethodStartTime: str | None, beforeMethodEndTime: str | None,
                 status: str, executor: str, pipelineId: int | None, metadata: list[dict]):
        self.packageName = packageName
        self.className = className
        self.methodName = methodName
        self.parameters = parameters
        self.startTime = startTime
        self.endTime = endTime
        self.testStartTime = testStartTime
        self.testEndTime = testEndTime
        self.beforeClassStartTime = beforeClassStartTime
        self.beforeClassEndTime = beforeClassEndTime
        self.beforeMethodStartTime = beforeMethodStartTime
        self.beforeMethodEndTime = beforeMethodEndTime
        self.status = status
        self.executor = executor
        self.pipelineId = pipelineId
        self.metadata = metadata

    def to_dict(self) -> dict:
        result = {
            "packageName": self.packageName,
            "className": self.className,
            "methodName": self.methodName,
            "startTime": self.startTime,
            "endTime": self.endTime,
            "status": self.status,
            "executor": self.executor,
            "metadata": self.metadata,
        }
        if self.parameters:
            result["parameters"] = self.parameters
        if self.testStartTime:
            result["testStartTime"] = self.testStartTime
        if self.testEndTime:
            result["testEndTime"] = self.testEndTime
        if self.beforeClassStartTime:
            result["beforeClassStartTime"] = self.beforeClassStartTime
        if self.beforeClassEndTime:
            result["beforeClassEndTime"] = self.beforeClassEndTime
        if self.beforeMethodStartTime:
            result["beforeMethodStartTime"] = self.beforeMethodStartTime
        if self.beforeMethodEndTime:
            result["beforeMethodEndTime"] = self.beforeMethodEndTime
        if self.pipelineId:
            result["pipelineId"] = self.pipelineId
        return result


class Pipeline:
    def __init__(self, project: str, name: str, description: str, number: str,
                 version: str, environment: str, startDate: str, endDate: str | None, metadata: list[dict]):
        self.project = project
        self.name = name
        self.description = description
        self.number = number
        self.version = version
        self.environment = environment
        self.startDate = startDate
        self.endDate = endDate
        self.metadata = metadata

    def to_dict(self) -> dict:
        result = {
            "project": self.project,
            "name": self.name,
            "description": self.description,
            "number": self.number,
            "version": self.version,
            "environment": self.environment,
            "startDate": self.startDate,
            "metadata": self.metadata,
        }
        if self.endDate:
            result["endDate"] = self.endDate
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


class PipelineExecutionFactory(factory.Factory):
    class Meta:
        model = PipelineExecution

    packageName = factory.LazyFunction(lambda: _short(f"com.{fake.word()}.{fake.word()}", 300))
    className = factory.LazyFunction(lambda: _short(f"{fake.word().capitalize()}Test", 300))
    methodName = factory.LazyFunction(lambda: _short(f"test{fake.word().capitalize()}", 300))
    parameters = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=3), 2000) if random.choice([True, False]) else None)
    startTime = factory.LazyFunction(_now_iso)
    endTime = factory.LazyFunction(lambda: _future_iso(random.randint(1, 10)))
    testStartTime = factory.LazyFunction(lambda: _now_iso() if random.choice([True, False]) else None)
    testEndTime = factory.LazyFunction(lambda: _future_iso(random.randint(1, 5)) if random.choice([True, False]) else None)
    beforeClassStartTime = factory.LazyFunction(lambda: _now_iso() if random.choice([True, False]) else None)
    beforeClassEndTime = factory.LazyFunction(lambda: _future_iso(1) if random.choice([True, False]) else None)
    beforeMethodStartTime = factory.LazyFunction(lambda: _now_iso() if random.choice([True, False]) else None)
    beforeMethodEndTime = factory.LazyFunction(lambda: _future_iso(1) if random.choice([True, False]) else None)
    pipelineId = None
    metadata = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 2))])

    @factory.lazy_attribute
    def executor(self):
        return AthenaTaskSet.get_username()

    @factory.lazy_attribute
    def status(self):
        from tasks.pipeline.pipeline_task_set import PipelineTaskSet
        return PipelineTaskSet.get_status()["name"]

    @classmethod
    def to_dict(cls, pipeline_id: int | None = None) -> dict:
        obj: PipelineExecution = PipelineExecutionFactory()
        if pipeline_id:
            obj.pipelineId = pipeline_id
        return obj.to_dict()


class PipelineFactory(factory.Factory):
    class Meta:
        model = Pipeline

    name = factory.LazyFunction(lambda: _short(random.choice(["build", "deploy", "test", "package", "release"]), 100))
    description = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=6), 300))
    number = factory.LazyFunction(lambda: _short(str(random.randint(1, 999999)), 100))
    startDate = factory.LazyFunction(_now_iso)
    endDate = factory.LazyFunction(lambda: _future_iso(random.randint(10, 60)) if random.choice([True, False]) else None)
    metadata = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 3))])

    @factory.lazy_attribute
    def project(self):
        return AthenaTaskSet.get_project_with_environments()

    @factory.lazy_attribute
    def version(self):
        if not self.project:
            return None
        return AthenaTaskSet.get_version(self.project)["code"]

    @factory.lazy_attribute
    def environment(self):
        if not self.project:
            return None
        return AthenaTaskSet.get_environment(self.project)["code"]

    @classmethod
    def to_dict(cls) -> dict:
        obj: Pipeline = PipelineFactory()
        return obj.to_dict()


class PipelineExecutionStatus:
    def __init__(self, name: str, id: int | None = None):
        self.id = id
        self.name = name

    def to_dict(self) -> dict:
        d = {"name": self.name}
        if self.id is not None:
            d["id"] = self.id
        return d

class PipelineExecutionStatusFactory(factory.Factory):
    class Meta:
        model = PipelineExecutionStatus

    name = factory.LazyFunction(lambda: _short(random.choice([
        "QUEUED", "RUNNING", "PASSED", "FAILED", "SKIPPED", "CANCELLED"
    ]), 100))
    id = None

    @classmethod
    def to_dict(cls, id: int | None = None) -> dict:
        obj: PipelineExecutionStatus = PipelineExecutionStatusFactory()
        if id is not None:
            obj.id = id
        return obj.to_dict()


class PipelineScenarioExecution:
    def __init__(self, feature: str, scenario: str, parameters: str, startTime: str, endTime: str,
                 beforeScenarioStartTime: str | None, beforeScenarioEndTime: str | None,
                 status: str, executor: str, pipelineId: int, metadata: list[dict], id: int | None = None):
        self.id = id
        self.feature = feature
        self.scenario = scenario
        self.parameters = parameters
        self.startTime = startTime
        self.endTime = endTime
        self.beforeScenarioStartTime = beforeScenarioStartTime
        self.beforeScenarioEndTime = beforeScenarioEndTime
        self.status = status
        self.executor = executor
        self.pipelineId = pipelineId
        self.metadata = metadata

    def to_dict(self) -> dict:
        d = {
            "feature": self.feature,
            "scenario": self.scenario,
            "parameters": self.parameters,
            "startTime": self.startTime,
            "endTime": self.endTime,
            "status": self.status,
            "executor": self.executor,
            "pipelineId": self.pipelineId,
            "metadata": self.metadata,
        }
        if self.id is not None:
            d["id"] = self.id
        if self.beforeScenarioStartTime:
            d["beforeScenarioStartTime"] = self.beforeScenarioStartTime
        if self.beforeScenarioEndTime:
            d["beforeScenarioEndTime"] = self.beforeScenarioEndTime
        return d

class PipelineScenarioExecutionFactory(factory.Factory):
    class Meta:
        model = PipelineScenarioExecution

    feature = factory.LazyFunction(lambda: _short(f"Feature {fake.word().capitalize()}", 1000))
    scenario = factory.LazyFunction(lambda: _short(f"Scenario {fake.word().capitalize()}", 500))
    parameters = factory.LazyFunction(lambda: _short(fake.sentence(nb_words=6), 2000))
    startTime = factory.LazyFunction(_now_iso)
    endTime = factory.LazyFunction(lambda: _future_iso(random.randint(1, 10)))
    beforeScenarioStartTime = factory.LazyFunction(lambda: _now_iso() if random.choice([True, False]) else None)
    beforeScenarioEndTime = factory.LazyFunction(lambda: _future_iso(1) if random.choice([True, False]) else None)
    pipelineId = 1  # will be set in to_dict
    metadata = factory.LazyFunction(lambda: [MetadataFactory.to_dict() for _ in range(random.randint(0, 2))])
    id = None

    @factory.lazy_attribute
    def executor(self):
        return AthenaTaskSet.get_username()

    @factory.lazy_attribute
    def status(self):
        from tasks.pipeline.pipeline_task_set import PipelineTaskSet
        return PipelineTaskSet.get_status()["name"]

    @classmethod
    def to_dict(cls, pipeline_id: int, id: int | None = None) -> dict:
        obj: PipelineScenarioExecution = PipelineScenarioExecutionFactory()
        obj.pipelineId = pipeline_id
        if id is not None:
            obj.id = id
        return obj.to_dict()

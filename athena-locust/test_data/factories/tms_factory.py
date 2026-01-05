from random import Random

import factory
from faker import Faker

import tasks
from tasks.athena_task_set import AthenaTaskSet
from utils.data_utils import to_long_format

fake = Faker()
random = Random()


class ItemType:
    def __init__(self, code: str, name: str):
        self.code = code
        self.name = name

    def to_dict(self) -> dict:
        return {"code": self.code, "name": self.name}


class Status:
    def __init__(self, code: str, name: str):
        self.code = code
        self.name = name

    def to_dict(self) -> dict:
        return {"code": self.code, "name": self.name}


class Priority:
    def __init__(self, code: str, name: str):
        self.code = code
        self.name = name

    def to_dict(self) -> dict:
        return {"code": self.code, "name": self.name}


class TestCycle:
    def __init__(self, code: str, name: str, project: str, startDate: int, endDate: int, version: str,
                 testExecutions: list):
        self.code = code
        self.name = name
        self.project = project
        self.startDate = startDate
        self.endDate = endDate
        self.version = version
        self.testExecutions = testExecutions

    def to_dict(self) -> dict:
        return {
            "code": self.code,
            "name": self.name,
            "project": self.project,
            "startDate": self.startDate,
            "endDate": self.endDate,
            "version": self.version,
            "testExecutions": self.testExecutions,
        }


class ItemMetadata:
    def __init__(self, name: str, value: str):
        self.name = name
        self.value = value

    def to_dict(self) -> dict:
        return {"name": self.name, "value": self.value}


class Item:
    def __init__(
            self,
            code: str,
            name: str,
            createdOn: int,
            createdBy: int,
            updatedOn: int,
            updatedBy: int,
            type: str,
            status: str,
            priority: str,
            project: int,
            versions: list[int] | None = None,
            metadata: list[dict] | None = None,
            statusTransitions: list[dict] | None = None,
    ):
        self.code = code
        self.name = name
        self.createdOn = createdOn
        self.createdBy = createdBy
        self.updatedOn = updatedOn
        self.updatedBy = updatedBy
        self.type = type
        self.status = status
        self.priority = priority
        self.project = project
        self.versions = versions or []
        self.metadata = metadata or []
        self.statusTransitions = statusTransitions or []

    def to_dict(self) -> dict:
        return {
            "code": self.code,
            "name": self.name,
            "createdOn": self.createdOn,
            "createdBy": self.createdBy,
            "updatedOn": self.updatedOn,
            "updatedBy": self.updatedBy,
            "type": self.type,
            "status": self.status,
            "priority": self.priority,
            "project": self.project,
            "versions": self.versions,
            "metadata": self.metadata,
            "statusTransitions": self.statusTransitions
        }


class StatusTransition:
    def __init__(self, _from: str, to: str, author: int, occurred: int):
        self._from = _from
        self.to = to
        self.author = author
        self.occurred = occurred

    def to_dict(self) -> dict:
        return {
            "from": self._from,
            "to": self.to,
            "author": self.author,
            "occurred": self.occurred,
        }


class TestExecution:
    def __init__(self, createdOn: int, executedOn: str, item: str, status: str, executor: str):
        self.createdOn = createdOn
        self.executedOn = executedOn
        self.item = item
        self.status = status
        self.executor = executor

    def to_dict(self) -> dict:
        return {
            "createdOn": self.createdOn,
            "executedOn": self.executedOn,
            "item": self.item,
            "status": self.status,
            "executor": self.executor,
        }


class ItemTypeFactory(factory.Factory):
    class Meta:
        model = ItemType

    code = factory.LazyFunction(lambda: f"IT{random.randint(1, 100_000_000)}")
    name = factory.LazyFunction(lambda: f"{fake.word().capitalize()} {random.randint(1, 1_000_000)}")

    @classmethod
    def to_dict(cls) -> dict:
        obj: ItemType = ItemTypeFactory()
        return obj.to_dict()


class StatusFactory(factory.Factory):
    class Meta:
        model = Status

    code = factory.LazyFunction(lambda: f"ST{random.randint(1, 100_000_000)}")
    name = factory.LazyFunction(lambda: f"{fake.word().capitalize()} {random.randint(1, 1_000_000)}")

    @classmethod
    def to_dict(cls) -> dict:
        obj: Status = StatusFactory()
        return obj.to_dict()


class PriorityFactory(factory.Factory):
    class Meta:
        model = Priority

    code = factory.LazyFunction(lambda: f"PR{random.randint(1, 100_000_000)}")
    name = factory.LazyFunction(lambda: f"{fake.word().capitalize()} {random.randint(1, 1_000_000)}")

    @classmethod
    def to_dict(cls) -> dict:
        obj: Priority = PriorityFactory()
        return obj.to_dict()


class TestCycleFactory(factory.Factory):
    class Meta:
        model = TestCycle

    code = factory.LazyFunction(lambda: f"TC{random.randint(1, 100_000_000)}")
    name = factory.LazyFunction(lambda: f"{fake.word().capitalize()} {random.randint(1, 1_000_000)}")
    testExecutions = factory.LazyFunction(
        lambda: [TestExecutionFactory.to_dict() for _ in range(random.randint(0, 3000))])

    @factory.lazy_attribute
    def startDate(self):
        return to_long_format(fake.date_time())

    @factory.lazy_attribute
    def endDate(self):
        return to_long_format(fake.date_time())

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
        obj: TestCycle = TestCycleFactory()
        return obj.to_dict()


class ItemMetadataFactory(factory.Factory):
    class Meta:
        model = ItemMetadata

    name = factory.LazyFunction(lambda: f"N_{random.randint(1, 10_000)}")
    value = factory.LazyFunction(lambda: fake.sentence(nb_words=4))

    @classmethod
    def to_dict(cls) -> dict:
        obj: ItemMetadata = ItemMetadataFactory()
        return obj.to_dict()


class StatusTransitionFactory(factory.Factory):
    class Meta:
        model = StatusTransition

    occurred = factory.LazyFunction(lambda: to_long_format(fake.date_time()))

    @factory.lazy_attribute
    def _from(self):
        return tasks.tms.tms_task_set.TmsTaskSet.get_status()["code"]

    @factory.lazy_attribute
    def to(self):
        return tasks.tms.tms_task_set.TmsTaskSet.get_status()["code"]

    @factory.lazy_attribute
    def author(self):
        return AthenaTaskSet.get_username()

    @classmethod
    def to_dict(cls) -> dict:
        obj: StatusTransition = StatusTransitionFactory()
        return obj.to_dict()


class ItemFactory(factory.Factory):
    class Meta:
        model = Item

    def __init__(self):
        super().__init__()

    code = factory.LazyFunction(lambda: f"I{random.randint(1, 100_000_000)}")
    name = factory.LazyFunction(lambda: f"{fake.word().capitalize()} {random.randint(1, 1_000_000)}")
    createdOn = factory.LazyFunction(lambda: to_long_format(fake.date_time()))
    updatedOn = factory.LazyFunction(lambda: to_long_format(fake.date_time()))
    metadata = factory.LazyFunction(lambda: [ItemMetadataFactory.to_dict() for _ in range(random.randint(0, 30))])
    statusTransitions = factory.LazyFunction(
        lambda: [StatusTransitionFactory.to_dict() for _ in range(random.randint(0, 30))])

    @factory.lazy_attribute
    def createdBy(self):
        return AthenaTaskSet.get_username()

    @factory.lazy_attribute
    def updatedBy(self):
        return AthenaTaskSet.get_username()

    @factory.lazy_attribute
    def type(self):
        return tasks.tms.tms_task_set.TmsTaskSet.get_item_type()["code"]

    @factory.lazy_attribute
    def status(self):
        return tasks.tms.tms_task_set.TmsTaskSet.get_status()["code"]

    @factory.lazy_attribute
    def priority(self):
        return tasks.tms.tms_task_set.TmsTaskSet.get_priority()["code"]

    @factory.lazy_attribute
    def project(self):
        return AthenaTaskSet.get_project_with_versions()

    @factory.lazy_attribute
    def versions(self):
        if not self.project:
            return []

        version = AthenaTaskSet.get_version(self.project)
        if not version:
            return []

        return list({AthenaTaskSet.get_version(self.project)["code"] for _ in range(random.randint(0, 10))})

    @classmethod
    def to_dict(cls) -> dict:
        obj: Item = ItemFactory()
        return obj.to_dict()


class TestExecutionFactory(factory.Factory):
    class Meta:
        model = TestExecution

    createdOn = factory.LazyFunction(lambda: to_long_format(fake.date_time()))
    executedOn = factory.LazyFunction(lambda: to_long_format(fake.date_time()))

    @factory.lazy_attribute
    def item(self):
        return tasks.tms.tms_task_set.TmsTaskSet.get_item()["code"]

    @factory.lazy_attribute
    def status(self):
        return tasks.tms.tms_task_set.TmsTaskSet.get_status()["code"]

    @factory.lazy_attribute
    def executor(self):
        return AthenaTaskSet.get_username()

    @classmethod
    def to_dict(cls) -> dict:
        obj: TestExecution = TestExecutionFactory()
        return obj.to_dict()

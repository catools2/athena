import factory
import random
import time
from faker import Faker

fake = Faker()


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


class SyncInfo:
    def __init__(self, action: str, component: str, startTime: int, endTime: int, projectId: int):
        self.action = action
        self.component = component
        self.startTime = startTime
        self.endTime = endTime
        self.projectId = projectId

    def to_dict(self) -> dict:
        return {
            "action": self.action,
            "component": self.component,
            "startTime": self.startTime,
            "endTime": self.endTime,
            "projectId": self.projectId,
        }


class TestCycle:
    def __init__(self, code: str, name: str, startDate: int, endDate: int, versionId: int):
        self.code = code
        self.name = name
        self.startDate = startDate
        self.endDate = endDate
        self.versionId = versionId

    def to_dict(self) -> dict:
        return {
            "code": self.code,
            "name": self.name,
            "startDate": self.startDate,
            "endDate": self.endDate,
            "versionId": self.versionId,
        }


class ItemMetadata:
    def __init__(self, key: str, value: str):
        self.key = key
        self.value = value

    def to_dict(self) -> dict:
        return {"key": self.key, "value": self.value}


class Item:
    def __init__(
        self,
        code: str,
        name: str,
        createdOn: int,
        createdBy: int,
        typeCode: str,
        statusCode: str,
        priorityCode: str,
        projectId: int,
        versionIds: list[int],
        metadata: list[dict] | None = None,
        statusTransitions: list[dict] | None = None,
    ):
        self.code = code
        self.name = name
        self.createdOn = createdOn
        self.createdBy = createdBy
        self.typeCode = typeCode
        self.statusCode = statusCode
        self.priorityCode = priorityCode
        self.projectId = projectId
        self.versionIds = versionIds
        self.metadata = metadata or []
        self.statusTransitions = statusTransitions or []

    def to_dict(self) -> dict:
        return {
            "code": self.code,
            "name": self.name,
            "createdOn": self.createdOn,
            "createdBy": self.createdBy,
            "typeCode": self.typeCode,
            "statusCode": self.statusCode,
            "priorityCode": self.priorityCode,
            "projectId": self.projectId,
            "versionIds": self.versionIds,
            "metadata": self.metadata,
            "statusTransitions": self.statusTransitions,
        }


class StatusTransition:
    def __init__(self, occurred: int, fromStatusCode: str, toStatusCode: str, itemCode: str, authorId: int):
        self.occurred = occurred
        self.fromStatusCode = fromStatusCode
        self.toStatusCode = toStatusCode
        self.itemCode = itemCode
        self.authorId = authorId

    def to_dict(self) -> dict:
        return {
            "occurred": self.occurred,
            "fromStatusCode": self.fromStatusCode,
            "toStatusCode": self.toStatusCode,
            "itemCode": self.itemCode,
            "authorId": self.authorId,
        }


class TestExecution:
    def __init__(self, createdOn: int, cycleCode: str, itemCode: str, statusCode: str, executorId: int):
        self.createdOn = createdOn
        self.cycleCode = cycleCode
        self.itemCode = itemCode
        self.statusCode = statusCode
        self.executorId = executorId

    def to_dict(self) -> dict:
        return {
            "createdOn": self.createdOn,
            "cycleCode": self.cycleCode,
            "itemCode": self.itemCode,
            "statusCode": self.statusCode,
            "executorId": self.executorId,
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


class SyncInfoFactory(factory.Factory):
    class Meta:
        model = SyncInfo

    action = factory.LazyFunction(lambda: f"ACTION_{random.randint(1, 1_000_000)}")
    component = factory.LazyFunction(lambda: f"COMP_{random.randint(1, 1_000_000)}")
    startTime = factory.LazyFunction(lambda: int(time.time() * 1000))
    endTime = factory.LazyFunction(lambda: int(time.time() * 1000) + 60_000)
    projectId = 1

    @classmethod
    def to_dict(cls) -> dict:
        obj: SyncInfo = SyncInfoFactory()
        return obj.to_dict()


class TestCycleFactory(factory.Factory):
    class Meta:
        model = TestCycle

    code = factory.LazyFunction(lambda: f"TC{random.randint(1, 100_000_000)}")
    name = factory.LazyFunction(lambda: f"{fake.word().capitalize()} {random.randint(1, 1_000_000)}")
    startDate = factory.LazyFunction(lambda: int(time.time() * 1000))
    endDate = factory.LazyFunction(lambda: int(time.time() * 1000) + 24 * 60 * 60 * 1000)
    versionId = 1

    @classmethod
    def to_dict(cls) -> dict:
        obj: TestCycle = TestCycleFactory()
        return obj.to_dict()


class ItemMetadataFactory(factory.Factory):
    class Meta:
        model = ItemMetadata

    key = factory.LazyFunction(lambda: f"KEY_{random.randint(1, 1_000_000)}")
    value = factory.LazyFunction(lambda: fake.sentence(nb_words=4))

    @classmethod
    def to_dict(cls) -> dict:
        obj: ItemMetadata = ItemMetadataFactory()
        return obj.to_dict()


class StatusTransitionFactory(factory.Factory):
    class Meta:
        model = StatusTransition

    occurred = factory.LazyFunction(lambda: int(time.time() * 1000))
    fromStatusCode = factory.LazyFunction(lambda: StatusFactory().code)
    toStatusCode = factory.LazyFunction(lambda: StatusFactory().code)
    itemCode = factory.LazyFunction(lambda: f"ITM{random.randint(1, 100_000_000)}")
    authorId = factory.LazyFunction(lambda: random.randint(1, 10_000))

    @classmethod
    def to_dict(cls) -> dict:
        obj: StatusTransition = StatusTransitionFactory()
        return obj.to_dict()


class ItemFactory(factory.Factory):
    class Meta:
        model = Item

    code = factory.LazyFunction(lambda: f"I{random.randint(1, 100_000_000)}")
    name = factory.LazyFunction(lambda: f"{fake.word().capitalize()} {random.randint(1, 1_000_000)}")
    createdOn = factory.LazyFunction(lambda: int(time.time() * 1000))
    createdBy = factory.LazyFunction(lambda: random.randint(1, 10_000))
    typeCode = factory.LazyFunction(lambda: ItemTypeFactory().code)
    statusCode = factory.LazyFunction(lambda: StatusFactory().code)
    priorityCode = factory.LazyFunction(lambda: PriorityFactory().code)
    projectId = 1
    versionIds = factory.LazyFunction(lambda: [1])
    metadata = factory.LazyFunction(lambda: [ItemMetadataFactory.to_dict()])
    statusTransitions = factory.LazyFunction(lambda: [StatusTransitionFactory.to_dict()])

    @classmethod
    def to_dict(cls) -> dict:
        obj: Item = ItemFactory()
        return obj.to_dict()


class TestExecutionFactory(factory.Factory):
    class Meta:
        model = TestExecution

    createdOn = factory.LazyFunction(lambda: int(time.time() * 1000))
    cycleCode = factory.LazyFunction(lambda: TestCycleFactory().code)
    itemCode = factory.LazyFunction(lambda: ItemFactory().code)
    statusCode = factory.LazyFunction(lambda: StatusFactory().code)
    executorId = factory.LazyFunction(lambda: random.randint(1, 10_000))

    @classmethod
    def to_dict(cls) -> dict:
        obj: TestExecution = TestExecutionFactory()
        return obj.to_dict()
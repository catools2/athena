import random

import factory
from faker import Faker

from tasks.athena_task_set import AthenaTaskSet
from utils.data_utils import to_long_format

fake = Faker()


class Repository:
    def __init__(self, name: str, url: str):
        self.name = name
        self.url = url

    def to_dict(self):
        return {"name": self.name, "url": self.url}


class Commit:
    def __init__(self, hash: str, shortMessage: str, repository: str, author: str, committer: str,
                 commitTime: int, parentCount: int, message: str = None):
        self.hash = hash
        self.shortMessage = shortMessage
        self.repository = repository
        self.author = author
        self.committer = committer
        self.commitTime = commitTime
        self.parentCount = parentCount
        self.message = message or shortMessage

    def to_dict(self):
        return {
            "hash": self.hash,
            "shortMessage": self.shortMessage,
            "repository": self.repository,
            "author": self.author,
            "committer": self.committer,
            "commitTime": self.commitTime,
            "parentCount": self.parentCount,
            "message": self.message
        }


class RepositoryFactory(factory.Factory):
    class Meta:
        model = Repository

    name = factory.LazyFunction(lambda: f"repo-{fake.word()}-{random.randint(1000, 99999)}")
    url = factory.LazyFunction(lambda: f"https://github.com/{fake.user_name()}/{fake.word()}.git")

    @classmethod
    def to_dict(cls):
        obj = RepositoryFactory()
        return obj.to_dict()


class CommitFactory(factory.Factory):
    class Meta:
        model = Commit

    hash = factory.LazyFunction(lambda: fake.sha1())
    shortMessage = factory.LazyFunction(lambda: fake.sentence(nb_words=4))
    repository = factory.LazyFunction(lambda: f"repo-{fake.word()}-{random.randint(1000, 99999)}")
    commitTime = factory.LazyFunction(lambda: to_long_format(fake.date_time()))
    parentCount = factory.LazyFunction(lambda: random.randint(0, 3))
    message = factory.LazyFunction(lambda: fake.sentence(nb_words=10))

    @factory.lazy_attribute
    def author(self):
        return AthenaTaskSet.get_username()

    @factory.lazy_attribute
    def committer(self):
        return AthenaTaskSet.get_username()

    @classmethod
    def to_dict(cls, repository: str = None):
        obj = CommitFactory()
        if repository is not None:
            obj.repository = repository
        return obj.to_dict()


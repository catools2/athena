import random

import factory
from faker import Faker

fake = Faker()


def _short(text: str, max_len: int) -> str:
    return text[:max_len]


class Project:
    def __init__(self, code: str, name: str):
        self.code = code
        self.name = name

    def to_dict(self):
        return {"code": self.code, "name": self.name}


class Environment:
    def __init__(self, code: str, name: str, project: str):
        self.code = code
        self.name = name
        self.project = project

    def to_dict(self):
        return {"code": self.code, "name": self.name, "project": self.project}


class Version:
    def __init__(self, code: str, name: str, project: str):
        self.code = code
        self.name = name
        self.project = project

    def to_dict(self):
        return {"code": self.code, "name": self.name, "project": self.project}


class Alias:
    def __init__(self, alias: str):
        self.alias = alias

    def to_dict(self):
        return {"alias": self.alias}


class User:
    def __init__(self, username: str, aliases: list[Alias]):
        self.username = username
        self.aliases = aliases

    def to_dict(self):
        return {
            "username": self.username,
            "aliases": [a.to_dict() for a in self.aliases],
        }


class ProjectFactory(factory.Factory):
    class Meta:
        model = Project

    code = factory.LazyFunction(lambda: _short(f"P{random.randint(1, 9_999_999):07d}", 10))
    name = factory.LazyFunction(lambda: _short(fake.company() + f" {random.randint(1, 9999)}", 50))

    @classmethod
    def to_dict(cls):
        proj: Project = ProjectFactory()  # build a User instance
        return proj.to_dict()


class VersionFactory(factory.Factory):
    class Meta:
        model = Version

    code = factory.LazyFunction(lambda: _short(f"V{random.randint(1, 9_999_999):07d}", 10))
    name = factory.LazyFunction(lambda: _short(fake.bs().replace("-", " "), 50))
    project = None

    @classmethod
    def to_dict(cls, project):
        version: Version = VersionFactory()  # build a User instance
        version.project = project
        return version.to_dict()


class EnvironmentFactory(factory.Factory):
    class Meta:
        model = Environment

    code = factory.LazyFunction(lambda: _short(f"E{random.randint(1, 9_999_999):07d}", 10))
    name = factory.LazyFunction(lambda: _short(fake.color_name() + f" {random.randint(1, 9999)}", 50))
    project = None

    @classmethod
    def to_dict(cls, project):
        environment: Environment = EnvironmentFactory()  # build a User instance
        environment.project = project
        return environment.to_dict()


class AliasFactory(factory.Factory):
    class Meta:
        model = Alias

    alias = factory.LazyFunction(lambda: _short(f"{fake.user_name()}.{random.randint(1, 10_000)}", 200))


class UserFactory(factory.Factory):
    class Meta:
        model = User

    username = factory.LazyFunction(lambda: _short(f"{fake.user_name()}.{random.randint(1, 10_000)}", 150))
    aliases = factory.LazyFunction(lambda: list({AliasFactory() for _ in range(random.randint(1, 3))}))

    @classmethod
    def to_dict(cls):
        user: User = UserFactory()  # build a User instance
        return user.to_dict()

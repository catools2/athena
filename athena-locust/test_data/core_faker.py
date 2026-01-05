from test_data.factories.core_factory import (
    UserFactory,
    ProjectFactory,
    VersionFactory,
    EnvironmentFactory
)


def build_user() -> dict:
    return UserFactory.to_dict()


def build_username():
    return UserFactory().username


# New: quick helpers to get only the codes
# Useful when you just need an identifier without a full object

def build_project_code() -> str:
    return ProjectFactory().code


def build_version_code(project_code: str) -> str:
    return VersionFactory.to_dict(project_code)["code"]


def build_environment_code(project_code: str) -> str:
    return EnvironmentFactory.to_dict(project_code)["code"]


def build_project(id: int = None) -> dict:
    to_dict = ProjectFactory.to_dict()
    if id is not None:
        to_dict["id"] = id
    return to_dict


def build_project_name():
    return ProjectFactory().name


def build_version(project_code: str, id: int = None) -> dict:
    to_dict = VersionFactory.to_dict(project_code)
    if id is not None:
        to_dict["id"] = id
    return to_dict


def build_version_name():
    return VersionFactory().name


def build_environment(project_code: str, id: int = None) -> dict:
    to_dict = EnvironmentFactory.to_dict(project_code)
    if id is not None:
        to_dict["id"] = id
    return to_dict


def build_environment_name():
    return EnvironmentFactory().name

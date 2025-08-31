from test_data.factories.core_factory import UserFactory, ProjectFactory, VersionFactory, EnvironmentFactory, \
    User, Project, Version, Environment

def get_user(id: int = None) -> dict:
    to_dict = UserFactory.to_dict()
    if id is not None:
        to_dict["id"] = id
    return to_dict

def get_username():
    return UserFactory().username

def get_project(id: int = None) -> dict:
    to_dict = ProjectFactory.to_dict()
    if id is not None:
        to_dict["id"] = id
    return to_dict

def get_project_name():
    return ProjectFactory().name

def get_version(project_code: str, id: int = None) -> dict:
    to_dict = VersionFactory.to_dict(project_code)
    if id is not None:
        to_dict["id"] = id
    return to_dict

def get_version_name():
    return VersionFactory().name

def get_environment(project_code: str, id: int = None) -> dict:
    to_dict = EnvironmentFactory.to_dict(project_code)
    if id is not None:
        to_dict["id"] = id
    return to_dict

def get_environment_name():
    return VersionFactory().name

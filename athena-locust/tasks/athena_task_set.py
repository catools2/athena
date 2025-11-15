from random import Random

from locust import TaskSet

from test_data.core_faker import build_project, build_version, build_environment, build_user
from utils.logger_utils import get_logger

random = Random()


class AthenaTaskSet(TaskSet):
    logger = get_logger('AthenaTaskSet')

    projects: list = []
    versions: list = []
    environments: list = []
    users: list = []

    def on_start(self) -> None:
        self.add_project()
        self.add_version()
        self.add_environment()
        self.add_user()

    # ---------------------------------------------------------------------
    # Project
    def add_project(self):
        project = build_project()
        with self.client.post("/core/project", name="AddProject", json=project) as response:
            if response.status_code == 201:
                project["id"] = int(response.headers.get('entity_id'))
                AthenaTaskSet.projects.append(project)

    @staticmethod
    def get_project():
        return AthenaTaskSet.projects[random.randint(0, len(AthenaTaskSet.projects) - 1)]

    # ---------------------------------------------------------------------
    # Version
    def add_version(self):
        version = build_version(AthenaTaskSet.get_project()["code"])
        with self.client.post("/core/version", name="AddVersion", json=version) as response:
            if response.status_code == 201:
                version["id"] = int(response.headers.get('entity_id'))
                AthenaTaskSet.versions.append(version)

    @staticmethod
    def get_version():
        return AthenaTaskSet.versions[random.randint(0, len(AthenaTaskSet.versions) - 1)]

    # ---------------------------------------------------------------------
    # Environment
    def add_environment(self):
        environment = build_environment(AthenaTaskSet.get_project()["code"])
        with self.client.post("/core/environment", name="AddEnvironment", json=environment) as response:
            if response.status_code == 201:
                environment["id"] = int(response.headers.get('entity_id'))
                AthenaTaskSet.environments.append(environment)

    @staticmethod
    def get_environment():
        return AthenaTaskSet.environments[random.randint(0, len(AthenaTaskSet.environments) - 1)]

    # ---------------------------------------------------------------------
    # User
    def add_user(self):
        user = build_user()
        with self.client.post("/core/user", name="AddUser", json=user) as response:
            if response.status_code == 201:
                user["id"] = int(response.headers.get('entity_id'))
                AthenaTaskSet.users.append(user)

    @staticmethod
    def get_user():
        return AthenaTaskSet.users[random.randint(0, len(AthenaTaskSet.users) - 1)]

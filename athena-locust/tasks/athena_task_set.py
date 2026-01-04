import random

from locust import TaskSet

from test_data.core_faker import build_project, build_version, build_environment, build_user
from utils.logger_utils import get_logger


class AthenaTaskSet(TaskSet):
    logger = get_logger('AthenaTaskSet')

    # Shared state across all threads/users
    projects: list = []
    versions: dict = {}
    environments: list = {}
    users: list = []

    projects_to_update: list = []
    versions_to_update: dict = {}
    environments_to_update: list = {}
    users_to_update: list = []

    # ---------------------------------------------------------------------
    # Project
    def add_project(self):
        project = build_project()
        with self.client.post("/core/project", name="AddProject", json=project) as response:
            if response.status_code == 201:
                project["id"] = int(response.headers.get('entity_id'))
                if len(AthenaTaskSet.projects) == 0 or random.choice([True, False]):
                    AthenaTaskSet.projects.append(project)
                else:
                    AthenaTaskSet.projects_to_update.append(project)

    @staticmethod
    def get_project():
        if len(AthenaTaskSet.projects) == 0:
            return None
        return random.choice(AthenaTaskSet.projects)

    @staticmethod
    def get_project_to_update():
        if len(AthenaTaskSet.projects_to_update) == 0:
            return None
        return random.choice(AthenaTaskSet.projects_to_update)

    # ---------------------------------------------------------------------
    # Version
    def add_version(self, project_code:str = None):
        if not project_code:
            proj = AthenaTaskSet.get_project()
            if not proj:
                self.add_project()
                proj = AthenaTaskSet.get_project()

            project_code = proj["code"]

            if not project_code:
                return

        version = build_version(project_code)
        with self.client.post("/core/version", name="AddVersion", json=version) as response:
            if response.status_code == 201:
                version["id"] = int(response.headers.get('entity_id'))
                if len(AthenaTaskSet.versions) == 0 or random.choice([True, False]):
                    if project_code not in AthenaTaskSet.versions:
                        AthenaTaskSet.versions[project_code] = []
                    AthenaTaskSet.versions[project_code].append(version)
                else:
                    if project_code not in AthenaTaskSet.versions_to_update:
                        AthenaTaskSet.versions_to_update[project_code] = []
                    AthenaTaskSet.versions_to_update[project_code].append(version)

    @staticmethod
    def get_version(project_code=None):
        if len(AthenaTaskSet.versions) == 0:
            return None

        if not project_code:
            return random.choice(random.choice(list(AthenaTaskSet.versions.values())))

        versions = AthenaTaskSet.versions.get(project_code)
        if not versions:
            return None
        return random.choice(versions)

    @staticmethod
    def get_version_to_update(project_code=None):
        if not project_code:
            if len(AthenaTaskSet.versions_to_update) == 0:
                return None

            versions = random.choice(list(AthenaTaskSet.versions_to_update.values()))
            if not versions:
                return None
            return random.choice(versions)

        versions = AthenaTaskSet.versions_to_update.get(project_code)
        if not versions:
            return None
        return random.choice(versions)

    @staticmethod
    def get_project_with_versions():
        if len(AthenaTaskSet.versions) == 0:
            return None

        return random.choice(list(AthenaTaskSet.versions.keys()))

    @staticmethod
    def get_project_with_environments():
        if len(AthenaTaskSet.environments) == 0:
            return None

        return random.choice(list(AthenaTaskSet.environments.keys()))

    # ---------------------------------------------------------------------
    # Environment
    def add_environment(self):
        project_code = AthenaTaskSet.get_project_with_versions()
        if not project_code:
            self.add_version()
            project_code = AthenaTaskSet.get_project_with_versions()

        environment = build_environment(project_code)
        with self.client.post("/core/environment", name="AddEnvironment", json=environment) as response:
            if response.status_code == 201:
                environment["id"] = int(response.headers.get('entity_id'))

                if len(AthenaTaskSet.environments) == 0 or random.choice([True, False]):
                    if project_code not in AthenaTaskSet.environments:
                        AthenaTaskSet.environments[project_code] = []
                    AthenaTaskSet.environments[project_code].append(environment)
                else:
                    if project_code not in AthenaTaskSet.environments_to_update:
                        AthenaTaskSet.environments_to_update[project_code] = []
                    AthenaTaskSet.environments_to_update[project_code].append(environment)

    @staticmethod
    def get_environment(project_code=None):
        if not project_code:
            if len(AthenaTaskSet.environments) == 0:
                return None

            environments = random.choice(list(AthenaTaskSet.environments.values()))
            if not environments:
                return None
            return random.choice(environments)

        environments = AthenaTaskSet.environments.get(project_code)
        if not environments:
            return None
        return random.choice(environments)



    @staticmethod
    def get_environment_to_update(project_code=None):
        if not project_code:
            if len(AthenaTaskSet.environments_to_update) == 0:
                return None

            environments = random.choice(list(AthenaTaskSet.environments_to_update.values()))
            if not environments:
                return None
            return random.choice(environments)

        environments = AthenaTaskSet.environments_to_update.get(project_code)
        if not environments:
            return None
        return random.choice(environments)

    # ---------------------------------------------------------------------
    # User
    def add_user(self):
        user = build_user()
        with self.client.post("/core/user", name="AddUser", json=user) as response:
            if response.status_code == 201:
                user["id"] = int(response.headers.get('entity_id'))
                if len(AthenaTaskSet.users) == 0 or random.choice([True, False]):
                    AthenaTaskSet.users.append(user)
                else:
                    AthenaTaskSet.users_to_update.append(user)

    @staticmethod
    def get_user():
        if len(AthenaTaskSet.users) == 0:
            return None
        return random.choice(AthenaTaskSet.users)

    @staticmethod
    def get_username():
        user = AthenaTaskSet.get_user()
        if not user:
            return None
        return user["username"]

    @staticmethod
    def get_user_to_update():
        if len(AthenaTaskSet.users_to_update) == 0:
            return None
        return random.choice(AthenaTaskSet.users_to_update)


from random import Random

from locust import TaskSet

from test_data.core_faker import get_project, get_version, get_user, get_environment

random = Random()


class AthenaTaskSet(TaskSet):
    abstract = True

    projects = []
    versions = []
    environments = []
    users = []

    def on_start(self) -> None:
        self.add_project()
        self.add_version()
        self.add_environment()

    def add_project(self):
        project = get_project()
        with self.client.post("/core/project", name="AddProject", json=project) as response:
            if response.status_code == 201:
                project["id"] = int(response.headers.get('entity_id'))
                self.projects.append(project)

    def get_project(self):
        return self.projects[random.randint(0, len(self.projects) - 1)]

    def add_version(self):
        version = get_version(self.get_project()["code"])
        with self.client.post("/core/version", name="AddVersion", json=version) as response:
            if response.status_code == 201:
                version["id"] = int(response.headers.get('entity_id'))
                self.versions.append(version)

    def get_version(self):
        return self.versions[random.randint(0, len(self.versions) - 1)]

    def add_environment(self):
        environment = get_environment(self.get_project()["code"])
        with self.client.post("/core/environment", name="AddEnvironment", json=environment) as response:
            if response.status_code == 201:
                environment["id"] = int(response.headers.get('entity_id'))
                self.environments.append(environment)

    def get_environment(self):
        return self.environments[random.randint(0, len(self.environments) - 1)]

    def add_user(self):
        user = get_user()
        with self.client.post("/core/user", name="AddUser", json=user) as response:
            if response.status_code == 201:
                user["id"] = int(response.headers.get('entity_id'))
                self.users.append(user)

    def get_user(self):
        return self.users[random.randint(0, len(self.users) - 1)]

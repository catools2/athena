from locust import TaskSet

from utils.data_utils import get_random_user
from utils.random_utils import random_string


class AthenaTaskSet(TaskSet):
    abstract = True
    project_code = None
    version_code = None
    environment_code = None

    def on_start(self) -> None:
        self.project_code = random_string(8)
        self.client.post("/core/project", name="AddProject", json={
            "code": self.project_code,
            "name": f"{get_random_user().get('ln')}_Proj",
        })

        self.version_code = random_string(8)
        self.client.post("/core/version", name="AddVersion", json={
            "code": self.version_code,
            "name": f"{get_random_user().get('ln')}_Ver",
            "project": self.project_code
        })

        self.environment_code = random_string(8)
        self.client.post("/core/environment", name="AddEnvironment", json={
            "code": self.environment_code,
            "name": f"{get_random_user().get('ln')}_Env",
            "project":self.project_code
        })

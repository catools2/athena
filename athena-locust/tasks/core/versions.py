import random

from locust import task

from tasks.athena_task_set import AthenaTaskSet
from utils.data_utils import get_random_user
from utils.random_utils import random_string


class AddVersion(AthenaTaskSet):

    @task
    def add_version(self):
        self.client.post("/core/version", name="AddVersion", json={
            "code": f"{random_string(8)}",
            "name": f"{get_random_user().get('ln')}_Ver",
            "project": self.project_code
        })


class GetVersionById(AthenaTaskSet):

    @task
    def get_version(self):
        self.client.get(f"/core/version/{random.randint(1, 100)}", name="GetVersionById")


class UpdateVersion(AthenaTaskSet):

    @task
    def update_version(self):
        self.client.put(f"/core/version", name="UpdateVersion", json={
            "id": f"{random.randint(0, 100)}",
            "code": f"{random_string(8)}",
            "name": f"{get_random_user().get('ln')} Env",
            "project": self.project_code
        })

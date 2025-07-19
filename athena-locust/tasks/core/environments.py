import random

from locust import task

from tasks.athena_task_set import AthenaTaskSet
from utils.data_utils import get_random_user
from utils.random_utils import random_string


class AddEnvironment(AthenaTaskSet):

    @task
    def add_environment(self):
        self.client.post("/core/environment", name="AddEnvironment", json={
            "code": f"{random_string(8)}",
            "name": f"{get_random_user().get('ln')}_Env",
            "project":self.project_code
        })


class GetEnvironmentById(AthenaTaskSet):

    @task
    def get_environment(self):
        self.client.get(f"/core/environment/{random.randint(1, 100)}", name="GetEnvironmentById")


class UpdateEnvironment(AthenaTaskSet):

    @task
    def update_environment(self):
        self.client.put(f"/core/environment", name="UpdateEnvironment", json={
            "id": f"{random.randint(0, 100)}",
            "code": f"{random_string(8)}",
            "name": f"{get_random_user().get('ln')} Env",
            "project": self.project_code
        })

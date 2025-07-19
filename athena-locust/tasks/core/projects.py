import random

from locust import task

from tasks.athena_task_set import AthenaTaskSet
from utils.data_utils import get_random_user
from utils.random_utils import random_string


class AddProject(AthenaTaskSet):

    @task
    def add_project(self):
        self.client.post("/core/project", name="AddProject", json={
            "code": f"{random_string(8)}",
            "name": f"{get_random_user().get('ln')}_Proj",
        })


class GetProjectById(AthenaTaskSet):

    @task
    def get_project(self):
        self.client.get(f"/core/project/{random.randint(1, 100)}", name="GetProjectById")


class UpdateProject(AthenaTaskSet):

    @task
    def update_project(self):
        self.client.put(f"/core/project", name="UpdateProject", json={
            "id": f"{random.randint(0, 100)}",
            "code": f"{random_string(8)}",
            "name": f"{get_random_user().get('ln')} Env"
        })

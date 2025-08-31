from locust import task

from test_data.core_faker import get_environment
from tasks.athena_task_set import AthenaTaskSet


class AddEnvironment(AthenaTaskSet):

    @task
    def add_environment_task(self):
        self.add_environment()


class GetEnvironmentById(AthenaTaskSet):

    @task
    def get_environment_task(self):
        self.client.get(f"/core/environment/{self.get_environment()["id"]}", name="GetEnvironmentById")


class UpdateEnvironment(AthenaTaskSet):

    @task
    def update_environment_task(self):
        self.client.put(f"/core/environment", name="UpdateEnvironment", json=get_environment(self.get_project()["code"], self.get_environment()["id"]))

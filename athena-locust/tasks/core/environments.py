from locust import task

from tasks.core.core_task_set import CoreTaskSet
from test_data.core_faker import get_environment


class AddEnvironment(CoreTaskSet):

    @task
    def add_environment_task(self):
        self.add_environment()


class GetEnvironmentById(CoreTaskSet):

    def on_start(self):
        self.add_environment()

    @task
    def get_environment_task(self):
        self.client.get(f"/core/environment/{self.get_environment()["id"]}", name="GetEnvironmentById")


class UpdateEnvironment(CoreTaskSet):

    def on_start(self):
        self.add_environment()

    @task
    def update_environment_task(self):
        self.client.put(f"/core/environment", name="UpdateEnvironment",
                        json=get_environment(self.get_project()["code"], self.get_environment()["id"]))

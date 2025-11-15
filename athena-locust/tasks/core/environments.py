from locust import task

from tasks.athena_task_set import AthenaTaskSet
from tasks.core.core_task_set import CoreTaskSet
from test_data.core_faker import build_environment


class AddEnvironment(CoreTaskSet):

    @task
    def add_environment_task(self):
        self.add_environment()


class GetEnvironmentById(CoreTaskSet):

    def on_start(self):
        super().on_start()
        self.add_environment()

    @task
    def get_environment_task(self):
        self.client.get(f"/core/environment/{AthenaTaskSet.get_environment()["id"]}", name="GetEnvironmentById")


class UpdateEnvironment(CoreTaskSet):

    def on_start(self):
        super().on_start()
        self.add_environment()

    @task
    def update_environment_task(self):
        self.client.put(f"/core/environment", name="UpdateEnvironment",
                        json=build_environment(AthenaTaskSet.get_project()["code"], AthenaTaskSet.get_environment()["id"]))

from locust import task

from tasks.core.core_task_set import CoreTaskSet
from test_data.core_faker import get_version


class AddVersion(CoreTaskSet):

    @task
    def add_version_task(self):
        self.add_version()


class GetVersionById(CoreTaskSet):

    def on_start(self):
        self.add_version()

    @task
    def get_version_task(self):
        self.client.get(f"/core/version/{self.get_version()["id"]}", name="GetVersionById")


class UpdateVersion(CoreTaskSet):

    def on_start(self):
        self.add_version()

    @task
    def update_version_task(self):
        self.client.put(f"/core/version", name="UpdateVersion",
                        json=get_version(self.get_project()["code"], self.get_version()["id"]))

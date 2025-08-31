from locust import task

from test_data.core_faker import get_version
from tasks.athena_task_set import AthenaTaskSet


class AddVersion(AthenaTaskSet):

    @task
    def add_version_task(self):
        self.add_version()


class GetVersionById(AthenaTaskSet):

    @task
    def get_version_task(self):
        self.client.get(f"/core/version/{self.get_version()["id"]}", name="GetVersionById")


class UpdateVersion(AthenaTaskSet):

    @task
    def update_version_task(self):
        self.client.put(f"/core/version", name="UpdateVersion", json=get_version(self.get_project()["code"], self.get_version()["id"]))

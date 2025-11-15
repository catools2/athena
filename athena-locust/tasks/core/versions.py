from locust import task

from tasks.athena_task_set import AthenaTaskSet
from tasks.core.core_task_set import CoreTaskSet
from test_data.core_faker import build_version


class AddVersion(CoreTaskSet):

    @task
    def add_version_task(self):
        self.add_version()


class GetVersionById(CoreTaskSet):

    def on_start(self):
        super().on_start()
        self.add_version()

    @task
    def get_version_task(self):
        self.client.get(f"/core/version/{AthenaTaskSet.get_version()["id"]}", name="GetVersionById")


class UpdateVersion(CoreTaskSet):

    def on_start(self):
        super().on_start()
        self.add_version()

    @task
    def update_version_task(self):
        self.client.put(f"/core/version", name="UpdateVersion",
                        json=build_version(AthenaTaskSet.get_project()["code"], AthenaTaskSet.get_version()["id"]))

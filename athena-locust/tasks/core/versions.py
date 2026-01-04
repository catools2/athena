from locust import task

from tasks.athena_task_set import AthenaTaskSet
from tasks.core.core_task_set import CoreTaskSet
from test_data.core_faker import build_version


class AddVersion(CoreTaskSet):

    @task
    def add_version_task(self):
        self.add_version()


class GetVersionById(CoreTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(CoreTaskSet.versions) < 3:
            self.add_version()

    @task
    def get_version_task(self):
        version = AthenaTaskSet.get_version()
        version_id = version["id"] if version else 1
        self.client.get(f"/core/version/{version_id}", name="GetVersionById")


class GetVersionByCode(CoreTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(CoreTaskSet.versions) < 3:
            self.add_version()

    @task
    def get_version_task(self):
        version = AthenaTaskSet.get_version()
        self.client.get(f"/core/version?project={version["project"]}&keyword={version["code"]}",
                        name="GetVersionByCode")


class UpdateVersion(CoreTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(CoreTaskSet.versions) < 3:
            self.add_version()

    @task
    def update_version_task(self):
        version = AthenaTaskSet.get_version_to_update()
        if not version:
            return
        self.client.put(f"/core/version", name="UpdateVersion",
                        json=build_version(version["project"], version["id"]))

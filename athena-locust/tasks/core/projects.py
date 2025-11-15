from locust import task

from tasks.athena_task_set import AthenaTaskSet
from tasks.core.core_task_set import CoreTaskSet
from test_data.core_faker import build_project


class AddProject(CoreTaskSet):

    @task
    def add_project_task(self):
        self.add_project()


class GetProjectById(CoreTaskSet):

    def on_start(self):
        super().on_start()
        self.add_project()

    @task
    def get_project_task(self):
        self.client.get(f"/core/project/{AthenaTaskSet.get_project()["id"]}", name="GetProjectById")


class UpdateProject(CoreTaskSet):

    def on_start(self):
        super().on_start()
        self.add_project()

    @task
    def update_project_task(self):
        self.client.put(f"/core/project", name="UpdateProject", json=build_project(AthenaTaskSet.get_project()["id"]))

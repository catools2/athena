from locust import task

from test_data.core_faker import get_project
from tasks.athena_task_set import AthenaTaskSet


class AddProject(AthenaTaskSet):

    @task
    def add_project_task(self):
        self.add_project()


class GetProjectById(AthenaTaskSet):

    @task
    def get_project_task(self):
        self.client.get(f"/core/project/{self.get_project()["id"]}", name="GetProjectById")


class UpdateProject(AthenaTaskSet):

    @task
    def update_project_task(self):
        self.client.put(f"/core/project", name="UpdateProject", json=get_project(self.get_project()["id"]))

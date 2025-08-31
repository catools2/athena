from locust import task

from tasks.core.core_task_set import CoreTaskSet
from test_data.core_faker import get_user


class AddUser(CoreTaskSet):

    @task
    def add_user_task(self):
        self.add_user()


class GetUserById(CoreTaskSet):

    def on_start(self):
        self.add_user()

    @task
    def get_user_task(self):
        self.client.get(f"/core/user/{self.get_user()["id"]}", name="GetUserById")


class UpdateUser(CoreTaskSet):

    def on_start(self):
        self.add_user()

    @task
    def update_user_task(self):
        self.client.put("/core/user", name="UpdateUser", json=get_user(self.get_user()["id"]))

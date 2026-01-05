from locust import task

from tasks.athena_task_set import AthenaTaskSet
from tasks.core.core_task_set import CoreTaskSet
from test_data.factories.core_factory import UserFactory


class AddUser(CoreTaskSet):

    @task
    def add_user_task(self):
        self.add_user()


class GetUserById(CoreTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(CoreTaskSet.users) < 3:
            self.add_user()

    @task
    def get_user_task(self):
        self.client.get(f"/core/user/{AthenaTaskSet.get_user()["id"]}", name="GetUserById")


class UpdateUser(CoreTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(CoreTaskSet.users) < 3:
            self.add_user()

    @task
    def update_user_task(self):
        user = AthenaTaskSet.get_user_to_update()
        if not user:
            return
        user['aliases'] = user['aliases'] + UserFactory.to_dict()["aliases"]
        self.client.put("/core/user", name="UpdateUser", json=user)

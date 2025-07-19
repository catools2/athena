import random

from locust import task

from tasks.athena_task_set import AthenaTaskSet
from utils.data_utils import get_random_user
from utils.random_utils import random_string


class AddUser(AthenaTaskSet):

    @task
    def add_user(self):
        self.client.post("/core/user", name="AddUser", json={
            "username": f"{get_random_user().get('fn')}.{get_random_user().get('ln')}",
            "aliases": [
                {
                    "alias": random_string(8)
                },
                {
                    "alias": random_string(9)
                },
                {
                    "alias": random_string(10)
                }
            ]
        })


class GetUserById(AthenaTaskSet):

    @task
    def get_user(self):
        self.client.get(f"/core/user/{random.randint(1, 100)}", name="GetUserById")


class UpdateUser(AthenaTaskSet):

    @task
    def update_user(self):
        self.client.put(f"/core/user", name="UpdateUser", json={
            "id": f"{random.randint(0, 100)}",
            "username": f"{get_random_user().get('fn')}.{get_random_user().get('ln')}",
            "aliases": [
                {
                    "alias": random_string(8)
                },
                {
                    "alias": random_string(9)
                },
                {
                    "alias": random_string(10)
                }
            ]
        })

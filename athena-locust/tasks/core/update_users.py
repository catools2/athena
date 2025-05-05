import random

from locust import task, TaskSet

from utils.data_utils import get_random_user
from utils.random_utils import random_string


class UpdateUser(TaskSet):

    @task
    def update_user(self):
        self.client.put(f"/core/user", name="Update User", json={
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

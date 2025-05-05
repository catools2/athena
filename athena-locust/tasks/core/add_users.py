from locust import task, TaskSet

from utils.data_utils import get_random_user
from utils.random_utils import random_string


class AddUser(TaskSet):

    @task
    def add_user(self):
        self.client.post("/core/user", name="Add User", json={
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

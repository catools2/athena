import random

from locust import task, TaskSet


class GetUserById(TaskSet):

    @task
    def get_user(self):
        self.client.get(f"/core/user/{random.randint(1, 100)}", name="Get User By Id")

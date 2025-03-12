import time
from locust import HttpUser, between, task


class AthenaCore(HttpUser):
    wait_time = between(1, 2)

    @task
    def get_user(self):
        for id in range(10):
            self.client.get(f"/core/user/{id}", name="/core/user")
            time.sleep(1)

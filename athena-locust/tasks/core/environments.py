from locust import task

from tasks.athena_task_set import AthenaTaskSet
from tasks.core.core_task_set import CoreTaskSet
from test_data.core_faker import build_environment


class AddEnvironment(CoreTaskSet):

    @task
    def add_environment_task(self):
        self.add_environment()


class GetEnvironmentById(CoreTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(CoreTaskSet.environments) < 3:
            self.add_environment()

    @task
    def get_environment_task(self):
        self.client.get(f"/core/environment/{AthenaTaskSet.get_environment()["id"]}", name="GetEnvironmentById")


class GetEnvironmentByCode(CoreTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(CoreTaskSet.environments) < 3:
            self.add_environment()

    @task
    def get_environment_task(self):
        environment = AthenaTaskSet.get_environment()
        self.client.get(f"/core/environment?project={environment["project"]}&keyword={environment["code"]}",
                        name="GetEnvironmentByCode")


class UpdateEnvironment(CoreTaskSet):

    def on_start(self) -> None:
        super().on_start()
        environment = AthenaTaskSet.get_environment_to_update()
        if not environment:
            for i in range(10):
                self.add_environment()
                environment = AthenaTaskSet.get_environment_to_update()
                if environment:
                    break

    @task
    def update_environment_task(self):
        environment = AthenaTaskSet.get_environment_to_update()
        if not environment:
            return

        env_to_add = build_environment(environment["project"], environment["id"])
        self.client.put(f"/core/environment", name="UpdateEnvironment", json=env_to_add)

from locust import task

from tasks.spec.spec_task_set import SpecTaskSet


class AddSpec(SpecTaskSet):

    @task
    def add_spec_task(self):
        self.add_spec()


class GetSpecById(SpecTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(SpecTaskSet.specs) < 1:
            self.add_spec()

    @task
    def get_spec_by_id_task(self):
        spec = SpecTaskSet.get_spec() 
        self.client.get(f"/spec/spec/{spec['id']}", name="GetSpecById")


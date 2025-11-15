from locust import task

from tasks.tms.tms_task_set import TmsTaskSet


class AddItem(TmsTaskSet):
    """Task for creating a new item."""

    @task
    def add_item_task(self) -> None:
        self.add_item()


class GetItemById(TmsTaskSet):
    """Task for retrieving an existing item by ID."""

    def on_start(self) -> None:
        super().on_start()
        self.add_item()

    @task
    def get_item_task(self) -> None:
        item = TmsTaskSet.get_item()
        self.client.get(f"/tms/item/{item['id']}", name="GetItemById")

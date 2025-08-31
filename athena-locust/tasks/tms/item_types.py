"""
Locust tasks for the TMS ItemType resource.

The ItemType endpoints support creating and retrieving item type definitions.  An
item type is defined by a code and a name.  These tasks rely on helper
functions provided by :class:`AthenaTmsTaskSet` to generate unique item types
and store them for later retrieval.  A minimal workflow typically involves
creating a new item type and then fetching it by its identifier.
"""

from locust import task

from tasks.tms.tms_task_set import TmsTaskSet


class AddItemType(TmsTaskSet):
    """Task for creating a new item type."""

    @task
    def add_item_type_task(self) -> None:
        self.add_item_type()


class GetItemTypeById(TmsTaskSet):
    """Task for retrieving an existing item type by ID."""

    def on_start(self) -> None:
        # Ensure at least one item type exists
        if not self.item_types:
            self.add_item_type()

    @task
    def get_item_type_task(self) -> None:
        item_type = self.get_item_type()
        self.client.get(f"/itemType/{item_type['id']}", name="GetItemTypeById")

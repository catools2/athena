"""
Locust tasks for the TMS Priority resource.

Priorities represent the importance of an item.  The API supports
creating priorities and retrieving them by their identifier.  These tasks
delegate to :class:`AthenaTmsTaskSet` for payload generation and storage.
"""

from locust import task

from tasks.tms.tms_task_set import TmsTaskSet


class AddPriority(TmsTaskSet):
    """Task for creating a new priority."""

    @task
    def add_priority_task(self) -> None:
        self.add_priority()


class GetPriorityById(TmsTaskSet):
    """Task for retrieving an existing priority by ID."""

    def on_start(self) -> None:
        super().on_start()
        if len(TmsTaskSet.priorities) < 3:
            self.add_priority()

    @task
    def get_priority_task(self) -> None:
        priority = TmsTaskSet.get_priority()
        self.client.get(f"/tms/priority/{priority['id']}", name="GetPriorityById")


class SearchPriorityByKeyword(TmsTaskSet):
    """Task for searching priorities by keyword."""

    def on_start(self) -> None:
        super().on_start()
        if len(TmsTaskSet.priorities) < 3:
            self.add_priority()

    @task
    def search_priority_task(self) -> None:
        priority = TmsTaskSet.get_priority()
        keyword = (priority.get('name') or 'Medium') if priority else 'Medium'
        self.client.get(f"/tms/priority?keyword={keyword}", name="SearchPriorityByKeyword")

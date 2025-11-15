"""
Locust tasks for the TMS Status resource.

Statuses describe the state of an item within the test management system.
These tasks cover the creation of new statuses and retrieval by identifier.
"""

from locust import task

from tasks.tms.tms_task_set import TmsTaskSet


class AddStatus(TmsTaskSet):
    """Task for creating a new status."""

    @task
    def add_status_task(self) -> None:
        self.add_status()


class GetStatusById(TmsTaskSet):
    """Task for retrieving an existing status by ID."""

    def on_start(self) -> None:
        if not self.statuses:
            self.add_status()

    @task
    def get_status_task(self) -> None:
        status = TmsTaskSet.get_status()
        self.client.get(f"/tms/status/{status['id']}", name="GetStatusById")

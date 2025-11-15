"""
Locust tasks for the TMS TestCycle resource.

Test cycles represent groups of test executions typically tied to a
release or a version.  The API allows clients to create cycles and
retrieve them by identifier.  These tasks use helper methods on
:class:`AthenaTmsTaskSet` to generate minimal but valid cycles and to
store them for retrieval.
"""

from locust import task

from tasks.tms.tms_task_set import TmsTaskSet


class AddTestCycle(TmsTaskSet):
    """Task for creating a new test cycle."""

    @task
    def add_test_cycle_task(self) -> None:
        self.add_test_cycle()


class GetTestCycleById(TmsTaskSet):
    """Task for retrieving an existing test cycle by ID."""

    def on_start(self) -> None:
        super().on_start()
        self.add_test_cycle()

    @task
    def get_test_cycle_task(self) -> None:
        cycle = TmsTaskSet.get_test_cycle()
        self.client.get(f"/tms/cycle/{cycle['id']}", name="GetTestCycleById")

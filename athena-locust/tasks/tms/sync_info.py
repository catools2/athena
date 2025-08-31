"""
Locust tasks for the TMS SyncInfo resource.

Sync information records capture metadata about synchronisation events
between Athena and external systems.  The API supports creating new
records and retrieving them by identifier.  These tasks interact with
the helper methods defined on :class:`AthenaTmsTaskSet` to generate
realistic payloads and store the resulting entities for later use.
"""

from locust import task

from tasks.tms.tms_task_set import TmsTaskSet


class AddSyncInfo(TmsTaskSet):
    """Task for creating a new sync info record."""

    @task
    def add_sync_info_task(self) -> None:
        self.add_sync_info()


class GetSyncInfoById(TmsTaskSet):
    """Task for retrieving an existing sync info record by ID."""

    def on_start(self) -> None:
        if not self.sync_infos:
            self.add_sync_info()

    @task
    def get_sync_info_task(self) -> None:
        sync_info = self.get_sync_info()
        self.client.get(f"/syncInfo/{sync_info['id']}", name="GetSyncInfoById")

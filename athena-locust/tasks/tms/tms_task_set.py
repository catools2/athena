from locust import TaskSet
from random import Random
from test_data.tms_faker import (
    get_item_type,
    get_status,
    get_priority,
    get_sync_info,
    get_test_cycle,
    get_item,
    get_status_transition,
    get_test_execution,
)
from typing import Dict

random = Random()


class TmsTaskSet(TaskSet):
    abstract = True

    item_types: list = []
    statuses: list = []
    priorities: list = []
    sync_infos: list = []
    test_cycles: list = []
    items: list = []
    status_transitions: list = []
    test_executions: list = []

    # ---------------------------------------------------------------------
    # Item Type
    def add_item_type(self) -> None:
        item_type = get_item_type()
        with self.client.post("/itemType", name="AddItemType", json=item_type) as response:
            if response.status_code == 201:
                item_type["id"] = int(response.headers.get('entity_id'))
                self.item_types.append(item_type)

    def get_item_type(self) -> Dict:
        """Return a random previously created item type."""
        return self.item_types[random.randint(0, len(self.item_types) - 1)]

    # ---------------------------------------------------------------------
    # Statuses
    def add_status(self) -> None:
        status = get_status()
        with self.client.post("/status", name="AddStatus", json=status) as response:
            if response.status_code == 201:
                status["id"] = int(response.headers.get('entity_id'))
                self.statuses.append(status)

    def get_status(self) -> Dict:
        return self.statuses[random.randint(0, len(self.statuses) - 1)]

    # ---------------------------------------------------------------------
    # Priorities
    def add_priority(self) -> None:
        priority = get_priority()
        with self.client.post("/priority", name="AddPriority", json=priority) as response:
            if response.status_code == 201:
                priority["id"] = int(response.headers.get('entity_id'))
                self.priorities.append(priority)

    def get_priority(self) -> Dict:
        return self.priorities[random.randint(0, len(self.priorities) - 1)]

    # ---------------------------------------------------------------------
    # Sync info
    def add_sync_info(self) -> None:
        sync_info = get_sync_info()
        with self.client.post("/syncInfo", name="AddSyncInfo", json=sync_info) as response:
            if response.status_code == 201:
                sync_info["id"] = int(response.headers.get('entity_id'))
                self.sync_infos.append(sync_info)

    def get_sync_info(self) -> Dict:
        return self.sync_infos[random.randint(0, len(self.sync_infos) - 1)]

    # ---------------------------------------------------------------------
    # Test cycles
    def add_test_cycle(self) -> None:
        test_cycle = get_test_cycle()
        with self.client.post("/cycle", name="AddTestCycle", json=test_cycle) as response:
            if response.status_code == 201:
                test_cycle["id"] = int(response.headers.get('entity_id'))
                self.test_cycles.append(test_cycle)

    def get_test_cycle(self) -> Dict:
        return self.test_cycles[random.randint(0, len(self.test_cycles) - 1)]

    # ---------------------------------------------------------------------
    # Item
    def add_item(self) -> None:
        item = get_item()
        with self.client.post("/item", name="AddItem", json=item) as response:
            if response.status_code == 201:
                item["id"] = int(response.headers.get('entity_id'))
                self.items.append(item)

    def get_item(self) -> Dict:
        return self.items[random.randint(0, len(self.items) - 1)]

    # ---------------------------------------------------------------------
    # Status Transition
    def add_status_transition(self) -> None:
        transition = get_status_transition()
        item_code = transition.pop("itemCode", None)
        params = {"itemCode": item_code} if item_code else {}
        with self.client.post("/transition", name="AddStatusTransition", params=params, json=transition) as response:
            if response.status_code == 201:
                transition["id"] = int(response.headers.get('entity_id'))
                if item_code:
                    transition["itemCode"] = item_code
                self.status_transitions.append(transition)

    def get_status_transition(self) -> Dict:
        return self.status_transitions[random.randint(0, len(self.status_transitions) - 1)]

    # ---------------------------------------------------------------------
    # Test Execution
    def add_test_execution(self) -> None:
        execution = get_test_execution()
        cycle_code = execution.pop("cycleCode", None)
        params = {"cycleCode": cycle_code} if cycle_code else {}
        with self.client.post("/execution", name="AddTestExecution", params=params, json=execution) as response:
            if response.status_code == 201:
                execution["id"] = int(response.headers.get('entity_id'))
                if cycle_code:
                    execution["cycleCode"] = cycle_code
                self.test_executions.append(execution)

    def get_test_execution(self) -> Dict:
        return self.test_executions[random.randint(0, len(self.test_executions) - 1)]
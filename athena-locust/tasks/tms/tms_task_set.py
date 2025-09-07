from random import Random
from typing import Dict

from tasks.athena_task_set import AthenaTaskSet
from test_data.tms_faker import (
    build_item_type,
    build_status,
    build_priority,
    build_test_cycle,
    build_item,
    build_status_transition,
    build_test_execution,
)

random = Random()


class TmsTaskSet(AthenaTaskSet):
    abstract = True

    item_types: list = []
    statuses: list = []
    priorities: list = []
    sync_infos: list = []
    test_cycles: list = []
    items: list = []
    status_transitions: list = []
    test_executions: list = []

    def on_start(self) -> None:
        super().on_start()
        self.add_item_type()
        self.add_status()
        self.add_project()
        self.add_version()
        self.add_priority()
        self.add_item()

    # ---------------------------------------------------------------------
    # Item Type
    def add_item_type(self) -> None:
        item_type = build_item_type()
        with self.client.post("/tms/itemType", name="AddItemType", json=item_type) as response:
            if response.status_code == 201:
                item_type["id"] = int(response.headers.get('entity_id'))
                TmsTaskSet.item_types.append(item_type)

    @staticmethod
    def get_item_type() -> Dict:
        return TmsTaskSet.item_types[random.randint(0, len(TmsTaskSet.item_types) - 1)]

    # ---------------------------------------------------------------------
    # Statuses
    def add_status(self) -> None:
        status = build_status()
        with self.client.post("/tms/status", name="AddStatus", json=status) as response:
            if response.status_code == 201:
                status["id"] = int(response.headers.get('entity_id'))
                TmsTaskSet.statuses.append(status)

    @staticmethod
    def get_status() -> Dict:
        return TmsTaskSet.statuses[random.randint(0, len(TmsTaskSet.statuses) - 1)]

    # ---------------------------------------------------------------------
    # Priorities
    def add_priority(self) -> None:
        priority = build_priority()
        with self.client.post("/tms/priority", name="AddPriority", json=priority) as response:
            if response.status_code == 201:
                priority["id"] = int(response.headers.get('entity_id'))
                TmsTaskSet.priorities.append(priority)

    @staticmethod
    def get_priority() -> Dict:
        return TmsTaskSet.priorities[random.randint(0, len(TmsTaskSet.priorities) - 1)]

    # ---------------------------------------------------------------------
    # Test cycles
    def add_test_cycle(self) -> None:
        test_cycle = build_test_cycle()
        with self.client.post("/tms/cycle", name="AddTestCycle", json=test_cycle) as response:
            if response.status_code == 201:
                test_cycle["id"] = int(response.headers.get('entity_id'))
                TmsTaskSet.test_cycles.append(test_cycle)

    @staticmethod
    def get_test_cycle() -> Dict:
        return TmsTaskSet.test_cycles[random.randint(0, len(TmsTaskSet.test_cycles) - 1)]

    # ---------------------------------------------------------------------
    # Item
    def add_item(self) -> None:
        item = build_item()
        with self.client.post("/tms/item", name="AddItem", json=item) as response:
            if response.status_code == 201:
                item["id"] = int(response.headers.get('entity_id'))
                TmsTaskSet.items.append(item)

    @staticmethod
    def get_item() -> Dict:
        return TmsTaskSet.items[random.randint(0, len(TmsTaskSet.items) - 1)]

    # ---------------------------------------------------------------------
    # Status Transition
    def add_status_transition(self) -> None:
        transition = build_status_transition()
        item_code = transition.pop("itemCode", None)
        params = {"itemCode": item_code} if item_code else {}
        with self.client.post("/tms/transition", name="AddStatusTransition", params=params,
                              json=transition) as response:
            if response.status_code == 201:
                transition["id"] = int(response.headers.get('entity_id'))
                if item_code:
                    transition["itemCode"] = item_code
                TmsTaskSet.status_transitions.append(transition)

    @staticmethod
    def get_status_transition() -> Dict:
        return TmsTaskSet.status_transitions[random.randint(0, len(TmsTaskSet.status_transitions) - 1)]

    # ---------------------------------------------------------------------
    # Test Execution
    def add_test_execution(self) -> None:
        execution = build_test_execution()
        cycle_code = execution.pop("cycleCode", None)
        params = {"cycleCode": cycle_code} if cycle_code else {}
        with self.client.post("/tms/execution", name="AddTestExecution", params=params, json=execution) as response:
            if response.status_code == 201:
                execution["id"] = int(response.headers.get('entity_id'))
                if cycle_code:
                    execution["cycleCode"] = cycle_code
                TmsTaskSet.test_executions.append(execution)

    @staticmethod
    def get_test_execution() -> Dict:
        return TmsTaskSet.test_executions[random.randint(0, len(TmsTaskSet.test_executions) - 1)]

from random import Random
from typing import Dict

from tasks.athena_task_set import AthenaTaskSet
from tasks.core.core_task_set import CoreTaskSet
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
        if len(TmsTaskSet.item_types) == 0:
            return None

        if len(TmsTaskSet.item_types) == 1:
            return TmsTaskSet.item_types[0]

        return random.choice(TmsTaskSet.item_types)

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
        if len(TmsTaskSet.statuses) == 0:
            return None

        if len(TmsTaskSet.statuses) == 1:
            return TmsTaskSet.statuses[0]

        return random.choice(TmsTaskSet.statuses)

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
        if len(TmsTaskSet.priorities) == 0:
            return None

        if len(TmsTaskSet.priorities) == 1:
            return TmsTaskSet.priorities[0]

        return random.choice(TmsTaskSet.priorities)

    # ---------------------------------------------------------------------
    # Test cycles
    def add_test_cycle(self) -> None:
        if len(TmsTaskSet.items) == 0:
            self.add_item()

        test_cycle = build_test_cycle()
        with self.client.post("/tms/cycle", name="AddTestCycle", json=test_cycle) as response:
            if response.status_code == 201:
                test_cycle["id"] = int(response.headers.get('entity_id'))
                TmsTaskSet.test_cycles.append(test_cycle)

    @staticmethod
    def get_test_cycle() -> Dict:
        if len(TmsTaskSet.test_cycles) == 0:
            return None

        if len(TmsTaskSet.test_cycles) == 1:
            return TmsTaskSet.test_cycles[0]

        return random.choice(TmsTaskSet.test_cycles)

    # ---------------------------------------------------------------------
    # Item
    def add_item(self) -> None:
        if not TmsTaskSet.get_item_type():
            self.add_item_type()

        if not TmsTaskSet.get_status():
            self.add_status()

        if not CoreTaskSet.get_version():
            self.add_version()

        if not TmsTaskSet.get_priority():
            self.add_priority()

        if not CoreTaskSet.get_user():
            self.add_user()

        item = build_item()
        with self.client.post("/tms/item", name="AddItem", json=item) as response:
            if response.status_code == 201:
                item["id"] = int(response.headers.get('entity_id'))
                TmsTaskSet.items.append(item)

    @staticmethod
    def get_item() -> Dict:
        if len(TmsTaskSet.items) == 0:
            return None

        if len(TmsTaskSet.items) == 1:
            return TmsTaskSet.items[0]

        return random.choice(TmsTaskSet.items)

    # ---------------------------------------------------------------------
    # Status Transition
    def add_status_transition(self) -> None:
        transition = build_status_transition()
        item = transition.pop("item", None)
        params = {"item": item} if item else {}
        with self.client.post("/tms/transition", name="AddStatusTransition", params=params,
                              json=transition) as response:
            if response.status_code == 201:
                transition["id"] = int(response.headers.get('entity_id'))
                if item:
                    transition["item"] = item
                TmsTaskSet.status_transitions.append(transition)

    @staticmethod
    def get_status_transition() -> Dict:
        if len(TmsTaskSet.status_transitions) == 0:
            return None

        if len(TmsTaskSet.status_transitions) == 1:
            return TmsTaskSet.status_transitions[0]

        return random.choice(TmsTaskSet.status_transitions)

    # ---------------------------------------------------------------------
    # Test Execution
    def add_test_execution(self) -> None:
        execution = build_test_execution()
        cycle = execution.pop("cycle", None)
        params = {"cycle": cycle} if cycle else {}
        with self.client.post("/tms/execution", name="AddTestExecution", params=params, json=execution) as response:
            if response.status_code == 201:
                execution["id"] = int(response.headers.get('entity_id'))
                if cycle:
                    execution["cycle"] = cycle
                TmsTaskSet.test_executions.append(execution)

    @staticmethod
    def get_test_execution() -> Dict:
        if len(TmsTaskSet.test_executions) == 0:
            return None

        if len(TmsTaskSet.test_executions) == 1:
            return TmsTaskSet.test_executions[0]

        return random.choice(TmsTaskSet.test_executions)

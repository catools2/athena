"""
Locust user class for the Athena TMS API.

This module defines a user type that exercises the test management
endpoints of Athena.  It imports all task definitions from the
``tasks.tms`` subpackage and assigns them weighted probabilities to
simulate realistic traffic patterns.  The weights are chosen to
prioritise read operations (fetching entities by ID) over create
operations, reflecting the common use case where existing definitions
are queried more frequently than they are created.  Adjust the
weights as needed for your test scenarios.
"""
from locust import between

from tasks.athena_base import AthenaBase
from tasks.tms.item_types import AddItemType, GetItemTypeById
from tasks.tms.items import AddItem, GetItemById
from tasks.tms.priorities import AddPriority, GetPriorityById
from tasks.tms.statuses import AddStatus, GetStatusById
from tasks.tms.test_cycles import AddTestCycle, GetTestCycleById


class AthenaTMS(AthenaBase):
    """Locust user exercising Athena TMS endpoints."""

    # Wait between two tasks (in seconds)
    wait_time = between(1, 2)

    # Weighting of tasks.  Tuple of (task class, weight).
    tasks = [
        (AddItem, 30),
        (GetItemById, 10),
        (AddItemType, 1),
        (AddStatus, 1),
        (AddPriority, 1),
        (AddTestCycle, 5),
        (GetItemTypeById, 1),
        (GetStatusById, 1),
        (GetPriorityById, 1),
        (GetTestCycleById, 1),
    ]

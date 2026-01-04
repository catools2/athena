"""
Locust user class for the Athena Spec API.

This module exercises OpenAPI spec endpoints: saving a spec and
retrieving by id.
"""
from locust import between

from tasks.athena_base import AthenaBase
from tasks.spec.specs import AddSpec, GetSpecById


class AthenaSpec(AthenaBase):
    wait_time = between(1, 2)

    tasks = [
        (AddSpec, 60),
        (GetSpecById, 40),
    ]


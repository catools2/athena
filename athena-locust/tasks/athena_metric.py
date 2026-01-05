"""
Locust user class for the Athena Metric API.

This module defines a user type that exercises the metrics
endpoints of Athena. It simulates common operations including
creating a metric record and retrieving it by id.
"""
from locust import between

from tasks.athena_base import AthenaBase
from tasks.metric.metrics import AddMetric, GetMetricById


class AthenaMetric(AthenaBase):
    """Locust user exercising Athena Metric endpoints."""

    wait_time = between(1, 2)

    tasks = [
        (AddMetric, 80),
        (GetMetricById, 20),
    ]


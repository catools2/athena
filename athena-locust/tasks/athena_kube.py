"""
Locust user class for the Athena Kubernetes API.

This module defines a user type that exercises the Kubernetes
pod endpoints of Athena. It simulates common pod operations
including listing, retrieving, and saving pod information.
"""
from locust import between

from tasks.athena_base import AthenaBase
from tasks.kube.pods import AddPod, GetPodById, GetPodByNameAndNamespace, ListPods


class AthenaKube(AthenaBase):
    """Locust user exercising Athena Kubernetes endpoints."""

    wait_time = between(1, 2)

    tasks = [
        (AddPod, 100),
        (GetPodById, 5),
        (GetPodByNameAndNamespace, 20),
        (ListPods, 5),
    ]


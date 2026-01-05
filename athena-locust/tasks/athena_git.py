"""
Locust user class for the Athena Git API.

This module defines a user type that exercises the Git repository
and commit endpoints of Athena. It imports task definitions from
the ``tasks.git`` subpackage and assigns them weighted probabilities
to simulate realistic traffic patterns.
"""
from locust import between

from tasks.athena_base import AthenaBase
from tasks.git.commits import AddCommit, GetCommitById, SearchCommitByHash
from tasks.git.repos import AddRepository, GetRepositoryById, SearchRepositoryByKeyword


class AthenaGit(AthenaBase):
    """Locust user exercising Athena Git endpoints."""

    wait_time = between(1, 2)

    tasks = [
        (AddRepository, 2),
        (AddCommit, 200),
        (GetRepositoryById, 5),
        (GetCommitById, 5),
        (SearchRepositoryByKeyword, 50),
        (SearchCommitByHash, 10),
    ]


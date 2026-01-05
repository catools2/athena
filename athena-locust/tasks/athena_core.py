from locust import between

from tasks.athena_base import AthenaBase
from tasks.core.environments import AddEnvironment, GetEnvironmentById, UpdateEnvironment
from tasks.core.projects import AddProject, GetProjectById, UpdateProject
from tasks.core.users import AddUser, GetUserById, UpdateUser
from tasks.core.versions import AddVersion, GetVersionById, UpdateVersion


class AthenaCore(AthenaBase):
    wait_time = between(1, 2)
    tasks = [(AddProject, 1),
             (AddEnvironment, 1),
             (AddVersion, 1),
             (AddUser, 10),
             (UpdateProject, 1),
             (UpdateEnvironment, 1),
             (UpdateVersion, 1),
             (UpdateUser, 5),
             (GetProjectById, 3),
             (GetEnvironmentById, 3),
             (GetVersionById, 3),
             (GetUserById, 70)
             ]

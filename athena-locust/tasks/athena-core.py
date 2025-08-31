from locust import between

from tasks.athena_base import AthenaBase
from tasks.core.environments import *
from tasks.core.projects import *
from tasks.core.users import *
from tasks.core.versions import *


class AthenaCore(AthenaBase):
    wait_time = between(1, 2)
    tasks = [(AddProject, 1),
             (AddEnvironment, 1),
             (AddVersion, 1),
             (AddUser, 13),
             (UpdateProject, 1),
             (UpdateEnvironment, 1),
             (UpdateVersion, 1),
             (UpdateUser, 1),
             (GetProjectById, 5),
             (GetEnvironmentById, 5),
             (GetVersionById, 5),
             (GetUserById, 65)
             ]

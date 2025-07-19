from locust import between, FastHttpUser

from tasks.core.environments import AddEnvironment, GetEnvironmentById, UpdateEnvironment
from tasks.core.projects import AddProject, GetProjectById, UpdateProject
from tasks.core.users import AddUser, GetUserById, UpdateUser
from tasks.core.versions import AddVersion, GetVersionById, UpdateVersion


class AthenaCore(FastHttpUser):
    tasks = {AddProject: 1, GetProjectById: 5, UpdateProject: 1,
             AddEnvironment: 1, GetEnvironmentById: 5, UpdateEnvironment: 1,
             AddVersion: 1, GetVersionById: 5, UpdateVersion: 1,
             AddUser: 13, GetUserById: 65, UpdateUser: 1}
    wait_time = between(1, 3)

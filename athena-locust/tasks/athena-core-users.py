from locust import between, FastHttpUser

from tasks.athena_base import AthenaBase
from tasks.core.add_users import AddUser
from tasks.core.get_user_by_id import GetUserById
from tasks.core.update_users import UpdateUser


class AthenaCore(FastHttpUser, AthenaBase):
    tasks = {AddUser: 30, GetUserById: 90, UpdateUser: 5}
    wait_time = between(1, 3)

import json
import random
import string
from locust import HttpUser, between, task


def read_file(file_path):
    try:
        with open(file_path, 'r') as file:
            data = json.load(file)
            return data
    except FileNotFoundError:
        print(f"Error: File not found: {file_path}")
        return None
    except json.JSONDecodeError:
        print(f"Error: Invalid JSON format in: {file_path}")
        return None
    except Exception as e:
        print(f"An unexpected error occurred: {e}")
        return None


def random_string(length):
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for i in range(length))


users = read_file("/mnt/locust/data/users.json")


def random_user():
    return random.choice(users)


class AthenaCore(HttpUser):
    wait_time = between(1, 2)

    @task(1)
    def add_user(self):
        self.client.post("/core/user", name="Add User", json={
            "username": f"{random_user().get('fn')}.{random_user().get('ln')}",
            "aliases": [
                {
                    "alias": random_string(8)
                },
                {
                    "alias": random_string(9)
                },
                {
                    "alias": random_string(10)
                }
            ]
        })

    @task(2)
    def get_user(self):
        self.client.get(f"/core/user/{random.randint(0, 10)}", name="Get User")

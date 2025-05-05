import random
from pathlib import Path

from utils.file_utils import read_file


root_dir = Path(__file__).resolve().parent.parent
users = read_file(root_dir / "data/users.json")


def get_random_user():
    return random.choice(users)

from random import Random

from tasks.athena_task_set import AthenaTaskSet
from utils.logger_utils import get_logger

random = Random()
logger = get_logger(__name__)


class GitTaskSet(AthenaTaskSet):
    abstract = True

    repositories = []
    commits = []

    # -------------------------------------------
    # Git Repository helpers
    def add_repository(self) -> None:
        # Minimal repo payload
        repo = {
            "name": f"repo-{random.randint(1000, 9999)}",
            "url": f"https://example.org/{random.randint(10000, 99999)}.git"
        }
        with self.client.post("/git/repo", name="AddGitRepository", json=repo) as response:
            if response.status_code == 201:
                repo["id"] = int(response.headers.get("entity_id"))
                GitTaskSet.repositories.append(repo)

    @staticmethod
    def get_repository():
        if not GitTaskSet.repositories:
            return None
        return random.choice(GitTaskSet.repositories)

    # -------------------------------------------
    # Commit helpers
    def add_commit(self) -> None:
        repo = GitTaskSet.get_repository()
        if not repo:
            self.add_repository()
            repo = GitTaskSet.get_repository()
            if not repo:
                return

        user = GitTaskSet.get_user()
        if not user:
            self.add_user()

        from test_data.git_faker import build_commit
        commit = build_commit(repository=repo.get("name"))

        with self.client.post("/git/commit", name="AddCommit", json=commit) as response:
            if response.status_code == 201:
                commit["id"] = int(response.headers.get("entity_id"))
                GitTaskSet.commits.append(commit)

    @staticmethod
    def get_commit():
        if not GitTaskSet.commits:
            return None
        return random.choice(GitTaskSet.commits)

from locust import task

from tasks.athena_task_set import AthenaTaskSet
from tasks.git.git_task_set import GitTaskSet


class AddCommit(GitTaskSet):

    @task
    def add_commit_task(self):
        self.add_commit()


class GetCommitById(GitTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(GitTaskSet.commits) < 3:
            self.add_commit()

    @task
    def get_commit_task(self):
        commit = GitTaskSet.get_commit()
        commit_id = commit.get("id") if commit else 1
        self.client.get(f"/git/commit/{commit_id}", name="GetCommitById")


class SearchCommitByHash(GitTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(GitTaskSet.commits) < 3:
            self.add_commit()

    @task
    def search_commit_task(self):
        commit = GitTaskSet.get_commit()
        commit_hash = commit.get("hash") if commit else "deadbeef"
        self.client.get(f"/git/commit?hash={commit_hash}", name="SearchCommitByHash")

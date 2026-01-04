from locust import task

from tasks.git.git_task_set import GitTaskSet


class AddRepository(GitTaskSet):

    @task
    def add_repository_task(self):
        self.add_repository()


class GetRepositoryById(GitTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(GitTaskSet.repositories) < 3:
            self.add_repository()

    @task
    def get_repository_task(self):
        repo = GitTaskSet.get_repository()
        repo_id = repo.get('id') if repo else 1
        self.client.get(f"/git/repo/{repo_id}", name="GetRepositoryById")


class SearchRepositoryByKeyword(GitTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(GitTaskSet.repositories) < 3:
            self.add_repository()

    @task
    def search_repository_task(self):
        repo = GitTaskSet.get_repository()
        keyword = (repo.get('name') or 'repo').split('-')[0] if repo else 'repo'
        self.client.get(f"/git/repo?keyword={keyword}", name="SearchRepositoryByKeyword")

from test_data.factories.git_factory import (
    RepositoryFactory,
    CommitFactory,
)


def build_repository(id: int | None = None) -> dict:
    result = RepositoryFactory.to_dict()
    if id is not None:
        result["id"] = id
    return result


def build_repository_name() -> str:
    return RepositoryFactory().name


def build_repository_url() -> str:
    return RepositoryFactory().url


def build_commit(repository: str = None, id: int | None = None) -> dict:
    result = CommitFactory.to_dict(repository)
    if id is not None:
        result["id"] = id
    return result


def build_commit_hash() -> str:
    return CommitFactory().hash


def build_commit_message() -> str:
    return CommitFactory().message




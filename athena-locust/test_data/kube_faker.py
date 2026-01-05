from test_data.factories.kube_factory import PodFactory


def build_pod(id: int | None = None) -> dict:
    result = PodFactory.to_dict()
    if id is not None:
        result["id"] = id
    return result


def build_pod_name() -> str:
    return PodFactory().name


def build_pod_namespace() -> str:
    return PodFactory().namespace


def build_pod_labels() -> dict:
    return PodFactory().labels


import random

from tasks.athena_task_set import AthenaTaskSet
from test_data.factories.kube_factory import (
    PodFactory,
)


class KubeTaskSet(AthenaTaskSet):
    abstract = True

    pods = []

    def add_pod(self):
        proj = AthenaTaskSet.get_project()
        if not proj:
            self.add_project()
            proj = AthenaTaskSet.get_project()

        pod = PodFactory.to_dict(project_code=proj["code"])
        with self.client.post("/kube/pod", name="AddPod", json=pod) as response:
            if response.status_code == 201:
                pod["id"] = int(response.headers.get("entity_id"))
                KubeTaskSet.pods.append(pod)

    @staticmethod
    def get_pod():
        if not KubeTaskSet.pods:
            return None
        return random.choice(KubeTaskSet.pods)

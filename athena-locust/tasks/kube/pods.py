import json

from locust import task

from tasks.kube.kube_task_set import KubeTaskSet


class AddPod(KubeTaskSet):

    @task
    def save_pod(self):
        self.add_pod()


class ListPods(KubeTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(KubeTaskSet.pods) < 3:
            self.add_pod()

    @task
    def list_pods(self):
        pod = KubeTaskSet.get_pod()
        if not pod or not pod.get('name'):
            return

        self.client.get(
            f"/kube/pods?project={pod.get('project')}&namespace={pod.get('namespace')}",
            name="ListPods",
        )


class GetPodById(KubeTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(KubeTaskSet.pods) < 3:
            self.add_pod()

    @task
    def get_pod_by_id(self):
        pod = KubeTaskSet.get_pod()
        pod_id = (pod or {}).get('id', 1)
        self.client.get(f"/kube/pod/{pod_id}", name="GetPodById")


class GetPodByNameAndNamespace(KubeTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(KubeTaskSet.pods) < 3:
            self.add_pod()

    @task
    def get_pod_by_name_namespace(self):
        pod = KubeTaskSet.get_pod()
        if not pod or not pod.get('name'):
            return

        self.client.get(
            f"/kube/pod?name={pod.get('name')}&namespace={pod.get('namespace')}",
            name="GetPodByNameAndNamespace",
        )

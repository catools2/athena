from tasks.athena_task_set import AthenaTaskSet
from test_data.metric_faker import build_metric


class MetricTaskSet(AthenaTaskSet):
    abstract = True

    metrics = []

    def add_metric(self):
        proj = AthenaTaskSet.get_project_with_environments()
        if not proj:
            self.add_environment()

        metric = build_metric()
        with self.client.post("/metric/metric", name="AddMetric", json=metric) as response:
            if response.status_code in (200, 201, 208):
                metric["id"] = int(response.headers.get("entity_id"))
                MetricTaskSet.metrics.append(metric)

    @staticmethod
    def get_metric():
        if not MetricTaskSet.metrics:
            return None
        return MetricTaskSet.metrics[0]

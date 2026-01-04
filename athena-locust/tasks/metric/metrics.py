from locust import task

from tasks.metric.metric_task_set import MetricTaskSet


class AddMetric(MetricTaskSet):

    @task
    def add_metric_task(self):
        self.add_metric()


class GetMetricById(MetricTaskSet):

    def on_start(self) -> None:
        super().on_start()
        if len(MetricTaskSet.metrics) < 5:
            self.add_metric()

    @task
    def get_metric_by_id_task(self):
        metric = MetricTaskSet.get_metric()
        self.client.get(f"/metric/metric/{metric['id']}", name="GetMetricById")

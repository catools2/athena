from test_data.factories.metric_factory import MetricFactory


def build_metric() -> dict:
    return MetricFactory.to_dict()

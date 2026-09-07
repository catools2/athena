package org.catools.athena.metric.common.mapper;

import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.model.metrics.ActionDto;
import org.catools.athena.model.metrics.MetricDto;

public interface MetricMapper {

  ActionDto actionToActionDto(Action action);

  Action actionDtoToAction(ActionDto action);

  Metric metricDtoToMetric(MetricDto metric);

  MetricDto metricToMetricDto(Metric metric);

}

package org.catools.athena.metric.common.mapper;

import org.catools.athena.core.common.mapper.CoreMapperService;
import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.metrics.model.ActionDto;
import org.catools.athena.metrics.model.MetricDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {CoreMapperService.class})
public interface MetricMapper {

  ActionDto actionToActionDto(Action action);

  Action actionDtoToAction(ActionDto action);

  Metric metricDtoToMetric(MetricDto metric);

  @Mapping(source = "project.code", target = "project")
  @Mapping(source = "environment.code", target = "environment")
  MetricDto metricToMetricDto(Metric metric);

}

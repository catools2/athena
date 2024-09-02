package org.catools.athena.metric.common.mapper;

import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.metrics.model.ActionDto;
import org.catools.athena.metrics.model.MetricDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {MetricMapperService.class})
public interface MetricMapper {

  ActionDto actionToActionDto(Action action);

  Action actionDtoToAction(ActionDto action);

  @Mapping(source = "project", target = "projectId", qualifiedByName = {"getProjectId"})
  @Mapping(source = "environment", target = "environmentId", qualifiedByName = {"getEnvironmentId"})
  Metric metricDtoToMetric(MetricDto metric);

  @Mapping(source = "projectId", target = "project", qualifiedByName = {"getProjectCode"})
  @Mapping(source = "environmentId", target = "environment", qualifiedByName = {"getEnvironmentCode"})
  MetricDto metricToMetricDto(Metric metric);

}

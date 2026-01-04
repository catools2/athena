package org.catools.athena.metric.common.mapper;

import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.model.metrics.ActionDto;
import org.catools.athena.model.metrics.MetricDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {MetricMapperService.class})
public interface MetricMapper {

  ActionDto actionToActionDto(Action action);

  Action actionDtoToAction(ActionDto action);

  @Mapping(source = "project", target = "projectId", qualifiedByName = "getProjectId")
  @Mapping(target = "environmentId", expression = "java(metric.getEnvironment() == null ? null : metricMapperService.getEnvironmentId(metric.getProject(), metric.getEnvironment()))")
  Metric metricDtoToMetric(MetricDto metric);

  @Mapping(source = "projectId", target = "project", qualifiedByName = "getProjectCode")
  @Mapping(source = "environmentId", target = "environment", qualifiedByName = "getEnvironmentCode")
  MetricDto metricToMetricDto(Metric metric);

}

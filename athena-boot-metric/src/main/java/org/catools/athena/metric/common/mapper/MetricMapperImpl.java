package org.catools.athena.metric.common.mapper;

import lombok.RequiredArgsConstructor;
import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.model.metrics.ActionDto;
import org.catools.athena.model.metrics.MetricDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetricMapperImpl implements MetricMapper {

  private final MetricMapperService metricMapperService;

  @Override
  public ActionDto actionToActionDto(final Action action) {
    if (action == null) {
      return null;
    }

    return new ActionDto()
        .setId(action.getId())
        .setName(action.getName())
        .setCategory(action.getCategory())
        .setTarget(action.getTarget())
        .setType(action.getType())
        .setCommand(action.getCommand())
        .setParameter(action.getParameter());
  }

  @Override
  public Action actionDtoToAction(final ActionDto action) {
    if (action == null) {
      return null;
    }

    return new Action()
        .setId(action.getId())
        .setCategory(action.getCategory())
        .setName(action.getName())
        .setType(action.getType())
        .setTarget(action.getTarget())
        .setCommand(action.getCommand())
        .setParameter(action.getParameter());
  }

  @Override
  public Metric metricDtoToMetric(final MetricDto metric) {
    if (metric == null) {
      return null;
    }

    return new Metric()
        .setProjectId(metricMapperService.getProjectId(metric.getProject()))
        .setEnvironmentId(metric.getEnvironment() == null ? null : metricMapperService.getEnvironmentId(metric.getProject(), metric.getEnvironment()))
        .setId(metric.getId())
        .setDuration(metric.getDuration())
        .setActionTime(metric.getActionTime())
        .setAction(actionDtoToAction(metric.getAction()));
  }

  @Override
  public MetricDto metricToMetricDto(final Metric metric) {
    if (metric == null) {
      return null;
    }

    return new MetricDto()
        .setProject(metricMapperService.getProjectCode(metric.getProjectId()))
        .setEnvironment(metricMapperService.getEnvironmentCode(metric.getEnvironmentId()))
        .setId(metric.getId())
        .setDuration(metric.getDuration())
        .setActionTime(metric.getActionTime())
        .setAction(actionToActionDto(metric.getAction()));
  }
}
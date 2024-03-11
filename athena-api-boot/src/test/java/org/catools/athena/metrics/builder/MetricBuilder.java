package org.catools.athena.metrics.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.metrics.model.ActionDto;
import org.catools.athena.metrics.model.MetricDto;
import org.instancio.Instancio;

import java.util.List;

import static org.instancio.Select.field;

@UtilityClass
public class MetricBuilder {
  public static List<Metric> buildMetric(final Project project, final Environment environment) {
    return Instancio.of(Metric.class)
        .ignore(field(Metric::getId))
        .ignore(field(Action::getId))
        .set(field(Metric::getProject), project)
        .set(field(Metric::getEnvironment), environment)
        .stream()
        .limit(10)
        .toList();
  }

  public static MetricDto buildMetricDto(final Metric metric) {
    return new MetricDto()
        .setId(metric.getId())
        .setDuration(metric.getDuration())
        .setActionTime(metric.getActionTime())
        .setEnvironment(metric.getEnvironment().getCode())
        .setProject(metric.getProject().getCode())
        .setAction(buildActionDto(metric.getAction()));
  }

  public static ActionDto buildActionDto(final Action action) {
    return new ActionDto()
        .setId(action.getId())
        .setName(action.getName())
        .setType(action.getType())
        .setTarget(action.getTarget())
        .setCommand(action.getCommand())
        .setParameter(action.getParameter());
  }
}

package org.catools.athena.metric.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.ProjectDto;
import org.instancio.Instancio;

import java.util.List;

import static org.instancio.Select.field;

@UtilityClass
public class MetricBuilder {
  public static List<Metric> buildMetric(final ProjectDto project, final EnvironmentDto environment) {
    return Instancio.of(Metric.class)
        .ignore(field(Metric::getId))
        .ignore(field(Action::getId))
        .set(field(Metric::getProjectId), project.getId())
        .set(field(Metric::getEnvironmentId), environment.getId())
        .stream()
        .limit(10)
        .toList();
  }
}

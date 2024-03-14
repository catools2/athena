package org.catools.athena.metric.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
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
}

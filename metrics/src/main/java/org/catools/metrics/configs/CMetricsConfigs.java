package org.catools.metrics.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.metrics.model.CMetricEnvironment;
import org.catools.metrics.model.CMetricProject;

public class CMetricsConfigs {
  public static final String PERFORMANCE_SCHEMA = "performance";

  public static boolean isEnabled() {
    return CHocon.asBoolean(Configs.CATOOLS_METRICS_ENABLED);
  }

  public static CMetricProject getProject() {
    return new CMetricProject(
        CHocon.asString(Configs.CATOOLS_METRICS_PROJECT_CODE),
        CHocon.asString(Configs.CATOOLS_METRICS_PROJECT_NAME)
    );
  }

  public static CMetricEnvironment getEnvironment() {
    return new CMetricEnvironment(
        CHocon.asString(Configs.CATOOLS_METRICS_ENVIRONMENT_CODE),
        CHocon.asString(Configs.CATOOLS_METRICS_ENVIRONMENT_NAME)
    );
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_METRICS_ENABLED("catools.metrics.enabled"),
    CATOOLS_METRICS_PROJECT_CODE("catools.metrics.project.code"),
    CATOOLS_METRICS_PROJECT_NAME("catools.metrics.project.name"),
    CATOOLS_METRICS_ENVIRONMENT_CODE("catools.metrics.environment.code"),
    CATOOLS_METRICS_ENVIRONMENT_NAME("catools.metrics.environment.name");

    private final String path;
  }
}

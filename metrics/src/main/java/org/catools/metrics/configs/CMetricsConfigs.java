package org.catools.metrics.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.metrics.model.CMetricEnvironment;
import org.catools.metrics.model.CMetricProject;

@UtilityClass
public class CMetricsConfigs {
  public static final String PERFORMANCE_SCHEMA = "performance";

  public static boolean isWebRecorderEnabled() {
    return CHocon.asBoolean(Configs.CATOOLS_PERFMETRIC_WEB_RECORDER_ENABLED);
  }

  public static boolean isSqlRecorderEnabled() {
    return CHocon.asBoolean(Configs.CATOOLS_PERFMETRIC_SQL_RECORDER_ENABLED);
  }

  public static boolean isWebServiceRecorderEnabled() {
    return CHocon.asBoolean(Configs.CATOOLS_PERFMETRIC_WEB_SERVICE_RECORDER_ENABLED);
  }

  public static CMetricProject getProject() {
    return new CMetricProject(
        CHocon.asString(Configs.CATOOLS_PERFMETRIC_PROJECT_CODE),
        CHocon.asString(Configs.CATOOLS_PERFMETRIC_PROJECT_NAME)
    );
  }

  public static CMetricEnvironment getEnvironment() {
    return new CMetricEnvironment(
        CHocon.asString(Configs.CATOOLS_PERFMETRIC_ENVIRONMENT_CODE),
        CHocon.asString(Configs.CATOOLS_PERFMETRIC_ENVIRONMENT_NAME)
    );
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_PERFMETRIC_WEB_SERVICE_RECORDER_ENABLED("catools.perfmetric.web_service_recorder_enabled"),
    CATOOLS_PERFMETRIC_WEB_RECORDER_ENABLED("catools.perfmetric.web_recorder_enabled"),
    CATOOLS_PERFMETRIC_SQL_RECORDER_ENABLED("catools.perfmetric.sql_recorder_enabled"),
    CATOOLS_PERFMETRIC_PROJECT_CODE("catools.perfmetric.project.code"),
    CATOOLS_PERFMETRIC_PROJECT_NAME("catools.perfmetric.project.name"),
    CATOOLS_PERFMETRIC_ENVIRONMENT_CODE("catools.perfmetric.environment.code"),
    CATOOLS_PERFMETRIC_ENVIRONMENT_NAME("catools.perfmetric.environment.name");

    private final String path;
  }}

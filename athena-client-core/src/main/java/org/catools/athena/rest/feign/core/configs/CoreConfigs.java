package org.catools.athena.rest.feign.core.configs;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;

@UtilityClass
public class CoreConfigs {

  private static final String DEFAULT = "default";

  static {
    reload();
  }

  @Setter
  @Getter
  private static String athenaHost;

  @Setter
  @Getter
  private static String projectName;

  @Setter
  @Getter
  private static String projectCode;

  @Setter
  @Getter
  private static String environmentName;

  @Setter
  @Getter
  private static String environmentCode;

  @Setter
  @Getter
  private static String versionName;

  @Setter
  @Getter
  private static String versionCode;

  @Setter
  @Getter
  private static Integer startAt;

  @Setter
  @Getter
  private static Integer bufferSize;

  @Setter
  @Getter
  private static Integer threadsCount;

  @Setter
  @Getter
  private static Long timeoutInMinutes;

  public static void reload() {
    athenaHost = ConfigUtils.getString("athena.host", "http://localhost:8080");
    projectName = ConfigUtils.getString("athena.project.name", DEFAULT);
    projectCode = ConfigUtils.getString("athena.project.code", DEFAULT);
    environmentName = ConfigUtils.getString("athena.environment.name", DEFAULT);
    environmentCode = ConfigUtils.getString("athena.environment.code", DEFAULT);
    versionName = ConfigUtils.getString("athena.version.name", DEFAULT);
    versionCode = ConfigUtils.getString("athena.version.code", DEFAULT);
    startAt = ConfigUtils.getInteger("athena.start_at", 0);
    bufferSize = ConfigUtils.getInteger("athena.buffer_size", 50);
    threadsCount = ConfigUtils.getInteger("athena.threads_count", 10);
    timeoutInMinutes = ConfigUtils.getLong("athena.timeout", 60 * 2L);
  }

  public static ProjectDto getProject() {
    return new ProjectDto(projectCode, projectName);
  }

  public static EnvironmentDto getEnvironment() {
    return new EnvironmentDto(environmentCode, environmentName, projectCode);
  }

  public static VersionDto getVersion() {
    return new VersionDto(versionCode, versionName, projectCode);
  }

}

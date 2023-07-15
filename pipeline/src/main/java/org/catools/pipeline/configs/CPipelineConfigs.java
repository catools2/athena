package org.catools.pipeline.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.collections.CList;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CStringUtil;
import org.catools.common.utils.CSystemUtil;
import org.catools.pipeline.model.CPipelineEnvironment;
import org.catools.pipeline.model.CPipelineMetaData;
import org.catools.pipeline.model.CPipelineProject;
import org.catools.pipeline.model.CPipelineUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CPipelineConfigs {
  public static final String PIPELINE_SCHEMA = "pipeline";

  public static String getPipelineType() {
    return CHocon.asString(Configs.CATOOLS_PIPELINE_TYPE);
  }

  public static String getPipelineName() {
    return CHocon.asString(Configs.CATOOLS_PIPELINE_NAME);
  }

  public static String getPipelineNumber() {
    return CHocon.asString(Configs.CATOOLS_PIPELINE_NUMBER);
  }

  public static String getPipelineDescription() {
    return CHocon.asString(Configs.CATOOLS_PIPELINE_DESCRIPTION);
  }

  public static List<CPipelineMetaData> getPipelineMetaData() {
    List<CPipelineMetaData> metaData = new ArrayList<>();
    for (Object object : CHocon.get(Configs.CATOOLS_PIPELINE_METADATA).asObjects(new ArrayList<>())) {
      Map<Object, Object> map = (Map<Object, Object>) object;
      metaData.add(new CPipelineMetaData(String.valueOf(map.get("name")), String.valueOf(map.get("value"))));
    }
    return metaData;
  }

  public static CPipelineMetaData getPipelineMetaData(String metadataName) {
    return CList.of(getPipelineMetaData()).getFirstOrNull(m -> CStringUtil.equalsAnyIgnoreCase(m.getName(), metadataName));
  }

  public static String getPipelineMetaData(String metadataName, String defaultValue) {
    CPipelineMetaData pipelineMetaData = getPipelineMetaData(metadataName);
    return pipelineMetaData == null ? defaultValue : pipelineMetaData.getValue();
  }

  public static CPipelineEnvironment getEnvironment() {
    return new CPipelineEnvironment(getEnvironmentCode(), getEnvironmentName());
  }

  public static String getEnvironmentCode() {
    return CHocon.asString(Configs.CATOOLS_PIPELINE_ENVIRONMENT_CODE);
  }

  public static String getEnvironmentName() {
    return CHocon.asString(Configs.CATOOLS_PIPELINE_ENVIRONMENT_NAME);
  }

  public static CPipelineProject getProject() {
    return new CPipelineProject(getProjectCode(), getProjectName());
  }

  public static String getProjectCode() {
    return CHocon.asString(Configs.CATOOLS_PIPELINE_PROJECT_CODE);
  }

  public static String getProjectName() {
    return CHocon.asString(Configs.CATOOLS_PIPELINE_PROJECT_NAME);
  }

  public static CPipelineUser getExecutor() {
    return new CPipelineUser(getExecutorName());
  }

  public static String getExecutorName() {
    if (CHocon.has(Configs.CATOOLS_PIPELINE_EXECUTOR_NAME) && CStringUtil.isNotBlank(CHocon.asString(Configs.CATOOLS_PIPELINE_EXECUTOR_NAME)))
      return CHocon.asString(Configs.CATOOLS_PIPELINE_EXECUTOR_NAME);

    return CSystemUtil.getUserName();
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_PIPELINE_TYPE("catools.pipeline.type"),
    CATOOLS_PIPELINE_NAME("catools.pipeline.name"),
    CATOOLS_PIPELINE_DESCRIPTION("catools.pipeline.description"),
    CATOOLS_PIPELINE_NUMBER("catools.pipeline.number"),
    CATOOLS_PIPELINE_METADATA("catools.pipeline.metadata"),
    CATOOLS_PIPELINE_ENVIRONMENT_CODE("catools.pipeline.environment.code"),
    CATOOLS_PIPELINE_ENVIRONMENT_NAME("catools.pipeline.environment.name"),
    CATOOLS_PIPELINE_PROJECT_CODE("catools.pipeline.project.code"),
    CATOOLS_PIPELINE_PROJECT_NAME("catools.pipeline.project.name"),
    CATOOLS_PIPELINE_EXECUTOR_NAME("catools.pipeline.executor.name");

    private final String path;
  }
}

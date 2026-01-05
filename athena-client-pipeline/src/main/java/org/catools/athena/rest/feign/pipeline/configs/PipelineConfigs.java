package org.catools.athena.rest.feign.pipeline.configs;

import lombok.Getter;
import lombok.Setter;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;

import java.util.Set;

public class PipelineConfigs {

  static {
    reload();
  }

  @Setter
  @Getter
  private static boolean createNewPipelineEnabled;

  @Setter
  @Getter
  private static String executorName;

  @Setter
  @Getter
  private static String pipelineName;

  @Setter
  @Getter
  private static String pipelineNumber;

  @Setter
  @Getter
  private static String pipelineDescription;

  @Setter
  @Getter
  private static Set<MetadataDto> pipelineMetadata;

  @Setter
  @Getter
  private static Set<MetadataDto> pipelineExecutionMetadata;

  public static void reload() {
    createNewPipelineEnabled = ConfigUtils.getBoolean("athena.pipeline.always_create_new_pipeline", false);
    executorName = ConfigUtils.getString("athena.pipeline.executor.name", System.getProperty("user.name"));
    pipelineName = ConfigUtils.getString("athena.pipeline.name", "Local");
    pipelineNumber = ConfigUtils.getString("athena.pipeline.number", "1");
    pipelineDescription = ConfigUtils.getString("athena.pipeline.description", "All local execution");
    pipelineMetadata = ConfigUtils.getMetadataSet("athena.pipeline.metadata");
    pipelineExecutionMetadata = ConfigUtils.getMetadataSet("athena.pipeline.execution.metadata");
  }
}

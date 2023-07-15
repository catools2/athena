package org.catools.pipeline.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;

public class CPipelineTestNGConfigs {
  public static boolean isEnabled() {
    return CHocon.asBoolean(Configs.CATOOLS_PIPELINE_LISTENER_ENABLED);
  }

  public static boolean always_create_new_pipeline() {
    return CHocon.asBoolean(Configs.CATOOLS_PIPELINE_LISTENER_ALWAYS_CREATE_NEW_PIPELINE);
  }

  public static boolean createPipelineIfNotExist() {
    return CHocon.asBoolean(Configs.CATOOLS_PIPELINE_LISTENER_CREATE_IF_NOT_EXIST);
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_PIPELINE_LISTENER_ENABLED("catools.pipeline.listener.enabled"),
    CATOOLS_PIPELINE_LISTENER_ALWAYS_CREATE_NEW_PIPELINE("catools.pipeline.listener.always_create_new_pipeline"),
    CATOOLS_PIPELINE_LISTENER_CREATE_IF_NOT_EXIST("catools.pipeline.listener.create_if_not_exist");

    private final String path;
  }
}

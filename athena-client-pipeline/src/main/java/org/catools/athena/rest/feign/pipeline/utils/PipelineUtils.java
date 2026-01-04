package org.catools.athena.rest.feign.pipeline.utils;

import feign.FeignException;
import lombok.experimental.UtilityClass;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.pipeline.clients.ExecutionClient;
import org.catools.athena.rest.feign.pipeline.clients.ExecutionStatusClient;
import org.catools.athena.rest.feign.pipeline.clients.PipelineClient;
import org.catools.athena.rest.feign.pipeline.clients.ScenarioExecutionClient;

import java.util.Optional;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getClient;

@UtilityClass
public class PipelineUtils {
  private static final ExecutionStatusClient EXECUTION_STATUS_CLIENT = getClient(ExecutionStatusClient.class, CoreConfigs.getAthenaHost());


  public static Optional<PipelineDto> getPipeline(final String pipelineName,
                                                  final String pipelineNumber,
                                                  final String versionCode,
                                                  final String environmentCode) {
    try {
      return Optional.ofNullable(getClient(PipelineClient.class, CoreConfigs.getAthenaHost()).getPipeline(pipelineName,
          pipelineNumber,
          versionCode,
          environmentCode));
    } catch (FeignException.NotFound ex) {
      return Optional.empty();
    }
  }

  public static PipelineExecutionStatusDto getExecutionStatus(PipelineExecutionStatusDto executionStatusDto) {
    return Optional.ofNullable(EXECUTION_STATUS_CLIENT.getExecutionStatus(executionStatusDto.getName())).orElseGet(() -> {
      EXECUTION_STATUS_CLIENT.saveExecutionStatus(executionStatusDto);
      return EXECUTION_STATUS_CLIENT.getExecutionStatus(executionStatusDto.getName());
    });
  }


  public static PipelineClient getPipelineClient() {
    return getClient(PipelineClient.class, CoreConfigs.getAthenaHost());
  }

  public static ExecutionClient getExecutionClient() {
    return getClient(ExecutionClient.class, CoreConfigs.getAthenaHost());
  }

  public static ScenarioExecutionClient getScenarioExecutionClient() {
    return getClient(ScenarioExecutionClient.class, CoreConfigs.getAthenaHost());
  }
}

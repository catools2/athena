package org.catools.athena.rest.feign.pipeline.helpers;

import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.model.pipeline.PipelineExecutionDto;
import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;
import org.catools.athena.model.pipeline.PipelineScenarioExecutionDto;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.pipeline.cache.PipelineCache;
import org.catools.athena.rest.feign.pipeline.configs.PipelineConfigs;
import org.catools.athena.rest.feign.pipeline.utils.PipelineUtils;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getEntityId;


@Slf4j
public class PipelineHelper {

  public static synchronized PipelineDto getPipeline() {
    return PipelineHelper.getPipeline(CoreConfigs.getAthenaHost(),
        CoreConfigs.getProject(),
        CoreConfigs.getVersion(),
        CoreConfigs.getEnvironment(),
        PipelineConfigs.getPipelineName(),
        PipelineConfigs.getPipelineNumber(),
        PipelineConfigs.getPipelineDescription(),
        PipelineConfigs.getPipelineMetadata());
  }

  public static synchronized PipelineDto getPipeline(final String host,
                                                     ProjectDto project,
                                                     VersionDto version,
                                                     EnvironmentDto environment,
                                                     String pipelineName,
                                                     String pipelineNumber,
                                                     String pipelineDescription,
                                                     Set<MetadataDto> metadataDto) {
    setupDependencies(host, project, version, environment);
    return Optional.ofNullable(PipelineUtils.getPipelineClient().getPipeline(pipelineName, pipelineNumber, version.getCode(), environment.getCode()))
        .orElse(buildPipeline(host, project, version, environment, pipelineName, pipelineNumber, pipelineDescription, metadataDto));
  }

  public static PipelineDto finishPipeline(PipelineDto pipeline) {
    return updatePipelineEndDate(pipeline, Instant.now());
  }

  public static PipelineDto updatePipelineEndDate(PipelineDto pipeline, Instant endDate) {
    return PipelineUtils.getPipelineClient().updatePipelineEndDate(pipeline.getId(), endDate);
  }

  public static Optional<Long> addScenarioExecution(PipelineScenarioExecutionDto execution) {
    execution.setExecutor(getUser(execution.getExecutor()).getUsername());
    execution.setStatus(getStatus(execution.getStatus()).getName());
    Response response = PipelineUtils.getScenarioExecutionClient().saveScenarioExecution(execution);
    return getEntityId(response);
  }

  public static Optional<Long> addExecution(PipelineExecutionDto execution) {
    execution.setExecutor(getUser(execution.getExecutor()).getUsername());
    execution.setStatus(getStatus(execution.getStatus()).getName());

    Response response = PipelineUtils.getExecutionClient().saveExecution(execution);
    return getEntityId(response);
  }

  public static PipelineDto buildPipeline() {
    return PipelineHelper.buildPipeline(CoreConfigs.getAthenaHost(),
        CoreConfigs.getProject(),
        CoreConfigs.getVersion(),
        CoreConfigs.getEnvironment(),
        PipelineConfigs.getPipelineName(),
        PipelineConfigs.getPipelineNumber(),
        PipelineConfigs.getPipelineDescription(),
        PipelineConfigs.getPipelineMetadata());
  }

  public static synchronized PipelineDto buildPipeline(final String host,
                                                       ProjectDto project,
                                                       VersionDto version,
                                                       EnvironmentDto environment,
                                                       String pipelineName,
                                                       String pipelineNumber,
                                                       String pipelineDescription,
                                                       Set<MetadataDto> metadataDto) {

    setupDependencies(host, project, version, environment);
    final PipelineDto pipeline = new PipelineDto().setName(pipelineName)
        .setDescription(pipelineDescription)
        .setNumber(pipelineNumber)
        .setStartDate(Instant.now())
        .setEnvironment(environment.getCode())
        .setVersion(version.getCode())
        .setMetadata(metadataDto);

    return PipelineUtils.getPipeline(pipeline.getName(), pipeline.getNumber(), pipeline.getVersion(), pipeline.getEnvironment()).orElseGet(() -> {
      Set<MetadataDto> metadata = new HashSet<>();

      for (MetadataDto md : pipeline.getMetadata()) {
        metadata.add(new MetadataDto().setName(md.getName()).setValue(md.getValue()));
      }

      PipelineDto pipelineToSave = new PipelineDto().setName(pipeline.getName())
          .setNumber(pipeline.getNumber())
          .setEnvironment(environment.getCode())
          .setVersion(version.getCode())
          .setDescription(pipeline.getDescription())
          .setStartDate(pipeline.getStartDate())
          .setEndDate(pipeline.getEndDate())
          .setMetadata(metadata);

      Response response = PipelineUtils.getPipelineClient().savePipeline(pipelineToSave);
      getEntityId(response).map(pipeline::setId);
      return pipeline;
    });
  }

  private synchronized static UserDto getUser(final String username) {
    return CoreCache.readUser(new UserDto(username));
  }

  private synchronized static PipelineExecutionStatusDto getStatus(final String status) {
    return PipelineCache.readPipelineExecutionStatus(new PipelineExecutionStatusDto(status));
  }

  private static void setupDependencies(String host, ProjectDto project, VersionDto version, EnvironmentDto environment) {
    CoreConfigs.setAthenaHost(host);
    CoreCache.readProject(project);
    CoreCache.readVersion(version);
    CoreCache.readEnvironment(environment);
  }
}

package org.catools.athena.pipeline.common.mapper;

import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.entity.PipelineExecution;
import org.catools.athena.pipeline.common.entity.PipelineExecutionMetadata;
import org.catools.athena.pipeline.common.entity.PipelineExecutionStatus;
import org.catools.athena.pipeline.common.entity.PipelineMetadata;
import org.catools.athena.pipeline.common.entity.PipelineScenarioExecution;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {PipelineMapperService.class})
public interface PipelineMapper {

  @Mapping(source = "versionId", target = "version", qualifiedByName = "getVersionCode")
  @Mapping(source = "environmentId", target = "environment", qualifiedByName = "getEnvironmentCode")
  PipelineDto pipelineToPipelineDto(Pipeline pipeline);

  @Mapping(source = "version", target = "versionId", qualifiedByName = "getVersionId")
  @Mapping(source = "environment", target = "environmentId", qualifiedByName = "getEnvironmentId")
  Pipeline pipelineDtoToPipeline(PipelineDto pipeline);

  @Mapping(source = "status", target = "status")
  @Mapping(source = "executor", target = "executorId", qualifiedByName = "getUserId")
  @Mapping(source = "pipelineId", target = "pipeline")
  PipelineExecution executionDtoToExecution(PipelineExecutionDto execution);

  @Mapping(source = "status.name", target = "status")
  @Mapping(source = "executorId", target = "executor", qualifiedByName = "getUsername")
  @Mapping(source = "pipeline.id", target = "pipelineId")
  PipelineExecutionDto executionToExecutionDto(PipelineExecution execution);

  @Mapping(source = "status", target = "status")
  @Mapping(source = "executor", target = "executorId", qualifiedByName = "getUserId")
  @Mapping(source = "pipelineId", target = "pipeline")
  PipelineScenarioExecution scenarioExecutionDtoToScenarioExecution(PipelineScenarioExecutionDto execution);

  @Mapping(source = "status.name", target = "status")
  @Mapping(source = "executorId", target = "executor", qualifiedByName = "getUsername")
  @Mapping(source = "pipeline.id", target = "pipelineId")
  PipelineScenarioExecutionDto scenarioExecutionToScenarioExecutionDto(PipelineScenarioExecution execution);

  MetadataDto pipelineMetadataToMetadataDto(PipelineMetadata metadata);

  PipelineMetadata metadataDtoToPipelineMetadata(MetadataDto metadata);

  MetadataDto pipelineExecutionMetadataToMetadataDto(PipelineExecutionMetadata metadata);

  PipelineExecutionMetadata metadataDtoToPipelineExecutionMetadata(MetadataDto metadata);

  PipelineExecutionStatus pipelineStatusDtoToPipelineStatus(PipelineExecutionStatusDto pipelineExecutionStatusDto);

  PipelineExecutionStatusDto pipelineStatusToPipelineStatusDto(PipelineExecutionStatus pipelineExecutionStatus);
}

package org.catools.athena.pipeline.common.mapper;

import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.model.pipeline.PipelineExecutionDto;
import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;
import org.catools.athena.model.pipeline.PipelineScenarioExecutionDto;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.entity.PipelineExecution;
import org.catools.athena.pipeline.common.entity.PipelineExecutionMetadata;
import org.catools.athena.pipeline.common.entity.PipelineExecutionStatus;
import org.catools.athena.pipeline.common.entity.PipelineMetadata;
import org.catools.athena.pipeline.common.entity.PipelineScenarioExecution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PipelineMapperService.class})
public interface PipelineMapper {

  @Mapping(source = "versionId", target = "version", qualifiedByName = "getVersionCode")
  @Mapping(source = "environmentId", target = "environment", qualifiedByName = "getEnvironmentCode")
  @Mapping(target = "project", expression = "java(pipeline.getVersionId() == null ? null : pipelineMapperService.getProjectByVersionId(pipeline.getVersionId()))")
  PipelineDto pipelineToPipelineDto(Pipeline pipeline);

  @Mapping(target = "versionId", expression = "java(pipeline.getVersion() == null ? null : pipelineMapperService.getVersionId(pipeline.getProject(), pipeline.getVersion()))")
  @Mapping(target = "environmentId", expression = "java(pipeline.getEnvironment() == null ? null : pipelineMapperService.getEnvironmentId(pipeline.getProject(), pipeline.getEnvironment()))")
  Pipeline pipelineDtoToPipeline(PipelineDto pipeline);

  @Mapping(source = "status", target = "status")
  @Mapping(source = "executor", target = "executorId", qualifiedByName = "getUserId")
  @Mapping(source = "pipelineId", target = "pipeline", qualifiedByName = "getPipelineById")
  PipelineExecution executionDtoToExecution(PipelineExecutionDto execution);

  @Mapping(source = "status.name", target = "status")
  @Mapping(source = "executorId", target = "executor", qualifiedByName = "getUsername")
  @Mapping(source = "pipeline.id", target = "pipelineId")
  PipelineExecutionDto executionToExecutionDto(PipelineExecution execution);

  @Mapping(source = "status", target = "status")
  @Mapping(source = "executor", target = "executorId", qualifiedByName = "getUserId")
  @Mapping(source = "pipelineId", target = "pipeline", qualifiedByName = "getPipelineById")
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

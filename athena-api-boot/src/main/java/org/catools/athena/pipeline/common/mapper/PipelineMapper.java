package org.catools.athena.pipeline.common.mapper;

import org.catools.athena.core.common.mapper.CoreMapperService;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.pipeline.common.entity.*;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {PipelineMapperService.class, CoreMapperService.class})
public interface PipelineMapper {

  @Mapping(source = "environment.code", target = "environmentCode")
  PipelineDto pipelineToPipelineDto(Pipeline pipeline);

  @Mapping(source = "environmentCode", target = "environment")
  Pipeline pipelineDtoToPipeline(PipelineDto pipeline);

  @Mapping(source = "status", target = "status")
  @Mapping(source = "executor", target = "executor")
  @Mapping(source = "pipelineId", target = "pipeline")
  PipelineExecution executionDtoToExecution(PipelineExecutionDto execution);

  @Mapping(source = "status.name", target = "status")
  @Mapping(source = "executor.username", target = "executor")
  @Mapping(source = "pipeline.id", target = "pipelineId")
  PipelineExecutionDto executionToExecutionDto(PipelineExecution execution);

  @Mapping(source = "status", target = "status")
  @Mapping(source = "executor", target = "executor")
  @Mapping(source = "pipelineId", target = "pipeline")
  PipelineScenarioExecution scenarioExecutionDtoToScenarioExecution(PipelineScenarioExecutionDto execution);

  @Mapping(source = "status.name", target = "status")
  @Mapping(source = "executor.username", target = "executor")
  @Mapping(source = "pipeline.id", target = "pipelineId")
  PipelineScenarioExecutionDto scenarioExecutionToScenarioExecutionDto(PipelineScenarioExecution execution);

  MetadataDto pipelineMetadataToMetadataDto(PipelineMetadata metadata);

  PipelineMetadata metadataDtoToPipelineMetadata(MetadataDto metadata);

  MetadataDto pipelineExecutionMetadataToMetadataDto(PipelineExecutionMetadata metadata);

  PipelineExecutionMetadata metadataDtoToPipelineExecutionMetadata(MetadataDto metadata);

  PipelineExecutionStatus pipelineStatusDtoToPipelineStatus(PipelineExecutionStatusDto pipelineExecutionStatusDto);

  PipelineExecutionStatusDto pipelineStatusToPipelineStatusDto(PipelineExecutionStatus pipelineExecutionStatus);
}

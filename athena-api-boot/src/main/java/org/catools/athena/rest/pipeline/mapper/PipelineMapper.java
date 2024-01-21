package org.catools.athena.rest.pipeline.mapper;

import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.core.mapper.CoreMapperService;
import org.catools.athena.rest.pipeline.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {PipelineMapperService.class, CoreMapperService.class})
public abstract class PipelineMapper {

  @Mapping(source = "environment.code", target = "environmentCode")
  public abstract PipelineDto pipelineToPipelineDto(Pipeline pipeline);

  @Mapping(source = "environmentCode", target = "environment")
  public abstract Pipeline pipelineDtoToPipeline(PipelineDto pipeline);

  @Mapping(source = "status", target = "status")
  @Mapping(source = "executor", target = "executor")
  @Mapping(source = "pipelineId", target = "pipeline")
  public abstract PipelineExecution executionDtoToExecution(PipelineExecutionDto execution);

  @Mapping(source = "status.name", target = "status")
  @Mapping(source = "executor.name", target = "executor")
  @Mapping(source = "pipeline.id", target = "pipelineId")
  public abstract PipelineExecutionDto executionToExecutionDto(PipelineExecution execution);

  @Mapping(source = "status", target = "status")
  @Mapping(source = "executor", target = "executor")
  @Mapping(source = "pipelineId", target = "pipeline")
  public abstract PipelineScenarioExecution scenarioExecutionDtoToScenarioExecution(PipelineScenarioExecutionDto execution);

  @Mapping(source = "status.name", target = "status")
  @Mapping(source = "executor.name", target = "executor")
  @Mapping(source = "pipeline.id", target = "pipelineId")
  public abstract PipelineScenarioExecutionDto scenarioExecutionToScenarioExecutionDto(PipelineScenarioExecution execution);

  public abstract MetadataDto pipelineMetadataToMetadataDto(PipelineMetadata metadata);

  public abstract PipelineMetadata metadataDtoToPipelineMetadata(MetadataDto metadata);

  public abstract MetadataDto pipelineExecutionMetadataToMetadataDto(PipelineExecutionMetadata metadata);

  public abstract PipelineExecutionMetadata metadataDtoToPipelineExecutionMetadata(MetadataDto metadata);

  public abstract PipelineExecutionStatus pipelineStatusDtoToPipelineStatus(PipelineExecutionStatusDto pipelineExecutionStatusDto);

  public abstract PipelineExecutionStatusDto pipelineStatusToPipelineStatusDto(PipelineExecutionStatus pipelineExecutionStatus);
}

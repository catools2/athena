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


public interface PipelineMapper {

  PipelineDto pipelineToPipelineDto(Pipeline pipeline);

  Pipeline pipelineDtoToPipeline(PipelineDto pipeline);

  PipelineExecution executionDtoToExecution(PipelineExecutionDto execution);

  PipelineExecutionDto executionToExecutionDto(PipelineExecution execution);

  PipelineScenarioExecution scenarioExecutionDtoToScenarioExecution(PipelineScenarioExecutionDto execution);

  PipelineScenarioExecutionDto scenarioExecutionToScenarioExecutionDto(PipelineScenarioExecution execution);

  MetadataDto pipelineMetadataToMetadataDto(PipelineMetadata metadata);

  PipelineMetadata metadataDtoToPipelineMetadata(MetadataDto metadata);

  MetadataDto pipelineExecutionMetadataToMetadataDto(PipelineExecutionMetadata metadata);

  PipelineExecutionMetadata metadataDtoToPipelineExecutionMetadata(MetadataDto metadata);

  PipelineExecutionStatus pipelineStatusDtoToPipelineStatus(PipelineExecutionStatusDto pipelineExecutionStatusDto);

  PipelineExecutionStatusDto pipelineStatusToPipelineStatusDto(PipelineExecutionStatus pipelineExecutionStatus);
}

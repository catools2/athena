package org.catools.athena.pipeline.common.mapper;

import java.util.LinkedHashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PipelineMapperImpl implements PipelineMapper {

  private final PipelineMapperService pipelineMapperService;

  @Override
  public PipelineDto pipelineToPipelineDto(final Pipeline pipeline) {
    if (pipeline == null) {
      return null;
    }

    return new PipelineDto()
        .setVersion(pipelineMapperService.getVersionCode(pipeline.getVersionId()))
        .setEnvironment(pipelineMapperService.getEnvironmentCode(pipeline.getEnvironmentId()))
        .setId(pipeline.getId())
        .setName(pipeline.getName())
        .setDescription(pipeline.getDescription())
        .setNumber(pipeline.getNumber())
        .setStartDate(pipeline.getStartDate())
        .setEndDate(pipeline.getEndDate())
        .setMetadata(pipelineMetadataSetToMetadataDtoSet(pipeline.getMetadata()))
        .setProject(pipeline.getVersionId() == null ? null : pipelineMapperService.getProjectByVersionId(pipeline.getVersionId()));
  }

  @Override
  public Pipeline pipelineDtoToPipeline(final PipelineDto pipeline) {
    if (pipeline == null) {
      return null;
    }

    return new Pipeline()
        .setId(pipeline.getId())
        .setName(pipeline.getName())
        .setDescription(pipeline.getDescription())
        .setNumber(pipeline.getNumber())
        .setStartDate(pipeline.getStartDate())
        .setEndDate(pipeline.getEndDate())
        .setMetadata(metadataDtoSetToPipelineMetadataSet(pipeline.getMetadata()))
        .setVersionId(pipeline.getVersion() == null ? null : pipelineMapperService.getVersionId(pipeline.getProject(), pipeline.getVersion()))
        .setEnvironmentId(pipeline.getEnvironment() == null ? null : pipelineMapperService.getEnvironmentId(pipeline.getProject(), pipeline.getEnvironment()));
  }

  @Override
  public PipelineExecution executionDtoToExecution(final PipelineExecutionDto execution) {
    if (execution == null) {
      return null;
    }

    return new PipelineExecution()
        .setStatus(pipelineMapperService.getPipelineStatusByName(execution.getStatus()))
        .setExecutorId(pipelineMapperService.getUserId(execution.getExecutor()))
        .setPipeline(pipelineMapperService.getPipelineById(execution.getPipelineId()))
        .setId(execution.getId())
        .setPackageName(execution.getPackageName())
        .setClassName(execution.getClassName())
        .setMethodName(execution.getMethodName())
        .setParameters(execution.getParameters())
        .setStartTime(execution.getStartTime())
        .setEndTime(execution.getEndTime())
        .setTestStartTime(execution.getTestStartTime())
        .setTestEndTime(execution.getTestEndTime())
        .setBeforeClassStartTime(execution.getBeforeClassStartTime())
        .setBeforeClassEndTime(execution.getBeforeClassEndTime())
        .setBeforeMethodStartTime(execution.getBeforeMethodStartTime())
        .setBeforeMethodEndTime(execution.getBeforeMethodEndTime())
        .setMetadata(metadataDtoSetToPipelineExecutionMetadataSet(execution.getMetadata()));
  }

  @Override
  public PipelineExecutionDto executionToExecutionDto(final PipelineExecution execution) {
    if (execution == null) {
      return null;
    }

    return new PipelineExecutionDto()
        .setStatus(executionStatusName(execution))
        .setExecutor(pipelineMapperService.getUsername(execution.getExecutorId()))
        .setPipelineId(executionPipelineId(execution))
        .setId(execution.getId())
        .setPackageName(execution.getPackageName())
        .setClassName(execution.getClassName())
        .setMethodName(execution.getMethodName())
        .setParameters(execution.getParameters())
        .setStartTime(execution.getStartTime())
        .setEndTime(execution.getEndTime())
        .setTestStartTime(execution.getTestStartTime())
        .setTestEndTime(execution.getTestEndTime())
        .setBeforeClassStartTime(execution.getBeforeClassStartTime())
        .setBeforeClassEndTime(execution.getBeforeClassEndTime())
        .setBeforeMethodStartTime(execution.getBeforeMethodStartTime())
        .setBeforeMethodEndTime(execution.getBeforeMethodEndTime())
        .setMetadata(pipelineExecutionMetadataSetToMetadataDtoSet(execution.getMetadata()));
  }

  @Override
  public PipelineScenarioExecution scenarioExecutionDtoToScenarioExecution(final PipelineScenarioExecutionDto execution) {
    if (execution == null) {
      return null;
    }

    return new PipelineScenarioExecution()
        .setStatus(pipelineMapperService.getPipelineStatusByName(execution.getStatus()))
        .setExecutorId(pipelineMapperService.getUserId(execution.getExecutor()))
        .setPipeline(pipelineMapperService.getPipelineById(execution.getPipelineId()))
        .setId(execution.getId())
        .setFeature(execution.getFeature())
        .setScenario(execution.getScenario())
        .setParameters(execution.getParameters())
        .setStartTime(execution.getStartTime())
        .setEndTime(execution.getEndTime())
        .setBeforeScenarioStartTime(execution.getBeforeScenarioStartTime())
        .setBeforeScenarioEndTime(execution.getBeforeScenarioEndTime())
        .setMetadata(metadataDtoSetToPipelineExecutionMetadataSet(execution.getMetadata()));
  }

  @Override
  public PipelineScenarioExecutionDto scenarioExecutionToScenarioExecutionDto(final PipelineScenarioExecution execution) {
    if (execution == null) {
      return null;
    }

    return new PipelineScenarioExecutionDto()
        .setStatus(executionScenarioStatusName(execution))
        .setExecutor(pipelineMapperService.getUsername(execution.getExecutorId()))
        .setPipelineId(executionScenarioPipelineId(execution))
        .setId(execution.getId())
        .setFeature(execution.getFeature())
        .setScenario(execution.getScenario())
        .setParameters(execution.getParameters())
        .setStartTime(execution.getStartTime())
        .setEndTime(execution.getEndTime())
        .setBeforeScenarioStartTime(execution.getBeforeScenarioStartTime())
        .setBeforeScenarioEndTime(execution.getBeforeScenarioEndTime())
        .setMetadata(pipelineExecutionMetadataSetToMetadataDtoSet(execution.getMetadata()));
  }

  @Override
  public MetadataDto pipelineMetadataToMetadataDto(final PipelineMetadata metadata) {
    if (metadata == null) {
      return null;
    }

    return new MetadataDto()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }

  @Override
  public PipelineMetadata metadataDtoToPipelineMetadata(final MetadataDto metadata) {
    if (metadata == null) {
      return null;
    }

    return new PipelineMetadata()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }

  @Override
  public MetadataDto pipelineExecutionMetadataToMetadataDto(final PipelineExecutionMetadata metadata) {
    if (metadata == null) {
      return null;
    }

    return new MetadataDto()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }

  @Override
  public PipelineExecutionMetadata metadataDtoToPipelineExecutionMetadata(final MetadataDto metadata) {
    if (metadata == null) {
      return null;
    }

    return new PipelineExecutionMetadata()
        .setId(metadata.getId())
        .setName(metadata.getName())
        .setValue(metadata.getValue());
  }

  @Override
  public PipelineExecutionStatus pipelineStatusDtoToPipelineStatus(final PipelineExecutionStatusDto pipelineExecutionStatusDto) {
    if (pipelineExecutionStatusDto == null) {
      return null;
    }

    return new PipelineExecutionStatus()
        .setId(pipelineExecutionStatusDto.getId())
        .setName(pipelineExecutionStatusDto.getName());
  }

  @Override
  public PipelineExecutionStatusDto pipelineStatusToPipelineStatusDto(final PipelineExecutionStatus pipelineExecutionStatus) {
    if (pipelineExecutionStatus == null) {
      return null;
    }

    return new PipelineExecutionStatusDto()
        .setId(pipelineExecutionStatus.getId())
        .setName(pipelineExecutionStatus.getName());
  }

  private Set<MetadataDto> pipelineMetadataSetToMetadataDtoSet(final Set<PipelineMetadata> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<MetadataDto> mapped = new LinkedHashSet<>();
    for (PipelineMetadata item : metadata) {
      mapped.add(pipelineMetadataToMetadataDto(item));
    }
    return mapped;
  }

  private Set<PipelineMetadata> metadataDtoSetToPipelineMetadataSet(final Set<MetadataDto> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<PipelineMetadata> mapped = new LinkedHashSet<>();
    for (MetadataDto item : metadata) {
      mapped.add(metadataDtoToPipelineMetadata(item));
    }
    return mapped;
  }

  private Set<PipelineExecutionMetadata> metadataDtoSetToPipelineExecutionMetadataSet(final Set<MetadataDto> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<PipelineExecutionMetadata> mapped = new LinkedHashSet<>();
    for (MetadataDto item : metadata) {
      mapped.add(metadataDtoToPipelineExecutionMetadata(item));
    }
    return mapped;
  }

  private Set<MetadataDto> pipelineExecutionMetadataSetToMetadataDtoSet(final Set<PipelineExecutionMetadata> metadata) {
    if (metadata == null) {
      return null;
    }

    final Set<MetadataDto> mapped = new LinkedHashSet<>();
    for (PipelineExecutionMetadata item : metadata) {
      mapped.add(pipelineExecutionMetadataToMetadataDto(item));
    }
    return mapped;
  }

  private String executionStatusName(final PipelineExecution execution) {
    final PipelineExecutionStatus status = execution.getStatus();
    return status == null ? null : status.getName();
  }

  private Long executionPipelineId(final PipelineExecution execution) {
    final Pipeline pipeline = execution.getPipeline();
    return pipeline == null ? null : pipeline.getId();
  }

  private String executionScenarioStatusName(final PipelineScenarioExecution execution) {
    final PipelineExecutionStatus status = execution.getStatus();
    return status == null ? null : status.getName();
  }

  private Long executionScenarioPipelineId(final PipelineScenarioExecution execution) {
    final Pipeline pipeline = execution.getPipeline();
    return pipeline == null ? null : pipeline.getId();
  }
}
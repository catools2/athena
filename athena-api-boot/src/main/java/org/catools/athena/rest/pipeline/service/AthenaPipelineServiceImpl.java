package org.catools.athena.rest.pipeline.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.pipeline.entity.*;
import org.catools.athena.rest.pipeline.exception.PipelineNotExistsException;
import org.catools.athena.rest.pipeline.mapper.AthenaPipelineMapper;
import org.catools.athena.rest.pipeline.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class AthenaPipelineServiceImpl implements AthenaPipelineService {
  private final PipelineExecutionStatusRepository pipelineExecutionStatusRepository;
  private final PipelineExecutionRepository pipelineExecutionRepository;
  private final PipelineScenarioExecutionRepository pipelineScenarioExecutionRepository;
  private final PipelineExecutionMetaDataRepository pipelineExecutionMetaDataRepository;
  private final PipelineMetaDataRepository pipelineMetaDataRepository;
  private final PipelineRepository pipelineRepository;

  // Mappers
  private final AthenaPipelineMapper athenaPipelineMapper;

  @Override
  public PipelineDto savePipeline(final PipelineDto pipelineDto) {
    final Pipeline pipelineToSave = athenaPipelineMapper.pipelineDtoToPipeline(pipelineDto);
    normalizePipelineMetadata(pipelineToSave);
    final Pipeline savedPipeline = pipelineRepository.saveAndFlush(pipelineToSave);
    return athenaPipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  @Override
  public PipelineDto updatePipelineEndDate(final long pipelineId, final Date enddate) {
    final Pipeline pipelineToPatch = pipelineRepository.findById(pipelineId).orElseThrow(PipelineNotExistsException::new);
    pipelineToPatch.setEndDate(enddate);
    final Pipeline savedPipeline = pipelineRepository.saveAndFlush(pipelineToPatch);
    return athenaPipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  @Override
  public PipelineExecutionDto saveExecution(final PipelineExecutionDto execution) {
    final PipelineExecution pipelineExecution = athenaPipelineMapper.executionDtoToExecution(execution);
    pipelineExecution.setMetadata(normalizePipelineExecutionMetadata(pipelineExecution.getMetadata()));
    final PipelineExecution savedPipelineExecution = pipelineExecutionRepository.saveAndFlush(pipelineExecution);
    return athenaPipelineMapper.executionToExecutionDto(savedPipelineExecution);
  }

  @Override
  public PipelineScenarioExecutionDto saveScenarioExecution(final PipelineScenarioExecutionDto execution) {
    final PipelineScenarioExecution pipelineExecution = athenaPipelineMapper.scenarioExecutionDtoToScenarioExecution(execution);
    pipelineExecution.setMetadata(normalizePipelineExecutionMetadata(pipelineExecution.getMetadata()));
    final PipelineScenarioExecution savedPipelineExecution = pipelineScenarioExecutionRepository.saveAndFlush(pipelineExecution);
    return athenaPipelineMapper.scenarioExecutionToScenarioExecutionDto(savedPipelineExecution);
  }

  @Override
  public List<PipelineExecutionStatusDto> getExecutionStatuses() {
    final List<PipelineExecutionStatus> executionStatus = pipelineExecutionStatusRepository.findAll();
    return executionStatus.stream().map(athenaPipelineMapper::pipelineStatusToPipelineStatusDto).toList();
  }

  @Override
  public Optional<PipelineExecutionStatusDto> getExecutionStatusByName(String name) {
    final Optional<PipelineExecutionStatus> executionStatus = pipelineExecutionStatusRepository.findByName(name);
    return executionStatus.map(athenaPipelineMapper::pipelineStatusToPipelineStatusDto);
  }

  @Override
  public PipelineExecutionStatusDto saveExecutionStatus(final PipelineExecutionStatusDto status) {
    final PipelineExecutionStatus pipelineExecutionStatus = athenaPipelineMapper.pipelineStatusDtoToPipelineStatus(status);
    final PipelineExecutionStatus savedPipelineExecutionStatus = pipelineExecutionStatusRepository.saveAndFlush(pipelineExecutionStatus);
    return athenaPipelineMapper.pipelineStatusToPipelineStatusDto(savedPipelineExecutionStatus);
  }

  @Override
  public Optional<PipelineDto> getLastPipelineDto(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final String environmentCode) {
    return getLastPipeline(pipelineName, pipelineNumber, environmentCode).map(athenaPipelineMapper::pipelineToPipelineDto);
  }

  private Optional<Pipeline> getLastPipeline(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final String environmentCode) {
    Optional<Pipeline> pipeline = Optional.empty();
    if (isNotBlank(pipelineName) && isNotBlank(pipelineNumber) && isNotBlank(environmentCode)) {
      pipeline = pipelineRepository.findTop1ByEnvironmentCodeAndNameAndNumberOrderByIdDesc(environmentCode, pipelineName, pipelineNumber);
    } else if (isNotBlank(pipelineName) && isNotBlank(environmentCode)) {
      pipeline = pipelineRepository.findTop1ByEnvironmentCodeAndNameOrderByNumberDescIdDesc(environmentCode, pipelineName);
    } else if (isNotBlank(pipelineName) && isNotBlank(pipelineNumber)) {
      pipeline = pipelineRepository.findTop1ByNameAndNumberOrderByIdDesc(pipelineName, pipelineNumber);
    } else if (isNotBlank(pipelineName)) {
      pipeline = pipelineRepository.findTop1ByNameOrderByNumberDescIdDesc(pipelineName);
    }
    return pipeline;
  }

  private void normalizePipelineMetadata(Pipeline pipeline) {
    final List<PipelineMetadata> metadata = new ArrayList<>();
    for (PipelineMetadata md : pipeline.getMetadata()) {
      // If we do not have md then we read it from DB and if MD does not exist we create one and assign it to the pipeline
      PipelineMetadata pipelineMD =
          pipelineMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> pipelineMetaDataRepository.saveAndFlush(md));
      metadata.add(pipelineMD);
    }
    pipeline.setMetadata(metadata);
  }

  private List<PipelineExecutionMetadata> normalizePipelineExecutionMetadata(List<PipelineExecutionMetadata> metadataList) {
    final List<PipelineExecutionMetadata> metadata = new ArrayList<>();
    for (PipelineExecutionMetadata md : metadataList) {
      // If we do not have md then we read it from DB and if MD does not exist we create one and assign it to the pipeline
      PipelineExecutionMetadata pipelineMD =
          pipelineExecutionMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> pipelineExecutionMetaDataRepository.saveAndFlush(md));
      metadata.add(pipelineMD);
    }
    return metadata;
  }
}
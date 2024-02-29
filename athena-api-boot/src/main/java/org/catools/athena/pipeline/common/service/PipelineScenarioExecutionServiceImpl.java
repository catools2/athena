package org.catools.athena.pipeline.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.pipeline.common.entity.PipelineScenarioExecution;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.repository.PipelineExecutionMetaDataRepository;
import org.catools.athena.pipeline.common.repository.PipelineScenarioExecutionRepository;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineScenarioExecutionServiceImpl implements PipelineScenarioExecutionService {

  private final PipelineExecutionMetaDataRepository pipelineExecutionMetaDataRepository;
  private final PipelineScenarioExecutionRepository pipelineScenarioExecutionRepository;

  private final PipelineMapper pipelineMapper;

  /**
   * Save execution
   */
  @Override
  public PipelineScenarioExecutionDto save(PipelineScenarioExecutionDto execution) {
    final PipelineScenarioExecution pipelineExecution = pipelineMapper.scenarioExecutionDtoToScenarioExecution(execution);
    pipelineExecution.setMetadata(normalizeMetadata(pipelineExecution.getMetadata(), pipelineExecutionMetaDataRepository));
    final PipelineScenarioExecution savedPipelineExecution = pipelineScenarioExecutionRepository.saveAndFlush(pipelineExecution);
    return pipelineMapper.scenarioExecutionToScenarioExecutionDto(savedPipelineExecution);
  }

  /**
   * Retrieve entity by id
   */
  @Override
  public Optional<PipelineScenarioExecutionDto> getById(Long id) {
    final Optional<PipelineScenarioExecution> savedPipelineExecution = pipelineScenarioExecutionRepository.findById(id);
    return savedPipelineExecution.map(pipelineMapper::scenarioExecutionToScenarioExecutionDto);
  }
}

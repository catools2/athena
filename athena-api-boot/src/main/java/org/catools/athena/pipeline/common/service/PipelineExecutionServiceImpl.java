package org.catools.athena.pipeline.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.pipeline.common.entity.PipelineExecution;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.repository.PipelineExecutionMetaDataRepository;
import org.catools.athena.pipeline.common.repository.PipelineExecutionRepository;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;

@Service
@RequiredArgsConstructor
public class PipelineExecutionServiceImpl implements PipelineExecutionService {
  private final PipelineExecutionMetaDataRepository pipelineExecutionMetaDataRepository;
  private final PipelineExecutionRepository pipelineExecutionRepository;
  private final PipelineMapper pipelineMapper;

  /**
   * Save entity
   */
  @Override
  public PipelineExecutionDto save(PipelineExecutionDto execution) {
    final PipelineExecution pipelineExecution = pipelineMapper.executionDtoToExecution(execution);
    pipelineExecution.setMetadata(normalizeMetadata(pipelineExecution.getMetadata(), pipelineExecutionMetaDataRepository));
    final PipelineExecution savedPipelineExecution = pipelineExecutionRepository.saveAndFlush(pipelineExecution);
    return pipelineMapper.executionToExecutionDto(savedPipelineExecution);
  }

  /**
   * Retrieve execution by id
   */
  @Override
  public Optional<PipelineExecutionDto> getById(Long id) {
    final Optional<PipelineExecution> savedPipelineExecution = pipelineExecutionRepository.findById(id);
    return savedPipelineExecution.map(pipelineMapper::executionToExecutionDto);
  }
}

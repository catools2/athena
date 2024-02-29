package org.catools.athena.pipeline.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.pipeline.common.entity.PipelineExecutionStatus;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.repository.PipelineExecutionStatusRepository;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineExecutionStatusServiceImpl implements PipelineExecutionStatusService {

  private final PipelineExecutionStatusRepository pipelineExecutionStatusRepository;

  private final PipelineMapper pipelineMapper;

  /**
   * Save execution status
   */
  @Override
  public PipelineExecutionStatusDto save(PipelineExecutionStatusDto status) {
    final PipelineExecutionStatus pipelineExecutionStatus = pipelineMapper.pipelineStatusDtoToPipelineStatus(status);
    final PipelineExecutionStatus savedPipelineExecutionStatus = pipelineExecutionStatusRepository.saveAndFlush(pipelineExecutionStatus);
    return pipelineMapper.pipelineStatusToPipelineStatusDto(savedPipelineExecutionStatus);
  }

  /**
   * Retrieve entity by id
   */
  @Override
  public Optional<PipelineExecutionStatusDto> getById(Long id) {
    final Optional<PipelineExecutionStatus> executionStatus = pipelineExecutionStatusRepository.findById(id);
    return executionStatus.map(pipelineMapper::pipelineStatusToPipelineStatusDto);
  }

  /**
   * Retrieve all execution statuses
   */
  @Override
  public Set<PipelineExecutionStatusDto> getAll() {
    final List<PipelineExecutionStatus> executionStatus = pipelineExecutionStatusRepository.findAll();
    return executionStatus.stream().map(pipelineMapper::pipelineStatusToPipelineStatusDto).collect(Collectors.toSet());
  }

  /**
   * Retrieve entity by name
   */
  @Override
  public Optional<PipelineExecutionStatusDto> getByName(String name) {
    final Optional<PipelineExecutionStatus> executionStatus = pipelineExecutionStatusRepository.findByName(name);
    return executionStatus.map(pipelineMapper::pipelineStatusToPipelineStatusDto);
  }

}

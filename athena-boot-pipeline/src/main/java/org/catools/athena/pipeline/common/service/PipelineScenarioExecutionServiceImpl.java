package org.catools.athena.pipeline.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.pipeline.common.entity.PipelineExecutionMetadata;
import org.catools.athena.pipeline.common.entity.PipelineScenarioExecution;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.repository.PipelineExecutionMetaDataRepository;
import org.catools.athena.pipeline.common.repository.PipelineScenarioExecutionRepository;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineScenarioExecutionServiceImpl implements PipelineScenarioExecutionService {

  private final PipelineExecutionMetaDataRepository pipelineExecutionMetaDataRepository;
  private final PipelineScenarioExecutionRepository pipelineScenarioExecutionRepository;

  private final PipelineMapper pipelineMapper;

  /**
   * Save execution
   *
   * @param entity
   */
  @Override
  public PipelineScenarioExecutionDto save(PipelineScenarioExecutionDto entity) {
    log.debug("Saving entity: {}", entity);
    final PipelineScenarioExecution pipelineExecution = pipelineMapper.scenarioExecutionDtoToScenarioExecution(entity);
    pipelineExecution.setMetadata(normalizeMetadata(pipelineExecution.getMetadata()));
    final PipelineScenarioExecution savedPipelineExecution = RetryUtils.retry(3, 1000, integer -> pipelineScenarioExecutionRepository.saveAndFlush(pipelineExecution));
    return pipelineMapper.scenarioExecutionToScenarioExecutionDto(savedPipelineExecution);
  }

  /**
   * Retrieve entity by id
   *
   * @param id
   */
  @Override
  public Optional<PipelineScenarioExecutionDto> getById(Long id) {
    final Optional<PipelineScenarioExecution> savedPipelineExecution = pipelineScenarioExecutionRepository.findById(id);
    return savedPipelineExecution.map(pipelineMapper::scenarioExecutionToScenarioExecutionDto);
  }

  private synchronized Set<PipelineExecutionMetadata> normalizeMetadata(Set<PipelineExecutionMetadata> metadataSet) {
    final Set<PipelineExecutionMetadata> metadata = new HashSet<>();

    for (PipelineExecutionMetadata md : metadataSet) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      PipelineExecutionMetadata pipelineMD =
          pipelineExecutionMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> pipelineExecutionMetaDataRepository.saveAndFlush(md));

      metadata.add(pipelineMD);
    }

    return metadata;
  }
}

package org.catools.athena.pipeline.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.model.pipeline.PipelineScenarioExecutionDto;
import org.catools.athena.pipeline.common.entity.PipelineExecutionMetadata;
import org.catools.athena.pipeline.common.entity.PipelineScenarioExecution;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.repository.PipelineExecutionMetaDataRepository;
import org.catools.athena.pipeline.common.repository.PipelineScenarioExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public PipelineScenarioExecutionDto save(PipelineScenarioExecutionDto entity) {
    log.debug("Saving entity: {}", entity);
    final PipelineScenarioExecution pipelineExecution = pipelineMapper.scenarioExecutionDtoToScenarioExecution(entity);

    final Set<PipelineExecutionMetadata> normalizedMetadata = normalizeMetadata(pipelineExecution.getMetadata());
    pipelineExecution.setMetadata(normalizedMetadata);

    final PipelineScenarioExecution savedPipelineExecution = RetryUtils.retry(3, 1000, integer -> pipelineScenarioExecutionRepository.saveAndFlush(pipelineExecution));
    return pipelineMapper.scenarioExecutionToScenarioExecutionDto(savedPipelineExecution);
  }

  /**
   * Retrieve entity by id
   *
   * @param id
   */
  @Override
  @Transactional(readOnly = true)
  public Optional<PipelineScenarioExecutionDto> getById(Long id) {
    final Optional<PipelineScenarioExecution> savedPipelineExecution = pipelineScenarioExecutionRepository.findById(id);
    return savedPipelineExecution.map(pipelineMapper::scenarioExecutionToScenarioExecutionDto);
  }

  private Set<PipelineExecutionMetadata> normalizeMetadata(Set<PipelineExecutionMetadata> metadataSet) {
    final Set<PipelineExecutionMetadata> metadata = new HashSet<>();

    for (PipelineExecutionMetadata md : metadataSet) {
      // Read md from DB and if MD does not exist we create one
      PipelineExecutionMetadata normalizedMd =
          pipelineExecutionMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> {
                // Let the database absorb the race. A plain insert here would abort the surrounding
                // transaction on conflict, and a recovery lookup issued afterwards could never run --
                // PostgreSQL refuses every statement in an aborted transaction, which surfaced as an
                // opaque Hibernate "null identifier" assertion rather than the 23505 that caused it.
                pipelineExecutionMetaDataRepository.insertIfAbsent(md.getName(), md.getValue());
                return pipelineExecutionMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
                    .orElseThrow(() -> new IllegalStateException(
                        "Metadata (" + md.getName() + ") could not be read back after insert"));
              });

      metadata.add(normalizedMd);
    }

    return metadata;
  }
}

package org.catools.athena.pipeline.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.model.pipeline.PipelineExecutionDto;
import org.catools.athena.pipeline.common.entity.PipelineExecution;
import org.catools.athena.pipeline.common.entity.PipelineExecutionMetadata;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.repository.PipelineExecutionMetaDataRepository;
import org.catools.athena.pipeline.common.repository.PipelineExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
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
  @Transactional
  public PipelineExecutionDto save(PipelineExecutionDto entity) {
    log.debug("Saving entity: {}", entity);
    final PipelineExecution pipelineExecution = pipelineMapper.executionDtoToExecution(entity);

    final Set<PipelineExecutionMetadata> normalizedMetadata = normalizeMetadata(pipelineExecution.getMetadata());
    pipelineExecution.setMetadata(normalizedMetadata);

    final PipelineExecution savedPipelineExecution = RetryUtils.retry(3, 1000, integer -> pipelineExecutionRepository.saveAndFlush(pipelineExecution));
    return pipelineMapper.executionToExecutionDto(savedPipelineExecution);
  }

  /**
   * Retrieve execution by id
   */
  @Override
  @Transactional(readOnly = true)
  public Optional<PipelineExecutionDto> getById(Long id) {
    final Optional<PipelineExecution> savedPipelineExecution = pipelineExecutionRepository.findById(id);
    return savedPipelineExecution.map(pipelineMapper::executionToExecutionDto);
  }

  /**
   * Normalizes metadata by ensuring all metadata entities exist in the database.
   * If metadata doesn't exist, it creates a new entry.
   * Database-level constraints ensure consistency in concurrent scenarios.
   *
   * @param metadataSet the set of metadata to normalize
   * @return normalized set of metadata
   */
  private Set<PipelineExecutionMetadata> normalizeMetadata(Set<PipelineExecutionMetadata> metadataSet) {
    if (metadataSet == null || metadataSet.isEmpty()) {
      return new HashSet<>();
    }

    final Set<PipelineExecutionMetadata> metadata = new HashSet<>();

    for (PipelineExecutionMetadata md : metadataSet) {
      // Read md from DB and if MD does not exist we create one
      PipelineExecutionMetadata normalizedMd =
          pipelineExecutionMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> {
                try {
                  return pipelineExecutionMetaDataRepository.saveAndFlush(md);
                } catch (org.springframework.dao.DataIntegrityViolationException e) {
                  // Another thread inserted it between our check and save - retry lookup
                  return pipelineExecutionMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
                      .orElseThrow(() -> new RuntimeException("Failed to find or create metadata after retry", e));
                }
              });

      metadata.add(normalizedMd);
    }

    return metadata;
  }
}

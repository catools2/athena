package org.catools.athena.pipeline.common.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.entity.PipelineMetadata;
import org.catools.athena.pipeline.common.exception.PipelineNotExistsException;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.mapper.PipelineMapperService;
import org.catools.athena.pipeline.common.repository.PipelineMetaDataRepository;
import org.catools.athena.pipeline.common.repository.PipelineRepository;
import org.catools.athena.pipeline.common.repository.builders.PipelineRepositoryCustom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static io.micrometer.common.util.StringUtils.isBlank;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {
  private final PipelineMetaDataRepository pipelineMetaDataRepository;

  private final PipelineRepositoryCustom pipelineRepositoryCustom;
  private final PipelineRepository pipelineRepository;

  private final PipelineMapperService pipelineMapperService;

  // Mappers
  private final PipelineMapper pipelineMapper;

  @Override
  @Transactional
  public PipelineDto saveOrUpdate(final PipelineDto entity) {
    log.debug("Saving entity: {}", entity);

    final Pipeline pipeline = pipelineMapper.pipelineDtoToPipeline(entity);

    final Set<PipelineMetadata> normalizedMetadata = normalizeMetadata(pipeline.getMetadata());

    final Pipeline pipelineToSave = pipelineRepository.findTop1ByEnvironmentIdAndNameLikeAndNumberLikeOrderByIdDesc(pipeline.getEnvironmentId(), entity.getName(), entity.getNumber())
        .map(p -> {
          p.setName(pipeline.getName());
          p.setDescription(pipeline.getDescription());
          p.setNumber(pipeline.getNumber());
          p.setStartDate(pipeline.getStartDate());
          p.setEndDate(pipeline.getEndDate());
          p.setEnvironmentId(pipeline.getEnvironmentId());
          p.setVersionId(pipeline.getVersionId());
          p.setName(pipeline.getName());

          // Clear and set with normalized metadata
          p.getMetadata().clear();
          p.getMetadata().addAll(normalizedMetadata);

          return p;
        }).orElseGet(() -> {
          // For new pipeline, set normalized metadata
          pipeline.setMetadata(normalizedMetadata);
          return pipeline;
        });

    final Pipeline savedPipeline = RetryUtils.retry(3, 1000, integer -> pipelineRepository.saveAndFlush(pipelineToSave));
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  @Override
  @Transactional
  public PipelineDto updatePipelineEndDate(final long pipelineId, final Instant enddate) {
    final Pipeline pipelineToPatch = pipelineRepository.findById(pipelineId).orElseThrow(PipelineNotExistsException::new);
    pipelineToPatch.setEndDate(enddate);
    final Pipeline savedPipeline = RetryUtils.retry(3, 1000, integer -> pipelineRepository.saveAndFlush(pipelineToPatch));
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  public Optional<PipelineDto> getPipeline(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final String projectCode, @Nullable final String versionCode, @Nullable final String environmentCode) {
    final Long versionId = pipelineMapperService.getVersionId(projectCode, versionCode);
    final Long environmentId = pipelineMapperService.getEnvironmentId(projectCode, environmentCode);
    return getLastPipeline(pipelineName, pipelineNumber, versionId, environmentId).map(pipelineMapper::pipelineToPipelineDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PipelineDto> getById(final Long id) {
    return pipelineRepository.findById(id).map(pipelineMapper::pipelineToPipelineDto);
  }

  private Optional<Pipeline> getLastPipeline(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final Long versionId, @Nullable final Long environmentId) {
    if (isBlank(pipelineName)) {
      return Optional.empty();
    }
    return pipelineRepositoryCustom.findLastPipeline(pipelineName, pipelineNumber, versionId, environmentId);
  }

  private synchronized Set<PipelineMetadata> normalizeMetadata(Set<PipelineMetadata> metadataSet) {
    final Set<PipelineMetadata> metadata = new HashSet<>();

    for (PipelineMetadata md : metadataSet) {
      // Create a detached instance to avoid issues with the original entity
      PipelineMetadata detachedMd = new PipelineMetadata();
      detachedMd.setName(md.getName());
      detachedMd.setValue(md.getValue());

      // Read md from DB and if MD does not exist we create one
      PipelineMetadata normalizedMd =
          pipelineMetaDataRepository.findByNameAndValue(detachedMd.getName(), detachedMd.getValue())
              .orElseGet(() -> {
                try {
                  return pipelineMetaDataRepository.saveAndFlush(detachedMd);
                } catch (org.springframework.dao.DataIntegrityViolationException e) {
                  // Another thread inserted it between our check and save - retry lookup
                  return pipelineMetaDataRepository.findByNameAndValue(detachedMd.getName(), detachedMd.getValue())
                      .orElseThrow(() -> new RuntimeException("Failed to find or create metadata after retry", e));
                }
              });

      metadata.add(normalizedMd);
    }

    return metadata;
  }
}
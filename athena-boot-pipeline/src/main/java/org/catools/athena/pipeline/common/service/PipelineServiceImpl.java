package org.catools.athena.pipeline.common.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.entity.PipelineMetadata;
import org.catools.athena.pipeline.common.exception.PipelineNotExistsException;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.mapper.PipelineMapperService;
import org.catools.athena.pipeline.common.repository.PipelineMetaDataRepository;
import org.catools.athena.pipeline.common.repository.PipelineRepository;
import org.catools.athena.pipeline.model.PipelineDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.micrometer.common.util.StringUtils.isBlank;
import static io.micrometer.common.util.StringUtils.isNotBlank;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {
  private final PipelineMetaDataRepository pipelineMetaDataRepository;

  private final PipelineRepository pipelineRepository;

  private final PipelineMapperService pipelineMapperService;

  // Mappers
  private final PipelineMapper pipelineMapper;

  @Override
  public PipelineDto saveOrUpdate(final PipelineDto entity) {
    log.debug("Saving entity: {}", entity);

    final Pipeline pipeline = pipelineMapper.pipelineDtoToPipeline(entity);

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

          p.getMetadata().removeIf(d1 -> pipeline.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));
          p.getMetadata().addAll(pipeline.getMetadata().stream().filter(d1 -> p.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))).collect(Collectors.toSet()));

          return p;
        }).orElse(pipeline);

    pipelineToSave.setMetadata(normalizeMetadata(pipelineToSave.getMetadata()));
    final Pipeline savedPipeline = RetryUtils.retry(3, 1000, integer -> pipelineRepository.saveAndFlush(pipelineToSave));
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  @Override
  public PipelineDto updatePipelineEndDate(final long pipelineId, final Instant enddate) {
    final Pipeline pipelineToPatch = pipelineRepository.findById(pipelineId).orElseThrow(PipelineNotExistsException::new);
    pipelineToPatch.setEndDate(enddate);
    final Pipeline savedPipeline = RetryUtils.retry(3, 1000, integer -> pipelineRepository.saveAndFlush(pipelineToPatch));
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  public Optional<PipelineDto> getPipeline(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final String versionCode, @Nullable final String environmentCode) {
    final Long versionId = pipelineMapperService.getVersionId(versionCode);
    final Long environmentId = pipelineMapperService.getEnvironmentId(environmentCode);
    return getLastPipeline(pipelineName, pipelineNumber, versionId, environmentId).map(pipelineMapper::pipelineToPipelineDto);
  }

  @Override
  public Optional<PipelineDto> getById(final Long id) {
    return pipelineRepository.findById(id).map(pipelineMapper::pipelineToPipelineDto);
  }

  private Optional<Pipeline> getLastPipeline(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final Long versionId, @Nullable final Long environmentId) {
    if (isBlank(pipelineName)) {
      return Optional.empty();
    }

    if (isNotBlank(pipelineNumber) && versionId != null && environmentId != null) {
      return pipelineRepository.findTop1ByVersionIdAndEnvironmentIdAndNameLikeAndNumberLikeOrderByIdDesc(versionId, environmentId, pipelineName, pipelineNumber);
    }

    if (isNotBlank(pipelineNumber) && environmentId != null) {
      return pipelineRepository.findTop1ByEnvironmentIdAndNameLikeAndNumberLikeOrderByIdDesc(environmentId, pipelineName, pipelineNumber);
    }

    if (versionId != null && environmentId != null) {
      return pipelineRepository.findTop1ByVersionIdAndEnvironmentIdAndNameLikeOrderByNumberDescIdDesc(versionId, environmentId, pipelineName);
    }

    if (environmentId != null) {
      return pipelineRepository.findTop1ByEnvironmentIdAndNameLikeOrderByNumberDescIdDesc(environmentId, pipelineName);
    }

    if (isNotBlank(pipelineNumber)) {
      return pipelineRepository.findTop1ByNameLikeAndNumberLikeOrderByIdDesc(pipelineName, pipelineNumber);
    }

    return pipelineRepository.findTop1ByNameLikeOrderByNumberDescIdDesc(pipelineName);
  }

  private synchronized Set<PipelineMetadata> normalizeMetadata(Set<PipelineMetadata> metadataSet) {
    final Set<PipelineMetadata> metadata = new HashSet<>();

    for (PipelineMetadata md : metadataSet) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      PipelineMetadata pipelineMD =
          pipelineMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> pipelineMetaDataRepository.saveAndFlush(md));

      metadata.add(pipelineMD);
    }

    return metadata;
  }
}
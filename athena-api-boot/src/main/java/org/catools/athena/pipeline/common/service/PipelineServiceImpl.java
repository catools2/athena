package org.catools.athena.pipeline.common.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.exception.PipelineNotExistsException;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.repository.PipelineMetaDataRepository;
import org.catools.athena.pipeline.common.repository.PipelineRepository;
import org.catools.athena.pipeline.model.PipelineDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.micrometer.common.util.StringUtils.isNotBlank;
import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;

@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {
  private final PipelineMetaDataRepository pipelineMetaDataRepository;
  private final PipelineRepository pipelineRepository;

  // Mappers
  private final PipelineMapper pipelineMapper;

  @Override
  public PipelineDto saveOrUpdate(final PipelineDto pipelineDto) {
    final Pipeline pipeline = pipelineMapper.pipelineDtoToPipeline(pipelineDto);

    final Pipeline pipelineToSave = pipelineRepository.findTop1ByEnvironmentCodeAndNameAndNumberOrderByIdDesc(pipelineDto.getEnvironmentCode(), pipelineDto.getName(), pipelineDto.getNumber())
        .map(p -> {
          p.setName(pipeline.getName());
          p.setDescription(pipeline.getDescription());
          p.setNumber(pipeline.getNumber());
          p.setStartDate(pipeline.getStartDate());
          p.setEndDate(pipeline.getEndDate());
          p.setEnvironment(pipeline.getEnvironment());
          p.setName(pipeline.getName());

          p.getMetadata().removeIf(d1 -> pipeline.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));
          p.getMetadata().addAll(pipeline.getMetadata().stream().filter(d1 -> p.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))).collect(Collectors.toSet()));

          return p;
        }).orElse(pipeline);

    pipelineToSave.setMetadata(normalizeMetadata(pipelineToSave.getMetadata(), pipelineMetaDataRepository));
    final Pipeline savedPipeline = pipelineRepository.saveAndFlush(pipelineToSave);
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  @Override
  public PipelineDto updatePipelineEndDate(final long pipelineId, final Instant enddate) {
    final Pipeline pipelineToPatch = pipelineRepository.findById(pipelineId).orElseThrow(PipelineNotExistsException::new);
    pipelineToPatch.setEndDate(enddate);
    final Pipeline savedPipeline = pipelineRepository.saveAndFlush(pipelineToPatch);
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  public Optional<PipelineDto> getPipeline(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final String environmentCode) {
    return getLastPipeline(pipelineName, pipelineNumber, environmentCode).map(pipelineMapper::pipelineToPipelineDto);
  }

  @Override
  public Optional<PipelineDto> getById(final Long id) {
    return pipelineRepository.findById(id).map(pipelineMapper::pipelineToPipelineDto);
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
}
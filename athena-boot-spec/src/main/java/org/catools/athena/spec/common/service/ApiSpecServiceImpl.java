package org.catools.athena.spec.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.catools.athena.spec.common.entity.ApiSpecMetadata;
import org.catools.athena.spec.common.mapper.ApiSpecMapper;
import org.catools.athena.spec.common.repository.ApiPathMetadataRepository;
import org.catools.athena.spec.common.repository.ApiSpecMetadataRepository;
import org.catools.athena.spec.common.repository.ApiSpecRepository;
import org.catools.athena.spec.utils.ApiSpecUtils;
import org.catools.athena.spec.utils.MetadataPersistentHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiSpecServiceImpl implements ApiSpecService {

  private final ApiSpecMetadataRepository apiSpecMetadataRepository;
  private final ApiPathMetadataRepository apiPathMetadataRepository;
  private final ApiSpecRepository apiSpecRepository;
  private final ApiSpecMapper apiSpecMapper;
  private final ApiSpecUtils apiSpecUtils;

  private final ProjectFeignClient projectFeignClient;

  @Override
  @Transactional
  public ApiSpecDto saveOrUpdate(final ApiSpecDto entity) {
    log.debug("Saving entity: {}", entity);
    final ApiSpec apiSpec = apiSpecMapper.apiSpecDtoToApiSpec(entity);

    ProjectDto projectByCode = Optional.ofNullable(projectFeignClient.search(entity.getProject()).body()).orElseThrow();

    final Set<ApiSpecMetadata> normalizedMetadata = MetadataPersistentHelper.normalizeMetadata(apiSpec.getMetadata(), apiSpecMetadataRepository);

    apiSpec.getPaths().forEach(p -> p.setMetadata(MetadataPersistentHelper.normalizeMetadata(p.getMetadata(), apiPathMetadataRepository)));

    final ApiSpec specToSave = apiSpecRepository.findByProjectIdAndName(projectByCode.getId(), entity.getName())
        .map(spec -> {
          spec.setTitle(apiSpec.getTitle());
          spec.setVersion(apiSpec.getVersion());
          spec.setLastSyncTime(apiSpec.getLastSyncTime());

          Set<ApiSpecMetadata> currentMetadata = spec.getMetadata();
          // Update metadata set by removing entries not present in the new normalized set
          currentMetadata.removeIf(metadata -> !normalizedMetadata.contains(metadata));
          // And adding any new entries that are not already present
          normalizedMetadata.stream()
              .filter(metadata -> !currentMetadata.contains(metadata))
              .forEach(currentMetadata::add);

          // We should not remove paths if they are not in the new spec to not loss tracking when building statistics
          apiSpec.getPaths()
              .stream()
              .filter(p1 -> apiSpecUtils.notContains(spec.getPaths(), p1))
              .forEach(spec::addPath);

          return spec;
        }).orElseGet(() -> {
          // For new pipeline, set normalized metadata
          apiSpec.setMetadata(normalizedMetadata);
          return apiSpec;
        });

    specToSave.getPaths().forEach(p -> p.setSpec(specToSave));
    ApiSpec savedApiSpec = apiSpecRepository.saveAndFlush(specToSave);

    return apiSpecMapper.apiSpecToApiSpecDto(savedApiSpec);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ApiSpecDto> getById(final Long id) {
    return apiSpecRepository.findById(id).map(getApiSpecToApiSpecDto());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ApiSpecDto> getByProjectCodeAndName(final String projectCode, final String name) {
    ProjectDto projectByCode = Optional.ofNullable(projectFeignClient.search(projectCode).body()).orElseThrow();
    return apiSpecRepository.findByProjectIdAndName(projectByCode.getId(), name).map(getApiSpecToApiSpecDto());
  }

  private Function<ApiSpec, ApiSpecDto> getApiSpecToApiSpecDto() {
    return apiSpecMapper::apiSpecToApiSpecDto;
  }
}

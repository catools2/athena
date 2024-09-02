package org.catools.athena.spec.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.spec.common.entity.ApiSpec;
import org.catools.athena.spec.common.mapper.ApiSpecMapper;
import org.catools.athena.spec.common.repository.ApiPathMetadataRepository;
import org.catools.athena.spec.common.repository.ApiSpecMetadataRepository;
import org.catools.athena.spec.common.repository.ApiSpecRepository;
import org.catools.athena.spec.utils.ApiSpecUtils;
import org.catools.athena.spec.utils.MetadataPersistentHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
  public ApiSpecDto saveOrUpdate(final ApiSpecDto entity) {
    log.debug("Saving entity: {}", entity);
    final ApiSpec apiSpec = apiSpecMapper.apiSpecDtoToApiSpec(entity);

    ProjectDto projectByCode = Optional.ofNullable(projectFeignClient.search(entity.getProject()).body()).orElseThrow();
    final ApiSpec specToSave = apiSpecRepository.findByProjectIdAndName(projectByCode.getId(), entity.getName())
        .map(spec -> {
          spec.setTitle(apiSpec.getTitle());
          spec.setVersion(apiSpec.getVersion());
          spec.setLastSyncTime(apiSpec.getLastSyncTime());

          spec.getMetadata().removeIf(m1 -> apiSpecUtils.notContains(apiSpec.getMetadata(), m1));
          spec.getMetadata().addAll(
              apiSpec.getMetadata()
                  .stream()
                  .filter(m1 -> apiSpecUtils.notContains(spec.getMetadata(), m1))
                  .collect(Collectors.toSet())
          );

          // We should not remove paths if they are not in the new spec to not loss tracking when building statistics
          apiSpec.getPaths()
              .stream()
              .filter(p1 -> apiSpecUtils.notContains(spec.getPaths(), p1))
              .forEach(spec::addPath);

          return spec;
        }).orElse(apiSpec);

    specToSave.setMetadata(MetadataPersistentHelper.normalizeMetadata(specToSave.getMetadata(), apiSpecMetadataRepository));
    specToSave.getPaths().forEach(p -> p.setMetadata(MetadataPersistentHelper.normalizeMetadata(p.getMetadata(), apiPathMetadataRepository)));
    specToSave.getPaths().forEach(p -> p.setSpec(specToSave));
    ApiSpec savedApiSpec = apiSpecRepository.saveAndFlush(specToSave);

    return apiSpecMapper.apiSpecToApiSpecDto(savedApiSpec);
  }

  @Override
  public Optional<ApiSpecDto> getById(final Long id) {
    return apiSpecRepository.findById(id).map(getApiSpecToApiSpecDto());
  }

  @Override
  public Optional<ApiSpecDto> getByProjectCodeAndName(final String projectCode, final String name) {
    ProjectDto projectByCode = Optional.ofNullable(projectFeignClient.search(projectCode).body()).orElseThrow();
    return apiSpecRepository.findByProjectIdAndName(projectByCode.getId(), name).map(getApiSpecToApiSpecDto());
  }

  private Function<ApiSpec, ApiSpecDto> getApiSpecToApiSpecDto() {
    return apiSpecMapper::apiSpecToApiSpecDto;
  }
}

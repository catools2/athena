package org.catools.athena.apispec.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.apispec.common.entity.ApiSpec;
import org.catools.athena.apispec.common.mapper.ApiSpecMapper;
import org.catools.athena.apispec.common.repository.ApiPathMetadataRepository;
import org.catools.athena.apispec.common.repository.ApiSpecMetadataRepository;
import org.catools.athena.apispec.common.repository.ApiSpecRepository;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.apispec.utils.ApiSpecUtils;
import org.catools.athena.common.utils.RetryUtil;
import org.catools.athena.core.utils.MetadataPersistentHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

  @Override
  public ApiSpecDto saveOrUpdate(final ApiSpecDto entity) {
    log.debug("Saving entity: {}", entity);
    final ApiSpec apiSpec = apiSpecMapper.apiSpecDtoToApiSpec(entity);

    final ApiSpec specToSave = apiSpecRepository.findByProjectCodeAndName(entity.getProject(), entity.getName())
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
    ApiSpec savedApiSpec = RetryUtil.retry(3, 1000, integer -> apiSpecRepository.saveAndFlush(specToSave));

    return apiSpecMapper.apiSpecToApiSpecDto(savedApiSpec);
  }

  @Override
  public Optional<ApiSpecDto> getById(final Long id) {
    return apiSpecRepository.findById(id).map(apiSpecMapper::apiSpecToApiSpecDto);
  }

  @Override
  public Optional<ApiSpecDto> getByProjectCodeAndName(final String projectCode, final String name) {
    return apiSpecRepository.findByProjectCodeAndName(projectCode, name).map(apiSpecMapper::apiSpecToApiSpecDto);
  }
}

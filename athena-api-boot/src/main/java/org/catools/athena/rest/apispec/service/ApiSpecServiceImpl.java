package org.catools.athena.rest.apispec.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.rest.apispec.entity.ApiSpec;
import org.catools.athena.rest.apispec.mapper.ApiSpecMapper;
import org.catools.athena.rest.apispec.repository.ApiPathMetadataRepository;
import org.catools.athena.rest.apispec.repository.ApiSpecMetadataRepository;
import org.catools.athena.rest.apispec.repository.ApiSpecRepository;
import org.catools.athena.rest.apispec.utils.ApiSpecUtils;
import org.catools.athena.rest.core.utils.MetadataPersistentHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiSpecServiceImpl implements ApiSpecService {

  private final ApiSpecMetadataRepository apiSpecMetadataRepository;
  private final ApiPathMetadataRepository apiPathMetadataRepository;
  private final ApiSpecRepository apiSpecRepository;

  private final ApiSpecMapper apiSpecMapper;
  private final ApiSpecUtils apiSpecUtils;

  @Override
  public ApiSpecDto save(final ApiSpecDto apiSpecDto) {
    final ApiSpec apiSpec = apiSpecMapper.apiSpecDtoToApiSpec(apiSpecDto);

    final ApiSpec specToSave = apiSpecRepository.findByProjectCodeAndName(apiSpecDto.getProject(), apiSpecDto.getName()).map(spec -> {
      spec.setTitle(apiSpec.getTitle());
      spec.setVersion(apiSpec.getVersion());
      spec.setLastSyncTime(apiSpec.getLastSyncTime());

      spec.getMetadata().removeIf(m1 -> apiSpecUtils.notContains(apiSpec.getMetadata(), m1));
      spec.getPaths().removeIf(p1 -> apiSpecUtils.notContains(apiSpec.getPaths(), p1));

      spec.getMetadata().addAll(apiSpec.getMetadata().stream().filter(m1 -> apiSpecUtils.notContains(spec.getMetadata(), m1)).collect(Collectors.toSet()));
      spec.getPaths().addAll(apiSpec.getPaths().stream().filter(p1 -> apiSpecUtils.notContains(spec.getPaths(), p1)).collect(Collectors.toSet()));
      return spec;
    }).orElse(apiSpec);

    return saveAndFlash(specToSave);
  }

  @Override
  public Optional<ApiSpecDto> getById(final Long id) {
    return apiSpecRepository.findById(id).map(apiSpecMapper::apiSpecToApiSpecDto);
  }

  @Override
  public Optional<ApiSpecDto> getByProjectCodeAndName(final String projectCode, final String name) {
    return apiSpecRepository.findByProjectCodeAndName(projectCode, name).map(apiSpecMapper::apiSpecToApiSpecDto);
  }

  private ApiSpecDto saveAndFlash(ApiSpec apiSpec) {
    MetadataPersistentHelper.normalizeMetadata(apiSpec.getMetadata(), apiSpecMetadataRepository);
    apiSpecUtils.normalizeApiPaths(apiSpec);
    apiSpec.getPaths().forEach(p -> MetadataPersistentHelper.normalizeMetadata(p.getMetadata(), apiPathMetadataRepository));
    ApiSpec savedApiSpec = apiSpecRepository.saveAndFlush(apiSpec);
    return apiSpecMapper.apiSpecToApiSpecDto(savedApiSpec);
  }
}

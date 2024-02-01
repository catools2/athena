package org.catools.athena.rest.apispec.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.rest.apispec.entity.ApiSpec;
import org.catools.athena.rest.apispec.mapper.ApiSpecMapper;
import org.catools.athena.rest.apispec.repository.ApiSpecRepository;
import org.catools.athena.rest.apispec.utils.ApiSpecUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiSpecServiceImpl implements ApiSpecService {

  private final ApiSpecRepository apiSpecRepository;

  private final ApiSpecMapper apiSpecMapper;
  private final ApiSpecUtils apiSpecUtils;

  @Override
  public ApiSpecDto save(final ApiSpecDto apiSpecDto) {
    final ApiSpec entityToSave = apiSpecMapper.apiSpecDtoToApiSpec(apiSpecDto);
    apiSpecUtils.normalizeApiSpecMetadata(entityToSave);
    final ApiSpec savedEntity = apiSpecRepository.saveAndFlush(entityToSave);
    return apiSpecMapper.apiSpecToApiSpecDto(savedEntity);
  }

  @Override
  public Optional<ApiSpecDto> getApiSpecById(final Long id) {
    return apiSpecRepository.findById(id).map(apiSpecMapper::apiSpecToApiSpecDto);
  }

  @Override
  public Optional<ApiSpecDto> getApiSpecByProjectCodeAndName(final String projectCode, final String name) {
    return apiSpecRepository.findByProjectCodeAndName(projectCode, name).map(apiSpecMapper::apiSpecToApiSpecDto);
  }
}

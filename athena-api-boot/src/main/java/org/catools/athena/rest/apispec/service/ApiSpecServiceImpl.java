package org.catools.athena.rest.apispec.service;

import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.rest.apispec.entity.ApiPath;
import org.catools.athena.rest.apispec.entity.ApiSpec;
import org.catools.athena.rest.apispec.mapper.ApiSpecMapper;
import org.catools.athena.rest.apispec.repository.ApiPathRepository;
import org.catools.athena.rest.apispec.repository.ApiSpecRepository;
import org.catools.athena.rest.apispec.utils.ApiSpecUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ApiSpecServiceImpl implements ApiSpecService {

  private final ApiPathRepository apiPathRepository;
  private final ApiSpecRepository apiSpecRepository;

  private final ApiSpecMapper apiSpecMapper;
  private final ApiSpecUtils apiSpecUtils;

  @Override
  public Pair<ApiSpecDto, Set<ApiPathDto>> saveOpenApiSpec(final JsonElement openAPISpec, final String specName, final String projectCode) {
    return apiSpecUtils.saveOpenApiSpec(openAPISpec, specName, projectCode);
  }

  @Override
  public ApiSpecDto saveApiSpec(final ApiSpecDto apiSpecDto) {
    final ApiSpec apiSpecToSave = apiSpecMapper.apiSpecDtoToApiSpec(apiSpecDto);
    apiSpecUtils.normalizeApiSpecMetadata(apiSpecToSave);
    final ApiSpec savedApiSpec = apiSpecRepository.saveAndFlush(apiSpecToSave);
    return apiSpecMapper.apiSpecToApiSpecDto(savedApiSpec);
  }

  @Override
  public Optional<ApiSpecDto> getApiSpecById(final Long id) {
    return apiSpecRepository.findById(id).map(apiSpecMapper::apiSpecToApiSpecDto);
  }

  @Override
  public Optional<ApiSpecDto> getApiSpecByProjectCodeAndName(final String projectCode, final String name) {
    return apiSpecRepository.findByProjectCodeAndName(projectCode, name).map(apiSpecMapper::apiSpecToApiSpecDto);
  }

  @Override
  public ApiPathDto saveApiPath(final ApiPathDto apiPathDto) {
    final ApiPath apiPathToSave = apiSpecMapper.apiPathDtoToApiPath(apiPathDto);
    apiSpecUtils.normalizeApiPathMetadata(apiPathToSave);
    apiSpecUtils.normalizeApiPathParameter(apiPathToSave);
    final ApiPath savedApiPath = apiPathRepository.saveAndFlush(apiPathToSave);
    return apiSpecMapper.apiPathToApiPathDto(savedApiPath);
  }
}

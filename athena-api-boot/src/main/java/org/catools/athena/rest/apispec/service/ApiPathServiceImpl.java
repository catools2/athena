package org.catools.athena.rest.apispec.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.rest.apispec.entity.ApiPath;
import org.catools.athena.rest.apispec.mapper.ApiSpecMapper;
import org.catools.athena.rest.apispec.repository.ApiPathRepository;
import org.catools.athena.rest.apispec.utils.ApiSpecUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApiPathServiceImpl implements ApiPathService {
  private final ApiPathRepository apiPathRepository;

  private final ApiSpecMapper apiSpecMapper;
  private final ApiSpecUtils apiSpecUtils;

  @Override
  public Optional<ApiPathDto> getById(Long id) {
    return apiPathRepository.findById(id).map(apiSpecMapper::apiPathToApiPathDto);
  }

  @Override
  public Optional<ApiPathDto> search(Long apiSpecId, String method, String url) {
    return apiPathRepository.findByApiSpecIdAndMethodAndUrl(apiSpecId, method, url).map(apiSpecMapper::apiPathToApiPathDto);
  }

  @Override
  public Set<ApiPathDto> getAll() {
    return apiPathRepository.findAll().stream().map(apiSpecMapper::apiPathToApiPathDto).collect(Collectors.toSet());
  }

  @Override
  public ApiPathDto save(ApiPathDto apiPathDto) {
    final ApiPath apiPathToSave = apiSpecMapper.apiPathDtoToApiPath(apiPathDto);
    apiSpecUtils.normalizeApiPathMetadata(apiPathToSave);
    final ApiPath savedApiPath = apiPathRepository.saveAndFlush(apiPathToSave);
    return apiSpecMapper.apiPathToApiPathDto(savedApiPath);
  }
}

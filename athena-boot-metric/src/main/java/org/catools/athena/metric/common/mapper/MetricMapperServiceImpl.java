package org.catools.athena.metric.common.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.feign.service.CachedEnvironmentFeignService;
import org.catools.athena.core.feign.service.CachedProjectFeignService;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.ProjectDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricMapperServiceImpl implements MetricMapperService {

  private final CachedProjectFeignService projectFeignService;
  private final CachedEnvironmentFeignService environmentFeignService;

  /**
   * Get project Id by code
   *
   * @param project
   */
  @Override
  @Transactional(readOnly = true)
  public Long getProjectId(String project) {
    if (StringUtils.isBlank(project)) return null;
    return Optional.ofNullable(projectFeignService.search(project).body())
        .map(ProjectDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("project", "code", project));
  }

  /**
   * Get project code by id
   */
  @Override
  @Transactional(readOnly = true)
  public String getProjectCode(Long projectId) {
    if (projectId == null) return null;
    return Optional.ofNullable(projectFeignService.getById(projectId).body())
        .map(ProjectDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("project", "id", projectId));
  }

  /**
   * Get environment Id by code
   *
   * @param project
   * @param environment
   */
  @Override
  @Transactional(readOnly = true)
  public Long getEnvironmentId(String project, String environment) {
    if (StringUtils.isBlank(environment)) return null;
    return Optional.ofNullable(environmentFeignService.search(project, environment).body())
        .map(EnvironmentDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("environment", "code", environment));
  }

  /**
   * Get environment code by id
   *
   * @param environmentId
   */
  @Override
  @Transactional(readOnly = true)
  public String getEnvironmentCode(Long environmentId) {
    if (environmentId == null) return null;
    return Optional.ofNullable(environmentFeignService.getById(environmentId).body())
        .map(EnvironmentDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("environment", "id", environmentId));
  }
}

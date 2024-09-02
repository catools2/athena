package org.catools.athena.metric.common.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.feign.EnvironmentFeignClient;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricMapperServiceImpl implements MetricMapperService {

  private final ProjectFeignClient projectFeignClient;
  private final EnvironmentFeignClient environmentFeignClient;

  /**
   * Get project Id by code
   *
   * @param projectCode
   */
  @Override
  @Cacheable(value = "projectIdByCode", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public Long getProjectId(String projectCode) {
    if (StringUtils.isBlank(projectCode)) return null;
    return Optional.ofNullable(projectFeignClient.search(projectCode).body())
        .map(ProjectDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("project", "code", projectCode));
  }

  /**
   * Get project code by id
   */
  @Override
  @Cacheable(value = "projectCodeById", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public String getProjectCode(Long projectId) {
    if (projectId == null) return null;
    return Optional.ofNullable(projectFeignClient.getById(projectId).body())
        .map(ProjectDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("project", "id", projectId));
  }

  /**
   * Get environment Id by code
   *
   * @param environmentCode
   */
  @Override
  @Cacheable(value = "environmentIdByCode", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public Long getEnvironmentId(String environmentCode) {
    if (StringUtils.isBlank(environmentCode)) return null;
    return Optional.ofNullable(environmentFeignClient.search(environmentCode).body())
        .map(EnvironmentDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("environment", "code", environmentCode));
  }

  /**
   * Get environment code by id
   *
   * @param environmentId
   */
  @Override
  @Cacheable(value = "environmentCodeById", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public String getEnvironmentCode(Long environmentId) {
    if (environmentId == null) return null;
    return Optional.ofNullable(environmentFeignClient.getById(environmentId).body())
        .map(EnvironmentDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("environment", "id", environmentId));
  }

  @CacheEvict(value = {"projectIdByCode", "projectCodeById", "environmentIdByCode", "environmentCodeById"}, allEntries = true)
  @Scheduled(fixedRate = 10 * 60 * 1000) // 10 minutes
  public void emptyCache() {
    // this is a scheduled timer to clean up caches
  }
}

package org.catools.athena.spec.common.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.core.model.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("unused")
public class ApiSpecMapperServiceImpl implements ApiSpecMapperService {

  @Autowired
  private ProjectFeignClient projectFeignClient;

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
   *
   * @param projectId
   */
  @Override
  @Cacheable(value = "projectCodeById", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public String getProjectCode(Long projectId) {
    if (projectId == null) return null;
    return Optional.ofNullable(projectFeignClient.getById(projectId).body())
        .map(ProjectDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("project", "id", projectId));
  }


  @CacheEvict(value = {"projectIdByCode", "projectCodeById"}, allEntries = true)
  @Scheduled(fixedRate = 10 * 60 * 1000) // 10 minutes
  public void emptyCache() {
    // this is a scheduled timer to clean up caches
  }
}

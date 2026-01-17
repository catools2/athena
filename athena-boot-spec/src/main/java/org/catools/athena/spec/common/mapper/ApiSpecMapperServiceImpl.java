package org.catools.athena.spec.common.mapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.feign.service.CachedProjectFeignService;
import org.catools.athena.model.core.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@SuppressWarnings("unused")
public class ApiSpecMapperServiceImpl implements ApiSpecMapperService {

  @Autowired
  private CachedProjectFeignService projectFeignService;

  /**
   * Get project Id by code
   *
   * @param projectCode
   */
  @Override
  @Transactional(readOnly = true)
  public Long getProjectId(String projectCode) {
    if (StringUtils.isBlank(projectCode)) return null;
    return Optional.ofNullable(projectFeignService.search(projectCode).body())
        .map(ProjectDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("project", "code", projectCode));
  }

  /**
   * Get project code by id
   *
   * @param projectId
   */
  @Override
  @Transactional(readOnly = true)
  public String getProjectCode(Long projectId) {
    if (projectId == null) return null;
    return Optional.ofNullable(projectFeignService.getById(projectId).body())
        .map(ProjectDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("project", "id", projectId));
  }

}

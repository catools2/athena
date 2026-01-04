package org.catools.athena.pipeline.common.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.feign.service.CachedEnvironmentFeignService;
import org.catools.athena.core.feign.service.CachedProjectFeignService;
import org.catools.athena.core.feign.service.CachedUserFeignService;
import org.catools.athena.core.feign.service.CachedVersionFeignService;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.entity.PipelineExecutionStatus;
import org.catools.athena.pipeline.common.repository.PipelineExecutionStatusRepository;
import org.catools.athena.pipeline.common.repository.PipelineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineMapperServiceImpl implements PipelineMapperService {

  private final PipelineExecutionStatusRepository pipelineExecutionStatusRepository;
  private final PipelineRepository pipelineRepository;
  private final CachedProjectFeignService projectFeignService;
  private final CachedVersionFeignService versionFeignService;
  private final CachedUserFeignService userFeignService;
  private final CachedEnvironmentFeignService environmentFeignService;


  @Override
  @Transactional(readOnly = true)
  public PipelineExecutionStatus getPipelineStatusByName(String name) {
    return pipelineExecutionStatusRepository.findByName(name).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public Pipeline getPipelineById(Long pipelineId) {
    return pipelineRepository.findById(pipelineId).orElse(null);
  }

  /**
   * Get project Id by code
   */
  @Override
  public Long getProjectId(String projectCode) {
    if (StringUtils.isBlank(projectCode)) return null;
    return Optional.ofNullable(projectFeignService.search(projectCode).body())
        .map(ProjectDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("project", "code", projectCode));
  }

  /**
   * Get project Id by code
   *
   * @param versionId
   */
  @Override
  public String getProjectByVersionId(Long versionId) {
    if (versionId == null) return null;
    return Optional.ofNullable(versionFeignService.getById(versionId).body())
        .map(VersionDto::getProject)
        .orElseThrow(() -> new RecordNotFoundException("version", "id", versionId));
  }

  /**
   * Get project code by id
   */
  @Override
  public String getProjectCode(Long projectId) {
    if (projectId == null) return null;
    return Optional.ofNullable(projectFeignService.getById(projectId).body())
        .map(ProjectDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("project", "id", projectId));
  }

  /**
   * Get environment Id by code
   */
  @Override
  public Long getEnvironmentId(String projectCode, String environmentCode) {
    if (StringUtils.isBlank(environmentCode)) return null;
    return Optional.ofNullable(environmentFeignService.search(projectCode, environmentCode).body())
        .map(EnvironmentDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("environment", "code", environmentCode));
  }

  /**
   * Get environment code by id
   */
  @Override
  public String getEnvironmentCode(Long environmentId) {
    if (environmentId == null) return null;
    return Optional.ofNullable(environmentFeignService.getById(environmentId).body())
        .map(EnvironmentDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("environment", "id", environmentId));
  }


  /**
   * Get version Id by code
   */
  @Override
  public Long getVersionId(String projectCode, String versionCode) {
    if (StringUtils.isBlank(versionCode)) return null;
    return Optional.ofNullable(versionFeignService.search(projectCode, versionCode).body())
        .map(VersionDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("version", "code", versionCode));
  }

  /**
   * Get version code by id
   */
  @Override
  public String getVersionCode(Long versionId) {
    if (versionId == null) return null;
    return Optional.ofNullable(versionFeignService.getById(versionId).body())
        .map(VersionDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("version", "id", versionId));
  }


  /**
   * Get user id by username
   */
  @Override
  public Long getUserId(String username) {
    if (StringUtils.isBlank(username)) return null;
    return Optional.ofNullable(userFeignService.search(username).body())
        .map(UserDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("user", "username", username));
  }

  /**
   * Get user name by Id
   */
  @Override
  public String getUsername(Long id) {
    if (id == null) return null;
    return Optional.ofNullable(userFeignService.getById(id).body())
        .map(UserDto::getUsername)
        .orElseThrow(() -> new RecordNotFoundException("user", "id", id));
  }

}

package org.catools.athena.tms.common.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.feign.EnvironmentFeignClient;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.core.feign.UserFeignClient;
import org.catools.athena.core.feign.VersionFeignClient;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.repository.ItemRepository;
import org.catools.athena.tms.common.repository.ItemTypeRepository;
import org.catools.athena.tms.common.repository.PriorityRepository;
import org.catools.athena.tms.common.repository.StatusRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TmsMapperServiceImpl implements TmsMapperService {

  private final ItemRepository itemRepository;
  private final ItemTypeRepository itemTypeRepository;
  private final StatusRepository statusRepository;
  private final PriorityRepository priorityRepository;

  private final ProjectFeignClient projectFeignClient;
  private final VersionFeignClient versionFeignClient;
  private final UserFeignClient userFeignClient;
  private final EnvironmentFeignClient environmentFeignClient;

  @Cacheable(value = "itemCache", key = "#code", condition = "#code!=null", unless = "#result == null")
  @Override
  public Item getItemByCode(String code) {
    if (StringUtils.isBlank(code)) return null;
    return itemRepository.findByCode(code).orElse(null);
  }

  @Cacheable(value = "itemTypeCache", key = "#keyword", condition = "#keyword!=null", unless = "#result == null")
  @Override
  public ItemType getItemType(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return itemTypeRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }

  @Cacheable(value = "statusCache", key = "#keyword", condition = "#keyword!=null", unless = "#result == null")
  @Override
  public Status getStatus(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return statusRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }

  @Cacheable(value = "priorityCache", key = "#keyword", condition = "#keyword!=null", unless = "#result == null")
  @Override
  public Priority getPriority(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return priorityRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }


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


  /**
   * Get version Id by code
   *
   * @param versionCode
   */
  @Override
  @Cacheable(value = "versionIdByCode", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public Long getVersionId(String versionCode) {
    if (StringUtils.isBlank(versionCode)) return null;
    return Optional.ofNullable(versionFeignClient.search(versionCode).body())
        .map(VersionDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("version", "code", versionCode));
  }

  /**
   * Get version code by id
   *
   * @param versionId
   */
  @Override
  @Cacheable(value = "versionCodeById", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public String getVersionCode(Long versionId) {
    if (versionId == null) return null;
    return Optional.ofNullable(versionFeignClient.getById(versionId).body())
        .map(VersionDto::getCode)
        .orElseThrow(() -> new RecordNotFoundException("version", "id", versionId));
  }


  /**
   * Get user id by username
   *
   * @param username
   */
  @Override
  @Cacheable(value = "userIdByUsername", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public Long getUserId(String username) {
    if (StringUtils.isBlank(username)) return null;
    return Optional.ofNullable(userFeignClient.search(username).body())
        .map(UserDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("user", "username", username));
  }

  /**
   * Get user name by Id
   *
   * @param id
   */
  @Override
  @Cacheable(value = "userNameById", key = "#p0", condition = "#p0!=null", unless = "#result==null")
  public String getUsername(Long id) {
    if (id == null) return null;
    return Optional.ofNullable(userFeignClient.getById(id).body())
        .map(UserDto::getUsername)
        .orElseThrow(() -> new RecordNotFoundException("user", "id", id));
  }

  @CacheEvict(value = {"projectIdByCode", "projectCodeById", "environmentIdByCode", "environmentCodeById", "versionIdByCode", "versionCodeById", "userNameById", "userIdByUsername", "priorityCache", "itemCache", "cycleCache", "statusCache", "itemTypeCache"}, allEntries = true)
  @Scheduled(fixedRate = 60 * 1000)
  public void emptyCache() {
    // this is a scheduled timer to clean up caches
  }

}

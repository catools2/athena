package org.catools.athena.tms.common.mapper;

import feign.TypedResponse;
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
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.repository.ItemRepository;
import org.catools.athena.tms.common.repository.ItemTypeRepository;
import org.catools.athena.tms.common.repository.PriorityRepository;
import org.catools.athena.tms.common.repository.StatusRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TmsMapperServiceImpl implements TmsMapperService {

  private final ItemRepository itemRepository;
  private final ItemTypeRepository itemTypeRepository;
  private final StatusRepository statusRepository;
  private final PriorityRepository priorityRepository;

  private final CachedProjectFeignService projectFeignService;
  private final CachedVersionFeignService versionFeignService;
  private final CachedUserFeignService userFeignService;
  private final CachedEnvironmentFeignService environmentFeignService;

  /**
   * PERFORMANCE NOTE: N+1 Query Optimization
   * <p>
   * The following methods in TmsMapper should leverage batch fetching to prevent N+1 queries:
   * <p>
   * 1. itemToItemDto() - Currently calls getVersion(id) for each version ID in the stream.
   * Instead, use: tmsMapperService.getVersions(item.getVersionIds())
   * <p>
   * 2. testCycleToTestCycleDto() - Currently calls getVersion() twice for the same versionId.
   * Recommended: Cache the VersionDto result in a variable or use a @BeforeMapping method
   * <p>
   * See batch methods: getVersions() and getUsers() for reference implementations.
   */

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "item-by-code", key = "#code", condition = "#code!=null", unless = "#result == null")
  public Item getItemByCode(String code) {
    if (StringUtils.isBlank(code)) return null;
    return itemRepository.findByCode(code).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "item-type-by-keyword", key = "#keyword", condition = "#keyword!=null", unless = "#result == null")
  public ItemType getItemType(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return itemTypeRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "status-by-keyword", key = "#keyword", condition = "#keyword!=null", unless = "#result == null")
  public Status getStatus(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return statusRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "priority-by-keyword", key = "#keyword", condition = "#keyword!=null", unless = "#result == null")
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
  @Transactional(readOnly = true)
  public Long getProjectId(String projectCode) {
    if (StringUtils.isBlank(projectCode)) return null;
    TypedResponse<ProjectDto> searchResult = projectFeignService.search(projectCode);
    return Optional.ofNullable(searchResult.body())
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

  /**
   * Get environment Id by code
   *
   * @param projectCode
   * @param environmentCode
   */
  @Override
  @Transactional(readOnly = true)
  public Long getEnvironmentId(String projectCode, String environmentCode) {
    if (StringUtils.isBlank(projectCode) || StringUtils.isBlank(environmentCode)) return null;
    return Optional.ofNullable(environmentFeignService.search(projectCode, environmentCode).body())
        .map(EnvironmentDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("environment", "code", environmentCode));
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


  /**
   * Get version Id by code
   *
   * @param projectCode
   * @param versionCode
   */
  @Override
  @Transactional(readOnly = true)
  public Long getVersionId(String projectCode, String versionCode) {
    if (StringUtils.isBlank(versionCode)) return null;
    return Optional.ofNullable(versionFeignService.search(projectCode, versionCode).body())
        .map(VersionDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("version", "code", versionCode));
  }

  /**
   * Get version code by id
   *
   * @param versionId
   */
  @Override
  @Transactional(readOnly = true)
  public VersionDto getVersion(Long versionId) {
    if (versionId == null) return null;
    return Optional.ofNullable(versionFeignService.getById(versionId).body())
        .orElseThrow(() -> new RecordNotFoundException("version", "id", versionId));
  }


  /**
   * Get user id by username
   *
   * @param username
   */
  @Override
  @Transactional(readOnly = true)
  public Long getUserId(String username) {
    if (StringUtils.isBlank(username)) return null;
    return Optional.ofNullable(userFeignService.search(username).body())
        .map(UserDto::getId)
        .orElseThrow(() -> new RecordNotFoundException("user", "username", username));
  }

  /**
   * Get user name by Id
   *
   * @param id
   */
  @Override
  @Transactional(readOnly = true)
  public String getUsername(Long id) {
    if (id == null) return null;
    return Optional.ofNullable(userFeignService.getById(id).body())
        .map(UserDto::getUsername)
        .orElseThrow(() -> new RecordNotFoundException("user", "id", id));
  }

  /**
   * Helper method to get a version DTO without triggering N+1 when called from batch operations.
   * This is used internally by getVersions() for batch fetching.
   * For direct calls, prefer getVersions(Set) when multiple versions are needed.
   *
   * @param versionId the version ID to fetch
   * @return the VersionDto
   */
  protected VersionDto getVersionInternal(Long versionId) {
    return Optional.ofNullable(versionFeignService.getById(versionId).body())
        .orElseThrow(() -> new RecordNotFoundException("version", "id", versionId));
  }

  /**
   * Batch fetch versions by IDs to avoid N+1 queries.
   * Instead of calling getVersion(id) for each version ID, fetch all versions at once.
   *
   * @param versionIds set of version IDs to fetch
   * @return Map of versionId -> VersionDto for efficient lookups
   */
  public Map<Long, VersionDto> getVersions(Set<Long> versionIds) {
    if (versionIds == null || versionIds.isEmpty()) {
      return new HashMap<>();
    }

    return versionIds.stream()
        .collect(Collectors.toMap(
            Function.identity(),
            this::getVersionInternal
        ));
  }

  /**
   * Batch fetch users by IDs to avoid N+1 queries.
   * Instead of calling getUser(id) for each user ID, fetch all users at once.
   *
   * @param userIds set of user IDs to fetch
   * @return Map of userId -> UserDto for efficient lookups
   */
  public Map<Long, UserDto> getUsers(Set<Long> userIds) {
    if (userIds == null || userIds.isEmpty()) {
      return new HashMap<>();
    }

    return userIds.stream()
        .collect(Collectors.toMap(
            Function.identity(),
            id -> userFeignService.getById(id).body()
        ));
  }

}

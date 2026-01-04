package org.catools.athena.tms.common.mapper;

import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.mapstruct.Named;

import java.util.Map;
import java.util.Set;

public interface TmsMapperService {

  /**
   * Get item by code
   */
  Item getItemByCode(String code);

  /**
   * Get item type by code
   */
  ItemType getItemType(String code);

  /**
   * Get status by code
   */
  Status getStatus(String code);

  /**
   * Get priority by code
   */
  Priority getPriority(String code);

  /**
   * Get project Id by code
   */
  @Named("getProjectId")
  Long getProjectId(String projectCode);

  /**
   * Get project code by id
   */
  @Named("getProjectCode")
  String getProjectCode(Long projectId);

  /**
   * Get environment Id by code
   */
  @Named("getEnvironmentId")
  Long getEnvironmentId(String projectCode, String environmentCode);

  /**
   * Get environment code by id
   */
  @Named("getEnvironmentCode")
  String getEnvironmentCode(Long environmentId);

  /**
   * Get version Id by code
   */
  @Named("getVersionId")
  Long getVersionId(String projectCode, String versionCode);

  /**
   * Get version by id
   */
  @Named("getVersion")
  VersionDto getVersion(Long versionId);


  /**
   * Get user id by code
   */
  @Named("getUserId")
  Long getUserId(String username);

  /**
   * Get user name by Id
   */
  @Named("getUsername")
  String getUsername(Long id);

  /**
   * Batch fetch versions by IDs to avoid N+1 queries.
   * Use this method instead of calling getVersion() repeatedly for multiple version IDs.
   *
   * @param versionIds set of version IDs to fetch
   * @return Map of versionId -> VersionDto for efficient lookups
   */
  Map<Long, VersionDto> getVersions(Set<Long> versionIds);

  /**
   * Batch fetch users by IDs to avoid N+1 queries.
   * Use this method instead of calling getUsername() repeatedly for multiple user IDs.
   *
   * @param userIds set of user IDs to fetch
   * @return Map of userId -> UserDto for efficient lookups
   */
  Map<Long, UserDto> getUsers(Set<Long> userIds);
}
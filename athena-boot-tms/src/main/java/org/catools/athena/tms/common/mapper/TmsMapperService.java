package org.catools.athena.tms.common.mapper;

import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.mapstruct.Named;

public interface TmsMapperService {

  /**
   * Get item type by code
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
  Long getEnvironmentId(String environmentCode);

  /**
   * Get environment code by id
   */
  @Named("getEnvironmentCode")
  String getEnvironmentCode(Long environmentId);

  /**
   * Get version Id by code
   */
  @Named("getVersionId")
  Long getVersionId(String versionCode);

  /**
   * Get version code by id
   */
  @Named("getVersionCode")
  String getVersionCode(Long versionId);


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
}
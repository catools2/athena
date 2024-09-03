package org.catools.athena.pipeline.common.mapper;

import org.catools.athena.pipeline.common.entity.Pipeline;
import org.catools.athena.pipeline.common.entity.PipelineExecutionStatus;
import org.mapstruct.Named;

public interface PipelineMapperService {
  PipelineExecutionStatus getPipelineStatusByName(String name);

  /**
   * Get pipeline Id by code
   */
  Pipeline getPipelineById(Long id);

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
package org.catools.athena.metric.common.mapper;

import org.mapstruct.Named;

public interface MetricMapperService {

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

}
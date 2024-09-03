package org.catools.athena.spec.common.mapper;

public interface ApiSpecMapperService {

  /**
   * Get project Id by code
   */
  Long getProjectId(String projectCode);

  /**
   * Get project code by id
   */
  String getProjectCode(Long projectId);

}
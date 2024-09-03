package org.catools.athena.kube.common.mapper;

public interface KubeMapperService {

  /**
   * Get project Id by code
   */
  Long getProjectId(String projectCode);

  /**
   * Get project code by id
   */
  String getProjectCode(Long projectId);

}
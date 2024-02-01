package org.catools.athena.rest.core.mapper;

import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.entity.Version;
import org.catools.athena.rest.pipeline.entity.Pipeline;

import java.util.Set;

public interface CoreMapperService {

  /**
   * Get project by code
   */
  Project getProjectByCode(String code);

  /**
   * Get environment by code
   */
  Environment getEnvironmentByCode(String code);

  /**
   * Get pipeline by id
   */
  Pipeline getPipelineById(Long pipelineId);

  /**
   * Get user by username or alias
   */
  User search(String keyword);

  /**
   * Get version by code
   */
  Version getVersionByCode(String name);

  /**
   * Get versions by codes
   */
  Set<Version> getVersionsByCode(Set<String> codes);

  /**
   * Get versions by codes
   */
  Set<String> getVersionCodesFromVersions(Set<Version> versions);
}
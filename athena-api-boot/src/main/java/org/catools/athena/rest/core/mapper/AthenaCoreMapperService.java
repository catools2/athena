package org.catools.athena.rest.core.mapper;

import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.pipeline.entity.Pipeline;

public interface AthenaCoreMapperService {

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
   * Get user by name
   */
  User getUserByName(String name);
}
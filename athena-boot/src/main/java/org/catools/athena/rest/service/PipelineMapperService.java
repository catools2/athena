package org.catools.athena.rest.service;

import org.catools.athena.rest.entity.core.Environment;
import org.catools.athena.rest.entity.core.Project;
import org.catools.athena.rest.entity.core.User;
import org.catools.athena.rest.entity.pipeline.Pipeline;
import org.catools.athena.rest.entity.pipeline.PipelineExecutionStatus;

public interface PipelineMapperService {

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

  /**
   * Get status by name
   */
  PipelineExecutionStatus getPipelineStatusByName(String name);
}
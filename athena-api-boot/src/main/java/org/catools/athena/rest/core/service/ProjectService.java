package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.common.service.BaseCodifiedService;

import java.util.Set;


public interface ProjectService extends BaseCodifiedService<ProjectDto> {

  /**
   * Retrieve all entity
   */
  Set<ProjectDto> getAll();


}
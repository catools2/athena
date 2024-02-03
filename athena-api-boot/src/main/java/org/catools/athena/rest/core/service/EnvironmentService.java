package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.rest.common.service.BaseCodifiedService;

import java.util.Set;


public interface EnvironmentService extends BaseCodifiedService<EnvironmentDto> {

  /**
   * Retrieve all entity
   */
  Set<EnvironmentDto> getAll();


}
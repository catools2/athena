package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;


public interface UserService extends BaseIdentifiableService<UserDto> {

  /**
   * Get user by name
   */
  Optional<UserDto> getUserByName(String name);
}
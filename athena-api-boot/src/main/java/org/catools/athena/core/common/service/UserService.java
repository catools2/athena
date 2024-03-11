package org.catools.athena.core.common.service;


import org.catools.athena.common.service.SearchableSaveOrUpdateService;
import org.catools.athena.core.model.UserDto;

import java.util.Optional;


public interface UserService extends SearchableSaveOrUpdateService<UserDto> {

  /**
   * Get user by name
   */
  Optional<UserDto> getByUsername(String username);

}
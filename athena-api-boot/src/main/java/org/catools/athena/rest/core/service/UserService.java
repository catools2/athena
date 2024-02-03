package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;
import java.util.Set;


public interface UserService extends BaseIdentifiableService<UserDto> {

  /**
   * Retrieve all entity
   */
  Set<UserDto> getAll();


  /**
   * Get user by name
   */
  Optional<UserDto> getUserByUsername(String username);

  /**
   * Get user by username or first matched alias
   */
  Optional<UserDto> search(String keyword);
}
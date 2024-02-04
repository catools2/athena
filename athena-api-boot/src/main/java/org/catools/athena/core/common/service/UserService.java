package org.catools.athena.core.common.service;


import org.catools.athena.common.service.BaseIdentifiableService;
import org.catools.athena.core.model.UserDto;

import java.util.Optional;


public interface UserService extends BaseIdentifiableService<UserDto> {

  /**
   * Get user by name
   */
  Optional<UserDto> getByUsername(String username);

  /**
   * Get user by username or first matched alias
   */
  Optional<UserDto> search(String keyword);
}
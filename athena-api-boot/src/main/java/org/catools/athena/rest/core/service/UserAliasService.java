package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.UserAliasDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;
import java.util.Set;


public interface UserAliasService extends BaseIdentifiableService<UserAliasDto> {

  /**
   * Get aliases for user
   */
  Set<UserAliasDto> getByUserId(Long userId);

  /**
   * Get user alias by alias
   */
  Optional<UserAliasDto> getByAlias(String alias);
}
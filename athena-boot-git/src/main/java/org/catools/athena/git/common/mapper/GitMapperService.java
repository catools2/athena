package org.catools.athena.git.common.mapper;

import org.catools.athena.git.common.entity.GitRepository;
import org.mapstruct.Named;

public interface GitMapperService {

  /**
   * Get user id by code
   */
  @Named("getUserId")
  Long getUserId(String username);

  /**
   * Get user name by Id
   */
  @Named("getUsername")
  String getUsername(Long id);

  /**
   * Get GitRepository by name
   */
  @Named("findRepositoryByName")
  GitRepository findRepositoryByName(String name);

}
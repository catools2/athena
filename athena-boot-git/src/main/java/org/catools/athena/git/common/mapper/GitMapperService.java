package org.catools.athena.git.common.mapper;

public interface GitMapperService {

  /**
   * Get user id by code
   */
  Long getUserId(String username);

  /**
   * Get user name by Id
   */
  String getUsername(Long id);

}
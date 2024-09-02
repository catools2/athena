package org.catools.athena.git.common.service;


import org.catools.athena.common.service.SaveOrUpdateService;
import org.catools.athena.git.model.GitRepositoryDto;

import java.util.Optional;

public interface GitRepositoryService extends SaveOrUpdateService<GitRepositoryDto> {

  /**
   * Retrieve repository where keyword matches by name or Url
   */
  Optional<GitRepositoryDto> findByNameOrUrl(String keyword);

}
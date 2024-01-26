package org.catools.athena.rest.git.service;


import org.catools.athena.git.model.GitRepositoryDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;

public interface GitRepositoryService extends BaseIdentifiableService<GitRepositoryDto> {

  /**
   * Retrieve repository where keyword matches by name or Url
   */
  Optional<GitRepositoryDto> search(String keyword);

}
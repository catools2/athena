package org.catools.athena.git.common.service;


import org.catools.athena.common.service.BaseIdentifiableService;
import org.catools.athena.git.model.GitRepositoryDto;

import java.util.Optional;

public interface GitRepositoryService extends BaseIdentifiableService<GitRepositoryDto> {

  /**
   * Retrieve repository where keyword matches by name or Url
   */
  Optional<GitRepositoryDto> search(String keyword);

}
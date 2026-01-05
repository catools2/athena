package org.catools.athena.git.common.service;


import org.catools.athena.common.service.SaveOrUpdateService;
import org.catools.athena.model.git.CommitDto;

import java.util.Optional;

public interface CommitService extends SaveOrUpdateService<CommitDto> {

  /**
   * Retrieve commit by name or hash
   */
  Optional<CommitDto> findByHash(String keyword);
}
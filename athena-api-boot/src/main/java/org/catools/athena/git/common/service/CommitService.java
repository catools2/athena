package org.catools.athena.git.common.service;


import org.catools.athena.common.service.BaseIdentifiableService;
import org.catools.athena.git.model.CommitDto;

import java.util.Optional;

public interface CommitService extends BaseIdentifiableService<CommitDto> {

  /**
   * Retrieve commit by name or hash
   */
  Optional<CommitDto> search(String keyword);
}
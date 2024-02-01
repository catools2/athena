package org.catools.athena.rest.git.service;


import org.catools.athena.git.model.CommitDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;

public interface CommitService extends BaseIdentifiableService<CommitDto> {

  /**
   * Retrieve commit by name or hash
   */
  Optional<CommitDto> search(String keyword);
}
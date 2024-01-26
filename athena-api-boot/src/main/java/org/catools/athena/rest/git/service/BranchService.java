package org.catools.athena.rest.git.service;


import org.catools.athena.git.model.BranchDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;

public interface BranchService extends BaseIdentifiableService<BranchDto> {

  /**
   * Retrieve branch by hash
   */
  Optional<BranchDto> search(String hash);

}
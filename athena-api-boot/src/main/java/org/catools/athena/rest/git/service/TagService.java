package org.catools.athena.rest.git.service;


import org.catools.athena.git.model.TagDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;

public interface TagService extends BaseIdentifiableService<TagDto> {

  /**
   * Retrieve tag by name or hash
   */
  Optional<TagDto> search(String keyword);
}
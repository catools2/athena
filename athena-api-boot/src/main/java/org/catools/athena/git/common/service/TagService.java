package org.catools.athena.git.common.service;


import org.catools.athena.common.service.BaseIdentifiableService;
import org.catools.athena.git.model.TagDto;

import java.util.Optional;

public interface TagService extends BaseIdentifiableService<TagDto> {

  /**
   * Retrieve tag by name or hash
   */
  Optional<TagDto> search(String keyword);
}
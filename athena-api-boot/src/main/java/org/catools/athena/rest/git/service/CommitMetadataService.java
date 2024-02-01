package org.catools.athena.rest.git.service;


import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;

public interface CommitMetadataService extends BaseIdentifiableService<MetadataDto> {

  /**
   * Retrieve commit metadata by name and value
   */
  Optional<MetadataDto> search(String name, String value);

}
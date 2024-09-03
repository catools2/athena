package org.catools.athena.core.common.service;

import org.catools.athena.core.model.VersionDto;

import java.util.Optional;

public interface VersionService {
  Optional<VersionDto> search(String keyword);

  Optional<VersionDto> getById(Long id);

  VersionDto saveOrUpdate(VersionDto entity);
}

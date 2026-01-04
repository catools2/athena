package org.catools.athena.core.common.service;

import org.catools.athena.core.entity.VersionFilterDto;
import org.catools.athena.model.core.VersionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VersionService {
  Page<VersionDto> getAll(Pageable pageable);

  Page<VersionDto> getAll(Pageable pageable, VersionFilterDto filters);

  Optional<VersionDto> search(String project, String keyword);

  Optional<VersionDto> getById(Long id);

  VersionDto save(VersionDto entity);

  VersionDto update(VersionDto entity);
}

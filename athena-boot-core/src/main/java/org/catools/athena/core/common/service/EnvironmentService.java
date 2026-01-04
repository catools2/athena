package org.catools.athena.core.common.service;

import org.catools.athena.core.entity.EnvironmentFilterDto;
import org.catools.athena.model.core.EnvironmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EnvironmentService {
  Page<EnvironmentDto> getAll(Pageable pageable);

  Page<EnvironmentDto> getAll(Pageable pageable, EnvironmentFilterDto filters);

  Optional<EnvironmentDto> search(String project, String keyword);

  Optional<EnvironmentDto> getById(Long id);

  EnvironmentDto save(EnvironmentDto entity);

  EnvironmentDto update(EnvironmentDto entity);
}

package org.catools.athena.core.common.service;

import org.catools.athena.core.entity.ProjectFilterDto;
import org.catools.athena.model.core.ProjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProjectService {
  Page<ProjectDto> getAll(Pageable pageable);

  Page<ProjectDto> getAll(Pageable pageable, ProjectFilterDto filters);

  Optional<ProjectDto> search(String keyword);

  Optional<ProjectDto> getById(Long id);

  ProjectDto save(ProjectDto entity);

  ProjectDto update(ProjectDto entity);
}

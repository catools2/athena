package org.catools.athena.core.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.model.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;

  // Mappers
  private final CoreMapper coreMapper;

  @Override
  public Optional<ProjectDto> search(final String keyword) {
    final Optional<Project> project = projectRepository.findByCodeOrName(keyword, keyword);
    return project.map(coreMapper::projectToProjectDto);
  }

  @Override
  public Optional<ProjectDto> getById(final Long id) {
    final Optional<Project> project = projectRepository.findById(id);
    return project.map(coreMapper::projectToProjectDto);
  }

  @Override
  public ProjectDto saveOrUpdate(final ProjectDto entity) {
    log.debug("Saving entity: {}", entity);
    final Project projectToSave = projectRepository.findByCodeOrName(entity.getCode(), entity.getName())
        .map(p -> p.setName(entity.getName()))
        .orElseGet(() -> coreMapper.projectDtoToProject(entity));

    final Project savedProject = RetryUtils.retry(3, 1000, integer -> projectRepository.saveAndFlush(projectToSave));
    return coreMapper.projectToProjectDto(savedProject);
  }
}
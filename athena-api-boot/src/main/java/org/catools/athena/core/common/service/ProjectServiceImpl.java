package org.catools.athena.core.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.model.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;

  // Mappers
  private final CoreMapper coreMapper;

  @Override
  public Optional<ProjectDto> getByCode(final String code) {
    final Optional<Project> project = projectRepository.findByCode(code);
    return project.map(coreMapper::projectToProjectDto);
  }

  @Override
  public Optional<ProjectDto> getById(final Long id) {
    final Optional<Project> project = projectRepository.findById(id);
    return project.map(coreMapper::projectToProjectDto);
  }

  @Override
  public ProjectDto saveOrUpdate(final ProjectDto project) {

    final Project projectToSave = projectRepository.findByCode(project.getCode())
        .map(p -> p.setName(project.getName()))
        .orElseGet(() -> coreMapper.projectDtoToProject(project));

    final Project savedProject = projectRepository.saveAndFlush(projectToSave);
    return coreMapper.projectToProjectDto(savedProject);
  }
}
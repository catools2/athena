package org.catools.athena.rest.core.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.mapper.CoreMapper;
import org.catools.athena.rest.core.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    // Mappers
    private final CoreMapper coreMapper;

    @Override
    @Transactional(readOnly = true)
    public Set<ProjectDto> getProjects() {
        final List<Project> projects = projectRepository.findAll();
        return projects.stream().map(coreMapper::projectToProjectDto).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDto> getProjectByCode(final String code) {
        final Optional<Project> project = projectRepository.findByCode(code);
        return project.map(coreMapper::projectToProjectDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDto> getProjectById(final Long id) {
        final Optional<Project> project = projectRepository.findById(id);
        return project.map(coreMapper::projectToProjectDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDto> getProjectByName(final String name) {
        final Optional<Project> project = projectRepository.findByName(name);
        return project.map(coreMapper::projectToProjectDto);
    }

    @Override
    @Transactional
    public ProjectDto save(final ProjectDto project) {
        final Project projectToSave = coreMapper.projectDtoToProject(project);
        final Project savedProject = projectRepository.saveAndFlush(projectToSave);
        return coreMapper.projectToProjectDto(savedProject);
    }
}
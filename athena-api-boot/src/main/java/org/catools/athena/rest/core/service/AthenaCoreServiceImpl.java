package org.catools.athena.rest.core.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.mapper.AthenaCoreMapper;
import org.catools.athena.rest.core.repository.EnvironmentRepository;
import org.catools.athena.rest.core.repository.ProjectRepository;
import org.catools.athena.rest.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AthenaCoreServiceImpl implements AthenaCoreService {
    private final UserRepository userRepository;
    private final EnvironmentRepository environmentRepository;
    private final ProjectRepository projectRepository;

    // Mappers
    private final AthenaCoreMapper athenaCoreMapper;

    @Override
    @Transactional(readOnly = true)
    public Set<ProjectDto> getProjects() {
        final List<Project> projects = projectRepository.findAll();
        return projects.stream().map(athenaCoreMapper::projectToProjectDto).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDto> getProjectByCode(final String code) {
        final Optional<Project> project = projectRepository.findByCode(code);
        return project.map(athenaCoreMapper::projectToProjectDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDto> getProjectByName(final String name) {
        final Optional<Project> project = projectRepository.findByName(name);
        return project.map(athenaCoreMapper::projectToProjectDto);
    }

    @Override
    public ProjectDto saveProject(final ProjectDto project) {
        final Project projectToSave = athenaCoreMapper.projectDtoToProject(project);
        final Project savedProject = projectRepository.saveAndFlush(projectToSave);
        return athenaCoreMapper.projectToProjectDto(savedProject);
    }

    @Override
    public Set<EnvironmentDto> getEnvironments() {
        final List<Environment> environments = environmentRepository.findAll();
        return environments.stream().map(athenaCoreMapper::environmentToEnvironmentDto).collect(Collectors.toSet());
    }

    @Override
    public Optional<EnvironmentDto> getEnvironmentByCode(final String code) {
        final Optional<Environment> environment = environmentRepository.findByCode(code);
        return environment.map(athenaCoreMapper::environmentToEnvironmentDto);
    }

    @Override
    public EnvironmentDto saveEnvironment(final EnvironmentDto environmentDto) {
        final Environment environmentToSave = athenaCoreMapper.environmentDtoToEnvironment(environmentDto);
        final Environment savedEnvironment = environmentRepository.saveAndFlush(environmentToSave);
        return athenaCoreMapper.environmentToEnvironmentDto(savedEnvironment);
    }

    @Override
    public Set<UserDto> getUsers() {
        final List<User> users = userRepository.findAll();
        return users.stream().map(athenaCoreMapper::userToUserDto).collect(Collectors.toSet());
    }

    @Override
    public Optional<UserDto> getUserByName(final String name) {
        final Optional<User> user = userRepository.findByName(name);
        return user.map(athenaCoreMapper::userToUserDto);
    }

    @Override
    public UserDto saveUser(final UserDto userDto) {
        final User userToSave = athenaCoreMapper.userDtoToUser(userDto);
        final User savedUser = userRepository.saveAndFlush(userToSave);
        return athenaCoreMapper.userToUserDto(savedUser);
    }
}
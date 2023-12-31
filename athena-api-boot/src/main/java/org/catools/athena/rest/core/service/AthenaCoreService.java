package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;

import java.util.Optional;
import java.util.Set;


public interface AthenaCoreService {

    /**
     * Returns a list of all available projects.
     */
    Set<ProjectDto> getProjects();

    /**
     * Get project by code
     */
    Optional<ProjectDto> getProjectByCode(String code);

    /**
     * Get project by code
     */
    Optional<ProjectDto> getProjectByName(String code);

    /**
     * Get project by id
     */
    Optional<ProjectDto> getProjectById(Long id);

    /**
     * Save project
     */
    ProjectDto saveProject(ProjectDto project);

    /**
     * Returns a list of all available environments.
     */
    Set<EnvironmentDto> getEnvironments();

    /**
     * Get environment by code
     */
    Optional<EnvironmentDto> getEnvironmentByCode(String code);

    /**
     * Get environment by id
     */
    Optional<EnvironmentDto> getEnvironmentById(Long id);

    /**
     * Save environment
     */
    EnvironmentDto saveEnvironment(EnvironmentDto environmentDto);

    /**
     * Returns a list of all available users.
     */
    Set<UserDto> getUsers();

    /**
     * Get user by name
     */
    Optional<UserDto> getUserByName(String name);

    /**
     * Get user by id
     */
    Optional<UserDto> getUserById(Long id);

    /**
     * Save user and return user id
     */
    UserDto saveUser(UserDto userDto);

}
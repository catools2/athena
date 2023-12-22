package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface AthenaCoreService {

  /**
   * Returns a list of all available projects.
   */
  List<ProjectDto> getProjects();

  /**
   * Get project by code
   */
  Optional<ProjectDto> getProjectByCode(String code);

  /**
   * Get project by code
   */
  Optional<ProjectDto> getProjectByName(String code);

  /**
   * Save project
   */
  ProjectDto saveProject(ProjectDto project);

  /**
   * Returns a list of all available environments.
   */
  List<EnvironmentDto> getEnvironments();

  /**
   * Get environment by code
   */
  Optional<EnvironmentDto> getEnvironmentByCode(String code);

  /**
   * Save environment
   */
  EnvironmentDto saveEnvironment(EnvironmentDto environmentDto);

  /**
   * Returns a list of all available users.
   */
  List<UserDto> getUsers();

  /**
   * Get user by name
   */
  Optional<UserDto> getUserByName(String name);

  /**
   * Save user and return user id
   */
  UserDto saveUser(UserDto userDto);

}
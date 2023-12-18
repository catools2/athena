package org.catools.athena.rest.service;


import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AthenaPipelineService {

  /**
   * Returns a list of all available projects.
   */
  List<ProjectDto> getProjects();

  /**
   * Get project by code
   */
  Optional<ProjectDto> getProjectByCode(String code);

  /**
   * Save project
   */
  long saveProject(ProjectDto project);

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
  long saveEnvironment(EnvironmentDto environmentDto);

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
  long saveUser(UserDto userDto);

  /**
   * Save pipeline
   */
  long savePipeline(PipelineDto pipelineDto);

  /**
   * Update pipeline end date
   */
  PipelineDto updatePipelineEndDate(long pipelineId, Date enddate);

  /**
   * Save execution
   */
  long saveExecution(PipelineExecutionDto execution);

  /**
   * Save scenario execution
   */
  long saveScenarioExecution(PipelineScenarioExecutionDto scenario);

  /**
   * Save execution
   */
  long saveStatus(PipelineExecutionStatusDto status);

  /**
   * Get pipeline
   */
  Optional<PipelineDto> getLastPipelineDto(String pipelineName, String pipelineNumber, String environmentCode);

}
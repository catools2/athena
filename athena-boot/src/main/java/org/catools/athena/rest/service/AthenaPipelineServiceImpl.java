package org.catools.athena.rest.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.entity.core.Environment;
import org.catools.athena.rest.entity.core.Project;
import org.catools.athena.rest.entity.core.User;
import org.catools.athena.rest.entity.pipeline.*;
import org.catools.athena.rest.exception.PipelineNotExistsException;
import org.catools.athena.rest.mapper.PipelineMapper;
import org.catools.athena.rest.repository.core.EnvironmentRepository;
import org.catools.athena.rest.repository.core.ProjectRepository;
import org.catools.athena.rest.repository.core.UserRepository;
import org.catools.athena.rest.repository.pipeline.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class AthenaPipelineServiceImpl implements AthenaPipelineService {
  private final PipelineExecutionStatusRepository pipelineExecutionStatusRepository;
  private final PipelineExecutionRepository pipelineExecutionRepository;
  private final PipelineScenarioExecutionRepository pipelineScenarioExecutionRepository;
  private final PipelineExecutionMetaDataRepository pipelineExecutionMetaDataRepository;
  private final PipelineMetaDataRepository pipelineMetaDataRepository;
  private final PipelineRepository pipelineRepository;
  private final UserRepository userRepository;
  private final EnvironmentRepository environmentRepository;
  private final ProjectRepository projectRepository;
  private final PipelineMapper pipelineMapper;

  @Override
  @Transactional(readOnly = true)
  public List<ProjectDto> getProjects() {
    final List<Project> projects = projectRepository.findAll();
    return projects.stream().map(pipelineMapper::projectToProjectDto).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ProjectDto> getProjectByCode(final String code) {
    final Optional<Project> project = projectRepository.findByCode(code);
    return project.map(pipelineMapper::projectToProjectDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ProjectDto> getProjectByName(final String name) {
    final Optional<Project> project = projectRepository.findByName(name);
    return project.map(pipelineMapper::projectToProjectDto);
  }

  @Override
  public ProjectDto saveProject(final ProjectDto project) {
    final Project projectToSave = pipelineMapper.projectDtoToProject(project);
    final Project savedProject = projectRepository.saveAndFlush(projectToSave);
    return pipelineMapper.projectToProjectDto(savedProject);
  }

  @Override
  public List<EnvironmentDto> getEnvironments() {
    final List<Environment> environments = environmentRepository.findAll();
    return environments.stream().map(pipelineMapper::environmentToEnvironmentDto).toList();
  }

  @Override
  public Optional<EnvironmentDto> getEnvironmentByCode(final String code) {
    final Optional<Environment> environment = environmentRepository.findByCode(code);
    return environment.map(pipelineMapper::environmentToEnvironmentDto);
  }

  @Override
  public EnvironmentDto saveEnvironment(final EnvironmentDto environmentDto) {
    final Environment environmentToSave = pipelineMapper.environmentDtoToEnvironment(environmentDto);
    final Environment savedEnvironment = environmentRepository.saveAndFlush(environmentToSave);
    return pipelineMapper.environmentToEnvironmentDto(savedEnvironment);
  }

  @Override
  public List<UserDto> getUsers() {
    final List<User> users = userRepository.findAll();
    return users.stream().map(pipelineMapper::userToUserDto).toList();
  }

  @Override
  public Optional<UserDto> getUserByName(final String name) {
    final Optional<User> user = userRepository.findByName(name);
    return user.map(pipelineMapper::userToUserDto);
  }

  @Override
  public UserDto saveUser(final UserDto userDto) {
    final User userToSave = pipelineMapper.userDtoToUser(userDto);
    final User savedUser = userRepository.saveAndFlush(userToSave);
    return pipelineMapper.userToUserDto(savedUser);
  }

  @Override
  public PipelineDto savePipeline(final PipelineDto pipelineDto) {
    final Pipeline pipelineToSave = pipelineMapper.pipelineDtoToPipeline(pipelineDto);
    normalizePipelineMetadata(pipelineToSave);
    final Pipeline savedPipeline = pipelineRepository.saveAndFlush(pipelineToSave);
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  @Override
  public PipelineDto updatePipelineEndDate(final long pipelineId, final Date enddate) {
    final Pipeline pipelineToPatch = pipelineRepository.findById(pipelineId).orElseThrow(PipelineNotExistsException::new);
    pipelineToPatch.setEndDate(enddate);
    final Pipeline savedPipeline = pipelineRepository.saveAndFlush(pipelineToPatch);
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  @Override
  public PipelineExecutionDto saveExecution(final PipelineExecutionDto execution) {
    final PipelineExecution pipelineExecution = pipelineMapper.executionDtoToExecution(execution);
    pipelineExecution.setMetadata(normalizePipelineExecutionMetadata(pipelineExecution.getMetadata()));
    final PipelineExecution savedPipelineExecution = pipelineExecutionRepository.saveAndFlush(pipelineExecution);
    return pipelineMapper.executionToExecutionDto(savedPipelineExecution);
  }

  @Override
  public PipelineScenarioExecutionDto saveScenarioExecution(final PipelineScenarioExecutionDto execution) {
    final PipelineScenarioExecution pipelineExecution = pipelineMapper.scenarioExecutionDtoToScenarioExecution(execution);
    pipelineExecution.setMetadata(normalizePipelineExecutionMetadata(pipelineExecution.getMetadata()));
    final PipelineScenarioExecution savedPipelineExecution = pipelineScenarioExecutionRepository.saveAndFlush(pipelineExecution);
    return pipelineMapper.scenarioExecutionToScenarioExecutionDto(savedPipelineExecution);
  }

  @Override
  public List<PipelineExecutionStatusDto> getExecutionStatuses() {
    final List<PipelineExecutionStatus> executionStatus = pipelineExecutionStatusRepository.findAll();
    return executionStatus.stream().map(pipelineMapper::pipelineStatusToPipelineStatusDto).toList();
  }

  @Override
  public Optional<PipelineExecutionStatusDto> getExecutionStatusByName(String name) {
    final Optional<PipelineExecutionStatus> executionStatus = pipelineExecutionStatusRepository.findByName(name);
    return executionStatus.map(pipelineMapper::pipelineStatusToPipelineStatusDto);
  }

  @Override
  public PipelineExecutionStatusDto saveExecutionStatus(final PipelineExecutionStatusDto status) {
    final PipelineExecutionStatus pipelineExecutionStatus = pipelineMapper.pipelineStatusDtoToPipelineStatus(status);
    final PipelineExecutionStatus savedPipelineExecutionStatus = pipelineExecutionStatusRepository.saveAndFlush(pipelineExecutionStatus);
    return pipelineMapper.pipelineStatusToPipelineStatusDto(savedPipelineExecutionStatus);
  }

  @Override
  public Optional<PipelineDto> getLastPipelineDto(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final String environmentCode) {
    return getLastPipeline(pipelineName, pipelineNumber, environmentCode).map(pipelineMapper::pipelineToPipelineDto);
  }

  private Optional<Pipeline> getLastPipeline(final String pipelineName, @Nullable final String pipelineNumber, @Nullable final String environmentCode) {
    Optional<Pipeline> pipeline = Optional.empty();
    if (isNotBlank(pipelineName) && isNotBlank(pipelineNumber) && isNotBlank(environmentCode)) {
      pipeline = pipelineRepository.findTop1ByEnvironmentCodeAndNameAndNumberOrderByIdDesc(environmentCode, pipelineName, pipelineNumber);
    } else if (isNotBlank(pipelineName) && isNotBlank(environmentCode)) {
      pipeline = pipelineRepository.findTop1ByEnvironmentCodeAndNameOrderByNumberDescIdDesc(environmentCode, pipelineName);
    } else if (isNotBlank(pipelineName) && isNotBlank(pipelineNumber)) {
      pipeline = pipelineRepository.findTop1ByNameAndNumberOrderByIdDesc(pipelineName, pipelineNumber);
    } else if (isNotBlank(pipelineName)) {
      pipeline = pipelineRepository.findTop1ByNameOrderByNumberDescIdDesc(pipelineName);
    }
    return pipeline;
  }

  private void normalizePipelineMetadata(Pipeline pipeline) {
    final List<PipelineMetadata> metadata = new ArrayList<>();
    for (PipelineMetadata md : pipeline.getMetadata()) {
      // If we do not have md then we read it from DB and if MD does not exist we create one and assign it to the pipeline
      PipelineMetadata pipelineMD =
          pipelineMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> pipelineMetaDataRepository.saveAndFlush(md));
      metadata.add(pipelineMD);
    }
    pipeline.setMetadata(metadata);
  }

  private List<PipelineExecutionMetadata> normalizePipelineExecutionMetadata(List<PipelineExecutionMetadata> metadataList) {
    final List<PipelineExecutionMetadata> metadata = new ArrayList<>();
    for (PipelineExecutionMetadata md : metadataList) {
      // If we do not have md then we read it from DB and if MD does not exist we create one and assign it to the pipeline
      PipelineExecutionMetadata pipelineMD =
          pipelineExecutionMetaDataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> pipelineExecutionMetaDataRepository.saveAndFlush(md));
      metadata.add(pipelineMD);
    }
    return metadata;
  }
}
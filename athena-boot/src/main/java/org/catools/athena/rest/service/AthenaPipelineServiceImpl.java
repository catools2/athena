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
import org.catools.athena.rest.exception.GeneralBadRequestException;
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
  public long saveProject(final ProjectDto project) {
    final Project projectToSave = pipelineMapper.projectDtoToProject(project);
    final Project savedProject = projectRepository.saveAndFlush(projectToSave);
    return savedProject.getId();
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
  public long saveEnvironment(final EnvironmentDto environmentDto) {
    final Environment environmentToSave = pipelineMapper.environmentDtoToEnvironment(environmentDto);
    final Environment savedEnvironment = environmentRepository.saveAndFlush(environmentToSave);
    return savedEnvironment.getId();
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
  public long saveUser(final UserDto userDto) {
    final User userToSave = pipelineMapper.userDtoToUser(userDto);
    final User savedUser = userRepository.saveAndFlush(userToSave);
    return savedUser.getId();
  }

  @Override
  public long savePipeline(final PipelineDto pipelineDto) {
    final Pipeline pipelineToSave = pipelineMapper.pipelineDtoToPipeline(pipelineDto);
    normalizePipelineMetadata(pipelineToSave);
    final Pipeline savedPipeline = pipelineRepository.saveAndFlush(pipelineToSave);
    return savedPipeline.getId();
  }

  @Override
  public PipelineDto updatePipelineEndDate(final long pipelineId, final Date enddate) {
    final Optional<Pipeline> pipelineToPatch = pipelineRepository.findById(pipelineId);
    if (pipelineToPatch.isEmpty())
      throw new GeneralBadRequestException("Cannot update pipeline end time, Pipeline does not exists! pipelineId: " + pipelineId);
    pipelineToPatch.get().setEndDate(enddate);
    final Pipeline savedPipeline = pipelineRepository.saveAndFlush(pipelineToPatch.get());
    return pipelineMapper.pipelineToPipelineDto(savedPipeline);
  }

  @Override
  public long saveExecution(final PipelineExecutionDto execution) {
    if (pipelineExecutionStatusRepository.findByName(execution.getStatus()).isEmpty()) {
      pipelineExecutionStatusRepository.saveAndFlush(new PipelineExecutionStatus().setName(execution.getStatus()));
    }

    final PipelineExecution pipelineExecution = pipelineMapper.executionDtoToExecution(execution);
    pipelineExecution.setMetadata(normalizePipelineExecutionMetadata(pipelineExecution.getMetadata()));
    final PipelineExecution savedPipelineExecution = pipelineExecutionRepository.saveAndFlush(pipelineExecution);
    return savedPipelineExecution.getId();
  }

  @Override
  public long saveScenarioExecution(final PipelineScenarioExecutionDto execution) {
    if (pipelineExecutionStatusRepository.findByName(execution.getStatus()).isEmpty()) {
      pipelineExecutionStatusRepository.saveAndFlush(new PipelineExecutionStatus().setName(execution.getStatus()));
    }

    final PipelineScenarioExecution pipelineExecution = pipelineMapper.scenarioExecutionDtoToScenarioExecution(execution);
    pipelineExecution.setMetadata(normalizePipelineExecutionMetadata(pipelineExecution.getMetadata()));
    final PipelineScenarioExecution savedPipelineExecution = pipelineScenarioExecutionRepository.saveAndFlush(pipelineExecution);
    return savedPipelineExecution.getId();
  }

  @Override
  public long saveStatus(final PipelineExecutionStatusDto status) {
    final PipelineExecutionStatus pipelineExecutionStatus = pipelineMapper.pipelineStatusDtoToPipelineStatus(status);
    final PipelineExecutionStatus savedPipelineExecutionStatus = pipelineExecutionStatusRepository.saveAndFlush(pipelineExecutionStatus);
    return savedPipelineExecutionStatus.getId();
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
      // If metadata id is already set it means we read it from DB and there are no reason to read it again
      if (md.getId() != null) {
        metadata.add(md);
        continue;
      }

      // If we do not have md then we read it from DB and if MD does not exist we create one and assign it to the pipeline
      Optional<PipelineMetadata> pipelineMD = pipelineMetaDataRepository.findByNameAndValue(md.getName(), md.getValue());
      if (pipelineMD.isPresent())
        metadata.add(pipelineMD.get());
      else
        metadata.add(pipelineMetaDataRepository.saveAndFlush(md));
    }
    pipeline.setMetadata(metadata);
  }

  private List<PipelineExecutionMetadata> normalizePipelineExecutionMetadata(List<PipelineExecutionMetadata> metadataList) {
    final List<PipelineExecutionMetadata> metadata = new ArrayList<>();
    for (PipelineExecutionMetadata md : metadataList) {
      // If metadata id is already set it means we read it from DB and there are no reason to read it again
      if (md.getId() != null) {
        metadata.add(md);
        continue;
      }

      // If we do not have md then we read it from DB and if MD does not exist we create one and assign it to the pipeline
      Optional<PipelineExecutionMetadata> pipelineMD = pipelineExecutionMetaDataRepository.findByNameAndValue(md.getName(), md.getValue());
      if (pipelineMD.isPresent())
        metadata.add(pipelineMD.get());
      else
        metadata.add(pipelineExecutionMetaDataRepository.saveAndFlush(md));
    }
    return metadata;
  }
}
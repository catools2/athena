package com.bt.automation.rest.qualitycenter.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.MetadataDto;
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
import org.instancio.Instancio;

import java.util.ArrayList;
import java.util.List;

import static org.instancio.Select.field;

@UtilityClass
public class PipelineBuilder {
  public static UserDto buildUserDto() {
    return Instancio.of(UserDto.class)
        .ignore(field(UserDto::getId))
        .create();
  }

  public static User buildUser(UserDto userDto) {
    return new User()
        .setId(userDto.getId())
        .setName(userDto.getName());
  }

  public static PipelineExecutionStatusDto buildPipelineExecutionStatusDto() {
    return Instancio.of(PipelineExecutionStatusDto.class)
        .ignore(field(PipelineExecutionStatusDto::getId))
        .create();
  }

  public static PipelineExecutionStatus buildPipelineExecutionStatus(PipelineExecutionStatusDto statusDto) {
    return new PipelineExecutionStatus()
        .setId(statusDto.getId())
        .setName(statusDto.getName());
  }

  public static ProjectDto buildProjectDto() {
    return Instancio.of(ProjectDto.class)
        .ignore(field(ProjectDto::getId))
        .generate(field(ProjectDto::getCode), gen -> gen.string().length(1, 5))
        .create();
  }

  public static Project buildProject(ProjectDto projectDto) {
    return new Project()
        .setId(projectDto.getId())
        .setCode(projectDto.getCode())
        .setName(projectDto.getName());
  }

  public static EnvironmentDto buildEnvironmentDto(ProjectDto project) {
    return Instancio.of(EnvironmentDto.class)
        .ignore(field(EnvironmentDto::getId))
        .generate(field(EnvironmentDto::getCode), gen -> gen.string().length(1, 5))
        .set(field(EnvironmentDto::getProjectCode), project.getCode())
        .create();
  }

  public static Environment buildEnvironment(EnvironmentDto environmentDto, Project project) {
    return new Environment()
        .setId(environmentDto.getId())
        .setName(environmentDto.getName())
        .setCode(environmentDto.getCode())
        .setProject(project);
  }

  public static PipelineDto buildPipelineDto(EnvironmentDto environment) {
    return Instancio.of(PipelineDto.class)
        .ignore(field(MetadataDto::getId))
        .ignore(field(PipelineDto::getId))
        .set(field(PipelineDto::getEnvironmentCode), environment.getCode())
        .create();
  }

  public static Pipeline buildPipeline(PipelineDto pipelineDto, Environment environment) {
    List<PipelineMetadata> metadata = new ArrayList<>();
    for (MetadataDto md : pipelineDto.getMetadata()) {
      metadata.add(new PipelineMetadata().setId(md.getId()).setName(md.getName()).setValue(md.getValue()));
    }

    return new Pipeline()
        .setId(pipelineDto.getId())
        .setName(pipelineDto.getName())
        .setNumber(pipelineDto.getNumber())
        .setEnvironment(environment)
        .setDescription(pipelineDto.getDescription())
        .setStartDate(pipelineDto.getStartDate())
        .setEndDate(pipelineDto.getEndDate())
        .setMetadata(metadata);
  }

  public static PipelineExecutionDto buildExecutionDto(PipelineDto pipeline, PipelineExecutionStatusDto pipelineStatus, UserDto user) {
    return Instancio.of(PipelineExecutionDto.class)
        .ignore(field(PipelineExecutionDto::getId))
        .ignore(field(MetadataDto::getId))
        .set(field(PipelineExecutionDto::getPipelineId), pipeline.getId())
        .set(field(PipelineExecutionDto::getExecutor), user.getName())
        .set(field(PipelineExecutionDto::getStatus), pipelineStatus.getName())
        .create();
  }

  public static PipelineExecution buildExecution(PipelineExecutionDto pipelineExecutionDto, Pipeline pipeline, PipelineExecutionStatus pipelineStatus, User user) {
    List<PipelineExecutionMetadata> metadata = new ArrayList<>();
    for (MetadataDto md : pipelineExecutionDto.getMetadata()) {
      metadata.add(new PipelineExecutionMetadata().setId(md.getId()).setName(md.getName()).setValue(md.getValue()));
    }

    return new PipelineExecution()
        .setId(pipelineExecutionDto.getId())
        .setPipeline(pipeline)
        .setStatus(pipelineStatus)
        .setExecutor(user)
        .setMetadata(metadata)
        .setPackageName(pipelineExecutionDto.getPackageName())
        .setClassName(pipelineExecutionDto.getClassName())
        .setMethodName(pipelineExecutionDto.getMethodName())
        .setParameters(pipelineExecutionDto.getParameters())
        .setStartTime(pipelineExecutionDto.getStartTime())
        .setEndTime(pipelineExecutionDto.getEndTime())
        .setTestStartTime(pipelineExecutionDto.getTestStartTime())
        .setTestEndTime(pipelineExecutionDto.getTestEndTime())
        .setBeforeClassStartTime(pipelineExecutionDto.getBeforeClassStartTime())
        .setBeforeClassEndTime(pipelineExecutionDto.getBeforeClassEndTime())
        .setBeforeMethodStartTime(pipelineExecutionDto.getBeforeMethodStartTime())
        .setBeforeMethodEndTime(pipelineExecutionDto.getBeforeMethodEndTime());
  }

  public static PipelineScenarioExecutionDto buildScenarioExecutionDto(PipelineDto pipeline, PipelineExecutionStatusDto pipelineStatus, UserDto user) {
    return Instancio.of(PipelineScenarioExecutionDto.class)
        .ignore(field(MetadataDto::getId))
        .ignore(field(PipelineScenarioExecutionDto::getId))
        .set(field(PipelineScenarioExecutionDto::getPipelineId), pipeline.getId())
        .set(field(PipelineScenarioExecutionDto::getExecutor), user.getName())
        .set(field(PipelineScenarioExecutionDto::getStatus), pipelineStatus.getName())
        .create();
  }

  public static PipelineScenarioExecution buildScenarioExecution(PipelineScenarioExecutionDto scenarioExecutionDto, Pipeline pipeline, PipelineExecutionStatus pipelineStatus, User user) {
    List<PipelineExecutionMetadata> metadata = new ArrayList<>();
    for (MetadataDto md : scenarioExecutionDto.getMetadata()) {
      metadata.add(new PipelineExecutionMetadata().setId(md.getId()).setName(md.getName()).setValue(md.getValue()));
    }

    return new PipelineScenarioExecution()
        .setId(scenarioExecutionDto.getId())
        .setPipeline(pipeline)
        .setStatus(pipelineStatus)
        .setExecutor(user)
        .setMetadata(metadata)
        .setFeature(scenarioExecutionDto.getFeature())
        .setScenario(scenarioExecutionDto.getScenario())
        .setParameters(scenarioExecutionDto.getParameters())
        .setStartTime(scenarioExecutionDto.getStartTime())
        .setEndTime(scenarioExecutionDto.getEndTime())
        .setBeforeScenarioEndTime(scenarioExecutionDto.getBeforeScenarioEndTime())
        .setBeforeScenarioStartTime(scenarioExecutionDto.getBeforeScenarioStartTime());
  }
}

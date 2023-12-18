package com.bt.automation.rest.qualitycenter.mapper;

import com.bt.automation.rest.qualitycenter.AthenaBaseTest;
import com.bt.automation.rest.qualitycenter.builder.PipelineBuilder;
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
import org.catools.athena.rest.mapper.PipelineMapper;
import org.catools.athena.rest.service.AthenaPipelineService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


public class PipelineMapperIT extends AthenaBaseTest {
  private static ProjectDto PROJECT_DTO;
  private static Project PROJECT;
  private static EnvironmentDto ENVIRONMENT_DTO;
  private static Environment ENVIRONMENT;
  private static PipelineDto PIPELINE_DTO;
  private static Pipeline PIPELINE;
  private static PipelineExecutionDto PIPELINE_EXECUTION_DTO;
  private static PipelineExecution PIPELINE_EXECUTION;

  private static PipelineScenarioExecutionDto PIPELINE_SCENARIO_EXECUTION_DTO;
  private static PipelineScenarioExecution PIPELINE_SCENARIO_EXECUTION;


  @Autowired
  PipelineMapper mapper;

  @Autowired
  AthenaPipelineService athenaPipelineService;

  @BeforeAll
  public void beforeAll() {
    UserDto userDto = PipelineBuilder.buildUserDto();
    userDto.setId(athenaPipelineService.saveUser(userDto));
    User user = PipelineBuilder.buildUser(userDto);

    PROJECT_DTO = PipelineBuilder.buildProjectDto();
    PROJECT_DTO.setId(athenaPipelineService.saveProject(PROJECT_DTO));
    PROJECT = PipelineBuilder.buildProject(PROJECT_DTO);

    PipelineExecutionStatusDto statusDto = PipelineBuilder.buildPipelineExecutionStatusDto();
    statusDto.setId(athenaPipelineService.saveStatus(statusDto));
    PipelineExecutionStatus STATUS = PipelineBuilder.buildPipelineExecutionStatus(statusDto);

    ENVIRONMENT_DTO = PipelineBuilder.buildEnvironmentDto(PROJECT_DTO);
    ENVIRONMENT_DTO.setId(athenaPipelineService.saveEnvironment(ENVIRONMENT_DTO));
    ENVIRONMENT = PipelineBuilder.buildEnvironment(ENVIRONMENT_DTO, PROJECT);

    PIPELINE_DTO = PipelineBuilder.buildPipelineDto(ENVIRONMENT_DTO);
    PIPELINE_DTO.setId(athenaPipelineService.savePipeline(PIPELINE_DTO));
    PIPELINE = PipelineBuilder.buildPipeline(PIPELINE_DTO, ENVIRONMENT);

    PIPELINE_EXECUTION_DTO = PipelineBuilder.buildExecutionDto(PIPELINE_DTO, statusDto, userDto);
    PIPELINE_EXECUTION_DTO.setPipelineId(athenaPipelineService.saveExecution(PIPELINE_EXECUTION_DTO));
    PIPELINE_EXECUTION = PipelineBuilder.buildExecution(PIPELINE_EXECUTION_DTO, PIPELINE, STATUS, user);

    PIPELINE_SCENARIO_EXECUTION_DTO = PipelineBuilder.buildScenarioExecutionDto(PIPELINE_DTO, statusDto, userDto);
    PIPELINE_SCENARIO_EXECUTION_DTO.setId(athenaPipelineService.saveScenarioExecution(PIPELINE_SCENARIO_EXECUTION_DTO));
    PIPELINE_SCENARIO_EXECUTION = PipelineBuilder.buildScenarioExecution(PIPELINE_SCENARIO_EXECUTION_DTO, PIPELINE, STATUS, user);
  }

  @Test
  public void executionToExecutionDto() {
    final PipelineExecutionDto pipelineExecutionDto = mapper.executionToExecutionDto(PIPELINE_EXECUTION);
    assertThat(pipelineExecutionDto.getId(), equalTo(PIPELINE_EXECUTION.getId()));
    assertThat(pipelineExecutionDto.getPackageName(), equalTo(PIPELINE_EXECUTION.getPackageName()));
    assertThat(pipelineExecutionDto.getClassName(), equalTo(PIPELINE_EXECUTION.getClassName()));
    assertThat(pipelineExecutionDto.getMethodName(), equalTo(PIPELINE_EXECUTION.getMethodName()));
    assertThat(pipelineExecutionDto.getParameters(), equalTo(PIPELINE_EXECUTION.getParameters()));
    assertThat(pipelineExecutionDto.getStartTime(), equalTo(PIPELINE_EXECUTION.getStartTime()));
    assertThat(pipelineExecutionDto.getEndTime(), equalTo(PIPELINE_EXECUTION.getEndTime()));
    assertThat(pipelineExecutionDto.getTestStartTime(), equalTo(PIPELINE_EXECUTION.getTestStartTime()));
    assertThat(pipelineExecutionDto.getTestEndTime(), equalTo(PIPELINE_EXECUTION.getTestEndTime()));
    assertThat(pipelineExecutionDto.getBeforeClassStartTime(), equalTo(PIPELINE_EXECUTION.getBeforeClassStartTime()));
    assertThat(pipelineExecutionDto.getBeforeClassEndTime(), equalTo(PIPELINE_EXECUTION.getBeforeClassEndTime()));
    assertThat(pipelineExecutionDto.getBeforeMethodStartTime(), equalTo(PIPELINE_EXECUTION.getBeforeMethodStartTime()));
    assertThat(pipelineExecutionDto.getBeforeMethodEndTime(), equalTo(PIPELINE_EXECUTION.getBeforeMethodEndTime()));
    assertThat(pipelineExecutionDto.getStatus(), equalTo(PIPELINE_EXECUTION.getStatus().getName()));
    assertThat(pipelineExecutionDto.getExecutor(), equalTo(PIPELINE_EXECUTION.getExecutor().getName()));
    assertThat(pipelineExecutionDto.getPipelineId(), equalTo(PIPELINE_EXECUTION.getPipeline().getId()));
    assertThat(pipelineExecutionDto.getMetadata().get(0).getName(), equalTo(PIPELINE_EXECUTION.getMetadata().get(0).getName()));
    assertThat(pipelineExecutionDto.getMetadata().get(0).getValue(), equalTo(PIPELINE_EXECUTION.getMetadata().get(0).getValue()));
    assertThat(pipelineExecutionDto.getMetadata().get(1).getName(), equalTo(PIPELINE_EXECUTION.getMetadata().get(1).getName()));
    assertThat(pipelineExecutionDto.getMetadata().get(1).getValue(), equalTo(PIPELINE_EXECUTION.getMetadata().get(1).getValue()));
  }


  @Rollback
  @Transactional
  @Test
  public void executionDtoToExecution() {
    final PipelineExecution execution = mapper.executionDtoToExecution(PIPELINE_EXECUTION_DTO);
    assertThat(execution.getPackageName(), equalTo(PIPELINE_EXECUTION_DTO.getPackageName()));
    assertThat(execution.getClassName(), equalTo(PIPELINE_EXECUTION_DTO.getClassName()));
    assertThat(execution.getMethodName(), equalTo(PIPELINE_EXECUTION_DTO.getMethodName()));
    assertThat(execution.getParameters(), equalTo(PIPELINE_EXECUTION_DTO.getParameters()));
    assertThat(execution.getStartTime(), equalTo(PIPELINE_EXECUTION_DTO.getStartTime()));
    assertThat(execution.getEndTime(), equalTo(PIPELINE_EXECUTION_DTO.getEndTime()));
    assertThat(execution.getTestStartTime(), equalTo(PIPELINE_EXECUTION_DTO.getTestStartTime()));
    assertThat(execution.getTestEndTime(), equalTo(PIPELINE_EXECUTION_DTO.getTestEndTime()));
    assertThat(execution.getBeforeClassStartTime(), equalTo(PIPELINE_EXECUTION_DTO.getBeforeClassStartTime()));
    assertThat(execution.getBeforeClassEndTime(), equalTo(PIPELINE_EXECUTION_DTO.getBeforeClassEndTime()));
    assertThat(execution.getBeforeMethodStartTime(), equalTo(PIPELINE_EXECUTION_DTO.getBeforeMethodStartTime()));
    assertThat(execution.getBeforeMethodEndTime(), equalTo(PIPELINE_EXECUTION_DTO.getBeforeMethodEndTime()));
    assertThat(execution.getStatus().getName(), equalTo(PIPELINE_EXECUTION_DTO.getStatus()));
    assertThat(execution.getExecutor().getName(), equalTo(PIPELINE_EXECUTION_DTO.getExecutor()));
    assertThat(execution.getPipeline().getId(), equalTo(PIPELINE_EXECUTION_DTO.getPipelineId()));
    assertThat(execution.getMetadata().get(0).getName(), equalTo(PIPELINE_EXECUTION_DTO.getMetadata().get(0).getName()));
    assertThat(execution.getMetadata().get(0).getValue(), equalTo(PIPELINE_EXECUTION_DTO.getMetadata().get(0).getValue()));
    assertThat(execution.getMetadata().get(1).getName(), equalTo(PIPELINE_EXECUTION_DTO.getMetadata().get(1).getName()));
    assertThat(execution.getMetadata().get(1).getValue(), equalTo(PIPELINE_EXECUTION_DTO.getMetadata().get(1).getValue()));
  }

  @Test
  public void scenarioExecutionToScenarioExecutionDto() {
    final PipelineScenarioExecutionDto pipelineExecutionDto = mapper.scenarioExecutionToScenarioExecutionDto(PIPELINE_SCENARIO_EXECUTION);
    assertThat(pipelineExecutionDto.getFeature(), equalTo(PIPELINE_SCENARIO_EXECUTION.getFeature()));
    assertThat(pipelineExecutionDto.getScenario(), equalTo(PIPELINE_SCENARIO_EXECUTION.getScenario()));
    assertThat(pipelineExecutionDto.getParameters(), equalTo(PIPELINE_SCENARIO_EXECUTION.getParameters()));
    assertThat(pipelineExecutionDto.getStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getStartTime()));
    assertThat(pipelineExecutionDto.getEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getEndTime()));
    assertThat(pipelineExecutionDto.getBeforeScenarioStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getBeforeScenarioStartTime()));
    assertThat(pipelineExecutionDto.getBeforeScenarioEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getBeforeScenarioEndTime()));
    assertThat(pipelineExecutionDto.getStatus(), equalTo(PIPELINE_SCENARIO_EXECUTION.getStatus().getName()));
    assertThat(pipelineExecutionDto.getExecutor(), equalTo(PIPELINE_SCENARIO_EXECUTION.getExecutor().getName()));
    assertThat(pipelineExecutionDto.getPipelineId(), equalTo(PIPELINE_SCENARIO_EXECUTION.getPipeline().getId()));
    assertThat(pipelineExecutionDto.getMetadata().get(0).getName(), equalTo(PIPELINE_SCENARIO_EXECUTION.getMetadata().get(0).getName()));
    assertThat(pipelineExecutionDto.getMetadata().get(0).getValue(), equalTo(PIPELINE_SCENARIO_EXECUTION.getMetadata().get(0).getValue()));
    assertThat(pipelineExecutionDto.getMetadata().get(1).getName(), equalTo(PIPELINE_SCENARIO_EXECUTION.getMetadata().get(1).getName()));
    assertThat(pipelineExecutionDto.getMetadata().get(1).getValue(), equalTo(PIPELINE_SCENARIO_EXECUTION.getMetadata().get(1).getValue()));
  }


  @Rollback
  @Transactional
  @Test
  public void scenarioExecutionDtoToScenarioExecution() {
    final PipelineScenarioExecution execution = mapper.scenarioExecutionDtoToScenarioExecution(PIPELINE_SCENARIO_EXECUTION_DTO);
    assertThat(execution.getFeature(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getFeature()));
    assertThat(execution.getScenario(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getScenario()));
    assertThat(execution.getParameters(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getParameters()));
    assertThat(execution.getStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getStartTime()));
    assertThat(execution.getEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getEndTime()));
    assertThat(execution.getBeforeScenarioStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getBeforeScenarioStartTime()));
    assertThat(execution.getBeforeScenarioEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getBeforeScenarioEndTime()));
    assertThat(execution.getStatus().getName(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getStatus()));
    assertThat(execution.getExecutor().getName(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getExecutor()));
    assertThat(execution.getPipeline().getId(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getPipelineId()));
    assertThat(execution.getMetadata().get(0).getName(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getMetadata().get(0).getName()));
    assertThat(execution.getMetadata().get(0).getValue(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getMetadata().get(0).getValue()));
    assertThat(execution.getMetadata().get(1).getName(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getMetadata().get(1).getName()));
    assertThat(execution.getMetadata().get(1).getValue(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getMetadata().get(1).getValue()));
  }

  @Test
  public void pipelineToPipelineDto() {
    final PipelineDto pipelineDto = mapper.pipelineToPipelineDto(PIPELINE);
    assertThat(pipelineDto.getId(), equalTo(PIPELINE.getId()));
    assertThat(pipelineDto.getName(), equalTo(PIPELINE.getName()));
    assertThat(pipelineDto.getDescription(), equalTo(PIPELINE.getDescription()));
    assertThat(pipelineDto.getNumber(), equalTo(PIPELINE.getNumber()));
    assertThat(pipelineDto.getStartDate(), equalTo(PIPELINE.getStartDate()));
    assertThat(pipelineDto.getEndDate(), equalTo(PIPELINE.getEndDate()));
    assertThat(pipelineDto.getEnvironmentCode(), equalTo(PIPELINE.getEnvironment().getCode()));
    assertThat(pipelineDto.getMetadata().get(0).getName(), equalTo(PIPELINE.getMetadata().get(0).getName()));
    assertThat(pipelineDto.getMetadata().get(0).getValue(), equalTo(PIPELINE.getMetadata().get(0).getValue()));
    assertThat(pipelineDto.getMetadata().get(1).getName(), equalTo(PIPELINE.getMetadata().get(1).getName()));
    assertThat(pipelineDto.getMetadata().get(1).getValue(), equalTo(PIPELINE.getMetadata().get(1).getValue()));
  }

  @Test
  public void pipelineDtoToPipeline() {
    final Pipeline pipeline = mapper.pipelineDtoToPipeline(PIPELINE_DTO);
    assertThat(pipeline.getId(), equalTo(PIPELINE_DTO.getId()));
    assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
    assertThat(pipeline.getDescription(), equalTo(PIPELINE_DTO.getDescription()));
    assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
    assertThat(pipeline.getStartDate(), equalTo(PIPELINE_DTO.getStartDate()));
    assertThat(pipeline.getEndDate(), equalTo(PIPELINE_DTO.getEndDate()));
    assertThat(pipeline.getEnvironment().getCode(), equalTo(PIPELINE_DTO.getEnvironmentCode()));
    assertThat(pipeline.getMetadata().get(0).getName(), equalTo(PIPELINE_DTO.getMetadata().get(0).getName()));
    assertThat(pipeline.getMetadata().get(0).getValue(), equalTo(PIPELINE_DTO.getMetadata().get(0).getValue()));
    assertThat(pipeline.getMetadata().get(1).getName(), equalTo(PIPELINE_DTO.getMetadata().get(1).getName()));
    assertThat(pipeline.getMetadata().get(1).getValue(), equalTo(PIPELINE_DTO.getMetadata().get(1).getValue()));
  }

  @Test
  public void pipelineMetadataToMetadataDto() {
    PipelineMetadata pipelineMetadata = PIPELINE.getMetadata().get(0);
    final MetadataDto metadataDto = mapper.pipelineMetadataToMetadataDto(pipelineMetadata);
    assertThat(metadataDto.getId(), equalTo(pipelineMetadata.getId()));
    assertThat(metadataDto.getValue(), equalTo(pipelineMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineMetadata.getName()));
  }

  @Test
  public void pipelineExecutionMetadataToMetadataDto() {
    final MetadataDto metadataDto = PIPELINE_DTO.getMetadata().get(0);
    final PipelineMetadata pipelineExecutionMetadata = mapper.metadataDtoToPipelineMetadata(metadataDto);
    assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
  }

  @Test
  public void metadataDtoToPipelineMetadata() {
    final PipelineExecutionMetadata pipelineExecutionMetadata = PIPELINE_EXECUTION.getMetadata().get(0);

    final MetadataDto metadataDto = mapper.pipelineExecutionMetadataToMetadataDto(pipelineExecutionMetadata);
    assertThat(metadataDto.getId(), equalTo(pipelineExecutionMetadata.getId()));
    assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
  }

  @Test
  public void metadataDtoToPipelineExecutionMetadata() {
    final MetadataDto metadataDto = PIPELINE_EXECUTION_DTO.getMetadata().get(0);
    final PipelineExecutionMetadata pipelineExecutionMetadata = mapper.metadataDtoToPipelineExecutionMetadata(metadataDto);
    assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
  }

  @Test
  public void projectDtoToProject() {
    final Project project = mapper.projectDtoToProject(PROJECT_DTO);
    assertThat(project.getCode(), equalTo(PROJECT_DTO.getCode()));
    assertThat(project.getName(), equalTo(PROJECT_DTO.getName()));
  }

  @Test
  public void projectToProjectDto() {
    final ProjectDto projectDto = mapper.projectToProjectDto(PROJECT);
    assertThat(PROJECT.getId(), equalTo(projectDto.getId()));
    assertThat(PROJECT.getCode(), equalTo(projectDto.getCode()));
    assertThat(PROJECT.getName(), equalTo(projectDto.getName()));
  }

  @Test
  public void environmentDtoToEnvironment() {
    final Environment environment = mapper.environmentDtoToEnvironment(ENVIRONMENT_DTO);
    assertThat(environment.getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(environment.getName(), equalTo(ENVIRONMENT_DTO.getName()));
    assertThat(environment.getProject().getCode(), equalTo(ENVIRONMENT_DTO.getProjectCode()));
  }

  @Test
  public void environmentToEnvironmentDto() {
    final EnvironmentDto environmentDto = mapper.environmentToEnvironmentDto(ENVIRONMENT);
    assertThat(ENVIRONMENT.getId(), equalTo(environmentDto.getId()));
    assertThat(ENVIRONMENT.getCode(), equalTo(environmentDto.getCode()));
    assertThat(ENVIRONMENT.getName(), equalTo(environmentDto.getName()));
    assertThat(ENVIRONMENT.getProject().getCode(), equalTo(environmentDto.getProjectCode()));
  }

  @Test
  public void userDtoToUser() {
    final UserDto userDto = new UserDto();
    userDto.setName("UserName");

    // when
    final User user = mapper.userDtoToUser(userDto);

    // then
    assertThat(userDto.getName(), equalTo(user.getName()));
  }

  @Test
  public void userToUserDto() {
    final User user = new User();
    user.setId(1L);
    user.setName("UserName");

    // when
    final UserDto actualDto = mapper.userToUserDto(user);

    // then
    assertThat(actualDto.getName(), equalTo(user.getName()));
  }
}
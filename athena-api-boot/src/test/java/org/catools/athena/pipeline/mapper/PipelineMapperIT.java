package org.catools.athena.pipeline.mapper;

import org.catools.athena.AthenaBaseIT;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.service.EnvironmentService;
import org.catools.athena.core.common.service.ProjectService;
import org.catools.athena.core.common.service.UserService;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.builder.PipelineBuilder;
import org.catools.athena.pipeline.common.entity.*;
import org.catools.athena.pipeline.common.mapper.PipelineMapper;
import org.catools.athena.pipeline.common.service.PipelineExecutionService;
import org.catools.athena.pipeline.common.service.PipelineExecutionStatusService;
import org.catools.athena.pipeline.common.service.PipelineScenarioExecutionService;
import org.catools.athena.pipeline.common.service.PipelineService;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;


class PipelineMapperIT extends AthenaBaseIT {
  private static PipelineDto PIPELINE_DTO;
  private static Pipeline PIPELINE;
  private static PipelineExecutionDto PIPELINE_EXECUTION_DTO;
  private static PipelineExecution PIPELINE_EXECUTION;
  private static PipelineScenarioExecutionDto PIPELINE_SCENARIO_EXECUTION_DTO;
  private static PipelineScenarioExecution PIPELINE_SCENARIO_EXECUTION;

  @Autowired
  PipelineMapper pipelineMapper;

  @Autowired
  UserService userService;

  @Autowired
  ProjectService projectService;

  @Autowired
  EnvironmentService environmentService;

  @Autowired
  PipelineService pipelineService;

  @Autowired
  PipelineExecutionService pipelineExecutionService;

  @Autowired
  PipelineScenarioExecutionService pipelineScenarioExecutionService;

  @Autowired
  PipelineExecutionStatusService pipelineExecutionStatusService;

  @BeforeAll
  public void beforeAll() {
    UserDto userDto = CoreBuilder.buildUserDto();
    userDto.setId(userService.saveOrUpdate(userDto).getId());
    User user = CoreBuilder.buildUser(userDto);

    ProjectDto projectDto = CoreBuilder.buildProjectDto();
    projectDto.setId(projectService.saveOrUpdate(projectDto).getId());
    Project project = CoreBuilder.buildProject(projectDto);

    PipelineExecutionStatusDto statusDto = PipelineBuilder.buildPipelineExecutionStatusDto();
    statusDto.setId(pipelineExecutionStatusService.save(statusDto).getId());
    PipelineExecutionStatus STATUS = PipelineBuilder.buildPipelineExecutionStatus(statusDto);

    EnvironmentDto environmentDto = CoreBuilder.buildEnvironmentDto(projectDto);
    environmentDto.setId(environmentService.saveOrUpdate(environmentDto).getId());
    Environment environment = CoreBuilder.buildEnvironment(environmentDto, project);

    PIPELINE_DTO = PipelineBuilder.buildPipelineDto(environmentDto);
    PIPELINE_DTO.setId(pipelineService.saveOrUpdate(PIPELINE_DTO).getId());
    PIPELINE = PipelineBuilder.buildPipeline(PIPELINE_DTO, environment);

    PIPELINE_EXECUTION_DTO = PipelineBuilder.buildExecutionDto(PIPELINE_DTO, statusDto, userDto);
    PIPELINE_EXECUTION_DTO.setPipelineId(pipelineExecutionService.save(PIPELINE_EXECUTION_DTO).getId());
    PIPELINE_EXECUTION = PipelineBuilder.buildExecution(PIPELINE_EXECUTION_DTO, PIPELINE, STATUS, user);

    PIPELINE_SCENARIO_EXECUTION_DTO = PipelineBuilder.buildScenarioExecutionDto(PIPELINE_DTO, statusDto, userDto);
    PIPELINE_SCENARIO_EXECUTION_DTO.setId(pipelineScenarioExecutionService.save(PIPELINE_SCENARIO_EXECUTION_DTO).getId());
    PIPELINE_SCENARIO_EXECUTION = PipelineBuilder.buildScenarioExecution(PIPELINE_SCENARIO_EXECUTION_DTO, PIPELINE, STATUS, user);
  }

  @Test
  void executionToExecutionDto() {
    final PipelineExecutionDto pipelineExecutionDto = pipelineMapper.executionToExecutionDto(PIPELINE_EXECUTION);
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
    assertThat(pipelineExecutionDto.getExecutor(), equalTo(PIPELINE_EXECUTION.getExecutor().getUsername()));
    assertThat(pipelineExecutionDto.getPipelineId(), equalTo(PIPELINE_EXECUTION.getPipeline().getId()));

    verifyNameValuePairs(pipelineExecutionDto.getMetadata(), PIPELINE_EXECUTION.getMetadata());
  }


  @Test
  void executionToExecutionDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.executionToExecutionDto(null), nullValue());
  }

  @Rollback
  @Test
  void executionDtoToExecution() {
    final PipelineExecution execution = pipelineMapper.executionDtoToExecution(PIPELINE_EXECUTION_DTO);
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
    assertThat(execution.getExecutor().getUsername(), equalTo(PIPELINE_EXECUTION_DTO.getExecutor()));
    assertThat(execution.getPipeline().getId(), equalTo(PIPELINE_EXECUTION_DTO.getPipelineId()));

    verifyNameValuePairs(execution.getMetadata(), PIPELINE_EXECUTION_DTO.getMetadata());
  }

  @Test
  void executionDtoToExecution_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.executionDtoToExecution(null), nullValue());
  }

  @Test
  void scenarioExecutionToScenarioExecutionDto() {
    final PipelineScenarioExecutionDto pipelineExecutionDto = pipelineMapper.scenarioExecutionToScenarioExecutionDto(PIPELINE_SCENARIO_EXECUTION);
    assertThat(pipelineExecutionDto.getFeature(), equalTo(PIPELINE_SCENARIO_EXECUTION.getFeature()));
    assertThat(pipelineExecutionDto.getScenario(), equalTo(PIPELINE_SCENARIO_EXECUTION.getScenario()));
    assertThat(pipelineExecutionDto.getParameters(), equalTo(PIPELINE_SCENARIO_EXECUTION.getParameters()));
    assertThat(pipelineExecutionDto.getStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getStartTime()));
    assertThat(pipelineExecutionDto.getEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getEndTime()));
    assertThat(pipelineExecutionDto.getBeforeScenarioStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getBeforeScenarioStartTime()));
    assertThat(pipelineExecutionDto.getBeforeScenarioEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION.getBeforeScenarioEndTime()));
    assertThat(pipelineExecutionDto.getStatus(), equalTo(PIPELINE_SCENARIO_EXECUTION.getStatus().getName()));
    assertThat(pipelineExecutionDto.getExecutor(), equalTo(PIPELINE_SCENARIO_EXECUTION.getExecutor().getUsername()));
    assertThat(pipelineExecutionDto.getPipelineId(), equalTo(PIPELINE_SCENARIO_EXECUTION.getPipeline().getId()));

    verifyNameValuePairs(pipelineExecutionDto.getMetadata(), PIPELINE_SCENARIO_EXECUTION.getMetadata());
  }

  @Test
  void scenarioExecutionToScenarioExecutionDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.scenarioExecutionToScenarioExecutionDto(null), nullValue());
  }


  @Rollback
  @Test
  void scenarioExecutionDtoToScenarioExecution() {
    final PipelineScenarioExecution execution = pipelineMapper.scenarioExecutionDtoToScenarioExecution(PIPELINE_SCENARIO_EXECUTION_DTO);
    assertThat(execution.getFeature(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getFeature()));
    assertThat(execution.getScenario(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getScenario()));
    assertThat(execution.getParameters(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getParameters()));
    assertThat(execution.getStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getStartTime()));
    assertThat(execution.getEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getEndTime()));
    assertThat(execution.getBeforeScenarioStartTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getBeforeScenarioStartTime()));
    assertThat(execution.getBeforeScenarioEndTime(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getBeforeScenarioEndTime()));
    assertThat(execution.getStatus().getName(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getStatus()));
    assertThat(execution.getExecutor().getUsername(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getExecutor()));
    assertThat(execution.getPipeline().getId(), equalTo(PIPELINE_SCENARIO_EXECUTION_DTO.getPipelineId()));

    verifyNameValuePairs(execution.getMetadata(), PIPELINE_SCENARIO_EXECUTION_DTO.getMetadata());
  }


  @Rollback
  @Test
  void scenarioExecutionDtoToScenarioExecution_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.scenarioExecutionDtoToScenarioExecution(null), nullValue());
  }

  @Test
  void pipelineToPipelineDto() {
    final PipelineDto pipelineDto = pipelineMapper.pipelineToPipelineDto(PIPELINE);
    assertThat(pipelineDto.getId(), equalTo(PIPELINE.getId()));
    assertThat(pipelineDto.getName(), equalTo(PIPELINE.getName()));
    assertThat(pipelineDto.getDescription(), equalTo(PIPELINE.getDescription()));
    assertThat(pipelineDto.getNumber(), equalTo(PIPELINE.getNumber()));
    assertThat(pipelineDto.getStartDate(), equalTo(PIPELINE.getStartDate()));
    assertThat(pipelineDto.getEndDate(), equalTo(PIPELINE.getEndDate()));
    assertThat(pipelineDto.getEnvironment(), equalTo(PIPELINE.getEnvironment().getCode()));

    verifyNameValuePairs(pipelineDto.getMetadata(), PIPELINE.getMetadata());
  }

  @Test
  void pipelineToPipelineDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.pipelineToPipelineDto(null), nullValue());
  }

  @Test
  void pipelineDtoToPipeline() {
    final Pipeline pipeline = pipelineMapper.pipelineDtoToPipeline(PIPELINE_DTO);
    assertThat(pipeline.getId(), equalTo(PIPELINE_DTO.getId()));
    assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
    assertThat(pipeline.getDescription(), equalTo(PIPELINE_DTO.getDescription()));
    assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
    assertThat(pipeline.getStartDate(), equalTo(PIPELINE_DTO.getStartDate()));
    assertThat(pipeline.getEndDate(), equalTo(PIPELINE_DTO.getEndDate()));
    assertThat(pipeline.getEnvironment().getCode(), equalTo(PIPELINE_DTO.getEnvironment()));

    verifyNameValuePairs(pipeline.getMetadata(), PIPELINE_DTO.getMetadata());
  }

  @Test
  void pipelineDtoToPipeline_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.pipelineDtoToPipeline(null), nullValue());
  }

  @Test
  void pipelineMetadataToMetadataDto() {
    PipelineMetadata pipelineMetadata = PIPELINE.getMetadata().stream().findFirst().orElse(null);
    assertThat(pipelineMetadata, notNullValue());
    final MetadataDto metadataDto = pipelineMapper.pipelineMetadataToMetadataDto(pipelineMetadata);
    assertThat(metadataDto.getId(), equalTo(pipelineMetadata.getId()));
    assertThat(metadataDto.getValue(), equalTo(pipelineMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineMetadata.getName()));
  }

  @Test
  void pipelineMetadataToMetadataDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.pipelineMetadataToMetadataDto(null), nullValue());
  }

  @Test
  void pipelineExecutionMetadataToMetadataDto() {
    final MetadataDto metadataDto = PIPELINE_DTO.getMetadata().stream().findFirst().orElse(null);
    assertThat(metadataDto, notNullValue());
    final PipelineMetadata pipelineExecutionMetadata = pipelineMapper.metadataDtoToPipelineMetadata(metadataDto);
    assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
  }

  @Test
  void pipelineExecutionMetadataToMetadataDto_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.metadataDtoToPipelineMetadata(null), nullValue());
  }

  @Test
  void metadataDtoToPipelineMetadata() {
    final PipelineExecutionMetadata pipelineExecutionMetadata = PIPELINE_EXECUTION.getMetadata().stream().findFirst().orElse(null);
    assertThat(pipelineExecutionMetadata, notNullValue());

    final MetadataDto metadataDto = pipelineMapper.pipelineExecutionMetadataToMetadataDto(pipelineExecutionMetadata);
    assertThat(metadataDto.getId(), equalTo(pipelineExecutionMetadata.getId()));
    assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
  }

  @Test
  void metadataDtoToPipelineMetadata_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.pipelineExecutionMetadataToMetadataDto(null), nullValue());
  }

  @Test
  void metadataDtoToPipelineExecutionMetadata() {
    final MetadataDto metadataDto = PIPELINE_EXECUTION_DTO.getMetadata().stream().findFirst().orElse(null);
    assertThat(metadataDto, notNullValue());
    final PipelineExecutionMetadata pipelineExecutionMetadata = pipelineMapper.metadataDtoToPipelineExecutionMetadata(metadataDto);
    assertThat(metadataDto.getValue(), equalTo(pipelineExecutionMetadata.getValue()));
    assertThat(metadataDto.getName(), equalTo(pipelineExecutionMetadata.getName()));
  }

  @Test
  void metadataDtoToPipelineExecutionMetadata_shallReturnNullIfTheInputIsNull() {
    assertThat(pipelineMapper.metadataDtoToPipelineExecutionMetadata(null), nullValue());
  }
}
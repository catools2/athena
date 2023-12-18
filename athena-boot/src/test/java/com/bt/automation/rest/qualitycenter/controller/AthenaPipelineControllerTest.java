package com.bt.automation.rest.qualitycenter.controller;

import com.bt.automation.rest.qualitycenter.AthenaBaseTest;
import com.bt.automation.rest.qualitycenter.builder.PipelineBuilder;
import org.assertj.core.util.DateUtil;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.controller.AthenaPipelineController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AthenaPipelineControllerTest extends AthenaBaseTest {

  private static UserDto USER_DTO;
  private static ProjectDto PROJECT_DTO;
  private static EnvironmentDto ENVIRONMENT_DTO;
  private static PipelineExecutionStatusDto STATUS_DTO;
  private static PipelineDto PIPELINE_DTO;

  @Autowired
  private AthenaPipelineController athenaPipelineController;

  @BeforeAll
  public void beforeAll() {
    USER_DTO = PipelineBuilder.buildUserDto();
    PROJECT_DTO = PipelineBuilder.buildProjectDto();
    STATUS_DTO = PipelineBuilder.buildPipelineExecutionStatusDto();
    ENVIRONMENT_DTO = PipelineBuilder.buildEnvironmentDto(PROJECT_DTO);
    PIPELINE_DTO = PipelineBuilder.buildPipelineDto(ENVIRONMENT_DTO);
  }

  @Test
  @Order(1)
  void saveProject() {
    ResponseEntity<HashMap<String, Long>> response = athenaPipelineController.saveProject(PROJECT_DTO);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().get("projectId"), greaterThanOrEqualTo(1L));
  }

  @Test
  @Order(10)
  void getProject() {
    ResponseEntity<ProjectDto> response = athenaPipelineController.getProject(PROJECT_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(PROJECT_DTO.getCode()));
    assertThat(response.getBody().getName(), equalTo(PROJECT_DTO.getName()));
  }

  @Test
  @Order(10)
  void getProjects() {
    ResponseEntity<List<ProjectDto>> response = athenaPipelineController.getProjects();
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().get(0).getCode(), equalTo(PROJECT_DTO.getCode()));
    assertThat(response.getBody().get(0).getName(), equalTo(PROJECT_DTO.getName()));
  }

  @Test
  @Order(2)
  void saveEnvironment() {
    ResponseEntity<HashMap<String, Long>> response = athenaPipelineController.saveEnvironment(ENVIRONMENT_DTO);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().get("environmentId"), greaterThanOrEqualTo(1L));
  }

  @Test
  @Order(10)
  void getEnvironment() {
    ResponseEntity<EnvironmentDto> response = athenaPipelineController.getEnvironment(ENVIRONMENT_DTO.getCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(response.getBody().getName(), equalTo(ENVIRONMENT_DTO.getName()));
  }

  @Test
  @Order(10)
  void getEnvironments() {
    ResponseEntity<List<EnvironmentDto>> response = athenaPipelineController.getEnvironments();
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().get(0).getCode(), equalTo(ENVIRONMENT_DTO.getCode()));
    assertThat(response.getBody().get(0).getName(), equalTo(ENVIRONMENT_DTO.getName()));
  }

  @Test
  @Order(1)
  void saveUser() {
    ResponseEntity<HashMap<String, Long>> response = athenaPipelineController.saveUser(USER_DTO);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().get("userId"), greaterThanOrEqualTo(1L));
  }

  @Test
  @Order(10)
  void getUser() {
    ResponseEntity<UserDto> response = athenaPipelineController.getUser(USER_DTO.getName());
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), equalTo(USER_DTO.getName()));
  }

  @Test
  @Order(10)
  void getUsers() {
    ResponseEntity<List<UserDto>> response = athenaPipelineController.getUsers();
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().get(0).getName(), equalTo(USER_DTO.getName()));
  }

  @Test
  @Order(9)
  void savePipeline() {
    ResponseEntity<HashMap<String, Long>> response = athenaPipelineController.savePipeline(PIPELINE_DTO);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().get("pipelineId"), greaterThanOrEqualTo(1L));
  }

  @Rollback
  @Transactional
  @Test
  @Order(10)
  void updatePipelineEndDate() {
    Date enddate = new Date();
    Long pipelineId = athenaPipelineController.getPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), PIPELINE_DTO.getEnvironmentCode()).getBody().getId();
    ResponseEntity<PipelineDto> response = athenaPipelineController.updatePipelineEndDate(pipelineId, enddate);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), equalTo(PIPELINE_DTO.getName()));
    assertThat(response.getBody().getNumber(), equalTo(PIPELINE_DTO.getNumber()));
    assertThat(DateUtil.formatAsDatetime(response.getBody().getEndDate()), equalTo(DateUtil.formatAsDatetime(enddate)));
  }

  @Test
  @Order(11)
  void getPipeline() {
    ResponseEntity<PipelineDto> response = athenaPipelineController.getPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), PIPELINE_DTO.getEnvironmentCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    PipelineDto pipeline = response.getBody();
    assertThat(pipeline, notNullValue());
    assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
    assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
    assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
    assertThat(pipeline.getDescription(), equalTo(PIPELINE_DTO.getDescription()));
    assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
    assertThat(DateUtil.formatAsDatetime(pipeline.getStartDate()), equalTo(DateUtil.formatAsDatetime(PIPELINE_DTO.getStartDate())));
    assertThat(DateUtil.formatAsDatetime(pipeline.getEndDate()), equalTo(DateUtil.formatAsDatetime(PIPELINE_DTO.getEndDate())));
    assertThat(pipeline.getEnvironmentCode(), equalTo(PIPELINE_DTO.getEnvironmentCode()));
    assertThat(pipeline.getMetadata().get(0).getName(), equalTo(PIPELINE_DTO.getMetadata().get(0).getName()));
    assertThat(pipeline.getMetadata().get(0).getValue(), equalTo(PIPELINE_DTO.getMetadata().get(0).getValue()));
    assertThat(pipeline.getMetadata().get(1).getName(), equalTo(PIPELINE_DTO.getMetadata().get(1).getName()));
    assertThat(pipeline.getMetadata().get(1).getValue(), equalTo(PIPELINE_DTO.getMetadata().get(1).getValue()));

    // we need this for next 2 testcases
    PIPELINE_DTO = response.getBody();
  }

  @Test
  @Order(12)
  void saveExecution() {
    PipelineExecutionDto executionDto = PipelineBuilder.buildExecutionDto(PIPELINE_DTO, STATUS_DTO, USER_DTO);
    ResponseEntity<HashMap<String, Long>> response = athenaPipelineController.saveExecution(executionDto);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().get("executionId"), greaterThanOrEqualTo(1L));
  }

  @Test
  @Order(12)
  void saveScenarioExecution() {
    PipelineScenarioExecutionDto executionDto = PipelineBuilder.buildScenarioExecutionDto(PIPELINE_DTO, STATUS_DTO, USER_DTO);
    ResponseEntity<HashMap<String, Long>> response = athenaPipelineController.saveScenarioExecution(executionDto);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().get("executionId"), greaterThanOrEqualTo(1L));
  }
}
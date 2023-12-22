package org.catools.athena.rest.pipeline.controller;

import org.assertj.core.util.DateUtil;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.catools.athena.rest.core.controller.AthenaCoreControllerTest;
import org.catools.athena.rest.pipeline.builder.AthenaPipelineBuilder;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AthenaPipelineControllerTest extends AthenaCoreControllerTest {

  private static PipelineDto PIPELINE_DTO;
  private static PipelineExecutionStatusDto STATUS_DTO;

  @BeforeAll
  public void beforeAll() {
    ProjectDto project = athenaProjectController.saveProject(AthenaCoreBuilder.buildProjectDto()).getBody();
    assertThat(project, notNullValue());
    EnvironmentDto environmentDto = athenaEnvironmentController.saveEnvironment(AthenaCoreBuilder.buildEnvironmentDto(project)).getBody();
    assertThat(environmentDto, notNullValue());
    PIPELINE_DTO = AthenaPipelineBuilder.buildPipelineDto(environmentDto);
    STATUS_DTO = AthenaPipelineBuilder.buildPipelineExecutionStatusDto();
  }

  @Test
  @Order(9)
  void savePipeline() {
    ResponseEntity<PipelineDto> response = athenaPipelineController.savePipeline(PIPELINE_DTO);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), greaterThanOrEqualTo(1L));
  }

  @Rollback
  @Transactional
  @Test
  @Order(10)
  void updatePipelineEndDate() {
    Date enddate = new Date();
    ResponseEntity<PipelineDto> pipeline = athenaPipelineController.getPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), PIPELINE_DTO.getEnvironmentCode());
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.getBody();
    assertThat(body, notNullValue());
    Long pipelineId = body.getId();
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
  void saveExecutionStatus() {
    PipelineExecutionStatusDto pipelineStatus = athenaPipelineController.saveExecutionStatus(STATUS_DTO).getBody();
    assertThat(pipelineStatus, notNullValue());
    assertThat(pipelineStatus.getName(), equalTo(STATUS_DTO.getName()));
  }

  @Test
  @Order(12)
  void getExecutionStatus() {
    PipelineExecutionStatusDto pipelineStatus = athenaPipelineController.getExecutionStatus(STATUS_DTO.getName()).getBody();
    assertThat(pipelineStatus, notNullValue());
    assertThat(pipelineStatus.getName(), equalTo(STATUS_DTO.getName()));
  }

  @Test
  @Order(12)
  void getExecutionStatuses() {
    List<PipelineExecutionStatusDto> pipelineStatuses = athenaPipelineController.getExecutionStatuses().getBody();
    assertThat(pipelineStatuses, notNullValue());
    PipelineExecutionStatusDto pipelineStatus = pipelineStatuses.stream().filter(s -> s.getName().equals(STATUS_DTO.getName())).findFirst().orElse(null);
    assertThat(pipelineStatus, notNullValue());
    assertThat(pipelineStatus.getName(), equalTo(STATUS_DTO.getName()));
  }

  @Test
  @Order(12)
  void saveExecution() {
    PipelineExecutionStatusDto pipelineStatus = athenaPipelineController.saveExecutionStatus(AthenaPipelineBuilder.buildPipelineExecutionStatusDto()).getBody();
    assertThat(pipelineStatus, notNullValue());
    UserDto user = athenaUserController.saveUser(AthenaCoreBuilder.buildUserDto()).getBody();
    assertThat(user, notNullValue());
    PipelineExecutionDto executionDto = AthenaPipelineBuilder.buildExecutionDto(PIPELINE_DTO, pipelineStatus, user);
    ResponseEntity<PipelineExecutionDto> response = athenaPipelineController.saveExecution(executionDto);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), greaterThanOrEqualTo(1L));
  }

  @Test
  @Order(12)
  void saveScenarioExecution() {
    PipelineExecutionStatusDto pipelineStatus = athenaPipelineController.saveExecutionStatus(AthenaPipelineBuilder.buildPipelineExecutionStatusDto()).getBody();
    assertThat(pipelineStatus, notNullValue());
    UserDto user = athenaUserController.saveUser(AthenaCoreBuilder.buildUserDto()).getBody();
    assertThat(user, notNullValue());
    PipelineScenarioExecutionDto executionDto = AthenaPipelineBuilder.buildScenarioExecutionDto(PIPELINE_DTO, pipelineStatus, user);
    ResponseEntity<PipelineScenarioExecutionDto> response = athenaPipelineController.saveScenarioExecution(executionDto);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getId(), greaterThanOrEqualTo(1L));
  }
}
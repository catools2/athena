package org.catools.athena.pipeline.controller;

import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.builder.CoreBuilder;
import org.catools.athena.core.controller.CoreControllerIT;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.pipeline.builder.PipelineBuilder;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.net.URI;
import java.time.Instant;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PipelineControllerIT extends CoreControllerIT {

  private static EnvironmentDto ENVIRONMENT_DTO;
  private static PipelineDto PIPELINE_DTO;
  private static PipelineExecutionStatusDto STATUS_DTO;

  @BeforeAll
  public void beforeAll() {
    ProjectDto projectDto = CoreBuilder.buildProjectDto();
    URI location = projectController.saveOrUpdate(projectDto).getHeaders().getLocation();
    assertThat(location, notNullValue());
    ENVIRONMENT_DTO = CoreBuilder.buildEnvironmentDto(projectDto);
    location = environmentController.save(ENVIRONMENT_DTO).getHeaders().getLocation();
    assertThat(location, notNullValue());
    PIPELINE_DTO = PipelineBuilder.buildPipelineDto(ENVIRONMENT_DTO);
    STATUS_DTO = PipelineBuilder.buildPipelineExecutionStatusDto();
  }

  @Test
  @Order(1)
  void savePipeline() {
    ResponseEntity<Void> responseEntity = pipelineController.saveOrUpdate(PIPELINE_DTO);
    verifyPipeline(responseEntity, PIPELINE_DTO);
  }

  @Test
  @Order(9)
  void updatePipeline() {
    PipelineDto pipelineDto = PipelineBuilder.buildPipelineDto(ENVIRONMENT_DTO)
        .setName(PIPELINE_DTO.getName())
        .setNumber(PIPELINE_DTO.getNumber());

    pipelineDto.getMetadata().add(PIPELINE_DTO.getMetadata().stream().findAny().get());

    ResponseEntity<Void> response = pipelineController.saveOrUpdate(pipelineDto);
    verifyPipeline(response, pipelineDto);
  }

  @Rollback
  @Test
  @Order(2)
  void updatePipelineEndDate() {
    ResponseEntity<PipelineDto> pipeline = pipelineController.getLastPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), PIPELINE_DTO.getEnvironmentCode());
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.getBody();
    assertThat(body, notNullValue());
    Long pipelineId = body.getId();

    Instant enddate = Instant.now();
    ResponseEntity<PipelineDto> response = pipelineController.updateEndDate(pipelineId, enddate);
    assertThat(response.getStatusCode().value(), equalTo(200));
    assertThat(response.getBody(), notNullValue());
    assertThat(response.getBody().getName(), equalTo(PIPELINE_DTO.getName()));
    assertThat(response.getBody().getNumber(), equalTo(PIPELINE_DTO.getNumber()));
    assertThat(response.getBody().getEndDate(), equalTo(enddate));
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByName() {
    ResponseEntity<PipelineDto> pipeline = pipelineController.getLastPipeline(PIPELINE_DTO.getName(), null, null);
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.getBody();
    assertThat(body, notNullValue());
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByNameAndNumber() {
    ResponseEntity<PipelineDto> pipeline = pipelineController.getLastPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), null);
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.getBody();
    assertThat(body, notNullValue());
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByNameAndEnvironmentCode() {
    ResponseEntity<PipelineDto> pipeline = pipelineController.getLastPipeline(PIPELINE_DTO.getName(), null, PIPELINE_DTO.getEnvironmentCode());
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.getBody();
    assertThat(body, notNullValue());
  }

  @Test
  @Order(2)
  void getPipeline() {
    ResponseEntity<PipelineDto> response = pipelineController.getLastPipeline(PIPELINE_DTO.getName(), PIPELINE_DTO.getNumber(), PIPELINE_DTO.getEnvironmentCode());
    assertThat(response.getStatusCode().value(), equalTo(200));
    PipelineDto pipeline = response.getBody();
    assertThat(pipeline, notNullValue());
    assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
    assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
    assertThat(pipeline.getName(), equalTo(PIPELINE_DTO.getName()));
    assertThat(pipeline.getDescription(), equalTo(PIPELINE_DTO.getDescription()));
    assertThat(pipeline.getNumber(), equalTo(PIPELINE_DTO.getNumber()));
    assertThat(pipeline.getStartDate(), equalTo(pipeline.getStartDate()));
    assertThat(pipeline.getEndDate(), equalTo(pipeline.getEndDate()));

    assertThat(pipeline.getEnvironmentCode(), equalTo(PIPELINE_DTO.getEnvironmentCode()));

    verifyNameValuePairs(pipeline.getMetadata(), PIPELINE_DTO.getMetadata());

    // we need this for next 2 testcases
    PIPELINE_DTO = response.getBody();
  }

  @Test
  @Order(10)
  void saveExecutionStatus() {
    ResponseEntity<Void> responseEntity = pipelineExecutionStatusController.save(STATUS_DTO);
    assertThat(responseEntity.getStatusCode().value(), equalTo(201));

    verifyExecutionStatus(responseEntity, STATUS_DTO);
  }

  @Test
  @Order(12)
  void updtaeExecutionStatus_IfRecordWithTheSameNameAlreadyExists() {
    PipelineExecutionStatusDto statusDto = PipelineBuilder.buildPipelineExecutionStatusDto().setName(STATUS_DTO.getName());
    ResponseEntity<Void> responseEntity = pipelineExecutionStatusController.save(statusDto);
    assertThat(responseEntity.getStatusCode().value(), equalTo(208));

    verifyExecutionStatus(responseEntity, statusDto);
  }

  @Test
  @Order(12)
  void getExecutionStatus() {
    PipelineExecutionStatusDto pipelineStatus = pipelineExecutionStatusController.getByName(STATUS_DTO.getName()).getBody();
    assertThat(pipelineStatus, notNullValue());
    assertThat(pipelineStatus.getName(), equalTo(STATUS_DTO.getName()));
  }

  @Test
  @Order(12)
  void getExecutionStatuses() {
    Set<PipelineExecutionStatusDto> pipelineStatuses = pipelineExecutionStatusController.getAll().getBody();
    assertThat(pipelineStatuses, notNullValue());
    PipelineExecutionStatusDto pipelineStatus = pipelineStatuses.stream().filter(s -> s.getName().equals(STATUS_DTO.getName())).findFirst().orElse(null);
    assertThat(pipelineStatus, notNullValue());
    assertThat(pipelineStatus.getName(), equalTo(STATUS_DTO.getName()));
  }

  @Test
  @Order(12)
  void saveExecution() {
    PipelineExecutionStatusDto pipelineStatus = PipelineBuilder.buildPipelineExecutionStatusDto();
    pipelineExecutionStatusController.save(pipelineStatus);
    assertThat(pipelineStatus, notNullValue());

    UserDto user = CoreBuilder.buildUserDto();
    userController.saveOrUpdate(user);
    assertThat(user, notNullValue());

    PipelineExecutionDto executionDto = PipelineBuilder.buildExecutionDto(PIPELINE_DTO, pipelineStatus, user);
    ResponseEntity<Void> responseEntity = pipelineExecutionController.save(executionDto);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    String[] split = location.getPath().split("/");
    ResponseEntity<PipelineExecutionDto> executionById = pipelineExecutionController.getById(Long.valueOf(split[split.length - 1]));
    assertThat(executionById.getBody(), notNullValue());
  }

  @Test
  @Order(12)
  void saveScenarioExecution() {
    PipelineExecutionStatusDto pipelineStatus = PipelineBuilder.buildPipelineExecutionStatusDto();
    pipelineExecutionStatusController.save(pipelineStatus);
    assertThat(pipelineStatus, notNullValue());

    UserDto user = CoreBuilder.buildUserDto();
    userController.saveOrUpdate(user);
    assertThat(user, notNullValue());

    PipelineScenarioExecutionDto executionDto = PipelineBuilder.buildScenarioExecutionDto(PIPELINE_DTO, pipelineStatus, user);
    ResponseEntity<Void> responseEntity = pipelineScenarioExecutionController.save(executionDto);
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getStatusCode().value(), Matchers.equalTo(201));
    assertThat(responseEntity.getBody(), nullValue());

    String[] split = location.getPath().split("/");
    ResponseEntity<PipelineScenarioExecutionDto> scenarioExecutionById = pipelineScenarioExecutionController.getById(Long.valueOf(split[split.length - 1]));
    assertThat(scenarioExecutionById.getBody(), notNullValue());
  }

  private void verifyPipeline(ResponseEntity<Void> response, PipelineDto pipelineDto) {
    URI location = response.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(response.getStatusCode().value(), Matchers.equalTo(201));
    assertThat(response.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    PipelineDto savedPipeline = pipelineController.getById(id).getBody();
    assertThat(savedPipeline, notNullValue());
    assertThat(savedPipeline.getNumber(), Matchers.equalTo(pipelineDto.getNumber()));
    assertThat(savedPipeline.getName(), Matchers.equalTo(pipelineDto.getName()));
    assertThat(savedPipeline.getDescription(), Matchers.equalTo(pipelineDto.getDescription()));
  }

  private void verifyExecutionStatus(ResponseEntity<Void> responseEntity, PipelineExecutionStatusDto statusDto) {
    URI location = responseEntity.getHeaders().getLocation();
    assertThat(location, notNullValue());
    assertThat(responseEntity.getBody(), nullValue());

    Long id = ResponseEntityUtils.getId(location);
    assertThat(id, notNullValue());
    PipelineExecutionStatusDto savedStatus = pipelineExecutionStatusController.getById(id).getBody();
    assertThat(savedStatus, notNullValue());
    assertThat(savedStatus.getName(), Matchers.equalTo(statusDto.getName()));
  }
}
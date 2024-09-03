package org.catools.athena.pipeline.controller;

import feign.TypedResponse;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.feign.FeignUtils;
import org.catools.athena.pipeline.builder.PipelineBuilder;
import org.catools.athena.pipeline.feign.PipelineExecutionFeignClient;
import org.catools.athena.pipeline.feign.PipelineExecutionStatusFeignClient;
import org.catools.athena.pipeline.feign.PipelineFeignClient;
import org.catools.athena.pipeline.feign.PipelineScenarioExecutionFeignClient;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PipelineControllerIT extends AthenaSpringBootIT {

  protected PipelineFeignClient pipelineFeignClient;
  protected PipelineExecutionFeignClient pipelineExecutionFeignClient;
  protected PipelineScenarioExecutionFeignClient pipelineScenarioExecutionFeignClient;
  protected PipelineExecutionStatusFeignClient pipelineExecutionStatusFeignClient;

  private static PipelineDto pipelineDto;

  private static PipelineExecutionStatusDto pipelineExecutionStatusDto;

  private static final EnvironmentDto environmentDto = StagedTestData.getEnvironment(1);
  private static final VersionDto versionDto = StagedTestData.getVersion(1);
  private static final UserDto userDto = StagedTestData.getUser(1);


  @BeforeAll
  public void beforeAll() {
    if (pipelineFeignClient == null) {
      pipelineFeignClient = testFeignBuilder.getClient(PipelineFeignClient.class);
    }
    if (pipelineExecutionFeignClient == null) {
      pipelineExecutionFeignClient = testFeignBuilder.getClient(PipelineExecutionFeignClient.class);
    }
    if (pipelineScenarioExecutionFeignClient == null) {
      pipelineScenarioExecutionFeignClient = testFeignBuilder.getClient(PipelineScenarioExecutionFeignClient.class);
    }
    if (pipelineExecutionStatusFeignClient == null) {
      pipelineExecutionStatusFeignClient = testFeignBuilder.getClient(PipelineExecutionStatusFeignClient.class);
    }
    pipelineDto = PipelineBuilder.buildPipelineDto(versionDto, environmentDto);
    pipelineExecutionStatusDto = PipelineBuilder.buildPipelineExecutionStatusDto();
  }

  @Test
  @Order(1)
  void savePipeline() {
    TypedResponse<Void> responseEntity = pipelineFeignClient.saveOrUpdate(pipelineDto);
    verifyPipeline(responseEntity, pipelineDto);
  }

  @Test
  @Order(9)
  void updatePipeline() {
    PipelineDto pipe = PipelineBuilder.buildPipelineDto(versionDto, environmentDto)
        .setName(PipelineControllerIT.pipelineDto.getName())
        .setNumber(PipelineControllerIT.pipelineDto.getNumber());

    pipe.getMetadata().add(PipelineControllerIT.pipelineDto.getMetadata().stream().findAny().orElseThrow());

    TypedResponse<Void> response = pipelineFeignClient.saveOrUpdate(pipe);
    verifyPipeline(response, pipe);
  }

  @Rollback
  @Test
  @Order(2)
  void updatePipelineEndDate() {
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), pipelineDto.getNumber(), pipelineDto.getVersion(), pipelineDto.getEnvironment());
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.body();
    assertThat(body, notNullValue());
    Long pipelineId = body.getId();

    Instant enddate = Instant.now();
    TypedResponse<PipelineDto> response = pipelineFeignClient.updateEndDate(pipelineId, enddate);
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getName(), equalTo(pipelineDto.getName()));
    assertThat(response.body().getNumber(), equalTo(pipelineDto.getNumber()));
    assertThat(response.body().getEndDate().truncatedTo(ChronoUnit.MILLIS), equalTo(enddate.truncatedTo(ChronoUnit.MILLIS)));
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByName() {
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), null, null, null);
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.body();
    assertThat(body, notNullValue());
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByNameAndNumber() {
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), pipelineDto.getNumber(), null, null);
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.body();
    assertThat(body, notNullValue());
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByNameAndNumberAndVersion() {
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), pipelineDto.getNumber(), pipelineDto.getVersion(), null);
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.body();
    assertThat(body, notNullValue());
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByNameAndVersionAndEnvironmentCode() {
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), null, pipelineDto.getVersion(), pipelineDto.getEnvironment());
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.body();
    assertThat(body, notNullValue());
  }

  @Test
  @Order(2)
  void getPipeline() {
    TypedResponse<PipelineDto> response = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), pipelineDto.getNumber(), pipelineDto.getVersion(), pipelineDto.getEnvironment());
    assertThat(response.status(), equalTo(200));
    PipelineDto pipeline = response.body();
    assertThat(pipeline, notNullValue());
    assertThat(pipeline.getName(), equalTo(pipelineDto.getName()));
    assertThat(pipeline.getNumber(), equalTo(pipelineDto.getNumber()));
    assertThat(pipeline.getName(), equalTo(pipelineDto.getName()));
    assertThat(pipeline.getDescription(), equalTo(pipelineDto.getDescription()));
    assertThat(pipeline.getNumber(), equalTo(pipelineDto.getNumber()));
    assertThat(pipeline.getStartDate(), equalTo(pipeline.getStartDate()));
    assertThat(pipeline.getEndDate(), equalTo(pipeline.getEndDate()));

    assertThat(pipeline.getEnvironment(), equalTo(pipelineDto.getEnvironment()));

    verifyNameValuePairs(pipeline.getMetadata(), pipelineDto.getMetadata());

    // we need this for next 2 testcases
    pipelineDto = response.body();
  }

  @Test
  @Order(10)
  void saveExecutionStatus() {
    TypedResponse<Void> responseEntity = pipelineExecutionStatusFeignClient.save(pipelineExecutionStatusDto);
    assertThat(responseEntity.status(), equalTo(201));

    verifyExecutionStatus(responseEntity, pipelineExecutionStatusDto);
  }

  @Test
  @Order(12)
  void updtaeExecutionStatus_IfRecordWithTheSameNameAlreadyExists() {
    PipelineExecutionStatusDto statusDto = PipelineBuilder.buildPipelineExecutionStatusDto().setName(pipelineExecutionStatusDto.getName());
    TypedResponse<Void> responseEntity = pipelineExecutionStatusFeignClient.save(statusDto);
    assertThat(responseEntity.status(), equalTo(208));

    verifyExecutionStatus(responseEntity, statusDto);
  }

  @Test
  @Order(12)
  void getExecutionStatus() {
    PipelineExecutionStatusDto pipelineStatus = pipelineExecutionStatusFeignClient.getByName(pipelineExecutionStatusDto.getName()).body();
    assertThat(pipelineStatus, notNullValue());
    assertThat(pipelineStatus.getName(), equalTo(pipelineExecutionStatusDto.getName()));
  }

  @Test
  @Order(12)
  void getExecutionStatuses() {
    Set<PipelineExecutionStatusDto> pipelineStatuses = pipelineExecutionStatusFeignClient.getAll().body();
    assertThat(pipelineStatuses, notNullValue());
    PipelineExecutionStatusDto pipelineStatus = pipelineStatuses.stream().filter(s -> s.getName().equals(pipelineExecutionStatusDto.getName())).findFirst().orElse(null);
    assertThat(pipelineStatus, notNullValue());
    assertThat(pipelineStatus.getName(), equalTo(pipelineExecutionStatusDto.getName()));
  }

  @Test
  @Order(12)
  void saveExecution() {
    PipelineExecutionStatusDto pipelineStatus = PipelineBuilder.buildPipelineExecutionStatusDto();
    pipelineExecutionStatusFeignClient.save(pipelineStatus);
    assertThat(pipelineStatus, notNullValue());

    PipelineExecutionDto executionDto = PipelineBuilder.buildExecutionDto(pipelineDto, pipelineStatus, userDto);
    TypedResponse<Void> responseEntity = pipelineExecutionFeignClient.save(executionDto);
    Long entityId = FeignUtils.getIdFromLocationHeader(responseEntity);
    assertThat(entityId, notNullValue());
    assertThat(responseEntity.status(), Matchers.equalTo(201));
    assertThat(responseEntity.body(), nullValue());

    TypedResponse<PipelineExecutionDto> executionById = pipelineExecutionFeignClient.getById(entityId);
    assertThat(executionById.body(), notNullValue());
  }

  @Test
  @Order(12)
  void saveScenarioExecution() {
    PipelineExecutionStatusDto pipelineStatus = PipelineBuilder.buildPipelineExecutionStatusDto();
    pipelineExecutionStatusFeignClient.save(pipelineStatus);
    assertThat(pipelineStatus, notNullValue());

    PipelineScenarioExecutionDto executionDto = PipelineBuilder.buildScenarioExecutionDto(pipelineDto, pipelineStatus, userDto);
    TypedResponse<Void> response = pipelineScenarioExecutionFeignClient.save(executionDto);
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
    assertThat(response.status(), Matchers.equalTo(201));
    assertThat(response.body(), nullValue());

    TypedResponse<PipelineScenarioExecutionDto> scenarioExecutionById = pipelineScenarioExecutionFeignClient.getById(entityId);
    assertThat(scenarioExecutionById.body(), notNullValue());
  }

  private void verifyPipeline(TypedResponse<Void> response, PipelineDto pipelineDto) {
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
    assertThat(response.status(), Matchers.equalTo(201));
    assertThat(response.body(), nullValue());

    PipelineDto savedPipeline = pipelineFeignClient.getById(entityId).body();
    assertThat(savedPipeline, notNullValue());
    assertThat(savedPipeline.getNumber(), Matchers.equalTo(pipelineDto.getNumber()));
    assertThat(savedPipeline.getName(), Matchers.equalTo(pipelineDto.getName()));
    assertThat(savedPipeline.getDescription(), Matchers.equalTo(pipelineDto.getDescription()));
  }

  private void verifyExecutionStatus(TypedResponse<Void> responseEntity, PipelineExecutionStatusDto statusDto) {
    Long entityId = FeignUtils.getIdFromLocationHeader(responseEntity);
    assertThat(entityId, notNullValue());
    assertThat(responseEntity.body(), nullValue());

    PipelineExecutionStatusDto savedStatus = pipelineExecutionStatusFeignClient.getById(entityId).body();
    assertThat(savedStatus, notNullValue());
    assertThat(savedStatus.getName(), Matchers.equalTo(statusDto.getName()));
  }
}
package org.catools.athena.pipeline.controller;

import feign.TypedResponse;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.model.pipeline.PipelineExecutionDto;
import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;
import org.catools.athena.model.pipeline.PipelineInventoryDto;
import org.catools.athena.model.pipeline.PipelineScenarioExecutionDto;
import org.catools.athena.model.pipeline.PipelineSummaryDto;
import org.catools.athena.model.pipeline.PipelineTrendPointDto;
import org.catools.athena.pipeline.builder.PipelineBuilder;
import org.catools.athena.pipeline.feign.PageResponse;
import org.catools.athena.pipeline.feign.PipelineExecutionFeignClient;
import org.catools.athena.pipeline.feign.PipelineExecutionStatusFeignClient;
import org.catools.athena.pipeline.feign.PipelineFeignClient;
import org.catools.athena.pipeline.feign.PipelineScenarioExecutionFeignClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.annotation.Rollback;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
  private static PipelineDto completedDashboardPipeline;
  private static PipelineDto runningDashboardPipeline;

  private static PipelineExecutionStatusDto pipelineExecutionStatusDto;
  private static final String DASHBOARD_NAME_FILTER = "pipeline-dashboard-" + System.currentTimeMillis();

  private static final EnvironmentDto environmentDto = StagedTestData.getEnvironment(1);
  private static final VersionDto versionDto = StagedTestData.getVersion(1);
  private static final UserDto userDto = StagedTestData.getUser(1);


  @BeforeAll
  void beforeAll() {
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
    pipe.setId(pipelineDto.getId());

    TypedResponse<Void> response = pipelineFeignClient.saveOrUpdate(pipe);
    verifyPipeline(response, pipe);
  }

  @Rollback
  @Test
  @Order(2)
  void updatePipelineEndDate() {
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), pipelineDto.getNumber(), pipelineDto.getProject(), pipelineDto.getVersion(), pipelineDto.getEnvironment());
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
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), null, pipelineDto.getProject(), null, null);
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.body();
    assertThat(body, notNullValue());
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByNameAndNumber() {
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), pipelineDto.getNumber(), pipelineDto.getProject(), null, null);
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.body();
    assertThat(body, notNullValue());
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByNameAndNumberAndVersion() {
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), pipelineDto.getNumber(), pipelineDto.getProject(), pipelineDto.getVersion(), null);
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.body();
    assertThat(body, notNullValue());
  }

  @Rollback
  @Test
  @Order(2)
  void getPipeline_shallReturnValueIfSearchOnlyByNameAndVersionAndEnvironmentCode() {
    TypedResponse<PipelineDto> pipeline = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), null, pipelineDto.getProject(), pipelineDto.getVersion(), pipelineDto.getEnvironment());
    assertThat(pipeline, notNullValue());
    PipelineDto body = pipeline.body();
    assertThat(body, notNullValue());
  }

  @Test
  @Order(2)
  void getPipeline() {
    TypedResponse<PipelineDto> response = pipelineFeignClient.getLastPipeline(pipelineDto.getName(), pipelineDto.getNumber(), pipelineDto.getProject(), pipelineDto.getVersion(), pipelineDto.getEnvironment());
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

    pipelineDto.setId(response.body().getId());
  }

  @Test
  @Order(3)
  void getPipelineDashboardSummary() {
    ensureDashboardPipelines();

    TypedResponse<PipelineSummaryDto> response = pipelineFeignClient.getSummary(
        versionDto.getProject(),
        versionDto.getCode(),
        environmentDto.getCode(),
        DASHBOARD_NAME_FILTER,
        null,
        null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalCount(), equalTo(2L));
    assertThat(response.body().getUniqueNameCount(), equalTo(2L));
    assertThat(response.body().getCompletedCount(), equalTo(1L));
    assertThat(response.body().getInProgressCount(), equalTo(1L));
    assertThat(response.body().getAverageDuration(), equalTo(2_700_000.0));
    assertThat(response.body().getLatestStartTime(), equalTo(runningDashboardPipeline.getStartDate()));
  }

  @Test
  @Order(3)
  void getPipelineDashboardSummaryWithinWindow() {
    ensureDashboardPipelines();

    TypedResponse<PipelineSummaryDto> response = pipelineFeignClient.getSummary(
        versionDto.getProject(),
        versionDto.getCode(),
        environmentDto.getCode(),
        DASHBOARD_NAME_FILTER,
        null,
      null,
        1);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalCount(), equalTo(1L));
    assertThat(response.body().getUniqueNameCount(), equalTo(1L));
    assertThat(response.body().getCompletedCount(), equalTo(0L));
    assertThat(response.body().getInProgressCount(), equalTo(1L));
    assertThat(response.body().getAverageDuration(), nullValue());
    assertThat(response.body().getLatestStartTime(), equalTo(runningDashboardPipeline.getStartDate()));
  }

  @Test
  @Order(4)
  void getPipelineDashboardTrend() {
    ensureDashboardPipelines();

    TypedResponse<List<PipelineTrendPointDto>> response = pipelineFeignClient.getTrend(
        versionDto.getProject(),
        versionDto.getCode(),
        environmentDto.getCode(),
        DASHBOARD_NAME_FILTER,
        null,
      null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), equalTo(2));
    assertThat(response.body().get(0).getPipelineCount(), equalTo(1L));
    assertThat(response.body().get(0).getCompletedCount(), equalTo(1L));
    assertThat(response.body().get(0).getAverageDuration(), equalTo(2_700_000.0));
    assertThat(response.body().get(1).getPipelineCount(), equalTo(1L));
    assertThat(response.body().get(1).getCompletedCount(), equalTo(0L));
    assertThat(response.body().get(1).getAverageDuration(), nullValue());
  }

  @Test
  @Order(4)
  void getPipelineDashboardTrendWithinWindow() {
    ensureDashboardPipelines();

    TypedResponse<List<PipelineTrendPointDto>> response = pipelineFeignClient.getTrend(
        versionDto.getProject(),
        versionDto.getCode(),
        environmentDto.getCode(),
        DASHBOARD_NAME_FILTER,
        null,
      null,
        1);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), equalTo(1));
    assertThat(response.body().getFirst().getBucketStart(), equalTo(runningDashboardPipeline.getStartDate().truncatedTo(ChronoUnit.DAYS)));
    assertThat(response.body().getFirst().getCompletedCount(), equalTo(0L));
  }

  @Test
  @Order(5)
  void getPipelineDashboardInventory() {
    ensureDashboardPipelines();

    TypedResponse<PageResponse<PipelineInventoryDto>> response = pipelineFeignClient.getAll(
        0,
        10,
        "startDate",
        "DESC",
        versionDto.getProject(),
        versionDto.getCode(),
        environmentDto.getCode(),
        DASHBOARD_NAME_FILTER,
        null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalElements(), equalTo(2L));
    assertThat(response.body().getContent().size(), equalTo(2));
    assertThat(response.body().getContent().get(0).getName(), equalTo(runningDashboardPipeline.getName()));
    assertThat(response.body().getContent().get(0).getState(), equalTo("RUNNING"));
    assertThat(response.body().getContent().get(0).getDuration(), nullValue());
    assertThat(response.body().getContent().get(1).getName(), equalTo(completedDashboardPipeline.getName()));
    assertThat(response.body().getContent().get(1).getState(), equalTo("COMPLETED"));
    assertThat(response.body().getContent().get(1).getDuration(), equalTo(2_700_000L));
  }

  @Test
  @Order(5)
  void getPipelineDashboardInventoryByState() {
    ensureDashboardPipelines();

    TypedResponse<PageResponse<PipelineInventoryDto>> response = pipelineFeignClient.getAll(
        0,
        10,
        "startDate",
        "DESC",
        versionDto.getProject(),
        versionDto.getCode(),
        environmentDto.getCode(),
        DASHBOARD_NAME_FILTER,
        null,
        "RUNNING");

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalElements(), equalTo(1L));
    assertThat(response.body().getContent().size(), equalTo(1));
    assertThat(response.body().getContent().getFirst().getState(), equalTo("RUNNING"));
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

  private void ensureDashboardPipelines() {
    if (completedDashboardPipeline != null && runningDashboardPipeline != null) {
      return;
    }

    completedDashboardPipeline = saveDashboardPipeline(
        DASHBOARD_NAME_FILTER + "-completed",
        "agg-101",
        Instant.parse("2026-05-10T10:00:00.000Z"),
        Instant.parse("2026-05-10T10:45:00.000Z"));

    runningDashboardPipeline = saveDashboardPipeline(
        DASHBOARD_NAME_FILTER + "-running",
        "agg-102",
        Instant.parse("2026-05-11T11:00:00.000Z"),
        null);
  }

  private PipelineDto saveDashboardPipeline(String name, String number, Instant startDate, Instant endDate) {
    PipelineDto pipeline = PipelineBuilder.buildPipelineDto(versionDto, environmentDto)
        .setName(name)
        .setNumber(number)
        .setDescription("Aggregation dashboard fixture " + number)
        .setStartDate(startDate)
        .setEndDate(endDate);

    TypedResponse<Void> response = pipelineFeignClient.saveOrUpdate(pipeline);
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
    assertThat(response.status(), Matchers.equalTo(201));
    pipeline.setId(entityId);
    return pipeline;
  }
}
package org.catools.athena.metric.controler;

import feign.TypedResponse;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.metric.builder.MetricBuilder;
import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.metric.common.mapper.MetricMapper;
import org.catools.athena.metric.feign.MetricFeignClient;
import org.catools.athena.metric.feign.PageResponse;
import org.catools.athena.model.core.EnvironmentDto;
import org.catools.athena.model.core.ProjectDto;
import org.catools.athena.model.metrics.MetricDto;
import org.catools.athena.model.metrics.MetricInventoryDto;
import org.catools.athena.model.metrics.MetricSummaryDto;
import org.catools.athena.model.metrics.MetricTrendPointDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MetricControllerIT extends AthenaSpringBootIT {
  static ProjectDto project = StagedTestData.getProject(1);
  static EnvironmentDto environment = StagedTestData.getEnvironment(1);

  protected MetricFeignClient metricFeignClient;
  protected MetricDto sampleMetric;

  @Autowired
  MetricMapper metricMapper;

  @BeforeAll
  void beforeAll() {
    if (metricFeignClient == null) {
      metricFeignClient = testFeignBuilder.getClient(MetricFeignClient.class);
    }
  }

  @Test
  @Order(1)
  void saveShallSaveMetricsIfDoNotExist() {
    for (Metric metric : MetricBuilder.buildMetric(project, environment)) {
      MetricDto metricDto = metricMapper.metricToMetricDto(metric);

      saveAndVerifyMetric(metricDto);
    }
  }

  @Test
  @Order(2)
  void saveShallSaveMetricIfActionAlreadyExist() {
    Action lastAction = null;
    for (Metric metric : MetricBuilder.buildMetric(project, environment)) {
      if (lastAction != null) metric.setAction(lastAction);
      lastAction = metric.getAction();

      MetricDto metricDto = metricMapper.metricToMetricDto(metric);
      saveAndVerifyMetric(metricDto);
    }
  }

  @Test
  @Order(3)
  void shallReturnInventoryPageWhenValidFiltersProvided() {
    TypedResponse<PageResponse<MetricInventoryDto>> response = metricFeignClient.getAll(
        0,
        10,
        "actionTime",
        "DESC",
        project.getCode(),
        environment.getCode(),
        sampleMetric == null || sampleMetric.getAction() == null ? null : sampleMetric.getAction().getName(),
        null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getContent(), notNullValue());
    assertThat(response.body().getContent().isEmpty(), equalTo(false));

    MetricInventoryDto metric = response.body().getContent().getFirst();
    assertThat(metric.getProject(), equalTo(project.getCode()));
    assertThat(metric.getEnvironment(), equalTo(environment.getCode()));
    assertThat(metric.getActionName(), notNullValue());
    assertThat(metric.getActionType(), notNullValue());
    assertThat(metric.getDuration(), notNullValue());
    assertThat(metric.getActionTime(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnSummaryWhenValidFiltersProvided() {
    TypedResponse<MetricSummaryDto> response = metricFeignClient.getSummary(
        project.getCode(),
        environment.getCode(),
        sampleMetric == null || sampleMetric.getAction() == null ? null : sampleMetric.getAction().getName(),
        null,
      null,
      null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalCount(), greaterThan(0L));
    assertThat(response.body().getUniqueActionCount(), greaterThan(0L));
    assertThat(response.body().getAverageDuration(), greaterThan(0.0));
    assertThat(response.body().getSlowestDuration(), greaterThan(0L));
    assertThat(response.body().getLatestActionTime(), notNullValue());
  }

  @Test
  @Order(4)
  void shallReturnTrendPointsWhenValidFiltersProvided() {
    TypedResponse<List<MetricTrendPointDto>> response = metricFeignClient.getTrend(
        project.getCode(),
        environment.getCode(),
        sampleMetric == null || sampleMetric.getAction() == null ? null : sampleMetric.getAction().getName(),
        null,
        null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().isEmpty(), equalTo(false));
    assertThat(response.body().getFirst().getMetricCount(), greaterThanOrEqualTo(1L));
    assertThat(response.body().getFirst().getAverageDuration(), greaterThan(0.0));
    assertThat(response.body().getFirst().getBucketStart(), notNullValue());
  }

  private void saveAndVerifyMetric(MetricDto metricDto) {
    TypedResponse<Void> response = metricFeignClient.save(metricDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());

    if (sampleMetric == null) {
      sampleMetric = metricFeignClient.getById(entityId).body();
    }
  }
}
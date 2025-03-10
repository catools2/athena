package org.catools.athena.metric.controler;

import feign.TypedResponse;
import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.metric.builder.MetricBuilder;
import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.metric.common.mapper.MetricMapper;
import org.catools.athena.metric.feign.MetricFeignClient;
import org.catools.athena.metrics.model.MetricDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MetricControllerIT extends AthenaSpringBootIT {
  static ProjectDto project = StagedTestData.getProject(1);
  static EnvironmentDto environment = StagedTestData.getEnvironment(1);

  protected MetricFeignClient metricFeignClient;

  @Autowired
  MetricMapper metricMapper;

  @BeforeAll
  public void beforeAll() {
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

  private void saveAndVerifyMetric(MetricDto metricDto) {
    TypedResponse<Void> response = metricFeignClient.save(metricDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }
}
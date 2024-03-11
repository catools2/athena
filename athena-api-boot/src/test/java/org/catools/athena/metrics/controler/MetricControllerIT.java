package org.catools.athena.metrics.controler;

import org.catools.athena.core.controller.CoreControllerIT;
import org.catools.athena.metric.common.entity.Action;
import org.catools.athena.metric.common.entity.Metric;
import org.catools.athena.metric.common.mapper.MetricMapper;
import org.catools.athena.metric.controller.MetricController;
import org.catools.athena.metrics.builder.MetricBuilder;
import org.catools.athena.metrics.model.MetricDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MetricControllerIT extends CoreControllerIT {

  @Autowired
  protected MetricController metricController;

  @Autowired
  private MetricMapper metricMapper;

  @Test
  @Order(1)
  void saveShallSaveMetricsIfDoNotExist() {
    for (Metric metric : MetricBuilder.buildMetric(PROJECT, ENVIRONMENT)) {
      MetricDto metricDto = metricMapper.metricToMetricDto(metric);

      saveAndVerifyMetric(metricDto);
    }
  }

  @Test
  @Order(2)
  void saveShallSaveMetricIfActionAlreadyExist() {
    Action lastAction = null;
    for (Metric metric : MetricBuilder.buildMetric(PROJECT, ENVIRONMENT)) {
      if (lastAction != null) metric.setAction(lastAction);
      lastAction = metric.getAction();

      MetricDto metricDto = metricMapper.metricToMetricDto(metric);
      saveAndVerifyMetric(metricDto);
    }
  }

  private void saveAndVerifyMetric(MetricDto metricDto) {
    ResponseEntity<Void> response = metricController.save(metricDto);
    assertThat(response.getStatusCode().value(), equalTo(201));
    assertThat(response.getHeaders().getLocation(), notNullValue());
  }
}
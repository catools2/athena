package org.catools.athena.metric.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.metric.common.service.MetricService;
import org.catools.athena.metrics.model.MetricDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Rest Metric Collector API")
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MetricController {

  private static final String METRIC = "/metric";

  private final MetricService metricService;

  @PostMapping(METRIC)
  @Operation(
      summary = "Save metric",
      responses = {
          @ApiResponse(responseCode = "201", description = "Metric is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The metric to save")
      @Validated @RequestBody final MetricDto metricDto
  ) {
    final MetricDto savedMetricDto = metricService.save(metricDto);
    return ResponseEntityUtils.created(METRIC, savedMetricDto.getId());
  }

  @GetMapping(METRIC + "/{id}")
  @Operation(
      summary = "Retrieve metric by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<MetricDto> getActionById(
      @Parameter(name = "id", description = "The id of the metric to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(metricService.getById(id));
  }
}

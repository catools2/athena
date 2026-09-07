package org.catools.athena.metric.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.metric.common.service.MetricService;
import org.catools.athena.model.metrics.MetricDto;
import org.catools.athena.model.metrics.MetricInventoryDto;
import org.catools.athena.model.metrics.MetricSummaryDto;
import org.catools.athena.model.metrics.MetricTrendPointDto;
import org.catools.athena.model.page.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Tag(name = "Athena Rest Metric Collector API")
@RestController
@RequestMapping(value = {"", "/metric"}, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MetricController {

  private static final String METRIC = "/metric";

  private final MetricService metricService;

    @GetMapping("/summary")
  @Operation(
      summary = "Retrieve metric workspace summary values",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<MetricSummaryDto> getSummary(
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "environment", description = "Filter by environment code")
      @RequestParam(required = false) final String environment,
      @Parameter(name = "actionName", description = "Filter by action name")
      @RequestParam(required = false) final String actionName,
      @Parameter(name = "actionType", description = "Filter by action type")
      @RequestParam(required = false) final String actionType,
      @Parameter(name = "actionTarget", description = "Filter by action target")
      @RequestParam(required = false) final String actionTarget,
      @Parameter(name = "windowDays", description = "Limit summary data to the latest N days relative to the newest matching action time")
      @RequestParam(required = false) final Integer windowDays
  ) {
    log.info("getSummary(project={}, environment={}, actionName={}, actionType={}, actionTarget={}, windowDays={})", project, environment, actionName, actionType, actionTarget, windowDays);
    return ResponseEntity.ok(metricService.getSummary(project, environment, actionName, actionType, actionTarget, windowDays));
  }

    @GetMapping("/trend")
  @Operation(
      summary = "Retrieve metric trend points grouped by day",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<MetricTrendPointDto>> getTrend(
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "environment", description = "Filter by environment code")
      @RequestParam(required = false) final String environment,
      @Parameter(name = "actionName", description = "Filter by action name")
      @RequestParam(required = false) final String actionName,
      @Parameter(name = "actionType", description = "Filter by action type")
      @RequestParam(required = false) final String actionType,
      @Parameter(name = "actionTarget", description = "Filter by action target")
      @RequestParam(required = false) final String actionTarget,
      @Parameter(name = "windowDays", description = "Limit trend buckets to the latest N days relative to the newest bucket")
      @RequestParam(required = false) final Integer windowDays
  ) {
    log.info("getTrend(project={}, environment={}, actionName={}, actionType={}, actionTarget={}, windowDays={})", project, environment, actionName, actionType, actionTarget, windowDays);
    return ResponseEntity.ok(metricService.getTrend(project, environment, actionName, actionType, actionTarget, windowDays));
  }

    @GetMapping("/all")
  @Operation(
      summary = "Retrieve metrics with pagination and optional filters",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PageDto<MetricInventoryDto>> getAll(
      @Parameter(name = "page", description = "Page number (0-based)")
      @RequestParam(defaultValue = "0") final int page,
      @Parameter(name = "size", description = "Page size")
      @RequestParam(defaultValue = "10") final int size,
      @Parameter(name = "sort", description = "Sort field")
      @RequestParam(defaultValue = "actionTime") final String sort,
      @Parameter(name = "direction", description = "Sort direction (ASC or DESC)")
      @RequestParam(defaultValue = "DESC") final String direction,
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "environment", description = "Filter by environment code")
      @RequestParam(required = false) final String environment,
      @Parameter(name = "actionName", description = "Filter by action name")
      @RequestParam(required = false) final String actionName,
      @Parameter(name = "actionType", description = "Filter by action type")
      @RequestParam(required = false) final String actionType,
      @Parameter(name = "actionTarget", description = "Filter by action target")
      @RequestParam(required = false) final String actionTarget
  ) {
    log.info("getAll(page={}, size={}, sort={}, direction={}, project={}, environment={}, actionName={}, actionType={}, actionTarget={})", page, size, sort, direction, project, environment, actionName, actionType, actionTarget);
    final Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    final Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
    return ResponseEntity.ok(toPageDto(metricService.getAll(pageable, project, environment, actionName, actionType, actionTarget)));
  }

    @PostMapping
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
    log.info("save({})", metricDto);
    final MetricDto savedMetricDto = metricService.save(metricDto);
    return ResponseEntityUtils.created(METRIC, savedMetricDto.getId());
  }

    @GetMapping("/{id}")
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
    log.info("getActionById(id={})", id);
    return ResponseEntityUtils.okOrNoContent(metricService.getById(id));
  }

  private static <T> PageDto<T> toPageDto(Page<T> page) {
    return PageDto.<T>builder()
        .content(page.getContent())
        .number(page.getNumber())
        .size(page.getSize())
        .numberOfElements(page.getNumberOfElements())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .first(page.isFirst())
        .last(page.isLast())
        .empty(page.isEmpty())
        .build();
  }
}

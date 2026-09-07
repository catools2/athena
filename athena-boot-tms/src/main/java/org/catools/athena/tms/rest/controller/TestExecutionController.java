package org.catools.athena.tms.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.model.tms.TestExecutionDto;
import org.catools.athena.model.tms.TestExecutionInventoryDto;
import org.catools.athena.model.tms.TestExecutionSummaryDto;
import org.catools.athena.model.tms.TestExecutionTrendPointDto;
import org.catools.athena.model.page.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.catools.athena.tms.common.service.TestExecutionService;
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
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Test Executions API")
@RestController
@RequestMapping(value = {"", "/tms"}, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TestExecutionController {
  private static final String TMS_TEST_EXECUTION = "/execution";
  private static final String TMS_TEST_EXECUTIONS = "/executions";

  private final TestExecutionService testExecutionService;

  @GetMapping("/summary")
  @Operation(
      summary = "Retrieve aggregated execution quality summary",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<TestExecutionSummaryDto> getSummary(
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "version", description = "Filter by version code")
      @RequestParam(required = false) final String version,
      @Parameter(name = "cycleCode", description = "Filter by cycle code")
      @RequestParam(required = false) final String cycleCode,
      @Parameter(name = "itemCode", description = "Filter by item code")
      @RequestParam(required = false) final String itemCode,
      @Parameter(name = "status", description = "Filter by execution status")
    @RequestParam(required = false) final String status,
    @Parameter(name = "progress", description = "Filter by execution progress")
    @RequestParam(required = false) final String progress,
    @Parameter(name = "windowDays", description = "Limit summary data to the latest N days relative to the newest matching activity time")
    @RequestParam(required = false) final Integer windowDays
  ) {
      return ResponseEntity.ok(testExecutionService.getSummary(project, version, cycleCode, itemCode, status, progress, windowDays));
  }

  @GetMapping("/trend")
  @Operation(
      summary = "Retrieve daily execution quality trend buckets",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<TestExecutionTrendPointDto>> getTrend(
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "version", description = "Filter by version code")
      @RequestParam(required = false) final String version,
      @Parameter(name = "cycleCode", description = "Filter by cycle code")
      @RequestParam(required = false) final String cycleCode,
      @Parameter(name = "itemCode", description = "Filter by item code")
      @RequestParam(required = false) final String itemCode,
      @Parameter(name = "status", description = "Filter by execution status")
    @RequestParam(required = false) final String status,
    @Parameter(name = "progress", description = "Filter by execution progress")
    @RequestParam(required = false) final String progress,
    @Parameter(name = "windowDays", description = "Limit trend buckets to the latest N days relative to the newest bucket")
    @RequestParam(required = false) final Integer windowDays
  ) {
      return ResponseEntity.ok(testExecutionService.getTrend(project, version, cycleCode, itemCode, status, progress, windowDays));
  }

  @GetMapping("/all")
  @Operation(
      summary = "Retrieve paged execution inventory for the quality workspace",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
    public ResponseEntity<PageDto<TestExecutionInventoryDto>> getAll(
      @Parameter(name = "page", description = "Page number (0-based)")
      @RequestParam(defaultValue = "0") final int page,
      @Parameter(name = "size", description = "Page size")
      @RequestParam(defaultValue = "10") final int size,
      @Parameter(name = "sort", description = "Sort field")
      @RequestParam(defaultValue = "executedOn") final String sort,
      @Parameter(name = "direction", description = "Sort direction (ASC or DESC)")
      @RequestParam(defaultValue = "DESC") final String direction,
      @Parameter(name = "project", description = "Filter by project code")
      @RequestParam(required = false) final String project,
      @Parameter(name = "version", description = "Filter by version code")
      @RequestParam(required = false) final String version,
      @Parameter(name = "cycleCode", description = "Filter by cycle code")
      @RequestParam(required = false) final String cycleCode,
      @Parameter(name = "itemCode", description = "Filter by item code")
      @RequestParam(required = false) final String itemCode,
      @Parameter(name = "status", description = "Filter by execution status")
            @RequestParam(required = false) final String status,
            @Parameter(name = "progress", description = "Filter by execution progress")
            @RequestParam(required = false) final String progress
  ) {
    final Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    final Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
                return ResponseEntity.ok(toPageDto(testExecutionService.getAll(pageable, project, version, cycleCode, itemCode, status, progress)));
  }

  @GetMapping(TMS_TEST_EXECUTION + "/{id}")
  @Operation(
      summary = "Retrieve test execution by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<TestExecutionDto> getById(
      @Parameter(name = "id", description = "The id of test execution to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(testExecutionService.getById(id));
  }

  @GetMapping(TMS_TEST_EXECUTIONS)
  @Operation(
      summary = "Retrieve test executions by cycle and/or item code, if none provided returns all executions",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Set<TestExecutionDto>> getAll(
      @Parameter(name = "itemCode", description = "The code of the item to retrieve test execution for")
      @RequestParam(required = false) final String itemCode,
      @Parameter(name = "cycleCode", description = "The code of the cycle to retrieve test execution for")
      @RequestParam(required = false) final String cycleCode
  ) {
    return ResponseEntityUtils.okOrNoContent(testExecutionService.getAll(itemCode, cycleCode));
  }

  @PostMapping(TMS_TEST_EXECUTION)
  @Operation(
      summary = "Save test execution or update the current one if any with the same item code, creation date and cycle code exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @Parameter(name = "cycleCode", description = "The id of the cycle that execution belongs to")
      @RequestParam final String cycleCode,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The test execution to save")
      @Validated @RequestBody final TestExecutionDto testExecution
  ) {
    final TestExecutionDto savedRecord = testExecutionService.save(cycleCode, testExecution);
    return ResponseEntityUtils.created(TMS_TEST_EXECUTION, savedRecord.getId());
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

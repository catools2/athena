package org.catools.athena.rest.tms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.tms.service.TestExecutionService;
import org.catools.athena.tms.model.TestExecutionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.tms.config.TmsPathDefinitions.TMS_TEST_EXECUTIONS_PATH;
import static org.catools.athena.rest.tms.config.TmsPathDefinitions.TMS_TEST_EXECUTION_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Test Executions API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TestExecutionController {

  private final TestExecutionService testExecutionService;

  @PostMapping(TMS_TEST_EXECUTION_PATH)
  @Operation(
      summary = "Save test execution",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "208", description = "Api spec is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The test execution to save")
      @Validated @RequestBody final TestExecutionDto testExecution
  ) {
    final Optional<TestExecutionDto> recordFromDB = testExecutionService.getByCreatedOnItemCodeAndCycleCode(
        testExecution.getCreatedOn(),
        testExecution.getItem(),
        testExecution.getCycle());

    if (recordFromDB.isPresent()) {
      return ResponseEntityUtils.alreadyReported(TMS_TEST_EXECUTION_PATH, recordFromDB.get().getId());
    }

    final TestExecutionDto savedRecord = testExecutionService.save(testExecution);
    return ResponseEntityUtils.created(TMS_TEST_EXECUTION_PATH, savedRecord.getId());
  }

  @GetMapping(TMS_TEST_EXECUTION_PATH + "/{id}")
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

  @GetMapping(TMS_TEST_EXECUTIONS_PATH)
  @Operation(
      summary = "Retrieve test executions by cycle and/or item code, if none provided returns all executions",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Set<TestExecutionDto>> getAll(
      @Parameter(name = "itemCode", description = "The code of the item to retrieve test execution for")
      @PathVariable(required = false) final String itemCode,
      @Parameter(name = "cycleCode", description = "The code of the cycle to retrieve test execution for")
      @PathVariable(required = false) final String cycleCode
  ) {
    return ResponseEntityUtils.okOrNoContent(testExecutionService.getAll(itemCode, cycleCode));
  }
}

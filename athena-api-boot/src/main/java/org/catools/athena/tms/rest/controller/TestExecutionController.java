package org.catools.athena.tms.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.tms.common.service.TestExecutionService;
import org.catools.athena.tms.model.TestExecutionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.catools.athena.tms.common.config.TmsPathDefinitions.TMS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Test Executions API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TestExecutionController {
  private static final String TMS_TEST_EXECUTION = TMS + "/execution";
  private static final String TMS_TEST_EXECUTIONS = TMS + "/executions";

  private final TestExecutionService testExecutionService;

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
}

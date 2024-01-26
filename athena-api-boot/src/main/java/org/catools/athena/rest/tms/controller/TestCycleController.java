package org.catools.athena.rest.tms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.tms.service.TestCycleService;
import org.catools.athena.tms.model.TestCycleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.tms.config.TmsPathDefinitions.TMS_TEST_CYCLE;
import static org.catools.athena.rest.tms.config.TmsPathDefinitions.TMS_TEST_CYCLES;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Test Cycle API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TestCycleController {

  private final TestCycleService testCycleService;

  @PostMapping(TMS_TEST_CYCLE)
  @Operation(
      summary = "Save test cycle",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "208", description = "Api spec is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The test cycle to save")
      @Validated @RequestBody final TestCycleDto testCycle
  ) {
    final Optional<TestCycleDto> entityFromDB = testCycleService.getByCode(testCycle.getCode());

    if (entityFromDB.isPresent()) {
      return ResponseEntityUtils.alreadyReported(TMS_TEST_CYCLE, entityFromDB.get().getId());
    }

    final TestCycleDto savedRecord = testCycleService.save(testCycle);
    return ResponseEntityUtils.created(TMS_TEST_CYCLE, savedRecord.getId());
  }

  @GetMapping(TMS_TEST_CYCLE + "/{id}")
  @Operation(
      summary = "Retrieve test cycle by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<TestCycleDto> getById(
      @Parameter(name = "id", description = "The id of test cycle to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(testCycleService.getById(id));
  }

  @GetMapping(TMS_TEST_CYCLE + "/{code}")
  @Operation(
      summary = "Retrieve test cycle by code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<TestCycleDto> getByCode(
      @Parameter(name = "code", description = "The code of test cycle to retrieve")
      @PathVariable final String code
  ) {
    return ResponseEntityUtils.okOrNoContent(testCycleService.getByCode(code));
  }

  @GetMapping(TMS_TEST_CYCLES)
  @Operation(
      summary = "Retrieve test cycles by version code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Set<TestCycleDto>> getByVersionCode(
      @Parameter(name = "versionCode", description = "The code of the version to retrieve test cycles for")
      @PathVariable final String versionCode
  ) {
    return ResponseEntityUtils.okOrNoContent(testCycleService.getByVersionCode(versionCode));
  }
}

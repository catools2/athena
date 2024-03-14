package org.catools.athena.tms.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.tms.common.service.TestCycleService;
import org.catools.athena.tms.model.TestCycleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.catools.athena.tms.common.config.TmsPathDefinitions.TMS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Test Cycle API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TestCycleController {
  private static final String TMS_TEST_CYCLE = TMS + "/cycle";
  private static final String TMS_TEST_CYCLES = TMS + "/cycles";

  private final TestCycleService testCycleService;

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

  @GetMapping(TMS_TEST_CYCLE + "/{code}/hash")
  @Operation(
      summary = "Retrieve test cycle hash by code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Integer> getUniqueHashByCode(
      @Parameter(name = "code", description = "The code of test cycle to retrieve hash for")
      @PathVariable final String code
  ) {
    return ResponseEntityUtils.okOrNoContent(testCycleService.getUniqueHashByCode(code));
  }

  @GetMapping(TMS_TEST_CYCLE)
  @Operation(
      summary = "Retrieve test cycle by code or name",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<TestCycleDto> search(
      @Parameter(name = "keyword", description = "The code or name of test cycle to retrieve")
      @RequestParam final String keyword
  ) {
    return ResponseEntityUtils.okOrNoContent(testCycleService.search(keyword));
  }

  @GetMapping(TMS_TEST_CYCLES)
  @Operation(
      summary = "Retrieve test cycles by version code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<TestCycleDto> findLastByPattern(
      @Parameter(name = "name", description = "The name of the test cycle to retrieve test cycles for")
      @RequestParam final String name,
      @Parameter(name = "versionCode", description = "The version code of the test cycle to retrieve test cycles for")
      @RequestParam final String versionCode
  ) {
    return ResponseEntityUtils.okOrNoContent(testCycleService.findLastByPattern(name, versionCode));
  }

  @PostMapping(TMS_TEST_CYCLE)
  @Operation(
      summary = "Save test cycle or update the current one if any with the same code exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The test cycle to save or update")
      @Validated @RequestBody final TestCycleDto testCycle
  ) {
    final TestCycleDto savedRecord = testCycleService.saveOrUpdate(testCycle);
    return ResponseEntityUtils.created(TMS_TEST_CYCLE, savedRecord.getId());
  }

}

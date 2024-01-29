package org.catools.athena.rest.pipeline.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.pipeline.service.PipelineService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.pipeline.config.PipelinePathDefinitions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Pipeline Metric Collector API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PipelineController {

  private final PipelineService pipelineService;

  @GetMapping(EXECUTION_STATUSES)
  @Operation(
      summary = "Retrieve execution statuses",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<PipelineExecutionStatusDto>> getExecutionStatuses() {
    return ResponseEntityUtils.okOrNoContent(pipelineService.getExecutionStatuses());
  }

  @GetMapping(EXECUTION_STATUS)
  @Operation(
      summary = "Retrieve execution status by name",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PipelineExecutionStatusDto> getExecutionStatusByName(
      @Parameter(name = "statusName", description = "The name of the status to retrieve")
      @RequestParam final String statusName
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineService.getExecutionStatusByName(statusName));
  }

  @GetMapping(EXECUTION_STATUS + "/{id}")
  @Operation(
      summary = "Retrieve execution status by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PipelineExecutionStatusDto> getExecutionStatusById(
      @Parameter(name = "id", description = "The id of the status to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineService.getExecutionStatusById(id));
  }

  @PostMapping(EXECUTION_STATUS)
  @Operation(
      summary = "Save execution status",
      responses = {
          @ApiResponse(responseCode = "201", description = "Execution status is created"),
          @ApiResponse(responseCode = "208", description = "Execution status is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The execution status to save")
      @Validated @RequestBody final PipelineExecutionStatusDto pipelineExecutionStatusDto
  ) {
    final Optional<PipelineExecutionStatusDto> executionStatusFromDb = pipelineService.getExecutionStatusByName(pipelineExecutionStatusDto.getName());

    if (executionStatusFromDb.isPresent()) {
      return ResponseEntityUtils.alreadyReported(EXECUTION_STATUS, executionStatusFromDb.get().getId());
    }

    final PipelineExecutionStatusDto saveExecutionStatus = pipelineService.saveExecutionStatus(pipelineExecutionStatusDto);
    return ResponseEntityUtils.created(EXECUTION_STATUS, saveExecutionStatus.getId());
  }

  @GetMapping(PIPELINE)
  @Operation(
      summary = "Retrieve pipeline by name, number and environment code, if pipeline number not provided, the latest pipeline will be considered",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> getPipeline(
      @Parameter(name = "name", description = "The pipeline name")
      @RequestParam final String name,
      @Parameter(name = "number", description = "The pipeline number")
      @RequestParam(required = false) final String number,
      @Parameter(name = "envCode", description = "The environment code")
      @RequestParam(required = false) final String envCode
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineService.getPipeline(name, number, envCode));
  }

  @GetMapping(PIPELINE + "/{id}")
  @Operation(
      summary = "Retrieve pipeline by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> getPipelineById(
      @Parameter(name = "id", description = "The id of the status to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineService.getPipelineById(id));
  }

  @PatchMapping(PIPELINE)
  @Operation(
      summary = "Update pipeline end cate, by pipeline id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> updatePipelineEndDate(
      @Parameter(name = "pipelineId", description = "The pipeline Id to update")
      @RequestParam final Long pipelineId,
      @Parameter(name = "endInstant", description = "The end date in ISO format")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      @RequestParam(required = false) final Instant date
  ) {
    Instant enddate = date == null ? Instant.now() : date;
    PipelineDto updatedPipeline = pipelineService.updatePipelineEndDate(pipelineId, enddate);
    return ResponseEntityUtils.ok(updatedPipeline);
  }

  @PostMapping(PIPELINE)
  @Operation(
      summary = "Save pipeline",
      responses = {
          @ApiResponse(responseCode = "201", description = "pipeline is created"),
          @ApiResponse(responseCode = "208", description = "pipeline is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pipeline to save")
      @Validated @RequestBody final PipelineDto pipelineDto
  ) {
    // We shouldn't have multiple environment with similar code
    final Optional<PipelineDto> pipeline = pipelineService.getPipeline(pipelineDto.getName(), pipelineDto.getNumber(), pipelineDto.getEnvironmentCode());
    if (pipeline.isPresent()) {
      return ResponseEntityUtils.alreadyReported(PIPELINE, pipeline.get().getId());
    }

    final PipelineDto savedPipelineDto = pipelineService.savePipeline(pipelineDto);
    return ResponseEntityUtils.created(PIPELINE, savedPipelineDto.getId());
  }

  @PostMapping(EXECUTION)
  @Operation(
      summary = "Save execution",
      responses = {
          @ApiResponse(responseCode = "201", description = "Pipeline execution is created"),
          @ApiResponse(responseCode = "208", description = "Pipeline execution is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pipeline execution to save")
      @Validated @RequestBody final PipelineExecutionDto execution
  ) {
    final PipelineExecutionDto savedExecutionDto = pipelineService.saveExecution(execution);
    return ResponseEntityUtils.created(EXECUTION, savedExecutionDto.getId());
  }


  @GetMapping(EXECUTION + "/{id}")
  @Operation(
      summary = "Retrieve execution by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineExecutionDto> getExecutionById(
      @Parameter(name = "id", description = "The id of the execution to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineService.getExecutionById(id));
  }

  @PostMapping(SCENARIO)
  @Operation(
      summary = "Save scenario execution",
      responses = {
          @ApiResponse(responseCode = "201", description = "Pipeline scenario execution is created"),
          @ApiResponse(responseCode = "208", description = "Pipeline scenario execution is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pipeline scenario execution to save")
      @Validated @RequestBody final PipelineScenarioExecutionDto scenario
  ) {
    final PipelineScenarioExecutionDto savedExecutionDto = pipelineService.saveScenarioExecution(scenario);
    return ResponseEntityUtils.created(SCENARIO, savedExecutionDto.getId());
  }


  @GetMapping(SCENARIO + "/{id}")
  @Operation(
      summary = "Retrieve scenario execution by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineScenarioExecutionDto> getScenarioExecutionById(
      @Parameter(name = "id", description = "The id of the scenario execution to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineService.getScenarioExecutionById(id));
  }
}

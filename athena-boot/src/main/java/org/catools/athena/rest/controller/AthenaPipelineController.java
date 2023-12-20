package org.catools.athena.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.catools.athena.pipeline.model.PipelineDto;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.catools.athena.pipeline.model.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.catools.athena.rest.exception.GeneralBadRequestException;
import org.catools.athena.rest.service.AthenaPipelineService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.catools.athena.rest.controller.AthenaApiConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = AthenaApiConstants.ROOT, produces = APPLICATION_JSON_VALUE)
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Request is not authenticated"),
    @ApiResponse(responseCode = "403", description = "Unauthorized to perform function")
})
public class AthenaPipelineController {

  private final AthenaPipelineService athenaPipelineService;

  public AthenaPipelineController(AthenaPipelineService AthenaPipelineService) {
    this.athenaPipelineService = AthenaPipelineService;
  }

  @GetMapping(EXECUTION_STATUSES)
  @Operation(
      summary = "Retrieve execution statuses",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<List<PipelineExecutionStatusDto>> getExecutionStatuses() {
    final List<PipelineExecutionStatusDto> executionStatuses = athenaPipelineService.getExecutionStatuses();
    return executionStatuses.isEmpty() ?
        ResponseEntity.noContent().cacheControl(MAX_AGE_SINGLE_DAY).build() :
        ResponseEntity.ok().cacheControl(MAX_AGE_SINGLE_DAY).body(executionStatuses);
  }

  @GetMapping(EXECUTION_STATUS)
  @Operation(
      summary = "Retrieve execution status by name",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully returned data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PipelineExecutionStatusDto> getExecutionStatus(
      @Parameter(name = "statusName")
      @RequestParam final String statusName
  ) {
    final Optional<PipelineExecutionStatusDto> executionStatusDto = athenaPipelineService.getExecutionStatusByName(statusName);
    return executionStatusDto.map(statusDto -> ResponseEntity.ok().cacheControl(MAX_AGE_SINGLE_DAY).body(statusDto))
        .orElseGet(() -> ResponseEntity.noContent().cacheControl(MAX_AGE_SINGLE_DAY).build());
  }

  @PostMapping(EXECUTION_STATUS)
  @Operation(
      summary = "Save execution status",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "208", description = "Execution status is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineExecutionStatusDto> saveExecutionStatus(
      @Parameter(description = "Execution status to save", name = "status", required = true)
      @Validated @RequestBody final PipelineExecutionStatusDto pipelineExecutionStatusDto
  ) {
    try {
      final Optional<PipelineExecutionStatusDto> executionStatusFromDb = athenaPipelineService.getExecutionStatusByName(pipelineExecutionStatusDto.getName());
      if (executionStatusFromDb.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final PipelineExecutionStatusDto saveExecutionStatus = athenaPipelineService.saveExecutionStatus(pipelineExecutionStatusDto);
      return ResponseEntity.ok().body(saveExecutionStatus);
    } catch (Throwable generalEx) {
      // let GeneralExceptionHandler to take care of it
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @GetMapping(PIPELINE)
  @Operation(
      summary = "Retrieve pipeline by name, number and environment code, if pipeline number not provided, the latest pipeline number will be considered",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> getPipeline(
      @Parameter(description = "Pipeline name", name = "name", required = true)
      @RequestParam final String pipelineName,
      @Parameter(description = "Pipeline number", name = "number", required = true)
      @RequestParam(required = false) final String pipelineNumber,
      @Parameter(description = "Environment code", name = "env", required = true)
      @RequestParam(required = false) final String environmentCode
  ) {
    try {
      final Optional<PipelineDto> pipeline = athenaPipelineService.getLastPipelineDto(pipelineName, pipelineNumber, environmentCode);
      return pipeline
          .map(pipelineDto -> ResponseEntity.ok().body(pipelineDto))
          .orElseGet(() -> ResponseEntity.notFound().build());
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @PatchMapping(PIPELINE)
  @Operation(
      summary = "Update pipeline end cate, by pipeline id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> updatePipelineEndDate(
      @Parameter(description = "Pipeline Id", name = "pipelineId", required = true)
      @RequestParam final Long pipelineId,
      @Parameter(description = "End date", name = "endDate")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false) final Date date
  ) {
    try {
      Date enddate = date == null ? new Date() : date;
      PipelineDto updatedPipeline = athenaPipelineService.updatePipelineEndDate(pipelineId, enddate);
      return ResponseEntity.ok().body(updatedPipeline);
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @PostMapping(PIPELINE)
  @Operation(
      summary = "Save pipeline",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> savePipeline(
      @Parameter(description = "Pipeline to save", name = "pipeline", required = true)
      @Validated @RequestBody final PipelineDto pipeline
  ) {
    try {
      // We shouldn't have multiple environment with similar code
      final Optional<PipelineDto> oPipeline = athenaPipelineService.getLastPipelineDto(pipeline.getName(), pipeline.getNumber(), pipeline.getEnvironmentCode());
      if (oPipeline.isPresent()) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
      }

      final PipelineDto savedPipelineDto = athenaPipelineService.savePipeline(pipeline);
      return ResponseEntity.ok().body(savedPipelineDto);
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @PostMapping(EXECUTION)
  @Operation(
      summary = "Save execution",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineExecutionDto> saveExecution(
      @Parameter(description = "Execution to save", name = "execution", required = true) @Validated @RequestBody final PipelineExecutionDto execution
  ) {
    try {
      final PipelineExecutionDto savedExecutionDto = athenaPipelineService.saveExecution(execution);
      return ResponseEntity.ok().body(savedExecutionDto);
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }

  @PostMapping(SCENARIO)
  @Operation(
      summary = "Save scenario execution",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineScenarioExecutionDto> saveScenarioExecution(
      @Parameter(description = "Scenario execution to save", name = "scenario", required = true) @Validated @RequestBody final PipelineScenarioExecutionDto scenario
  ) {
    try {
      final PipelineScenarioExecutionDto savedExecutionDto = athenaPipelineService.saveScenarioExecution(scenario);
      return ResponseEntity.ok().body(savedExecutionDto);
    } catch (Throwable generalEx) {
      throw new GeneralBadRequestException(generalEx);
    }
  }
}

package org.catools.athena.pipeline.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;
import org.catools.athena.pipeline.common.service.PipelineExecutionStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.pipeline.common.config.PipelinePathDefinitions.EXECUTION_STATUS;
import static org.catools.athena.pipeline.common.config.PipelinePathDefinitions.EXECUTION_STATUSES;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Pipeline Metric Collector API")
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PipelineExecutionStatusController {

  private final PipelineExecutionStatusService pipelineExecutionStatusService;

  @GetMapping(EXECUTION_STATUSES)
  @Operation(
      summary = "Retrieve execution statuses",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<PipelineExecutionStatusDto>> getAll() {
    return ResponseEntityUtils.okOrNoContent(pipelineExecutionStatusService.getAll());
  }

  @GetMapping(EXECUTION_STATUS + "/{id}")
  @Operation(
      summary = "Retrieve execution status by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PipelineExecutionStatusDto> getById(
      @Parameter(name = "id", description = "The id of the status to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineExecutionStatusService.getById(id));
  }

  @GetMapping(EXECUTION_STATUS)
  @Operation(
      summary = "Retrieve execution status by name",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PipelineExecutionStatusDto> getByName(
      @Parameter(name = "name", description = "The name of the status to retrieve")
      @RequestParam final String name
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineExecutionStatusService.getByName(name));
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
    final Optional<PipelineExecutionStatusDto> executionStatusFromDb = pipelineExecutionStatusService.getByName(pipelineExecutionStatusDto.getName());

    if (executionStatusFromDb.isPresent()) {
      return ResponseEntityUtils.alreadyReported(EXECUTION_STATUS, executionStatusFromDb.get().getId());
    }

    final PipelineExecutionStatusDto saveExecutionStatus = pipelineExecutionStatusService.save(pipelineExecutionStatusDto);
    return ResponseEntityUtils.created(EXECUTION_STATUS, saveExecutionStatus.getId());
  }

}

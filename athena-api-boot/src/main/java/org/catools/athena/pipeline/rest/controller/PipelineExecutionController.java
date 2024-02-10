package org.catools.athena.pipeline.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.pipeline.common.service.PipelineExecutionService;
import org.catools.athena.pipeline.model.PipelineExecutionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.catools.athena.pipeline.common.config.PipelinePathDefinitions.EXECUTION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Pipeline Metric Collector API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PipelineExecutionController {

  private final PipelineExecutionService pipelineExecutionService;

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
    final PipelineExecutionDto savedExecutionDto = pipelineExecutionService.save(execution);
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
  public ResponseEntity<PipelineExecutionDto> getById(
      @Parameter(name = "id", description = "The id of the execution to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineExecutionService.getById(id));
  }
}

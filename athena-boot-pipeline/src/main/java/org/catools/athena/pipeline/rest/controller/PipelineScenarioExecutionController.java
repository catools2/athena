package org.catools.athena.pipeline.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.pipeline.common.service.PipelineScenarioExecutionService;
import org.catools.athena.pipeline.model.PipelineScenarioExecutionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.catools.athena.pipeline.common.config.PipelinePathDefinitions.SCENARIO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Pipeline Metric Collector API")
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PipelineScenarioExecutionController {

  private final PipelineScenarioExecutionService pipelineScenarioExecutionService;

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
    final PipelineScenarioExecutionDto savedExecutionDto = pipelineScenarioExecutionService.save(scenario);
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
  public ResponseEntity<PipelineScenarioExecutionDto> getById(
      @Parameter(name = "id", description = "The id of the scenario execution to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineScenarioExecutionService.getById(id));
  }
}

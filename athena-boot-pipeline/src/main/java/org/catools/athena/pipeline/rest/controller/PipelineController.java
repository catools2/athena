package org.catools.athena.pipeline.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.pipeline.common.service.PipelineService;
import org.catools.athena.pipeline.model.PipelineDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static org.catools.athena.pipeline.common.config.PipelinePathDefinitions.PIPELINE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Pipeline Metric Collector API")
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PipelineController {

  private final PipelineService pipelineService;

  @GetMapping(PIPELINE)
  @Operation(
      summary = "Retrieve the last pipeline by name, number, version code and environment code." +
          "Note that name and number can have SQL like format for more flexibility during search.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> getLastPipeline(
      @Parameter(name = "name", description = "The pipeline name")
      @RequestParam final String name,
      @Parameter(name = "number", description = "The pipeline number")
      @RequestParam(required = false) final String number,
      @Parameter(name = "versionCode", description = "The version code")
      @RequestParam(required = false) final String versionCode,
      @Parameter(name = "envCode", description = "The environment code")
      @RequestParam(required = false) final String envCode
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineService.getPipeline(name, number, versionCode, envCode));
  }

  @GetMapping(PIPELINE + "/{id}")
  @Operation(
      summary = "Retrieve pipeline by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> getById(
      @Parameter(name = "id", description = "The id of the status to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(pipelineService.getById(id));
  }

  @PutMapping(PIPELINE)
  @Operation(
      summary = "Update pipeline end cate, by pipeline id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully processed request"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<PipelineDto> updateEndDate(
      @Parameter(name = "pipelineId", description = "The pipeline Id to update")
      @RequestParam final Long pipelineId,
      @Parameter(name = "date", description = "The end date in ISO format")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
      @RequestParam(required = false) final Instant date
  ) {
    Instant endDate = date == null ? Instant.now() : date;
    PipelineDto updatedPipeline = pipelineService.updatePipelineEndDate(pipelineId, endDate);
    return ResponseEntityUtils.ok(updatedPipeline);
  }

  @PostMapping(PIPELINE)
  @Operation(
      summary = "Save pipeline or update the current one if any with the same name, number and environment code exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "pipeline is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pipeline to save or update")
      @Validated @RequestBody final PipelineDto pipelineDto
  ) {
    final PipelineDto savedPipelineDto = pipelineService.saveOrUpdate(pipelineDto);
    return ResponseEntityUtils.created(PIPELINE, savedPipelineDto.getId());
  }
}

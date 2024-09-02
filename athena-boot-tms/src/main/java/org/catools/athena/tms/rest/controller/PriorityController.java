package org.catools.athena.tms.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.tms.common.service.PriorityService;
import org.catools.athena.tms.model.PriorityDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.catools.athena.tms.common.config.TmsPathDefinitions.TMS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - API")
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PriorityController {
  private static final String TMS_STATUS = TMS + "/priority";

  private final PriorityService priorityService;

  @PostMapping(TMS_STATUS)
  @Operation(
      summary = "Save priority or update the current one if any with the same code exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The priority to save or update")
      @Validated @RequestBody final PriorityDto priorityDto
  ) {
    final PriorityDto savedRecord = priorityService.saveOrUpdate(priorityDto);
    return ResponseEntityUtils.created(TMS_STATUS, savedRecord.getId());
  }

  @GetMapping(TMS_STATUS + "/{id}")
  @Operation(
      summary = "Retrieve priority by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PriorityDto> getById(
      @Parameter(name = "id", description = "The id of priority to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(priorityService.getById(id));
  }

  @GetMapping(TMS_STATUS)
  @Operation(
      summary = "Retrieve priority by code or name",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PriorityDto> search(
      @Parameter(name = "keyword", description = "The code or name of the priority to retrieve")
      @RequestParam final String keyword
  ) {
    return ResponseEntityUtils.okOrNoContent(priorityService.search(keyword));
  }

}

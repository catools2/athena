package org.catools.athena.rest.tms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.tms.service.StatusTransitionService;
import org.catools.athena.tms.model.StatusTransitionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.tms.config.TmsPathDefinitions.TMS_STATUS_TRANSITIONS_PATH;
import static org.catools.athena.rest.tms.config.TmsPathDefinitions.TMS_STATUS_TRANSITION_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Transition API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StatusTransitionController {

  private final StatusTransitionService statusTransitionService;

  @PostMapping(TMS_STATUS_TRANSITION_PATH)
  @Operation(
      summary = "Save status transition",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "208", description = "Api spec is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @Parameter(name = "itemCode", description = "The item code which status transition belongs to.")
      @PathVariable final String itemCode,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The status transition to save")
      @Validated @RequestBody final StatusTransitionDto statusTransitionDto
  ) {
    final Optional<StatusTransitionDto> recordFromDB = statusTransitionService.findStatusTransition(statusTransitionDto, itemCode);

    if (recordFromDB.isPresent()) {
      return ResponseEntityUtils.alreadyReported(TMS_STATUS_TRANSITION_PATH, recordFromDB.get().getId());
    }

    final StatusTransitionDto savedRecord = statusTransitionService.save(statusTransitionDto, itemCode);
    return ResponseEntityUtils.created(TMS_STATUS_TRANSITION_PATH, savedRecord.getId());
  }

  @GetMapping(TMS_STATUS_TRANSITION_PATH + "/{id}")
  @Operation(
      summary = "Retrieve status transition by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<StatusTransitionDto> getById(
      @Parameter(name = "id", description = "The id of status transition to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(statusTransitionService.getById(id));
  }

  @GetMapping(TMS_STATUS_TRANSITIONS_PATH)
  @Operation(
      summary = "Retrieve status transition by code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Set<StatusTransitionDto>> getAllByItemCode(
      @Parameter(name = "itemCode", description = "The code of the status transition to retrieve")
      @PathVariable final String itemCode
  ) {
    return ResponseEntityUtils.okOrNoContent(statusTransitionService.getAllByItemCode(itemCode));
  }
}

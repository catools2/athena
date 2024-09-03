package org.catools.athena.tms.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.tms.common.service.StatusTransitionService;
import org.catools.athena.tms.model.StatusTransitionDto;
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

import static org.catools.athena.tms.common.config.TmsPathDefinitions.TMS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Transition API")
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StatusTransitionController {
  private static final String TMS_STATUS_TRANSITION = TMS + "/transition";
  private static final String TMS_STATUS_TRANSITIONS = TMS + "/transitions";

  private final StatusTransitionService statusTransitionService;

  @PostMapping(TMS_STATUS_TRANSITION)
  @Operation(
      summary = "Save status transition",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "208", description = "Api spec is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @Parameter(name = "itemCode", description = "The item code which status transition belongs to.")
      @RequestParam final String itemCode,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The status transition to save")
      @Validated @RequestBody final StatusTransitionDto statusTransitionDto
  ) {
    final Optional<StatusTransitionDto> entityFromDB = statusTransitionService.findStatusTransition(statusTransitionDto, itemCode);

    if (entityFromDB.isPresent()) {
      return ResponseEntityUtils.alreadyReported(TMS_STATUS_TRANSITION, entityFromDB.get().getId());
    }

    final StatusTransitionDto savedRecord = statusTransitionService.save(statusTransitionDto, itemCode);
    return ResponseEntityUtils.created(TMS_STATUS_TRANSITION, savedRecord.getId());
  }

  @GetMapping(TMS_STATUS_TRANSITION + "/{id}")
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

  @GetMapping(TMS_STATUS_TRANSITIONS)
  @Operation(
      summary = "Retrieve status transition by code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Set<StatusTransitionDto>> getAllByItemCode(
      @Parameter(name = "itemCode", description = "The code of the status transition to retrieve")
      @RequestParam final String itemCode
  ) {
    return ResponseEntityUtils.okOrNoContent(statusTransitionService.getAllByItemCode(itemCode));
  }
}

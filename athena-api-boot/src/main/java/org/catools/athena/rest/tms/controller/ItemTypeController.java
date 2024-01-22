package org.catools.athena.rest.tms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.tms.service.ItemTypeService;
import org.catools.athena.tms.model.ItemTypeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.tms.config.TmsPathDefinitions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Item API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ItemTypeController {

  private final ItemTypeService itemTypeService;

  @PostMapping(TMS_ITEM_TYPE_PATH)
  @Operation(
      summary = "Save item type",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "208", description = "Api spec is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The item type to save")
      @Validated @RequestBody final ItemTypeDto itemTypeDto
  ) {
    final Optional<ItemTypeDto> entityFromDB = itemTypeService.getByCode(itemTypeDto.getCode());

    if (entityFromDB.isPresent()) {
      return ResponseEntityUtils.alreadyReported(TMS_ITEM_PATH, entityFromDB.get().getId());
    }

    final ItemTypeDto savedRecord = itemTypeService.save(itemTypeDto);
    return ResponseEntityUtils.created(TMS_ITEM_PATH, savedRecord.getId());
  }

  @GetMapping(TMS_ITEM_TYPE_PATH + "/{id}")
  @Operation(
      summary = "Retrieve item type by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ItemTypeDto> getById(
      @Parameter(name = "id", description = "The id of item type to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(itemTypeService.getById(id));
  }

  @GetMapping(TMS_ITEM_TYPE_PATH)
  @Operation(
      summary = "Retrieve item type by code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ItemTypeDto> getByCode(
      @Parameter(name = "code", description = "The code of the item type to retrieve")
      @PathVariable final String code
  ) {
    return ResponseEntityUtils.okOrNoContent(itemTypeService.getByCode(code));
  }


  @GetMapping(TMS_ITEM_TYPES_PATH)
  @Operation(
      summary = "Retrieve all item types",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Set<ItemTypeDto>> getAll() {
    return ResponseEntityUtils.okOrNoContent(itemTypeService.getAll());
  }
}

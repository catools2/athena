package org.catools.athena.rest.tms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.tms.service.ItemService;
import org.catools.athena.tms.model.ItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.catools.athena.rest.tms.config.TmsPathDefinitions.TMS_ITEM_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Item API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @PostMapping(TMS_ITEM_PATH)
  @Operation(
      summary = "Save item",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "208", description = "Api spec is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The item to save")
      @Validated @RequestBody final ItemDto itemDto
  ) {
    final Optional<ItemDto> entityFromDB = itemService.getByCode(itemDto.getCode());

    if (entityFromDB.isPresent()) {
      return ResponseEntityUtils.alreadyReported(TMS_ITEM_PATH, entityFromDB.get().getId());
    }

    final ItemDto savedRecord = itemService.save(itemDto);
    return ResponseEntityUtils.created(TMS_ITEM_PATH, savedRecord.getId());
  }

  @GetMapping(TMS_ITEM_PATH + "/{id}")
  @Operation(
      summary = "Retrieve item by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ItemDto> getById(
      @Parameter(name = "id", description = "The id of item to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(itemService.getById(id));
  }

  @GetMapping(TMS_ITEM_PATH)
  @Operation(
      summary = "Retrieve item by code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ItemDto> getByCode(
      @Parameter(name = "code", description = "The code of the item to retrieve")
      @PathVariable final String code
  ) {
    return ResponseEntityUtils.okOrNoContent(itemService.getByCode(code));
  }
}

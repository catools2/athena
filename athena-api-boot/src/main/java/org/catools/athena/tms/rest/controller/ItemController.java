package org.catools.athena.tms.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.tms.common.service.ItemService;
import org.catools.athena.tms.model.ItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.catools.athena.tms.common.config.TmsPathDefinitions.TMS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Task Management System - Item API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ItemController {

  private static final String TMS_ITEM = TMS + "/item";


  private final ItemService itemService;

  @PostMapping(TMS_ITEM)
  @Operation(
      summary = "Save item or update the current one if any with the same code exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The item to save or update")
      @Validated @RequestBody final ItemDto itemDto
  ) {
    final ItemDto savedRecord = itemService.saveOrUpdate(itemDto);
    return ResponseEntityUtils.created(TMS_ITEM, savedRecord.getId());
  }

  @GetMapping(TMS_ITEM + "/{id}")
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

  @GetMapping(TMS_ITEM)
  @Operation(
      summary = "Retrieve item by code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ItemDto> getByCode(
      @Parameter(name = "code", description = "The code of the item to retrieve")
      @RequestParam final String code
  ) {
    return ResponseEntityUtils.okOrNoContent(itemService.getByCode(code));
  }
}

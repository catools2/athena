package org.catools.athena.tms.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.tms.common.service.SyncInfoService;
import org.catools.athena.tms.model.SyncInfoDto;
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

@Tag(name = "Athena Task Management System - Sync Info API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SyncInfoController {
  public static final String TMS_SYNC_INFO = TMS + "/syncInfo";

  private final SyncInfoService syncInfoService;

  @GetMapping(TMS_SYNC_INFO + "/{id}")
  @Operation(
      summary = "Retrieve sync info by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<SyncInfoDto> getById(
      @Parameter(name = "id", description = "The id of sync info to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(syncInfoService.getById(id));
  }

  @GetMapping(TMS_SYNC_INFO)
  @Operation(
      summary = "Retrieve sync infos by action, component and project code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<SyncInfoDto> search(
      @Parameter(name = "action", description = "The action to retrieve sync info for")
      @RequestParam final String action,
      @Parameter(name = "component", description = "The component to retrieve sync info for")
      @RequestParam final String component,
      @Parameter(name = "project", description = "The project code to retrieve sync info for")
      @RequestParam final String project
  ) {
    return ResponseEntityUtils.okOrNoContent(syncInfoService.search(action, component, project));
  }

  @PostMapping(TMS_SYNC_INFO)
  @Operation(
      summary = "Save sync info or update the current one if any with the same action, component and project exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api spec is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The sync info to save or update")
      @Validated @RequestBody final SyncInfoDto syncInfo
  ) {
    final SyncInfoDto savedRecord = syncInfoService.saveOrUpdate(syncInfo);
    return ResponseEntityUtils.created(TMS_SYNC_INFO, savedRecord.getId());
  }
}

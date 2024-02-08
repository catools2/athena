package org.catools.athena.core.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.core.common.service.VersionService;
import org.catools.athena.core.model.VersionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Version Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VersionController {

  public static final String VERSION = "/version";

  private final VersionService versionService;

  @GetMapping(VERSION)
  @Operation(
      summary = "Retrieve version by version code",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<VersionDto> getByCode(
      @Parameter(name = "versionCode", description = "The code of the version to retrieve")
      @RequestParam final String envCode
  ) {
    return ResponseEntityUtils.okOrNoContent(versionService.getByCode(envCode));
  }

  @GetMapping(VERSION + "/{id}")
  @Operation(
      summary = "Retrieve version by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<VersionDto> getById(
      @Parameter(name = "id", description = "The id of the version to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(versionService.getById(id));
  }

  @PostMapping(VERSION)
  @Operation(
      summary = "Save version",
      responses = {
          @ApiResponse(responseCode = "201", description = "Version is created"),
          @ApiResponse(responseCode = "208", description = "Version is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The version to save")
      @Validated @RequestBody final VersionDto version
  ) {
    final Optional<VersionDto> versionByCode = versionService.getByCode(version.getCode());
    if (versionByCode.isPresent()) {
      return ResponseEntityUtils.alreadyReported(VERSION, versionByCode.get().getId());
    }

    final VersionDto savedVersionDto = versionService.save(version);
    return ResponseEntityUtils.created(VERSION, savedVersionDto.getId());
  }
}

package org.catools.athena.core.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.controlleradvice.ControllerErrorHandler;
import org.catools.athena.common.markers.IdRequired;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.service.VersionService;
import org.catools.athena.core.entity.VersionFilterDto;
import org.catools.athena.model.core.VersionDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
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

import java.util.Optional;

import static org.catools.athena.core.rest.controller.VersionController.VERSION;

@RestController
@Tag(name = "Athena Version Rest API")
@RequestMapping(value = VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@Import(ControllerErrorHandler.class)
@RequiredArgsConstructor
public class VersionController {

  public static final String VERSION = "/version";

  private final VersionService versionService;

  @GetMapping("/all")
  @Operation(
      summary = "Retrieve all versions with pagination",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Page<VersionDto>> getAll(
      @Parameter(name = "page", description = "Page number (0-based)")
      @RequestParam(defaultValue = "0") final int page,
      @Parameter(name = "size", description = "Page size")
      @RequestParam(defaultValue = "10") final int size,
      @Parameter(name = "sort", description = "Sort field")
      @RequestParam(defaultValue = "code") final String sort,
      @Parameter(name = "direction", description = "Sort direction (ASC or DESC)")
      @RequestParam(defaultValue = "ASC") final String direction,
      @Parameter(name = "code", description = "Filter by code")
      @RequestParam(required = false) final String code,
      @Parameter(name = "name", description = "Filter by name")
      @RequestParam(required = false) final String name,
      @Parameter(name = "project", description = "Filter by project")
      @RequestParam(required = false) final String project
  ) {
    Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));
    // Build VersionFilterDto from individual parameters
    VersionFilterDto filter = new VersionFilterDto(code, name, project);
    Page<VersionDto> result = versionService.getAll(pageable, filter);
    return ResponseEntity.ok(result);
  }

  @GetMapping
  @Operation(
      summary = "Retrieve version by version code or name, optionally filtered by project",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<VersionDto> search(
      @Parameter(name = "keyword", description = "The code or name of the version to retrieve")
      @RequestParam final String keyword,
      @Parameter(name = "project", description = "The project code to filter by")
      @RequestParam final String project
  ) {
    return ResponseEntityUtils.okOrNoContent(versionService.search(project, keyword));
  }

  @GetMapping("/{id}")
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

  @PostMapping
  @Operation(
      summary = "Save version or update the current one if any with the same code exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Version is created"),
          @ApiResponse(responseCode = "208", description = "Version already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request"),
          @ApiResponse(responseCode = "409", description = "Version already exists")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The version to save or update")
      @Validated @RequestBody final VersionDto version
  ) {
    try {
      final VersionDto savedVersionDto = versionService.save(version);
      return ResponseEntityUtils.created(VERSION, savedVersionDto.getId());
    } catch (DataIntegrityViolationException ex) {
      if (ex.getCause() instanceof ConstraintViolationException) {
        if (version.getCode() == null)
          return ResponseEntityUtils.conflicted();

        Optional<VersionDto> dbRecord = versionService.search(version.getProject(), version.getCode());
        if (dbRecord.isPresent())
          return ResponseEntityUtils.alreadyReported(VERSION, dbRecord.get().getId());
      }
      return ResponseEntityUtils.conflicted();
    }
  }

  @PutMapping
  @Operation(
      summary = "Update version if one with the provided id exists",
      responses = {
          @ApiResponse(responseCode = "200", description = "Version is updated"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> update(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The version to update")
      @Validated(IdRequired.class) @RequestBody final VersionDto version
  ) {
    final VersionDto savedVersionDto = versionService.update(version);
    return ResponseEntityUtils.updated(VERSION, savedVersionDto.getId());
  }
}

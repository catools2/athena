package org.catools.athena.rest.git.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.git.service.CommitMetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.git.config.GitPathDefinitions.COMMIT_METADATA;
import static org.catools.athena.rest.git.config.GitPathDefinitions.COMMIT_METADATA_SET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Git Commit Metadata Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CommitMetadataController {

  private final CommitMetadataService commitMetadataService;

  @GetMapping(COMMIT_METADATA_SET)
  @Operation(
      summary = "Retrieve all git commit metadata",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<MetadataDto>> getAll() {
    return ResponseEntityUtils.okOrNoContent(commitMetadataService.getAll());
  }

  @GetMapping(COMMIT_METADATA)
  @Operation(
      summary = "Retrieve commit metadata by name and value",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<MetadataDto> search(
      @Parameter(name = "name", description = "The commit metadata name to search for")
      @RequestParam final String name,
      @Parameter(name = "value", description = "The commit metadata value to search for")
      @RequestParam final String value
  ) {
    return ResponseEntityUtils.okOrNoContent(commitMetadataService.search(name, value));
  }

  @GetMapping(COMMIT_METADATA + "/{id}")
  @Operation(
      summary = "Retrieve commit metadata by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<MetadataDto> getById(
      @Parameter(name = "id", description = "The id of the commit metadata to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(commitMetadataService.getById(id));
  }

  @PostMapping(COMMIT_METADATA)
  @Operation(
      summary = "Save commit metadata",
      responses = {
          @ApiResponse(responseCode = "201", description = "Commit metadata is created"),
          @ApiResponse(responseCode = "208", description = "Commit metadata is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The Commit metadata to save")
      @Validated @RequestBody final MetadataDto commit
  ) {
    final Optional<MetadataDto> commitFromDb = commitMetadataService.search(commit.getName(), commit.getValue());
    if (commitFromDb.isPresent()) {
      return ResponseEntityUtils.alreadyReported(COMMIT_METADATA, commitFromDb.get().getId());
    }

    final MetadataDto savedMetadataDto = commitMetadataService.save(commit);
    return ResponseEntityUtils.created(COMMIT_METADATA, savedMetadataDto.getId());
  }
}

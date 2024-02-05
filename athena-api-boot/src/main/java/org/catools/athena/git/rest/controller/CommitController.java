package org.catools.athena.git.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.git.common.service.CommitService;
import org.catools.athena.git.model.CommitDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Git Commit Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CommitController {
  public static final String COMMIT = "/commit";

  private final CommitService commitService;

  @PostMapping(COMMIT)
  @Operation(
      summary = "Save commit",
      responses = {
          @ApiResponse(responseCode = "201", description = "Commit is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The commit to save")
      @Validated @RequestBody final CommitDto commit
  ) {
    final CommitDto savedCommitDto = commitService.save(commit);
    return ResponseEntityUtils.created(COMMIT, savedCommitDto.getId());
  }

  @GetMapping(COMMIT)
  @Operation(
      summary = "Retrieve commit where keyword can be either commit hash",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<CommitDto> search(
      @Parameter(name = "keyword", description = "The commit hash to search for")
      @RequestParam final String keyword
  ) {
    return ResponseEntityUtils.okOrNoContent(commitService.search(keyword));
  }

  @GetMapping(COMMIT + "/{id}")
  @Operation(
      summary = "Retrieve commit by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<CommitDto> getById(
      @Parameter(name = "id", description = "The id of the commit to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(commitService.getById(id));
  }
}

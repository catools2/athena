package org.catools.athena.git.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.git.common.service.CommitService;
import org.catools.athena.git.model.CommitDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Git Commit Rest API")
@RequestMapping(path = CommitController.COMMIT, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CommitController {
  public static final String COMMIT = "/commit";

  private final CommitService commitService;

  @GetMapping
  @Operation(
      summary = "Retrieve commit by commit hash",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<CommitDto> search(
      @Parameter(name = "hash", description = "The commit hash to search for")
      @RequestParam final String hash
  ) {
    return ResponseEntityUtils.okOrNoContent(commitService.findByHash(hash));
  }

  @GetMapping("/{id}")
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

  @PostMapping
  @Operation(
      summary = "Save commit or update the current one if any with the same hash exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Commit is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The commit to save or update")
      @Validated @RequestBody final CommitDto commit
  ) {
    final CommitDto savedCommitDto = commitService.saveOrUpdate(commit);
    return ResponseEntityUtils.created(COMMIT, savedCommitDto.getId());
  }
}

package org.catools.athena.git.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.git.common.service.GitRepositoryService;
import org.catools.athena.git.model.GitRepositoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Git Repository Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GitRepositoryController {
  public static final String REPOSITORY = "/repo";

  private final GitRepositoryService repositoryService;

  @GetMapping(REPOSITORY)
  @Operation(
      summary = "Retrieve repository where keyword can be either repository name or url",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<GitRepositoryDto> search(
      @Parameter(name = "keyword", description = "The repository name or url to search for")
      @RequestParam final String keyword
  ) {
    return ResponseEntityUtils.okOrNoContent(repositoryService.search(keyword));
  }

  @GetMapping(REPOSITORY + "/{id}")
  @Operation(
      summary = "Retrieve repository by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<GitRepositoryDto> getById(
      @Parameter(name = "id", description = "The id of the repository to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(repositoryService.getById(id));
  }

  @PostMapping(REPOSITORY)
  @Operation(
      summary = "Save repository",
      responses = {
          @ApiResponse(responseCode = "201", description = "Repository is created"),
          @ApiResponse(responseCode = "208", description = "Repository is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The repository to save")
      @Validated @RequestBody final GitRepositoryDto repository
  ) {
    final Optional<GitRepositoryDto> repositoryFromDb = repositoryService.search(repository.getUrl());
    if (repositoryFromDb.isPresent()) {
      return ResponseEntityUtils.alreadyReported(REPOSITORY, repositoryFromDb.get().getId());
    }

    final GitRepositoryDto savedGitRepositoryDto = repositoryService.save(repository);
    return ResponseEntityUtils.created(REPOSITORY, savedGitRepositoryDto.getId());
  }
}

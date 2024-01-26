package org.catools.athena.rest.git.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.git.model.BranchDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.git.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.git.config.GitPathDefinitions.BRANCH;
import static org.catools.athena.rest.git.config.GitPathDefinitions.BRANCHES;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Git Repository Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BranchController {

  private final BranchService branchService;

  @GetMapping(BRANCHES)
  @Operation(
      summary = "Retrieve all git branches",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<BranchDto>> getAll() {
    return ResponseEntityUtils.okOrNoContent(branchService.getAll());
  }

  @GetMapping(BRANCH)
  @Operation(
      summary = "Retrieve branch where keyword can be either branch name or url",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<BranchDto> search(
      @Parameter(name = "keyword", description = "The branch name or hash to search for")
      @RequestParam final String keyword
  ) {
    return ResponseEntityUtils.okOrNoContent(branchService.search(keyword));
  }

  @GetMapping(BRANCH + "/{id}")
  @Operation(
      summary = "Retrieve branch by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<BranchDto> getById(
      @Parameter(name = "id", description = "The id of the branch to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(branchService.getById(id));
  }

  @PostMapping(BRANCH)
  @Operation(
      summary = "Save branch",
      responses = {
          @ApiResponse(responseCode = "201", description = "Repository is created"),
          @ApiResponse(responseCode = "208", description = "Repository is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The branch to save")
      @Validated @RequestBody final BranchDto branch
  ) {
    final Optional<BranchDto> branchFromDb = branchService.search(branch.getHash());
    if (branchFromDb.isPresent()) {
      return ResponseEntityUtils.alreadyReported(BRANCH, branchFromDb.get().getId());
    }

    final BranchDto savedBranchDto = branchService.save(branch);
    return ResponseEntityUtils.created(BRANCH, savedBranchDto.getId());
  }
}

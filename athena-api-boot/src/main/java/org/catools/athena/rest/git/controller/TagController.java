package org.catools.athena.rest.git.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.git.model.TagDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.git.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Git Tag Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TagController {
  public static final String TAG = "/tag";

  private final TagService tagService;

  @GetMapping(TAG)
  @Operation(
      summary = "Retrieve tag where keyword can be either tag hash",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<TagDto> search(
      @Parameter(name = "keyword", description = "The tag hash to search for")
      @RequestParam final String keyword
  ) {
    return ResponseEntityUtils.okOrNoContent(tagService.search(keyword));
  }

  @GetMapping(TAG + "/{id}")
  @Operation(
      summary = "Retrieve tag by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<TagDto> getById(
      @Parameter(name = "id", description = "The id of the tag to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(tagService.getById(id));
  }

  @PostMapping(TAG)
  @Operation(
      summary = "Save tag",
      responses = {
          @ApiResponse(responseCode = "201", description = "Tag is created"),
          @ApiResponse(responseCode = "208", description = "Tag is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The tag to save")
      @Validated @RequestBody final TagDto tag
  ) {
    final Optional<TagDto> tagFromDb = tagService.search(tag.getName());
    if (tagFromDb.isPresent()) {
      return ResponseEntityUtils.alreadyReported(TAG, tagFromDb.get().getId());
    }

    final TagDto savedTagDto = tagService.save(tag);
    return ResponseEntityUtils.created(TAG, savedTagDto.getId());
  }
}

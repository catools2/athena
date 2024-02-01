package org.catools.athena.rest.apispec.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.rest.apispec.service.ApiPathService;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.catools.athena.rest.apispec.config.ApiSpecPathDefinitions.API_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Athena Api Path API")
@RestController
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiPathController {

  private final ApiPathService apiPathService;

  @PostMapping(API_PATH)
  @Operation(
      summary = "Save api path",
      responses = {
          @ApiResponse(responseCode = "201", description = "Api path is created"),
          @ApiResponse(responseCode = "208", description = "Api path is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The api path to save")
      @Validated @RequestBody final ApiPathDto apiPathDto
  ) {
    final Optional<ApiPathDto> apiPathFromDb = apiPathService.search(apiPathDto.getApiSpecId(), apiPathDto.getMethod(), apiPathDto.getUrl());

    if (apiPathFromDb.isPresent()) {
      apiPathDto.setId(apiPathFromDb.get().getId());
      apiPathDto.setFirstTimeSeen(apiPathFromDb.get().getFirstTimeSeen());
      ApiPathDto savedPath = apiPathService.save(apiPathDto);
      return ResponseEntityUtils.alreadyReported(API_PATH, savedPath.getId());
    }

    final ApiPathDto savedPath = apiPathService.save(apiPathDto);
    return ResponseEntityUtils.created(API_PATH, savedPath.getId());
  }

  @GetMapping(API_PATH + "/{id}")
  @Operation(
      summary = "Retrieve api path by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ApiPathDto> getById(
      @Parameter(name = "id", description = "The id of the api path to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(apiPathService.getById(id));
  }

  @GetMapping(API_PATH)
  @Operation(
      summary = "Retrieve api path by spec id, method and url",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ApiPathDto> search(
      @Parameter(name = "apiSpecId", description = "The path id of the api path to retrieve")
      @RequestParam final Long apiSpecId,
      @Parameter(name = "method", description = "The method of the api path to retrieve")
      @RequestParam final String method,
      @Parameter(name = "url", description = "The url of the api path to retrieve")
      @RequestParam final String url
  ) {
    return ResponseEntityUtils.okOrNoContent(apiPathService.search(apiSpecId, method, url));
  }
}

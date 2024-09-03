package org.catools.athena.spec.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.spec.common.service.ApiSpecService;
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

@Tag(name = "Athena Api Specification API")
@RestController
@RequestMapping(value = ApiSpecController.API_SPEC, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiSpecController {
  public static final String API_SPEC = "/spec";

  private final ApiSpecService apiSpecService;

  @PostMapping
  @Operation(
      summary = "Save new API specification or update the exist one if the specification with the same name exists for the same project",
      responses = {
          @ApiResponse(responseCode = "201", description = "API specification is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The api spec to save or update")
      @Validated @RequestBody final ApiSpecDto apiSpecDto
  ) {
    final ApiSpecDto savedApiSpec = apiSpecService.saveOrUpdate(apiSpecDto);
    return ResponseEntityUtils.created(API_SPEC, savedApiSpec.getId());
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Retrieve api spec by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ApiSpecDto> getById(
      @Parameter(name = "id", description = "The id of the api spec to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(apiSpecService.getById(id));
  }

  @GetMapping
  @Operation(
      summary = "Retrieve api spec by project code and name",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ApiSpecDto> search(
      @Parameter(name = "projectCode", description = "The project code of the api spec to retrieve")
      @RequestParam final String projectCode,
      @Parameter(name = "name", description = "The name of the api spec to retrieve")
      @RequestParam final String name
  ) {
    return ResponseEntityUtils.okOrNoContent(apiSpecService.getByProjectCodeAndName(projectCode, name));
  }
}

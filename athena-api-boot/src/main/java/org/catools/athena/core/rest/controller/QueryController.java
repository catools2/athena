package org.catools.athena.core.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.core.common.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Query Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QueryController {

  public static final String QUERY = "/query";
  private final QueryService queryService;

  @GetMapping(QUERY + "/record")
  @Operation(
      summary = "Retrieve single object by query",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Object> querySingleResult(
      @Parameter(name = "query", description = "The query to execute and retrieve object")
      @RequestParam final String query
  ) {
    return ResponseEntityUtils.okOrNoContent(queryService.querySingleResult(query));
  }

  @GetMapping(QUERY + "/records")
  @Operation(
      summary = "Retrieve set of objects by query",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<Object>> queryCollection(
      @Parameter(name = "query", description = "The query to execute and retrieve set of objects")
      @RequestParam final String query
  ) {
    return ResponseEntityUtils.okOrNoContent(queryService.queryCollection(query));
  }

}

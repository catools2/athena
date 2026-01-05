package org.catools.athena.kube.rest.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.utils.ResponseEntityUtils;
import org.catools.athena.kube.common.service.PodService;
import org.catools.athena.model.kube.PodDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Kube Pod Rest API")
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PodController {
  public static final String POD = "/pod";
  public static final String PODS = "/pods";

  private final PodService podService;

  @GetMapping(PODS)
  @Operation(
      summary = "Retrieve pods by project id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<PodDto>> getAll(
      @Parameter(name = "project", description = "The project code of the pod to retrieve")
      @RequestParam final String project,
      @Parameter(name = "namespace", description = "The namespace of the pod to retrieve")
      @RequestParam final String namespace
  ) {
    return ResponseEntityUtils.okOrNoContent(podService.getPods(project, namespace));
  }

  @GetMapping(POD)
  @Operation(
      summary = "Retrieve pod by pod name and namespace",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PodDto> getByNameAndNamespace(
      @Parameter(name = "name", description = "The name of the pod to retrieve")
      @RequestParam final String name,
      @Parameter(name = "namespace", description = "The namespace of the pod to retrieve")
      @RequestParam final String namespace
  ) {
    return ResponseEntityUtils.okOrNoContent(podService.getByNameAndNamespace(name, namespace));
  }

  @GetMapping(POD + "/{id}")
  @Operation(
      summary = "Retrieve pod by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PodDto> getById(
      @Parameter(name = "id", description = "The id of the pod to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(podService.getById(id));
  }

  @PostMapping(POD)
  @Operation(
      summary = "Save pod or update the current one if any with the same name and namespace exists",
      responses = {
          @ApiResponse(responseCode = "201", description = "Pod is created"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> saveOrUpdate(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pod to save or update")
      @Validated @RequestBody final PodDto pod
  ) {
    final PodDto savedPodDto = podService.saveOrUpdate(pod);
    return ResponseEntityUtils.created(POD, savedPodDto.getId());
  }
}

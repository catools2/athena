package org.catools.athena.rest.kube.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.model.PodDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.kube.service.PodService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.kube.config.KubePathDefinitions.POD;
import static org.catools.athena.rest.kube.config.KubePathDefinitions.PODS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Kube Pod Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PodController {

  private final PodService podService;

  @GetMapping(PODS)
  @Operation(
      summary = "Retrieve pods by project id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<PodDto>> getPods(
      @Parameter(name = "projectId", description = "The project id of the pod to retrieve")
      @PathVariable final Long projectId,
      @Parameter(name = "namespace", description = "The namespace of the pod to retrieve")
      @RequestParam final String namespace
  ) {
    return ResponseEntityUtils.okOrNoContent(podService.getByProjectIdAndNamespace(projectId, namespace));
  }

  @GetMapping(POD)
  @Operation(
      summary = "Retrieve pod by pod name and namespace",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<PodDto> getPodByNameAndNamespace(
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
  public ResponseEntity<PodDto> getPodById(
      @Parameter(name = "id", description = "The id of the pod to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(podService.getById(id));
  }

  @PostMapping(POD)
  @Operation(
      summary = "Save pod",
      responses = {
          @ApiResponse(responseCode = "201", description = "Pod is created"),
          @ApiResponse(responseCode = "208", description = "Pod is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The pod to save")
      @Validated @RequestBody final PodDto pod
  ) {
    final Optional<PodDto> podByCode = podService.getByNameAndNamespace(pod.getName(), pod.getNamespace());

    if (podByCode.isPresent()) {
      return ResponseEntityUtils.alreadyReported(POD, podByCode.get().getId());
    }

    final PodDto savedPodDto = podService.save(pod);
    return ResponseEntityUtils.created(POD, savedPodDto.getId());
  }
}

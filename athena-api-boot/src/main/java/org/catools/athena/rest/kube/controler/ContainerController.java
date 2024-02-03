package org.catools.athena.rest.kube.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.catools.athena.kube.model.ContainerDto;
import org.catools.athena.rest.common.utils.ResponseEntityUtils;
import org.catools.athena.rest.core.config.CorePathDefinitions;
import org.catools.athena.rest.kube.service.ContainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static org.catools.athena.rest.kube.config.KubePathDefinitions.CONTAINER;
import static org.catools.athena.rest.kube.config.KubePathDefinitions.CONTAINERS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "Athena Kube Container Rest API")
@RequestMapping(value = CorePathDefinitions.ROOT_API, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ContainerController {

  private final ContainerService containerService;

  @GetMapping(CONTAINERS + "/{podId}")
  @Operation(
      summary = "Retrieve containers by pod id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<Set<ContainerDto>> getContainers(
      @Parameter(name = "podId", description = "The pod id of the container to retrieve")
      @RequestParam final Long podId
  ) {
    return ResponseEntityUtils.okOrNoContent(containerService.getAllByPodId(podId));
  }

  @GetMapping(CONTAINER)
  @Operation(
      summary = "Retrieve container by container name and pod id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ContainerDto> getContainerByName(
      @Parameter(name = "name", description = "The name of the container to retrieve")
      @RequestParam final String name,
      @Parameter(name = "podId", description = "The pod id of the container to retrieve")
      @RequestParam final Long podId
  ) {
    return ResponseEntityUtils.okOrNoContent(containerService.getByNameAndPodId(name, podId));
  }

  @GetMapping(CONTAINER + "/{id}")
  @Operation(
      summary = "Retrieve container by id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved data"),
          @ApiResponse(responseCode = "204", description = "No content to return")
      })
  public ResponseEntity<ContainerDto> getContainerById(
      @Parameter(name = "id", description = "The id of the container to retrieve")
      @PathVariable final Long id
  ) {
    return ResponseEntityUtils.okOrNoContent(containerService.getById(id));
  }

  @PostMapping(CONTAINER)
  @Operation(
      summary = "Save container",
      responses = {
          @ApiResponse(responseCode = "201", description = "Container is created"),
          @ApiResponse(responseCode = "208", description = "Container is already exists"),
          @ApiResponse(responseCode = "400", description = "Failed to process request")
      })
  public ResponseEntity<Void> save(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The container to save")
      @Validated @RequestBody final ContainerDto container
  ) {
    final Optional<ContainerDto> containerByCode = containerService.getByNameAndPodId(container.getName(), container.getPodId());
    if (containerByCode.isPresent()) {
      return ResponseEntityUtils.alreadyReported(CONTAINER, containerByCode.get().getId());
    }

    final ContainerDto savedContainerDto = containerService.save(container);
    return ResponseEntityUtils.created(CONTAINER, savedContainerDto.getId());
  }
}

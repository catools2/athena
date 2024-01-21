package org.catools.athena.rest.kube.service;

import org.catools.athena.kube.model.ContainerStateDto;

import java.util.Optional;
import java.util.Set;

public interface ContainerStateService {
  /**
   * Save record
   */
  ContainerStateDto save(ContainerStateDto record, Long containerId);

  /**
   * Retrieve all record
   */
  Set<ContainerStateDto> getAll();

  /**
   * Retrieve record by id
   */
  Optional<ContainerStateDto> getById(Long id);

  /**
   * Retrieve record by syncTime, type, message, value and container id
   */
  Optional<ContainerStateDto> getState(ContainerStateDto stateDto, Long containerId);

  /**
   * Retrieve all record by container id
   */
  Set<ContainerStateDto> getAllByContainerId(Long containerId);
}

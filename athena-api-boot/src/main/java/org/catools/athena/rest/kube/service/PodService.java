package org.catools.athena.rest.kube.service;


import org.catools.athena.kube.model.PodDto;

import java.util.Optional;
import java.util.Set;

public interface PodService {

  /**
   * Save entity
   */
  PodDto save(PodDto entity);

  /**
   * Retrieve entity by id
   */
  Optional<PodDto> getById(Long id);

  /**
   * Retrieve pods by namespace
   */
  Set<PodDto> getByProjectIdAndNamespace(Long projectId, String namespace);

  /**
   * Retrieve pod by name
   */
  Optional<PodDto> getByNameAndNamespace(String name, String namespace);
}
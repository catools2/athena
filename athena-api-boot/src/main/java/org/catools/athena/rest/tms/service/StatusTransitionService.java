package org.catools.athena.rest.tms.service;


import org.catools.athena.tms.model.StatusTransitionDto;

import java.util.Optional;
import java.util.Set;

public interface StatusTransitionService {

  /**
   * Save entity
   */
  StatusTransitionDto save(StatusTransitionDto entity, String itemCode);

  /**
   * Retrieve all entity related to specific item
   */
  Set<StatusTransitionDto> getAllByItemCode(String itemCode);

  /**
   * Retrieve entity by id
   */
  Optional<StatusTransitionDto> getById(Long id);

  /**
   * Retrieve entity by code
   */
  Optional<StatusTransitionDto> findStatusTransition(StatusTransitionDto entity, String itemCode);

}
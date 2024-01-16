package org.catools.athena.rest.tms.service;


import org.catools.athena.tms.model.StatusTransitionDto;

import java.util.Optional;
import java.util.Set;

public interface StatusTransitionService {

    /**
     * Save record
     */
    StatusTransitionDto save(StatusTransitionDto record, String itemCode);

    /**
     * Retrieve all record
     */
    Set<StatusTransitionDto> getAll();

    /**
     * Retrieve all record related to specific item
     */
    Set<StatusTransitionDto> getAllByItemCode(String itemCode);

    /**
     * Retrieve record by id
     */
    Optional<StatusTransitionDto> getById(Long id);

    /**
     * Retrieve record by code
     */
    Optional<StatusTransitionDto> findStatusTransition(StatusTransitionDto record, String itemCode);

}
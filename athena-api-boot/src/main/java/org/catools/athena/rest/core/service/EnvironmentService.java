package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.EnvironmentDto;

import java.util.Optional;
import java.util.Set;


public interface EnvironmentService {

    /**
     * Returns a list of all available environments.
     */
    Set<EnvironmentDto> getEnvironments();

    /**
     * Get environment by code
     */
    Optional<EnvironmentDto> getEnvironmentByCode(String code);

    /**
     * Get environment by id
     */
    Optional<EnvironmentDto> getEnvironmentById(Long id);

    /**
     * Save environment
     */
    EnvironmentDto saveEnvironment(EnvironmentDto environmentDto);
}
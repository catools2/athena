package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.VersionDto;

import java.util.Optional;
import java.util.Set;


public interface VersionService {

    /**
     * Returns a list of all available versions for specific project.
     */
    Set<VersionDto> getAll(String projectCode);

    /**
     * Get version by code
     */
    Optional<VersionDto> getByCode(String code);

    /**
     * Get version by id
     */
    Optional<VersionDto> getById(Long id);

    /**
     * Save version
     */
    VersionDto save(VersionDto version);
}
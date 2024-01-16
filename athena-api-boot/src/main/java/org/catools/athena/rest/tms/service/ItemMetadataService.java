package org.catools.athena.rest.tms.service;


import org.catools.athena.core.model.MetadataDto;

import java.util.Optional;
import java.util.Set;

public interface ItemMetadataService {
    /**
     * Save record
     */
    MetadataDto save(MetadataDto record);

    /**
     * Retrieve all record
     */
    Set<MetadataDto> getAll();

    /**
     * Retrieve record by id
     */
    Optional<MetadataDto> getById(Long id);

}
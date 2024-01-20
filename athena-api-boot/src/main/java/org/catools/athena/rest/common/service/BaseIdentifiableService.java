package org.catools.athena.rest.common.service;

import java.util.Optional;
import java.util.Set;

public interface BaseIdentifiableService<T> {
    /**
     * Save record
     */
    T save(T record);

    /**
     * Retrieve all record
     */
    Set<T> getAll();

    /**
     * Retrieve record by id
     */
    Optional<T> getById(Long id);
}
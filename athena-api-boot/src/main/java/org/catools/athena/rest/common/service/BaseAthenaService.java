package org.catools.athena.rest.common.service;

import java.util.Optional;
import java.util.Set;

public interface BaseAthenaService<T> {
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

    /**
     * Retrieve record by code
     */
    Optional<T> getByCode(String code);
}
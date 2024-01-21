package org.catools.athena.rest.common.service;

import java.util.Optional;
import java.util.Set;

public interface BaseIdentifiableService<T> {
  /**
   * Save entity
   */
  T save(T entity);

  /**
   * Retrieve all entity
   */
  Set<T> getAll();

  /**
   * Retrieve entity by id
   */
  Optional<T> getById(Long id);
}
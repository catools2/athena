package org.catools.athena.common.service;

import java.util.Optional;

public interface BaseIdentifiableService<T> {
  /**
   * Save entity
   */
  T save(T entity);

  /**
   * Retrieve entity by id
   */
  Optional<T> getById(Long id);
}
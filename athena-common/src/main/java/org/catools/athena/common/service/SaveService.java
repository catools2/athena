package org.catools.athena.common.service;

import java.util.Optional;

public interface SaveService<T> {
  /**
   * Save entity
   */
  T save(T entity);

  /**
   * Retrieve entity by id
   */
  Optional<T> getById(Long id);
}
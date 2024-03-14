package org.catools.athena.common.service;

import java.util.Optional;

public interface SaveOrUpdateService<T> {
  /**
   * Save entity
   */
  T saveOrUpdate(T entity);

  /**
   * Retrieve entity by id
   */
  Optional<T> getById(Long id);
}
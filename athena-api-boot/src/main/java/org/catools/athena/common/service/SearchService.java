package org.catools.athena.common.service;

import java.util.Optional;

public interface SearchService<T> {
  /**
   * Retrieve entity by code
   */
  Optional<T> search(String code);
}
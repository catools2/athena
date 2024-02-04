package org.catools.athena.common.service;

import java.util.Optional;

public interface BaseCodifiedService<T> extends BaseIdentifiableService<T> {
  /**
   * Retrieve entity by code
   */
  Optional<T> getByCode(String code);
}
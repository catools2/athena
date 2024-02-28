package org.catools.athena.tms.common.service;


import jakarta.annotation.Nullable;
import org.catools.athena.tms.model.TestExecutionDto;

import java.util.Optional;
import java.util.Set;

public interface TestExecutionService {
  /**
   * Save entity
   */
  TestExecutionDto save(String cycleCode, TestExecutionDto entity);

  /**
   * Retrieve entity by id
   */
  Optional<TestExecutionDto> getById(Long id);

  /**
   * Retrieve entities by item code and cycle code
   */
  Set<TestExecutionDto> getAll(@Nullable String itemCode, @Nullable String cycleCode);
}
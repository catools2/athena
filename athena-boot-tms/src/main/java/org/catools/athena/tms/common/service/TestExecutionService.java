package org.catools.athena.tms.common.service;


import jakarta.annotation.Nullable;
import org.catools.athena.model.tms.TestExecutionInventoryDto;
import org.catools.athena.model.tms.TestExecutionDto;
import org.catools.athena.model.tms.TestExecutionSummaryDto;
import org.catools.athena.model.tms.TestExecutionTrendPointDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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

    /**
     * Retrieve paged inventory rows for the quality workspace.
     */
    Page<TestExecutionInventoryDto> getAll(
      Pageable pageable,
      @Nullable String project,
      @Nullable String version,
      @Nullable String cycleCode,
      @Nullable String itemCode,
      @Nullable String status,
      @Nullable String progress);

    /**
     * Retrieve summary values for the quality workspace.
     */
    TestExecutionSummaryDto getSummary(
      @Nullable String project,
      @Nullable String version,
      @Nullable String cycleCode,
      @Nullable String itemCode,
      @Nullable String status,
      @Nullable String progress,
      @Nullable Integer windowDays);

    /**
     * Retrieve daily activity buckets for the quality workspace.
     */
    List<TestExecutionTrendPointDto> getTrend(
      @Nullable String project,
      @Nullable String version,
      @Nullable String cycleCode,
      @Nullable String itemCode,
      @Nullable String status,
      @Nullable String progress,
      @Nullable Integer windowDays);
}
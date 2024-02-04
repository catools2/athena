package org.catools.athena.tms.common.service;


import jakarta.annotation.Nullable;
import org.catools.athena.common.service.BaseCodifiedService;
import org.catools.athena.tms.model.TestExecutionDto;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public interface TestExecutionService extends BaseCodifiedService<TestExecutionDto> {
  Optional<TestExecutionDto> getByCreatedOnItemCodeAndCycleCode(Instant createdOn, String itemCode, String cycleCode);

  Set<TestExecutionDto> getAll(@Nullable String itemCode, @Nullable String cycleCode);
}
package org.catools.athena.rest.tms.service;


import jakarta.annotation.Nullable;
import org.catools.athena.rest.common.service.BaseAthenaService;
import org.catools.athena.tms.model.TestExecutionDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface TestExecutionService extends BaseAthenaService<TestExecutionDto> {
    Optional<TestExecutionDto> getByCreatedOnItemCodeAndCycleCode(LocalDateTime createdOn, String itemCode, String cycleCode);

    Set<TestExecutionDto> getAll(@Nullable String itemCode, @Nullable String cycleCode);
}
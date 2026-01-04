package org.catools.athena.tms.common.service;


import org.catools.athena.common.service.SearchableSaveOrUpdateService;
import org.catools.athena.model.tms.TestCycleDto;

import java.util.Optional;

public interface TestCycleService extends SearchableSaveOrUpdateService<TestCycleDto> {

  Optional<TestCycleDto> findLastByPattern(String name, String projectCode, String versionCode);


}
package org.catools.athena.tms.common.service;


import org.catools.athena.common.service.SearchableSaveOrUpdateService;
import org.catools.athena.tms.model.TestCycleDto;

import java.util.Optional;

public interface TestCycleService extends SearchableSaveOrUpdateService<TestCycleDto> {

  Optional<Integer> getUniqueHashByCode(String code);

  Optional<TestCycleDto> findLastByPattern(String name, String versionCode);


}
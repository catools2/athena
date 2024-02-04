package org.catools.athena.tms.common.service;


import org.catools.athena.common.service.BaseCodifiedService;
import org.catools.athena.tms.model.TestCycleDto;

import java.util.Set;

public interface TestCycleService extends BaseCodifiedService<TestCycleDto> {
  Set<TestCycleDto> getByVersionCode(String versionCode);
}
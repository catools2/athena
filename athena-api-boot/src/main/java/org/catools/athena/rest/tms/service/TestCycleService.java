package org.catools.athena.rest.tms.service;


import org.catools.athena.rest.common.service.BaseCodifiedService;
import org.catools.athena.tms.model.TestCycleDto;

import java.util.Set;

public interface TestCycleService extends BaseCodifiedService<TestCycleDto> {
  Set<TestCycleDto> getByVersionCode(String versionCode);
}
package org.catools.athena.tms.common.service;


import org.catools.athena.common.service.BaseCodifiedService;
import org.catools.athena.tms.model.StatusDto;

import java.util.Set;

public interface StatusService extends BaseCodifiedService<StatusDto> {

  Set<StatusDto> getAll();

}
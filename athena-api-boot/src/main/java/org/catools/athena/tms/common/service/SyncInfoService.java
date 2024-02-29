package org.catools.athena.tms.common.service;


import org.catools.athena.common.service.BaseIdentifiableService;
import org.catools.athena.tms.model.SyncInfoDto;

import java.util.Optional;

public interface SyncInfoService extends BaseIdentifiableService<SyncInfoDto> {
  Optional<SyncInfoDto> search(String action, String component, String projectCode);

}
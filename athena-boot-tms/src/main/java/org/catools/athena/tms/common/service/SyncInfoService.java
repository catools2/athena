package org.catools.athena.tms.common.service;


import org.catools.athena.common.service.SaveOrUpdateService;
import org.catools.athena.model.tms.SyncInfoDto;

import java.util.Optional;

public interface SyncInfoService extends SaveOrUpdateService<SyncInfoDto> {
  Optional<SyncInfoDto> search(String action, String component, String projectCode);

}
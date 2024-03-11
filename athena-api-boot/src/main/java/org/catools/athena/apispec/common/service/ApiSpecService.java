package org.catools.athena.apispec.common.service;


import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.common.service.SaveOrUpdateService;

import java.util.Optional;

public interface ApiSpecService extends SaveOrUpdateService<ApiSpecDto> {

  /**
   * get api spec by project code and name
   */
  Optional<ApiSpecDto> getByProjectCodeAndName(String projectCode, String name);

}
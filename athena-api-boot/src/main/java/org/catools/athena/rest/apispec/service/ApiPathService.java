package org.catools.athena.rest.apispec.service;


import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;

public interface ApiPathService extends BaseIdentifiableService<ApiPathDto> {

  Optional<ApiPathDto> search(Long apiSpecId, String method, String url);
}
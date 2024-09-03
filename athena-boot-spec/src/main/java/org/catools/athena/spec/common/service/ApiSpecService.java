package org.catools.athena.spec.common.service;

import org.catools.athena.apispec.model.ApiSpecDto;

import java.util.Optional;

public interface ApiSpecService {
  ApiSpecDto saveOrUpdate(ApiSpecDto entity);

  Optional<ApiSpecDto> getById(Long id);

  Optional<ApiSpecDto> getByProjectCodeAndName(String projectCode, String name);
}

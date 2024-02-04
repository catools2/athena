package org.catools.athena.apispec.common.service;


import org.catools.athena.apispec.model.ApiSpecDto;

import java.util.Optional;

public interface ApiSpecService {

  /**
   * get api spec by id
   */
  Optional<ApiSpecDto> getById(Long id);

  /**
   * get api spec by project code and name
   */
  Optional<ApiSpecDto> getByProjectCodeAndName(String projectCode, String name);

  /**
   * Save api spec
   */
  ApiSpecDto save(ApiSpecDto apiSpecDto);
}
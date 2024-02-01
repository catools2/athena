package org.catools.athena.rest.apispec.service;


import org.catools.athena.apispec.model.ApiSpecDto;

import java.util.Optional;

public interface ApiSpecService {

  /**
   * get api spec by id
   */
  Optional<ApiSpecDto> getApiSpecById(Long id);

  /**
   * get api spec by project code and name
   */
  Optional<ApiSpecDto> getApiSpecByProjectCodeAndName(String projectCode, String name);

  /**
   * Save api spec
   */
  ApiSpecDto save(ApiSpecDto apiSpecDto);
}
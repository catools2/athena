package org.catools.athena.rest.apispec.service;


import com.google.gson.JsonElement;
import org.apache.commons.lang3.tuple.Pair;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;

import java.util.Optional;
import java.util.Set;

public interface ApiSpecService {
  /**
   * Save api spec
   */
  Pair<ApiSpecDto, Set<ApiPathDto>> saveOpenApiSpec(JsonElement openAPISpec, String specName, String projectCode);

  /**
   * Save api spec
   */
  ApiSpecDto saveApiSpec(ApiSpecDto apiSpecDto);

  /**
   * get api spec by id
   */
  Optional<ApiSpecDto> getApiSpecById(Long id);

  /**
   * get api spec by project code and name
   */
  Optional<ApiSpecDto> getApiSpecByProjectCodeAndName(String projectCode, String name);


  /**
   * Save api path
   */
  ApiPathDto saveApiPath(ApiPathDto apiPathDto);
}
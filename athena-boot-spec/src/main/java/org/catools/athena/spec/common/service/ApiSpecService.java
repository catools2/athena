package org.catools.athena.spec.common.service;

import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.model.apispec.ApiSpecDriftDto;
import org.catools.athena.model.apispec.ApiSpecFreshnessDto;
import org.catools.athena.model.apispec.ApiSpecInventoryDto;
import org.catools.athena.model.apispec.ApiSpecSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApiSpecService {
  ApiSpecDto saveOrUpdate(ApiSpecDto entity);

  Page<ApiSpecInventoryDto> getAll(Pageable pageable, String project, String name, String title, String version, String freshness);

  ApiSpecSummaryDto getSummary(String project, String name, String title, String version, String freshness, Integer windowDays);

  ApiSpecFreshnessDto getFreshness(String project, String name, String title, String version, String freshness, Integer windowDays);

  Page<ApiSpecDriftDto> getDrift(Pageable pageable, String project, String name, String title, String version, String freshness);

  Optional<ApiSpecDto> getById(Long id);

  Optional<ApiSpecDto> getByProjectCodeAndName(String projectCode, String name);
}

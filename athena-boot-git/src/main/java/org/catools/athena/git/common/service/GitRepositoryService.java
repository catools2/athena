package org.catools.athena.git.common.service;


import org.catools.athena.common.service.SaveOrUpdateService;
import org.catools.athena.model.git.GitRepositoryDto;
import org.catools.athena.model.git.GitRepositoryInventoryDto;
import org.catools.athena.model.git.GitRepositorySummaryDto;
import org.catools.athena.model.git.GitRepositoryTrendPointDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GitRepositoryService extends SaveOrUpdateService<GitRepositoryDto> {

  /**
   * Retrieve repository where keyword matches by name or Url
   */
  Optional<GitRepositoryDto> findByNameOrUrl(String keyword);

  Page<GitRepositoryInventoryDto> getAll(Pageable pageable, String keyword, String host, String freshness);

  GitRepositorySummaryDto getSummary(String keyword, String host, String freshness, Integer windowDays);

  List<GitRepositoryTrendPointDto> getTrend(String keyword, String host, String freshness, Integer windowDays);

}
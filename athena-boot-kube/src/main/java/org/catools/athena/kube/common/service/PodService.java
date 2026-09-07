package org.catools.athena.kube.common.service;


import org.catools.athena.common.service.SaveOrUpdateService;
import org.catools.athena.model.kube.PodDto;
import org.catools.athena.model.kube.PodInventoryDto;
import org.catools.athena.model.kube.PodSummaryDto;
import org.catools.athena.model.kube.PodTrendPointDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PodService extends SaveOrUpdateService<PodDto> {

  /**
   * Retrieve pods by namespace
   */
  Set<PodDto> getPods(String project, String namespace);

  /**
   * Retrieve pod by name
   */
  Optional<PodDto> getByNameAndNamespace(String name, String namespace);

    Page<PodInventoryDto> getAll(
      Pageable pageable,
      String project,
      String namespace,
      String name,
      String nodeName,
      String status);

    PodSummaryDto getSummary(
      String project,
      String namespace,
      String name,
      String nodeName,
      String status,
      Integer windowDays);

    List<PodTrendPointDto> getTrend(
      String project,
      String namespace,
      String name,
      String nodeName,
      String status,
      Integer windowDays);
}
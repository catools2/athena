package org.catools.athena.pipeline.common.service;


import org.catools.athena.common.service.SaveOrUpdateService;
import org.catools.athena.model.pipeline.PipelineDto;
import org.catools.athena.model.pipeline.PipelineInventoryDto;
import org.catools.athena.model.pipeline.PipelineSummaryDto;
import org.catools.athena.model.pipeline.PipelineTrendPointDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface PipelineService extends SaveOrUpdateService<PipelineDto> {

  /**
   * Update pipeline end date
   */
  PipelineDto updatePipelineEndDate(long pipelineId, Instant enddate);

  /**
   * Get pipeline
   */
  Optional<PipelineDto> getPipeline(String pipelineName, String pipelineNumber, String projectCode, String versionCode, String environmentCode);

    Page<PipelineInventoryDto> getAll(
      Pageable pageable,
      String project,
      String version,
      String environment,
      String name,
      String number,
      String state);

    PipelineSummaryDto getSummary(
      String project,
      String version,
      String environment,
      String name,
      String number,
      String state,
      Integer windowDays);

    List<PipelineTrendPointDto> getTrend(
      String project,
      String version,
      String environment,
      String name,
      String number,
      String state,
      Integer windowDays);

}
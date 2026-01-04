package org.catools.athena.rest.feign.pipeline.cache;

import org.catools.athena.model.pipeline.PipelineExecutionStatusDto;
import org.catools.athena.rest.feign.common.cache.CacheStorage;
import org.catools.athena.rest.feign.pipeline.utils.PipelineUtils;

public class PipelineCache {
  private static final CacheStorage<String, PipelineExecutionStatusDto> EXECUTION_STATUS = new CacheStorage<>("PipelineExecutionStatus",
      PipelineExecutionStatusDto::getName,
      PipelineUtils::getExecutionStatus);

  public static synchronized PipelineExecutionStatusDto readPipelineExecutionStatus(PipelineExecutionStatusDto executionStatus) {
    return EXECUTION_STATUS.read(executionStatus);
  }

}

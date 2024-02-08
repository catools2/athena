package org.catools.athena.pipeline.common.mapper;

import org.catools.athena.pipeline.common.entity.PipelineExecutionStatus;

public interface PipelineMapperService {
  PipelineExecutionStatus getPipelineStatusByName(String name);
}
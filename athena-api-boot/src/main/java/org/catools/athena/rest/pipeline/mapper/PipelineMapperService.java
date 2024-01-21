package org.catools.athena.rest.pipeline.mapper;

import org.catools.athena.rest.pipeline.entity.PipelineExecutionStatus;

public interface PipelineMapperService {
  PipelineExecutionStatus getPipelineStatusByName(String name);
}
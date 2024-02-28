package org.catools.athena.pipeline.common.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.pipeline.common.entity.PipelineExecutionStatus;
import org.catools.athena.pipeline.common.repository.PipelineExecutionStatusRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PipelineMapperServiceImpl implements PipelineMapperService {

  private final PipelineExecutionStatusRepository pipelineExecutionStatusRepository;

  @Override
  public PipelineExecutionStatus getPipelineStatusByName(String name) {
    return pipelineExecutionStatusRepository.findByName(name).orElse(null);
  }
}

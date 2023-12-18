package org.catools.athena.rest.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.entity.core.Environment;
import org.catools.athena.rest.entity.core.Project;
import org.catools.athena.rest.entity.core.User;
import org.catools.athena.rest.entity.pipeline.Pipeline;
import org.catools.athena.rest.entity.pipeline.PipelineExecutionStatus;
import org.catools.athena.rest.repository.core.EnvironmentRepository;
import org.catools.athena.rest.repository.core.ProjectRepository;
import org.catools.athena.rest.repository.core.UserRepository;
import org.catools.athena.rest.repository.pipeline.PipelineExecutionStatusRepository;
import org.catools.athena.rest.repository.pipeline.PipelineRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PipelineMapperServiceImpl implements PipelineMapperService {
  private final PipelineRepository pipelineRepository;
  private final EnvironmentRepository environmentRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final PipelineExecutionStatusRepository pipelineExecutionStatusRepository;

  @SuppressWarnings("notused")
  @Override
  public Project getProjectByCode(String code) {
    return projectRepository.findByCode(code).orElse(null);
  }

  @Override
  public Environment getEnvironmentByCode(String code) {
    return environmentRepository.findByCode(code).orElse(null);
  }

  @Override
  public Pipeline getPipelineById(Long pipelineId) {
    return pipelineRepository.findById(pipelineId).orElse(null);
  }

  @Override
  public User getUserByName(String name) {
    return userRepository.findByName(name).orElse(null);
  }

  @Override
  public PipelineExecutionStatus getPipelineStatusByName(String name) {
    return pipelineExecutionStatusRepository.findByName(name).orElse(null);
  }
}

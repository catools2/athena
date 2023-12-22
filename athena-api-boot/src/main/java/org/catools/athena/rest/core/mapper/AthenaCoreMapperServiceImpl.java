package org.catools.athena.rest.core.mapper;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.repository.EnvironmentRepository;
import org.catools.athena.rest.core.repository.ProjectRepository;
import org.catools.athena.rest.core.repository.UserRepository;
import org.catools.athena.rest.pipeline.entity.Pipeline;
import org.catools.athena.rest.pipeline.repository.PipelineRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AthenaCoreMapperServiceImpl implements AthenaCoreMapperService {
  private final PipelineRepository pipelineRepository;
  private final EnvironmentRepository environmentRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

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
}

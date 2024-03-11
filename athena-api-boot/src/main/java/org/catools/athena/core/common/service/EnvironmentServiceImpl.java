package org.catools.athena.core.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtil;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.EnvironmentRepository;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.model.EnvironmentDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnvironmentServiceImpl implements EnvironmentService {

  private final ProjectRepository projectRepository;
  private final EnvironmentRepository environmentRepository;

  // Mappers
  private final CoreMapper coreMapper;

  @Override
  public Optional<EnvironmentDto> search(final String keyword) {
    final Optional<Environment> environment = environmentRepository.findByCodeOrName(keyword, keyword);
    return environment.map(coreMapper::environmentToEnvironmentDto);
  }

  @Override
  public Optional<EnvironmentDto> getById(final Long id) {
    final Optional<Environment> environment = environmentRepository.findById(id);
    return environment.map(coreMapper::environmentToEnvironmentDto);
  }

  @Override
  public EnvironmentDto saveOrUpdate(final EnvironmentDto entity) {
    log.debug("Saving entity: {}", entity);
    final Environment environmentToSave = environmentRepository.findByCodeOrName(entity.getCode(), entity.getName())
        .map(env -> {
          env.setName(entity.getName());
          env.setProject(projectRepository.findByCode(entity.getProject()).orElse(null));
          return env;
        })
        .orElseGet(() -> coreMapper.environmentDtoToEnvironment(entity));

    final Environment savedEnvironment = RetryUtil.retry(3, 1000, integer -> environmentRepository.saveAndFlush(environmentToSave));
    return coreMapper.environmentToEnvironmentDto(savedEnvironment);
  }
}
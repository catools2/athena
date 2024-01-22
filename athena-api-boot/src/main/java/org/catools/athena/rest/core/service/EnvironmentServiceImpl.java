package org.catools.athena.rest.core.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.EnvironmentDto;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.mapper.CoreMapper;
import org.catools.athena.rest.core.repository.EnvironmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnvironmentServiceImpl implements EnvironmentService {

  private final EnvironmentRepository environmentRepository;

  // Mappers
  private final CoreMapper coreMapper;

  @Override
  public Set<EnvironmentDto> getAll() {
    final List<Environment> environments = environmentRepository.findAll();
    return environments.stream().map(coreMapper::environmentToEnvironmentDto).collect(Collectors.toSet());
  }

  @Override
  public Optional<EnvironmentDto> getByCode(final String code) {
    final Optional<Environment> environment = environmentRepository.findByCode(code);
    return environment.map(coreMapper::environmentToEnvironmentDto);
  }

  @Override
  public Optional<EnvironmentDto> getById(final Long id) {
    final Optional<Environment> environment = environmentRepository.findById(id);
    return environment.map(coreMapper::environmentToEnvironmentDto);
  }

  @Override
  public EnvironmentDto save(final EnvironmentDto environmentDto) {
    final Environment environmentToSave = coreMapper.environmentDtoToEnvironment(environmentDto);
    final Environment savedEnvironment = environmentRepository.saveAndFlush(environmentToSave);
    return coreMapper.environmentToEnvironmentDto(savedEnvironment);
  }
}
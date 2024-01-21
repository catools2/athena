package org.catools.athena.rest.tms.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.tms.entity.Priority;
import org.catools.athena.rest.tms.mapper.TmsMapper;
import org.catools.athena.rest.tms.repository.PriorityRepository;
import org.catools.athena.tms.model.PriorityDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriorityServiceImpl implements PriorityService {
  private final PriorityRepository priorityRepository;
  private final TmsMapper tmsMapper;

  @Override
  public PriorityDto save(PriorityDto priority) {
    final Priority recordToSave = tmsMapper.priorityDtoToPriority(priority);
    final Priority savedRecord = priorityRepository.saveAndFlush(recordToSave);
    return tmsMapper.priorityToPriorityDto(savedRecord);
  }

  @Override
  public Set<PriorityDto> getAll() {
    return priorityRepository.findAll().stream().map(tmsMapper::priorityToPriorityDto).collect(Collectors.toSet());
  }

  @Override
  public Optional<PriorityDto> getById(Long id) {
    return priorityRepository.findById(id).map(tmsMapper::priorityToPriorityDto);
  }

  @Override
  public Optional<PriorityDto> getByCode(String code) {
    return priorityRepository.findByCode(code).map(tmsMapper::priorityToPriorityDto);
  }
}

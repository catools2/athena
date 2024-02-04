package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.PriorityRepository;
import org.catools.athena.tms.model.PriorityDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriorityServiceImpl implements PriorityService {
  private final PriorityRepository priorityRepository;
  private final TmsMapper tmsMapper;

  @Override
  public PriorityDto save(PriorityDto priority) {
    final Priority entityToSave = tmsMapper.priorityDtoToPriority(priority);
    final Priority savedRecord = priorityRepository.saveAndFlush(entityToSave);
    return tmsMapper.priorityToPriorityDto(savedRecord);
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

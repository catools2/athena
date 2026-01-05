package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.model.tms.PriorityDto;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.PriorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriorityServiceImpl implements PriorityService {
  private final PriorityRepository priorityRepository;
  private final TmsMapper tmsMapper;

  @Override
  @Transactional
  public PriorityDto saveOrUpdate(PriorityDto entity) {
    log.debug("Saving entity: {}", entity);
    final Priority entityToSave = priorityRepository.findByCodeOrName(entity.getCode(), entity.getName()).map(s -> {
      s.setName(entity.getName());
      return s;
    }).orElseGet(() -> tmsMapper.priorityDtoToPriority(entity));

    final Priority savedRecord = RetryUtils.retry(3, 1000, integer -> priorityRepository.saveAndFlush(entityToSave));
    return tmsMapper.priorityToPriorityDto(savedRecord);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PriorityDto> getById(Long id) {
    return priorityRepository.findById(id).map(tmsMapper::priorityToPriorityDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<PriorityDto> search(String keyword) {
    return priorityRepository.findByCodeOrName(keyword, keyword).map(tmsMapper::priorityToPriorityDto);
  }
}

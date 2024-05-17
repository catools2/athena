package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.StatusRepository;
import org.catools.athena.tms.model.StatusDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
  private final StatusRepository statusRepository;
  private final TmsMapper tmsMapper;

  @Override
  public StatusDto saveOrUpdate(StatusDto entity) {
    log.debug("Saving entity: {}", entity);
    final Status entityToSave = statusRepository.findByCodeOrName(entity.getCode(), entity.getName()).map(s -> {
      s.setCode(entity.getCode());
      s.setName(entity.getName());
      return s;
    }).orElseGet(() -> tmsMapper.statusDtoToStatus(entity));

    final Status savedRecord = RetryUtils.retry(3, 1000, integer -> statusRepository.saveAndFlush(entityToSave));
    return tmsMapper.statusToStatusDto(savedRecord);
  }

  @Override
  public Optional<StatusDto> getById(Long id) {
    return statusRepository.findById(id).map(tmsMapper::statusToStatusDto);
  }

  @Override
  public Optional<StatusDto> search(String keyword) {
    return statusRepository.findByCodeOrName(keyword, keyword).map(tmsMapper::statusToStatusDto);
  }

}

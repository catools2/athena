package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    final Status entityToSave = statusRepository.findByCode(entity.getCode()).map(s -> {
      s.setName(entity.getName());
      return s;
    }).orElseGet(() -> tmsMapper.statusDtoToStatus(entity));

    final Status savedRecord = statusRepository.saveAndFlush(entityToSave);
    return tmsMapper.statusToStatusDto(savedRecord);
  }

  @Override
  public Optional<StatusDto> getById(Long id) {
    return statusRepository.findById(id).map(tmsMapper::statusToStatusDto);
  }

  @Override
  public Optional<StatusDto> getByCode(String code) {
    return statusRepository.findByCode(code).map(tmsMapper::statusToStatusDto);
  }

}

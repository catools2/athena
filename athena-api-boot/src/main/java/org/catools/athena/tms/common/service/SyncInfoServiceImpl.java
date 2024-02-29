package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.tms.common.entity.SyncInfo;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.SyncInfoRepository;
import org.catools.athena.tms.model.SyncInfoDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncInfoServiceImpl implements SyncInfoService {
  private final SyncInfoRepository syncInfoRepository;
  private final TmsMapper tmsMapper;

  @Override
  public SyncInfoDto saveOrUpdate(SyncInfoDto entity) {
    log.debug("Saving entity: {}", entity);
    final SyncInfo syncInfo = tmsMapper.syncInfoDtoToSyncInfo(entity);

    final SyncInfo entityToSave = syncInfoRepository.findByActionAndComponentAndProjectId(syncInfo.getAction(), syncInfo.getComponent(), syncInfo.getProject().getId()).map(c -> {
      c.setAction(syncInfo.getAction());
      c.setComponent(syncInfo.getComponent());
      c.setStartTime(syncInfo.getStartTime());
      c.setEndTime(syncInfo.getEndTime());
      c.setProject(syncInfo.getProject());
      return c;
    }).orElse(syncInfo);

    final SyncInfo savedRecord = syncInfoRepository.saveAndFlush(entityToSave);
    return tmsMapper.syncInfoToSyncInfoDto(savedRecord);
  }

  @Override
  public Optional<SyncInfoDto> getById(Long id) {
    return syncInfoRepository.findById(id).map(tmsMapper::syncInfoToSyncInfoDto);
  }
}

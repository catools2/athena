package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.model.tms.SyncInfoDto;
import org.catools.athena.tms.common.entity.SyncInfo;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.mapper.TmsMapperService;
import org.catools.athena.tms.common.repository.SyncInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncInfoServiceImpl implements SyncInfoService {
  private final SyncInfoRepository syncInfoRepository;
  private final TmsMapperService tmsMapperService;
  private final TmsMapper tmsMapper;

  @Override
  @Transactional
  public SyncInfoDto saveOrUpdate(SyncInfoDto entity) {
    log.debug("Saving entity: {}", entity);
    final SyncInfo syncInfo = tmsMapper.syncInfoDtoToSyncInfo(entity);

    final SyncInfo entityToSave = syncInfoRepository.findByActionAndComponentAndProjectId(syncInfo.getAction(), syncInfo.getComponent(), syncInfo.getProjectId()).map(c -> {
      c.setAction(syncInfo.getAction());
      c.setComponent(syncInfo.getComponent());
      c.setStartTime(syncInfo.getStartTime());
      c.setEndTime(syncInfo.getEndTime());
      c.setProjectId(syncInfo.getProjectId());
      return c;
    }).orElse(syncInfo);

    final SyncInfo savedRecord = RetryUtils.retry(3, 1000, integer -> syncInfoRepository.saveAndFlush(entityToSave));
    return tmsMapper.syncInfoToSyncInfoDto(savedRecord);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SyncInfoDto> getById(Long id) {
    return syncInfoRepository.findById(id).map(tmsMapper::syncInfoToSyncInfoDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SyncInfoDto> search(String action, String component, String projectCode) {
    Long projectId = tmsMapperService.getProjectId(projectCode);
    return syncInfoRepository.findByActionAndComponentAndProjectId(action, component, projectId).map(tmsMapper::syncInfoToSyncInfoDto);
  }
}

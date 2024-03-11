package org.catools.athena.core.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtil;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.common.repository.VersionRepository;
import org.catools.athena.core.model.VersionDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {

  private final ProjectRepository projectRepository;
  private final VersionRepository versionRepository;

  private final CoreMapper coreMapper;

  @Override
  public Optional<VersionDto> search(String keyword) {
    final Optional<AppVersion> version = versionRepository.findByCode(keyword);
    return version.map(coreMapper::versionToVersionDto);
  }

  @Override
  public Optional<VersionDto> getById(Long id) {
    final Optional<AppVersion> version = versionRepository.findById(id);
    return version.map(coreMapper::versionToVersionDto);
  }

  @Override
  public VersionDto saveOrUpdate(VersionDto entity) {
    log.debug("Saving entity: {}", entity);
    final AppVersion appVersionToSave = versionRepository.findByCode(entity.getCode()).map(ver -> {
      ver.setName(entity.getName());
      ver.setProject(projectRepository.findByCode(entity.getProject()).orElse(null));
      return ver;
    }).orElseGet(() -> coreMapper.versionDtoToVersion(entity));

    final AppVersion savedAppVersion = RetryUtil.retry(3, 1000, integer -> versionRepository.saveAndFlush(appVersionToSave));
    return coreMapper.versionToVersionDto(savedAppVersion);
  }
}

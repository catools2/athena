package org.catools.athena.core.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.common.entity.Version;
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
  public Optional<VersionDto> getByCode(String code) {
    final Optional<Version> version = versionRepository.findByCode(code);
    return version.map(coreMapper::versionToVersionDto);
  }

  @Override
  public Optional<VersionDto> getById(Long id) {
    final Optional<Version> version = versionRepository.findById(id);
    return version.map(coreMapper::versionToVersionDto);
  }

  @Override
  public VersionDto saveOrUpdate(VersionDto entity) {
    log.debug("Saving entity: {}", entity);
    final Version versionToSave = versionRepository.findByCode(entity.getCode()).map(ver -> {
      ver.setName(entity.getName());
      ver.setProject(projectRepository.findByCode(entity.getProject()).orElse(null));
      return ver;
    }).orElseGet(() -> coreMapper.versionDtoToVersion(entity));

    final Version savedVersion = versionRepository.saveAndFlush(versionToSave);
    return coreMapper.versionToVersionDto(savedVersion);
  }
}

package org.catools.athena.core.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.common.entity.Version;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.common.repository.VersionRepository;
import org.catools.athena.core.model.VersionDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
  public VersionDto saveOrUpdate(VersionDto version) {
    final Version versionToSave = versionRepository.findByCode(version.getCode()).map(ver -> {
      ver.setName(version.getName());
      ver.setProject(projectRepository.findByCode(version.getProject()).orElse(null));
      return ver;
    }).orElseGet(() -> coreMapper.versionDtoToVersion(version));

    final Version savedVersion = versionRepository.saveAndFlush(versionToSave);
    return coreMapper.versionToVersionDto(savedVersion);
  }
}

package org.catools.athena.rest.core.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.VersionDto;
import org.catools.athena.rest.core.entity.Version;
import org.catools.athena.rest.core.mapper.CoreMapper;
import org.catools.athena.rest.core.repository.VersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {

    private final VersionRepository versionRepository;

    private final CoreMapper coreMapper;

    @Override
    @Transactional(readOnly = true)
    public Set<VersionDto> getVersions(String projectCode) {
        final Set<Version> versions = versionRepository.findAllByProjectCode(projectCode);
        return versions.stream().map(coreMapper::versionToVersionDto).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VersionDto> getVersionByCode(String code) {
        final Optional<Version> version = versionRepository.findByCode(code);
        return version.map(coreMapper::versionToVersionDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VersionDto> getVersionById(Long id) {
        final Optional<Version> version = versionRepository.findById(id);
        return version.map(coreMapper::versionToVersionDto);
    }

    @Override
    @Transactional
    public VersionDto save(VersionDto version) {
        final Version versionToSave = coreMapper.versionDtoToVersion(version);
        final Version savedVersion = versionRepository.saveAndFlush(versionToSave);
        return coreMapper.versionToVersionDto(savedVersion);
    }
}

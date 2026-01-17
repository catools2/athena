package org.catools.athena.core.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.AppVersionRepository;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.common.repository.builders.VersionDynamicQueryBuilder;
import org.catools.athena.core.entity.VersionFilterDto;
import org.catools.athena.model.core.VersionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {

  private final ProjectRepository projectRepository;
  private final AppVersionRepository appVersionRepository;
  private final CoreMapper coreMapper;
  private final EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public Page<VersionDto> getAll(final Pageable pageable) {
    log.debug("Getting all versions with pagination: {}", pageable);
    return appVersionRepository.findAll(pageable).map(coreMapper::versionToVersionDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<VersionDto> search(String projectCode, String keyword) {
    final Optional<AppVersion> version = appVersionRepository.findByProjectIdAndCodeOrName(projectCode, keyword, keyword);
    return version.map(coreMapper::versionToVersionDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<VersionDto> getById(Long id) {
    final Optional<AppVersion> version = appVersionRepository.findById(id);
    return version.map(coreMapper::versionToVersionDto);
  }

  @Override
  @Transactional
  public VersionDto save(VersionDto entity) {
    log.debug("Saving entity: {}", entity);
    final AppVersion appVersionToSave = coreMapper.versionDtoToVersion(entity);
    final AppVersion savedAppVersion = appVersionRepository.saveAndFlush(appVersionToSave);
    return coreMapper.versionToVersionDto(savedAppVersion);
  }

  @Override
  @Transactional
  public VersionDto update(final VersionDto entity) {
    log.debug("Saving entity: {}", entity);
    Project project = projectRepository.findByCode(entity.getProject()).orElseThrow(() -> new EntityNotFoundException("project", entity.getProject()));
    final AppVersion appVersionToSave = appVersionRepository.findById(entity.getId()).map(ver -> {
          ver.setName(entity.getName());
          ver.setProject(project);
          return ver;
        })
        .orElseThrow(() -> new RecordNotFoundException("version", "id", entity.getId()));

    final AppVersion savedAppVersion = appVersionRepository.saveAndFlush(appVersionToSave);
    return coreMapper.versionToVersionDto(savedAppVersion);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<VersionDto> getAll(final Pageable pageable, final VersionFilterDto filters) {
    log.debug("Getting all versions with pagination and filters: {}", filters);

    // Use dynamic query builder with sorting
    VersionDynamicQueryBuilder queryBuilder = new VersionDynamicQueryBuilder(filters);
    String jpqlQuery = queryBuilder.buildQueryWithSort(pageable);

    TypedQuery<AppVersion> query = entityManager.createQuery(jpqlQuery, AppVersion.class);

    String countQuery = queryBuilder.buildCountQuery();
    Long total = entityManager.createQuery(countQuery, Long.class).getSingleResult();

    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());

    List<AppVersion> items = query.getResultList();
    List<VersionDto> body = items.stream().map(coreMapper::versionToVersionDto).toList();
    return new PageImpl<>(body, pageable, total);
  }
}

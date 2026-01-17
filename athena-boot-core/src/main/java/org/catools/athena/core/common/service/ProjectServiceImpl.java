package org.catools.athena.core.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.common.repository.builders.ProjectDynamicQueryBuilder;
import org.catools.athena.core.entity.ProjectFilterDto;
import org.catools.athena.model.core.ProjectDto;
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
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final CoreMapper coreMapper;
  private final EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public Page<ProjectDto> getAll(final Pageable pageable) {
    log.debug("Getting all projects with pagination: {}", pageable);
    return projectRepository.findAll(pageable).map(coreMapper::projectToProjectDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProjectDto> getAll(final Pageable pageable, final ProjectFilterDto filters) {
    log.debug("Getting all projects with pagination and filters: {}", filters);

    // Use dynamic query builder with sorting
    ProjectDynamicQueryBuilder queryBuilder = new ProjectDynamicQueryBuilder(filters);
    String jpqlQuery = queryBuilder.buildQueryWithSort(pageable);

    TypedQuery<Project> query = entityManager.createQuery(jpqlQuery, Project.class);

    String countQuery = queryBuilder.buildCountQuery();
    Long total = entityManager.createQuery(countQuery, Long.class).getSingleResult();

    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());

    List<Project> items = query.getResultList();
    List<ProjectDto> body = items.stream().map(coreMapper::projectToProjectDto).toList();
    return new PageImpl<>(body, pageable, total);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ProjectDto> search(final String keyword) {
    final Optional<Project> project = projectRepository.findByCodeOrName(keyword, keyword);
    return project.map(coreMapper::projectToProjectDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ProjectDto> getById(final Long id) {
    final Optional<Project> project = projectRepository.findById(id);
    return project.map(coreMapper::projectToProjectDto);
  }

  @Override
  @Transactional
  public ProjectDto save(final ProjectDto entity) {
    log.debug("Saving entity: {}", entity);
    final Project projectToSave = coreMapper.projectDtoToProject(entity);
    final Project savedProject = projectRepository.saveAndFlush(projectToSave);
    return coreMapper.projectToProjectDto(savedProject);
  }

  @Override
  @Transactional
  public ProjectDto update(final ProjectDto entity) {
    log.debug("Updating entity: {}", entity);
    final Project projectToSave = projectRepository.findById(entity.getId())
        .map(p -> {
          p.setCode(entity.getCode());
          p.setName(entity.getName());
          return p;
        })
        .orElseThrow(() -> new RecordNotFoundException("project", "id", entity.getId()));

    final Project savedProject = projectRepository.saveAndFlush(projectToSave);
    return coreMapper.projectToProjectDto(savedProject);
  }
}

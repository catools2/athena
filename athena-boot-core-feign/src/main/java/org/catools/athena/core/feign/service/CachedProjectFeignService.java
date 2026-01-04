package org.catools.athena.core.feign.service;

import feign.TypedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.model.core.ProjectDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Cached wrapper service for ProjectFeignClient.
 * <p>
 * Purpose: Provides caching layer for project lookups to reduce network calls.
 * Spring's @Cacheable does not work on Feign interface methods, so we wrap the client
 * in a Spring service bean where AOP caching proxies work correctly.
 * <p>
 * Cache Strategy:
 * - project-by-keyword: Caches search results by code/name
 * - project-by-id: Caches project lookups by ID
 * - TTL: 30 minutes (configured in application.yml)
 * - Max Size: 10,000 entries per cache
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CachedProjectFeignService {

  private final ProjectFeignClient projectFeignClient;

  /**
   * Search for project by keyword (code or name) with caching.
   *
   * @param keyword project code or name to search for
   * @return TypedResponse containing ProjectDto if found
   */
  @Cacheable(value = "project-by-keyword", key = "#keyword.toLowerCase()",
      condition = "#keyword != null", unless = "#result == null || #result.body() == null")
  public TypedResponse<ProjectDto> search(String keyword) {
    log.debug("Cache miss - fetching project by keyword: {}", keyword);
    return projectFeignClient.search(keyword);
  }

  /**
   * Get project by ID with caching.
   *
   * @param id project ID to fetch
   * @return TypedResponse containing ProjectDto if found
   */
  @Cacheable(value = "project-by-id", key = "#id",
      condition = "#id != null", unless = "#result == null || #result.body() == null")
  public TypedResponse<ProjectDto> getById(Long id) {
    log.debug("Cache miss - fetching project by ID: {}", id);
    return projectFeignClient.getById(id);
  }

  /**
   * Save project - delegates to Feign client without caching.
   *
   * @param project ProjectDto to save
   * @return TypedResponse with save result
   */
  public TypedResponse<Void> save(ProjectDto project) {
    return projectFeignClient.save(project);
  }

  /**
   * Update project - delegates to Feign client without caching.
   *
   * @param project ProjectDto to update
   * @return TypedResponse with update result
   */
  public TypedResponse<Void> update(ProjectDto project) {
    return projectFeignClient.update(project);
  }
}


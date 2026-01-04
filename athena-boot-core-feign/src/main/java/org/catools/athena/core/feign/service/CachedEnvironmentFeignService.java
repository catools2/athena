package org.catools.athena.core.feign.service;

import feign.TypedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.feign.EnvironmentFeignClient;
import org.catools.athena.model.core.EnvironmentDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Cached wrapper service for EnvironmentFeignClient.
 * <p>
 * Purpose: Provides caching layer for environment lookups to reduce network calls.
 * Spring's @Cacheable does not work on Feign interface methods, so we wrap the client
 * in a Spring service bean where AOP caching proxies work correctly.
 * <p>
 * Cache Strategy:
 * - environment-by-project-keyword: Caches search results by project + keyword
 * - environment-by-id: Caches environment lookups by ID
 * - TTL: 30 minutes (configured in application.yml)
 * - Max Size: 10,000 entries per cache
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CachedEnvironmentFeignService {

  private final EnvironmentFeignClient environmentFeignClient;

  /**
   * Search for environment by project and keyword with caching.
   *
   * @param project project code
   * @param keyword environment code or name to search for
   * @return TypedResponse containing EnvironmentDto if found
   */
  @Cacheable(value = "environment-by-project-keyword",
      key = "#project.toLowerCase() + '-' + #keyword.toLowerCase()",
      condition = "#project != null && #keyword != null",
      unless = "#result == null || #result.body() == null")
  public TypedResponse<EnvironmentDto> search(String project, String keyword) {
    log.debug("Cache miss - fetching environment by project: {} keyword: {}", project, keyword);
    return environmentFeignClient.search(project, keyword);
  }

  /**
   * Get environment by ID with caching.
   *
   * @param id environment ID to fetch
   * @return TypedResponse containing EnvironmentDto if found
   */
  @Cacheable(value = "environment-by-id", key = "#id",
      condition = "#id != null", unless = "#result == null || #result.body() == null")
  public TypedResponse<EnvironmentDto> getById(Long id) {
    log.debug("Cache miss - fetching environment by ID: {}", id);
    return environmentFeignClient.getById(id);
  }

  /**
   * Save environment - delegates to Feign client without caching.
   *
   * @param environment EnvironmentDto to save
   * @return TypedResponse with save result
   */
  public TypedResponse<Void> save(EnvironmentDto environment) {
    return environmentFeignClient.save(environment);
  }

  /**
   * Update environment - delegates to Feign client without caching.
   *
   * @param environment EnvironmentDto to update
   * @return TypedResponse with update result
   */
  public TypedResponse<Void> update(EnvironmentDto environment) {
    return environmentFeignClient.update(environment);
  }
}


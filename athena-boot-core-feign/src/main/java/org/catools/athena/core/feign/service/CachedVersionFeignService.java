package org.catools.athena.core.feign.service;

import feign.TypedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.feign.VersionFeignClient;
import org.catools.athena.model.core.VersionDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Cached wrapper service for VersionFeignClient.
 * <p>
 * Purpose: Provides caching layer for version lookups to reduce network calls.
 * Spring's @Cacheable does not work on Feign interface methods, so we wrap the client
 * in a Spring service bean where AOP caching proxies work correctly.
 * <p>
 * Cache Strategy:
 * - version-by-project-keyword: Caches search results by project + keyword
 * - version-by-id: Caches version lookups by ID
 * - TTL: 30 minutes (configured in application.yml)
 * - Max Size: 10,000 entries per cache
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CachedVersionFeignService {

  private final VersionFeignClient versionFeignClient;

  /**
   * Search for version by project and keyword with caching.
   *
   * @param project project code
   * @param keyword version code or name to search for
   * @return TypedResponse containing VersionDto if found
   */
  @Cacheable(value = "version-by-project-keyword",
      key = "#project + '-' + #keyword",
      condition = "#project != null && #keyword != null",
      unless = "#result == null || #result.body() == null")
  public TypedResponse<VersionDto> search(String project, String keyword) {
    log.debug("Cache miss - fetching version by project: {} keyword: {}", project, keyword);
    return versionFeignClient.search(project, keyword);
  }

  /**
   * Get version by ID with caching.
   *
   * @param id version ID to fetch
   * @return TypedResponse containing VersionDto if found
   */
  @Cacheable(value = "version-by-id", key = "#id",
      condition = "#id != null", unless = "#result == null || #result.body() == null")
  public TypedResponse<VersionDto> getById(Long id) {
    log.debug("Cache miss - fetching version by ID: {}", id);
    return versionFeignClient.getById(id);
  }

  /**
   * Save version - delegates to Feign client without caching.
   *
   * @param version VersionDto to save
   * @return TypedResponse with save result
   */
  public TypedResponse<Void> save(VersionDto version) {
    return versionFeignClient.save(version);
  }

  /**
   * Update version - delegates to Feign client without caching.
   *
   * @param version VersionDto to update
   * @return TypedResponse with update result
   */
  public TypedResponse<Void> update(VersionDto version) {
    return versionFeignClient.update(version);
  }
}


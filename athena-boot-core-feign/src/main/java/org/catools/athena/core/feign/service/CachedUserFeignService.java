package org.catools.athena.core.feign.service;

import feign.TypedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.feign.UserFeignClient;
import org.catools.athena.model.core.UserDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Cached wrapper service for UserFeignClient.
 * <p>
 * Purpose: Provides caching layer for user lookups to reduce network calls.
 * Spring's @Cacheable does not work on Feign interface methods, so we wrap the client
 * in a Spring service bean where AOP caching proxies work correctly.
 * <p>
 * Cache Strategy:
 * - user-by-keyword: Caches search results by username/alias
 * - user-by-id: Caches user lookups by ID
 * - TTL: 30 minutes (configured in application.yml)
 * - Max Size: 10,000 entries per cache
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CachedUserFeignService {

  private final UserFeignClient userFeignClient;

  /**
   * Search for user by keyword (username or alias) with caching.
   *
   * @param keyword username or alias to search for
   * @return TypedResponse containing UserDto if found
   */
  @Cacheable(value = "user-by-keyword", key = "#keyword.toLowerCase()",
      condition = "#keyword != null", unless = "#result == null || #result.body() == null")
  public TypedResponse<UserDto> search(String keyword) {
    log.debug("Cache miss - fetching user by keyword: {}", keyword);
    return userFeignClient.search(keyword);
  }

  /**
   * Get user by ID with caching.
   *
   * @param id user ID to fetch
   * @return TypedResponse containing UserDto if found
   */
  @Cacheable(value = "user-by-id", key = "#id",
      condition = "#id != null", unless = "#result == null || #result.body() == null")
  public TypedResponse<UserDto> getById(Long id) {
    log.debug("Cache miss - fetching user by ID: {}", id);
    return userFeignClient.getById(id);
  }

  /**
   * Save user - delegates to Feign client without caching.
   *
   * @param user UserDto to save
   * @return TypedResponse with save result
   */
  public TypedResponse<Void> save(UserDto user) {
    return userFeignClient.save(user);
  }

  /**
   * Update user - delegates to Feign client without caching.
   *
   * @param user UserDto to update
   * @return TypedResponse with update result
   */
  public TypedResponse<Void> update(UserDto user) {
    return userFeignClient.update(user);
  }
}


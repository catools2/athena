package org.catools.athena.core.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

/**
 * Provides a lightweight CacheManager for tests to satisfy @EnableCaching.
 */
@TestConfiguration
public class TestCacheConfig {

  @Bean
  public CacheManager cacheManager() {
    // Simple in-memory cache manager suitable for unit and slice tests
    return new ConcurrentMapCacheManager(
        "users", "projects", "environments", "versions",
        "user-by-username", "user-by-alias",
        "project-by-keyword", "environment-by-keyword", "version-by-keyword"
    );
  }
}


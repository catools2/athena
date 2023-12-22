package org.catools.athena.rest.core.config;

import org.springframework.http.CacheControl;

import java.util.concurrent.TimeUnit;

public class AthenaCoreConstant {
  /**
   * Common root for every 'athena' resource.
   */
  public static final String ROOT = "/athena";

  /**
   * Common api root for every 'athena' resource.
   */
  public static final String ROOT_API = ROOT + "/athena/api";

  public static final CacheControl CACHE_MAX_AGE_ONE_HOUR = CacheControl.maxAge(1L, TimeUnit.HOURS);

  /**
   * Common schema for every 'athena' model.
   */
  public static final String ATHENA_SCHEMA = "athena";
}
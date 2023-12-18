package org.catools.athena.rest.controller;

import lombok.experimental.UtilityClass;

/**
 * A list of 'quality center' related resources.
 */
@UtilityClass
public final class AthenaApiConstants {

  /**
   * Common root for every 'quality center' resource.
   */
  public static final String ROOT = "/api/athena";
  public static final String PIPELINE = "/pipeline";
  public static final String PROJECT = "/project";
  public static final String PROJECTS = "/projects";
  public static final String ENVIRONMENT = "/environment";
  public static final String ENVIRONMENTS = "/environments";
  public static final String USER = "/user";
  public static final String USERS = "/users";
  public static final String EXECUTION = "/execution";
  public static final String SCENARIO = "/scenario";
}
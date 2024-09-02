package org.catools.athena.core.common.mapper;

import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;

import java.util.Set;

public interface CoreMapperService {

  /**
   * Get project by code
   */
  Project getProject(String code);

  /**
   * Get environment by code
   */
  Environment getEnvironment(String code);

  /**
   * Get user by username or alias
   */
  User search(String keyword);

  /**
   * Get version by code
   */
  AppVersion getVersion(String name);

  /**
   * Get versions by codes
   */
  Set<String> getVersionCodesFromVersions(Set<AppVersion> appVersions);
}
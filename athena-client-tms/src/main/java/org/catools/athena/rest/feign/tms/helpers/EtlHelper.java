package org.catools.athena.rest.feign.tms.helpers;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.core.UserDto;
import org.catools.athena.model.core.VersionDto;
import org.catools.athena.model.tms.ItemTypeDto;
import org.catools.athena.model.tms.PriorityDto;
import org.catools.athena.model.tms.StatusDto;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.tms.cache.TmsCache;

import static org.catools.athena.rest.feign.common.utils.EtlUtils.generateCode;

@Slf4j
@UtilityClass
public class EtlHelper {
  private static final String UNSET = "UNSET";

  public static String getUser(final String username) {
    if (username == null) {
      return null;
    }
    return CoreCache.readUser(new UserDto(username)).getUsername();
  }

  public static String getVersion(final String version, final String projectCode) {
    return StringUtils.isBlank(version) ?
        CoreCache.readVersion(new VersionDto(UNSET, UNSET, projectCode)).getCode() :
        CoreCache.readVersion(new VersionDto(generateCode(version), version, projectCode)).getCode();
  }

  public static MetadataDto getMetaData(final String name, final String value) {
    return new MetadataDto(name, value);
  }

  public static String getItemType(final String issueType) {
    return StringUtils.isBlank(issueType) ?
        TmsCache.readType(new ItemTypeDto(UNSET, UNSET)).getCode() :
        TmsCache.readType(new ItemTypeDto(generateCode(issueType), issueType)).getCode();
  }

  public static String getPriority(final String priority) {
    return StringUtils.isBlank(priority) ?
        TmsCache.readPriority(new PriorityDto(UNSET, UNSET)).getCode() :
        TmsCache.readPriority(new PriorityDto(generateCode(priority), priority)).getCode();
  }

  public static String getStatus(final String statusName) {
    return StringUtils.isBlank(statusName) ?
        TmsCache.readStatus(new StatusDto(UNSET, UNSET)).getCode() :
        TmsCache.readStatus(new StatusDto(generateCode(statusName), statusName)).getCode();
  }
}

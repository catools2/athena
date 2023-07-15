package org.catools.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.enums.CPlatform;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This is a tool for common System call we use.
 */
@UtilityClass
public class CSystemUtil {
  private static String hostAddress;
  private static CPlatform platform;

  public static synchronized CPlatform getPlatform() {
    if (platform == null) {
      platform = CPlatform.fromName(System.getProperty("os.name"));
    }
    return platform;
  }

  public static String getUserName() {
    return System.getProperty("user.name");
  }

  public static synchronized String getHostIP() {
    try {
      if (StringUtils.isBlank(hostAddress)) {
        hostAddress = InetAddress.getLocalHost().getHostAddress();
      }
      return hostAddress;
    } catch (UnknownHostException e) {
      throw new RuntimeException("Could not get the host ip address.", e);
    }
  }
}

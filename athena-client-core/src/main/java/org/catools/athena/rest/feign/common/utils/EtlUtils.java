package org.catools.athena.rest.feign.common.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class EtlUtils {

  // Generate unique code for string.
  // if the string is less than 10 character then just change the string to uppercase and return it
  // otherwise use UUID unique code for the string
  public static String generateCode(final String name) {
    String normalizedName = name.toUpperCase();

    if (normalizedName.length() < 11) {
      return normalizedName;
    }

    normalizedName = normalizedName.replaceAll("\\s+", "");

    String code = String.valueOf(UUID.nameUUIDFromBytes(normalizedName.getBytes())).replaceAll("\\W", "");
    return code.substring(0, Math.min(code.length(), 10));
  }
}

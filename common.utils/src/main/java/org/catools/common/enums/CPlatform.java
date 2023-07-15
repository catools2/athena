package org.catools.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.regex.Pattern;

public enum CPlatform {
  WINDOWS("^.*win.*$"),
  LINUX("^.*(nix|nux|aix).*$"),
  MAC("^.*mac.*$");

  private Pattern namePattern;

  CPlatform(String nameRegEx) {
    this.namePattern = Pattern.compile(nameRegEx);
  }

  public static CPlatform fromName(String input) {
    return EnumSet.allOf(CPlatform.class).stream()
        .filter(v -> v.namePattern.matcher(StringUtils.defaultString(input).toLowerCase()).find())
        .findFirst()
        .orElse(null);
  }

  public boolean isLinux() {
    return this.equals(LINUX);
  }

  public boolean isMac() {
    return this.equals(MAC);
  }

  public boolean isWindows() {
    return this.equals(WINDOWS);
  }
}

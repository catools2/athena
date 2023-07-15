package org.catools.common.text.match;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CStringMatchUtil {
  public static double calculateMatchValue(String actual, String match) {
    char[] longOne = actual.toCharArray();
    char[] shortOne = match.toCharArray();

    if (actual.length() < match.length()) {
      shortOne = actual.toCharArray();
      longOne = match.toCharArray();
    }

    int diffValue = 0;
    for (int i = 0; i < longOne.length; i++) {
      if (i >= shortOne.length || longOne[i] != shortOne[i]) {
        diffValue++;
      }
    }
    return 100 - ((double) diffValue / (double) longOne.length * 100);
  }
}

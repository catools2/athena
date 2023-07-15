package org.catools.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RegExUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Normally we should not extend Utility classes but this class is just an extension so we can have
 * one source for all related utils. So we do not have different class for regex utility in code and
 * in case of change in parent, cost of maintenance is still the same.
 *
 * <p>General regex manipulation utilities. 1- Automation is internal tools and end user totally
 * have control on flow. 2- Also, the test automation flow usually cross several app tier and uses
 * different utils. Being said, having unchecked exceptions will impact code readability and large
 * list of throws on test methods. To avoid it we wrap some RegExUtils methods to throw specific
 * runtime exceptions here.
 */
@UtilityClass
public class CRegExUtil extends RegExUtils {
  public static final String ALPHANUMERIC_PATTERN = "^[0-9a-zA-Z]*$";
  public static final String NUMERIC_PATTERN = "^[\\d]*$";

  public static List<String> groups(String input, Pattern pattern) {
    List<String> matches = new ArrayList<>();
    Matcher matcher = pattern.matcher(input);
    while (matcher.find()) {
      int i = 0;
      while (i++ < matcher.groupCount()) {
        matches.add(matcher.group(i));
      }
    }
    return matches;
  }

  public static boolean isAlphaNumeric(String input) {
    return isMatch(input, ALPHANUMERIC_PATTERN);
  }

  public static boolean isAlphaNumericSpace(String input) {
    input = removeAll(input, "\\s+");
    return CStringUtil.isNotBlank(input) && isMatch(input, ALPHANUMERIC_PATTERN);
  }

  public static boolean isMatch(String input, String pattern) {
    return isMatch(input, Pattern.compile(pattern));
  }

  public static boolean isMatch(String input, Pattern pattern) {
    return pattern.matcher(CStringUtil.defaultString(input)).find();
  }

  public static boolean isNumeric(String input) {
    return isMatch(input, NUMERIC_PATTERN);
  }
}

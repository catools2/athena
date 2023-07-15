package org.catools.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Normally we should not extend Utility classes but this class is just an extension so we can have
 * one source for all related utils. So we do not have different class for string utility in code
 * and in case of change in parent, cost of maintenance is still the same.
 *
 * <p>General string manipulation utilities. 1- Automation is internal tools and end user totally
 * have control on flow. 2- Also, the test automation flow usually cross several app tier and uses
 * different utils. Being said, having unchecked exceptions will impact code readability and large
 * list of throws on test methods. To avoid it we wrap some StringUtils methods to throw specific
 * runtime exceptions here.
 */
@UtilityClass
public class CStringUtil extends StringUtils {

  public static String format(String format, Object... parameters) {
    return parameters.length == 0 ? format : String.format(format, parameters);
  }

  public static String removeWhiteSpaces(String input) {
    return CRegExUtil.replaceAll(input, "[\\s]+", EMPTY);
  }

  public static String normalizeSpace(String input) {
    return removeEnd(removeStart(CRegExUtil.replaceAll(input, "[\\s]+", SPACE), SPACE), SPACE);
  }

  public static String scrunch(String input, boolean changeCase) {
    String output = defaultString(input);
    if (changeCase) {
      output = output.toUpperCase();
    }
    return output.replaceAll("([^\\da-zA-Z])", EMPTY);
  }

  public static String scrunch(String input) {
    return scrunch(input, true);
  }

  // WordUtils
  public static List<String> wordWrap(String input, int maxLength) {
    return Arrays.asList(WordUtils.wrap(input, maxLength).split(System.lineSeparator()));
  }

  public static boolean equalsAny(CharSequence sequence, CharSequence... searchStrings) {
    if (!isEmpty(sequence) && !ArrayUtils.isEmpty(searchStrings)) {
      CharSequence[] var2 = searchStrings;
      int var3 = searchStrings.length;

      for (int var4 = 0; var4 < var3; ++var4) {
        CharSequence searchString = var2[var4];
        if (equals(sequence, searchString)) {
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  public static boolean equalsAnyIgnoreCase(CharSequence sequence, CharSequence... searchStrings) {
    if (!isEmpty(sequence) && !ArrayUtils.isEmpty(searchStrings)) {
      CharSequence[] var2 = searchStrings;
      int var3 = searchStrings.length;

      for (int var4 = 0; var4 < var3; ++var4) {
        CharSequence searchString = var2[var4];
        if (equalsIgnoreCase(sequence, searchString)) {
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  public static boolean containsAny(CharSequence sequence, CharSequence... searchStrings) {
    if (!isEmpty(sequence) && !ArrayUtils.isEmpty(searchStrings)) {
      CharSequence[] var2 = searchStrings;
      int var3 = searchStrings.length;

      for (int var4 = 0; var4 < var3; ++var4) {
        CharSequence searchString = var2[var4];
        if (contains(sequence, searchString)) {
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }

  public static boolean containsAnyIgnoreCase(
      CharSequence sequence, CharSequence... searchStrings) {
    if (!isEmpty(sequence) && !ArrayUtils.isEmpty(searchStrings)) {
      CharSequence[] var2 = searchStrings;
      int var3 = searchStrings.length;

      for (int var4 = 0; var4 < var3; ++var4) {
        CharSequence searchString = var2[var4];
        if (containsIgnoreCase(sequence, searchString)) {
          return true;
        }
      }

      return false;
    } else {
      return false;
    }
  }
}

package org.catools.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

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

  /**
   * Retrun substring with max length of maxLength. If string length is less than maxLength then return full string.
   *
   * @param input
   * @param maxLength
   * @return
   */
  public static String trySubstring(String input, int maxLength) {
    if (CStringUtil.isBlank(input)) return "";
    return input.substring(0, Math.min(input.length(), maxLength));
  }

  /**
   * Wrapper for string format to skip empty list of parameters.
   *
   * @param format
   * @param parameters
   * @return
   */
  public static String format(String format, Object... parameters) {
    return parameters == null || parameters.length == 0 ? format : String.format(format, parameters);
  }

  /**
   * remote all WhiteSpaces from string
   *
   * @param input
   * @return
   */
  public static String removeWhiteSpaces(String input) {
    return CRegExUtil.replaceAll(input, "[\\s]+", EMPTY);
  }

  /**
   * replace all sequence of WhiteSpaces with one space
   *
   * @param input
   * @return
   */
  public static String normalizeSpace(String input) {
    return removeEnd(removeStart(CRegExUtil.replaceAll(input, "[\\s]+", SPACE), SPACE), SPACE);
  }

  /**
   * return true if the initial sequence equals to any of search values NOT ignoring case sensitivity
   *
   * @param sequence
   * @param searchValues
   * @return
   */
  public static boolean equalsAny(CharSequence sequence, CharSequence... searchValues) {
    if (!isEmpty(sequence) && !ArrayUtils.isEmpty(searchValues)) {
      CharSequence[] var2 = searchValues;
      int var3 = searchValues.length;

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

  /**
   * return true if the initial sequence equals to any of search values ignoring case sensitivity
   *
   * @param sequence
   * @param searchValues
   * @return
   */
  public static boolean equalsAnyIgnoreCase(CharSequence sequence, CharSequence... searchValues) {
    if (!isEmpty(sequence) && !ArrayUtils.isEmpty(searchValues)) {
      CharSequence[] var2 = searchValues;
      int var3 = searchValues.length;

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

  /**
   * return true if the initial sequence contains any of search values NOT ignoring case sensitivity
   *
   * @param sequence
   * @param searchValues
   * @return
   */
  public static boolean containsAny(CharSequence sequence, CharSequence... searchValues) {
    if (!isEmpty(sequence) && !ArrayUtils.isEmpty(searchValues)) {
      CharSequence[] var2 = searchValues;
      int var3 = searchValues.length;

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

  /**
   * return true if the initial sequence contains any of search values ignoring case sensitivity
   *
   * @param sequence
   * @param searchValues
   * @return
   */
  public static boolean containsAnyIgnoreCase(
      CharSequence sequence, CharSequence... searchValues) {
    if (!isEmpty(sequence) && !ArrayUtils.isEmpty(searchValues)) {
      CharSequence[] var2 = searchValues;
      int var3 = searchValues.length;

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

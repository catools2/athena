package org.catools.common.extensions.states.interfaces;

import org.catools.common.date.CDate;

import java.util.Date;
import java.util.Objects;

/**
 * CDateState is an interface for Date state related methods.
 *
 * <p>We need this interface to have possibility of adding state to any exists objects with the
 * minimum change in the code.
 */
public interface CDateState extends CObjectState<Date> {

  default boolean isEqual(final Date expected) {
    return Objects.equals(_get(), expected);
  }

  /**
   * Check if actual and expected have the exact same string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd" passes.
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected value to compare
   * @param format   date format to be use
   * @return execution boolean result
   */
  default boolean equalsByFormat(final Date expected, final String format) {
    Date o = _get();
    return o == null || expected == null
        ? o == expected
        : new CDate(o).compareDateByFormat(new CDate(expected), format) == 0;
  }

  /**
   * Check if actual and expected have same string value after they converted using "yyyy-MM-dd" for
   * format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean equalsDatePortion(final Date expected) {
    Date o = _get();
    return o == null || expected == null
        ? o == expected
        : new CDate(o).compareDatePortion(new CDate(expected)) == 0;
  }

  /**
   * Check if actual and expected have same string value after they converted using "HH:mm:ss" for
   * format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20" passes
   *
   * <p>Please note that verification consider as passe if both value is null
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean equalsTimePortion(final Date expected) {
    Date o = _get();
    return o == null || expected == null
        ? o == expected
        : new CDate(o).compareTimePortion(new CDate(expected)) == 0;
  }

  /**
   * Check if actual and expected have different string value after they converted using the
   * provided date format. Means that verification of "2019-08-09 12:20" and "2019-08-09 11:20"
   * using "yyyy-MM-dd HH" passes (means values are different)
   *
   * <p>Please note that verification consider as passe if expected value is null
   *
   * @param expected value to compare
   * @param format   date format to be use
   * @return execution boolean result
   */
  default boolean notEqualsByFormat(final Date expected, final String format) {
    Date o = _get();
    return o == null || expected == null
        ? o != expected
        : new CDate(o).compareDateByFormat(new CDate(expected), format) != 0;
  }

  /**
   * Check if actual and expected have different string value after they converted using
   * "yyyy-MM-dd" for format. Means that verification of "2019-08-09 12:20" and "2019-08-08 12:20"
   * passes (means values are different)
   *
   * <p>Please note that verification consider as passe if expected value is null
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean notEqualsDatePortion(final Date expected) {
    Date o = _get();
    return o == null || expected == null
        ? o != expected
        : new CDate(o).compareDatePortion(new CDate(expected)) != 0;
  }

  /**
   * Check if actual and expected have different string value after they converted using "HH:mm:ss"
   * for format. Means that verification of "2019-08-09 12:20:31" and "2019-08-09 12:20:30" passes
   * (means values are different)
   *
   * <p>Please note that verification consider as passe if expected value is null
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean notEqualsTimePortion(final Date expected) {
    Date o = _get();
    return o == null || expected == null
        ? o != expected
        : new CDate(o).compareTimePortion(new CDate(expected)) != 0;
  }
}

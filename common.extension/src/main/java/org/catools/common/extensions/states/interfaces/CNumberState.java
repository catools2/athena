package org.catools.common.extensions.states.interfaces;

import org.catools.common.exception.CInvalidRangeException;

import java.util.Objects;

/**
 * CNumberState is an interface for Boolean state related methods.
 *
 * <p>We need this interface to have possibility of adding state to any exists objects with the
 * minimum change in the code.
 */
public interface CNumberState<N extends Number & Comparable<N>> extends CObjectState<N> {
  /**
   * Check if actual value is between lower and higher bound values (exclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @return execution boolean result
   */
  default boolean betweenExclusive(final N lowerBound, final N higherBound) {
    Objects.requireNonNull(lowerBound);
    Objects.requireNonNull(higherBound);

    if (lowerBound.compareTo(higherBound) > 0) {
      throw new CInvalidRangeException(
          "Lower Bound value should be less or equal to Higher Bound Value");
    }
    N o = _get();
    return o.compareTo(higherBound) < 0 && o.compareTo(lowerBound) > 0;
  }

  /**
   * Check if actual value is between lower and higher bound values (Inclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @return execution boolean result
   */
  default boolean betweenInclusive(final N lowerBound, final N higherBound) {
    Objects.requireNonNull(lowerBound);
    Objects.requireNonNull(higherBound);

    if (lowerBound.compareTo(higherBound) > 0) {
      throw new CInvalidRangeException(
          "Lower Bound value should be less or equal to Higher Bound Value");
    }
    N o = _get();
    return o.compareTo(higherBound) <= 0 && o.compareTo(lowerBound) >= 0;
  }

  /**
   * Check if actual and expected have the exact same value.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean isEqual(final N expected) {
    N o = _get();
    return o == null || expected == null ? o == expected : o.compareTo(expected) == 0;
  }

  /**
   * Check if actual and expected do not have the exact same value.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean isNotEqual(final N expected) {
    N o = _get();
    return o == null || expected == null ? o != expected : o.compareTo(expected) != 0;
  }

  /**
   * Check if actual and expected have the exact same value or their difference is less than
   * precision value.
   *
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @return execution boolean result
   */
  default boolean isEqual(final N expected, final N precision) {
    N o = _get();
    if (o == null || expected == null) {
      return o == expected;
    }

    if (o instanceof Integer) {
      return Math.abs(o.intValue() - expected.intValue()) <= precision.intValue();
    }
    if (o instanceof Long) {
      return Math.abs(o.longValue() - expected.longValue()) <= precision.longValue();
    }
    if (o instanceof Float) {
      return Math.abs(o.floatValue() - expected.floatValue()) <= precision.floatValue();
    }
    if (o instanceof Byte) {
      return Math.abs(o.byteValue() - expected.byteValue()) <= precision.byteValue();
    }
    if (o instanceof Short) {
      return Math.abs(o.shortValue() - expected.shortValue()) <= precision.shortValue();
    }
    return Math.abs(o.doubleValue() - expected.doubleValue()) <= precision.doubleValue();
  }

  /**
   * Check if actual has value greater than expected.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean greater(final N expected) {
    Objects.requireNonNull(expected);
    return _get().compareTo(expected) > 0;
  }

  /**
   * Check if actual has value greater or equal to expected.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean greaterOrEqual(final N expected) {
    Objects.requireNonNull(expected);
    return _get().compareTo(expected) >= 0;
  }

  /**
   * Check if actual has value less than expected.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean less(final N expected) {
    Objects.requireNonNull(expected);
    return _get().compareTo(expected) < 0;
  }

  /**
   * Check if actual has value less or equal than expected.
   *
   * @param expected value to compare
   * @return execution boolean result
   */
  default boolean lessOrEqual(final N expected) {
    Objects.requireNonNull(expected);
    return _get().compareTo(expected) <= 0;
  }

  /**
   * Check if actual value is NOT between lower and higher bound values (Exclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @return execution boolean result
   */
  default boolean notBetweenExclusive(final N lowerBound, final N higherBound) {
    return !betweenExclusive(lowerBound, higherBound);
  }

  /**
   * Check if actual value is NOT between lower and higher bound values (Inclusive).
   *
   * @param lowerBound  lower bound inclusive
   * @param higherBound higher bound inclusive
   * @return execution boolean result
   */
  default boolean notBetweenInclusive(final N lowerBound, final N higherBound) {
    return !betweenInclusive(lowerBound, higherBound);
  }

  /**
   * Check if actual and expected have different value greater than precision value.
   *
   * <p>Please note that verification consider as passe if one value is null
   *
   * @param expected  value to compare
   * @param precision the acceptable precision
   * @return execution boolean result
   */
  default boolean notEquals(final N expected, final N precision) {
    return !isEqual(expected, precision);
  }
}

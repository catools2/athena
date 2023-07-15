package org.catools.common.extensions.states.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.catools.common.collections.CList;
import org.catools.common.utils.CIterableUtil;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * CObjectState is an interface for Boolean state related methods.
 *
 * <p>We need this interface to have possibility of adding state to any exists objects with the
 * minimum change in the code.
 */
public interface CObjectState<O> extends CBaseState<O> {

  default boolean test(Predicate<O> predicate) {
    return predicate.test(_get());
  }

  /**
   * Check if actual and expected are equal
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isEqual(O expected) {
    return Objects.equals(_get(), expected);
  }


  /**
   * Check if actual and expected are not equal
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean notEquals(final O expected) {
    return !isEqual(expected);
  }

  /**
   * Check if actual value equals to any expected values
   *
   * @param expected values to compare, return false if no expected value provided.
   * @return execution result
   */
  default boolean equalsAny(final O... expected) {
    if (expected == null) {
      return false;
    }

    return equalsAny(new CList<>(expected));
  }

  /**
   * Check if actual value equals to any expected values
   *
   * @param expected values to compare, return false if no expected value provided.
   * @return execution result
   */
  default boolean equalsAny(final Iterable<O> expected) {
    if (expected == null) {
      return false;
    }

    for (O o : expected) {
      if (isEqual(o)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if actual value not equals to any of expected values, return false if no expected value
   * provided.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean equalsNone(final O... expected) {
    if (expected == null) {
      return false;
    }

    return equalsNone(new CList<>(expected));
  }

  /**
   * Check if actual value not equals to any of expected values, return false if no expected value
   * provided.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean equalsNone(final Iterable<O> expected) {
    if (expected == null) {
      return false;
    }

    for (O o : expected) {
      if (isEqual(o)) {
        return false;
      }
    }
    return CIterableUtil.isNotEmpty(expected);
  }

  /**
   * Check if actual is null
   *
   * @return execution result
   */
  @JsonIgnore
  default boolean isNull() {
    return _get() == null;
  }

  /**
   * Check if actual is NOT null
   *
   * @return execution result
   */
  @JsonIgnore
  default boolean isNotNull() {
    return _get() != null;
  }
}

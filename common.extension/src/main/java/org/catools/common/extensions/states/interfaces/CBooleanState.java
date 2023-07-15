package org.catools.common.extensions.states.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * CBooleanState is an interface for Boolean state related methods.
 *
 * <p>We need this interface to have possibility of adding state to any exists objects with the
 * minimum change in the code.
 *
 * <p>It is critical to remember that we have 3 values for Boolean, True, False and Null
 */
public interface CBooleanState extends CObjectState<Boolean> {

  default boolean isEqual(final Boolean expected) {
    return Objects.equals(_get(), expected);
  }

  /**
   * Check if actual value is false
   *
   * @return execution boolean result
   */
  @JsonIgnore
  default Boolean isFalse() {
    return !_get();
  }

  /**
   * Check if actual value is true
   *
   * @return execution boolean result
   */
  @JsonIgnore
  default Boolean isTrue() {
    return _get();
  }
}

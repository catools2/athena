package org.catools.common.extensions.wait.interfaces;

import org.catools.common.date.CDate;
import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.utils.CSleeper;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * CObjectWaiter is an interface for Object waiter related methods.
 */
public interface CObjectWaiter<O> extends CBaseWaiter<O> {

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till predicate
   * returns the true result
   *
   * @param predicate predicate to test.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean wait(final Predicate<O> predicate) {
    return wait(predicate, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till predicate returns
   * the true result
   *
   * @param predicate     predicate to test.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean wait(final Predicate<O> predicate, final int waitInSeconds) {
    return wait(predicate, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till predicate returns the true result
   *
   * @param predicate              predicate to test.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean wait(final Predicate<O> predicate, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> toState(o).test(predicate), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value
   * is null.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNull() {
    return waitIsNull(getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value is
   * null.
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNull(final int waitInSeconds) {
    return waitIsNull(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual value is null.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNull(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(Objects::isNull, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value
   * is NOT null.
   *
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotNull() {
    return waitIsNotNull(getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value is NOT
   * null.
   *
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotNull(final int waitInSeconds) {
    return waitIsNotNull(waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual value is NOT null.
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitIsNotNull(final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(Objects::nonNull, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value
   * equals to expected value.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final O expected) {
    return waitEquals(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value equals
   * to expected value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final O expected, final int waitInSeconds) {
    return waitEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual value equals to expected value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final O expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> Objects.equals(o, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value
   * does not equal to expected value.
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEquals(final O expected) {
    return waitNotEquals(expected, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual value does
   * not equal to expected value.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEquals(final O expected, final int waitInSeconds) {
    return waitNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual value does not equal to expected value.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEquals(final O expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> !Objects.equals(o, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * actual value equals to any of the expected values.
   *
   * @param expectedList a list of values, may be {@code null}.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsAny(List<O> expectedList) {
    return waitEqualsAny(expectedList, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds until actual value equals to any of the expected values.
   *
   * @param expectedList  a list of values, may be {@code null}.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsAny(List<O> expectedList, final int waitInSeconds) {
    return waitEqualsAny(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until actual value equals to any of the expected values.
   *
   * @param expectedList           a list of values, may be {@code null}.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsAny(List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).equalsAny(expectedList), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * actual value equals none of the expected values
   *
   * @param expectedList a list of values, may be {@code null}.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsNone(List<O> expectedList) {
    return waitEqualsNone(expectedList, getDefaultWaitInSeconds());
  }

  /**
   * Wait for defined number of seconds until actual value equals none of the expected values.
   *
   * @param expectedList  a list of values, may be {@code null}.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsNone(List<O> expectedList, final int waitInSeconds) {
    return waitEqualsNone(expectedList, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until actual value equals none of the expected values.
   *
   * @param expectedList           a list of values, may be {@code null}.
   * @param waitInSeconds          maximum wait time.
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsNone(List<O> expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(a -> toState(a).equalsNone(expectedList), waitInSeconds, intervalInMilliSeconds);
  }

  default boolean _waiter(Predicate<O> waitMethod, final int waitInSeconds, final int intervalInMilliSeconds) {
    boolean isTimeOuted = false;
    Throwable lastException = null;

    CDate deadLine = new CDate().addSeconds(waitInSeconds);
    // A little ugly code for sake of debugging and branch readability
    while (true) {
      try {
        if (waitMethod.test(_get())) {
          break;
        }
      } catch (Throwable t) {
        lastException = t;
      }

      if (deadLine.before(CDate.now())) {
        isTimeOuted = true;
        break;
      }

      if ((waitInSeconds * 1000) > intervalInMilliSeconds) {
        CSleeper.sleepTight(intervalInMilliSeconds);
      }
    }

    if (isTimeOuted && lastException != null) {
      if (lastException instanceof RuntimeException) {
        throw (RuntimeException) lastException;
      }
      throw new RuntimeException(lastException);
    }

    return !isTimeOuted;
  }

  private CObjectState<O> toState(O e) {
    return new CObjectState<>() {
      @Override
      public boolean isEqual(Object expected) {
        return Objects.equals(_get(), expected);
      }

      @Override
      public O _get() {
        return e;
      }
    };
  }
}

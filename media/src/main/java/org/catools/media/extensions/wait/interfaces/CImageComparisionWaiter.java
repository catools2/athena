package org.catools.media.extensions.wait.interfaces;

import org.catools.common.extensions.wait.interfaces.CObjectWaiter;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.extensions.states.interfaces.CImageComparisionState;
import org.catools.media.utils.CImageComparisionUtil;

import java.awt.image.BufferedImage;
import java.util.Objects;

import static org.catools.media.enums.CImageComparisonType.GRAY_FLOAT_32;
import static org.catools.media.utils.CImageComparisionUtil.toBufferedImageList;

/**
 * CImageFileWaiter is an interface for BufferedImage waiter related methods.
 */
public interface CImageComparisionWaiter extends CObjectWaiter<BufferedImage> {

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual and
   * expected are equal
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final CFile expected) {
    return waitEquals(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual and expected
   * are equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final CFile expected, final int waitInSeconds) {
    return waitEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(
      final CFile expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> Objects.equals(o, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual and
   * expected are not equal
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEquals(final CFile expected) {
    return waitNotEquals(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual and expected
   * are not equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEquals(final CFile expected, final int waitInSeconds) {
    return waitNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEquals(
      final CFile expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> !Objects.equals(o, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual and
   * expected are equal
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final CResource expected) {
    return waitEquals(expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual and expected
   * are equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(final CResource expected, final int waitInSeconds) {
    return waitEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEquals(
      final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> Objects.equals(o, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds with
   * {@code CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual and
   * expected are not equal
   *
   * @param expected value to compare
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEquals(final CResource expected) {
    return waitNotEquals(
        expected, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds with {@code
   * CTypeExtensionConfigs.getDefaultWaitIntervalInMilliSeconds()} interval till actual and expected
   * are not equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEquals(final CResource expected, final int waitInSeconds) {
    return waitNotEquals(expected, waitInSeconds, getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds till actual and expected are not equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitNotEquals(
      final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(o -> !Objects.equals(o, expected), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * actual value equals to any of the expected values.
   *
   * @param expectedList a list of values, may be {@code null}.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsAny(Iterable expectedList) {
    return waitEqualsAny(
        expectedList, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until actual value equals to any of the expected values.
   *
   * @param expectedList  a list of values, may be {@code null}.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsAny(Iterable expectedList, final int waitInSeconds) {
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
  default boolean waitEqualsAny(
      Iterable expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).equalsAny(toBufferedImageList(expectedList)),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  /**
   * Wait for {@code CTypeExtensionConfigs.getDefaultWaitInSeconds()} number of milliseconds until
   * actual value equals none of the expected values
   *
   * @param expectedList a list of values, may be {@code null}.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsNone(Iterable expectedList) {
    return waitEqualsNone(
        expectedList, getDefaultWaitInSeconds(), getDefaultWaitIntervalInMilliSeconds());
  }

  /**
   * Wait for defined number of seconds until actual value equals none of the expected values.
   *
   * @param expectedList  a list of values, may be {@code null}.
   * @param waitInSeconds maximum wait time.
   * @return true if wait operation succeed otherwise return false
   */
  default boolean waitEqualsNone(Iterable expectedList, final int waitInSeconds) {
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
  default boolean waitEqualsNone(
      Iterable expectedList, final int waitInSeconds, final int intervalInMilliSeconds) {
    return _waiter(
        a -> toState(a).equalsNone(toBufferedImageList(expectedList)),
        waitInSeconds,
        intervalInMilliSeconds);
  }

  private CImageComparisionState toState(BufferedImage e) {
    return new CImageComparisionState() {
      @Override
      public boolean isEqual(BufferedImage expected) {
        return CImageComparisionUtil.getDiffs(_get(), expected, GRAY_FLOAT_32).isEmpty();
      }

      @Override
      public BufferedImage _get() {
        return e;
      }
    };
  }
}

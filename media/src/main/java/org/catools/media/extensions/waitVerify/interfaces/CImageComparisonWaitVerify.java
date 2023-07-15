package org.catools.media.extensions.waitVerify.interfaces;

import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.extensions.verify.interfaces.CImageComparisonVerify;
import org.catools.media.utils.CImageUtil;

import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

import static org.catools.media.enums.CImageComparisonType.GRAY_FLOAT_32;

/**
 * CImageComparisionWaitVerifier is an interface for BufferedImage verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CImageComparisonWaitVerify extends CImageComparisonVerify {
  /**
   * Verify that actual and expected are equal
   *
   * @param expected      value to compare
   * @param diffFileName  the file name for diff image which should be generated in case if images
   *                      did not match
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final BufferedImage expected,
      final String diffFileName,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyEquals(
        expected,
        diffFileName,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final CFile expected,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyEquals(
        expected,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final CResource expected,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyEquals(
        expected,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected               value to compare
   * @param diffFileName           the file name for diff image which should be generated in case if images
   *                               did not match
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final BufferedImage expected,
      final String diffFileName,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (a, b) -> _toState(a).isEqual(b),
        (BiConsumer<BufferedImage, BufferedImage>) (a, b) -> CImageUtil.generateDiffFile(a, b, diffFileName, GRAY_FLOAT_32),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final CFile expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    verifyEquals(
        CImageUtil.readImageOrNull(expected),
        expected.getName(),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final CResource expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    verifyEquals(
        CImageUtil.readImageOrNull(expected),
        expected.getResourceName(),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(
      final CFile expected,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyNotEquals(
        expected,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(
      final CResource expected,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyNotEquals(
        expected,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(
      final CFile expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (a, b) -> _toState(a).isNotEqual(b),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(
      final CResource expected,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (a, b) -> _toState(a).isNotEqual(b),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   * @param waitInSeconds      maximum wait time
   * @param message            information about the purpose of this verification
   * @param params             parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(
      final Iterable expected,
      final String diffFileNamePrefix,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    verifyEqualsAny(
        expected,
        diffFileNamePrefix,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param diffFileNamePrefix     the file name for diff image which should be generated in case if
   *                               images did not match. Please Note that we add index to the end to avoid duplicate file
   *                               name.
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(
      final Iterable expected,
      final String diffFileNamePrefix,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(
        expected,
        (a, e) -> _toState(a).equalsAny(e),
        (a, e) -> generateDiffForAllExpected(diffFileNamePrefix, e),
        waitInSeconds,
        intervalInMilliSeconds,
        message,
        params);
  }
}

package org.catools.media.extensions.verify.interfaces.waitVerify;

import boofcv.factory.template.TemplateScoreType;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.configs.CMediaConfigs;
import org.catools.media.extensions.verify.interfaces.base.CImageComparisonVerify;
import org.catools.media.utils.CImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.catools.media.enums.CImageComparisonType.GRAY_FLOAT_32;
import static org.catools.media.utils.CImageUtil.toBufferedImage;

/**
 * CImageComparisonWaitVerifier is an interface for BufferedImage verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 */
public interface CImageComparisonWaitVerify<O> extends CImageComparisonVerify<O> {

  /**
   * Verify if actual is null
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNull(final int waitInSeconds) {
    verifyIsNull(waitInSeconds, getDefaultMessage("Is Null"));
  }

  /**
   * Verify if actual is null
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNull(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNull(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is Null"));
  }

  /**
   * Verify if actual is null
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNull(final int waitInSeconds, final String message, final Object... params) {
    _verify(null, (a, b) -> _toState(a).isNull(), waitInSeconds, message, params);
  }

  /**
   * Verify if actual is null
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNull(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(null, (a, b) -> _toState(a).isNull(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual is NOT null
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotNull(final int waitInSeconds) {
    verifyIsNotNull(waitInSeconds, getDefaultMessage("Is not null"));
  }

  /**
   * Verify if actual is NOT null
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotNull(final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyIsNotNull(waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Is not null"));
  }

  /**
   * Verify if actual is NOT null
   *
   * @param waitInSeconds maximum wait time
   */
  default void verifyIsNotNull(final int waitInSeconds, final String message, final Object... params) {
    _verify(null, (a, b) -> _toState(a).isNotNull(), waitInSeconds, message, params);
  }

  /**
   * Verify if actual is NOT null
   *
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyIsNotNull(final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(null, (a, b) -> _toState(a).isNotNull(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected      value to compare
   * @param diffFileName  the file name for diff image which should be generated in case if images
   *                      did not match
   * @param waitInSeconds maximum wait time
   */
  default void verifyEquals(final BufferedImage expected, final String diffFileName, final int waitInSeconds) {
    verifyEquals(expected, diffFileName, waitInSeconds, getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected               value to compare
   * @param diffFileName           the file name for diff image which should be generated in case if images
   *                               did not match
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEquals(final BufferedImage expected, final String diffFileName, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEquals(expected, diffFileName, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Equals"));
  }

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
  default void verifyEquals(final BufferedImage expected, final String diffFileName, final int waitInSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).isEqual(b), (a, b) -> CImageUtil.generateDiffFile(toBufferedImage(a), b, diffFileName, GRAY_FLOAT_32), waitInSeconds, message, params);
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
  default void verifyEquals(final BufferedImage expected, final String diffFileName, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).isEqual(b), (a, b) -> CImageUtil.generateDiffFile(toBufferedImage(a), b, diffFileName, GRAY_FLOAT_32), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEquals(final CFile expected, final int waitInSeconds) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getName(), waitInSeconds, getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEquals(final CFile expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getName(), waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final CFile expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getName(), waitInSeconds, message, params);
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
  default void verifyEquals(final CFile expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getName(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyEquals(final CResource expected, final int waitInSeconds) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getResourceName(), waitInSeconds, getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEquals(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getResourceName(), waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(final CResource expected, final int waitInSeconds, final String message, final Object... params) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getResourceName(), waitInSeconds, message, params);
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
  default void verifyEquals(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyEquals(CImageUtil.readImageOrNull(expected), expected.getResourceName(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected      value to compare
   * @param diffFileName  the file name for diff image which should be generated in case if images
   *                      did not match
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEquals(final BufferedImage expected, final String diffFileName, final int waitInSeconds) {
    verifyNotEquals(expected, diffFileName, waitInSeconds, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected               value to compare
   * @param diffFileName           the file name for diff image which should be generated in case if images
   *                               did not match
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEquals(final BufferedImage expected, final String diffFileName, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEquals(expected, diffFileName, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Equals"));
  }

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
  default void verifyNotEquals(final BufferedImage expected, final String diffFileName, final int waitInSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).isNotEqual(b), (a, b) -> CImageUtil.generateDiffFile(toBufferedImage(a), b, diffFileName, GRAY_FLOAT_32), waitInSeconds, message, params);
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
  default void verifyNotEquals(final BufferedImage expected, final String diffFileName, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).isNotEqual(b), (a, b) -> CImageUtil.generateDiffFile(toBufferedImage(a), b, diffFileName, GRAY_FLOAT_32), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEquals(final CFile expected, final int waitInSeconds) {
    verifyNotEquals(expected, waitInSeconds, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEquals(final CFile expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final CFile expected, final int waitInSeconds, final String message, final Object... params) {
    _verify(toBufferedImage(expected), (a, b) -> _toState(a).isNotEqual(b), waitInSeconds, message, params);
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
  default void verifyNotEquals(final CFile expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(toBufferedImage(expected), (a, b) -> _toState(a).isNotEqual(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotEquals(final CResource expected, final int waitInSeconds) {
    verifyNotEquals(expected, waitInSeconds, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotEquals(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotEquals(expected, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Equals"));
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(final CResource expected, final int waitInSeconds, final String message, final Object... params) {
    _verify(toBufferedImage(expected), (a, b) -> _toState(a).isNotEqual(b), waitInSeconds, message, params);
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
  default void verifyNotEquals(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(toBufferedImage(expected), (a, b) -> _toState(a).isNotEqual(b), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual equals to any expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   * @param waitInSeconds      maximum wait time
   */
  default void verifyEqualsAny(final Iterable<?> expected, final String diffFileNamePrefix, final int waitInSeconds) {
    verifyEqualsAny(expected, diffFileNamePrefix, waitInSeconds, getDefaultMessage("Equals Any"));
  }

  /**
   * Verify that actual equals to any expected value
   *
   * @param diffFileNamePrefix     the file name for diff image which should be generated in case if
   *                               images did not match. Please Note that we add index to the end to avoid duplicate file
   *                               name.
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsAny(final Iterable<?> expected, final String diffFileNamePrefix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsAny(expected, diffFileNamePrefix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Equals Any"));
  }

  /**
   * Verify that actual equals to any expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   * @param waitInSeconds      maximum wait time
   * @param message            information about the purpose of this verification
   * @param params             parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(final Iterable<?> expected, final String diffFileNamePrefix, final int waitInSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).equalsAny(e), (a, e) -> _toState(a).generateDiffForAllExpected(diffFileNamePrefix, e), waitInSeconds, message, params);
  }

  /**
   * Verify that actual equals to any expected value
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
  default void verifyEqualsAny(final Iterable<?> expected, final String diffFileNamePrefix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).equalsAny(e), (a, e) -> _toState(a).generateDiffForAllExpected(diffFileNamePrefix, e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual equals to none of expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   * @param waitInSeconds      maximum wait time
   */
  default void verifyEqualsNone(final Iterable<?> expected, final String diffFileNamePrefix, final int waitInSeconds) {
    verifyEqualsNone(expected, diffFileNamePrefix, waitInSeconds, getDefaultMessage("Equals None"));
  }

  /**
   * Verify that actual equals to none of expected value
   *
   * @param diffFileNamePrefix     the file name for diff image which should be generated in case if
   *                               images did not match. Please Note that we add index to the end to avoid duplicate file
   *                               name.
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyEqualsNone(final Iterable<?> expected, final String diffFileNamePrefix, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyEqualsNone(expected, diffFileNamePrefix, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Equals None"));
  }

  /**
   * Verify that actual equals to none of expected value
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   * @param waitInSeconds      maximum wait time
   * @param message            information about the purpose of this verification
   * @param params             parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsNone(final Iterable<?> expected, final String diffFileNamePrefix, final int waitInSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).equalsNone(e), (a, e) -> _toState(a).generateDiffForAllExpected(diffFileNamePrefix, e), waitInSeconds, message, params);
  }

  /**
   * Verify that actual equals to none of expected value
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
  default void verifyEqualsNone(final Iterable<?> expected, final String diffFileNamePrefix, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, e) -> _toState(a).equalsNone(e), (a, e) -> _toState(a).generateDiffForAllExpected(diffFileNamePrefix, e), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(final BufferedImage expected, final int waitInSeconds) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final BufferedImage expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(final File expected, final int waitInSeconds) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final File expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(final CResource expected, final int waitInSeconds) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds) {
    verifyContains(expected, scoreType, mask, matchPrecision, waitInSeconds, getDefaultMessage("Contains"));
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains"));
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).contains(b, scoreType, mask, matchPrecision), waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).contains(b, scoreType, mask, matchPrecision), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(final BufferedImage expected, final int waitInSeconds) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final BufferedImage expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(final File expected, final int waitInSeconds) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final File expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(final CResource expected, final int waitInSeconds) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      value to compare
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               value to compare
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected      value to compare
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, mask, matchPrecision, waitInSeconds, getDefaultMessage("Not Contains"));
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Not Contains"));
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notContains(b, scoreType, mask, matchPrecision), waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).notContains(b, scoreType, mask, matchPrecision), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final BufferedImage expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final File expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(expected, scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected       value to compare
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify if actual contains expected image with at least 99.9% match precision.
   *
   * @param expected               value to compare
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyNotContains(final CResource expected, TemplateScoreType scoreType, final CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyNotContains(toBufferedImage(expected), scoreType, mask == null ? null : toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      values to compare, return false if no value provided. all {@code null} value in
   *                      expected list ignores.
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsAny(final Iterable<?> expected, final int waitInSeconds) {
    verifyContainsAny(expected, TemplateScoreType.NCC, waitInSeconds);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAny(final Iterable<?> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAny(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      values to compare, return false if no value provided. all {@code null} value in
   *                      expected list ignores.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, TemplateScoreType.NCC, waitInSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected      values to compare, return false if no value provided. all {@code null} value in
   *                      expected list ignores.
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds) {
    verifyContainsAny(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAny(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected      values to compare, return false if no value provided. all {@code null} value in
   *                      expected list ignores.
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds) {
    verifyContainsAny(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAny(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, (BufferedImage) null, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds) {
    verifyContainsAny(expected, scoreType, mask, matchPrecision, waitInSeconds, getDefaultMessage("Contains Any"));
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAny(expected, scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains Any"));
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).containsAny(b, scoreType, mask, matchPrecision), waitInSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).containsAny(b, scoreType, mask, matchPrecision), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision, final int waitInSeconds) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, File mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision, final int waitInSeconds) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected       values to compare, return false if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify that actual image contains any of expected images.
   *
   * @param expected               values to compare, return false if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsAny(final Iterable<?> expected, TemplateScoreType scoreType, CResource mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContainsAny(expected, scoreType, toBufferedImage(mask), matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      values to compare, return true if no value provided. all {@code null} value in
   *                      expected list ignores.
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsNone(final Iterable<?> expected, final int waitInSeconds) {
    verifyContainsNone(expected, TemplateScoreType.NCC, waitInSeconds);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsNone(final Iterable<?> expected, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsNone(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected      values to compare, return true if no value provided. all {@code null} value in
   *                      expected list ignores.
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final Iterable<?> expected, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsNone(expected, TemplateScoreType.NCC, waitInSeconds, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision using {@link TemplateScoreType#NCC} formula to score matches in a template.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final Iterable<?> expected, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContainsNone(expected, TemplateScoreType.NCC, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected      values to compare, return true if no value provided. all {@code null} value in
   *                      expected list ignores.
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds) {
    verifyContainsNone(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsNone(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected      values to compare, return true if no value provided. all {@code null} value in
   *                      expected list ignores.
   * @param scoreType     formula to score matches in a template
   * @param waitInSeconds maximum wait time
   * @param message       information about the purpose of this verification
   * @param params        parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsNone(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images
   * with at least 99.9% match precision.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContainsNone(expected, scoreType, CMediaConfigs.getDefaultMatchPrecision(), waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds) {
    verifyContainsNone(expected, scoreType, null, matchPrecision, waitInSeconds);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsNone(expected, scoreType, null, matchPrecision, waitInSeconds, intervalInMilliSeconds);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    verifyContainsNone(expected, scoreType, null, matchPrecision, waitInSeconds, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    verifyContainsNone(expected, scoreType, null, matchPrecision, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds) {
    verifyContainsNone(expected, scoreType, mask, matchPrecision, waitInSeconds, getDefaultMessage("Contains None"));
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds) {
    verifyContainsNone(expected, scoreType, mask, matchPrecision, waitInSeconds, intervalInMilliSeconds, getDefaultMessage("Contains None"));
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected       values to compare, return true if no value provided. all {@code null} value in
   *                       expected list ignores.
   * @param scoreType      formula to score matches in a template
   * @param mask           Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision The match precision in percent (100 means 100% match)
   * @param waitInSeconds  maximum wait time
   * @param message        information about the purpose of this verification
   * @param params         parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).containsAny(b, scoreType, mask, matchPrecision), waitInSeconds, message, params);
  }

  /**
   * Verify that actual image does not contain any of expected images.
   *
   * @param expected               values to compare, return true if no value provided. all {@code null} value in
   *                               expected list ignores.
   * @param scoreType              formula to score matches in a template
   * @param mask                   Mask which determines the weight of each template pixel in the match score
   * @param matchPrecision         The match precision in percent (100 means 100% match)
   * @param waitInSeconds          maximum wait time
   * @param intervalInMilliSeconds interval between retries in milliseconds
   * @param message                information about the purpose of this verification
   * @param params                 parameters in case if message is a format {@link String#format}
   */
  default void verifyContainsNone(final Iterable<?> expected, TemplateScoreType scoreType, BufferedImage mask, float matchPrecision, final int waitInSeconds, final int intervalInMilliSeconds, final String message, final Object... params) {
    _verify(expected, (a, b) -> _toState(a).containsAny(b, scoreType, mask, matchPrecision), waitInSeconds, intervalInMilliSeconds, message, params);
  }
}

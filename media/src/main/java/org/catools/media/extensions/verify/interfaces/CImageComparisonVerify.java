package org.catools.media.extensions.verify.interfaces;

import org.catools.common.extensions.verify.interfaces.base.CObjectVerify;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.extensions.states.interfaces.CImageComparisionState;
import org.catools.media.extensions.verify.CImageComparisionVerification;
import org.catools.media.utils.CImageComparisionUtil;
import org.catools.media.utils.CImageUtil;

import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.function.BiConsumer;

import static org.catools.media.enums.CImageComparisonType.GRAY_FLOAT_32;
import static org.catools.media.utils.CImageComparisionUtil.toBufferedImageList;

/**
 * CImageComparisionVerifier is an interface for BufferedImage verification related methods.
 *
 * <p>We need this interface to have possibility of adding verification to any exists objects with
 * the minimum change in the code. In the meantime adding verification method in one place can be
 * extended across all other objects:
 *
 * <p>Please Note that we should extend manually {@link CImageComparisionVerification} for each new
 * added verification here
 */
public interface CImageComparisonVerify extends CObjectVerify<BufferedImage, CImageComparisionState> {
  /**
   * Verify that actual and expected are equal
   *
   * @param expected     value to compare
   * @param diffFileName the file name for diff image which should be generated in case if images
   *                     did not match
   * @param message      information about the purpose of this verification
   * @param params       parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final BufferedImage expected,
      final String diffFileName,
      final String message,
      final Object... params) {
    if (withWaiter()) {
      _verify(
          expected,
          (a, b) -> _toState(a).isEqual(b),
          (BiConsumer<BufferedImage, BufferedImage>) (a, b) -> CImageUtil.generateDiffFile(a, b, diffFileName, GRAY_FLOAT_32),
          getDefaultWaitInSeconds(),
          getDefaultWaitIntervalInMilliSeconds(),
          message,
          params);
    } else {
      _verify(
          expected,
          (a, b) -> _toState(a).isEqual(b),
          (BiConsumer<BufferedImage, BufferedImage>) (a, b) -> CImageUtil.generateDiffFile(a, b, diffFileName, GRAY_FLOAT_32),
          message,
          params);
    }
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   */
  default void verifyEquals(final CFile expected) {
    verifyEquals(
        CImageUtil.readImageOrNull(expected),
        expected.getName(),
        getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final CFile expected,
      final String message,
      final Object... params) {
    verifyEquals(
        CImageUtil.readImageOrNull(expected),
        expected.getName(),
        message,
        params);
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   */
  default void verifyEquals(final CResource expected) {
    verifyEquals(
        CImageUtil.readImageOrNull(expected),
        expected.getResourceName(),
        getDefaultMessage("Equals"));
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyEquals(
      final CResource expected,
      final String message,
      final Object... params) {
    verifyEquals(
        CImageUtil.readImageOrNull(expected),
        expected.getResourceName(),
        message,
        params);
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(
      final CFile expected,
      final String message,
      final Object... params) {
    BufferedImage bufferedImage = CImageUtil.readImageOrNull(expected);
    if (withWaiter()) {
      _verify(
          bufferedImage,
          (a, b) -> _toState(a).isNotEqual(b),
          getDefaultWaitInSeconds(),
          getDefaultWaitIntervalInMilliSeconds(),
          message,
          params);
    } else {
      _verify(bufferedImage, (a, b) -> _toState(a).isNotEqual(b), message, params);
    }
  }

  /**
   * Verify that actual and expected are not equal
   *
   * @param expected value to compare
   * @param message  information about the purpose of this verification
   * @param params   parameters in case if message is a format {@link String#format}
   */
  default void verifyNotEquals(
      final CResource expected,
      final String message,
      final Object... params) {
    BufferedImage bufferedImage = CImageUtil.readImageOrNull(expected);
    if (withWaiter()) {
      _verify(
          bufferedImage,
          (a, b) -> _toState(a).isNotEqual(b),
          getDefaultWaitInSeconds(),
          getDefaultWaitIntervalInMilliSeconds(),
          message,
          params);
    } else {
      _verify(bufferedImage, (a, b) -> _toState(a).isNotEqual(b), message, params);
    }
  }

  /**
   * Verify that actual and expected are equal
   *
   * @param diffFileNamePrefix the file name for diff image which should be generated in case if
   *                           images did not match. Please Note that we add index to the end to avoid duplicate file
   *                           name.
   * @param expected           value to compare
   * @param message            information about the purpose of this verification
   * @param params             parameters in case if message is a format {@link String#format}
   */
  default void verifyEqualsAny(
      final Iterable<BufferedImage> expected,
      final String diffFileNamePrefix,
      final String message,
      final Object... params) {
    if (withWaiter()) {
      _verify(
          expected,
          (a, e) -> _toState(a).equalsAny(e),
          (a, e) -> generateDiffForAllExpected(diffFileNamePrefix, e),
          getDefaultWaitInSeconds(),
          getDefaultWaitIntervalInMilliSeconds(),
          message,
          params);
    } else {
      _verify(
          expected,
          (a, e) -> _toState(a).equalsAny(e),
          (a, e) -> generateDiffForAllExpected(diffFileNamePrefix, e),
          message,
          params);
    }
  }

  default void generateDiffForAllExpected(String diffFileNamePrefix, Iterable<?> expectedList) {
    int counter = 0;
    BufferedImage actual = _get();
    for (BufferedImage exp : toBufferedImageList(expectedList)) {
      CImageUtil.generateDiffFile(
          actual,
          exp,
          diffFileNamePrefix + "_" + ++counter,
          GRAY_FLOAT_32);
    }
  }

  default CImageComparisionState _toState(BufferedImage o) {
    return new CImageComparisionState() {
      @Override
      public boolean isEqual(BufferedImage expected) {
        if (expected == null) {
          return false;
        }
        return CImageComparisionUtil.getDiffs(Objects.requireNonNull(_get()), expected, GRAY_FLOAT_32).isEmpty();
      }

      @Override
      public BufferedImage _get() {
        if (o == null) {
          return null;
        }
        return o;
      }
    };
  }
}

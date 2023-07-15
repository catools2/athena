package org.catools.media.extensions.states.interfaces;

import org.catools.common.collections.CList;
import org.catools.common.extensions.states.interfaces.CObjectState;
import org.catools.common.io.CResource;
import org.catools.media.utils.CImageComparisionUtil;
import org.catools.media.utils.CImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;

import static org.catools.media.enums.CImageComparisonType.GRAY_FLOAT_32;
import static org.catools.media.utils.CImageComparisionUtil.toBufferedImageList;

/**
 * CImageComparisionState is an interface to define state of BufferedImage in relation to one or
 * more File, Resource or BufferedImage.
 */
public interface CImageComparisionState extends CObjectState<BufferedImage> {
  /**
   * Check if actual and expected are equal.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isEqual(final BufferedImage expected) {
    return CImageComparisionUtil.getDiffs(_get(), expected, GRAY_FLOAT_32).isEmpty();
  }

  /**
   * Check if actual and expected are equal, after converting content to BufferedImage.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isEqual(final CResource expected) {
    return CImageComparisionUtil.getDiffs(
            _get(), CImageUtil.readImageOrNull(expected), GRAY_FLOAT_32)
        .isEmpty();
  }

  /**
   * Check if actual and expected are not equal.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isNotEqual(final BufferedImage expected) {
    return CImageComparisionUtil.getDiffs(_get(), expected, GRAY_FLOAT_32).isNotEmpty();
  }

  /**
   * Check if actual and expected are not equal, after converting content to BufferedImage.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isNotEqual(final CResource expected) {
    return CImageComparisionUtil.getDiffs(
            _get(), CImageUtil.readImageOrNull(expected), GRAY_FLOAT_32)
        .isNotEmpty();
  }

  /**
   * Check if actual and expected are equal, after converting content to BufferedImage
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isEqual(final File expected) {
    return CImageComparisionUtil.getDiffs(
            _get(), CImageUtil.readImageOrNull(expected), GRAY_FLOAT_32)
        .isEmpty();
  }

  /**
   * Check if actual and expected are not equal, after converting content to BufferedImage
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean isNotEqual(final File expected) {
    return CImageComparisionUtil.getDiffs(
            _get(), CImageUtil.readImageOrNull(expected), GRAY_FLOAT_32)
        .isNotEmpty();
  }

  /**
   * Check if actual value equals to any expected values, after converting content to BufferedImage.
   *
   * @param expected values to compare, return false if no value provided. all {@code null} value in
   *                 expected list ignores.
   * @return execution result
   */
  default boolean equalsAny(final Iterable expected) {
    for (BufferedImage bufferedImage : toBufferedImageList(expected)) {
      if (bufferedImage == null) {
        continue;
      }
      if (CImageComparisionUtil.getDiffs(_get(), bufferedImage, GRAY_FLOAT_32).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if actual value equals to none of expected values, after converting content to
   * BufferedImage.
   *
   * @param expected value to compare
   * @return execution result
   */
  default boolean equalsNone(final Iterable expected) {
    CList<BufferedImage> bufferedImages = toBufferedImageList(expected);
    for (BufferedImage bufferedImage : bufferedImages) {
      if (bufferedImage == null) {
        continue;
      }
      if (CImageComparisionUtil.getDiffs(_get(), bufferedImage, GRAY_FLOAT_32).isNotEmpty()) {
        return false;
      }
    }
    return bufferedImages.isNotEmpty();
  }
}

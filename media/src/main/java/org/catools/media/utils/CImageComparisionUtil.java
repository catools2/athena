package org.catools.media.utils;

import boofcv.struct.image.GrayF32;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.NotImplementedException;
import org.catools.common.collections.CList;
import org.catools.common.exception.CRuntimeException;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.enums.CImageComparisonType;
import org.catools.media.model.CDiffPoint;
import org.catools.media.model.CDiffPoints;
import org.testng.Assert;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

import static org.catools.media.utils.CImageUtil.writePNG;

@UtilityClass
public class CImageComparisionUtil {
  /**
   * Compare 2 images and return difference. We use bootcv library here to convert image to flat
   * gray with one bound to improve match algorithm.
   *
   * @param comparisonType CImageComparisonType value which defines comparison type.
   * @param img1           first image to compare
   * @param img2           second image to compare
   * @param diff           file to save diff in png format
   * @return {@link CDiffPoints} contains list of diff, empty if not diff found
   */
  public static CDiffPoints getDiffs(
      @NonNull final BufferedImage img1,
      @NonNull final BufferedImage img2,
      @NonNull final CFile diff,
      @NonNull final CImageComparisonType comparisonType) {
    CDiffPoints diffs = getDiffs(img1, img2, comparisonType);

    if (diffs == null || diffs.isEmpty()) {
      return new CDiffPoints();
    }
    return drawDiffImage(diff, diffs, img1);
  }

  /**
   * Compare 2 images and return difference. We use bootcv library here to convert image to flat
   * gray with one bound to improve match algorithm.
   *
   * @param img1           first image to compare
   * @param img2           second image to compare
   * @param comparisonType CImageComparisonType value which defines comparison type.
   * @return {@link CDiffPoints} contains list of diff, empty if not diff found
   */
  public static CDiffPoints getDiffs(
      @NonNull final BufferedImage img1,
      @NonNull final BufferedImage img2,
      @NonNull final CImageComparisonType comparisonType) {
    return switch (comparisonType) {
      case GRAY_FLOAT_32 -> getGrayF32Diffs(img1, img2);
      case FULL_COLOR -> getDiffs(img1, img2);
      default ->
          throw new NotImplementedException("There is no implementation in CImageSimpleComparator for type of " + comparisonType);
    };
  }

  private static CDiffPoints getGrayF32Diffs(
      @NonNull final BufferedImage img1, @NonNull final BufferedImage img2) {
    Objects.requireNonNull(img1);
    Objects.requireNonNull(img2);

    GrayF32 grayImg1 = CBoofCVUtil.toGrayF32(img1);
    GrayF32 grayImg2 = CBoofCVUtil.toGrayF32(img2);

    CBoofCVUtil.reshapeToSameSize(grayImg1, grayImg2);

    CDiffPoints diffs = new CDiffPoints();
    for (int x = 0; x < grayImg1.getWidth(); x++) {
      for (int y = 0; y < grayImg1.getHeight(); y++) {
        if (Math.abs(grayImg1.get(x, y) - grayImg2.get(x, y)) > 5) {
          diffs.add(new CDiffPoint(x, y, img1.getRGB(x, y), img1.getRGB(x, y)));
        }
      }
    }
    return diffs;
  }

  private static CDiffPoints getDiffs(
      @NonNull final BufferedImage img1, @NonNull final BufferedImage img2) {
    Objects.requireNonNull(img1);
    Objects.requireNonNull(img2);

    Assert.assertEquals(img1.getHeight(), img2.getHeight(), "Both image have a same Height.");
    Assert.assertEquals(img1.getWidth(), img2.getWidth(), "Both image have a same Width.");

    CDiffPoints diffs = new CDiffPoints();
    for (int x = 0; x < img1.getWidth(); x++) {
      for (int y = 0; y < img1.getHeight(); y++) {
        if (Math.abs(img1.getRGB(x, y) - img2.getRGB(x, y)) > 5) {
          diffs.add(new CDiffPoint(x, y, img1.getRGB(x, y), img1.getRGB(x, y)));
        }
      }
    }
    return diffs;
  }

  private static CDiffPoints drawDiffImage(
      @NonNull final CFile diff,
      @NonNull final CDiffPoints diffs,
      @NonNull final BufferedImage img1) {
    BufferedImage bufferedImage =
        new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.03f));
    graphics.drawImage(img1, 0, 0, null);
    graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

    for (CDiffPoint r : diffs) {
      graphics.setColor(new Color(r.getDiffColor()));
      graphics.drawLine(r.x, r.y, r.x, r.y);
    }
    graphics.dispose();
    writePNG(bufferedImage, diff);
    return diffs;
  }

  public static CList<BufferedImage> toBufferedImageList(@NonNull final Iterable input) {
    CList<BufferedImage> output = new CList<>();
    for (Object o : input) {
      if (o instanceof File file) {
        output.add(CImageUtil.readImage(file));
      } else if (o instanceof CResource resource) {
        output.add(CImageUtil.readImage(resource));
      } else if (o instanceof BufferedImage buffer) {
        output.add(buffer);
      } else if (o == null) {
        output.add(null);
      } else {
        throw new CRuntimeException(
            "Expected list can contains only File, CResource and BufferedImage values. Current record is "
                + o);
      }
    }
    return output;
  }
}

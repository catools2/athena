package org.catools.media.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.media.enums.CImageComparisonType;
import org.catools.media.exception.CIOException;
import org.catools.reportportal.utils.CReportPortalUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@UtilityClass
@Slf4j
public class CImageUtil {
  public static BufferedImage readImage(File file) {
    try {
      return ImageIO.read(file);
    } catch (IOException e) {
      throw new CIOException("Cannot read file " + file, e);
    }
  }

  public static BufferedImage readImage(CResource resource) {
    return resource.performActionOnResource((s, inputStream) -> {
      try {
        return ImageIO.read(inputStream);
      } catch (IOException e) {
        throw new CIOException("Cannot read file " + s, e);
      }
    });
  }

  public static BufferedImage readImage(byte[] imageData) {
    try {
      return ImageIO.read(new ByteArrayInputStream(imageData));
    } catch (IOException e) {
      throw new CIOException("Cannot convert array of bytes to BufferedImage.", e);
    }
  }

  public static BufferedImage readImageOrNull(File file) {
    try {
      return ImageIO.read(file);
    } catch (Throwable t) {
      return null;
    }
  }

  public static BufferedImage readImageOrNull(CResource resource) {
    return resource.performActionOnResource((s, inputStream) -> {
      try {
        return ImageIO.read(inputStream);
      } catch (IOException e) {
        return null;
      }
    });
  }

  public static BufferedImage readImageOrNull(byte[] imageData) {
    try {
      return ImageIO.read(new ByteArrayInputStream(imageData));
    } catch (Throwable t) {
      return null;
    }
  }

  public static boolean writeImage(BufferedImage image, String formatName, CFile output) {
    try {
      return ImageIO.write(image, formatName, output);
    } catch (IOException e) {
      throw new CIOException("Cannot read file " + output, e);
    }
  }

  public static boolean writePNG(BufferedImage image, CFile output) {
    try {
      return ImageIO.write(image, "png", output);
    } catch (IOException e) {
      throw new CIOException("Cannot read file " + output, e);
    }
  }

  public static void generateDiffFile(
      BufferedImage actual,
      BufferedImage expected,
      String filename,
      CImageComparisonType comparisonType) {
    if (actual == null) {
      log.warn("Cannot generate diff file {}. Actual image does not exist.", filename);
      return;
    }
    writePNG(actual, CFile.of(CPathConfigs.getActualImagesFolder()).getChildFile(filename));

    if (expected == null) {
      log.warn("Cannot generate diff file {}. Expected image does not exist.", filename);
      return;
    }
    writePNG(expected, CFile.of(CPathConfigs.getExpectedImagesFolder()).getChildFile(filename));

    CFile diff = CFile.of(CPathConfigs.getDiffImagesFolder()).getChildFile(filename);

    if (CImageComparisionUtil.getDiffs(actual, expected, diff, comparisonType).isNotEmpty()) {
      String message =
          "Verify that screen capture matches with expected image for image: " + filename;
      CReportPortalUtil.sendToReportPortal(Level.ERROR, message, diff);
    }
  }
}

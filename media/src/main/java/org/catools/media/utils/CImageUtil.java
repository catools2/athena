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
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Base64;

@UtilityClass
@Slf4j
public class CImageUtil {
  /**
   * Read image from file
   *
   * @param file
   * @return
   */
  public static BufferedImage readImage(File file) {
    try {
      return ImageIO.read(file);
    } catch (IOException e) {
      throw new CIOException("Cannot read file " + file, e);
    }
  }

  /**
   * Read image from resource
   *
   * @param resource
   * @return
   */
  public static BufferedImage readImage(CResource resource) {
    return resource.performActionOnResource((s, inputStream) -> {
      try {
        return ImageIO.read(inputStream);
      } catch (IOException e) {
        throw new CIOException("Cannot read file " + s, e);
      }
    });
  }

  /**
   * Read image from file or return null of anything went wrong
   *
   * @param file
   * @return
   */
  public static BufferedImage readImageOrNull(File file) {
    try {
      return ImageIO.read(file);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Read image from resource or return null of anything went wrong
   *
   * @param resource
   * @return
   */
  public static BufferedImage readImageOrNull(CResource resource) {
    return resource.performActionOnResource((s, inputStream) -> {
      try {
        return ImageIO.read(inputStream);
      } catch (IOException e) {
        return null;
      }
    });
  }

  /**
   * Read image from bytes or return null of anything went wrong
   *
   * @param imageData
   * @return
   */
  public static BufferedImage readImageOrNull(byte[] imageData) {
    try {
      return ImageIO.read(new ByteArrayInputStream(imageData));
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Write RenderedImage bytes to file with specified image format
   *
   * @param image
   * @param formatName the informal name of the image format (png, jpg)
   * @param output
   * @return
   */
  public static boolean writeImage(RenderedImage image, String formatName, CFile output) {
    try {
      return ImageIO.write(image, formatName, output);
    } catch (IOException e) {
      throw new CIOException("Cannot read file " + output, e);
    }
  }

  /**
   * Write RenderedImage bytes to file as png image
   *
   * @param image
   * @param output
   * @return
   */
  public static boolean writePNG(RenderedImage image, CFile output) {
    return writeImage(image, "png", output);
  }

  /**
   * Read RenderedImage bytes
   *
   * @param img
   * @param formatName
   * @return
   */
  public static byte[] getBytes(RenderedImage img, String formatName) {
    final ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (final OutputStream b64os = Base64.getEncoder().wrap(os)) {
      ImageIO.write(img, formatName, b64os);
    } catch (final IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
    return os.toByteArray();
  }

  /**
   * REad RenderedImage to Base64 string
   *
   * @param img
   * @param formatName
   * @return
   */
  public static String getBase64(RenderedImage img, String formatName) {
    final ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (final OutputStream b64os = Base64.getEncoder().wrap(os)) {
      ImageIO.write(img, formatName, b64os);
    } catch (final IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
    return os.toString();
  }


  /**
   * Save difference between 2 files in a file and return it back.
   * If anything went wrong, method returns null.
   *
   * @param actual
   * @param expected
   * @param filename
   * @param comparisonType
   * @return
   */
  public static CFile generateDiffFile(
      BufferedImage actual,
      BufferedImage expected,
      String filename,
      CImageComparisonType comparisonType) {
    if (actual == null) {
      log.warn("Cannot generate diff file {}. Actual image does not exist.", filename);
      return null;
    }
    writePNG(actual, CFile.of(CPathConfigs.getOutputActualImagesFolder()).getChildFile(filename));

    if (expected == null) {
      log.warn("Cannot generate diff file {}. Expected image does not exist.", filename);
      return null;
    }
    writePNG(expected, CFile.of(CPathConfigs.getOutputExpectedImagesFolder()).getChildFile(filename));

    CFile diff = CFile.of(CPathConfigs.getOutputDiffImagesFolder()).getChildFile(filename);

    if (CImageComparisionUtil.getDiffs(actual, expected, diff, comparisonType).isNotEmpty()) {
      String message =
          "Verify that screen capture matches with expected image for image: " + filename;
      CReportPortalUtil.sendToReportPortal(Level.ERROR, message, diff);
    }
    return diff;
  }
}

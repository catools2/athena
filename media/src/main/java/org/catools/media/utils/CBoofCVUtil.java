package org.catools.media.utils;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageBase;
import lombok.experimental.UtilityClass;

import java.awt.image.BufferedImage;

@UtilityClass
public class CBoofCVUtil {

  public static GrayF32 toGrayF32(BufferedImage buffered) {
    return ConvertBufferedImage.convertFrom(
        buffered, new GrayF32(buffered.getWidth(), buffered.getHeight()));
  }

  public static void reshapeToSameSize(ImageBase img1, ImageBase img2) {
    int maxHeight = Math.max(img1.getHeight(), img2.getHeight());
    int maxWidth = Math.max(img1.getWidth(), img2.getWidth());

    img1.reshape(maxWidth, maxHeight);
    img2.reshape(maxWidth, maxHeight);
  }
}

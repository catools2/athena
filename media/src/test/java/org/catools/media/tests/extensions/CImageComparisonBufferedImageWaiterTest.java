package org.catools.media.tests.extensions;

import org.catools.media.extensions.wait.interfaces.CImageComparisonWaiter;

import java.awt.image.BufferedImage;

public class CImageComparisonBufferedImageWaiterTest extends CBaseImageComparisonWaiterTest<BufferedImage> {

  @Override
  protected CImageComparisonWaiter<BufferedImage> toWaiter() {
    return () -> FROG_BUFFERED_IMAGE;
  }
}
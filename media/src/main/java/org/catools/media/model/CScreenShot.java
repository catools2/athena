package org.catools.media.model;

import org.catools.common.date.CDate;
import org.catools.common.io.CFile;
import org.catools.media.enums.CImageComparisonType;
import org.catools.media.extensions.types.interfaces.CImageComparisonExtension;
import org.catools.media.utils.CImageComparisionUtil;
import org.catools.media.utils.CImageUtil;

import java.awt.image.BufferedImage;

public abstract class CScreenShot implements CImageComparisonExtension {

  @Override
  public boolean isEqual(BufferedImage expected) {
    return CImageComparisionUtil.getDiffs(_get(), expected, CImageComparisonType.GRAY_FLOAT_32)
        .isEmpty();
  }

  public CFile saveAsPng() {
    return saveAs("png", CFile.fromTmp(CDate.now().toTimeStampForFileName()));
  }

  public CFile saveAsPng(CFile file) {
    return saveAs("png", file);
  }

  public CFile saveAs(String formatName, CFile file) {
    if (_get() == null) {
      return null;
    }
    CImageUtil.writeImage(_get(), formatName, file);
    return file;
  }
}

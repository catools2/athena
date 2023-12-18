package org.catools.media.extensions.types.interfaces;

import org.catools.common.date.CDate;
import org.catools.common.io.CFile;
import org.catools.media.extensions.states.interfaces.CImageComparisonState;
import org.catools.media.extensions.verify.interfaces.waitVerify.CImageComparisonWaitVerify;
import org.catools.media.extensions.wait.interfaces.CImageComparisonWaiter;
import org.catools.media.utils.CImageUtil;

import static org.catools.media.utils.CImageUtil.toBufferedImage;

/**
 * CImageComparisonExtension is an central interface where we extend all boolean related interfaces
 * so adding new functionality will be much easier. <strong>
 *
 * <p>Java does not allow to override Object methods in interface level so this is something we
 * should care about it manually.
 *
 * <p>Make sure to override equals, hashCode (and if needed toString methods) in your
 * implementations. <code>
 *
 * @Override public int hashCode() {
 * return getValue().hashCode();
 * }
 * @Override public boolean equals(Object obj) {
 * return isEqual(obj);
 * }
 * </code> </strong>
 */
public interface CImageComparisonExtension<O> extends CImageComparisonWaiter<O>, CImageComparisonWaitVerify<O>, CImageComparisonState<O> {

  /**
   * Save screenshot using specific format to the specified file
   *
   * @param formatName
   * @param file
   * @return
   */
  default CFile saveAs(String formatName, CFile file) {
    if (_get() == null) {
      return null;
    }
    CImageUtil.writeImage(toBufferedImage(_get()), formatName, file);
    return file;
  }

  /**
   * Save screenshot to PNG file and return file
   *
   * @return PNG file
   */
  default CFile saveAsPng() {
    return saveAs("png", CFile.fromTmp(CDate.now().toTimeStampForFileName() + ".png"));
  }

  /**
   * Save screenshot to the specific PNG file and return file
   *
   * @return PNG file
   */
  default CFile saveAsPng(CFile file) {
    return saveAs("png", file);
  }

  /**
   * return screenshot bytes
   *
   * @return screenshot bytes
   */
  default byte[] getBytes() {
    return CImageUtil.getBytes(toBufferedImage(_get()), "png");
  }

  /**
   * return screenshot as base64 string
   *
   * @return base64 string
   */
  default String getBase64() {
    return CImageUtil.getBase64(toBufferedImage(_get()), "png");
  }

}

package org.catools.media.tests.utils;

import boofcv.factory.template.TemplateScoreType;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.common.testng.utils.CRetryAnalyzer;
import org.catools.common.tests.CTest;
import org.catools.media.utils.CBoofCVUtil;
import org.catools.media.utils.CImageUtil;
import org.catools.media.utils.CTemplateMatchingUtil;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;

public class CTemplateMatchingUtilTest extends CTest {

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void testGetMatch() {
    BufferedImage frog =
        CImageUtil.readImageOrNull(new CResource("testData/frog.jpg", CImageUtilTest.class));
    BufferedImage frogEye =
        CImageUtil.readImageOrNull(new CResource("testData/frog_eye.jpg", CImageUtilTest.class));

    BufferedImage match = CTemplateMatchingUtil.getMatch(CBoofCVUtil.toGrayF32(frog), CBoofCVUtil.toGrayF32(frogEye), null, TemplateScoreType.NCC, 1);
    CImageUtil.writePNG(match, CFile.fromOutput("diff.png"));
  }
}
package org.catools.media.tests.utils;

import boofcv.factory.template.TemplateScoreType;
import boofcv.struct.feature.Match;
import org.catools.common.collections.CList;
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
  public void testGetMatchWithLossPrecision() {
    BufferedImage frog = CImageUtil.readImageOrNull(new CResource("testData/frog.jpg"));
    BufferedImage frogEye = CImageUtil.readImageOrNull(new CResource("testData/frog_eye.jpg"));

    CList<Match> matches = CTemplateMatchingUtil.findMatches(
        CBoofCVUtil.toGrayF32(frog),
        CBoofCVUtil.toGrayF32(frogEye),
        null,
        TemplateScoreType.NCC,
        1,
        50);

    matches.verifySizeEquals(1);
    matches.verifyHas(m -> m.score > 0.99);
  }

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void testGetBestMatchWithMaxNumber() {
    BufferedImage frog = CImageUtil.readImageOrNull(new CResource("testData/frog.jpg"));
    BufferedImage frogEye = CImageUtil.readImageOrNull(new CResource("testData/frog_eye.jpg"));

    CList<Match> matches = CTemplateMatchingUtil.findMatches(
        CBoofCVUtil.toGrayF32(frog),
        CBoofCVUtil.toGrayF32(frogEye),
        null,
        TemplateScoreType.NCC,
        10,
        99);

    matches.verifySizeEquals(1);
    matches.verifyHas(m -> m.score > 0.99);
  }
}
package org.catools.media.tests.utils;

import org.catools.common.extensions.verify.CVerify;
import org.catools.common.io.CFile;
import org.catools.common.io.CResource;
import org.catools.common.testng.utils.CRetryAnalyzer;
import org.catools.common.tests.CTest;
import org.catools.media.utils.CImageComparisonUtil;
import org.catools.media.utils.CImageUtil;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;

import static org.catools.media.enums.CImageComparisonType.GRAY_FLOAT_32;

public class CImageUtilTest extends CTest {

  @Test(retryAnalyzer = CRetryAnalyzer.class)
  public void testDiff() {
    BufferedImage frog1 =
        CImageUtil.readImageOrNull(new CResource("testData/frog.jpg", CImageUtilTest.class));
    BufferedImage frog2 =
        CImageUtil.readImageOrNull(new CResource("testData/frog2.jpg", CImageUtilTest.class));

    CVerify.Collection.isEmpty(
        CImageComparisonUtil.getDiffs(frog1, frog1, GRAY_FLOAT_32),
        "Compare found no differences");
    CVerify.Int.equals(
        CImageComparisonUtil.getDiffs(frog2, frog1, GRAY_FLOAT_32).size(),
        6,
        "Compare defined differences");
    CVerify.Int.equals(
        CImageComparisonUtil.getDiffs(frog2, frog1, CFile.fromTmp("diff.png"), GRAY_FLOAT_32)
            .size(),
        6,
        "Compare defined differences");
  }
}

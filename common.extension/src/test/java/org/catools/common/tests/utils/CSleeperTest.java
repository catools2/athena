package org.catools.common.tests.utils;

import org.catools.common.date.CDate;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.utils.CSleeper;
import org.testng.annotations.Test;

public class CSleeperTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSleepTightInSeconds() {
    CDate start = CDate.now();
    CSleeper.sleepTightInSeconds(1);
    CVerify.Long.betweenInclusive(
        start.getDurationToNow().getSeconds(), 0L, 3L, "Sleeper wait correct amount of time");
  }
}

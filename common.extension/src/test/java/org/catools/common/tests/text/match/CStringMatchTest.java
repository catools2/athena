package org.catools.common.tests.text.match;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.text.match.CStringMatch;
import org.catools.common.text.match.CStringMatchInfo;
import org.testng.annotations.Test;

public class CStringMatchTest extends CBaseUnitTest {
  private final CList<String> options =
      CList.of(
          "123456789qwertyuiop!@#$%^&*()",
          "12345789qwertyuiop!@#$%^&*()",
          "123456789qwrtyuiop!@#$%^&*()",
          "123456789qwertyuiop!@#$^&*()",
          "1234589qwertuiop!#$%^&()",
          "123456789qwertyiop!@$%^&*()");

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetBestMatch() {
    CStringMatchInfo<String> matche = CStringMatch.getBestMatch("12345", options, s -> s, 10);
    CVerify.String.equals(matche.getMatch(), "1234589qwertuiop!#$%^&()", "correct record found.");
    CVerify.Double.betweenInclusive(matche.getPercent(), 20D, 21D, "correct % calculated.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetMatches() {
    CList<CStringMatchInfo<String>> matches = CStringMatch.getMatches("12345", options, s -> s, 20);
    matches.verifySizeEquals(1);
    CVerify.String.equals(
        matches.getFirst().getMatch(), "1234589qwertuiop!#$%^&()", "correct record found.");
    CVerify.Double.betweenInclusive(
        matches.getFirst().getPercent(), 20D, 21D, "correct % calculated.");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testGetMatch() {
    CStringMatchInfo<String> matche =
        CStringMatch.getMatch("12345", "1234589qwertuiop!#$%^&()", s -> s, 20);
    CVerify.String.equals(matche.getMatch(), "1234589qwertuiop!#$%^&()", "correct record found.");
    CVerify.Double.betweenInclusive(matche.getPercent(), 20D, 21D, "correct % calculated.");
  }
}

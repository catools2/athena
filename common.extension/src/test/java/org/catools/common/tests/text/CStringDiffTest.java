package org.catools.common.tests.text;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.text.match.CStringDiff;
import org.testng.annotations.Test;

import java.util.LinkedList;

public class CStringDiffTest extends CBaseUnitTest {

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testDiff_NoDiff() {
    LinkedList<DiffMatchPatch.Diff> diff = CStringDiff.diff("sometext to be used as the origin", "sometext to be used as the origin");
    CVerify.Int.equals(diff.size(), 1, "Number of diff matched");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testDiff() {
    LinkedList<DiffMatchPatch.Diff> diff = CStringDiff.diff("sometext to be used as the origin", "some other text to be used for match with the origin");
    CVerify.Int.equals(diff.size(), 8, "Number of diff matched");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testPrettyDiff() {
    String diff = CStringDiff.prettyDiff("sometext to be used as the origin", "some other text to be used for match with the origin");
    CVerify.String.equals(diff, "some|(+) other |text to be used |(+)for m|a|(-)s||(+)tch with| the origin", "Diff string matched");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testColoredDiff() {
    String diff = CStringDiff.coloredDiff("sometext to be used as the origin", "some other text to be used for match with the origin");
    CVerify.String.equals(diff, "\u001B[1;39;49;0m\u001B[m\u001B[1;39;49;1msome\u001B[m\u001B[1;33;49;1m other \u001B[m\u001B[1;39;49;1mtext to be used \u001B[m\u001B[1;33;49;1mfor m\u001B[m\u001B[1;39;49;1ma\u001B[m\u001B[1;31;49;1ms\u001B[m\u001B[1;33;49;1mtch with\u001B[m\u001B[1;39;49;1m the origin\u001B[m\u001B[1;39;49;0m\u001B[m", "Diff string matched");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testColoredDiff_NoDiff() {
    String diff = CStringDiff.coloredDiff("", "");
    CVerify.String.equals(diff, "", "Diff string matched");
  }
}

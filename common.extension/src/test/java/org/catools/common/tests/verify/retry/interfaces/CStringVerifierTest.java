package org.catools.common.tests.verify.retry.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.interfaces.verifier.CStringVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

@Slf4j
@Test(retryAnalyzer = CTestRetryAnalyzer.class)
public class CStringVerifierTest extends CBaseUnitTest {

  private static CStringVerify toCS(String s) {
    return new CStringVerify() {
      @Override
      public String _get() {
        return s;
      }
    };
  }

  public static class WithIntervalCenterPadEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testVerifyEquals() {
      toCS("  some string ").verifyCenterPadEquals(40, "", "               some string              ", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadEquals(29, "&%", "&%&%&%&  SOM@#$%^& o &%&%&%&%", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadEquals(20, "#$%^", "#$%  SOM@#$%^& o #$%", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadEquals(10, "", "  SOM@#$%^& o ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCenterPadEquals(40, "", "               some string              ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string ").verifyCenterPadEquals(40, "", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string ").verifyCenterPadEquals(40, "", "             some string              ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalCenterPadNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string ").verifyCenterPadNotEquals(40, "", "              some string              ", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadNotEquals(20, "#$%^", "#$%  SOM@$%^& o #$%", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadNotEquals(10, "", "  SOM@#$%^& o", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCenterPadNotEquals(40, "", "              some string              ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string ").verifyCenterPadNotEquals(40, "", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string ").verifyCenterPadNotEquals(40, "", "               some string              ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalCompareTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyCompare("  some string    ", 0, "%s#%s", getParams());
      toCS("  SOME string    ").verifyCompare("  some string    ", -32, "%s#%s", getParams());
      toCS(null).verifyCompare(null, 0, "%s#%s", getParams());
      toCS("  some string    ").verifyCompare("  some String    ", 32, "%s#%s", getParams());
      toCS("  some string    ").verifyCompare(null, 1, "%s#%s", getParams());
      toCS(null).verifyCompare("  some string    ", -1, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCompare("  some string    ", 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyCompare(null, 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  SOME string    ").verifyCompare("  some string    ", -1, "%s#%s", getParams());
    }
  }

  public static class WithIntervalCompareIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyCompareIgnoreCase("  SOME string    ", 0, "%s#%s", getParams());
      toCS("  SOME string    ").verifyCompareIgnoreCase("  some string    ", 0, "%s#%s", getParams());
      toCS(null).verifyCompareIgnoreCase(null, 0, "%s#%s", getParams());
      toCS("  some string    ").verifyCompareIgnoreCase("  some xtring    ", -5, "%s#%s", getParams());
      toCS("  some string    ").verifyCompareIgnoreCase(null, 1, "%s#%s", getParams());
      toCS(null).verifyCompareIgnoreCase("  some string    ", -1, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCompareIgnoreCase("  SOME string    ", 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyCompareIgnoreCase(null, 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyCompareIgnoreCase("SOME string    ", 0, "%s#%s", getParams());
    }
  }

  public static class WithIntervalContainsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyContains("so", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyContains("so", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyContains(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyContains("sox", "%s#%s", getParams());
    }
  }

  public static class WithIntervalContainsIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  Some string    ").verifyContainsIgnoreCase(" so", "%s#%s", getParams());
      toCS("  some $tring    ").verifyContainsIgnoreCase("$TRING", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyContainsIgnoreCase(" so", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  Some string    ").verifyContainsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  Some string    ").verifyContainsIgnoreCase(" sox", "%s#%s", getParams());
    }
  }

  public static class WithIntervalEndsWithTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWith("   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWith("   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWith(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWith(" a s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalEndsWithAnyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWithAny(new CList<>("A", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWithAny(new CList<>("A", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWithAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWithAny(new CList<>("A", null, " sx "), "%s#%s", getParams());
    }
  }

  public static class WithIntervalEndsWithIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWithIgnoreCase("   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyEndsWithIgnoreCase("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWithIgnoreCase("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWithIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWithIgnoreCase("   SX ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyEndsWithNoneTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWithNone(new CList<>("A", null, " S "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWithNone(new CList<>("A", null, " S "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWithNone(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWithNone(new CList<>("A", null, " s "), "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEquals("  some string    ", "%s#%s", getParams());
      toCS(null).verifyEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEquals("  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEquals("  some string ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyEqualsAnyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsAny(new CList<>("", "  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsAny(new CList<>("", "  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsAny(new CList<>("", "  sometring    "), "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyEqualsAnyIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  some string    "), "%s#%s", getParams());
      toCS("  some STRING    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  SOME string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsAnyIgnoreCase(new CList<>("", "  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsAnyIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  somestring    "), "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyEqualsIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsIgnoreCase("  SOME string    ", "%s#%s", getParams());
      toCS(null).verifyEqualsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsIgnoreCase("  SOME string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsIgnoreCase("  SOME string", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyEqualsIgnoreWhiteSpacesTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  SOME string    ").verifyEqualsIgnoreWhiteSpaces("SO ME st ring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsIgnoreWhiteSpaces("SO ME st ring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  SOME string    ").verifyEqualsIgnoreWhiteSpaces(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  SOME string    ").verifyEqualsIgnoreWhiteSpaces("SME st ring    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyEqualsNoneTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsNone(new CList<>("", "  some", "string    "), "%s#%s", getParams());
      toCS("  some STRING    ").verifyEqualsNone(new CList<>("", "  SOME string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsNone(new CList<>("", "  some", "string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsNone(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsNone(new CList<>("", "some", "  some string    "), "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyEqualsNoneIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "string    "), "%s#%s", getParams());
      toCS("  some STRING    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  $OME string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsNoneIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "  some string    "), "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulajksn").verifyIsAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiu1!lajksn").verifyIsAlpha("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsEmptyOrAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulajksn").verifyIsEmptyOrAlpha("%s#%s", getParams());
      toCS("").verifyIsEmptyOrAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiu1 lajksn").verifyIsEmptyOrAlpha("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsAlphaSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(" aiu ajk sn").verifyIsAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1 aiu ajk sn").verifyIsAlphaSpace("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj45872ksn1").verifyIsAlphanumeric("%s#%s", getParams());
      toCS("blkajsblas").verifyIsAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testInvalidChar() {
      toCS("aiulaj4\u5872ksn1").verifyIsAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj4 5872ksn1").verifyIsAlphanumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsEmptyOrAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265opksn").verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
      toCS("").verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6!265opksn").verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsAlphanumericSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jksn").verifyIsAlphanumericSpace("%s#%s", getParams());
      toCS(" ai1ul90 ajk sn").verifyIsAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aksaskjhas654!").verifyIsAlphanumericSpace("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsAsciiPrintableTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 32;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
      chars[5] = 33;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
      chars[5] = 125;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
      chars[5] = 126;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNotAsciiPrintableTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      toCS(new String(chars)).verifyIsNotAsciiPrintable("%s#%s", getParams());
      chars[5] = 127;
      toCS(new String(chars)).verifyIsNotAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("asasa").verifyIsNotAsciiPrintable("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsBlankTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(null).verifyIsBlank("%s#%s", getParams());
      toCS("").verifyIsBlank("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("A").verifyIsBlank("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsEmptyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("").verifyIsEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyIsEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("s").verifyIsEmpty("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNotAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiu1lajks1n").verifyIsNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("alskdla").verifyIsNotAlpha("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsEmptyOrNotAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("a1").verifyIsEmptyOrNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("abs").verifyIsEmptyOrNotAlpha("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNotAlphaSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1aiul ajk sn").verifyIsNotAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("sdfghjk").verifyIsNotAlphaSpace("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNotAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj4587 2ksn1").verifyIsNotAlphanumeric("%s#%s", getParams());
      toCS("blkajsbla!s").verifyIsNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj45872ksn1").verifyIsNotAlphanumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsEmptyOrNotAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265!opksn").verifyIsEmptyOrNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6265opksn").verifyIsEmptyOrNotAlphanumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNotAlphanumericSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jks!n").verifyIsNotAlphanumericSpace("%s#%s", getParams());
      toCS("ai1ul90jks !").verifyIsNotAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("ai1ul9 0jksn").verifyIsNotAlphanumericSpace("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNotBlankTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jks !").verifyIsNotBlank("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotBlank("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("").verifyIsNotBlank("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNotEmptyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jks !").verifyIsNotEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("").verifyIsNotEmpty("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNotNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567A").verifyIsNotNumeric("%s#%s", getParams());
      toCS("").verifyIsNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234567").verifyIsNotNumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsEmptyOrNotNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("12345A67").verifyIsEmptyOrNotNumeric("%s#%s", getParams());
      toCS("A").verifyIsEmptyOrNotNumeric("%s#%s", getParams());
      toCS("").verifyIsEmptyOrNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234567").verifyIsEmptyOrNotNumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNotNumericSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("234567!8").verifyIsNotNumericSpace("%s#%s", getParams());
      toCS(" 1254 7A86 1").verifyIsNotNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("2345678").verifyIsNotNumericSpace("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567").verifyIsNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("A1234567").verifyIsNumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsEmptyOrNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567").verifyIsEmptyOrNumeric("%s#%s", getParams());
      toCS("").verifyIsEmptyOrNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234A567").verifyIsEmptyOrNumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsNumericSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("2345678").verifyIsNumericSpace("%s#%s", getParams());
      toCS(" 1254 786 1").verifyIsNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("2345A678").verifyIsNumericSpace("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyLeftValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyLeftValueEquals(7, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftValueEquals(7, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyLeftValueEquals(7, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyLeftValueEquals(7, "some ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyLeftValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyLeftValueNotEquals(3, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftValueNotEquals(3, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyLeftValueNotEquals(3, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyLeftValueNotEquals(7, "  some ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyLeftPadEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyLeftPadEquals(30, "", "              some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyLeftPadEquals(10, null, "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyLeftPadEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyLeftPadNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyLeftPadNotEquals(10, null, "  some string  s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyLeftPadNotEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyLengthTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLengthEquals(18, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLengthEquals(18, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLengthEquals(17, "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyLengthNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLengthNotEquals(17, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyLengthNotEquals(17, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLengthNotEquals(18, "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyMidValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyMidValueEquals(2, 4, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMidValueEquals(2, 4, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyMidValueEquals(2, 4, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyMidValueEquals(2, 3, "some", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyMidValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyMidValueNotEquals(2, 5, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMidValueNotEquals(2, 5, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyMidValueNotEquals(2, 5, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyMidValueNotEquals(2, 4, "some", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNotContainsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotContains("sO", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotContains("sO", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotContains(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotContains("som", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNotContainsIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  Some string    ").verifyNotContainsIgnoreCase(" sX", "%s#%s", getParams());
      toCS("  some $tring    ").verifyNotContainsIgnoreCase("STRING", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotContainsIgnoreCase("STRING", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some $tring    ").verifyNotContainsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some $tring    ").verifyNotContainsIgnoreCase("TRING", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNotEndsWithTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotEndsWith("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotEndsWith("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotEndsWith(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotEndsWith("   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNotEndsWithIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotEndsWithIgnoreCase("   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotEndsWithIgnoreCase("   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotEndsWithIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotEndsWithIgnoreCase("tring   S ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotEquals("  some STRING    ", "%s#%s", getParams());
      toCS("  some string    ").verifyNotEquals("  some String   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyNotEquals("  some STRING    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotEquals("  some string    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNotEqualsIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotEqualsIgnoreCase("  SOME tring    ", "%s#%s", getParams());
      toCS(null).verifyNotEqualsIgnoreCase("", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyNotEqualsIgnoreCase("  SOME tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotEqualsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotEqualsIgnoreCase("  some STRING    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNotEqualsIgnoreWhiteSpacesTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  SOME string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyNotEqualsIgnoreWhiteSpaces("  SOME string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  some string    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNotStartsWithTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotStartsWith("  S", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotStartsWith("  S", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotStartsWith(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotStartsWith("  s", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNotStartsWithIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotStartsWithIgnoreCase("A", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotStartsWithIgnoreCase("A", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotStartsWithIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotStartsWithIgnoreCase("  ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNumberOfMatchesEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNumberOfMatchesEquals("s", 3, "%s#%s", getParams());
      toCS("  some String   s ").verifyNumberOfMatchesEquals("s", 2, "%s#%s", getParams());
      toCS("  some $tring   s ").verifyNumberOfMatchesEquals("$", 1, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNumberOfMatchesEquals("s", 3, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNumberOfMatchesEquals(null, 3, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNumberOfMatchesEquals("s", 1, "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyNumberOfMatchesNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNumberOfMatchesNotEquals("s", 2, "%s#%s", getParams());
      toCS("  some String   s ").verifyNumberOfMatchesNotEquals("s", 1, "%s#%s", getParams());
      toCS("  some $tring   s ").verifyNumberOfMatchesNotEquals("$", 3, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNumberOfMatchesNotEquals("s", 2, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNumberOfMatchesNotEquals(null, 2, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNumberOfMatchesNotEquals("s", 3, "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEquals("s", "  ome tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyRemoveEquals("so", "  me String    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEquals("s", "  ome tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEquals("s", "  ome string    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveEndEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndEquals("  some ", "  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndEquals("some string   s ", "  ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndEquals("  some string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndEquals(null, "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndEquals("tring   s ", "  some S", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveEndEquals("tring   s ", "  some $", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndEquals("  some ", "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndEquals("  some ", "some string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveEndIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("some String   s ", "  ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  sOME string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndIgnoreCaseEquals(null, "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndIgnoreCaseEquals("tring   S ", "  some S", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveEndIgnoreCaseEquals("TRING   s ", "  some $", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveEndIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndIgnoreCaseNotEquals(null, "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  Some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  some ", "  some string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveEndNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndNotEquals("  some ", "ome string   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndNotEquals(null, "  some String   S", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndNotEquals("tring   S ", "  some s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndNotEquals("  some ", "ome string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndNotEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndNotEquals("  some ", "  some string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseEquals("s", "  ome tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyRemoveIgnoreCaseEquals("SO", "  me String    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveIgnoreCaseEquals("s", "  ome tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseEquals("s", "  ome trng    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyRemoveIgnoreCaseNotEquals("SO", "  me String    x", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveIgnoreCaseNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", "  ome tring    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyRemoveNotEquals(null, "  me String    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveNotEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveNotEquals("s", "  ome tring    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveStartEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartEquals("  some ", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartEquals("some string   s ", "  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartEquals("  some string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartEquals(null, "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartEquals("  some S", "tring   s ", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveStartEquals("  some $", "tring   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartEquals("  some ", "string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartEquals("  some ", "string s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveStartIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some ", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("Some string   s ", "  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartIgnoreCaseEquals(null, "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartIgnoreCaseEquals("  some s", "tring   s ", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveStartIgnoreCaseEquals("  some $", "tring   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartIgnoreCaseEquals("  some ", "string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "string s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveStartIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", "String   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  Some ", "string  s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartIgnoreCaseNotEquals("  some ", "String   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testRemoveNull() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals(null, "string  s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", "string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRemoveStartNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartNotEquals("  some", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartNotEquals(null, " Some string   s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartNotEquals("  some", "string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartNotEquals("  some", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartNotEquals("  some ", "string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReplaceEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceEquals("s", "", "  ome tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceEquals("so", "XX", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   S ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReplaceIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceIgnoreCaseEquals("s", "|", "  |ome |tring   | ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  some String   x ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReplaceIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceIgnoreCaseNotEquals("s", "|", "  |ome string   | ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceIgnoreCaseNotEquals("SO", "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceIgnoreCaseNotEquals("s", "|", "  |ome |tring   | ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReplaceNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceNotEquals("s", "", "  ome String    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceNotEquals("so", "XX", "  XXme XXtring   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceNotEquals("so", "XX", "  XXme XXtring   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceNotEquals("so", "XX", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceNotEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReplaceOnceEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceEquals("s", "", "  ome string   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  XXme String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceEquals("so", "XX", "  XXme String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceOnceEquals("so", "XX", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  Xome String   so ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReplaceOnceIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceIgnoreCaseEquals("SO", "x", "  xme String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |some string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReplaceOnceIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals("s", "|", "  \\|ome string   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals("s", "|", "  |ome string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReplaceOnceNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceNotEquals("s", "", "  ome String   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceNotEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceNotEquals("s", "", "  ome String   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyReplaceOnceNotEquals("s", "", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceOnceNotEquals("s", "", "  ome string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReverseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReverseEquals(" s   gnirts emos  ", "%s#%s", getParams());
      toCS("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReverseEquals(" os   .*&^%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some @#$%^&*.   so ").verifyReverseEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%# emos  ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyReverseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReverseNotEquals(" s   gnirt emos  ", "%s#%s", getParams());
      toCS("  some @#$%^&*.   so ").verifyReverseNotEquals(" os   .*%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReverseNotEquals(" os   .*%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some @#$%^&*.   so ").verifyReverseNotEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReverseNotEquals(" s   gnirts emos  ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRightValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyRightValueEquals(7, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightValueEquals(7, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyRightValueEquals(7, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyRightValueEquals(7, "ing   ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRightValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyRightValueNotEquals(6, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightValueNotEquals(6, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyRightValueNotEquals(6, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyRightValueNotEquals(7, "ing    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRightPadEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
      toCS("  some string   s ").verifyRightPadEquals(30, "", "  some string   s             ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRightPadEquals(10, null, "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRightPadEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRightPadEquals(40, "x", "  some string   s ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyRightPadNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
      toCS("  some string   s ").verifyRightPadNotEquals(10, null, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRightPadNotEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStartsWithTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWith("  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWith("  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWith(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWith("some", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStartsWithAnyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWithAny(new CList<>("A", null, "  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWithAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "some"), "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStartsWithIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWithIgnoreCase("  some", "%s#%s", getParams());
      toCS("  some string   s ").verifyStartsWithIgnoreCase("  Some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWithIgnoreCase("  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWithIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWithIgnoreCase(" some", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStartsWithNoneTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWithNone(new CList<>("A", null, "  Some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWithNone(new CList<>("A", null, "  Some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWithNone(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWithNone(new CList<>("A", "  some", " Some"), "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStripedEndValueTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedEndValue(" ", "  some string", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedEndValue(null, "  some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValue("|", "|some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValue(null, "|some string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedEndValue("|", "|some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedEndValue("|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedEndValue("|", "|som string", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStripedEndValueNotTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedEndValueNot(" ", "  ome string", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedEndValueNot(null, "  ome string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValueNot("|", "|som string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValueNot(null, "|soe string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedEndValueNot("|", "|som string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedEndValueNot("|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedEndValueNot("|", "|some string", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStripedStartValueTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedStartValue(" ", "some string    ", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedStartValue(null, "some string    ", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValue("|", "some string||||", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValue(null, "|some string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedStartValue("|", "some string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedStartValue("|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedStartValue("|", "some string|", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStripedStartValueNotTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedStartValueNot(" ", "ome string    ", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedStartValueNot(null, "ome string    ", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValueNot("|", "ome string||||", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValueNot(null, "|ome string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedStartValueNot(" ", "ome string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyStripedStartValueNot(" ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyStripedStartValueNot(" ", "some string    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStripedValueTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedValue(" ", "some string", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedValue(null, "some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValue("|", "some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValue(null, "|some string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedValue("|", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedValue("|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedValue("|", "some String", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyStripedValueNotTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedValueNot(" ", "somestring", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedValueNot(null, "som string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValueNot("|", "somestring", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValueNot(null, "|soe string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedValueNot(" ", "somestring", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyStripedValueNot(" ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyStripedValueNot(" ", "some string", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringEquals(0, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringEquals(0, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringEquals(0, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringEquals(0, "  some string", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringEqualsWithEndTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringEquals(0, 3, "  s", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringEquals(2, 4, "so", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringEquals(0, 3, "  s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringEquals(0, 3, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringEquals(0, 3, "  some", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringAfterEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterEquals(" s", "ome string    ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterEquals(null, "", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterEquals(" s", "ome string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterEquals(" s", "Some string    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringAfterLastEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterLastEquals(" s", "tring    ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterLastEquals(null, "", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterLastEquals(" s", "tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterLastEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterLastEquals(" s", "String    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringAfterLastNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterLastNotEquals(" s", "trng    ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterLastNotEquals(null, "something", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterLastNotEquals(" s", "trng    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterLastNotEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterLastNotEquals(" s", "tring    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringAfterNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterNotEquals(" s", "ome string   ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterNotEquals(null, "X", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterNotEquals(" s", "ome string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterNotEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterNotEquals(" s", "ome string    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringBeforeEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeEquals(" st", "  some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeEquals(null, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeEquals(" st", "  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeEquals(" st", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeEquals(" st", "some", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringBeforeLastEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeLastEquals(" s", "  some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeLastEquals(null, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeLastEquals(" s", "  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeLastEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeLastEquals(" s", "some", "%s#%s", getParams());
    }
  }

  public static class WithIntervalSubstringBeforeLastNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeLastNotEquals(" s", " some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeLastNotEquals(null, " some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeLastNotEquals("s", " some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeLastNotEquals("S ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeLastNotEquals(null, "  some string    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringBeforeNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeNotEquals(" st", " some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeNotEquals(null, "  some string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeNotEquals(" st", " some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeNotEquals(" st", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeNotEquals(" st", "  some", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringBetweenEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", "    ", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBetweenEquals("  ", "    ", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string    ").verifySubstringBetweenEquals(null, "    ", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", null, "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", "    ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", "    ", "sme string", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringBetweenNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "sme string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBetweenNotEquals("  ", "    ", "sme string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "some string", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringNotEquals(0, " some string    ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringNotEquals(2, "ome string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringNotEquals(0, " some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringNotEquals(0, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringNotEquals(0, "  some string    ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifySubstringNotEqualsWithEndTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringNotEquals(0, 3, " s", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringNotEquals(2, 4, "sox", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringNotEquals(0, 3, " s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringNotEquals(0, 3, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringNotEquals(0, 3, "  s", "%s#%s", getParams());
    }
  }

  public static class WithIntervalSubstringsBetweenEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(null, "s", new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", null, new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegativeOnSize() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", ""), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", " "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative2() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  ", " "), "%s#%s", getParams());
    }
  }

  public static class WithIntervalSubstringsBetweenContainsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", "s", " ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenContains(" ", "s", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenContains(null, "s", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", null, "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", "s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", "s", "some string", "%s#%s", getParams());
    }
  }

  public static class WithIntervalSubstringsBetweenNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(null, "s", new CList<>(" ", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", null, new CList<>(" ", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "", "  "), "%s#%s", getParams());
    }
  }

  public static class WithIntervalSubstringsBetweenNotContainsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains(" ", "s", "some string", "%s#%s", getParams());
      toCS("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenNotContains("some ", "ing", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains(null, "s", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains(" ", null, "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", "str", "%s#%s", getParams());
    }
  }

  public static class WithIntervalTrimmedValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTrimmedValueEquals("some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTrimmedValueEquals("some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTrimmedValueEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTrimmedValueEquals("some st$ng", "%s#%s", getParams());
    }
  }

  public static class WithIntervalTrimmedValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTrimmedValueNotEquals("some strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTrimmedValueNotEquals("some strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTrimmedValueNotEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTrimmedValueNotEquals("some string", "%s#%s", getParams());
    }
  }

  public static class WithIntervalTruncatedValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueEquals(10, "some strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueEquals(4, " string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueEquals(4, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueEquals(5, " string   ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalTruncatedValueEqualsWithEndTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueEquals(4, 10, " string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueEquals(4, 10, " string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueEquals(4, 10, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueEquals(5, 10, " string   ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalTruncatedValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueNotEquals(10, "ome strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueNotEquals(10, "ome strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueNotEquals(10, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueNotEquals(5, "some ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalTruncatedValueNotEqualsWithEndTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueNotEquals(4, 10, " tring   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueNotEquals(4, 10, " tring   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueNotEquals(4, 10, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueNotEquals(4, 10, " string   ", "%s#%s", getParams());
    }
  }

  public static class WithIntervalIsMatchesTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyMatches("[a-z ]+", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMatches(" tring   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyMatches((String) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyMatches("\\d+", "%s#%s", getParams());
    }
  }

  public static class WithIntervalIsMatchesPatternTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyMatches(Pattern.compile("[a-z ]+"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMatches(Pattern.compile("[a-z ]+"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyMatches((Pattern) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyMatches(Pattern.compile("^[A-Z ]+$"), "%s#%s", getParams());
    }
  }

  public static class WithIntervalIsNotMatchesTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyNotMatches("^[A-Z ]+$", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotMatches(" tring   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyNotMatches((String) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyNotMatches("[a-z ]+", "%s#%s", getParams());
    }
  }

  public static class WithIntervalIsNotMatchesPatternTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyNotMatches(Pattern.compile("^[A-Z ]+$"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotMatches(Pattern.compile("[a-z ]+"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyNotMatches((Pattern) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyNotMatches(Pattern.compile("[a-z ]+"), "%s#%s", getParams());
    }
  }

  public static class CenterPadEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testVerifyEquals() {
      toCS("  some string ").verifyCenterPadEquals(40, "", "               some string              ", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadEquals(29, "&%", "&%&%&%&  SOM@#$%^& o &%&%&%&%", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadEquals(20, "#$%^", "#$%  SOM@#$%^& o #$%", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadEquals(10, "", "  SOM@#$%^& o ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCenterPadEquals(40, "", "               some string              ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string ").verifyCenterPadEquals(40, "", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string ").verifyCenterPadEquals(40, "", "             some string              ", "%s#%s", getParams());
    }
  }

  public static class CenterPadNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string ").verifyCenterPadNotEquals(40, "", "              some string              ", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadNotEquals(20, "#$%^", "#$%  SOM@$%^& o #$%", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadNotEquals(10, "", "  SOM@#$%^& o", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCenterPadNotEquals(40, "", "              some string              ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string ").verifyCenterPadNotEquals(40, "", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string ").verifyCenterPadNotEquals(40, "", "               some string              ", "%s#%s", getParams());
    }
  }

  public static class CompareTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyCompare("  some string    ", 0, "%s#%s", getParams());
      toCS("  SOME string    ").verifyCompare("  some string    ", -32, "%s#%s", getParams());
      toCS(null).verifyCompare(null, 0, "%s#%s", getParams());
      toCS("  some string    ").verifyCompare("  some String    ", 32, "%s#%s", getParams());
      toCS("  some string    ").verifyCompare(null, 1, "%s#%s", getParams());
      toCS(null).verifyCompare("  some string    ", -1, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCompare("  some string    ", 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyCompare(null, 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  SOME string    ").verifyCompare("  some string    ", -1, "%s#%s", getParams());
    }
  }

  public static class CompareIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyCompareIgnoreCase("  SOME string    ", 0, "%s#%s", getParams());
      toCS("  SOME string    ").verifyCompareIgnoreCase("  some string    ", 0, "%s#%s", getParams());
      toCS(null).verifyCompareIgnoreCase(null, 0, "%s#%s", getParams());
      toCS("  some string    ").verifyCompareIgnoreCase("  some xtring    ", -5, "%s#%s", getParams());
      toCS("  some string    ").verifyCompareIgnoreCase(null, 1, "%s#%s", getParams());
      toCS(null).verifyCompareIgnoreCase("  some string    ", -1, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCompareIgnoreCase("  SOME string    ", 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyCompareIgnoreCase(null, 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyCompareIgnoreCase("SOME string    ", 0, "%s#%s", getParams());
    }
  }

  public static class ContainsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyContains("so", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyContains("so", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyContains(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyContains("sox", "%s#%s", getParams());
    }
  }

  public static class ContainsIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  Some string    ").verifyContainsIgnoreCase(" so", "%s#%s", getParams());
      toCS("  some $tring    ").verifyContainsIgnoreCase("$TRING", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyContainsIgnoreCase(" so", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  Some string    ").verifyContainsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  Some string    ").verifyContainsIgnoreCase(" sox", "%s#%s", getParams());
    }
  }

  public static class EndsWithTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWith("   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWith("   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWith(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWith(" a s ", "%s#%s", getParams());
    }
  }

  public static class EndsWithAnyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWithAny(new CList<>("A", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWithAny(new CList<>("A", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWithAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWithAny(new CList<>("A", null, " sx "), "%s#%s", getParams());
    }
  }

  public static class EndsWithIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWithIgnoreCase("   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyEndsWithIgnoreCase("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWithIgnoreCase("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWithIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWithIgnoreCase("   SX ", "%s#%s", getParams());
    }
  }

  public static class VerifyEndsWithNoneTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWithNone(new CList<>("A", null, " S "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWithNone(new CList<>("A", null, " S "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWithNone(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWithNone(new CList<>("A", null, " s "), "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEquals("  some string    ", "%s#%s", getParams());
      toCS(null).verifyEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEquals("  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEquals("  some string ", "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsAnyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsAny(new CList<>("", "  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsAny(new CList<>("", "  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsAny(new CList<>("", "  sometring    "), "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsAnyIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  some string    "), "%s#%s", getParams());
      toCS("  some STRING    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  SOME string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsAnyIgnoreCase(new CList<>("", "  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsAnyIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  somestring    "), "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsIgnoreCase("  SOME string    ", "%s#%s", getParams());
      toCS(null).verifyEqualsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsIgnoreCase("  SOME string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsIgnoreCase("  SOME string", "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsIgnoreWhiteSpacesTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  SOME string    ").verifyEqualsIgnoreWhiteSpaces("SO ME st ring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsIgnoreWhiteSpaces("SO ME st ring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  SOME string    ").verifyEqualsIgnoreWhiteSpaces(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  SOME string    ").verifyEqualsIgnoreWhiteSpaces("SME st ring    ", "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsNoneTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsNone(new CList<>("", "  some", "string    "), "%s#%s", getParams());
      toCS("  some STRING    ").verifyEqualsNone(new CList<>("", "  SOME string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsNone(new CList<>("", "  some", "string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsNone(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsNone(new CList<>("", "some", "  some string    "), "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsNoneIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "string    "), "%s#%s", getParams());
      toCS("  some STRING    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  $OME string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsNoneIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "  some string    "), "%s#%s", getParams());
    }
  }

  public static class VerifyIsAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulajksn").verifyIsAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiu1!lajksn").verifyIsAlpha("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulajksn").verifyIsEmptyOrAlpha("%s#%s", getParams());
      toCS("").verifyIsEmptyOrAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiu1 lajksn").verifyIsEmptyOrAlpha("%s#%s", getParams());
    }
  }

  public static class VerifyIsAlphaSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(" aiu ajk sn").verifyIsAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1 aiu ajk sn").verifyIsAlphaSpace("%s#%s", getParams());
    }
  }

  public static class VerifyIsAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj45872ksn1").verifyIsAlphanumeric("%s#%s", getParams());
      toCS("blkajsblas").verifyIsAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testInvalidChar() {
      toCS("aiulaj4\u5872ksn1").verifyIsAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj4 5872ksn1").verifyIsAlphanumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265opksn").verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
      toCS("").verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6!265opksn").verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsAlphanumericSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jksn").verifyIsAlphanumericSpace("%s#%s", getParams());
      toCS(" ai1ul90 ajk sn").verifyIsAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aksaskjhas654!").verifyIsAlphanumericSpace("%s#%s", getParams());
    }
  }

  public static class VerifyIsAsciiPrintableTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 32;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
      chars[5] = 33;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
      chars[5] = 125;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
      chars[5] = 126;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAsciiPrintableTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      toCS(new String(chars)).verifyIsNotAsciiPrintable("%s#%s", getParams());
      chars[5] = 127;
      toCS(new String(chars)).verifyIsNotAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("asasa").verifyIsNotAsciiPrintable("%s#%s", getParams());
    }
  }

  public static class VerifyIsBlankTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(null).verifyIsBlank("%s#%s", getParams());
      toCS("").verifyIsBlank("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("A").verifyIsBlank("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("").verifyIsEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyIsEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("s").verifyIsEmpty("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiu1lajks1n").verifyIsNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("alskdla").verifyIsNotAlpha("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNotAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("a1").verifyIsEmptyOrNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("abs").verifyIsEmptyOrNotAlpha("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlphaSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1aiul ajk sn").verifyIsNotAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("sdfghjk").verifyIsNotAlphaSpace("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj4587 2ksn1").verifyIsNotAlphanumeric("%s#%s", getParams());
      toCS("blkajsbla!s").verifyIsNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj45872ksn1").verifyIsNotAlphanumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNotAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265!opksn").verifyIsEmptyOrNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6265opksn").verifyIsEmptyOrNotAlphanumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlphanumericSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jks!n").verifyIsNotAlphanumericSpace("%s#%s", getParams());
      toCS("ai1ul90jks !").verifyIsNotAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("ai1ul9 0jksn").verifyIsNotAlphanumericSpace("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotBlankTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jks !").verifyIsNotBlank("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotBlank("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("").verifyIsNotBlank("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotEmptyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jks !").verifyIsNotEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("").verifyIsNotEmpty("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567A").verifyIsNotNumeric("%s#%s", getParams());
      toCS("").verifyIsNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234567").verifyIsNotNumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNotNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("12345A67").verifyIsEmptyOrNotNumeric("%s#%s", getParams());
      toCS("A").verifyIsEmptyOrNotNumeric("%s#%s", getParams());
      toCS("").verifyIsEmptyOrNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234567").verifyIsEmptyOrNotNumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotNumericSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("234567!8").verifyIsNotNumericSpace("%s#%s", getParams());
      toCS(" 1254 7A86 1").verifyIsNotNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("2345678").verifyIsNotNumericSpace("%s#%s", getParams());
    }
  }

  public static class VerifyIsNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567").verifyIsNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("A1234567").verifyIsNumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567").verifyIsEmptyOrNumeric("%s#%s", getParams());
      toCS("").verifyIsEmptyOrNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234A567").verifyIsEmptyOrNumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsNumericSpaceTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("2345678").verifyIsNumericSpace("%s#%s", getParams());
      toCS(" 1254 786 1").verifyIsNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("2345A678").verifyIsNumericSpace("%s#%s", getParams());
    }
  }

  public static class VerifyLeftValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyLeftValueEquals(7, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftValueEquals(7, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyLeftValueEquals(7, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyLeftValueEquals(7, "some ", "%s#%s", getParams());
    }
  }

  public static class VerifyLeftValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyLeftValueNotEquals(3, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftValueNotEquals(3, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyLeftValueNotEquals(3, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyLeftValueNotEquals(7, "  some ", "%s#%s", getParams());
    }
  }

  public static class VerifyLeftPadEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyLeftPadEquals(30, "", "              some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyLeftPadEquals(10, null, "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyLeftPadEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyLeftPadNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyLeftPadNotEquals(10, null, "  some string  s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyLeftPadNotEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyLengthTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLengthEquals(18, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLengthEquals(18, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLengthEquals(17, "%s#%s", getParams());
    }
  }

  public static class VerifyLengthNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLengthNotEquals(17, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyLengthNotEquals(17, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLengthNotEquals(18, "%s#%s", getParams());
    }
  }

  public static class VerifyMidValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyMidValueEquals(2, 4, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMidValueEquals(2, 4, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyMidValueEquals(2, 4, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyMidValueEquals(2, 3, "some", "%s#%s", getParams());
    }
  }

  public static class VerifyMidValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyMidValueNotEquals(2, 5, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMidValueNotEquals(2, 5, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyMidValueNotEquals(2, 5, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyMidValueNotEquals(2, 4, "some", "%s#%s", getParams());
    }
  }

  public static class VerifyNotContainsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotContains("sO", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotContains("sO", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotContains(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotContains("som", "%s#%s", getParams());
    }
  }

  public static class VerifyNotContainsIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  Some string    ").verifyNotContainsIgnoreCase(" sX", "%s#%s", getParams());
      toCS("  some $tring    ").verifyNotContainsIgnoreCase("STRING", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotContainsIgnoreCase("STRING", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some $tring    ").verifyNotContainsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some $tring    ").verifyNotContainsIgnoreCase("TRING", "%s#%s", getParams());
    }
  }

  public static class VerifyNotEndsWithTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotEndsWith("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotEndsWith("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotEndsWith(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotEndsWith("   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyNotEndsWithIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotEndsWithIgnoreCase("   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotEndsWithIgnoreCase("   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotEndsWithIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotEndsWithIgnoreCase("tring   S ", "%s#%s", getParams());
    }
  }

  public static class VerifyNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotEquals("  some STRING    ", "%s#%s", getParams());
      toCS("  some string    ").verifyNotEquals("  some String   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyNotEquals("  some STRING    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotEquals("  some string    ", "%s#%s", getParams());
    }
  }

  public static class VerifyNotEqualsIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotEqualsIgnoreCase("  SOME tring    ", "%s#%s", getParams());
      toCS(null).verifyNotEqualsIgnoreCase("", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyNotEqualsIgnoreCase("  SOME tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotEqualsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotEqualsIgnoreCase("  some STRING    ", "%s#%s", getParams());
    }
  }

  public static class VerifyNotEqualsIgnoreWhiteSpacesTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  SOME string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyNotEqualsIgnoreWhiteSpaces("  SOME string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  some string    ", "%s#%s", getParams());
    }
  }

  public static class VerifyNotStartsWithTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotStartsWith("  S", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotStartsWith("  S", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotStartsWith(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotStartsWith("  s", "%s#%s", getParams());
    }
  }

  public static class VerifyNotStartsWithIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotStartsWithIgnoreCase("A", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotStartsWithIgnoreCase("A", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotStartsWithIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotStartsWithIgnoreCase("  ", "%s#%s", getParams());
    }
  }

  public static class VerifyNumberOfMatchesEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNumberOfMatchesEquals("s", 3, "%s#%s", getParams());
      toCS("  some String   s ").verifyNumberOfMatchesEquals("s", 2, "%s#%s", getParams());
      toCS("  some $tring   s ").verifyNumberOfMatchesEquals("$", 1, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNumberOfMatchesEquals("s", 3, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNumberOfMatchesEquals(null, 3, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNumberOfMatchesEquals("s", 1, "%s#%s", getParams());
    }
  }

  public static class VerifyNumberOfMatchesNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNumberOfMatchesNotEquals("s", 2, "%s#%s", getParams());
      toCS("  some String   s ").verifyNumberOfMatchesNotEquals("s", 1, "%s#%s", getParams());
      toCS("  some $tring   s ").verifyNumberOfMatchesNotEquals("$", 3, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNumberOfMatchesNotEquals("s", 2, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNumberOfMatchesNotEquals(null, 2, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNumberOfMatchesNotEquals("s", 3, "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEquals("s", "  ome tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyRemoveEquals("so", "  me String    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEquals("s", "  ome tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEquals("s", "  ome string    ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveEndEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndEquals("  some ", "  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndEquals("some string   s ", "  ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndEquals("  some string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndEquals(null, "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndEquals("tring   s ", "  some S", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveEndEquals("tring   s ", "  some $", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndEquals("  some ", "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndEquals("  some ", "some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveEndIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("some String   s ", "  ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  sOME string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndIgnoreCaseEquals(null, "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndIgnoreCaseEquals("tring   S ", "  some S", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveEndIgnoreCaseEquals("TRING   s ", "  some $", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveEndIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndIgnoreCaseNotEquals(null, "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  Some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  some ", "  some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveEndNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndNotEquals("  some ", "ome string   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndNotEquals(null, "  some String   S", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndNotEquals("tring   S ", "  some s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndNotEquals("  some ", "ome string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndNotEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndNotEquals("  some ", "  some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseEquals("s", "  ome tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyRemoveIgnoreCaseEquals("SO", "  me String    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveIgnoreCaseEquals("s", "  ome tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseEquals("s", "  ome trng    ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyRemoveIgnoreCaseNotEquals("SO", "  me String    x", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveIgnoreCaseNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", "  ome tring    ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyRemoveNotEquals(null, "  me String    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveNotEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveNotEquals("s", "  ome tring    ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveStartEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartEquals("  some ", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartEquals("some string   s ", "  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartEquals("  some string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartEquals(null, "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartEquals("  some S", "tring   s ", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveStartEquals("  some $", "tring   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartEquals("  some ", "string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartEquals("  some ", "string s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveStartIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some ", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("Some string   s ", "  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartIgnoreCaseEquals(null, "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartIgnoreCaseEquals("  some s", "tring   s ", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveStartIgnoreCaseEquals("  some $", "tring   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartIgnoreCaseEquals("  some ", "string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "string s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveStartIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", "String   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  Some ", "string  s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartIgnoreCaseNotEquals("  some ", "String   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testRemoveNull() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals(null, "string  s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", "string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveStartNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartNotEquals("  some", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartNotEquals(null, " Some string   s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartNotEquals("  some", "string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartNotEquals("  some", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartNotEquals("  some ", "string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceEquals("s", "", "  ome tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceEquals("so", "XX", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   S ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceIgnoreCaseEquals("s", "|", "  |ome |tring   | ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  some String   x ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceIgnoreCaseNotEquals("s", "|", "  |ome string   | ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceIgnoreCaseNotEquals("SO", "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceIgnoreCaseNotEquals("s", "|", "  |ome |tring   | ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceNotEquals("s", "", "  ome String    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceNotEquals("so", "XX", "  XXme XXtring   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceNotEquals("so", "XX", "  XXme XXtring   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceNotEquals("so", "XX", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceNotEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceOnceEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceEquals("s", "", "  ome string   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  XXme String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceEquals("so", "XX", "  XXme String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceOnceEquals("so", "XX", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  Xome String   so ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceIgnoreCaseEquals("SO", "x", "  xme String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals("s", "|", "  \\|ome string   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals("s", "|", "  |ome string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceOnceNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceNotEquals("s", "", "  ome String   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceNotEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceNotEquals("s", "", "  ome String   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyReplaceOnceNotEquals("s", "", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceOnceNotEquals("s", "", "  ome string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyReverseEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReverseEquals(" s   gnirts emos  ", "%s#%s", getParams());
      toCS("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReverseEquals(" os   .*&^%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some @#$%^&*.   so ").verifyReverseEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%# emos  ", "%s#%s", getParams());
    }
  }

  public static class VerifyReverseNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReverseNotEquals(" s   gnirt emos  ", "%s#%s", getParams());
      toCS("  some @#$%^&*.   so ").verifyReverseNotEquals(" os   .*%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReverseNotEquals(" os   .*%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some @#$%^&*.   so ").verifyReverseNotEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReverseNotEquals(" s   gnirts emos  ", "%s#%s", getParams());
    }
  }

  public static class VerifyRightValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyRightValueEquals(7, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightValueEquals(7, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyRightValueEquals(7, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyRightValueEquals(7, "ing   ", "%s#%s", getParams());
    }
  }

  public static class VerifyRightValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyRightValueNotEquals(6, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightValueNotEquals(6, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyRightValueNotEquals(6, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyRightValueNotEquals(7, "ing    ", "%s#%s", getParams());
    }
  }

  public static class VerifyRightPadEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
      toCS("  some string   s ").verifyRightPadEquals(30, "", "  some string   s             ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRightPadEquals(10, null, "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRightPadEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRightPadEquals(40, "x", "  some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRightPadNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
      toCS("  some string   s ").verifyRightPadNotEquals(10, null, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRightPadNotEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
    }
  }

  public static class VerifyStartsWithTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWith("  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWith("  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWith(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWith("some", "%s#%s", getParams());
    }
  }

  public static class VerifyStartsWithAnyTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWithAny(new CList<>("A", null, "  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWithAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "some"), "%s#%s", getParams());
    }
  }

  public static class VerifyStartsWithIgnoreCaseTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWithIgnoreCase("  some", "%s#%s", getParams());
      toCS("  some string   s ").verifyStartsWithIgnoreCase("  Some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWithIgnoreCase("  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWithIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWithIgnoreCase(" some", "%s#%s", getParams());
    }
  }

  public static class VerifyStartsWithNoneTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWithNone(new CList<>("A", null, "  Some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWithNone(new CList<>("A", null, "  Some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWithNone(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWithNone(new CList<>("A", "  some", " Some"), "%s#%s", getParams());
    }
  }

  public static class VerifyStripedEndValueTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedEndValue(" ", "  some string", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedEndValue(null, "  some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValue("|", "|some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValue(null, "|some string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedEndValue("|", "|some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedEndValue("|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedEndValue("|", "|som string", "%s#%s", getParams());
    }
  }

  public static class VerifyStripedEndValueNotTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedEndValueNot(" ", "  ome string", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedEndValueNot(null, "  ome string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValueNot("|", "|som string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValueNot(null, "|soe string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedEndValueNot("|", "|som string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedEndValueNot("|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedEndValueNot("|", "|some string", "%s#%s", getParams());
    }
  }

  public static class VerifyStripedStartValueTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedStartValue(" ", "some string    ", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedStartValue(null, "some string    ", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValue("|", "some string||||", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValue(null, "|some string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedStartValue("|", "some string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedStartValue("|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedStartValue("|", "some string|", "%s#%s", getParams());
    }
  }

  public static class VerifyStripedStartValueNotTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedStartValueNot(" ", "ome string    ", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedStartValueNot(null, "ome string    ", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValueNot("|", "ome string||||", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValueNot(null, "|ome string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedStartValueNot(" ", "ome string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyStripedStartValueNot(" ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyStripedStartValueNot(" ", "some string    ", "%s#%s", getParams());
    }
  }

  public static class VerifyStripedValueTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedValue(" ", "some string", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedValue(null, "some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValue("|", "some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValue(null, "|some string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedValue("|", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedValue("|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedValue("|", "some String", "%s#%s", getParams());
    }
  }

  public static class VerifyStripedValueNotTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedValueNot(" ", "somestring", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedValueNot(null, "som string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValueNot("|", "somestring", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValueNot(null, "|soe string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedValueNot(" ", "somestring", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyStripedValueNot(" ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyStripedValueNot(" ", "some string", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringEquals(0, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringEquals(0, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringEquals(0, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringEquals(0, "  some string", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringEqualsWithEndTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringEquals(0, 3, "  s", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringEquals(2, 4, "so", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringEquals(0, 3, "  s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringEquals(0, 3, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringEquals(0, 3, "  some", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringAfterEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterEquals(" s", "ome string    ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterEquals(null, "", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterEquals(" s", "ome string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterEquals(" s", "Some string    ", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringAfterLastEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterLastEquals(" s", "tring    ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterLastEquals(null, "", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterLastEquals(" s", "tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterLastEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterLastEquals(" s", "String    ", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringAfterLastNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterLastNotEquals(" s", "trng    ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterLastNotEquals(null, "something", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterLastNotEquals(" s", "trng    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterLastNotEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterLastNotEquals(" s", "tring    ", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringAfterNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterNotEquals(" s", "ome string   ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterNotEquals(null, "X", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterNotEquals(" s", "ome string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterNotEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterNotEquals(" s", "ome string    ", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringBeforeEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeEquals(" st", "  some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeEquals(null, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeEquals(" st", "  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeEquals(" st", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeEquals(" st", "some", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringBeforeLastEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeLastEquals(" s", "  some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeLastEquals(null, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeLastEquals(" s", "  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeLastEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeLastEquals(" s", "some", "%s#%s", getParams());
    }
  }

  public static class SubstringBeforeLastNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeLastNotEquals(" s", " some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeLastNotEquals(null, " some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeLastNotEquals("s", " some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeLastNotEquals("S ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeLastNotEquals(null, "  some string    ", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringBeforeNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeNotEquals(" st", " some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeNotEquals(null, "  some string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeNotEquals(" st", " some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeNotEquals(" st", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeNotEquals(" st", "  some", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringBetweenEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", "    ", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBetweenEquals("  ", "    ", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string    ").verifySubstringBetweenEquals(null, "    ", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", null, "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", "    ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", "    ", "sme string", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringBetweenNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "sme string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBetweenNotEquals("  ", "    ", "sme string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "some string", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringNotEquals(0, " some string    ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringNotEquals(2, "ome string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringNotEquals(0, " some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringNotEquals(0, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringNotEquals(0, "  some string    ", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringNotEqualsWithEndTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringNotEquals(0, 3, " s", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringNotEquals(2, 4, "sox", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringNotEquals(0, 3, " s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringNotEquals(0, 3, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringNotEquals(0, 3, "  s", "%s#%s", getParams());
    }
  }

  public static class SubstringsBetweenEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(null, "s", new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", null, new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegativeOnSize() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", ""), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", " "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative2() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  ", " "), "%s#%s", getParams());
    }
  }

  public static class SubstringsBetweenContainsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", "s", " ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenContains(" ", "s", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenContains(null, "s", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", null, "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", "s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", "s", "some string", "%s#%s", getParams());
    }
  }

  public static class SubstringsBetweenNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(null, "s", new CList<>(" ", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", null, new CList<>(" ", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "", "  "), "%s#%s", getParams());
    }
  }

  public static class SubstringsBetweenNotContainsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains(" ", "s", "some string", "%s#%s", getParams());
      toCS("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenNotContains("some ", "ing", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains(null, "s", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains(" ", null, "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", "str", "%s#%s", getParams());
    }
  }

  public static class TrimmedValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTrimmedValueEquals("some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTrimmedValueEquals("some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTrimmedValueEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTrimmedValueEquals("some st$ng", "%s#%s", getParams());
    }
  }

  public static class TrimmedValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTrimmedValueNotEquals("some strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTrimmedValueNotEquals("some strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTrimmedValueNotEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTrimmedValueNotEquals("some string", "%s#%s", getParams());
    }
  }

  public static class TruncatedValueEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueEquals(10, "some strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueEquals(4, " string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueEquals(4, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueEquals(5, " string   ", "%s#%s", getParams());
    }
  }

  public static class TruncatedValueEqualsWithEndTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueEquals(4, 10, " string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueEquals(4, 10, " string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueEquals(4, 10, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueEquals(5, 10, " string   ", "%s#%s", getParams());
    }
  }

  public static class TruncatedValueNotEqualsTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueNotEquals(10, "ome strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueNotEquals(10, "ome strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueNotEquals(10, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueNotEquals(5, "some ", "%s#%s", getParams());
    }
  }

  public static class TruncatedValueNotEqualsWithEndTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueNotEquals(4, 10, " tring   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueNotEquals(4, 10, " tring   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueNotEquals(4, 10, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueNotEquals(4, 10, " string   ", "%s#%s", getParams());
    }
  }

  public static class IsMatchesTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyMatches("[a-z ]+", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMatches(" tring   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyMatches((String) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyMatches("\\d+", "%s#%s", getParams());
    }
  }

  public static class IsMatchesPatternTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyMatches(Pattern.compile("[a-z ]+"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMatches(Pattern.compile("[a-z ]+"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyMatches((Pattern) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyMatches(Pattern.compile("^[A-Z ]+$"), "%s#%s", getParams());
    }
  }

  public static class IsNotMatchesTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyNotMatches("^[A-Z ]+$", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotMatches(" tring   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyNotMatches((String) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyNotMatches("[a-z ]+", "%s#%s", getParams());
    }
  }

  public static class IsNotMatchesPatternTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyNotMatches(Pattern.compile("^[A-Z ]+$"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotMatches(Pattern.compile("[a-z ]+"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyNotMatches((Pattern) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyNotMatches(Pattern.compile("[a-z ]+"), "%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsBlankOrAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulajksn").verifyIsBlankOrAlpha("%s#%s", getParams());
      toCS("     ").verifyIsBlankOrAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiu1 lajksn").verifyIsBlankOrAlpha("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsBlankOrAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265opksn").verifyIsBlankOrAlphanumeric("%s#%s", getParams());
      toCS("     ").verifyIsBlankOrAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6!265opksn").verifyIsBlankOrAlphanumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsBlankOrNotAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("a1").verifyIsBlankOrNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("abs").verifyIsBlankOrNotAlpha("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsBlankOrNotAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265!opksn").verifyIsBlankOrNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6265opksn").verifyIsBlankOrNotAlphanumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsBlankOrNotNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("12345A67").verifyIsBlankOrNotNumeric("%s#%s", getParams());
      toCS("A").verifyIsBlankOrNotNumeric("%s#%s", getParams());
      toCS("    ").verifyIsBlankOrNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234567").verifyIsBlankOrNotNumeric("%s#%s", getParams());
    }
  }

  public static class WithIntervalVerifyIsBlankOrNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567").verifyIsBlankOrNumeric("%s#%s", getParams());
      toCS("    ").verifyIsBlankOrNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234A567").verifyIsBlankOrNumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsBlankOrAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulajksn").verifyIsBlankOrAlpha("%s#%s", getParams());
      toCS("   ").verifyIsBlankOrAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiu1 lajksn").verifyIsBlankOrAlpha("%s#%s", getParams());
    }
  }

  public static class VerifyIsBlankOrAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265opksn").verifyIsBlankOrAlphanumeric("%s#%s", getParams());
      toCS("   ").verifyIsBlankOrAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6!265opksn").verifyIsBlankOrAlphanumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsBlankOrNotAlphaTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("a1").verifyIsBlankOrNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("abs").verifyIsBlankOrNotAlpha("%s#%s", getParams());
    }
  }

  public static class VerifyIsBlankOrNotAlphanumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265!opksn").verifyIsBlankOrNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6265opksn").verifyIsBlankOrNotAlphanumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsBlankOrNotNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("12345A67").verifyIsBlankOrNotNumeric("%s#%s", getParams());
      toCS("A").verifyIsBlankOrNotNumeric("%s#%s", getParams());
      toCS("   ").verifyIsBlankOrNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234567").verifyIsBlankOrNotNumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsBlankOrNumericTest {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567").verifyIsBlankOrNumeric("%s#%s", getParams());
      toCS("   ").verifyIsBlankOrNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsBlankOrNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234A567").verifyIsBlankOrNumeric("%s#%s", getParams());
    }
  }
}

package org.catools.common.tests.waitVerifier.interfaces;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CStringWaitVerifier;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.catools.common.tests.waitVerifier.CBaseWaitVerifyTest;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

public class CStringWaitVerifyTest extends CBaseWaitVerifyTest {
  private static CStringWaitVerifier toWaitVerifier(String s) {
    return () -> s;
  }

  public static class CenterPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testVerifyEquals() {
      verify(verifier -> toWaitVerifier("  some string ").verifyCenterPadEquals(verifier, 40, "", "               some string              ", 1));
      verify(verifier -> toWaitVerifier("  SOM@#$%^& o ").verifyCenterPadEquals(verifier, 29, "&%", "&%&%&%&  SOM@#$%^& o &%&%&%&%"));
      verify(verifier -> toWaitVerifier("  SOM@#$%^& o ").verifyCenterPadEquals(verifier, 20, "#$%^", "#$%  SOM@#$%^& o #$%"));
      verify(verifier -> toWaitVerifier("  SOM@#$%^& o ").verifyCenterPadEquals(verifier, 10, "", "  SOM@#$%^& o "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyCenterPadEquals(verifier, 40, "", "               some string              "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string ").verifyCenterPadEquals(verifier, 40, "", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string ").verifyCenterPadEquals(verifier, 40, "", "             some string              "));
    }
  }

  public static class CenterPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string ").verifyCenterPadNotEquals(verifier, 40, "", "              some string              ", 1));
      verify(verifier -> toWaitVerifier("  SOM@#$%^& o ").verifyCenterPadNotEquals(verifier, 20, "#$%^", "#$%  SOM@$%^& o #$%"));
      verify(verifier -> toWaitVerifier("  SOM@#$%^& o ").verifyCenterPadNotEquals(verifier, 10, "", "  SOM@#$%^& o"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyCenterPadNotEquals(verifier, 40, "", "              some string              "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string ").verifyCenterPadNotEquals(verifier, 40, "", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string ").verifyCenterPadNotEquals(verifier, 40, "", "               some string              "));
    }
  }

  public static class Compare {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyCompare(verifier, "  some string    ", 0, 1));
      verify(verifier -> toWaitVerifier("  SOME string    ").verifyCompare(verifier, "  some string    ", -32));
      verify(verifier -> toWaitVerifier(null).verifyCompare(verifier, null, 0));
      verify(verifier -> toWaitVerifier("  some string    ").verifyCompare(verifier, "  some String    ", 32));
      verify(verifier -> toWaitVerifier("  some string    ").verifyCompare(verifier, null, 1));
      verify(verifier -> toWaitVerifier(null).verifyCompare(verifier, "  some string    ", -1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyCompare(verifier, "  some string    ", 0));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyCompare(verifier, null, 0));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  SOME string    ").verifyCompare(verifier, "  some string    ", -1));
    }
  }

  public static class CompareIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyCompareIgnoreCase(verifier, "  SOME string    ", 0, 1));
      verify(verifier -> toWaitVerifier("  SOME string    ").verifyCompareIgnoreCase(verifier, "  some string    ", 0));
      verify(verifier -> toWaitVerifier(null).verifyCompareIgnoreCase(verifier, null, 0));
      verify(verifier -> toWaitVerifier("  some string    ").verifyCompareIgnoreCase(verifier, "  some xtring    ", -5));
      verify(verifier -> toWaitVerifier("  some string    ").verifyCompareIgnoreCase(verifier, null, 1));
      verify(verifier -> toWaitVerifier(null).verifyCompareIgnoreCase(verifier, "  some string    ", -1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyCompareIgnoreCase(verifier, "  SOME string    ", 0));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyCompareIgnoreCase(verifier, null, 0));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyCompareIgnoreCase(verifier, "SOME string    ", 0));
    }
  }

  public static class Contains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyContains(verifier, "so", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyContains(verifier, "so"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyContains(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyContains(verifier, "sox"));
    }
  }

  public static class ContainsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  Some string    ").verifyContainsIgnoreCase(verifier, " so", 1));
      verify(verifier -> toWaitVerifier("  some $tring    ").verifyContainsIgnoreCase(verifier, "$TRING"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyContainsIgnoreCase(verifier, " so"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  Some string    ").verifyContainsIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  Some string    ").verifyContainsIgnoreCase(verifier, " sox"));
    }
  }

  public static class EndsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWith(verifier, "   s "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWith(verifier, "   s ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEndsWith(verifier, "   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWith(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWith(verifier, " a s "));
    }
  }

  public static class EndsWithAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithAny(verifier, new CList<>("A", null, " s ")));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithAny(verifier, new CList<>("A", null, " s "), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEndsWithAny(verifier, new CList<>("A", null, " s ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithAny(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithAny(verifier, new CList<>("A", null, " sx ")));
    }
  }

  public static class EndsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithIgnoreCase(verifier, "   s "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithIgnoreCase(verifier, "   S ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEndsWithIgnoreCase(verifier, "   S "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithIgnoreCase(verifier, "   SX "));
    }
  }

  public static class VerifyEndsWithNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithNone(verifier, new CList<>("A", null, " S ")));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithNone(verifier, new CList<>("A", null, " S "), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEndsWithNone(verifier, new CList<>("A", null, " S ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithNone(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithNone(verifier, new CList<>("A", null, " s ")));
    }
  }

  public static class VerifyEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEquals(verifier, "  some string    "));
      verify(verifier -> toWaitVerifier(null).verifyEquals(verifier, null, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEquals(verifier, "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEquals(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEquals(verifier, "  some string "));
    }
  }

  public static class VerifyEqualsAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAny(verifier, new CList<>("", "  some string    ")));
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAny(verifier, new CList<>("", "  some string    "), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEqualsAny(verifier, new CList<>("", "  some string    ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAny(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAny(verifier, new CList<>("", "  sometring    ")));
    }
  }

  public static class VerifyEqualsAnyIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAnyIgnoreCase(verifier, new CList<>("", "  some string    ")));
      verify(verifier -> toWaitVerifier("  some STRING    ").verifyEqualsAnyIgnoreCase(verifier, new CList<>("", "  SOME string    "), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEqualsAnyIgnoreCase(verifier, new CList<>("", "  some string    ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAnyIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAnyIgnoreCase(verifier, new CList<>("", "  somestring    ")));
    }
  }

  public static class VerifyEqualsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsIgnoreCase(verifier, "  SOME string    "));
      verify(verifier -> toWaitVerifier(null).verifyEqualsIgnoreCase(verifier, null, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEqualsIgnoreCase(verifier, "  SOME string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsIgnoreCase(verifier, "  SOME string"));
    }
  }

  public static class VerifyEqualsIgnoreWhiteSpaces {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  SOME string    ").verifyEqualsIgnoreWhiteSpaces(verifier, "SO ME st ring    "));
      verify(verifier -> toWaitVerifier("  SOME string    ").verifyEqualsIgnoreWhiteSpaces(verifier, "SO ME st ring    ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEqualsIgnoreWhiteSpaces(verifier, "SO ME st ring    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  SOME string    ").verifyEqualsIgnoreWhiteSpaces(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  SOME string    ").verifyEqualsIgnoreWhiteSpaces(verifier, "SME st ring    "));
    }
  }

  public static class VerifyEqualsNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNone(verifier, new CList<>("", "  some", "string    ")));
      verify(verifier -> toWaitVerifier("  some STRING    ").verifyEqualsNone(verifier, new CList<>("", "  SOME string    "), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEqualsNone(verifier, new CList<>("", "  some", "string    ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNone(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNone(verifier, new CList<>("", "some", "  some string    ")));
    }
  }

  public static class VerifyEqualsNoneIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNoneIgnoreCase(verifier, new CList<>("", "  some", "string    ")));
      verify(verifier -> toWaitVerifier("  some STRING    ").verifyEqualsNoneIgnoreCase(verifier, new CList<>("", "  $OME string    "), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyEqualsNoneIgnoreCase(verifier, new CList<>("", "  some", "string    ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNoneIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNoneIgnoreCase(verifier, new CList<>("", "  some", "  some string    ")));
    }
  }

  public static class VerifyIsAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("aiulajksn").verifyIsAlpha(verifier));
      verify(verifier -> toWaitVerifier("aiulajksn").verifyIsAlpha(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsAlpha(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("aiu1!lajksn").verifyIsAlpha(verifier, 1));
    }
  }

  public static class VerifyIsEmptyOrAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("aiulajksn").verifyIsEmptyOrAlpha(verifier));
      verify(verifier -> toWaitVerifier("").verifyIsEmptyOrAlpha(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsEmptyOrAlpha(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("aiu1 lajksn").verifyIsEmptyOrAlpha(verifier, 1));
    }
  }

  public static class VerifyIsAlphaSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier(" aiu ajk sn").verifyIsAlphaSpace(verifier));
      verify(verifier -> toWaitVerifier(" aiu ajk sn").verifyIsAlphaSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsAlphaSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("1 aiu ajk sn").verifyIsAlphaSpace(verifier, 1));
    }
  }

  public static class VerifyIsAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("aiulaj45872ksn1").verifyIsAlphanumeric(verifier));
      verify(verifier -> toWaitVerifier("blkajsblas").verifyIsAlphanumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsAlphanumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testInvalidChar() {
      verify(verifier -> toWaitVerifier("aiulaj4\u5872ksn1").verifyIsAlphanumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("aiulaj4 5872ksn1").verifyIsAlphanumeric(verifier, 1));
    }
  }

  public static class VerifyIsEmptyOrAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("aiulaj6265opksn").verifyIsEmptyOrAlphanumeric(verifier));
      verify(verifier -> toWaitVerifier("").verifyIsEmptyOrAlphanumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsEmptyOrAlphanumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("aiulaj6!265opksn").verifyIsEmptyOrAlphanumeric(verifier, 1));
    }
  }

  public static class VerifyIsAlphanumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("ai1ul90jksn").verifyIsAlphanumericSpace(verifier));
      verify(verifier -> toWaitVerifier(" ai1ul90 ajk sn").verifyIsAlphanumericSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsAlphanumericSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("aksaskjhas654!").verifyIsAlphanumericSpace(verifier, 1));
    }
  }

  public static class VerifyIsAsciiPrintable {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 32;
      verify(verifier -> toWaitVerifier(new String(chars)).verifyIsAsciiPrintable(verifier));
      chars[5] = 33;
      verify(verifier -> toWaitVerifier(new String(chars)).verifyIsAsciiPrintable(verifier, 1));
      chars[5] = 125;
      verify(verifier -> toWaitVerifier(new String(chars)).verifyIsAsciiPrintable(verifier, 1));
      chars[5] = 126;
      verify(verifier -> toWaitVerifier(new String(chars)).verifyIsAsciiPrintable(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsAsciiPrintable(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      verify(verifier -> toWaitVerifier(new String(chars)).verifyIsAsciiPrintable(verifier, 1));
    }
  }

  public static class VerifyIsNotAsciiPrintable {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      verify(verifier -> toWaitVerifier(new String(chars)).verifyIsNotAsciiPrintable(verifier));

      chars[5] = 127;
      verify(verifier -> toWaitVerifier(new String(chars)).verifyIsNotAsciiPrintable(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNotAsciiPrintable(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("asasa").verifyIsNotAsciiPrintable(verifier, 1));
    }
  }

  public static class VerifyIsBlank {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier(null).verifyIsBlank(verifier));
      verify(verifier -> toWaitVerifier("").verifyIsBlank(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("A").verifyIsBlank(verifier, 1));
    }
  }

  public static class VerifyIsEmpty {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("").verifyIsEmpty(verifier));
      verify(verifier -> toWaitVerifier("").verifyIsEmpty(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsEmpty(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("s").verifyIsEmpty(verifier, 1));
    }
  }

  public static class VerifyIsNotAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("aiu1lajks1n").verifyIsNotAlpha(verifier));
      verify(verifier -> toWaitVerifier("aiu1lajks1n").verifyIsNotAlpha(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNotAlpha(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("alskdla").verifyIsNotAlpha(verifier, 1));
    }
  }

  public static class VerifyIsEmptyOrNotAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("a1").verifyIsEmptyOrNotAlpha(verifier));
      verify(verifier -> toWaitVerifier("a1").verifyIsEmptyOrNotAlpha(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsEmptyOrNotAlpha(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("abs").verifyIsEmptyOrNotAlpha(verifier, 1));
    }
  }

  public static class VerifyIsNotAlphaSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("1aiul ajk sn").verifyIsNotAlphaSpace(verifier));
      verify(verifier -> toWaitVerifier("1aiul ajk sn").verifyIsNotAlphaSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNotAlphaSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("sdfghjk").verifyIsNotAlphaSpace(verifier, 1));
    }
  }

  public static class VerifyIsNotAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("aiulaj4587 2ksn1").verifyIsNotAlphanumeric(verifier));
      verify(verifier -> toWaitVerifier("blkajsbla!s").verifyIsNotAlphanumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNotAlphanumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("aiulaj45872ksn1").verifyIsNotAlphanumeric(verifier, 1));
    }
  }

  public static class VerifyIsEmptyOrNotAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("aiulaj6265!opksn").verifyIsEmptyOrNotAlphanumeric(verifier));
      verify(verifier -> toWaitVerifier("aiulaj6265!opksn").verifyIsEmptyOrNotAlphanumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsEmptyOrNotAlphanumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("aiulaj6265opksn").verifyIsEmptyOrNotAlphanumeric(verifier, 1));
    }
  }

  public static class VerifyIsNotAlphanumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("ai1ul90jks!n").verifyIsNotAlphanumericSpace(verifier));
      verify(verifier -> toWaitVerifier("ai1ul90jks !").verifyIsNotAlphanumericSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNotAlphanumericSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("ai1ul9 0jksn").verifyIsNotAlphanumericSpace(verifier, 1));
    }
  }

  public static class VerifyIsNotBlank {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("ai1ul90jks !").verifyIsNotBlank(verifier));
      verify(verifier -> toWaitVerifier("ai1ul90jks !").verifyIsNotBlank(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNotBlank(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("").verifyIsNotBlank(verifier, 1));
    }
  }

  public static class VerifyIsNotEmpty {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("ai1ul90jks !").verifyIsNotEmpty(verifier));
      verify(verifier -> toWaitVerifier("ai1ul90jks !").verifyIsNotEmpty(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNotEmpty(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("").verifyIsNotEmpty(verifier, 1));
    }
  }

  public static class VerifyIsNotNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("1234567A").verifyIsNotNumeric(verifier));
      verify(verifier -> toWaitVerifier("").verifyIsNotNumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNotNumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("1234567").verifyIsNotNumeric(verifier, 1));
    }
  }

  public static class VerifyIsEmptyOrNotNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("12345A67").verifyIsEmptyOrNotNumeric(verifier));
      verify(verifier -> toWaitVerifier("A").verifyIsEmptyOrNotNumeric(verifier, 1));
      verify(verifier -> toWaitVerifier("").verifyIsEmptyOrNotNumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsEmptyOrNotNumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("1234567").verifyIsEmptyOrNotNumeric(verifier, 1));
    }
  }

  public static class VerifyIsNotNumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("234567!8").verifyIsNotNumericSpace(verifier));
      verify(verifier -> toWaitVerifier(" 1254 7A86 1").verifyIsNotNumericSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNotNumericSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("2345678").verifyIsNotNumericSpace(verifier, 1));
    }
  }

  public static class VerifyIsNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("1234567").verifyIsNumeric(verifier));
      verify(verifier -> toWaitVerifier("1234567").verifyIsNumeric(verifier, 3));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("A1234567").verifyIsNumeric(verifier, 1));
    }
  }

  public static class VerifyIsEmptyOrNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("1234567").verifyIsEmptyOrNumeric(verifier));
      verify(verifier -> toWaitVerifier("").verifyIsEmptyOrNumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsEmptyOrNumeric(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("1234A567").verifyIsEmptyOrNumeric(verifier, 1));
    }
  }

  public static class VerifyIsNumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("2345678").verifyIsNumericSpace(verifier));
      verify(verifier -> toWaitVerifier(" 1254 786 1").verifyIsNumericSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyIsNumericSpace(verifier, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("2345A678").verifyIsNumericSpace(verifier, 1));
    }
  }

  public static class VerifyLeftValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueEquals(verifier, 7, "  some "));
      verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueEquals(verifier, 7, "  some ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyLeftValueEquals(verifier, 7, "  some "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueEquals(verifier, 7, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueEquals(verifier, 7, "some "));
    }
  }

  public static class VerifyLeftValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueNotEquals(verifier, 3, "  some "));
      verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueNotEquals(verifier, 3, "  some ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyLeftValueNotEquals(verifier, 3, "  some "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueNotEquals(verifier, 3, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueNotEquals(verifier, 7, "  some "));
    }
  }

  public static class VerifyLeftPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadEquals(verifier, 40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadEquals(verifier, 40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadEquals(verifier, 30, "", "              some string   s "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadEquals(verifier, 10, null, "  some string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyLeftPadEquals(verifier, 40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadEquals(verifier, 40, "x", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadEquals(verifier, 40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s "));
    }
  }

  public static class VerifyLeftPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadNotEquals(verifier, 40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadNotEquals(verifier, 40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadNotEquals(verifier, 10, null, "  some string  s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyLeftPadNotEquals(verifier, 40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadNotEquals(verifier, 40, "x", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadNotEquals(verifier, 40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s "));
    }
  }

  public static class VerifyLength {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthEquals(verifier, 18));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthEquals(verifier, 18, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyLengthEquals(verifier, 18));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthEquals(verifier, 17));
    }
  }

  public static class VerifyLengthNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthNotEquals(verifier, 17));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthNotEquals(verifier, 17, 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyLengthNotEquals(verifier, 17));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthNotEquals(verifier, 18));
    }
  }

  public static class VerifyMidValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueEquals(verifier, 2, 4, "some"));
      verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueEquals(verifier, 2, 4, "some", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyMidValueEquals(verifier, 2, 4, "some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueEquals(verifier, 2, 4, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueEquals(verifier, 2, 3, "some"));
    }
  }

  public static class VerifyMidValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueNotEquals(verifier, 2, 5, "some"));
      verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueNotEquals(verifier, 2, 5, "some", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyMidValueNotEquals(verifier, 2, 5, "some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueNotEquals(verifier, 2, 5, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueNotEquals(verifier, 2, 4, "some"));
    }
  }

  public static class VerifyNotContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotContains(verifier, "sO"));
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotContains(verifier, "sO", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotContains(verifier, "sO"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotContains(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotContains(verifier, "som"));
    }
  }

  public static class VerifyNotContainsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  Some string    ").verifyNotContainsIgnoreCase(verifier, " sX", 1));
      verify(verifier -> toWaitVerifier("  some $tring    ").verifyNotContainsIgnoreCase(verifier, "STRING"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotContainsIgnoreCase(verifier, "STRING"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some $tring    ").verifyNotContainsIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some $tring    ").verifyNotContainsIgnoreCase(verifier, "TRING"));
    }
  }

  public static class VerifyNotEndsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWith(verifier, "   S "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWith(verifier, "   S ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotEndsWith(verifier, "   S "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWith(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWith(verifier, "   s "));
    }
  }

  public static class VerifyNotEndsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWithIgnoreCase(verifier, "   x "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWithIgnoreCase(verifier, "   x ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotEndsWithIgnoreCase(verifier, "   x "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWithIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWithIgnoreCase(verifier, "tring   S "));
    }
  }

  public static class VerifyNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEquals(verifier, "  some STRING    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEquals(verifier, "  some String   "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotEquals(verifier, "  some STRING    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEquals(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEquals(verifier, "  some string    "));
    }
  }

  public static class VerifyNotEqualsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreCase(verifier, "  SOME tring    ", 1));
      verify(verifier -> toWaitVerifier(null).verifyNotEqualsIgnoreCase(verifier, ""));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotEqualsIgnoreCase(verifier, "  SOME tring    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreCase(verifier, "  some STRING    "));
    }
  }

  public static class VerifyNotEqualsIgnoreWhiteSpaces {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(verifier, "  SOME string    "));
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(verifier, "  SOME string    ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotEqualsIgnoreWhiteSpaces(verifier, "  SOME string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(verifier, "  some string    "));
    }
  }

  public static class VerifyNotStartsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWith(verifier, "  S"));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWith(verifier, "  S", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotStartsWith(verifier, "  S"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWith(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWith(verifier, "  s"));
    }
  }

  public static class VerifyNotStartsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWithIgnoreCase(verifier, "A"));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWithIgnoreCase(verifier, "A", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotStartsWithIgnoreCase(verifier, "A"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWithIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWithIgnoreCase(verifier, "  "));
    }
  }

  public static class VerifyNumberOfMatchesEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNumberOfMatchesEquals(verifier, "s", 3, 1));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyNumberOfMatchesEquals(verifier, "s", 2));
      verify(verifier -> toWaitVerifier("  some $tring   s ").verifyNumberOfMatchesEquals(verifier, "$", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNumberOfMatchesEquals(verifier, "s", 3));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNumberOfMatchesEquals(verifier, null, 3));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNumberOfMatchesEquals(verifier, "s", 1));
    }
  }

  public static class VerifyNumberOfMatchesNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNumberOfMatchesNotEquals(verifier, "s", 2, 1));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyNumberOfMatchesNotEquals(verifier, "s", 1));
      verify(verifier -> toWaitVerifier("  some $tring   s ").verifyNumberOfMatchesNotEquals(verifier, "$", 3));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNumberOfMatchesNotEquals(verifier, "s", 2));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNumberOfMatchesNotEquals(verifier, null, 2));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyNumberOfMatchesNotEquals(verifier, "s", 3));
    }
  }

  public static class VerifyRemoveEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEquals(verifier, "s", "  ome tring    ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyRemoveEquals(verifier, "so", "  me String    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveEquals(verifier, "s", "  ome tring    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEquals(verifier, "s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEquals(verifier, "s", "  ome string    "));
    }
  }

  public static class VerifyRemoveEndEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndEquals(verifier, "  some ", "  some string   s ", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndEquals(verifier, "some string   s ", "  "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndEquals(verifier, "  some string   s ", ""));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndEquals(verifier, null, "  some String   s "));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndEquals(verifier, "tring   s ", "  some S"));
      verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveEndEquals(verifier, "tring   s ", "  some $"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveEndEquals(verifier, "  some ", "  some string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndEquals(verifier, "  some ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndEquals(verifier, "  some ", "some string   s "));
    }
  }

  public static class VerifyRemoveEndIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "  Some ", "  some string   s ", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "some String   s ", "  "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "  sOME string   s ", ""));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndIgnoreCaseEquals(verifier, null, "  some String   s "));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "tring   S ", "  some S"));
      verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "TRING   s ", "  some $"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveEndIgnoreCaseEquals(verifier, "  Some ", "  some string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "  Some ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "  Some ", "  some string   s"));
    }
  }

  public static class VerifyRemoveEndIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals(verifier, "  Some ", "  some String   s ", 1));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndIgnoreCaseNotEquals(verifier, null, "  some string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveEndIgnoreCaseNotEquals(verifier, "  Some ", "  some String   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals(verifier, "  Some ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals(verifier, "  some ", "  some string   s "));
    }
  }

  public static class VerifyRemoveEndNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndNotEquals(verifier, "  some ", "ome string   s ", 1));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndNotEquals(verifier, null, "  some String   S"));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndNotEquals(verifier, "tring   S ", "  some s"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveEndNotEquals(verifier, "  some ", "ome string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndNotEquals(verifier, "  some ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndNotEquals(verifier, "  some ", "  some string   s "));
    }
  }

  public static class VerifyRemoveIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveIgnoreCaseEquals(verifier, "s", "  ome tring    ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyRemoveIgnoreCaseEquals(verifier, "SO", "  me String    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveIgnoreCaseEquals(verifier, "s", "  ome tring    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveIgnoreCaseEquals(verifier, "s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveIgnoreCaseEquals(verifier, "s", "  ome trng    "));
    }
  }

  public static class VerifyRemoveIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveIgnoreCaseNotEquals(verifier, "s", "  ome Tring    ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyRemoveIgnoreCaseNotEquals(verifier, "SO", "  me String    x"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveIgnoreCaseNotEquals(verifier, "s", "  ome Tring    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveIgnoreCaseNotEquals(verifier, "s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveIgnoreCaseNotEquals(verifier, "s", "  ome tring    "));
    }
  }

  public static class VerifyRemoveNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveNotEquals(verifier, "s", "  ome Tring    ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyRemoveNotEquals(verifier, null, "  me String    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveNotEquals(verifier, "s", "  ome Tring    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveNotEquals(verifier, "s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveNotEquals(verifier, "s", "  ome tring    "));
    }
  }

  public static class VerifyRemoveStartEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartEquals(verifier, "  some ", "string   s ", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartEquals(verifier, "some string   s ", "  some string   s "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartEquals(verifier, "  some string   s ", ""));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveStartEquals(verifier, null, "  some String   s "));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveStartEquals(verifier, "  some S", "tring   s "));
      verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveStartEquals(verifier, "  some $", "tring   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveStartEquals(verifier, "  some ", "string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartEquals(verifier, "  some ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartEquals(verifier, "  some ", "string s "));
    }
  }

  public static class VerifyRemoveStartIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  some ", "string   s ", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  Some ", "string   s "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "Some string   s ", "  some string   s "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  Some string   s ", ""));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveStartIgnoreCaseEquals(verifier, null, "  some String   s "));
      verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  some s", "tring   s "));
      verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  some $", "tring   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveStartIgnoreCaseEquals(verifier, "  some ", "string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  some ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  some ", "string s "));
    }
  }

  public static class VerifyRemoveStartIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals(verifier, "  some ", "String   s ", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals(verifier, "  Some ", "string  s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveStartIgnoreCaseNotEquals(verifier, "  some ", "String   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testRemoveNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals(verifier, null, "string  s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals(verifier, "  some ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals(verifier, "  some ", "string   s "));
    }
  }

  public static class VerifyRemoveStartNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartNotEquals(verifier, "  some", "string   s ", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartNotEquals(verifier, null, " Some string   s"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRemoveStartNotEquals(verifier, "  some", "string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartNotEquals(verifier, "  some", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartNotEquals(verifier, "  some ", "string   s "));
    }
  }

  public static class VerifyReplaceEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceEquals(verifier, "s", "", "  ome tring    ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceEquals(verifier, "so", "XX", "  XXme String   XX "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReplaceEquals(verifier, "so", "XX", "  XXme String   XX "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceEquals(verifier, "so", "XX", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceEquals(verifier, "so", "XX", "  XXme String   S "));
    }
  }

  public static class VerifyReplaceIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceIgnoreCaseEquals(verifier, "s", "|", "  |ome |tring   | ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceIgnoreCaseEquals(verifier, "SO", "x", "  xme String   x "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReplaceIgnoreCaseEquals(verifier, "SO", "x", "  xme String   x "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceIgnoreCaseEquals(verifier, "SO", "x", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceIgnoreCaseEquals(verifier, "SO", "x", "  some String   x "));
    }
  }

  public static class VerifyReplaceIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceIgnoreCaseNotEquals(verifier, "s", "|", "  |ome string   | ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceIgnoreCaseNotEquals(verifier, "SO", "x", "  xme tring   x "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReplaceIgnoreCaseNotEquals(verifier, "SO", "x", "  xme tring   x "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceIgnoreCaseNotEquals(verifier, "SO", "x", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceIgnoreCaseNotEquals(verifier, "s", "|", "  |ome |tring   | "));
    }
  }

  public static class VerifyReplaceNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceNotEquals(verifier, "s", "", "  ome String    ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceNotEquals(verifier, "so", "XX", "  XXme XXtring   XX "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReplaceNotEquals(verifier, "so", "XX", "  XXme XXtring   XX "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceNotEquals(verifier, "so", "XX", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceNotEquals(verifier, "so", "XX", "  XXme String   XX "));
    }
  }

  public static class VerifyReplaceOnceEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceEquals(verifier, "s", "", "  ome string   s ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceEquals(verifier, "so", "XX", "  XXme String   so "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReplaceOnceEquals(verifier, "so", "XX", "  XXme String   so "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceEquals(verifier, "so", "XX", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceEquals(verifier, "so", "XX", "  Xome String   so "));
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceIgnoreCaseEquals(verifier, "s", "|", "  |ome string   s ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceIgnoreCaseEquals(verifier, "SO", "x", "  xme String   so "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReplaceOnceIgnoreCaseEquals(verifier, "s", "|", "  |ome string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceIgnoreCaseEquals(verifier, "s", "|", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceIgnoreCaseEquals(verifier, "s", "|", "  |some string   s "));
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals(verifier, "s", "|", "  \\|ome string   s ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals(verifier, "SO", "x", "  .*e String   so "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReplaceOnceIgnoreCaseNotEquals(verifier, "SO", "x", "  .*e String   so "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals(verifier, "SO", "x", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals(verifier, "s", "|", "  |ome string   s "));
    }
  }

  public static class VerifyReplaceOnceNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceNotEquals(verifier, "s", "", "  ome String   s ", 1));
      verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceNotEquals(verifier, "so", "XX", "  XXme String   XX "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReplaceOnceNotEquals(verifier, "s", "", "  ome String   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceNotEquals(verifier, "s", "", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceNotEquals(verifier, "s", "", "  ome string   s "));
    }
  }

  public static class VerifyReverseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReverseEquals(verifier, " s   gnirts emos  ", 1));
      verify(verifier -> toWaitVerifier("  some @#$%^&*.   so ").verifyReverseEquals(verifier, " os   .*&^%$#@ emos  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReverseEquals(verifier, " os   .*&^%$#@ emos  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some @#$%^&*.   so ").verifyReverseEquals(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some @#$%^&*.   so ").verifyReverseEquals(verifier, " os   .*&^%# emos  "));
    }
  }

  public static class VerifyReverseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReverseNotEquals(verifier, " s   gnirt emos  ", 1));
      verify(verifier -> toWaitVerifier("  some @#$%^&*.   so ").verifyReverseNotEquals(verifier, " os   .*%$#@ emos  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyReverseNotEquals(verifier, " os   .*%$#@ emos  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some @#$%^&*.   so ").verifyReverseNotEquals(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyReverseNotEquals(verifier, " s   gnirts emos  "));
    }
  }

  public static class VerifyRightValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueEquals(verifier, 7, "ing    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueEquals(verifier, 7, "ing    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRightValueEquals(verifier, 7, "ing    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueEquals(verifier, 7, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueEquals(verifier, 7, "ing   "));
    }
  }

  public static class VerifyRightValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueNotEquals(verifier, 6, "ing    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueNotEquals(verifier, 6, "ing    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRightValueNotEquals(verifier, 6, "ing    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueNotEquals(verifier, 6, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueNotEquals(verifier, 7, "ing    "));
    }
  }

  public static class VerifyRightPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadEquals(verifier, 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadEquals(verifier, 30, "", "  some string   s             "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadEquals(verifier, 10, null, "  some string   s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRightPadEquals(verifier, 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadEquals(verifier, 40, "x", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadEquals(verifier, 40, "x", "  some string   s "));
    }
  }

  public static class VerifyRightPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadNotEquals(verifier, 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadNotEquals(verifier, 10, null, "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyRightPadNotEquals(verifier, 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadNotEquals(verifier, 40, "x", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadNotEquals(verifier, 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx"));
    }
  }

  public static class VerifyStartsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWith(verifier, "  some", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWith(verifier, "  some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStartsWith(verifier, "  some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWith(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWith(verifier, "some"));
    }
  }

  public static class VerifyStartsWithAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithAny(verifier, new CList<>("A", null, "  some"), 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithAny(verifier, new CList<>("A", null, "  some")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStartsWithAny(verifier, new CList<>("A", null, "  some")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithAny(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithAny(verifier, new CList<>("A", null, "some")));
    }
  }

  public static class VerifyStartsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithIgnoreCase(verifier, "  some", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithIgnoreCase(verifier, "  Some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStartsWithIgnoreCase(verifier, "  some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithIgnoreCase(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithIgnoreCase(verifier, " some"));
    }
  }

  public static class VerifyStartsWithNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithNone(verifier, new CList<>("A", null, "  Some")));
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithNone(verifier, new CList<>("A", null, "  Some"), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStartsWithNone(verifier, new CList<>("A", null, "  Some")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithNone(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithNone(verifier, new CList<>("A", "  some", " Some")));
    }
  }

  public static class VerifyStripedEndValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValue(verifier, " ", "  some string", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValue(verifier, null, "  some string"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValue(verifier, "|", "|some string"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValue(verifier, null, "|some string||||"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStripedEndValue(verifier, "|", "|some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValue(verifier, "|", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValue(verifier, "|", "|som string"));
    }
  }

  public static class VerifyStripedEndValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValueNot(verifier, " ", "  ome string", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValueNot(verifier, null, "  ome string"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValueNot(verifier, "|", "|som string"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValueNot(verifier, null, "|soe string||||"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStripedEndValueNot(verifier, "|", "|som string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValueNot(verifier, "|", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValueNot(verifier, "|", "|some string"));
    }
  }

  public static class VerifyStripedStartValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValue(verifier, " ", "some string    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValue(verifier, null, "some string    "));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValue(verifier, "|", "some string||||"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValue(verifier, null, "|some string||||"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStripedStartValue(verifier, "|", "some string||||"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValue(verifier, "|", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValue(verifier, "|", "some string|"));
    }
  }

  public static class VerifyStripedStartValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValueNot(verifier, " ", "ome string    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValueNot(verifier, null, "ome string    "));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValueNot(verifier, "|", "ome string||||"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValueNot(verifier, null, "|ome string||||"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStripedStartValueNot(verifier, " ", "ome string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValueNot(verifier, " ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValueNot(verifier, " ", "some string    "));
    }
  }

  public static class VerifyStripedValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValue(verifier, " ", "some string", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValue(verifier, null, "some string"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValue(verifier, "|", "some string"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValue(verifier, null, "|some string||||"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStripedValue(verifier, "|", "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValue(verifier, "|", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValue(verifier, "|", "some String"));
    }
  }

  public static class VerifyStripedValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValueNot(verifier, " ", "somestring", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValueNot(verifier, null, "som string"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValueNot(verifier, "|", "somestring"));
      verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValueNot(verifier, null, "|soe string||||"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyStripedValueNot(verifier, " ", "somestring"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValueNot(verifier, " ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValueNot(verifier, " ", "some string"));
    }
  }

  public static class VerifySubstringEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, "  some string    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringEquals(verifier, 0, "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, "  some string"));
    }
  }

  public static class VerifySubstringEquals_WithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, 3, "  s", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 2, 4, "so"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringEquals(verifier, 0, 3, "  s"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, 3, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, 3, "  some"));
    }
  }

  public static class VerifySubstringAfterEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterEquals(verifier, " s", "ome string    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterEquals(verifier, null, ""));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringAfterEquals(verifier, " s", "ome string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterEquals(verifier, " s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterEquals(verifier, " s", "Some string    "));
    }
  }

  public static class VerifySubstringAfterLastEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastEquals(verifier, " s", "tring    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastEquals(verifier, null, ""));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringAfterLastEquals(verifier, " s", "tring    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastEquals(verifier, " s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastEquals(verifier, " s", "String    "));
    }
  }

  public static class VerifySubstringAfterLastNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastNotEquals(verifier, " s", "trng    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastNotEquals(verifier, null, "something"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringAfterLastNotEquals(verifier, " s", "trng    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastNotEquals(verifier, " s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastNotEquals(verifier, " s", "tring    "));
    }
  }

  public static class VerifySubstringAfterNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterNotEquals(verifier, " s", "ome string   ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterNotEquals(verifier, null, "X"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringAfterNotEquals(verifier, " s", "ome string   "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterNotEquals(verifier, " s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterNotEquals(verifier, " s", "ome string    "));
    }
  }

  public static class VerifySubstringBeforeEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeEquals(verifier, " st", "  some",1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeEquals(verifier, null, "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringBeforeEquals(verifier, " st", "  some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeEquals(verifier, " st", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeEquals(verifier, " st", "some"));
    }
  }

  public static class VerifySubstringBeforeLastEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastEquals(verifier, " s", "  some", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastEquals(verifier, null, "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringBeforeLastEquals(verifier, " s", "  some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastEquals(verifier, " s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastEquals(verifier, " s", "some"));
    }
  }

  public static class SubstringBeforeLastNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastNotEquals(verifier, " s", " some", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastNotEquals(verifier, null, " some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringBeforeLastNotEquals(verifier, "s", " some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastNotEquals(verifier, "S ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastNotEquals(verifier, null, "  some string    "));
    }
  }

  public static class VerifySubstringBeforeNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeNotEquals(verifier, " st", " some", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeNotEquals(verifier, null, "  some string   "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringBeforeNotEquals(verifier, " st", " some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeNotEquals(verifier, " st", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeNotEquals(verifier, " st", "  some"));
    }
  }

  public static class VerifySubstringBetweenEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenEquals(verifier, "  ", "    ", "some string", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenEquals(verifier, "  ", "    ", "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringBetweenEquals(verifier, "  ", "    ", "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenEquals(verifier, null, "    ", "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenEquals(verifier, "  ", null, "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenEquals(verifier, "  ", "    ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenEquals(verifier, "  ", "    ", "sme string"));
    }
  }

  public static class VerifySubstringBetweenNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenNotEquals(verifier, "  ", "    ", "sme string"));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenNotEquals(verifier, "  ", "    ", "sme string", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringBetweenNotEquals(verifier, "  ", "    ", "sme string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenNotEquals(verifier, "  ", "    ", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenNotEquals(verifier, "  ", "    ", "some string"));
    }
  }

  public static class VerifySubstringNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, " some string    ", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 2, "ome string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringNotEquals(verifier, 0, " some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, "  some string    "));
    }
  }

  public static class VerifySubstringNotEquals_WithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, 3, " s", 1));
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 2, 4, "sox"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringNotEquals(verifier, 0, 3, " s"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, 3, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, 3, "  s"));
    }
  }

  public static class SubstringsBetweenEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, " ", "s", new CList<>(" ", "", "  ")));
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, " ", "s", new CList<>(" ", "", "  "), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringsBetweenEquals(verifier, " ", "s", new CList<>(" ", "", "  ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, null, "s", new CList<>(" ", "", "  ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, " ", null, new CList<>(" ", "", "  ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, " ", "s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegativeOnSize() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, " ", "s", new CList<>(" ", "")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, " ", "s", new CList<>(" ", "", " ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative2() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, " ", "s", new CList<>(" ", "", "  ", " ")));
    }
  }

  public static class SubstringsBetweenContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenContains(verifier, " ", "s", " "));
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenContains(verifier, " ", "s", " ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringsBetweenContains(verifier, " ", "s", "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenContains(verifier, null, "s", "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenContains(verifier, " ", null, "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenContains(verifier, " ", "s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenContains(verifier, " ", "s", "some string"));
    }
  }

  public static class SubstringsBetweenNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotEquals(verifier, " ", "s", new CList<>(" ", "  ")));
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotEquals(verifier, " ", "s", new CList<>(" ", "  "), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringsBetweenNotEquals(verifier, " ", "s", new CList<>(" ", "  ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotEquals(verifier, null, "s", new CList<>(" ", "  ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotEquals(verifier, " ", null, new CList<>(" ", "  ")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotEquals(verifier, " ", "s", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotEquals(verifier, " ", "s", new CList<>(" ", "", "  ")));
    }
  }

  public static class SubstringsBetweenNotContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotContains(verifier, " ", "s", "some string", 1));
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotContains(verifier, "some ", "ing", "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifySubstringsBetweenNotContains(verifier, "some ", "ing", "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testOpenNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotContains(verifier, null, "s", "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testCloseNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotContains(verifier, " ", null, "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotContains(verifier, "some ", "ing", null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotContains(verifier, "some ", "ing", "str"));
    }
  }

  public static class TrimmedValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueEquals(verifier, "some string"));
      verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueEquals(verifier, "some string", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyTrimmedValueEquals(verifier, "some string"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueEquals(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueEquals(verifier, "some st$ng"));
    }
  }

  public static class TrimmedValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueNotEquals(verifier, "some strin"));
      verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueNotEquals(verifier, "some strin", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyTrimmedValueNotEquals(verifier, "some strin"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueNotEquals(verifier, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueNotEquals(verifier, "some string"));
    }
  }

  public static class TruncatedValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 10, "some strin"));
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 10, "some strin", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyTruncatedValueEquals(verifier, 4, " string   "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 4, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 5, " string   "));
    }
  }

  public static class TruncatedValueEqualsWithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 4, 10, " string   "));
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 4, 10, " string   ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyTruncatedValueEquals(verifier, 4, 10, " string   "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 4, 10, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 5, 10, " string   "));
    }
  }

  public static class TruncatedValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 10, "ome strin"));
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 10, "ome strin", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyTruncatedValueNotEquals(verifier, 10, "ome strin"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 10, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 5, "some "));
    }
  }

  public static class TruncatedValueNotEqualsWithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 4, 10, " tring   "));
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 4, 10, " tring   ", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyTruncatedValueNotEquals(verifier, 4, 10, " tring   "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 4, 10, null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 4, 10, " string   "));
    }
  }

  public static class IsMatches {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, "[a-z ]+"));
      verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, "[a-z ]+", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyMatches(verifier, " tring   "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = NullPointerException.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, (String) null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, "\\d+"));
    }
  }

  public static class IsMatchesPattern {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, Pattern.compile("[a-z ]+")));
      verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, Pattern.compile("[a-z ]+"), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyMatches(verifier, Pattern.compile("[a-z ]+")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, (Pattern) null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, Pattern.compile("^[A-Z ]+$")));
    }
  }

  public static class IsNotMatches {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, "^[A-Z ]+$"));
      verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, "^[A-Z ]+$", 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotMatches(verifier, " tring   "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, (String) null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, "[a-z ]+"));
    }
  }

  public static class IsNotMatchesPattern {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, Pattern.compile("^[A-Z ]+$")));
      verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, Pattern.compile("^[A-Z ]+$"), 1));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      verify(verifier -> toWaitVerifier(null).verifyNotMatches(verifier, Pattern.compile("[a-z ]+")));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, (Pattern) null));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, Pattern.compile("[a-z ]+")));
    }
  }
}

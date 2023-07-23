package org.catools.common.tests.verify.interfaces.verifier;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.interfaces.base.CStringVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

@Slf4j
@Test(retryAnalyzer = CTestRetryAnalyzer.class)
public class CStringVerifierTest extends CBaseUnitTest {

  private static CStringVerify toCS(String s) {
    return () -> s;
  }

  public static class CenterPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testVerifyEquals() {
      toCS("  some string ").verifyCenterPadEquals(40, "", "               some string              ", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadEquals(29, "&%", "&%&%&%&  SOM@#$%^& o &%&%&%&%");
      toCS("  SOM@#$%^& o ").verifyCenterPadEquals(20, "#$%^", "#$%  SOM@#$%^& o #$%", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadEquals(10, "", "  SOM@#$%^& o ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCenterPadEquals(40, "", "               some string              ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string ").verifyCenterPadEquals(40, "", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string ").verifyCenterPadEquals(40, "", "             some string              ", "%s#%s", getParams());
    }
  }

  public static class CenterPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string ").verifyCenterPadNotEquals(40, "", "              some string              ");
      toCS("  SOM@#$%^& o ").verifyCenterPadNotEquals(20, "#$%^", "#$%  SOM@$%^& o #$%", "%s#%s", getParams());
      toCS("  SOM@#$%^& o ").verifyCenterPadNotEquals(10, "", "  SOM@#$%^& o");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCenterPadNotEquals(40, "", "              some string              ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string ").verifyCenterPadNotEquals(40, "", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string ").verifyCenterPadNotEquals(40, "", "               some string              ", "%s#%s", getParams());
    }
  }

  public static class Compare {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyCompare("  some string    ", 0);
      toCS("  SOME string    ").verifyCompare("  some string    ", -32, "%s#%s", getParams());
      toCS(null).verifyCompare(null, 0);
      toCS("  some string    ").verifyCompare("  some String    ", 32, "%s#%s", getParams());
      toCS("  some string    ").verifyCompare(null, 1);
      toCS(null).verifyCompare("  some string    ", -1, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCompare("  some string    ", 0);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyCompare(null, 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  SOME string    ").verifyCompare("  some string    ", -1);
    }
  }

  public static class CompareIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyCompareIgnoreCase("  SOME string    ", 0, "%s#%s", getParams());
      toCS("  SOME string    ").verifyCompareIgnoreCase("  some string    ", 0);
      toCS(null).verifyCompareIgnoreCase(null, 0, "%s#%s", getParams());
      toCS("  some string    ").verifyCompareIgnoreCase("  some xtring    ", -5);
      toCS("  some string    ").verifyCompareIgnoreCase(null, 1, "%s#%s", getParams());
      toCS(null).verifyCompareIgnoreCase("  some string    ", -1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyCompareIgnoreCase("  SOME string    ", 0, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyCompareIgnoreCase(null, 0);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyCompareIgnoreCase("SOME string    ", 0, "%s#%s", getParams());
    }
  }

  public static class Contains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyContains("so");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyContains("so", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyContains(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyContains("sox", "%s#%s", getParams());
    }
  }

  public static class ContainsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  Some string    ").verifyContainsIgnoreCase(" so");
      toCS("  some $tring    ").verifyContainsIgnoreCase("$TRING", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyContainsIgnoreCase(" so");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  Some string    ").verifyContainsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  Some string    ").verifyContainsIgnoreCase(" sox");
    }
  }

  public static class EndsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWith("   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWith("   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWith(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWith(" a s ");
    }
  }

  public static class EndsWithAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWithAny(new CList<>("A", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWithAny(new CList<>("A", null, " s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWithAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWithAny(new CList<>("A", null, " sx "));
    }
  }

  public static class EqualsAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(" some s ").verifyEqualsAny(new CList<>(" some s ", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsAny(new CList<>(" some s ", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS(" some s ").verifyEqualsAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS(" some s ").verifyEqualsAny(new CList<>("some s ", null, " s "), "%s#%s", getParams());
    }
  }

  public static class EqualsAnyIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(" soMe s ").verifyEqualsAnyIgnoreCase(new CList<>(" some s ", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsAnyIgnoreCase(new CList<>(" some s ", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS(" soMe s ").verifyEqualsAnyIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS(" soMe s ").verifyEqualsAnyIgnoreCase(new CList<>("some s ", null, " s "), "%s#%s", getParams());
    }
  }

  public static class ContainsAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(" some s ").verifyContainsAny(new CList<>(" some s ", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyContainsAny(new CList<>(" some s ", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS(" some s ").verifyContainsAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS(" somes ").verifyContainsAny(new CList<>("some s ", null, " s "), "%s#%s", getParams());
    }
  }

  public static class ContainsAnyIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(" SoMe S ").verifyContainsAnyIgnoreCase(new CList<>(" some s ", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyContainsAnyIgnoreCase(new CList<>(" some s ", null, " s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS(" soMe s ").verifyContainsAnyIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS(" SoMeS ").verifyContainsAnyIgnoreCase(new CList<>("some s ", null, " s "), "%s#%s", getParams());
    }
  }

  public static class EndsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWithIgnoreCase("   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyEndsWithIgnoreCase("   S ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWithIgnoreCase("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWithIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWithIgnoreCase("   SX ", "%s#%s", getParams());
    }
  }

  public static class VerifyEndsWithNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyEndsWithNone(new CList<>("A", null, " S "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEndsWithNone(new CList<>("A", null, " S "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyEndsWithNone(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyEndsWithNone(new CList<>("A", null, " s "), "%s#%s", getParams());
    }
  }

  public static class VerifyEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEquals("  some string    ");
      toCS(null).verifyEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEquals("  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEquals("  some string ");
    }
  }

  public static class VerifyEqualsAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsAny(new CList<>("", "  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsAny(new CList<>("", "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsAny(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsAny(new CList<>("", "  sometring    "));
    }
  }

  public static class VerifyEqualsAnyIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  some string    "), "%s#%s", getParams());
      toCS("  some STRING    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  SOME string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsAnyIgnoreCase(new CList<>("", "  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsAnyIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  somestring    "), "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsIgnoreCase("  SOME string    ");
      toCS(null).verifyEqualsIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsIgnoreCase("  SOME string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsIgnoreCase("  SOME string", "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsIgnoreWhiteSpaces {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  SOME string    ").verifyEqualsIgnoreWhiteSpaces("SO ME st ring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsIgnoreWhiteSpaces("SO ME st ring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  SOME string    ").verifyEqualsIgnoreWhiteSpaces(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  SOME string    ").verifyEqualsIgnoreWhiteSpaces("SME st ring    ", "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsNone(new CList<>("", "  some", "string    "));
      toCS("  some STRING    ").verifyEqualsNone(new CList<>("", "  SOME string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsNone(new CList<>("", "  some", "string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsNone(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsNone(new CList<>("", "some", "  some string    "));
    }
  }

  public static class VerifyEqualsNoneIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "string    "), "%s#%s", getParams());
      toCS("  some STRING    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  $OME string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyEqualsNoneIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "  some string    "), "%s#%s", getParams());
    }
  }

  public static class VerifyIsAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulajksn").verifyIsAlpha();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiu1!lajksn").verifyIsAlpha();
    }
  }

  public static class VerifyIsEmptyOrAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulajksn").verifyIsEmptyOrAlpha("%s#%s", getParams());
      toCS("").verifyIsEmptyOrAlpha();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiu1 lajksn").verifyIsEmptyOrAlpha();
    }
  }

  public static class VerifyIsAlphaSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(" aiu ajk sn").verifyIsAlphaSpace();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1 aiu ajk sn").verifyIsAlphaSpace();
    }
  }

  public static class VerifyIsAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj45872ksn1").verifyIsAlphanumeric("%s#%s", getParams());
      toCS("blkajsblas").verifyIsAlphanumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testInvalidChar() {
      toCS("aiulaj4\u5872ksn1").verifyIsAlphanumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj4 5872ksn1").verifyIsAlphanumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265opksn").verifyIsEmptyOrAlphanumeric();
      toCS("").verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrAlphanumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6!265opksn").verifyIsEmptyOrAlphanumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsAlphanumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jksn").verifyIsAlphanumericSpace();
      toCS(" ai1ul90 ajk sn").verifyIsAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAlphanumericSpace();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aksaskjhas654!").verifyIsAlphanumericSpace("%s#%s", getParams());
    }
  }

  public static class VerifyIsAsciiPrintable {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 32;
      toCS(new String(chars)).verifyIsAsciiPrintable();
      chars[5] = 33;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
      chars[5] = 125;
      toCS(new String(chars)).verifyIsAsciiPrintable();
      chars[5] = 126;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsAsciiPrintable();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      toCS(new String(chars)).verifyIsAsciiPrintable("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAsciiPrintable {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      toCS(new String(chars)).verifyIsNotAsciiPrintable();
      chars[5] = 127;
      toCS(new String(chars)).verifyIsNotAsciiPrintable("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAsciiPrintable();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("asasa").verifyIsNotAsciiPrintable("%s#%s", getParams());
    }
  }

  public static class VerifyIsBlank {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS(null).verifyIsBlank();
      toCS("").verifyIsBlank("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("A").verifyIsBlank();
    }
  }

  public static class VerifyIsEmpty {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("").verifyIsEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyIsEmpty();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("s").verifyIsEmpty("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiu1lajks1n").verifyIsNotAlpha();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("alskdla").verifyIsNotAlpha();
    }
  }

  public static class VerifyIsEmptyOrNotAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("a1").verifyIsEmptyOrNotAlpha("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNotAlpha();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("abs").verifyIsEmptyOrNotAlpha("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlphaSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1aiul ajk sn").verifyIsNotAlphaSpace();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlphaSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("sdfghjk").verifyIsNotAlphaSpace();
    }
  }

  public static class VerifyIsNotAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj4587 2ksn1").verifyIsNotAlphanumeric("%s#%s", getParams());
      toCS("blkajsbla!s").verifyIsNotAlphanumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj45872ksn1").verifyIsNotAlphanumeric();
    }
  }

  public static class VerifyIsEmptyOrNotAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("aiulaj6265!opksn").verifyIsEmptyOrNotAlphanumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNotAlphanumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("aiulaj6265opksn").verifyIsEmptyOrNotAlphanumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlphanumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jks!n").verifyIsNotAlphanumericSpace();
      toCS("ai1ul90jks !").verifyIsNotAlphanumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotAlphanumericSpace();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("ai1ul9 0jksn").verifyIsNotAlphanumericSpace("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotBlank {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jks !").verifyIsNotBlank();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotBlank("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("").verifyIsNotBlank();
    }
  }

  public static class VerifyIsNotEmpty {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("ai1ul90jks !").verifyIsNotEmpty("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotEmpty();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("").verifyIsNotEmpty("%s#%s", getParams());
    }
  }

  public static class VerifyIsNotNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567A").verifyIsNotNumeric();
      toCS("").verifyIsNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotNumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234567").verifyIsNotNumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNotNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("12345A67").verifyIsEmptyOrNotNumeric();
      toCS("A").verifyIsEmptyOrNotNumeric("%s#%s", getParams());
      toCS("").verifyIsEmptyOrNotNumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNotNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234567").verifyIsEmptyOrNotNumeric();
    }
  }

  public static class VerifyIsNotNumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("234567!8").verifyIsNotNumericSpace("%s#%s", getParams());
      toCS(" 1254 7A86 1").verifyIsNotNumericSpace();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNotNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("2345678").verifyIsNotNumericSpace();
    }
  }

  public static class VerifyIsNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567").verifyIsNumeric("%s#%s", getParams());
      toCS("1234567").verifyIsNumeric("%s#%s", 3, 7, getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("A1234567").verifyIsNumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("1234567").verifyIsEmptyOrNumeric();
      toCS("").verifyIsEmptyOrNumeric("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsEmptyOrNumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("1234A567").verifyIsEmptyOrNumeric("%s#%s", getParams());
    }
  }

  public static class VerifyIsNumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("2345678").verifyIsNumericSpace();
      toCS(" 1254 786 1").verifyIsNumericSpace("%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyIsNumericSpace();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("2345A678").verifyIsNumericSpace("%s#%s", getParams());
    }
  }

  public static class VerifyLeftValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyLeftValueEquals(7, "  some ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftValueEquals(7, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyLeftValueEquals(7, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyLeftValueEquals(7, "some ", "%s#%s", getParams());
    }
  }

  public static class VerifyLeftValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyLeftValueNotEquals(3, "  some ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftValueNotEquals(3, "  some ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyLeftValueNotEquals(3, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyLeftValueNotEquals(7, "  some ", "%s#%s", getParams());
    }
  }

  public static class VerifyLeftPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ");
      toCS("  some string   s ").verifyLeftPadEquals(30, "", "              some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyLeftPadEquals(10, null, "  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyLeftPadEquals(40, "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyLeftPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ");
      toCS("  some string   s ").verifyLeftPadNotEquals(10, null, "  some string  s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyLeftPadNotEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ");
    }
  }

  public static class VerifyLength {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLengthEquals(18, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyLengthEquals(18);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLengthEquals(17, "%s#%s", getParams());
    }
  }

  public static class VerifyLengthNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyLengthNotEquals(17);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyLengthNotEquals(17, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyLengthNotEquals(18);
    }
  }

  public static class VerifyMidValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyMidValueEquals(2, 4, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMidValueEquals(2, 4, "some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyMidValueEquals(2, 4, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyMidValueEquals(2, 3, "some");
    }
  }

  public static class VerifyMidValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyMidValueNotEquals(2, 5, "some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMidValueNotEquals(2, 5, "some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyMidValueNotEquals(2, 5, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyMidValueNotEquals(2, 4, "some");
    }
  }

  public static class VerifyNotContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotContains("sO", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotContains("sO");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotContains(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotContains("som");
    }
  }

  public static class VerifyNotContainsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  Some string    ").verifyNotContainsIgnoreCase(" sX", "%s#%s", getParams());
      toCS("  some $tring    ").verifyNotContainsIgnoreCase("STRING");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotContainsIgnoreCase("STRING", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some $tring    ").verifyNotContainsIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some $tring    ").verifyNotContainsIgnoreCase("TRING", "%s#%s", getParams());
    }
  }

  public static class VerifyNotEndsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotEndsWith("   S ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotEndsWith("   S ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotEndsWith(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotEndsWith("   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyNotEndsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotEndsWithIgnoreCase("   x ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotEndsWithIgnoreCase("   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotEndsWithIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotEndsWithIgnoreCase("tring   S ", "%s#%s", getParams());
    }
  }

  public static class VerifyNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotEquals("  some STRING    ");
      toCS("  some string    ").verifyNotEquals("  some String   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyNotEquals("  some STRING    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotEquals("  some string    ");
    }
  }

  public static class VerifyNotEqualsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotEqualsIgnoreCase("  SOME tring    ", "%s#%s", getParams());
      toCS(null).verifyNotEqualsIgnoreCase("");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyNotEqualsIgnoreCase("  SOME tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotEqualsIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotEqualsIgnoreCase("  some STRING    ", "%s#%s", getParams());
    }
  }

  public static class VerifyNotEqualsIgnoreWhiteSpaces {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  SOME string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toCS(null).verifyNotEqualsIgnoreWhiteSpaces("  SOME string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  some string    ", "%s#%s", getParams());
    }
  }

  public static class VerifyNotStartsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotStartsWith("  S");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotStartsWith("  S", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotStartsWith(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotStartsWith("  s", "%s#%s", getParams());
    }
  }

  public static class VerifyNotStartsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNotStartsWithIgnoreCase("A");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotStartsWithIgnoreCase("A", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNotStartsWithIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNotStartsWithIgnoreCase("  ", "%s#%s", getParams());
    }
  }

  public static class VerifyNumberOfMatchesEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNumberOfMatchesEquals("s", 3);
      toCS("  some String   s ").verifyNumberOfMatchesEquals("s", 2, "%s#%s", getParams());
      toCS("  some $tring   s ").verifyNumberOfMatchesEquals("$", 1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNumberOfMatchesEquals("s", 3, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNumberOfMatchesEquals(null, 3);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNumberOfMatchesEquals("s", 1, "%s#%s", getParams());
    }
  }

  public static class VerifyNumberOfMatchesNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyNumberOfMatchesNotEquals("s", 2);
      toCS("  some String   s ").verifyNumberOfMatchesNotEquals("s", 1, "%s#%s", getParams());
      toCS("  some $tring   s ").verifyNumberOfMatchesNotEquals("$", 3);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNumberOfMatchesNotEquals("s", 2, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyNumberOfMatchesNotEquals(null, 2);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyNumberOfMatchesNotEquals("s", 3, "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEquals("s", "  ome tring    ");
      toCS("  some String   so ").verifyRemoveEquals("so", "  me String    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEquals("s", "  ome tring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEquals("s", "  ome string    ");
    }
  }

  public static class VerifyRemoveEndEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndEquals("  some ", "  some string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndEquals("some string   s ", "  ");
      toCS("  some string   s ").verifyRemoveEndEquals("  some string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndEquals(null, "  some String   s ");
      toCS("  some String   s ").verifyRemoveEndEquals("tring   s ", "  some S", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveEndEquals("tring   s ", "  some $");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndEquals("  some ", "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndEquals("  some ", "some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveEndIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s ");
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("some String   s ", "  ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  sOME string   s ", "");
      toCS("  some String   s ").verifyRemoveEndIgnoreCaseEquals(null, "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndIgnoreCaseEquals("tring   S ", "  some S");
      toCS("  some $tring   s ").verifyRemoveEndIgnoreCaseEquals("TRING   s ", "  some $", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s");
    }
  }

  public static class VerifyRemoveEndIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s ", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndIgnoreCaseNotEquals(null, "  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  Some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  some ", "  some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveEndNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveEndNotEquals("  some ", "ome string   s ");
      toCS("  some String   s ").verifyRemoveEndNotEquals(null, "  some String   S", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveEndNotEquals("tring   S ", "  some s");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveEndNotEquals("  some ", "ome string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveEndNotEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveEndNotEquals("  some ", "  some string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseEquals("s", "  ome tring    ");
      toCS("  some String   so ").verifyRemoveIgnoreCaseEquals("SO", "  me String    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveIgnoreCaseEquals("s", "  ome tring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseEquals("s", "  ome trng    ");
    }
  }

  public static class VerifyRemoveIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyRemoveIgnoreCaseNotEquals("SO", "  me String    x");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveIgnoreCaseNotEquals("s", "  ome Tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", "  ome tring    ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveNotEquals("s", "  ome Tring    ");
      toCS("  some String   so ").verifyRemoveNotEquals(null, "  me String    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveNotEquals("s", "  ome Tring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveNotEquals("s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveNotEquals("s", "  ome tring    ");
    }
  }

  public static class VerifyRemoveStartEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartEquals("  some ", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartEquals("some string   s ", "  some string   s ");
      toCS("  some string   s ").verifyRemoveStartEquals("  some string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartEquals(null, "  some String   s ");
      toCS("  some String   s ").verifyRemoveStartEquals("  some S", "tring   s ", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveStartEquals("  some $", "tring   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartEquals("  some ", "string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartEquals("  some ", "string s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveStartIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "string   s ");
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some ", "string   s ", "%s#%s", getParams());
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("Some string   s ", "  some string   s ");
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some string   s ", "", "%s#%s", getParams());
      toCS("  some String   s ").verifyRemoveStartIgnoreCaseEquals(null, "  some String   s ");
      toCS("  some String   s ").verifyRemoveStartIgnoreCaseEquals("  some s", "tring   s ", "%s#%s", getParams());
      toCS("  some $tring   s ").verifyRemoveStartIgnoreCaseEquals("  some $", "tring   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartIgnoreCaseEquals("  some ", "string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "string s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveStartIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", "String   s ");
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  Some ", "string  s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartIgnoreCaseNotEquals("  some ", "String   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testRemoveNull() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals(null, "string  s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", "string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyRemoveStartNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRemoveStartNotEquals("  some", "string   s ");
      toCS("  some string   s ").verifyRemoveStartNotEquals(null, " Some string   s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRemoveStartNotEquals("  some", "string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRemoveStartNotEquals("  some", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRemoveStartNotEquals("  some ", "string   s ");
    }
  }

  public static class VerifyReplaceEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceEquals("s", "", "  ome tring    ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   XX ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceEquals("so", "XX", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   S ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceIgnoreCaseEquals("s", "|", "  |ome |tring   | ");
      toCS("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  some String   x ");
    }
  }

  public static class VerifyReplaceIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceIgnoreCaseNotEquals("s", "|", "  |ome string   | ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceIgnoreCaseNotEquals("SO", "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceIgnoreCaseNotEquals("s", "|", "  |ome |tring   | ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceNotEquals("s", "", "  ome String    ");
      toCS("  some String   so ").verifyReplaceNotEquals("so", "XX", "  XXme XXtring   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceNotEquals("so", "XX", "  XXme XXtring   XX ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceNotEquals("so", "XX", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceNotEquals("so", "XX", "  XXme String   XX ");
    }
  }

  public static class VerifyReplaceOnceEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceEquals("s", "", "  ome string   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  XXme String   so ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceEquals("so", "XX", "  XXme String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceOnceEquals("so", "XX", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  Xome String   so ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s ");
      toCS("  some String   so ").verifyReplaceOnceIgnoreCaseEquals("SO", "x", "  xme String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |some string   s ");
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals("s", "|", "  \\|ome string   s ", "%s#%s", getParams());
      toCS("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals("s", "|", "  |ome string   s ", "%s#%s", getParams());
    }
  }

  public static class VerifyReplaceOnceNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReplaceOnceNotEquals("s", "", "  ome String   s ");
      toCS("  some String   so ").verifyReplaceOnceNotEquals("so", "XX", "  XXme String   XX ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReplaceOnceNotEquals("s", "", "  ome String   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyReplaceOnceNotEquals("s", "", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReplaceOnceNotEquals("s", "", "  ome string   s ");
    }
  }

  public static class VerifyReverseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReverseEquals(" s   gnirts emos  ", "%s#%s", getParams());
      toCS("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%$#@ emos  ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReverseEquals(" os   .*&^%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some @#$%^&*.   so ").verifyReverseEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%# emos  ", "%s#%s", getParams());
    }
  }

  public static class VerifyReverseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyReverseNotEquals(" s   gnirt emos  ");
      toCS("  some @#$%^&*.   so ").verifyReverseNotEquals(" os   .*%$#@ emos  ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyReverseNotEquals(" os   .*%$#@ emos  ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some @#$%^&*.   so ").verifyReverseNotEquals(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyReverseNotEquals(" s   gnirts emos  ");
    }
  }

  public static class VerifyRightValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyRightValueEquals(7, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightValueEquals(7, "ing    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyRightValueEquals(7, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyRightValueEquals(7, "ing   ");
    }
  }

  public static class VerifyRightValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyRightValueNotEquals(6, "ing    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightValueNotEquals(6, "ing    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyRightValueNotEquals(6, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyRightValueNotEquals(7, "ing    ");
    }
  }

  public static class VerifyRightPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
      toCS("  some string   s ").verifyRightPadEquals(30, "", "  some string   s             ");
      toCS("  some string   s ").verifyRightPadEquals(10, null, "  some string   s ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRightPadEquals(40, "x", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRightPadEquals(40, "x", "  some string   s ");
    }
  }

  public static class VerifyRightPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
      toCS("  some string   s ").verifyRightPadNotEquals(10, null, "  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyRightPadNotEquals(40, "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", "%s#%s", getParams());
    }
  }

  public static class VerifyStartsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWith("  some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWith("  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWith(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWith("some", "%s#%s", getParams());
    }
  }

  public static class VerifyStartsWithAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "  some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWithAny(new CList<>("A", null, "  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWithAny(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "some"), "%s#%s", getParams());
    }
  }

  public static class VerifyStartsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWithIgnoreCase("  some");
      toCS("  some string   s ").verifyStartsWithIgnoreCase("  Some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWithIgnoreCase("  some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWithIgnoreCase(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWithIgnoreCase(" some");
    }
  }

  public static class VerifyStartsWithNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifyStartsWithNone(new CList<>("A", null, "  Some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStartsWithNone(new CList<>("A", null, "  Some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifyStartsWithNone(null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifyStartsWithNone(new CList<>("A", "  some", " Some"));
    }
  }

  public static class VerifyStripedEndValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedEndValue(" ", "  some string", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedEndValue(null, "  some string");
      toCS("|some string||||").verifyStripedEndValue("|", "|some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValue(null, "|some string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedEndValue("|", "|some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedEndValue("|", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedEndValue("|", "|som string", "%s#%s", getParams());
    }
  }

  public static class VerifyStripedEndValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedEndValueNot(" ", "  ome string");
      toCS("  some string    ").verifyStripedEndValueNot(null, "  ome string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedEndValueNot("|", "|som string");
      toCS("|some string||||").verifyStripedEndValueNot(null, "|soe string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedEndValueNot("|", "|som string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedEndValueNot("|", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedEndValueNot("|", "|some string");
    }
  }

  public static class VerifyStripedStartValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedStartValue(" ", "some string    ", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedStartValue(null, "some string    ");
      toCS("|some string||||").verifyStripedStartValue("|", "some string||||", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValue(null, "|some string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedStartValue("|", "some string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedStartValue("|", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedStartValue("|", "some string|", "%s#%s", getParams());
    }
  }

  public static class VerifyStripedStartValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedStartValueNot(" ", "ome string    ");
      toCS("  some string    ").verifyStripedStartValueNot(null, "ome string    ", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedStartValueNot("|", "ome string||||");
      toCS("|some string||||").verifyStripedStartValueNot(null, "|ome string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedStartValueNot(" ", "ome string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyStripedStartValueNot(" ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyStripedStartValueNot(" ", "some string    ");
    }
  }

  public static class VerifyStripedValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedValue(" ", "some string", "%s#%s", getParams());
      toCS("  some string    ").verifyStripedValue(null, "some string");
      toCS("|some string||||").verifyStripedValue("|", "some string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValue(null, "|some string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedValue("|", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("|some string||||").verifyStripedValue("|", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("|some string||||").verifyStripedValue("|", "some String", "%s#%s", getParams());
    }
  }

  public static class VerifyStripedValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifyStripedValueNot(" ", "somestring");
      toCS("  some string    ").verifyStripedValueNot(null, "som string", "%s#%s", getParams());
      toCS("|some string||||").verifyStripedValueNot("|", "somestring");
      toCS("|some string||||").verifyStripedValueNot(null, "|soe string||||", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyStripedValueNot(" ", "somestring");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifyStripedValueNot(" ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifyStripedValueNot(" ", "some string");
    }
  }

  public static class VerifySubstringEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringEquals(0, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringEquals(0, "  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringEquals(0, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringEquals(0, "  some string");
    }
  }

  public static class VerifySubstringEquals_WithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringEquals(0, 3, "  s", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringEquals(2, 4, "so");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringEquals(0, 3, "  s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringEquals(0, 3, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringEquals(0, 3, "  some", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringAfterEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterEquals(" s", "ome string    ");
      toCS("  some string    ").verifySubstringAfterEquals(null, "", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterEquals(" s", "ome string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterEquals(" s", "Some string    ");
    }
  }

  public static class VerifySubstringAfterLastEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterLastEquals(" s", "tring    ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterLastEquals(null, "");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterLastEquals(" s", "tring    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterLastEquals(" s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterLastEquals(" s", "String    ", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringAfterLastNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterLastNotEquals(" s", "trng    ");
      toCS("  some string    ").verifySubstringAfterLastNotEquals(null, "something", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterLastNotEquals(" s", "trng    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterLastNotEquals(" s", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterLastNotEquals(" s", "tring    ");
    }
  }

  public static class VerifySubstringAfterNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringAfterNotEquals(" s", "ome string   ", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringAfterNotEquals(null, "X");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringAfterNotEquals(" s", "ome string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringAfterNotEquals(" s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringAfterNotEquals(" s", "ome string    ", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringBeforeEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeEquals(" st", "  some");
      toCS("  some string    ").verifySubstringBeforeEquals(null, "  some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeEquals(" st", "  some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeEquals(" st", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeEquals(" st", "some");
    }
  }

  public static class VerifySubstringBeforeLastEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeLastEquals(" s", "  some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeLastEquals(null, "  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeLastEquals(" s", "  some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeLastEquals(" s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeLastEquals(" s", "some", "%s#%s", getParams());
    }
  }

  public static class SubstringBeforeLastNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeLastNotEquals(" s", " some");
      toCS("  some string    ").verifySubstringBeforeLastNotEquals(null, " some string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeLastNotEquals("s", " some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeLastNotEquals("S ", null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeLastNotEquals(null, "  some string    ");
    }
  }

  public static class VerifySubstringBeforeNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBeforeNotEquals(" st", " some", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringBeforeNotEquals(null, "  some string   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBeforeNotEquals(" st", " some", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBeforeNotEquals(" st", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBeforeNotEquals(" st", "  some", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringBetweenEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", "    ", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBetweenEquals("  ", "    ", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string    ").verifySubstringBetweenEquals(null, "    ", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", null, "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", "    ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBetweenEquals("  ", "    ", "sme string", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringBetweenNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "sme string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringBetweenNotEquals("  ", "    ", "sme string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "some string", "%s#%s", getParams());
    }
  }

  public static class VerifySubstringNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringNotEquals(0, " some string    ");
      toCS("  some string    ").verifySubstringNotEquals(2, "ome string    ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringNotEquals(0, " some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringNotEquals(0, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringNotEquals(0, "  some string    ");
    }
  }

  public static class VerifySubstringNotEquals_WithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string    ").verifySubstringNotEquals(0, 3, " s", "%s#%s", getParams());
      toCS("  some string    ").verifySubstringNotEquals(2, 4, "sox");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringNotEquals(0, 3, " s", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string    ").verifySubstringNotEquals(0, 3, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string    ").verifySubstringNotEquals(0, 3, "  s", "%s#%s", getParams());
    }
  }

  public static class SubstringsBetweenEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(null, "s", new CList<>(" ", "", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", null, new CList<>(" ", "", "  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegativeOnSize() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", ""), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", " "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative2() {
      toCS("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  ", " "), "%s#%s", getParams());
    }
  }

  public static class SubstringsBetweenContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", "s", " ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenContains(" ", "s", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenContains(null, "s", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", null, "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", "s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenContains(" ", "s", "some string", "%s#%s", getParams());
    }
  }

  public static class SubstringsBetweenNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  "), "some string", "SubstringsBetweenNotEquals#");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  "), "some string", "SubstringsBetweenNotEquals#");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(null, "s", new CList<>(" ", "  "), "some string", "SubstringsBetweenNotEquals #");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", null, new CList<>(" ", "  "), "some string", "SubstringsBetweenNotEquals #");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", null, "some string", "SubstringsBetweenNotEquals #");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "", "  "));
    }
  }

  public static class SubstringsBetweenNotContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains(" ", "s", "some string", "%s#%s", getParams());
      toCS("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifySubstringsBetweenNotContains("some ", "ing", "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testOpenNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains(null, "s", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testCloseNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains(" ", null, "some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", "str", "%s#%s", getParams());
    }
  }

  public static class TrimmedValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTrimmedValueEquals("some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTrimmedValueEquals("some string", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTrimmedValueEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTrimmedValueEquals("some st$ng", "%s#%s", getParams());
    }
  }

  public static class TrimmedValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTrimmedValueNotEquals("some strin");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTrimmedValueNotEquals("some strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTrimmedValueNotEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTrimmedValueNotEquals("some string", "%s#%s", getParams());
    }
  }

  public static class TruncatedValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueEquals(10, "some strin");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueEquals(4, " string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueEquals(4, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueEquals(5, " string   ");
    }
  }

  public static class TruncatedValueEqualsWithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueEquals(4, 10, " string   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueEquals(4, 10, " string   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueEquals(4, 10, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueEquals(5, 10, " string   ");
    }
  }

  public static class TruncatedValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueNotEquals(10, "ome strin", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueNotEquals(10, "ome strin");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueNotEquals(10, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueNotEquals(5, "some ");
    }
  }

  public static class TruncatedValueNotEqualsWithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyTruncatedValueNotEquals(4, 10, " tring   ", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyTruncatedValueNotEquals(4, 10, " tring   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyTruncatedValueNotEquals(4, 10, null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyTruncatedValueNotEquals(4, 10, " string   ");
    }
  }

  public static class IsMatches {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyMatches("[a-z ]+", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMatches(" tring   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyMatches((String) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyMatches("\\d+");
    }
  }

  public static class IsMatchesPattern {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyMatches(Pattern.compile("[a-z ]+"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyMatches(Pattern.compile("[a-z ]+"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyMatches((Pattern) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyMatches(Pattern.compile("^[A-Z ]+$"));
    }
  }

  public static class IsNotMatches {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyNotMatches("^[A-Z ]+$", "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotMatches(" tring   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toCS("some string    ").verifyNotMatches((String) null, "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toCS("some string    ").verifyNotMatches("[a-z ]+");
    }
  }

  public static class IsNotMatchesPattern {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toCS("some string    ").verifyNotMatches(Pattern.compile("^[A-Z ]+$"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toCS(null).verifyNotMatches(Pattern.compile("[a-z ]+"));
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
}

package org.catools.common.tests.waitVerify.interfaces;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.interfaces.waitVerify.CStringWaitVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

public class CStringWaitVerifyTest extends CBaseUnitTest {
  private static CStringWaitVerify toWaitVerify(String s) {
    return () -> s;
  }

  public static class CenterPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testVerifyEquals() {
      toWaitVerify("  some string ").verifyCenterPadEquals(40, "", "               some string              ");
      toWaitVerify("  SOM@#$%^& o ").verifyCenterPadEquals(29, "&%", "&%&%&%&  SOM@#$%^& o &%&%&%&%");
      toWaitVerify("  SOM@#$%^& o ").verifyCenterPadEquals(20, "#$%^", "#$%  SOM@#$%^& o #$%");
      toWaitVerify("  SOM@#$%^& o ").verifyCenterPadEquals(10, "", "  SOM@#$%^& o ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyCenterPadEquals(40, "", "               some string              ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string ").verifyCenterPadEquals(40, "", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string ").verifyCenterPadEquals(40, "", "             some string              ");
    }
  }

  public static class CenterPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string ").verifyCenterPadNotEquals(40, "", "              some string              ");
      toWaitVerify("  SOM@#$%^& o ").verifyCenterPadNotEquals(20, "#$%^", "#$%  SOM@$%^& o #$%");
      toWaitVerify("  SOM@#$%^& o ").verifyCenterPadNotEquals(10, "", "  SOM@#$%^& o");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyCenterPadNotEquals(40, "", "              some string              ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string ").verifyCenterPadNotEquals(40, "", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string ").verifyCenterPadNotEquals(40, "", "               some string              ");
    }
  }

  public static class Compare {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyCompare("  some string    ", 0);
      toWaitVerify("  SOME string    ").verifyCompare("  some string    ", -32);
      toWaitVerify(null).verifyCompare(null, 0);
      toWaitVerify("  some string    ").verifyCompare("  some String    ", 32);
      toWaitVerify("  some string    ").verifyCompare(null, 1);
      toWaitVerify(null).verifyCompare("  some string    ", -1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyCompare("  some string    ", 0);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyCompare(null, 0);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  SOME string    ").verifyCompare("  some string    ", -1);
    }
  }

  public static class CompareIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyCompareIgnoreCase("  SOME string    ", 0);
      toWaitVerify("  SOME string    ").verifyCompareIgnoreCase("  some string    ", 0);
      toWaitVerify(null).verifyCompareIgnoreCase(null, 0);
      toWaitVerify("  some string    ").verifyCompareIgnoreCase("  some xtring    ", -5);
      toWaitVerify("  some string    ").verifyCompareIgnoreCase(null, 1);
      toWaitVerify(null).verifyCompareIgnoreCase("  some string    ", -1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyCompareIgnoreCase("  SOME string    ", 0);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyCompareIgnoreCase(null, 0);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyCompareIgnoreCase("SOME string    ", 0);
    }
  }

  public static class Contains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyContains("so");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyContains("so");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyContains(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyContains("sox");
    }
  }

  public static class ContainsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  Some string    ").verifyContainsIgnoreCase(" so");
      toWaitVerify("  some $tring    ").verifyContainsIgnoreCase("$TRING");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyContainsIgnoreCase(" so");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  Some string    ").verifyContainsIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  Some string    ").verifyContainsIgnoreCase(" sox");
    }
  }

  public static class EndsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyEndsWith("   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEndsWith("   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyEndsWith(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyEndsWith(" a s ");
    }
  }

  public static class EndsWithAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyEndsWithAny(new CList<>("A", null, " s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEndsWithAny(new CList<>("A", null, " s "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyEndsWithAny(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyEndsWithAny(new CList<>("A", null, " sx "));
    }
  }

  public static class EndsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyEndsWithIgnoreCase("   s ");
      toWaitVerify("  some string   s ").verifyEndsWithIgnoreCase("   S ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEndsWithIgnoreCase("   S ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyEndsWithIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyEndsWithIgnoreCase("   SX ");
    }
  }

  public static class VerifyEndsWithNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyEndsWithNone(new CList<>("A", null, " S "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEndsWithNone(new CList<>("A", null, " S "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyEndsWithNone(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyEndsWithNone(new CList<>("A", null, " s "));
    }
  }

  public static class VerifyEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyEquals("  some string    ");
      toWaitVerify(null).verifyEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEquals("  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyEquals("  some string ");
    }
  }

  public static class VerifyEqualsAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyEqualsAny(new CList<>("", "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEqualsAny(new CList<>("", "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyEqualsAny(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyEqualsAny(new CList<>("", "  sometring    "));
    }
  }

  public static class VerifyEqualsAnyIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  some string    "));
      toWaitVerify("  some STRING    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  SOME string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEqualsAnyIgnoreCase(new CList<>("", "  some string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyEqualsAnyIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  somestring    "));
    }
  }

  public static class VerifyEqualsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyEqualsIgnoreCase("  SOME string    ");
      toWaitVerify(null).verifyEqualsIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEqualsIgnoreCase("  SOME string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyEqualsIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyEqualsIgnoreCase("  SOME string");
    }
  }

  public static class VerifyEqualsIgnoreWhiteSpaces {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  SOME string    ").verifyEqualsIgnoreWhiteSpaces("SO ME st ring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEqualsIgnoreWhiteSpaces("SO ME st ring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  SOME string    ").verifyEqualsIgnoreWhiteSpaces(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  SOME string    ").verifyEqualsIgnoreWhiteSpaces("SME st ring    ");
    }
  }

  public static class VerifyEqualsNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyEqualsNone(new CList<>("", "  some", "string    "));
      toWaitVerify("  some STRING    ").verifyEqualsNone(new CList<>("", "  SOME string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEqualsNone(new CList<>("", "  some", "string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyEqualsNone(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyEqualsNone(new CList<>("", "some", "  some string    "));
    }
  }

  public static class VerifyEqualsNoneIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "string    "));
      toWaitVerify("  some STRING    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  $OME string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "string    "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyEqualsNoneIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  some", "  some string    "));
    }
  }

  public static class VerifyIsAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("aiulajksn").verifyIsAlpha();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsAlpha(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("aiu1!lajksn").verifyIsAlpha(1);
    }
  }

  public static class VerifyIsEmptyOrAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("aiulajksn").verifyIsEmptyOrAlpha();
      toWaitVerify("").verifyIsEmptyOrAlpha(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsEmptyOrAlpha(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("aiu1 lajksn").verifyIsEmptyOrAlpha(1);
    }
  }

  public static class VerifyIsAlphaSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify(" aiu ajk sn").verifyIsAlphaSpace();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsAlphaSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("1 aiu ajk sn").verifyIsAlphaSpace(1);
    }
  }

  public static class VerifyIsAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("aiulaj45872ksn1").verifyIsAlphanumeric();
      toWaitVerify("blkajsblas").verifyIsAlphanumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsAlphanumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testInvalidChar() {
      toWaitVerify("aiulaj4\u5872ksn1").verifyIsAlphanumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("aiulaj4 5872ksn1").verifyIsAlphanumeric(1);
    }
  }

  public static class VerifyIsEmptyOrAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("aiulaj6265opksn").verifyIsEmptyOrAlphanumeric();
      toWaitVerify("").verifyIsEmptyOrAlphanumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsEmptyOrAlphanumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("aiulaj6!265opksn").verifyIsEmptyOrAlphanumeric(1);
    }
  }

  public static class VerifyIsAlphanumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("ai1ul90jksn").verifyIsAlphanumericSpace();
      toWaitVerify(" ai1ul90 ajk sn").verifyIsAlphanumericSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsAlphanumericSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("aksaskjhas654!").verifyIsAlphanumericSpace(1);
    }
  }

  public static class VerifyIsAsciiPrintable {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 32;
      toWaitVerify(new String(chars)).verifyIsAsciiPrintable();
      chars[5] = 33;
      toWaitVerify(new String(chars)).verifyIsAsciiPrintable(1);
      chars[5] = 125;
      toWaitVerify(new String(chars)).verifyIsAsciiPrintable(1);
      chars[5] = 126;
      toWaitVerify(new String(chars)).verifyIsAsciiPrintable(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsAsciiPrintable(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      toWaitVerify(new String(chars)).verifyIsAsciiPrintable(1);
    }
  }

  public static class VerifyIsNotAsciiPrintable {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      toWaitVerify(new String(chars)).verifyIsNotAsciiPrintable();
      chars[5] = 127;
      toWaitVerify(new String(chars)).verifyIsNotAsciiPrintable(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNotAsciiPrintable(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("asasa").verifyIsNotAsciiPrintable(1);
    }
  }

  public static class VerifyIsBlank {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify(null).verifyIsBlank();
      toWaitVerify("").verifyIsBlank(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("A").verifyIsBlank(1);
    }
  }

  public static class VerifyIsEmpty {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("").verifyIsEmpty();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsEmpty(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("s").verifyIsEmpty(1);
    }
  }

  public static class VerifyIsNotAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("aiu1lajks1n").verifyIsNotAlpha();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNotAlpha(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("alskdla").verifyIsNotAlpha(1);
    }
  }

  public static class VerifyIsEmptyOrNotAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("a1").verifyIsEmptyOrNotAlpha();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsEmptyOrNotAlpha(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("abs").verifyIsEmptyOrNotAlpha(1);
    }
  }

  public static class VerifyIsNotAlphaSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("1aiul ajk sn").verifyIsNotAlphaSpace();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNotAlphaSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("sdfghjk").verifyIsNotAlphaSpace(1);
    }
  }

  public static class VerifyIsNotAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("aiulaj4587 2ksn1").verifyIsNotAlphanumeric();
      toWaitVerify("blkajsbla!s").verifyIsNotAlphanumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNotAlphanumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("aiulaj45872ksn1").verifyIsNotAlphanumeric(1);
    }
  }

  public static class VerifyIsEmptyOrNotAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("aiulaj6265!opksn").verifyIsEmptyOrNotAlphanumeric();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsEmptyOrNotAlphanumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("aiulaj6265opksn").verifyIsEmptyOrNotAlphanumeric(1);
    }
  }

  public static class VerifyIsNotAlphanumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("ai1ul90jks!n").verifyIsNotAlphanumericSpace();
      toWaitVerify("ai1ul90jks !").verifyIsNotAlphanumericSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNotAlphanumericSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("ai1ul9 0jksn").verifyIsNotAlphanumericSpace(1);
    }
  }

  public static class VerifyIsNotBlank {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("ai1ul90jks !").verifyIsNotBlank();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNotBlank(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("").verifyIsNotBlank(1);
    }
  }

  public static class VerifyIsNotEmpty {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("ai1ul90jks !").verifyIsNotEmpty();
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNotEmpty(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("").verifyIsNotEmpty(1);
    }
  }

  public static class VerifyIsNotNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("1234567A").verifyIsNotNumeric();
      toWaitVerify("").verifyIsNotNumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNotNumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("1234567").verifyIsNotNumeric(1);
    }
  }

  public static class VerifyIsEmptyOrNotNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("12345A67").verifyIsEmptyOrNotNumeric();
      toWaitVerify("A").verifyIsEmptyOrNotNumeric(1);
      toWaitVerify("").verifyIsEmptyOrNotNumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsEmptyOrNotNumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("1234567").verifyIsEmptyOrNotNumeric(1);
    }
  }

  public static class VerifyIsNotNumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("234567!8").verifyIsNotNumericSpace();
      toWaitVerify(" 1254 7A86 1").verifyIsNotNumericSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNotNumericSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("2345678").verifyIsNotNumericSpace(1);
    }
  }

  public static class VerifyIsNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("1234567").verifyIsNumeric(3, 7);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("A1234567").verifyIsNumeric(1);
    }
  }

  public static class VerifyIsEmptyOrNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("1234567").verifyIsEmptyOrNumeric();
      toWaitVerify("").verifyIsEmptyOrNumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsEmptyOrNumeric(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("1234A567").verifyIsEmptyOrNumeric(1);
    }
  }

  public static class VerifyIsNumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("2345678").verifyIsNumericSpace();
      toWaitVerify(" 1254 786 1").verifyIsNumericSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyIsNumericSpace(1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("2345A678").verifyIsNumericSpace(1);
    }
  }

  public static class VerifyLeftValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyLeftValueEquals(7, "  some ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyLeftValueEquals(7, "  some ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyLeftValueEquals(7, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyLeftValueEquals(7, "some ");
    }
  }

  public static class VerifyLeftValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyLeftValueNotEquals(3, "  some ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyLeftValueNotEquals(3, "  some ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyLeftValueNotEquals(3, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyLeftValueNotEquals(7, "  some ");
    }
  }

  public static class VerifyLeftPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ");
      toWaitVerify("  some string   s ").verifyLeftPadEquals(30, "", "              some string   s ");
      toWaitVerify("  some string   s ").verifyLeftPadEquals(10, null, "  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyLeftPadEquals(40, "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ");
    }
  }

  public static class VerifyLeftPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ");
      toWaitVerify("  some string   s ").verifyLeftPadNotEquals(10, null, "  some string  s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyLeftPadNotEquals(40, "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s ");
    }
  }

  public static class VerifyLength {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyLengthEquals(18);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyLengthEquals(18);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyLengthEquals(17);
    }
  }

  public static class VerifyLengthNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyLengthNotEquals(17);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toWaitVerify(null).verifyLengthNotEquals(17);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyLengthNotEquals(18);
    }
  }

  public static class VerifyMidValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyMidValueEquals(2, 4, "some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyMidValueEquals(2, 4, "some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyMidValueEquals(2, 4, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyMidValueEquals(2, 3, "some");
    }
  }

  public static class VerifyMidValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyMidValueNotEquals(2, 5, "some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyMidValueNotEquals(2, 5, "some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyMidValueNotEquals(2, 5, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyMidValueNotEquals(2, 4, "some");
    }
  }

  public static class VerifyNotContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyNotContains("sO");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotContains("sO");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyNotContains(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyNotContains("som");
    }
  }

  public static class VerifyNotContainsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  Some string    ").verifyNotContainsIgnoreCase(" sX");
      toWaitVerify("  some $tring    ").verifyNotContainsIgnoreCase("STRING");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotContainsIgnoreCase("STRING");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some $tring    ").verifyNotContainsIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some $tring    ").verifyNotContainsIgnoreCase("TRING");
    }
  }

  public static class VerifyNotEndsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyNotEndsWith("   S ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotEndsWith("   S ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyNotEndsWith(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyNotEndsWith("   s ");
    }
  }

  public static class VerifyNotEndsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyNotEndsWithIgnoreCase("   x ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotEndsWithIgnoreCase("   x ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyNotEndsWithIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyNotEndsWithIgnoreCase("tring   S ");
    }
  }

  public static class VerifyNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyNotEquals("  some STRING    ");
      toWaitVerify("  some string    ").verifyNotEquals("  some String   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotEquals("  some STRING    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyNotEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyNotEquals("  some string    ");
    }
  }

  public static class VerifyNotEqualsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyNotEqualsIgnoreCase("  SOME tring    ");
      toWaitVerify(null).verifyNotEqualsIgnoreCase("");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotEqualsIgnoreCase("  SOME tring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyNotEqualsIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyNotEqualsIgnoreCase("  some STRING    ");
    }
  }

  public static class VerifyNotEqualsIgnoreWhiteSpaces {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  SOME string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotEqualsIgnoreWhiteSpaces("  SOME string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  some string    ");
    }
  }

  public static class VerifyNotStartsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyNotStartsWith("  S");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotStartsWith("  S");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyNotStartsWith(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyNotStartsWith("  s");
    }
  }

  public static class VerifyNotStartsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyNotStartsWithIgnoreCase("A");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotStartsWithIgnoreCase("A");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyNotStartsWithIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyNotStartsWithIgnoreCase("  ");
    }
  }

  public static class VerifyNumberOfMatchesEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyNumberOfMatchesEquals("s", 3);
      toWaitVerify("  some String   s ").verifyNumberOfMatchesEquals("s", 2);
      toWaitVerify("  some $tring   s ").verifyNumberOfMatchesEquals("$", 1);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNumberOfMatchesEquals("s", 3);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyNumberOfMatchesEquals(null, 3);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyNumberOfMatchesEquals("s", 1);
    }
  }

  public static class VerifyNumberOfMatchesNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyNumberOfMatchesNotEquals("s", 2);
      toWaitVerify("  some String   s ").verifyNumberOfMatchesNotEquals("s", 1);
      toWaitVerify("  some $tring   s ").verifyNumberOfMatchesNotEquals("$", 3);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNumberOfMatchesNotEquals("s", 2);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyNumberOfMatchesNotEquals(null, 2);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyNumberOfMatchesNotEquals("s", 3);
    }
  }

  public static class VerifyRemoveEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveEquals("s", "  ome tring    ");
      toWaitVerify("  some String   so ").verifyRemoveEquals("so", "  me String    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveEquals("s", "  ome tring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveEquals("s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveEquals("s", "  ome string    ");
    }
  }

  public static class VerifyRemoveEndEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveEndEquals("  some ", "  some string   s ");
      toWaitVerify("  some string   s ").verifyRemoveEndEquals("some string   s ", "  ");
      toWaitVerify("  some string   s ").verifyRemoveEndEquals("  some string   s ", "");
      toWaitVerify("  some String   s ").verifyRemoveEndEquals(null, "  some String   s ");
      toWaitVerify("  some String   s ").verifyRemoveEndEquals("tring   s ", "  some S");
      toWaitVerify("  some $tring   s ").verifyRemoveEndEquals("tring   s ", "  some $");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveEndEquals("  some ", "  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveEndEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveEndEquals("  some ", "some string   s ");
    }
  }

  public static class VerifyRemoveEndIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s ");
      toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseEquals("some String   s ", "  ");
      toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  sOME string   s ", "");
      toWaitVerify("  some String   s ").verifyRemoveEndIgnoreCaseEquals(null, "  some String   s ");
      toWaitVerify("  some String   s ").verifyRemoveEndIgnoreCaseEquals("tring   S ", "  some S");
      toWaitVerify("  some $tring   s ").verifyRemoveEndIgnoreCaseEquals("TRING   s ", "  some $");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s");
    }
  }

  public static class VerifyRemoveEndIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s ");
      toWaitVerify("  some String   s ").verifyRemoveEndIgnoreCaseNotEquals(null, "  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  Some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseNotEquals("  some ", "  some string   s ");
    }
  }

  public static class VerifyRemoveEndNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveEndNotEquals("  some ", "ome string   s ");
      toWaitVerify("  some String   s ").verifyRemoveEndNotEquals(null, "  some String   S");
      toWaitVerify("  some String   s ").verifyRemoveEndNotEquals("tring   S ", "  some s");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveEndNotEquals("  some ", "ome string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveEndNotEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveEndNotEquals("  some ", "  some string   s ");
    }
  }

  public static class VerifyRemoveIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveIgnoreCaseEquals("s", "  ome tring    ");
      toWaitVerify("  some String   so ").verifyRemoveIgnoreCaseEquals("SO", "  me String    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveIgnoreCaseEquals("s", "  ome tring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveIgnoreCaseEquals("s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveIgnoreCaseEquals("s", "  ome trng    ");
    }
  }

  public static class VerifyRemoveIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", "  ome Tring    ");
      toWaitVerify("  some String   so ").verifyRemoveIgnoreCaseNotEquals("SO", "  me String    x");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveIgnoreCaseNotEquals("s", "  ome Tring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveIgnoreCaseNotEquals("s", "  ome tring    ");
    }
  }

  public static class VerifyRemoveNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveNotEquals("s", "  ome Tring    ");
      toWaitVerify("  some String   so ").verifyRemoveNotEquals(null, "  me String    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveNotEquals("s", "  ome Tring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveNotEquals("s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveNotEquals("s", "  ome tring    ");
    }
  }

  public static class VerifyRemoveStartEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveStartEquals("  some ", "string   s ");
      toWaitVerify("  some string   s ").verifyRemoveStartEquals("some string   s ", "  some string   s ");
      toWaitVerify("  some string   s ").verifyRemoveStartEquals("  some string   s ", "");
      toWaitVerify("  some String   s ").verifyRemoveStartEquals(null, "  some String   s ");
      toWaitVerify("  some String   s ").verifyRemoveStartEquals("  some S", "tring   s ");
      toWaitVerify("  some $tring   s ").verifyRemoveStartEquals("  some $", "tring   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveStartEquals("  some ", "string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveStartEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveStartEquals("  some ", "string s ");
    }
  }

  public static class VerifyRemoveStartIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "string   s ");
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some ", "string   s ");
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("Some string   s ", "  some string   s ");
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some string   s ", "");
      toWaitVerify("  some String   s ").verifyRemoveStartIgnoreCaseEquals(null, "  some String   s ");
      toWaitVerify("  some String   s ").verifyRemoveStartIgnoreCaseEquals("  some s", "tring   s ");
      toWaitVerify("  some $tring   s ").verifyRemoveStartIgnoreCaseEquals("  some $", "tring   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveStartIgnoreCaseEquals("  some ", "string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "string s ");
    }
  }

  public static class VerifyRemoveStartIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", "String   s ");
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  Some ", "string  s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveStartIgnoreCaseNotEquals("  some ", "String   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testRemoveNull() {
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals(null, "string  s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", "string   s ");
    }
  }

  public static class VerifyRemoveStartNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRemoveStartNotEquals("  some", "string   s ");
      toWaitVerify("  some string   s ").verifyRemoveStartNotEquals(null, " Some string   s");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRemoveStartNotEquals("  some", "string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRemoveStartNotEquals("  some", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRemoveStartNotEquals("  some ", "string   s ");
    }
  }

  public static class VerifyReplaceEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReplaceEquals("s", "", "  ome tring    ");
      toWaitVerify("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   XX ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReplaceEquals("so", "XX", "  XXme String   XX ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some String   so ").verifyReplaceEquals("so", "XX", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   S ");
    }
  }

  public static class VerifyReplaceIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReplaceIgnoreCaseEquals("s", "|", "  |ome |tring   | ");
      toWaitVerify("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  some String   x ");
    }
  }

  public static class VerifyReplaceIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReplaceIgnoreCaseNotEquals("s", "|", "  |ome string   | ");
      toWaitVerify("  some String   so ").verifyReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some String   so ").verifyReplaceIgnoreCaseNotEquals("SO", "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyReplaceIgnoreCaseNotEquals("s", "|", "  |ome |tring   | ");
    }
  }

  public static class VerifyReplaceNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReplaceNotEquals("s", "", "  ome String    ");
      toWaitVerify("  some String   so ").verifyReplaceNotEquals("so", "XX", "  XXme XXtring   XX ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReplaceNotEquals("so", "XX", "  XXme XXtring   XX ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some String   so ").verifyReplaceNotEquals("so", "XX", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some String   so ").verifyReplaceNotEquals("so", "XX", "  XXme String   XX ");
    }
  }

  public static class VerifyReplaceOnceEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReplaceOnceEquals("s", "", "  ome string   s ");
      toWaitVerify("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  XXme String   so ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReplaceOnceEquals("so", "XX", "  XXme String   so ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some String   so ").verifyReplaceOnceEquals("so", "XX", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  Xome String   so ");
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s ");
      toWaitVerify("  some String   so ").verifyReplaceOnceIgnoreCaseEquals("SO", "x", "  xme String   so ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |some string   s ");
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals("s", "|", "  \\|ome string   s ");
      toWaitVerify("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some String   so ").verifyReplaceOnceIgnoreCaseNotEquals("SO", "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyReplaceOnceIgnoreCaseNotEquals("s", "|", "  |ome string   s ");
    }
  }

  public static class VerifyReplaceOnceNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReplaceOnceNotEquals("s", "", "  ome String   s ");
      toWaitVerify("  some String   so ").verifyReplaceOnceNotEquals("so", "XX", "  XXme String   XX ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReplaceOnceNotEquals("s", "", "  ome String   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyReplaceOnceNotEquals("s", "", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyReplaceOnceNotEquals("s", "", "  ome string   s ");
    }
  }

  public static class VerifyReverseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReverseEquals(" s   gnirts emos  ");
      toWaitVerify("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%$#@ emos  ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReverseEquals(" os   .*&^%$#@ emos  ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some @#$%^&*.   so ").verifyReverseEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%# emos  ");
    }
  }

  public static class VerifyReverseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyReverseNotEquals(" s   gnirt emos  ");
      toWaitVerify("  some @#$%^&*.   so ").verifyReverseNotEquals(" os   .*%$#@ emos  ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyReverseNotEquals(" os   .*%$#@ emos  ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some @#$%^&*.   so ").verifyReverseNotEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyReverseNotEquals(" s   gnirts emos  ");
    }
  }

  public static class VerifyRightValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyRightValueEquals(7, "ing    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRightValueEquals(7, "ing    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyRightValueEquals(7, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyRightValueEquals(7, "ing   ");
    }
  }

  public static class VerifyRightValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyRightValueNotEquals(6, "ing    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRightValueNotEquals(6, "ing    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyRightValueNotEquals(6, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyRightValueNotEquals(7, "ing    ");
    }
  }

  public static class VerifyRightPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx");
      toWaitVerify("  some string   s ").verifyRightPadEquals(30, "", "  some string   s             ");
      toWaitVerify("  some string   s ").verifyRightPadEquals(10, null, "  some string   s ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRightPadEquals(40, "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRightPadEquals(40, "x", "  some string   s ");
    }
  }

  public static class VerifyRightPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx");
      toWaitVerify("  some string   s ").verifyRightPadNotEquals(10, null, "  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyRightPadNotEquals(40, "x", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx");
    }
  }

  public static class VerifyStartsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyStartsWith("  some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStartsWith("  some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyStartsWith(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyStartsWith("some");
    }
  }

  public static class VerifyStartsWithAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "  some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStartsWithAny(new CList<>("A", null, "  some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyStartsWithAny(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "some"));
    }
  }

  public static class VerifyStartsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyStartsWithIgnoreCase("  some");
      toWaitVerify("  some string   s ").verifyStartsWithIgnoreCase("  Some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStartsWithIgnoreCase("  some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyStartsWithIgnoreCase(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyStartsWithIgnoreCase(" some");
    }
  }

  public static class VerifyStartsWithNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifyStartsWithNone(new CList<>("A", null, "  Some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStartsWithNone(new CList<>("A", null, "  Some"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifyStartsWithNone(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifyStartsWithNone(new CList<>("A", "  some", " Some"));
    }
  }

  public static class VerifyStripedEndValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyStripedEndValue(" ", "  some string");
      toWaitVerify("  some string    ").verifyStripedEndValue(null, "  some string");
      toWaitVerify("|some string||||").verifyStripedEndValue("|", "|some string");
      toWaitVerify("|some string||||").verifyStripedEndValue(null, "|some string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStripedEndValue("|", "|some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("|some string||||").verifyStripedEndValue("|", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("|some string||||").verifyStripedEndValue("|", "|som string");
    }
  }

  public static class VerifyStripedEndValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyStripedEndValueNot(" ", "  ome string");
      toWaitVerify("  some string    ").verifyStripedEndValueNot(null, "  ome string");
      toWaitVerify("|some string||||").verifyStripedEndValueNot("|", "|som string");
      toWaitVerify("|some string||||").verifyStripedEndValueNot(null, "|soe string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStripedEndValueNot("|", "|som string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("|some string||||").verifyStripedEndValueNot("|", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("|some string||||").verifyStripedEndValueNot("|", "|some string");
    }
  }

  public static class VerifyStripedStartValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyStripedStartValue(" ", "some string    ");
      toWaitVerify("  some string    ").verifyStripedStartValue(null, "some string    ");
      toWaitVerify("|some string||||").verifyStripedStartValue("|", "some string||||");
      toWaitVerify("|some string||||").verifyStripedStartValue(null, "|some string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStripedStartValue("|", "some string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("|some string||||").verifyStripedStartValue("|", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("|some string||||").verifyStripedStartValue("|", "some string|");
    }
  }

  public static class VerifyStripedStartValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyStripedStartValueNot(" ", "ome string    ");
      toWaitVerify("  some string    ").verifyStripedStartValueNot(null, "ome string    ");
      toWaitVerify("|some string||||").verifyStripedStartValueNot("|", "ome string||||");
      toWaitVerify("|some string||||").verifyStripedStartValueNot(null, "|ome string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStripedStartValueNot(" ", "ome string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyStripedStartValueNot(" ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyStripedStartValueNot(" ", "some string    ");
    }
  }

  public static class VerifyStripedValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyStripedValue(" ", "some string");
      toWaitVerify("  some string    ").verifyStripedValue(null, "some string");
      toWaitVerify("|some string||||").verifyStripedValue("|", "some string");
      toWaitVerify("|some string||||").verifyStripedValue(null, "|some string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStripedValue("|", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("|some string||||").verifyStripedValue("|", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("|some string||||").verifyStripedValue("|", "some String");
    }
  }

  public static class VerifyStripedValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifyStripedValueNot(" ", "somestring");
      toWaitVerify("  some string    ").verifyStripedValueNot(null, "som string");
      toWaitVerify("|some string||||").verifyStripedValueNot("|", "somestring");
      toWaitVerify("|some string||||").verifyStripedValueNot(null, "|soe string||||");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyStripedValueNot(" ", "somestring");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifyStripedValueNot(" ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifyStripedValueNot(" ", "some string");
    }
  }

  public static class VerifySubstringEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringEquals(0, "  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringEquals(0, "  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringEquals(0, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringEquals(0, "  some string");
    }
  }

  public static class VerifySubstringEquals_WithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringEquals(0, 3, "  s");
      toWaitVerify("  some string    ").verifySubstringEquals(2, 4, "so");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringEquals(0, 3, "  s");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringEquals(0, 3, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringEquals(0, 3, "  some");
    }
  }

  public static class VerifySubstringAfterEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringAfterEquals(" s", "ome string    ");
      toWaitVerify("  some string    ").verifySubstringAfterEquals(null, "");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringAfterEquals(" s", "ome string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringAfterEquals(" s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringAfterEquals(" s", "Some string    ");
    }
  }

  public static class VerifySubstringAfterLastEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringAfterLastEquals(" s", "tring    ");
      toWaitVerify("  some string    ").verifySubstringAfterLastEquals(null, "");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringAfterLastEquals(" s", "tring    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringAfterLastEquals(" s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringAfterLastEquals(" s", "String    ");
    }
  }

  public static class VerifySubstringAfterLastNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringAfterLastNotEquals(" s", "trng    ");
      toWaitVerify("  some string    ").verifySubstringAfterLastNotEquals(null, "something");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringAfterLastNotEquals(" s", "trng    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringAfterLastNotEquals(" s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringAfterLastNotEquals(" s", "tring    ");
    }
  }

  public static class VerifySubstringAfterNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringAfterNotEquals(" s", "ome string   ");
      toWaitVerify("  some string    ").verifySubstringAfterNotEquals(null, "X");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringAfterNotEquals(" s", "ome string   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringAfterNotEquals(" s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringAfterNotEquals(" s", "ome string    ");
    }
  }

  public static class VerifySubstringBeforeEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringBeforeEquals(" st", "  some");
      toWaitVerify("  some string    ").verifySubstringBeforeEquals(null, "  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringBeforeEquals(" st", "  some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringBeforeEquals(" st", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringBeforeEquals(" st", "some");
    }
  }

  public static class VerifySubstringBeforeLastEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringBeforeLastEquals(" s", "  some");
      toWaitVerify("  some string    ").verifySubstringBeforeLastEquals(null, "  some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringBeforeLastEquals(" s", "  some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringBeforeLastEquals(" s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringBeforeLastEquals(" s", "some");
    }
  }

  public static class SubstringBeforeLastNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringBeforeLastNotEquals(" s", " some");
      toWaitVerify("  some string    ").verifySubstringBeforeLastNotEquals(null, " some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringBeforeLastNotEquals("s", " some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringBeforeLastNotEquals("S ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringBeforeLastNotEquals(null, "  some string    ");
    }
  }

  public static class VerifySubstringBeforeNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringBeforeNotEquals(" st", " some");
      toWaitVerify("  some string    ").verifySubstringBeforeNotEquals(null, "  some string   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringBeforeNotEquals(" st", " some");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringBeforeNotEquals(" st", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringBeforeNotEquals(" st", "  some");
    }
  }

  public static class VerifySubstringBetweenEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringBetweenEquals("  ", "    ", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringBetweenEquals("  ", "    ", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toWaitVerify("  some string    ").verifySubstringBetweenEquals(null, "    ", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toWaitVerify("  some string    ").verifySubstringBetweenEquals("  ", null, "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringBetweenEquals("  ", "    ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringBetweenEquals("  ", "    ", "sme string");
    }
  }

  public static class VerifySubstringBetweenNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "sme string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringBetweenNotEquals("  ", "    ", "sme string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "some string");
    }
  }

  public static class VerifySubstringNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringNotEquals(0, " some string    ");
      toWaitVerify("  some string    ").verifySubstringNotEquals(2, "ome string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringNotEquals(0, " some string    ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringNotEquals(0, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringNotEquals(0, "  some string    ");
    }
  }

  public static class VerifySubstringNotEquals_WithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string    ").verifySubstringNotEquals(0, 3, " s");
      toWaitVerify("  some string    ").verifySubstringNotEquals(2, 4, "sox");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringNotEquals(0, 3, " s");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string    ").verifySubstringNotEquals(0, 3, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string    ").verifySubstringNotEquals(0, 3, "  s");
    }
  }

  public static class SubstringsBetweenEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenEquals(null, "s", new CList<>(" ", "", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenEquals(" ", null, new CList<>(" ", "", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenEquals(" ", "s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegativeOnSize() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", ""));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", " "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative2() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  ", " "));
    }
  }

  public static class SubstringsBetweenContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenContains(" ", "s", " ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringsBetweenContains(" ", "s", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenContains(null, "s", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenContains(" ", null, "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenContains(" ", "s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenContains(" ", "s", "some string");
    }
  }

  public static class SubstringsBetweenNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotEquals(null, "s", new CList<>(" ", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotEquals(" ", null, new CList<>(" ", "  "));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "", "  "));
    }
  }

  public static class SubstringsBetweenNotContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotContains(" ", "s", "some string");
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifySubstringsBetweenNotContains("some ", "ing", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testOpenNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotContains(null, "s", "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testCloseNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotContains(" ", null, "some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("  some string   s ").verifySubstringsBetweenNotContains("some ", "ing", "str");
    }
  }

  public static class TrimmedValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyTrimmedValueEquals("some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyTrimmedValueEquals("some string");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyTrimmedValueEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyTrimmedValueEquals("some st$ng");
    }
  }

  public static class TrimmedValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyTrimmedValueNotEquals("some strin");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyTrimmedValueNotEquals("some strin");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyTrimmedValueNotEquals(null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyTrimmedValueNotEquals("some string");
    }
  }

  public static class TruncatedValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyTruncatedValueEquals(10, "some strin");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyTruncatedValueEquals(4, " string   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyTruncatedValueEquals(4, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyTruncatedValueEquals(5, " string   ");
    }
  }

  public static class TruncatedValueEqualsWithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyTruncatedValueEquals(4, 10, " string   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyTruncatedValueEquals(4, 10, " string   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyTruncatedValueEquals(4, 10, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyTruncatedValueEquals(5, 10, " string   ");
    }
  }

  public static class TruncatedValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyTruncatedValueNotEquals(10, "ome strin");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyTruncatedValueNotEquals(10, "ome strin");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyTruncatedValueNotEquals(10, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyTruncatedValueNotEquals(5, "some ");
    }
  }

  public static class TruncatedValueNotEqualsWithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyTruncatedValueNotEquals(4, 10, " tring   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyTruncatedValueNotEquals(4, 10, " tring   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyTruncatedValueNotEquals(4, 10, null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyTruncatedValueNotEquals(4, 10, " string   ");
    }
  }

  public static class IsMatches {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyMatches("[a-z ]+");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyMatches(" tring   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = NullPointerException.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyMatches((String) null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyMatches("\\d+");
    }
  }

  public static class IsMatchesPattern {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyMatches(Pattern.compile("[a-z ]+"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyMatches(Pattern.compile("[a-z ]+"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyMatches((Pattern) null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyMatches(Pattern.compile("^[A-Z ]+$"));
    }
  }

  public static class IsNotMatches {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyNotMatches("^[A-Z ]+$");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotMatches(" tring   ");
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyNotMatches((String) null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyNotMatches("[a-z ]+");
    }
  }

  public static class IsNotMatchesPattern {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      toWaitVerify("some string    ").verifyNotMatches(Pattern.compile("^[A-Z ]+$"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      toWaitVerify(null).verifyNotMatches(Pattern.compile("[a-z ]+"));
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      toWaitVerify("some string    ").verifyNotMatches((Pattern) null);
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      toWaitVerify("some string    ").verifyNotMatches(Pattern.compile("[a-z ]+"));
    }
  }
}

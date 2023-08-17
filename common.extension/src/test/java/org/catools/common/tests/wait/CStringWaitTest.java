package org.catools.common.tests.wait;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.CStringWait;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

public class CStringWaitTest extends CBaseUnitTest {
  private static final String CSTRING1 =
      "This is the first String with some 1209op31mk2w9@# values.";
  private String NULL = null;
  private String EMPTY = "";

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitCenterPadEquals("  some string    ", 10, "@", "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitCenterPadEquals(
                "  some string    ", 30, "@", "@@@@@@  some string    @@@@@@@", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCenterPadEquals("  some string    ", 10, NULL, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitCenterPadNotEquals("  some string    ", 10, "@", " some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitCenterPadNotEquals(
                "  some string    ", 30, "@", "@@@@@  some string    @@@@@@@", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitCenterPadNotEquals("  some string    ", 10, NULL, " some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompare() {
    CVerify.Bool.isTrue(
        CStringWait.waitCompare("  some string    ", "  some string    ", 0, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCompare("  SOME string    ", "  some string    ", -32, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(CStringWait.waitCompare(NULL, null, 0, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCompare("  some string    ", "  some String    ", 32, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCompare("  some string    ", null, 1, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCompare(NULL, "  some string    ", -1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompareIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait.waitCompareIgnoreCase("  some string    ", "  SOME string    ", 0, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCompareIgnoreCase("  SOME string    ", "  some string    ", 0, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCompareIgnoreCase(NULL, null, 0, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCompareIgnoreCase("  some string    ", "  some xtring    ", -5, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCompareIgnoreCase("  some string    ", null, 1, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitCompareIgnoreCase(NULL, "  some string    ", -1, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    CVerify.Bool.isTrue(
        CStringWait.waitContains("  some string    ", "so", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait.waitContainsIgnoreCase("  Some string    ", " so", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitContainsIgnoreCase("  some $tring    ", "$TRING", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWith() {
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWith("  some string   s ", "   s ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithAny() {
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWithAny("  some string   s ", new CList<>("A", null, " s "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWithIgnoreCase("  some string   s ", "   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWithIgnoreCase("  some string   s ", "   S ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithNone() {
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWithNone("  some string   s ", new CList<>("A", " s  "), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWithNone("  some string   s ", new CList<>("A", " S "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitEquals("  some string    ", "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(CStringWait.waitEquals(NULL, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotEquals("  some string    ", "  some string1    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(CStringWait.waitNotEquals(NULL, new String(), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAny() {
    CVerify.Bool.isTrue(
        CStringWait.waitEqualsAny("  some string    ", new CList<>("", "  some string    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAnyIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitEqualsAnyIgnoreCase(
                "  some STRING    ", new CList<>("", "  SOME string    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait.waitEqualsIgnoreCase("  some string    ", "  SOME string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(CStringWait.waitEqualsIgnoreCase(NULL, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreWhiteSpaces() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitEqualsIgnoreWhiteSpaces("  some string    ", " s o me s t r ing    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitEqualsIgnoreWhiteSpaces("  some string    ", "somestring", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNone() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitEqualsNone("  some string    ", new CList<>("", "  some String    "), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitEqualsNone("  some string    ", new CList<>("", null), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNoneIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitEqualsNoneIgnoreCase(
                "  some STRING    ", new CList<>("", "  $ome string    "), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitEqualsNoneIgnoreCase("  some string    ", new CList<>("", null), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlpha() {
    CVerify.Bool.isTrue(CStringWait.waitIsAlpha("aiulajksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlpha() {
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrAlpha("aiulajksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrAlpha("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphaSpace() {
    CVerify.Bool.isTrue(CStringWait.waitIsAlphaSpace(" aiul ajk sn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumeric() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsAlphanumeric("aiulaj45872ksn1", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsAlphanumeric("blkajsblas", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlphanumeric() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsEmptyOrAlphanumeric("aiulaj6265opksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrAlphanumeric("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumericSpace() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsAlphanumericSpace("ai1ul90jksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitIsAlphanumericSpace(" ai1ul90 ajk sn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();

    chars[5] = 32;
    CVerify.Bool.isTrue(
        CStringWait.waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 33;
    CVerify.Bool.isTrue(
        CStringWait.waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 125;
    CVerify.Bool.isTrue(
        CStringWait.waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 126;
    CVerify.Bool.isTrue(
        CStringWait.waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlank() {
    CVerify.Bool.isTrue(CStringWait.waitIsBlank(NULL, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsBlank(EMPTY, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    CVerify.Bool.isTrue(CStringWait.waitIsEmpty(EMPTY, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlpha() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotAlpha("123aasf2", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsNotAlpha("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlpha() {
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrNotAlpha("aiulaj626", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrNotAlpha("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphaSpace() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotAlphaSpace("aiulaj626", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsNotAlphaSpace("@ a", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumeric() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsNotAlphanumeric("aiulaj626!5opksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsNotAlphanumeric("@#.*", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlphanumeric() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsEmptyOrNotAlphanumeric("aiulaj626 5opksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitIsEmptyOrNotAlphanumeric("@#.*", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrNotAlphanumeric("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumericSpace() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsNotAlphanumericSpace("aiulaj626 !5opksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsNotAlphanumericSpace("@#.*", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 30;
    CVerify.Bool.isTrue(
        CStringWait.waitIsNotAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 31;
    CVerify.Bool.isTrue(
        CStringWait.waitIsNotAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 127;
    CVerify.Bool.isTrue(
        CStringWait.waitIsNotAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotBlank() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotBlank(CSTRING1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotEmpty(CSTRING1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumeric() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotNumeric("a1234567", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsNotNumeric(" ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotNumeric() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsEmptyOrNotNumeric("a123 4567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumericSpace() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotNumericSpace("a123 4567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumeric() {
    CVerify.Bool.isTrue(CStringWait.waitIsNumeric("1234567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNumeric() {
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrNumeric("1234567", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrNumeric("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumericSpace() {
    CVerify.Bool.isTrue(CStringWait.waitIsNumericSpace("2345678", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(CStringWait.waitIsNumericSpace(" 1254 786 1", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitLeftPadEquals("  some string    ", 10, "@", "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitLeftPadEquals(
                "  some string    ", 30, "@", "@@@@@@@@@@@@@  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitLeftPadEquals("  some string    ", 10, NULL, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitLeftPadEquals(
                "  some string   s ", 30, "", "              some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitLeftPadNotEquals("  some string    ", 10, "@", " some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitLeftPadNotEquals(
                "  some string    ", 30, "@", "@@@@@@@@@@@@  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitLeftPadNotEquals("  some string    ", 10, NULL, " some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitLeftPadNotEquals(
                "  some string   s ", 30, "", "             some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitLeftValueEquals("  some string    ", 7, "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitLeftValueNotEquals("  some string    ", 7, " some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitLengthEquals("  some string   s ", 18, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthNotEquals() {
    CVerify.Bool.isTrue(CStringWait.waitLengthNotEquals("aasa", 0, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitMidValueEquals("  some string    ", 2, 4, "some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitMidValueNotEquals("  some string    ", 2, 4, "some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotContains("  some string    ", "sox", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotContainsIgnoreCase("  Some string    ", " sox", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitNotContainsIgnoreCase("  some $tring    ", "x$TRING", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWith() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotEndsWith("  some string   s ", ".* ", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitNotEndsWith("  some string   s ", "S ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWithIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotEndsWithIgnoreCase("  some string   s ", "   $ ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotEqualsIgnoreCase("  some string    ", "  $OME string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(CStringWait.waitNotEqualsIgnoreCase(NULL, "", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreWhiteSpaces() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotEqualsIgnoreWhiteSpaces("  some string    ", "  $OME string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWith() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotStartsWith("  some string   s ", " some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWithIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotStartsWithIgnoreCase("  some string   s ", " Some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitNumberOfMatchesEquals("  some string   s ", "s", 3, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitNumberOfMatchesEquals("  some String   s ", "s", 2, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitNumberOfMatchesEquals("  some $tring   s ", "$", 1, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitNumberOfMatchesNotEquals("  some String   s ", "s", 1, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndEquals("  some string   s ", "  some ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEndEquals("  some string   s ", "some string   s ", "  ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEndEquals("  some string   s ", "  some string   s ", "", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEndEquals("  some String   s ", null, "  some String   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEndEquals("  some String   s ", "tring   s ", "  some S", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEndEquals("  some $tring   s ", "tring   s ", "  some $", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndIgnoreCaseEquals(
                "  some string   s ", "  Some ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndIgnoreCaseEquals("  some string   s ", "some String   s ", "  ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndIgnoreCaseEquals("  some string   s ", "  sOME string   s ", "", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndIgnoreCaseEquals(
                "  some String   s ", null, "  some String   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndIgnoreCaseEquals("  some String   s ", "tring   S ", "  some S", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndIgnoreCaseEquals("  some $tring   s ", "TRING   s ", "  some $", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndIgnoreCaseNotEquals("  some STRING    ", " ", "  STRING    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEndNotEquals("  some STRING    ", "STRING    ", "  SOME ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEquals("  some string   s ", "s", "  ome tring    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEquals("  some String   so ", "so", "  me String    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveIgnoreCaseEquals("  some string   s ", "s", "  ome tring    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveIgnoreCaseEquals("  some String   so ", "SO", "  me String    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveIgnoreCaseNotEquals("  some STRING    ", " ", "  some STRING ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveNotEquals("  some STRING    ", "STRING   ", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveStartEquals("  some string   s ", "  some ", "string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartEquals(
                "  some string   s ", "some string   s ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveStartEquals("  some string   s ", "  some string   s ", "", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveStartEquals("  some String   s ", null, "  some String   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveStartEquals("  some String   s ", "  some S", "tring   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveStartEquals("  some $tring   s ", "  some $", "tring   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "  some ", "string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "  Some ", "string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "Some string   s ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "  Some string   s ", "", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseEquals(
                "  some String   s ", null, "  some String   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseEquals(
                "  some String   s ", "  some s", "tring   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseEquals(
                "  some $tring   s ", "  some $", "tring   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseNotEquals(
                "  some $tring   s ", "  some ", " $tring   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveStartNotEquals("  some string   s ", "  some ", "String   S ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitReplaceEquals("  some string   s ", "s", "", "  ome tring    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceEquals("  some String   so ", "so", "XX", "  XXme String   XX ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome |tring   | ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   x ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitReplaceNotEquals("  some String   s ", " s", "x", " ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceEquals("  some string   s ", "s", "", "  ome string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceEquals(
                "  some String   so ", "so", "XX", "  XXme String   so ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   so ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceNotEquals("  some String   s ", " s", "x", " ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitReverseEquals("  some string   s ", " s   gnirts emos  ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitReverseEquals("  some @#$%^&*.   so ", " os   .*&^%$#@ emos  ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitReverseNotEquals("  some string  s ", " s   gnirts emos  ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitReverseNotEquals("  some @#$%^*.   so ", " os   .*&^%$#@ emos  ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitRightPadEquals("  some string    ", 10, "@", "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitRightPadEquals(
                "  some string    ", 30, "@", "  some string    @@@@@@@@@@@@@", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitRightPadEquals("  some string    ", 10, NULL, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRightPadNotEquals(
                "  some string   s ", 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitRightValueEquals("  some string    ", 7, "ing    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitRightValueNotEquals("  some string    ", 7, "iNg    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWith() {
    CVerify.Bool.isTrue(
        CStringWait.waitStartsWith("  some string   s ", "  some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithAny() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitStartsWithAny("  some string   s ", new CList<>("A", null, "  some"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithIgnoreCase() {
    CVerify.Bool.isTrue(
        CStringWait.waitStartsWithIgnoreCase("  some string   s ", "  some", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStartsWithIgnoreCase("  some string   s ", "  Some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithNone() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitStartsWithNone("  some string   s ", new CList<>(" some", "     ", " s "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValue() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValue("  some string    ", " ", "  some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValue("  some string    ", null, "  some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValue("|some string||||", "|", "|some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValue("|some string||||", null, "|some string||||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValueNot() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValueNot("  some string    ", " ", " some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValueNot("  some string    ", null, "  somestring", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValueNot("|some string||||", "|", "|some string|", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValueNot("|some string||||", null, "|some string|||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValue() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValue("  some string    ", " ", "some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValue("  some string    ", null, "some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValue("|some string||||", "|", "some string||||", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValue("|some string||||", null, "|some string||||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValueNot() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValueNot("  some string    ", " ", "some string   ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValueNot("  some string    ", null, "some string   ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValueNot("|some string||||", "|", "some string|||", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValueNot("|some string||||", null, "|some string|||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValue() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValue("  some string    ", " ", "some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValue("  some string    ", null, "some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValue("|some string||||", "|", "some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValue("|some string||||", null, "|some string||||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValueNot() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValueNot("  some string    ", " ", " some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValueNot("  some string    ", null, "somestring", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValueNot("|some string||||", "|", "some string|", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValueNot("|some string||||", null, "|some string|||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterEquals("  some string    ", " s", "ome string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterEquals("  some string    ", null, "", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterLastEquals("  some string    ", " s", "tring    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterLastEquals("  some string    ", null, "", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterLastNotEquals("  some string    ", " s", "trinG    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterNotEquals("  some string    ", " s", "omE string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeEquals("  some string    ", " st", "  some", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringBeforeEquals("  some string    ", null, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeLastEquals("  some string    ", " s", "  some", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringBeforeLastEquals("  some string    ", null, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeLastNotEquals("  some string    ", " s", "  somE", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeNotEquals("  some string    ", " st", "  Some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringBetweenEquals("  some string    ", "  ", "    ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringBetweenNotEquals(
                "  some string    ", "  ", "    ", "some String", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringEquals("  some string    ", 0, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringEquals("  some string    ", 0, 3, "  s", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringEquals("  some string    ", 2, 4, "so", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringNotEquals("  some string    ", 0, " some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringNotEquals("  some string    ", 0, 3, " s", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringNotEquals("  some string    ", 2, 4, "so ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenContains() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringsBetweenContains("  some string   s ", " ", "s", " ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringsBetweenEquals(
                "  some string   s ", " ", "s", new CList<>(" ", "", "  "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotContains() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringsBetweenNotContains("  some string   s ", " ", "s", "x", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotEquals() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringsBetweenNotEquals(
                "  some string   s ", " ", "s", new CList<>(" ", "S", "  "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValue() {
    CVerify.Bool.isTrue(
        CStringWait.waitTrimmedValueEquals("some string    ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValueNot() {
    CVerify.Bool.isTrue(
        CStringWait.waitTrimmedValueNotEquals("some string    ", " some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValue() {
    CVerify.Bool.isTrue(
        CStringWait.waitTruncatedValueEquals("some string    ", 10, "some strin", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitTruncatedValueEquals("some string    ", 4, 10, " string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValueNot() {
    CVerify.Bool.isTrue(
        CStringWait.waitTruncatedValueNot("some string    ", 10, "some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        CStringWait.waitTruncatedValueNot("some string    ", 4, 10, " string  ", 0, 100),
        "%s#%s",
        getParams());
  }

  // Negative Scenarios
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitCenterPadEquals("  some string    ", 10, NULL, "  somestring    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitCenterPadNotEquals("  some string    ", 10, NULL, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompare_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitCompare(NULL, "  some string    ", 0, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompareIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitCompareIgnoreCase("  some string    ", "  ScOME string    ", 1, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitContains("  some string    ", "sso", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitContainsIgnoreCase("  Some string    ", " sco", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWith_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWith("  some string   s ", "   x ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithAny_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWithAny("  some string   s ", new CList<>("X", null, " D "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWithIgnoreCase("  some string   s ", "   xs ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithNone_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitEndsWithNone("  some string   s ", new CList<>("a", " s "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitEquals(NULL, "x", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAny_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitEqualsAny("  some string    ", new CList<>("", "  sxme string    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAnyIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitEqualsAnyIgnoreCase(
                "  some STRING    ", new CList<>("", "  SXME string    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitEqualsIgnoreCase("  some string    ", "  SXME string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreWhiteSpaces_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitEqualsIgnoreWhiteSpaces("  some string    ", " s x me s t r ing    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNone_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitEqualsNone(
                "  some string    ", new CList<>("  some string    ", "  sxe String    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNoneIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitEqualsNoneIgnoreCase(
                "  some string    ", new CList<>("  some string    ", null), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlpha_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsAlpha("aiul@ajksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrAlpha_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrAlpha("&", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphaSpace_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsAlphaSpace(" aiu@l ajk sn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumeric_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsAlphanumeric("blka$jsblas", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumericSpace_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsAlphanumericSpace(" ai1ul#@90 ajk sn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 30;
    CVerify.Bool.isTrue(
        CStringWait.waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsBlank_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsBlank("asas", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsEmpty("asas", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlpha_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotAlpha("aasf", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlpha_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrNotAlpha("aiulaj", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphaSpace_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotAlphaSpace("aiulaj", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumeric_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsNotAlphanumeric("aiulaj6265opksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlphanumeric_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsEmptyOrNotAlphanumeric("aiulaj6265opksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumericSpace_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitIsNotAlphanumericSpace("aiulaj6265opksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 32;
    CVerify.Bool.isTrue(
        CStringWait.waitIsNotAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotBlank_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotBlank("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotEmpty(EMPTY, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumeric_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotNumeric("1", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotNumeric_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrNotNumeric("1234567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumericSpace_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsNotNumericSpace("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumeric_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsNumeric("123a4567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNumeric_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsEmptyOrNumeric("1a234567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumericSpace_Negative() {
    CVerify.Bool.isTrue(CStringWait.waitIsNumericSpace("23a45678", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitLeftPadEquals(
                "  some string   s ", 30, "", "            some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitLeftPadNotEquals(
                "  some string   s ", 30, "", "              some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitLeftValueEquals("  some string    ", 7, " some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitLeftValueNotEquals("  some string    ", 7, "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitLengthEquals("  some string   s ", 7, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitLengthNotEquals("  some string   s ", 18, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitMidValueEquals("  some string    ", 2, 4, "some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitMidValueNotEquals("  some string    ", 2, 4, "some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotContains("  some string    ", "some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotContainsIgnoreCase("  Some string    ", " Some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWith_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotEndsWith("  some string   s ", "s ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWithIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotEndsWithIgnoreCase("  some string   s ", "   S ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotEqualsIgnoreCase("  some string    ", "  SOME string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreWhiteSpaces_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitNotEqualsIgnoreWhiteSpaces("  some string    ", "  so me string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWith_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotStartsWith("  some string   s ", "  some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWithIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotStartsWithIgnoreCase("  some string   s ", "  some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNumberOfMatchesEquals("  some string   s ", "s", 2, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNumberOfMatchesNotEquals("  some String   s ", "s", 2, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndEquals("  some string   s ", "  some ", "  some string   S ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndIgnoreCaseEquals("  some $tring   s ", "TRING   x ", "  some $", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveEndIgnoreCaseNotEquals("  some STRING    ", " ", "  some STRING   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEndNotEquals("  some STRING    ", "STRING    ", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveEquals("  some String   so ", "so", "  me string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveIgnoreCaseEquals("  some String   so ", "SO", "  me Xtring    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveIgnoreCaseNotEquals("  some STRING    ", " ", "someSTRING", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveNotEquals("  some STRING    ", "STRING   ", "  some  ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartEquals("  some string   s ", "  some ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "  some ", "some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRemoveStartIgnoreCaseNotEquals(
                "  some $tring   s ", "  some ", "$tring   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitRemoveStartNotEquals("  some string   s ", "  some ", "string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceEquals("  some String   so ", "so", "XX", "  XXme String   XX", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   x", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " xomextring  x ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceNotEquals("  some String   s ", " s", "x", " xome String  x ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceEquals(
                "  some String   so ", "so", "XX", "  some String   so ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome string   s", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " xome String   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitReplaceOnceNotEquals("  some String   s ", " s", "x", " xome String   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitReverseEquals("  some @#$%^&*.   so ", " os   .*&^%$#@ emos ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitReverseNotEquals("  some string  s ", " s  gnirts emos  ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitRightPadEquals("  some string    ", 10, "@", "  some string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitRightPadNotEquals(
                "  some string   s ", 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitRightValueEquals("  some string    ", 7, "ing   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitRightValueNotEquals("  some string    ", 7, "ing    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWith_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitStartsWith("  some string   s ", " some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithAny_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitStartsWithAny("  some string   s ", new CList<>("A", null, "some"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitStartsWithIgnoreCase("  some string   s ", "some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithNone_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitStartsWithNone("  some string   s ", new CList<>("  some", "     ", "s "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValue_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValue("  some string    ", " ", "  some string ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValueNot_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedEndValueNot("  some string    ", " ", "  some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValue_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValue("  some string    ", " ", "some string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValueNot_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedStartValueNot("  some string    ", " ", "some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValue_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValue("  some string    ", " ", "some string ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValueNot_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitStripedValueNot("  some string    ", " ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals1_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterEquals("  some string    ", " s", "ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals2_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterEquals("  some string    ", null, "s", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals1_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterLastEquals("  some string    ", null, "s", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals2_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterLastEquals("  some string    ", " s", "tring   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterLastNotEquals("  some string    ", " s", "tring    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringAfterNotEquals("  some string    ", " s", "ome string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals1_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeEquals("  some string    ", " st", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals2_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeEquals("  some string    ", null, "  some string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals1_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeLastEquals("  some string    ", " s", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals2_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeLastEquals("  some string    ", " s", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeLastNotEquals("  some string    ", " s", "  some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringBeforeNotEquals("  some string    ", " st", "  some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringBetweenEquals("  some string    ", "  ", "    ", "some sstring", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringBetweenNotEquals(
                "  some string    ", "  ", "    ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals1_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringEquals("  some string    ", 0, "  some strin    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals2_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringEquals("  some string    ", 0, 3, "  sx", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals3_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringEquals("  some string    ", 2, 4, "sxo", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals1_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringNotEquals("  some string    ", 0, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals2_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringNotEquals("  some string    ", 0, 3, "  s", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals3_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringNotEquals("  some string    ", 2, 4, "so", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenContains_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringsBetweenContains("  some string   s ", " ", "s", "x", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringsBetweenEquals(
                "  some string   s ", " ", "s", new CList<>(" ", "S"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotContains_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitSubstringsBetweenNotContains("  some string   s ", " ", "s", " ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotEquals_Negative() {
    CVerify.Bool.isTrue(
        CStringWait
            .waitSubstringsBetweenNotEquals(
                "  some string   s ", " ", "s", new CList<>(" ", "", "  "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValue_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitTrimmedValueEquals("some string    ", "some String", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValueNot_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitTrimmedValueNotEquals("some string    ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue1_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitTruncatedValueEquals("some string    ", 10, "some sxtrin", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue2_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitTruncatedValueEquals("some string    ", 4, 10, " sxtring   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot1_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitTruncatedValueNot("some string    ", 10, "some strin", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot2_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitTruncatedValueNot("some string    ", 4, 10, " string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatches() {
    CVerify.Bool.isTrue(
        CStringWait.waitMatches("some string    ", "[a-z ]+", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_ExpectedNull() {
    CVerify.Bool.isTrue(CStringWait.waitMatches(null, " tring   ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitMatches("some string    ", "\\d+", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesPattern() {
    CVerify.Bool.isTrue(
        CStringWait.waitMatches("some string    ", Pattern.compile("[a-z ]+"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_ExpectedNull() {
    CVerify.Bool.isTrue(CStringWait.waitMatches(null, " tring   ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitMatches("some string    ", Pattern.compile("\\d+"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatches() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotMatches("some string    ", "\\d+", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_ExpectedNull() {
    CVerify.Bool.isTrue(CStringWait.waitNotMatches(null, " tring   ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotMatches("some string    ", "[a-z ]+", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatchesPattern() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotMatches("some string    ", Pattern.compile("\\d+"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_ExpectedNull() {
    CVerify.Bool.isTrue(CStringWait.waitNotMatches(null, " tring   ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_Negative() {
    CVerify.Bool.isTrue(
        CStringWait.waitNotMatches("some string    ", Pattern.compile("[a-z ]+"), 0, 100),
        "%s#%s",
        getParams());
  }
}

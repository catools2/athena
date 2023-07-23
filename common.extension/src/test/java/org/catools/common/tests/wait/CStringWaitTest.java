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
        toWaiter().waitCenterPadEquals("  some string    ", 10, "@", "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitCenterPadEquals(
                "  some string    ", 30, "@", "@@@@@@  some string    @@@@@@@", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCenterPadEquals("  some string    ", 10, NULL, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitCenterPadNotEquals("  some string    ", 10, "@", " some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitCenterPadNotEquals(
                "  some string    ", 30, "@", "@@@@@  some string    @@@@@@@", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitCenterPadNotEquals("  some string    ", 10, NULL, " some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompare() {
    CVerify.Bool.isTrue(
        toWaiter().waitCompare("  some string    ", "  some string    ", 0, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCompare("  SOME string    ", "  some string    ", -32, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(toWaiter().waitCompare(NULL, null, 0, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCompare("  some string    ", "  some String    ", 32, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCompare("  some string    ", null, 1, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCompare(NULL, "  some string    ", -1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompareIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter().waitCompareIgnoreCase("  some string    ", "  SOME string    ", 0, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCompareIgnoreCase("  SOME string    ", "  some string    ", 0, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCompareIgnoreCase(NULL, null, 0, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCompareIgnoreCase("  some string    ", "  some xtring    ", -5, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCompareIgnoreCase("  some string    ", null, 1, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitCompareIgnoreCase(NULL, "  some string    ", -1, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    CVerify.Bool.isTrue(
        toWaiter().waitContains("  some string    ", "so", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsIgnoreCase("  Some string    ", " so", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitContainsIgnoreCase("  some $tring    ", "$TRING", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWith() {
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWith("  some string   s ", "   s ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithAny() {
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWithAny("  some string   s ", new CList<>("A", null, " s "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWithIgnoreCase("  some string   s ", "   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWithIgnoreCase("  some string   s ", "   S ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithNone() {
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWithNone("  some string   s ", new CList<>("A", " s  "), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWithNone("  some string   s ", new CList<>("A", " S "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitEquals("  some string    ", "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(toWaiter().waitEquals(NULL, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAny() {
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsAny("  some string    ", new CList<>("", "  some string    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAnyIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsAnyIgnoreCase(
                "  some STRING    ", new CList<>("", "  SOME string    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsIgnoreCase("  some string    ", "  SOME string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(toWaiter().waitEqualsIgnoreCase(NULL, null, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreWhiteSpaces() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsIgnoreWhiteSpaces("  some string    ", " s o me s t r ing    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsIgnoreWhiteSpaces("  some string    ", "somestring", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNone() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsNone("  some string    ", new CList<>("", "  some String    "), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsNone("  some string    ", new CList<>("", null), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNoneIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsNoneIgnoreCase(
                "  some STRING    ", new CList<>("", "  $ome string    "), 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsNoneIgnoreCase("  some string    ", new CList<>("", null), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlpha() {
    CVerify.Bool.isTrue(toWaiter().waitIsAlpha("aiulajksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlpha() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrAlpha("aiulajksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrAlpha("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphaSpace() {
    CVerify.Bool.isTrue(toWaiter().waitIsAlphaSpace(" aiul ajk sn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumeric() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsAlphanumeric("aiulaj45872ksn1", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsAlphanumeric("blkajsblas", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlphanumeric() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsEmptyOrAlphanumeric("aiulaj6265opksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrAlphanumeric("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumericSpace() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsAlphanumericSpace("ai1ul90jksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitIsAlphanumericSpace(" ai1ul90 ajk sn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();

    chars[5] = 32;
    CVerify.Bool.isTrue(
        toWaiter().waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 33;
    CVerify.Bool.isTrue(
        toWaiter().waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 125;
    CVerify.Bool.isTrue(
        toWaiter().waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 126;
    CVerify.Bool.isTrue(
        toWaiter().waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlank() {
    CVerify.Bool.isTrue(toWaiter().waitIsBlank(NULL, 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsBlank(EMPTY, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmpty(EMPTY, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlpha() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotAlpha("123aasf2", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsNotAlpha("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlpha() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrNotAlpha("aiulaj626", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrNotAlpha("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphaSpace() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotAlphaSpace("aiulaj626", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsNotAlphaSpace("@ a", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumeric() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotAlphanumeric("aiulaj626!5opksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsNotAlphanumeric("@#.*", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlphanumeric() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsEmptyOrNotAlphanumeric("aiulaj626 5opksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitIsEmptyOrNotAlphanumeric("@#.*", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrNotAlphanumeric("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumericSpace() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotAlphanumericSpace("aiulaj626 !5opksn", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsNotAlphanumericSpace("@#.*", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 30;
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 31;
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());

    chars[5] = 127;
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotBlank() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotBlank(CSTRING1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotEmpty(CSTRING1, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumeric() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotNumeric("a1234567", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsNotNumeric(" ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotNumeric() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsEmptyOrNotNumeric("a123 4567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumericSpace() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotNumericSpace("a123 4567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumeric() {
    CVerify.Bool.isTrue(toWaiter().waitIsNumeric("1234567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNumeric() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrNumeric("1234567", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrNumeric("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumericSpace() {
    CVerify.Bool.isTrue(toWaiter().waitIsNumericSpace("2345678", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(toWaiter().waitIsNumericSpace(" 1254 786 1", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitLeftPadEquals("  some string    ", 10, "@", "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitLeftPadEquals(
                "  some string    ", 30, "@", "@@@@@@@@@@@@@  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitLeftPadEquals("  some string    ", 10, NULL, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitLeftPadEquals(
                "  some string   s ", 30, "", "              some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitLeftPadNotEquals("  some string    ", 10, "@", " some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitLeftPadNotEquals(
                "  some string    ", 30, "@", "@@@@@@@@@@@@  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitLeftPadNotEquals("  some string    ", 10, NULL, " some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitLeftPadNotEquals(
                "  some string   s ", 30, "", "             some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitLeftValueEquals("  some string    ", 7, "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitLeftValueNotEquals("  some string    ", 7, " some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitLengthEquals("  some string   s ", 18, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthNotEquals() {
    CVerify.Bool.isTrue(toWaiter().waitLengthNotEquals("aasa", 0, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitMidValueEquals("  some string    ", 2, 4, "some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitMidValueNotEquals("  some string    ", 2, 4, "some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContains("  some string    ", "sox", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContainsIgnoreCase("  Some string    ", " sox", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotContainsIgnoreCase("  some $tring    ", "x$TRING", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWith() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEndsWith("  some string   s ", ".* ", 0, 100), "%s#%s", getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNotEndsWith("  some string   s ", "S ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWithIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEndsWithIgnoreCase("  some string   s ", "   $ ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsIgnoreCase("  some string    ", "  $OME string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(toWaiter().waitNotEqualsIgnoreCase(NULL, "", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreWhiteSpaces() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsIgnoreWhiteSpaces("  some string    ", "  $OME string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWith() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotStartsWith("  some string   s ", " some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWithIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotStartsWithIgnoreCase("  some string   s ", " Some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitNumberOfMatchesEquals("  some string   s ", "s", 3, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNumberOfMatchesEquals("  some String   s ", "s", 2, 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitNumberOfMatchesEquals("  some $tring   s ", "$", 1, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitNumberOfMatchesNotEquals("  some String   s ", "s", 1, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndEquals("  some string   s ", "  some ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEndEquals("  some string   s ", "some string   s ", "  ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEndEquals("  some string   s ", "  some string   s ", "", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEndEquals("  some String   s ", null, "  some String   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEndEquals("  some String   s ", "tring   s ", "  some S", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEndEquals("  some $tring   s ", "tring   s ", "  some $", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndIgnoreCaseEquals(
                "  some string   s ", "  Some ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndIgnoreCaseEquals("  some string   s ", "some String   s ", "  ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndIgnoreCaseEquals("  some string   s ", "  sOME string   s ", "", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndIgnoreCaseEquals(
                "  some String   s ", null, "  some String   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndIgnoreCaseEquals("  some String   s ", "tring   S ", "  some S", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndIgnoreCaseEquals("  some $tring   s ", "TRING   s ", "  some $", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndIgnoreCaseNotEquals("  some STRING    ", " ", "  STRING    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEndNotEquals("  some STRING    ", "STRING    ", "  SOME ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEquals("  some string   s ", "s", "  ome tring    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEquals("  some String   so ", "so", "  me String    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveIgnoreCaseEquals("  some string   s ", "s", "  ome tring    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveIgnoreCaseEquals("  some String   so ", "SO", "  me String    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveIgnoreCaseNotEquals("  some STRING    ", " ", "  some STRING ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveNotEquals("  some STRING    ", "STRING   ", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveStartEquals("  some string   s ", "  some ", "string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartEquals(
                "  some string   s ", "some string   s ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveStartEquals("  some string   s ", "  some string   s ", "", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveStartEquals("  some String   s ", null, "  some String   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveStartEquals("  some String   s ", "  some S", "tring   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveStartEquals("  some $tring   s ", "  some $", "tring   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "  some ", "string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "  Some ", "string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "Some string   s ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "  Some string   s ", "", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseEquals(
                "  some String   s ", null, "  some String   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseEquals(
                "  some String   s ", "  some s", "tring   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseEquals(
                "  some $tring   s ", "  some $", "tring   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseNotEquals(
                "  some $tring   s ", "  some ", " $tring   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveStartNotEquals("  some string   s ", "  some ", "String   S ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitReplaceEquals("  some string   s ", "s", "", "  ome tring    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceEquals("  some String   so ", "so", "XX", "  XXme String   XX ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome |tring   | ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   x ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitReplaceNotEquals("  some String   s ", " s", "x", " ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceEquals("  some string   s ", "s", "", "  ome string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceEquals(
                "  some String   so ", "so", "XX", "  XXme String   so ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome string   s ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   so ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceNotEquals("  some String   s ", " s", "x", " ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitReverseEquals("  some string   s ", " s   gnirts emos  ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitReverseEquals("  some @#$%^&*.   so ", " os   .*&^%$#@ emos  ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitReverseNotEquals("  some string  s ", " s   gnirts emos  ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitReverseNotEquals("  some @#$%^*.   so ", " os   .*&^%$#@ emos  ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitRightPadEquals("  some string    ", 10, "@", "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRightPadEquals(
                "  some string    ", 30, "@", "  some string    @@@@@@@@@@@@@", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitRightPadEquals("  some string    ", 10, NULL, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRightPadNotEquals(
                "  some string   s ", 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitRightValueEquals("  some string    ", 7, "ing    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitRightValueNotEquals("  some string    ", 7, "iNg    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWith() {
    CVerify.Bool.isTrue(
        toWaiter().waitStartsWith("  some string   s ", "  some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithAny() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitStartsWithAny("  some string   s ", new CList<>("A", null, "  some"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithIgnoreCase() {
    CVerify.Bool.isTrue(
        toWaiter().waitStartsWithIgnoreCase("  some string   s ", "  some", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStartsWithIgnoreCase("  some string   s ", "  Some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithNone() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitStartsWithNone("  some string   s ", new CList<>(" some", "     ", " s "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValue() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValue("  some string    ", " ", "  some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValue("  some string    ", null, "  some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValue("|some string||||", "|", "|some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValue("|some string||||", null, "|some string||||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValueNot() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValueNot("  some string    ", " ", " some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValueNot("  some string    ", null, "  somestring", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValueNot("|some string||||", "|", "|some string|", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValueNot("|some string||||", null, "|some string|||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValue() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValue("  some string    ", " ", "some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValue("  some string    ", null, "some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValue("|some string||||", "|", "some string||||", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValue("|some string||||", null, "|some string||||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValueNot() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValueNot("  some string    ", " ", "some string   ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValueNot("  some string    ", null, "some string   ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValueNot("|some string||||", "|", "some string|||", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValueNot("|some string||||", null, "|some string|||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValue() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValue("  some string    ", " ", "some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValue("  some string    ", null, "some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValue("|some string||||", "|", "some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValue("|some string||||", null, "|some string||||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValueNot() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValueNot("  some string    ", " ", " some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValueNot("  some string    ", null, "somestring", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValueNot("|some string||||", "|", "some string|", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValueNot("|some string||||", null, "|some string|||", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterEquals("  some string    ", " s", "ome string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterEquals("  some string    ", null, "", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterLastEquals("  some string    ", " s", "tring    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterLastEquals("  some string    ", null, "", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterLastNotEquals("  some string    ", " s", "trinG    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterNotEquals("  some string    ", " s", "omE string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeEquals("  some string    ", " st", "  some", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringBeforeEquals("  some string    ", null, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeLastEquals("  some string    ", " s", "  some", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringBeforeLastEquals("  some string    ", null, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeLastNotEquals("  some string    ", " s", "  somE", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeNotEquals("  some string    ", " st", "  Some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringBetweenEquals("  some string    ", "  ", "    ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringBetweenNotEquals(
                "  some string    ", "  ", "    ", "some String", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringEquals("  some string    ", 0, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringEquals("  some string    ", 0, 3, "  s", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringEquals("  some string    ", 2, 4, "so", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringNotEquals("  some string    ", 0, " some string    ", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringNotEquals("  some string    ", 0, 3, " s", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringNotEquals("  some string    ", 2, 4, "so ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenContains() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringsBetweenContains("  some string   s ", " ", "s", " ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringsBetweenEquals(
                "  some string   s ", " ", "s", new CList<>(" ", "", "  "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotContains() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringsBetweenNotContains("  some string   s ", " ", "s", "x", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotEquals() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringsBetweenNotEquals(
                "  some string   s ", " ", "s", new CList<>(" ", "S", "  "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValue() {
    CVerify.Bool.isTrue(
        toWaiter().waitTrimmedValueEquals("some string    ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValueNot() {
    CVerify.Bool.isTrue(
        toWaiter().waitTrimmedValueNotEquals("some string    ", " some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValue() {
    CVerify.Bool.isTrue(
        toWaiter().waitTruncatedValueEquals("some string    ", 10, "some strin", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitTruncatedValueEquals("some string    ", 4, 10, " string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValueNot() {
    CVerify.Bool.isTrue(
        toWaiter().waitTruncatedValueNot("some string    ", 10, "some string", 0, 100),
        "%s#%s",
        getParams());
    CVerify.Bool.isTrue(
        toWaiter().waitTruncatedValueNot("some string    ", 4, 10, " string  ", 0, 100),
        "%s#%s",
        getParams());
  }

  // Negative Scenarios
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitCenterPadEquals("  some string    ", 10, NULL, "  somestring    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitCenterPadNotEquals("  some string    ", 10, NULL, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompare_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitCompare(NULL, "  some string    ", 0, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompareIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitCompareIgnoreCase("  some string    ", "  ScOME string    ", 1, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitContains("  some string    ", "sso", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitContainsIgnoreCase("  Some string    ", " sco", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWith_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWith("  some string   s ", "   x ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithAny_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWithAny("  some string   s ", new CList<>("X", null, " D "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWithIgnoreCase("  some string   s ", "   xs ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithNone_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitEndsWithNone("  some string   s ", new CList<>("a", " s "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitEquals(NULL, "x", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAny_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsAny("  some string    ", new CList<>("", "  sxme string    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAnyIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsAnyIgnoreCase(
                "  some STRING    ", new CList<>("", "  SXME string    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitEqualsIgnoreCase("  some string    ", "  SXME string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreWhiteSpaces_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsIgnoreWhiteSpaces("  some string    ", " s x me s t r ing    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNone_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsNone(
                "  some string    ", new CList<>("  some string    ", "  sxe String    "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNoneIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitEqualsNoneIgnoreCase(
                "  some string    ", new CList<>("  some string    ", null), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlpha_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsAlpha("aiul@ajksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrAlpha_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrAlpha("&", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphaSpace_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsAlphaSpace(" aiu@l ajk sn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumeric_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsAlphanumeric("blka$jsblas", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumericSpace_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsAlphanumericSpace(" ai1ul#@90 ajk sn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 30;
    CVerify.Bool.isTrue(
        toWaiter().waitIsAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsBlank_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsBlank("asas", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmpty("asas", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlpha_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotAlpha("aasf", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlpha_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrNotAlpha("aiulaj", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphaSpace_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotAlphaSpace("aiulaj", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumeric_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotAlphanumeric("aiulaj6265opksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlphanumeric_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsEmptyOrNotAlphanumeric("aiulaj6265opksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumericSpace_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotAlphanumericSpace("aiulaj6265opksn", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 32;
    CVerify.Bool.isTrue(
        toWaiter().waitIsNotAsciiPrintable(String.valueOf(chars), 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotBlank_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotBlank("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotEmpty(EMPTY, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumeric_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotNumeric("1", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotNumeric_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrNotNumeric("1234567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumericSpace_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsNotNumericSpace("", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumeric_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsNumeric("123a4567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNumeric_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsEmptyOrNumeric("1a234567", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumericSpace_Negative() {
    CVerify.Bool.isTrue(toWaiter().waitIsNumericSpace("23a45678", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitLeftPadEquals(
                "  some string   s ", 30, "", "            some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitLeftPadNotEquals(
                "  some string   s ", 30, "", "              some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitLeftValueEquals("  some string    ", 7, " some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitLeftValueNotEquals("  some string    ", 7, "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitLengthEquals("  some string   s ", 7, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitLengthNotEquals("  some string   s ", 18, 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitMidValueEquals("  some string    ", 2, 4, "some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitMidValueNotEquals("  some string    ", 2, 4, "some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContains("  some string    ", "some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotContainsIgnoreCase("  Some string    ", " Some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWith_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEndsWith("  some string   s ", "s ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWithIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEndsWithIgnoreCase("  some string   s ", "   S ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotEqualsIgnoreCase("  some string    ", "  SOME string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreWhiteSpaces_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitNotEqualsIgnoreWhiteSpaces("  some string    ", "  so me string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWith_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotStartsWith("  some string   s ", "  some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWithIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotStartsWithIgnoreCase("  some string   s ", "  some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNumberOfMatchesEquals("  some string   s ", "s", 2, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNumberOfMatchesNotEquals("  some String   s ", "s", 2, 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndEquals("  some string   s ", "  some ", "  some string   S ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndIgnoreCaseEquals("  some $tring   s ", "TRING   x ", "  some $", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveEndIgnoreCaseNotEquals("  some STRING    ", " ", "  some STRING   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEndNotEquals("  some STRING    ", "STRING    ", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveEquals("  some String   so ", "so", "  me string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveIgnoreCaseEquals("  some String   so ", "SO", "  me Xtring    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveIgnoreCaseNotEquals("  some STRING    ", " ", "someSTRING", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveNotEquals("  some STRING    ", "STRING   ", "  some  ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartEquals("  some string   s ", "  some ", "  some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseEquals(
                "  some string   s ", "  some ", "some string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRemoveStartIgnoreCaseNotEquals(
                "  some $tring   s ", "  some ", "$tring   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitRemoveStartNotEquals("  some string   s ", "  some ", "string   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceEquals("  some String   so ", "so", "XX", "  XXme String   XX", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   x", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " xomextring  x ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceNotEquals("  some String   s ", " s", "x", " xome String  x ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceEquals(
                "  some String   so ", "so", "XX", "  some String   so ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome string   s", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " xome String   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitReplaceOnceNotEquals("  some String   s ", " s", "x", " xome String   s ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitReverseEquals("  some @#$%^&*.   so ", " os   .*&^%$#@ emos ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitReverseNotEquals("  some string  s ", " s  gnirts emos  ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitRightPadEquals("  some string    ", 10, "@", "  some string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitRightPadNotEquals(
                "  some string   s ", 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitRightValueEquals("  some string    ", 7, "ing   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitRightValueNotEquals("  some string    ", 7, "ing    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWith_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitStartsWith("  some string   s ", " some", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithAny_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitStartsWithAny("  some string   s ", new CList<>("A", null, "some"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithIgnoreCase_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitStartsWithIgnoreCase("  some string   s ", "some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithNone_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitStartsWithNone("  some string   s ", new CList<>("  some", "     ", "s "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValue_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValue("  some string    ", " ", "  some string ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValueNot_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedEndValueNot("  some string    ", " ", "  some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValue_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValue("  some string    ", " ", "some string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValueNot_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedStartValueNot("  some string    ", " ", "some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValue_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValue("  some string    ", " ", "some string ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValueNot_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitStripedValueNot("  some string    ", " ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals1_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterEquals("  some string    ", " s", "ome string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals2_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterEquals("  some string    ", null, "s", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals1_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterLastEquals("  some string    ", null, "s", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals2_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterLastEquals("  some string    ", " s", "tring   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterLastNotEquals("  some string    ", " s", "tring    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringAfterNotEquals("  some string    ", " s", "ome string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals1_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeEquals("  some string    ", " st", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals2_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeEquals("  some string    ", null, "  some string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals1_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeLastEquals("  some string    ", " s", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals2_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeLastEquals("  some string    ", " s", "  some ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeLastNotEquals("  some string    ", " s", "  some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringBeforeNotEquals("  some string    ", " st", "  some", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringBetweenEquals("  some string    ", "  ", "    ", "some sstring", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringBetweenNotEquals(
                "  some string    ", "  ", "    ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals1_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringEquals("  some string    ", 0, "  some strin    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals2_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringEquals("  some string    ", 0, 3, "  sx", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals3_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringEquals("  some string    ", 2, 4, "sxo", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals1_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringNotEquals("  some string    ", 0, "  some string    ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals2_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringNotEquals("  some string    ", 0, 3, "  s", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals3_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringNotEquals("  some string    ", 2, 4, "so", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenContains_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringsBetweenContains("  some string   s ", " ", "s", "x", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringsBetweenEquals(
                "  some string   s ", " ", "s", new CList<>(" ", "S"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotContains_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitSubstringsBetweenNotContains("  some string   s ", " ", "s", " ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotEquals_Negative() {
    CVerify.Bool.isTrue(
        toWaiter()
            .waitSubstringsBetweenNotEquals(
                "  some string   s ", " ", "s", new CList<>(" ", "", "  "), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValue_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitTrimmedValueEquals("some string    ", "some String", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValueNot_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitTrimmedValueNotEquals("some string    ", "some string", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue1_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitTruncatedValueEquals("some string    ", 10, "some sxtrin", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue2_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitTruncatedValueEquals("some string    ", 4, 10, " sxtring   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot1_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitTruncatedValueNot("some string    ", 10, "some strin", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot2_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitTruncatedValueNot("some string    ", 4, 10, " string   ", 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatches() {
    CVerify.Bool.isTrue(
        toWaiter().waitMatches("some string    ", "[a-z ]+", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_ExpectedNull() {
    CVerify.Bool.isTrue(toWaiter().waitMatches(null, " tring   ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitMatches("some string    ", "\\d+", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesPattern() {
    CVerify.Bool.isTrue(
        toWaiter().waitMatches("some string    ", Pattern.compile("[a-z ]+"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_ExpectedNull() {
    CVerify.Bool.isTrue(toWaiter().waitMatches(null, " tring   ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitMatches("some string    ", Pattern.compile("\\d+"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatches() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotMatches("some string    ", "\\d+", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_ExpectedNull() {
    CVerify.Bool.isTrue(toWaiter().waitNotMatches(null, " tring   ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotMatches("some string    ", "[a-z ]+", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatchesPattern() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotMatches("some string    ", Pattern.compile("\\d+"), 0, 100),
        "%s#%s",
        getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_ExpectedNull() {
    CVerify.Bool.isTrue(toWaiter().waitNotMatches(null, " tring   ", 0, 100), "%s#%s", getParams());
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_Negative() {
    CVerify.Bool.isTrue(
        toWaiter().waitNotMatches("some string    ", Pattern.compile("[a-z ]+"), 0, 100),
        "%s#%s",
        getParams());
  }

  private CStringWait toWaiter() {
    return new CStringWait();
  }
}

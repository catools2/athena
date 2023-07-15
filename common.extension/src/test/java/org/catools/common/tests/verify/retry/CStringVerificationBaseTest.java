package org.catools.common.tests.verify.retry;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.hard.CStringVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public abstract class CStringVerificationBaseTest extends CBaseUnitTest {
  private static final String CSTRING1 =
      "This is the first String with some 1209op31mk2w9@# values.";
  private String NULL = null;
  private String EMPTY = "";

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testCenterPadEquals() {
    verify(
        string ->
            string.centerPadEquals(
                "  some string    ", 10, "@", "  some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.centerPadEquals(
                "  some string    ",
                30,
                "@",
                "@@@@@@  some string    @@@@@@@",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.centerPadEquals(
                "  some string    ", 10, NULL, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testCenterPadNotEquals() {
    verify(
        string ->
            string.centerPadNotEquals(
                "  some string    ", 10, "@", " some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.centerPadNotEquals(
                "  some string    ",
                30,
                "@",
                "@@@@@  some string    @@@@@@@",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.centerPadNotEquals(
                "  some string    ", 10, NULL, " some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testCompare() {
    verify(
        string ->
            string.compare("  some string    ", "  some string    ", 0, "%s#%s", getParams()));
    verify(
        string ->
            string.compare("  SOME string    ", "  some string    ", -32, "%s#%s", getParams()));
    verify(string -> string.compare(NULL, null, 0, "%s#%s", getParams()));
    verify(
        string ->
            string.compare("  some string    ", "  some String    ", 32, "%s#%s", getParams()));
    verify(string -> string.compare("  some string    ", null, 1, "%s#%s", getParams()));
    verify(string -> string.compare(NULL, "  some string    ", -1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testCompareIgnoreCase() {
    verify(
        string ->
            string.compareIgnoreCase(
                "  some string    ", "  SOME string    ", 0, "%s#%s", getParams()));
    verify(
        string ->
            string.compareIgnoreCase(
                "  SOME string    ", "  some string    ", 0, "%s#%s", getParams()));
    verify(string -> string.compareIgnoreCase(NULL, null, 0, "%s#%s", getParams()));
    verify(
        string ->
            string.compareIgnoreCase(
                "  some string    ", "  some xtring    ", -5, "%s#%s", getParams()));
    verify(string -> string.compareIgnoreCase("  some string    ", null, 1, "%s#%s", getParams()));
    verify(string -> string.compareIgnoreCase(NULL, "  some string    ", -1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testContains() {
    verify(string -> string.contains("  some string    ", "so", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testContainsIgnoreCase() {
    verify(string -> string.containsIgnoreCase("  Some string    ", " so", "%s#%s", getParams()));
    verify(
        string -> string.containsIgnoreCase("  some $tring    ", "$TRING", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEndsWith() {
    verify(string -> string.endsWith("  some string   s ", "   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEndsWithAny() {
    verify(
        string ->
            string.endsWithAny(
                "  some string   s ", new CList<>("A", null, " s "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEndsWithIgnoreCase() {
    verify(
        string -> string.endsWithIgnoreCase("  some string   s ", "   s ", "%s#%s", getParams()));
    verify(
        string -> string.endsWithIgnoreCase("  some string   s ", "   S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEndsWithNone() {
    verify(
        string ->
            string.endsWithNone(
                "  some string   s ", new CList<>("A", " s  "), "%s#%s", getParams()));
    verify(
        string ->
            string.endsWithNone(
                "  some string   s ", new CList<>("A", " S "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEquals() {
    verify(string -> string.equals("  some string    ", "  some string    ", "%s#%s", getParams()));
    verify(string -> string.equals(NULL, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsAny() {
    verify(
        string ->
            string.equalsAny(
                "  some string    ", new CList<>("", "  some string    "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsAnyIgnoreCase() {
    verify(
        string ->
            string.equalsAnyIgnoreCase(
                "  some STRING    ", new CList<>("", "  SOME string    "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsIgnoreCase() {
    verify(
        string ->
            string.equalsIgnoreCase(
                "  some string    ", "  SOME string    ", "%s#%s", getParams()));
    verify(string -> string.equalsIgnoreCase(NULL, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsIgnoreWhiteSpaces() {
    verify(
        string ->
            string.equalsIgnoreWhiteSpaces(
                "  some string    ", " s o me s t r ing    ", "%s#%s", getParams()));
    verify(
        string ->
            string.equalsIgnoreWhiteSpaces(
                "  some string    ", "somestring", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsNone() {
    verify(
        string ->
            string.equalsNone(
                "  some string    ", new CList<>("", "  some String    "), "%s#%s", getParams()));
    verify(
        string ->
            string.equalsNone("  some string    ", new CList<>("", null), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testEqualsNoneIgnoreCase() {
    verify(
        string ->
            string.equalsNoneIgnoreCase(
                "  some STRING    ", new CList<>("", "  $ome string    "), "%s#%s", getParams()));
    verify(
        string ->
            string.equalsNoneIgnoreCase(
                "  some string    ", new CList<>("", null), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsAlpha() {
    verify(string -> string.isAlpha("aiulajksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsEmptyOrAlpha() {
    verify(string -> string.isEmptyOrAlpha("aiulajksn", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrAlpha("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsAlphaSpace() {
    verify(string -> string.isAlphaSpace(" aiul ajk sn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsAlphanumeric() {
    verify(string -> string.isAlphanumeric("aiulaj45872ksn1", "%s#%s", getParams()));
    verify(string -> string.isAlphanumeric("blkajsblas", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsEmptyOrAlphanumeric() {
    verify(string -> string.isEmptyOrAlphanumeric("aiulaj6265opksn", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrAlphanumeric("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsAlphanumericSpace() {
    verify(string -> string.isAlphanumericSpace("ai1ul90jksn", "%s#%s", getParams()));
    verify(string -> string.isAlphanumericSpace(" ai1ul90 ajk sn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(
        string -> {
          chars[5] = 32;
          string.isAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 33;
          string.isAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 125;
          string.isAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 126;
          string.isAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsBlank() {
    verify(string -> string.isBlank(NULL, "%s#%s", getParams()));
    verify(string -> string.isBlank(EMPTY, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsEmpty() {
    verify(string -> string.isEmpty(EMPTY, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotAlpha() {
    verify(string -> string.isNotAlpha("123aasf2", "%s#%s", getParams()));
    verify(string -> string.isNotAlpha("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsEmptyOrNotAlpha() {
    verify(string -> string.isEmptyOrNotAlpha("aiulaj626", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrNotAlpha("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotAlphaSpace() {
    verify(string -> string.isNotAlphaSpace("aiulaj626", "%s#%s", getParams()));
    verify(string -> string.isNotAlphaSpace("@ a", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotAlphanumeric() {
    verify(string -> string.isNotAlphanumeric("aiulaj626!5opksn", "%s#%s", getParams()));
    verify(string -> string.isNotAlphanumeric("@#.*", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsEmptyOrNotAlphanumeric() {
    verify(string -> string.isEmptyOrNotAlphanumeric("aiulaj626 5opksn", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrNotAlphanumeric("@#.*", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrNotAlphanumeric("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotAlphanumericSpace() {
    verify(string -> string.isNotAlphanumericSpace("aiulaj626 !5opksn", "%s#%s", getParams()));
    verify(string -> string.isNotAlphanumericSpace("@#.*", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(
        string -> {
          chars[5] = 30;
          string.isNotAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 31;
          string.isNotAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 127;
          string.isNotAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotBlank() {
    verify(string -> string.isNotBlank(CSTRING1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotEmpty() {
    verify(string -> string.isNotEmpty(CSTRING1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotNumeric() {
    verify(string -> string.isNotNumeric("a1234567", "%s#%s", getParams()));
    verify(string -> string.isNotNumeric(" ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsEmptyOrNotNumeric() {
    verify(string -> string.isEmptyOrNotNumeric("a123 4567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotNumericSpace() {
    verify(string -> string.isNotNumericSpace("a123 4567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNumeric() {
    verify(string -> string.isNumeric("1234567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsEmptyOrNumeric() {
    verify(string -> string.isEmptyOrNumeric("1234567", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrNumeric("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNumericSpace() {
    verify(string -> string.isNumericSpace("2345678", "%s#%s", getParams()));
    verify(string -> string.isNumericSpace(" 1254 786 1", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testLeftPadEquals() {
    verify(
        string ->
            string.leftPadEquals(
                "  some string    ", 10, "@", "  some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.leftPadEquals(
                "  some string    ",
                30,
                "@",
                "@@@@@@@@@@@@@  some string    ",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.leftPadEquals(
                "  some string    ", 10, NULL, "  some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.leftPadEquals(
                "  some string   s ",
                30,
                "",
                "              some string   s ",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testLeftPadNotEquals() {
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string    ", 10, "@", " some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string    ",
                30,
                "@",
                "@@@@@@@@@@@@  some string    ",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string    ", 10, NULL, " some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string   s ",
                30,
                "",
                "             some string   s ",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testLeftValueEquals() {
    verify(
        string -> string.leftValueEquals("  some string    ", 7, "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testLeftValueNotEquals() {
    verify(
        string ->
            string.leftValueNotEquals("  some string    ", 7, " some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testLengthEquals() {
    verify(string -> string.lengthEquals("  some string   s ", 18, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testLengthNotEquals() {
    verify(string -> string.lengthNotEquals("aasa", 0, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testMidValueEquals() {
    verify(
        string -> string.midValueEquals("  some string    ", 2, 4, "some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testMidValueNotEquals() {
    verify(
        string ->
            string.midValueNotEquals("  some string    ", 2, 4, "some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotContains() {
    verify(string -> string.notContains("  some string    ", "sox", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotContainsIgnoreCase() {
    verify(
        string -> string.notContainsIgnoreCase("  Some string    ", " sox", "%s#%s", getParams()));
    verify(
        string ->
            string.notContainsIgnoreCase("  some $tring    ", "x$TRING", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEndsWith() {
    verify(string -> string.notEndsWith("  some string   s ", ".* ", "%s#%s", getParams()));
    verify(string -> string.notEndsWith("  some string   s ", "S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEndsWithIgnoreCase() {
    verify(
        string ->
            string.notEndsWithIgnoreCase("  some string   s ", "   $ ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEqualsIgnoreCase() {
    verify(
        string ->
            string.notEqualsIgnoreCase(
                "  some string    ", "  $OME string    ", "%s#%s", getParams()));
    verify(string -> string.notEqualsIgnoreCase(NULL, "", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotEqualsIgnoreWhiteSpaces() {
    verify(
        string ->
            string.notEqualsIgnoreWhiteSpaces(
                "  some string    ", "  $OME string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotStartsWith() {
    verify(string -> string.notStartsWith("  some string   s ", " some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNotStartsWithIgnoreCase() {
    verify(
        string ->
            string.notStartsWithIgnoreCase("  some string   s ", " Some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNumberOfMatchesEquals() {
    verify(
        string -> string.numberOfMatchesEquals("  some string   s ", "s", 3, "%s#%s", getParams()));
    verify(
        string -> string.numberOfMatchesEquals("  some String   s ", "s", 2, "%s#%s", getParams()));
    verify(
        string -> string.numberOfMatchesEquals("  some $tring   s ", "$", 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testNumberOfMatchesNotEquals() {
    verify(
        string ->
            string.numberOfMatchesNotEquals("  some String   s ", "s", 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveEndEquals() {
    verify(
        string ->
            string.removeEndEquals(
                "  some string   s ", "  some ", "  some string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some string   s ", "some string   s ", "  ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some string   s ", "  some string   s ", "", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some String   s ", null, "  some String   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some String   s ", "tring   s ", "  some S", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some $tring   s ", "tring   s ", "  some $", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveEndIgnoreCaseEquals() {
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some string   s ", "  Some ", "  some string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some string   s ", "some String   s ", "  ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some string   s ", "  sOME string   s ", "", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some String   s ", null, "  some String   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some String   s ", "tring   S ", "  some S", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some $tring   s ", "TRING   s ", "  some $", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveEndIgnoreCaseNotEquals() {
    verify(
        string ->
            string.removeEndIgnoreCaseNotEquals(
                "  some STRING    ", " ", "  STRING    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveEndNotEquals() {
    verify(
        string ->
            string.removeEndNotEquals(
                "  some STRING    ", "STRING    ", "  SOME ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveEquals() {
    verify(
        string ->
            string.removeEquals(
                "  some string   s ", "s", "  ome tring    ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEquals(
                "  some String   so ", "so", "  me String    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveIgnoreCaseEquals() {
    verify(
        string ->
            string.removeIgnoreCaseEquals(
                "  some string   s ", "s", "  ome tring    ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeIgnoreCaseEquals(
                "  some String   so ", "SO", "  me String    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveIgnoreCaseNotEquals() {
    verify(
        string ->
            string.removeIgnoreCaseNotEquals(
                "  some STRING    ", " ", "  some STRING ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveNotEquals() {
    verify(
        string ->
            string.removeNotEquals(
                "  some STRING    ", "STRING   ", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveStartEquals() {
    verify(
        string ->
            string.removeStartEquals(
                "  some string   s ", "  some ", "string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some string   s ",
                "some string   s ",
                "  some string   s ",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some string   s ", "  some string   s ", "", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some String   s ", null, "  some String   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some String   s ", "  some S", "tring   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some $tring   s ", "  some $", "tring   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveStartIgnoreCaseEquals() {
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ", "  some ", "string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ", "  Some ", "string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ",
                "Some string   s ",
                "  some string   s ",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ", "  Some string   s ", "", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some String   s ", null, "  some String   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some String   s ", "  some s", "tring   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some $tring   s ", "  some $", "tring   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveStartIgnoreCaseNotEquals() {
    verify(
        string ->
            string.removeStartIgnoreCaseNotEquals(
                "  some $tring   s ", "  some ", " $tring   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRemoveStartNotEquals() {
    verify(
        string ->
            string.removeStartNotEquals(
                "  some string   s ", "  some ", "String   S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReplaceEquals() {
    verify(
        string ->
            string.replaceEquals(
                "  some string   s ", "s", "", "  ome tring    ", "%s#%s", getParams()));
    verify(
        string ->
            string.replaceEquals(
                "  some String   so ", "so", "XX", "  XXme String   XX ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReplaceIgnoreCaseEquals() {
    verify(
        string ->
            string.replaceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome |tring   | ", "%s#%s", getParams()));
    verify(
        string ->
            string.replaceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   x ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReplaceIgnoreCaseNotEquals() {
    verify(
        string ->
            string.replaceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReplaceNotEquals() {
    verify(
        string ->
            string.replaceNotEquals(
                "  some String   s ", " s", "x", " ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReplaceOnceEquals() {
    verify(
        string ->
            string.replaceOnceEquals(
                "  some string   s ", "s", "", "  ome string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.replaceOnceEquals(
                "  some String   so ", "so", "XX", "  XXme String   so ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReplaceOnceIgnoreCaseEquals() {
    verify(
        string ->
            string.replaceOnceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.replaceOnceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   so ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReplaceOnceIgnoreCaseNotEquals() {
    verify(
        string ->
            string.replaceOnceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReplaceOnceNotEquals() {
    verify(
        string ->
            string.replaceOnceNotEquals(
                "  some String   s ", " s", "x", " ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReverseEquals() {
    verify(
        string ->
            string.reverseEquals("  some string   s ", " s   gnirts emos  ", "%s#%s", getParams()));
    verify(
        string ->
            string.reverseEquals(
                "  some @#$%^&*.   so ", " os   .*&^%$#@ emos  ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testReverseNotEquals() {
    verify(
        string ->
            string.reverseNotEquals(
                "  some string  s ", " s   gnirts emos  ", "%s#%s", getParams()));
    verify(
        string ->
            string.reverseNotEquals(
                "  some @#$%^*.   so ", " os   .*&^%$#@ emos  ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRightPadEquals() {
    verify(
        string ->
            string.rightPadEquals(
                "  some string    ", 10, "@", "  some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.rightPadEquals(
                "  some string    ",
                30,
                "@",
                "  some string    @@@@@@@@@@@@@",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.rightPadEquals(
                "  some string    ", 10, NULL, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRightPadNotEquals() {
    verify(
        string ->
            string.rightPadNotEquals(
                "  some string   s ",
                40,
                "x",
                "  some string   s xxxxxxxxxxxxxxxxxxxxx",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRightValueEquals() {
    verify(
        string -> string.rightValueEquals("  some string    ", 7, "ing    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testRightValueNotEquals() {
    verify(
        string ->
            string.rightValueNotEquals("  some string    ", 7, "iNg    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStartsWith() {
    verify(string -> string.startsWith("  some string   s ", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStartsWithAny() {
    verify(
        string ->
            string.startsWithAny(
                "  some string   s ", new CList<>("A", null, "  some"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStartsWithIgnoreCase() {
    verify(
        string ->
            string.startsWithIgnoreCase("  some string   s ", "  some", "%s#%s", getParams()));
    verify(
        string ->
            string.startsWithIgnoreCase("  some string   s ", "  Some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStartsWithNone() {
    verify(
        string ->
            string.startsWithNone(
                "  some string   s ", new CList<>(" some", "     ", " s "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStripedEndValue() {
    verify(
        string ->
            string.stripedEndValue(
                "  some string    ", " ", "  some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValue(
                "  some string    ", null, "  some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValue("|some string||||", "|", "|some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValue(
                "|some string||||", null, "|some string||||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStripedEndValueNot() {
    verify(
        string ->
            string.stripedEndValueNot(
                "  some string    ", " ", " some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValueNot(
                "  some string    ", null, "  somestring", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValueNot(
                "|some string||||", "|", "|some string|", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValueNot(
                "|some string||||", null, "|some string|||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStripedStartValue() {
    verify(
        string ->
            string.stripedStartValue(
                "  some string    ", " ", "some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValue(
                "  some string    ", null, "some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValue(
                "|some string||||", "|", "some string||||", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValue(
                "|some string||||", null, "|some string||||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStripedStartValueNot() {
    verify(
        string ->
            string.stripedStartValueNot(
                "  some string    ", " ", "some string   ", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValueNot(
                "  some string    ", null, "some string   ", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValueNot(
                "|some string||||", "|", "some string|||", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValueNot(
                "|some string||||", null, "|some string|||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStripedValue() {
    verify(
        string ->
            string.stripedValue("  some string    ", " ", "some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValue("  some string    ", null, "some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValue("|some string||||", "|", "some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValue(
                "|some string||||", null, "|some string||||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testStripedValueNot() {
    verify(
        string ->
            string.stripedValueNot("  some string    ", " ", " some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValueNot("  some string    ", null, "somestring", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValueNot("|some string||||", "|", "some string|", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValueNot(
                "|some string||||", null, "|some string|||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringAfterEquals() {
    verify(
        string ->
            string.substringAfterEquals(
                "  some string    ", " s", "ome string    ", "%s#%s", getParams()));
    verify(
        string -> string.substringAfterEquals("  some string    ", null, "", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringAfterLastEquals() {
    verify(
        string ->
            string.substringAfterLastEquals(
                "  some string    ", " s", "tring    ", "%s#%s", getParams()));
    verify(
        string ->
            string.substringAfterLastEquals("  some string    ", null, "", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringAfterLastNotEquals() {
    verify(
        string ->
            string.substringAfterLastNotEquals(
                "  some string    ", " s", "trinG    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringAfterNotEquals() {
    verify(
        string ->
            string.substringAfterNotEquals(
                "  some string    ", " s", "omE string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringBeforeEquals() {
    verify(
        string ->
            string.substringBeforeEquals(
                "  some string    ", " st", "  some", "%s#%s", getParams()));
    verify(
        string ->
            string.substringBeforeEquals(
                "  some string    ", null, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringBeforeLastEquals() {
    verify(
        string ->
            string.substringBeforeLastEquals(
                "  some string    ", " s", "  some", "%s#%s", getParams()));
    verify(
        string ->
            string.substringBeforeLastEquals(
                "  some string    ", null, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringBeforeLastNotEquals() {
    verify(
        string ->
            string.substringBeforeLastNotEquals(
                "  some string    ", " s", "  somE", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringBeforeNotEquals() {
    verify(
        string ->
            string.substringBeforeNotEquals(
                "  some string    ", " st", "  Some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringBetweenEquals() {
    verify(
        string ->
            string.substringBetweenEquals(
                "  some string    ", "  ", "    ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringBetweenNotEquals() {
    verify(
        string ->
            string.substringBetweenNotEquals(
                "  some string    ", "  ", "    ", "some String", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringEquals() {
    verify(
        string ->
            string.substringEquals(
                "  some string    ", 0, "  some string    ", "%s#%s", getParams()));
    verify(
        string -> string.substringEquals("  some string    ", 0, 3, "  s", "%s#%s", getParams()));
    verify(string -> string.substringEquals("  some string    ", 2, 4, "so", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringNotEquals() {
    verify(
        string ->
            string.substringNotEquals(
                "  some string    ", 0, " some string    ", "%s#%s", getParams()));
    verify(
        string -> string.substringNotEquals("  some string    ", 0, 3, " s", "%s#%s", getParams()));
    verify(
        string ->
            string.substringNotEquals("  some string    ", 2, 4, "so ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringsBetweenContains() {
    verify(
        string ->
            string.substringsBetweenContains(
                "  some string   s ", " ", "s", " ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringsBetweenEquals() {
    verify(
        string ->
            string.substringsBetweenEquals(
                "  some string   s ",
                " ",
                "s",
                new CList<>(" ", "", "  "),
                "some string",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringsBetweenNotContains() {
    verify(
        string ->
            string.substringsBetweenNotContains(
                "  some string   s ", " ", "s", "x", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testSubstringsBetweenNotEquals() {
    verify(
        string ->
            string.substringsBetweenNotEquals(
                "  some string   s ",
                " ",
                "s",
                new CList<>(" ", "S", "  "),
                "some string",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testTrimmedValue() {
    verify(string -> string.trimmedValue("some string    ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testTrimmedValueNot() {
    verify(
        string -> string.trimmedValueNot("some string    ", " some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testTruncatedValue() {
    verify(
        string -> string.truncatedValue("some string    ", 10, "some strin", "%s#%s", getParams()));
    verify(
        string ->
            string.truncatedValue("some string    ", 4, 10, " string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testTruncatedValueNot() {
    verify(
        string ->
            string.truncatedValueNot("some string    ", 10, "some string", "%s#%s", getParams()));
    verify(
        string ->
            string.truncatedValueNot("some string    ", 4, 10, " string  ", "%s#%s", getParams()));
  }

  // Negative Scenarios
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testCenterPadEquals_Negative() {
    verify(
        string ->
            string.centerPadEquals(
                "  some string    ", 10, NULL, "  somestring    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testCenterPadNotEquals_Negative() {
    verify(
        string ->
            string.centerPadNotEquals(
                "  some string    ", 10, NULL, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testCompare_Negative() {
    verify(string -> string.compare(NULL, "  some string    ", 0, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testCompareIgnoreCase_Negative() {
    verify(
        string ->
            string.compareIgnoreCase(
                "  some string    ", "  ScOME string    ", 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContains_Negative() {
    verify(string -> string.contains("  some string    ", "sso", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testContainsIgnoreCase_Negative() {
    verify(string -> string.containsIgnoreCase("  Some string    ", " sco", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEndsWith_Negative() {
    verify(string -> string.endsWith("  some string   s ", "   x ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEndsWithAny_Negative() {
    verify(
        string ->
            string.endsWithAny(
                "  some string   s ", new CList<>("X", null, " D "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEndsWithIgnoreCase_Negative() {
    verify(
        string -> string.endsWithIgnoreCase("  some string   s ", "   xs ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEndsWithNone_Negative() {
    verify(
        string ->
            string.endsWithNone(
                "  some string   s ", new CList<>("a", " s "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEquals_Negative() {
    verify(string -> string.equals(NULL, "x", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsAny_Negative() {
    verify(
        string ->
            string.equalsAny(
                "  some string    ", new CList<>("", "  sxme string    "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsAnyIgnoreCase_Negative() {
    verify(
        string ->
            string.equalsAnyIgnoreCase(
                "  some STRING    ", new CList<>("", "  SXME string    "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsIgnoreCase_Negative() {
    verify(
        string ->
            string.equalsIgnoreCase(
                "  some string    ", "  SXME string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsIgnoreWhiteSpaces_Negative() {
    verify(
        string ->
            string.equalsIgnoreWhiteSpaces(
                "  some string    ", " s x me s t r ing    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsNone_Negative() {
    verify(
        string ->
            string.equalsNone(
                "  some string    ",
                new CList<>("  some string    ", "  sxe String    "),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testEqualsNoneIgnoreCase_Negative() {
    verify(
        string ->
            string.equalsNoneIgnoreCase(
                "  some string    ", new CList<>("  some string    ", null), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsAlpha_Negative() {
    verify(string -> string.isAlpha("aiul@ajksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsEmptyOrAlpha_Negative() {
    verify(string -> string.isEmptyOrAlpha("&", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsAlphaSpace_Negative() {
    verify(string -> string.isAlphaSpace(" aiu@l ajk sn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsAlphanumeric_Negative() {
    verify(string -> string.isAlphanumeric("blka$jsblas", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsAlphanumericSpace_Negative() {
    verify(string -> string.isAlphanumericSpace(" ai1ul#@90 ajk sn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(
        string -> {
          chars[5] = 30;
          string.isAsciiPrintable(String.valueOf(chars), "isAsciiPrintable");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsBlank_Negative() {
    verify(string -> string.isBlank("asas", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsEmpty_Negative() {
    verify(string -> string.isEmpty("asas", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotAlpha_Negative() {
    verify(string -> string.isNotAlpha("aasf", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsEmptyOrNotAlpha_Negative() {
    verify(string -> string.isEmptyOrNotAlpha("aiulaj", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotAlphaSpace_Negative() {
    verify(string -> string.isNotAlphaSpace("aiulaj", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotAlphanumeric_Negative() {
    verify(string -> string.isNotAlphanumeric("aiulaj6265opksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsEmptyOrNotAlphanumeric_Negative() {
    verify(string -> string.isEmptyOrNotAlphanumeric("aiulaj6265opksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotAlphanumericSpace_Negative() {
    verify(string -> string.isNotAlphanumericSpace("aiulaj6265opksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(
        string -> {
          chars[5] = 32;
          string.isNotAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotBlank_Negative() {
    verify(string -> string.isNotBlank("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotEmpty_Negative() {
    verify(string -> string.isNotEmpty(EMPTY, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotNumeric_Negative() {
    verify(string -> string.isNotNumeric("1", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsEmptyOrNotNumeric_Negative() {
    verify(string -> string.isEmptyOrNotNumeric("1234567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotNumericSpace_Negative() {
    verify(string -> string.isNotNumericSpace("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNumeric_Negative() {
    verify(string -> string.isNumeric("123a4567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsEmptyOrNumeric_Negative() {
    verify(string -> string.isEmptyOrNumeric("1a234567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNumericSpace_Negative() {
    verify(string -> string.isNumericSpace("23a45678", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLeftPadEquals_Negative() {
    verify(
        string ->
            string.leftPadEquals(
                "  some string   s ",
                30,
                "",
                "            some string   s ",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLeftPadNotEquals_Negative() {
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string   s ",
                30,
                "",
                "              some string   s ",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLeftValueEquals_Negative() {
    verify(
        string -> string.leftValueEquals("  some string    ", 7, " some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLeftValueNotEquals_Negative() {
    verify(
        string ->
            string.leftValueNotEquals("  some string    ", 7, "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLengthEquals_Negative() {
    verify(string -> string.lengthEquals("  some string   s ", 7, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testLengthNotEquals_Negative() {
    verify(string -> string.lengthNotEquals("  some string   s ", 18, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testMidValueEquals_Negative() {
    verify(
        string -> string.midValueEquals("  some string    ", 2, 4, "some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testMidValueNotEquals_Negative() {
    verify(
        string ->
            string.midValueNotEquals("  some string    ", 2, 4, "some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotContains_Negative() {
    verify(string -> string.notContains("  some string    ", "some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotContainsIgnoreCase_Negative() {
    verify(
        string -> string.notContainsIgnoreCase("  Some string    ", " Some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEndsWith_Negative() {
    verify(string -> string.notEndsWith("  some string   s ", "s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEndsWithIgnoreCase_Negative() {
    verify(
        string ->
            string.notEndsWithIgnoreCase("  some string   s ", "   S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualsIgnoreCase_Negative() {
    verify(
        string ->
            string.notEqualsIgnoreCase(
                "  some string    ", "  SOME string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotEqualsIgnoreWhiteSpaces_Negative() {
    verify(
        string ->
            string.notEqualsIgnoreWhiteSpaces(
                "  some string    ", "  so me string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotStartsWith_Negative() {
    verify(string -> string.notStartsWith("  some string   s ", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNotStartsWithIgnoreCase_Negative() {
    verify(
        string ->
            string.notStartsWithIgnoreCase("  some string   s ", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNumberOfMatchesEquals_Negative() {
    verify(
        string -> string.numberOfMatchesEquals("  some string   s ", "s", 2, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testNumberOfMatchesNotEquals_Negative() {
    verify(
        string ->
            string.numberOfMatchesNotEquals("  some String   s ", "s", 2, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveEndEquals_Negative() {
    verify(
        string ->
            string.removeEndEquals(
                "  some string   s ", "  some ", "  some string   S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveEndIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some $tring   s ", "TRING   x ", "  some $", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveEndIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.removeEndIgnoreCaseNotEquals(
                "  some STRING    ", " ", "  some STRING   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveEndNotEquals_Negative() {
    verify(
        string ->
            string.removeEndNotEquals(
                "  some STRING    ", "STRING    ", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveEquals_Negative() {
    verify(
        string ->
            string.removeEquals(
                "  some String   so ", "so", "  me string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.removeIgnoreCaseEquals(
                "  some String   so ", "SO", "  me Xtring    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.removeIgnoreCaseNotEquals(
                "  some STRING    ", " ", "someSTRING", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveNotEquals_Negative() {
    verify(
        string ->
            string.removeNotEquals(
                "  some STRING    ", "STRING   ", "  some  ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveStartEquals_Negative() {
    verify(
        string ->
            string.removeStartEquals(
                "  some string   s ", "  some ", "  some string   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveStartIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ", "  some ", "some string   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveStartIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.removeStartIgnoreCaseNotEquals(
                "  some $tring   s ", "  some ", "$tring   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRemoveStartNotEquals_Negative() {
    verify(
        string ->
            string.removeStartNotEquals(
                "  some string   s ", "  some ", "string   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReplaceEquals_Negative() {
    verify(
        string ->
            string.replaceEquals(
                "  some String   so ", "so", "XX", "  XXme String   XX", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReplaceIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.replaceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   x", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReplaceIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.replaceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " xomextring  x ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReplaceNotEquals_Negative() {
    verify(
        string ->
            string.replaceNotEquals(
                "  some String   s ", " s", "x", " xome String  x ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReplaceOnceEquals_Negative() {
    verify(
        string ->
            string.replaceOnceEquals(
                "  some String   so ", "so", "XX", "  some String   so ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReplaceOnceIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.replaceOnceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome string   s", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReplaceOnceIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.replaceOnceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " xome String   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReplaceOnceNotEquals_Negative() {
    verify(
        string ->
            string.replaceOnceNotEquals(
                "  some String   s ", " s", "x", " xome String   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReverseEquals_Negative() {
    verify(
        string ->
            string.reverseEquals(
                "  some @#$%^&*.   so ", " os   .*&^%$#@ emos ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testReverseNotEquals_Negative() {
    verify(
        string ->
            string.reverseNotEquals(
                "  some string  s ", " s  gnirts emos  ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRightPadEquals_Negative() {
    verify(
        string ->
            string.rightPadEquals(
                "  some string    ", 10, "@", "  some string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRightPadNotEquals_Negative() {
    verify(
        string ->
            string.rightPadNotEquals(
                "  some string   s ",
                40,
                "x",
                "  some string   s xxxxxxxxxxxxxxxxxxxxxx",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRightValueEquals_Negative() {
    verify(
        string -> string.rightValueEquals("  some string    ", 7, "ing   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testRightValueNotEquals_Negative() {
    verify(
        string ->
            string.rightValueNotEquals("  some string    ", 7, "ing    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStartsWith_Negative() {
    verify(string -> string.startsWith("  some string   s ", " some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStartsWithAny_Negative() {
    verify(
        string ->
            string.startsWithAny(
                "  some string   s ", new CList<>("A", null, "some"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStartsWithIgnoreCase_Negative() {
    verify(
        string -> string.startsWithIgnoreCase("  some string   s ", "some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStartsWithNone_Negative() {
    verify(
        string ->
            string.startsWithNone(
                "  some string   s ", new CList<>("  some", "     ", "s "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStripedEndValue_Negative() {
    verify(
        string ->
            string.stripedEndValue(
                "  some string    ", " ", "  some string ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStripedEndValueNot_Negative() {
    verify(
        string ->
            string.stripedEndValueNot(
                "  some string    ", " ", "  some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStripedStartValue_Negative() {
    verify(
        string ->
            string.stripedStartValue(
                "  some string    ", " ", "some string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStripedStartValueNot_Negative() {
    verify(
        string ->
            string.stripedStartValueNot(
                "  some string    ", " ", "some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStripedValue_Negative() {
    verify(
        string ->
            string.stripedValue("  some string    ", " ", "some string ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testStripedValueNot_Negative() {
    verify(
        string ->
            string.stripedValueNot("  some string    ", " ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringAfterEquals1_Negative() {
    verify(
        string ->
            string.substringAfterEquals(
                "  some string    ", " s", "ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringAfterEquals2_Negative() {
    verify(
        string ->
            string.substringAfterEquals("  some string    ", null, "s", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringAfterLastEquals1_Negative() {
    verify(
        string ->
            string.substringAfterLastEquals("  some string    ", null, "s", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringAfterLastEquals2_Negative() {
    verify(
        string ->
            string.substringAfterLastEquals(
                "  some string    ", " s", "tring   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringAfterLastNotEquals_Negative() {
    verify(
        string ->
            string.substringAfterLastNotEquals(
                "  some string    ", " s", "tring    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringAfterNotEquals_Negative() {
    verify(
        string ->
            string.substringAfterNotEquals(
                "  some string    ", " s", "ome string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringBeforeEquals1_Negative() {
    verify(
        string ->
            string.substringBeforeEquals(
                "  some string    ", " st", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringBeforeEquals2_Negative() {
    verify(
        string ->
            string.substringBeforeEquals(
                "  some string    ", null, "  some string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringBeforeLastEquals1_Negative() {
    verify(
        string ->
            string.substringBeforeLastEquals(
                "  some string    ", " s", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringBeforeLastEquals2_Negative() {
    verify(
        string ->
            string.substringBeforeLastEquals(
                "  some string    ", " s", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringBeforeLastNotEquals_Negative() {
    verify(
        string ->
            string.substringBeforeLastNotEquals(
                "  some string    ", " s", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringBeforeNotEquals_Negative() {
    verify(
        string ->
            string.substringBeforeNotEquals(
                "  some string    ", " st", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringBetweenEquals_Negative() {
    verify(
        string ->
            string.substringBetweenEquals(
                "  some string    ", "  ", "    ", "some sstring", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringBetweenNotEquals_Negative() {
    verify(
        string ->
            string.substringBetweenNotEquals(
                "  some string    ", "  ", "    ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringEquals1_Negative() {
    verify(
        string ->
            string.substringEquals(
                "  some string    ", 0, "  some strin    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringEquals2_Negative() {
    verify(
        string -> string.substringEquals("  some string    ", 0, 3, "  sx", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringEquals3_Negative() {
    verify(
        string -> string.substringEquals("  some string    ", 2, 4, "sxo", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringNotEquals1_Negative() {
    verify(
        string ->
            string.substringNotEquals(
                "  some string    ", 0, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringNotEquals2_Negative() {
    verify(
        string ->
            string.substringNotEquals("  some string    ", 0, 3, "  s", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringNotEquals3_Negative() {
    verify(
        string -> string.substringNotEquals("  some string    ", 2, 4, "so", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringsBetweenContains_Negative() {
    verify(
        string ->
            string.substringsBetweenContains(
                "  some string   s ", " ", "s", "x", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringsBetweenNotContains_Negative() {
    verify(
        string ->
            string.substringsBetweenNotContains(
                "  some string   s ", " ", "s", " ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testSubstringsBetweenNotEquals_Negative() {
    verify(
        string ->
            string.substringsBetweenNotEquals(
                "  some string   s ",
                " ",
                "s",
                new CList<>(" ", "", "  "),
                "some string",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testTrimmedValue_Negative() {
    verify(string -> string.trimmedValue("some string    ", "some String", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testTrimmedValueNot_Negative() {
    verify(
        string -> string.trimmedValueNot("some string    ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testTruncatedValue1_Negative() {
    verify(
        string ->
            string.truncatedValue("some string    ", 10, "some sxtrin", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testTruncatedValue2_Negative() {
    verify(
        string ->
            string.truncatedValue("some string    ", 4, 10, " sxtring   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testTruncatedValueNot1_Negative() {
    verify(
        string ->
            string.truncatedValueNot("some string    ", 10, "some strin", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testTruncatedValueNot2_Negative() {
    verify(
        string ->
            string.truncatedValueNot("some string    ", 4, 10, " string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsMatches() {
    verify(string -> string.matches("some string    ", "[a-z ]+", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsMatches_ExpectedNull() {
    verify(string -> string.matches(null, " tring   ", "%s#%s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsMatches_Negative() {
    verify(string -> string.matches("some string    ", "\\d+", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsMatchesPattern() {
    verify(
        string ->
            string.matches("some string    ", Pattern.compile("[a-z ]+"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsMatchesPattern_ExpectedNull() {
    verify(string -> string.matches(null, " tring   ", "%s#%s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsMatchesPattern_Negative() {
    verify(
        string -> string.matches("some string    ", Pattern.compile("\\d+"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotMatches() {
    verify(string -> string.notMatches("some string    ", "\\d+", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotMatches_ExpectedNull() {
    verify(string -> string.notMatches(null, " tring   ", "%s#%s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotMatches_Negative() {
    verify(string -> string.notMatches("some string    ", "[a-z ]+", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void WithInterval_testIsNotMatchesPattern() {
    verify(
        string ->
            string.notMatches("some string    ", Pattern.compile("\\d+"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotMatchesPattern_ExpectedNull() {
    verify(string -> string.notMatches(null, " tring   ", "%s#%s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void WithInterval_testIsNotMatchesPattern_Negative() {
    verify(
        string ->
            string.notMatches("some string    ", Pattern.compile("[a-z ]+"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadEquals() {
    verify(
        string ->
            string.centerPadEquals(
                "  some string    ", 10, "@", "  some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.centerPadEquals(
                "  some string    ",
                30,
                "@",
                "@@@@@@  some string    @@@@@@@",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.centerPadEquals(
                "  some string    ", 10, NULL, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadNotEquals() {
    verify(
        string ->
            string.centerPadNotEquals(
                "  some string    ", 10, "@", " some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.centerPadNotEquals(
                "  some string    ",
                30,
                "@",
                "@@@@@  some string    @@@@@@@",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.centerPadNotEquals(
                "  some string    ", 10, NULL, " some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompare() {
    verify(
        string ->
            string.compare("  some string    ", "  some string    ", 0, "%s#%s", getParams()));
    verify(
        string ->
            string.compare("  SOME string    ", "  some string    ", -32, "%s#%s", getParams()));
    verify(string -> string.compare(NULL, null, 0, "%s#%s", getParams()));
    verify(
        string ->
            string.compare("  some string    ", "  some String    ", 32, "%s#%s", getParams()));
    verify(string -> string.compare("  some string    ", null, 1, "%s#%s", getParams()));
    verify(string -> string.compare(NULL, "  some string    ", -1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompareIgnoreCase() {
    verify(
        string ->
            string.compareIgnoreCase(
                "  some string    ", "  SOME string    ", 0, "%s#%s", getParams()));
    verify(
        string ->
            string.compareIgnoreCase(
                "  SOME string    ", "  some string    ", 0, "%s#%s", getParams()));
    verify(string -> string.compareIgnoreCase(NULL, null, 0, "%s#%s", getParams()));
    verify(
        string ->
            string.compareIgnoreCase(
                "  some string    ", "  some xtring    ", -5, "%s#%s", getParams()));
    verify(string -> string.compareIgnoreCase("  some string    ", null, 1, "%s#%s", getParams()));
    verify(string -> string.compareIgnoreCase(NULL, "  some string    ", -1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify(string -> string.contains("  some string    ", "so", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsIgnoreCase() {
    verify(string -> string.containsIgnoreCase("  Some string    ", " so", "%s#%s", getParams()));
    verify(
        string -> string.containsIgnoreCase("  some $tring    ", "$TRING", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWith() {
    verify(string -> string.endsWith("  some string   s ", "   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithAny() {
    verify(
        string ->
            string.endsWithAny(
                "  some string   s ", new CList<>("A", null, " s "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithIgnoreCase() {
    verify(
        string -> string.endsWithIgnoreCase("  some string   s ", "   s ", "%s#%s", getParams()));
    verify(
        string -> string.endsWithIgnoreCase("  some string   s ", "   S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithNone() {
    verify(
        string ->
            string.endsWithNone(
                "  some string   s ", new CList<>("A", " s  "), "%s#%s", getParams()));
    verify(
        string ->
            string.endsWithNone(
                "  some string   s ", new CList<>("A", " S "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(string -> string.equals("  some string    ", "  some string    ", "%s#%s", getParams()));
    verify(string -> string.equals(NULL, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAny() {
    verify(
        string ->
            string.equalsAny(
                "  some string    ", new CList<>("", "  some string    "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAnyIgnoreCase() {
    verify(
        string ->
            string.equalsAnyIgnoreCase(
                "  some STRING    ", new CList<>("", "  SOME string    "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreCase() {
    verify(
        string ->
            string.equalsIgnoreCase(
                "  some string    ", "  SOME string    ", "%s#%s", getParams()));
    verify(string -> string.equalsIgnoreCase(NULL, null, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreWhiteSpaces() {
    verify(
        string ->
            string.equalsIgnoreWhiteSpaces(
                "  some string    ", " s o me s t r ing    ", "%s#%s", getParams()));
    verify(
        string ->
            string.equalsIgnoreWhiteSpaces(
                "  some string    ", "somestring", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNone() {
    verify(
        string ->
            string.equalsNone(
                "  some string    ", new CList<>("", "  some String    "), "%s#%s", getParams()));
    verify(
        string ->
            string.equalsNone("  some string    ", new CList<>("", null), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNoneIgnoreCase() {
    verify(
        string ->
            string.equalsNoneIgnoreCase(
                "  some STRING    ", new CList<>("", "  $ome string    "), "%s#%s", getParams()));
    verify(
        string ->
            string.equalsNoneIgnoreCase(
                "  some string    ", new CList<>("", null), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlpha() {
    verify(string -> string.isAlpha("aiulajksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlpha() {
    verify(string -> string.isEmptyOrAlpha("aiulajksn", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrAlpha("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphaSpace() {
    verify(string -> string.isAlphaSpace(" aiul ajk sn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumeric() {
    verify(string -> string.isAlphanumeric("aiulaj45872ksn1", "%s#%s", getParams()));
    verify(string -> string.isAlphanumeric("blkajsblas", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlphanumeric() {
    verify(string -> string.isEmptyOrAlphanumeric("aiulaj6265opksn", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrAlphanumeric("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumericSpace() {
    verify(string -> string.isAlphanumericSpace("ai1ul90jksn", "%s#%s", getParams()));
    verify(string -> string.isAlphanumericSpace(" ai1ul90 ajk sn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(
        string -> {
          chars[5] = 32;
          string.isAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 33;
          string.isAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 125;
          string.isAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 126;
          string.isAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlank() {
    verify(string -> string.isBlank(NULL, "%s#%s", getParams()));
    verify(string -> string.isBlank(EMPTY, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify(string -> string.isEmpty(EMPTY, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlpha() {
    verify(string -> string.isNotAlpha("123aasf2", "%s#%s", getParams()));
    verify(string -> string.isNotAlpha("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlpha() {
    verify(string -> string.isEmptyOrNotAlpha("aiulaj626", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrNotAlpha("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphaSpace() {
    verify(string -> string.isNotAlphaSpace("aiulaj626", "%s#%s", getParams()));
    verify(string -> string.isNotAlphaSpace("@ a", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumeric() {
    verify(string -> string.isNotAlphanumeric("aiulaj626!5opksn", "%s#%s", getParams()));
    verify(string -> string.isNotAlphanumeric("@#.*", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlphanumeric() {
    verify(string -> string.isEmptyOrNotAlphanumeric("aiulaj626 5opksn", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrNotAlphanumeric("@#.*", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrNotAlphanumeric("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumericSpace() {
    verify(string -> string.isNotAlphanumericSpace("aiulaj626 !5opksn", "%s#%s", getParams()));
    verify(string -> string.isNotAlphanumericSpace("@#.*", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(
        string -> {
          chars[5] = 30;
          string.isNotAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 31;
          string.isNotAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
    verify(
        string -> {
          chars[5] = 127;
          string.isNotAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotBlank() {
    verify(string -> string.isNotBlank(CSTRING1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify(string -> string.isNotEmpty(CSTRING1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumeric() {
    verify(string -> string.isNotNumeric("a1234567", "%s#%s", getParams()));
    verify(string -> string.isNotNumeric(" ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotNumeric() {
    verify(string -> string.isEmptyOrNotNumeric("a123 4567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumericSpace() {
    verify(string -> string.isNotNumericSpace("a123 4567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumeric() {
    verify(string -> string.isNumeric("1234567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNumeric() {
    verify(string -> string.isEmptyOrNumeric("1234567", "%s#%s", getParams()));
    verify(string -> string.isEmptyOrNumeric("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumericSpace() {
    verify(string -> string.isNumericSpace("2345678", "%s#%s", getParams()));
    verify(string -> string.isNumericSpace(" 1254 786 1", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadEquals() {
    verify(
        string ->
            string.leftPadEquals(
                "  some string    ", 10, "@", "  some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.leftPadEquals(
                "  some string    ",
                30,
                "@",
                "@@@@@@@@@@@@@  some string    ",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.leftPadEquals(
                "  some string    ", 10, NULL, "  some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.leftPadEquals(
                "  some string   s ",
                30,
                "",
                "              some string   s ",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadNotEquals() {
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string    ", 10, "@", " some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string    ",
                30,
                "@",
                "@@@@@@@@@@@@  some string    ",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string    ", 10, NULL, " some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string   s ",
                30,
                "",
                "             some string   s ",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueEquals() {
    verify(
        string -> string.leftValueEquals("  some string    ", 7, "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueNotEquals() {
    verify(
        string ->
            string.leftValueNotEquals("  some string    ", 7, " some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthEquals() {
    verify(string -> string.lengthEquals("  some string   s ", 18, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthNotEquals() {
    verify(string -> string.lengthNotEquals("aasa", 0, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueEquals() {
    verify(
        string -> string.midValueEquals("  some string    ", 2, 4, "some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueNotEquals() {
    verify(
        string ->
            string.midValueNotEquals("  some string    ", 2, 4, "some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify(string -> string.notContains("  some string    ", "sox", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsIgnoreCase() {
    verify(
        string -> string.notContainsIgnoreCase("  Some string    ", " sox", "%s#%s", getParams()));
    verify(
        string ->
            string.notContainsIgnoreCase("  some $tring    ", "x$TRING", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWith() {
    verify(string -> string.notEndsWith("  some string   s ", ".* ", "%s#%s", getParams()));
    verify(string -> string.notEndsWith("  some string   s ", "S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWithIgnoreCase() {
    verify(
        string ->
            string.notEndsWithIgnoreCase("  some string   s ", "   $ ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreCase() {
    verify(
        string ->
            string.notEqualsIgnoreCase(
                "  some string    ", "  $OME string    ", "%s#%s", getParams()));
    verify(string -> string.notEqualsIgnoreCase(NULL, "", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreWhiteSpaces() {
    verify(
        string ->
            string.notEqualsIgnoreWhiteSpaces(
                "  some string    ", "  $OME string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWith() {
    verify(string -> string.notStartsWith("  some string   s ", " some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWithIgnoreCase() {
    verify(
        string ->
            string.notStartsWithIgnoreCase("  some string   s ", " Some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesEquals() {
    verify(
        string -> string.numberOfMatchesEquals("  some string   s ", "s", 3, "%s#%s", getParams()));
    verify(
        string -> string.numberOfMatchesEquals("  some String   s ", "s", 2, "%s#%s", getParams()));
    verify(
        string -> string.numberOfMatchesEquals("  some $tring   s ", "$", 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesNotEquals() {
    verify(
        string ->
            string.numberOfMatchesNotEquals("  some String   s ", "s", 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndEquals() {
    verify(
        string ->
            string.removeEndEquals(
                "  some string   s ", "  some ", "  some string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some string   s ", "some string   s ", "  ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some string   s ", "  some string   s ", "", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some String   s ", null, "  some String   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some String   s ", "tring   s ", "  some S", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndEquals(
                "  some $tring   s ", "tring   s ", "  some $", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseEquals() {
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some string   s ", "  Some ", "  some string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some string   s ", "some String   s ", "  ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some string   s ", "  sOME string   s ", "", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some String   s ", null, "  some String   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some String   s ", "tring   S ", "  some S", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some $tring   s ", "TRING   s ", "  some $", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseNotEquals() {
    verify(
        string ->
            string.removeEndIgnoreCaseNotEquals(
                "  some STRING    ", " ", "  STRING    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndNotEquals() {
    verify(
        string ->
            string.removeEndNotEquals(
                "  some STRING    ", "STRING    ", "  SOME ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEquals() {
    verify(
        string ->
            string.removeEquals(
                "  some string   s ", "s", "  ome tring    ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeEquals(
                "  some String   so ", "so", "  me String    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseEquals() {
    verify(
        string ->
            string.removeIgnoreCaseEquals(
                "  some string   s ", "s", "  ome tring    ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeIgnoreCaseEquals(
                "  some String   so ", "SO", "  me String    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseNotEquals() {
    verify(
        string ->
            string.removeIgnoreCaseNotEquals(
                "  some STRING    ", " ", "  some STRING ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveNotEquals() {
    verify(
        string ->
            string.removeNotEquals(
                "  some STRING    ", "STRING   ", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartEquals() {
    verify(
        string ->
            string.removeStartEquals(
                "  some string   s ", "  some ", "string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some string   s ",
                "some string   s ",
                "  some string   s ",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some string   s ", "  some string   s ", "", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some String   s ", null, "  some String   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some String   s ", "  some S", "tring   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartEquals(
                "  some $tring   s ", "  some $", "tring   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseEquals() {
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ", "  some ", "string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ", "  Some ", "string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ",
                "Some string   s ",
                "  some string   s ",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ", "  Some string   s ", "", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some String   s ", null, "  some String   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some String   s ", "  some s", "tring   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some $tring   s ", "  some $", "tring   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseNotEquals() {
    verify(
        string ->
            string.removeStartIgnoreCaseNotEquals(
                "  some $tring   s ", "  some ", " $tring   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartNotEquals() {
    verify(
        string ->
            string.removeStartNotEquals(
                "  some string   s ", "  some ", "String   S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceEquals() {
    verify(
        string ->
            string.replaceEquals(
                "  some string   s ", "s", "", "  ome tring    ", "%s#%s", getParams()));
    verify(
        string ->
            string.replaceEquals(
                "  some String   so ", "so", "XX", "  XXme String   XX ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseEquals() {
    verify(
        string ->
            string.replaceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome |tring   | ", "%s#%s", getParams()));
    verify(
        string ->
            string.replaceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   x ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseNotEquals() {
    verify(
        string ->
            string.replaceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceNotEquals() {
    verify(
        string ->
            string.replaceNotEquals(
                "  some String   s ", " s", "x", " ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceEquals() {
    verify(
        string ->
            string.replaceOnceEquals(
                "  some string   s ", "s", "", "  ome string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.replaceOnceEquals(
                "  some String   so ", "so", "XX", "  XXme String   so ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseEquals() {
    verify(
        string ->
            string.replaceOnceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome string   s ", "%s#%s", getParams()));
    verify(
        string ->
            string.replaceOnceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   so ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseNotEquals() {
    verify(
        string ->
            string.replaceOnceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceNotEquals() {
    verify(
        string ->
            string.replaceOnceNotEquals(
                "  some String   s ", " s", "x", " ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseEquals() {
    verify(
        string ->
            string.reverseEquals("  some string   s ", " s   gnirts emos  ", "%s#%s", getParams()));
    verify(
        string ->
            string.reverseEquals(
                "  some @#$%^&*.   so ", " os   .*&^%$#@ emos  ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseNotEquals() {
    verify(
        string ->
            string.reverseNotEquals(
                "  some string  s ", " s   gnirts emos  ", "%s#%s", getParams()));
    verify(
        string ->
            string.reverseNotEquals(
                "  some @#$%^*.   so ", " os   .*&^%$#@ emos  ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadEquals() {
    verify(
        string ->
            string.rightPadEquals(
                "  some string    ", 10, "@", "  some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.rightPadEquals(
                "  some string    ",
                30,
                "@",
                "  some string    @@@@@@@@@@@@@",
                "%s#%s",
                getParams()));
    verify(
        string ->
            string.rightPadEquals(
                "  some string    ", 10, NULL, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadNotEquals() {
    verify(
        string ->
            string.rightPadNotEquals(
                "  some string   s ",
                40,
                "x",
                "  some string   s xxxxxxxxxxxxxxxxxxxxx",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueEquals() {
    verify(
        string -> string.rightValueEquals("  some string    ", 7, "ing    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueNotEquals() {
    verify(
        string ->
            string.rightValueNotEquals("  some string    ", 7, "iNg    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWith() {
    verify(string -> string.startsWith("  some string   s ", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithAny() {
    verify(
        string ->
            string.startsWithAny(
                "  some string   s ", new CList<>("A", null, "  some"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithIgnoreCase() {
    verify(
        string ->
            string.startsWithIgnoreCase("  some string   s ", "  some", "%s#%s", getParams()));
    verify(
        string ->
            string.startsWithIgnoreCase("  some string   s ", "  Some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithNone() {
    verify(
        string ->
            string.startsWithNone(
                "  some string   s ", new CList<>(" some", "     ", " s "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValue() {
    verify(
        string ->
            string.stripedEndValue(
                "  some string    ", " ", "  some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValue(
                "  some string    ", null, "  some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValue("|some string||||", "|", "|some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValue(
                "|some string||||", null, "|some string||||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValueNot() {
    verify(
        string ->
            string.stripedEndValueNot(
                "  some string    ", " ", " some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValueNot(
                "  some string    ", null, "  somestring", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValueNot(
                "|some string||||", "|", "|some string|", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedEndValueNot(
                "|some string||||", null, "|some string|||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValue() {
    verify(
        string ->
            string.stripedStartValue(
                "  some string    ", " ", "some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValue(
                "  some string    ", null, "some string    ", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValue(
                "|some string||||", "|", "some string||||", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValue(
                "|some string||||", null, "|some string||||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValueNot() {
    verify(
        string ->
            string.stripedStartValueNot(
                "  some string    ", " ", "some string   ", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValueNot(
                "  some string    ", null, "some string   ", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValueNot(
                "|some string||||", "|", "some string|||", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedStartValueNot(
                "|some string||||", null, "|some string|||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValue() {
    verify(
        string ->
            string.stripedValue("  some string    ", " ", "some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValue("  some string    ", null, "some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValue("|some string||||", "|", "some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValue(
                "|some string||||", null, "|some string||||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValueNot() {
    verify(
        string ->
            string.stripedValueNot("  some string    ", " ", " some string", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValueNot("  some string    ", null, "somestring", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValueNot("|some string||||", "|", "some string|", "%s#%s", getParams()));
    verify(
        string ->
            string.stripedValueNot(
                "|some string||||", null, "|some string|||", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterEquals() {
    verify(
        string ->
            string.substringAfterEquals(
                "  some string    ", " s", "ome string    ", "%s#%s", getParams()));
    verify(
        string -> string.substringAfterEquals("  some string    ", null, "", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastEquals() {
    verify(
        string ->
            string.substringAfterLastEquals(
                "  some string    ", " s", "tring    ", "%s#%s", getParams()));
    verify(
        string ->
            string.substringAfterLastEquals("  some string    ", null, "", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastNotEquals() {
    verify(
        string ->
            string.substringAfterLastNotEquals(
                "  some string    ", " s", "trinG    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterNotEquals() {
    verify(
        string ->
            string.substringAfterNotEquals(
                "  some string    ", " s", "omE string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeEquals() {
    verify(
        string ->
            string.substringBeforeEquals(
                "  some string    ", " st", "  some", "%s#%s", getParams()));
    verify(
        string ->
            string.substringBeforeEquals(
                "  some string    ", null, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastEquals() {
    verify(
        string ->
            string.substringBeforeLastEquals(
                "  some string    ", " s", "  some", "%s#%s", getParams()));
    verify(
        string ->
            string.substringBeforeLastEquals(
                "  some string    ", null, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastNotEquals() {
    verify(
        string ->
            string.substringBeforeLastNotEquals(
                "  some string    ", " s", "  somE", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeNotEquals() {
    verify(
        string ->
            string.substringBeforeNotEquals(
                "  some string    ", " st", "  Some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenEquals() {
    verify(
        string ->
            string.substringBetweenEquals(
                "  some string    ", "  ", "    ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenNotEquals() {
    verify(
        string ->
            string.substringBetweenNotEquals(
                "  some string    ", "  ", "    ", "some String", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringEquals() {
    verify(
        string ->
            string.substringEquals(
                "  some string    ", 0, "  some string    ", "%s#%s", getParams()));
    verify(
        string -> string.substringEquals("  some string    ", 0, 3, "  s", "%s#%s", getParams()));
    verify(string -> string.substringEquals("  some string    ", 2, 4, "so", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringNotEquals() {
    verify(
        string ->
            string.substringNotEquals(
                "  some string    ", 0, " some string    ", "%s#%s", getParams()));
    verify(
        string -> string.substringNotEquals("  some string    ", 0, 3, " s", "%s#%s", getParams()));
    verify(
        string ->
            string.substringNotEquals("  some string    ", 2, 4, "so ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenContains() {
    verify(
        string ->
            string.substringsBetweenContains(
                "  some string   s ", " ", "s", " ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenEquals() {
    verify(
        string ->
            string.substringsBetweenEquals(
                "  some string   s ",
                " ",
                "s",
                new CList<>(" ", "", "  "),
                "some string",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotContains() {
    verify(
        string ->
            string.substringsBetweenNotContains(
                "  some string   s ", " ", "s", "x", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotEquals() {
    verify(
        string ->
            string.substringsBetweenNotEquals(
                "  some string   s ",
                " ",
                "s",
                new CList<>(" ", "S", "  "),
                "some string",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValue() {
    verify(string -> string.trimmedValue("some string    ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValueNot() {
    verify(
        string -> string.trimmedValueNot("some string    ", " some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValue() {
    verify(
        string -> string.truncatedValue("some string    ", 10, "some strin", "%s#%s", getParams()));
    verify(
        string ->
            string.truncatedValue("some string    ", 4, 10, " string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValueNot() {
    verify(
        string ->
            string.truncatedValueNot("some string    ", 10, "some string", "%s#%s", getParams()));
    verify(
        string ->
            string.truncatedValueNot("some string    ", 4, 10, " string  ", "%s#%s", getParams()));
  }

  // Negative Scenarios
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadEquals_Negative() {
    verify(
        string ->
            string.centerPadEquals(
                "  some string    ", 10, NULL, "  somestring    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadNotEquals_Negative() {
    verify(
        string ->
            string.centerPadNotEquals(
                "  some string    ", 10, NULL, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompare_Negative() {
    verify(string -> string.compare(NULL, "  some string    ", 0, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompareIgnoreCase_Negative() {
    verify(
        string ->
            string.compareIgnoreCase(
                "  some string    ", "  ScOME string    ", 1, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_Negative() {
    verify(string -> string.contains("  some string    ", "sso", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsIgnoreCase_Negative() {
    verify(string -> string.containsIgnoreCase("  Some string    ", " sco", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWith_Negative() {
    verify(string -> string.endsWith("  some string   s ", "   x ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithAny_Negative() {
    verify(
        string ->
            string.endsWithAny(
                "  some string   s ", new CList<>("X", null, " D "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithIgnoreCase_Negative() {
    verify(
        string -> string.endsWithIgnoreCase("  some string   s ", "   xs ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithNone_Negative() {
    verify(
        string ->
            string.endsWithNone(
                "  some string   s ", new CList<>("a", " s "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_Negative() {
    verify(string -> string.equals(NULL, "x", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAny_Negative() {
    verify(
        string ->
            string.equalsAny(
                "  some string    ", new CList<>("", "  sxme string    "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAnyIgnoreCase_Negative() {
    verify(
        string ->
            string.equalsAnyIgnoreCase(
                "  some STRING    ", new CList<>("", "  SXME string    "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreCase_Negative() {
    verify(
        string ->
            string.equalsIgnoreCase(
                "  some string    ", "  SXME string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreWhiteSpaces_Negative() {
    verify(
        string ->
            string.equalsIgnoreWhiteSpaces(
                "  some string    ", " s x me s t r ing    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNone_Negative() {
    verify(
        string ->
            string.equalsNone(
                "  some string    ",
                new CList<>("  some string    ", "  sxe String    "),
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNoneIgnoreCase_Negative() {
    verify(
        string ->
            string.equalsNoneIgnoreCase(
                "  some string    ", new CList<>("  some string    ", null), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlpha_Negative() {
    verify(string -> string.isAlpha("aiul@ajksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrAlpha_Negative() {
    verify(string -> string.isEmptyOrAlpha("&", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphaSpace_Negative() {
    verify(string -> string.isAlphaSpace(" aiu@l ajk sn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumeric_Negative() {
    verify(string -> string.isAlphanumeric("blka$jsblas", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumericSpace_Negative() {
    verify(string -> string.isAlphanumericSpace(" ai1ul#@90 ajk sn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(
        string -> {
          chars[5] = 30;
          string.isAsciiPrintable(String.valueOf(chars), "isAsciiPrintable");
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsBlank_Negative() {
    verify(string -> string.isBlank("asas", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_Negative() {
    verify(string -> string.isEmpty("asas", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlpha_Negative() {
    verify(string -> string.isNotAlpha("aasf", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlpha_Negative() {
    verify(string -> string.isEmptyOrNotAlpha("aiulaj", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphaSpace_Negative() {
    verify(string -> string.isNotAlphaSpace("aiulaj", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumeric_Negative() {
    verify(string -> string.isNotAlphanumeric("aiulaj6265opksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlphanumeric_Negative() {
    verify(string -> string.isEmptyOrNotAlphanumeric("aiulaj6265opksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumericSpace_Negative() {
    verify(string -> string.isNotAlphanumericSpace("aiulaj6265opksn", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(
        string -> {
          chars[5] = 32;
          string.isNotAsciiPrintable(String.valueOf(chars), "%s#%s", getParams());
        });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotBlank_Negative() {
    verify(string -> string.isNotBlank("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_Negative() {
    verify(string -> string.isNotEmpty(EMPTY, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumeric_Negative() {
    verify(string -> string.isNotNumeric("1", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotNumeric_Negative() {
    verify(string -> string.isEmptyOrNotNumeric("1234567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumericSpace_Negative() {
    verify(string -> string.isNotNumericSpace("", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumeric_Negative() {
    verify(string -> string.isNumeric("123a4567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNumeric_Negative() {
    verify(string -> string.isEmptyOrNumeric("1a234567", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumericSpace_Negative() {
    verify(string -> string.isNumericSpace("23a45678", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadEquals_Negative() {
    verify(
        string ->
            string.leftPadEquals(
                "  some string   s ",
                30,
                "",
                "            some string   s ",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadNotEquals_Negative() {
    verify(
        string ->
            string.leftPadNotEquals(
                "  some string   s ",
                30,
                "",
                "              some string   s ",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueEquals_Negative() {
    verify(
        string -> string.leftValueEquals("  some string    ", 7, " some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueNotEquals_Negative() {
    verify(
        string ->
            string.leftValueNotEquals("  some string    ", 7, "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthEquals_Negative() {
    verify(string -> string.lengthEquals("  some string   s ", 7, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthNotEquals_Negative() {
    verify(string -> string.lengthNotEquals("  some string   s ", 18, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueEquals_Negative() {
    verify(
        string -> string.midValueEquals("  some string    ", 2, 4, "some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueNotEquals_Negative() {
    verify(
        string ->
            string.midValueNotEquals("  some string    ", 2, 4, "some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_Negative() {
    verify(string -> string.notContains("  some string    ", "some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsIgnoreCase_Negative() {
    verify(
        string -> string.notContainsIgnoreCase("  Some string    ", " Some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWith_Negative() {
    verify(string -> string.notEndsWith("  some string   s ", "s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWithIgnoreCase_Negative() {
    verify(
        string ->
            string.notEndsWithIgnoreCase("  some string   s ", "   S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreCase_Negative() {
    verify(
        string ->
            string.notEqualsIgnoreCase(
                "  some string    ", "  SOME string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreWhiteSpaces_Negative() {
    verify(
        string ->
            string.notEqualsIgnoreWhiteSpaces(
                "  some string    ", "  so me string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWith_Negative() {
    verify(string -> string.notStartsWith("  some string   s ", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWithIgnoreCase_Negative() {
    verify(
        string ->
            string.notStartsWithIgnoreCase("  some string   s ", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesEquals_Negative() {
    verify(
        string -> string.numberOfMatchesEquals("  some string   s ", "s", 2, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesNotEquals_Negative() {
    verify(
        string ->
            string.numberOfMatchesNotEquals("  some String   s ", "s", 2, "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndEquals_Negative() {
    verify(
        string ->
            string.removeEndEquals(
                "  some string   s ", "  some ", "  some string   S ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.removeEndIgnoreCaseEquals(
                "  some $tring   s ", "TRING   x ", "  some $", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.removeEndIgnoreCaseNotEquals(
                "  some STRING    ", " ", "  some STRING   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndNotEquals_Negative() {
    verify(
        string ->
            string.removeEndNotEquals(
                "  some STRING    ", "STRING    ", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEquals_Negative() {
    verify(
        string ->
            string.removeEquals(
                "  some String   so ", "so", "  me string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.removeIgnoreCaseEquals(
                "  some String   so ", "SO", "  me Xtring    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.removeIgnoreCaseNotEquals(
                "  some STRING    ", " ", "someSTRING", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveNotEquals_Negative() {
    verify(
        string ->
            string.removeNotEquals(
                "  some STRING    ", "STRING   ", "  some  ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartEquals_Negative() {
    verify(
        string ->
            string.removeStartEquals(
                "  some string   s ", "  some ", "  some string   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.removeStartIgnoreCaseEquals(
                "  some string   s ", "  some ", "some string   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.removeStartIgnoreCaseNotEquals(
                "  some $tring   s ", "  some ", "$tring   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartNotEquals_Negative() {
    verify(
        string ->
            string.removeStartNotEquals(
                "  some string   s ", "  some ", "string   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceEquals_Negative() {
    verify(
        string ->
            string.replaceEquals(
                "  some String   so ", "so", "XX", "  XXme String   XX", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.replaceIgnoreCaseEquals(
                "  some String   so ", "SO", "x", "  xme String   x", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.replaceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " xomextring  x ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceNotEquals_Negative() {
    verify(
        string ->
            string.replaceNotEquals(
                "  some String   s ", " s", "x", " xome String  x ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceEquals_Negative() {
    verify(
        string ->
            string.replaceOnceEquals(
                "  some String   so ", "so", "XX", "  some String   so ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseEquals_Negative() {
    verify(
        string ->
            string.replaceOnceIgnoreCaseEquals(
                "  some string   s ", "s", "|", "  |ome string   s", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseNotEquals_Negative() {
    verify(
        string ->
            string.replaceOnceIgnoreCaseNotEquals(
                "  some String   s ", " s", "x", " xome String   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceNotEquals_Negative() {
    verify(
        string ->
            string.replaceOnceNotEquals(
                "  some String   s ", " s", "x", " xome String   s ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseEquals_Negative() {
    verify(
        string ->
            string.reverseEquals(
                "  some @#$%^&*.   so ", " os   .*&^%$#@ emos ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseNotEquals_Negative() {
    verify(
        string ->
            string.reverseNotEquals(
                "  some string  s ", " s  gnirts emos  ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadEquals_Negative() {
    verify(
        string ->
            string.rightPadEquals(
                "  some string    ", 10, "@", "  some string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadNotEquals_Negative() {
    verify(
        string ->
            string.rightPadNotEquals(
                "  some string   s ",
                40,
                "x",
                "  some string   s xxxxxxxxxxxxxxxxxxxxxx",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueEquals_Negative() {
    verify(
        string -> string.rightValueEquals("  some string    ", 7, "ing   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueNotEquals_Negative() {
    verify(
        string ->
            string.rightValueNotEquals("  some string    ", 7, "ing    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWith_Negative() {
    verify(string -> string.startsWith("  some string   s ", " some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithAny_Negative() {
    verify(
        string ->
            string.startsWithAny(
                "  some string   s ", new CList<>("A", null, "some"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithIgnoreCase_Negative() {
    verify(
        string -> string.startsWithIgnoreCase("  some string   s ", "some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithNone_Negative() {
    verify(
        string ->
            string.startsWithNone(
                "  some string   s ", new CList<>("  some", "     ", "s "), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValue_Negative() {
    verify(
        string ->
            string.stripedEndValue(
                "  some string    ", " ", "  some string ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValueNot_Negative() {
    verify(
        string ->
            string.stripedEndValueNot(
                "  some string    ", " ", "  some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValue_Negative() {
    verify(
        string ->
            string.stripedStartValue(
                "  some string    ", " ", "some string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValueNot_Negative() {
    verify(
        string ->
            string.stripedStartValueNot(
                "  some string    ", " ", "some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValue_Negative() {
    verify(
        string ->
            string.stripedValue("  some string    ", " ", "some string ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValueNot_Negative() {
    verify(
        string ->
            string.stripedValueNot("  some string    ", " ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals1_Negative() {
    verify(
        string ->
            string.substringAfterEquals(
                "  some string    ", " s", "ome string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals2_Negative() {
    verify(
        string ->
            string.substringAfterEquals("  some string    ", null, "s", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals1_Negative() {
    verify(
        string ->
            string.substringAfterLastEquals("  some string    ", null, "s", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals2_Negative() {
    verify(
        string ->
            string.substringAfterLastEquals(
                "  some string    ", " s", "tring   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastNotEquals_Negative() {
    verify(
        string ->
            string.substringAfterLastNotEquals(
                "  some string    ", " s", "tring    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterNotEquals_Negative() {
    verify(
        string ->
            string.substringAfterNotEquals(
                "  some string    ", " s", "ome string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals1_Negative() {
    verify(
        string ->
            string.substringBeforeEquals(
                "  some string    ", " st", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals2_Negative() {
    verify(
        string ->
            string.substringBeforeEquals(
                "  some string    ", null, "  some string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals1_Negative() {
    verify(
        string ->
            string.substringBeforeLastEquals(
                "  some string    ", " s", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals2_Negative() {
    verify(
        string ->
            string.substringBeforeLastEquals(
                "  some string    ", " s", "  some ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastNotEquals_Negative() {
    verify(
        string ->
            string.substringBeforeLastNotEquals(
                "  some string    ", " s", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeNotEquals_Negative() {
    verify(
        string ->
            string.substringBeforeNotEquals(
                "  some string    ", " st", "  some", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenEquals_Negative() {
    verify(
        string ->
            string.substringBetweenEquals(
                "  some string    ", "  ", "    ", "some sstring", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenNotEquals_Negative() {
    verify(
        string ->
            string.substringBetweenNotEquals(
                "  some string    ", "  ", "    ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals1_Negative() {
    verify(
        string ->
            string.substringEquals(
                "  some string    ", 0, "  some strin    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals2_Negative() {
    verify(
        string -> string.substringEquals("  some string    ", 0, 3, "  sx", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals3_Negative() {
    verify(
        string -> string.substringEquals("  some string    ", 2, 4, "sxo", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals1_Negative() {
    verify(
        string ->
            string.substringNotEquals(
                "  some string    ", 0, "  some string    ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals2_Negative() {
    verify(
        string ->
            string.substringNotEquals("  some string    ", 0, 3, "  s", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals3_Negative() {
    verify(
        string -> string.substringNotEquals("  some string    ", 2, 4, "so", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenContains_Negative() {
    verify(
        string ->
            string.substringsBetweenContains(
                "  some string   s ", " ", "s", "x", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotContains_Negative() {
    verify(
        string ->
            string.substringsBetweenNotContains(
                "  some string   s ", " ", "s", " ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotEquals_Negative() {
    verify(
        string ->
            string.substringsBetweenNotEquals(
                "  some string   s ",
                " ",
                "s",
                new CList<>(" ", "", "  "),
                "some string",
                "%s#%s",
                getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValue_Negative() {
    verify(string -> string.trimmedValue("some string    ", "some String", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValueNot_Negative() {
    verify(
        string -> string.trimmedValueNot("some string    ", "some string", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue1_Negative() {
    verify(
        string ->
            string.truncatedValue("some string    ", 10, "some sxtrin", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue2_Negative() {
    verify(
        string ->
            string.truncatedValue("some string    ", 4, 10, " sxtring   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot1_Negative() {
    verify(
        string ->
            string.truncatedValueNot("some string    ", 10, "some strin", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot2_Negative() {
    verify(
        string ->
            string.truncatedValueNot("some string    ", 4, 10, " string   ", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatches() {
    verify(string -> string.matches("some string    ", "[a-z ]+", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_ExpectedNull() {
    verify(string -> string.matches(null, " tring   ", "%s#%s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_Negative() {
    verify(string -> string.matches("some string    ", "\\d+", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesPattern() {
    verify(
        string ->
            string.matches("some string    ", Pattern.compile("[a-z ]+"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_ExpectedNull() {
    verify(string -> string.matches(null, " tring   ", "%s#%s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_Negative() {
    verify(
        string -> string.matches("some string    ", Pattern.compile("\\d+"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatches() {
    verify(string -> string.notMatches("some string    ", "\\d+", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_ExpectedNull() {
    verify(string -> string.notMatches(null, " tring   ", "%s#%s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_Negative() {
    verify(string -> string.notMatches("some string    ", "[a-z ]+", "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatchesPattern() {
    verify(
        string ->
            string.notMatches("some string    ", Pattern.compile("\\d+"), "%s#%s", getParams()));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_ExpectedNull() {
    verify(string -> string.notMatches(null, " tring   ", "%s#%s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_Negative() {
    verify(
        string ->
            string.notMatches("some string    ", Pattern.compile("[a-z ]+"), "%s#%s", getParams()));
  }

  public abstract void verify(Consumer<CStringVerification> action);
}

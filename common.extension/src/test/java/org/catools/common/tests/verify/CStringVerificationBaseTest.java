package org.catools.common.tests.verify;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.hard.CStringVerification;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public abstract class CStringVerificationBaseTest extends CBaseUnitTest {
  private static final String CSTRING1 = "This is the first String with some 1209op31mk2w9@# values.";
  private String NULL = null;
  private String EMPTY = "";

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadEquals() {
    verify(string -> string.centerPadEquals("  some string    ", 10, "@", "  some string    "));
    verify(string -> string.centerPadEquals("  some string    ", 10, "@", "  some string    ", "testCenterPadEquals"));
    verify(string -> string.centerPadEquals("  some string    ", 30, "@", "@@@@@@  some string    @@@@@@@"));
    verify(string -> string.centerPadEquals("  some string    ", 30, "@", "@@@@@@  some string    @@@@@@@", "testCenterPadEquals"));
    verify(string -> string.centerPadEquals("  some string    ", 10, NULL, "  some string    "));
    verify(string -> string.centerPadEquals("  some string    ", 10, NULL, "  some string    ", "testCenterPadEquals_PadNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadNotEquals() {
    verify(string -> string.centerPadNotEquals("  some string    ", 10, "@", " some string    "));
    verify(string -> string.centerPadNotEquals("  some string    ", 10, "@", " some string    ", "testCenterPadNotEquals"));
    verify(string -> string.centerPadNotEquals("  some string    ", 30, "@", "@@@@@  some string    @@@@@@@"));
    verify(string -> string.centerPadNotEquals("  some string    ", 30, "@", "@@@@@  some string    @@@@@@@", "testCenterPadNotEquals"));
    verify(string -> string.centerPadNotEquals("  some string    ", 10, NULL, " some string    "));
    verify(string -> string.centerPadNotEquals("  some string    ", 10, NULL, " some string    ", "testCenterPadNotEquals_PadNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompare() {
    verify(string -> string.compare("  some string    ", "  some string    ", 0));
    verify(string -> string.compare("  some string    ", "  some string    ", 0, "testCompare"));
    verify(string -> string.compare("  SOME string    ", "  some string    ", -32));
    verify(string -> string.compare("  SOME string    ", "  some string    ", -32, "testCompare"));
    verify(string -> string.compare(NULL, null, 0));
    verify(string -> string.compare(NULL, null, 0, "testStripedEndValue"));
    verify(string -> string.compare("  some string    ", "  some String    ", 32));
    verify(string -> string.compare("  some string    ", "  some String    ", 32, "testCompare_NotCompare"));
    verify(string -> string.compare("  some string    ", null, 1));
    verify(string -> string.compare("  some string    ", null, 1, "testCompare_ExpectedNull"));
    verify(string -> string.compare(NULL, "  some string    ", -1));
    verify(string -> string.compare(NULL, "  some string    ", -1, "testCompare_ActualNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompareIgnoreCase() {
    verify(string -> string.compareIgnoreCase("  some string    ", "  SOME string    ", 0));
    verify(string -> string.compareIgnoreCase("  some string    ", "  SOME string    ", 0, "testCompareIgnoreCase"));
    verify(string -> string.compareIgnoreCase("  SOME string    ", "  some string    ", 0));
    verify(string -> string.compareIgnoreCase("  SOME string    ", "  some string    ", 0, "testCompareIgnoreCase"));
    verify(string -> string.compareIgnoreCase(NULL, null, 0));
    verify(string -> string.compareIgnoreCase(NULL, null, 0, "testStripedEndValue"));
    verify(string -> string.compareIgnoreCase("  some string    ", "  some xtring    ", -5));
    verify(string -> string.compareIgnoreCase("  some string    ", "  some xtring    ", -5, "testCompareIgnoreCase_NotCompare"));
    verify(string -> string.compareIgnoreCase("  some string    ", null, 1));
    verify(string -> string.compareIgnoreCase("  some string    ", null, 1, "testCompareIgnoreCase_ExpectedNull"));
    verify(string -> string.compareIgnoreCase(NULL, "  some string    ", -1));
    verify(string -> string.compareIgnoreCase(NULL, "  some string    ", -1, "testCompareIgnoreCase_ActualNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify(string -> string.contains("  some string    ", "so"));
    verify(string -> string.contains("  some string    ", "so", "testContains"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsIgnoreCase() {
    verify(string -> string.containsIgnoreCase("  Some string    ", " so"));
    verify(string -> string.containsIgnoreCase("  Some string    ", " so", "testContainsIgnoreCase"));
    verify(string -> string.containsIgnoreCase("  some $tring    ", "$TRING"));
    verify(string -> string.containsIgnoreCase("  some $tring    ", "$TRING", "testContainsIgnoreCase"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWith() {
    verify(string -> string.endsWith("  some string   s ", "   s "));
    verify(string -> string.endsWith("  some string   s ", "   s ", "testEndsWith"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithAny() {
    verify(string -> string.endsWithAny("  some string   s ", new CList<>("A", null, " s ")));
    verify(string -> string.endsWithAny("  some string   s ", new CList<>("A", null, " s "), "testEndsWithAny"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithIgnoreCase() {
    verify(string -> string.endsWithIgnoreCase("  some string   s ", "   s "));
    verify(string -> string.endsWithIgnoreCase("  some string   s ", "   s ", "testEndsWithIgnoreCase"));
    verify(string -> string.endsWithIgnoreCase("  some string   s ", "   S "));
    verify(string -> string.endsWithIgnoreCase("  some string   s ", "   S ", "testEndsWithIgnoreCase"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithNone() {
    verify(string -> string.endsWithNone("  some string   s ", new CList<>("A", " s  ")));
    verify(string -> string.endsWithNone("  some string   s ", new CList<>("A", " s  "), "testEndsWithNone"));
    verify(string -> string.endsWithNone("  some string   s ", new CList<>("A", " S ")));
    verify(string -> string.endsWithNone("  some string   s ", new CList<>("A", " S "), "testEndsWithNone"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(string -> string.equals("  some string    ", "  some string    "));
    verify(string -> string.equals("  some string    ", "  some string    ", "testEquals"));
    verify(string -> string.equals(NULL, null));
    verify(string -> string.equals(NULL, null, "testStripedEndValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEquals() {
    verify(string -> string.notEquals("  some string    ", "  some sring    "));
    verify(string -> string.notEquals("  some string    ", "  some sring    ", "testEquals"));
    verify(string -> string.notEquals(NULL, "null"));
    verify(string -> string.notEquals("null", null, "testStripedEndValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAny() {
    verify(string -> string.equalsAny("  some string    ", new CList<>("", "  some string    ")));
    verify(string -> string.equalsAny("  some string    ", new CList<>("", "  some string    "), "testEqualsAny"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAnyIgnoreCase() {
    verify(string -> string.equalsAnyIgnoreCase("  some STRING    ", new CList<>("", "  SOME string    ")));
    verify(string -> string.equalsAnyIgnoreCase("  some STRING    ", new CList<>("", "  SOME string    "), "testEqualsAnyIgnoreCase"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreCase() {
    verify(string -> string.equalsIgnoreCase("  some string    ", "  SOME string    "));
    verify(string -> string.equalsIgnoreCase("  some string    ", "  SOME string    ", "testEqualsIgnoreCase"));
    verify(string -> string.equalsIgnoreCase(NULL, null));
    verify(string -> string.equalsIgnoreCase(NULL, null, "testStripedEndValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreWhiteSpaces() {
    verify(string -> string.equalsIgnoreWhiteSpaces("  some string    ", " s o me s t r ing    "));
    verify(string -> string.equalsIgnoreWhiteSpaces("  some string    ", " s o me s t r ing    ", "testEqualsIgnoreWhiteSpaces"));
    verify(string -> string.equalsIgnoreWhiteSpaces("  some string    ", "somestring"));
    verify(string -> string.equalsIgnoreWhiteSpaces("  some string    ", "somestring", "testEqualsIgnoreWhiteSpaces"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNone() {
    verify(string -> string.equalsNone("  some string    ", new CList<>("", "  some String    ")));
    verify(string -> string.equalsNone("  some string    ", new CList<>("", "  some String    "), "testNone"));
    verify(string -> string.equalsNone("  some string    ", new CList<>("", null)));
    verify(string -> string.equalsNone("  some string    ", new CList<>("", null), "testNone_ExpectedNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNoneIgnoreCase() {
    verify(string -> string.equalsNoneIgnoreCase("  some STRING    ", new CList<>("", "  $ome string    ")));
    verify(string -> string.equalsNoneIgnoreCase("  some STRING    ", new CList<>("", "  $ome string    "), "testNoneIgnoreCase"));
    verify(string -> string.equalsNoneIgnoreCase("  some string    ", new CList<>("", null)));
    verify(string -> string.equalsNoneIgnoreCase("  some string    ", new CList<>("", null), "testNoneIgnoreCase_ExpectedNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlpha() {
    verify(string -> string.isAlpha("aiulajksn"));
    verify(string -> string.isAlpha("aiulajksn", "testIsAlpha"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlpha() {
    verify(string -> string.isEmptyOrAlpha("aiulajksn"));
    verify(string -> string.isEmptyOrAlpha("aiulajksn", "testIsEmptyOrAlpha"));
    verify(string -> string.isEmptyOrAlpha(""));
    verify(string -> string.isEmptyOrAlpha("", "testIsEmptyOrAlpha"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlankOrAlpha() {
    verify(string -> string.isBlankOrAlpha("aiulajksn"));
    verify(string -> string.isBlankOrAlpha("aiulajksn", "testIsBlankOrAlpha"));
    verify(string -> string.isBlankOrAlpha(null));
    verify(string -> string.isBlankOrAlpha(null, "testIsBlankOrAlpha"));
    verify(string -> string.isBlankOrAlpha(""));
    verify(string -> string.isBlankOrAlpha("", "testIsBlankOrAlpha"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlankOrNotAlpha() {
    verify(string -> string.isBlankOrNotAlpha("aiu12lajksn"));
    verify(string -> string.isBlankOrNotAlpha("aiu12lajksn", "testIsBlankOrAlpha"));
    verify(string -> string.isBlankOrNotAlpha(null));
    verify(string -> string.isBlankOrNotAlpha(null, "testIsBlankOrAlpha"));
    verify(string -> string.isBlankOrNotAlpha(""));
    verify(string -> string.isBlankOrNotAlpha("", "testIsBlankOrAlpha"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlankOrAlphanumeric() {
    verify(string -> string.isBlankOrAlphanumeric("aiul12ajksn"));
    verify(string -> string.isBlankOrAlphanumeric("aiula12jksn", "testIsBlankOrAlpha"));
    verify(string -> string.isBlankOrAlphanumeric(null));
    verify(string -> string.isBlankOrAlphanumeric(null, "testIsBlankOrAlpha"));
    verify(string -> string.isBlankOrAlphanumeric(""));
    verify(string -> string.isBlankOrAlphanumeric("", "testIsBlankOrAlpha"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlankOrNotAlphanumeric() {
    verify(string -> string.isBlankOrNotAlphanumeric("aiul 12ajksn"));
    verify(string -> string.isBlankOrNotAlphanumeric("aiul a12jksn", "testIsBlankOrAlpha"));
    verify(string -> string.isBlankOrNotAlphanumeric(""));
    verify(string -> string.isBlankOrNotAlphanumeric("", "testIsBlankOrAlpha"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlankOrNumeric() {
    verify(string -> string.isBlankOrNumeric("123456"));
    verify(string -> string.isBlankOrNumeric("123456", "testIsBlankOrAlpha"));
    verify(string -> string.isBlankOrNumeric(""));
    verify(string -> string.isBlankOrNumeric("", "testIsBlankOrAlpha"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlankOrNotNumeric() {
    verify(string -> string.isBlankOrNotNumeric("123a456"));
    verify(string -> string.isBlankOrNotNumeric("123a456", "testIsBlankOrAlpha"));
    verify(string -> string.isBlankOrNotNumeric(""));
    verify(string -> string.isBlankOrNotNumeric("", "testIsBlankOrAlpha"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphaSpace() {
    verify(string -> string.isAlphaSpace(" aiul ajk sn"));
    verify(string -> string.isAlphaSpace(" aiul ajk sn", "testIsAlphaSpace"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumeric() {
    verify(string -> string.isAlphanumeric("aiulaj45872ksn1"));
    verify(string -> string.isAlphanumeric("aiulaj45872ksn1", "testIsAlphanumeric"));
    verify(string -> string.isAlphanumeric("1234"));
    verify(string -> string.isAlphanumeric("1234", "testIsAlphanumeric"));
    verify(string -> string.isAlphanumeric("blkajsblas"));
    verify(string -> string.isAlphanumeric("blkajsblas", "testIsAlphanumeric"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlphanumeric() {
    verify(string -> string.isEmptyOrAlphanumeric("aiulaj6265opksn"));
    verify(string -> string.isEmptyOrAlphanumeric("aiulaj6265opksn", "testIsEmptyOrAlphanumeric"));
    verify(string -> string.isEmptyOrAlphanumeric(""));
    verify(string -> string.isEmptyOrAlphanumeric("", "testIsEmptyOrAlphanumeric"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumericSpace() {
    verify(string -> string.isAlphanumericSpace("ai1ul90jksn"));
    verify(string -> string.isAlphanumericSpace("ai1ul90jksn", "testIsAlphanumericSpace"));
    verify(string -> string.isAlphanumericSpace(" ai1ul90 ajk sn"));
    verify(string -> string.isAlphanumericSpace(" ai1ul90 ajk sn", "testIsAlphanumericSpace"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(string -> {
      chars[5] = 32;
      string.isAsciiPrintable(String.valueOf(chars));
      string.isAsciiPrintable(String.valueOf(chars), "isAsciiPrintable");
    });
    verify(string -> {
      chars[5] = 33;
      string.isAsciiPrintable(String.valueOf(chars));
      string.isAsciiPrintable(String.valueOf(chars), "isAsciiPrintable");
    });
    verify(string -> {
      chars[5] = 125;
      string.isAsciiPrintable(String.valueOf(chars));
      string.isAsciiPrintable(String.valueOf(chars), "isAsciiPrintable");
    });
    verify(string -> {
      chars[5] = 126;
      string.isAsciiPrintable(String.valueOf(chars));
      string.isAsciiPrintable(String.valueOf(chars), "isAsciiPrintable");
    });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlank() {
    verify(string -> string.isBlank(NULL));
    verify(string -> string.isBlank(NULL, "testIsBlank"));
    verify(string -> string.isBlank(EMPTY));
    verify(string -> string.isBlank(EMPTY, "testIsBlank"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify(string -> string.isEmpty(EMPTY));
    verify(string -> string.isEmpty(EMPTY, "testIsEmpty"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlpha() {
    verify(string -> string.isNotAlpha("123aasf2"));
    verify(string -> string.isNotAlpha("123aasf2", "testIsNotAlphanumericSpace"));
    verify(string -> string.isNotAlpha(""));
    verify(string -> string.isNotAlpha("", "testIsNotAlphanumericSpace"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlpha() {
    verify(string -> string.isEmptyOrNotAlpha("aiulaj626"));
    verify(string -> string.isEmptyOrNotAlpha("aiulaj626", "testIsNotAlphanumericSpace"));
    verify(string -> string.isEmptyOrNotAlpha(""));
    verify(string -> string.isEmptyOrNotAlpha("", "testIsNotAlphanumericSpace"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphaSpace() {
    verify(string -> string.isNotAlphaSpace("aiulaj626"));
    verify(string -> string.isNotAlphaSpace("aiulaj626", "testIsNotAlphanumericSpace"));
    verify(string -> string.isNotAlphaSpace("@ a"));
    verify(string -> string.isNotAlphaSpace("@ a", "testIsNotAlphanumericSpace"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumeric() {
    verify(string -> string.isNotAlphanumeric("aiulaj626!5opksn"));
    verify(string -> string.isNotAlphanumeric("aiulaj626!5opksn", "testIsNotAlphanumericSpace"));
    verify(string -> string.isNotAlphanumeric("@#.*"));
    verify(string -> string.isNotAlphanumeric("@#.*", "testIsNotAlphanumericSpace"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlphanumeric() {
    verify(string -> string.isEmptyOrNotAlphanumeric("aiulaj626 5opksn"));
    verify(string -> string.isEmptyOrNotAlphanumeric("aiulaj626 5opksn", "testIsEmptyOrNotAlphanumeric"));
    verify(string -> string.isEmptyOrNotAlphanumeric("@#.*"));
    verify(string -> string.isEmptyOrNotAlphanumeric("@#.*", "testIsEmptyOrNotAlphanumeric"));
    verify(string -> string.isEmptyOrNotAlphanumeric(""));
    verify(string -> string.isEmptyOrNotAlphanumeric("", "testIsEmptyOrNotAlphanumeric"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumericSpace() {
    verify(string -> string.isNotAlphanumericSpace("aiulaj626 !5opksn"));
    verify(string -> string.isNotAlphanumericSpace("aiulaj626 !5opksn", "testIsNotAlphanumericSpace"));
    verify(string -> string.isNotAlphanumericSpace("@#.*"));
    verify(string -> string.isNotAlphanumericSpace("@#.*", "testIsNotAlphanumericSpace"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    verify(string -> {
      chars[5] = 30;
      string.isNotAsciiPrintable(String.valueOf(chars));
      string.isNotAsciiPrintable(String.valueOf(chars), "testIsNotAsciiPrintable");
    });
    verify(string -> {
      chars[5] = 31;
      string.isNotAsciiPrintable(String.valueOf(chars));
      string.isNotAsciiPrintable(String.valueOf(chars), "testIsNotAsciiPrintable");
    });
    verify(string -> {
      chars[5] = 127;
      string.isNotAsciiPrintable(String.valueOf(chars));
      string.isNotAsciiPrintable(String.valueOf(chars), "testIsNotAsciiPrintable");
    });
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotBlank() {
    verify(string -> string.isNotBlank(CSTRING1));
    verify(string -> string.isNotBlank(CSTRING1, "testIsNotBlank"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify(string -> string.isNotEmpty(CSTRING1));
    verify(string -> string.isNotEmpty(CSTRING1, "testIsNotEmpty"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumeric() {
    verify(string -> string.isNotNumeric("a1234567"));
    verify(string -> string.isNotNumeric("a1234567", "testIsNotNumeric"));
    verify(string -> string.isNotNumeric(" "));
    verify(string -> string.isNotNumeric(" ", "testIsNotNumeric"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotNumeric() {
    verify(string -> string.isEmptyOrNotNumeric("a123 4567"));
    verify(string -> string.isEmptyOrNotNumeric("a123 4567", "testIsEmptyOrNotNumeric"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumericSpace() {
    verify(string -> string.isNotNumericSpace("a123 4567"));
    verify(string -> string.isNotNumericSpace("a123 4567", "testIsNotNumericSpace"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumeric() {
    verify(string -> string.isNumeric("1234567"));
    verify(string -> string.isNumeric("1234567", "testIsNumeric"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNumeric() {
    verify(string -> string.isEmptyOrNumeric("1234567"));
    verify(string -> string.isEmptyOrNumeric("1234567", "testIsEmptyOrNumeric"));
    verify(string -> string.isEmptyOrNumeric(""));
    verify(string -> string.isEmptyOrNumeric("", "testIsEmptyOrNumeric"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumericSpace() {
    verify(string -> string.isNumericSpace("2345678"));
    verify(string -> string.isNumericSpace("2345678", "testIsNumericSpace"));
    verify(string -> string.isNumericSpace(" 1254 786 1"));
    verify(string -> string.isNumericSpace(" 1254 786 1", "testIsNumericSpace"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadEquals() {
    verify(string -> string.leftPadEquals("  some string    ", 10, "@", "  some string    "));
    verify(string -> string.leftPadEquals("  some string    ", 10, "@", "  some string    ", "testLeftPad"));
    verify(string -> string.leftPadEquals("  some string    ", 30, "@", "@@@@@@@@@@@@@  some string    "));
    verify(string -> string.leftPadEquals("  some string    ", 30, "@", "@@@@@@@@@@@@@  some string    ", "testLeftPad"));
    verify(string -> string.leftPadEquals("  some string    ", 10, NULL, "  some string    "));
    verify(string -> string.leftPadEquals("  some string    ", 10, NULL, "  some string    ", "testLeftPad_PadNull"));
    verify(string -> string.leftPadEquals("  some string   s ", 30, "", "              some string   s "));
    verify(string -> string.leftPadEquals("  some string   s ", 30, "", "              some string   s ", "testLeftPad"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadNotEquals() {
    verify(string -> string.leftPadNotEquals("  some string    ", 10, "@", " some string    "));
    verify(string -> string.leftPadNotEquals("  some string    ", 10, "@", " some string    ", "testLeftPadNotEquals"));
    verify(string -> string.leftPadNotEquals("  some string    ", 30, "@", "@@@@@@@@@@@@  some string    "));
    verify(string -> string.leftPadNotEquals("  some string    ", 30, "@", "@@@@@@@@@@@@  some string    ", "testLeftPadNotEquals"));
    verify(string -> string.leftPadNotEquals("  some string    ", 10, NULL, " some string    "));
    verify(string -> string.leftPadNotEquals("  some string    ", 10, NULL, " some string    ", "testLeftPadNotEquals"));
    verify(string -> string.leftPadNotEquals("  some string   s ", 30, "", "             some string   s "));
    verify(string -> string.leftPadNotEquals("  some string   s ", 30, "", "             some string   s ", "testLeftPadNotEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueEquals() {
    verify(string -> string.leftValueEquals("  some string    ", 7, "  some "));
    verify(string -> string.leftValueEquals("  some string    ", 7, "  some ", "testSubstring"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueNotEquals() {
    verify(string -> string.leftValueNotEquals("  some string    ", 7, " some "));
    verify(string -> string.leftValueNotEquals("  some string    ", 7, " some ", "testSubstring"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthEquals() {
    verify(string -> string.lengthEquals("  some string   s ", 18));
    verify(string -> string.lengthEquals("  some string   s ", 18, "testLength"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthNotEquals() {
    verify(string -> string.lengthNotEquals("aasa", 0));
    verify(string -> string.lengthNotEquals("aasa", 0, "testLengthNotEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueEquals() {
    verify(string -> string.midValueEquals("  some string    ", 2, 4, "some"));
    verify(string -> string.midValueEquals("  some string    ", 2, 4, "some", "testMid"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueNotEquals() {
    verify(string -> string.midValueNotEquals("  some string    ", 2, 4, "some "));
    verify(string -> string.midValueNotEquals("  some string    ", 2, 4, "some ", "testMid"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify(string -> string.notContains("  some string    ", "sox"));
    verify(string -> string.notContains("  some string    ", "sox", "testNotContains"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsIgnoreCase() {
    verify(string -> string.notContainsIgnoreCase("  Some string    ", " sox"));
    verify(string -> string.notContainsIgnoreCase("  Some string    ", " sox", "testNotContainsIgnoreCase"));
    verify(string -> string.notContainsIgnoreCase("  some $tring    ", "x$TRING"));
    verify(string -> string.notContainsIgnoreCase("  some $tring    ", "x$TRING", "testNotContainsIgnoreCase"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWith() {
    verify(string -> string.notEndsWith("  some string   s ", ".* "));
    verify(string -> string.notEndsWith("  some string   s ", ".* ", "testEndsWith_CaseNotMatch"));
    verify(string -> string.notEndsWith("  some string   s ", "S "));
    verify(string -> string.notEndsWith("  some string   s ", "S ", "testEndsWith_CaseNotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWithIgnoreCase() {
    verify(string -> string.notEndsWithIgnoreCase("  some string   s ", "   $ "));
    verify(string -> string.notEndsWithIgnoreCase("  some string   s ", "   $ ", "testEndsWithIgnoreCase"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreCase() {
    verify(string -> string.notEqualsIgnoreCase("  some string    ", "  $OME string    "));
    verify(string -> string.notEqualsIgnoreCase("  some string    ", "  $OME string    ", "testEqualsIgnoreCase"));
    verify(string -> string.notEqualsIgnoreCase(NULL, ""));
    verify(string -> string.notEqualsIgnoreCase(NULL, "", "testStripedEndValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreWhiteSpaces() {
    verify(string -> string.notEqualsIgnoreWhiteSpaces("  some string    ", "  $OME string    "));
    verify(string -> string.notEqualsIgnoreWhiteSpaces("  some string    ", "  $OME string    ", "testEqualsIgnoreWhiteSpaces"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWith() {
    verify(string -> string.notStartsWith("  some string   s ", " some"));
    verify(string -> string.notStartsWith("  some string   s ", " some", "testStartsWith"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWithIgnoreCase() {
    verify(string -> string.notStartsWithIgnoreCase("  some string   s ", " Some"));
    verify(string -> string.notStartsWithIgnoreCase("  some string   s ", " Some", "testStartsWith"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesEquals() {
    verify(string -> string.numberOfMatchesEquals("  some string   s ", "s", 3));
    verify(string -> string.numberOfMatchesEquals("  some string   s ", "s", 3, "testNumberOfMatches"));
    verify(string -> string.numberOfMatchesEquals("  some String   s ", "s", 2));
    verify(string -> string.numberOfMatchesEquals("  some String   s ", "s", 2, "testNumberOfMatches"));
    verify(string -> string.numberOfMatchesEquals("  some $tring   s ", "$", 1));
    verify(string -> string.numberOfMatchesEquals("  some $tring   s ", "$", 1, "testNumberOfMatches"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesNotEquals() {
    verify(string -> string.numberOfMatchesNotEquals("  some String   s ", "s", 1));
    verify(string -> string.numberOfMatchesNotEquals("  some String   s ", "s", 1, "testNumberOfMatches_NotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndEquals() {
    verify(string -> string.removeEndEquals("  some string   s ", "  some ", "  some string   s "));
    verify(string -> string.removeEndEquals("  some string   s ", "  some ", "  some string   s ", "testRemoveEnd"));
    verify(string -> string.removeEndEquals("  some string   s ", "some string   s ", "  "));
    verify(string -> string.removeEndEquals("  some string   s ", "some string   s ", "  ", "testRemoveEnd"));
    verify(string -> string.removeEndEquals("  some string   s ", "  some string   s ", ""));
    verify(string -> string.removeEndEquals("  some string   s ", "  some string   s ", "", "testRemoveEnd"));
    verify(string -> string.removeEndEquals("  some String   s ", null, "  some String   s "));
    verify(string -> string.removeEndEquals("  some String   s ", null, "  some String   s ", "testRemoveEnd"));
    verify(string -> string.removeEndEquals("  some String   s ", "tring   s ", "  some S"));
    verify(string -> string.removeEndEquals("  some String   s ", "tring   s ", "  some S", "testRemoveEnd"));
    verify(string -> string.removeEndEquals("  some $tring   s ", "tring   s ", "  some $"));
    verify(string -> string.removeEndEquals("  some $tring   s ", "tring   s ", "  some $", "testRemoveEnd"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseEquals() {
    verify(string -> string.removeEndIgnoreCaseEquals("  some string   s ", "  Some ", "  some string   s "));
    verify(string -> string.removeEndIgnoreCaseEquals("  some string   s ", "  Some ", "  some string   s ", "testRemoveEndIgnoreCase"));
    verify(string -> string.removeEndIgnoreCaseEquals("  some string   s ", "some String   s ", "  "));
    verify(string -> string.removeEndIgnoreCaseEquals("  some string   s ", "some String   s ", "  ", "testRemoveEndIgnoreCase"));
    verify(string -> string.removeEndIgnoreCaseEquals("  some string   s ", "  sOME string   s ", ""));
    verify(string -> string.removeEndIgnoreCaseEquals("  some string   s ", "  sOME string   s ", "", "testRemoveEndIgnoreCase"));
    verify(string -> string.removeEndIgnoreCaseEquals("  some String   s ", null, "  some String   s "));
    verify(string -> string.removeEndIgnoreCaseEquals("  some String   s ", null, "  some String   s ", "testRemoveEndIgnoreCase"));
    verify(string -> string.removeEndIgnoreCaseEquals("  some String   s ", "tring   S ", "  some S"));
    verify(string -> string.removeEndIgnoreCaseEquals("  some String   s ", "tring   S ", "  some S", "testRemoveEndIgnoreCase"));
    verify(string -> string.removeEndIgnoreCaseEquals("  some $tring   s ", "TRING   s ", "  some $"));
    verify(string -> string.removeEndIgnoreCaseEquals("  some $tring   s ", "TRING   s ", "  some $", "testRemoveEndIgnoreCase"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseNotEquals() {
    verify(string -> string.removeEndIgnoreCaseNotEquals("  some STRING    ", " ", "  STRING    "));
    verify(string -> string.removeEndIgnoreCaseNotEquals("  some STRING    ", " ", "  STRING    ", "testRemoveEndIgnoreCase_RemoveIgnoreCaseNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndNotEquals() {
    verify(string -> string.removeEndNotEquals("  some STRING    ", "STRING    ", "  SOME "));
    verify(string -> string.removeEndNotEquals("  some STRING    ", "STRING    ", "  SOME ", "testRemoveEndIgnoreCase_RemoveIgnoreCaseNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEquals() {
    verify(string -> string.removeEquals("  some string   s ", "s", "  ome tring    "));
    verify(string -> string.removeEquals("  some string   s ", "s", "  ome tring    ", "testRemove"));
    verify(string -> string.removeEquals("  some String   so ", "so", "  me String    "));
    verify(string -> string.removeEquals("  some String   so ", "so", "  me String    ", "testRemove"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseEquals() {
    verify(string -> string.removeIgnoreCaseEquals("  some string   s ", "s", "  ome tring    "));
    verify(string -> string.removeIgnoreCaseEquals("  some string   s ", "s", "  ome tring    ", "testRemoveIgnoreCaseEquals"));
    verify(string -> string.removeIgnoreCaseEquals("  some String   so ", "SO", "  me String    "));
    verify(string -> string.removeIgnoreCaseEquals("  some String   so ", "SO", "  me String    ", "testRemoveIgnoreCaseEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseNotEquals() {
    verify(string -> string.removeIgnoreCaseNotEquals("  some STRING    ", " ", "  some STRING "));
    verify(string -> string.removeIgnoreCaseNotEquals("  some STRING    ", " ", "  some STRING ", "testRemoveIgnoreCaseEquals_RemoveIgnoreCaseNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveNotEquals() {
    verify(string -> string.removeNotEquals("  some STRING    ", "STRING   ", "  some "));
    verify(string -> string.removeNotEquals("  some STRING    ", "STRING   ", "  some ", "testRemoveIgnoreCaseEquals_RemoveIgnoreCaseNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartEquals() {
    verify(string -> string.removeStartEquals("  some string   s ", "  some ", "string   s "));
    verify(string -> string.removeStartEquals("  some string   s ", "  some ", "string   s ", "testRemoveStartEquals"));
    verify(string -> string.removeStartEquals("  some string   s ", "some string   s ", "  some string   s "));
    verify(string -> string.removeStartEquals("  some string   s ", "some string   s ", "  some string   s ", "testRemoveStartEquals"));
    verify(string -> string.removeStartEquals("  some string   s ", "  some string   s ", ""));
    verify(string -> string.removeStartEquals("  some string   s ", "  some string   s ", "", "testRemoveStartEquals"));
    verify(string -> string.removeStartEquals("  some String   s ", null, "  some String   s "));
    verify(string -> string.removeStartEquals("  some String   s ", null, "  some String   s ", "testRemoveStartEquals"));
    verify(string -> string.removeStartEquals("  some String   s ", "  some S", "tring   s "));
    verify(string -> string.removeStartEquals("  some String   s ", "  some S", "tring   s ", "testRemoveStartEquals"));
    verify(string -> string.removeStartEquals("  some $tring   s ", "  some $", "tring   s "));
    verify(string -> string.removeStartEquals("  some $tring   s ", "  some $", "tring   s ", "testRemoveStartEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseEquals() {
    verify(string -> string.removeStartIgnoreCaseEquals("  some string   s ", "  some ", "string   s "));
    verify(string -> string.removeStartIgnoreCaseEquals("  some string   s ", "  some ", "string   s ", "testRemoveStartIgnoreCase"));
    verify(string -> string.removeStartIgnoreCaseEquals("  some string   s ", "  Some ", "string   s "));
    verify(string -> string.removeStartIgnoreCaseEquals("  some string   s ", "  Some ", "string   s ", "testRemoveStartIgnoreCase"));
    verify(string -> string.removeStartIgnoreCaseEquals("  some string   s ", "Some string   s ", "  some string   s "));
    verify(string -> string.removeStartIgnoreCaseEquals("  some string   s ", "Some string   s ", "  some string   s ", "testRemoveStartIgnoreCase"));
    verify(string -> string.removeStartIgnoreCaseEquals("  some string   s ", "  Some string   s ", ""));
    verify(string -> string.removeStartIgnoreCaseEquals("  some string   s ", "  Some string   s ", "", "testRemoveStartIgnoreCase"));
    verify(string -> string.removeStartIgnoreCaseEquals("  some String   s ", null, "  some String   s "));
    verify(string -> string.removeStartIgnoreCaseEquals("  some String   s ", null, "  some String   s ", "testRemoveStartIgnoreCase"));
    verify(string -> string.removeStartIgnoreCaseEquals("  some String   s ", "  some s", "tring   s "));
    verify(string -> string.removeStartIgnoreCaseEquals("  some String   s ", "  some s", "tring   s ", "testRemoveStartIgnoreCase"));
    verify(string -> string.removeStartIgnoreCaseEquals("  some $tring   s ", "  some $", "tring   s "));
    verify(string -> string.removeStartIgnoreCaseEquals("  some $tring   s ", "  some $", "tring   s ", "testRemoveStartIgnoreCase"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseNotEquals() {
    verify(string -> string.removeStartIgnoreCaseNotEquals("  some $tring   s ", "  some ", " $tring   s "));
    verify(string -> string.removeStartIgnoreCaseNotEquals("  some $tring   s ", "  some ", " $tring   s ", "testRemoveStartIgnoreCaseEquals_NotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartNotEquals() {
    verify(string -> string.removeStartNotEquals("  some string   s ", "  some ", "String   S "));
    verify(string -> string.removeStartNotEquals("  some string   s ", "  some ", "String   S ", "testRemoveStartNotEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceEquals() {
    verify(string -> string.replaceEquals("  some string   s ", "s", "", "  ome tring    "));
    verify(string -> string.replaceEquals("  some string   s ", "s", "", "  ome tring    ", "testReplaceEquals"));
    verify(string -> string.replaceEquals("  some String   so ", "so", "XX", "  XXme String   XX "));
    verify(string -> string.replaceEquals("  some String   so ", "so", "XX", "  XXme String   XX ", "testReplaceEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseEquals() {
    verify(string -> string.replaceIgnoreCaseEquals("  some string   s ", "s", "|", "  |ome |tring   | "));
    verify(string -> string.replaceIgnoreCaseEquals("  some string   s ", "s", "|", "  |ome |tring   | ", "testReplaceIgnoreCaseEquals"));
    verify(string -> string.replaceIgnoreCaseEquals("  some String   so ", "SO", "x", "  xme String   x "));
    verify(string -> string.replaceIgnoreCaseEquals("  some String   so ", "SO", "x", "  xme String   x ", "testReplaceIgnoreCaseEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseNotEquals() {
    verify(string -> string.replaceIgnoreCaseNotEquals("  some String   s ", " s", "x", " ome string   "));
    verify(string -> string.replaceIgnoreCaseNotEquals("  some String   s ", " s", "x", " ome string   ", "testReplace_NotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceNotEquals() {
    verify(string -> string.replaceNotEquals("  some String   s ", " s", "x", " ome string   "));
    verify(string -> string.replaceNotEquals("  some String   s ", " s", "x", " ome string   ", "testReplace_NotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceEquals() {
    verify(string -> string.replaceOnceEquals("  some string   s ", "s", "", "  ome string   s "));
    verify(string -> string.replaceOnceEquals("  some string   s ", "s", "", "  ome string   s ", "testReplaceOnceEquals"));
    verify(string -> string.replaceOnceEquals("  some String   so ", "so", "XX", "  XXme String   so "));
    verify(string -> string.replaceOnceEquals("  some String   so ", "so", "XX", "  XXme String   so ", "testReplaceOnceEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseEquals() {
    verify(string -> string.replaceOnceIgnoreCaseEquals("  some string   s ", "s", "|", "  |ome string   s "));
    verify(string -> string.replaceOnceIgnoreCaseEquals("  some string   s ", "s", "|", "  |ome string   s ", "testReplaceOnceIgnoreCaseEquals"));
    verify(string -> string.replaceOnceIgnoreCaseEquals("  some String   so ", "SO", "x", "  xme String   so "));
    verify(string -> string.replaceOnceIgnoreCaseEquals("  some String   so ", "SO", "x", "  xme String   so ", "testReplaceOnceIgnoreCaseEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseNotEquals() {
    verify(string -> string.replaceOnceIgnoreCaseNotEquals("  some String   s ", " s", "x", " ome string   "));
    verify(string -> string.replaceOnceIgnoreCaseNotEquals("  some String   s ", " s", "x", " ome string   ", "testReplaceOnceIgnoreCaseNotEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceNotEquals() {
    verify(string -> string.replaceOnceNotEquals("  some String   s ", " s", "x", " ome string   "));
    verify(string -> string.replaceOnceNotEquals("  some String   s ", " s", "x", " ome string   ", "testReplaceOnceIgnoreCaseNotEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseEquals() {
    verify(string -> string.reverseEquals("  some string   s ", " s   gnirts emos  "));
    verify(string -> string.reverseEquals("  some string   s ", " s   gnirts emos  ", "testReverse"));
    verify(string -> string.reverseEquals("  some @#$%^&*.   so ", " os   .*&^%$#@ emos  "));
    verify(string -> string.reverseEquals("  some @#$%^&*.   so ", " os   .*&^%$#@ emos  ", "testReverse"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseNotEquals() {
    verify(string -> string.reverseNotEquals("  some string  s ", " s   gnirts emos  "));
    verify(string -> string.reverseNotEquals("  some string  s ", " s   gnirts emos  ", "testReverse"));
    verify(string -> string.reverseNotEquals("  some @#$%^*.   so ", " os   .*&^%$#@ emos  "));
    verify(string -> string.reverseNotEquals("  some @#$%^*.   so ", " os   .*&^%$#@ emos  ", "testReverse"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadEquals() {
    verify(string -> string.rightPadEquals("  some string    ", 10, "@", "  some string    "));
    verify(string -> string.rightPadEquals("  some string    ", 10, "@", "  some string    ", "testRightPad"));
    verify(string -> string.rightPadEquals("  some string    ", 30, "@", "  some string    @@@@@@@@@@@@@"));
    verify(string -> string.rightPadEquals("  some string    ", 30, "@", "  some string    @@@@@@@@@@@@@", "testRightPad"));
    verify(string -> string.rightPadEquals("  some string    ", 10, NULL, "  some string    "));
    verify(string -> string.rightPadEquals("  some string    ", 10, NULL, "  some string    ", "testRightPad_PadNull"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadNotEquals() {
    verify(string -> string.rightPadNotEquals("  some string   s ", 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx"));
    verify(string -> string.rightPadNotEquals("  some string   s ", 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx", "testRightPad"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueEquals() {
    verify(string -> string.rightValueEquals("  some string    ", 7, "ing    "));
    verify(string -> string.rightValueEquals("  some string    ", 7, "ing    ", "testRight"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueNotEquals() {
    verify(string -> string.rightValueNotEquals("  some string    ", 7, "iNg    "));
    verify(string -> string.rightValueNotEquals("  some string    ", 7, "iNg    ", "testRight_CaseNotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWith() {
    verify(string -> string.startsWith("  some string   s ", "  some"));
    verify(string -> string.startsWith("  some string   s ", "  some", "testStartsWith"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithAny() {
    verify(string -> string.startsWithAny("  some string   s ", new CList<>("A", null, "  some")));
    verify(string -> string.startsWithAny("  some string   s ", new CList<>("A", null, "  some"), "testStartsWithAny"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithIgnoreCase() {
    verify(string -> string.startsWithIgnoreCase("  some string   s ", "  some"));
    verify(string -> string.startsWithIgnoreCase("  some string   s ", "  some", "testStartsWithIgnoreCase"));
    verify(string -> string.startsWithIgnoreCase("  some string   s ", "  Some"));
    verify(string -> string.startsWithIgnoreCase("  some string   s ", "  Some", "testStartsWithIgnoreCase"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithNone() {
    verify(string -> string.startsWithNone("  some string   s ", new CList<>(" some", "     ", " s ")));
    verify(string -> string.startsWithNone("  some string   s ", new CList<>(" some", "     ", " s "), "testStartsWithIgnoreCase_NotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValue() {
    verify(string -> string.stripedEndValue("  some string    ", " ", "  some string"));
    verify(string -> string.stripedEndValue("  some string    ", " ", "  some string", "testStripedEndValue"));
    verify(string -> string.stripedEndValue("  some string    ", null, "  some string"));
    verify(string -> string.stripedEndValue("  some string    ", null, "  some string", "testStripedEndValue"));
    verify(string -> string.stripedEndValue("|some string||||", "|", "|some string"));
    verify(string -> string.stripedEndValue("|some string||||", "|", "|some string", "testStripedEndValue"));
    verify(string -> string.stripedEndValue("|some string||||", null, "|some string||||"));
    verify(string -> string.stripedEndValue("|some string||||", null, "|some string||||", "testStripedEndValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValueNot() {
    verify(string -> string.stripedEndValueNot("  some string    ", " ", " some string"));
    verify(string -> string.stripedEndValueNot("  some string    ", " ", " some string", "testStripedEndValue"));
    verify(string -> string.stripedEndValueNot("  some string    ", null, "  somestring"));
    verify(string -> string.stripedEndValueNot("  some string    ", null, "  somestring", "testStripedEndValue"));
    verify(string -> string.stripedEndValueNot("|some string||||", "|", "|some string|"));
    verify(string -> string.stripedEndValueNot("|some string||||", "|", "|some string|", "testStripedEndValue"));
    verify(string -> string.stripedEndValueNot("|some string||||", null, "|some string|||"));
    verify(string -> string.stripedEndValueNot("|some string||||", null, "|some string|||", "testStripedEndValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValue() {
    verify(string -> string.stripedStartValue("  some string    ", " ", "some string    "));
    verify(string -> string.stripedStartValue("  some string    ", " ", "some string    ", "testStripedStartValue"));
    verify(string -> string.stripedStartValue("  some string    ", null, "some string    "));
    verify(string -> string.stripedStartValue("  some string    ", null, "some string    ", "testStripedStartValue"));
    verify(string -> string.stripedStartValue("|some string||||", "|", "some string||||"));
    verify(string -> string.stripedStartValue("|some string||||", "|", "some string||||", "testStripedStartValue"));
    verify(string -> string.stripedStartValue("|some string||||", null, "|some string||||"));
    verify(string -> string.stripedStartValue("|some string||||", null, "|some string||||", "testStripedStartValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValueNot() {
    verify(string -> string.stripedStartValueNot("  some string    ", " ", "some string   "));
    verify(string -> string.stripedStartValueNot("  some string    ", " ", "some string   ", "testStripedStartValue"));
    verify(string -> string.stripedStartValueNot("  some string    ", null, "some string   "));
    verify(string -> string.stripedStartValueNot("  some string    ", null, "some string   ", "testStripedStartValue"));
    verify(string -> string.stripedStartValueNot("|some string||||", "|", "some string|||"));
    verify(string -> string.stripedStartValueNot("|some string||||", "|", "some string|||", "testStripedStartValue"));
    verify(string -> string.stripedStartValueNot("|some string||||", null, "|some string|||"));
    verify(string -> string.stripedStartValueNot("|some string||||", null, "|some string|||", "testStripedStartValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValue() {
    verify(string -> string.stripedValue("  some string    ", " ", "some string"));
    verify(string -> string.stripedValue("  some string    ", " ", "some string", "testStripedValue"));
    verify(string -> string.stripedValue("  some string    ", null, "some string"));
    verify(string -> string.stripedValue("  some string    ", null, "some string", "testStripedValue"));
    verify(string -> string.stripedValue("|some string||||", "|", "some string"));
    verify(string -> string.stripedValue("|some string||||", "|", "some string", "testStripedValue"));
    verify(string -> string.stripedValue("|some string||||", null, "|some string||||"));
    verify(string -> string.stripedValue("|some string||||", null, "|some string||||", "testStripedValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValueNot() {
    verify(string -> string.stripedValueNot("  some string    ", " ", " some string"));
    verify(string -> string.stripedValueNot("  some string    ", " ", " some string", "testStripedValue"));
    verify(string -> string.stripedValueNot("  some string    ", null, "somestring"));
    verify(string -> string.stripedValueNot("  some string    ", null, "somestring", "testStripedValue"));
    verify(string -> string.stripedValueNot("|some string||||", "|", "some string|"));
    verify(string -> string.stripedValueNot("|some string||||", "|", "some string|", "testStripedValue"));
    verify(string -> string.stripedValueNot("|some string||||", null, "|some string|||"));
    verify(string -> string.stripedValueNot("|some string||||", null, "|some string|||", "testStripedValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterEquals() {
    verify(string -> string.substringAfterEquals("  some string    ", " s", "ome string    "));
    verify(string -> string.substringAfterEquals("  some string    ", " s", "ome string    ", "testSubstringAfter"));
    verify(string -> string.substringAfterEquals("  some string    ", null, ""));
    verify(string -> string.substringAfterEquals("  some string    ", null, "", "testSubstringAfter"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastEquals() {
    verify(string -> string.substringAfterLastEquals("  some string    ", " s", "tring    "));
    verify(string -> string.substringAfterLastEquals("  some string    ", " s", "tring    ", "testSubstringAfterLast"));
    verify(string -> string.substringAfterLastEquals("  some string    ", null, ""));
    verify(string -> string.substringAfterLastEquals("  some string    ", null, "", "testSubstringAfterLast"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastNotEquals() {
    verify(string -> string.substringAfterLastNotEquals("  some string    ", " s", "trinG    "));
    verify(string -> string.substringAfterLastNotEquals("  some string    ", " s", "trinG    ", "testSubstringAfterLast_CaseNotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterNotEquals() {
    verify(string -> string.substringAfterNotEquals("  some string    ", " s", "omE string    "));
    verify(string -> string.substringAfterNotEquals("  some string    ", " s", "omE string    ", "testSubstringAfter_CaseNotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeEquals() {
    verify(string -> string.substringBeforeEquals("  some string    ", " st", "  some"));
    verify(string -> string.substringBeforeEquals("  some string    ", " st", "  some", "testSubstringBefore"));
    verify(string -> string.substringBeforeEquals("  some string    ", null, "  some string    "));
    verify(string -> string.substringBeforeEquals("  some string    ", null, "  some string    ", "testSubstringBefore"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastEquals() {
    verify(string -> string.substringBeforeLastEquals("  some string    ", " s", "  some"));
    verify(string -> string.substringBeforeLastEquals("  some string    ", " s", "  some", "testSubstringBeforeLast"));
    verify(string -> string.substringBeforeLastEquals("  some string    ", null, "  some string    "));
    verify(string -> string.substringBeforeLastEquals("  some string    ", null, "  some string    ", "testSubstringBeforeLast"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastNotEquals() {
    verify(string -> string.substringBeforeLastNotEquals("  some string    ", " s", "  somE"));
    verify(string -> string.substringBeforeLastNotEquals("  some string    ", " s", "  somE", "testSubstringBeforeLast_CaseNotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeNotEquals() {
    verify(string -> string.substringBeforeNotEquals("  some string    ", " st", "  Some"));
    verify(string -> string.substringBeforeNotEquals("  some string    ", " st", "  Some", "testSubstringBefore_CaseNotMatch"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenEquals() {
    verify(string -> string.substringBetweenEquals("  some string    ", "  ", "    ", "some string"));
    verify(string -> string.substringBetweenEquals("  some string    ", "  ", "    ", "some string", "testSubstringBetween"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenNotEquals() {
    verify(string -> string.substringBetweenNotEquals("  some string    ", "  ", "    ", "some String"));
    verify(string -> string.substringBetweenNotEquals("  some string    ", "  ", "    ", "some String", "testSubstringBetween"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringEquals() {
    verify(string -> string.substringEquals("  some string    ", 0, "  some string    "));
    verify(string -> string.substringEquals("  some string    ", 0, "  some string    ", "testSubstring"));
    verify(string -> string.substringEquals("  some string    ", 0, 3, "  s"));
    verify(string -> string.substringEquals("  some string    ", 0, 3, "  s", "testSubstring"));
    verify(string -> string.substringEquals("  some string    ", 2, 4, "so"));
    verify(string -> string.substringEquals("  some string    ", 2, 4, "so", "testSubstring"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringNotEquals() {
    verify(string -> string.substringNotEquals("  some string    ", 0, " some string    "));
    verify(string -> string.substringNotEquals("  some string    ", 0, " some string    ", "testSubstring"));
    verify(string -> string.substringNotEquals("  some string    ", 0, 3, " s"));
    verify(string -> string.substringNotEquals("  some string    ", 0, 3, " s", "testSubstring"));
    verify(string -> string.substringNotEquals("  some string    ", 2, 4, "so "));
    verify(string -> string.substringNotEquals("  some string    ", 2, 4, "so ", "testSubstring"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenContains() {
    verify(string -> string.substringsBetweenContains("  some string   s ", " ", "s", " "));
    verify(string -> string.substringsBetweenContains("  some string   s ", " ", "s", " ", "testSubstringsBetweenContains"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenEquals() {
    verify(string -> string.substringsBetweenEquals("  some string   s ", " ", "s", new CList<>(" ", "", "  ")));
    verify(string -> string.substringsBetweenEquals("  some string   s ", " ", "s", new CList<>(" ", "", "  "), "testSubstringsBetween"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotContains() {
    verify(string -> string.substringsBetweenNotContains("  some string   s ", " ", "s", "x"));
    verify(string -> string.substringsBetweenNotContains("  some string   s ", " ", "s", "x", "testSubstringsBetweenContains"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotEquals() {
    verify(string -> string.substringsBetweenNotEquals("  some string   s ", " ", "s", new CList<>(" ", "S", "  ")));
    verify(string -> string.substringsBetweenNotEquals("  some string   s ", " ", "s", new CList<>(" ", "S", "  "), "testSubstringsBetweenNotEquals"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValue() {
    verify(string -> string.trimmedValue("some string    ", "some string"));
    verify(string -> string.trimmedValue("some string    ", "some string", "testTrimmedValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValueNot() {
    verify(string -> string.trimmedValueNot("some string    ", " some string"));
    verify(string -> string.trimmedValueNot("some string    ", " some string", "testTrimmedValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValue() {
    verify(string -> string.truncatedValue("some string    ", 10, "some strin"));
    verify(string -> string.truncatedValue("some string    ", 10, "some strin", "testTruncatedValue"));
    verify(string -> string.truncatedValue("some string    ", 4, 10, " string   "));
    verify(string -> string.truncatedValue("some string    ", 4, 10, " string   ", "testTruncatedValueWithOffset"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValueNot() {
    verify(string -> string.truncatedValueNot("some string    ", 10, "some string"));
    verify(string -> string.truncatedValueNot("some string    ", 10, "some string", "testTruncatedValueNot"));
    verify(string -> string.truncatedValueNot("some string    ", 4, 10, " string  "));
    verify(string -> string.truncatedValueNot("some string    ", 4, 10, " string  ", "testTruncatedValueNotWithOffset"));
  }

  // Negative Scenarios
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadEquals_Negative() {
    verify(string -> string.centerPadEquals("  some string    ", 10, NULL, "  somestring    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadNotEquals_Negative() {
    verify(string -> string.centerPadNotEquals("  some string    ", 10, NULL, "  some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompare_Negative() {
    verify(string -> string.compare(NULL, "  some string    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompareIgnoreCase_Negative() {
    verify(string -> string.compareIgnoreCase("  some string    ", "  ScOME string    ", 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_Negative() {
    verify(string -> string.contains("  some string    ", "sso"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsIgnoreCase_Negative() {
    verify(string -> string.containsIgnoreCase("  Some string    ", " sco"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWith_Negative() {
    verify(string -> string.endsWith("  some string   s ", "   x "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithAny_Negative() {
    verify(string -> string.endsWithAny("  some string   s ", new CList<>("X", null, " D ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithIgnoreCase_Negative() {
    verify(string -> string.endsWithIgnoreCase("  some string   s ", "   xs "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithNone_Negative() {
    verify(string -> string.endsWithNone("  some string   s ", new CList<>("a", " s ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_Negative() {
    verify(string -> string.equals(NULL, "x"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAny_Negative() {
    verify(string -> string.equalsAny("  some string    ", new CList<>("", "  sxme string    ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAnyIgnoreCase_Negative() {
    verify(string -> string.equalsAnyIgnoreCase("  some STRING    ", new CList<>("", "  SXME string    ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreCase_Negative() {
    verify(string -> string.equalsIgnoreCase("  some string    ", "  SXME string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreWhiteSpaces_Negative() {
    verify(string -> string.equalsIgnoreWhiteSpaces("  some string    ", " s x me s t r ing    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNone_Negative() {
    verify(string -> string.equalsNone("  some string    ", new CList<>("  some string    ", "  sxe String    ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNoneIgnoreCase_Negative() {
    verify(string -> string.equalsNoneIgnoreCase("  some string    ", new CList<>("  some string    ", null)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlpha_Negative() {
    verify(string -> string.isAlpha("aiul@ajksn"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrAlpha_Negative() {
    verify(string -> string.isEmptyOrAlpha("&"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphaSpace_Negative() {
    verify(string -> string.isAlphaSpace(" aiu@l ajk sn"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumeric_Negative() {
    verify(string -> string.isAlphanumeric("blka$jsblas"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumericSpace_Negative() {
    verify(string -> string.isAlphanumericSpace(" ai1ul#@90 ajk sn"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 30;
    verify(string -> string.isAsciiPrintable(String.valueOf(chars)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsBlank_Negative() {
    verify(string -> string.isBlank("asas"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_Negative() {
    verify(string -> string.isEmpty("asas"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlpha_Negative() {
    verify(string -> string.isNotAlpha("aasf"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlpha_Negative() {
    verify(string -> string.isEmptyOrNotAlpha("aiulaj"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphaSpace_Negative() {
    verify(string -> string.isNotAlphaSpace("aiulaj"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumeric_Negative() {
    verify(string -> string.isNotAlphanumeric("aiulaj6265opksn"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlphanumeric_Negative() {
    verify(string -> string.isEmptyOrNotAlphanumeric("aiulaj6265opksn"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumericSpace_Negative() {
    verify(string -> string.isNotAlphanumericSpace("aiulaj6265opksn"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 32;
    verify(string -> string.isNotAsciiPrintable(String.valueOf(chars)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotBlank_Negative() {
    verify(string -> string.isNotBlank(""));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_Negative() {
    verify(string -> string.isNotEmpty(EMPTY));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumeric_Negative() {
    verify(string -> string.isNotNumeric("1"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotNumeric_Negative() {
    verify(string -> string.isEmptyOrNotNumeric("1234567"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumericSpace_Negative() {
    verify(string -> string.isNotNumericSpace(""));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumeric_Negative() {
    verify(string -> string.isNumeric("123a4567"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNumeric_Negative() {
    verify(string -> string.isEmptyOrNumeric("1a234567"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumericSpace_Negative() {
    verify(string -> string.isNumericSpace("23a45678"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadEquals_Negative() {
    verify(string -> string.leftPadEquals("  some string   s ", 30, "", "            some string   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadNotEquals_Negative() {
    verify(string -> string.leftPadNotEquals("  some string   s ", 30, "", "              some string   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueEquals_Negative() {
    verify(string -> string.leftValueEquals("  some string    ", 7, " some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueNotEquals_Negative() {
    verify(string -> string.leftValueNotEquals("  some string    ", 7, "  some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthEquals_Negative() {
    verify(string -> string.lengthEquals("  some string   s ", 7));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthNotEquals_Negative() {
    verify(string -> string.lengthNotEquals("  some string   s ", 18));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueEquals_Negative() {
    verify(string -> string.midValueEquals("  some string    ", 2, 4, "some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueNotEquals_Negative() {
    verify(string -> string.midValueNotEquals("  some string    ", 2, 4, "some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_Negative() {
    verify(string -> string.notContains("  some string    ", "some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsIgnoreCase_Negative() {
    verify(string -> string.notContainsIgnoreCase("  Some string    ", " Some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWith_Negative() {
    verify(string -> string.notEndsWith("  some string   s ", "s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWithIgnoreCase_Negative() {
    verify(string -> string.notEndsWithIgnoreCase("  some string   s ", "   S "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreCase_Negative() {
    verify(string -> string.notEqualsIgnoreCase("  some string    ", "  SOME string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreWhiteSpaces_Negative() {
    verify(string -> string.notEqualsIgnoreWhiteSpaces("  some string    ", "  so me string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWith_Negative() {
    verify(string -> string.notStartsWith("  some string   s ", "  some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWithIgnoreCase_Negative() {
    verify(string -> string.notStartsWithIgnoreCase("  some string   s ", "  some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesEquals_Negative() {
    verify(string -> string.numberOfMatchesEquals("  some string   s ", "s", 2));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesNotEquals_Negative() {
    verify(string -> string.numberOfMatchesNotEquals("  some String   s ", "s", 2));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndEquals_Negative() {
    verify(string -> string.removeEndEquals("  some string   s ", "  some ", "  some string   S "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseEquals_Negative() {
    verify(string -> string.removeEndIgnoreCaseEquals("  some $tring   s ", "TRING   x ", "  some $"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseNotEquals_Negative() {
    verify(string -> string.removeEndIgnoreCaseNotEquals("  some STRING    ", " ", "  some STRING   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndNotEquals_Negative() {
    verify(string -> string.removeEndNotEquals("  some STRING    ", "STRING    ", "  some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEquals_Negative() {
    verify(string -> string.removeEquals("  some String   so ", "so", "  me string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseEquals_Negative() {
    verify(string -> string.removeIgnoreCaseEquals("  some String   so ", "SO", "  me Xtring    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseNotEquals_Negative() {
    verify(string -> string.removeIgnoreCaseNotEquals("  some STRING    ", " ", "someSTRING"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveNotEquals_Negative() {
    verify(string -> string.removeNotEquals("  some STRING    ", "STRING   ", "  some  "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartEquals_Negative() {
    verify(string -> string.removeStartEquals("  some string   s ", "  some ", "  some string   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseEquals_Negative() {
    verify(string -> string.removeStartIgnoreCaseEquals("  some string   s ", "  some ", "some string   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseNotEquals_Negative() {
    verify(string -> string.removeStartIgnoreCaseNotEquals("  some $tring   s ", "  some ", "$tring   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartNotEquals_Negative() {
    verify(string -> string.removeStartNotEquals("  some string   s ", "  some ", "string   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceEquals_Negative() {
    verify(string -> string.replaceEquals("  some String   so ", "so", "XX", "  XXme String   XX"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseEquals_Negative() {
    verify(string -> string.replaceIgnoreCaseEquals("  some String   so ", "SO", "x", "  xme String   x"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseNotEquals_Negative() {
    verify(string -> string.replaceIgnoreCaseNotEquals("  some String   s ", " s", "x", " xomextring  x "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceNotEquals_Negative() {
    verify(string -> string.replaceNotEquals("  some String   s ", " s", "x", " xome String  x "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceEquals_Negative() {
    verify(string -> string.replaceOnceEquals("  some String   so ", "so", "XX", "  some String   so "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseEquals_Negative() {
    verify(string -> string.replaceOnceIgnoreCaseEquals("  some string   s ", "s", "|", "  |ome string   s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseNotEquals_Negative() {
    verify(string -> string.replaceOnceIgnoreCaseNotEquals("  some String   s ", " s", "x", " xome String   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceNotEquals_Negative() {
    verify(string -> string.replaceOnceNotEquals("  some String   s ", " s", "x", " xome String   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseEquals_Negative() {
    verify(string -> string.reverseEquals("  some @#$%^&*.   so ", " os   .*&^%$#@ emos "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseNotEquals_Negative() {
    verify(string -> string.reverseNotEquals("  some string  s ", " s  gnirts emos  "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadEquals_Negative() {
    verify(string -> string.rightPadEquals("  some string    ", 10, "@", "  some string   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadNotEquals_Negative() {
    verify(string -> string.rightPadNotEquals("  some string   s ", 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueEquals_Negative() {
    verify(string -> string.rightValueEquals("  some string    ", 7, "ing   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueNotEquals_Negative() {
    verify(string -> string.rightValueNotEquals("  some string    ", 7, "ing    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWith_Negative() {
    verify(string -> string.startsWith("  some string   s ", " some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithAny_Negative() {
    verify(string -> string.startsWithAny("  some string   s ", new CList<>("A", null, "some")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithIgnoreCase_Negative() {
    verify(string -> string.startsWithIgnoreCase("  some string   s ", "some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithNone_Negative() {
    verify(string -> string.startsWithNone("  some string   s ", new CList<>("  some", "     ", "s ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValue_Negative() {
    verify(string -> string.stripedEndValue("  some string    ", " ", "  some string "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValueNot_Negative() {
    verify(string -> string.stripedEndValueNot("  some string    ", " ", "  some string", "testStripedEndValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValue_Negative() {
    verify(string -> string.stripedStartValue("  some string    ", " ", "some string   ", "testStripedStartValue"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValueNot_Negative() {
    verify(string -> string.stripedStartValueNot("  some string    ", " ", "some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValue_Negative() {
    verify(string -> string.stripedValue("  some string    ", " ", "some string "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValueNot_Negative() {
    verify(string -> string.stripedValueNot("  some string    ", " ", "some string"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals1_Negative() {
    verify(string -> string.substringAfterEquals("  some string    ", " s", "ome string   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals2_Negative() {
    verify(string -> string.substringAfterEquals("  some string    ", null, "s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals1_Negative() {
    verify(string -> string.substringAfterLastEquals("  some string    ", null, "s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals2_Negative() {
    verify(string -> string.substringAfterLastEquals("  some string    ", " s", "tring   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastNotEquals_Negative() {
    verify(string -> string.substringAfterLastNotEquals("  some string    ", " s", "tring    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterNotEquals_Negative() {
    verify(string -> string.substringAfterNotEquals("  some string    ", " s", "ome string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals1_Negative() {
    verify(string -> string.substringBeforeEquals("  some string    ", " st", "  some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals2_Negative() {
    verify(string -> string.substringBeforeEquals("  some string    ", null, "  some string   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals1_Negative() {
    verify(string -> string.substringBeforeLastEquals("  some string    ", " s", "  some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals2_Negative() {
    verify(string -> string.substringBeforeLastEquals("  some string    ", " s", "  some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastNotEquals_Negative() {
    verify(string -> string.substringBeforeLastNotEquals("  some string    ", " s", "  some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeNotEquals_Negative() {
    verify(string -> string.substringBeforeNotEquals("  some string    ", " st", "  some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenEquals_Negative() {
    verify(string -> string.substringBetweenEquals("  some string    ", "  ", "    ", "some sstring"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenNotEquals_Negative() {
    verify(string -> string.substringBetweenNotEquals("  some string    ", "  ", "    ", "some string"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals1_Negative() {
    verify(string -> string.substringEquals("  some string    ", 0, "  some strin    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals2_Negative() {
    verify(string -> string.substringEquals("  some string    ", 0, 3, "  sx"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals3_Negative() {
    verify(string -> string.substringEquals("  some string    ", 2, 4, "sxo"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals1_Negative() {
    verify(string -> string.substringNotEquals("  some string    ", 0, "  some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals2_Negative() {
    verify(string -> string.substringNotEquals("  some string    ", 0, 3, "  s"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals3_Negative() {
    verify(string -> string.substringNotEquals("  some string    ", 2, 4, "so"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenContains_Negative() {
    verify(string -> string.substringsBetweenContains("  some string   s ", " ", "s", "x"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenEquals_Negative() {
    verify(string -> string.substringsBetweenEquals("  some string   s ", " ", "s", new CList<>(" ", "S", "  "), "some string"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotContains_Negative() {
    verify(string -> string.substringsBetweenNotContains("  some string   s ", " ", "s", " "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotEquals_Negative() {
    verify(string -> string.substringsBetweenNotEquals("  some string   s ", " ", "s", new CList<>(" ", "", "  "), "some string"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValue_Negative() {
    verify(string -> string.trimmedValue("some string    ", "some String"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValueNot_Negative() {
    verify(string -> string.trimmedValueNot("some string    ", "some string"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue1_Negative() {
    verify(string -> string.truncatedValue("some string    ", 10, "some sxtrin"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue2_Negative() {
    verify(string -> string.truncatedValue("some string    ", 4, 10, " sxtring   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot1_Negative() {
    verify(string -> string.truncatedValueNot("some string    ", 10, "some strin"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot2_Negative() {
    verify(string -> string.truncatedValueNot("some string    ", 4, 10, " string   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatches() {
    verify(string -> string.matches("some string    ", "[a-z ]+"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_ExpectedNull() {
    verify(string -> string.matches(null, " tring   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_Negative() {
    verify(string -> string.matches("some string    ", "\\d+"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesPattern() {
    verify(string -> string.matches("some string    ", Pattern.compile("[a-z ]+")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_ExpectedNull() {
    verify(string -> string.matches(null, " tring   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_Negative() {
    verify(string -> string.matches("some string    ", Pattern.compile("\\d+")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesAny() {
    verify(string -> string.matchesAny("some string    ", List.of(Pattern.compile("[a-z ]+"), Pattern.compile("\\d")), "testPositive"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesNone() {
    verify(string -> string.matchesNone("some string    ", List.of(Pattern.compile("[^a-z ]+"), Pattern.compile("\\d"))));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatches() {
    verify(string -> string.notMatches("some string    ", "\\d+"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_ExpectedNull() {
    verify(string -> string.notMatches(null, " tring   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_Negative() {
    verify(string -> string.notMatches("some string    ", "[a-z ]+"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatchesPattern() {
    verify(string -> string.notMatches("some string    ", Pattern.compile("\\d+")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_ExpectedNull() {
    verify(string -> string.notMatches(null, " tring   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_Negative() {
    verify(string -> string.notMatches("some string    ", Pattern.compile("[a-z ]+")));
  }

  public abstract void verify(Consumer<CStringVerification> action);
}

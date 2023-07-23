package org.catools.common.tests.waitVerify;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.interfaces.waitVerify.CStringWaitVerify;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.List;
import java.util.regex.Pattern;

@Test(priority = 10)
public class CStringWaitVerifyTest extends CBaseUnitTest {
  private static final String CSTRING1 = "This is the first String with some 1209op31mk2w9@# values.";
  private String NULL = null;
  private String EMPTY = "";

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadEquals() {
    toWaitVerify("  some string    ").verifyCenterPadEquals(10, "@", "  some string    ");
    toWaitVerify("  some string    ").verifyCenterPadEquals(30, "@", "@@@@@@  some string    @@@@@@@");
    toWaitVerify("  some string    ").verifyCenterPadEquals(10, NULL, "  some string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadNotEquals() {
    toWaitVerify("  some string    ").verifyCenterPadNotEquals(10, "@", " some string    ");
    toWaitVerify("  some string    ").verifyCenterPadNotEquals(30, "@", "@@@@@  some string    @@@@@@@");
    toWaitVerify("  some string    ").verifyCenterPadNotEquals(10, NULL, " some string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompare() {
    toWaitVerify("  some string    ").verifyCompare("  some string    ", 0);
    toWaitVerify("  SOME string    ").verifyCompare("  some string    ", -32);
    toWaitVerify(NULL).verifyCompare(null, 0);
    toWaitVerify("  some string    ").verifyCompare("  some String    ", 32);
    toWaitVerify("  some string    ").verifyCompare(null, 1);
    toWaitVerify(NULL).verifyCompare("  some string    ", -1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompareIgnoreCase() {
    toWaitVerify("  some string    ").verifyCompareIgnoreCase("  SOME string    ", 0);
    toWaitVerify("  SOME string    ").verifyCompareIgnoreCase("  some string    ", 0);
    toWaitVerify(NULL).verifyCompareIgnoreCase(null, 0);
    toWaitVerify("  some string    ").verifyCompareIgnoreCase("  some xtring    ", -5);
    toWaitVerify("  some string    ").verifyCompareIgnoreCase(null, 1);
    toWaitVerify(NULL).verifyCompareIgnoreCase("  some string    ", -1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    toWaitVerify("  some string    ").verifyContains("so");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsIgnoreCase() {
    toWaitVerify("  Some string    ").verifyContainsIgnoreCase(" so");
    toWaitVerify("  some $tring    ").verifyContainsIgnoreCase("$TRING");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWith() {
    toWaitVerify("  some string   s ").verifyEndsWith("   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithAny() {
    toWaitVerify("  some string   s ").verifyEndsWithAny(new CList<>("A", null, " s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithIgnoreCase() {
    toWaitVerify("  some string   s ").verifyEndsWithIgnoreCase("   s ");
    toWaitVerify("  some string   s ").verifyEndsWithIgnoreCase("   S ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithNone() {
    toWaitVerify("  some string   s ").verifyEndsWithNone(new CList<>("A", " s  "));
    toWaitVerify("  some string   s ").verifyEndsWithNone(new CList<>("A", " S "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    toWaitVerify("  some string    ").verifyEquals("  some string    ");
    toWaitVerify(NULL).verifyEquals(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAny() {
    toWaitVerify("  some string    ").verifyEqualsAny(new CList<>("", "  some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAnyIgnoreCase() {
    toWaitVerify("  some STRING    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  SOME string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreCase() {
    toWaitVerify("  some string    ").verifyEqualsIgnoreCase("  SOME string    ");
    toWaitVerify(NULL).verifyEqualsIgnoreCase(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreWhiteSpaces() {
    toWaitVerify("  some string    ").verifyEqualsIgnoreWhiteSpaces(" s o me s t r ing    ");
    toWaitVerify("  some string    ").verifyEqualsIgnoreWhiteSpaces("somestring");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNone() {
    toWaitVerify("  some string    ").verifyEqualsNone(new CList<>("", "  some String    "));
    toWaitVerify("  some string    ").verifyEqualsNone(new CList<>("", null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNoneIgnoreCase() {
    toWaitVerify("  some STRING    ").verifyEqualsNoneIgnoreCase(new CList<>("", "  $ome string    "));
    toWaitVerify("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("", null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlpha() {
    toWaitVerify("aiulajksn").verifyIsAlpha(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlpha() {
    toWaitVerify("aiulajksn").verifyIsEmptyOrAlpha(1);
    toWaitVerify("").verifyIsEmptyOrAlpha(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphaSpace() {
    toWaitVerify(" aiul ajk sn").verifyIsAlphaSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumeric() {
    toWaitVerify("aiulaj45872ksn1").verifyIsAlphanumeric(1);
    toWaitVerify("blkajsblas").verifyIsAlphanumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlphanumeric() {
    toWaitVerify("aiulaj6265opksn").verifyIsEmptyOrAlphanumeric(1);
    toWaitVerify("").verifyIsEmptyOrAlphanumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumericSpace() {
    toWaitVerify("ai1ul90jksn").verifyIsAlphanumericSpace(1);
    toWaitVerify(" ai1ul90 ajk sn").verifyIsAlphanumericSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();

    chars[5] = 32;
    toWaitVerify(String.valueOf(chars)).verifyIsAsciiPrintable(1);

    chars[5] = 33;
    toWaitVerify(String.valueOf(chars)).verifyIsAsciiPrintable(1);

    chars[5] = 125;
    toWaitVerify(String.valueOf(chars)).verifyIsAsciiPrintable(1);

    chars[5] = 126;
    toWaitVerify(String.valueOf(chars)).verifyIsAsciiPrintable(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlank() {
    toWaitVerify(NULL).verifyIsBlank(1);
    toWaitVerify(EMPTY).verifyIsBlank(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    toWaitVerify(EMPTY).verifyIsEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlpha() {
    toWaitVerify("123aasf2").verifyIsNotAlpha(1);
    toWaitVerify("").verifyIsNotAlpha(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlpha() {
    toWaitVerify("aiulaj626").verifyIsEmptyOrNotAlpha(1);
    toWaitVerify("").verifyIsEmptyOrNotAlpha(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphaSpace() {
    toWaitVerify("aiulaj626").verifyIsNotAlphaSpace(1);
    toWaitVerify("@ a").verifyIsNotAlphaSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumeric() {
    toWaitVerify("aiulaj626!5opksn").verifyIsNotAlphanumeric(1);
    toWaitVerify("@#.*").verifyIsNotAlphanumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlphanumeric() {
    toWaitVerify("aiulaj626 5opksn").verifyIsEmptyOrNotAlphanumeric(1);
    toWaitVerify("@#.*").verifyIsEmptyOrNotAlphanumeric(1);
    toWaitVerify("").verifyIsEmptyOrNotAlphanumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumericSpace() {
    toWaitVerify("aiulaj626 !5opksn").verifyIsNotAlphanumericSpace(1);
    toWaitVerify("@#.*").verifyIsNotAlphanumericSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 30;
    toWaitVerify(String.valueOf(chars)).verifyIsNotAsciiPrintable(1);

    chars[5] = 31;
    toWaitVerify(String.valueOf(chars)).verifyIsNotAsciiPrintable(1);

    chars[5] = 127;
    toWaitVerify(String.valueOf(chars)).verifyIsNotAsciiPrintable(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotBlank() {
    toWaitVerify(CSTRING1).verifyIsNotBlank(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    toWaitVerify(CSTRING1).verifyIsNotEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumeric() {
    toWaitVerify("a1234567").verifyIsNotNumeric(1);
    toWaitVerify(" ").verifyIsNotNumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotNumeric() {
    toWaitVerify("a123 4567").verifyIsEmptyOrNotNumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumericSpace() {
    toWaitVerify("a123 4567").verifyIsNotNumericSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumeric() {
    toWaitVerify("1234567").verifyIsNumeric();
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNumeric() {
    toWaitVerify("1234567").verifyIsEmptyOrNumeric(1);
    toWaitVerify("").verifyIsEmptyOrNumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumericSpace() {
    toWaitVerify("2345678").verifyIsNumericSpace(1);
    toWaitVerify(" 1254 786 1").verifyIsNumericSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadEquals() {
    toWaitVerify("  some string    ").verifyLeftPadEquals(10, "@", "  some string    ");
    toWaitVerify("  some string    ").verifyLeftPadEquals(30, "@", "@@@@@@@@@@@@@  some string    ");
    toWaitVerify("  some string    ").verifyLeftPadEquals(10, NULL, "  some string    ");
    toWaitVerify("  some string   s ").verifyLeftPadEquals(30, "", "              some string   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadNotEquals() {
    toWaitVerify("  some string    ").verifyLeftPadNotEquals(10, "@", " some string    ");
    toWaitVerify("  some string    ").verifyLeftPadNotEquals(30, "@", "@@@@@@@@@@@@  some string    ");
    toWaitVerify("  some string    ").verifyLeftPadNotEquals(10, NULL, " some string    ");
    toWaitVerify("  some string   s ").verifyLeftPadNotEquals(30, "", "             some string   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueEquals() {
    toWaitVerify("  some string    ").verifyLeftValueEquals(7, "  some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueNotEquals() {
    toWaitVerify("  some string    ").verifyLeftValueNotEquals(7, " some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthEquals() {
    toWaitVerify("  some string   s ").verifyLengthEquals(18);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthNotEquals() {
    toWaitVerify("aasa").verifyLengthNotEquals(0);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueEquals() {
    toWaitVerify("  some string    ").verifyMidValueEquals(2, 4, "some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueNotEquals() {
    toWaitVerify("  some string    ").verifyMidValueNotEquals(2, 4, "some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    toWaitVerify("  some string    ").verifyNotContains("sox");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsIgnoreCase() {
    toWaitVerify("  Some string    ").verifyNotContainsIgnoreCase(" sox");
    toWaitVerify("  some $tring    ").verifyNotContainsIgnoreCase("x$TRING");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWith() {
    toWaitVerify("  some string   s ").verifyNotEndsWith(".* ");
    toWaitVerify("  some string   s ").verifyNotEndsWith("S ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWithIgnoreCase() {
    toWaitVerify("  some string   s ").verifyNotEndsWithIgnoreCase("   $ ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreCase() {
    toWaitVerify("  some string    ").verifyNotEqualsIgnoreCase("  $OME string    ");
    toWaitVerify(NULL).verifyNotEqualsIgnoreCase("");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreWhiteSpaces() {
    toWaitVerify("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  $OME string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWith() {
    toWaitVerify("  some string   s ").verifyNotStartsWith(" some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWithIgnoreCase() {
    toWaitVerify("  some string   s ").verifyNotStartsWithIgnoreCase(" Some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesEquals() {
    toWaitVerify("  some string   s ").verifyNumberOfMatchesEquals("s", 3);
    toWaitVerify("  some String   s ").verifyNumberOfMatchesEquals("s", 2);
    toWaitVerify("  some $tring   s ").verifyNumberOfMatchesEquals("$", 1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesNotEquals() {
    toWaitVerify("  some String   s ").verifyNumberOfMatchesNotEquals("s", 1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndEquals() {
    toWaitVerify("  some string   s ").verifyRemoveEndEquals("  some ", "  some string   s ");
    toWaitVerify("  some string   s ").verifyRemoveEndEquals("some string   s ", "  ");
    toWaitVerify("  some string   s ").verifyRemoveEndEquals("  some string   s ", "");
    toWaitVerify("  some String   s ").verifyRemoveEndEquals(null, "  some String   s ");
    toWaitVerify("  some String   s ").verifyRemoveEndEquals("tring   s ", "  some S");
    toWaitVerify("  some $tring   s ").verifyRemoveEndEquals("tring   s ", "  some $");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseEquals() {
    toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  Some ", "  some string   s ");
    toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseEquals("some String   s ", "  ");
    toWaitVerify("  some string   s ").verifyRemoveEndIgnoreCaseEquals("  sOME string   s ", "");
    toWaitVerify("  some String   s ").verifyRemoveEndIgnoreCaseEquals(null, "  some String   s ");
    toWaitVerify("  some String   s ").verifyRemoveEndIgnoreCaseEquals("tring   S ", "  some S");
    toWaitVerify("  some $tring   s ").verifyRemoveEndIgnoreCaseEquals("TRING   s ", "  some $");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseNotEquals() {
    toWaitVerify("  some STRING    ").verifyRemoveEndIgnoreCaseNotEquals(" ", "  STRING    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndNotEquals() {
    toWaitVerify("  some STRING    ").verifyRemoveEndNotEquals("STRING    ", "  SOME ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEquals() {
    toWaitVerify("  some string   s ").verifyRemoveEquals("s", "  ome tring    ");
    toWaitVerify("  some String   so ").verifyRemoveEquals("so", "  me String    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseEquals() {
    toWaitVerify("  some string   s ").verifyRemoveIgnoreCaseEquals("s", "  ome tring    ");
    toWaitVerify("  some String   so ").verifyRemoveIgnoreCaseEquals("SO", "  me String    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseNotEquals() {
    toWaitVerify("  some STRING    ").verifyRemoveIgnoreCaseNotEquals(" ", "  some STRING ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveNotEquals() {
    toWaitVerify("  some STRING    ").verifyRemoveNotEquals("STRING   ", "  some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartEquals() {
    toWaitVerify("  some string   s ").verifyRemoveStartEquals("  some ", "string   s ");
    toWaitVerify("  some string   s ").verifyRemoveStartEquals("some string   s ", "  some string   s ");
    toWaitVerify("  some string   s ").verifyRemoveStartEquals("  some string   s ", "");
    toWaitVerify("  some String   s ").verifyRemoveStartEquals(null, "  some String   s ");
    toWaitVerify("  some String   s ").verifyRemoveStartEquals("  some S", "tring   s ");
    toWaitVerify("  some $tring   s ").verifyRemoveStartEquals("  some $", "tring   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseEquals() {
    toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "string   s ");
    toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some ", "string   s ");
    toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("Some string   s ", "  some string   s ");
    toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  Some string   s ", "");
    toWaitVerify("  some String   s ").verifyRemoveStartIgnoreCaseEquals(null, "  some String   s ");
    toWaitVerify("  some String   s ").verifyRemoveStartIgnoreCaseEquals("  some s", "tring   s ");
    toWaitVerify("  some $tring   s ").verifyRemoveStartIgnoreCaseEquals("  some $", "tring   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseNotEquals() {
    toWaitVerify("  some $tring   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", " $tring   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartNotEquals() {
    toWaitVerify("  some string   s ").verifyRemoveStartNotEquals("  some ", "String   S ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceEquals() {
    toWaitVerify("  some string   s ").verifyReplaceEquals("s", "", "  ome tring    ");
    toWaitVerify("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   XX ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseEquals() {
    toWaitVerify("  some string   s ").verifyReplaceIgnoreCaseEquals("s", "|", "  |ome |tring   | ");
    toWaitVerify("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseNotEquals() {
    toWaitVerify("  some String   s ").verifyReplaceIgnoreCaseNotEquals(" s", "x", " ome string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceNotEquals() {
    toWaitVerify("  some String   s ").verifyReplaceNotEquals(" s", "x", " ome string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceEquals() {
    toWaitVerify("  some string   s ").verifyReplaceOnceEquals("s", "", "  ome string   s ");
    toWaitVerify("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  XXme String   so ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseEquals() {
    toWaitVerify("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s ");
    toWaitVerify("  some String   so ").verifyReplaceOnceIgnoreCaseEquals("SO", "x", "  xme String   so ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseNotEquals() {
    toWaitVerify("  some String   s ").verifyReplaceOnceIgnoreCaseNotEquals(" s", "x", " ome string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceNotEquals() {
    toWaitVerify("  some String   s ").verifyReplaceOnceNotEquals(" s", "x", " ome string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseEquals() {
    toWaitVerify("  some string   s ").verifyReverseEquals(" s   gnirts emos  ");
    toWaitVerify("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%$#@ emos  ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseNotEquals() {
    toWaitVerify("  some string  s ").verifyReverseNotEquals(" s   gnirts emos  ");
    toWaitVerify("  some @#$%^*.   so ").verifyReverseNotEquals(" os   .*&^%$#@ emos  ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadEquals() {
    toWaitVerify("  some string    ").verifyRightPadEquals(10, "@", "  some string    ");
    toWaitVerify("  some string    ").verifyRightPadEquals(30, "@", "  some string    @@@@@@@@@@@@@");
    toWaitVerify("  some string    ").verifyRightPadEquals(10, NULL, "  some string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadNotEquals() {
    toWaitVerify("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueEquals() {
    toWaitVerify("  some string    ").verifyRightValueEquals(7, "ing    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueNotEquals() {
    toWaitVerify("  some string    ").verifyRightValueNotEquals(7, "iNg    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWith() {
    toWaitVerify("  some string   s ").verifyStartsWith("  some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithAny() {
    toWaitVerify("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "  some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithIgnoreCase() {
    toWaitVerify("  some string   s ").verifyStartsWithIgnoreCase("  some");
    toWaitVerify("  some string   s ").verifyStartsWithIgnoreCase("  Some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithNone() {
    toWaitVerify("  some string   s ").verifyStartsWithNone(new CList<>(" some", "     ", " s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValue() {
    toWaitVerify("  some string    ").verifyStripedEndValue(" ", "  some string");
    toWaitVerify("  some string    ").verifyStripedEndValue(null, "  some string");
    toWaitVerify("|some string||||").verifyStripedEndValue("|", "|some string");
    toWaitVerify("|some string||||").verifyStripedEndValue(null, "|some string||||");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValueNot() {
    toWaitVerify("  some string    ").verifyStripedEndValueNot(" ", " some string");
    toWaitVerify("  some string    ").verifyStripedEndValueNot(null, "  somestring");
    toWaitVerify("|some string||||").verifyStripedEndValueNot("|", "|some string|");
    toWaitVerify("|some string||||").verifyStripedEndValueNot(null, "|some string|||");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValue() {
    toWaitVerify("  some string    ").verifyStripedStartValue(" ", "some string    ");
    toWaitVerify("  some string    ").verifyStripedStartValue(null, "some string    ");
    toWaitVerify("|some string||||").verifyStripedStartValue("|", "some string||||");
    toWaitVerify("|some string||||").verifyStripedStartValue(null, "|some string||||");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValueNot() {
    toWaitVerify("  some string    ").verifyStripedStartValueNot(" ", "some string   ");
    toWaitVerify("  some string    ").verifyStripedStartValueNot(null, "some string   ");
    toWaitVerify("|some string||||").verifyStripedStartValueNot("|", "some string|||");
    toWaitVerify("|some string||||").verifyStripedStartValueNot(null, "|some string|||");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValue() {
    toWaitVerify("  some string    ").verifyStripedValue(" ", "some string");
    toWaitVerify("  some string    ").verifyStripedValue(null, "some string");
    toWaitVerify("|some string||||").verifyStripedValue("|", "some string");
    toWaitVerify("|some string||||").verifyStripedValue(null, "|some string||||");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValueNot() {
    toWaitVerify("  some string    ").verifyStripedValueNot(" ", " some string");
    toWaitVerify("  some string    ").verifyStripedValueNot(null, "somestring");
    toWaitVerify("|some string||||").verifyStripedValueNot("|", "some string|");
    toWaitVerify("|some string||||").verifyStripedValueNot(null, "|some string|||");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterEquals() {
    toWaitVerify("  some string    ").verifySubstringAfterEquals(" s", "ome string    ");
    toWaitVerify("  some string    ").verifySubstringAfterEquals(null, "");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastEquals() {
    toWaitVerify("  some string    ").verifySubstringAfterLastEquals(" s", "tring    ");
    toWaitVerify("  some string    ").verifySubstringAfterLastEquals(null, "");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastNotEquals() {
    toWaitVerify("  some string    ").verifySubstringAfterLastNotEquals(" s", "trinG    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterNotEquals() {
    toWaitVerify("  some string    ").verifySubstringAfterNotEquals(" s", "omE string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeEquals() {
    toWaitVerify("  some string    ").verifySubstringBeforeEquals(" st", "  some");
    toWaitVerify("  some string    ").verifySubstringBeforeEquals(null, "  some string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastEquals() {
    toWaitVerify("  some string    ").verifySubstringBeforeLastEquals(" s", "  some");
    toWaitVerify("  some string    ").verifySubstringBeforeLastEquals(null, "  some string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastNotEquals() {
    toWaitVerify("  some string    ").verifySubstringBeforeLastNotEquals(" s", "  somE");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeNotEquals() {
    toWaitVerify("  some string    ").verifySubstringBeforeNotEquals(" st", "  Some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenEquals() {
    toWaitVerify("  some string    ").verifySubstringBetweenEquals("  ", "    ", "some string");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenNotEquals() {
    toWaitVerify("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "some String");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringEquals() {
    toWaitVerify("  some string    ").verifySubstringEquals(0, "  some string    ");
    toWaitVerify("  some string    ").verifySubstringEquals(0, 3, "  s");
    toWaitVerify("  some string    ").verifySubstringEquals(2, 4, "so");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringNotEquals() {
    toWaitVerify("  some string    ").verifySubstringNotEquals(0, " some string    ");
    toWaitVerify("  some string    ").verifySubstringNotEquals(0, 3, " s");
    toWaitVerify("  some string    ").verifySubstringNotEquals(2, 4, "so ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenContains() {
    toWaitVerify("  some string   s ").verifySubstringsBetweenContains(" ", "s", " ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenEquals() {
    toWaitVerify("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotContains() {
    toWaitVerify("  some string   s ").verifySubstringsBetweenNotContains(" ", "s", "x");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotEquals() {
    toWaitVerify("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "S", "  "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValue() {
    toWaitVerify("some string    ").verifyTrimmedValueEquals("some string");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValueNot() {
    toWaitVerify("some string    ").verifyTrimmedValueNotEquals(" some string");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValue() {
    toWaitVerify("some string    ").verifyTruncatedValueEquals(10, "some strin");
    toWaitVerify("some string    ").verifyTruncatedValueEquals(4, 10, " string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValueNot() {
    toWaitVerify("some string    ").verifyTruncatedValueNotEquals(10, "some string");
    toWaitVerify("some string    ").verifyTruncatedValueNotEquals(4, 10, " string  ");
  }

  // Negative Scenarios
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadEquals_Negative() {
    toWaitVerify("  some string    ").verifyCenterPadEquals(10, NULL, "  somestring    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadNotEquals_Negative() {
    toWaitVerify("  some string    ").verifyCenterPadNotEquals(10, NULL, "  some string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompare_Negative() {
    toWaitVerify(NULL).verifyCompare("  some string    ", 0);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompareIgnoreCase_Negative() {
    toWaitVerify("  some string    ").verifyCompareIgnoreCase("  ScOME string    ", 1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_Negative() {
    toWaitVerify("  some string    ").verifyContains("sso");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsIgnoreCase_Negative() {
    toWaitVerify("  Some string    ").verifyContainsIgnoreCase(" sco");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWith_Negative() {
    toWaitVerify("  some string   s ").verifyEndsWith("   x ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithAny_Negative() {
    toWaitVerify("  some string   s ").verifyEndsWithAny(new CList<>("X", null, " D "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithIgnoreCase_Negative() {
    toWaitVerify("  some string   s ").verifyEndsWithIgnoreCase("   xs ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithNone_Negative() {
    toWaitVerify("  some string   s ").verifyEndsWithNone(new CList<>("a", " s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_Negative() {
    toWaitVerify(NULL).verifyEquals("x");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAny_Negative() {
    toWaitVerify("  some string    ").verifyEqualsAny(new CList<>("", "  sxme string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAnyIgnoreCase_Negative() {
    toWaitVerify("  some STRING    ").verifyEqualsAnyIgnoreCase(new CList<>("", "  SXME string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreCase_Negative() {
    toWaitVerify("  some string    ").verifyEqualsIgnoreCase("  SXME string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreWhiteSpaces_Negative() {
    toWaitVerify("  some string    ").verifyEqualsIgnoreWhiteSpaces(" s x me s t r ing    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNone_Negative() {
    toWaitVerify("  some string    ").verifyEqualsNone(new CList<>("  some string    ", "  sxe String    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNoneIgnoreCase_Negative() {
    toWaitVerify("  some string    ").verifyEqualsNoneIgnoreCase(new CList<>("  some string    ", null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlpha_Negative() {
    toWaitVerify("aiul@ajksn").verifyIsAlpha(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrAlpha_Negative() {
    toWaitVerify("&").verifyIsEmptyOrAlpha(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphaSpace_Negative() {
    toWaitVerify(" aiu@l ajk sn").verifyIsAlphaSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumeric_Negative() {
    toWaitVerify("blka$jsblas").verifyIsAlphanumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumericSpace_Negative() {
    toWaitVerify(" ai1ul#@90 ajk sn").verifyIsAlphanumericSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 30;
    toWaitVerify(String.valueOf(chars)).verifyIsAsciiPrintable(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsBlank_Negative() {
    toWaitVerify("asas").verifyIsBlank(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_Negative() {
    toWaitVerify("asas").verifyIsEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlpha_Negative() {
    toWaitVerify("aasf").verifyIsNotAlpha(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlpha_Negative() {
    toWaitVerify("aiulaj").verifyIsEmptyOrNotAlpha(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphaSpace_Negative() {
    toWaitVerify("aiulaj").verifyIsNotAlphaSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumeric_Negative() {
    toWaitVerify("aiulaj6265opksn").verifyIsNotAlphanumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlphanumeric_Negative() {
    toWaitVerify("aiulaj6265opksn").verifyIsEmptyOrNotAlphanumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumericSpace_Negative() {
    toWaitVerify("aiulaj6265opksn").verifyIsNotAlphanumericSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 32;
    toWaitVerify(String.valueOf(chars)).verifyIsNotAsciiPrintable(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotBlank_Negative() {
    toWaitVerify("").verifyIsNotBlank(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_Negative() {
    toWaitVerify(EMPTY).verifyIsNotEmpty(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumeric_Negative() {
    toWaitVerify("1").verifyIsNotNumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotNumeric_Negative() {
    toWaitVerify("1234567").verifyIsEmptyOrNotNumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumericSpace_Negative() {
    toWaitVerify("").verifyIsNotNumericSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumeric_Negative() {
    toWaitVerify("123a4567").verifyIsNumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNumeric_Negative() {
    toWaitVerify("1a234567").verifyIsEmptyOrNumeric(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumericSpace_Negative() {
    toWaitVerify("23a45678").verifyIsNumericSpace(1);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadEquals_Negative() {
    toWaitVerify("  some string   s ").verifyLeftPadEquals(30, "", "            some string   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadNotEquals_Negative() {
    toWaitVerify("  some string   s ").verifyLeftPadNotEquals(30, "", "              some string   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueEquals_Negative() {
    toWaitVerify("  some string    ").verifyLeftValueEquals(7, " some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueNotEquals_Negative() {
    toWaitVerify("  some string    ").verifyLeftValueNotEquals(7, "  some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthEquals_Negative() {
    toWaitVerify("  some string   s ").verifyLengthEquals(7);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthNotEquals_Negative() {
    toWaitVerify("  some string   s ").verifyLengthNotEquals(18);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueEquals_Negative() {
    toWaitVerify("  some string    ").verifyMidValueEquals(2, 4, "some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueNotEquals_Negative() {
    toWaitVerify("  some string    ").verifyMidValueNotEquals(2, 4, "some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_Negative() {
    toWaitVerify("  some string    ").verifyNotContains("some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsIgnoreCase_Negative() {
    toWaitVerify("  Some string    ").verifyNotContainsIgnoreCase(" Some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWith_Negative() {
    toWaitVerify("  some string   s ").verifyNotEndsWith("s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWithIgnoreCase_Negative() {
    toWaitVerify("  some string   s ").verifyNotEndsWithIgnoreCase("   S ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreCase_Negative() {
    toWaitVerify("  some string    ").verifyNotEqualsIgnoreCase("  SOME string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreWhiteSpaces_Negative() {
    toWaitVerify("  some string    ").verifyNotEqualsIgnoreWhiteSpaces("  so me string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWith_Negative() {
    toWaitVerify("  some string   s ").verifyNotStartsWith("  some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWithIgnoreCase_Negative() {
    toWaitVerify("  some string   s ").verifyNotStartsWithIgnoreCase("  some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesEquals_Negative() {
    toWaitVerify("  some string   s ").verifyNumberOfMatchesEquals("s", 2);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesNotEquals_Negative() {
    toWaitVerify("  some String   s ").verifyNumberOfMatchesNotEquals("s", 2);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndEquals_Negative() {
    toWaitVerify("  some string   s ").verifyRemoveEndEquals("  some ", "  some string   S ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseEquals_Negative() {
    toWaitVerify("  some $tring   s ").verifyRemoveEndIgnoreCaseEquals("TRING   x ", "  some $");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseNotEquals_Negative() {
    toWaitVerify("  some STRING    ").verifyRemoveEndIgnoreCaseNotEquals(" ", "  some STRING   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndNotEquals_Negative() {
    toWaitVerify("  some STRING    ").verifyRemoveEndNotEquals("STRING    ", "  some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEquals_Negative() {
    toWaitVerify("  some String   so ").verifyRemoveEquals("so", "  me string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseEquals_Negative() {
    toWaitVerify("  some String   so ").verifyRemoveIgnoreCaseEquals("SO", "  me Xtring    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseNotEquals_Negative() {
    toWaitVerify("  some STRING    ").verifyRemoveIgnoreCaseNotEquals(" ", "someSTRING");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveNotEquals_Negative() {
    toWaitVerify("  some STRING    ").verifyRemoveNotEquals("STRING   ", "  some  ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartEquals_Negative() {
    toWaitVerify("  some string   s ").verifyRemoveStartEquals("  some ", "  some string   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseEquals_Negative() {
    toWaitVerify("  some string   s ").verifyRemoveStartIgnoreCaseEquals("  some ", "some string   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseNotEquals_Negative() {
    toWaitVerify("  some $tring   s ").verifyRemoveStartIgnoreCaseNotEquals("  some ", "$tring   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartNotEquals_Negative() {
    toWaitVerify("  some string   s ").verifyRemoveStartNotEquals("  some ", "string   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceEquals_Negative() {
    toWaitVerify("  some String   so ").verifyReplaceEquals("so", "XX", "  XXme String   XX");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseEquals_Negative() {
    toWaitVerify("  some String   so ").verifyReplaceIgnoreCaseEquals("SO", "x", "  xme String   x");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseNotEquals_Negative() {
    toWaitVerify("  some String   s ").verifyReplaceIgnoreCaseNotEquals(" s", "x", " xomextring  x ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceNotEquals_Negative() {
    toWaitVerify("  some String   s ").verifyReplaceNotEquals(" s", "x", " xome String  x ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceEquals_Negative() {
    toWaitVerify("  some String   so ").verifyReplaceOnceEquals("so", "XX", "  some String   so ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseEquals_Negative() {
    toWaitVerify("  some string   s ").verifyReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseNotEquals_Negative() {
    toWaitVerify("  some String   s ").verifyReplaceOnceIgnoreCaseNotEquals(" s", "x", " xome String   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceNotEquals_Negative() {
    toWaitVerify("  some String   s ").verifyReplaceOnceNotEquals(" s", "x", " xome String   s ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseEquals_Negative() {
    toWaitVerify("  some @#$%^&*.   so ").verifyReverseEquals(" os   .*&^%$#@ emos ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseNotEquals_Negative() {
    toWaitVerify("  some string  s ").verifyReverseNotEquals(" s  gnirts emos  ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadEquals_Negative() {
    toWaitVerify("  some string    ").verifyRightPadEquals(10, "@", "  some string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadNotEquals_Negative() {
    toWaitVerify("  some string   s ").verifyRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueEquals_Negative() {
    toWaitVerify("  some string    ").verifyRightValueEquals(7, "ing   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueNotEquals_Negative() {
    toWaitVerify("  some string    ").verifyRightValueNotEquals(7, "ing    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWith_Negative() {
    toWaitVerify("  some string   s ").verifyStartsWith(" some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithAny_Negative() {
    toWaitVerify("  some string   s ").verifyStartsWithAny(new CList<>("A", null, "some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithIgnoreCase_Negative() {
    toWaitVerify("  some string   s ").verifyStartsWithIgnoreCase("some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithNone_Negative() {
    toWaitVerify("  some string   s ").verifyStartsWithNone(new CList<>("  some", "     ", "s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValue_Negative() {
    toWaitVerify("  some string    ").verifyStripedEndValue(" ", "  some string ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValueNot_Negative() {
    toWaitVerify("  some string    ").verifyStripedEndValueNot(" ", "  some string");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValue_Negative() {
    toWaitVerify("  some string    ").verifyStripedStartValue(" ", "some string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValueNot_Negative() {
    toWaitVerify("  some string    ").verifyStripedStartValueNot(" ", "some string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValue_Negative() {
    toWaitVerify("  some string    ").verifyStripedValue(" ", "some string ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValueNot_Negative() {
    toWaitVerify("  some string    ").verifyStripedValueNot(" ", "some string");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals1_Negative() {
    toWaitVerify("  some string    ").verifySubstringAfterEquals(" s", "ome string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals2_Negative() {
    toWaitVerify("  some string    ").verifySubstringAfterEquals(null, "s");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals1_Negative() {
    toWaitVerify("  some string    ").verifySubstringAfterLastEquals(null, "s");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals2_Negative() {
    toWaitVerify("  some string    ").verifySubstringAfterLastEquals(" s", "tring   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastNotEquals_Negative() {
    toWaitVerify("  some string    ").verifySubstringAfterLastNotEquals(" s", "tring    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterNotEquals_Negative() {
    toWaitVerify("  some string    ").verifySubstringAfterNotEquals(" s", "ome string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals1_Negative() {
    toWaitVerify("  some string    ").verifySubstringBeforeEquals(" st", "  some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals2_Negative() {
    toWaitVerify("  some string    ").verifySubstringBeforeEquals(null, "  some string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals1_Negative() {
    toWaitVerify("  some string    ").verifySubstringBeforeLastEquals(" s", "  some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals2_Negative() {
    toWaitVerify("  some string    ").verifySubstringBeforeLastEquals(" s", "  some ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastNotEquals_Negative() {
    toWaitVerify("  some string    ").verifySubstringBeforeLastNotEquals(" s", "  some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeNotEquals_Negative() {
    toWaitVerify("  some string    ").verifySubstringBeforeNotEquals(" st", "  some");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenEquals_Negative() {
    toWaitVerify("  some string    ").verifySubstringBetweenEquals("  ", "    ", "some sstring");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenNotEquals_Negative() {
    toWaitVerify("  some string    ").verifySubstringBetweenNotEquals("  ", "    ", "some string");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals1_Negative() {
    toWaitVerify("  some string    ").verifySubstringEquals(0, "  some strin    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals2_Negative() {
    toWaitVerify("  some string    ").verifySubstringEquals(0, 3, "  sx");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals3_Negative() {
    toWaitVerify("  some string    ").verifySubstringEquals(2, 4, "sxo");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals1_Negative() {
    toWaitVerify("  some string    ").verifySubstringNotEquals(0, "  some string    ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals2_Negative() {
    toWaitVerify("  some string    ").verifySubstringNotEquals(0, 3, "  s");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals3_Negative() {
    toWaitVerify("  some string    ").verifySubstringNotEquals(2, 4, "so");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenContains_Negative() {
    toWaitVerify("  some string   s ").verifySubstringsBetweenContains(" ", "s", "x");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenEquals_Negative() {
    toWaitVerify("  some string   s ").verifySubstringsBetweenEquals(" ", "s", new CList<>(" ", "S"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotContains_Negative() {
    toWaitVerify("  some string   s ").verifySubstringsBetweenNotContains(" ", "s", " ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotEquals_Negative() {
    toWaitVerify("  some string   s ").verifySubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "", "  "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValue_Negative() {
    toWaitVerify("some string    ").verifyTrimmedValueEquals("some String");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValueNot_Negative() {
    toWaitVerify("some string    ").verifyTrimmedValueNotEquals("some string");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue1_Negative() {
    toWaitVerify("some string    ").verifyTruncatedValueEquals(10, "some sxtrin");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue2_Negative() {
    toWaitVerify("some string    ").verifyTruncatedValueEquals(4, 10, " sxtring   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot1_Negative() {
    toWaitVerify("some string    ").verifyTruncatedValueNotEquals(10, "some strin");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot2_Negative() {
    toWaitVerify("some string    ").verifyTruncatedValueNotEquals(4, 10, " string   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatches() {
    toWaitVerify("some string    ").verifyMatches("[a-z ]+");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_ExpectedNull() {
    toWaitVerify(null).verifyMatches(" tring   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_Negative() {
    toWaitVerify("some string    ").verifyMatches("\\d+");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesPattern() {
    toWaitVerify("some string    ").verifyMatches(Pattern.compile("[a-z ]+"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_ExpectedNull() {
    toWaitVerify(null).verifyMatches(" tring   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_Negative() {
    toWaitVerify("some string    ").verifyMatches(Pattern.compile("\\d+"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesAny() {
    toWaitVerify("some string    ").verifyMatchesAny(List.of(Pattern.compile("[a-z ]+"), Pattern.compile("\\d")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesAny_ExpectedNull() {
    toWaitVerify("some string    ").verifyMatchesAny(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesAny_Negative() {
    toWaitVerify("some string    ").verifyMatchesAny(List.of(Pattern.compile("[^a-z ]+"), Pattern.compile("\\d")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesNone() {
    toWaitVerify("some string    ").verifyMatchesNone(List.of(Pattern.compile("[^a-z ]+"), Pattern.compile("\\d")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesNone_ExpectedNull() {
    toWaitVerify("some string    ").verifyMatchesNone(null);
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesNone_Negative() {
    toWaitVerify("some string    ").verifyMatchesNone(List.of(Pattern.compile("[a-z ]+"), Pattern.compile("\\d")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatches() {
    toWaitVerify("some string    ").verifyNotMatches("\\d+");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_ExpectedNull() {
    toWaitVerify(null).verifyNotMatches(" tring   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_Negative() {
    toWaitVerify("some string    ").verifyNotMatches("[a-z ]+");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatchesPattern() {
    toWaitVerify("some string    ").verifyNotMatches(Pattern.compile("\\d+"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_ExpectedNull() {
    toWaitVerify(null).verifyNotMatches(" tring   ");
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_Negative() {
    toWaitVerify("some string    ").verifyNotMatches(Pattern.compile("[a-z ]+"));
  }

  private CStringWaitVerify toWaitVerify(String val) {
    return () -> val;
  }
}

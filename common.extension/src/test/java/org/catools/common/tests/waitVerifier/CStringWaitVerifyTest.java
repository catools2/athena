package org.catools.common.tests.waitVerifier;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.interfaces.waitVerifier.CStringWaitVerifier;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.List;
import java.util.regex.Pattern;

@Test(priority = 10)
public class CStringWaitVerifyTest extends CBaseWaitVerifyTest {
  private static final String CSTRING1 = "This is the first String with some 1209op31mk2w9@# values.";
  private String NULL = null;
  private String EMPTY = "";

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyCenterPadEquals(verifier, 10, "@", "  some string    ", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyCenterPadEquals(verifier, 30, "@", "@@@@@@  some string    @@@@@@@"));
    verify(verifier -> toWaitVerifier("  some string    ").verifyCenterPadEquals(verifier, 10, NULL, "  some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCenterPadNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyCenterPadNotEquals(verifier, 10, "@", " some string    ", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyCenterPadNotEquals(verifier, 30, "@", "@@@@@  some string    @@@@@@@"));
    verify(verifier -> toWaitVerifier("  some string    ").verifyCenterPadNotEquals(verifier, 10, NULL, " some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompare() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyCompare(verifier, "  some string    ", 0, 1));
    verify(verifier -> toWaitVerifier("  SOME string    ").verifyCompare(verifier, "  some string    ", -32));
    verify(verifier -> toWaitVerifier(NULL).verifyCompare(verifier, null, 0));
    verify(verifier -> toWaitVerifier("  some string    ").verifyCompare(verifier, "  some String    ", 32));
    verify(verifier -> toWaitVerifier("  some string    ").verifyCompare(verifier, null, 1));
    verify(verifier -> toWaitVerifier(NULL).verifyCompare(verifier, "  some string    ", -1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testCompareIgnoreCase() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyCompareIgnoreCase(verifier, "  SOME string    ", 0, 11));
    verify(verifier -> toWaitVerifier("  SOME string    ").verifyCompareIgnoreCase(verifier, "  some string    ", 0));
    verify(verifier -> toWaitVerifier(NULL).verifyCompareIgnoreCase(verifier, null, 0));
    verify(verifier -> toWaitVerifier("  some string    ").verifyCompareIgnoreCase(verifier, "  some xtring    ", -5));
    verify(verifier -> toWaitVerifier("  some string    ").verifyCompareIgnoreCase(verifier, null, 1));
    verify(verifier -> toWaitVerifier(NULL).verifyCompareIgnoreCase(verifier, "  some string    ", -1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContains() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyContains(verifier, "so", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyContains(verifier, "so"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testContainsIgnoreCase() {
    verify(verifier -> toWaitVerifier("  Some string    ").verifyContainsIgnoreCase(verifier, " so", 1));
    verify(verifier -> toWaitVerifier("  some $tring    ").verifyContainsIgnoreCase(verifier, "$TRING"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWith() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWith(verifier, "   s ", 1));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWith(verifier, "   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithAny() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithAny(verifier, new CList<>("A", null, " s "), 1));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithAny(verifier, new CList<>("A", null, " s ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithIgnoreCase() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithIgnoreCase(verifier, "   s ", 1));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithIgnoreCase(verifier, "   S "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEndsWithNone() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithNone(verifier, new CList<>("A", " s  "), 1));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithNone(verifier, new CList<>("A", " S ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEquals(verifier, "  some string    ", 1));
    verify(verifier -> toWaitVerifier(NULL).verifyEquals(verifier, null));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAny() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAny(verifier, new CList<>("", "  some string    "), 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAny(verifier, new CList<>("", "  some string    ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsAnyIgnoreCase() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyEqualsAnyIgnoreCase(verifier, new CList<>("", "  SOME string    "), 1));
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyEqualsAnyIgnoreCase(verifier, new CList<>("", "  SOME string    ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreCase() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsIgnoreCase(verifier, "  SOME string    "));
    verify(verifier -> toWaitVerifier(NULL).verifyEqualsIgnoreCase(verifier, null, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsIgnoreWhiteSpaces() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsIgnoreWhiteSpaces(verifier, " s o me s t r ing    ", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsIgnoreWhiteSpaces(verifier, "somestring"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNone() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNone(verifier, new CList<>("", "  some String    "), 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNone(verifier, new CList<>("", null)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testEqualsNoneIgnoreCase() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyEqualsNoneIgnoreCase(verifier, new CList<>("", "  $ome string    "), 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNoneIgnoreCase(verifier, new CList<>("", null)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlpha() {
    verify(verifier -> toWaitVerifier("aiulajksn").verifyIsAlpha(verifier));
    verify(verifier -> toWaitVerifier("aiulajksn").verifyIsAlpha(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlpha() {
    verify(verifier -> toWaitVerifier("aiulajksn").verifyIsEmptyOrAlpha(verifier, 1));
    verify(verifier -> toWaitVerifier("").verifyIsEmptyOrAlpha(verifier));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphaSpace() {
    verify(verifier -> toWaitVerifier(" aiul ajk sn").verifyIsAlphaSpace(verifier));
    verify(verifier -> toWaitVerifier(" aiul ajk sn").verifyIsAlphaSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumeric() {
    verify(verifier -> toWaitVerifier("aiulaj45872ksn1").verifyIsAlphanumeric(verifier));
    verify(verifier -> toWaitVerifier("blkajsblas").verifyIsAlphanumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrAlphanumeric() {
    verify(verifier -> toWaitVerifier("aiulaj6265opksn").verifyIsEmptyOrAlphanumeric(verifier));
    verify(verifier -> toWaitVerifier("").verifyIsEmptyOrAlphanumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAlphanumericSpace() {
    verify(verifier -> toWaitVerifier("ai1ul90jksn").verifyIsAlphanumericSpace(verifier));
    verify(verifier -> toWaitVerifier(" ai1ul90 ajk sn").verifyIsAlphanumericSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();

    chars[5] = 32;
    verify(verifier -> toWaitVerifier(String.valueOf(chars)).verifyIsAsciiPrintable(verifier));

    chars[5] = 33;
    verify(verifier -> toWaitVerifier(String.valueOf(chars)).verifyIsAsciiPrintable(verifier, 1));

    chars[5] = 125;
    verify(verifier -> toWaitVerifier(String.valueOf(chars)).verifyIsAsciiPrintable(verifier, 1));

    chars[5] = 126;
    verify(verifier -> toWaitVerifier(String.valueOf(chars)).verifyIsAsciiPrintable(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsBlank() {
    verify(verifier -> toWaitVerifier(NULL).verifyIsBlank(verifier));
    verify(verifier -> toWaitVerifier(EMPTY).verifyIsBlank(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmpty() {
    verify(verifier -> toWaitVerifier(EMPTY).verifyIsEmpty(verifier));
    verify(verifier -> toWaitVerifier(EMPTY).verifyIsEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlpha() {
    verify(verifier -> toWaitVerifier("123aasf2").verifyIsNotAlpha(verifier));
    verify(verifier -> toWaitVerifier("").verifyIsNotAlpha(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlpha() {
    verify(verifier -> toWaitVerifier("aiulaj626").verifyIsEmptyOrNotAlpha(verifier));
    verify(verifier -> toWaitVerifier("").verifyIsEmptyOrNotAlpha(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphaSpace() {
    verify(verifier -> toWaitVerifier("aiulaj626").verifyIsNotAlphaSpace(verifier));
    verify(verifier -> toWaitVerifier("@ a").verifyIsNotAlphaSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumeric() {
    verify(verifier -> toWaitVerifier("aiulaj626!5opksn").verifyIsNotAlphanumeric(verifier));
    verify(verifier -> toWaitVerifier("@#.*").verifyIsNotAlphanumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotAlphanumeric() {
    verify(verifier -> toWaitVerifier("aiulaj626 5opksn").verifyIsEmptyOrNotAlphanumeric(verifier));
    verify(verifier -> toWaitVerifier("@#.*").verifyIsEmptyOrNotAlphanumeric(verifier, 1));
    verify(verifier -> toWaitVerifier("").verifyIsEmptyOrNotAlphanumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAlphanumericSpace() {
    verify(verifier -> toWaitVerifier("aiulaj626 !5opksn").verifyIsNotAlphanumericSpace(verifier));
    verify(verifier -> toWaitVerifier("@#.*").verifyIsNotAlphanumericSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotAsciiPrintable() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 30;
    verify(verifier -> toWaitVerifier(String.valueOf(chars)).verifyIsNotAsciiPrintable(verifier));

    chars[5] = 31;
    verify(verifier -> toWaitVerifier(String.valueOf(chars)).verifyIsNotAsciiPrintable(verifier, 1));

    chars[5] = 127;
    verify(verifier -> toWaitVerifier(String.valueOf(chars)).verifyIsNotAsciiPrintable(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotBlank() {
    verify(verifier -> toWaitVerifier(CSTRING1).verifyIsNotBlank(verifier));
    verify(verifier -> toWaitVerifier(CSTRING1).verifyIsNotBlank(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotEmpty() {
    verify(verifier -> toWaitVerifier(CSTRING1).verifyIsNotEmpty(verifier));
    verify(verifier -> toWaitVerifier(CSTRING1).verifyIsNotEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumeric() {
    verify(verifier -> toWaitVerifier("a1234567").verifyIsNotNumeric(verifier));
    verify(verifier -> toWaitVerifier(" ").verifyIsNotNumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNotNumeric() {
    verify(verifier -> toWaitVerifier("a123 4567").verifyIsEmptyOrNotNumeric(verifier));
    verify(verifier -> toWaitVerifier("a123 4567").verifyIsEmptyOrNotNumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotNumericSpace() {
    verify(verifier -> toWaitVerifier("a123 4567").verifyIsNotNumericSpace(verifier));
    verify(verifier -> toWaitVerifier("a123 4567").verifyIsNotNumericSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumeric() {
    verify(verifier -> toWaitVerifier("1234567").verifyIsNumeric(verifier));
    verify(verifier -> toWaitVerifier("1234567").verifyIsNumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsEmptyOrNumeric() {
    verify(verifier -> toWaitVerifier("1234567").verifyIsEmptyOrNumeric(verifier));
    verify(verifier -> toWaitVerifier("").verifyIsEmptyOrNumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNumericSpace() {
    verify(verifier -> toWaitVerifier("2345678").verifyIsNumericSpace(verifier));
    verify(verifier -> toWaitVerifier(" 1254 786 1").verifyIsNumericSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftPadEquals(verifier, 10, "@", "  some string    ", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftPadEquals(verifier, 30, "@", "@@@@@@@@@@@@@  some string    "));
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftPadEquals(verifier, 10, NULL, "  some string    "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadEquals(verifier, 30, "", "              some string   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftPadNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftPadNotEquals(verifier, 10, "@", " some string    ", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftPadNotEquals(verifier, 30, "@", "@@@@@@@@@@@@  some string    "));
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftPadNotEquals(verifier, 10, NULL, " some string    "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadNotEquals(verifier, 30, "", "             some string   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueEquals(verifier, 7, "  some ", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueEquals(verifier, 7, "  some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLeftValueNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueNotEquals(verifier, 7, " some ", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueNotEquals(verifier, 7, " some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthEquals(verifier, 18, 1));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthEquals(verifier, 18));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testLengthNotEquals() {
    verify(verifier -> toWaitVerifier("aasa").verifyLengthNotEquals(verifier, 0));
    verify(verifier -> toWaitVerifier("aasa").verifyLengthNotEquals(verifier, 0, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueEquals(verifier, 2, 4, "some", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueEquals(verifier, 2, 4, "some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testMidValueNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueNotEquals(verifier, 2, 4, "some ", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueNotEquals(verifier, 2, 4, "some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContains() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyNotContains(verifier, "sox", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyNotContains(verifier, "sox"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotContainsIgnoreCase() {
    verify(verifier -> toWaitVerifier("  Some string    ").verifyNotContainsIgnoreCase(verifier, " sox", 1));
    verify(verifier -> toWaitVerifier("  some $tring    ").verifyNotContainsIgnoreCase(verifier, "x$TRING"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWith() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWith(verifier, ".* ", 1));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWith(verifier, "S "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEndsWithIgnoreCase() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWithIgnoreCase(verifier, "   $ ", 1));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWithIgnoreCase(verifier, "   $ "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreCase() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreCase(verifier, "  $OME string    ", 1));
    verify(verifier -> toWaitVerifier(NULL).verifyNotEqualsIgnoreCase(verifier, ""));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotEqualsIgnoreWhiteSpaces() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(verifier, "  $OME string    ", 1));
    verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(verifier, "  $OME string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWith() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWith(verifier, " some", 1));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWith(verifier, " some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNotStartsWithIgnoreCase() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWithIgnoreCase(verifier, " Some", 1));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWithIgnoreCase(verifier, " Some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNumberOfMatchesEquals(verifier, "s", 3));
    verify(verifier -> toWaitVerifier("  some String   s ").verifyNumberOfMatchesEquals(verifier, "s", 2));
    verify(verifier -> toWaitVerifier("  some $tring   s ").verifyNumberOfMatchesEquals(verifier, "$", 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testNumberOfMatchesNotEquals() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyNumberOfMatchesNotEquals(verifier, "s", 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndEquals(verifier, "  some ", "  some string   s "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndEquals(verifier, "some string   s ", "  "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndEquals(verifier, "  some string   s ", ""));
    verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndEquals(verifier, null, "  some String   s "));
    verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndEquals(verifier, "tring   s ", "  some S"));
    verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveEndEquals(verifier, "tring   s ", "  some $"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "  Some ", "  some string   s "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "some String   s ", "  "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "  sOME string   s ", ""));
    verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndIgnoreCaseEquals(verifier, null, "  some String   s "));
    verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "tring   S ", "  some S"));
    verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "TRING   s ", "  some $"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndIgnoreCaseNotEquals() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyRemoveEndIgnoreCaseNotEquals(verifier, " ", "  STRING    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEndNotEquals() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyRemoveEndNotEquals(verifier, "STRING    ", "  SOME "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEquals(verifier, "s", "  ome tring    "));
    verify(verifier -> toWaitVerifier("  some String   so ").verifyRemoveEquals(verifier, "so", "  me String    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveIgnoreCaseEquals(verifier, "s", "  ome tring    "));
    verify(verifier -> toWaitVerifier("  some String   so ").verifyRemoveIgnoreCaseEquals(verifier, "SO", "  me String    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveIgnoreCaseNotEquals() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyRemoveIgnoreCaseNotEquals(verifier, " ", "  some STRING "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveNotEquals() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyRemoveNotEquals(verifier, "STRING   ", "  some "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartEquals(verifier, "  some ", "string   s "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartEquals(verifier, "some string   s ", "  some string   s "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartEquals(verifier, "  some string   s ", ""));
    verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveStartEquals(verifier, null, "  some String   s "));
    verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveStartEquals(verifier, "  some S", "tring   s "));
    verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveStartEquals(verifier, "  some $", "tring   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  some ", "string   s "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  Some ", "string   s "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "Some string   s ", "  some string   s "));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  Some string   s ", ""));
    verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveStartIgnoreCaseEquals(verifier, null, "  some String   s "));
    verify(verifier -> toWaitVerifier("  some String   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  some s", "tring   s "));
    verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  some $", "tring   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartIgnoreCaseNotEquals() {
    verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveStartIgnoreCaseNotEquals(verifier, "  some ", " $tring   s "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRemoveStartNotEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartNotEquals(verifier, "  some ", "String   S "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceEquals(verifier, "s", "", "  ome tring    "));
    verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceEquals(verifier, "so", "XX", "  XXme String   XX "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceIgnoreCaseEquals(verifier, "s", "|", "  |ome |tring   | "));
    verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceIgnoreCaseEquals(verifier, "SO", "x", "  xme String   x "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceIgnoreCaseNotEquals() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyReplaceIgnoreCaseNotEquals(verifier, " s", "x", " ome string   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceNotEquals() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyReplaceNotEquals(verifier, " s", "x", " ome string   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceEquals(verifier, "s", "", "  ome string   s "));
    verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceEquals(verifier, "so", "XX", "  XXme String   so "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceIgnoreCaseEquals(verifier, "s", "|", "  |ome string   s "));
    verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceIgnoreCaseEquals(verifier, "SO", "x", "  xme String   so "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceIgnoreCaseNotEquals() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyReplaceOnceIgnoreCaseNotEquals(verifier, " s", "x", " ome string   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReplaceOnceNotEquals() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyReplaceOnceNotEquals(verifier, " s", "x", " ome string   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyReverseEquals(verifier, " s   gnirts emos  "));
    verify(verifier -> toWaitVerifier("  some @#$%^&*.   so ").verifyReverseEquals(verifier, " os   .*&^%$#@ emos  "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testReverseNotEquals() {
    verify(verifier -> toWaitVerifier("  some string  s ").verifyReverseNotEquals(verifier, " s   gnirts emos  "));
    verify(verifier -> toWaitVerifier("  some @#$%^*.   so ").verifyReverseNotEquals(verifier, " os   .*&^%$#@ emos  "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyRightPadEquals(verifier, 10, "@", "  some string    "));
    verify(verifier -> toWaitVerifier("  some string    ").verifyRightPadEquals(verifier, 30, "@", "  some string    @@@@@@@@@@@@@"));
    verify(verifier -> toWaitVerifier("  some string    ").verifyRightPadEquals(verifier, 10, NULL, "  some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightPadNotEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadNotEquals(verifier, 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueEquals(verifier, 7, "ing    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testRightValueNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueNotEquals(verifier, 7, "iNg    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWith() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWith(verifier, "  some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithAny() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithAny(verifier, new CList<>("A", null, "  some")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithIgnoreCase() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithIgnoreCase(verifier, "  some"));
    verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithIgnoreCase(verifier, "  Some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStartsWithNone() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithNone(verifier, new CList<>(" some", "     ", " s ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValue() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValue(verifier, " ", "  some string"));
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValue(verifier, null, "  some string"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValue(verifier, "|", "|some string"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValue(verifier, null, "|some string||||"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedEndValueNot() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValueNot(verifier, " ", " some string"));
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValueNot(verifier, null, "  somestring"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValueNot(verifier, "|", "|some string|"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedEndValueNot(verifier, null, "|some string|||"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValue() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValue(verifier, " ", "some string    "));
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValue(verifier, null, "some string    "));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValue(verifier, "|", "some string||||"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValue(verifier, null, "|some string||||"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedStartValueNot() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValueNot(verifier, " ", "some string   "));
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValueNot(verifier, null, "some string   "));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValueNot(verifier, "|", "some string|||"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedStartValueNot(verifier, null, "|some string|||"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValue() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValue(verifier, " ", "some string"));
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValue(verifier, null, "some string"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValue(verifier, "|", "some string"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValue(verifier, null, "|some string||||"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testStripedValueNot() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValueNot(verifier, " ", " some string"));
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValueNot(verifier, null, "somestring"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValueNot(verifier, "|", "some string|"));
    verify(verifier -> toWaitVerifier("|some string||||").verifyStripedValueNot(verifier, null, "|some string|||"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterEquals(verifier, " s", "ome string    "));
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterEquals(verifier, null, ""));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastEquals(verifier, " s", "tring    "));
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastEquals(verifier, null, ""));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterLastNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastNotEquals(verifier, " s", "trinG    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringAfterNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterNotEquals(verifier, " s", "omE string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeEquals(verifier, " st", "  some"));
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeEquals(verifier, null, "  some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastEquals(verifier, " s", "  some"));
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastEquals(verifier, null, "  some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeLastNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastNotEquals(verifier, " s", "  somE"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBeforeNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeNotEquals(verifier, " st", "  Some"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenEquals(verifier, "  ", "    ", "some string"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringBetweenNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenNotEquals(verifier, "  ", "    ", "some String"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, "  some string    "));
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, 3, "  s"));
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 2, 4, "so"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringNotEquals() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, " some string    "));
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, 3, " s"));
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 2, 4, "so "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenContains() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenContains(verifier, " ", "s", " "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, " ", "s", new CList<>(" ", "", "  ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotContains() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotContains(verifier, " ", "s", "x"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testSubstringsBetweenNotEquals() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotEquals(verifier, " ", "s", new CList<>(" ", "S", "  ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValue() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueEquals(verifier, "some string"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTrimmedValueNot() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueNotEquals(verifier, " some string"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValue() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 10, "some strin"));
    verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 4, 10, " string   "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testTruncatedValueNot() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 10, "some string"));
    verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 4, 10, " string  "));
  }

  // Negative Scenarios
  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyCenterPadEquals(verifier, 10, NULL, "  somestring    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCenterPadNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyCenterPadNotEquals(verifier, 10, NULL, "  some string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompare_Negative() {
    verify(verifier -> toWaitVerifier(NULL).verifyCompare(verifier, "  some string    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testCompareIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyCompareIgnoreCase(verifier, "  ScOME string    ", 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContains_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyContains(verifier, "sso"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testContainsIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  Some string    ").verifyContainsIgnoreCase(verifier, " sco"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWith_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWith(verifier, "   x "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithAny_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithAny(verifier, new CList<>("X", null, " D ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithIgnoreCase(verifier, "   xs "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEndsWithNone_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyEndsWithNone(verifier, new CList<>("a", " s ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEquals_Negative() {
    verify(verifier -> toWaitVerifier(NULL).verifyEquals(verifier, "x"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAny_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsAny(verifier, new CList<>("", "  sxme string    ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsAnyIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyEqualsAnyIgnoreCase(verifier, new CList<>("", "  SXME string    ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsIgnoreCase(verifier, "  SXME string    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsIgnoreWhiteSpaces_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsIgnoreWhiteSpaces(verifier, " s x me s t r ing    "));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNone_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNone(verifier, new CList<>("  some string    ", "  sxe String    ")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testEqualsNoneIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyEqualsNoneIgnoreCase(verifier, new CList<>("  some string    ", null)));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlpha_Negative() {
    verify(verifier -> toWaitVerifier("aiul@ajksn").verifyIsAlpha(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrAlpha_Negative() {
    verify(verifier -> toWaitVerifier("&").verifyIsEmptyOrAlpha(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphaSpace_Negative() {
    verify(verifier -> toWaitVerifier(" aiu@l ajk sn").verifyIsAlphaSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumeric_Negative() {
    verify(verifier -> toWaitVerifier("blka$jsblas").verifyIsAlphanumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAlphanumericSpace_Negative() {
    verify(verifier -> toWaitVerifier(" ai1ul#@90 ajk sn").verifyIsAlphanumericSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 30;
    verify(verifier -> toWaitVerifier(String.valueOf(chars)).verifyIsAsciiPrintable(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsBlank_Negative() {
    verify(verifier -> toWaitVerifier("asas").verifyIsBlank(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmpty_Negative() {
    verify(verifier -> toWaitVerifier("asas").verifyIsEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlpha_Negative() {
    verify(verifier -> toWaitVerifier("aasf").verifyIsNotAlpha(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlpha_Negative() {
    verify(verifier -> toWaitVerifier("aiulaj").verifyIsEmptyOrNotAlpha(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphaSpace_Negative() {
    verify(verifier -> toWaitVerifier("aiulaj").verifyIsNotAlphaSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumeric_Negative() {
    verify(verifier -> toWaitVerifier("aiulaj6265opksn").verifyIsNotAlphanumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotAlphanumeric_Negative() {
    verify(verifier -> toWaitVerifier("aiulaj6265opksn").verifyIsEmptyOrNotAlphanumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAlphanumericSpace_Negative() {
    verify(verifier -> toWaitVerifier("aiulaj6265opksn").verifyIsNotAlphanumericSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotAsciiPrintable_Negative() {
    char[] chars = "5rtfghuik".toCharArray();
    chars[5] = 32;
    verify(verifier -> toWaitVerifier(String.valueOf(chars)).verifyIsNotAsciiPrintable(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotBlank_Negative() {
    verify(verifier -> toWaitVerifier("").verifyIsNotBlank(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotEmpty_Negative() {
    verify(verifier -> toWaitVerifier(EMPTY).verifyIsNotEmpty(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumeric_Negative() {
    verify(verifier -> toWaitVerifier("1").verifyIsNotNumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNotNumeric_Negative() {
    verify(verifier -> toWaitVerifier("1234567").verifyIsEmptyOrNotNumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotNumericSpace_Negative() {
    verify(verifier -> toWaitVerifier("").verifyIsNotNumericSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumeric_Negative() {
    verify(verifier -> toWaitVerifier("123a4567").verifyIsNumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsEmptyOrNumeric_Negative() {
    verify(verifier -> toWaitVerifier("1a234567").verifyIsEmptyOrNumeric(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNumericSpace_Negative() {
    verify(verifier -> toWaitVerifier("23a45678").verifyIsNumericSpace(verifier, 1));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadEquals(verifier, 30, "", "            some string   s ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftPadNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyLeftPadNotEquals(verifier, 30, "", "              some string   s ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueEquals(verifier, 7, " some ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLeftValueNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyLeftValueNotEquals(verifier, 7, "  some ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthEquals(verifier, 7, 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testLengthNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyLengthNotEquals(verifier, 18, 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueEquals(verifier, 2, 4, "some ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testMidValueNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyMidValueNotEquals(verifier, 2, 4, "some", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContains_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyNotContains(verifier, "some", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotContainsIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  Some string    ").verifyNotContainsIgnoreCase(verifier, " Some", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWith_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWith(verifier, "s ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEndsWithIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotEndsWithIgnoreCase(verifier, "   S ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreCase(verifier, "  SOME string    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotEqualsIgnoreWhiteSpaces_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyNotEqualsIgnoreWhiteSpaces(verifier, "  so me string    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWith_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWith(verifier, "  some", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNotStartsWithIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNotStartsWithIgnoreCase(verifier, "  some", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyNumberOfMatchesEquals(verifier, "s", 2, 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testNumberOfMatchesNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyNumberOfMatchesNotEquals(verifier, "s", 2, 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveEndEquals(verifier, "  some ", "  some string   S ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveEndIgnoreCaseEquals(verifier, "TRING   x ", "  some $", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndIgnoreCaseNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyRemoveEndIgnoreCaseNotEquals(verifier, " ", "  some STRING   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEndNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyRemoveEndNotEquals(verifier, "STRING    ", "  some ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   so ").verifyRemoveEquals(verifier, "so", "  me string    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   so ").verifyRemoveIgnoreCaseEquals(verifier, "SO", "  me Xtring    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveIgnoreCaseNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyRemoveIgnoreCaseNotEquals(verifier, " ", "someSTRING", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some STRING    ").verifyRemoveNotEquals(verifier, "STRING   ", "  some  ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartEquals(verifier, "  some ", "  some string   s ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartIgnoreCaseEquals(verifier, "  some ", "some string   s ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartIgnoreCaseNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some $tring   s ").verifyRemoveStartIgnoreCaseNotEquals(verifier, "  some ", "$tring   s ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRemoveStartNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRemoveStartNotEquals(verifier, "  some ", "string   s ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceEquals(verifier, "so", "XX", "  XXme String   XX", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceIgnoreCaseEquals(verifier, "SO", "x", "  xme String   x", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceIgnoreCaseNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyReplaceIgnoreCaseNotEquals(verifier, " s", "x", " xomextring  x ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyReplaceNotEquals(verifier, " s", "x", " xome String  x ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   so ").verifyReplaceOnceEquals(verifier, "so", "XX", "  some String   so ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyReplaceOnceIgnoreCaseEquals(verifier, "s", "|", "  |ome string   s", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceIgnoreCaseNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyReplaceOnceIgnoreCaseNotEquals(verifier, " s", "x", " xome String   s ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReplaceOnceNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some String   s ").verifyReplaceOnceNotEquals(verifier, " s", "x", " xome String   s ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some @#$%^&*.   so ").verifyReverseEquals(verifier, " os   .*&^%$#@ emos ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testReverseNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string  s ").verifyReverseNotEquals(verifier, " s  gnirts emos  ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyRightPadEquals(verifier, 10, "@", "  some string   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightPadNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyRightPadNotEquals(verifier, 40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueEquals(verifier, 7, "ing   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testRightValueNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyRightValueNotEquals(verifier, 7, "ing    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWith_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWith(verifier, " some", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithAny_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithAny(verifier, new CList<>("A", null, "some"), 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithIgnoreCase_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithIgnoreCase(verifier, "some", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStartsWithNone_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifyStartsWithNone(verifier, new CList<>("  some", "     ", "s "), 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValue_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValue(verifier, " ", "  some string ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedEndValueNot_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedEndValueNot(verifier, " ", "  some string", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValue_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValue(verifier, " ", "some string   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedStartValueNot_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedStartValueNot(verifier, " ", "some string    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValue_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValue(verifier, " ", "some string ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testStripedValueNot_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifyStripedValueNot(verifier, " ", "some string", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals1_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterEquals(verifier, " s", "ome string   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterEquals2_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterEquals(verifier, null, "s", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals1_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastEquals(verifier, null, "s", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastEquals2_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastEquals(verifier, " s", "tring   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterLastNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterLastNotEquals(verifier, " s", "tring    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringAfterNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringAfterNotEquals(verifier, " s", "ome string    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals1_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeEquals(verifier, " st", "  some ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeEquals2_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeEquals(verifier, null, "  some string   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals1_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastEquals(verifier, " s", "  some ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastEquals2_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastEquals(verifier, " s", "  some ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeLastNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeLastNotEquals(verifier, " s", "  some", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBeforeNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBeforeNotEquals(verifier, " st", "  some", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenEquals(verifier, "  ", "    ", "some sstring", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringBetweenNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringBetweenNotEquals(verifier, "  ", "    ", "some string", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals1_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, "  some strin    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals2_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 0, 3, "  sx", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringEquals3_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringEquals(verifier, 2, 4, "sxo", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals1_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, "  some string    ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals2_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 0, 3, "  s", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringNotEquals3_Negative() {
    verify(verifier -> toWaitVerifier("  some string    ").verifySubstringNotEquals(verifier, 2, 4, "so", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenContains_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenContains(verifier, " ", "s", "x", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenEquals(verifier, " ", "s", new CList<>(" ", "S"), 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotContains_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotContains(verifier, " ", "s", " ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testSubstringsBetweenNotEquals_Negative() {
    verify(verifier -> toWaitVerifier("  some string   s ").verifySubstringsBetweenNotEquals(verifier, " ", "s", new CList<>(" ", "", "  "), 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValue_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueEquals(verifier, "some String", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTrimmedValueNot_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTrimmedValueNotEquals(verifier, "some string", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue1_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 10, "some sxtrin", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValue2_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueEquals(verifier, 4, 10, " sxtring   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot1_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 10, "some strin", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testTruncatedValueNot2_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyTruncatedValueNotEquals(verifier, 4, 10, " string   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatches() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, "[a-z ]+"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_ExpectedNull() {
    verify(verifier -> toWaitVerifier(null).verifyMatches(verifier, " tring   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatches_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, "\\d+", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesPattern() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, Pattern.compile("[a-z ]+")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_ExpectedNull() {
    verify(verifier -> toWaitVerifier(null).verifyMatches(verifier, " tring   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesPattern_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatches(verifier, Pattern.compile("\\d+"), 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesAny() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatchesAny(verifier, List.of(Pattern.compile("[a-z ]+"), Pattern.compile("\\d"))));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesAny_ExpectedNull() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatchesAny(verifier, null, 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesAny_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatchesAny(verifier, List.of(Pattern.compile("[^a-z ]+"), Pattern.compile("\\d")), 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsMatchesNone() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatchesNone(verifier, List.of(Pattern.compile("[^a-z ]+"), Pattern.compile("\\d"))));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesNone_ExpectedNull() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatchesNone(verifier, null, 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsMatchesNone_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyMatchesNone(verifier, List.of(Pattern.compile("[a-z ]+"), Pattern.compile("\\d")), 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatches() {
    verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, "\\d+"));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_ExpectedNull() {
    verify(verifier -> toWaitVerifier(null).verifyNotMatches(verifier, " tring   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatches_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, "[a-z ]+", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class)
  public void testIsNotMatchesPattern() {
    verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, Pattern.compile("\\d+")));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_ExpectedNull() {
    verify(verifier -> toWaitVerifier(null).verifyNotMatches(verifier, " tring   ", 0));
  }

  @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
  public void testIsNotMatchesPattern_Negative() {
    verify(verifier -> toWaitVerifier("some string    ").verifyNotMatches(verifier, Pattern.compile("[a-z ]+"), 0));
  }

  private CStringWaitVerifier toWaitVerifier(String val) {
    return () -> val;
  }
}

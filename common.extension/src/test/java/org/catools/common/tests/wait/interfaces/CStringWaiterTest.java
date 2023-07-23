package org.catools.common.tests.wait.interfaces;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.CVerify;
import org.catools.common.extensions.wait.interfaces.CStringWaiter;
import org.catools.common.tests.CBaseUnitTest;
import org.catools.common.tests.CTestRetryAnalyzer;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

public class CStringWaiterTest extends CBaseUnitTest {

  public static class CenterPadEquals {

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testVerifyEquals() {
      CVerify.Bool.isTrue(
          toWaiter("  some string ")
              .waitCenterPadEquals(40, "", "               some string              "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  SOM@#$%^& o ").waitCenterPadEquals(29, "&%", "&%&%&%&  SOM@#$%^& o &%&%&%&%"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  SOM@#$%^& o ").waitCenterPadEquals(20, "#$%^", "#$%  SOM@#$%^& o #$%"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  SOM@#$%^& o ").waitCenterPadEquals(10, "", "  SOM@#$%^& o "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitCenterPadEquals(40, "", "               some string              "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string ").waitCenterPadEquals(40, "", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string ")
              .waitCenterPadEquals(40, "", "             some string              "),
          "%s#%s",
          getParams());
    }
  }

  public static class CenterPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string ")
              .waitCenterPadNotEquals(40, "", "              some string              "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  SOM@#$%^& o ").waitCenterPadNotEquals(20, "#$%^", "#$%  SOM@$%^& o #$%"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  SOM@#$%^& o ").waitCenterPadNotEquals(10, "", "  SOM@#$%^& o"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitCenterPadNotEquals(40, "", "              some string              "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string ").waitCenterPadNotEquals(40, "", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string ")
              .waitCenterPadNotEquals(40, "", "               some string              "),
          "%s#%s",
          getParams());
    }
  }

  public static class Compare {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitCompare("  some string    ", 0), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  SOME string    ").waitCompare("  some string    ", -32),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(toWaiter(null).waitCompare(null, 0), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitCompare("  some String    ", 32), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitCompare(null, 1), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter(null).waitCompare("  some string    ", -1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitCompare("  some string    ", 0), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitCompare(null, 0), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  SOME string    ").waitCompare("  some string    ", -1), "%s#%s", getParams());
    }
  }

  public static class CompareIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitCompareIgnoreCase("  SOME string    ", 0),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  SOME string    ").waitCompareIgnoreCase("  some string    ", 0),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(toWaiter(null).waitCompareIgnoreCase(null, 0), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitCompareIgnoreCase("  some xtring    ", -5),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitCompareIgnoreCase(null, 1), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter(null).waitCompareIgnoreCase("  some string    ", -1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitCompareIgnoreCase("  SOME string    ", 0), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitCompareIgnoreCase(null, 0), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitCompareIgnoreCase("SOME string    ", 0),
          "%s#%s",
          getParams());
    }
  }

  public static class Contains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitContains("so"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitContains("so"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitContains(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitContains("sox"), "%s#%s", getParams());
    }
  }

  public static class ContainsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  Some string    ").waitContainsIgnoreCase(" so"), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some $tring    ").waitContainsIgnoreCase("$TRING"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitContainsIgnoreCase(" so"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  Some string    ").waitContainsIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  Some string    ").waitContainsIgnoreCase(" sox"), "%s#%s", getParams());
    }
  }

  public static class EndsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWith("   s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitEndsWith("   s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(toWaiter("  some string   s ").waitEndsWith(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWith(" a s "), "%s#%s", getParams());
    }
  }

  public static class EndsWithAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithAny(new CList<>("A", null, " s ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitEndsWithAny(new CList<>("A", null, " s ")), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithAny(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithAny(new CList<>("A", null, " sx ")),
          "%s#%s",
          getParams());
    }
  }

  public static class EndsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithIgnoreCase("   s "), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithIgnoreCase("   S "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitEndsWithIgnoreCase("   S "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithIgnoreCase("   SX "), "%s#%s", getParams());
    }
  }

  public static class VerifyEndsWithNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithNone(new CList<>("A", null, " S ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitEndsWithNone(new CList<>("A", null, " S ")), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithNone(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitEndsWithNone(new CList<>("A", null, " s ")),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEquals("  some string    "), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter(null).waitEquals(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitEquals("  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitEquals(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEquals("  some string "), "%s#%s", getParams());
    }
  }

  public static class VerifyEqualsAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEqualsAny(new CList<>("", "  some string    ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitEqualsAny(new CList<>("", "  some string    ")), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitEqualsAny(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEqualsAny(new CList<>("", "  sometring    ")),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyEqualsAnyIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ")
              .waitEqualsAnyIgnoreCase(new CList<>("", "  some string    ")),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some STRING    ")
              .waitEqualsAnyIgnoreCase(new CList<>("", "  SOME string    ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitEqualsAnyIgnoreCase(new CList<>("", "  some string    ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEqualsAnyIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ")
              .waitEqualsAnyIgnoreCase(new CList<>("", "  somestring    ")),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyEqualsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEqualsIgnoreCase("  SOME string    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(toWaiter(null).waitEqualsIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitEqualsIgnoreCase("  SOME string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEqualsIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEqualsIgnoreCase("  SOME string"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyEqualsIgnoreWhiteSpaces {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  SOME string    ").waitEqualsIgnoreWhiteSpaces("SO ME st ring    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitEqualsIgnoreWhiteSpaces("SO ME st ring    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  SOME string    ").waitEqualsIgnoreWhiteSpaces(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  SOME string    ").waitEqualsIgnoreWhiteSpaces("SME st ring    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyEqualsNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEqualsNone(new CList<>("", "  some", "string    ")),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some STRING    ").waitEqualsNone(new CList<>("", "  SOME string    ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitEqualsNone(new CList<>("", "  some", "string    ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitEqualsNone(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ")
              .waitEqualsNone(new CList<>("", "some", "  some string    ")),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyEqualsNoneIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ")
              .waitEqualsNoneIgnoreCase(new CList<>("", "  some", "string    ")),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some STRING    ")
              .waitEqualsNoneIgnoreCase(new CList<>("", "  $OME string    ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitEqualsNoneIgnoreCase(new CList<>("", "  some", "string    ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitEqualsNoneIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ")
              .waitEqualsNoneIgnoreCase(new CList<>("", "  some", "  some string    ")),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyIsAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("aiulajksn").waitIsAlpha(), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsAlpha(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("aiu1!lajksn").waitIsAlpha(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("aiulajksn").waitIsEmptyOrAlpha(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("").waitIsEmptyOrAlpha(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsEmptyOrAlpha(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("aiu1 lajksn").waitIsEmptyOrAlpha(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsAlphaSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter(" aiu ajk sn").waitIsAlphaSpace(), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsAlphaSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("1 aiu ajk sn").waitIsAlphaSpace(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("aiulaj45872ksn1").waitIsAlphanumeric(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("blkajsblas").waitIsAlphanumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsAlphanumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testInvalidChar() {
      CVerify.Bool.isTrue(toWaiter("aiulaj4\u5872ksn1").waitIsAlphanumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("aiulaj4 5872ksn1").waitIsAlphanumeric(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("aiulaj6265opksn").waitIsEmptyOrAlphanumeric(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("").waitIsEmptyOrAlphanumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsEmptyOrAlphanumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("aiulaj6!265opksn").waitIsEmptyOrAlphanumeric(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsAlphanumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("ai1ul90jksn").waitIsAlphanumericSpace(), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter(" ai1ul90 ajk sn").waitIsAlphanumericSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsAlphanumericSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("aksaskjhas654!").waitIsAlphanumericSpace(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsAsciiPrintable {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 32;
      CVerify.Bool.isTrue(toWaiter(new String(chars)).waitIsAsciiPrintable(), "%s#%s", getParams());
      chars[5] = 33;
      CVerify.Bool.isTrue(toWaiter(new String(chars)).waitIsAsciiPrintable(1), "%s#%s", getParams());
      chars[5] = 125;
      CVerify.Bool.isTrue(toWaiter(new String(chars)).waitIsAsciiPrintable(1), "%s#%s", getParams());
      chars[5] = 126;
      CVerify.Bool.isTrue(toWaiter(new String(chars)).waitIsAsciiPrintable(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsAsciiPrintable(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      CVerify.Bool.isTrue(toWaiter(new String(chars)).waitIsAsciiPrintable(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAsciiPrintable {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      char[] chars = "5rtfghuik".toCharArray();
      chars[5] = 31;
      CVerify.Bool.isTrue(
          toWaiter(new String(chars)).waitIsNotAsciiPrintable(), "%s#%s", getParams());
      chars[5] = 127;
      CVerify.Bool.isTrue(
          toWaiter(new String(chars)).waitIsNotAsciiPrintable(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNotAsciiPrintable(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("asasa").waitIsNotAsciiPrintable(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsBlank {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsBlank(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("").waitIsBlank(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("A").waitIsBlank(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsEmpty {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("").waitIsEmpty(), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsEmpty(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("s").waitIsEmpty(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("aiu1lajks1n").waitIsNotAlpha(), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNotAlpha(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("alskdla").waitIsNotAlpha(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNotAlpha {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("a1").waitIsEmptyOrNotAlpha(), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsEmptyOrNotAlpha(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("abs").waitIsEmptyOrNotAlpha(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlphaSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("1aiul ajk sn").waitIsNotAlphaSpace(), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNotAlphaSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("sdfghjk").waitIsNotAlphaSpace(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("aiulaj4587 2ksn1").waitIsNotAlphanumeric(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("blkajsbla!s").waitIsNotAlphanumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNotAlphanumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("aiulaj45872ksn1").waitIsNotAlphanumeric(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNotAlphanumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("aiulaj6265!opksn").waitIsEmptyOrNotAlphanumeric(), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsEmptyOrNotAlphanumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("aiulaj6265opksn").waitIsEmptyOrNotAlphanumeric(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNotAlphanumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("ai1ul90jks!n").waitIsNotAlphanumericSpace(), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("ai1ul90jks !").waitIsNotAlphanumericSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNotAlphanumericSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("ai1ul9 0jksn").waitIsNotAlphanumericSpace(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNotBlank {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("ai1ul90jks !").waitIsNotBlank(), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNotBlank(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("").waitIsNotBlank(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNotEmpty {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("ai1ul90jks !").waitIsNotEmpty(), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNotEmpty(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("").waitIsNotEmpty(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNotNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("1234567A").waitIsNotNumeric(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("").waitIsNotNumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNotNumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("1234567").waitIsNotNumeric(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNotNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("12345A67").waitIsEmptyOrNotNumeric(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("A").waitIsEmptyOrNotNumeric(1), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("").waitIsEmptyOrNotNumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsEmptyOrNotNumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("1234567").waitIsEmptyOrNotNumeric(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNotNumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("234567!8").waitIsNotNumericSpace(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter(" 1254 7A86 1").waitIsNotNumericSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNotNumericSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("2345678").waitIsNotNumericSpace(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("1234567").waitIsNumeric(3, 7), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("A1234567").waitIsNumeric(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsEmptyOrNumeric {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("1234567").waitIsEmptyOrNumeric(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter("").waitIsEmptyOrNumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsEmptyOrNumeric(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("1234A567").waitIsEmptyOrNumeric(1), "%s#%s", getParams());
    }
  }

  public static class VerifyIsNumericSpace {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("2345678").waitIsNumericSpace(), "%s#%s", getParams());
      CVerify.Bool.isTrue(toWaiter(" 1254 786 1").waitIsNumericSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitIsNumericSpace(1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("2345A678").waitIsNumericSpace(1), "%s#%s", getParams());
    }
  }

  public static class VerifyLeftValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitLeftValueEquals(7, "  some "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitLeftValueEquals(7, "  some "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitLeftValueEquals(7, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitLeftValueEquals(7, "some "), "%s#%s", getParams());
    }
  }

  public static class VerifyLeftValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitLeftValueNotEquals(3, "  some "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitLeftValueNotEquals(3, "  some "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitLeftValueNotEquals(3, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitLeftValueNotEquals(7, "  some "), "%s#%s", getParams());
    }
  }

  public static class VerifyLeftPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitLeftPadEquals(30, "", "              some string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitLeftPadEquals(10, null, "  some string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitLeftPadEquals(40, "x", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitLeftPadEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyLeftPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitLeftPadNotEquals(10, null, "  some string  s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxx  some string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitLeftPadNotEquals(40, "x", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitLeftPadNotEquals(40, "x", "xxxxxxxxxxxxxxxxxxxxxx  some string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyLength {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("  some string   s ").waitLengthEquals(18), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitLengthEquals(18), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("  some string   s ").waitLengthEquals(17), "%s#%s", getParams());
    }
  }

  public static class VerifyLengthNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitLengthNotEquals(17), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitLengthNotEquals(17), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitLengthNotEquals(18), "%s#%s", getParams());
    }
  }

  public static class VerifyMidValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitMidValueEquals(2, 4, "some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitMidValueEquals(2, 4, "some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitMidValueEquals(2, 4, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitMidValueEquals(2, 3, "some"), "%s#%s", getParams());
    }
  }

  public static class VerifyMidValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitMidValueNotEquals(2, 5, "some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitMidValueNotEquals(2, 5, "some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitMidValueNotEquals(2, 5, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitMidValueNotEquals(2, 4, "some"), "%s#%s", getParams());
    }
  }

  public static class VerifyNotContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitNotContains("sO"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNotContains("sO"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitNotContains(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotContains("som"), "%s#%s", getParams());
    }
  }

  public static class VerifyNotContainsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  Some string    ").waitNotContainsIgnoreCase(" sX"), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some $tring    ").waitNotContainsIgnoreCase("STRING"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNotContainsIgnoreCase("STRING"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some $tring    ").waitNotContainsIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some $tring    ").waitNotContainsIgnoreCase("TRING"), "%s#%s", getParams());
    }
  }

  public static class VerifyNotEndsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotEndsWith("   S "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNotEndsWith("   S "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotEndsWith(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotEndsWith("   s "), "%s#%s", getParams());
    }
  }

  public static class VerifyNotEndsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotEndsWithIgnoreCase("   x "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNotEndsWithIgnoreCase("   x "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotEndsWithIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotEndsWithIgnoreCase("tring   S "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotEquals("  some STRING    "), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotEquals("  some String   "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNotEquals("  some STRING    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(toWaiter("  some string    ").waitNotEquals(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotEquals("  some string    "), "%s#%s", getParams());
    }
  }

  public static class VerifyNotEqualsIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotEqualsIgnoreCase("  SOME tring    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(toWaiter(null).waitNotEqualsIgnoreCase(""), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitNotEqualsIgnoreCase("  SOME tring    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotEqualsIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotEqualsIgnoreCase("  some STRING    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyNotEqualsIgnoreWhiteSpaces {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotEqualsIgnoreWhiteSpaces("  SOME string    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitNotEqualsIgnoreWhiteSpaces("  SOME string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotEqualsIgnoreWhiteSpaces(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitNotEqualsIgnoreWhiteSpaces("  some string    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyNotStartsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotStartsWith("  S"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNotStartsWith("  S"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotStartsWith(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotStartsWith("  s"), "%s#%s", getParams());
    }
  }

  public static class VerifyNotStartsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotStartsWithIgnoreCase("A"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNotStartsWithIgnoreCase("A"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotStartsWithIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNotStartsWithIgnoreCase("  "), "%s#%s", getParams());
    }
  }

  public static class VerifyNumberOfMatchesEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNumberOfMatchesEquals("s", 3), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitNumberOfMatchesEquals("s", 2), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some $tring   s ").waitNumberOfMatchesEquals("$", 1), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNumberOfMatchesEquals("s", 3), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNumberOfMatchesEquals(null, 3), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNumberOfMatchesEquals("s", 1), "%s#%s", getParams());
    }
  }

  public static class VerifyNumberOfMatchesNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNumberOfMatchesNotEquals("s", 2),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitNumberOfMatchesNotEquals("s", 1),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some $tring   s ").waitNumberOfMatchesNotEquals("$", 3),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNumberOfMatchesNotEquals("s", 2), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNumberOfMatchesNotEquals(null, 2),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitNumberOfMatchesNotEquals("s", 3),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEquals("s", "  ome tring    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitRemoveEquals("so", "  me String    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveEquals("s", "  ome tring    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEquals("s", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEquals("s", "  ome string    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveEndEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndEquals("  some ", "  some string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndEquals("some string   s ", "  "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndEquals("  some string   s ", ""),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitRemoveEndEquals(null, "  some String   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitRemoveEndEquals("tring   s ", "  some S"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some $tring   s ").waitRemoveEndEquals("tring   s ", "  some $"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveEndEquals("  some ", "  some string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndEquals("  some ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndEquals("  some ", "some string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveEndIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRemoveEndIgnoreCaseEquals("  Some ", "  some string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndIgnoreCaseEquals("some String   s ", "  "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndIgnoreCaseEquals("  sOME string   s ", ""),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitRemoveEndIgnoreCaseEquals(null, "  some String   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitRemoveEndIgnoreCaseEquals("tring   S ", "  some S"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some $tring   s ").waitRemoveEndIgnoreCaseEquals("TRING   s ", "  some $"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveEndIgnoreCaseEquals("  Some ", "  some string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndIgnoreCaseEquals("  Some ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRemoveEndIgnoreCaseEquals("  Some ", "  some string   s"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveEndIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ")
              .waitRemoveEndIgnoreCaseNotEquals(null, "  some string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveEndIgnoreCaseNotEquals("  Some ", "  some String   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndIgnoreCaseNotEquals("  Some ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRemoveEndIgnoreCaseNotEquals("  some ", "  some string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveEndNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndNotEquals("  some ", "ome string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitRemoveEndNotEquals(null, "  some String   S"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitRemoveEndNotEquals("tring   S ", "  some s"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveEndNotEquals("  some ", "ome string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndNotEquals("  some ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveEndNotEquals("  some ", "  some string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveIgnoreCaseEquals("s", "  ome tring    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitRemoveIgnoreCaseEquals("SO", "  me String    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveIgnoreCaseEquals("s", "  ome tring    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveIgnoreCaseEquals("s", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveIgnoreCaseEquals("s", "  ome trng    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveIgnoreCaseNotEquals("s", "  ome Tring    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitRemoveIgnoreCaseNotEquals("SO", "  me String    x"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveIgnoreCaseNotEquals("s", "  ome Tring    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveIgnoreCaseNotEquals("s", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveIgnoreCaseNotEquals("s", "  ome tring    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveNotEquals("s", "  ome Tring    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitRemoveNotEquals(null, "  me String    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveNotEquals("s", "  ome Tring    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveNotEquals("s", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveNotEquals("s", "  ome tring    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveStartEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartEquals("  some ", "string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRemoveStartEquals("some string   s ", "  some string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartEquals("  some string   s ", ""),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitRemoveStartEquals(null, "  some String   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitRemoveStartEquals("  some S", "tring   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some $tring   s ").waitRemoveStartEquals("  some $", "tring   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveStartEquals("  some ", "string   s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartEquals("  some ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartEquals("  some ", "string s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveStartIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartIgnoreCaseEquals("  some ", "string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartIgnoreCaseEquals("  Some ", "string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRemoveStartIgnoreCaseEquals("Some string   s ", "  some string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartIgnoreCaseEquals("  Some string   s ", ""),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ")
              .waitRemoveStartIgnoreCaseEquals(null, "  some String   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   s ").waitRemoveStartIgnoreCaseEquals("  some s", "tring   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some $tring   s ").waitRemoveStartIgnoreCaseEquals("  some $", "tring   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveStartIgnoreCaseEquals("  some ", "string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartIgnoreCaseEquals("  some ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartIgnoreCaseEquals("  some ", "string s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveStartIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRemoveStartIgnoreCaseNotEquals("  some ", "String   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRemoveStartIgnoreCaseNotEquals("  Some ", "string  s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveStartIgnoreCaseNotEquals("  some ", "String   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testRemoveNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartIgnoreCaseNotEquals(null, "string  s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartIgnoreCaseNotEquals("  some ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRemoveStartIgnoreCaseNotEquals("  some ", "string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRemoveStartNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartNotEquals("  some", "string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartNotEquals(null, " Some string   s"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRemoveStartNotEquals("  some", "string   s "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartNotEquals("  some", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRemoveStartNotEquals("  some ", "string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReplaceEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReplaceEquals("s", "", "  ome tring    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceEquals("so", "XX", "  XXme String   XX "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReplaceEquals("so", "XX", "  XXme String   XX "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceEquals("so", "XX", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceEquals("so", "XX", "  XXme String   S "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReplaceIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitReplaceIgnoreCaseEquals("s", "|", "  |ome |tring   | "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ")
              .waitReplaceIgnoreCaseEquals("SO", "x", "  xme String   x "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReplaceIgnoreCaseEquals("SO", "x", "  xme String   x "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceIgnoreCaseEquals("SO", "x", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ")
              .waitReplaceIgnoreCaseEquals("SO", "x", "  some String   x "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReplaceIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitReplaceIgnoreCaseNotEquals("s", "|", "  |ome string   | "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ")
              .waitReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReplaceIgnoreCaseNotEquals("SO", "x", "  xme tring   x "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceIgnoreCaseNotEquals("SO", "x", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitReplaceIgnoreCaseNotEquals("s", "|", "  |ome |tring   | "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReplaceNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReplaceNotEquals("s", "", "  ome String    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceNotEquals("so", "XX", "  XXme XXtring   XX "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReplaceNotEquals("so", "XX", "  XXme XXtring   XX "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceNotEquals("so", "XX", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceNotEquals("so", "XX", "  XXme String   XX "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReplaceOnceEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReplaceOnceEquals("s", "", "  ome string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceOnceEquals("so", "XX", "  XXme String   so "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReplaceOnceEquals("so", "XX", "  XXme String   so "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceOnceEquals("so", "XX", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceOnceEquals("so", "XX", "  Xome String   so "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ")
              .waitReplaceOnceIgnoreCaseEquals("SO", "x", "  xme String   so "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReplaceOnceIgnoreCaseEquals("s", "|", "  |ome string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReplaceOnceIgnoreCaseEquals("s", "|", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitReplaceOnceIgnoreCaseEquals("s", "|", "  |some string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReplaceOnceIgnoreCaseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitReplaceOnceIgnoreCaseNotEquals("s", "|", "  \\|ome string   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ")
              .waitReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReplaceOnceIgnoreCaseNotEquals("SO", "x", "  .*e String   so "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ").waitReplaceOnceIgnoreCaseNotEquals("SO", "x", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitReplaceOnceIgnoreCaseNotEquals("s", "|", "  |ome string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReplaceOnceNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReplaceOnceNotEquals("s", "", "  ome String   s "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some String   so ")
              .waitReplaceOnceNotEquals("so", "XX", "  XXme String   XX "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReplaceOnceNotEquals("s", "", "  ome String   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReplaceOnceNotEquals("s", "", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReplaceOnceNotEquals("s", "", "  ome string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReverseEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReverseEquals(" s   gnirts emos  "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some @#$%^&*.   so ").waitReverseEquals(" os   .*&^%$#@ emos  "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReverseEquals(" os   .*&^%$#@ emos  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some @#$%^&*.   so ").waitReverseEquals(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some @#$%^&*.   so ").waitReverseEquals(" os   .*&^%# emos  "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyReverseNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReverseNotEquals(" s   gnirt emos  "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some @#$%^&*.   so ").waitReverseNotEquals(" os   .*%$#@ emos  "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitReverseNotEquals(" os   .*%$#@ emos  "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some @#$%^&*.   so ").waitReverseNotEquals(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitReverseNotEquals(" s   gnirts emos  "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRightValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitRightValueEquals(7, "ing    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitRightValueEquals(7, "ing    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitRightValueEquals(7, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitRightValueEquals(7, "ing   "), "%s#%s", getParams());
    }
  }

  public static class VerifyRightValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitRightValueNotEquals(6, "ing    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRightValueNotEquals(6, "ing    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitRightValueNotEquals(6, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitRightValueNotEquals(7, "ing    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRightPadEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRightPadEquals(30, "", "  some string   s             "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRightPadEquals(10, null, "  some string   s "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRightPadEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRightPadEquals(40, "x", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRightPadEquals(40, "x", "  some string   s "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyRightPadNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRightPadNotEquals(10, null, "  some string    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxx"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitRightPadNotEquals(40, "x", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitRightPadNotEquals(40, "x", "  some string   s xxxxxxxxxxxxxxxxxxxxxx"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyStartsWith {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWith("  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitStartsWith("  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(toWaiter("  some string   s ").waitStartsWith(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWith("some"), "%s#%s", getParams());
    }
  }

  public static class VerifyStartsWithAny {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithAny(new CList<>("A", null, "  some")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitStartsWithAny(new CList<>("A", null, "  some")), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithAny(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithAny(new CList<>("A", null, "some")),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyStartsWithIgnoreCase {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithIgnoreCase("  some"), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithIgnoreCase("  Some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitStartsWithIgnoreCase("  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithIgnoreCase(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithIgnoreCase(" some"), "%s#%s", getParams());
    }
  }

  public static class VerifyStartsWithNone {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithNone(new CList<>("A", null, "  Some")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitStartsWithNone(new CList<>("A", null, "  Some")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithNone(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitStartsWithNone(new CList<>("A", "  some", " Some")),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyStripedEndValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedEndValue(" ", "  some string"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedEndValue(null, "  some string"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedEndValue("|", "|some string"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedEndValue(null, "|some string||||"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitStripedEndValue("|", "|some string"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedEndValue("|", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedEndValue("|", "|som string"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyStripedEndValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedEndValueNot(" ", "  ome string"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedEndValueNot(null, "  ome string"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedEndValueNot("|", "|som string"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedEndValueNot(null, "|soe string||||"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitStripedEndValueNot("|", "|som string"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedEndValueNot("|", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedEndValueNot("|", "|some string"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyStripedStartValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedStartValue(" ", "some string    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedStartValue(null, "some string    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedStartValue("|", "some string||||"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedStartValue(null, "|some string||||"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitStripedStartValue("|", "some string||||"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedStartValue("|", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedStartValue("|", "some string|"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyStripedStartValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedStartValueNot(" ", "ome string    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedStartValueNot(null, "ome string    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedStartValueNot("|", "ome string||||"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedStartValueNot(null, "|ome string||||"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitStripedStartValueNot(" ", "ome string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedStartValueNot(" ", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedStartValueNot(" ", "some string    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifyStripedValue {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedValue(" ", "some string"), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedValue(null, "some string"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedValue("|", "some string"), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedValue(null, "|some string||||"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitStripedValue("|", "some string"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedValue("|", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedValue("|", "some String"), "%s#%s", getParams());
    }
  }

  public static class VerifyStripedValueNot {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedValueNot(" ", "somestring"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedValueNot(null, "som string"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedValueNot("|", "somestring"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("|some string||||").waitStripedValueNot(null, "|soe string||||"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitStripedValueNot(" ", "somestring"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedValueNot(" ", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitStripedValueNot(" ", "some string"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringEquals(0, "  some string    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringEquals(0, "  some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringEquals(0, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringEquals(0, "  some string"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringEquals_WithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringEquals(0, 3, "  s"), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringEquals(2, 4, "so"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitSubstringEquals(0, 3, "  s"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringEquals(0, 3, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringEquals(0, 3, "  some"), "%s#%s", getParams());
    }
  }

  public static class VerifySubstringAfterEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterEquals(" s", "ome string    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterEquals(null, ""), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringAfterEquals(" s", "ome string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterEquals(" s", null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterEquals(" s", "Some string    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringAfterLastEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterLastEquals(" s", "tring    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterLastEquals(null, ""),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringAfterLastEquals(" s", "tring    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterLastEquals(" s", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterLastEquals(" s", "String    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringAfterLastNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterLastNotEquals(" s", "trng    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterLastNotEquals(null, "something"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringAfterLastNotEquals(" s", "trng    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterLastNotEquals(" s", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterLastNotEquals(" s", "tring    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringAfterNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterNotEquals(" s", "ome string   "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterNotEquals(null, "X"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringAfterNotEquals(" s", "ome string   "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterNotEquals(" s", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringAfterNotEquals(" s", "ome string    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringBeforeEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeEquals(" st", "  some"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeEquals(null, "  some string    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringBeforeEquals(" st", "  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeEquals(" st", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeEquals(" st", "some"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringBeforeLastEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeLastEquals(" s", "  some"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeLastEquals(null, "  some string    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringBeforeLastEquals(" s", "  some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeLastEquals(" s", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeLastEquals(" s", "some"),
          "%s#%s",
          getParams());
    }
  }

  public static class SubstringBeforeLastNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeLastNotEquals(" s", " some"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeLastNotEquals(null, " some string    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringBeforeLastNotEquals("s", " some string    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeLastNotEquals("S ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeLastNotEquals(null, "  some string    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringBeforeNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeNotEquals(" st", " some"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeNotEquals(null, "  some string   "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringBeforeNotEquals(" st", " some"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeNotEquals(" st", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBeforeNotEquals(" st", "  some"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringBetweenEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBetweenEquals("  ", "    ", "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringBetweenEquals("  ", "    ", "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBetweenEquals(null, "    ", "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBetweenEquals("  ", null, "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBetweenEquals("  ", "    ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBetweenEquals("  ", "    ", "sme string"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringBetweenNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBetweenNotEquals("  ", "    ", "sme string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringBetweenNotEquals("  ", "    ", "sme string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBetweenNotEquals("  ", "    ", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringBetweenNotEquals("  ", "    ", "some string"),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringNotEquals(0, " some string    "),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringNotEquals(2, "ome string    "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringNotEquals(0, " some string    "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringNotEquals(0, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringNotEquals(0, "  some string    "),
          "%s#%s",
          getParams());
    }
  }

  public static class VerifySubstringNotEquals_WithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringNotEquals(0, 3, " s"), "%s#%s", getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringNotEquals(2, 4, "sox"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitSubstringNotEquals(0, 3, " s"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringNotEquals(0, 3, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string    ").waitSubstringNotEquals(0, 3, "  s"), "%s#%s", getParams());
    }
  }

  public static class SubstringsBetweenEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenEquals(null, "s", new CList<>(" ", "", "  ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenEquals(" ", null, new CList<>(" ", "", "  ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenEquals(" ", "s", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegativeOnSize() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenEquals(" ", "s", new CList<>(" ", "")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenEquals(" ", "s", new CList<>(" ", "", " ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative2() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenEquals(" ", "s", new CList<>(" ", "", "  ", " ")),
          "%s#%s",
          getParams());
    }
  }

  public static class SubstringsBetweenContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenContains(" ", "s", " "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringsBetweenContains(" ", "s", "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenContains(null, "s", "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenContains(" ", null, "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenContains(" ", "s", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenContains(" ", "s", "some string"),
          "%s#%s",
          getParams());
    }
  }

  public static class SubstringsBetweenNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "  ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testOpenNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenNotEquals(null, "s", new CList<>(" ", "  ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testCloseNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenNotEquals(" ", null, new CList<>(" ", "  ")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenNotEquals(" ", "s", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenNotEquals(" ", "s", new CList<>(" ", "", "  ")),
          "%s#%s",
          getParams());
    }
  }

  public static class SubstringsBetweenNotContains {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenNotContains(" ", "s", "some string"),
          "%s#%s",
          getParams());
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ")
              .waitSubstringsBetweenNotContains("some ", "ing", "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitSubstringsBetweenNotContains("some ", "ing", "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testOpenNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenNotContains(null, "s", "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testCloseNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenNotContains(" ", null, "some string"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenNotContains("some ", "ing", null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("  some string   s ").waitSubstringsBetweenNotContains("some ", "ing", "str"),
          "%s#%s",
          getParams());
    }
  }

  public static class TrimmedValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTrimmedValueEquals("some string"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitTrimmedValueEquals("some string"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTrimmedValueEquals(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTrimmedValueEquals("some st$ng"), "%s#%s", getParams());
    }
  }

  public static class TrimmedValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTrimmedValueNotEquals("some strin"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitTrimmedValueNotEquals("some strin"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTrimmedValueNotEquals(null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTrimmedValueNotEquals("some string"),
          "%s#%s",
          getParams());
    }
  }

  public static class TruncatedValueEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueEquals(10, "some strin"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitTruncatedValueEquals(4, " string   "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueEquals(4, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueEquals(5, " string   "),
          "%s#%s",
          getParams());
    }
  }

  public static class TruncatedValueEqualsWithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueEquals(4, 10, " string   "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitTruncatedValueEquals(4, 10, " string   "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueEquals(4, 10, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueEquals(5, 10, " string   "),
          "%s#%s",
          getParams());
    }
  }

  public static class TruncatedValueNotEquals {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueNotEquals(10, "ome strin"),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitTruncatedValueNotEquals(10, "ome strin"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueNotEquals(10, null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueNotEquals(5, "some "),
          "%s#%s",
          getParams());
    }
  }

  public static class TruncatedValueNotEqualsWithEnd {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueNotEquals(4, 10, " tring   "),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitTruncatedValueNotEquals(4, 10, " tring   "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueNotEquals(4, 10, null),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitTruncatedValueNotEquals(4, 10, " string   "),
          "%s#%s",
          getParams());
    }
  }

  public static class IsMatches {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(toWaiter("some string    ").waitMatches("[a-z ]+"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitMatches(" tring   "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitMatches((String) null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(toWaiter("some string    ").waitMatches("\\d+"), "%s#%s", getParams());
    }
  }

  public static class IsMatchesPattern {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitMatches(Pattern.compile("[a-z ]+")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitMatches(Pattern.compile("[a-z ]+")), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitMatches((Pattern) null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitMatches(Pattern.compile("^[A-Z ]+$")),
          "%s#%s",
          getParams());
    }
  }

  public static class IsNotMatches {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitNotMatches("^[A-Z ]+$"), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(toWaiter(null).waitNotMatches(" tring   "), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitNotMatches((String) null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitNotMatches("[a-z ]+"), "%s#%s", getParams());
    }
  }

  public static class IsNotMatchesPattern {
    @Test(retryAnalyzer = CTestRetryAnalyzer.class)
    public void testPositive() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitNotMatches(Pattern.compile("^[A-Z ]+$")),
          "%s#%s",
          getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testActualNull() {
      CVerify.Bool.isTrue(
          toWaiter(null).waitNotMatches(Pattern.compile("[a-z ]+")), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testExpectedNull() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitNotMatches((Pattern) null), "%s#%s", getParams());
    }

    @Test(retryAnalyzer = CTestRetryAnalyzer.class, expectedExceptions = AssertionError.class)
    public void testNegative() {
      CVerify.Bool.isTrue(
          toWaiter("some string    ").waitNotMatches(Pattern.compile("[a-z ]+")),
          "%s#%s",
          getParams());
    }
  }

  private static CStringWaiter toWaiter(String s) {
    return () -> s;
  }
}

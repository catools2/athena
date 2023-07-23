package org.catools.common.extensions.verify;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CList;
import org.catools.common.configs.CAnsiConfigs;
import org.catools.common.date.CDate;
import org.catools.common.utils.CAnsiUtil;
import org.catools.common.utils.CSleeper;
import org.catools.common.utils.CStringUtil;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static org.catools.common.text.CStringDiffConfigs.getDiffEditCost;
import static org.catools.common.text.match.CStringDiff.coloredDiff;
import static org.catools.common.text.match.CStringDiff.prettyDiff;

@Slf4j
public class CVerificationInfo<A, B> {
  private final Supplier<A> actualSupplier;
  private final Supplier<B> expectedSupplier;
  private final String message;
  private final boolean printDiff;
  private final int waitInSeconds;
  private final int intervalInMilliSeconds;
  private final BiFunction<A, B, Boolean> verifyMethod;
  private final BiConsumer<A, B> onFail;

  public CVerificationInfo(
      Supplier<A> actualSupplier,
      Supplier<B> expectedSupplier,
      String message,
      boolean printDiff,
      BiFunction<A, B, Boolean> verifyMethod) {
    this(actualSupplier, expectedSupplier, message, printDiff, verifyMethod, null);
  }

  public CVerificationInfo(
      Supplier<A> actualSupplier,
      Supplier<B> expectedSupplier,
      String message,
      boolean printDiff,
      BiFunction<A, B, Boolean> verifyMethod,
      BiConsumer<A, B> onFail) {
    this(actualSupplier, expectedSupplier, message, printDiff, -1, -1, verifyMethod, onFail);
  }

  public CVerificationInfo(
      Supplier<A> actualSupplier,
      Supplier<B> expectedSupplier,
      String message,
      boolean printDiff,
      int waitInSeconds,
      int intervalInMilliSeconds,
      BiFunction<A, B, Boolean> verifyMethod) {
    this(
        actualSupplier,
        expectedSupplier,
        message,
        printDiff,
        waitInSeconds,
        intervalInMilliSeconds,
        verifyMethod,
        null);
  }

  public CVerificationInfo(
      Supplier<A> actualSupplier,
      Supplier<B> expectedSupplier,
      String message,
      boolean printDiff,
      int waitInSeconds,
      int intervalInMilliSeconds,
      BiFunction<A, B, Boolean> verifyMethod,
      BiConsumer<A, B> onFail) {
    this.actualSupplier = actualSupplier;
    this.expectedSupplier = expectedSupplier;
    this.message = message;
    this.printDiff = printDiff;
    this.verifyMethod = verifyMethod;
    this.waitInSeconds = waitInSeconds;
    this.intervalInMilliSeconds = intervalInMilliSeconds;
    this.onFail = onFail;
  }

  public boolean test(StringBuilder verificationMessages) {
    CVerificationResult result = computeResult();
    String message = getMessage(result, result.computedResult);
    verificationMessages.append(message).append(System.lineSeparator());
    return result.computedResult;
  }

  private CVerificationResult<?> computeResult() {
    // If waitInSeconds is not -1
    // then it means we should retry in case if the verification result is false for defined seconds
    // and interval.
    if (waitInSeconds != -1) {
      return computeResultWithWait();
    }
    CVerificationResult<?> result = null;
    try {
      A actual = actualSupplier.get();
      B expected = expectedSupplier.get();
      result = new CVerificationResult<>(actual, expected, verifyMethod.apply(actual, expected));
    } finally {
      if (result == null || !result.computedResult) {
        applyOnFail();
      }
    }
    return result;
  }

  private CVerificationResult<?> computeResultWithWait() {
    Throwable lastException = null;
    CVerificationResult<?> result;
    A actual = null;
    B expected = null;

    CDate deadLine = new CDate().addSeconds(waitInSeconds);
    // A little ugly code for the sake of debugging and branch readability
    while (true) {
      try {
        actual = actualSupplier.get();
        expected = expectedSupplier.get();
        result = new CVerificationResult<>(actual, expected, verifyMethod.apply(actual, expected));
        if (result.computedResult) {
          return result;
        }
        CSleeper.sleepTight(intervalInMilliSeconds);
      } catch (Throwable t) {
        lastException = t;
      }
      if (deadLine.before(CDate.now())) {
        break;
      }
    }

    applyOnFail();

    if (lastException != null) {
      if (lastException instanceof RuntimeException) {
        throw (RuntimeException) lastException;
      }
      throw new AssertionError("Verification Failed!", lastException);
    }

    return new CVerificationResult<>(actual, expected, false);
  }

  private void applyOnFail() {
    if (onFail != null) {
      try {
        onFail.accept(actualSupplier.get(), expectedSupplier.get());
      } catch (Throwable t) {
        log.error("Verification Failed.", t);
      }
    }
  }

  private String getMessage(CVerificationResult<?> result, boolean passed) {
    String expectedText = getString(result.expected);
    String actualText = getString(result.actual);

    boolean consoleSupportAnsi = CAnsiConfigs.isPrintInColorAvailable();
    if (passed) {
      StringBuilder output = new StringBuilder("PASS ::> ");
      output.append(message.trim());
      output.append(CStringUtil.format(" Exp: '%s', Act: '%s'", expectedText, actualText));
      return consoleSupportAnsi ? CAnsiUtil.toGreen(output.toString()) : output.toString();
    } else {
      StringBuilder output =
          new StringBuilder(consoleSupportAnsi ? CAnsiUtil.toRed("FAIL ::> ") : "FAIL ::> ");
      output.append(message.trim());
      if (printDiff) {
        String diff =
            consoleSupportAnsi
                ? coloredDiff(expectedText, actualText, getDiffEditCost())
                : prettyDiff(expectedText, actualText, getDiffEditCost());
        output.append(
            CStringUtil.format(
                "\\n" + "Diff: '%s',\\n" + "Exp: '%s',\\n" + "Act: '%s'",
                diff, expectedText, actualText));
      } else {
        output.append(CStringUtil.format(" Exp: '%s', Act: '%s'", expectedText, actualText));
      }
      return output.toString();
    }
  }

  private String getString(Object obj) {
    if (obj == null) {
      return "<NULL>";
    }
    return obj.getClass().isArray() ? new CList<>((String[]) obj).toString() : (obj + "");
  }

  record CVerificationResult<O>(O actual, O expected, boolean computedResult) { }
}

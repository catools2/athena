package org.catools.common.extensions.verify.interfaces;

import org.catools.common.extensions.states.interfaces.CBaseState;
import org.catools.common.extensions.verify.CVerificationInfo;
import org.catools.common.extensions.verify.CVerificationQueue;
import org.catools.common.extensions.wait.interfaces.CBaseWaiter;
import org.catools.common.utils.CStringUtil;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CBaseVerifier is an interface to hold shared method between all verifier classes.
 */
//
public interface CBaseVerify<O, S extends CBaseState<O>> extends CBaseWaiter<O> {

  S _toState(O o);

  default <A, B> void _verify(
      Function<O, A> actualProvider,
      Supplier<B> expectedSupplier,
      BiFunction<A, B, Boolean> verifyMethod,
      final String message,
      final Object... params) {
    _verify(getVerificationQueue(), actualProvider, expectedSupplier, verifyMethod, message, params);
  }

  default <A, B, V extends CVerificationQueue> void _verify(
      V verifier,
      Function<O, A> actualProvider,
      Supplier<B> expectedSupplier,
      BiFunction<A, B, Boolean> verifyMethod,
      final String message,
      final Object... params) {
    if (withWaiter()) {
      verifier.queue(
          new CVerificationInfo<>(
              () -> actualProvider.apply(_get()),
              expectedSupplier,
              CStringUtil.format(message, params),
              printDiff(),
              getDefaultWaitInSeconds(),
              getDefaultWaitIntervalInMilliSeconds(),
              verifyMethod));
    } else {
      verifier.queue(
          new CVerificationInfo<>(
              () -> actualProvider.apply(_get()),
              expectedSupplier,
              CStringUtil.format(message, params),
              printDiff(),
              verifyMethod));
    }
  }


  default <B> void _verify(
      O actual,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final String message,
      final Object... params) {
    _verify(getVerificationQueue(), actual, expected, verifyMethod, message, params);
  }

  default <B, V extends CVerificationQueue> void _verify(
      V verifier,
      O actual,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final String message,
      final Object... params) {
    if (withWaiter()) {
      verifier.queue(
          new CVerificationInfo<>(
              () -> actual,
              () -> expected,
              CStringUtil.format(message, params),
              printDiff(),
              getDefaultWaitInSeconds(),
              getDefaultWaitIntervalInMilliSeconds(),
              verifyMethod));
    } else {
      verifier.queue(
          new CVerificationInfo<>(
              () -> actual,
              () -> expected,
              CStringUtil.format(message, params),
              printDiff(),
              verifyMethod));
    }
  }

  default <B> void _verify(
      O actual,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(getVerificationQueue(), actual, expected, verifyMethod, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  default <B, V extends CVerificationQueue> void _verify(
      V verifier,
      O actual,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    verifier.queue(
        new CVerificationInfo<>(
            () -> actual,
            () -> expected,
            CStringUtil.format(message, params),
            printDiff(),
            waitInSeconds,
            intervalInMilliSeconds,
            verifyMethod));
  }

  default <B> void _verify(
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final String message,
      final Object... params) {
    _verify(getVerificationQueue(), expected, verifyMethod, message, params);
  }

  default <B, V extends CVerificationQueue> void _verify(
      V verifier,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final String message,
      final Object... params) {
    if (withWaiter()) {
      verifier.queue(
          new CVerificationInfo<>(
              this::_get,
              () -> expected,
              CStringUtil.format(message, params),
              printDiff(),
              getDefaultWaitInSeconds(),
              getDefaultWaitIntervalInMilliSeconds(),
              verifyMethod));
    } else {
      verifier.queue(
          new CVerificationInfo<>(
              this::_get,
              () -> expected,
              CStringUtil.format(message, params),
              printDiff(),
              verifyMethod));
    }
  }

  default <B> void _verify(
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      BiConsumer<O, B> onFail,
      final String message,
      final Object... params) {
    _verify(getVerificationQueue(), expected, verifyMethod, onFail, message, params);
  }

  default <B, V extends CVerificationQueue> void _verify(
      V verifier,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      BiConsumer<O, B> onFail,
      final String message,
      final Object... params) {
    if (withWaiter()) {
      verifier.queue(
          new CVerificationInfo<>(
              this::_get,
              () -> expected,
              CStringUtil.format(message, params),
              printDiff(),
              getDefaultWaitInSeconds(),
              getDefaultWaitIntervalInMilliSeconds(),
              verifyMethod,
              onFail));
    } else {
      verifier.queue(
          new CVerificationInfo<>(
              this::_get,
              () -> expected,
              CStringUtil.format(message, params),
              printDiff(),
              verifyMethod,
              onFail));
    }
  }

  default <B> void _verify(
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    _verify(getVerificationQueue(), expected, verifyMethod, waitInSeconds, message, params);
  }

  default <B, V extends CVerificationQueue> void _verify(
      V verifier,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    _verify(
        verifier,
        expected,
        verifyMethod,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  default <B> void _verify(
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      BiConsumer<O, B> onFail,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    _verify(getVerificationQueue(), expected, verifyMethod, onFail, waitInSeconds, message, params);
  }

  default <B, V extends CVerificationQueue> void _verify(
      V verifier,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      BiConsumer<O, B> onFail,
      final int waitInSeconds,
      final String message,
      final Object... params) {
    _verify(
        verifier,
        expected,
        verifyMethod,
        onFail,
        waitInSeconds,
        getDefaultWaitIntervalInMilliSeconds(),
        message,
        params);
  }

  default <B> void _verify(
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(getVerificationQueue(), expected, verifyMethod, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  default <B, V extends CVerificationQueue> void _verify(
      V verifier,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    verifier.queue(
        new CVerificationInfo<>(
            this::_get,
            () -> expected,
            CStringUtil.format(message, params),
            printDiff(),
            waitInSeconds,
            intervalInMilliSeconds,
            verifyMethod));
  }

  default <B> void _verify(
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      BiConsumer<O, B> onFail,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    _verify(getVerificationQueue(), expected, verifyMethod, onFail, waitInSeconds, intervalInMilliSeconds, message, params);
  }

  default <B, V extends CVerificationQueue> void _verify(
      V verifier,
      B expected,
      BiFunction<O, B, Boolean> verifyMethod,
      BiConsumer<O, B> onFail,
      final int waitInSeconds,
      final int intervalInMilliSeconds,
      final String message,
      final Object... params) {
    verifier.queue(
        new CVerificationInfo<>(
            this::_get,
            () -> expected,
            CStringUtil.format(message, params),
            printDiff(),
            waitInSeconds,
            intervalInMilliSeconds,
            verifyMethod,
            onFail));
  }

  default boolean withWaiter() {
    return false;
  }

  default boolean printDiff() {
    return false;
  }

  default CVerificationQueue getVerificationQueue() {
    return new CVerificationQueue() {
    };
  }
}

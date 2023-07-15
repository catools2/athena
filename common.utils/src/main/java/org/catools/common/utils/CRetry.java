package org.catools.common.utils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * It happens that we need to retry an action many times depend on some criteria. To have the retry
 * logic concentrated and avoid potential dead loop or make the code noisy we move the logic here.
 */
public class CRetry {

  /**
   * Retry the function get if the predicate {@code retryIf} returns true. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m          function to be called
   * @param retryIf    predicate to be test
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param <R>        type of returned object
   * @return result of {@code m} method get or null if all retry failed
   */
  public static <R> R retryIf(
      Function<Integer, R> m, Predicate<R> retryIf, int retryCount, int interval) {
    return retryIf(m, retryIf, retryCount, interval, null, true);
  }

  /**
   * Retry the function get if the predicate {@code retryIf} returns true. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m          function to be called
   * @param retryIf    predicate to be test
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param orElse     supplier to generate alternative result if retryIf result was always true,
   *                   returns null if the supplier is null
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIf(
      Function<Integer, R> m,
      Predicate<R> retryIf,
      int retryCount,
      int interval,
      Supplier<R> orElse) {
    return retryIf(m, retryIf, retryCount, interval, orElse, true);
  }

  /**
   * Retry the function get if the predicate {@code retryIf} returns true. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m                  function to be called
   * @param retryIf            predicate to be test
   * @param retryCount         maximum number of retry
   * @param interval           interval between retries in milliseconds
   * @param orElse             supplier to generate alternative result if retryIf result was always true,
   *                           returns null if the supplier is null
   * @param <R>                type of returned object
   * @param throwLastException whether we should throw exception which has been throws during
   *                           invocation at the end or not
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIf(
      Function<Integer, R> m,
      Predicate<R> retryIf,
      int retryCount,
      int interval,
      @Nullable Supplier<R> orElse,
      boolean throwLastException) {
    Throwable ex = null;
    int counter = 0;
    do {
      try {
        R r = m.apply(counter);
        if (retryIf == null || !retryIf.test(r)) {
          return r;
        }
      } catch (Throwable e) {
        ex = e;
      }
      counter++;
      CSleeper.sleepTight(interval);
    } while (retryCount-- > 0);

    if (throwLastException && ex != null) {
      if (ex instanceof RuntimeException) {
        throw (RuntimeException) ex;
      }
      throw new RuntimeException(ex);
    }

    return orElse == null ? null : orElse.get();
  }

  /**
   * Retry the function if the predicate {@code retryIfNot} returns false. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m          function to be called
   * @param retryIfNot predicate to be test
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param <R>        type of returned object
   * @return result of {@code m} method get or null if all retry failed
   */
  public static <R> R retryIfNot(
      Function<Integer, R> m, Predicate<R> retryIfNot, int retryCount, int interval) {
    return retryIfNot(m, retryIfNot, retryCount, interval, null, true);
  }

  /**
   * Retry the function if the predicate {@code retryIfNot} returns false. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m          function to be called
   * @param retryIfNot predicate to be test
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param orElse     supplier to generate alternative result if retryIf result was always true,
   *                   returns null if the supplier is null
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfNot(
      Function<Integer, R> m,
      Predicate<R> retryIfNot,
      int retryCount,
      int interval,
      Supplier<R> orElse) {
    return retryIfNot(m, retryIfNot, retryCount, interval, orElse, true);
  }

  /**
   * Retry the function if the predicate {@code retryIfNot} returns false. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m                  function to be called
   * @param retryIfNot         predicate to be test
   * @param retryCount         maximum number of retry
   * @param interval           interval between retries in milliseconds
   * @param orElse             supplier to generate alternative result if retryIf result was always true,
   *                           returns null if the supplier is null
   * @param <R>                type of returned object
   * @param throwLastException whether we should throw exception which has been throws during
   *                           invocation at the end or not
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfNot(
      Function<Integer, R> m,
      Predicate<R> retryIfNot,
      int retryCount,
      int interval,
      @Nullable Supplier<R> orElse,
      boolean throwLastException) {
    Throwable ex = null;
    int counter = 0;
    do {
      try {
        R r = m.apply(counter);
        if (retryIfNot == null || retryIfNot.test(r)) {
          return r;
        }
      } catch (Throwable e) {
        ex = e;
      }
      counter++;
      CSleeper.sleepTight(interval);
    } while (retryCount-- > 0);

    if (throwLastException && ex != null) {
      if (ex instanceof RuntimeException) {
        throw (RuntimeException) ex;
      }
      throw new RuntimeException(ex);
    }

    return orElse == null ? null : orElse.get();
  }

  /**
   * Retry the function get if the result of get is Boolean.false or null. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfFalse(Function<Integer, R> m, int retryCount, int interval) {
    return retryIfFalse(m, retryCount, interval, null, true);
  }

  /**
   * Retry the function get if the result of get is Boolean.false or null. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param orElse     supplier to generate alternative result if retryIf result was always true,
   *                   returns null if the supplier is null
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfFalse(
      Function<Integer, R> m, int retryCount, int interval, Supplier<R> orElse) {
    return retryIfFalse(m, retryCount, interval, orElse, true);
  }

  /**
   * Retry the function get if the result of get is Boolean.false or null. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m                  function to be called
   * @param retryCount         maximum number of retry
   * @param interval           interval between retries in milliseconds
   * @param orElse             supplier to generate alternative result if retryIf result was always true,
   *                           returns null if the supplier is null
   * @param <R>                type of returned object
   * @param throwLastException whether we should throw exception which has been throws during get at
   *                           the end
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfFalse(
      Function<Integer, R> m,
      int retryCount,
      int interval,
      Supplier<R> orElse,
      boolean throwLastException) {
    return retryIf(
        m,
        t -> t == null || ((t instanceof Boolean) && !((Boolean) t)),
        retryCount,
        interval,
        orElse,
        throwLastException);
  }

  /**
   * Retry the function get if the result of get is Boolean.true. Please note that we throw runtime
   * exception which wrap the original exception so we do not have to add throwable to all method
   * deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param <R>        type of returned object
   * @return result of {@code m} method get or null if all retry failed
   */
  public static <R> R retryIfTrue(Function<Integer, R> m, int retryCount, int interval) {
    return retryIfTrue(m, retryCount, interval, null, true);
  }

  /**
   * Retry the function get if the result of get is Boolean.true. Please note that we throw runtime
   * exception which wrap the original exception so we do not have to add throwable to all method
   * deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param orElse     supplier to generate alternative result if retryIf result was always true,
   *                   returns null if the supplier is null
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfTrue(
      Function<Integer, R> m, int retryCount, int interval, Supplier<R> orElse) {
    return retryIfTrue(m, retryCount, interval, orElse, true);
  }

  /**
   * Retry the function get if the result of get is Boolean.true. Please note that we throw runtime
   * exception which wrap the original exception so we do not have to add throwable to all method
   * deceleration.
   *
   * @param m                  function to be called
   * @param retryCount         maximum number of retry
   * @param interval           interval between retries in milliseconds
   * @param orElse             supplier to generate alternative result if retryIf result was always true,
   *                           returns null if the supplier is null
   * @param <R>                type of returned object
   * @param throwLastException whether we should throw exception which has been throws during get at
   *                           the end
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfTrue(
      Function<Integer, R> m,
      int retryCount,
      int interval,
      Supplier<R> orElse,
      boolean throwLastException) {
    return retryIf(
        m,
        t -> (t instanceof Boolean) && ((Boolean) t),
        retryCount,
        interval,
        orElse,
        throwLastException);
  }

  /**
   * Retry the function get if the result of get is an empty collection. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param <R>        type of returned object
   * @return result of {@code m} method get or null if all retry failed
   */
  public static <R extends Collection> R retryIfEmpty(
      Function<Integer, R> m, int retryCount, int interval) {
    return retryIfEmpty(m, retryCount, interval, null, true);
  }

  /**
   * Retry the function get if the result of get is an empty collection. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param orElse     supplier to generate alternative result if retryIf result was always true,
   *                   returns null if the supplier is null
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R extends Collection> R retryIfEmpty(
      Function<Integer, R> m, int retryCount, int interval, Supplier<R> orElse) {
    return retryIfEmpty(m, retryCount, interval, orElse, true);
  }

  /**
   * Retry the function get if the result of get is an empty collection. Please note that we throw
   * runtime exception which wrap the original exception so we do not have to add throwable to all
   * method deceleration.
   *
   * @param m                  function to be called
   * @param retryCount         maximum number of retry
   * @param interval           interval between retries in milliseconds
   * @param orElse             supplier to generate alternative result if retryIf result was always true,
   *                           returns null if the supplier is null
   * @param <R>                type of returned object
   * @param throwLastException whether we should throw exception which has been throws during get at
   *                           the end
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R extends Collection> R retryIfEmpty(
      Function<Integer, R> m,
      int retryCount,
      int interval,
      Supplier<R> orElse,
      boolean throwLastException) {
    return retryIf(
        m, t -> t == null || t.isEmpty(), retryCount, interval, orElse, throwLastException);
  }

  /**
   * Retry the function get if the result of get is not an empty collection. Please note that we
   * throw runtime exception which wrap the original exception so we do not have to add throwable to
   * all method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param <R>        type of returned object
   * @return result of {@code m} method get or null if all retry failed
   */
  public static <R extends Collection> R retryIfNotEmpty(
      Function<Integer, R> m, int retryCount, int interval) {
    return retryIfNotEmpty(m, retryCount, interval, null, true);
  }

  /**
   * Retry the function get if the result of get is not an empty collection. Please note that we
   * throw runtime exception which wrap the original exception so we do not have to add throwable to
   * all method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param orElse     supplier to generate alternative result if retryIf result was always true,
   *                   returns null if the supplier is null
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R extends Collection> R retryIfNotEmpty(
      Function<Integer, R> m, int retryCount, int interval, Supplier<R> orElse) {
    return retryIfNotEmpty(m, retryCount, interval, orElse, true);
  }

  /**
   * Retry the function get if the result of get is not an empty collection. Please note that we
   * throw runtime exception which wrap the original exception so we do not have to add throwable to
   * all method deceleration.
   *
   * @param m                  function to be called
   * @param retryCount         maximum number of retry
   * @param interval           interval between retries in milliseconds
   * @param orElse             supplier to generate alternative result if retryIf result was always true,
   *                           returns null if the supplier is null
   * @param <R>                type of returned object
   * @param throwLastException whether we should throw exception which has been throws during get at
   *                           the end
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R extends Collection> R retryIfNotEmpty(
      Function<Integer, R> m,
      int retryCount,
      int interval,
      Supplier<R> orElse,
      boolean throwLastException) {
    return retryIf(
        m, t -> t != null && !t.isEmpty(), retryCount, interval, orElse, throwLastException);
  }

  /**
   * Retry the function get if the result of get is null. Please note that we throw runtime
   * exception which wrap the original exception so we do not have to add throwable to all method
   * deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param <R>        type of returned object
   * @return result of {@code m} method get or null if all retry failed
   */
  public static <R> R retryIfNull(Function<Integer, R> m, int retryCount, int interval) {
    return retryIfNull(m, retryCount, interval, null, true);
  }

  /**
   * Retry the function get if the result of get is null. Please note that we throw runtime
   * exception which wrap the original exception so we do not have to add throwable to all method
   * deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param orElse     supplier to generate alternative result if retryIf result was always true,
   *                   returns null if the supplier is null
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfNull(
      Function<Integer, R> m, int retryCount, int interval, Supplier<R> orElse) {
    return retryIfNull(m, retryCount, interval, orElse, true);
  }

  /**
   * Retry the function get if the result of get is null. Please note that we throw runtime
   * exception which wrap the original exception so we do not have to add throwable to all method
   * deceleration.
   *
   * @param m                  function to be called
   * @param retryCount         maximum number of retry
   * @param interval           interval between retries in milliseconds
   * @param orElse             supplier to generate alternative result if retryIf result was always true,
   *                           returns null if the supplier is null
   * @param <R>                type of returned object
   * @param throwLastException whether we should throw exception which has been throws during get at
   *                           the end
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfNull(
      Function<Integer, R> m,
      int retryCount,
      int interval,
      Supplier<R> orElse,
      boolean throwLastException) {
    return retryIf(m, t -> t == null, retryCount, interval, orElse, throwLastException);
  }

  /**
   * Retry the function get if the result of get is not an null collection. Please note that we
   * throw runtime exception which wrap the original exception so we do not have to add throwable to
   * all method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param <R>        type of returned object
   * @return result of {@code m} method get or null if all retry failed
   */
  public static <R> R retryIfNotNull(Function<Integer, R> m, int retryCount, int interval) {
    return retryIfNotNull(m, retryCount, interval, null, true);
  }

  /**
   * Retry the function get if the result of get is not an null collection. Please note that we
   * throw runtime exception which wrap the original exception so we do not have to add throwable to
   * all method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param orElse     supplier to generate alternative result if retryIf result was always true,
   *                   returns null if the supplier is null
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfNotNull(
      Function<Integer, R> m, int retryCount, int interval, Supplier<R> orElse) {
    return retryIfNotNull(m, retryCount, interval, orElse, true);
  }

  /**
   * Retry the function get if the result of get is not an null collection. Please note that we
   * throw runtime exception which wrap the original exception so we do not have to add throwable to
   * all method deceleration.
   *
   * @param m                  function to be called
   * @param retryCount         maximum number of retry
   * @param interval           interval between retries in milliseconds
   * @param orElse             supplier to generate alternative result if retryIf result was always true,
   *                           returns null if the supplier is null
   * @param <R>                type of returned object
   * @param throwLastException whether we should throw exception which has been throws during get at
   *                           the end
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retryIfNotNull(
      Function<Integer, R> m,
      int retryCount,
      int interval,
      Supplier<R> orElse,
      boolean throwLastException) {
    return retryIf(m, t -> t != null, retryCount, interval, orElse, throwLastException);
  }

  /**
   * Retry the function get if any exception throws by result of the function get. Please note that
   * we throw runtime exception which wrap the original exception so we do not have to add throwable
   * to all method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param <R>        type of returned object
   * @return result of {@code m} method get or null if all retry failed
   */
  public static <R> R retry(Function<Integer, R> m, int retryCount, int interval) {
    return retryIf(m, null, retryCount, interval, null);
  }

  /**
   * Retry the function get if any exception throws by result of the function get. Please note that
   * we throw runtime exception which wrap the original exception so we do not have to add throwable
   * to all method deceleration.
   *
   * @param m          function to be called
   * @param retryCount maximum number of retry
   * @param interval   interval between retries in milliseconds
   * @param orElse     supplier to generate alternative result if retryIf result was always true,
   *                   returns null if the supplier is null
   * @param <R>        type of returned object
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retry(
      Function<Integer, R> m, int retryCount, int interval, Supplier<R> orElse) {
    return retryIf(m, null, retryCount, interval, orElse);
  }

  /**
   * Retry the function get if any exception throws by result of the function get. Please note that
   * we throw runtime exception which wrap the original exception so we do not have to add throwable
   * to all method deceleration.
   *
   * @param m                  function to be called
   * @param retryCount         maximum number of retry
   * @param interval           interval between retries in milliseconds
   * @param orElse             supplier to generate alternative result if retryIf result was always true,
   *                           returns null if the supplier is null
   * @param <R>                type of returned object
   * @param throwLastException whether we should throw exception which has been throws during get at
   *                           the end
   * @return result of {@code m} method get. if all retry failed then return orElse invocation
   * result or null if orElse is null.
   */
  public static <R> R retry(
      Function<Integer, R> m,
      int retryCount,
      int interval,
      Supplier<R> orElse,
      boolean throwLastException) {
    return retryIf(m, null, retryCount, interval, orElse, throwLastException);
  }
}

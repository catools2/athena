package org.catools.common.concurrent;

import com.google.common.util.concurrent.SimpleTimeLimiter;
import org.catools.common.concurrent.exceptions.CThreadTimeoutException;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * There are many time in automation when you stock on some task due to application response time or
 * dead loop inside automation code. We do want to fix both but we do not want to limit our
 * execution due to such scenarios so we use a {@link CTimeBoxRunner} which is job is to wait for
 * task only in defined time frame and throw exception if the task execution timeout
 *
 * @param <R> type of result object
 */
public class CTimeBoxRunner<R> implements Runnable {
  private final Supplier<R> job;
  private final int timeoutInSeconds;
  private final boolean throwExceptionIfTimeout;
  private Throwable ex;
  private R r;

  private CTimeBoxRunner(Supplier<R> job, int timeoutInSeconds, boolean throwExceptionIfTimeout) {
    this.job = job;
    this.timeoutInSeconds = timeoutInSeconds;
    this.throwExceptionIfTimeout = throwExceptionIfTimeout;
  }

  /**
   * Perform a task in separate concurrent and return the execution result. If the task not been
   * finished in the defined time box then then return null without throwing any exception
   *
   * @param job              task to be perform
   * @param timeoutInSeconds execution timeout in seconds
   * @param <R>              the type of return objects
   * @return the result of task model
   */
  public static <R> R get(Supplier<R> job, int timeoutInSeconds) {
    return get(job, timeoutInSeconds, false);
  }

  /**
   * Perform a task in separate concurrent and return the execution result. If the {@code
   * throwExceptionIfTimeout} is set to be FALSE and the task not been finished in the defined time
   * box then then return null without throwing any exception If the {@code throwExceptionIfTimeout}
   * is set to be TRUE and the task not been finished in the defined time box then then throw any
   * exception {@link CThreadTimeoutException}
   *
   * @param job                     task to be perform
   * @param timeout                 execution timeout amount
   * @param unit                    execution timeout time unit
   * @param throwExceptionIfTimeout whether should throw execution on timeout or not
   * @param <R>                     the type of return objects
   * @return the result of task model
   * @throws CThreadTimeoutException throw execution on timeout if throwExceptionIfTimeout parameter
   *                                 set to TRUE
   */
  public static <R> R get(
      Supplier<R> job, long timeout, TimeUnit unit, boolean throwExceptionIfTimeout) {
    return new CTimeBoxRunner<R>(
        job, (int) TimeUnit.SECONDS.convert(timeout, unit), throwExceptionIfTimeout)
        .get();
  }

  /**
   * Perform a task in separate concurrent and return the execution result. If the {@code
   * throwExceptionIfTimeout} is set to be FALSE and the task not been finished in the defined time
   * box then then return null without throwing any exception If the {@code throwExceptionIfTimeout}
   * is set to be TRUE and the task not been finished in the defined time box then then throw any
   * exception {@link CThreadTimeoutException}
   *
   * @param job                     task to be perform
   * @param timeoutInSeconds        execution timeout in seconds
   * @param throwExceptionIfTimeout whether should throw execution on timeout or not
   * @param <R>                     the type of return objects
   * @return the result of task model
   * @throws CThreadTimeoutException throw execution on timeout if throwExceptionIfTimeout parameter
   *                                 set to TRUE
   */
  public static <R> R get(Supplier<R> job, int timeoutInSeconds, boolean throwExceptionIfTimeout) {
    return new CTimeBoxRunner<R>(job, timeoutInSeconds, throwExceptionIfTimeout).get();
  }

  @Override
  public void run() {
    try {
      r =
          SimpleTimeLimiter.create(Executors.newFixedThreadPool(1))
              .callWithTimeout(() -> job.get(), timeoutInSeconds, TimeUnit.SECONDS);
    } catch (TimeoutException e) {
      if (throwExceptionIfTimeout) {
        throw new CThreadTimeoutException("Job execution takes more time than expected");
      }
      this.ex = e;
    } catch (Throwable e) {
      throw new RuntimeException(ex);
    }
  }

  private R get() {
    run();
    return r;
  }
}

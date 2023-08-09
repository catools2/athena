package org.catools.common.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A simple implementation to execute parallel tasks using {@link CExecutorService} with a simplified interface
 * for the majority of automation needs
 *
 * @param <T>
 */
public class CParallelRunner<T> {
  private final CExecutorService<T> executor;

  public CParallelRunner(String name, int threadCount, Callable<T> callable) {
    this(name, threadCount, callable, true);
  }

  public CParallelRunner(
      String name, int threadCount, Callable<T> callable, boolean stopOnException) {
    this.executor = new CExecutorService<>(name, threadCount, stopOnException);
    for (int i = 0; i < threadCount; i++) {
      executor.addCallable(callable);
    }
  }

  /**
   * Define if the executor has been started or not
   *
   * @return true if executor has been started otherwise false
   */
  public boolean isStarted() {
    return executor.isStarted();
  }

  /**
   * Define if the executor has been finished or not
   *
   * @return true if executor has been finished otherwise false
   */
  public boolean isFinished() {
    return executor.isFinished();
  }

  /**
   * Define if the executor has been shutdown or not
   *
   * @return true if executor has been shutdown otherwise false
   */
  public boolean isShutdown() {
    return executor.isShutdown();
  }

  /**
   * Define if the executor has been terminated or not
   *
   * @return true if executor has been terminated otherwise false
   */
  public boolean isTerminated() {
    return executor.isTerminated();
  }

  /**
   * Executes the given tasks, returning a list of Futures holding their status and results when all
   * complete or the timeout expires, whichever happens first.
   */
  public void invokeAll() throws Throwable {
    executor.invokeAll();
  }

  /**
   * Executes the given tasks, returning a list of Futures holding their status and results when all
   * complete or the timeout expires, whichever happens first.
   *
   * @param timeout the maximum time to wait
   * @param unit    the time unit of the timeout argument
   */
  public void invokeAll(long timeout, TimeUnit unit) throws Throwable {
    executor.invokeAll(timeout, unit);
  }

  /**
   * Initiates an orderly shutdown in which previously submitted tasks are executed, but no new
   * tasks will be accepted. Invocation has no additional effect if already shut down.
   *
   * <p>This method does not wait for previously submitted tasks to complete execution. Use {@link
   * ExecutorService#awaitTermination awaitTermination} to do that.
   *
   * @throws SecurityException if a security manager exists and shutting down this ExecutorService
   *                           may manipulate threads that the caller is not permitted to modify because it does not hold
   *                           {@link java.lang.RuntimePermission}{@code ("modifyThread")}, or the security manager's
   *                           {@code checkAccess} method denies access.
   */
  public void shutdown() {
    executor.shutdown();
  }

  /**
   * Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and
   * returns a list of the tasks that were awaiting execution.
   *
   * <p>This method does not wait for actively executing tasks to terminate. Use {@link
   * ExecutorService#awaitTermination awaitTermination} to do that.
   *
   * <p>There are no guarantees beyond best-effort attempts to stop processing actively executing
   * tasks. For example, typical implementations will cancel via {@link Thread#interrupt}, so any
   * task that fails to respond to interrupts may never terminate.
   *
   * @throws SecurityException if a security manager exists and shutting down this ExecutorService
   *                           may manipulate threads that the caller is not permitted to modify because it does not hold
   *                           {@link java.lang.RuntimePermission}{@code ("modifyThread")}, or the security manager's
   *                           {@code checkAccess} method denies access.
   */
  public void shutdownNow() {
    executor.shutdownNow();
  }
}

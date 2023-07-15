package org.catools.common.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

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

  public boolean isStarted() {
    return executor.isStarted();
  }

  public boolean isFinished() {
    return executor.isFinished();
  }

  public boolean isShutdown() {
    return executor.isShutdown();
  }

  public boolean isTerminated() {
    return executor.isTerminated();
  }

  public void invokeAll() throws Throwable {
    executor.invokeAll();
  }

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

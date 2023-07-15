package org.catools.common.concurrent;

import org.catools.common.concurrent.exceptions.CInterruptedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * A simple implementation of {@link ExecutorService} to simplify interface for majority of
 * automation needs
 *
 * @param <T>
 */
public class CExecutorService<T> {
  private final AtomicReference<Throwable> throwableReference = new AtomicReference();
  private final List<Callable<T>> queue = Collections.synchronizedList(new ArrayList<>());
  private final AtomicBoolean started = new AtomicBoolean();
  private final AtomicBoolean finished = new AtomicBoolean();
  private final ExecutorService executor;
  private final String name;
  private final Long timeout;
  private final TimeUnit unit;
  private final boolean stopOnException;

  public CExecutorService(String name, int threadCount) {
    this(name, threadCount, null, null, true);
  }

  public CExecutorService(String name, int threadCount, boolean stopOnException) {
    this(name, threadCount, null, null, stopOnException);
  }

  public CExecutorService(String name, int threadCount, Long timeout, TimeUnit unit) {
    this(name, threadCount, timeout, unit, true);
  }

  public CExecutorService(
      String name, int threadCount, Long timeout, TimeUnit unit, boolean stopOnException) {
    this.executor = Executors.newFixedThreadPool(threadCount);
    this.name = name;
    this.timeout = timeout;
    this.unit = unit;
    this.stopOnException = stopOnException;
  }

  /**
   * Define if the executor has been started or not
   *
   * @return true if executor has been started otherwise false
   */
  public boolean isStarted() {
    return started.get();
  }

  /**
   * Define if the executor has been finished or not
   *
   * @return true if executor has been finished otherwise false
   */
  public boolean isFinished() {
    return finished.get();
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
   * Adding new task to the task queue.
   *
   * @param callable a new task to be added to queue
   */
  public void addCallable(Callable<T> callable) {
    this.queue.add(
        () -> {
          try {
            return callable.call();
          } catch (Throwable t) {
            throwableReference.set(t);
            if (stopOnException) {
              shutdownNow();
            }
            throw t;
          }
        });
  }

  /**
   * Executes the given tasks, returning a list of Futures holding their status and results when all
   * complete or the timeout expires, whichever happens first.
   */
  public void invokeAll() throws Throwable {
    if (timeout != null && unit != null) {
      invokeAll(timeout, unit);
    } else {
      doInvoke(
          () -> {
            try {
              executor.invokeAll(this.queue);
            } catch (InterruptedException e) {
              throw new CInterruptedException("Parallel execution interrupted for " + name, e);
            }
            return true;
          });
    }
  }

  /**
   * Executes the given tasks, returning a list of Futures holding their status and results when all
   * complete or the timeout expires, whichever happens first.
   *
   * @param timeout the maximum time to wait
   * @param unit    the time unit of the timeout argument
   */
  public void invokeAll(long timeout, TimeUnit unit) throws Throwable {
    doInvoke(
        () -> {
          try {
            executor.invokeAll(this.queue, timeout, unit);
          } catch (InterruptedException e) {
            throw new CInterruptedException("Parallel execution interrupted for " + name, e);
          }
          return true;
        });
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

  private void doInvoke(Supplier supplier) throws Throwable {
    started.set(true);
    supplier.get();

    executor.shutdown();

    while (!executor.isTerminated()) {
    }

    finished.set(true);

    if (throwableReference.get() != null) {
      throw throwableReference.get();
    }
  }
}

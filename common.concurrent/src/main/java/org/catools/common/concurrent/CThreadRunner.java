package org.catools.common.concurrent;

import java.util.concurrent.Executors;

/**
 * Thread related methods can be find here
 */
public class CThreadRunner {
  /**
   * Perform action in parallel concurrent
   *
   * @param task to be perform
   * @return the concurrent object for farther usage
   */
  public static Thread run(Runnable task) {
    Thread thread = Executors.defaultThreadFactory().newThread(task);
    thread.start();
    return thread;
  }
}

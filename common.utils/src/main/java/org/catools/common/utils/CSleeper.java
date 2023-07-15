package org.catools.common.utils;

public class CSleeper {
  public static void sleepTight(long milisecs) {
    try {
      Thread.sleep(milisecs);
    } catch (InterruptedException e) {
    }
  }

  public static void sleepTightInSeconds(long secs) {
    sleepTight(secs * 1000);
  }
}

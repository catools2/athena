package org.catools.common.logger;

import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

/**
 * We need this Event Manager to subscript listeners for other system with least changes. Messages
 * we send to the listener are masked and secure.
 */
public class CLoggerSubscriber {
  private static final Map<String, CLoggerEvent> listeners = new HashMap<>();

  public static void subscribe(String name, CLoggerEvent listener) {
    synchronized (listeners) {
      listeners.put(name, listener);
    }
  }

  public static void unSubscribe(String name) {
    synchronized (listeners) {
      listeners.remove(name);
    }
  }

  public static void notify(Level level, String message) {
    synchronized (listeners) {
      for (CLoggerEvent listener : listeners.values()) {
        listener.onLogging(level, message);
      }
    }
  }
}

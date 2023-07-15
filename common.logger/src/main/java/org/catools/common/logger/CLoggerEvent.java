package org.catools.common.logger;

import org.apache.logging.log4j.Level;

/**
 * We need this listener so we have capability to redirect messages to other system with least
 * changes. The main point for this listener is that we get messages masked, so it is secure.
 */
public interface CLoggerEvent {

  void onLogging(Level level, String msg);
}

package org.catools.common.tests.logger;

import org.apache.logging.log4j.Level;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.logger.CLoggerSubscriber;
import org.catools.common.tests.CBaseUnitTest;
import org.testng.annotations.Test;

public class CLoggerSubscriberTest extends CBaseUnitTest {

  @Test
  public void testSubscribe() {
    CMap<String, Level> events = new CHashMap<>();
    CLoggerSubscriber.subscribe("A", (level, msg) -> events.put(msg, level));
    getLogger().trace("ERROR");
    getLogger().info("ERROR");
    getLogger().error("ERROR");

    CLoggerSubscriber.unSubscribe("A");
    getLogger().error("ERROR2");

    events.verifyContains("ERROR", Level.ERROR);
    events.verifyNotContains("ERROR2", Level.ERROR);
  }
}

package org.catools.common.logger.appender;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.catools.common.logger.CLoggerSubscriber;
import org.catools.common.security.CSensitiveDataMaskingManager;

import java.io.Serializable;

import static org.catools.common.logger.CLogEventHelper.getMessage;

@Plugin(
    name = "CLogSubscriberAppender",
    category = "Core",
    elementType = "appender",
    printObject = true)
public class CLogSubscriberAppender extends AbstractAppender {

  protected CLogSubscriberAppender(
      final String name,
      final Filter filter,
      final Layout<? extends Serializable> layout,
      final boolean ignoreExceptions) {
    super(name, filter, layout, ignoreExceptions);
  }

  @PluginFactory
  public static CLogSubscriberAppender createAppender(
      @PluginAttribute("name") String name,
      @PluginElement("filter") Filter filter,
      @PluginElement("layout") Layout<? extends Serializable> layout) {
    if (name == null) {
      LOGGER.error("No name provided for CLogSubscriberAppender");
      return null;
    }
    if (layout == null) {
      LOGGER.error("No layout provided for CLogSubscriberAppender");
      return null;
    }
    return new CLogSubscriberAppender(name, filter, layout, true);
  }

  @Override
  public void append(final LogEvent event) {
    String message = getMessage(event);
    if (StringUtils.isNotBlank(message)) {
      CLoggerSubscriber.notify(event.getLevel(), CSensitiveDataMaskingManager.mask(message));
    }
  }
}

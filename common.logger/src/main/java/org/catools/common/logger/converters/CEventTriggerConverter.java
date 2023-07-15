package org.catools.common.logger.converters;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.catools.common.logger.CLoggerSubscriber;
import org.catools.common.security.CSensitiveDataMaskingManager;

import static org.catools.common.logger.CLogEventHelper.getMessage;

@Plugin(name = "CEventTriggerConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"CEvnT"})
public class CEventTriggerConverter extends LogEventPatternConverter {

  /**
   * Constructs an instance of CEventTriggerConverter.
   */
  protected CEventTriggerConverter() {
    super("Event Trigger", "CEvnT");
  }

  public static CEventTriggerConverter newInstance(final String[] options) {
    return new CEventTriggerConverter();
  }

  @Override
  public void format(LogEvent event, StringBuilder toAppendTo) {
    // we just call CLoggerSubscriber.notify(event)
    String message = getMessage(event);
    if (StringUtils.isNotBlank(message)) {
      CLoggerSubscriber.notify(event.getLevel(), CSensitiveDataMaskingManager.mask(message));
    }
  }
}

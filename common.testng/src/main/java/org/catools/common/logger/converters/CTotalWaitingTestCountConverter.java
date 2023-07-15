package org.catools.common.logger.converters;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.catools.common.testng.listeners.CExecutionStatisticListener;

@Plugin(name = "CTotalWaitingTestCountConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"ETW"})
public class CTotalWaitingTestCountConverter extends CBaseExecutionStatisticConverter {

  /**
   * Constructs an instance of CTotalWaitingTestCountConverter.
   *
   * @param stringFormat format to be used. defaults to "%d"
   */
  protected CTotalWaitingTestCountConverter(final String stringFormat) {
    super(
        "Total Waiting", "ETW", stringFormat, () -> CExecutionStatisticListener.getTotalWaiting());
  }

  public static CTotalWaitingTestCountConverter newInstance(final String[] options) {
    return new CTotalWaitingTestCountConverter(validateAndGetOption(options));
  }
}

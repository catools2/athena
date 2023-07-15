package org.catools.common.logger.converters;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.catools.common.testng.listeners.CExecutionStatisticListener;

@Plugin(name = "CTotalRunningTestCountConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"ETR"})
public class CTotalRunningTestCountConverter extends CBaseExecutionStatisticConverter {

  /**
   * Constructs an instance of CCurrentRunNumberConverter.
   *
   * @param stringFormat format to be used. defaults to "%d"
   */
  protected CTotalRunningTestCountConverter(final String stringFormat) {
    super(
        "Total Running", "ETR", stringFormat, () -> CExecutionStatisticListener.getTotalRunning());
  }

  public static CTotalRunningTestCountConverter newInstance(final String[] options) {
    return new CTotalRunningTestCountConverter(validateAndGetOption(options));
  }
}

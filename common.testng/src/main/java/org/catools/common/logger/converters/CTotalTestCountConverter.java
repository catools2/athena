package org.catools.common.logger.converters;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.catools.common.testng.listeners.CExecutionStatisticListener;

@Plugin(name = "CTotalTestCountConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"ETT"})
public class CTotalTestCountConverter extends CBaseExecutionStatisticConverter {

  /**
   * Constructs an instance of CTotalTestCountConverter.
   *
   * @param stringFormat format to be used. defaults to "%d"
   */
  protected CTotalTestCountConverter(final String stringFormat) {
    super("Total Tests", "ETT", stringFormat, () -> CExecutionStatisticListener.getTotal());
  }

  public static CTotalTestCountConverter newInstance(final String[] options) {
    return new CTotalTestCountConverter(validateAndGetOption(options));
  }
}

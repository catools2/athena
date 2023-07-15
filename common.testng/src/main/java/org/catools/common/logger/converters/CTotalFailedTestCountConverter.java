package org.catools.common.logger.converters;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.catools.common.testng.listeners.CExecutionStatisticListener;

@Plugin(name = "CTotalFailedTestCountConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"ETF"})
public class CTotalFailedTestCountConverter extends CBaseExecutionStatisticConverter {

  /**
   * Constructs an instance of CTotalFailedTestCountConverter.
   *
   * @param stringFormat format to be used. defaults to "%d"
   */
  protected CTotalFailedTestCountConverter(final String stringFormat) {
    super("Total Failed", "ETF", stringFormat, () -> CExecutionStatisticListener.getTotalFailed());
  }

  public static CTotalFailedTestCountConverter newInstance(final String[] options) {
    return new CTotalFailedTestCountConverter(validateAndGetOption(options));
  }
}

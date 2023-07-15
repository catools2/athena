package org.catools.common.logger.converters;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.catools.common.testng.listeners.CExecutionStatisticListener;

@Plugin(name = "CTotalPassedTestCountConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"ETP"})
public class CTotalPassedTestCountConverter extends CBaseExecutionStatisticConverter {

  /**
   * Constructs an instance of CTotalPassedTestCountConverter.
   *
   * @param stringFormat format to be used. defaults to "%d"
   */
  protected CTotalPassedTestCountConverter(final String stringFormat) {
    super("Total Pass", "ETP", stringFormat, () -> CExecutionStatisticListener.getTotalPassed());
  }

  public static CTotalPassedTestCountConverter newInstance(final String[] options) {
    return new CTotalPassedTestCountConverter(validateAndGetOption(options));
  }
}

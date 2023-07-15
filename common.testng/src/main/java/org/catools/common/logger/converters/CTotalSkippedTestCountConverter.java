package org.catools.common.logger.converters;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.catools.common.testng.listeners.CExecutionStatisticListener;

@Plugin(name = "CTotalSkippedTestCountConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"ETS"})
public class CTotalSkippedTestCountConverter extends CBaseExecutionStatisticConverter {

  /**
   * Constructs an instance of CTotalSkippedTestCountConverter.
   *
   * @param stringFormat format to be used. defaults to "%d"
   */
  protected CTotalSkippedTestCountConverter(final String stringFormat) {
    super(
        "Total Skipped", "ETS", stringFormat, () -> CExecutionStatisticListener.getTotalSkipped());
  }

  public static CTotalSkippedTestCountConverter newInstance(final String[] options) {
    return new CTotalSkippedTestCountConverter(validateAndGetOption(options));
  }
}

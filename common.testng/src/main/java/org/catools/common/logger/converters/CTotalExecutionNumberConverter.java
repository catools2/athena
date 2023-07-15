package org.catools.common.logger.converters;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.catools.common.testng.CTestNGConfigs;

@Plugin(name = "CTotalExecutionNumberConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"EX"})
public class CTotalExecutionNumberConverter extends CBaseExecutionStatisticConverter {

  /**
   * Constructs an instance of CTotalExecutionNumberConverter.
   *
   * @param stringFormat format to be used. defaults to "%d"
   */
  protected CTotalExecutionNumberConverter(final String stringFormat) {
    super("Total Execution", "EX", stringFormat, () -> CTestNGConfigs.getSuiteRetryCount() + 1);
  }

  public static CTotalExecutionNumberConverter newInstance(final String[] options) {
    return new CTotalExecutionNumberConverter(validateAndGetOption(options));
  }
}

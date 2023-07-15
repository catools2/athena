package org.catools.common.logger.converters;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.catools.common.testng.CTestNGConfigs;

@Plugin(name = "CCurrentExecutionNumberConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"EC"})
public class CCurrentExecutionNumberConverter extends CBaseExecutionStatisticConverter {

  /**
   * Constructs an instance of CCurrentExecutionNumberConverter.
   *
   * @param stringFormat format to be used. defaults to "%d"
   */
  protected CCurrentExecutionNumberConverter(final String stringFormat) {
    super("Current Execution", "EC", stringFormat, () -> CTestNGConfigs.getSuiteRunCounter());
  }

  public static CCurrentExecutionNumberConverter newInstance(final String[] options) {
    return new CCurrentExecutionNumberConverter(validateAndGetOption(options));
  }
}

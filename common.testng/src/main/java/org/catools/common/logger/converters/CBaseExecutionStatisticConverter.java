package org.catools.common.logger.converters;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.catools.common.utils.CStringUtil;

import java.util.function.Supplier;

public abstract class CBaseExecutionStatisticConverter extends LogEventPatternConverter {
  private final Supplier<Integer> supplier;
  private final String stringFormat;

  protected CBaseExecutionStatisticConverter(
      final String name,
      final String style,
      final String stringFormat,
      final Supplier<Integer> supplier) {
    super(name, style);
    this.stringFormat = CStringUtil.defaultIfBlank(stringFormat, "%d");
    this.supplier = supplier;
  }

  @Override
  public void format(LogEvent event, StringBuilder toAppendTo) {
    toAppendTo.append(String.format(stringFormat, supplier.get()));
  }

  public static String validateAndGetOption(final String[] options) {
    assert options.length < 2;
    return options == null || options.length == 0 ? null : options[0];
  }
}

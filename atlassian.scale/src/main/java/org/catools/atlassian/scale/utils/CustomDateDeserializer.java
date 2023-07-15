package org.catools.atlassian.scale.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.catools.atlassian.scale.configs.CZScaleConfigs;
import org.catools.common.collections.CList;
import org.catools.common.date.CDate;

import java.io.IOException;
import java.util.Date;

public class CustomDateDeserializer extends StdDeserializer<Date> {
  private static final CList<String> dateFormats = CZScaleConfigs.Scale.getDateFormats();

  public CustomDateDeserializer() {
    this(null);
  }

  public CustomDateDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
    String date = jsonparser.getText();
    return CDate.valueOf(date, dateFormats.toArray(new String[dateFormats.size()]));
  }
}

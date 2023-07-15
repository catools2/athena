package org.catools.atlassian.scale.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.catools.atlassian.scale.configs.CZScaleConfigs;
import org.catools.common.collections.CList;
import org.catools.common.date.CDate;

import java.io.IOException;
import java.util.Date;

public class CustomDateSerializer extends StdSerializer<Date> {
  private static final CList<String> dateFormats = CZScaleConfigs.Scale.getDateFormats();

  public CustomDateSerializer() {
    this(null);
  }

  public CustomDateSerializer(Class<Date> t) {
    super(t);
  }

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(CDate.valueOf(date).toFormat(dateFormats.getFirst()));
  }
}

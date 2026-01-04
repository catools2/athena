package org.catools.athena.atlassian.etl.scale.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class CustomDateSerializer extends StdSerializer<Date> {

  public CustomDateSerializer() {
    this(null);
  }

  public CustomDateSerializer(Class<Date> t) {
    super(t);
  }

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
    try {
      jsonGenerator.writeString(DateFormatUtils.format(date, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    } catch (Throwable t) {
      throw new RuntimeException(String.format("cannot convert %s to date", date), t);
    }
  }
}

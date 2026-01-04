package org.catools.athena.atlassian.etl.scale.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class CustomDateDeserializer extends StdDeserializer<Date> {

  public CustomDateDeserializer() {
    this(null);
  }

  public CustomDateDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Date deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
    String date = jsonparser.getText();
    try {
      return DateUtils.parseDateStrictly(date, Locale.getDefault(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    } catch (Throwable t) {
      throw new RuntimeException(String.format("cannot convert %s to date", date), t);
    }
  }
}

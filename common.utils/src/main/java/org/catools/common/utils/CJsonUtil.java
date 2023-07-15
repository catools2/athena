package org.catools.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.experimental.UtilityClass;
import org.catools.common.exception.CInvalidJsonFormatException;
import org.catools.common.exception.CJsonGenerationException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

@UtilityClass
public class CJsonUtil {
  public static <T> T clone(T object, Module... modules) {
    return (T) read(toString(object), object.getClass(), modules);
  }

  public static <T> T read(File file, Class<T> clazz, Module... modules) {
    return read(CFileUtil.readString(file, Charset.forName("UTF-8")), clazz, modules);
  }

  public static <T> T read(File file, JavaType type, Module... modules) {
    return _read(CFileUtil.readString(file, Charset.forName("UTF-8")), type, modules);
  }

  public static <T> T read(String input, Class<T> clazz, Module... modules) {
    return _read(input, clazz, modules);
  }

  public static <T> T read(
      String wsResponseContent, TypeReference<T> typeReference, Module... modules) {
    final ObjectMapper mapper = getObjectMapper(modules);
    try {
      return (T) mapper.readValue(wsResponseContent, typeReference);
    } catch (Throwable t) {
      throw new CJsonGenerationException("Could not convert object to JSON string", t);
    }
  }

  public static String toString(Object object, Module... modules) {
    return toString(true, object, modules);
  }

  public static String toString(boolean prettyPrint, Object object, Module... modules) {
    try {
      Objects.requireNonNull(object);
      return getObjectWriter(prettyPrint, modules).writeValueAsString(object);
    } catch (Throwable t) {
      throw new CJsonGenerationException("Could not convert object to JSON string", t);
    }
  }

  public static void write(File file, Object object, Module... modules) {
    write(file, object, true, modules);
  }

  public static void write(File file, Object object, boolean prettyPrint, Module... modules) {
    try {
      file.getParentFile().mkdirs();
      getObjectWriter(prettyPrint, modules).writeValue(file, object);
    } catch (Throwable t) {
      throw new CJsonGenerationException("Could not write object to file as JSON", t);
    }
  }

  /**
   * Check if input has defined json path.
   *
   * @param modules modules to be used
   * @return true if path found otherwise false
   */
  public static TypeFactory getTypeFactory(Module... modules) {
    return getObjectMapper(modules).getTypeFactory();
  }

  private static synchronized <T> T _read(String input, Class<T> clazz, Module... modules) {
    try {
      ObjectMapper mapper = getObjectMapper(modules);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      return mapper.readValue(input, clazz);
    } catch (IOException e) {
      throw new CInvalidJsonFormatException("Could not read json from " + input, e);
    }
  }

  private static synchronized <T> T _read(String input, JavaType type, Module... modules) {
    try {
      ObjectMapper mapper = getObjectMapper(modules);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      return mapper.readValue(input, type);
    } catch (IOException e) {
      throw new CInvalidJsonFormatException("Could not read json from " + input, e);
    }
  }

  private static synchronized ObjectWriter getObjectWriter(boolean prettyPrint, Module... modules) {
    ObjectMapper mapper = getObjectMapper(modules);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    return prettyPrint ? mapper.writerWithDefaultPrettyPrinter() : mapper.writer();
  }

  private static synchronized ObjectMapper getObjectMapper(Module... modules) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModules(modules);
    return mapper;
  }
}

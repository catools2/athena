package org.catools.common.serialization;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.experimental.UtilityClass;
import org.catools.common.exception.CRuntimeException;
import org.catools.common.utils.CResourceUtil;

import java.io.File;

@UtilityClass
public class CXmlSerializationUtil {
  public static String toXml(Object obj, SerializationFeature... serializationFeature) {
    try {
      return getMarshaller(serializationFeature).writeValueAsString(obj);
    } catch (Throwable e) {
      throw new CRuntimeException("Failed to serialize object to xml ", e);
    }
  }

  public static Object read(String resourceName, Class<?> classLoader, Class<?> clazz, SerializationFeature... serializationFeature) {
    return CResourceUtil.performActionOnResource(resourceName, classLoader, (s, inputStream) -> {
      try {
        return getMarshaller(serializationFeature).readValue(inputStream, clazz);
      } catch (Throwable e) {
        throw new CRuntimeException("Failed to deserialize file " + resourceName, e);
      }
    });
  }

  public static Object read(File file, Class<?> clazz, SerializationFeature... serializationFeature) {
    try {
      return getMarshaller(serializationFeature).readValue(file, clazz);
    } catch (Throwable e) {
      throw new CRuntimeException("Failed to deserialize file " + file, e);
    }
  }

  public static File write(Object obj, File file, SerializationFeature... serializationFeature) {
    try {
      getMarshaller(serializationFeature).writeValue(file, obj);
      return file;
    } catch (Throwable e) {
      throw new CRuntimeException("Failed to serialize to file " + file, e);
    }
  }

  private static XmlMapper getMarshaller(SerializationFeature... serializationFeature) {
    XmlMapper xmlMapper = new XmlMapper();
    for (SerializationFeature feature : serializationFeature) {
      xmlMapper.enable(feature);
    }
    return xmlMapper;
  }
}

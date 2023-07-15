package org.catools.common.serialization;

import lombok.experimental.UtilityClass;
import org.catools.common.exception.CRuntimeException;
import org.catools.common.utils.CResourceUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;

@UtilityClass
public class CXmlSerializationUtil {
  public static String toXml(Object obj) {
    try {
      StringWriter writer = new StringWriter();
      getMarshaller(obj.getClass()).marshal(obj, writer);
      return writer.toString();
    } catch (Throwable e) {
      throw new CRuntimeException("Failed to serialize object to xml ", e);
    }
  }

  public static Object read(String resourceName, Class classLoader, Class clazz) {
    return CResourceUtil.performActionOnResource(resourceName, classLoader, (s, inputStream) -> {
      try {
        JAXBContext jc = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return unmarshaller.unmarshal(inputStream);
      } catch (Throwable e) {
        throw new CRuntimeException("Failed to deserialize file " + resourceName, e);
      }
    });
  }

  public static Object read(File file, Class clazz) {
    try {
      JAXBContext jc = JAXBContext.newInstance(clazz);
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      return unmarshaller.unmarshal(file);
    } catch (Throwable e) {
      throw new CRuntimeException("Failed to deserialize file " + file, e);
    }
  }

  public static File write(Object obj, File file) {
    try {
      getMarshaller(obj.getClass()).marshal(obj, file);
      return file;
    } catch (Throwable e) {
      throw new CRuntimeException("Failed to serialize to file " + file, e);
    }
  }

  private static Marshaller getMarshaller(Class clazz) throws JAXBException {
    JAXBContext jc = JAXBContext.newInstance(clazz);
    return jc.createMarshaller();
  }
}

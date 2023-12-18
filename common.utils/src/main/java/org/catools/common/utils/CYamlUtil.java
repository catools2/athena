package org.catools.common.utils;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.experimental.UtilityClass;
import org.catools.common.exception.CFileNotFoundException;
import org.catools.common.exception.CInvalidYamlContentFormatException;
import org.catools.common.exception.CInvalidYamlFileFormatException;

import java.io.File;

@UtilityClass
public class CYamlUtil {
  public static <T> T readFromResources(String fileName, Class<T> clazz, Module... modules) {
    return readFromResources(fileName, clazz, clazz, modules);
  }

  public static <T> T readFromResources(
      String fileName, Class classLoader, Class<T> clazz, Module... modules) {
    return readFromString(CResourceUtil.getString(fileName, classLoader), clazz, modules);
  }

  public static <T> T readFromFile(File file, Class<T> clazz, Module... modules) {
    if (!file.exists()) {
      throw new CFileNotFoundException(file, "Property file not found");
    }
    try {
      T t = getObjectMapper(modules).readValue(file, clazz);
      if (t == null) {
        throw new CInvalidYamlFileFormatException(file);
      }
      return t;
    } catch (Exception e) {
      throw new CInvalidYamlFileFormatException(file.getPath(), e);
    }
  }

  public static <T> T readFromString(String content, Class<T> clazz, Module... modules) {
    try {
      T t = getObjectMapper(modules).readValue(content, clazz);
      if (t == null) {
        throw new CInvalidYamlContentFormatException(content);
      }
      return t;
    } catch (Exception e) {
      throw new CInvalidYamlContentFormatException(content, e);
    }
  }

  public static void writeToFile(Object obj, File file, Module... modules) {
    try {
      getObjectMapper(modules).writeValue(file, obj);
    } catch (Exception e) {
      throw new RuntimeException("Failed to serialize and write the object to the file!", e);
    }
  }

  public static String toString(Object obj, Module... modules) {
    try {
      return getObjectMapper(modules).writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException("Failed to serialize object to string!", e);
    }
  }

  private static ObjectMapper getObjectMapper(Module... modules) {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    mapper.findAndRegisterModules();
    mapper.registerModules(modules);
    return mapper;
  }
}

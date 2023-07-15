package org.catools.common.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;

/**
 * This is a tool for common System call we use.
 */
@UtilityClass
public class CClassUtil {
  public static synchronized <T> Class<T> getType(Class clazz) {
    return (Class<T>)
        ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
  }
}

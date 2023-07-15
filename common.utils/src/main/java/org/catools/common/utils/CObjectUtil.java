package org.catools.common.utils;

import com.rits.cloning.Cloner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CObjectUtil {
  public static synchronized <T> T clone(T object) {
    return new Cloner().deepClone(object);
  }
}

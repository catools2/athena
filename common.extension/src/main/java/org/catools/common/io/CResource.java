package org.catools.common.io;

import org.catools.common.collections.CList;
import org.catools.common.utils.CResourceUtil;
import org.catools.common.utils.CStringUtil;

import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class CResource {
  private final String resourceFullName;
  private final Class clazz;

  public CResource(String resourceFullName) {
    this(resourceFullName, CResource.class);
  }

  public CResource(String resourceFullName, @Nullable Class clazz) {
    this.resourceFullName = resourceFullName;
    this.clazz = clazz;
  }

  public boolean exists() {
    try {
      getByteArray();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public CList<String> readLines() {
    return new CList<>(getString().split("\n"));
  }

  public CList<String> readLines(Predicate<String> predicate) {
    return readLines().getAll(predicate);
  }

  public String getResourceFullName() {
    return resourceFullName;
  }

  public String getResourceName() {
    if (resourceFullName.contains("\\")) {
      return CStringUtil.substringAfterLast(resourceFullName.replaceAll("\\\\", "/"), "/");
    }
    if (resourceFullName.contains("/")) {
      return CStringUtil.substringAfterLast(resourceFullName, "/");
    }
    return resourceFullName;
  }

  public String getString() {
    return new String(getByteArray());
  }

  public byte[] getByteArray() {
    return CResourceUtil.getByteArray(resourceFullName, clazz);
  }

  public CFile saveToFolder(File targetFolder) {
    return CFile.of(CResourceUtil.saveToFolder(resourceFullName, clazz, targetFolder));
  }

  public <T> T performActionOnResource(BiFunction<String, InputStream, T> action) {
    return CResourceUtil.performActionOnResource(resourceFullName, clazz, action);
  }
}

package org.catools.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.exception.CResourceNotFoundException;

import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.catools.common.utils.CSystemUtil.getPlatform;

@UtilityClass
public class CResourceUtil {

  public static List<String> readLines(String resourceFullName, @Nullable Class<?> clazz) {
    return List.of(getString(resourceFullName, clazz).split("\n"));
  }

  public static String getString(String resourceFullName, @Nullable Class<?> clazz) {
    return new String(getByteArray(resourceFullName, clazz));
  }

  public static byte[] getByteArray(String resourceFullName, @Nullable Class<?> clazz) {
    try {
      return performActionOnResource(resourceFullName, clazz, (resourceName, is) -> CInputStreamUtil.toByteArray((is)));
    } catch (Throwable t) {
      throw new CResourceNotFoundException("Failed to read resource " + resourceFullName, t);
    }
  }

  public static File saveToFolder(String resourceFullName, @Nullable Class<?> clazz, File targetFolder) {
    return performActionOnResource(
        resourceFullName,
        clazz,
        (resourceName, inputStream) -> {
          File destFile = new File(targetFolder, resourceName);
          destFile.getParentFile().mkdirs();
          CFileUtil.writeByteArrayToFile(destFile, CInputStreamUtil.toByteArray(inputStream));
          return destFile;
        });
  }

  public static File saveToFile(String resourceFullName, @Nullable Class<?> clazz, File destFile) {
    return performActionOnResource(
        resourceFullName,
        clazz,
        (resourceName, inputStream) -> {
          destFile.getParentFile().mkdirs();
          CFileUtil.writeByteArrayToFile(destFile, CInputStreamUtil.toByteArray(inputStream));
          return destFile;
        });
  }

  public static <T> T performActionOnResource(String resourceName, Class<?> clazz, BiFunction<String, InputStream, T> action) {
    if (CStringUtil.isBlank(resourceName)) {
      throw new CResourceNotFoundException("Resource name cannot be null or empty!");
    }

    Pair<Class<?>, ClassLoader> pair = getClassLoader(resourceName, clazz);
    if (pair == null) {
      throw new CResourceNotFoundException(resourceName + " resource not found!");
    }

    clazz = pair.getKey();
    try {
      URL resource = pair.getValue().getResource(resourceName);
      Objects.requireNonNull(resource);
      URI uri = resource.toURI();
      if (uri.getScheme().contains("jar")) {
        URL jar = clazz.getProtectionDomain().getCodeSource().getLocation();
        Path path =
            Paths.get(
                CStringUtil.substringAfter(
                    jar.toString(), getPlatform().isWindows() ? "file:/" : "file:"));
        try (FileSystem fs = FileSystems.newFileSystem(path)) {
          Path resourcePath = fs.getPath(resourceName);
          if (Files.isDirectory(resourcePath)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(resourcePath)) {
              Pair<InputStream, Path> res = getFirstAvailableResourceFromDirectoryStream(clazz, resourcePath, directoryStream);
              return action.apply(Path.of(resourceName, res.getValue().getFileName().toString()).toString(), res.getKey());
            }
          } else {
            try (JarFile jarFile = new JarFile(clazz.getProtectionDomain().getCodeSource().getLocation().getFile())) {
              JarEntry jarEntry = jarFile.getJarEntry(resourceName);
              return action.apply(jarEntry.getName(), jarFile.getInputStream(jarFile.getEntry(resourceName)));
            }
          }
        }
      } else {
        Path path = Paths.get(uri);
        if (Files.isDirectory(path)) {
          try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            Pair<InputStream, Path> res = getFirstAvailableResourceFromDirectoryStream(clazz, path, directoryStream);
            return action.apply(Path.of(resourceName, res.getValue().getFileName().toString()).toString(), FileUtils.openInputStream(res.getValue().toFile()));
          }
        } else {
          return action.apply(path.getFileName().toString(), FileUtils.openInputStream(new File(uri)));
        }
      }
    } catch (Throwable t) {
      throw new CResourceNotFoundException(
          "Unable to perform action on " + resourceName + " resource.", t);
    }
  }

  private static Pair<InputStream, Path> getFirstAvailableResourceFromDirectoryStream(Class<?> clazz, Path resourcePath, DirectoryStream<Path> directoryStream) {
    InputStream is = null;
    for (Path p : directoryStream) {
      is = clazz.getResourceAsStream(p.toString());
      if (is == null) {
        is = getResourceAsStream(p.toString(), clazz);
      }
      if (is != null) {
        return Pair.of(is, p);
      }
    }
    throw new CResourceNotFoundException("Failed to read " + resourcePath);
  }

  public static File saveToTemp(String resourceName, Class clazz) {
    return saveToFolder(resourceName, clazz, CPathConfigs.getTempFolder());
  }

  public static File saveToTempAs(String resourceName, Class clazz, String resourceNewName) {
    return saveToFile(resourceName, clazz, new File(CPathConfigs.getTempFolder(), resourceNewName));
  }

  public static File saveToOutput(String resourceName, Class clazz) {
    return saveToFolder(resourceName, clazz, CPathConfigs.getOutputFolder());
  }

  public static File saveToOutputAS(String resourceName, Class clazz, String resourceNewName) {
    return saveToFile(resourceName, clazz, new File(CPathConfigs.getOutputFolder(), resourceNewName));
  }

  public static URL getResource(String resource, Class<?> clazz) {
    return Objects.requireNonNull(getClassLoader(resource, clazz)).getValue().getResource(resource);
  }

  public static InputStream getResourceAsStream(String resource, Class<?> clazz) {
    return Objects.requireNonNull(getClassLoader(resource, clazz)).getValue().getResourceAsStream(resource);
  }

  public static Pair<Class<?>, ClassLoader> getClassLoader(String resource, Class<?> clazz) {
    final Map<Class<?>, ClassLoader> classLoaders = new HashMap<>();
    if (clazz != null) {
      classLoaders.put(clazz, clazz.getClassLoader());
      classLoaders.put(clazz.getClass(), clazz.getClass().getClassLoader());
    }
    classLoaders.put(CResourceUtil.class, CResourceUtil.class.getClassLoader());
    for (Class<?> c : classLoaders.keySet()) {
      if (c != null
          && classLoaders.get(c) != null
          && classLoaders.get(c).getResourceAsStream(resource) != null) {
        return Pair.of(clazz, classLoaders.get(c));
      }
    }
    return null;
  }
}

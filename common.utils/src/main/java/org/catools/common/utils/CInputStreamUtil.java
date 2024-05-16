package org.catools.common.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Slf4j
@UtilityClass
public class CInputStreamUtil {
  public static String read(InputStream inputStream) {
    try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
      try (BufferedReader br = new BufferedReader(inputStreamReader)) {
        StringWriter sw = new StringWriter();
        char[] buffer = new char[1024 * 4];
        int n;
        while (-1 != (n = br.read(buffer))) {
          sw.write(buffer, 0, n);
        }
        return sw.toString();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] toByteArray(InputStream inputStream) {
    try {
      return IOUtils.toByteArray(inputStream);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void save(InputStream inputStream, File targetFile) {
    try {
      Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

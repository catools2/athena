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
    BufferedReader br = null;

    try {
      br = new BufferedReader(new InputStreamReader(inputStream));
      StringWriter sw = new StringWriter();
      char[] buffer = new char[1024 * 4];
      int n;
      while (-1 != (n = br.read(buffer))) {
        sw.write(buffer, 0, n);
      }
      return sw.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          log.error("Failed to close input stream.", e);
        }
      }
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          log.error("Failed to close buffer reader.", e);
        }
      }
    }
  }

  public static byte[] toByteArray(InputStream inputStream) {
    try {
      return IOUtils.toByteArray(inputStream);
    } catch (Throwable t) {
      throw new RuntimeException(t);
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

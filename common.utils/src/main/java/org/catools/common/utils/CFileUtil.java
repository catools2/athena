package org.catools.common.utils;

import org.apache.commons.io.FileUtils;
import org.catools.common.exception.CFileNotFoundException;
import org.catools.common.exception.CFileOperationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Normally we should not extend Utility classes but this class is just an extension so we can have
 * one source for all related utils. So we do not have different class for file utility in code and
 * in case of change in parent, cost of maintenance is still the same.
 *
 * <p>General file manipulation utilities. 1- Automation is internal tools and end user totally have
 * control on flow. 2- Also, the test automation flow usually cross several app tier and uses
 * different utils. Being said, having unchecked exceptions will impact code readability and large
 * list of throws on test methods. To avoid it we wrap some FileUtils methods to throw specific
 * runtime exceptions here.
 */
public class CFileUtil extends FileUtils {
  static {
    System.setProperty("file.encoding", "UTF-8");
  }

  public static File fromRemote(String ip, File parent, String child) {
    return fromRemote(ip, new File(parent, child));
  }

  public static File fromRemote(String ip, String fullName) {
    return fromRemote(ip, new File(fullName));
  }

  public static File fromRemote(String ip, File dest) {
    File file =
        new File(String.format("\\\\%s\\%s", ip, getCanonicalPath(dest).replaceFirst(":", "\\$")));
    if (!file.exists()) {
      throw new RuntimeException("The destination file not found after copy:" + file);
    }
    return file;
  }

  public static File fromURL(URL url, File parent, String child) {
    return fromURL(url, new File(parent, child));
  }

  public static File fromURL(URL url, String fullName) {
    return fromURL(url, new File(fullName));
  }

  public static File fromURL(URL url, File destination) {
    try {
      FileUtils.copyURLToFile(url, destination);
    } catch (IOException e) {
      throw new RuntimeException("Failed to copy from remote url: " + url, e);
    }

    if (!destination.exists()) {
      throw new RuntimeException("The destination file not found after copy:" + destination);
    }
    return destination;
  }

  /**
   * Reads all characters from a file into a string, decoding from bytes to characters using default
   * {@linkplain Charset charset}. The method ensures that the file is closed when all content have
   * been read or an I/O error, or other runtime exception, is thrown.
   *
   * <p>This method reads all content including the line separators in the middle and/or at the end.
   * The resulting string will contain line separators as they appear in the file.
   *
   * @return a String containing the content read from the file
   * @throws CFileOperationException if anything goes wrong
   */
  public static String readString(File file) {
    return readString(file, Charset.defaultCharset());
  }

  /**
   * Reads all characters from a file into a string, decoding from bytes to characters using the
   * specified {@linkplain Charset charset}. The method ensures that the file is closed when all
   * content have been read or an I/O error, or other runtime exception, is thrown.
   *
   * <p>This method reads all content including the line separators in the middle and/or at the end.
   * The resulting string will contain line separators as they appear in the file.
   *
   * @param charset the charset to use for decoding
   * @return a String containing the content read from the file
   * @throws CFileOperationException if anything goes wrong
   */
  public static String readString(File file, Charset charset) {
    try {
      return FileUtils.readFileToString(file, charset);
    } catch (Throwable e) {
      throw new CFileOperationException(file, "Failed to read from the file.", e);
    }
  }

  /**
   * Returns the canonical form of this abstract pathname. Equivalent to <code>
   * new&nbsp;File(this.{@see #getCanonicalPath})</code>.
   *
   * @return The canonical pathname string denoting the same file or directory as this abstract
   * pathname
   */
  public static File getCanonicalFile(File file) {
    try {
      return file.getCanonicalFile();
    } catch (Exception e) {
      throw new CFileOperationException(
          file, "Failed to get the Canonical path as file to the file", e);
    }
  }

  /**
   * Returns the canonical pathname string of this abstract pathname.
   *
   * <p>Every pathname that denotes an existing file or directory has a unique canonical form. Every
   * pathname that denotes a nonexistent file or directory also has a unique canonical form. The
   * canonical form of the pathname of a nonexistent file or directory may be different from the
   * canonical form of the same pathname after the file or directory is created. Similarly, the
   * canonical form of the pathname of an existing file or directory may be different from the
   * canonical form of the same pathname after the file or directory is deleted.
   *
   * @return The canonical pathname string denoting the same file or directory as this abstract
   * pathname
   */
  public static String getCanonicalPath(File file) {
    try {
      return file.getCanonicalPath();
    } catch (Throwable e) {
      throw new CFileOperationException(file, "Failed to get the Canonical path to the file", e);
    }
  }

  /**
   * Returns the File which refer to the abstract pathname's child file.
   *
   * @param filename the path to the child file
   * @return the File which refer to the abstract pathname's child file.
   */
  public File getChildFile(File file, String filename) {
    file.mkdirs();
    return new File(file, filename);
  }

  /**
   * Returns the File which refer to the abstract pathname's child folder.
   *
   * @param filename the path to the child folder
   * @return the File which refer to the abstract pathname's child folder.
   */
  public static File getChildFolder(File file, String filename) {
    File File = new File(file, filename);
    File.mkdirs();
    return File;
  }

  /**
   * Get the Input Stream for the file
   *
   * @return the Input Stream for the file
   */
  public static InputStream getInputStream(File file) {
    try {
      return new FileInputStream(file);
    } catch (Exception e) {
      throw new CFileNotFoundException(file, e);
    }
  }

  /**
   * Read all lines from a file. This method ensures that the file is closed when all bytes have
   * been read or an I/O error, or other runtime exception, is thrown. Bytes from the file are
   * decoded into characters using the specified charset.
   *
   * <p>This method recognizes the following as line terminators:
   *
   * <ul>
   *   <li><code>&#92;u000D</code> followed by <code>&#92;u000A</code>, CARRIAGE RETURN followed by
   *       LINE FEED
   *   <li><code>&#92;u000A</code>, LINE FEED
   *   <li><code>&#92;u000D</code>, CARRIAGE RETURN
   * </ul>
   *
   * <p>Additional Unicode line terminators may be recognized in future releases.
   *
   * <p>Note that this method is intended for simple cases where it is convenient to read all lines
   * in a single operation. It is not intended for reading in large files.
   *
   * @return the lines from the file as a {@code CList}; whether the {@code CList} is modifiable or
   * not is implementation dependent and therefore not specified
   * @throws CFileOperationException if anything goes wrong
   * @see List contains all lines from the file
   */
  public static List<String> readLines(File file) {
    return readLines(file, Charset.defaultCharset());
  }

  /**
   * Read all lines from a file. This method ensures that the file is closed when all bytes have
   * been read or an I/O error, or other runtime exception, is thrown. Bytes from the file are
   * decoded into characters using the specified charset.
   *
   * <p>This method recognizes the following as line terminators:
   *
   * <ul>
   *   <li><code>&#92;u000D</code> followed by <code>&#92;u000A</code>, CARRIAGE RETURN followed by
   *       LINE FEED
   *   <li><code>&#92;u000A</code>, LINE FEED
   *   <li><code>&#92;u000D</code>, CARRIAGE RETURN
   * </ul>
   *
   * <p>Additional Unicode line terminators may be recognized in future releases.
   *
   * @param charset the charset to use for decoding
   * @return the lines from the file as a {@code List}; whether the {@code List} is modifiable or
   * not is implementation dependent and therefore not specified
   * @throws CFileOperationException if anything goes wrong
   * @see List contains all lines from the file
   */
  public static List<String> readLines(File file, Charset charset) {
    try {
      return Files.readAllLines(file.toPath(), charset);
    } catch (Throwable e) {
      throw new CFileOperationException(file, "Failed to read lines from the file.", e);
    }
  }

  public static void writeByteArrayToFile(File file, byte[] data) {
    writeByteArrayToFile(file, data, false);
  }

  public static void writeByteArrayToFile(File file, byte[] data, boolean append) {
    writeByteArrayToFile(file, data, 0, data.length, append);
  }

  public static void writeByteArrayToFile(File file, byte[] data, int off, int len) {
    writeByteArrayToFile(file, data, off, len, false);
  }

  public static void writeByteArrayToFile(
      File file, byte[] data, int off, int len, boolean append) {
    try {
      FileUtils.writeByteArrayToFile(file, data, off, len, append);
    } catch (IOException e) {
      throw new CFileOperationException(file, "Failed to write byte array to file.", e);
    }
  }

  public static void forceMkdir(File directory) {
    try {
      FileUtils.forceMkdir(directory);
    } catch (IOException e) {
      throw new CFileOperationException(directory, "Failed to create directory", e);
    }
  }

  public static byte[] readFileToByteArray(File file) {
    try {
      return FileUtils.readFileToByteArray(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String copyToRemoteFolder(File file, String remoteIP, File destFolder) {
    try {
      File remoteFolder = fromRemote(remoteIP, destFolder);
      remoteFolder.mkdirs();
      File destFile = new File(remoteFolder, file.getName());
      CFileUtil.copyFile(file, destFile);
      return getCanonicalPath(destFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static File copyFromRemoteFolder(String remoteIP, File srcFile, File destFile) {
    try {
      File remoteFolder = new File(getRemoteFileName(remoteIP, srcFile));
      CFileUtil.copyFile(remoteFolder, destFile);
      return destFile;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String getRemoteFileName(String remoteIP, File destFolder) {
    String root = Paths.get(getCanonicalPath(destFolder)).getRoot().toString();
    String absolutePath = destFolder.getAbsolutePath();
    absolutePath = absolutePath.substring(absolutePath.lastIndexOf(root));
    return CRegExUtil.replaceAll(
        absolutePath.replace(root, "//" + remoteIP + "/" + root.replace(":", "$")),
        "\\\\(\\.\\\\)?",
        "/");
  }
}

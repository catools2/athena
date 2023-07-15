package org.catools.common.io;

import org.apache.commons.io.IOUtils;
import org.catools.common.collections.CList;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.exception.CFileNotFoundException;
import org.catools.common.exception.CFileOperationException;
import org.catools.common.extensions.types.interfaces.CDynamicFileExtension;
import org.catools.common.utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CFile extends File implements CDynamicFileExtension {
  static {
    System.setProperty("file.encoding", "UTF-8");
  }

  public CFile(File file) {
    this(file.toURI());
  }

  public CFile(String pathname) {
    super(pathname);
  }

  public CFile(String parent, String child) {
    super(parent, child);
  }

  public CFile(File parent, String child) {
    super(parent, child);
  }

  public CFile(URI uri) {
    super(uri);
  }

  public static CFile of(File file) {
    return new CFile(file);
  }

  public static CFile of(String pathname) {
    return new CFile(pathname);
  }

  public static CFile of(String parent, String child) {
    return new CFile(parent, child);
  }

  public static CFile of(File parent, String child) {
    return new CFile(parent, child);
  }

  public static CFile of(URI uri) {
    return new CFile(uri);
  }

  public static CFile fromRemote(String ip, File parent, String child) {
    return fromRemote(ip, new CFile(parent, child));
  }

  public static CFile fromRemote(String ip, String fullName) {
    return fromRemote(ip, new CFile(fullName));
  }

  public static CFile fromRemote(String ip, File path) {
    return CFile.of(CFileUtil.fromRemote(ip, path));
  }

  public static CFile fromURL(URL url, File parent, String child) {
    return fromURL(url, new CFile(parent, child));
  }

  public static CFile fromURL(URL url, String fullName) {
    return fromURL(url, new CFile(fullName));
  }

  public static CFile fromURL(URL url, CFile destination) {
    return of(CFileUtil.fromURL(url, destination));
  }

  /**
   * return a CFile which is pointing to the file in current folder.
   *
   * @param child file name to be referred to
   * @return a CFile which is pointing to the child
   */
  public static CFile fromCurrent(String child) {
    return new CFile(".", child);
  }

  /**
   * return a CFile which is pointing to the file in parent folder.
   *
   * @param parent file
   * @param child  file name to be referred to
   * @return a CFile which is pointing to the child
   */
  public static CFile getChild(String parent, String child) {
    return new CFile(parent, child);
  }

  /**
   * return a CFile which is pointing to the file in parent folder.
   *
   * @param parent file
   * @param child  file name to be referred to
   * @return a CFile which is pointing to the child
   */
  public static CFile getChild(File parent, String child) {
    return of(parent, child);
  }

  /**
   * return a File which is pointing to the file in output folder.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the child
   */
  public static CFile fromOutput(String child) {
    return of(CPathConfigs.fromOutput(child));
  }

  /**
   * return a File which is pointing to the file in current storage.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the file
   */
  public static CFile fromStorage(String child) {
    return of(CPathConfigs.fromStorage(child));
  }

  /**
   * return a File which is pointing to the file in {@link CPathConfigs#getTempFolder()} folder.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the file
   */
  public static CFile fromTmp(String child) {
    return of(CPathConfigs.fromTmp(child));
  }

  /**
   * Append a text to a file creating the file if it does not exist. The parent directories of the
   * file will be created if they do not exist.
   *
   * @param content the content to write to the file
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile append(String content) {
    return append(content, Charset.defaultCharset());
  }

  /**
   * Append a text to a file creating the file if it does not exist. The parent directories of the
   * file will be created if they do not exist.
   *
   * @param content the content to write to the file
   * @param charset the charset to use
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile append(String content, Charset charset) {
    try {
      getParentFile().mkdirs();
      CFileUtil.writeStringToFile(this, content, charset, true);
      return this;
    } catch (Throwable e) {
      throw new CFileOperationException(this, "Failed to write to the file.", e);
    }
  }

  /**
   * Append a byte array to a file creating the file if it does not exist. The parent directories of
   * the file will be created if they do not exist.
   *
   * @param content the content to write to the file
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile append(byte[] content) {
    try {
      getParentFile().mkdirs();
      CFileUtil.writeByteArrayToFile(this, content, true);
      return this;
    } catch (Throwable e) {
      throw new CFileOperationException(this, "Failed to write to the file.", e);
    }
  }

  /**
   * Copies a whole directory or file to a new location preserving the file dates.
   *
   * <p>If the current file is directory then this method copies the specified directory and all its
   * child directories and files to the specified destination. The destination is the new location
   * and name of the directory.
   *
   * <p><strong>Note:</strong>
   *
   * <p>If the destination directory did exist, then this method merges the source with the
   * destination, with the source taking precedence.
   *
   * <p>If the current file is not directory then this method copy it to the destination.
   *
   * <p>The destination directory is created if it does not exist.
   *
   * <p>This method tries to preserve the files' last modified date/times using {@link
   * File#setLastModified(long)}, however it is not guaranteed that those operations will succeed.
   * If the modification operation fails, no indication is provided.
   *
   * @param destFile where file or directory should be copy
   * @return current instance
   */
  public CFile copy(final File destFile) {
    if (isDirectory()) {
      try {
        CFileUtil.copyDirectory(this, destFile);
      } catch (Throwable t) {
        throw new CFileOperationException(
            this, "Failed to copy the file to destination file: " + destFile.getAbsolutePath());
      }
    } else {
      copyFile(destFile);
    }
    return new CFile(destFile);
  }

  /**
   * Copies a file to a new location preserving the file date.
   *
   * <p>This method copies the contents of the specified source file to the specified destination
   * file. The directory holding the destination file is created if it does not exist. If the
   * destination file exists, then this method will overwrite it.
   *
   * <p><strong>Note:</strong> This method tries to preserve the file's last modified date/times
   * using {@link File#setLastModified(long)}, however it is not guaranteed that the operation will
   * succeed. If the modification operation fails, no indication is provided.
   *
   * @param destFile the new file, must not be {@code null}
   * @return current instance of CFile
   * @throws CFileOperationException if something goes wrong
   */
  public CFile copyFile(File destFile) {
    try {
      getParentFile().mkdirs();
      CFileUtil.copyFile(this, destFile);
      return new CFile(destFile);
    } catch (Throwable e) {
      throw new CFileOperationException(this, "Failed to copy file to " + destFile, e);
    }
  }

  /**
   * Atomically creates a new, empty file named by this abstract pathname if and only if a file with
   * this name does not yet exist. The check for the existence of the file and the creation of the
   * file if it does not exist are a single operation that is atomic with respect to all other
   * filesystem activities that might affect the file.
   *
   * <p>Note: this method should <i>not</i> be used for file-locking, as the resulting protocol
   * cannot be made to work reliably. The {@link java.nio.channels.FileLock FileLock} facility
   * should be used instead.
   *
   * @return <code>true</code> if the named file does not exist and was successfully created; <code>
   * false</code> if the named file already exists
   */
  @Override
  public boolean createNewFile() {
    try {
      return super.createNewFile();
    } catch (Throwable e) {
      throw new CFileOperationException(this, "Failed to create the file.", e);
    }
  }

  /**
   * Deletes the file or directory denoted by this abstract pathname. If this pathname denotes a
   * directory, then the directory will be clean first.
   *
   * @return <code>true</code> if and only if the file or directory is successfully deleted; <code>
   * false</code> otherwise
   */
  @Override
  public boolean delete() {
    try {
      if (isDirectory()) {
        CFileUtil.cleanDirectory(this);
      }

      return super.delete();
    } catch (Throwable e) {
      return false;
    }
  }

  /**
   * Deletes a file. If file is a directory, delete it and all sub-directories.
   *
   * <p>The difference between {@see #delete()} and this method are:
   *
   * <ul>
   *   <li>A directory to be deleted does not have to be empty.
   *   <li>You get exceptions when a file or directory cannot be deleted.
   * </ul>
   *
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile forceDelete() {
    CRetry.retryIf(
        c -> {
          try {
            CFileUtil.forceDelete(this);
            return this;
          } catch (Throwable e) {
            throw new CFileOperationException(this, "Failed to delete file.", e);
          }
        },
        CFile::exists,
        3,
        1000,
        null);
    return this;
  }

  /**
   * Delete the file or directory then if the current is directory then create an empty one; if it
   * is file then do nothing.
   *
   * @return true if the directory been created otherwise false
   */
  public boolean forceMkdirs() {
    delete();
    return super.mkdirs();
  }

  /**
   * Returns the canonical form of this abstract pathname. Equivalent to <code>
   * new&nbsp;File(this.{@see #getCanonicalPath})</code>.
   *
   * @return The canonical pathname string denoting the same file or directory as this abstract
   * pathname
   */
  @Override
  public CFile getCanonicalFile() {
    try {
      return new CFile(super.getCanonicalFile());
    } catch (Throwable t) {
      throw new CFileOperationException(
          this, "Failed to get the Canonical path as file to the file", t);
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
  @Override
  public String getCanonicalPath() {
    try {
      return super.getCanonicalPath();
    } catch (Throwable e) {
      throw new CFileOperationException(this, "Failed to get the Canonical path to the file", e);
    }
  }

  /**
   * Returns the CFile which refer to the abstract pathname's child file.
   *
   * @param filename the path to the child file
   * @return the CFile which refer to the abstract pathname's child file.
   */
  public CFile getChildFile(String filename) {
    mkdirs();
    return new CFile(this, filename);
  }

  /**
   * Returns the CFile which refer to the abstract pathname's child folder.
   *
   * @param filename the path to the child folder
   * @return the CFile which refer to the abstract pathname's child folder.
   */
  public CFile getChildFolder(String filename) {
    CFile cFile = new CFile(this, filename);
    cFile.mkdirs();
    return cFile;
  }

  /**
   * Get the Input Stream for the file
   *
   * @return the Input Stream for the file
   */
  public InputStream getInputStream() {
    try {
      return new FileInputStream(this);
    } catch (Throwable t) {
      throw new CFileNotFoundException(this, t);
    }
  }

  /**
   * Returns the CFile which refer to the abstract pathname's parent, or <code>null</code> if
   * current file does not name a parent directory.
   *
   * <p>The <em>parent</em> of an abstract pathname consists of the pathname's prefix, if any, and
   * each name in the pathname's name sequence except for the last. If the name sequence is empty
   * then the pathname does not name a parent directory.
   *
   * @return The abstract pathname of the parent directory named by this abstract pathname, or
   * <code>null</code> if this pathname does not name a parent
   */
  @Override
  public CFile getParentFile() {
    return new CFile(super.getParentFile());
  }

  /**
   * Move file to new destination and return the {@link CFile}: which points to new file.
   *
   * @param dest where the file should be move to
   * @return the {@link CFile}: which points to new file
   */
  public CFile moveTo(File dest) {
    copyFile(dest);
    forceDelete();
    return new CFile(dest);
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
   * @see CList contains all lines from the file
   */
  public CList<String> readLines() {
    return readLines(Charset.defaultCharset());
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
   * @see CList contains all lines from the file
   */
  public CList<String> readLines(Charset charset) {
    try {
      return new CList<>(Files.readAllLines(toPath(), charset));
    } catch (Throwable e) {
      throw new CFileOperationException(this, "Failed to read lines from the file.", e);
    }
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
  public String readString() {
    return readString(Charset.defaultCharset());
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
  public String readString(Charset charset) {
    try {
      return Files.readString(toPath(), charset);
    } catch (Throwable e) {
      throw new CFileOperationException(this, "Failed to read from the file.", e);
    }
  }

  /**
   * If the destination file exist and the overwrite parameter is false then do nothing. If the
   * destination file exist and the overwrite parameter is true then delete the destination file.
   * After that get {@see #renameTo(File)} and then delete the original file
   *
   * @param destFile  where the file or directory should copy to
   * @param overwrite whether we should overwrite the destination if it is exists
   * @return CFile pointing to the destination
   */
  public CFile safeRename(File destFile, boolean overwrite) {
    if (destFile.exists()) {
      if (overwrite) {
        if (!destFile.delete()) {
          throw new CFileOperationException(
              this,
              "Failed to delete the destination file before rename it. destination file: "
                  + destFile.getAbsolutePath());
        }
      } else {
        return this;
      }
    }

    return moveTo(destFile);
  }

  /**
   * Wait for {@code timeoutInSeconds} for file to be available and writable
   *
   * @param timeoutInSeconds timeout in seconds to wait for file to be available
   * @return true if file is available and writable otherwise false
   */
  public boolean waitForWritable(int timeoutInSeconds) {
    return waitForWritable(timeoutInSeconds, 100);
  }

  /**
   * Wait for {@code timeoutInSeconds} for file to be available and writable
   *
   * @param timeoutInSeconds      timeout in seconds to wait for file to be available
   * @param intervalInMillSeconds the interval of milliseconds between each retry
   * @return true if file is available and writable otherwise false
   */
  public boolean waitForWritable(int timeoutInSeconds, int intervalInMillSeconds) {
    int retryCount = (timeoutInSeconds * 1000) / intervalInMillSeconds;
    return CRetry.retryIfFalse(
        integer -> exists() && canWrite(), retryCount, intervalInMillSeconds, () -> false, false);
  }

  /**
   * Wait for {@code timeoutInSeconds} for file to be available
   *
   * @param timeoutInSeconds timeout in seconds to wait for file to be available
   * @return true if file is available otherwise false
   */
  public boolean waitToExists(int timeoutInSeconds) {
    return waitToExists(timeoutInSeconds, 100);
  }

  /**
   * Wait for {@code timeoutInSeconds} for file to be available
   *
   * @param timeoutInSeconds      timeout in seconds to wait for file to be available
   * @param intervalInMillSeconds the interval of milliseconds between each retry
   * @return true if file is available otherwise false
   */
  public boolean waitToExists(int timeoutInSeconds, int intervalInMillSeconds) {
    int retryCount = (timeoutInSeconds * 1000) / intervalInMillSeconds;
    return CRetry.retryIfFalse(integer -> exists(), retryCount, intervalInMillSeconds, null, false);
  }

  /**
   * Writes an object as json to a file creating the file if it does not exist. The parent
   * directories of the file will be created if they do not exist.
   *
   * @param object the object to be serialize and write to the file
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile writeJson(Object object) {
    return writeJson(object, true);
  }

  /**
   * Writes an object as json to a file creating the file if it does not exist. The parent
   * directories of the file will be created if they do not exist.
   *
   * @param object      the object to be serialize and write to the file
   * @param prettyPrint if output should be pretty print
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile writeJson(Object object, boolean prettyPrint) {
    CJsonUtil.write(this, object, prettyPrint);
    return this;
  }

  /**
   * Writes an object as json to a file creating the file if it does not exist. The parent
   * directories of the file will be created if they do not exist.
   *
   * @param clazz the object class to be deserialize
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public <T> T readJson(Class<T> clazz) {
    return readJson(clazz, Charset.defaultCharset());
  }

  /**
   * Writes an object as json to a file creating the file if it does not exist. The parent
   * directories of the file will be created if they do not exist.
   *
   * @param clazz   the object class to be deserialize
   * @param charset the charset to use
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public <T> T readJson(Class<T> clazz, Charset charset) {
    return CJsonUtil.read(readString(charset), clazz);
  }

  /**
   * Writes a text to a file creating the file if it does not exist. The parent directories of the
   * file will be created if they do not exist.
   *
   * @param content the content to write to the file
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile write(String content) {
    return write(content, Charset.defaultCharset());
  }

  /**
   * Writes a text to a file creating the file if it does not exist. The parent directories of the
   * file will be created if they do not exist.
   *
   * @param content the content to write to the file
   * @param charset the charset to use
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile write(String content, Charset charset) {
    try {
      getParentFile().mkdirs();
      CFileUtil.writeStringToFile(this, content, charset);
      return this;
    } catch (Throwable e) {
      throw new CFileOperationException(this, "Failed to write to the file.", e);
    }
  }

  /**
   * Writes a byte array to a file creating the file if it does not exist. The parent directories of
   * the file will be created if they do not exist.
   *
   * @param content the content to write to the file
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile write(byte[] content) {
    try {
      getParentFile().mkdirs();
      CFileUtil.writeByteArrayToFile(this, content);
      return this;
    } catch (Throwable e) {
      throw new CFileOperationException(this, "Failed to write to the file.", e);
    }
  }

  /**
   * Writes a byte array to a file creating the file if it does not exist. The parent directories of
   * the file will be created if they do not exist.
   *
   * @param inputStream the input stream
   * @return current instance of CFile
   * @throws CFileOperationException if anything goes wrong
   */
  public CFile write(InputStream inputStream) {
    return write(CInputStreamUtil.toByteArray(inputStream));
  }

  /**
   * Convert file to byte array and return the result.
   *
   * @return file data in <code>byte[]</code> format
   * @throws CFileOperationException for any IOException
   */
  public byte[] getBytes() {
    try {
      return IOUtils.toByteArray(getInputStream());
    } catch (IOException e) {
      throw new CFileOperationException(this, "Failed to read file", e);
    }
  }

  /**
   * Check whether the file or directory denoted by this abstract pathname exists.
   *
   * @return <code>true</code> if and only if the file or directory denoted by this abstract
   * pathname does not exist; <code>false</code> otherwise
   * @throws SecurityException If a security manager exists and denies read access to the
   *                           file or directory
   */
  public boolean notExist() {
    return !exists();
  }

  public CFile copyToRemoteFolder(String remoteIP, File destFolder) {
    try {
      CFile remoteFolder = new CFile(getRemoteFileName(remoteIP, destFolder));
      remoteFolder.mkdirs();
      CFile destFile = new CFile(remoteFolder, getName());
      CFileUtil.copyFile(this, destFile);
      return destFile;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public CFile copyFromRemoteFolder(String remoteIP, File destFolder) {
    try {
      CFile remoteFolder = new CFile(getRemoteFileName(remoteIP, destFolder));
      remoteFolder.mkdirs();
      CFile destFile = new CFile(remoteFolder, getName());
      CFileUtil.copyFile(destFile, this);
      return this;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static String getRemoteFileName(String remoteIP, File destFolder) {
    String root = Paths.get(new CFile(destFolder).getCanonicalPath()).getRoot().toString();
    String absolutePath = destFolder.getAbsolutePath();
    absolutePath = absolutePath.substring(absolutePath.lastIndexOf(root));
    return CRegExUtil.replaceAll(
        absolutePath.replace(root, "//" + remoteIP + "/" + root.replace(":", "$")),
        "\\\\(\\.\\\\)?",
        "/");
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public CFile _get() {
    return this;
  }
}

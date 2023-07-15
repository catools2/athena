package org.catools.common.serialization;

import lombok.experimental.UtilityClass;
import org.catools.common.exception.CRuntimeException;

import java.io.*;

/**
 * CObjectSerializationUtil contains method to read/write object from/to file.
 */
@UtilityClass
public class CObjectSerializationUtil {
  /**
   * Read Object From File
   *
   * @param file file name from which we should read file
   * @param <T>  Object Type
   * @return De-serialized Object
   */
  public static <T> T read(File file) {
    try (FileInputStream fileInputStream = new FileInputStream(file)) {
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      return (T) objectInputStream.readObject();
    } catch (Throwable t) {
      throw new CRuntimeException("Failed to deserialize file " + file, t);
    }
  }

  /**
   * Write object to file
   *
   * @param file file to write object into
   * @param t    Object to serialize
   * @param <T>  Object type
   * @throws IOException
   */
  public static <T> void write(File file, T t) {
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(t);
      objectOutputStream.flush();
    } catch (Throwable t1) {
      throw new CRuntimeException("Failed to serialize to file " + file, t1);
    }
  }
}

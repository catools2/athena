package org.catools.common.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.hocon.CHocon;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.utils.CConfigUtil;
import org.catools.common.utils.CFileUtil;

import java.io.File;

@UtilityClass
public class CPathConfigs {

  /**
   * Get path to the resource folder inside temp folder. See {@link CPathConfigs#getTempFolder()}.
   *
   * @return path to tmp resource folder
   */
  public static File getTmpResourcesFolder() {
    return getTempChildFolder("resources");
  }

  public static File getUserDownloadFolder() {
    return getHomeChildFolder("downloads");
  }

  /**
   * Get path to the uploads folder inside temp folder. See {@link CPathConfigs#getTempFolder()}.
   *
   * @return path to tmp uploads folder
   */
  public static File getTmpUploadFolder() {
    return getTempChildFolder("uploads");
  }

  public static File getLocalConfigFolder() {
    File properties = getStorageChildFolder("configs");
    properties.mkdirs();
    return properties;
  }

  public static File getOutputFolder() {
    String runName = StringUtils.defaultString(CConfigUtil.getRunName());
    File file = new File(CHocon.asString(Configs.CATOOLS_PATH_OUTPUT_DIRECTORY) + runName);
    file.mkdirs();
    return file;
  }

  public static String getOutputPath() {
    return CFileUtil.getCanonicalPath(getOutputFolder());
  }

  public static File getOutputRoot() {
    File output = new File(CHocon.asString(Configs.CATOOLS_PATH_OUTPUT_DIRECTORY));
    CFileUtil.forceMkdir(output);
    return output;
  }

  /**
   * return a File which is pointing to the file in current folder.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the child
   */
  public static File fromCurrent(String child) {
    return new File(".", child);
  }

  /**
   * return a File which is pointing to the file in output folder.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the child
   */
  public static File fromOutput(String child) {
    return new File(CPathConfigs.getOutputPath(), child);
  }

  /**
   * return a File which is pointing to the file in current storage.
   *
   * @param child file name to be referred to
   * @return a File which is pointing to the file
   */
  public static File fromStorage(String child) {
    File file = new File(CPathConfigs.getStorageFolder(), child);
    file.mkdirs();
    return file;
  }

  /**
   * return a File which is pointing to the file in {@link CPathConfigs#getTempFolder()} folder.
   *
   * @param child file name to be referred to inside temp folder
   * @return a File which is pointing to the file
   */
  public static File fromTmp(String child) {
    return new File(CPathConfigs.getTempFolder(), child);
  }

  /**
   * return a File which is pointing to the file in {@link CPathConfigs#getHomeFolder()} folder.
   *
   * @param child file name to be referred to inside user.home folder
   * @return a File which is pointing to the file
   */
  public static File fromHome(String child) {
    return new File(CPathConfigs.getHomeFolder(), child);
  }

  public static File getStorageFolder() {
    File file = new File(CHocon.asString(Configs.CATOOLS_PATH_STORAGE_DIRECTORY));
    file.mkdirs();
    return file;
  }

  public static File getImagesFolder() {
    return fromOutput("images");
  }

  public static File getHomeFolder() {
    File file = new File(CHocon.asString(Configs.CATOOLS_PATH_HOME_DIRECTORY));
    file.mkdirs();
    return file;
  }

  public static File getTempFolder() {
    return getOutputChildFolder("tmp");
  }

  public static File getOutputChildFolder(String childFolder) {
    File file = fromOutput(childFolder);
    file.mkdirs();
    return file;
  }

  public static File getHomeChildFolder(String childFolder) {
    File file = fromHome(childFolder);
    file.mkdirs();
    return file;
  }

  public static File getTempChildFolder(String childFolder) {
    File file = fromTmp(childFolder);
    file.mkdirs();
    return file;
  }

  public static File getStorageChildFolder(String childFolder) {
    File file = fromStorage(childFolder);
    file.mkdirs();
    return file;
  }

  public static File getOutputChildFile(String childFolder) {
    return fromOutput(childFolder);
  }

  public static File getTempChildFile(String childFolder) {
    return fromTmp(childFolder);
  }

  public static File getStorageChildFile(String childFolder) {
    return fromStorage(childFolder);
  }

  public static File getOutputActualImagesFolder() {
    return CFileUtil.getChildFolder(getImagesFolder(), "actual");
  }

  public static File getOutputExpectedImagesFolder() {
    return CFileUtil.getChildFolder(getImagesFolder(), "expected");
  }

  public static File getOutputDiffImagesFolder() {
    return CFileUtil.getChildFolder(getImagesFolder(), "diff");
  }

  @Getter
  @AllArgsConstructor
  private enum Configs implements CHoconPath {
    CATOOLS_PATH_HOME_DIRECTORY("catools.paths.home_directory"),
    CATOOLS_PATH_STORAGE_DIRECTORY("catools.paths.storage_directory"),
    CATOOLS_PATH_OUTPUT_DIRECTORY("catools.paths.output_directory");

    private final String path;
  }
}

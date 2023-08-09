package org.catools.common.hocon;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import org.apache.commons.lang3.StringUtils;
import org.catools.common.configs.CPathConfigs;
import org.catools.common.exception.CFileOperationException;
import org.catools.common.hocon.exception.CHoconException;
import org.catools.common.hocon.model.CHoconConfig;
import org.catools.common.hocon.model.CHoconPath;
import org.catools.common.hocon.utils.CHoconUtils;
import org.catools.common.utils.CFileUtil;
import org.catools.common.utils.CRetry;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * A class to work safe with Type Safe Configuration
 */
public class CHocon {
  private static Config CONFIG;

  public static final String CONFIGS_TO_LOAD = "CONFIGS_TO_LOAD";

  /**
   * Load configuration and set them in System.properties
   */
  public synchronized static void reload() {
    ConfigFactory.invalidateCaches();
    String configToLoad = System.getProperty(CONFIGS_TO_LOAD);
    CONFIG = configToLoad != null ? ConfigFactory.load(configToLoad) : ConfigFactory.load();
    getUserDefinedSettings()
        .forEach(
            entry -> {
              String key = entry.getKey();
              String propName = CHoconUtils.pathToEnvVariableName(key);
              if (System.getProperty(propName) == null) {
                System.setProperty(propName, CONFIG.getValue(key).unwrapped().toString());
              }
            });
  }

  public static Stream<Map.Entry<String, ConfigValue>> getUserDefinedSettings() {
    return CONFIG
        .entrySet()
        .stream()
        .filter(entry -> entry.getValue().origin().resource() != null);
  }

  private synchronized static void cleaUp() {
    if (StringUtils.isNotBlank(CPathConfigs.getOutputPath())) {
      File outputRoot = CPathConfigs.getOutputRoot();
      CRetry.retry(idx -> {
        try {
          CFileUtil.deleteDirectory(outputRoot);
          return true;
        } catch (IOException e) {
          throw new CFileOperationException(outputRoot, "Delete output directory.");
        }
      }, 30, 1000);
    }
  }

  public static Config getConfig() {
    if (CONFIG == null) {
      try {
        reload();
        cleaUp();
      } catch (Exception e) {
        throw new CHoconException("Failed to initialize hocon", e);
      }
    }
    return CONFIG;
  }

  public static CHoconConfig get(String path) {
    return new CHoconConfig(getConfig(), path);
  }

  public static <T extends CHoconPath> CHoconConfig get(T config) {
    return new CHoconConfig(getConfig(), config.getPath());
  }

  public static boolean has(String path) {
    return get(path).isDefined();
  }

  public static <T extends CHoconPath> boolean has(T config) {
    return get(config).isDefined();
  }

  // Shortcuts
  public static String asString(String path) {
    return get(path).asString();
  }

  public static <T extends CHoconPath> String asString(T config) {
    return get(config).asString();
  }

  public static Boolean asBoolean(String path) {
    return get(path).asBoolean();
  }

  public static <T extends CHoconPath> Boolean asBoolean(T config) {
    return get(config).asBoolean();
  }

  public static Number asNumber(String path) {
    return get(path).asNumber();
  }

  public static <T extends CHoconPath> Number asNumber(T config) {
    return get(config).asNumber();
  }

  public static Integer asInteger(String path) {
    return get(path).asInteger();
  }

  public static <T extends CHoconPath> Integer asInteger(T config) {
    return get(config).asInteger();
  }

  public static Long asLong(String path) {
    return get(path).asLong();
  }

  public static <T extends CHoconPath> Long asLong(T config) {
    return get(config).asLong();
  }

  public static Double asDouble(String path) {
    return get(path).asDouble();
  }

  public static <T extends CHoconPath> Double asDouble(T config) {
    return get(config).asDouble();
  }

  public static <E extends Enum<E>> E asEnum(String path, Class<E> aClass) {
    return get(path).asEnum(aClass);
  }

  public static <T extends CHoconPath, E extends Enum<E>> E asEnum(T config, Class<E> aClass) {
    return get(config).asEnum(aClass);
  }

  public static Object asObject(String path) {
    return get(path).asObject();
  }

  public static <T extends CHoconPath> Object asObject(T config) {
    return get(config).asObject();
  }

  public static List<Boolean> asBooleans(String path) {
    return get(path).asBooleans();
  }

  public static <T extends CHoconPath> List<Boolean> asBooleans(T config) {
    return get(config).asBooleans();
  }

  public static List<Number> asNumbers(String path) {
    return get(path).asNumbers();
  }

  public static <T extends CHoconPath> List<Number> asNumbers(T config) {
    return get(config).asNumbers();
  }

  public static List<Integer> asIntegers(String path) {
    return get(path).asIntegers();
  }

  public static <T extends CHoconPath> List<Integer> asIntegers(T config) {
    return get(config).asIntegers();
  }

  public static List<Long> asLongs(String path) {
    return get(path).asLongs();
  }

  public static <T extends CHoconPath> List<Long> asLongs(T config) {
    return get(config).asLongs();
  }

  public static List<Double> asDoubles(String path) {
    return get(path).asDoubles();
  }

  public static <T extends CHoconPath> List<Double> asDoubles(T config) {
    return get(config).asDoubles();
  }

  public static List<String> asStrings(String path) {
    return get(path).asStrings();
  }

  public static <T extends CHoconPath> List<String> asStrings(T config) {
    return get(config).asStrings();
  }

  public static <T extends CHoconPath, E extends Enum<E>> List<E> asEnumList(String path, Class<E> aClass) {
    return get(path).asEnumList(aClass);
  }

  public static <T extends CHoconPath, E extends Enum<E>> List<E> asEnumList(T config, Class<E> aClass) {
    return get(config).asEnumList(aClass);
  }

  public static List<? extends Object> asObjects(String path) {
    return get(path).asObjects();
  }

  public static <T extends CHoconPath> List<? extends Object> asObjects(T config) {
    return get(config).asObjects();
  }

  /**
   * Read model using Type Safe Configuration implementation or Jackson
   *
   * @param clazz model class type
   * @param <T>   class Type
   * @return the model
   */
  public static <T extends CHoconPath, M> M asModel(String path, Class<M> clazz) {
    return get(path).asModel(clazz);
  }

  /**
   * Read model using Type Safe Configuration implementation or Jackson
   *
   * @param clazz model class type
   * @param <T>   class Type
   * @return the model
   */
  public static <T extends CHoconPath, M> M asModel(T config, Class<M> clazz) {
    return get(config).asModel(clazz);
  }

  /**
   * Read model using Type Safe Configuration implementation or Jackson
   *
   * @param clazz model class type
   * @param <T>   class Type
   * @return the model
   */
  public static <T extends CHoconPath, M> List<M> asList(T config, Class<M> clazz) {
    return get(config).asList(clazz);
  }
}

package org.catools.common.hocon.utils;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigRenderOptions;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.catools.common.hocon.exception.CHoconPathNotFoundException;
import org.catools.common.hocon.exception.CHoconPathOperationException;
import org.catools.common.utils.CJsonUtil;
import org.catools.common.utils.CStringUtil;

/**
 * A class to work safe with Type Safe Configuration
 */
@Slf4j
public class CHoconUtils {
  public static final String VALUE_PATH = ".value";
  public static final String SENSITIVE_PATH = ".sensitive";


  /**
   * Read and resolve variables in special path in the resource configuration to model using Type Safe Configuration
   * implementation
   *
   * @param resourceBasename path to the resource.
   * @param objectName       the object name to convert.
   * @param clazz            model class type
   * @param <T>              class Type
   * @return the model
   */
  public static <T> T resolveModel(String resourceBasename, String objectName, Class<T> clazz) {
    ConfigFactory.invalidateCaches();
    return resolveModel(resourceBasename, ConfigFactory.load(), objectName, clazz);
  }

  /**
   * Read and resolve variables in special path in the resource configuration to model using Type Safe Configuration
   * implementation
   *
   * @param resourceBasename path to the resource.
   * @param resolveWith      the config to be used to resolve variables
   * @param objectName       the object name to convert.
   * @param clazz            model class type
   * @param <T>              class Type
   * @return the model
   */
  public static <T> T resolveModel(String resourceBasename, Config resolveWith, String objectName, Class<T> clazz) {
    Config config = ConfigFactory.parseResources(resourceBasename).resolveWith(resolveWith).getConfig(objectName);
    try {
      return ConfigBeanFactory.create(config, clazz);
    } catch (Exception ex) {
      String jsonFormatString = config.resolve().root().render(ConfigRenderOptions.concise());
      return CJsonUtil.read(jsonFormatString, clazz);
    }
  }

  /**
   * Read special path in the resource configuration to model using Type Safe Configuration
   * implementation
   *
   * @param resourceBasename path to the resource.
   * @param objectName       the object name to convert.
   * @param clazz            model class type
   * @param <T>              class Type
   * @return the model
   */
  public static <T> T readModel(String resourceBasename, String objectName, Class<T> clazz) {
    ConfigFactory.invalidateCaches();
    Config config = ConfigFactory.load(resourceBasename).getConfig(objectName);
    try {
      return ConfigBeanFactory.create(config, clazz);
    } catch (Exception ex) {
      String jsonFormatString = config.resolve().root().render(ConfigRenderOptions.concise());
      return CJsonUtil.read(jsonFormatString, clazz);
    }
  }

  /**
   * Read first value from json using <a href="https://github.com/json-path/JsonPath">JsonPath
   * expression</a>.
   *
   * @param input    valid json string value
   * @param jsonPath JsonPath expression to search for
   * @param clazz    model type to be used
   * @param <T>      generic type for class
   * @return a model
   * @throws CHoconPathNotFoundException if the given path is not valid
   */
  public static <T> T getFirstModelByJsonPath(String input, String jsonPath, Class<T> clazz) {
    try {
      Object obj = JsonPath.read(input, jsonPath);
      if (obj instanceof JSONArray) {
        obj = ((JSONArray) obj).get(0);
      }
      String json = CJsonUtil.toString(obj);
      return CJsonUtil.read(json, clazz);
    } catch (PathNotFoundException ex) {
      throw new CHoconPathNotFoundException(input, jsonPath, ex);
    }
  }

  /**
   * Read value from json using <a href="https://github.com/json-path/JsonPath">JsonPath
   * expression</a>.
   *
   * @param input    valid json string value
   * @param jsonPath JsonPath expression to search for
   * @param clazz    model type to be used
   * @param <T>      generic type for class
   * @return a model
   */
  public static <T> T getModelByJsonPath(String input, String jsonPath, Class<T> clazz) {
    Object obj = JsonPath.read(input, jsonPath);
    String json = CJsonUtil.toString(obj);
    return CJsonUtil.read(json, clazz);
  }

  /**
   * Read string value from json input using <a
   * href="https://github.com/json-path/JsonPath">JsonPath expression</a>. <br>
   * If the given expression points to the array, then return the string value of first element.
   *
   * @param input    valid json string value
   * @param jsonPath JsonPath expression to search for
   * @return String value
   * @throws CHoconPathNotFoundException if the given path is not valid
   */
  public static String getFirstStringFromJsonPath(String input, String jsonPath) {
    try {
      Object value = JsonPath.read(input, jsonPath);
      if (value instanceof JSONArray) {
        return String.valueOf(((JSONArray) value).get(0));
      }
      return String.valueOf(value);
    } catch (PathNotFoundException ex) {
      throw new CHoconPathNotFoundException(input, jsonPath, ex);
    }
  }

  /**
   * Read string value from json using <a href="https://github.com/json-path/JsonPath">JsonPath
   * expression</a>.
   *
   * @param input    valid json string value
   * @param jsonPath JsonPath expression to search for
   * @return String value
   * @throws CHoconPathNotFoundException  if the given path is not valid
   * @throws CHoconPathOperationException if the given path is valid but points to the value which
   *                                      cannot be cast to String (i.e. array).
   */
  public static String getStringByJsonPath(String input, String jsonPath) {
    try {
      return JsonPath.read(input, jsonPath);
    } catch (ClassCastException ex) {
      throw new CHoconPathOperationException(input, jsonPath, ex);
    } catch (PathNotFoundException ex) {
      throw new CHoconPathNotFoundException(input, jsonPath, ex);
    }
  }

  /**
   * Build environment variable name from config path for defined HOCON config.
   *
   * @param path configuration path in hocon config
   * @return environment variable name
   */
  public static String pathToEnvVariableName(String path) {
    path = path.endsWith(VALUE_PATH) ? CStringUtil.removeEnd(path, VALUE_PATH) : path;
    return path.toUpperCase().replaceAll("[^a-zA-Z0-9]+", "_");
  }

  /**
   * Check if input has defined json path.
   *
   * @param input    valid json string value
   * @param jsonPath JsonPath expression to search for
   * @return true if path found otherwise false
   */
  public static boolean has(String input, String jsonPath) {
    try {
      JsonPath.read(input, jsonPath);
      return true;
    } catch (PathNotFoundException ex) {
      return false;
    }
  }
}

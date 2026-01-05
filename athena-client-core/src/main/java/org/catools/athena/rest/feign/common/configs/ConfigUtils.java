package org.catools.athena.rest.feign.common.configs;

import com.google.common.collect.Sets;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigList;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigValue;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.rest.feign.common.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class ConfigUtils {
  private static final String VALUE = "value";

  private static Config config;

  static {
    reload();
  }

  public static final String CONFIGS_TO_LOAD = "CONFIGS_TO_LOAD";

  /**
   * Load configuration and set them in System.properties
   */
  public static synchronized void reload() {
    ConfigFactory.invalidateCaches();
    String configToLoad = getProperty(CONFIGS_TO_LOAD);
    config = configToLoad != null ? ConfigFactory.load(configToLoad) : ConfigFactory.load();
  }

  public static Set<MetadataDto> getMetadataSet(final String propertyName) {
    return asSet(propertyName, MetadataDto.class);
  }

  public static Integer getInteger(final String property, Integer defaultValue) {
    return asT(property, defaultValue, Config::getInt);
  }

  public static Long getLong(final String property, Long defaultValue) {
    return asT(property, defaultValue, Config::getLong);
  }

  public static String getString(final String property, String defaultValue) {
    return asT(property, defaultValue, Config::getString);
  }

  public static String getString(final String property) {
    return getString(property, null);
  }

  public static List<String> getStrings(final String property, List<String> defaultValue) {
    return asT(property, defaultValue, Config::getStringList);
  }

  public static List<String> getStrings(final String property) {
    return getStrings(property, List.of());
  }

  public static Boolean getBoolean(final String property, Boolean defaultValue) {
    return asT(property, defaultValue, Config::getBoolean);
  }

  public static <T> T asModel(final String property, final Class<T> clazz) {
    try {
      return asT(property, null, (c, p) -> getModelFromConfig(clazz, c.getConfig(p)));
    } catch (ConfigException ignored) {
      return asT(property, null, (c, p) -> getModelFromConfig(clazz, c.getList(p)));
    }
  }

  private static <T> T getModelFromConfig(Class<T> clazz, Config config) {
    String jsonFormatString = config.resolve().root().render(ConfigRenderOptions.concise());
    return JsonUtils.readValue(jsonFormatString, clazz);
  }

  private static <T> T getModelFromConfig(Class<T> clazz, ConfigList config) {
    String jsonFormatString = config.render(ConfigRenderOptions.concise());
    return JsonUtils.readValue(jsonFormatString, clazz);
  }

  public static <T> Set<T> asSet(final String property, final Class<T> clazz) {
    return asT(property, Sets.newHashSet(), (c, p) -> {
      Set<T> output = new HashSet<>();
      List<? extends Config> configs = config.getConfigList(property);

      for (Config val : configs) {
        output.add(getModelFromConfig(clazz, val));
      }
      return output;
    });
  }

  public static boolean isDefined(final String property) {
    try {
      return !config.getIsNull(property);
    } catch (ConfigException ex) {
      return false;
    }
  }

  private static Stream<Map.Entry<String, ConfigValue>> getUserDefinedSettings() {
    return config.entrySet().stream().filter(entry -> entry.getValue().origin().resource() != null);
  }


  public <T> T asT(String path, T defaultValue, BiFunction<Config, String, T> fuc) {
    // If configuration defined then we might have 2 scenarios.
    // 1- Case when value setup directly in configuration.
    // 2- Case when value setup value using environmental variables.
    // In the second scenario we need to read and parse the string value and process it.
    // If the value is not defined in configuration then try to read value from Environmental Variables or System Properties
    if (isDefined(path)) {
      return getDefinedValue(path, fuc);
    }

    if (isDefined(convertToEnvVariable(path))) {
      return getDefinedValue(convertToEnvVariable(path), fuc);
    }
    String value = readPropertyOrEnv(path);

    if (StringUtils.isBlank(value)) {
      return defaultValue;
    }

    try {
      return Optional.of(parseStringValue(value)).map(c -> fuc.apply(c, VALUE)).orElse(defaultValue);
    } catch (ConfigException ignored) {
      return Optional.of(parseStringValue(String.format("\"%s\"", value))).map(c -> fuc.apply(c, VALUE)).orElse(defaultValue);
    }
  }

  private static <T> T getDefinedValue(String path, BiFunction<Config, String, T> fuc) {
    try {
      return fuc.apply(config, path);
    } catch (ConfigException ex) {
      return fuc.apply(parseStringPath(path), VALUE);
    }
  }

  private static Config parseStringPath(String path) {
    return parseStringValue(config.getString(path));
  }

  private static Config parseStringValue(String value) {
    return ConfigFactory.parseString(VALUE + " = " + value);
  }

  private static String readPropertyOrEnv(String property) {
    String key = convertToEnvVariable(property);
    return getProperty(key);
  }

  @NotNull
  private static String convertToEnvVariable(final String property) {
    return property.toUpperCase().replaceAll("[^a-zA-Z0-9]+", "_");
  }


  /**
   * Read system property or environment variable and return the value.
   *
   * @param key key to search for
   * @return value from System Property or Environmental Variables
   */
  public static String getProperty(String key) {
    return StringUtils.defaultIfBlank(System.getProperty(key), System.getenv(key));
  }
}

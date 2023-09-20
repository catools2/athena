package org.catools.common.hocon.model;

import com.typesafe.config.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.catools.common.utils.CJsonUtil;
import org.testng.collections.Lists;

import java.util.ArrayList;
import java.util.List;

import static org.catools.common.hocon.utils.CHoconUtils.SENSITIVE_PATH;
import static org.catools.common.hocon.utils.CHoconUtils.VALUE_PATH;

@NoArgsConstructor
public class CHoconConfig implements CConfig {
  private static final String TEMP_LIST_VALUE_PLACE_HODLER = "value";
  private Config config;

  @Getter
  private String name;
  private String valuePath;
  private String path;

  public CHoconConfig(Config config, String path) {
    this(config, path, path.toUpperCase().replaceAll("\\W", "_"));
  }

  public CHoconConfig(Config config, String path, String name) {
    this.config = config;
    this.path = path;
    this.name = name;

    this.valuePath = config.hasPath(path + VALUE_PATH) ? path + VALUE_PATH : path;
  }

  public boolean isSensitive() {
    return config.hasPath(path + SENSITIVE_PATH) && config.getBoolean(path + SENSITIVE_PATH);
  }

  public boolean isDefined() {
    try {
      return !getConfig().getIsNull(valuePath);
    } catch (ConfigException ex) {
      return false;
    }
  }

  public String asString() {
    return isNotDefined() ? "" : getConfig().getString(valuePath);
  }

  public Boolean asBoolean() {
    return isDefined() && getConfig().getBoolean(valuePath);
  }

  public Number asNumber() {
    return isNotDefined() ? null : getConfig().getNumber(valuePath);
  }

  public Integer asInteger() {
    return isNotDefined() ? 0 : getConfig().getInt(valuePath);
  }

  public Long asLong() {
    return isNotDefined() ? 0 : getConfig().getLong(valuePath);
  }

  public Double asDouble() {
    return isNotDefined() ? 0 : getConfig().getDouble(valuePath);
  }

  public <T extends Enum<T>> T asEnum(Class<T> aClass) {
    return isNotDefined() ? null : getConfig().getEnum(aClass, valuePath);
  }

  public Object asObject() {
    return isNotDefined() ? null : getConfig().getAnyRef(valuePath);
  }

  public List<Boolean> asBooleans() {
    try {
      return isNotDefined() ? Lists.newArrayList() : getConfig().getBooleanList(valuePath);
    } catch (ConfigException ex) {
      return parseStringList().getBooleanList(TEMP_LIST_VALUE_PLACE_HODLER);
    }
  }

  public List<Number> asNumbers() {
    try {
      return isNotDefined() ? Lists.newArrayList() : getConfig().getNumberList(valuePath);
    } catch (ConfigException ex) {
      return parseStringList().getNumberList(TEMP_LIST_VALUE_PLACE_HODLER);
    }
  }

  public List<Integer> asIntegers() {
    try {
      return isNotDefined() ? Lists.newArrayList() : getConfig().getIntList(valuePath);
    } catch (ConfigException ex) {
      return parseStringList().getIntList(TEMP_LIST_VALUE_PLACE_HODLER);
    }
  }

  public List<Long> asLongs() {
    try {
      return isNotDefined() ? Lists.newArrayList() : getConfig().getLongList(valuePath);
    } catch (ConfigException ex) {
      return parseStringList().getLongList(TEMP_LIST_VALUE_PLACE_HODLER);
    }
  }

  public List<Double> asDoubles() {
    try {
      return isNotDefined() ? Lists.newArrayList() : getConfig().getDoubleList(valuePath);
    } catch (ConfigException ex) {
      return parseStringList().getDoubleList(TEMP_LIST_VALUE_PLACE_HODLER);
    }
  }

  public List<String> asStrings() {
    try {
      return isNotDefined() ? Lists.newArrayList() : getConfig().getStringList(valuePath);
    } catch (ConfigException ex) {
      return parseStringList().getStringList(TEMP_LIST_VALUE_PLACE_HODLER);
    }
  }

  public <T extends Enum<T>> List<T> asEnums(Class<T> aClass) {
    try {
      return isNotDefined() ? Lists.newArrayList() : getConfig().getEnumList(aClass, valuePath);
    } catch (ConfigException ex) {
      return parseStringList().getEnumList(aClass, TEMP_LIST_VALUE_PLACE_HODLER);
    }
  }

  public List<? extends Object> asObjects() {
    return isNotDefined() ? Lists.newArrayList() : getConfig().getAnyRefList(valuePath);
  }

  /**
   * Read model using Type Safe Configuration implementation or Jackson
   *
   * @param clazz model class type
   * @param <T>   class Type
   * @return the model
   */
  public <T> T asModel(Class<T> clazz) {
    Config val = getConfig().getConfig(this.valuePath);
    return getModelFromConfig(clazz, val);
  }

  /**
   * Read model using Type Safe Configuration implementation or Jackson
   *
   * @param clazz model class type
   * @param <T>   class Type
   * @return the model
   */
  public <T> List<T> asList(Class<T> clazz) {
    List<T> output = new ArrayList<>();
    List<? extends Config> configs = getConfig().getConfigList(this.valuePath);
    for (Config val : configs) {
      output.add(getModelFromConfig(clazz, val));
    }
    return output;
  }

  private static <T> T getModelFromConfig(Class<T> clazz, Config val) {
    try {
      return ConfigBeanFactory.create(val, clazz);
    } catch (Exception ex) {
      String jsonFormatString = val.resolve().root().render(ConfigRenderOptions.concise());
      return CJsonUtil.read(jsonFormatString, clazz);
    }
  }

  private Config getConfig() {
    return config;
  }

  private Config parseStringList() {
    String listString = config.getString(valuePath);
    return ConfigFactory.parseString(TEMP_LIST_VALUE_PLACE_HODLER + " = " + listString);
  }
}

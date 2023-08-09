package org.catools.common.hocon.model;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigRenderOptions;
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
    return isNotDefined() ? Lists.newArrayList() : getConfig().getBooleanList(valuePath);
  }

  public List<Number> asNumbers() {
    return isNotDefined() ? Lists.newArrayList() : getConfig().getNumberList(valuePath);
  }

  public List<Integer> asIntegers() {
    return isNotDefined() ? Lists.newArrayList() : getConfig().getIntList(valuePath);
  }

  public List<Long> asLongs() {
    return isNotDefined() ? Lists.newArrayList() : getConfig().getLongList(valuePath);
  }

  public List<Double> asDoubles() {
    return isNotDefined() ? Lists.newArrayList() : getConfig().getDoubleList(valuePath);
  }

  public List<String> asStrings() {
    return isNotDefined() ? Lists.newArrayList() : getConfig().getStringList(valuePath);
  }

  public <T extends Enum<T>> List<T> asEnumList(Class<T> aClass) {
    return isNotDefined() ? Lists.newArrayList() : getConfig().getEnumList(aClass, valuePath);
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
}

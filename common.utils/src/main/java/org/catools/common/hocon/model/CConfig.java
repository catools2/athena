package org.catools.common.hocon.model;

import java.util.List;

public interface CConfig {
  /**
   * Define if the config value is sensitive.
   * Sensitive data replace to "*" when using catools common.logger.
   *
   * @return true if data sensitive otherwise false
   */
  boolean isSensitive();

  /**
   * Define if the config value is set.
   *
   * @return true if value is set otherwise false
   */
  boolean isDefined();

  /**
   * Define if the config value is not set.
   *
   * @return true if value is NOT set otherwise false
   */
  default boolean isNotDefined() {
    return !isDefined();
  }

  /**
   * return the string value of configuration
   *
   * @return string value
   */
  String asString();

  /**
   * return the string value of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return string value of configuration or the default value if the configuration is not defined
   */
  default String asString(String defaultValue) {
    return isDefined() ? asString() : defaultValue;
  }

  /**
   * return the Boolean value of configuration
   *
   * @return Boolean value of configuration
   */
  Boolean asBoolean();

  /**
   * return the Boolean value of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Boolean value of configuration or the default value if the configuration is not defined
   */
  default Boolean asBoolean(Boolean defaultValue) {
    return isDefined() ? asBoolean() : defaultValue;
  }

  /**
   * return the Number value of configuration
   *
   * @return Number value of configuration
   */
  Number asNumber();

  /**
   * return the Number value of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Number value of configuration or the default value if the configuration is not defined
   */
  default Number asNumber(Number defaultValue) {
    return isDefined() ? asNumber() : defaultValue;
  }

  /**
   * return the Integer value of configuration
   *
   * @return Integer value of configuration
   */
  Integer asInteger();

  /**
   * return the Integer value of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Integer value of configuration or the default value if the configuration is not defined
   */
  default Integer asInteger(Integer defaultValue) {
    return isDefined() ? asInteger() : defaultValue;
  }

  /**
   * return the Long value of configuration
   *
   * @return Long value of configuration
   */
  Long asLong();

  /**
   * return the Long value of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Long value of configuration or the default value if the configuration is not defined
   */
  default Long asLong(Long defaultValue) {
    return isDefined() ? asLong() : defaultValue;
  }

  /**
   * return the Double value of configuration
   *
   * @return Double value of configuration
   */
  Double asDouble();

  /**
   * return the Double value of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Double value of configuration or the default value if the configuration is not defined
   */
  default Double asDouble(Double defaultValue) {
    return isDefined() ? asDouble() : defaultValue;
  }

  /**
   * return the Enum value of configuration
   *
   * @param clazz model class type
   * @return Enum value of configuration
   */
  <T extends Enum<T>> T asEnum(Class<T> clazz);

  /**
   * return the Enum value of configuration or the default value if the configuration is not defined
   *
   * @param clazz model class type
   * @param defaultValue default value to set if configuration is not defined
   * @return Enum value of configuration or the default value if the configuration is not defined
   */
  default <T extends Enum<T>> T asEnum(Class<T> clazz, T defaultValue) {
    return isDefined() ? asEnum(clazz) : defaultValue;
  }

  /**
   * return the Object value of configuration
   *
   * @return Object value of configuration
   */
  Object asObject();

  /**
   * return the Object value of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Object value of configuration or the default value if the configuration is not defined
   */
  default Object asObject(Object defaultValue) {
    return isDefined() ? asObject() : defaultValue;
  }

  /**
   * return the string list of configuration
   *
   * @return string list of configuration
   */
  List<String> asStrings();

  /**
   * return the string list of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return string list of configuration or the default value if the configuration is not defined
   */
  default List<String> asStrings(List<String> defaultValue) {
    return isDefined() ? asStrings() : defaultValue;
  }

  /**
   * return the Boolean list of configuration
   *
   * @return Boolean list of configuration
   */
  List<Boolean> asBooleans();

  /**
   * return the Boolean list of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Boolean list of configuration or the default value if the configuration is not defined
   */
  default List<Boolean> asBooleans(List<Boolean> defaultValue) {
    return isDefined() ? asBooleans() : defaultValue;
  }

  /**
   * return the Number list of configuration
   *
   * @return Number list of configuration
   */
  List<Number> asNumbers();

  /**
   * return the Number list of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Number list of configuration or the default value if the configuration is not defined
   */
  default List<Number> asNumbers(List<Number> defaultValue) {
    return isDefined() ? asNumbers() : defaultValue;
  }

  /**
   * return the Integer list of configuration
   *
   * @return Integer list of configuration
   */
  List<Integer> asIntegers();

  /**
   * return the Integer list of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Integer list of configuration or the default value if the configuration is not defined
   */
  default List<Integer> asIntegers(List<Integer> defaultValue) {
    return isDefined() ? asIntegers() : defaultValue;
  }

  /**
   * return the Long list of configuration
   *
   * @return Long list of configuration
   */
  List<Long> asLongs();

  /**
   * return the Long list of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Long list of configuration or the default value if the configuration is not defined
   */
  default List<Long> asLongs(List<Long> defaultValue) {
    return isDefined() ? asLongs() : defaultValue;
  }

  /**
   * return the Double list of configuration
   *
   * @return Double list of configuration
   */
  List<Double> asDoubles();

  /**
   * return the Double list of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Double list of configuration or the default value if the configuration is not defined
   */
  default List<Double> asDoubles(List<Double> defaultValue) {
    return isDefined() ? asDoubles() : defaultValue;
  }

  /**
   * return the Enum list of configuration
   *
   * @return Enum list of configuration
   */
  <T extends Enum<T>> List<T> asEnumList(Class<T> aClass);

  /**
   * return the Enum list of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Enum list of configuration or the default value if the configuration is not defined
   */
  default <T extends Enum<T>> List<T> asEnumList(Class<T> aClass, List<T> defaultValue) {
    return isDefined() ? asEnumList(aClass) : defaultValue;
  }

  /**
   * return the Object list of configuration
   *
   * @return Object list of configuration
   */
  List<? extends Object> asObjects();

  /**
   * return the Object list of configuration or the default value if the configuration is not defined
   *
   * @param defaultValue default value to set if configuration is not defined
   * @return Object list of configuration or the default value if the configuration is not defined
   */
  default List<? extends Object> asObjects(List<Object> defaultValue) {
    return isDefined() ? asObjects() : defaultValue;
  }

  /**
   * Read model from configuration
   *
   * @param clazz model class type
   * @param <T>   class Type
   * @return the model
   */
  <T> T asModel(Class<T> clazz);

  /**
   * Read model from configuration
   *
   * @param clazz        model class type
   * @param <T>          class Type
   * @param defaultValue the default value to return if the property not found
   * @return the model
   */
  default <T> T asModel(Class<T> clazz, T defaultValue) {
    return isDefined() ? asModel(clazz) : defaultValue;
  }
}

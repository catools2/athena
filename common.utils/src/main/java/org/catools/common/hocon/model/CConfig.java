package org.catools.common.hocon.model;

import java.util.List;

public interface CConfig {
  boolean isSensitive();

  boolean isDefined();

  default boolean isNotDefined() {
    return !isDefined();
  }

  String asString();

  default String asString(String defaultValue) {
    return isDefined() ? asString() : defaultValue;
  }

  Boolean asBoolean();

  default Boolean asBoolean(Boolean defaultValue) {
    return isDefined() ? asBoolean() : defaultValue;
  }

  Number asNumber();

  default Number asNumber(Number defaultValue) {
    return isDefined() ? asNumber() : defaultValue;
  }

  Integer asInteger();

  default Integer asInteger(Integer defaultValue) {
    return isDefined() ? asInteger() : defaultValue;
  }

  Long asLong();

  default Long asLong(Long defaultValue) {
    return isDefined() ? asLong() : defaultValue;
  }

  Double asDouble();

  default Double asDouble(Double defaultValue) {
    return isDefined() ? asDouble() : defaultValue;
  }

  <T extends Enum<T>> T asEnum(Class<T> aClass);

  default <T extends Enum<T>> T asEnum(Class<T> aClass, T defaultValue) {
    return isDefined() ? asEnum(aClass) : defaultValue;
  }

  Object asObject();

  default Object asObject(Object defaultValue) {
    return isDefined() ? asObject() : defaultValue;
  }

  List<String> asStrings();

  default List<String> asStrings(List<String> defaultValue) {
    return isDefined() ? asStrings() : defaultValue;
  }

  List<Boolean> asBooleans();

  default List<Boolean> asBooleans(List<Boolean> defaultValue) {
    return isDefined() ? asBooleans() : defaultValue;
  }

  List<Number> asNumbers();

  default List<Number> asNumbers(List<Number> defaultValue) {
    return isDefined() ? asNumbers() : defaultValue;
  }

  List<Integer> asIntegers();

  default List<Integer> asIntegers(List<Integer> defaultValue) {
    return isDefined() ? asIntegers() : defaultValue;
  }

  List<Long> asLongs();

  default List<Long> asLongs(List<Long> defaultValue) {
    return isDefined() ? asLongs() : defaultValue;
  }

  List<Double> asDoubles();

  default List<Double> asDoubles(List<Double> defaultValue) {
    return isDefined() ? asDoubles() : defaultValue;
  }

  <T extends Enum<T>> List<T> asEnumList(Class<T> aClass);

  default <T extends Enum<T>> List<T> asEnumList(Class<T> aClass, List<T> defaultValue) {
    return isDefined() ? asEnumList(aClass) : defaultValue;
  }

  List<? extends Object> asObjects();

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

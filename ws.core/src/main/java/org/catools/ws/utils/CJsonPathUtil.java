package org.catools.ws.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.experimental.UtilityClass;
import org.catools.common.collections.CHashMap;
import org.catools.common.collections.CList;
import org.catools.common.collections.interfaces.CMap;
import org.catools.common.exception.CRuntimeException;
import org.catools.common.extensions.types.CStaticBooleanExtension;
import org.catools.common.extensions.types.CStaticNumberExtension;
import org.catools.common.extensions.types.CStaticObjectExtension;
import org.catools.common.extensions.types.CStaticStringExtension;

import java.io.IOException;
import java.util.Collection;

@UtilityClass
public class CJsonPathUtil {

  public static <T> CList<T> readList(String input, String jsonPath) {
    CList<T> list = new CList<>();
    list.addAll(read(input, jsonPath));
    return list;
  }

  public static <K, V> CMap<K, V> readMap(String input, String jsonPath) {
    CHashMap<K, V> map = new CHashMap<>();
    map.putAll(read(input, jsonPath));
    return map;
  }

  public static CStaticObjectExtension readJsonNodeExtension(String input, String jsonPath) {
    return new CStaticObjectExtension() {
      @Override
      @JsonIgnore
      public JsonNode _get() {
        return readJsonNode(input, jsonPath);
      }
    };
  }

  public static CStaticStringExtension readStringExtension(String input, String jsonPath) {
    return new CStaticStringExtension() {
      @Override
      @JsonIgnore
      public String _get() {
        return read(input, jsonPath);
      }
    };
  }

  public static <N extends Number & Comparable<N>> CStaticNumberExtension<N> readNumberExtension(
      String input, String jsonPath) {
    return new CStaticNumberExtension<>() {
      @Override
      @JsonIgnore
      public N _get() {
        return read(input, jsonPath);
      }
    };
  }

  public static CStaticBooleanExtension readBooleanExtension(String input, String jsonPath) {
    return new CStaticBooleanExtension() {
      @Override
      @JsonIgnore
      public Boolean _get() {
        return read(input, jsonPath);
      }
    };
  }

  @SuppressWarnings("unchecked")
  public static <T> T read(String input, String jsonPath) {
    Object result = JsonPath.parse(input).read(jsonPath);
    if (result instanceof Collection) {
      return ((Collection<T>) result).stream().findFirst().orElse(null);
    }
    return (T) result;
  }

  public static JsonNode readJsonNode(String input) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.findAndRegisterModules();
      return mapper.readTree(input);
    } catch (IOException e) {
      throw new CRuntimeException("Filed to string as json: " + input, e);
    }
  }

  public static JsonNode readJsonNode(String input, String jsonPath) {
    return readJsonNode(input).at(jsonPath);
  }
}
